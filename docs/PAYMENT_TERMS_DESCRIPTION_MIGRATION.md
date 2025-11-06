# Database-Backed Implementation for Payment Terms Description Identifier

This document describes the database-backed implementation for UN/CEFACT Payment Terms Description Identifier (Code List 64277), replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/PaymentTermsDescriptionIdentifier.java`

JPA Entity class for storing UN/CEFACT Payment Terms Description Identifiers in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 2)
- Fields: `name`, `description`, `isDraftRequired`, timestamps
- Code normalization (trim only, no uppercase)
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 7 codes (1-7)

**Payment Description Categories**:
- **Banking Drafts** (codes 1-3):
  - `1` - Draft(s) drawn on issuing bank
  - `2` - Draft(s) drawn on advising bank
  - `3` - Draft(s) drawn on reimbursing bank
- **Other Drafts** (codes 4-5):
  - `4` - Draft(s) drawn on applicant
  - `5` - Draft(s) drawn on any other drawee
- **No Draft** (codes 6-7):
  - `6` - No drafts required
  - `7` - Payment means specified in commercial account summary

**Business Logic Methods**:
- `isDraftRequired()` - Check if draft is required
- `isBankingDraft()` - Check if banking draft (codes 1-3)
- `isIssuingBank()` - Code 1
- `isAdvisingBank()` - Code 2
- `isReimbursingBank()` - Code 3
- `isNoDraft()` - Code 6

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/PaymentTermsDescriptionIdentifierRepository.java`

Spring Data JPA repository for database operations.

**Query Methods**:
- `findByCode(code)` - Find by code
- `findDraftRequired()` - Get all requiring draft (codes 1-5)
- `findNoDraft()` - Get all not requiring draft (codes 6-7)
- `findBankingRelated()` - Get banking drafts (codes 1-3)
- `findByNameContaining(name)` - Search by name
- `findByDescriptionContaining(keyword)` - Search by description
- `existsByCode(code)` - Check if code exists
- `findIssuingBankDraft()` - Get code 1
- `findAdvisingBankDraft()` - Get code 2
- `findReimbursingBankDraft()` - Get code 3
- `findNoDraftOption()` - Get code 6
- `findCommercialAccountSummary()` - Get code 7

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/PaymentTermsDescriptionIdentifierAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods:
  - `isValid(code)` - Validate code exists
  - `getPaymentTermsDescriptionName(code)` - Get name from code
  - `isDraftRequired(code)` - Check if draft required
  - `isBankingDraft(code)` - Check if banking draft
  - `isIssuingBankDraft(code)` - Check if issuing bank
  - `isNoDraft(code)` - Check if no draft

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/paymenttermsdescription/PaymentTermsDescriptionType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A`
- Helper methods: `getCode()`, `getName()`, `getDescription()`, `isDraftRequired()`, `isBankingDraft()`, `isIssuingBank()`, `isAdvisingBank()`, `isReimbursingBank()`, `isNoDraft()`
- Factory methods: `of(String)`, `of(PaymentTermsDescriptionIdentifier)`

**File**: `src/main/java/com/wpanther/etax/xml/paymenttermsdescription/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A`
- Prefix: `ids64277`
- Element form: QUALIFIED

### 5. Database Schema (Already Exists)

**File**: `payment_terms_description_identifier.sql` (135 lines)

PostgreSQL table schema with:
- Table definition with 6 columns
- 2 indexes for performance (name, is_draft_required)
- Auto-update timestamp trigger
- 3 views (draft_required, no_draft, banking)
- 2 helper functions (get_payment_terms_name, is_draft_required)
- 7 data inserts (codes 1-7)

## Architecture Pattern

This implementation follows the **exact same pattern** as other code lists:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <PaymentTermsDescriptionIdentifier>1</...>                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      PaymentTermsDescriptionType (XML Wrapper)              │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│  PaymentTermsDescriptionIdentifierAdapter (XmlAdapter)     │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│  PaymentTermsDescriptionIdentifierRepository (Spring JPA)  │
│  - findByCode()                                             │
│  - findDraftRequired()                                      │
│  - findBankingRelated()                                     │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│   PaymentTermsDescriptionIdentifier (JPA Entity)           │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - Draft requirement classification                         │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: payment_terms_description_identifier (7 records)   │
└─────────────────────────────────────────────────────────────┘
```

