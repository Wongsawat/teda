# AllowanceChargeReasonCode Migration to Database-Backed Implementation

## Overview

This document describes the migration of UN/CEFACT Allowance Charge Reason Code (Code List 64465, Version D15B) from JAXB-generated code to a database-backed implementation using JPA entities and Spring Data repositories.

**UN/CEFACT Code List**: 64465
**Schema Version**: D15B (30 May 2016)
**Total Codes**: 105 (1-104 + ZZZ)
**Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B`
**Prefix**: `clm64465AllowanceChargeReasonCode`

## Code Categories

Allowance and charge reasons are organized into **10 categories**:

| Category | Count | Description | Example Codes |
|----------|-------|-------------|---------------|
| **Quality Issue** | 8 | Damaged goods, below specification, transport damage | 3, 5, 6, 10, 13, 14, 15, 16 |
| **Delivery Issue** | 11 | Short delivery, wrong delivery, returns | 4, 8, 17, 20, 21, 23, 24, 33, 34, 35, 36 |
| **Administrative Error** | 11 | Invoice errors, incorrect data, references | 2, 7, 9, 12, 18, 22, 25, 26, 28, 29, 30 |
| **Discount/Allowance** | 34 | Trade discount, volume discount, promotions | 19, 41, 42, 60, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 74, 78, 79, 88, 95, 100 |
| **Financial Charges** | 12 | Bank charges, commissions, drafts | 11, 27, 40, 44, 45, 46, 47, 48, 49, 50, 51, 52 |
| **Claims/Disputes** | 10 | Counter claims, credit balance, offset | 1, 37, 38, 39, 43, 53, 54, 55, 56, 57 |
| **Freight/Logistics** | 7 | Container charges, freight costs | 31, 32, 58, 59, 61, 72, 73 |
| **Payment Terms** | 6 | Proof of delivery, settlement terms | 75, 76, 77, 80, 81, 82 |
| **HR Related** | 5 | Employee changes, salary adjustments | 83, 84, 85, 86, 87 |
| **Custom/Other** | 1 | Mutually defined | ZZZ |

## Files Created

### 1. Entity (`AllowanceChargeReasonCode.java`)
**Location**: `src/main/java/com/wpanther/etax/entity/AllowanceChargeReasonCode.java`

JPA entity representing allowance/charge reason codes with:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `category`, `createdAt`, `updatedAt`
- Code normalization (trim and uppercase)
- 11 business logic methods for category checking:
  - `isQualityIssue()`
  - `isDeliveryIssue()`
  - `isAdministrativeError()`
  - `isDiscountOrAllowance()`
  - `isFinancialCharge()`
  - `isClaimOrDispute()`
  - `isFreightOrLogistics()`
  - `isPaymentTerms()`
  - `isHRRelated()`
  - `isMutuallyDefined()`
  - `isCustomOrOther()`

### 2. Repository (`AllowanceChargeReasonCodeRepository.java`)
**Location**: `src/main/java/com/wpanther/etax/repository/AllowanceChargeReasonCodeRepository.java`

Spring Data JPA repository with **30 query methods**:

**Basic Queries**:
- `findByCode(code)` - Find by code (case-insensitive)
- `findByCategory(category)` - Find all in category
- `findAllCategories()` - Get distinct categories
- `existsByCode(code)` - Check existence

**Category-Specific Queries**:
- `findQualityIssues()` - Quality issue reasons
- `findDeliveryIssues()` - Delivery issue reasons
- `findAdministrativeErrors()` - Administrative error reasons
- `findDiscountsAndAllowances()` - Discount/allowance reasons
- `findFinancialCharges()` - Financial charge reasons
- `findClaimsAndDisputes()` - Claim/dispute reasons
- `findFreightAndLogistics()` - Freight/logistics reasons
- `findPaymentTerms()` - Payment terms reasons
- `findHRRelated()` - HR related reasons

**Search Queries**:
- `findByNameContaining(name)` - Search by name
- `findByDescriptionContaining(keyword)` - Search by description
- `searchByKeyword(searchTerm)` - Full-text search

**Common Codes**:
- `findCommonReasons()` - Most used codes (19, 74, 78, 66, 1, 3, 4, 9, 11, ZZZ)
- `findAgreedSettlement()` - Code 1
- `findDamagedGoods()` - Code 3
- `findShortDelivery()` - Code 4
- `findInvoiceError()` - Code 9
- `findBankCharges()` - Code 11
- `findTradeDiscount()` - Code 19
- `findCashDiscount()` - Code 66
- `findQuantityDiscount()` - Code 74
- `findVolumeDiscount()` - Code 78
- `findMutuallyDefined()` - Code ZZZ

**Statistics**:
- `countByCategory()` - Count by category

### 3. XmlAdapter (`AllowanceChargeReasonCodeAdapter.java`)
**Location**: `src/main/java/com/wpanther/etax/adapter/AllowanceChargeReasonCodeAdapter.java`

JAXB adapter converting between XML strings and database entities:
- `marshal(entity)` - Convert entity → XML string (code)
- `unmarshal(code)` - Convert XML string → entity (database lookup)
- Placeholder creation for unknown codes
- **14 static helper methods**:
  - `isValid(code)`
  - `getReasonName(code)`
  - `getReasonCategory(code)`
  - `getReasonDescription(code)`
  - `isQualityIssue(code)`
  - `isDeliveryIssue(code)`
  - `isAdministrativeError(code)`
  - `isDiscountOrAllowance(code)`
  - `isFinancialCharge(code)`
  - `isClaimOrDispute(code)`
  - `isFreightOrLogistics(code)`
  - `isPaymentTerms(code)`
  - `isHRRelated(code)`
  - `isMutuallyDefined(code)`

### 4. XML Type (`AllowanceChargeReasonCodeType.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/allowancecharge/AllowanceChargeReasonCodeType.java`

Custom JAXB type replacing generated String with database-backed implementation:
- `@XmlValue` with `@XmlJavaTypeAdapter(AllowanceChargeReasonCodeAdapter.class)`
- Constructors: default, from entity, from code string
- Getters: `getCode()`, `getName()`, `getDescription()`, `getCategory()`
- Business logic methods (11 category checks)
- Factory methods: `of(String)`, `of(AllowanceChargeReasonCode)`
- Custom `toString()` with code, name, and category

### 5. Package Configuration (`package-info.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/allowancecharge/package-info.java`

JAXB namespace configuration:
```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm64465AllowanceChargeReasonCode",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B"
        )
    }
)
```

## Architecture Pattern

```
┌─────────────────────────────────────────────────────────────────┐
│                         XML Document                             │
│  <AllowanceChargeReasonCode>19</AllowanceChargeReasonCode>      │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JAXB Unmarshalling
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│           AllowanceChargeReasonCodeAdapter (XmlAdapter)          │
│  • unmarshal("19") → database lookup → entity                   │
│  • marshal(entity) → "19"                                       │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ Database Lookup
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│      AllowanceChargeReasonCodeRepository (Spring Data JPA)       │
│  • findByCode("19") → Optional<AllowanceChargeReasonCode>       │
│  • findTradeDiscount() → Optional<AllowanceChargeReasonCode>    │
│  • findDiscountsAndAllowances() → List<...>                     │
│  • searchByKeyword("discount") → List<...>                      │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JPA Query
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                           │
│  Table: allowance_charge_reason_code                             │
│  • code (PK): "19"                                              │
│  • name: "Trade discount"                                       │
│  • description: "Discount given to trading partners..."         │
│  • category: "Discount/Allowance"                               │
│                                                                  │
│  Indexes:                                                        │
│  • idx_allowance_charge_reason_code_name                        │
│  • idx_allowance_charge_reason_code_category                    │
│  • idx_allowance_charge_reason_code_name_gin (Full-text)        │
│  • idx_allowance_charge_reason_code_description_gin (Full-text) │
│                                                                  │
│  Views:                                                          │
│  • quality_issues_view, delivery_issues_view                    │
│  • admin_errors_view, discounts_allowances_view                 │
│  • financial_charges_view, claims_disputes_view                 │
│  • category_summary_view                                        │
└─────────────────────────────────────────────────────────────────┘
```

## Benefits

### 1. Type Safety
- Compile-time type checking
- No invalid reason codes at runtime
- IDE autocomplete support

### 2. Rich Metadata
- **Name**: Human-readable reason name
- **Description**: Detailed explanation of when to use
- **Category**: Organized classification (10 categories)

### 3. Business Logic
- **11 category check methods** on entity and type
- Easy validation: `entity.isDiscountOrAllowance()`
- Category-based processing logic

### 4. Advanced Query Capabilities
- Find by category: `repository.findDiscountsAndAllowances()`
- Search by name/description: `repository.findByNameContaining("discount")`
- Full-text search: `repository.searchByKeyword("trade")`
- Statistics: `repository.countByCategory()`

### 5. Maintainability
- Add new codes via SQL insert (no code changes)
- Update descriptions without recompilation
- Centralized reason code management

### 6. Validation
- Database constraints ensure data integrity
- Repository methods for existence checking
- Adapter creates placeholders for unknown codes

## Database Setup

### 1. Create Table
```bash
psql -U username -d database_name -f allowance_charge_reason_code.sql
```

### 2. Load Data
```bash
psql -U username -d database_name -f allowance_charge_reason_code_data.sql
```

### 3. Verify Installation
```sql
-- Check total codes (should be 105)
SELECT COUNT(*) FROM allowance_charge_reason_code;

