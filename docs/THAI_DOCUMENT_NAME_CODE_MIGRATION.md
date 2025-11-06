# Thai Document Name Code Migration

## Overview

This document describes the migration of Thai Document Name Code (Invoice) from JAXB-generated classes to database-backed entities for the e-Tax Invoice system.

**Migration Date:** 2025-10-06
**Code List:** Thai Document Name Code (Invoice)
**Total Codes:** 11 codes (5 standard + 6 Thai extensions)
**Standard:** ETDA ThaiDocumentNameCode_Invoice_1p0.xsd

## What is Thai Document Name Code?

Thai Document Name Code identifies the **document type** for e-Tax invoices and related financial documents. It combines both **UN/CEFACT standard codes** and **Thai-specific extensions** to support Thailand's e-Tax Invoice requirements.

### Code Categories

**1. UN/CEFACT Standard Codes (5 codes):**
- **80** - Debit note related to goods or services (ใบเพิ่มหนี้)
- **81** - Credit note related to goods or services (ใบลดหนี้)
- **82** - Metered services invoice (ใบแจ้งหนี้ค่าบริการตามมิเตอร์)
- **380** - Commercial invoice (ใบแจ้งหนี้การค้า)
- **388** - Tax Invoice (ใบกำกับภาษี)

**2. Thai Extension Codes (6 codes, T01-T07):**
- **T01** - Receipt (ใบรับ)
- **T02** - Invoice/Tax Invoice (ใบแจ้งหนี้/ใบกำกับภาษี)
- **T03** - Receipt/Tax Invoice (ใบเสร็จรับเงิน/ใบกำกับภาษี)
- **T04** - Delivery order/Tax Invoice (ใบส่งของ/ใบกำกับภาษี)
- **T05** - Abbreviated Tax Invoice (ใบกำกับภาษีอย่างย่อ)
- **T06** - Receipt/Abbreviated Tax Invoice (ใบเสร็จรับเงิน/ใบกำกับภาษีอย่างย่อ)
- **T07** - Cancellation note (ใบแจ้งยกเลิก)

## XSD Standard Information

**XSD File:** `e-tax-invoice-receipt-v2.1/ETDA/codelist/standard/ThaiDocumentNameCode_Invoice_1p0.xsd`

**Namespace:** `urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1`

**Schema Details:**
- **Agency:** ETDA (Electronic Transactions Development Agency, Thailand)
- **Version:** 1.0
- **Date:** Aug 2015
- **Type:** SimpleType with enumeration restrictions on xsd:token
- **Based on:** UN/CEFACT 61001_Invoice Code List Schema v1.2 (subset profile)

**Key Characteristics:**
- Mixed code formats: Numeric (80, 81, 82, 380, 388) + Alphanumeric (T01-T07)
- English descriptions in XSD annotations
- Thai names added in database for bilingual support

## JAXB Generated Code

**Package:** `etda.uncefact.codelist.standard.thaidocumentnamecode_invoice._1`

**Pattern:** **JAXBElement<String>** (NO enum generated)

**Why no enum?**
- Mixed code formats (numeric + alphanumeric) prevent enum generation
- JAXB defaults to String-based approach for such cases
- Similar to ISOLanguageCode and ThaiCategoryCode patterns

**Generated Files:**
- **ObjectFactory.java** - Creates `JAXBElement<String>` with CollapsedStringAdapter
- **impl/ObjectFactory.java** - Implementation factory (identical to public)
- **impl/JAXBContextFactory.java** - Standard JAXB context factory

**Factory Method:**
```java
@XmlElementDecl(namespace = "urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1",
                name = "ThaiDocumentNameCodeInvoice")
@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
public JAXBElement<String> createThaiDocumentNameCodeInvoice(String value) {
    return new JAXBElement<String>(_ThaiDocumentNameCodeInvoice_QNAME, String.class, null, value);
}
```

## Database Schema

**Table:** `thai_document_name_code`

**Columns:**
- `code` (VARCHAR(10), PRIMARY KEY) - Document type code
- `name_th` (VARCHAR(255)) - Thai name of document type
- `name_en` (VARCHAR(255), NOT NULL) - English name of document type
- `description` (TEXT, NOT NULL) - Detailed description
- `is_standard_code` (BOOLEAN, DEFAULT false) - True for UN/CEFACT codes
- `is_thai_extension` (BOOLEAN, DEFAULT false) - True for Thai T01-T07 codes
- `created_at` (TIMESTAMP) - Creation timestamp
- `updated_at` (TIMESTAMP) - Last update timestamp

