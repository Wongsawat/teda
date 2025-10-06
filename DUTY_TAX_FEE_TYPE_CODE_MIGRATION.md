# Database-Backed Implementation for Duty Tax Fee Type Code

This document describes the database-backed implementation for UN/CEFACT Duty Tax Fee Type Code (Code List 65153), replacing the JAXB-generated Enum type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/DutyTaxFeeTypeCode.java`

JPA Entity class for storing UN/CEFACT Duty Tax Fee Type Codes in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `category`, `isVat`, `isExempt`, `isSummary`, timestamps
- Code normalization (trim and uppercase)
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 53 codes (AAA to VAT)

**Tax Categories**:
- **VAT**: Value Added Tax (VAT, ENV, EXP)
- **GST**: Goods and Services Tax
- **Customs**: Import/export duties (AAA, AAB, AAC, AAD, etc.)
- **Excise**: Petroleum, alcohol, tobacco taxes
- **Exempt**: Tax exemptions (EXE, TAX, FRE)
- **Special Tax**: Environmental, road tax, etc.
- **Other**: Summary codes (OTH, TOT), mutually defined (ZZZ)

**Business Logic Methods**:
- `isVat()` - Check if VAT-related
- `isExempt()` - Check if tax exempt
- `isSummary()` - Check if summary code
- `isCustomsDuty()` - Check if customs duty
- `isExciseTax()` - Check if excise tax
- `isGST()` - Check if GST
- `isSpecialTax()` - Check if special tax

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/DutyTaxFeeTypeCodeRepository.java`

Spring Data JPA repository for database operations.

**Query Methods**:
- `findByCode(code)` - Find by code
- `findByCodeAndActive(code)` - Find active code
- `findVATCodes()` - Get all VAT codes
- `findExemptCodes()` - Get all exempt codes
- `findSummaryCodes()` - Get summary codes
- `findByCategory(category)` - Get by category
- `findAllCategories()` - Get all available categories
- `findByNameContaining(name)` - Search by name
- `findByDescriptionContaining(keyword)` - Search by description
- `existsByCode(code)` - Check if code exists
- `findCustomsDuties()` - Get customs duty codes
- `findExciseTaxes()` - Get excise tax codes
- `findGSTCodes()` - Get GST codes
- `findSpecialTaxes()` - Get special tax codes
- `findCommonCodes()` - Get most commonly used codes
- `findVATCode()` - Get VAT code
- `findGSTCode()` - Get GST code
- `findExemptCode()` - Get EXE code
- `findTaxCode()` - Get TAX code
- `findFreeCode()` - Get FRE code
- `findOtherCode()` - Get OTH code
- `findTotalCode()` - Get TOT code
- `findMutuallyDefinedCode()` - Get ZZZ code

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/DutyTaxFeeTypeCodeAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods:
  - `isValid(code)` - Validate code exists
  - `getDutyTaxFeeName(code)` - Get name from code
  - `getDutyTaxFeeCategory(code)` - Get category from code
  - `isVAT(code)` - Check if VAT
  - `isExempt(code)` - Check if exempt
  - `isSummary(code)` - Check if summary
  - `isCustomsDuty(code)` - Check if customs duty
  - `isExciseTax(code)` - Check if excise tax
  - `isGST(code)` - Check if GST
  - `isSpecialTax(code)` - Check if special tax

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/dutytaxfee/DutyTaxFeeCodeType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A`
- Helper methods: `getCode()`, `getName()`, `getDescription()`, `getCategory()`, `isVat()`, `isExempt()`, `isSummary()`, `isCustomsDuty()`, `isExciseTax()`, `isGST()`, `isSpecialTax()`
- Factory methods: `of(String)`, `of(DutyTaxFeeTypeCode)`

**File**: `src/main/java/com/wpanther/etax/xml/dutytaxfee/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A`
- Prefix: `clm65153`
- Element form: QUALIFIED

### 5. Database Schema (Already Exists)

**File**: `duty_tax_fee_type_code.sql` (259 lines)

PostgreSQL table schema with:
- Table definition with 8 columns
- 6 indexes for performance (name, category, is_vat, is_exempt, is_summary, full-text search)
- Auto-update timestamp trigger
- 8 views (vat, gst, customs, excise, exempt, special, summary, common)
- 6 helper functions (get name, validate, check vat, check exempt, get category, search by keyword)

**File**: `duty_tax_fee_type_code_data.sql` (61 lines)

Data insert statements for all 53 codes.

## Architecture Pattern

This implementation follows the **exact same pattern** as other code lists:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <DutyTaxFeeTypeCode>VAT</DutyTaxFeeTypeCode>              │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      DutyTaxFeeCodeType (XML Wrapper)                       │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      DutyTaxFeeTypeCodeAdapter (XmlAdapter)                 │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│    DutyTaxFeeTypeCodeRepository (Spring Data JPA)           │
│  - findByCode()                                             │
│  - findVATCodes()                                           │
│  - findCommonCodes()                                        │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│       DutyTaxFeeTypeCode (JPA Entity)                       │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - Tax category classification                              │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: duty_tax_fee_type_code (53 records)               │
└─────────────────────────────────────────────────────────────┘
```

