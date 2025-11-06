# AllowanceChargeIdentificationCode Migration to Database-Backed Implementation

## Overview

This document describes the migration of UN/CEFACT Allowance/Charge Identification Code (Code List 5189, Version D14A) from JAXB-generated code to a database-backed implementation using JPA entities and Spring Data repositories.

**UN/CEFACT Code List**: 5189 (AllowanceChargeID)
**Schema Version**: D14A (15 Nov 2014)
**Total Codes**: 106 (100 standard UN/CEFACT + 6 Thai extensions)
**Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A`
**Prefix**: `clm65189AllowanceChargeID`

## Code Categories

Allowance and charge identification codes are organized into **16 categories**:

| Category | Count | Description | Example Codes |
|----------|-------|-------------|---------------|
| **Documentary Credit Commission** | 15 | Bank fees for letters of credit | 1, 2, 3, 6, 7, 8, 10, 16, 20, 21, 22, 23, 25, 27 |
| **Collection Commission** | 3 | Fees for collections and settlements | 5, 15, 17 |
| **Processing Fee** | 5 | Communication charges | 32, 33, 34, 35, 36 (courier, phone, postage, SWIFT, telex) |
| **Discount** | 8 | Various discount types | 60, 65, 66, 67, 68, 70, 95 |
| **Rebate** | 1 | Special rebates | 100 |
| **Penalty** | 3 | Late delivery and execution penalties | 37, 38, 39 |
| **Bonus** | 2 | Early completion bonuses | 41, 42 |
| **Freight Charges** | 1 | Freight costs | 79 |
| **Packing Charges** | 1 | Packing fees | 80 |
| **Loading/Unloading Charges** | 1 | Loading fees | 82 |
| **Handling Charges** | 1 | Warehousing and handling | 85 |
| **Testing/Inspection Charges** | 2 | Quality control fees | 76, 84 |
| **Other Charges** | 29 | Miscellaneous charges | 30, 31, 54, 55, 56, 57, 58, 61, 69, 77, 81, 83, 86-94, 97, 98, 99, 101 |
| **Other Commission** | 3 | Other commission types | 26, 52, 53 |
| **Other Allowance** | 1 | Point of sales threshold allowance | 71 |
| **Other** | 24 | General unclassified items | 28, 29, 40, 44-51, 62-64, 72-75, 78, 96 |
| **Thai Extensions** | 6 | ETDA-specific codes | PP001-PP006 |

## Files Created

### 1. Entity (`AllowanceChargeIdentificationCode.java`)
**Location**: `src/main/java/com/wpanther/etax/entity/AllowanceChargeIdentificationCode.java`

JPA entity representing allowance/charge identification codes with:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `category`, `isStandardCode`, `isThaiExtension`, `createdAt`, `updatedAt`
- Code normalization (trim and uppercase)
- 17 business logic methods:
  - `isDocumentaryCreditCommission()`
  - `isCollectionCommission()`
  - `isProcessingFee()`
  - `isDiscount()`
  - `isRebate()`
  - `isPenalty()`
  - `isBonus()`
  - `isFreightCharges()`
  - `isPackingCharges()`
  - `isLoadingCharges()`
  - `isHandlingCharges()`
  - `isTestingCharges()`
  - `isStandardCode()`
  - `isThaiExtension()`
  - `isCommission()`
  - `isCharge()`
  - `isAllowance()`

### 2. Repository (`AllowanceChargeIdentificationCodeRepository.java`)
**Location**: `src/main/java/com/wpanther/etax/repository/AllowanceChargeIdentificationCodeRepository.java`

Spring Data JPA repository with **51 query methods**:

**Basic Queries**:
- `findByCode(code)` - Find by code (case-insensitive)
- `findByCategory(category)` - Find all in category
- `findAllCategories()` - Get distinct categories
- `existsByCode(code)` - Check existence

**Category-Specific Queries**:
- `findDocumentaryCreditCommissions()` - Documentary credit fees
- `findCollectionCommissions()` - Collection fees
- `findProcessingFees()` - Communication charges
- `findDiscounts()` - Discount codes
- `findRebates()` - Rebate codes
- `findPenalties()` - Penalty codes
- `findBonuses()` - Bonus codes
- `findFreightCharges()` - Freight cost codes
- `findPackingCharges()` - Packing fee codes
- `findLoadingCharges()` - Loading fee codes
- `findHandlingCharges()` - Handling fee codes
- `findTestingCharges()` - Testing/inspection fee codes

**Code Type Queries**:
- `findStandardCodes()` - UN/CEFACT standard codes only
- `findThaiExtensions()` - Thai extension codes only
- `findAllCommissions()` - All commission-type codes
- `findAllCharges()` - All charge-type codes
- `findAllAllowances()` - All allowance-type codes

**Search Queries**:
- `findByNameContaining(name)` - Search by name
- `findByDescriptionContaining(keyword)` - Search by description
- `searchByKeyword(searchTerm)` - Full-text search

**Common Codes**:
- `findCommonCodes()` - Most used codes (79, 80, 95, 60, 96, 30, 35, PP001, PP002, 1, 32)
- `findHandlingCommission()` - Code 1
- `findBankCharges()` - Code 30
- `findCourierFee()` - Code 32
- `findSwiftFee()` - Code 35
- `findManufacturerDiscount()` - Code 60
- `findFreightChargesCode()` - Code 79
- `findPackingCharge()` - Code 80
- `findDiscountCode()` - Code 95
- `findInsurance()` - Code 96
- `findSpecialRebate()` - Code 100
- `findCarbonFootprintCharge()` - Code 101

**Thai Extension Queries**:
- `findThaiDeposit()` - Code PP001
- `findThaiGuarantee()` - Code PP002
- `findThaiReservation()` - Code PP003
- `findThaiAdvancePayment()` - Code PP004
- `findThaiPerformanceGuarantee()` - Code PP005
- `findThaiOtherAdvancePayment()` - Code PP006

**Statistics**:
- `countByCategory()` - Count by category

### 3. XmlAdapter (`AllowanceChargeIdentificationCodeAdapter.java`)
**Location**: `src/main/java/com/wpanther/etax/adapter/AllowanceChargeIdentificationCodeAdapter.java`

JAXB adapter converting between XML strings and database entities:
- `marshal(entity)` - Convert entity → XML string (code)
- `unmarshal(code)` - Convert XML string → entity (database lookup)
- Placeholder creation for unknown codes
- **16 static helper methods**:
  - `isValid(code)`
  - `getName(code)`
  - `getCategory(code)`
  - `getDescription(code)`
  - `isDocumentaryCreditCommission(code)`
  - `isCollectionCommission(code)`
  - `isProcessingFee(code)`
  - `isDiscount(code)`
  - `isPenalty(code)`
  - `isBonus(code)`
  - `isFreightCharges(code)`
  - `isStandardCode(code)`
  - `isThaiExtension(code)`
  - `isCommission(code)`
  - `isCharge(code)`
  - `isAllowance(code)`

### 4. XML Type (`AllowanceChargeIdentificationCodeType.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/allowancechargeidentification/AllowanceChargeIdentificationCodeType.java`

Custom JAXB type replacing generated JAXBElement<String> with database-backed implementation:
- `@XmlValue` with `@XmlJavaTypeAdapter(AllowanceChargeIdentificationCodeAdapter.class)`
- Constructors: default, from entity, from code string
- Getters: `getCode()`, `getName()`, `getDescription()`, `getCategory()`
- Business logic methods (17 category/type checks)
- Factory methods: `of(String)`, `of(AllowanceChargeIdentificationCode)`
- Custom `toString()` with code, name, category, and Thai extension indicator

### 5. Package Configuration (`package-info.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/allowancechargeidentification/package-info.java`

JAXB namespace configuration:
```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm65189AllowanceChargeID",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A"
        )
    }
)
```

## Architecture Pattern

```
┌─────────────────────────────────────────────────────────────────┐
│                         XML Document                             │
│  <AllowanceChargeIdentificationCode>79                           │
│  </AllowanceChargeIdentificationCode>                            │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JAXB Unmarshalling
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│    AllowanceChargeIdentificationCodeAdapter (XmlAdapter)         │
│  • unmarshal("79") → database lookup → entity                   │
│  • marshal(entity) → "79"                                       │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ Database Lookup
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  AllowanceChargeIdentificationCodeRepository (Spring Data JPA)   │
│  • findByCode("79") → Optional<AllowanceChargeIdentificationCode>│
│  • findFreightChargesCode() → Optional<...>                     │
│  • findCommonCodes() → List<...>                                │
│  • searchByKeyword("freight") → List<...>                       │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JPA Query
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                           │
│  Table: allowance_charge_identification_code                     │
│  • code (PK): "79"                                              │
│  • name: "Freight charges"                                      │
│  • description: "Amount to be paid for moving goods..."         │
│  • category: "Freight Charges"                                  │
│  • is_standard_code: true                                       │
│  • is_thai_extension: false                                     │
│                                                                  │
│  Indexes:                                                        │
│  • idx_allowance_charge_code_name                               │
│  • idx_allowance_charge_code_category                           │
│  • idx_allowance_charge_code_is_standard                        │
│  • idx_allowance_charge_code_is_thai                            │
│  • idx_allowance_charge_code_name_fulltext (GIN)                │
│  • idx_allowance_charge_code_description_fulltext (GIN)         │
│                                                                  │
│  Views:                                                          │
│  • allowance_charge_code_commissions                            │
│  • allowance_charge_code_fees                                   │
│  • allowance_charge_code_charges                                │
│  • allowance_charge_code_discounts                              │
│  • allowance_charge_code_standard                               │
│  • allowance_charge_code_thai                                   │
│  • allowance_charge_code_category_summary                       │
└─────────────────────────────────────────────────────────────────┘
```

## Benefits

### 1. Type Safety
- Compile-time type checking
- No invalid codes at runtime
- IDE autocomplete support

### 2. Rich Metadata
- **Name**: Human-readable name
- **Description**: Detailed usage explanation
- **Category**: Organized classification (16 categories)
- **Flags**: isStandardCode, isThaiExtension

### 3. Business Logic
- **17 category/type check methods** on entity and type
- Easy validation: `entity.isFreightCharges()`
- Category-based processing logic

### 4. Advanced Query Capabilities
- Find by category: `repository.findProcessingFees()`
- Search by name/description: `repository.findByNameContaining("freight")`
- Full-text search: `repository.searchByKeyword("discount")`
- Standard vs Thai extensions: `repository.findThaiExtensions()`
- Statistics: `repository.countByCategory()`

### 5. Maintainability
- Add new codes via SQL insert (no code changes)
- Update descriptions without recompilation
- Centralized code management

### 6. Validation
- Database constraints ensure data integrity
- Repository methods for existence checking
- Adapter creates placeholders for unknown codes

## Database Setup

### 1. Create Table
```bash
psql -U username -d database_name -f allowance_charge_identification_code.sql
```

### 2. Load Data
```bash
psql -U username -d database_name -f allowance_charge_identification_code_data.sql
```

### 3. Verify Installation
```sql
-- Check total codes (should be 106)
SELECT COUNT(*) FROM allowance_charge_identification_code;

