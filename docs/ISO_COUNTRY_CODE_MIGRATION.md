# ISO 3166-1 Two-letter Country Code Migration Guide

## Overview

This document describes the migration from JAXB-generated country code handling to a **database-backed implementation** for ISO 3166-1 Two-letter Country Codes in the e-Tax Invoice system.

**Standard**: ISO 3166-1 alpha-2
**Code List Version**: Second Edition 2006
**Total Countries**: 252
- 249 standard ISO codes
- **3 ETDA custom extensions** (AN, KS, UN)
**Namespace**: `urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006`
**Code Format**: 2 uppercase letters (e.g., TH, US, CN, JP, SG)

### JAXB Pattern: JAXBElement<ENUM> (Same as Currency Code)

The JAXB generated code uses an **enum pattern**, not a String pattern:
```java
JAXBElement<ISOTwoletterCountryCodeContentType>
```

Where `ISOTwoletterCountryCodeContentType` is a Java enum with 252 values.

## Migration Components

### 1. Database Schema (`iso_country_code.sql`)

**Table Structure**:
```sql
CREATE TABLE iso_country_code (
    code VARCHAR(2) PRIMARY KEY,              -- 2 uppercase letters (e.g., 'TH', 'US')
    name VARCHAR(255) NOT NULL,               -- country name (e.g., 'THAILAND', 'UNITED STATES')
    description TEXT,                         -- additional notes
    is_etda_extension BOOLEAN DEFAULT false,  -- true for AN, KS, UN
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_country_code_format CHECK (code ~ '^[A-Z]{2}$')
);
```

**Key Design Decisions**:
- **Primary key is uppercase only** - Enforced by CHECK constraint
- **No case variations** - Unlike language codes (always uppercase)
- **ETDA extension flag** - Distinguishes custom codes (AN, KS, UN) from standard ISO
- **Active status** - AN is inactive (historical), KS and UN are active

**Indexes**:
```sql
CREATE INDEX idx_iso_country_code_name ON iso_country_code(name);
CREATE INDEX idx_iso_country_code_is_etda_extension ON iso_country_code(is_etda_extension);
CREATE INDEX idx_iso_country_code_is_active ON iso_country_code(is_active);
CREATE INDEX idx_iso_country_code_name_lower ON iso_country_code(LOWER(name));
```

**Views** (4 specialized views):

1. **Active Countries**:
```sql
CREATE VIEW iso_country_code_active AS
SELECT code, name, description
FROM iso_country_code
WHERE is_active = true
ORDER BY name;
```

2. **Standard ISO Codes** (excluding ETDA extensions):
```sql
CREATE VIEW iso_country_code_standard AS
SELECT code, name, description
FROM iso_country_code
WHERE is_etda_extension = false
ORDER BY code;
```

3. **ETDA Extensions** (AN, KS, UN):
```sql
CREATE VIEW iso_country_code_etda_extensions AS
SELECT code, name, description
FROM iso_country_code
WHERE is_etda_extension = true
ORDER BY code;
```

4. **ASEAN Countries** (10 members):
```sql
CREATE VIEW iso_country_code_asean AS
SELECT code, name
FROM iso_country_code
WHERE code IN ('TH', 'BN', 'KH', 'ID', 'LA', 'MY', 'MM', 'PH', 'SG', 'VN')
  AND is_active = true
ORDER BY name;
```

**Helper Function**:
```sql
CREATE OR REPLACE FUNCTION get_country_name(country_code VARCHAR(2))
RETURNS VARCHAR(255) AS $$
DECLARE
    country_name VARCHAR(255);
BEGIN
    SELECT name INTO country_name
    FROM iso_country_code
    WHERE code = UPPER(country_code) AND is_active = true;
    RETURN country_name;
END;
$$ LANGUAGE plpgsql;
```

### 2. Database Data (`iso_country_code_data.sql`)

**Data Loading**:
- **3 ETDA extensions** pre-loaded in schema file:
  ```sql
  INSERT INTO iso_country_code (code, name, description, is_etda_extension, is_active) VALUES
  ('AN', 'NETHERLANDS ANTILLES', 'This code is not listed in ISO 3166. ETDA has added for domestic use.', true, false),
  ('KS', 'KOSOVO', 'This code is not listed in ISO 3166. ETDA has added for domestic use.', true, true),
  ('UN', 'UNITED NATIONS', 'This code is not listed in ISO 3166. ETDA has added for domestic use.', true, true);
  ```