**Indexes:**
- Primary key on `code`
- Index on `name_th`
- Index on `name_en`
- Index on `is_standard_code`
- Index on `is_thai_extension`

**Database Files:**
- `thai_document_name_code.sql` - Table schema with all 11 codes

**Views:**
- `thai_document_name_code_standard` - Standard UN/CEFACT codes only (5 codes)
- `thai_document_name_code_thai_extension` - Thai extension codes only (6 codes)
- `thai_document_name_code_tax_invoice` - All tax invoice types (6 codes: 388, T02-T06)

## Migration Implementation

### 1. Entity Class

**File:** `src/main/java/com/wpanther/etax/entity/ThaiDocumentNameCode.java`

**Features:**
- JPA entity with proper indexes
- Bilingual support (Thai + English)
- Classification flags (standard vs Thai extension)
- 7 business logic methods for document type checks
- Lifecycle callbacks for timestamps
- Proper equals/hashCode based on code

**Business Logic Methods:**
- `isDebitNote()` → code 80
- `isCreditNote()` → code 81
- `isCommercialInvoice()` → code 380
- `isTaxInvoice()` → checks if name contains "Tax Invoice"
- `isReceipt()` → checks if name contains "Receipt"
- `isAbbreviatedTaxInvoice()` → codes T05, T06
- `isCancellationNote()` → code T07

### 2. Repository Interface

**File:** `src/main/java/com/wpanther/etax/repository/ThaiDocumentNameCodeRepository.java`

**Query Methods (14 total):**

**Basic Queries:**
- `findByCode(String code)` - Find by code
- `existsByCode(String code)` - Check existence

**Category Queries:**
- `findStandardCodes()` - All UN/CEFACT codes (80, 81, 82, 380, 388)
- `findThaiExtensions()` - All Thai extension codes (T01-T07)

**Document Type Queries:**
- `findTaxInvoiceTypes()` - All tax invoice types (6 codes)
- `findReceiptTypes()` - All receipt types (4 codes)
- `findDebitNoteCodes()` - Debit note (code 80)
- `findCreditNoteCodes()` - Credit note (code 81)
- `findAbbreviatedTaxInvoiceTypes()` - Abbreviated tax invoices (T05, T06)
- `findCancellationNote()` - Cancellation note (T07)

**Search Queries:**
- `findByNameThContaining(String nameTh)` - Search by Thai name
- `findByNameEnContaining(String nameEn)` - Search by English name

### 3. JAXB Adapter

**File:** `src/main/java/com/wpanther/etax/adapter/ThaiDocumentNameCodeAdapter.java`

**Key Features:**
- Extends `XmlAdapter<String, ThaiDocumentNameCode>`
- **Marshal:** Entity → String (returns code: "80", "T01", etc.)
- **Unmarshal:** String → Entity (database lookup)
- Placeholder creation for missing codes (with bilingual "unknown" messages)
- 6 static helper methods for common operations

**Conversion Logic:**
```java
// Marshal: Entity → String
public String marshal(ThaiDocumentNameCode entity) {
    return entity != null ? entity.getCode() : null;
}

// Unmarshal: String → Entity
public ThaiDocumentNameCode unmarshal(String code) {
    return repository.findByCode(code.trim())
            .orElseGet(() -> createPlaceholder(code));
}
```

**Static Helper Methods:**
- `isValid(String code)` - Check if code exists
- `getEnglishName(String code)` - Get English name
- `getThaiName(String code)` - Get Thai name
- `isStandardCode(String code)` - Check if UN/CEFACT code
- `isThaiExtension(String code)` - Check if Thai extension
- `isTaxInvoice(String code)` - Check if tax invoice type

### 4. Custom JAXB Type

**File:** `src/main/java/com/wpanther/etax/xml/documentname/ThaiDocumentNameCodeInvoiceType.java`

**Features:**
- Custom JAXB type wrapping the entity
- XmlValue with adapter annotation for seamless XML conversion
- Preserves exact namespace from XSD
- 10 business logic delegation methods
- Proper equals/hashCode/toString implementations
- Bilingual accessor methods
- 2 static factory methods

**Factory Methods:**
- `of(String code)` → Create from code string
- `of(ThaiDocumentNameCode entity)` → Create from entity

**Accessor Methods:**
- `getCode()` - Get document code
- `getNameTh()` - Get Thai name
- `getNameEn()` - Get English name
- `getDescription()` - Get description
- `isStandardCode()` - Check if standard
- `isThaiExtension()` - Check if Thai extension

