# ISO Currency Code Migration to Database-Backed Implementation

## Overview

This document describes the migration of ISO 4217 Three-letter Currency Code from JAXB-generated enum to a database-backed implementation using JPA entities and Spring Data repositories.

**Standard**: ISO 4217 alpha-3
**Code List Version**: 2012-08-31
**Schema Version**: 9.3 (15 Nov 2014)
**Total Codes**: 172
**Namespace**: `urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31`
**Prefix**: `clm5ISO42173A`

## Currency Code Structure

ISO 4217 currency codes are **3 uppercase letters** representing currencies worldwide:

| Code | Name | Numeric | Decimals | Description |
|------|------|---------|----------|-------------|
| **THB** | Baht | 764 | 2 | Thai currency |
| **USD** | US Dollar | 840 | 2 | United States Dollar |
| **EUR** | Euro | 978 | 2 | European Union currency |
| **JPY** | Yen | 392 | **0** | Japanese Yen (no cents) |
| **CNY** | Yuan Renminbi | 156 | 2 | Chinese currency |
| **GBP** | Pound Sterling | 826 | 2 | British currency |
| **BHD** | Bahraini Dinar | 048 | **3** | High precision currency |
| **KWD** | Kuwaiti Dinar | 414 | **3** | High precision currency |

## Files Created

### 1. Entity (`ISOCurrencyCode.java`)
**Location**: `src/main/java/com/wpanther/etax/entity/ISOCurrencyCode.java`

JPA entity representing ISO 4217 currency codes with:
- Primary key: `code` (VARCHAR 3)
- Fields: `name`, `description`, `numericCode`, `minorUnits`, `isActive`, timestamps
- Code normalization (trim and uppercase)
- 9 business logic methods:
  - `isThaiBasht()` - Check if THB
  - `isUSDollar()` - Check if USD
  - `isEuro()` - Check if EUR
  - `isMajorCurrency()` - Check if major reserve currency
  - `isASEANCurrency()` - Check if ASEAN currency
  - `hasNoDecimalPlaces()` - Check if 0 decimals (JPY, KRW)
  - `hasThreeDecimalPlaces()` - Check if 3 decimals (BHD, KWD)
  - `getDecimalPlaces()` - Get decimal count (defaults to 2)
  - `formatAmount(double)` - Format amount with currency

### 2. Repository (`ISOCurrencyCodeRepository.java`)
**Location**: `src/main/java/com/wpanther/etax/repository/ISOCurrencyCodeRepository.java`

Spring Data JPA repository with **29 query methods**:

**Basic Queries**:
- `findByCode(code)` - Find by code (case-insensitive)
- `findByNumericCode(numericCode)` - Find by numeric code
- `findAllActive()` - Get all active currencies
- `existsByCode(code)` - Check existence

**Currency Groups**:
- `findMajorCurrencies()` - USD, EUR, JPY, GBP, CNY, CHF, CAD, AUD
- `findASEANCurrencies()` - THB, BND, KHR, IDR, LAK, MYR, MMK, PHP, SGD, VND
- `findThaiTradingCurrencies()` - 16 common currencies used in Thai trade

**Decimal Place Queries**:
- `findByMinorUnits(minorUnits)` - Find by decimal places
- `findZeroDecimalCurrencies()` - JPY, KRW, VND, etc.
- `findThreeDecimalCurrencies()` - BHD, KWD, etc.

**Search**:
- `findByNameContaining(name)` - Search by name

**Specific Currency Queries** (16 methods):
- `findThaiBasht()` - THB
- `findUSDollar()` - USD
- `findEuro()` - EUR
- `findJapaneseYen()` - JPY
- `findChineseYuan()` - CNY
- `findBritishPound()` - GBP
- `findSingaporeDollar()` - SGD
- `findMalaysianRinggit()` - MYR
- `findIndonesianRupiah()` - IDR
- `findHongKongDollar()` - HKD
- `findKoreanWon()` - KRW
- `findVietnameseDong()` - VND
- `findAustralianDollar()` - AUD
- `findSwissFranc()` - CHF
- `findCanadianDollar()` - CAD
- `findIndianRupee()` - INR

### 3. XmlAdapter (`ISOCurrencyCodeAdapter.java`)
**Location**: `src/main/java/com/wpanther/etax/adapter/ISOCurrencyCodeAdapter.java`