- **249 standard ISO country codes** in data file

**Sample Data**:
```sql
INSERT INTO iso_country_code (code, name, description, is_etda_extension, is_active) VALUES
('AD', 'ANDORRA', NULL, false, true),
('AE', 'UNITED ARAB EMIRATES', NULL, false, true),
('TH', 'THAILAND', NULL, false, true),
('US', 'UNITED STATES', NULL, false, true),
('CN', 'CHINA', NULL, false, true),
('JP', 'JAPAN', NULL, false, true),
('SG', 'SINGAPORE', NULL, false, true),
-- ... 242 more countries
```

**Common Country Groups**:

**ASEAN Countries** (10):
- `TH` - THAILAND
- `BN` - BRUNEI DARUSSALAM
- `KH` - CAMBODIA
- `ID` - INDONESIA
- `LA` - LAO PEOPLE'S DEMOCRATIC REPUBLIC
- `MY` - MALAYSIA
- `MM` - MYANMAR
- `PH` - PHILIPPINES
- `SG` - SINGAPORE
- `VN` - VIET NAM

**Major Trading Partners** (11):
- `CN` - CHINA
- `JP` - JAPAN
- `KR` - KOREA, REPUBLIC OF
- `US` - UNITED STATES
- `GB` - UNITED KINGDOM
- `DE` - GERMANY
- `AU` - AUSTRALIA
- `IN` - INDIA
- `TW` - TAIWAN, PROVINCE OF CHINA
- `HK` - HONG KONG
- `SG` - SINGAPORE

### 3. JPA Entity (`com.wpanther.etax.entity.ISOCountryCode`)

**Entity Class**:
```java
@Entity
@Table(name = "iso_country_code")
public class ISOCountryCode {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;  // Stored in uppercase

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_etda_extension")
    private Boolean etdaExtension = false;

    @Column(name = "is_active")
    private Boolean active = true;

    // Constructor with normalization
    public ISOCountryCode(String code) {
        this.code = code != null ? code.toUpperCase() : null;  // Always uppercase
    }
}
```

**Business Logic Methods** (14 methods):

```java
// Country type checks
public boolean isThailand() { return "TH".equals(code); }
public boolean isChina() { return "CN".equals(code); }
public boolean isJapan() { return "JP".equals(code); }
public boolean isSouthKorea() { return "KR".equals(code); }
public boolean isUnitedStates() { return "US".equals(code); }
public boolean isSingapore() { return "SG".equals(code); }
public boolean isMalaysia() { return "MY".equals(code); }
public boolean isIndonesia() { return "ID".equals(code); }
public boolean isVietnam() { return "VN".equals(code); }
public boolean isPhilippines() { return "PH".equals(code); }

// Country group checks
public boolean isASEANCountry() {
    return "TH".equals(code) || "BN".equals(code) || "KH".equals(code) ||
           "ID".equals(code) || "LA".equals(code) || "MY".equals(code) ||
           "MM".equals(code) || "PH".equals(code) || "SG".equals(code) ||
           "VN".equals(code);
}

public boolean isMajorTradingPartner() {
    return "CN".equals(code) || "JP".equals(code) || "KR".equals(code) ||
           "US".equals(code) || "GB".equals(code) || "DE".equals(code) ||
           "AU".equals(code) || "IN".equals(code) || "TW".equals(code) ||
           "HK".equals(code) || "SG".equals(code);
}

// Extension checks
public boolean isETDAExtension() { return Boolean.TRUE.equals(etdaExtension); }
public boolean isStandardISO() { return !Boolean.TRUE.equals(etdaExtension); }
```

### 4. Spring Data Repository (`com.wpanther.etax.repository.ISOCountryCodeRepository`)

