# Database-Backed Implementation for UNECE Reference Type Code

This document describes the database-backed implementation for UN/CEFACT Reference Type Codes, replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/UNECEReferenceTypeCode.java`

JPA Entity class for storing UN/CEFACT Reference Type Codes in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `etdaExtension`, `active`, timestamps
- Auto-uppercase code normalization
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 798 codes (787 standard + 11 ETDA extensions)

**ETDA Extensions**:
- `80` - ใบเพิ่มหนี้ (Debit note)
- `81` - ใบบลดหนี้ (Credit note)
- `380` - ใบแจ้งหนี้ (Invoice)
- `388` - ใบกำกับภาษี (Tax Invoice)
- `T01` - ใบรับ (Receipt)
- `T02` - ใบแจ้งหนี้/ใบกำกับภาษี (Invoice/Tax Invoice)
- `T03` - ใบเสร็จรับเงิน/ใบกำกับภาษี (Receipt/Tax Invoice)
- `T04` - ใบส่งของ/ใบกำกับภาษี (Delivery order/Tax Invoice)
- `T05` - ใบกำกับภาษีอย่างย่อ (Abbreviated Tax Invoice)
- `T06` - ใบเสร็จรับเงิน/ใบกำกับภาษีอย่างย่อ (Receipt/Abbreviated Tax Invoice)
- `T07` - ใบแจ้งยกเลิก (Cancellation note)

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/UNECEReferenceTypeCodeRepository.java`

Spring Data JPA repository for database operations.

**Query Methods**:
- `findByCodeAndActive(code)` - Find by code (case-insensitive)
- `findByActiveTrue()` - Get all active codes
- `findEtdaExtensions()` - Get Thai ETDA extensions only
- `findInvoiceRelatedCodes()` - Get invoice-related codes
- `findFinancialRelatedCodes()` - Get financial/payment codes
- `findByNameContaining(name)` - Search by name
- `existsByCodeAndActive(code)` - Check if code exists

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/UNECEReferenceTypeCodeAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods:
  - `isValid(code)` - Validate code exists
  - `getReferenceTypeName(code)` - Get name from code
  - `isEtdaExtension(code)` - Check if ETDA extension

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/referencecode/ReferenceCodeType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A`
- Helper methods: `getCode()`, `getName()`, `getDescription()`, `isEtdaExtension()`, `isActive()`
- Factory methods: `of(String)`, `of(UNECEReferenceTypeCode)`

**File**: `src/main/java/com/wpanther/etax/xml/referencecode/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A`
- Prefix: `clm61153`
- Element form: QUALIFIED

### 5. Database Schema

**File**: `unece_reference_type_code.sql` (121 lines)

PostgreSQL table schema with:
- Table definition with 7 columns
- 4 indexes for performance
- Auto-update timestamp trigger
- 5 views (active, standard, ETDA extensions, invoice, financial)
- 1 lookup function

**File**: `unece_reference_type_code_data.sql` (810 lines, 100KB)

Data insert statements for all 798 codes.

## Architecture Pattern

This implementation follows the **exact same pattern** as `ISOCountryCode`:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <ReferenceTypeCode>AAA</ReferenceTypeCode>                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│           ReferenceCodeType (XML Wrapper)                   │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      UNECEReferenceTypeCodeAdapter (XmlAdapter)            │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│    UNECEReferenceTypeCodeRepository (Spring Data JPA)      │
│  - findByCodeAndActive()                                    │
│  - findEtdaExtensions()                                     │
│  - findInvoiceRelatedCodes()                                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│       UNECEReferenceTypeCode (JPA Entity)                  │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: unece_reference_type_code (798 records)            │
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
- Access to code names and descriptions
- Identify ETDA extensions programmatically
- Search by name, filter by type

### ✅ Runtime Configuration
- Update codes without recompiling
- Activate/deactivate codes at runtime
- Add new codes dynamically

### ✅ Query Capabilities
- Search invoice-related codes
- Filter financial/payment codes
- Find ETDA Thai extensions
- Case-insensitive search

### ✅ Maintainability
- Centralized code management in database
- Easy to add new codes or categories
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <ReferenceTypeCode>AAA</ReferenceTypeCode>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access reference code details
ReferenceCodeType refCode = invoice.getReferenceCode();
String code = refCode.getCode();           // "AAA"
String name = refCode.getName();           // "Order acknowledgement document identifier"
String desc = refCode.getDescription();    // "[1018] Reference number identifying..."
boolean isETDA = refCode.isEtdaExtension(); // false
```

