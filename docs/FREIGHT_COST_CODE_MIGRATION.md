# Database-Backed Implementation for Freight Cost Code

This document describes the database-backed implementation for UN/CEFACT Freight Cost Code (Recommendation 23, Version 4), replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/FreightCostCode.java`

JPA Entity class for storing UN/CEFACT Freight Cost Codes in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 6)
- Fields: `name`, `category`, `codeGroup` (generated column), timestamps
- Code normalization (trim)
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 1,641 codes (100000 to 609144)

**Freight Categories** (12):
- **Basic Freight**: Standard freight charges (code group 101)
- **Freight Surcharges**: Allowances, discounts, commissions
- **Container Services**: Container handling and services
- **Terminal Charges**: Port and terminal operations
- **Handling Charges**: Cargo handling and labor
- **Storage & Demurrage**: Storage and detention charges
- **Customs & Documentation**: Customs clearance and paperwork
- **Dangerous Goods**: Hazardous materials handling
- **Special Freight**: Special cargo and services
- **Insurance**: Cargo insurance charges
- **Other Charges**: Miscellaneous fees
- **General Freight**: General freight classifications

**Business Logic Methods**:
- `isBasicFreight()` - Check if basic freight
- `isFreightSurcharge()` - Check if surcharge/allowance
- `isContainerService()` - Check if container-related
- `isTerminalCharge()` - Check if terminal charge
- `isHandlingCharge()` - Check if handling charge
- `isStorageOrDemurrage()` - Check if storage/demurrage
- `isCustomsOrDocumentation()` - Check if customs/documentation
- `isDangerousGoods()` - Check if dangerous goods
- `isSpecialFreight()` - Check if special freight
- `isInsurance()` - Check if insurance
- `getCodeGroup()` - Get first 3 digits of code

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/FreightCostCodeRepository.java`

Spring Data JPA repository for database operations.

**Query Methods** (28):
- `findByCode(code)` - Find by code
- `findByCategory(category)` - Get by category
- `findAllCategories()` - Get all available categories
- `findByCodeGroup(codeGroup)` - Get by 3-digit code group
- `findByNameContaining(name)` - Search by name
- `existsByCode(code)` - Check if code exists
- `findBasicFreight()` - Get basic freight codes
- `findFreightSurcharges()` - Get surcharges/allowances
- `findContainerServices()` - Get container codes
- `findTerminalCharges()` - Get terminal codes
- `findHandlingCharges()` - Get handling codes
- `findStorageAndDemurrage()` - Get storage codes
- `findCustomsAndDocumentation()` - Get customs codes
- `findDangerousGoods()` - Get dangerous goods codes
- `findSpecialFreight()` - Get special freight codes
- `findInsurance()` - Get insurance codes
- `findGeneralFreight()` - Get general freight codes
- `findOtherCharges()` - Get other charges
- `findCommonCodes()` - Get most commonly used codes
- `findByCodeRange(start, end)` - Get codes in range
- `countByCategory()` - Count by category
- `countByCodeGroup()` - Count by code group
- `findFreightChargesCode()` - Get code 100000
- `findBasicFreightCode()` - Get code 101000
- `findAllFreightChargesCode()` - Get code 100999
- `searchByKeyword(term)` - Full-text search

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/FreightCostCodeAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods (15):
  - `isValid(code)` - Validate code exists
  - `getFreightCostName(code)` - Get name from code
  - `getFreightCostCategory(code)` - Get category from code
  - `getCodeGroup(code)` - Get code group from code
  - `isBasicFreight(code)` - Check if basic freight
  - `isFreightSurcharge(code)` - Check if surcharge
  - `isContainerService(code)` - Check if container service
  - `isTerminalCharge(code)` - Check if terminal charge
  - `isHandlingCharge(code)` - Check if handling charge
  - `isStorageOrDemurrage(code)` - Check if storage/demurrage
  - `isCustomsOrDocumentation(code)` - Check if customs/documentation
  - `isDangerousGoods(code)` - Check if dangerous goods
  - `isSpecialFreight(code)` - Check if special freight
  - `isInsurance(code)` - Check if insurance

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/freightcost/FreightCostCodeType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4`
- Helper methods: `getCode()`, `getName()`, `getCategory()`, `getCodeGroup()`, plus all business logic methods
- Factory methods: `of(String)`, `of(FreightCostCode)`

**File**: `src/main/java/com/wpanther/etax/xml/freightcost/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4`
- Prefix: `ids6Recommendation23`
- Element form: QUALIFIED

### 5. Database Schema (Already Exists)

**File**: `freight_cost_code.sql` (121 lines)

PostgreSQL table schema with:
- Table definition with 6 columns
- Generated column: `code_group` (first 3 digits)
- 4 indexes for performance (name full-text, category, code_group, name pattern)
- Auto-update timestamp trigger
- 4 views (basic, container, dangerous_goods, group_summary)
- 1 full-text search function

**File**: `freight_cost_code_data.sql` (1,658 lines)

Data insert statements for all 1,641 codes in 4 batches.

## Architecture Pattern

This implementation follows the **exact same pattern** as other code lists:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <FreightCostCode>101000</FreightCostCode>                 │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      FreightCostCodeType (XML Wrapper)                      │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      FreightCostCodeAdapter (XmlAdapter)                    │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│    FreightCostCodeRepository (Spring Data JPA)              │
│  - findByCode()                                             │
│  - findByCategory()                                         │
│  - findCommonCodes()                                        │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│       FreightCostCode (JPA Entity)                          │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - Freight category classification                          │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: freight_cost_code (1,641 records)                 │
└─────────────────────────────────────────────────────────────┘
```