**Repository Interface**:
```java
@Repository
public interface ISOCountryCodeRepository extends JpaRepository<ISOCountryCode, String> {

    // Basic queries
    @Query("SELECT c FROM ISOCountryCode c WHERE UPPER(c.code) = UPPER(:code) AND c.active = true")
    Optional<ISOCountryCode> findByCode(@Param("code") String code);

    @Query("SELECT c FROM ISOCountryCode c WHERE c.active = true ORDER BY c.name")
    List<ISOCountryCode> findAllActive();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.etdaExtension = false AND c.active = true ORDER BY c.code")
    List<ISOCountryCode> findStandardISO();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.etdaExtension = true ORDER BY c.code")
    List<ISOCountryCode> findETDAExtensions();

    // ASEAN countries (10 members)
    @Query("SELECT c FROM ISOCountryCode c WHERE c.code IN ('TH', 'BN', 'KH', 'ID', 'LA', 'MY', 'MM', 'PH', 'SG', 'VN') AND c.active = true ORDER BY c.name")
    List<ISOCountryCode> findASEANCountries();

    // Major trading partners (11 countries)
    @Query("SELECT c FROM ISOCountryCode c WHERE c.code IN ('CN', 'JP', 'KR', 'US', 'GB', 'DE', 'AU', 'IN', 'TW', 'HK', 'SG') AND c.active = true ORDER BY ...")
    List<ISOCountryCode> findMajorTradingPartners();

    // Specific country finders (10 ASEAN + 7 trading partners)
    Optional<ISOCountryCode> findThailand();
    Optional<ISOCountryCode> findSingapore();
    Optional<ISOCountryCode> findChina();
    Optional<ISOCountryCode> findJapan();
    // ... etc
}
```

**Total Query Methods**: 27
- 5 basic queries
- 10 ASEAN country finders
- 7 major trading partner finders
- 3 search queries
- 2 count queries

### 5. JAXB Adapter (`com.wpanther.etax.adapter.ISOCountryCodeAdapter`)

**Adapter Class**:
```java
@Component
public class ISOCountryCodeAdapter extends XmlAdapter<ISOTwoletterCountryCodeContentType, ISOCountryCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOCountryCodeAdapter.class);
    private static ISOCountryCodeRepository repository;

    @Autowired
    public void setRepository(ISOCountryCodeRepository repository) {
        ISOCountryCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOCountryCode entity to JAXB enum value
     */
    @Override
    public ISOTwoletterCountryCodeContentType marshal(ISOCountryCode entity) throws Exception {
        if (entity == null) return null;
        String code = entity.getCode();
        try {
            // Convert entity code to JAXB enum
            ISOTwoletterCountryCodeContentType enumValue = ISOTwoletterCountryCodeContentType.valueOf(code.toUpperCase());
            log.debug("Marshalled ISOCountryCode: {} ({}) -> {}", code, entity.getName(), enumValue);
            return enumValue;
        } catch (IllegalArgumentException e) {
            log.warn("Country code '{}' not found in JAXB enum, returning null", code);
            return null;
        }
    }

    /**
     * Unmarshal: Convert JAXB enum value to ISOCountryCode entity from database
     */
    @Override
    public ISOCountryCode unmarshal(ISOTwoletterCountryCodeContentType enumValue) throws Exception {
        if (enumValue == null) return null;

        // Get country code from enum (e.g., "TH")
        String code = enumValue.name();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", code);
            return createPlaceholder(code);
        }

        // Lookup in database
        return repository.findByCode(code)
                .orElseGet(() -> {
                    log.warn("Country code '{}' not found in database, creating placeholder", code);
                    return createPlaceholder(code);
                });
    }

    private ISOCountryCode createPlaceholder(String code) {
        ISOCountryCode placeholder = new ISOCountryCode(code);
        placeholder.setName("Unknown Country: " + code);
        placeholder.setActive(false);
        placeholder.setEtdaExtension(false);
        return placeholder;
    }
}
```

**Static Helper Methods** (11 methods):

```java
// Validation
public static boolean isValid(String code);
public static String getName(String code);
public static String normalize(String code);  // Normalize to uppercase

// Country type checks
public static boolean isThailand(String code);
public static boolean isChina(String code);
public static boolean isJapan(String code);
public static boolean isUnitedStates(String code);
public static boolean isSingapore(String code);

// Country group checks
public static boolean isASEANCountry(String code);
public static boolean isMajorTradingPartner(String code);
public static boolean isETDAExtension(String code);
```

