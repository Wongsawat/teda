# Thai e-Tax Invoice Library - Integration Examples

This directory contains complete working examples showing how to integrate the Thai e-Tax Invoice Library into your Spring Boot application.

## Files in this Directory

### 1. SpringBootIntegrationExample.java
Complete Spring Boot application showing:
- How to configure component scanning for library entities and repositories
- Basic CRUD operations with country, currency, and province codes
- XML marshalling and unmarshalling examples
- Database query examples

### 2. InvoiceServiceExample.java
Practical service layer example showing:
- Creating an invoice with seller/buyer information
- Working with Thai addresses and province codes
- Adding line items and calculating totals
- Validating country and currency codes
- Searching provinces by name

### 3. application.properties.example
Complete Spring Boot configuration file with:
- Database connection settings
- JPA/Hibernate configuration
- Logging configuration
- Connection pool settings

### 4. pom.xml.example
Maven configuration file showing:
- How to add the library as a dependency
- Required Spring Boot dependencies
- JAXB dependencies for Java 11+
- Optional development tools

## Quick Start

### Step 1: Install the Library

```bash
# Navigate to the library project
cd /home/wpanther/projects/teda

# Install to local Maven repository
mvn clean install
```

### Step 2: Create Your Spring Boot Project

Create a new Spring Boot project or use an existing one.

### Step 3: Add Dependencies

Copy the dependencies from `pom.xml.example` to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.wpanther</groupId>
    <artifactId>thai-etax-invoice</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Step 4: Configure Database

1. Create PostgreSQL database:
```bash
psql -U postgres -c "CREATE DATABASE etax;"
```

2. Load schema and data:
```bash
cd /home/wpanther/projects/teda/src/main/resources/db

# Load schemas
psql -U postgres -d etax -f schema/*.sql

# Load data
psql -U postgres -d etax -f data/*.sql
```

3. Copy `application.properties.example` to your project's `src/main/resources/application.properties` and update credentials.

### Step 5: Configure Component Scanning

In your main application class, add entity and repository scanning:

```java
@SpringBootApplication
@EntityScan(basePackages = {
    "com.wpanther.etax.core.entity",
    "com.yourcompany.yourapp.entity"
})
@EnableJpaRepositories(basePackages = {
    "com.wpanther.etax.core.repository",
    "com.yourcompany.yourapp.repository"
})
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

### Step 6: Use the Library

See `SpringBootIntegrationExample.java` and `InvoiceServiceExample.java` for complete working examples.

## Example 1: Basic Usage

```java
@Service
public class MyService {

    @Autowired
    private ISOCountryCodeRepository countryRepository;

    public void example() {
        // Fetch from database
        ISOCountryCode thailand = countryRepository.findByCode("TH")
            .orElseThrow(() -> new RuntimeException("Not found"));

        // Create JAXB type
        ISOTwoletterCountryCodeType countryType =
            ISOTwoletterCountryCodeType.of(thailand);

        // Use in your invoice
        System.out.println("Country: " + countryType.getName());
    }
}
```

## Example 2: Marshal to XML

```java
@Service
public class XmlService {

    public String toXml(ISOCountryCode country) throws JAXBException {
        ISOTwoletterCountryCodeType type =
            ISOTwoletterCountryCodeType.of(country);

        JAXBContext context = JAXBContext.newInstance(
            ISOTwoletterCountryCodeType.class
        );
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(type, writer);
        return writer.toString();
    }
}
```

## Example 3: Query Database

```java
@Service
public class CountryService {

    @Autowired
    private ISOCountryCodeRepository countryRepository;

    public List<ISOCountryCode> getAllActiveCountries() {
        return countryRepository.findByActiveTrue();
    }

    public boolean isValidCountry(String code) {
        return countryRepository.existsByCode(code);
    }

    public Optional<ISOCountryCode> findByCode(String code) {
        return countryRepository.findByCode(code);
    }
}
```

## Available Repositories

The library provides these Spring Data JPA repositories:

| Repository | Entity | Description |
|-----------|--------|-------------|
| `ISOCountryCodeRepository` | `ISOCountryCode` | 252 ISO country codes |
| `ISOCurrencyCodeRepository` | `ISOCurrencyCode` | 180+ currency codes |
| `ISOLanguageCodeRepository` | `ISOLanguageCode` | 180+ language codes |
| `ThaiProvinceCodeRepository` | `ThaiProvinceCode` | 77 Thai provinces |
| `TISISubdistrictRepository` | `TISISubdistrict` | 8,940 Thai subdivisions |
| `ThaiDocumentNameCodeRepository` | `ThaiDocumentNameCode` | 12 document types |
| `DutyTaxFeeTypeCodeRepository` | `DutyTaxFeeTypeCode` | Tax type codes |
| `UNECEReferenceTypeCodeRepository` | `UNECEReferenceTypeCode` | 798 reference types |
| And 11+ more... | | |

All repositories extend `JpaRepository` and provide:
- `findByCode(String code)` - Find by code
- `findByActiveTrue()` - Find all active records
- `existsByCode(String code)` - Check if code exists
- Standard CRUD operations (save, delete, findAll, etc.)

## Common Use Cases

### 1. Creating an Invoice

```java
// Set currency
ISOCurrencyCode thb = currencyRepository.findByCode("THB").get();
invoice.setCurrency(ISOThreeletterCurrencyCodeType.of(thb));

// Set seller country
ISOCountryCode thailand = countryRepository.findByCode("TH").get();
seller.setCountry(ISOTwoletterCountryCodeType.of(thailand));
```

### 2. Working with Thai Addresses

```java
// Set province
ThaiProvinceCode bangkok = provinceRepository.findByCode("10").get();
address.setProvince(ThaiProvinceCodeType.of(bangkok));
```

### 3. Validating Input

```java
// Validate country code
if (!countryRepository.existsByCode(inputCountryCode)) {
    throw new ValidationException("Invalid country code");
}
```

### 4. Dropdown/Selection Lists

```java
// Get all active countries for dropdown
List<ISOCountryCode> countries = countryRepository.findByActiveTrue();

// Get all provinces for selection
List<ThaiProvinceCode> provinces = provinceRepository.findByActiveTrue();
```

## Troubleshooting

### Problem: Repository not found
**Solution**: Make sure you have `@EnableJpaRepositories` with the correct base package:
```java
@EnableJpaRepositories(basePackages = "com.wpanther.etax.core.repository")
```

### Problem: Entity not found
**Solution**: Add `@EntityScan` with the correct base package:
```java
@EntityScan(basePackages = "com.wpanther.etax.core.entity")
```

### Problem: Database connection error
**Solution**: Check your `application.properties`:
- Verify PostgreSQL is running
- Check username/password
- Verify database name is correct

### Problem: No data found
**Solution**: Load the SQL data files:
```bash
psql -U postgres -d etax -f iso_country_code_data.sql
```

## Next Steps

1. Review the example files in this directory
2. Copy and adapt the examples to your project
3. Read the migration guides in `docs/migrations/` for detailed information about each code list
4. Refer to the main [README.md](../README.md) for architecture details

## Support

For more information:
- [README.md](../README.md) - Main project documentation
- [QUICK_START.md](../QUICK_START.md) - Quick start guide
- [JAXB_INTEGRATION_GUIDE.md](../JAXB_INTEGRATION_GUIDE.md) - JAXB integration details
- [DATABASE_BACKED_JAXB.md](../DATABASE_BACKED_JAXB.md) - Architecture explanation