## Benefits

### ✅ Type Safety
- Strongly-typed entities instead of plain strings
- Compile-time validation of entity structure
- Runtime flexibility for new codes

### ✅ Validation
- Automatic validation during XML unmarshalling
- Rejects invalid codes or creates placeholder
- Database constraints enforce data integrity

### ✅ Rich Metadata
- Access to freight cost names and descriptions
- Category-based classification (12 categories)
- Code group classification (3-digit prefixes)

### ✅ Business Logic Integration
- Query by freight type (basic, surcharge, container, etc.)
- Filter by category
- Identify special charges (dangerous goods, insurance, etc.)
- Search by keyword

### ✅ Query Capabilities
- Search by category (12 main categories)
- Filter by code group (100-609)
- Find common codes (10 most used)
- Full-text search on names
- Case-insensitive search

### ✅ Maintainability
- Centralized management in database
- Easy to add new codes
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <FreightCostCode>101000</FreightCostCode>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access freight cost details
FreightCostCodeType freightCode = invoice.getFreightCostCode();
String code = freightCode.getCode();                    // "101000"
String name = freightCode.getName();                    // "BASIC FREIGHT"
String category = freightCode.getCategory();            // "Basic Freight"
String codeGroup = freightCode.getCodeGroup();          // "101"
boolean isBasic = freightCode.isBasicFreight();         // true
boolean isContainer = freightCode.isContainerService(); // false
```

### XML Marshalling

```java
// Create from entity
FreightCostCode entity = repository.findByCode("101000").get();
FreightCostCodeType freightCode = FreightCostCodeType.of(entity);

// Or create from code string
FreightCostCodeType freightCode = FreightCostCodeType.of("100000");

// Set in invoice
invoice.setFreightCostCode(freightCode);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <FreightCostCode>100000</FreightCostCode>
```

### Validation

```java
// Check if code is valid
if (FreightCostCodeAdapter.isValid("101000")) {
    // Code exists
}

// Get freight cost name
String name = FreightCostCodeAdapter.getFreightCostName("101000");
// Returns: "BASIC FREIGHT"

// Get category
String category = FreightCostCodeAdapter.getFreightCostCategory("101000");
// Returns: "Basic Freight"

// Check freight type
boolean isBasic = FreightCostCodeAdapter.isBasicFreight("101000");
// Returns: true

boolean isContainer = FreightCostCodeAdapter.isContainerService("103000");
// Returns: depends on code

boolean isDangerous = FreightCostCodeAdapter.isDangerousGoods("609141");
// Returns: true
```

### Repository Queries

```java
@Autowired
private FreightCostCodeRepository repository;

// Get basic freight code
Optional<FreightCostCode> basicFreight = repository.findBasicFreightCode();

// Get all basic freight codes
List<FreightCostCode> basicCodes = repository.findBasicFreight();

// Get container services
List<FreightCostCode> containerCodes = repository.findContainerServices();

// Get by category
List<FreightCostCode> terminalCodes = repository.findByCategory("Terminal Charges");

// Get by code group
List<FreightCostCode> group101 = repository.findByCodeGroup("101");

// Full-text search
List<FreightCostCode> results = repository.searchByKeyword("container");

// Get common codes
List<FreightCostCode> common = repository.findCommonCodes();
// Returns: 100000, 101000, 101021, 102000, etc.

