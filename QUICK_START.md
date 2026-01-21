# Quick Start Guide - Database-Backed JAXB

This guide shows how to run the database-backed JAXB implementation for Thai e-Tax Invoice codes.

## Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Database created and loaded with data

## Step 1: Setup Database

```bash
# Create database
psql -U postgres -c "CREATE DATABASE etax;"

# Load schema
psql -U postgres -d etax -f iso_country_code.sql

# Load data
psql -U postgres -d etax -f iso_country_code_data.sql

# Verify
psql -U postgres -d etax -c "SELECT COUNT(*) FROM iso_country_code;"
# Should show 252 rows (250 ISO + 2 Thai extensions)
```

## Step 2: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/etax
spring.datasource.username=postgres
spring.datasource.password=your_actual_password
```

## Step 3: Build Project

```bash
# Clean and compile
mvn clean compile

# This will:
# - Download dependencies (Spring Boot, JPA, PostgreSQL, JAXB)
# - Compile JPA entities
# - Compile JAXB adapters
# - Compile custom JAXB types
```

## Step 4: Run Example

```bash
# Run the example application
mvn spring-boot:run

# Note: This is a library - add to your project as a dependency
# See README.md for usage examples
```

## Expected Output

```
========================================
ISO Country Code XML Example
========================================

Example 1: Marshal to XML
--------------------------
Marshalled XML:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ids5ISO316612A:ISOTwoletterCountryCodeType
    xmlns:ids5ISO316612A="urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006">
    TH
</ids5ISO316612A:ISOTwoletterCountryCodeType>

Example 2: Unmarshal from XML
------------------------------
Unmarshalled country:
  Code: US
  Name: UNITED STATES
  Thai Extension: false
  Active: true

Example 3: Round-trip Test
--------------------------
Original: ISOCountryCode{code='JP', name='JAPAN', thaiExtension=false, active=true}

XML:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ids5ISO316612A:ISOTwoletterCountryCodeType>JP</ids5ISO316612A:ISOTwoletterCountryCodeType>

Restored: ISOCountryCode{code='JP', name='JAPAN', thaiExtension=false, active=true}

Round-trip successful: true
```

## File Structure

```
src/main/java/com/wpanther/etax/core/
├── entity/
│   └── ISOCountryCode.java                 # JPA entity (database model)
├── repository/
│   └── ISOCountryCodeRepository.java       # Spring Data repository
├── adapter/
│   └── common/
│       └── ISOCountryCodeAdapter.java      # JAXB XmlAdapter (XML ↔ Database)
├── xml/
│   └── country/
│       ├── ISOTwoletterCountryCodeType.java # Custom JAXB type (replaces enum)
│       └── package-info.java               # Namespace configuration
└── [20 code lists with this pattern]

src/main/java/com/wpanther/etax/validation/  # Schematron validation module
├── SchematronValidator.java                # Validation interface
├── SchematronValidatorImpl.java            # Implementation
├── DocumentSchematron.java                 # Document type enum
├── SchematronValidationResult.java         # Result model
├── SchematronError.java                    # Error model
└── SchematronValidationException.java      # Exception

src/main/resources/
├── application.properties                   # Spring Boot configuration
├── jaxb-bindings-taxinvoice.xjb            # TaxInvoice JAXB binding config
├── jaxb-bindings-receipt.xjb               # Receipt JAXB binding config
├── jaxb-bindings-debitcreditnote.xjb       # DebitCreditNote JAXB binding config
├── jaxb-bindings-cancellationnote.xjb      # CancellationNote JAXB binding config
├── jaxb-bindings-abbreviatedtaxinvoice.xjb # AbbreviatedTaxInvoice JAXB binding config
├── jaxb-bindings-invoice.xjb               # Generic Invoice JAXB binding config
└── db/                                     # Database migration scripts (42 files)
    ├── iso_country_code.sql
    ├── iso_country_code_data.sql
    └── [40 more SQL files]

