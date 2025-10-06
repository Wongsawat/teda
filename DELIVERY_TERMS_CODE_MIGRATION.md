# Database-Backed Implementation for Delivery Terms Code

This document describes the database-backed implementation for UN/CEFACT Delivery Terms Code (Code List 64053, INCOTERMS 2010), replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/DeliveryTermsCode.java`

JPA Entity class for storing UN/CEFACT Delivery Terms Codes (INCOTERMS 2010) in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `incotermGroup`, `sellerObligation`, `isIncoterm`, timestamps
- Code normalization (trim and uppercase)
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 14 codes (2 custom + 12 INCOTERMS)

**INCOTERMS Groups**:
- **Group E** (Departure): EXW - Minimum seller obligation
- **Group F** (Main carriage unpaid): FCA, FAS, FOB - Low seller obligation
- **Group C** (Main carriage paid): CFR, CIF, CPT, CIP - Medium seller obligation
- **Group D** (Arrival): DAP, DAT, DPU, DDP - High to maximum seller obligation

**Business Logic Methods**:
- `isIncoterm()` - Check if official INCOTERM
- `isGroupE()`, `isGroupF()`, `isGroupC()`, `isGroupD()` - Check INCOTERMS group
- `hasMinimumSellerObligation()` to `hasMaximumSellerObligation()` - Check obligation level
- `includesInsurance()` - Check if includes insurance (CIF, CIP)
- `includesFreight()` - Check if includes freight payment
- `isSeaTransportOnly()` - Check if for sea/inland waterway only
- `isAnyTransportMode()` - Check if for any mode of transport

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/DeliveryTermsCodeRepository.java`

Spring Data JPA repository for database operations.