// Count by category
List<Object[]> categoryCounts = repository.countByCategory();
```

## Database Setup

```sql
-- 1. Create schema (already done)
\i freight_cost_code.sql

-- 2. Load data (already done)
\i freight_cost_code_data.sql

-- 3. Verify
SELECT COUNT(*) FROM freight_cost_code;
-- Expected: 1641

SELECT COUNT(DISTINCT category) FROM freight_cost_code;
-- Expected: 12

SELECT COUNT(DISTINCT code_group) FROM freight_cost_code;
-- Expected: ~50 (100-609)

-- 4. Query examples
SELECT * FROM freight_cost_code WHERE code = '101000';
SELECT * FROM freight_cost_code_basic;
SELECT * FROM freight_cost_code_container;
SELECT * FROM freight_cost_code_group_summary;

-- 5. Use functions
SELECT * FROM search_freight_cost_code('container');
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "101000";  // Just a string, could be invalid
JAXBElement<String> freightCode = objectFactory.createFreightCostCode(code);
// No way to know if "101000" is valid
// No way to get the name or category
// No business logic integration
```

### After (Database-Backed)

```java
// Database-backed: FreightCostCodeType with full entity
FreightCostCodeType freightCode = FreightCostCodeType.of("101000");
// Validates against database
// Full access to metadata

String code = freightCode.getCode();                // "101000"
String name = freightCode.getName();                // "BASIC FREIGHT"
String category = freightCode.getCategory();        // "Basic Freight"
String codeGroup = freightCode.getCodeGroup();      // "101"
boolean isBasic = freightCode.isBasicFreight();     // true
boolean isContainer = freightCode.isContainerService(); // false
```

## Comparison with Other Code Lists

| Feature | Freight Cost Code | Reference Type Code | Duty Tax Fee Type |
|---------|-------------------|---------------------|-------------------|
| **Total Codes** | 1,641 | 798 | 53 |
| **Code Format** | 6-digit numeric | Alphanumeric | Alphanumeric |
| **JAXB Original** | String | String | Enum |
| **Categories** | ✅ 12 categories | ✅ 9 categories | ✅ 7 categories |
| **Code Groups** | ✅ 3-digit (100-609) | ❌ | ❌ |
| **Entity** | ✅ | ✅ | ✅ |
| **Repository** | ✅ 28 methods | ✅ 13 methods | ✅ 24 methods |
| **Adapter** | ✅ 15 helpers | ✅ 6 helpers | ✅ 10 helpers |
| **XML Wrapper** | ✅ | ✅ | ✅ |
| **Package-info** | ✅ | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ | ✅ |

## Freight Cost Code - Common Codes

| Code | Name | Category | Code Group |
|------|------|----------|------------|
| 100000 | FREIGHT CHARGES | General Freight | 100 |
| 100999 | All freight charges | General Freight | 100 |
| 101000 | BASIC FREIGHT | Basic Freight | 101 |
| 101021 | Airfreight (FF) | Basic Freight | 101 |
| 102000 | FREIGHT CHARGE ALLOWANCE | Freight Surcharges | 102 |
| 103000 | Container charges | Container Services | 103 |
| 104000 | Terminal handling | Terminal Charges | 104 |
| 105000 | Cargo handling | Handling Charges | 105 |
| 106000 | Storage charges | Storage & Demurrage | 106 |
| 107000 | Customs fees | Customs & Documentation | 107 |

## e-Tax Invoice Use Cases

Freight Cost Codes are used in e-Tax Invoice to indicate transportation and logistics charges:

1. **Basic Freight (101000)**: Standard shipping costs
2. **Container Services**: Container handling and repositioning
3. **Terminal Charges**: Port operations and terminal fees
4. **Customs & Documentation**: Clearance and paperwork fees
5. **Dangerous Goods**: Special handling for hazardous materials
6. **Storage & Demurrage**: Warehousing and detention charges
7. **Insurance**: Cargo insurance premiums

## Next Steps

1. **Database already setup** ✅ - SQL files exist and are loaded
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Use freight cost queries** for invoice transportation charges
5. **Integrate with invoice processing workflow** for automated freight calculation

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **PaymentTermsTypeCode** - Complete (79 codes)
- ✅ **PaymentTermsDescriptionIdentifier** - Complete (7 codes)
- ✅ **MessageFunctionCode** - Complete (65 codes)
- ✅ **DutyTaxFeeTypeCode** - Complete (53 codes)
- ✅ **FreightCostCode** - Just completed (1,641 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT**: Recommendation 23, Version 4
**Use Case**: e-Tax Invoice freight and transportation charge classification