### XML Marshalling

```java
// Create from entity
UNECEReferenceTypeCode entity = repository.findByCodeAndActive("T01").get();
ReferenceCodeType refCode = ReferenceCodeType.of(entity);

// Or create from code string
ReferenceCodeType refCode = ReferenceCodeType.of("380");

// Set in invoice
invoice.setReferenceCode(refCode);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <ReferenceTypeCode>380</ReferenceTypeCode>
```

### Validation

```java
// Check if code is valid
if (UNECEReferenceTypeCodeAdapter.isValid("AAA")) {
    // Code exists and is active
}

// Get code name
String name = UNECEReferenceTypeCodeAdapter.getReferenceTypeName("T01");
// Returns: "ใบรับ"

// Check if ETDA extension
boolean isETDA = UNECEReferenceTypeCodeAdapter.isEtdaExtension("380");
// Returns: true
```

### Repository Queries

```java
@Autowired
private UNECEReferenceTypeCodeRepository repository;

// Get all active codes
List<UNECEReferenceTypeCode> allCodes = repository.findByActiveTrue();

// Get Thai ETDA extensions
List<UNECEReferenceTypeCode> thaiCodes = repository.findEtdaExtensions();

// Get invoice-related codes
List<UNECEReferenceTypeCode> invoiceCodes = repository.findInvoiceRelatedCodes();

// Search by name
List<UNECEReferenceTypeCode> creditCodes = repository.findByNameContaining("credit");
```

## Database Setup

```sql
-- 1. Create schema
\i unece_reference_type_code.sql

-- 2. Load data
\i unece_reference_type_code_data.sql

-- 3. Verify
SELECT COUNT(*) FROM unece_reference_type_code;
-- Expected: 798

SELECT COUNT(*) FROM unece_reference_type_code WHERE is_etda_extension = true;
-- Expected: 11

-- 4. Query examples
SELECT * FROM unece_reference_type_code WHERE code = 'AAA';
SELECT * FROM unece_reference_type_code_etda_extensions;
SELECT * FROM unece_reference_type_code_invoice;
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "AAA";  // Just a string, could be invalid
JAXBElement<String> refCode = objectFactory.createReferenceTypeCode(code);
// No way to know if "AAA" is valid
// No way to get the name or description
```

### After (Database-Backed)

```java
// Database-backed: ReferenceCodeType with full entity
ReferenceCodeType refCode = ReferenceCodeType.of("AAA");
// Validates against database
// Full access to metadata

String code = refCode.getCode();          // "AAA"
String name = refCode.getName();          // "Order acknowledgement document identifier"
String desc = refCode.getDescription();   // Full description
boolean valid = refCode.isActive();       // true
```

## Comparison with ISO Country Code

| Feature | ISOCountryCode | UNECEReferenceTypeCode |
|---------|----------------|------------------------|
| **Total Codes** | 252 | 798 |
| **ETDA Extensions** | 3 (AN, KS, UN) | 11 (80, 81, 380, 388, T01-T07) |
| **Code Format** | 2 letters | Alphanumeric (1-10 chars) |
| **JAXB Original** | Enum | String |
| **Entity** | ✅ | ✅ |
| **Repository** | ✅ | ✅ |
| **Adapter** | ✅ | ✅ |
| **XML Wrapper** | ✅ | ✅ |
| **Package-info** | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ |
| **SQL Data** | ✅ | ✅ |

## Next Steps

1. **Run SQL scripts** to create table and load data
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Verify ETDA extensions** are properly identified
5. **Consider migrating other code lists** using the same pattern

## Related Code Lists

Other code lists that could benefit from the same pattern:

- ✅ **ISOCountryCode** - Already migrated (252 codes)
- 🔄 **UNECEReferenceTypeCode** - Just completed (798 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **DutyTaxFeeTypeCode** - Pending
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