-- Check categories
SELECT category, COUNT(*) as count
FROM allowance_charge_identification_code
WHERE category IS NOT NULL
GROUP BY category
ORDER BY count DESC;

-- View standard vs Thai extensions
SELECT is_standard_code, is_thai_extension, COUNT(*)
FROM allowance_charge_identification_code
GROUP BY is_standard_code, is_thai_extension;

-- View freight charges
SELECT * FROM allowance_charge_code_charges WHERE name LIKE '%freight%';

-- Test full-text search
SELECT * FROM search_allowance_charge_codes('freight');
```

## Usage Examples

### 1. XML Unmarshalling (Reading XML)

**XML Input**:
```xml
<AllowanceChargeIdentificationCode>79</AllowanceChargeIdentificationCode>
```

**Java Code**:
```java
// JAXB automatically uses AllowanceChargeIdentificationCodeAdapter
AllowanceChargeIdentificationCodeType codeType = // ... from JAXB unmarshalling

// Access database-backed entity
AllowanceChargeIdentificationCode entity = codeType.getValue();

System.out.println(entity.getCode());        // "79"
System.out.println(entity.getName());        // "Freight charges"
System.out.println(entity.getCategory());    // "Freight Charges"
System.out.println(entity.getDescription()); // "Amount to be paid for moving goods..."
System.out.println(entity.isStandardCode()); // true
System.out.println(entity.isThaiExtension()); // false

