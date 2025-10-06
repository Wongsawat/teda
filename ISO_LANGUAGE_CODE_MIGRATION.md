# ISO 639-1 Two-letter Language Code Migration Guide

## Overview

This document describes the migration from JAXB-generated language code handling to a **database-backed implementation** for ISO 639-1 Two-letter Language Codes in the e-Tax Invoice system.

**Standard**: ISO 639-1 alpha-2
**Code List Version**: 2006-10-27
**Total Languages**: 185 unique language codes
**XSD Entries**: 370 (both lowercase and UPPERCASE variants)
**Namespace**: `urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27`

### Key Feature: Case-Insensitive Handling

The XSD contains **both lowercase and UPPERCASE variants** for each language (e.g., 'th' and 'TH', 'en' and 'EN'). The database implementation handles this elegantly:
- **Stores codes in lowercase** (normalized format)
- **Generated columns** for uppercase/lowercase variants
- **Case-insensitive queries** work seamlessly
- **Both 'th' and 'TH' resolve to the same entity**

## Migration Components

### 1. Database Schema (`iso_language_code.sql`)

**Table Structure**:
```sql
CREATE TABLE iso_language_code (
    code VARCHAR(2) PRIMARY KEY,              -- stored in lowercase (e.g., 'th', 'en')
    name VARCHAR(255) NOT NULL,               -- language name (e.g., 'Thai', 'English')
    code_upper VARCHAR(2) GENERATED ALWAYS AS (UPPER(code)) STORED,
    code_lower VARCHAR(2) GENERATED ALWAYS AS (LOWER(code)) STORED,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Key Design Decisions**:
- **Primary key is lowercase** - Normalized format for consistency
- **Generated columns** - Automatic uppercase/lowercase for queries
- **No duplicate storage** - Only 185 rows (not 370) despite XSD having both cases
- **Indexing** - Supports fast case-insensitive lookups

**Indexes**:
```sql
CREATE INDEX idx_iso_language_code_upper ON iso_language_code(code_upper);
CREATE INDEX idx_iso_language_code_lower ON iso_language_code(code_lower);
CREATE INDEX idx_iso_language_name ON iso_language_code(name);
CREATE INDEX idx_iso_language_active ON iso_language_code(is_active);
```

**Views** (3 specialized views):

1. **ASEAN Languages** (9 languages):
```sql
CREATE VIEW v_iso_language_asean AS
SELECT * FROM iso_language_code
WHERE code IN ('th', 'en', 'ms', 'id', 'vi', 'my', 'km', 'lo', 'tl')
  AND is_active = TRUE
ORDER BY CASE code
    WHEN 'th' THEN 1
    WHEN 'en' THEN 2
    ELSE 3 END, name;
```

2. **Major Trading Partner Languages** (10 languages):
```sql
CREATE VIEW v_iso_language_major_trading AS
SELECT * FROM iso_language_code
WHERE code IN ('en', 'th', 'zh', 'ja', 'ko', 'de', 'fr', 'es', 'ar', 'ru')
  AND is_active = TRUE
ORDER BY CASE code
    WHEN 'th' THEN 1
    WHEN 'en' THEN 2
    WHEN 'zh' THEN 3
    WHEN 'ja' THEN 4
    WHEN 'ko' THEN 5
    ELSE 6 END, name;
```

3. **Active Languages**:
```sql
CREATE VIEW v_iso_language_active AS
SELECT * FROM iso_language_code
WHERE is_active = TRUE
ORDER BY name;
```

**Helper Functions** (3 utility functions):

1. **Get Language Name** (case-insensitive):
```sql
CREATE OR REPLACE FUNCTION get_language_name(p_code VARCHAR(2))
RETURNS VARCHAR(255) AS $$
BEGIN
    RETURN (SELECT name FROM iso_language_code WHERE LOWER(code) = LOWER(p_code) AND is_active = TRUE);
END;
$$ LANGUAGE plpgsql;
```

2. **Check Valid Language Code** (case-insensitive):
```sql
CREATE OR REPLACE FUNCTION is_valid_language_code(p_code VARCHAR(2))
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS(SELECT 1 FROM iso_language_code WHERE LOWER(code) = LOWER(p_code) AND is_active = TRUE);
END;
$$ LANGUAGE plpgsql;
```

3. **Normalize Language Code** (to lowercase):
```sql
CREATE OR REPLACE FUNCTION normalize_language_code(p_code VARCHAR(2))
RETURNS VARCHAR(2) AS $$
BEGIN
    RETURN LOWER(TRIM(p_code));