JAXB adapter converting between JAXB enum values and database entities:
- `marshal(entity)` - Convert entity → JAXB enum value
- `unmarshal(enumValue)` - Convert JAXB enum → entity (database lookup)
- Placeholder creation for unknown codes
- **12 static helper methods**:
  - `isValid(code)`
  - `getName(code)`
  - `getNumericCode(code)`
  - `getMinorUnits(code)`
  - `isThaiBasht(code)`
  - `isUSDollar(code)`
  - `isMajorCurrency(code)`
  - `isASEANCurrency(code)`
  - `hasNoDecimalPlaces(code)`
  - `formatAmount(amount, code)`

### 4. XML Type (`ISOCurrencyCodeType.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/isocurrency/ISOCurrencyCodeType.java`

Custom JAXB type replacing generated JAXBElement<ENUM> with database-backed implementation:
- `@XmlValue` with `@XmlJavaTypeAdapter(ISOCurrencyCodeAdapter.class)`
- Constructors: default, from entity, from code string
- Getters: `getCode()`, `getName()`, `getDescription()`, `getNumericCode()`, `getMinorUnits()`, `getDecimalPlaces()`
- Business logic methods (9 type checks)
- `formatAmount(double)` - Format with currency
- Factory methods: `of(String)`, `of(ISOCurrencyCode)`
- Custom `toString()` with code, name, numeric code, and decimal places

### 5. Package Configuration (`package-info.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/isocurrency/package-info.java`

JAXB namespace configuration:
```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm5ISO42173A",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31"
        )
    }
)
```

## Architecture Pattern

```
┌─────────────────────────────────────────────────────────────────┐
│                         XML Document                             │
│  <ISO3AlphaCurrencyCode>THB</ISO3AlphaCurrencyCode>             │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JAXB Unmarshalling
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│           ISOCurrencyCodeAdapter (XmlAdapter)                    │
│  • unmarshal(ENUM.THB) → database lookup → entity               │
│  • marshal(entity) → ENUM.THB                                   │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ Database Lookup
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│         ISOCurrencyCodeRepository (Spring Data JPA)              │
│  • findByCode("THB") → Optional<ISOCurrencyCode>                │
│  • findThaiBasht() → Optional<ISOCurrencyCode>                  │
│  • findMajorCurrencies() → List<ISOCurrencyCode>                │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JPA Query
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                           │
│  Table: iso_currency_code                                        │
│  • code (PK): "THB"                                             │
│  • name: "Baht"                                                 │
│  • numeric_code: "764"                                          │
│  • minor_units: 2                                               │
│  • is_active: true                                              │
│                                                                  │
│  Indexes:                                                        │
│  • idx_iso_currency_code_name                                   │
│  • idx_iso_currency_code_is_active                              │
│  • idx_iso_currency_code_numeric                                │
│                                                                  │
│  Views:                                                          │
│  • iso_currency_code_major (8 major currencies)                 │
│  • iso_currency_code_asean (10 ASEAN currencies)                │
│  • iso_currency_code_thai_trading (16 currencies)               │
│  • iso_currency_code_active (all active)                        │
│                                                                  │
│  Helper Functions:                                               │
│  • get_currency_name(code)                                      │
│  • is_valid_currency_code(code)                                 │
│  • get_currency_minor_units(code)                               │
│  • format_amount_with_currency(amount, code)                    │
└─────────────────────────────────────────────────────────────────┘
```

## Benefits

### 1. Type Safety
- Compile-time type checking
- No invalid currency codes at runtime
- IDE autocomplete support

### 2. Rich Metadata
- **Name**: Currency name (Baht, US Dollar, Euro)
- **Numeric Code**: ISO 4217 numeric code (764, 840, 978)
- **Minor Units**: Decimal places (0, 2, or 3)
- **Description**: Additional information (effective dates, special notes)

### 3. Decimal Place Handling
- **Automatic detection** of decimal places
- **0 decimals**: JPY (Yen), KRW (Won), VND (Dong), etc.
- **2 decimals**: Most currencies (USD, EUR, THB, etc.)
- **3 decimals**: BHD (Bahraini Dinar), KWD (Kuwaiti Dinar), etc.

### 4. Amount Formatting
- **Automatic formatting** based on currency
- `formatAmount(1234.56, "THB")` → "1,234.56 THB"
- `formatAmount(1234.56, "JPY")` → "1,235 JPY" (no decimals)
- `formatAmount(1.234, "BHD")` → "1.234 BHD" (3 decimals)

### 5. Business Logic
- **Currency groupings**: Major, ASEAN, Thai trading partners
- **Type checking**: isMajorCurrency(), isASEANCurrency()
- **Validation**: existsByCode(), isValid()

### 6. Query Capabilities
- Find by code or numeric code
- Search by name
- Filter by decimal places
- Group by currency type