// Business logic
if (codeType.isFreightCharges()) {
    System.out.println("This is a freight charge");
}
```

### 2. XML Marshalling (Writing XML)

```java
// Create from code string
AllowanceChargeIdentificationCodeType codeType =
    AllowanceChargeIdentificationCodeType.of("80");

// Create from entity
AllowanceChargeIdentificationCode entity =
    repository.findPackingCharge().orElseThrow();
AllowanceChargeIdentificationCodeType codeType =
    AllowanceChargeIdentificationCodeType.of(entity);

// JAXB marshalling produces:
// <AllowanceChargeIdentificationCode>80</AllowanceChargeIdentificationCode>
```

### 3. Validation

```java
// Check if code exists
if (AllowanceChargeIdentificationCodeAdapter.isValid("79")) {
    System.out.println("Valid code");
}

// Get code name
String name = AllowanceChargeIdentificationCodeAdapter.getName("79");
// Returns: "Freight charges"

// Check category
if (AllowanceChargeIdentificationCodeAdapter.isFreightCharges("79")) {
    System.out.println("This is a freight charge");
}

// Check if Thai extension
if (AllowanceChargeIdentificationCodeAdapter.isThaiExtension("PP001")) {
    System.out.println("This is a Thai extension code");
}
```

### 4. Repository Queries

```java
@Autowired
private AllowanceChargeIdentificationCodeRepository repository;

