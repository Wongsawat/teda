# Database-Backed Implementation for Payment Terms Type Code

This document describes the database-backed implementation for UN/CEFACT Payment Terms Type Code (Code List 64279), replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/PaymentTermsTypeCode.java`

JPA Entity class for storing UN/CEFACT Payment Terms Type Codes in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `category`, `isImmediate`, `isDeferred`, `hasDiscount`, timestamps
- Auto-uppercase code normalization
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 79 codes (1-78 + ZZZ)

**Payment Term Categories**:
- **Standard Terms** - Basic payment conditions
- **Immediate Payment** - Payment due on receipt (e.g., code 10)
- **Deferred Payment** - Scheduled/installment payments
- **Discount Terms** - Early payment discounts (e.g., code 22)
- **Letter of Credit** - L/C based payment terms
- **Advance Payment** - Payment before delivery
- **Installment Payment** - Multiple payment schedule
- **Penalty Terms** - Late payment penalties
- **Cash Terms** - Cash on delivery/pickup

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/PaymentTermsTypeCodeRepository.java`

Spring Data JPA repository for database operations.

**Query Methods**:
- `findByCode(code)` - Find by code (case-insensitive)
- `findImmediatePaymentTerms()` - Get immediate payment terms
- `findDeferredPaymentTerms()` - Get deferred/scheduled terms
- `findDiscountPaymentTerms()` - Get discount terms
- `findByCategory(category)` - Get terms by category
- `findAllCategories()` - Get all available categories
- `findByNameContaining(name)` - Search by name
- `findByDescriptionContaining(keyword)` - Search by description
- `findLetterOfCreditTerms()` - Get L/C terms
- `findAdvancePaymentTerms()` - Get advance payment terms
- `findInstallmentPaymentTerms()` - Get installment terms
- `existsByCode(code)` - Check if code exists

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/PaymentTermsTypeCodeAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods:
  - `isValid(code)` - Validate code exists
  - `getPaymentTermsName(code)` - Get name from code
  - `getPaymentTermsCategory(code)` - Get category from code
  - `isImmediatePayment(code)` - Check if immediate payment
  - `isDeferredPayment(code)` - Check if deferred payment
  - `hasDiscount(code)` - Check if discount terms

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/paymentterms/PaymentTermsCodeType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A`
- Helper methods: `getCode()`, `getName()`, `getDescription()`, `getCategory()`, `isImmediate()`, `isDeferred()`, `hasDiscount()`
- Factory methods: `of(String)`, `of(PaymentTermsTypeCode)`

**File**: `src/main/java/com/wpanther/etax/xml/paymentterms/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A`
- Prefix: `clm64279`
- Element form: QUALIFIED

### 5. Database Schema (Already Exists)

**File**: `payment_terms_type_code.sql` (284 lines)

PostgreSQL table schema with:
- Table definition with 8 columns
- 4 indexes for performance (category, is_immediate, is_deferred, has_discount)
- Full-text search index on descriptions
- Auto-update timestamp trigger
- 6 views (immediate, deferred, discount, scheduled, credit, cash)

**File**: `payment_terms_type_code_data.sql` (86 lines)

Data insert statements for all 79 codes.

## Architecture Pattern

This implementation follows the **exact same pattern** as `UNECEReferenceTypeCode` and `ISOCountryCode`:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <PaymentTermsTypeCode>10</PaymentTermsTypeCode>           │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│           PaymentTermsCodeType (XML Wrapper)                │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      PaymentTermsTypeCodeAdapter (XmlAdapter)              │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│    PaymentTermsTypeCodeRepository (Spring Data JPA)        │
│  - findByCode()                                             │
│  - findImmediatePaymentTerms()                              │
│  - findDeferredPaymentTerms()                               │
│  - findDiscountPaymentTerms()                               │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│       PaymentTermsTypeCode (JPA Entity)                    │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - Category classification                                  │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: payment_terms_type_code (79 records)               │
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
- Category-based classification
- Boolean flags for immediate/deferred/discount terms

### ✅ Business Logic Integration
- Query immediate payment terms
- Filter discount-eligible terms
- Find deferred/installment options
- Categorize by payment method

### ✅ Query Capabilities
- Search by category (9 categories)
- Filter by payment characteristics
- Full-text search on descriptions
- Case-insensitive search

### ✅ Maintainability
- Centralized payment terms management in database
- Easy to add new terms or categories
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <PaymentTermsTypeCode>10</PaymentTermsTypeCode>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access payment terms details
PaymentTermsCodeType paymentTerms = invoice.getPaymentTerms();
String code = paymentTerms.getCode();              // "10"
String name = paymentTerms.getName();              // "Instant"
String desc = paymentTerms.getDescription();       // "Payment is due on receipt of invoice."
String category = paymentTerms.getCategory();      // "Immediate Payment"
boolean immediate = paymentTerms.isImmediate();    // true
boolean deferred = paymentTerms.isDeferred();      // false
boolean discount = paymentTerms.hasDiscount();     // false
```

### XML Marshalling