**Query Methods** (30):
- `findByCode(code)` - Find by code (case-insensitive)
- `findAllIncoterms()` - Get all INCOTERMS codes
- `findNonIncoterms()` - Get custom delivery arrangements
- `findByIncotermGroup(group)` - Get by group
- `findGroupE()` - Get Group E codes
- `findGroupF()` - Get Group F codes
- `findGroupC()` - Get Group C codes
- `findGroupD()` - Get Group D codes
- `findBySellerObligation(obligation)` - Get by obligation level
- `findMinimumSellerObligation()` - Get EXW
- `findMaximumSellerObligation()` - Get DDP
- `findWithInsurance()` - Get codes with insurance (CIF, CIP)
- `findWithFreight()` - Get codes with freight payment
- `findSeaTransportOnly()` - Get sea transport codes
- `findAnyTransportMode()` - Get any transport mode codes
- `findByNameContaining(name)` - Search by name
- `existsByCode(code)` - Check if code exists
- `findCommonIncoterms()` - Get most commonly used INCOTERMS
- `findEXW()`, `findFOB()`, `findCIF()`, `findDDP()`, `findFCA()` - Get specific INCOTERMS
- `findCFR()`, `findDAP()`, `findDPU()` - Get specific INCOTERMS
- `findAllIncotermGroups()` - Get all groups
- `findAllSellerObligationLevels()` - Get all obligation levels

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/DeliveryTermsCodeAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods (14):
  - `isValid(code)` - Validate code exists
  - `getDeliveryTermsName(code)` - Get name from code
  - `getIncotermGroup(code)` - Get INCOTERMS group
  - `getSellerObligation(code)` - Get seller obligation level
  - `isIncoterm(code)` - Check if official INCOTERM
  - `isGroupE(code)`, `isGroupF(code)`, `isGroupC(code)`, `isGroupD(code)` - Check group
  - `includesInsurance(code)` - Check if includes insurance
  - `includesFreight(code)` - Check if includes freight
  - `isSeaTransportOnly(code)` - Check if sea transport only
  - `isAnyTransportMode(code)` - Check if any transport mode

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/deliveryterms/DeliveryTermsCodeType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010`
- Helper methods: All entity business logic methods exposed
- Factory methods: `of(String)`, `of(DeliveryTermsCode)`

**File**: `src/main/java/com/wpanther/etax/xml/deliveryterms/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010`
- Prefix: `clm64053`
- Element form: QUALIFIED

### 5. Database Schema (Already Exists)

**File**: `delivery_terms_code.sql` (256 lines)

PostgreSQL table schema with:
- Table definition with 8 columns
- 3 indexes for performance (name, incoterm_group, is_incoterm)
- Auto-update timestamp trigger
- 6 views (group_e, group_f, group_c, group_d, non_incoterm, incoterm_only)
- 1 summary view (group and obligation summary)
- 4 helper functions (get name, validate, get group, get obligation level)
- Complete data for all 14 codes included in schema file

## Architecture Pattern

This implementation follows the **exact same pattern** as other code lists:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <DeliveryTermsCode>FOB</DeliveryTermsCode>                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      DeliveryTermsCodeType (XML Wrapper)                    │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      DeliveryTermsCodeAdapter (XmlAdapter)                  │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│    DeliveryTermsCodeRepository (Spring Data JPA)            │
│  - findByCode()                                             │
│  - findByIncotermGroup()                                    │
│  - findCommonIncoterms()                                    │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│       DeliveryTermsCode (JPA Entity)                        │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - INCOTERMS group classification                           │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: delivery_terms_code (14 records)                   │
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
- Access to INCOTERMS names and descriptions
- Group-based classification (E, F, C, D)
- Seller obligation levels (Minimum to Maximum)

### ✅ Business Logic Integration
- Query by INCOTERMS group
- Filter by seller obligation level
- Identify insurance/freight inclusion
- Classify by transport mode

### ✅ Query Capabilities
- Search by group (4 INCOTERMS groups)
- Filter by seller obligation (5 levels)
- Find common INCOTERMS (5 most used)
- Case-insensitive search

### ✅ Maintainability
- Centralized management in database
- Easy to add new INCOTERMS versions
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <DeliveryTermsCode>FOB</DeliveryTermsCode>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access delivery terms details
DeliveryTermsCodeType deliveryTerms = invoice.getDeliveryTermsCode();
String code = deliveryTerms.getCode();                          // "FOB"
String name = deliveryTerms.getName();                          // "Free On Board..."
String group = deliveryTerms.getIncotermGroup();                // "F"
String obligation = deliveryTerms.getSellerObligation();        // "Low"
boolean isIncoterm = deliveryTerms.isIncoterm();                // true
boolean isGroupF = deliveryTerms.isGroupF();                    // true
boolean includesInsurance = deliveryTerms.includesInsurance();  // false
boolean isSeaOnly = deliveryTerms.isSeaTransportOnly();         // true
```

### XML Marshalling

```java
// Create from entity
DeliveryTermsCode entity = repository.findByCode("FOB").get();
DeliveryTermsCodeType deliveryTerms = DeliveryTermsCodeType.of(entity);

// Or create from code string
DeliveryTermsCodeType deliveryTerms = DeliveryTermsCodeType.of("CIF");

// Set in invoice
invoice.setDeliveryTermsCode(deliveryTerms);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <DeliveryTermsCode>CIF</DeliveryTermsCode>
```

### Validation

```java
// Check if code is valid
if (DeliveryTermsCodeAdapter.isValid("FOB")) {
    // Code exists
}

// Get delivery terms name
String name = DeliveryTermsCodeAdapter.getDeliveryTermsName("FOB");
// Returns: "Free On Board (insert named port of shipment)"

// Get INCOTERMS group
String group = DeliveryTermsCodeAdapter.getIncotermGroup("FOB");
// Returns: "F"

// Get seller obligation
String obligation = DeliveryTermsCodeAdapter.getSellerObligation("FOB");
// Returns: "Low"

// Check INCOTERMS properties
boolean isIncoterm = DeliveryTermsCodeAdapter.isIncoterm("FOB");
// Returns: true

boolean isGroupF = DeliveryTermsCodeAdapter.isGroupF("FOB");
// Returns: true

boolean includesInsurance = DeliveryTermsCodeAdapter.includesInsurance("CIF");
// Returns: true

boolean isSeaOnly = DeliveryTermsCodeAdapter.isSeaTransportOnly("FOB");
// Returns: true
```