-- Check categories
SELECT category, COUNT(*) as count
FROM allowance_charge_reason_code
WHERE category IS NOT NULL
GROUP BY category
ORDER BY category;

-- View common discount codes
SELECT * FROM discounts_allowances_view;

-- Test full-text search
SELECT * FROM search_allowance_charge_reason_codes('discount');
```

## Usage Examples

### 1. XML Unmarshalling (Reading XML)

**XML Input**:
```xml
<AllowanceChargeReasonCode>19</AllowanceChargeReasonCode>
```

**Java Code**:
```java
// JAXB automatically uses AllowanceChargeReasonCodeAdapter
AllowanceChargeReasonCodeType reasonType = // ... from JAXB unmarshalling

// Access database-backed entity
AllowanceChargeReasonCode entity = reasonType.getValue();

System.out.println(entity.getCode());        // "19"
System.out.println(entity.getName());        // "Trade discount"
System.out.println(entity.getCategory());    // "Discount/Allowance"
System.out.println(entity.getDescription()); // "Discount given to trading partners..."

// Business logic
if (reasonType.isDiscountOrAllowance()) {
    System.out.println("This is a discount/allowance reason");
}
```

### 2. XML Marshalling (Writing XML)

```java
// Create from code string
AllowanceChargeReasonCodeType reasonType = AllowanceChargeReasonCodeType.of("74");