END;
$$ LANGUAGE plpgsql;
```

### 2. Database Data (`iso_language_code_data.sql`)

**Data Loading**:
- **185 INSERT statements** (deduplicated from 370 XSD entries)
- Only lowercase codes inserted
- Generated columns automatically create uppercase variants

**Sample Data**:
```sql
INSERT INTO iso_language_code (code, name, is_active) VALUES
('aa', 'Afar', TRUE),
('ab', 'Abkhazian', TRUE),
('ar', 'Arabic', TRUE),
('en', 'English', TRUE),
('id', 'Indonesian', TRUE),
('ja', 'Japanese', TRUE),
('km', 'Khmer', TRUE),
('ko', 'Korean', TRUE),
('lo', 'Lao', TRUE),
('ms', 'Malay', TRUE),
('my', 'Burmese', TRUE),
('th', 'Thai', TRUE),
('tl', 'Tagalog', TRUE),
('vi', 'Vietnamese', TRUE),
('zh', 'Chinese', TRUE),
-- ... 170 more languages
```

**Common Language Groups**:

**ASEAN Languages** (9):
- `th` - Thai
- `en` - English
- `ms` - Malay
- `id` - Indonesian
- `vi` - Vietnamese
- `my` - Burmese
- `km` - Khmer
- `lo` - Lao
- `tl` - Tagalog

**Major Trading Partner Languages** (10):
- `en` - English
- `th` - Thai
- `zh` - Chinese
- `ja` - Japanese
- `ko` - Korean
- `de` - German
- `fr` - French
- `es` - Spanish
- `ar` - Arabic
- `ru` - Russian

### 3. JPA Entity (`com.wpanther.etax.entity.ISOLanguageCode`)

**Entity Class**:
```java
@Entity
@Table(name = "iso_language_code")
public class ISOLanguageCode {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;  // Stored in lowercase

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "code_upper", length = 2, insertable = false, updatable = false)
    private String codeUpper;  // Generated column

    @Column(name = "code_lower", length = 2, insertable = false, updatable = false)
    private String codeLower;  // Generated column

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructor with normalization
    public ISOLanguageCode(String code) {
        this.code = normalizeCode(code);  // Always lowercase
    }

    private String normalizeCode(String code) {
        if (code == null) return null;
        return code.trim().toLowerCase();  // Normalize to lowercase
    }
}
```

**Business Logic Methods** (6 methods):

```java
// Language type checks
public boolean isThai() { return "th".equals(code); }
public boolean isEnglish() { return "en".equals(code); }
public boolean isChinese() { return "zh".equals(code); }
public boolean isJapanese() { return "ja".equals(code); }

// Language group checks
public boolean isASEANLanguage() {
    return "th".equals(code) || "en".equals(code) || "ms".equals(code) ||
           "id".equals(code) || "vi".equals(code) || "my".equals(code) ||
           "km".equals(code) || "lo".equals(code) || "tl".equals(code);
}

public boolean isMajorTradingLanguage() {
    return "en".equals(code) || "th".equals(code) || "zh".equals(code) ||
           "ja".equals(code) || "ko".equals(code) || "de".equals(code) ||
           "fr".equals(code) || "es".equals(code) || "ar".equals(code) ||
           "ru".equals(code);
}
```

### 4. Spring Data Repository (`com.wpanther.etax.repository.ISOLanguageCodeRepository`)

**Repository Interface**:
```java
@Repository
public interface ISOLanguageCodeRepository extends JpaRepository<ISOLanguageCode, String> {

    // Case-insensitive lookup (handles both 'th' and 'TH')
    @Query("SELECT l FROM ISOLanguageCode l WHERE LOWER(l.code) = LOWER(:code) AND l.isActive = true")
    Optional<ISOLanguageCode> findByCode(@Param("code") String code);

    // All active languages
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.isActive = true ORDER BY l.name")
    List<ISOLanguageCode> findAllActive();

    // ASEAN languages (9 languages)
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code IN ('th', 'en', 'ms', 'id', 'vi', 'my', 'km', 'lo', 'tl') AND l.isActive = true ORDER BY l.name")
    List<ISOLanguageCode> findASEANLanguages();

