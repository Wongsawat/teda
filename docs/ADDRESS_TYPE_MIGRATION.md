# AddressType Migration to Database-Backed Implementation

## Overview

This document describes the migration of UN/CEFACT Address Type Code (Code List 3131, Version D14A) from JAXB-generated code to a database-backed implementation using JPA entities and Spring Data repositories.

**UN/CEFACT Code List**: 3131
**Schema Version**: D14A (15 Nov 2014)
**Total Codes**: 3
**Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A`
**Prefix**: `clm63131`

## Code Values

This is one of the simplest code lists with only **3 address type classifications**:

| Code | Name | Description | Usage |
|------|------|-------------|-------|
| **1** | Postal address | The address is representing a postal address | Mailing and delivery addresses |
| **2** | Fiscal address | Identification of an address as required by fiscal administrations | Tax registration addresses |
| **3** | Physical address | The address represents an actual physical location | Business premises, warehouses |

## Files Created

### 1. Entity (`AddressType.java`)
**Location**: `src/main/java/com/wpanther/etax/entity/AddressType.java`

JPA entity representing address type codes with:
- Primary key: `code` (VARCHAR 2)
- Fields: `name`, `description`, `createdAt`, `updatedAt`
- Code normalization (trim)
- 3 business logic methods:
  - `isPostalAddress()` - Check if code is 1
  - `isFiscalAddress()` - Check if code is 2
  - `isPhysicalAddress()` - Check if code is 3

### 2. Repository (`AddressTypeRepository.java`)
**Location**: `src/main/java/com/wpanther/etax/repository/AddressTypeRepository.java`

Spring Data JPA repository with **5 query methods**:

**Basic Queries**:
- `findByCode(code)` - Find by code
- `existsByCode(code)` - Check existence

**Specific Type Queries**:
- `findPostalAddress()` - Get postal address type (code 1)
- `findFiscalAddress()` - Get fiscal address type (code 2)
- `findPhysicalAddress()` - Get physical address type (code 3)

### 3. XmlAdapter (`AddressTypeAdapter.java`)
**Location**: `src/main/java/com/wpanther/etax/adapter/AddressTypeAdapter.java`

JAXB adapter converting between XML strings and database entities:
- `marshal(entity)` - Convert entity → XML string (code)
- `unmarshal(code)` - Convert XML string → entity (database lookup)
- Placeholder creation for unknown codes
- **6 static helper methods**:
  - `isValid(code)` - Validate code exists
  - `getName(code)` - Get name from code
  - `getDescription(code)` - Get description from code
  - `isPostalAddress(code)` - Check if postal (1)
  - `isFiscalAddress(code)` - Check if fiscal (2)
  - `isPhysicalAddress(code)` - Check if physical (3)

### 4. XML Type (`AddressTypeType.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/addresstype/AddressTypeType.java`

Custom JAXB type replacing generated JAXBElement<String> with database-backed implementation:
- `@XmlValue` with `@XmlJavaTypeAdapter(AddressTypeAdapter.class)`
- Constructors: default, from entity, from code string
- Getters: `getCode()`, `getName()`, `getDescription()`
- Business logic methods (3 type checks)
- Factory methods: `of(String)`, `of(AddressType)`
- Custom `toString()` with code and name

### 5. Package Configuration (`package-info.java`)
**Location**: `src/main/java/com/wpanther/etax/xml/addresstype/package-info.java`

JAXB namespace configuration:
```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm63131",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A"
        )
    }
)
```

## Architecture Pattern

```
┌─────────────────────────────────────────────────────────────────┐
│                         XML Document                             │
│  <AddressType>2</AddressType>                                   │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JAXB Unmarshalling
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│              AddressTypeAdapter (XmlAdapter)                     │
│  • unmarshal("2") → database lookup → entity                    │
│  • marshal(entity) → "2"                                        │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ Database Lookup
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│          AddressTypeRepository (Spring Data JPA)                 │
│  • findByCode("2") → Optional<AddressType>                      │
│  • findFiscalAddress() → Optional<AddressType>                  │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ JPA Query
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                           │
│  Table: address_type                                             │
│  • code (PK): "2"                                               │
│  • name: "Fiscal address"                                       │
│  • description: "Identification of an address as required..."   │
│                                                                  │
│  Index:                                                          │
│  • idx_address_type_name                                        │
│                                                                  │
│  Helper Functions:                                               │
│  • get_address_type_name(code)                                  │
│  • is_valid_address_type(code)                                  │
└─────────────────────────────────────────────────────────────────┘
```

## Benefits

### 1. Type Safety
- Compile-time type checking
- No invalid codes at runtime
- IDE autocomplete support

### 2. Rich Metadata
- **Name**: Human-readable type name
- **Description**: Detailed explanation
- Clear distinction between postal, fiscal, and physical addresses

### 3. Business Logic
- **3 type check methods** on entity and type
- Easy validation: `entity.isFiscalAddress()`
- Type-based processing logic

### 4. Query Capabilities
- Find by specific type: `repository.findFiscalAddress()`
- Validation: `repository.existsByCode("2")`

### 5. Maintainability
- Centralized code management
- Database-driven configuration
- Easy to extend if new types are added

### 6. Validation
- Database constraints ensure data integrity
- Repository methods for existence checking
- Adapter creates placeholders for unknown codes

## Database Setup

### 1. Create Table and Load Data
```bash
psql -U username -d database_name -f address_type.sql
```

The SQL file creates the table and inserts all 3 codes in one step.

### 2. Verify Installation
```sql
-- Check total codes (should be 3)
SELECT COUNT(*) FROM address_type;