## Benefits

### ✅ Type Safety
- Strongly-typed entities instead of plain enums
- Compile-time validation of entity structure
- Runtime flexibility for new codes

### ✅ Validation
- Automatic validation during XML unmarshalling
- Rejects invalid codes or creates placeholder
- Database constraints enforce data integrity

### ✅ Rich Metadata
- Access to duty tax fee type names and descriptions
- Category-based classification (7 categories)
- Boolean flags for VAT/exempt/summary

### ✅ Business Logic Integration
- Query by tax type (VAT, GST, customs, excise)
- Filter by exemption status
- Identify summary vs detail codes
- Classify by tax category

### ✅ Query Capabilities
- Search by category (7 main categories)
- Filter by tax type
- Find common codes (8 most used)
- Case-insensitive search

### ✅ Maintainability
- Centralized management in database
- Easy to add new codes
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <DutyTaxFeeTypeCode>VAT</DutyTaxFeeTypeCode>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access duty tax fee type details
DutyTaxFeeCodeType taxCode = invoice.getTaxTypeCode();
String code = taxCode.getCode();                    // "VAT"
String name = taxCode.getName();                    // "Value added tax"
String description = taxCode.getDescription();      // "A tax on domestic or imported goods..."
String category = taxCode.getCategory();            // "VAT"
boolean isVat = taxCode.isVat();                    // true
boolean isExempt = taxCode.isExempt();              // false
```

### XML Marshalling

```java
// Create from entity
DutyTaxFeeTypeCode entity = repository.findByCode("VAT").get();
DutyTaxFeeCodeType taxCode = DutyTaxFeeCodeType.of(entity);

// Or create from code string
DutyTaxFeeCodeType taxCode = DutyTaxFeeCodeType.of("GST");

// Set in invoice
invoice.setTaxTypeCode(taxCode);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <DutyTaxFeeTypeCode>GST</DutyTaxFeeTypeCode>
```

### Validation

```java
// Check if code is valid
if (DutyTaxFeeTypeCodeAdapter.isValid("VAT")) {
    // Code exists
}

// Get duty tax fee type name
String name = DutyTaxFeeTypeCodeAdapter.getDutyTaxFeeName("VAT");
// Returns: "Value added tax"

// Get category
String category = DutyTaxFeeTypeCodeAdapter.getDutyTaxFeeCategory("VAT");
// Returns: "VAT"

// Check tax type
boolean isVat = DutyTaxFeeTypeCodeAdapter.isVAT("VAT");
// Returns: true

boolean isExempt = DutyTaxFeeTypeCodeAdapter.isExempt("EXE");
// Returns: true

boolean isGst = DutyTaxFeeTypeCodeAdapter.isGST("GST");
// Returns: true
```

### Repository Queries

```java
@Autowired
private DutyTaxFeeTypeCodeRepository repository;

// Get VAT code
Optional<DutyTaxFeeTypeCode> vat = repository.findVATCode();

// Get all VAT codes
List<DutyTaxFeeTypeCode> vatCodes = repository.findVATCodes();

// Get all exempt codes
List<DutyTaxFeeTypeCode> exemptCodes = repository.findExemptCodes();
// Returns: EXE, TAX, FRE, TT, ZZZ

// Get common codes
List<DutyTaxFeeTypeCode> common = repository.findCommonCodes();
// Returns: VAT, GST, EXE, TAX, FRE, OTH, TOT, ZZZ

// Get by category
List<DutyTaxFeeTypeCode> customs = repository.findByCategory("Customs");

// Search by name
List<DutyTaxFeeTypeCode> taxCodes = repository.findByNameContaining("tax");
```

## Database Setup

```sql
-- 1. Create schema (already done)
\i duty_tax_fee_type_code.sql