    // Major trading partner languages (10 languages)
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code IN ('en', 'th', 'zh', 'ja', 'ko', 'de', 'fr', 'es', 'ar', 'ru') AND l.isActive = true ORDER BY " +
           "CASE l.code " +
           "WHEN 'th' THEN 1 " +
           "WHEN 'en' THEN 2 " +
           "WHEN 'zh' THEN 3 " +
           "WHEN 'ja' THEN 4 " +
           "WHEN 'ko' THEN 5 " +
           "ELSE 6 END, l.name")
    List<ISOLanguageCode> findMajorTradingLanguages();

    // Search by name (case-insensitive)
    @Query("SELECT l FROM ISOLanguageCode l WHERE UPPER(l.name) LIKE UPPER(CONCAT('%', :name, '%')) AND l.isActive = true ORDER BY l.name")
    List<ISOLanguageCode> findByNameContaining(@Param("name") String name);

    // Check existence (case-insensitive)
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM ISOLanguageCode l WHERE LOWER(l.code) = LOWER(:code) AND l.isActive = true")
    boolean existsByCode(@Param("code") String code);
}
```

**Total Query Methods**: 22

**Specific Language Finders** (13 methods):
- `findThai()` - th
- `findEnglish()` - en
- `findChinese()` - zh
- `findJapanese()` - ja
- `findKorean()` - ko
- `findIndonesian()` - id
- `findMalay()` - ms
- `findVietnamese()` - vi
- `findBurmese()` - my
- `findKhmer()` - km
- `findLao()` - lo
- `findTagalog()` - tl
- Plus German, French, Spanish, Arabic, Russian

### 5. JAXB Adapter (`com.wpanther.etax.adapter.ISOLanguageCodeAdapter`)

**Adapter Class**:
```java
@Component
public class ISOLanguageCodeAdapter extends XmlAdapter<String, ISOLanguageCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOLanguageCodeAdapter.class);
    private static ISOLanguageCodeRepository repository;

    @Autowired
    public void setRepository(ISOLanguageCodeRepository repository) {
        ISOLanguageCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOLanguageCode entity to XML String (code)
     */
    @Override
    public String marshal(ISOLanguageCode entity) throws Exception {
        if (entity == null) return null;
        String code = entity.getCode();
        log.debug("Marshalling ISOLanguageCode: {} -> {}", entity.getName(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to ISOLanguageCode entity from database
     * CASE-INSENSITIVE: Both 'th' and 'TH' work
     */
    @Override
    public ISOLanguageCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) return null;

        String trimmedCode = code.trim();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Case-insensitive lookup (handles both 'th' and 'TH')
        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Language code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    private ISOLanguageCode createPlaceholder(String code) {
        ISOLanguageCode placeholder = new ISOLanguageCode(code);
        placeholder.setName("Unknown Language: " + code);
        placeholder.setIsActive(false);
        return placeholder;
    }
}
```

**Static Helper Methods** (10 methods):

```java
// Validation
public static boolean isValid(String code) {
    if (repository == null || code == null || code.trim().isEmpty()) return false;
    return repository.existsByCode(code.trim());  // Case-insensitive
}

// Language name lookup
public static String getName(String code) {
    if (repository == null || code == null || code.trim().isEmpty()) return null;
    return repository.findByCode(code.trim())
            .map(ISOLanguageCode::getName)
            .orElse(null);
}

// Code normalization
public static String normalize(String code) {
    if (code == null || code.trim().isEmpty()) return null;
    return code.trim().toLowerCase();  // Always lowercase
}

// Language type checks (case-insensitive)
public static boolean isThai(String code) {
    return "th".equalsIgnoreCase(code != null ? code.trim() : null);
}

public static boolean isEnglish(String code) {
    return "en".equalsIgnoreCase(code != null ? code.trim() : null);
}

public static boolean isChinese(String code) {
    return "zh".equalsIgnoreCase(code != null ? code.trim() : null);
}

public static boolean isJapanese(String code) {
    return "ja".equalsIgnoreCase(code != null ? code.trim() : null);
}

// Language group checks
public static boolean isASEANLanguage(String code) {
    if (repository == null || code == null || code.trim().isEmpty()) return false;
    return repository.findByCode(code.trim())
            .map(ISOLanguageCode::isASEANLanguage)
            .orElse(false);
}

public static boolean isMajorTradingLanguage(String code) {
    if (repository == null || code == null || code.trim().isEmpty()) return false;
    return repository.findByCode(code.trim())
            .map(ISOLanguageCode::isMajorTradingLanguage)
            .orElse(false);
}
```

### 6. Custom JAXB Type (`com.wpanther.etax.xml.isolanguage.ISOLanguageCodeType`)

**JAXB Type Class**:
```java
@XmlAccessorType(XmlAccessType.FIELD)
public class ISOLanguageCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOLanguageCodeAdapter.class)
    private ISOLanguageCode value;

    // Constructors
    public ISOLanguageCodeType() {}

    public ISOLanguageCodeType(ISOLanguageCode value) {
        this.value = value;
    }

    public ISOLanguageCodeType(String code) {
        this.value = ISOLanguageCodeAdapter.isValid(code)
            ? new ISOLanguageCode(code, ISOLanguageCodeAdapter.getName(code))
            : new ISOLanguageCode(code);
    }

    // Factory methods
    public static ISOLanguageCodeType of(String code) {
        return new ISOLanguageCodeType(code);
    }

    public static ISOLanguageCodeType thai() { return of("th"); }
    public static ISOLanguageCodeType english() { return of("en"); }
    public static ISOLanguageCodeType chinese() { return of("zh"); }
    public static ISOLanguageCodeType japanese() { return of("ja"); }

    // Business logic delegation
    public boolean isThai() { return value != null && value.isThai(); }
    public boolean isEnglish() { return value != null && value.isEnglish(); }
    public boolean isASEANLanguage() { return value != null && value.isASEANLanguage(); }
    public boolean isMajorTradingLanguage() { return value != null && value.isMajorTradingLanguage(); }
}
```

### 7. Package Configuration (`com.wpanther.etax.xml.isolanguage.package-info`)

```java
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "clm5ISO63912A",
               namespaceURI = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27")
    }
)
package com.wpanther.etax.xml.isolanguage;
```

## Usage Examples

### Case-Insensitive Handling

```java
// All of these work and resolve to the same entity
ISOLanguageCodeType thai1 = ISOLanguageCodeType.of("th");   // Lowercase
ISOLanguageCodeType thai2 = ISOLanguageCodeType.of("TH");   // Uppercase
ISOLanguageCodeType thai3 = ISOLanguageCodeType.of("Th");   // Mixed case