### 7. Maintainability
- Add new currencies via SQL insert (no code changes)
- Update metadata without recompilation
- Centralized currency management

## Database Setup

### 1. Create Table
```bash
psql -U username -d database_name -f iso_currency_code.sql
```

### 2. Load Data
```bash
psql -U username -d database_name -f iso_currency_code_data.sql
```

### 3. Verify Installation
```sql
-- Check total codes (should be 172)
SELECT COUNT(*) FROM iso_currency_code WHERE is_active = true;

-- View Thai Baht
SELECT * FROM iso_currency_code WHERE code = 'THB';

-- View major currencies
SELECT * FROM iso_currency_code_major;

-- View ASEAN currencies
SELECT * FROM iso_currency_code_asean;

-- View currencies with 0 decimal places
SELECT code, name, minor_units FROM iso_currency_code WHERE minor_units = 0;

-- Test helper function
SELECT get_currency_name('THB');  -- Returns: Baht
SELECT get_currency_minor_units('JPY');  -- Returns: 0
SELECT format_amount_with_currency(1234.56, 'THB');
```

## Usage Examples

### 1. XML Unmarshalling (Reading XML)

**XML Input**:
```xml
<ISO3AlphaCurrencyCode>THB</ISO3AlphaCurrencyCode>
```

**Java Code**:
```java
// JAXB automatically uses ISOCurrencyCodeAdapter
ISOCurrencyCodeType currencyType = // ... from JAXB unmarshalling

// Access database-backed entity
ISOCurrencyCode entity = currencyType.getValue();

System.out.println(entity.getCode());         // "THB"
System.out.println(entity.getName());         // "Baht"
System.out.println(entity.getNumericCode());  // "764"
System.out.println(entity.getMinorUnits());   // 2
System.out.println(entity.getDecimalPlaces()); // 2

// Business logic
if (currencyType.isThaiBasht()) {
    System.out.println("This is Thai Baht");
}

// Format amount
String formatted = currencyType.formatAmount(1234.56);
System.out.println(formatted);  // "1,234.56 THB"
```

### 2. XML Marshalling (Writing XML)

```java
// Create from code string
ISOCurrencyCodeType currencyType = ISOCurrencyCodeType.of("THB");

// Create from entity
ISOCurrencyCode entity = repository.findThaiBasht().orElseThrow();
ISOCurrencyCodeType currencyType = ISOCurrencyCodeType.of(entity);

// JAXB marshalling produces:
// <ISO3AlphaCurrencyCode>THB</ISO3AlphaCurrencyCode>
```

### 3. Validation

```java
// Check if code exists
if (ISOCurrencyCodeAdapter.isValid("THB")) {
    System.out.println("Valid currency code");
}

// Get currency name
String name = ISOCurrencyCodeAdapter.getName("THB");
// Returns: "Baht"

// Get decimal places
Integer decimals = ISOCurrencyCodeAdapter.getMinorUnits("JPY");
// Returns: 0 (Japanese Yen has no decimal places)

// Check currency type
if (ISOCurrencyCodeAdapter.isASEANCurrency("THB")) {
    System.out.println("ASEAN currency");
}
```

### 4. Repository Queries

```java
@Autowired
private ISOCurrencyCodeRepository repository;

// Find by code
Optional<ISOCurrencyCode> thb = repository.findByCode("THB");

// Find specific currencies
Optional<ISOCurrencyCode> baht = repository.findThaiBasht();
Optional<ISOCurrencyCode> dollar = repository.findUSDollar();
Optional<ISOCurrencyCode> yen = repository.findJapaneseYen();

// Find currency groups
List<ISOCurrencyCode> major = repository.findMajorCurrencies();
// Returns: USD, EUR, JPY, GBP, CNY, CHF, CAD, AUD

List<ISOCurrencyCode> asean = repository.findASEANCurrencies();
// Returns: THB, BND, KHR, IDR, LAK, MYR, MMK, PHP, SGD, VND

List<ISOCurrencyCode> trading = repository.findThaiTradingCurrencies();
// Returns: 16 currencies in priority order (THB, USD, EUR, JPY, ...)

// Find by decimal places
List<ISOCurrencyCode> noDecimals = repository.findZeroDecimalCurrencies();
// Returns: JPY, KRW, VND, BIF, CLP, DJF, GNF, ISK, KMF, PYG, RWF, UGX, VUV, XAF, XOF, XPF

List<ISOCurrencyCode> threeDecimals = repository.findThreeDecimalCurrencies();
// Returns: BHD, IQD, JOD, KWD, LYD, OMR, TND

// Search by name
List<ISOCurrencyCode> dollars = repository.findByNameContaining("Dollar");
// Returns: All currencies with "Dollar" in the name
```

