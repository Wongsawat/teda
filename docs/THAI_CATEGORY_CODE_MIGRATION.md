# Thai Category Code Migration Guide

## Overview

This document describes the migration from JAXB-generated category code handling to a **database-backed implementation** for ETDA Thai Category Codes in the e-Tax Invoice system.

**Standard**: ETDA Thai Category Code
**Code List Version**: 1.0
**Schema Date**: Feb 2025
**Total Codes**: 2 (01, 02)
**Namespace**: `urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1`
**Code Format**: 2-digit numeric codes

### JAXB Pattern: JAXBElement<String>

Unlike currency and country codes (which use enums), the Thai Category Code uses a **String pattern** because it only has 2 codes. JAXB did not generate an enum for this small code list.

```java
JAXBElement<String>  // String-based, not enum
```

## Migration Components

### 1. Database Schema (`thai_category_code.sql`)

**Table Structure**:
```sql
CREATE TABLE thai_category_code (
    code VARCHAR(2) PRIMARY KEY,           -- '01', '02'
    name_th VARCHAR(500) NOT NULL,         -- Thai description
    name_en VARCHAR(500),                  -- English translation
    description TEXT,                      -- Detailed explanation
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Key Design Decisions**:
- **Primary key is 2-digit code** - Simple numeric codes
- **Bilingual support** - Both Thai (name_th) and English (name_en) names
- **Detailed descriptions** - Explain when to use each category
- **No active status** - Both codes are always active
- **No check constraint** - Not needed for just 2 codes

**Indexes**:
```sql
CREATE INDEX idx_thai_category_code_name_th ON thai_category_code(name_th);
CREATE INDEX idx_thai_category_code_name_en ON thai_category_code(name_en);
```

**View**:
```sql
CREATE VIEW thai_category_code_active AS
SELECT code, name_th, name_en, description
FROM thai_category_code
ORDER BY code;
```

**Data** (2 codes embedded in schema file):

**Code 01** - Original Document Reference:
```sql
INSERT INTO thai_category_code (code, name_th, name_en, description) VALUES
('01',
 'อ้างอิงเอกสารฉบับเดิมกรณี 1.ยกเลิก 2.เพิ่มหนี้ 3.ลดหนี้',
 'Reference to original document for: 1. Cancellation 2. Debit note 3. Credit note',
 'Used when referencing the original invoice document for cases of cancellation, creating debit notes (increasing debt), or creating credit notes (decreasing debt). This category is essential for document amendment and correction workflows.');
```

**Code 02** - Advance Payment Reference:
```sql
INSERT INTO thai_category_code (code, name_th, name_en, description) VALUES
('02',
 'อ้างอิงเอกสารเพื่อออกเงินรับล่วงหน้า',
 'Reference to document for advance payment receipt',
 'Used when referencing documents related to advance payments or prepayments. This category is used when issuing receipts for payments received before the actual goods/services delivery or final invoice.');
```

### 2. JPA Entity (`com.wpanther.etax.entity.ThaiCategoryCode`)

**Entity Class**:
```java
@Entity
@Table(name = "thai_category_code")
public class ThaiCategoryCode {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;  // '01' or '02'

    @Column(name = "name_th", nullable = false, length = 500)
    private String nameTh;