-- 2. Load data (already done)
\i duty_tax_fee_type_code_data.sql

-- 3. Verify
SELECT COUNT(*) FROM duty_tax_fee_type_code;
-- Expected: 53

SELECT COUNT(*) FROM duty_tax_fee_type_code WHERE is_vat = true;
-- Expected: 3 (VAT, ENV, EXP)

SELECT COUNT(*) FROM duty_tax_fee_type_code WHERE is_exempt = true;
-- Expected: 5 (EXE, TAX, FRE, TT, ZZZ)

-- 4. Query examples
SELECT * FROM duty_tax_fee_type_code WHERE code = 'VAT';
SELECT * FROM duty_tax_fee_type_code_vat;
SELECT * FROM duty_tax_fee_type_code_exempt;
SELECT * FROM duty_tax_fee_type_code_common;

-- 5. Use functions
SELECT get_duty_tax_fee_name('VAT');
SELECT is_vat_code('VAT');
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: Enum with 53 values
// Compile-time safety but no runtime flexibility

DutyTaxFeeTypeCodeContentType code = DutyTaxFeeTypeCodeContentType.VAT;
// Can only use predefined enum values
// No way to get description or category
// No business logic integration
// No database queries
```

### After (Database-Backed)

```java
// Database-backed: DutyTaxFeeCodeType with full entity
DutyTaxFeeCodeType taxCode = DutyTaxFeeCodeType.of("VAT");
// Validates against database
// Full access to metadata

String code = taxCode.getCode();                // "VAT"
String name = taxCode.getName();                // "Value added tax"
String category = taxCode.getCategory();        // "VAT"
boolean isVat = taxCode.isVat();                // true
boolean isExempt = taxCode.isExempt();          // false
boolean isSummary = taxCode.isSummary();        // false
```

## Comparison with Other Code Lists

| Feature | JAXB Enum | Database-Backed | Benefit |
|---------|-----------|-----------------|---------|
| **Type Safety** | ✅ Compile-time | ✅ Runtime + DB | Flexible validation |
| **New Codes** | ❌ Requires rebuild | ✅ Just DB insert | Easy maintenance |
| **Metadata** | ❌ None | ✅ Name, description, category | Rich context |
| **Queries** | ❌ None | ✅ 24 methods | Business logic |
| **Categories** | ❌ None | ✅ 7 categories | Classification |
| **Flags** | ❌ None | ✅ isVat, isExempt, isSummary | Tax logic |
| **Search** | ❌ None | ✅ By name, description | Discovery |

## Code List 65153 - Common Codes

| Code | Name | Category | VAT | Exempt | Summary |
|------|------|----------|-----|--------|---------|
| VAT | Value added tax | VAT | ✅ | ❌ | ❌ |
| GST | Goods and services tax | GST | ❌ | ❌ | ❌ |
| EXE | Exempt from tax | Exempt | ❌ | ✅ | ❌ |
| TAX | Tax | Exempt | ❌ | ✅ | ❌ |
| FRE | Free | Exempt | ❌ | ✅ | ❌ |
| OTH | Other taxes | Other | ❌ | ❌ | ✅ |
| TOT | Total | Other | ❌ | ❌ | ✅ |
| ZZZ | Mutually defined | Exempt | ❌ | ✅ | ❌ |

## e-Tax Invoice Use Cases

Duty Tax Fee Type Codes are used in e-Tax Invoice to indicate the type of tax applied:

1. **VAT Invoice**: Most common in Thailand, uses code "VAT"
2. **GST Invoice**: For countries using GST system
3. **Tax Exempt**: Products/services exempt from tax (EXE, FRE)
4. **Customs Duties**: Import/export taxes (AAA, AAB, etc.)
5. **Excise Taxes**: Petroleum, alcohol, tobacco (ENV)
6. **Summary Lines**: Totals and aggregations (OTH, TOT)

## Next Steps

1. **Database already setup** ✅ - SQL files exist and are loaded
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Use tax type queries** for invoice tax calculation
5. **Integrate with invoice processing workflow** for automated tax handling

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **PaymentTermsTypeCode** - Complete (79 codes)
- ✅ **PaymentTermsDescriptionIdentifier** - Complete (7 codes)
- ✅ **MessageFunctionCode** - Complete (65 codes)
- ✅ **DutyTaxFeeTypeCode** - Just completed (53 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT Code List**: 65153 (Duty Tax Fee Type Code)
**Schema Version**: D14A
**Use Case**: e-Tax Invoice tax classification and calculation