### 5. Amount Formatting

```java
ISOCurrencyCode thb = repository.findThaiBasht().orElseThrow();
ISOCurrencyCode jpy = repository.findJapaneseYen().orElseThrow();
ISOCurrencyCode bhd = repository.findByCode("BHD").orElseThrow();

// Format with Thai Baht (2 decimals)
String thbAmount = thb.formatAmount(1234.56);
// Returns: "1,234.56 THB"

// Format with Japanese Yen (0 decimals)
String jpyAmount = jpy.formatAmount(1234.56);
// Returns: "1,235 JPY" (rounded to no decimals)

// Format with Bahraini Dinar (3 decimals)
String bhdAmount = bhd.formatAmount(1.234567);
// Returns: "1.235 BHD" (3 decimal places)

// Using static helper
String formatted = ISOCurrencyCodeAdapter.formatAmount(1234.56, "USD");
// Returns: "1,234.56 USD"
```

## Migration from JAXB

### Before (JAXB Generated - ENUM Pattern)

```java
// JAXB generated enum
@XmlEnum
public enum ISO3AlphaCurrencyCodeContentType {
    AED, AFN, ALL, AMD, ..., THB, ..., USD, ..., ZWL;
}

// JAXB generated ObjectFactory
public class ObjectFactory {
    @XmlElementDecl(namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31",
                    name = "ISO3AlphaCurrencyCode")
    public JAXBElement<ISO3AlphaCurrencyCodeContentType> createISO3AlphaCurrencyCode(
        ISO3AlphaCurrencyCodeContentType value) {
        return new JAXBElement<ISO3AlphaCurrencyCodeContentType>(...);
    }
}

// Usage
JAXBElement<ISO3AlphaCurrencyCodeContentType> element =
    objectFactory.createISO3AlphaCurrencyCode(ISO3AlphaCurrencyCodeContentType.THB);
ISO3AlphaCurrencyCodeContentType value = element.getValue(); // Just enum THB - no metadata!

// No currency name, no numeric code, no decimal places info
```

### After (Database-Backed)

```java
// Custom type with database lookup
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISO3AlphaCurrencyCodeContentType")
public class ISOCurrencyCodeType {
    @XmlValue
    @XmlJavaTypeAdapter(ISOCurrencyCodeAdapter.class)
    private ISOCurrencyCode value;

    // Rich metadata and business logic
    public String getCode() { return value.getCode(); }
    public String getName() { return value.getName(); }
    public String getNumericCode() { return value.getNumericCode(); }
    public Integer getMinorUnits() { return value.getMinorUnits(); }
    public int getDecimalPlaces() { return value.getDecimalPlaces(); }

    public boolean isThaiBasht() { return value.isThaiBasht(); }
    public boolean isMajorCurrency() { return value.isMajorCurrency(); }
    public String formatAmount(double amount) { return value.formatAmount(amount); }
    // ... 8 more methods
}

// Usage
ISOCurrencyCodeType type = ISOCurrencyCodeType.of("THB");
String code = type.getCode();              // "THB"
String name = type.getName();              // "Baht"
String numeric = type.getNumericCode();    // "764"
Integer decimals = type.getMinorUnits();   // 2
boolean isThai = type.isThaiBasht();       // true
String formatted = type.formatAmount(1234.56); // "1,234.56 THB"

// Full metadata, validation, and formatting available!
```

## Common Currency Codes

### Major Reserve Currencies (8 codes)
| Code | Name | Numeric | Decimals | Usage |
|------|------|---------|----------|-------|
| **USD** | US Dollar | 840 | 2 | World reserve currency |
| **EUR** | Euro | 978 | 2 | European Union |
| **JPY** | Yen | 392 | **0** | Japan (no cents) |
| **GBP** | Pound Sterling | 826 | 2 | United Kingdom |
| **CNY** | Yuan Renminbi | 156 | 2 | China |
| **CHF** | Swiss Franc | 756 | 2 | Switzerland |
| **CAD** | Canadian Dollar | 124 | 2 | Canada |
| **AUD** | Australian Dollar | 036 | 2 | Australia |

