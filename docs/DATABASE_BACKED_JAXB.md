# Database-Backed JAXB Implementation

This document explains how to convert JAXB-generated enum classes to use database-backed entities while maintaining full XML marshalling/unmarshalling compatibility with exact namespace preservation.

## Problem

JAXB generates Java enums from XSD enumeration types. This works well for small code lists but has limitations:

1. **Size Limit**: JAXB cannot generate enums with > 256 values (e.g., 798 reference types, 8,940 subdivisions)
2. **Inflexibility**: Enum values are hardcoded at compile time
3. **No Metadata**: Cannot store additional information (descriptions, translations, active status)
4. **No Updates**: Cannot update code lists without regenerating and recompiling

## Solution: Database-Backed XmlAdapter

Use `XmlAdapter` to bridge between XML and database while maintaining JAXB compatibility.

## Architecture

```
XML Document
    ↕ (JAXB Marshal/Unmarshal)
Custom JAXB Type (ISOTwoletterCountryCodeType)
    ↕ (@XmlJavaTypeAdapter)
XmlAdapter (ISOCountryCodeAdapter)
    ↕ (Database Query)
JPA Entity (ISOCountryCode)
    ↕ (Spring Data JPA)
PostgreSQL Database
```

## Components

### 1. JPA Entity (`ISOCountryCode.java`)

Database entity with metadata:

```java
@Entity
@Table(name = "iso_country_code")
public class ISOCountryCode {
    @Id
    private String code;           // "TH"
    private String name;           // "THAILAND"
    private String description;    // Full description
    private Boolean thaiExtension; // Thai-specific codes
    private Boolean active;        // Active status
}
```

### 2. Spring Data Repository (`ISOCountryCodeRepository.java`)

Database access layer:

```java
@Repository
public interface ISOCountryCodeRepository extends JpaRepository<ISOCountryCode, String> {
    Optional<ISOCountryCode> findByCodeAndActive(String code);
    List<ISOCountryCode> findByActiveTrue();
    // ... other query methods
}
```

### 3. XmlAdapter (`ISOCountryCodeAdapter.java`)

Converts between XML strings and database entities:

```java
@Component
public class ISOCountryCodeAdapter extends XmlAdapter<String, ISOCountryCode> {

    @Override
    public String marshal(ISOCountryCode entity) {
        // Entity -> XML: Return country code string
        return entity != null ? entity.getCode() : null;
    }

    @Override
    public ISOCountryCode unmarshal(String code) {
        // XML -> Entity: Fetch from database
        return repository.findByCodeAndActive(code)
                .orElseGet(() -> createPlaceholder(code));
    }
}
```

### 4. Custom JAXB Type (`ISOTwoletterCountryCodeType.java`)

Replaces the generated enum:

```java
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISOTwoletterCountryCodeContentType",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
public class ISOTwoletterCountryCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOCountryCodeAdapter.class)
    private ISOCountryCode value;

    // Getters, setters, convenience methods...
}
```

### 5. Namespace Configuration (`package-info.java`)

Preserves exact namespace prefix as per ETDA schema:

```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "ids5ISO316612A",
            namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006"
        )
    }
)
package com.wpanther.etax.core.xml.country;
```

## Usage Examples

### Marshal to XML

```java
// Fetch from database
ISOCountryCode thailand = repository.findByCodeAndActive("TH").get();

// Create JAXB type
ISOTwoletterCountryCodeType countryType = ISOTwoletterCountryCodeType.of(thailand);

// Marshal to XML
JAXBContext context = JAXBContext.newInstance(ISOTwoletterCountryCodeType.class);
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(countryType, outputStream);
```

**Output XML:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ids5ISO316612A:ISOTwoletterCountryCodeType
    xmlns:ids5ISO316612A="urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006">
    TH
</ids5ISO316612A:ISOTwoletterCountryCodeType>
```

### Unmarshal from XML

```java
// XML input
String xml = "<ids5ISO316612A:ISOTwoletterCountryCodeType>US</ids5ISO316612A:ISOTwoletterCountryCodeType>";