### 6. Custom JAXB Type (`com.wpanther.etax.xml.isocountry.ISOCountryCodeType`)

**JAXB Type Class**:
```java
@XmlAccessorType(XmlAccessType.FIELD)
public class ISOCountryCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOCountryCodeAdapter.class)
    private ISOCountryCode value;

    // Constructors
    public ISOCountryCodeType() {}
    public ISOCountryCodeType(ISOCountryCode value) { this.value = value; }
    public ISOCountryCodeType(String code) {
        this.value = ISOCountryCodeAdapter.isValid(code)
            ? new ISOCountryCode(code, ISOCountryCodeAdapter.getName(code))
            : new ISOCountryCode(code);
    }

    // Factory methods
    public static ISOCountryCodeType of(String code) { return new ISOCountryCodeType(code); }
    public static ISOCountryCodeType thailand() { return of("TH"); }
    public static ISOCountryCodeType unitedStates() { return of("US"); }
    public static ISOCountryCodeType china() { return of("CN"); }
    public static ISOCountryCodeType japan() { return of("JP"); }
    public static ISOCountryCodeType singapore() { return of("SG"); }

    // Business logic delegation
    public boolean isThailand() { return value != null && value.isThailand(); }
    public boolean isASEANCountry() { return value != null && value.isASEANCountry(); }
    public boolean isMajorTradingPartner() { return value != null && value.isMajorTradingPartner(); }
    public boolean isETDAExtension() { return value != null && value.isETDAExtension(); }
    // ... 10 more country type checks
}
```

### 7. Package Configuration (`com.wpanther.etax.xml.isocountry.package-info`)

```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "ids5ISO316612A",
               namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
    }
)
package com.wpanther.etax.xml.isocountry;
```

## Usage Examples

### Basic Country Operations

```java
// Create country codes
ISOCountryCodeType thailand = ISOCountryCodeType.thailand();
ISOCountryCodeType usa = ISOCountryCodeType.of("US");
ISOCountryCodeType china = ISOCountryCodeType.china();

// Get country information
String code = thailand.getCode();     // "TH"
String name = thailand.getName();     // "THAILAND"

// Check country type
if (ISOCountryCodeAdapter.isThailand("TH")) {
    System.out.println("Thai invoice detected");
}

// Validate codes
boolean valid = ISOCountryCodeAdapter.isValid("TH");  // true
boolean invalid = ISOCountryCodeAdapter.isValid("XX");  // false
```

### Country Group Checks

```java
// Check ASEAN countries
ISOCountryCodeType singapore = ISOCountryCodeType.of("SG");
if (singapore.isASEANCountry()) {
    System.out.println("ASEAN country detected");
}

// Check major trading partners
ISOCountryCodeType japan = ISOCountryCodeType.of("JP");
if (japan.isMajorTradingPartner()) {
    System.out.println("Major trading partner: " + japan.getName());
}

// Check ETDA extensions
ISOCountryCodeType kosovo = ISOCountryCodeType.of("KS");
if (kosovo.isETDAExtension()) {
    System.out.println("ETDA custom extension");
}
```

### Repository Queries

```java
@Autowired
private ISOCountryCodeRepository repository;

// Find by code
Optional<ISOCountryCode> thailand = repository.findByCode("TH");
Optional<ISOCountryCode> usa = repository.findByCode("US");

// Get ASEAN countries
List<ISOCountryCode> asean = repository.findASEANCountries();
// Returns: TH, BN, KH, ID, LA, MY, MM, PH, SG, VN

// Get major trading partners
List<ISOCountryCode> trading = repository.findMajorTradingPartners();
// Returns: CN, JP, KR, US, GB, DE, AU, IN, TW, HK, SG (priority ordered)

// Search by name
List<ISOCountryCode> countries = repository.findByNameContaining("UNITED");
// Returns: UNITED ARAB EMIRATES, UNITED KINGDOM, UNITED STATES, UNITED NATIONS

// Get specific countries
Optional<ISOCountryCode> china = repository.findChina();
Optional<ISOCountryCode> japan = repository.findJapan();
```

### XML Marshalling/Unmarshalling