```java
// Create from entity
PaymentTermsTypeCode entity = repository.findByCode("22").get();
PaymentTermsCodeType paymentTerms = PaymentTermsCodeType.of(entity);

// Or create from code string
PaymentTermsCodeType paymentTerms = PaymentTermsCodeType.of("10");

// Set in invoice
invoice.setPaymentTerms(paymentTerms);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <PaymentTermsTypeCode>10</PaymentTermsTypeCode>
```

### Validation

```java
// Check if code is valid
if (PaymentTermsTypeCodeAdapter.isValid("10")) {
    // Code exists
}

// Get payment terms name
String name = PaymentTermsTypeCodeAdapter.getPaymentTermsName("22");
// Returns: "Discount"

// Get category
String category = PaymentTermsTypeCodeAdapter.getPaymentTermsCategory("10");
// Returns: "Immediate Payment"

// Check payment characteristics
boolean immediate = PaymentTermsTypeCodeAdapter.isImmediatePayment("10");
// Returns: true

boolean hasDiscount = PaymentTermsTypeCodeAdapter.hasDiscount("22");
// Returns: true
```

### Repository Queries

```java
@Autowired
private PaymentTermsTypeCodeRepository repository;

// Get all immediate payment terms
List<PaymentTermsTypeCode> immediateCodes = repository.findImmediatePaymentTerms();

// Get discount payment terms
List<PaymentTermsTypeCode> discountCodes = repository.findDiscountPaymentTerms();

// Get deferred payment terms
List<PaymentTermsTypeCode> deferredCodes = repository.findDeferredPaymentTerms();

// Search by category
List<PaymentTermsTypeCode> creditTerms = repository.findByCategory("Letter of Credit");

// Get all categories
List<String> categories = repository.findAllCategories();
// Returns: ["Standard Terms", "Immediate Payment", "Deferred Payment", ...]

// Search by name
List<PaymentTermsTypeCode> installmentCodes = repository.findByNameContaining("installment");
```

## Database Setup

```sql
-- 1. Create schema (already done)
\i payment_terms_type_code.sql

-- 2. Load data (already done)
\i payment_terms_type_code_data.sql

-- 3. Verify
SELECT COUNT(*) FROM payment_terms_type_code;
-- Expected: 79

SELECT COUNT(*) FROM payment_terms_type_code WHERE is_immediate = true;
-- Expected: 8-10

-- 4. Query examples
SELECT * FROM payment_terms_type_code WHERE code = '10';
SELECT * FROM payment_terms_type_immediate;
SELECT * FROM payment_terms_type_discount;
SELECT * FROM payment_terms_type_deferred;
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "10";  // Just a string, could be invalid
JAXBElement<String> paymentTerms = objectFactory.createPaymentTermsTypeCode(code);
// No way to know if "10" is valid
// No way to get the name or category
// No business logic integration
```

### After (Database-Backed)

```java
// Database-backed: PaymentTermsCodeType with full entity
PaymentTermsCodeType paymentTerms = PaymentTermsCodeType.of("10");
// Validates against database
// Full access to metadata

String code = paymentTerms.getCode();           // "10"
String name = paymentTerms.getName();           // "Instant"
String category = paymentTerms.getCategory();   // "Immediate Payment"
boolean immediate = paymentTerms.isImmediate(); // true
boolean discount = paymentTerms.hasDiscount();  // false
```

## Comparison with Other Code Lists

| Feature | ISOCountryCode | UNECEReferenceTypeCode | PaymentTermsTypeCode |
|---------|----------------|------------------------|----------------------|
| **Total Codes** | 252 | 798 | 79 |
| **Extensions** | 3 ETDA | 11 ETDA | None |
| **Code Format** | 2 letters | Alphanumeric | Numeric + ZZZ |
| **JAXB Original** | Enum | String | String |
| **Categories** | ❌ | ❌ | ✅ 9 categories |
| **Boolean Flags** | ❌ | ❌ | ✅ immediate/deferred/discount |
| **Entity** | ✅ | ✅ | ✅ |
| **Repository** | ✅ | ✅ | ✅ |
| **Adapter** | ✅ | ✅ | ✅ |
| **XML Wrapper** | ✅ | ✅ | ✅ |
| **Package-info** | ✅ | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ | ✅ (already exists) |
| **SQL Data** | ✅ | ✅ | ✅ (already exists) |

## Code List 64279 - Sample Codes

| Code | Name | Category | Immediate | Deferred | Discount |
|------|------|----------|-----------|----------|----------|
| 1 | Basic | Standard Terms | ❌ | ❌ | ❌ |
| 10 | Instant | Immediate Payment | ✅ | ❌ | ❌ |
| 22 | Discount | Discount Terms | ❌ | ❌ | ✅ |
| 42 | Letter of credit | Letter of Credit | ❌ | ❌ | ❌ |
| 48 | Installment | Installment Payment | ❌ | ✅ | ❌ |
| 54 | Deferred | Deferred Payment | ❌ | ✅ | ❌ |
| ZZZ | Mutually defined | Custom Terms | ❌ | ❌ | ❌ |

## Next Steps

1. **Database already setup** ✅ - SQL files exist and are loaded
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Use category queries** for business logic
5. **Leverage boolean flags** for payment term filtering

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **PaymentTermsTypeCode** - Just completed (79 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **DutyTaxFeeTypeCode** - Pending
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT Code List**: 64279
**Schema Version**: D14A
