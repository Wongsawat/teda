
# Thai e-Tax Invoice - Database-Backed JAXB Library

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JAXB](https://img.shields.io/badge/Jakarta%20XML%20Binding-4.0-blue.svg)](https://eclipse-ee4j.github.io/jaxb-ri/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12%2B-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red.svg)](https://maven.apache.org/)


A reusable Java library for Thailand ETDA (Electronic Transactions Development Agency) e-Tax Invoice XSD schemas, providing database-backed JAXB integration for code lists and XML marshalling/unmarshalling.

## Overview

This project implements the **Thai e-Tax Invoice Specification v2.1** (based on UN/CEFACT CrossIndustryInvoice standard) with a novel solution to JAXB's enum size limitations. Instead of generating large hardcoded enums, code values are stored in PostgreSQL with rich metadata and accessed through JPA entities while maintaining full JAXB XML compatibility.

## Key Features

- **Database-Backed JAXB**: Overcomes JAXB's 256-member enum limitation using `XmlAdapter` pattern
- **Unlimited Code Lists**: Handles 798 UN/CEFACT reference types and 8,940 Thai subdivisions
- **Full JAXB Compatibility**: Maintains exact XML namespaces and schema compliance
- **Rich Metadata**: Store descriptions, translations, active/inactive status in PostgreSQL
- **Runtime Updates**: Modify code lists via SQL without recompilation

- **Spring Data JPA Support**: Integrates with JPA repositories for database-backed code lists
- **Comprehensive Documentation**: 25+ detailed documentation files included

## Architecture

```
XML Document
    ↕ (JAXB Marshal/Unmarshal)
Custom JAXB Type
    ↕ (@XmlJavaTypeAdapter)
XmlAdapter
    ↕ (Database Query)
JPA Entity
    ↕ (Spring Data JPA)
PostgreSQL Database
```

## Project Statistics

| Component | Count | Description |
|-----------|-------|-------------|
| Custom Java Files | 101 | Entities, repositories, adapters, examples |
| Generated JAXB Classes | 293+ | Root schema, business entities, data types |
| XSD Schemas | 35+ | Thai e-Tax Invoice v2.1 specification |
| Database Tables | 19 | Code list tables with indexes and views |
| SQL Migration Files | 42 | Schema DDL and data insertion scripts |
| Documentation Files | 25 | Architecture, migration guides, examples |
| Python Scripts | 8 | XSD to SQL data extraction utilities |

## Technology Stack

- **Java 17+**: Modern Java language features
- **Spring Boot 3.2.0**: Application framework and dependency injection
- **Jakarta XML Binding 4.0**: JAXB implementation for XML marshalling
- **Spring Data JPA**: Database access layer with repositories
- **PostgreSQL 12+**: Relational database for code list storage
- **Maven 3.6+**: Build automation and dependency management

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- Git (for cloning the repository)


## Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd teda
```

### 2. Setup Database

```bash
# Create database
psql -U postgres -c "CREATE DATABASE etax;"

# Load schema (example: ISO country codes)
psql -U postgres -d etax -f iso_country_code.sql

# Load data
psql -U postgres -d etax -f iso_country_code_data.sql

# Verify
psql -U postgres -d etax -c "SELECT COUNT(*) FROM iso_country_code;"
# Should return: 252 (249 ISO + 3 ETDA extensions)
```


### 3. Generate JAXB Classes

```bash
# Generate Java classes from XSD schemas using Maven
mvn clean generate-sources
```

### 4. Build Library

```bash
# Compile and package the library
mvn clean compile package
```


## Project Structure

```
teda/
├── pom.xml                                 # Maven configuration
├── src/
│   └── main/
│       ├── java/com/wpanther/etax/core/    # Library source code (entities, repositories, adapters, xml types)
│       └── resources/
│           ├── jaxb-bindings.xjb           # JAXB binding configuration
│           └── e-tax-invoice-receipt-v2.1/ # XSD schemas (35+ files)
├── target/
│   └── generated-sources/jaxb/             # Generated JAXB classes (293+)
├── Documentation/                          # 25 documentation files
│   ├── QUICK_START.md
│   ├── DATABASE_BACKED_JAXB.md
│   ├── JAXB_GENERATION_SUMMARY.md
│   ├── JAXB_INTEGRATION_GUIDE.md
│   └── *_MIGRATION.md (19 files)
└── Database Migration Files/               # 42 SQL scripts
    ├── iso_country_code.sql
    ├── iso_country_code_data.sql
    └── ... (40 more files)
```

## Core Components

### 1. XSD Schemas

Located in [src/main/resources/e-tax-invoice-receipt-v2.1/](src/main/resources/e-tax-invoice-receipt-v2.1/)

- **Root Schema**: `TaxInvoice_CrossIndustryInvoice_2p1.xsd`
- **19 Code List Schemas**: ISO standards (countries, currencies, languages), Thai-specific codes, UN/CEFACT codes
- **Example XML Files**: Sample invoices, receipts, credit/debit notes

### 2. Java Packages

Located in [src/main/java/com/wpanther/etax/](src/main/java/com/wpanther/etax/)

#### Entity Layer (19 JPA Entities)
Database models for code lists with metadata:
- [ISOCountryCode.java](src/main/java/com/wpanther/etax/entity/ISOCountryCode.java) - 252 ISO country codes
- [ISOCurrencyCode.java](src/main/java/com/wpanther/etax/entity/ISOCurrencyCode.java) - ISO 4217 currencies
- [TISISubdistrict.java](src/main/java/com/wpanther/etax/entity/TISISubdistrict.java) - 8,940 Thai subdivisions
- [UNECEReferenceTypeCode.java](src/main/java/com/wpanther/etax/entity/UNECEReferenceTypeCode.java) - 798 reference types
- And 15 more code list entities

#### Repository Layer (19 Spring Data Repositories)
Database access with query methods:
```java
public interface ISOCountryCodeRepository extends JpaRepository<ISOCountryCode, String> {
    Optional<ISOCountryCode> findByCode(String code);
    List<ISOCountryCode> findByActiveTrue();
    // ... additional query methods
}
```


#### Adapter Layer (19 JAXB XmlAdapters)
Bridge between XML and database:

```java
public class ISOCountryCodeAdapter extends XmlAdapter<String, ISOCountryCode> {
    @Override
    public String marshal(ISOCountryCode entity) {
        return entity != null ? entity.getCode() : null;
    }

    @Override
    public ISOCountryCode unmarshal(String code) {
        return repository.findByCode(code).orElse(null);
    }
}
```

#### XML Layer (19 Custom JAXB Types)
Replace generated enums with database-backed types:
```java
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISOTwoletterCountryCodeContentType")
public class ISOTwoletterCountryCodeType {
    @XmlValue
    @XmlJavaTypeAdapter(ISOCountryCodeAdapter.class)
    private ISOCountryCode value;

    // Convenience methods
    public String getCode() { return value != null ? value.getCode() : null; }
    public String getName() { return value != null ? value.getName() : null; }
}
```

### 3. Generated JAXB Classes

Located in [target/generated-sources/jaxb/](target/generated-sources/jaxb/)

293+ classes organized into 4 packages:
- **rsm** (Root Schema Module): Main invoice document types (6 classes)
- **ram** (Reusable Aggregate Entities): Business entities like parties, addresses, taxes (74 classes)
- **qdt** (Qualified Data Types): Thai-specific data types (72 classes)
- **udt** (Unqualified Data Types): UN/CEFACT standard types (44 classes)

### 4. Database Tables

19 PostgreSQL tables with full metadata:

| Table | Records | Description |
|-------|---------|-------------|
| iso_country_code | 252 | ISO 3166-1 alpha-2 country codes + ETDA extensions |
| iso_currency_code | 180+ | ISO 4217 currency codes |
| iso_language_code | 180+ | ISO 639-1 language codes |
| tisi_subdistrict | 8,940 | Thai subdivisions (TISI standard) |
| unece_reference_type_code | 798 | UN/CEFACT reference type codes |
| thai_province_code | 77 | Thai provinces |
| thai_document_name_code | 12 | Thai document type codes |
| thai_message_function_code | 25 | Thai message function codes |
| ... | ... | 11 more code list tables |

Each table includes:
- Primary key constraint
- Performance indexes
- Active/inactive status flag
- Audit timestamps (created_at, updated_at)
- Trigger functions for automatic timestamp updates
- Helper views and functions


## Usage

This project is a pure Java library. There are no executable classes or example runners included. To use the library:

- Add it as a dependency to your project (see 'Using as a Java Library' section below)
- Integrate with your own application code and database
- Refer to the documentation files for migration, integration, and advanced usage


## Key Innovation: Database-Backed JAXB

### The Problem

JAXB generates Java enums from XSD enumeration types, but this has limitations:

1. **Size Limit**: Cannot generate enums with > 256 values
2. **Inflexibility**: Values are hardcoded at compile time
3. **No Metadata**: Cannot store descriptions, translations, or status flags
4. **No Updates**: Cannot modify code lists without regeneration

### The Solution

Use `XmlAdapter` to bridge between XML and PostgreSQL database while maintaining full JAXB compatibility:

| Feature | Generated Enum | Database-Backed |
|---------|----------------|-----------------|
| Max Size | 256 values | Unlimited |
| Metadata | None | Rich (descriptions, dates, flags) |
| Updates | Requires regeneration | SQL update only |
| Memory | All loaded at startup | Lazy/cached loading |
| Validation | Compile-time | Runtime (database) |
| Namespace Preservation | Yes | Yes |
| XML Compatibility | Yes | Yes |
| Complexity | Simple | Moderate |

### When to Use Each Approach

**Use Generated Enum When:**
- Code list has < 100 values
- Values rarely change
- No metadata needed
- Compile-time validation preferred

**Use Database-Backed When:**
- Code list has > 256 values (JAXB limit)
- Frequent updates expected
- Rich metadata required (descriptions, translations)
- Need active/inactive status management
- Different environments need different code lists

## Documentation

Comprehensive documentation is available in 25 files:

### Getting Started
- [QUICK_START.md](QUICK_START.md) - Quick start guide with examples
- [SIMPLE_EXAMPLE.md](SIMPLE_EXAMPLE.md) - Simple working examples

### Architecture & Design
- [DATABASE_BACKED_JAXB.md](DATABASE_BACKED_JAXB.md) - Core architecture explanation
- [JAXB_GENERATION_SUMMARY.md](JAXB_GENERATION_SUMMARY.md) - Generated code summary (293+ classes)
- [JAXB_INTEGRATION_GUIDE.md](JAXB_INTEGRATION_GUIDE.md) - Integration guide
- [XSD_DEPENDENCY_HIERARCHY.md](XSD_DEPENDENCY_HIERARCHY.md) - XSD dependency tree

### Database Migration Guides (19 files)
Each code list has a dedicated migration guide with:
- Schema DDL
- Data insertion SQL
- Entity Java code
- Repository implementation
- Adapter implementation
- Usage examples

Examples:
- [ISO_COUNTRY_CODE_MIGRATION.md](ISO_COUNTRY_CODE_MIGRATION.md)
- [UNECE_REFERENCE_CODE_MIGRATION.md](UNECE_REFERENCE_CODE_MIGRATION.md)
- [THAI_PROVINCE_DATABASE_BACKED.md](THAI_PROVINCE_DATABASE_BACKED.md)

## Build Commands

```bash
# Generate JAXB classes from XSD
./generate-jaxb.sh
# or
mvn clean generate-sources

# Compile project
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn clean package

# Run application
mvn spring-boot:run

# Run specific example
mvn exec:java -Dexec.mainClass="com.wpanther.etax.example.ProvinceCodeXmlExample"
```

## Thai e-Tax Invoice Structure

The project implements **ETDA e-Tax Invoice Specification v2.1** with:

### Document Types
- **388**: Tax Invoice (ใบกำกับภาษี)
- **80**: Receipt (ใบเสร็จรับเงิน)
- **81**: Abbreviated Tax Invoice (ใบกำกับภาษีอย่างย่อ)
- **381**: Credit Note (ใบลดหนี้)
- **383**: Debit Note (ใบเพิ่มหนี้)

### Key Components
- **ExchangedDocument**: Invoice header (number, date, type)
- **SupplyChainTradeTransaction**: Transaction details
  - **SellerTradeParty**: Seller information with Thai tax ID
  - **BuyerTradeParty**: Buyer information
  - **LineItems**: Product/service details with quantities and prices
  - **ApplicableTradeTax**: VAT and tax calculations
  - **MonetarySummation**: Totals (subtotal, tax, grand total)
- **Signature**: XML Digital Signature support

### Thai-Specific Features
- Thai province codes (77 provinces)
- Thai city/district codes (8,940 subdivisions)
- Thai document name codes (12 types)
- Thai message function codes (25 functions)
- Thai address format support
- Thai tax ID (NIDN) format

## Benefits

- **Overcomes JAXB Limitations**: Handle code lists with 256+ values
- **Database-Backed Flexibility**: Update code lists via SQL without recompilation
- **Rich Metadata Storage**: Descriptions, translations, active/inactive flags
- **Full JAXB Compatibility**: Exact namespace preservation, seamless XML marshalling
- **Production-Ready**: Spring Boot, PostgreSQL, comprehensive error handling
- **Standards-Compliant**: ETDA v2.1, UN/CEFACT, ISO, W3C XML Signature
- **Well-Documented**: 25 documentation files with examples
- **Scalable**: Handles large datasets efficiently with caching and lazy loading

## Troubleshooting

### Database Connection Error
```
Error: Connection refused
```
**Solution**: Verify PostgreSQL is running and credentials in `application.properties` are correct.

### Repository Not Initialized
```
Warning: Repository not initialized, creating placeholder
```
**Solution**: Ensure `@ComponentScan` includes `com.wpanther.etax` package in Spring Boot application.

### JAXB Generation Fails
```
Error: class/interface with same name already in use
```
**Solution**: Check `jaxb-bindings.xjb` for proper package separation configuration.

### Enum Size Limit Warning
```
Warning: Simple type was not mapped to Enum due to EnumMemberSizeCap
```
**Solution**: Verify `typesafeEnumMaxMembers="9000"` is set in `jaxb-bindings.xjb`.

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## References

- [ETDA e-Tax Invoice Specification](https://www.etda.or.th/)
- [UN/CEFACT CrossIndustryInvoice](https://unece.org/trade/uncefact/xml-schemas)
- [Jakarta XML Binding (JAXB)](https://eclipse-ee4j.github.io/jaxb-ri/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)


## Using as a Java Library

### 1. Add Dependency (Maven)

If published to a Maven repository, add to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.wpanther</groupId>
    <artifactId>thai-etax-invoice</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

If using locally, install to your local Maven repository:

```bash
mvn clean install
```
Then add the same dependency to your consumer project.

### 2. Required Dependencies

Your project should also include:

- JPA implementation (e.g., Hibernate or Spring Data JPA)
- PostgreSQL JDBC driver
- Jakarta XML Binding (JAXB)

### 3. Example Usage

```java
// Fetch entity from database (using your repository)
ISOCountryCode thailand = countryRepository.findByCode("TH").orElse(null);

// Create JAXB type
ISOTwoletterCountryCodeType countryType = ISOTwoletterCountryCodeType.of(thailand);

// Marshal to XML
JAXBContext context = JAXBContext.newInstance(ISOTwoletterCountryCodeType.class);
Marshaller marshaller = context.createMarshaller();
marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
marshaller.marshal(countryType, System.out);
```

### 4. Database Setup

See the migration SQL files and documentation for required tables and initial data. Example:

```bash
psql -U postgres -d etax -f iso_country_code.sql
psql -U postgres -d etax -f iso_country_code_data.sql
```

### 5. Configuration

Configure your database connection in `application.properties` or your framework's config.

### 6. More Examples

See the documentation files for advanced usage, integration, and migration guides.

## Support

For issues or questions:
1. Check the [QUICK_START.md](QUICK_START.md) guide
2. Review relevant migration documentation files
3. Enable debug logging: `logging.level.com.wpanther.etax=DEBUG`
4. Check database connectivity: `psql -U postgres -d etax -c "SELECT version();"`
5. Open an issue on GitHub

## Acknowledgments

- **ETDA** for the Thai e-Tax Invoice specification
- **UN/CEFACT** for the CrossIndustryInvoice standard
- **Spring Boot** and **Jakarta EE** communities
- Contributors to this project

---

**Status**: Production Ready
**Version**: 1.0.0-SNAPSHOT
**Last Updated**: October 2025
**Maintained by**: wpanther