```java
// In e-Tax Invoice classes
@XmlElement(namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
private ISOCountryCodeType countryID;

// Setting country
invoice.setCountryID(ISOCountryCodeType.thailand());
invoice.setCountryID(ISOCountryCodeType.of("US"));

// XML output
// <countryID>TH</countryID>

// XML input (enum value TH converted to database entity)
// <countryID>TH</countryID>
```

### Database Helper Function

```sql
-- Get country name
SELECT get_country_name('TH');    -- Returns: 'THAILAND'
SELECT get_country_name('US');    -- Returns: 'UNITED STATES'
SELECT get_country_name('XX');    -- Returns: NULL
```

### Using Database Views

```sql
-- ASEAN countries view
SELECT * FROM iso_country_code_asean;
-- Returns: 10 ASEAN countries ordered by name

-- Major trading partners (custom query)
SELECT * FROM iso_country_code
WHERE code IN ('CN', 'JP', 'KR', 'US', 'SG')
ORDER BY name;

-- Active countries view
SELECT * FROM iso_country_code_active;
-- Returns: All 252 active countries

-- ETDA extensions view
SELECT * FROM iso_country_code_etda_extensions;
-- Returns: AN (inactive), KS, UN
```

## Migration Benefits

### 1. Enum-Based Type Safety
- **JAXB enum pattern** provides compile-time type checking
- All 252 country codes available as enum values
- Same pattern as currency code migration

### 2. Database-Backed Validation
- Country codes validated against database
- Invalid codes create placeholder entities (graceful degradation)
- Active/inactive status support
- Extensible without code changes

### 3. Enhanced Querying
- **4 specialized views** (active, standard, ETDA, ASEAN)
- **27 repository methods** for common operations
- **1 helper function** for SQL-level operations
- Full-text search on country names

### 4. Business Logic Integration
- **10 country type checks** (Thailand, China, Japan, etc.)
- **3 country group checks** (ASEAN, major trading, ETDA)
- Country name resolution
- Normalization utilities

### 5. Thai e-Tax Specific Features
- **ASEAN country group** - Regional integration (10 countries)
- **Major trading partner group** - International trade (11 countries)
- **ETDA extensions** - Local requirements (AN, KS, UN)
- **Thailand priority** - Thai country checks throughout

## Database Statistics

- **Total Countries**: 252
- **Standard ISO Codes**: 249
- **ETDA Extensions**: 3 (AN inactive, KS active, UN active)
- **ASEAN Countries**: 10
- **Major Trading Partners**: 11
- **Database Views**: 4
- **Helper Functions**: 1
- **Indexes**: 4
- **Repository Methods**: 27
- **Adapter Static Methods**: 11
- **Entity Business Methods**: 14

## Code Organization

```
src/main/java/com/wpanther/etax/
├── entity/
│   └── ISOCountryCode.java                   (JPA entity, 14 business methods)
├── repository/
│   └── ISOCountryCodeRepository.java         (27 query methods)
├── adapter/
│   └── ISOCountryCodeAdapter.java            (JAXB adapter with enum, 11 static helpers)
└── xml/
    └── isocountry/
        ├── ISOCountryCodeType.java           (Custom JAXB type)
        └── package-info.java                 (Namespace configuration)

sql/
├── iso_country_code.sql                      (Schema: table, views, function)
└── iso_country_code_data.sql                 (249 standard ISO codes)
```

## Testing Recommendations

### 1. Enum Conversion Tests
```java
@Test
void testEnumConversion() {
    ISOTwoletterCountryCodeContentType enumTH = ISOTwoletterCountryCodeContentType.TH;
    assertEquals("TH", enumTH.name());

    ISOCountryCode thailand = repository.findByCode("TH").orElseThrow();
    assertEquals("THAILAND", thailand.getName());
}
```

### 2. Country Group Tests
```java
@Test
void testASEANCountries() {
    List<ISOCountryCode> asean = repository.findASEANCountries();
    assertEquals(10, asean.size());

    Set<String> codes = asean.stream()
        .map(ISOCountryCode::getCode)
        .collect(Collectors.toSet());

    assertTrue(codes.containsAll(Arrays.asList(
        "TH", "BN", "KH", "ID", "LA", "MY", "MM", "PH", "SG", "VN"
    )));
}
```