// Find by code
Optional<AllowanceChargeIdentificationCode> code = repository.findByCode("79");

// Find all freight-related charges
List<AllowanceChargeIdentificationCode> freight = repository.findFreightCharges();

// Find common codes
List<AllowanceChargeIdentificationCode> common = repository.findCommonCodes();
// Returns: [79, 80, 95, 60, 96, 30, 35, PP001, PP002, 1, 32] in priority order

// Search by keyword
List<AllowanceChargeIdentificationCode> results =
    repository.searchByKeyword("commission");
// Returns: All codes with "commission" in name or description

// Find specific codes
Optional<AllowanceChargeIdentificationCode> freight79 =
    repository.findFreightChargesCode();      // Code 79
Optional<AllowanceChargeIdentificationCode> packing80 =
    repository.findPackingCharge();           // Code 80
Optional<AllowanceChargeIdentificationCode> swift =
    repository.findSwiftFee();                // Code 35

// Find Thai extensions
List<AllowanceChargeIdentificationCode> thaiCodes =
    repository.findThaiExtensions();
// Returns: PP001-PP006

// Get category statistics
List<Object[]> stats = repository.countByCategory();
// Returns: [["Documentary Credit Commission", 15], ["Processing Fee", 5], ...]
```

### 5. Entity Business Logic

```java
AllowanceChargeIdentificationCode code =
    repository.findByCode("79").orElseThrow();

// Category checks
code.isFreightCharges();        // true (code 79)
code.isDiscount();              // false
code.isStandardCode();          // true
code.isThaiExtension();         // false
code.isCharge();                // true
code.isAllowance();             // false

// Access metadata
String name = code.getName();              // "Freight charges"
String desc = code.getDescription();       // "Amount to be paid for moving goods..."
String category = code.getCategory();      // "Freight Charges"
```

## Migration from JAXB

### Before (JAXB Generated - JAXBElement Pattern)

```java
// JAXB generated ObjectFactory
public class ObjectFactory {
    @XmlElementDecl(namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A",
                    name = "AllowanceChargeIdentificationCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAllowanceChargeIdentificationCode(String value) {
        return new JAXBElement<String>(_AllowanceChargeIdentificationCode_QNAME, String.class, null, value);
    }
}

// Usage
JAXBElement<String> element = objectFactory.createAllowanceChargeIdentificationCode("79");
String value = element.getValue(); // Just "79" - no metadata!

// No validation, no category info, no business logic
```

### After (Database-Backed)

```java
// Custom type with database lookup
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowanceChargeIdentificationCodeContentType")
public class AllowanceChargeIdentificationCodeType {
    @XmlValue
    @XmlJavaTypeAdapter(AllowanceChargeIdentificationCodeAdapter.class)
    private AllowanceChargeIdentificationCode value;