// Unmarshal
JAXBContext context = JAXBContext.newInstance(ISOTwoletterCountryCodeType.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
ISOTwoletterCountryCodeType countryType =
    (ISOTwoletterCountryCodeType) unmarshaller.unmarshal(new StringReader(xml));

// Access database entity
ISOCountryCode country = countryType.getValue();
System.out.println(country.getName()); // "UNITED STATES"
```

## Benefits

### ✅ Scalability
- Works with any number of codes (798, 8,940, or millions)
- No JVM enum size limitations

### ✅ Flexibility
- Add/update codes via database without recompiling
- Store rich metadata (descriptions, translations, effective dates)
- Active/inactive status management

### ✅ Full JAXB Compatibility
- Exact namespace preservation
- Same XML structure as generated enums
- Works with existing JAXB tools and frameworks

### ✅ Performance
- Repository caching reduces database queries
- Lazy loading for large datasets
- Batch operations support

### ✅ Maintainability
- Centralized code list management in database
- Easy to extend with additional fields
- Standard JPA/Spring patterns

## Comparison: Generated Enum vs Database-Backed

| Feature | Generated Enum | Database-Backed |
|---------|---------------|-----------------|
| Max Size | 256 values | Unlimited |
| Metadata | None | Rich (description, dates, flags) |
| Updates | Requires regeneration | Database update only |
| Memory | All loaded at startup | Lazy/cached loading |
| Validation | Compile-time | Runtime (database) |
| Namespace | ✅ Preserved | ✅ Preserved |
| XML Output | ✅ Compatible | ✅ Compatible |
| Complexity | Simple | Moderate |
| Best For | Small static lists | Large dynamic lists |

## When to Use Each Approach

### Use Generated Enum When:
- Code list has < 100 values
- Values never change (ISO standards)
- No metadata needed
- Compile-time validation preferred
- Simplicity is priority

### Use Database-Backed When:
- Code list has > 256 values (JAXB limit)
- Frequent updates expected
- Need rich metadata (descriptions, translations)
- Runtime updates required
- Managing active/inactive codes
- Multiple environments need different code lists

## Migration Path

For Thai e-Tax Invoice schema, **20 code lists** are database-backed:

| Code List | Count | Approach | Reason |
|-----------|-------|----------|--------|
| ISO Country Code | 252 | Database | Frequent updates, Thai extensions |
| ISO Currency Code | 180+ | Database | Dynamic, Thai-specific |
| ISO Language Code | 180+ | Database | Dynamic, Thai-specific |
| UNECE Reference Type Code | 798 | Database | Exceeds enum limit |
| UNECE Document Name Code (Invoice) | 17 | Database | Shared by all documents |
| UNECE Freight Cost Code | 66 | Database | Shipping logistics |
| TISI Subdistrict (Thai) | 8,940 | Database | Far exceeds enum limit |
| Thai Province Code | 77 | Database | Thai administrative regions |
| Thai Document Name Code | 12 | Database | Thai-specific document types |
| Thai Message Function Code | 25 | Database | Thai message functions |
| ... (10 more) | ... | Database | Various Thai-specific codes |

## Database Schema

See `iso_country_code.sql` for complete schema with:
- Primary key on code
- Indexes for performance
- Full-text search support
- Helper functions for validation
- Audit timestamps
- Thai extension flag

## Next Steps

1. **Load Data**: Execute `iso_country_code_data.sql` to populate database
2. **Configure Spring Boot**: Set up `application.properties` with database connection
3. **Initialize Repository**: Ensure `@ComponentScan` includes adapter package
4. **Test**: Run `CountryCodeXmlExample` to verify marshalling/unmarshalling
5. **Apply Pattern**: Extend to other large code lists (Reference Types, Subdivisions)

## Files Created

```
src/main/java/com/wpanther/etax/core/
├── entity/
│   └── ISOCountryCode.java              # JPA entity
├── repository/
│   └── ISOCountryCodeRepository.java    # Spring Data repository
├── adapter/
│   └── common/
│       └── ISOCountryCodeAdapter.java   # JAXB XmlAdapter
├── xml/
│   └── country/
│       ├── ISOTwoletterCountryCodeType.java # Custom JAXB type
│       └── package-info.java                # Namespace configuration
└── [20 code lists with this pattern]
```

## Configuration Required

### application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/etax
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

### Spring Boot Application
```java
@SpringBootApplication
@ComponentScan(basePackages = "com.wpanther.etax")
public class ETaxApplication {
    public static void main(String[] args) {
        SpringApplication.run(ETaxApplication.class, args);
    }
}
```

## Testing

See `CountryCodeXmlExample.java` for complete examples demonstrating:
1. Marshal entity to XML
2. Unmarshal XML to entity
3. Round-trip conversion
4. Namespace preservation
5. Database lookup during unmarshalling

## Conclusion

This approach provides the best of both worlds:
- **Database flexibility** for managing large, dynamic code lists
- **JAXB compatibility** for seamless XML integration
- **Full namespace preservation** as required by ETDA schema
- **Production-ready** with proper error handling and logging

**Status**: This pattern has been successfully applied to **20 code lists** in this library:
- UNECE Reference Type Codes (798 codes)
- Thai Subdivisions (8,940 codes)
- ISO Country Codes (252 codes)
- UNECE Document Name Code Invoice (17 codes)
- UNECE Freight Cost Codes (66 codes)
- ... (15 more)

All 20 code lists share the same adapter pattern in `src/main/java/com/wpanther/etax/core/adapter/common/` and are shared across TaxInvoice, Receipt, DebitCreditNote, CancellationNote, AbbreviatedTaxInvoice, and Invoice documents.