-- View all codes
SELECT * FROM address_type ORDER BY code;

-- Test helper function
SELECT get_address_type_name('2');  -- Returns: Fiscal address

-- Test validation function
SELECT is_valid_address_type('2');  -- Returns: true
SELECT is_valid_address_type('9');  -- Returns: false
```

## Usage Examples

### 1. XML Unmarshalling (Reading XML)

**XML Input**:
```xml
<AddressType>2</AddressType>
```

**Java Code**:
```java
// JAXB automatically uses AddressTypeAdapter
AddressTypeType typeCode = // ... from JAXB unmarshalling

// Access database-backed entity
AddressType entity = typeCode.getValue();

System.out.println(entity.getCode());        // "2"
System.out.println(entity.getName());        // "Fiscal address"
System.out.println(entity.getDescription()); // "Identification of an address as required by fiscal administrations."

// Business logic
if (typeCode.isFiscalAddress()) {
    System.out.println("This is a fiscal address required for tax purposes");
}
```

### 2. XML Marshalling (Writing XML)

```java
// Create from code string
AddressTypeType typeCode = AddressTypeType.of("2");

// Create from entity
AddressType entity = repository.findFiscalAddress().orElseThrow();
AddressTypeType typeCode = AddressTypeType.of(entity);

// JAXB marshalling produces:
// <AddressType>2</AddressType>
```

### 3. Validation

```java
// Check if code exists
if (AddressTypeAdapter.isValid("2")) {
    System.out.println("Valid address type code");
}

// Get type name
String name = AddressTypeAdapter.getName("2");
// Returns: "Fiscal address"

// Check specific type
if (AddressTypeAdapter.isFiscalAddress("2")) {
    System.out.println("This is a fiscal address");
}
```

### 4. Repository Queries

```java
@Autowired
private AddressTypeRepository repository;

// Find by code
Optional<AddressType> type = repository.findByCode("2");

// Find specific types
Optional<AddressType> postal = repository.findPostalAddress();      // Code 1
Optional<AddressType> fiscal = repository.findFiscalAddress();      // Code 2
Optional<AddressType> physical = repository.findPhysicalAddress();  // Code 3

// Validate code
boolean exists = repository.existsByCode("2");  // true
boolean invalid = repository.existsByCode("9"); // false
```

### 5. Entity Business Logic

```java
AddressType type = repository.findByCode("2").orElseThrow();

// Type checks
type.isFiscalAddress();     // true (code 2)
type.isPostalAddress();     // false
type.isPhysicalAddress();   // false

// Access metadata
String name = type.getName();              // "Fiscal address"
String desc = type.getDescription();       // "Identification of an address as required..."
```

## Migration from JAXB

### Before (JAXB Generated - JAXBElement Pattern)

```java
// JAXB generated ObjectFactory
public class ObjectFactory {
    @XmlElementDecl(namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A",
                    name = "AddressType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAddressType(String value) {
        return new JAXBElement<String>(_AddressType_QNAME, String.class, null, value);
    }
}

// Usage
JAXBElement<String> element = objectFactory.createAddressType("2");
String value = element.getValue(); // Just "2" - no metadata!

// No validation, no type info, no business logic
```

### After (Database-Backed)

```java
// Custom type with database lookup
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressTypeContentType")
public class AddressTypeType {
    @XmlValue
    @XmlJavaTypeAdapter(AddressTypeAdapter.class)
    private AddressType value;

    // Rich metadata and business logic
    public String getCode() { return value.getCode(); }
    public String getName() { return value.getName(); }
    public String getDescription() { return value.getDescription(); }