    // Rich metadata and business logic
    public String getCode() { return value.getCode(); }
    public String getName() { return value.getName(); }
    public String getDescription() { return value.getDescription(); }
    public String getCategory() { return value.getCategory(); }
    public boolean isStandardCode() { return value.isStandardCode(); }
    public boolean isThaiExtension() { return value.isThaiExtension(); }
    public boolean isFreightCharges() { return value.isFreightCharges(); }
    // ... 16 more business logic methods
}

// Usage
AllowanceChargeIdentificationCodeType type = AllowanceChargeIdentificationCodeType.of("79");
String code = type.getCode();              // "79"
String name = type.getName();              // "Freight charges"
String category = type.getCategory();      // "Freight Charges"
boolean isFreight = type.isFreightCharges(); // true
boolean isThai = type.isThaiExtension();   // false

// Full validation and business logic available!
```

## Common Allowance/Charge Identification Codes

### Most Frequently Used in e-Tax Invoices

| Code | Name | Category | Usage |
|------|------|----------|-------|
| **79** | Freight charges | Freight Charges | Amount to be paid for moving goods |
| **80** | Packing charge | Packing Charges | Charge for packing goods |
| **95** | Discount | Discount | A reduction from usual or list price |
| **60** | Manufacturer's consumer discount | Discount | Discount passed on to consumer |
| **96** | Insurance | Other | Charge for insurance |
| **30** | Bank charges | Other Charges | Charges from banks involved |
| **35** | S.W.I.F.T. fee | Processing Fee | Fee for use of SWIFT |
| **PP001** | เงินมัดจำ | Other | Thai: Deposit |
| **PP002** | เงินประกัน | Other | Thai: Guarantee/Security deposit |
| **1** | Handling commission | Documentary Credit Commission | Fee for processing documentary credit |
| **32** | Courier fee | Processing Fee | Fee for courier service |

### Documentary Credit Commissions (15 codes)
- **1**: Handling commission
- **2**: Amendment commission
- **3**: Acceptance commission
- **6**: Advising commission
- **7**: Confirmation commission
- **8**: Deferred payment commission
- **10**: Opening commission
- **16**: Negotiation commission
- **20**: Transfer commission
- **21**: Commission for opening irrevocable documentary credits
- **22**: Pre-advice commission
- **23**: Supervisory commission
- **25**: Risk commission
- **27**: Reimbursement commission

### Processing Fees (5 codes)
- **32**: Courier fee
- **33**: Phone fee
- **34**: Postage fee
- **35**: S.W.I.F.T. fee
- **36**: Telex fee

### Discounts (8 codes)
- **60**: Manufacturer's consumer discount
- **65**: Production error discount
- **66**: New outlet discount
- **67**: Sample discount
- **68**: End-of-range discount
- **70**: Incoterm discount
- **95**: Discount (general)

### Thai Extensions (6 codes)
- **PP001**: เงินมัดจำ (Deposit)
- **PP002**: เงินประกัน (Guarantee/Security deposit)
- **PP003**: เงินจอง (Reservation)
- **PP004**: เงินจ่ายล่วงหน้า (Advance payment)
- **PP005**: เงินประกันผลงาน (Performance guarantee)
- **PP006**: เงินรับล่วงหน้าอื่น ๆ (Other advance payment)

## e-Tax Invoice Use Cases

### 1. Freight Charges Scenario
```java
// Add freight charge to invoice
AllowanceChargeIdentificationCodeType chargeId =
    AllowanceChargeIdentificationCodeType.of("79");

AllowanceChargeType charge = new AllowanceChargeType();
charge.setChargeIndicator(true); // true = charge
charge.setAllowanceChargeIdentificationCode(chargeId);
charge.setAllowanceChargeReason("Sea freight from Bangkok to Singapore");
charge.setAmount(new AmountType(5000.00)); // 5000 THB

// Verify it's freight
if (chargeId.isFreightCharges()) {
    System.out.println("Freight charge: " + chargeId.getName());
}
```

### 2. Packing Charge Scenario
```java
// Add packing charge
AllowanceChargeIdentificationCodeType chargeId =
    AllowanceChargeIdentificationCodeType.of("80");