// All resolve to the same database entity with code="th"
assert thai1.getCode().equals("th");  // Normalized to lowercase
assert thai2.getCode().equals("th");  // Normalized to lowercase
assert thai3.getCode().equals("th");  // Normalized to lowercase
```

### Basic Language Operations

```java
// Create language codes
ISOLanguageCodeType thai = ISOLanguageCodeType.thai();
ISOLanguageCodeType english = ISOLanguageCodeType.english();

// Get language information
String code = thai.getCode();           // "th"
String name = thai.getName();           // "Thai"

// Check language type (case-insensitive)
if (ISOLanguageCodeAdapter.isThai("TH")) {  // Works with uppercase
    System.out.println("Thai invoice detected");
}

// Validate codes (case-insensitive)
boolean valid1 = ISOLanguageCodeAdapter.isValid("th");  // true
boolean valid2 = ISOLanguageCodeAdapter.isValid("TH");  // true
boolean valid3 = ISOLanguageCodeAdapter.isValid("xx");  // false
```

### Language Group Checks

```java
// Check ASEAN languages
ISOLanguageCodeType vietnamese = ISOLanguageCodeType.of("vi");
if (vietnamese.isASEANLanguage()) {
    System.out.println("ASEAN country detected");
}

// Check major trading languages
ISOLanguageCodeType chinese = ISOLanguageCodeType.of("ZH");  // Uppercase works
if (chinese.isMajorTradingLanguage()) {
    System.out.println("Major trading partner: " + chinese.getName());
}
```

### Repository Queries

```java
@Autowired
private ISOLanguageCodeRepository repository;

// Find by code (case-insensitive)
Optional<ISOLanguageCode> thai = repository.findByCode("th");
Optional<ISOLanguageCode> thaiUpper = repository.findByCode("TH");  // Same result

// Get ASEAN languages
List<ISOLanguageCode> asean = repository.findASEANLanguages();
// Returns: th, en, ms, id, vi, my, km, lo, tl