## Benefits

### ✅ Type Safety
- Strongly-typed entities instead of plain strings
- Compile-time validation of entity structure

### ✅ Validation
- Automatic validation during XML unmarshalling
- Rejects invalid codes or creates placeholder
- Database constraints enforce data integrity

### ✅ Rich Metadata
- Access to payment terms names and descriptions
- Draft requirement classification
- Banking draft identification

### ✅ Business Logic Integration
- Query draft-required terms
- Filter banking-specific drafts
- Identify no-draft options
- Letter of credit support

### ✅ Query Capabilities
- Search by draft requirement
- Filter banking-related terms
- Specific bank draft queries
- Case-insensitive search

### ✅ Maintainability
- Centralized management in database
- Easy to add new identifiers
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <PaymentTermsDescriptionIdentifier>1</PaymentTermsDescriptionIdentifier>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access payment terms description details
PaymentTermsDescriptionType desc = invoice.getPaymentTermsDescription();
String code = desc.getCode();                  // "1"
String name = desc.getName();                  // "Draft(s) drawn on issuing bank"
String description = desc.getDescription();    // "Draft(s) must be drawn on the issuing bank."
boolean draftReq = desc.isDraftRequired();     // true
boolean bankDraft = desc.isBankingDraft();     // true
boolean issuing = desc.isIssuingBank();        // true
```

### XML Marshalling

```java
// Create from entity
PaymentTermsDescriptionIdentifier entity = repository.findByCode("1").get();
PaymentTermsDescriptionType desc = PaymentTermsDescriptionType.of(entity);

// Or create from code string
PaymentTermsDescriptionType desc = PaymentTermsDescriptionType.of("6");

// Set in invoice
invoice.setPaymentTermsDescription(desc);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <PaymentTermsDescriptionIdentifier>1</PaymentTermsDescriptionIdentifier>
```

### Validation

```java
// Check if code is valid
if (PaymentTermsDescriptionIdentifierAdapter.isValid("1")) {
    // Code exists
}

// Get description name
String name = PaymentTermsDescriptionIdentifierAdapter.getPaymentTermsDescriptionName("1");
// Returns: "Draft(s) drawn on issuing bank"

// Check if draft required
boolean required = PaymentTermsDescriptionIdentifierAdapter.isDraftRequired("6");
// Returns: false

// Check if banking draft
boolean banking = PaymentTermsDescriptionIdentifierAdapter.isBankingDraft("2");
// Returns: true

// Check if issuing bank
boolean issuing = PaymentTermsDescriptionIdentifierAdapter.isIssuingBankDraft("1");
// Returns: true
```

### Repository Queries

```java
@Autowired
private PaymentTermsDescriptionIdentifierRepository repository;

// Get all draft-required terms
List<PaymentTermsDescriptionIdentifier> draftRequired = repository.findDraftRequired();
// Returns: codes 1-5

// Get no-draft terms
List<PaymentTermsDescriptionIdentifier> noDraft = repository.findNoDraft();
// Returns: codes 6-7

// Get banking drafts
List<PaymentTermsDescriptionIdentifier> bankingDrafts = repository.findBankingRelated();
// Returns: codes 1-3

// Get specific drafts
Optional<PaymentTermsDescriptionIdentifier> issuing = repository.findIssuingBankDraft();
Optional<PaymentTermsDescriptionIdentifier> advising = repository.findAdvisingBankDraft();
Optional<PaymentTermsDescriptionIdentifier> reimbursing = repository.findReimbursingBankDraft();

// Get no draft option
Optional<PaymentTermsDescriptionIdentifier> noDraftOpt = repository.findNoDraftOption();
```

## Database Setup

```sql
-- 1. Create schema (already done)
\i payment_terms_description_identifier.sql