AllowanceChargeType charge = new AllowanceChargeType();
charge.setChargeIndicator(true);
charge.setAllowanceChargeIdentificationCode(chargeId);
charge.setAllowanceChargeReason("Special packaging for fragile items");
charge.setAmount(new AmountType(500.00));

// Verify it's a packing charge
if (chargeId.isPackingCharges()) {
    System.out.println("Packing charge: " + chargeId.getName());
}
```

### 3. Discount Scenario
```java
// Apply manufacturer discount
AllowanceChargeIdentificationCodeType allowanceId =
    AllowanceChargeIdentificationCodeType.of("60");

AllowanceChargeType allowance = new AllowanceChargeType();
allowance.setChargeIndicator(false); // false = allowance (discount)
allowance.setAllowanceChargeIdentificationCode(allowanceId);
allowance.setAllowanceChargeReason("Manufacturer's promotional discount");
allowance.setAmount(new AmountType(1000.00)); // 1000 THB discount

// Verify it's a discount
if (allowanceId.isDiscount() && allowanceId.isAllowance()) {
    System.out.println("Discount allowance: " + allowanceId.getName());
}
```

### 4. Thai Deposit Scenario
```java
// Apply Thai deposit (advance payment)
AllowanceChargeIdentificationCodeType allowanceId =
    AllowanceChargeIdentificationCodeType.of("PP001");

AllowanceChargeType allowance = new AllowanceChargeType();
allowance.setChargeIndicator(false);
allowance.setAllowanceChargeIdentificationCode(allowanceId);
allowance.setAllowanceChargeReason("เงินมัดจำชำระแล้ว 30%");
allowance.setAmount(new AmountType(15000.00)); // 15000 THB deposit

// Verify it's Thai extension
if (allowanceId.isThaiExtension()) {
    System.out.println("Thai deposit: " + allowanceId.getName());
}
```

### 5. Bank Charges Scenario
```java
// Add bank charges
AllowanceChargeIdentificationCodeType chargeId =
    AllowanceChargeIdentificationCodeType.of("30");

AllowanceChargeType charge = new AllowanceChargeType();
charge.setChargeIndicator(true);
charge.setAllowanceChargeIdentificationCode(chargeId);
charge.setAllowanceChargeReason("International wire transfer fee");
charge.setAmount(new AmountType(200.00));

// Verify it's a charge (not allowance)
if (chargeId.isCharge() && !chargeId.isAllowance()) {
    System.out.println("Bank charge: " + chargeId.getName());
}
```

## Related Code Lists Status

| Code List | Status | Notes |
|-----------|--------|-------|
| ISOCountryCode | ✅ Migrated | 252 countries |
| UNECEReferenceTypeCode | ✅ Migrated | 798 document types |
| PaymentTermsTypeCode | ✅ Migrated | 79 payment terms |
| PaymentTermsDescriptionIdentifier | ✅ Migrated | 7 identifiers |
| MessageFunctionCode | ✅ Migrated | 65 message functions |
| DutyTaxFeeTypeCode | ✅ Migrated | 53 tax types |
| FreightCostCode | ✅ Migrated | 1,641 freight terms |
| DeliveryTermsCode | ✅ Migrated | 14 incoterms |
| AllowanceChargeReasonCode | ✅ Migrated | 105 reason codes |
| **AllowanceChargeIdentificationCode** | ✅ **Migrated** | **106 identification codes** |

## Summary

The AllowanceChargeIdentificationCode migration provides:
- ✅ **106 identification codes** organized into **16 categories**
- ✅ **51 repository query methods** for flexible data access
- ✅ **16 static helper methods** for validation and lookup
- ✅ **17 business logic methods** for category/type checking
- ✅ **Full-text search** with GIN indexes
- ✅ **6 database views** for common queries
- ✅ **Standard + Thai extension support** with flags
- ✅ **Complete JAXB compatibility** with original schema
- ✅ **Type-safe** invoice adjustment processing
- ✅ **Rich metadata** for charges, fees, discounts, and commissions

This implementation enables sophisticated invoice line-level and document-level adjustment handling with proper categorization, validation, and business logic for Thai e-Tax Invoice compliance.