// Create from entity
AllowanceChargeReasonCode entity = repository.findQuantityDiscount().orElseThrow();
AllowanceChargeReasonCodeType reasonType = AllowanceChargeReasonCodeType.of(entity);

// JAXB marshalling produces:
// <AllowanceChargeReasonCode>74</AllowanceChargeReasonCode>
```

### 3. Validation

```java
// Check if code exists
if (AllowanceChargeReasonCodeAdapter.isValid("19")) {
    System.out.println("Valid reason code");
}

// Get reason name
String name = AllowanceChargeReasonCodeAdapter.getReasonName("19");
// Returns: "Trade discount"

// Check category
if (AllowanceChargeReasonCodeAdapter.isDiscountOrAllowance("19")) {
    System.out.println("This is a discount/allowance");
}
```

### 4. Repository Queries

```java
@Autowired
private AllowanceChargeReasonCodeRepository repository;

// Find by code
Optional<AllowanceChargeReasonCode> code = repository.findByCode("19");

// Find all discount reasons
List<AllowanceChargeReasonCode> discounts = repository.findDiscountsAndAllowances();
// Returns: 34 discount/allowance codes

// Find common reasons
List<AllowanceChargeReasonCode> common = repository.findCommonReasons();
// Returns: [19, 74, 78, 66, 1, 3, 4, 9, 11, ZZZ] in priority order

// Search by keyword
List<AllowanceChargeReasonCode> results = repository.searchByKeyword("delivery");
// Returns: All codes with "delivery" in name or description

// Find specific discount types
Optional<AllowanceChargeReasonCode> trade = repository.findTradeDiscount();        // Code 19
Optional<AllowanceChargeReasonCode> quantity = repository.findQuantityDiscount();  // Code 74
Optional<AllowanceChargeReasonCode> volume = repository.findVolumeDiscount();      // Code 78