### Repository Queries

```java
@Autowired
private DeliveryTermsCodeRepository repository;

// Get specific INCOTERM
Optional<DeliveryTermsCode> fob = repository.findFOB();
Optional<DeliveryTermsCode> cif = repository.findCIF();
Optional<DeliveryTermsCode> exw = repository.findEXW();

// Get all INCOTERMS
List<DeliveryTermsCode> allIncoterms = repository.findAllIncoterms();

// Get by group
List<DeliveryTermsCode> groupF = repository.findGroupF();
// Returns: FCA, FAS, FOB

List<DeliveryTermsCode> groupC = repository.findGroupC();
// Returns: CFR, CIF, CPT, CIP

// Get with insurance
List<DeliveryTermsCode> withInsurance = repository.findWithInsurance();
// Returns: CIF, CIP

// Get common INCOTERMS
List<DeliveryTermsCode> common = repository.findCommonIncoterms();
// Returns: EXW, FOB, CIF, DDP, FCA

// Get by seller obligation
Optional<DeliveryTermsCode> minimum = repository.findMinimumSellerObligation();
// Returns: EXW

Optional<DeliveryTermsCode> maximum = repository.findMaximumSellerObligation();
// Returns: DDP

// Get sea transport only
List<DeliveryTermsCode> seaOnly = repository.findSeaTransportOnly();
// Returns: FAS, FOB, CFR, CIF
```

## Database Setup

```sql
-- 1. Create schema and load data (already done - data included in schema file)
\i delivery_terms_code.sql

-- 2. Verify
SELECT COUNT(*) FROM delivery_terms_code;
-- Expected: 14

SELECT COUNT(*) FROM delivery_terms_code WHERE is_incoterm = true;
-- Expected: 12

SELECT COUNT(DISTINCT incoterm_group) FROM delivery_terms_code WHERE incoterm_group IS NOT NULL;
-- Expected: 4 (E, F, C, D)

-- 3. Query examples
SELECT * FROM delivery_terms_code WHERE code = 'FOB';
SELECT * FROM delivery_terms_incoterm_group_f;
SELECT * FROM delivery_terms_incoterm_only;
SELECT * FROM delivery_terms_code_summary;

-- 4. Use functions
SELECT get_delivery_terms_name('FOB');
SELECT is_valid_delivery_terms_code('FOB');
SELECT get_incoterm_group('FOB');
SELECT get_seller_obligation_level('FOB');
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "FOB";  // Just a string, could be invalid
JAXBElement<String> deliveryTerms = objectFactory.createDeliveryTermsCode(code);
// No way to know if "FOB" is valid
// No way to get the name, group, or obligation level
// No business logic integration
```

### After (Database-Backed)

```java
// Database-backed: DeliveryTermsCodeType with full entity
DeliveryTermsCodeType deliveryTerms = DeliveryTermsCodeType.of("FOB");
// Validates against database
// Full access to metadata

String code = deliveryTerms.getCode();                  // "FOB"
String name = deliveryTerms.getName();                  // "Free On Board..."
String group = deliveryTerms.getIncotermGroup();        // "F"
String obligation = deliveryTerms.getSellerObligation(); // "Low"
boolean isIncoterm = deliveryTerms.isIncoterm();        // true
boolean isGroupF = deliveryTerms.isGroupF();            // true
boolean includesInsurance = deliveryTerms.includesInsurance(); // false
boolean isSeaOnly = deliveryTerms.isSeaTransportOnly(); // true
```