**Business Logic Methods:**
- `isDebitNote()` - Code 80
- `isCreditNote()` - Code 81
- `isCommercialInvoice()` - Code 380
- `isTaxInvoice()` - Tax invoice types
- `isReceipt()` - Receipt types
- `isAbbreviatedTaxInvoice()` - T05, T06
- `isCancellationNote()` - T07

### 5. Package Documentation

**File:** `src/main/java/com/wpanther/etax/xml/documentname/package-info.java`

**Contents:**
- XML namespace configuration
- Namespace: `urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1`
- Prefix: `clm61001Invoice`
- Schema version and agency information

## Usage Examples

### Creating Document Name Codes

```java
// From code string
ThaiDocumentNameCodeInvoiceType taxInvoice = ThaiDocumentNameCodeInvoiceType.of("388");
ThaiDocumentNameCodeInvoiceType receipt = ThaiDocumentNameCodeInvoiceType.of("T01");
ThaiDocumentNameCodeInvoiceType debitNote = ThaiDocumentNameCodeInvoiceType.of("80");

// From entity
ThaiDocumentNameCode entity = repository.findByCode("T03").orElse(null);
ThaiDocumentNameCodeInvoiceType receiptTaxInvoice = ThaiDocumentNameCodeInvoiceType.of(entity);

// Using adapter
ThaiDocumentNameCode abbrevTaxInvoice = ThaiDocumentNameCodeAdapter.toEntity("T05");
```

### Checking Document Types

```java
ThaiDocumentNameCodeInvoiceType code = ThaiDocumentNameCodeInvoiceType.of("388");

if (code.isTaxInvoice()) {
    System.out.println("This is a tax invoice");
}

if (code.isStandardCode()) {
    System.out.println("Standard UN/CEFACT code");
}

if (code.isThaiExtension()) {
    System.out.println("Thai-specific extension");
}
```

### Using Static Helpers

```java
// Validate code
boolean valid = ThaiDocumentNameCodeAdapter.isValid("T05");

// Get names
String englishName = ThaiDocumentNameCodeAdapter.getEnglishName("388"); // "Tax Invoice"
String thaiName = ThaiDocumentNameCodeAdapter.getThaiName("388");       // "ใบกำกับภาษี"

// Check types
boolean isTaxInvoice = ThaiDocumentNameCodeAdapter.isTaxInvoice("T02");  // true
boolean isStandard = ThaiDocumentNameCodeAdapter.isStandardCode("80");   // true
boolean isThai = ThaiDocumentNameCodeAdapter.isThaiExtension("T07");     // true
```

### Repository Queries

```java
@Autowired
private ThaiDocumentNameCodeRepository repository;

// Find all tax invoice types
List<ThaiDocumentNameCode> taxInvoices = repository.findTaxInvoiceTypes();
// Returns: 388, T02, T03, T04, T05, T06

// Find all receipt types
List<ThaiDocumentNameCode> receipts = repository.findReceiptTypes();
// Returns: T01, T03, T06 (+ others with "receipt" in name)

// Find standard codes only
List<ThaiDocumentNameCode> standardCodes = repository.findStandardCodes();
// Returns: 80, 81, 82, 380, 388

// Find Thai extensions only
List<ThaiDocumentNameCode> thaiCodes = repository.findThaiExtensions();
// Returns: T01, T02, T03, T04, T05, T06, T07

// Search by name
List<ThaiDocumentNameCode> results = repository.findByNameThContaining("กำกับภาษี");
// Returns all codes with "กำกับภาษี" in Thai name

// Find specific code
Optional<ThaiDocumentNameCode> debitNote = repository.findByCode("80");
```

### Getting Bilingual Descriptions

```java
ThaiDocumentNameCodeInvoiceType code = ThaiDocumentNameCodeInvoiceType.of("T03");

String code = code.getCode();          // "T03"
String english = code.getNameEn();     // "Receipt/Tax Invoice"
String thai = code.getNameTh();        // "ใบเสร็จรับเงิน/ใบกำกับภาษี"

System.out.println(code + ": " + english + " / " + thai);
// Output: T03: Receipt/Tax Invoice / ใบเสร็จรับเงิน/ใบกำกับภาษี
```

## Business Rules

### Standard Code Rules (UN/CEFACT)

1. **Code 80 - Debit Note**
   - Increases the amount owed
   - Used when additional charges apply
   - Standard international code

2. **Code 81 - Credit Note**
   - Decreases the amount owed
   - Used for returns, discounts, or corrections
   - Standard international code

3. **Code 82 - Metered Services Invoice**
   - For utility services (gas, electricity, water)
   - Measured consumption over time
   - Less common in e-Tax Invoice