### ASEAN Currencies (10 codes)
| Code | Name | Numeric | Decimals | Country |
|------|------|---------|----------|---------|
| **THB** | Baht | 764 | 2 | Thailand |
| **SGD** | Singapore Dollar | 702 | 2 | Singapore |
| **MYR** | Malaysian Ringgit | 458 | 2 | Malaysia |
| **IDR** | Rupiah | 360 | 2 | Indonesia |
| **PHP** | Philippine Peso | 608 | 2 | Philippines |
| **VND** | Dong | 704 | **0** | Vietnam |
| **BND** | Brunei Dollar | 096 | 2 | Brunei |
| **MMK** | Kyat | 104 | 2 | Myanmar |
| **LAK** | Kip | 418 | 2 | Laos |
| **KHR** | Riel | 116 | 2 | Cambodia |

### Currencies with Special Decimal Places

**Zero Decimals (16 codes)**: JPY, KRW, VND, BIF, CLP, DJF, GNF, ISK, KMF, PYG, RWF, UGX, VUV, XAF, XOF, XPF

**Three Decimals (7 codes)**: BHD, IQD, JOD, KWD, LYD, OMR, TND

## e-Tax Invoice Use Cases

### 1. Thai Baht Invoice
```java
// Create invoice in Thai Baht
ISOCurrencyCodeType currency = ISOCurrencyCodeType.of("THB");

MonetaryTotalType totals = new MonetaryTotalType();
totals.setTaxExclusiveAmount(new AmountType(10000.00, currency));
totals.setTaxInclusiveAmount(new AmountType(10700.00, currency));

// Verify currency
if (currency.isThaiBasht()) {
    System.out.println("Invoice in Thai Baht");
    System.out.println("Decimals: " + currency.getDecimalPlaces());  // 2
}
```

### 2. Foreign Currency Invoice (USD)
```java
// Create invoice in US Dollars
ISOCurrencyCodeType currency = ISOCurrencyCodeType.of("USD");

MonetaryTotalType totals = new MonetaryTotalType();
totals.setTaxExclusiveAmount(new AmountType(1000.00, currency));

// Check if major currency
if (currency.isMajorCurrency()) {
    System.out.println("Major reserve currency: " + currency.getName());
}

// Format amount
String formatted = currency.formatAmount(1234.56);
System.out.println(formatted);  // "1,234.56 USD"
```

### 3. Japanese Yen (Zero Decimals)
```java
// Create invoice in Japanese Yen
ISOCurrencyCodeType currency = ISOCurrencyCodeType.of("JPY");

// JPY has no decimal places
System.out.println("Decimals: " + currency.getDecimalPlaces());  // 0

if (currency.hasNoDecimalPlaces()) {
    // Use integer amounts only
    MonetaryTotalType totals = new MonetaryTotalType();
    totals.setTaxExclusiveAmount(new AmountType(100000, currency));  // ¥100,000
}

// Format properly (no decimals)
String formatted = currency.formatAmount(100000);
System.out.println(formatted);  // "100,000 JPY"
```

### 4. Multi-Currency Support
```java
@Autowired
private ISOCurrencyCodeRepository repository;

// Get all currencies commonly used in Thai trade
List<ISOCurrencyCode> tradingCurrencies = repository.findThaiTradingCurrencies();

// Display currency options to user
tradingCurrencies.forEach(curr -> {
    System.out.printf("%s - %s (%d decimals)%n",
                     curr.getCode(),
                     curr.getName(),
                     curr.getDecimalPlaces());
});

// Output:
// THB - Baht (2 decimals)
// USD - US Dollar (2 decimals)
// EUR - Euro (2 decimals)
// JPY - Yen (0 decimals)
// CNY - Yuan Renminbi (2 decimals)
// ...
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
| AllowanceChargeIdentificationCode | ✅ Migrated | 106 identification codes |
| AddressType | ✅ Migrated | 3 address types |
| **ISO3AlphaCurrencyCode** | ✅ **Migrated** | **172 currencies** |

## Summary

The ISO Currency Code migration provides:
- ✅ **172 currency codes** with full ISO 4217 metadata
- ✅ **29 repository query methods** for flexible data access
- ✅ **12 static helper methods** for validation and lookup
- ✅ **9 business logic methods** for type checking and formatting
- ✅ **Automatic decimal place handling** (0, 2, or 3 decimals)
- ✅ **Amount formatting** based on currency
- ✅ **Currency groupings** (major, ASEAN, Thai trading partners)
- ✅ **4 database views** for common queries
- ✅ **4 helper functions** for SQL-level operations
- ✅ **Complete JAXB compatibility** with original enum
- ✅ **Type-safe** monetary amount handling

This implementation enables sophisticated multi-currency invoice processing with proper decimal place handling, automatic amount formatting, and rich currency metadata for Thai e-Tax Invoice compliance.