## Comparison with Other Code Lists

| Feature | Delivery Terms | Payment Terms Type | Message Function |
|---------|----------------|-------------------|------------------|
| **Total Codes** | 14 | 79 | 65 |
| **Code Format** | Mixed (1,2,CFR,CIF) | Numeric + ZZZ | Numeric 1-65 |
| **JAXB Original** | String | String | String |
| **Categories** | ✅ 4 groups (E/F/C/D) | ✅ 9 categories | ✅ 5 categories |
| **Classification** | ✅ Seller obligation | ✅ Payment timing | ✅ Transaction type |
| **Entity** | ✅ | ✅ | ✅ |
| **Repository** | ✅ 30 methods | ✅ 13 methods | ✅ 25 methods |
| **Adapter** | ✅ 14 helpers | ✅ 6 helpers | ✅ 10 helpers |
| **XML Wrapper** | ✅ | ✅ | ✅ |
| **Package-info** | ✅ | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ | ✅ |

## INCOTERMS 2010 - All Codes

| Code | Name | Group | Seller Obligation | Insurance | Freight | Transport Mode |
|------|------|-------|-------------------|-----------|---------|----------------|
| **1** | Delivery by supplier | - | - | - | - | Custom |
| **2** | Delivery by LSP | - | - | - | - | Custom |
| **EXW** | Ex Works | E | Minimum | ❌ | ❌ | Any |
| **FCA** | Free Carrier | F | Low | ❌ | ❌ | Any |
| **FAS** | Free Alongside Ship | F | Low | ❌ | ❌ | Sea only |
| **FOB** | Free On Board | F | Low | ❌ | ❌ | Sea only |
| **CFR** | Cost and Freight | C | Medium | ❌ | ✅ | Sea only |
| **CIF** | Cost, Insurance, Freight | C | Medium | ✅ | ✅ | Sea only |
| **CPT** | Carriage Paid To | C | Medium | ❌ | ✅ | Any |
| **CIP** | Carriage, Insurance Paid | C | Medium | ✅ | ✅ | Any |
| **DAP** | Delivered At Place | D | High | ❌ | ✅ | Any |
| **DAT** | Delivered At Terminal | D | High | ❌ | ✅ | Any |
| **DPU** | Delivered Place Unloaded | D | High | ❌ | ✅ | Any |
| **DDP** | Delivered Duty Paid | D | Maximum | ❌ | ✅ | Any |

## e-Tax Invoice Use Cases

Delivery Terms Codes (INCOTERMS) are used in e-Tax Invoice to define:

1. **EXW (Ex Works)**: Buyer collects from seller's premises - minimal seller responsibility
2. **FOB (Free On Board)**: Common for sea freight - seller delivers to ship
3. **CIF (Cost, Insurance, Freight)**: Popular for international trade - seller pays shipping and insurance
4. **DDP (Delivered Duty Paid)**: Maximum seller responsibility - delivered to buyer's door
5. **FCA (Free Carrier)**: Flexible for any transport mode - seller delivers to carrier

## Next Steps

1. **Database already setup** ✅ - SQL schema with data included
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Use INCOTERMS queries** for invoice delivery term validation
5. **Integrate with invoice processing workflow** for automated INCOTERMS handling

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **PaymentTermsTypeCode** - Complete (79 codes)
- ✅ **PaymentTermsDescriptionIdentifier** - Complete (7 codes)
- ✅ **MessageFunctionCode** - Complete (65 codes)
- ✅ **DutyTaxFeeTypeCode** - Complete (53 codes)
- ✅ **FreightCostCode** - Complete (1,641 codes)
- ✅ **DeliveryTermsCode** - Just completed (14 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT Code List**: 64053 (Delivery Terms Code)
**Standard**: INCOTERMS 2010 (with INCOTERMS 2020 updates)
**Use Case**: e-Tax Invoice delivery terms and international trade responsibilities