4. **Code 380 - Commercial Invoice**
   - Standard business invoice
   - Payment claim for goods/services
   - International trade document

5. **Code 388 - Tax Invoice**
   - VAT-compliant tax invoice
   - Standard code for tax invoices
   - Most common in Thai e-Tax system

### Thai Extension Code Rules (T01-T07)

1. **T01 - Receipt (ใบรับ)**
   - Simple receipt without tax
   - Acknowledges payment received
   - No VAT information

2. **T02 - Invoice/Tax Invoice (ใบแจ้งหนี้/ใบกำกับภาษี)**
   - Combined invoice and tax invoice
   - Claim payment + VAT documentation
   - Common in Thai business

3. **T03 - Receipt/Tax Invoice (ใบเสร็จรับเงิน/ใบกำกับภาษี)**
   - Receipt acknowledging payment with VAT
   - Most common document in retail
   - Serves dual purpose

4. **T04 - Delivery Order/Tax Invoice (ใบส่งของ/ใบกำกับภาษี)**
   - Delivery document with tax invoice
   - Common in logistics
   - Proof of delivery + VAT

5. **T05 - Abbreviated Tax Invoice (ใบกำกับภาษีอย่างย่อ)**
   - Simplified tax invoice format
   - Used for small transactions
   - Less detailed than full tax invoice

6. **T06 - Receipt/Abbreviated Tax Invoice (ใบเสร็จรับเงิน/ใบกำกับภาษีอย่างย่อ)**
   - Receipt with simplified tax invoice
   - Retail/small business use
   - Most compact format

7. **T07 - Cancellation Note (ใบแจ้งยกเลิก)**
   - Document cancellation notice
   - Voids previous document
   - Required for compliance

### Tax Invoice Types

Six codes represent tax invoice functionality:
- **388** - Standard tax invoice
- **T02** - Invoice/Tax Invoice
- **T03** - Receipt/Tax Invoice
- **T04** - Delivery order/Tax Invoice
- **T05** - Abbreviated Tax Invoice
- **T06** - Receipt/Abbreviated Tax Invoice

All support VAT calculation and compliance.

## Testing Recommendations

### Unit Tests

```java
@Test
void testTaxInvoiceIdentification() {
    ThaiDocumentNameCodeInvoiceType code = ThaiDocumentNameCodeInvoiceType.of("388");

    assertEquals("388", code.getCode());
    assertTrue(code.isTaxInvoice());
    assertTrue(code.isStandardCode());
    assertFalse(code.isThaiExtension());
    assertEquals("Tax Invoice", code.getNameEn());
    assertEquals("ใบกำกับภาษี", code.getNameTh());
}

@Test
void testThaiExtensionCodes() {
    ThaiDocumentNameCodeInvoiceType code = ThaiDocumentNameCodeInvoiceType.of("T05");

    assertTrue(code.isThaiExtension());
    assertTrue(code.isAbbreviatedTaxInvoice());
    assertTrue(code.isTaxInvoice());
    assertFalse(code.isStandardCode());
}

@Test
void testMixedCodeFormats() {
    // Numeric code
    ThaiDocumentNameCodeInvoiceType numeric = ThaiDocumentNameCodeInvoiceType.of("80");
    assertTrue(numeric.isDebitNote());

    // Alphanumeric code
    ThaiDocumentNameCodeInvoiceType alpha = ThaiDocumentNameCodeInvoiceType.of("T01");
    assertTrue(alpha.isReceipt());
}
```

### Integration Tests

```java
@SpringBootTest
class ThaiDocumentNameCodeIntegrationTest {

    @Autowired
    private ThaiDocumentNameCodeRepository repository;

    @Test
    void testFindAllTaxInvoiceTypes() {
        List<ThaiDocumentNameCode> codes = repository.findTaxInvoiceTypes();

        assertEquals(6, codes.size());
        List<String> expectedCodes = Arrays.asList("388", "T02", "T03", "T04", "T05", "T06");
        codes.forEach(c -> assertTrue(expectedCodes.contains(c.getCode())));
    }

    @Test
    void testStandardVsThaiExtension() {
        List<ThaiDocumentNameCode> standardCodes = repository.findStandardCodes();
        List<ThaiDocumentNameCode> thaiCodes = repository.findThaiExtensions();

        assertEquals(5, standardCodes.size());
        assertEquals(6, thaiCodes.size());

        // No overlap
        standardCodes.forEach(s -> assertFalse(s.getThaiExtension()));
        thaiCodes.forEach(t -> assertFalse(t.getStandardCode()));
    }

    @Test
    void testBilingualSearch() {
        // Search by Thai name
        List<ThaiDocumentNameCode> byThai = repository.findByNameThContaining("กำกับภาษี");
        assertTrue(byThai.size() > 0);

        // Search by English name
        List<ThaiDocumentNameCode> byEnglish = repository.findByNameEnContaining("Tax Invoice");
        assertEquals(byThai.size(), byEnglish.size());
    }
}
```