    public boolean isPostalAddress() { return value.isPostalAddress(); }
    public boolean isFiscalAddress() { return value.isFiscalAddress(); }
    public boolean isPhysicalAddress() { return value.isPhysicalAddress(); }
}

// Usage
AddressTypeType type = AddressTypeType.of("2");
String code = type.getCode();              // "2"
String name = type.getName();              // "Fiscal address"
String desc = type.getDescription();       // "Identification of an address as required by fiscal administrations."
boolean isFiscal = type.isFiscalAddress(); // true

// Full validation and business logic available!
```

## e-Tax Invoice Use Cases

### 1. Seller Fiscal Address (Tax Registration)
```java
// Use fiscal address for seller's tax registration address
AddressTypeType addressType = AddressTypeType.of("2");

PartyTaxSchemeType sellerTaxScheme = new PartyTaxSchemeType();
AddressType fiscalAddress = new AddressType();
fiscalAddress.setAddressTypeCode(addressType);
fiscalAddress.setBuildingNumber("123");
fiscalAddress.setStreetName("Silom Road");
fiscalAddress.setCityName("Bangkok");
fiscalAddress.setPostalZone("10500");

// Verify it's fiscal address
if (addressType.isFiscalAddress()) {
    System.out.println("This is the official tax registration address");
}
```

### 2. Buyer Postal Address (Delivery)
```java
// Use postal address for delivery location
AddressTypeType addressType = AddressTypeType.of("1");

AddressType deliveryAddress = new AddressType();
deliveryAddress.setAddressTypeCode(addressType);
deliveryAddress.setBuildingNumber("456");
deliveryAddress.setStreetName("Sukhumvit Road");
deliveryAddress.setCityName("Bangkok");
deliveryAddress.setPostalZone("10110");

// Verify it's postal address
if (addressType.isPostalAddress()) {
    System.out.println("This is a postal/delivery address");
}
```

### 3. Physical Business Location
```java
// Use physical address for actual business premises
AddressTypeType addressType = AddressTypeType.of("3");

AddressType businessLocation = new AddressType();
businessLocation.setAddressTypeCode(addressType);
businessLocation.setBuildingNumber("789");
businessLocation.setStreetName("Rama IV Road");
businessLocation.setCityName("Bangkok");
businessLocation.setPostalZone("10400");

// Verify it's physical address
if (addressType.isPhysicalAddress()) {
    System.out.println("This is the actual physical business location");
}
```

### 4. Multi-Address Invoice
```java
// An invoice can have different address types for different purposes

// Seller's fiscal address (required by tax authority)
AddressType sellerFiscalAddr = new AddressType();
sellerFiscalAddr.setAddressTypeCode(AddressTypeType.of("2"));
// ... set fiscal address details

// Buyer's postal address (for delivery)
AddressType buyerPostalAddr = new AddressType();
buyerPostalAddr.setAddressTypeCode(AddressTypeType.of("1"));
// ... set delivery address details

// Physical location (warehouse/branch)
AddressType warehouseAddr = new AddressType();
warehouseAddr.setAddressTypeCode(AddressTypeType.of("3"));
// ... set warehouse location details
```

## Common Scenarios

### Postal Address (Code 1)
- **Purpose**: Mailing and delivery
- **Use Cases**:
  - Customer delivery addresses
  - Invoice mailing addresses
  - Correspondence addresses
  - PO Box addresses

### Fiscal Address (Code 2)
- **Purpose**: Tax registration and compliance
- **Use Cases**:
  - Seller's tax registration address
  - Buyer's tax registration address
  - Address required by Revenue Department
  - Official business address for tax purposes

### Physical Address (Code 3)
- **Purpose**: Actual physical location
- **Use Cases**:
  - Business premises
  - Warehouse locations
  - Branch offices
  - Manufacturing facilities
  - Store locations

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
| **AddressType** | ✅ **Migrated** | **3 address types** |

## Summary

The AddressType migration provides:
- ✅ **3 address type codes** (postal, fiscal, physical)
- ✅ **5 repository query methods** for data access
- ✅ **6 static helper methods** for validation and lookup
- ✅ **3 business logic methods** for type checking
- ✅ **Complete JAXB compatibility** with original schema
- ✅ **Type-safe** address classification
- ✅ **Rich metadata** for proper address handling

This implementation enables proper address classification in e-Tax Invoice documents with clear distinction between postal addresses (delivery), fiscal addresses (tax compliance), and physical addresses (business locations) for Thai e-Tax Invoice compliance.