// Get category statistics
List<Object[]> stats = repository.countByCategory();
// Returns: [["Quality Issue", 8], ["Delivery Issue", 11], ...]
```

### 5. Entity Business Logic

```java
AllowanceChargeReasonCode code = repository.findByCode("3").orElseThrow();

// Category checks
code.isQualityIssue();          // true (code 3 is "Damaged goods")
code.isDiscountOrAllowance();   // false
code.isMutuallyDefined();       // false

// Access metadata
String name = code.getName();              // "Damaged goods"
String desc = code.getDescription();       // "Goods received in damaged condition"
String category = code.getCategory();      // "Quality Issue"
```

## Migration from JAXB

### Before (JAXB Generated - Interface/Implementation Pattern)

```java
// JAXB generated interface
public interface AllowanceChargeReasonCode {
    String getValue();
    void setValue(String value);
}

// JAXB generated implementation
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowanceChargeReasonCode")
public class AllowanceChargeReasonCodeImpl implements AllowanceChargeReasonCode {
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}

// Usage
AllowanceChargeReasonCode code = new AllowanceChargeReasonCodeImpl();
code.setValue("19");
String value = code.getValue(); // Just "19" - no metadata!

// No validation, no category info, no business logic
```

### After (Database-Backed)

```java
// Custom type with database lookup
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowanceChargeReasonCodeContentType")
public class AllowanceChargeReasonCodeType {
    @XmlValue
    @XmlJavaTypeAdapter(AllowanceChargeReasonCodeAdapter.class)
    private AllowanceChargeReasonCode value;

    // Rich metadata and business logic
    public String getCode() { return value.getCode(); }
    public String getName() { return value.getName(); }
    public String getDescription() { return value.getDescription(); }
    public String getCategory() { return value.getCategory(); }

    public boolean isDiscountOrAllowance() {
        return value.isDiscountOrAllowance();
    }
    // ... 10 more category methods
}

// Usage
AllowanceChargeReasonCodeType type = AllowanceChargeReasonCodeType.of("19");
String code = type.getCode();              // "19"
String name = type.getName();              // "Trade discount"
String category = type.getCategory();      // "Discount/Allowance"
boolean isDiscount = type.isDiscountOrAllowance(); // true

// Full validation and business logic available!
```

## Common Allowance/Charge Reason Codes

### Most Frequently Used in e-Tax Invoices

| Code | Name | Category | Usage |
|------|------|----------|-------|
| **19** | Trade discount | Discount/Allowance | Standard discount given to trading partners |
| **74** | Quantity discount | Discount/Allowance | Discount based on quantity purchased |
| **78** | Volume discount | Discount/Allowance | Discount based on total volume |
| **66** | Cash discount | Discount/Allowance | Discount for early/cash payment |
| **1** | Agreed settlement | Claims/Disputes | Mutually agreed settlement amount |
| **3** | Damaged goods | Quality Issue | Goods received damaged |
| **4** | Short delivery | Delivery Issue | Delivery quantity less than ordered |
| **9** | Invoice error | Administrative Error | Error in invoice calculation |
| **11** | Bank charges | Financial Charges | Banking fees and charges |
| **ZZZ** | Mutually defined | Custom/Other | Custom reason agreed by parties |

### Quality Issues (8 codes)
- **3**: Damaged goods
- **5**: Goods returned
- **6**: Error in goods invoiced
- **10**: Goods not delivered
- **13**: Quality allowance
- **14**: Quantity discount
- **15**: Promotion discount
- **16**: Special rebate

### Delivery Issues (11 codes)
- **4**: Short delivery
- **8**: Discount
- **17**: Sundry discount
- **20**: Special rebate
- **21**: Fixed long term
- **23**: Temporary allowance
- **24**: Standard discount
- **33**: Discount for late delivery
- **34**: Discount for early delivery
- **35**: Discount for quantity variance
- **36**: Discount for weight variance

### Discounts (34 codes - Sample)
- **19**: Trade discount
- **41**: Bonus for works ahead of schedule
- **42**: Other bonus
- **60**: Manufacturer's consumer discount
- **62**: Freight equalization
- **63**: Due to military status
- **64**: Due to work accident
- **65**: Special agreement
- **66**: Cash discount
- **67**: Payment discount
- **68**: Quick payment discount
- **69**: Early payment discount
- **70**: Prompt payment discount
- **71**: End of range discount
- **74**: Quantity discount
- **78**: Volume discount
- **79**: Temporary discount
- **88**: Material surcharge/deduction
- **95**: Discount
- **100**: Special rebate

## e-Tax Invoice Use Cases

### 1. Trade Discount Scenario
```java
// Apply 10% trade discount to invoice
AllowanceChargeReasonCodeType reason = AllowanceChargeReasonCodeType.of("19");