// Get major trading languages
List<ISOLanguageCode> trading = repository.findMajorTradingLanguages();
// Returns: th, en, zh, ja, ko, de, fr, es, ar, ru (ordered)

// Search by name
List<ISOLanguageCode> languages = repository.findByNameContaining("chinese");
// Returns: ISOLanguageCode{code='zh', name='Chinese'}

// Get specific language
Optional<ISOLanguageCode> japanese = repository.findJapanese();
```

### XML Marshalling/Unmarshalling

```java
// In e-Tax Invoice classes
@XmlElement(namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27")
private ISOLanguageCodeType languageID;

// Setting language (case-insensitive)
invoice.setLanguageID(ISOLanguageCodeType.of("th"));   // Lowercase
invoice.setLanguageID(ISOLanguageCodeType.of("TH"));   // Uppercase - same result

// XML output (always lowercase)
// <languageID>th</languageID>

// XML input (case-insensitive)
// Both <languageID>th</languageID> and <languageID>TH</languageID> work
```

### Database Helper Functions

```sql
-- Get language name (case-insensitive)
SELECT get_language_name('th');    -- Returns: 'Thai'
SELECT get_language_name('TH');    -- Returns: 'Thai' (same)

-- Validate code (case-insensitive)
SELECT is_valid_language_code('en');   -- Returns: true
SELECT is_valid_language_code('EN');   -- Returns: true
SELECT is_valid_language_code('xx');   -- Returns: false

-- Normalize code
SELECT normalize_language_code('TH');  -- Returns: 'th'
SELECT normalize_language_code('En');  -- Returns: 'en'
```

### Using Database Views

```sql
-- ASEAN languages view
SELECT * FROM v_iso_language_asean;
-- Returns: th, en, ms, id, vi, my, km, lo, tl (ordered)

-- Major trading languages view
SELECT * FROM v_iso_language_major_trading;
-- Returns: th, en, zh, ja, ko, de, fr, es, ar, ru (priority ordered)

-- All active languages view
SELECT * FROM v_iso_language_active;
-- Returns: All 185 active languages (alphabetically ordered)
```

## Migration Benefits

### 1. Case-Insensitive Flexibility
- **XSD has 370 entries** (both cases), **database has 185 rows** (deduplicated)
- Both 'th' and 'TH' work seamlessly
- Normalized storage (lowercase) with generated columns for queries
- No duplicate data or logic

### 2. Database-Backed Validation
- Language codes validated against database
- Invalid codes create placeholder entities (graceful degradation)
- Active/inactive status support
- Extensible without code changes

### 3. Enhanced Querying
- **3 specialized views** (ASEAN, major trading, active)
- **22 repository methods** for common operations
- **3 helper functions** for SQL-level operations
- Full-text search on language names

### 4. Business Logic Integration
- Language type checks (Thai, English, Chinese, Japanese)
- Language group checks (ASEAN, major trading)
- Language name resolution
- Normalization utilities

### 5. Thai e-Tax Specific Features
- **ASEAN language group** - Regional integration
- **Major trading language group** - International trade
- **Thai as priority** - Local market focus
- **English as secondary** - International standard

## Database Statistics

- **Total Languages**: 185
- **ASEAN Languages**: 9 (th, en, ms, id, vi, my, km, lo, tl)
- **Major Trading Languages**: 10 (en, th, zh, ja, ko, de, fr, es, ar, ru)
- **Database Views**: 3 (ASEAN, major trading, active)
- **Helper Functions**: 3 (name, validation, normalization)
- **Indexes**: 4 (code_upper, code_lower, name, is_active)
- **Repository Methods**: 22
- **Adapter Static Methods**: 10
- **Entity Business Methods**: 6

## Code Organization

```
src/main/java/com/wpanther/etax/
├── entity/
│   └── ISOLanguageCode.java                  (JPA entity, 6 business methods)
├── repository/
│   └── ISOLanguageCodeRepository.java        (22 query methods)
├── adapter/
│   └── ISOLanguageCodeAdapter.java           (JAXB adapter, 10 static helpers)
└── xml/
    └── isolanguage/
        ├── ISOLanguageCodeType.java          (Custom JAXB type)
        └── package-info.java                 (Namespace configuration)

sql/
├── iso_language_code.sql                     (Schema: table, views, functions)
└── iso_language_code_data.sql                (185 INSERT statements)
```

## Testing Recommendations

### 1. Case-Insensitive Tests
```java
@Test
void testCaseInsensitiveLookup() {
    // Test lowercase
    ISOLanguageCode thai1 = repository.findByCode("th").orElseThrow();
    assertEquals("th", thai1.getCode());

    // Test uppercase
    ISOLanguageCode thai2 = repository.findByCode("TH").orElseThrow();
    assertEquals("th", thai2.getCode());

    // Same entity
    assertEquals(thai1, thai2);
}
```

### 2. Language Group Tests
```java
@Test
void testASEANLanguages() {
    List<ISOLanguageCode> asean = repository.findASEANLanguages();
    assertEquals(9, asean.size());

    Set<String> codes = asean.stream()
        .map(ISOLanguageCode::getCode)
        .collect(Collectors.toSet());

    assertTrue(codes.containsAll(Arrays.asList(
        "th", "en", "ms", "id", "vi", "my", "km", "lo", "tl"
    )));
}
```

### 3. JAXB Marshalling Tests
```java
@Test
void testMarshalling() throws Exception {
    ISOLanguageCode thai = new ISOLanguageCode("th", "Thai");
    ISOLanguageCodeAdapter adapter = new ISOLanguageCodeAdapter();

    String marshalled = adapter.marshal(thai);
    assertEquals("th", marshalled);  // Always lowercase
}

@Test
void testUnmarshallingCaseInsensitive() throws Exception {
    ISOLanguageCodeAdapter adapter = new ISOLanguageCodeAdapter();

    // Test lowercase
    ISOLanguageCode thai1 = adapter.unmarshal("th");
    assertEquals("th", thai1.getCode());

    // Test uppercase
    ISOLanguageCode thai2 = adapter.unmarshal("TH");
    assertEquals("th", thai2.getCode());
}
```

### 4. Normalization Tests
```java
@Test
void testNormalization() {
    assertEquals("th", ISOLanguageCodeAdapter.normalize("TH"));
    assertEquals("en", ISOLanguageCodeAdapter.normalize("EN"));
    assertEquals("th", ISOLanguageCodeAdapter.normalize("  th  "));
    assertNull(ISOLanguageCodeAdapter.normalize(null));
}
```

## Common Language Codes Reference

### ASEAN Languages (9)
| Code | Name | Usage |
|------|------|-------|
| th | Thai | Primary Thai language |
| en | English | International business |
| ms | Malay | Malaysia, Brunei |
| id | Indonesian | Indonesia |
| vi | Vietnamese | Vietnam |
| my | Burmese | Myanmar |
| km | Khmer | Cambodia |
| lo | Lao | Laos |
| tl | Tagalog | Philippines |

### Major Trading Languages (10)
| Code | Name | Region/Country |
|------|------|----------------|
| th | Thai | Thailand |
| en | English | International |
| zh | Chinese | China |
| ja | Japanese | Japan |
| ko | Korean | Korea |
| de | German | Germany |
| fr | French | France |
| es | Spanish | Spain |
| ar | Arabic | Middle East |
| ru | Russian | Russia |

## Migration Checklist

- [x] Database schema created (`iso_language_code.sql`)
- [x] Database data loaded (`iso_language_code_data.sql` - 185 rows)
- [x] JPA entity created (`ISOLanguageCode.java`)
- [x] Repository interface created (`ISOLanguageCodeRepository.java`)
- [x] JAXB adapter created (`ISOLanguageCodeAdapter.java`)
- [x] Custom JAXB type created (`ISOLanguageCodeType.java`)
- [x] Package configuration created (`package-info.java`)
- [x] Case-insensitive handling implemented
- [x] Generated columns for uppercase/lowercase
- [x] ASEAN language group support
- [x] Major trading language group support
- [ ] Unit tests for case-insensitive lookup
- [ ] Unit tests for language groups
- [ ] Integration tests for JAXB marshalling
- [ ] Update existing code to use new types
- [ ] Remove old JAXB-generated code
- [ ] Documentation updated

## Conclusion

The ISO 639-1 Language Code migration successfully transforms JAXB-generated code (with 370 duplicate entries) into an efficient database-backed implementation with only 185 unique language codes. The case-insensitive design ensures both 'th' and 'TH' work seamlessly, while specialized views and helper functions provide powerful querying capabilities for ASEAN and major trading partner languages.