target/generated-sources/jaxb/              # Generated JAXB classes (748+ classes)
└── com/wpanther/etax/generated/
    ├── common/                             # Shared UDT/QDT classes (116 classes)
    ├── taxinvoice/                         # TaxInvoice RSM/RAM classes (~80 classes)
    ├── receipt/                            # Receipt RSM/RAM classes (~76 classes)
    ├── debitcreditnote/                    # DebitCreditNote RSM/RAM classes (~76 classes)
    ├── cancellationnote/                   # CancellationNote RSM/RAM classes (~74 classes)
    ├── abbreviatedtaxinvoice/              # AbbreviatedTaxInvoice RSM/RAM classes (~80 classes)
    └── invoice/                            # Generic Invoice RSM/RAM/QDT classes (~146 classes)
```

## How It Works

### Original Approach (Generated Enum)
```
XSD → JAXB → 3,324-line Enum (hardcoded values)
```
**Problem**: Can't handle > 256 values, no database, no updates

### New Approach (Database-Backed)
```
XSD → Database Schema → JPA Entity → XmlAdapter → Custom JAXB Type → XML
```
**Benefits**: Unlimited values, database storage, runtime updates, full JAXB compatibility

### Key Components

1. **JPA Entity** (`ISOCountryCode`)
   - Stores country codes in PostgreSQL
   - Rich metadata (name, description, Thai extension flag, active status)

2. **Spring Data Repository** (`ISOCountryCodeRepository`)
   - Database queries with caching
   - Efficient lookups by code

3. **XmlAdapter** (`ISOCountryCodeAdapter`)
   - Converts database entities ↔ XML strings
   - Transparent to JAXB marshaller/unmarshaller

4. **Custom JAXB Type** (`ISOTwoletterCountryCodeType`)
   - Replaces generated enum
   - Uses `@XmlJavaTypeAdapter` to delegate to adapter
   - Preserves exact namespace as per ETDA schema

5. **Namespace Configuration** (`package-info.java`)
   - Configures XML namespace prefix: `ids5ISO316612A`
   - Ensures output matches ETDA requirements exactly

## Testing Marshalling

```java
// Fetch from database
ISOCountryCode thailand = repository.findByCodeAndActive("TH").get();

// Create JAXB type
ISOTwoletterCountryCodeType country = ISOTwoletterCountryCodeType.of(thailand);

// Marshal to XML
JAXBContext context = JAXBContext.newInstance(ISOTwoletterCountryCodeType.class);
Marshaller marshaller = context.createMarshaller();
marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
marshaller.marshal(country, System.out);
```

**Output:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ids5ISO316612A:ISOTwoletterCountryCodeType
    xmlns:ids5ISO316612A="urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006">
    TH
</ids5ISO316612A:ISOTwoletterCountryCodeType>
```

## Testing Unmarshalling

```java
String xml = "<ids5ISO316612A:ISOTwoletterCountryCodeType>JP</ids5ISO316612A:ISOTwoletterCountryCodeType>";

JAXBContext context = JAXBContext.newInstance(ISOTwoletterCountryCodeType.class);
Unmarshaller unmarshaller = context.createUnmarshaller();

ISOTwoletterCountryCodeType country =
    (ISOTwoletterCountryCodeType) unmarshaller.unmarshal(new StringReader(xml));

// Access database-backed entity
System.out.println(country.getName()); // Fetched from database: "JAPAN"
```

## Comparison: Before vs After

### Before (Generated Enum)
```java
// target/generated-sources/jaxb/.../ISOTwoletterCountryCodeContentType.java
public enum ISOTwoletterCountryCodeContentType {
    AD, AE, AF, AG, AI, AL, ..., ZW;  // 3,324 lines of hardcoded values
}
```
- ❌ Fixed at compile time
- ❌ No metadata storage
- ❌ Limited to 256 values (JAXB constraint)
- ❌ Requires regeneration for updates

### After (Database-Backed)
```java
@Entity
public class ISOCountryCode {
    private String code;
    private String name;
    private String description;
    private Boolean thaiExtension;
    private Boolean active;
}
```
- ✅ Dynamic from database
- ✅ Rich metadata
- ✅ Unlimited values
- ✅ Update via SQL, no recompilation
- ✅ Full JAXB compatibility maintained

## Schematron Validation

The library includes runtime Schematron validation for Thai e-Tax Invoice business rules.

### Supported Document Types