### 3. JAXB Marshalling Tests
```java
@Test
void testMarshalling() throws Exception {
    ISOCountryCode thailand = new ISOCountryCode("TH", "THAILAND");
    ISOCountryCodeAdapter adapter = new ISOCountryCodeAdapter();

    ISOTwoletterCountryCodeContentType enumValue = adapter.marshal(thailand);
    assertEquals(ISOTwoletterCountryCodeContentType.TH, enumValue);
}

@Test
void testUnmarshalling() throws Exception {
    ISOCountryCodeAdapter adapter = new ISOCountryCodeAdapter();

    ISOCountryCode thailand = adapter.unmarshal(ISOTwoletterCountryCodeContentType.TH);
    assertEquals("TH", thailand.getCode());
    assertEquals("THAILAND", thailand.getName());
}
```

### 4. ETDA Extension Tests
```java
@Test
void testETDAExtensions() {
    List<ISOCountryCode> extensions = repository.findETDAExtensions();
    assertEquals(3, extensions.size());

    // Check Kosovo is active
    ISOCountryCode kosovo = repository.findByCode("KS").orElseThrow();
    assertTrue(kosovo.isETDAExtension());
    assertTrue(kosovo.isActiveCountry());

    // Check Netherlands Antilles is inactive
    ISOCountryCode an = repository.findByCode("AN").orElseThrow();
    assertTrue(an.isETDAExtension());
    assertFalse(an.isActiveCountry());
}
```

## Common Country Codes Reference

### ASEAN Countries (10)
| Code | Name | Usage |
|------|------|-------|
| TH | THAILAND | Primary Thai country |
| BN | BRUNEI DARUSSALAM | ASEAN member |
| KH | CAMBODIA | ASEAN member |
| ID | INDONESIA | ASEAN member |
| LA | LAO PEOPLE'S DEMOCRATIC REPUBLIC | ASEAN member |
| MY | MALAYSIA | ASEAN member |
| MM | MYANMAR | ASEAN member |
| PH | PHILIPPINES | ASEAN member |
| SG | SINGAPORE | ASEAN member & trading partner |
| VN | VIET NAM | ASEAN member |

### Major Trading Partners (11)
| Code | Name | Region |
|------|------|--------|
| CN | CHINA | East Asia |
| JP | JAPAN | East Asia |
| KR | KOREA, REPUBLIC OF | East Asia |
| US | UNITED STATES | North America |
| GB | UNITED KINGDOM | Europe |
| DE | GERMANY | Europe |
| AU | AUSTRALIA | Oceania |
| IN | INDIA | South Asia |
| TW | TAIWAN, PROVINCE OF CHINA | East Asia |
| HK | HONG KONG | East Asia |
| SG | SINGAPORE | Southeast Asia (also ASEAN) |

### ETDA Extensions (3)
| Code | Name | Status | Note |
|------|------|--------|------|
| AN | NETHERLANDS ANTILLES | Inactive | Historical code |
| KS | KOSOVO | Active | Not in standard ISO 3166 |
| UN | UNITED NATIONS | Active | Not in standard ISO 3166 |

## Migration Checklist

- [x] Database schema created (`iso_country_code.sql`)
- [x] Database data loaded (`iso_country_code_data.sql` - 249 + 3 codes)
- [x] JPA entity created (`ISOCountryCode.java`)
- [x] Repository interface created (`ISOCountryCodeRepository.java`)
- [x] JAXB adapter created (`ISOCountryCodeAdapter.java` with enum support)
- [x] Custom JAXB type created (`ISOCountryCodeType.java`)
- [x] Package configuration created (`package-info.java`)
- [x] Business logic methods implemented (14 in entity)
- [x] Static helper methods implemented (11 in adapter)
- [x] ASEAN country group support
- [x] Major trading partner group support
- [x] ETDA extension handling
- [ ] Unit tests for country groups
- [ ] Integration tests for JAXB marshalling with enum
- [ ] Update existing code to use new types
- [ ] Remove old JAXB-generated code
- [ ] Documentation updated

## Conclusion

The ISO 3166-1 Country Code migration successfully transforms JAXB-generated enum code into an efficient database-backed implementation with 252 unique country codes. The enum-based pattern (same as currency code) provides type safety, while specialized views and helper functions enable powerful querying capabilities for ASEAN countries, major trading partners, and ETDA custom extensions.