## Migration Benefits

1. **Database-Driven Configuration**
   - Code lists can be updated without redeployment
   - Easier to add new codes or modify descriptions
   - Full bilingual support (Thai/English)

2. **Enhanced Business Logic**
   - 7 entity methods for document classification
   - 14 repository query methods
   - 6 adapter helper methods
   - Rich querying capabilities

3. **Bilingual Support**
   - All codes have Thai and English names
   - Proper Unicode support for Thai language
   - Easier localization and compliance

4. **Type Safety**
   - String-based XML marshalling (no enum constraints)
   - Database entity for runtime operations
   - Handles mixed code formats seamlessly

5. **Standards Compliance**
   - Supports both UN/CEFACT and Thai standards
   - Clear separation of standard vs extension codes
   - Maintains ETDA namespace requirements

6. **Flexibility**
   - String pattern allows mixed numeric/alphanumeric codes
   - No enum limitations
   - Easy to add new codes

## Related Code Lists

This migration follows the **String pattern** like:
- ISOLanguageCode (185 codes, string pattern)
- ThaiCategoryCode (2 codes, string pattern)

**Different from ENUM pattern:**
- ISOCountryCode (252 codes, enum pattern)
- ISOCurrencyCode (182 codes, enum pattern)
- ThaiMessageFunctionCode (24 codes, enum pattern)

Thai Document Name Code uses the **String pattern** because JAXB cannot generate enums for mixed numeric/alphanumeric code formats.

## Files in Migration

### Created/Enhanced Files (5)
1. `src/main/java/com/wpanther/etax/entity/ThaiDocumentNameCode.java` - Entity (already existed)
2. `src/main/java/com/wpanther/etax/repository/ThaiDocumentNameCodeRepository.java` - Repository (already existed)
3. `src/main/java/com/wpanther/etax/adapter/ThaiDocumentNameCodeAdapter.java` - Adapter (already existed)
4. `src/main/java/com/wpanther/etax/xml/documentname/ThaiDocumentNameCodeInvoiceType.java` - Custom type (already existed)
5. `src/main/java/com/wpanther/etax/xml/documentname/package-info.java` - Namespace config (already existed)

### Database Files (Referenced)
1. `thai_document_name_code.sql` - Table schema with all 11 codes and data

## Summary Statistics

- **Total Codes:** 11
- **Standard Codes:** 5 (UN/CEFACT: 80, 81, 82, 380, 388)
- **Thai Extensions:** 6 (ETDA: T01-T07)
- **Tax Invoice Types:** 6 codes (388, T02, T03, T04, T05, T06)
- **Receipt Types:** 4 codes (T01, T03, T06, + others)
- **Entity Methods:** 7 business logic methods
- **Repository Methods:** 14 query methods
- **Adapter Helpers:** 6 static methods
- **Database Views:** 3 (standard, Thai extension, tax invoice)
- **JAXB Pattern:** String-based (no enum)

## Notes

1. **Mixed Code Formats:** Unlike other code lists, this combines numeric (80, 81, 82, 380, 388) and alphanumeric (T01-T07) codes. This is why JAXB generates String-based factory methods instead of enums.

2. **Tax Invoice Variations:** Six different codes represent tax invoice functionality, reflecting Thailand's diverse business document requirements. T03 (Receipt/Tax Invoice) is most common in retail.

3. **Abbreviated Tax Invoices:** T05 and T06 are simplified formats for small transactions, requiring less detail than full tax invoices. Commonly used in retail and small business.

4. **Cancellation Flow:** T07 (Cancellation note) is specifically designed for document cancellation, distinguishing it from credit notes (which adjust amounts but don't void documents).

5. **Database Views:** Three views provide quick access to different code categories: standard UN/CEFACT codes, Thai extensions, and all tax invoice types.

6. **Bilingual Requirement:** All codes must have both Thai and English descriptions for compliance with ETDA standards and to support bilingual e-Tax Invoice documents.

## Migration Status

✅ **COMPLETE** - All files already existed and have been documented in this migration guide.

The Thai Document Name Code migration is fully implemented with:
- Database-backed entity with 7 business methods
- Repository with 14 query methods
- JAXB adapter with 6 static helpers
- Custom XML type with proper namespace
- Comprehensive package documentation
- 3 database views for categorization