AllowanceChargeType allowance = new AllowanceChargeType();
allowance.setChargeIndicator(false); // false = allowance (discount)
allowance.setAllowanceChargeReasonCode(reason);
allowance.setAllowanceChargeReason("10% Trade Discount for Preferred Partner");
allowance.setAmount(new AmountType(1000.00)); // 1000 THB discount

// Verify it's a discount
if (reason.isDiscountOrAllowance()) {
    System.out.println("Applying discount: " + reason.getName());
}
```

### 2. Bank Charge Scenario
```java
// Add bank transfer charge
AllowanceChargeReasonCodeType reason = AllowanceChargeReasonCodeType.of("11");

AllowanceChargeType charge = new AllowanceChargeType();
charge.setChargeIndicator(true); // true = charge (addition)
charge.setAllowanceChargeReasonCode(reason);
charge.setAllowanceChargeReason("Wire Transfer Fee");
charge.setAmount(new AmountType(50.00)); // 50 THB charge

// Verify it's a financial charge
if (reason.isFinancialCharge()) {
    System.out.println("Applying financial charge: " + reason.getName());
}
```

### 3. Damaged Goods Allowance
```java
// Credit for damaged goods
AllowanceChargeReasonCodeType reason = AllowanceChargeReasonCodeType.of("3");

AllowanceChargeType allowance = new AllowanceChargeType();
allowance.setChargeIndicator(false);
allowance.setAllowanceChargeReasonCode(reason);
allowance.setAllowanceChargeReason("Partial refund for damaged items in shipment");
allowance.setAmount(new AmountType(500.00));

// Verify it's a quality issue
if (reason.isQualityIssue()) {
    System.out.println("Quality issue allowance: " + reason.getName());
}
```

### 4. Invoice Error Correction
```java
// Correct invoice calculation error
AllowanceChargeReasonCodeType reason = AllowanceChargeReasonCodeType.of("9");

AllowanceChargeType allowance = new AllowanceChargeType();
allowance.setChargeIndicator(false);
allowance.setAllowanceChargeReasonCode(reason);
allowance.setAllowanceChargeReason("Correction for overcharged amount");
allowance.setAmount(new AmountType(250.00));

// Verify it's administrative
if (reason.isAdministrativeError()) {
    System.out.println("Administrative correction: " + reason.getName());
}
```

### 5. Custom Reason (ZZZ)
```java
// Custom mutually-defined reason
AllowanceChargeReasonCodeType reason = AllowanceChargeReasonCodeType.of("ZZZ");

AllowanceChargeType allowance = new AllowanceChargeType();
allowance.setChargeIndicator(false);
allowance.setAllowanceChargeReasonCode(reason);
allowance.setAllowanceChargeReason("Loyalty program reward - 1000 points redeemed");
allowance.setAmount(new AmountType(100.00));

// Verify it's mutually defined
if (reason.isMutuallyDefined()) {
    System.out.println("Custom reason: " + allowance.getAllowanceChargeReason());
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
| **AllowanceChargeReasonCode** | ✅ **Migrated** | **105 reason codes** |

## Summary

The AllowanceChargeReasonCode migration provides:
- ✅ **105 reason codes** organized into **10 categories**
- ✅ **30 repository query methods** for flexible data access
- ✅ **14 static helper methods** for validation and lookup
- ✅ **11 business logic methods** for category checking
- ✅ **Full-text search** with GIN indexes
- ✅ **7 database views** for common queries
- ✅ **Complete JAXB compatibility** with original schema
- ✅ **Type-safe** invoice adjustment processing
- ✅ **Rich metadata** for discounts, charges, and corrections

This implementation enables sophisticated invoice adjustment handling with proper categorization, validation, and business logic for Thai e-Tax Invoice compliance.