| Document Type | Rules | Prefix |
|---------------|-------|--------|
| Tax Invoice | 91 rules | TIV-* |
| Receipt | 75 rules | RCT-* |
| Debit/Credit Note | 76 rules | DCN-* |
| Invoice | 5 rules | INV-* |
| Cancellation Note | 0 rules | CN-* |
| Abbreviated Tax Invoice | 0 rules | ATI-* |

### Usage Example

```java
import com.wpanther.etax.validation.SchematronValidator;
import com.wpanther.etax.validation.DocumentSchematron;
import com.wpanther.etax.validation.SchematronValidationResult;

@Autowired
private SchematronValidator schematronValidator;

public void validateInvoice(String xmlContent) {
    SchematronValidationResult result = schematronValidator.validate(
        xmlContent,
        DocumentSchematron.TAX_INVOICE
    );

    if (result.isValid()) {
        System.out.println("Validation passed!");
    } else {
        System.out.println("Validation failed with " + result.getErrors().size() + " errors");
        for (SchematronError error : result.getErrors()) {
            System.out.println("  [" + error.getRuleId() + "] " + error.getMessage());
        }
    }
}
```

### Dependencies

The library includes all required dependencies:
- ph-schematron-api 8.0.6
- ph-schematron-pure 8.0.6
- Saxon-HE 10.8

For detailed usage, see [docs/SCHEMATRON_VALIDATION.md](docs/SCHEMATRON_VALIDATION.md).

## Running Tests

The library includes comprehensive integration tests for all 20 code list repositories using Testcontainers and PostgreSQL.

### Run All Tests
```bash
mvn test
```

### Run Repository Integration Tests
```bash
# With Docker
mvn test -Dtest="*RepositoryTest"

# With Podman: set DOCKER_HOST to Podman socket
export DOCKER_HOST="unix:///run/user/$(id -u)/podman/podman.sock"
mvn test -Dtest="*RepositoryTest"

# Single repository test
mvn test -Dtest=ISOCountryCodeRepositoryTest
```

### Test Coverage
- 20 repository test classes
- 418 individual tests
- Tests for query methods, business logic, and edge cases
- Uses Testcontainers for real PostgreSQL database testing

## Next Steps

1. **Apply to Large Code Lists**
   - Reference Type Codes (798 values) - exceeds enum limit
   - Thai Subdivisions (8,940 values) - far exceeds limit

2. **Add Caching**
   - Spring Cache abstraction
   - Reduce database queries for frequently accessed codes

3. **Add Validation**
   - Bean Validation annotations
   - Custom validators for business rules

4. **Performance Tuning**
   - Connection pooling (HikariCP)
   - Query optimization
   - Batch loading for collections

5. **Testing**
   - Unit tests for adapter
   - Integration tests with embedded PostgreSQL
   - XML schema validation tests

## Troubleshooting

### Database Connection Error
```
Error: Connection refused
```
**Solution**: Check PostgreSQL is running and credentials in `application.properties` are correct

### Repository Not Initialized
```
Warning: Repository not initialized, creating placeholder for code: TH
```
**Solution**: Ensure `@ComponentScan` includes `com.wpanther.etax` package

### Namespace Mismatch
```
Error: Unexpected element
```
**Solution**: Verify `package-info.java` has correct namespace URI

### No Data Found
```
Warning: Country code 'TH' not found in database
```
**Solution**: Run `iso_country_code_data.sql` to load data

## References

- [DATABASE_BACKED_JAXB.md](DATABASE_BACKED_JAXB.md) - Full architecture documentation
- [iso_country_code.sql](iso_country_code.sql) - Database schema
- [pom.xml](pom.xml) - Maven dependencies
- [ETDA e-Tax Invoice v2.1](e-tax-invoice-receipt-v2.1/) - Original XSD schemas

## Support

For issues or questions:
1. Check logs in console output
2. Verify database connectivity: `psql -U postgres -d etax -c "SELECT version();"`
3. Test repository: `psql -U postgres -d etax -c "SELECT * FROM iso_country_code LIMIT 5;"`
4. Enable debug logging: `logging.level.com.wpanther.etax=DEBUG` in application.properties