-- 2. Verify
SELECT COUNT(*) FROM payment_terms_description_identifier;
-- Expected: 7

SELECT COUNT(*) FROM payment_terms_description_identifier WHERE is_draft_required = true;
-- Expected: 5

-- 3. Query examples
SELECT * FROM payment_terms_description_identifier WHERE code = '1';
SELECT * FROM payment_terms_draft_required;
SELECT * FROM payment_terms_no_draft;
SELECT * FROM payment_terms_banking;

-- 4. Use functions
SELECT get_payment_terms_name('1');
SELECT is_draft_required('6');
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "1";  // Just a string, could be invalid
JAXBElement<String> desc = objectFactory.createPaymentTermsDescriptionIdentifier(code);
// No way to know if "1" is valid
// No way to get the name or description
// No business logic integration
```

### After (Database-Backed)

```java
// Database-backed: PaymentTermsDescriptionType with full entity
PaymentTermsDescriptionType desc = PaymentTermsDescriptionType.of("1");
// Validates against database
// Full access to metadata

String code = desc.getCode();                   // "1"
String name = desc.getName();                   // "Draft(s) drawn on issuing bank"
String description = desc.getDescription();     // Full description
boolean draftRequired = desc.isDraftRequired(); // true
boolean bankingDraft = desc.isBankingDraft();   // true
boolean issuingBank = desc.isIssuingBank();     // true
```

## Comparison with Other Code Lists

| Feature | PaymentTermsTypeCode | PaymentTermsDescriptionIdentifier |
|---------|---------------------|-----------------------------------|
| **Total Codes** | 79 | 7 |
| **Code Format** | Numeric + ZZZ | Numeric 1-7 |
| **JAXB Original** | String | String |
| **Categories** | ✅ 9 categories | ✅ 3 categories |
| **Boolean Flags** | ✅ immediate/deferred/discount | ✅ isDraftRequired |
| **Entity** | ✅ | ✅ |
| **Repository** | ✅ | ✅ |
| **Adapter** | ✅ | ✅ |
| **XML Wrapper** | ✅ | ✅ |
| **Package-info** | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ (already exists) |

## Code List 64277 - All Codes

| Code | Name | Draft Required | Category |
|------|------|----------------|----------|
| 1 | Draft(s) drawn on issuing bank | ✅ | Banking Draft |
| 2 | Draft(s) drawn on advising bank | ✅ | Banking Draft |
| 3 | Draft(s) drawn on reimbursing bank | ✅ | Banking Draft |
| 4 | Draft(s) drawn on applicant | ✅ | Other Draft |
| 5 | Draft(s) drawn on any other drawee | ✅ | Other Draft |
| 6 | No drafts | ❌ | No Draft |
| 7 | Payment means specified in commercial account summary | ❌ | Specified Elsewhere |

## Letter of Credit Use Case

Payment Terms Description Identifiers are primarily used in **Letter of Credit (L/C)** transactions to specify how drafts (bills of exchange) should be drawn:

1. **Issuing Bank (Code 1)**: Draft payable by the bank that issued the L/C
2. **Advising Bank (Code 2)**: Draft payable by the bank that advises the beneficiary
3. **Reimbursing Bank (Code 3)**: Draft payable by the bank authorized to reimburse
4. **Applicant (Code 4)**: Draft payable by the buyer/importer
5. **Other Drawee (Code 5)**: Draft payable by another specified party
6. **No Draft (Code 6)**: Payment without draft requirement
7. **Commercial Account (Code 7)**: Terms specified in separate document

## Next Steps

1. **Database already setup** ✅ - SQL file exists and is loaded
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample L/C documents
4. **Use draft requirement queries** for banking logic
5. **Integrate with payment terms type code** for complete payment specification

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **PaymentTermsTypeCode** - Complete (79 codes)
- ✅ **PaymentTermsDescriptionIdentifier** - Just completed (7 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **DutyTaxFeeTypeCode** - Pending
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT Code List**: 64277 (Payment Terms Description Identifier)
**Schema Version**: D14A
**Use Case**: Letter of Credit draft specifications