    @Column(name = "name_en", length = 500)
    private String nameEn;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

**Business Logic Methods** (2 methods):

```java
/**
 * Check if this is code 01 - Reference to original document for cancellation/debit/credit
 */
public boolean isOriginalDocumentReference() {
    return "01".equals(code);
}

/**
 * Check if this is code 02 - Reference to document for advance payment
 */
public boolean isAdvancePaymentReference() {
    return "02".equals(code);
}
```

### 3. Spring Data Repository (`com.wpanther.etax.repository.ThaiCategoryCodeRepository`)

**Repository Interface**:
```java
@Repository
public interface ThaiCategoryCodeRepository extends JpaRepository<ThaiCategoryCode, String> {

    // Basic queries
    @Query("SELECT c FROM ThaiCategoryCode c WHERE UPPER(c.code) = UPPER(:code)")
    Optional<ThaiCategoryCode> findByCode(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ThaiCategoryCode c WHERE UPPER(c.code) = UPPER(:code)")
    boolean existsByCode(@Param("code") String code);

    // Specific category finders
    @Query("SELECT c FROM ThaiCategoryCode c WHERE c.code = '01'")
    Optional<ThaiCategoryCode> findOriginalDocumentReference();

    @Query("SELECT c FROM ThaiCategoryCode c WHERE c.code = '02'")
    Optional<ThaiCategoryCode> findAdvancePaymentReference();
}
```

**Total Query Methods**: 4
- 2 basic queries (find, exists)
- 2 specific category finders

### 4. JAXB Adapter (`com.wpanther.etax.adapter.ThaiCategoryCodeAdapter`)

**Adapter Class**:
```java
@Component
public class ThaiCategoryCodeAdapter extends XmlAdapter<String, ThaiCategoryCode> {

    private static final Logger log = LoggerFactory.getLogger(ThaiCategoryCodeAdapter.class);
    private static ThaiCategoryCodeRepository repository;

    @Autowired
    public void setRepository(ThaiCategoryCodeRepository repository) {
        ThaiCategoryCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ThaiCategoryCode entity to XML String (code)
     */
    @Override
    public String marshal(ThaiCategoryCode entity) throws Exception {
        if (entity == null) return null;
        String code = entity.getCode();
        log.debug("Marshalling ThaiCategoryCode: {} -> {}", entity.getNameEn(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to ThaiCategoryCode entity from database
     */
    @Override
    public ThaiCategoryCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) return null;

        String trimmedCode = code.trim();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Category code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    private ThaiCategoryCode createPlaceholder(String code) {
        ThaiCategoryCode placeholder = new ThaiCategoryCode(code);
        placeholder.setNameTh("Unknown Category Code: " + code);
        placeholder.setNameEn("Unknown Category Code: " + code);
        placeholder.setDescription("Placeholder for unknown category code");
        return placeholder;
    }
}
```

**Static Helper Methods** (5 methods):

```java
// Validation
public static boolean isValid(String code);
public static String getNameTh(String code);
public static String getNameEn(String code);

// Category type checks
public static boolean isOriginalDocumentReference(String code);
public static boolean isAdvancePaymentReference(String code);
```

### 5. Custom JAXB Type (`com.wpanther.etax.xml.thaicategory.ThaiCategoryCodeType`)

**JAXB Type Class**:
```java
@XmlAccessorType(XmlAccessType.FIELD)
public class ThaiCategoryCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ThaiCategoryCodeAdapter.class)
    private ThaiCategoryCode value;

    // Constructors
    public ThaiCategoryCodeType() {}
    public ThaiCategoryCodeType(ThaiCategoryCode value) { this.value = value; }
    public ThaiCategoryCodeType(String code) {
        this.value = ThaiCategoryCodeAdapter.isValid(code)
            ? new ThaiCategoryCode(code, ThaiCategoryCodeAdapter.getNameTh(code), ThaiCategoryCodeAdapter.getNameEn(code))
            : new ThaiCategoryCode(code);
    }

    // Factory methods
    public static ThaiCategoryCodeType of(String code) { return new ThaiCategoryCodeType(code); }
    public static ThaiCategoryCodeType originalDocumentReference() { return of("01"); }
    public static ThaiCategoryCodeType advancePaymentReference() { return of("02"); }

    // Business logic delegation
    public boolean isOriginalDocumentReference() { return value != null && value.isOriginalDocumentReference(); }
    public boolean isAdvancePaymentReference() { return value != null && value.isAdvancePaymentReference(); }
    public String getNameTh() { return value != null ? value.getNameTh() : null; }
    public String getNameEn() { return value != null ? value.getNameEn() : null; }
}
```

### 6. Package Configuration (`com.wpanther.etax.xml.thaicategory.package-info`)

```java
@XmlSchema(
    namespace = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "thaiCategory",
               namespaceURI = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1")
    }
)
package com.wpanther.etax.xml.thaicategory;
```

## Usage Examples

### Basic Category Operations

```java
// Create category codes
ThaiCategoryCodeType cancellation = ThaiCategoryCodeType.originalDocumentReference();
ThaiCategoryCodeType advance = ThaiCategoryCodeType.advancePaymentReference();
ThaiCategoryCodeType custom = ThaiCategoryCodeType.of("01");

// Get category information
String code = cancellation.getCode();       // "01"
String nameTh = cancellation.getNameTh();   // Thai description
String nameEn = cancellation.getNameEn();   // English description

// Check category type
if (ThaiCategoryCodeAdapter.isOriginalDocumentReference("01")) {
    System.out.println("Document amendment category");
}

// Validate codes
boolean valid = ThaiCategoryCodeAdapter.isValid("01");    // true
boolean invalid = ThaiCategoryCodeAdapter.isValid("99");  // false
```

### Category Type Checks

```java
// Check for original document reference
ThaiCategoryCodeType category = ThaiCategoryCodeType.of("01");
if (category.isOriginalDocumentReference()) {
    System.out.println("Used for: Cancellation, Debit Note, or Credit Note");
}

// Check for advance payment
ThaiCategoryCodeType advance = ThaiCategoryCodeType.of("02");
if (advance.isAdvancePaymentReference()) {
    System.out.println("Used for: Advance payment receipt");
}
```

### Repository Queries

```java
@Autowired
private ThaiCategoryCodeRepository repository;

// Find by code
Optional<ThaiCategoryCode> cat01 = repository.findByCode("01");
Optional<ThaiCategoryCode> cat02 = repository.findByCode("02");

// Get specific categories
Optional<ThaiCategoryCode> cancellation = repository.findOriginalDocumentReference();
Optional<ThaiCategoryCode> advance = repository.findAdvancePaymentReference();

// Check existence
boolean exists = repository.existsByCode("01");  // true
```

### XML Marshalling/Unmarshalling

```java
// In e-Tax Invoice classes
@XmlElement(namespace = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1")
private ThaiCategoryCodeType categoryCode;

// Setting category
invoice.setCategoryCode(ThaiCategoryCodeType.originalDocumentReference());
invoice.setCategoryCode(ThaiCategoryCodeType.of("02"));

// XML output
// <categoryCode>01</categoryCode>

// XML input (string value converted to database entity)
// <categoryCode>01</categoryCode>
```

## Business Context

### Code 01 - Original Document Reference

**Use Cases**:
1. **Cancellation** (ยกเลิก) - Canceling an entire invoice
2. **Debit Note** (เพิ่มหนี้) - Increasing the amount due (adding charges)
3. **Credit Note** (ลดหนี้) - Decreasing the amount due (reducing charges)

**Example Scenario**:
```java
// Issuing a credit note to reduce charges
ThaiCategoryCodeType category = ThaiCategoryCodeType.originalDocumentReference();
creditNote.setCategoryCode(category);
creditNote.setOriginalInvoiceReference("INV-2025-001");

// Check in business logic
if (category.isOriginalDocumentReference()) {
    // This is a document amendment - validate original invoice exists
    validateOriginalInvoice(creditNote.getOriginalInvoiceReference());
}
```

### Code 02 - Advance Payment Reference

**Use Cases**:
1. **Advance Payment Receipt** - Receipt for prepayment before delivery
2. **Deposit Receipt** - Receipt for deposits
3. **Down Payment** - Receipt for down payments on orders

**Example Scenario**:
```java
// Issuing advance payment receipt
ThaiCategoryCodeType category = ThaiCategoryCodeType.advancePaymentReference();
receipt.setCategoryCode(category);
receipt.setAdvancePaymentAmount(50000.00);
receipt.setPurchaseOrderReference("PO-2025-042");

// Check in business logic
if (category.isAdvancePaymentReference()) {
    // This is an advance payment - mark as prepayment in accounting
    accountingSystem.recordPrepayment(receipt);
}
```

## Migration Benefits

### 1. String-Based Simplicity
- **Simple String pattern** - No enum complexity for just 2 codes
- **Straightforward validation** - Direct string comparison
- **Easy to extend** - Can add new codes without enum changes

### 2. Database-Backed Validation
- Category codes validated against database
- Invalid codes create placeholder entities (graceful degradation)
- Bilingual names stored in database
- Extensible without code changes

### 3. Enhanced Querying
- **1 specialized view** (active categories)
- **4 repository methods** for common operations
- Bilingual name lookup (Thai and English)

### 4. Business Logic Integration
- **2 category type checks** (original document, advance payment)
- Thai and English name resolution
- Description lookup for documentation

### 5. Thai e-Tax Specific Features
- **Document amendment workflow** - Code 01 for cancellation/debit/credit notes
- **Advance payment handling** - Code 02 for prepayment receipts
- **Bilingual support** - Thai and English names for both codes
- **ETDA standard compliance** - Follows Thai e-Tax Invoice requirements

## Database Statistics

- **Total Codes**: 2
- **Code 01**: Original document reference (cancellation/debit/credit)
- **Code 02**: Advance payment reference
- **Database Views**: 1 (active categories)
- **Indexes**: 2 (name_th, name_en)
- **Repository Methods**: 4
- **Adapter Static Methods**: 5
- **Entity Business Methods**: 2

## Code Organization

```
src/main/java/com/wpanther/etax/
├── entity/
│   └── ThaiCategoryCode.java                (JPA entity, 2 business methods)
├── repository/
│   └── ThaiCategoryCodeRepository.java      (4 query methods)
├── adapter/
│   └── ThaiCategoryCodeAdapter.java         (JAXB adapter with String, 5 static helpers)
└── xml/
    └── thaicategory/
        ├── ThaiCategoryCodeType.java        (Custom JAXB type)
        └── package-info.java                (Namespace configuration)

sql/
└── thai_category_code.sql                   (Schema with data embedded - 2 INSERT statements)
```

## Category Codes Reference

| Code | Thai Name | English Name | Usage |
|------|-----------|--------------|-------|
| 01 | อ้างอิงเอกสารฉบับเดิมกรณี 1.ยกเลิก 2.เพิ่มหนี้ 3.ลดหนี้ | Reference to original document for: 1.Cancellation 2.Debit note 3.Credit note | Document amendments, corrections |
| 02 | อ้างอิงเอกสารเพื่อออกเงินรับล่วงหน้า | Reference to document for advance payment receipt | Prepayments, deposits, down payments |

## Testing Recommendations

### 1. Basic Validation Tests
```java
@Test
void testCategoryValidation() {
    assertTrue(ThaiCategoryCodeAdapter.isValid("01"));
    assertTrue(ThaiCategoryCodeAdapter.isValid("02"));
    assertFalse(ThaiCategoryCodeAdapter.isValid("99"));
}
```

### 2. Category Type Tests
```java
@Test
void testCategoryTypes() {
    ThaiCategoryCode cat01 = repository.findByCode("01").orElseThrow();
    assertTrue(cat01.isOriginalDocumentReference());
    assertFalse(cat01.isAdvancePaymentReference());

    ThaiCategoryCode cat02 = repository.findByCode("02").orElseThrow();
    assertFalse(cat02.isOriginalDocumentReference());
    assertTrue(cat02.isAdvancePaymentReference());
}
```

### 3. JAXB Marshalling Tests
```java
@Test
void testMarshalling() throws Exception {
    ThaiCategoryCode cat = new ThaiCategoryCode("01");
    cat.setNameTh("อ้างอิงเอกสารฉบับเดิม");
    cat.setNameEn("Original document reference");

    ThaiCategoryCodeAdapter adapter = new ThaiCategoryCodeAdapter();
    String marshalled = adapter.marshal(cat);
    assertEquals("01", marshalled);
}

@Test
void testUnmarshalling() throws Exception {
    ThaiCategoryCodeAdapter adapter = new ThaiCategoryCodeAdapter();
    ThaiCategoryCode cat = adapter.unmarshal("01");
    assertEquals("01", cat.getCode());
    assertNotNull(cat.getNameTh());
    assertNotNull(cat.getNameEn());
}
```

### 4. Bilingual Name Tests
```java
@Test
void testBilingualNames() {
    String nameTh = ThaiCategoryCodeAdapter.getNameTh("01");
    assertNotNull(nameTh);
    assertTrue(nameTh.contains("ยกเลิก"));

    String nameEn = ThaiCategoryCodeAdapter.getNameEn("01");
    assertNotNull(nameEn);
    assertTrue(nameEn.contains("Cancellation"));
}
```

## Migration Checklist

- [x] Database schema created (`thai_category_code.sql`)
- [x] Database data embedded (2 INSERT statements in schema file)
- [x] JPA entity created (`ThaiCategoryCode.java`)
- [x] Repository interface created (`ThaiCategoryCodeRepository.java`)
- [x] JAXB adapter created (`ThaiCategoryCodeAdapter.java` with String support)
- [x] Custom JAXB type created (`ThaiCategoryCodeType.java`)
- [x] Package configuration created (`package-info.java`)
- [x] Business logic methods implemented (2 in entity)
- [x] Static helper methods implemented (5 in adapter)
- [x] Bilingual support (Thai + English)
- [ ] Unit tests for category validation
- [ ] Integration tests for JAXB marshalling with String
- [ ] Update existing code to use new types
- [ ] Remove old JAXB-generated code
- [ ] Documentation updated

## Conclusion

The Thai Category Code migration successfully transforms JAXB-generated string handling into an efficient database-backed implementation with just 2 category codes. The string-based pattern (not enum) provides simplicity, while bilingual support and business logic methods enable proper Thai e-Tax Invoice document categorization for cancellations, debit/credit notes, and advance payments.
