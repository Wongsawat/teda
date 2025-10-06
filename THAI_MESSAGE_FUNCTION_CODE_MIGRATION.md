# Thai Message Function Code Migration

## Overview

This document describes the migration of Thai Message Function Code from JAXB-generated enum classes to database-backed entities for the e-Tax Invoice system.

**Migration Date:** 2025-10-05
**Code List:** Thai Message Function Code
**Total Codes:** 24 codes (DBNG01-RCTC99)
**Standard:** ETDA Thai_MessageFunctionCode_1p0.xsd

## What is Thai Message Function Code?

Thai Message Function Code is a composite code that indicates the **document type**, **category**, and **function** for Thai e-Tax invoices, receipts, debit notes, and credit notes.

### Code Structure

Each code consists of 6 characters: `[PREFIX][SUFFIX]`

**PREFIX (4 characters)** - Document Type + Category:
- **DBNG** - Debit Note Goods (ใบเพิ่มหนี้ - สินค้า)
- **DBNS** - Debit Note Services (ใบเพิ่มหนี้ - บริการ)
- **CDNG** - Credit Note Goods (ใบลดหนี้ - สินค้า)
- **CDNS** - Credit Note Services (ใบลดหนี้ - บริการ)
- **TIVC** - Tax Invoice (ใบกำกับภาษี)
- **RCTC** - Receipt (ใบเสร็จรับเงิน)

**SUFFIX (2 digits)** - Document Function:
- **01** - Original (ต้นฉบับ)
- **02** - Replacement (ฉบับแทน)
- **03** - Cancellation (ยกเลิก) - Only for Credit Notes and Receipts
- **04** - Copy (สำเนา) - Only for Credit Notes and Receipts
- **05** - Addition (เพิ่มเติม) - Only for Credit Note Goods
- **99** - Other (อื่นๆ)

## Code List Breakdown

### Debit Note Codes (6 codes)

#### Debit Note - Goods (3 codes)
- **DBNG01** - Debit Note - Goods (Original) / ใบเพิ่มหนี้ - สินค้า (ต้นฉบับ)
- **DBNG02** - Debit Note - Goods (Replacement) / ใบเพิ่มหนี้ - สินค้า (ฉบับแทน)
- **DBNG99** - Debit Note - Goods (Other) / ใบเพิ่มหนี้ - สินค้า (อื่นๆ)

#### Debit Note - Services (3 codes)
- **DBNS01** - Debit Note - Services (Original) / ใบเพิ่มหนี้ - บริการ (ต้นฉบับ)
- **DBNS02** - Debit Note - Services (Replacement) / ใบเพิ่มหนี้ - บริการ (ฉบับแทน)
- **DBNS99** - Debit Note - Services (Other) / ใบเพิ่มหนี้ - บริการ (อื่นๆ)

### Credit Note Codes (11 codes)

#### Credit Note - Goods (6 codes)
- **CDNG01** - Credit Note - Goods (Original) / ใบลดหนี้ - สินค้า (ต้นฉบับ)
- **CDNG02** - Credit Note - Goods (Replacement) / ใบลดหนี้ - สินค้า (ฉบับแทน)
- **CDNG03** - Credit Note - Goods (Cancellation) / ใบลดหนี้ - สินค้า (ยกเลิก)
- **CDNG04** - Credit Note - Goods (Copy) / ใบลดหนี้ - สินค้า (สำเนา)
- **CDNG05** - Credit Note - Goods (Addition) / ใบลดหนี้ - สินค้า (เพิ่มเติม)
- **CDNG99** - Credit Note - Goods (Other) / ใบลดหนี้ - สินค้า (อื่นๆ)

#### Credit Note - Services (5 codes)
- **CDNS01** - Credit Note - Services (Original) / ใบลดหนี้ - บริการ (ต้นฉบับ)
- **CDNS02** - Credit Note - Services (Replacement) / ใบลดหนี้ - บริการ (ฉบับแทน)
- **CDNS03** - Credit Note - Services (Cancellation) / ใบลดหนี้ - บริการ (ยกเลิก)
- **CDNS04** - Credit Note - Services (Copy) / ใบลดหนี้ - บริการ (สำเนา)
- **CDNS99** - Credit Note - Services (Other) / ใบลดหนี้ - บริการ (อื่นๆ)

### Tax Invoice Codes (3 codes)
- **TIVC01** - Tax Invoice (Original) / ใบกำกับภาษี (ต้นฉบับ)
- **TIVC02** - Tax Invoice (Replacement) / ใบกำกับภาษี (ฉบับแทน)
- **TIVC99** - Tax Invoice (Other) / ใบกำกับภาษี (อื่นๆ)

### Receipt Codes (5 codes)
- **RCTC01** - Receipt (Original) / ใบเสร็จรับเงิน (ต้นฉบับ)
- **RCTC02** - Receipt (Replacement) / ใบเสร็จรับเงิน (ฉบับแทน)
- **RCTC03** - Receipt (Copy) / ใบเสร็จรับเงิน (สำเนา)
- **RCTC04** - Receipt (Cancellation) / ใบเสร็จรับเงิน (ยกเลิก)
- **RCTC99** - Receipt (Other) / ใบเสร็จรับเงิน (อื่นๆ)

## XSD Standard Information

**XSD File:** `e-tax-invoice-receipt-v2.1/ETDA/codelist/standard/Thai_MessageFunctionCode_1p0.xsd`

**Namespace:** `urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560`

**Schema Details:**
- **Agency:** UNCEFACT
- **Version:** 100.0
- **Date:** 30 May 2016
- **Type:** SimpleType with enumeration restrictions
- **Base Type:** xsd:token

**Note:** The XSD originally contained 65 UN/CEFACT standard message function codes (1-65) which are now commented out. Only the 24 ETDA-specific Thai codes are active.

## JAXB Generated Code

**Package:** `un.unece.uncefact.codelist.standard.etda.thaimessagefunctioncode._2560`

**Generated Enum:** `ThaiMessageFunctionCodeContentType`
- **Type:** Java enum with 24 values
- **Enum Constants:** DBNG_01, DBNG_02, DBNG_99, DBNS_01, ..., RCTC_99
- **Value Mapping:** Enum name uses underscore (DBNG_01), XmlEnumValue is original code (DBNG01)
- **Pattern:** JAXBElement<ENUM> (same as ISOCountryCode and ISOCurrencyCode)

**Example:**
```java
@XmlEnumValue("DBNG01")
DBNG_01("DBNG01"),
```

## Database Schema

**Table:** `thai_message_function_code`

**Columns:**
- `code` (VARCHAR(6), PRIMARY KEY) - The message function code
- `description_en` (VARCHAR(255), NOT NULL) - English description
- `description_th` (VARCHAR(255)) - Thai description
- `document_type` (VARCHAR(20), NOT NULL) - DebitNote, CreditNote, TaxInvoice, Receipt
- `category` (VARCHAR(10), NOT NULL) - Goods, Service, Invoice, Receipt
- `active` (BOOLEAN, NOT NULL, DEFAULT TRUE) - Active status
- `created_at` (TIMESTAMP) - Creation timestamp
- `updated_at` (TIMESTAMP) - Last update timestamp

**Indexes:**
- Primary key on `code`
- Index on `document_type`
- Index on `category`
- Index on `active`

**Database Files:**
- `thai_message_function_code.sql` - Table schema
- `thai_message_function_code_data.sql` - 24 code insertions with bilingual descriptions

**Views:**
- `thai_message_function_code_summary` - Summary by document type and category
- `thai_message_function_code_active` - Active codes only

## Migration Implementation

### 1. Entity Class

**File:** `src/main/java/com/wpanther/etax/entity/ThaiMessageFunctionCode.java`

**Features:**
- JPA entity with Lombok annotations
- 20 business logic methods for code classification
- Lifecycle callbacks for timestamps
- Document type checks: `isDebitNote()`, `isCreditNote()`, `isTaxInvoice()`, `isReceipt()`
- Category checks: `isGoods()`, `isService()`
- Function checks: `isOriginal()`, `isReplacement()`, `isCancellation()`, `isCopy()`, `isAddition()`, `isOther()`
- Prefix/suffix extraction: `getDocumentPrefix()`, `getFunctionSuffix()`
- Combined checks: `isDebitNoteGoods()`, `isDebitNoteServices()`, `isCreditNoteGoods()`, `isCreditNoteServices()`

### 2. Repository Interface

**File:** `src/main/java/com/wpanther/etax/repository/ThaiMessageFunctionCodeRepository.java`

**Query Methods (33 total):**

**General Queries:**
- `findByCode(String code)` - Find by code
- `findAllActive()` - All active codes
- `findByDocumentType(String documentType)` - Filter by document type
- `findByCategory(String category)` - Filter by category
- `findByDocumentTypeAndCategory(String, String)` - Combined filter

**Debit Note Queries (5):**
- `findDebitNoteCodes()` - All debit notes
- `findDebitNoteGoodsCodes()` - DBNG* codes
- `findDebitNoteServicesCodes()` - DBNS* codes
- `findDebitNoteGoodsOriginal()` - DBNG01
- `findDebitNoteServicesOriginal()` - DBNS01

**Credit Note Queries (5):**
- `findCreditNoteCodes()` - All credit notes
- `findCreditNoteGoodsCodes()` - CDNG* codes
- `findCreditNoteServicesCodes()` - CDNS* codes
- `findCreditNoteGoodsOriginal()` - CDNG01
- `findCreditNoteServicesOriginal()` - CDNS01

**Tax Invoice Queries (3):**
- `findTaxInvoiceCodes()` - All tax invoices
- `findTaxInvoiceOriginal()` - TIVC01
- `findTaxInvoiceReplacement()` - TIVC02

**Receipt Queries (5):**
- `findReceiptCodes()` - All receipts
- `findReceiptOriginal()` - RCTC01
- `findReceiptReplacement()` - RCTC02
- `findReceiptCopy()` - RCTC03
- `findReceiptCancellation()` - RCTC04

**Function Type Queries (6):**
- `findOriginalDocumentCodes()` - All *01 codes
- `findReplacementDocumentCodes()` - All *02 codes
- `findCancellationDocumentCodes()` - All *03 codes
- `findCopyDocumentCodes()` - All *04 codes
- `findAdditionDocumentCodes()` - All *05 codes
- `findOtherDocumentCodes()` - All *99 codes

### 3. JAXB Adapter

**File:** `src/main/java/com/wpanther/etax/adapter/ThaiMessageFunctionCodeAdapter.java`

**Key Features:**
- Extends `XmlAdapter<ThaiMessageFunctionCodeContentType, ThaiMessageFunctionCode>`
- **Marshal:** Entity → JAXB enum (converts DBNG01 to DBNG_01 enum constant)
- **Unmarshal:** JAXB enum → Entity (database lookup)
- Placeholder creation for missing codes
- Automatic document type/category detection from code prefix
- 18 static helper methods for common operations

**Conversion Logic:**
```java
// Marshal: DBNG01 entity → DBNG_01 enum
String enumName = code.substring(0, 4) + "_" + code.substring(4);
ThaiMessageFunctionCodeContentType enumValue = ThaiMessageFunctionCodeContentType.valueOf(enumName);

// Unmarshal: DBNG_01 enum → DBNG01 entity
String code = enumValue.value(); // "DBNG01"
return repository.findByCode(code).orElseGet(() -> createPlaceholder(code));
```

**Static Helper Methods:**
- Conversion: `toEntity()`, `toCode()`, `fromEnum()`, `toEnum()`
- Document type checks: `isDebitNote()`, `isCreditNote()`, `isTaxInvoice()`, `isReceipt()`
- Category checks: `isGoods()`, `isServices()`
- Function checks: `isOriginal()`, `isReplacement()`, `isCancellation()`, `isCopy()`, `isAddition()`, `isOther()`

### 4. Custom JAXB Type

**File:** `src/main/java/com/wpanther/etax/xml/thaimessagefunction/ThaiMessageFunctionCodeType.java`

**Features:**
- Custom JAXB type wrapping the entity
- XmlValue with adapter annotation for seamless XML conversion
- 10 factory methods for common codes
- 18 business logic delegation methods
- Proper equals/hashCode/toString implementations
- Bilingual description getters

**Factory Methods:**
- `taxInvoiceOriginal()` → TIVC01
- `taxInvoiceReplacement()` → TIVC02
- `receiptOriginal()` → RCTC01
- `receiptReplacement()` → RCTC02
- `receiptCopy()` → RCTC03
- `receiptCancellation()` → RCTC04
- `debitNoteGoodsOriginal()` → DBNG01
- `debitNoteServicesOriginal()` → DBNS01
- `creditNoteGoodsOriginal()` → CDNG01
- `creditNoteServicesOriginal()` → CDNS01

**Usage Example:**
```java
ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.taxInvoiceOriginal();
String englishDesc = code.getDescriptionEn(); // "Tax Invoice (Original)"
String thaiDesc = code.getDescriptionTh();     // "ใบกำกับภาษี (ต้นฉบับ)"
boolean isOriginal = code.isOriginal();         // true
boolean isTaxInvoice = code.isTaxInvoice();     // true
```

### 5. Package Documentation

**File:** `src/main/java/com/wpanther/etax/xml/thaimessagefunction/package-info.java`

**Contents:**
- Complete overview of Thai Message Function Code system
- Code structure explanation with examples
- Full listing of all 24 codes with Thai/English descriptions
- Usage examples and common patterns
- XSD standard information
- Database schema documentation
- JAXB integration details

## Usage Examples

### Creating Message Function Codes

```java
// Using factory methods
ThaiMessageFunctionCodeType taxInvoice = ThaiMessageFunctionCodeType.taxInvoiceOriginal();
ThaiMessageFunctionCodeType receipt = ThaiMessageFunctionCodeType.receiptOriginal();
ThaiMessageFunctionCodeType debitNote = ThaiMessageFunctionCodeType.debitNoteGoodsOriginal();
ThaiMessageFunctionCodeType creditNote = ThaiMessageFunctionCodeType.creditNoteServicesOriginal();

// From code string
ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.of("TIVC01");

// Using adapter
ThaiMessageFunctionCode entity = ThaiMessageFunctionCodeAdapter.toEntity("DBNG01");
```

### Checking Document Types

```java
ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.of("TIVC01");

if (code.isTaxInvoice()) {
    System.out.println("Tax Invoice");
}

if (code.isDebitNote()) {
    System.out.println("Debit Note");
}

if (code.isCreditNote()) {
    System.out.println("Credit Note");
}

if (code.isReceipt()) {
    System.out.println("Receipt");
}
```

### Checking Categories

```java
if (code.isGoods()) {
    System.out.println("Goods transaction");
}

if (code.isService()) {
    System.out.println("Service transaction");
}
```

### Checking Functions

```java
if (code.isOriginal()) {
    System.out.println("Original document");
}

if (code.isReplacement()) {
    System.out.println("Replacement document");
}

if (code.isCancellation()) {
    System.out.println("Cancellation document");
}
```

### Repository Queries

```java
@Autowired
private ThaiMessageFunctionCodeRepository repository;

// Find all tax invoice codes
List<ThaiMessageFunctionCode> taxInvoices = repository.findTaxInvoiceCodes();

// Find all debit note codes for goods
List<ThaiMessageFunctionCode> debitNoteGoods = repository.findDebitNoteGoodsCodes();

// Find specific code
Optional<ThaiMessageFunctionCode> original = repository.findTaxInvoiceOriginal();

// Find all original documents
List<ThaiMessageFunctionCode> originals = repository.findOriginalDocumentCodes();

// Find by document type and category
List<ThaiMessageFunctionCode> creditNoteServices =
    repository.findByDocumentTypeAndCategory("CreditNote", "Service");
```

### Getting Bilingual Descriptions

```java
ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.taxInvoiceOriginal();

String english = code.getDescriptionEn();  // "Tax Invoice (Original)"
String thai = code.getDescriptionTh();     // "ใบกำกับภาษี (ต้นฉบับ)"

System.out.println(english + " / " + thai);
```

## Business Rules

### Document Type Rules

1. **Debit Notes** increase the amount owed (ใบเพิ่มหนี้)
   - Used when additional charges are needed
   - Can be for goods or services

2. **Credit Notes** decrease the amount owed (ใบลดหนี้)
   - Used for returns, discounts, or corrections
   - Can be for goods or services
   - Most function variations (6 for goods, 5 for services)

3. **Tax Invoices** are the primary VAT documents (ใบกำกับภาษี)
   - Required for VAT-registered transactions
   - 3 function codes (Original, Replacement, Other)

4. **Receipts** acknowledge payment received (ใบเสร็จรับเงิน)
   - Can be issued with or without tax invoice
   - 5 function codes including cancellation

### Function Code Rules

1. **Original (01)** - Available for all document types
   - The first issuance of the document
   - Most commonly used code

2. **Replacement (02)** - Available for all document types
   - Replaces a previously issued document
   - Original document becomes invalid

3. **Cancellation (03)** - Only for Credit Notes and Receipts
   - Voids a previously issued document
   - Not available for Debit Notes or Tax Invoices

4. **Copy (04)** - Only for Credit Notes and Receipts
   - Duplicate for record keeping
   - Not available for Debit Notes or Tax Invoices

5. **Addition (05)** - Only for Credit Note Goods (CDNG05)
   - Additional credit note items
   - Very specific use case

6. **Other (99)** - Available for all document types
   - Catch-all for special cases
   - Use sparingly

## Testing Recommendations

### Unit Tests

```java
@Test
void testTaxInvoiceOriginalCreation() {
    ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.taxInvoiceOriginal();

    assertEquals("TIVC01", code.getCode());
    assertTrue(code.isTaxInvoice());
    assertTrue(code.isOriginal());
    assertFalse(code.isDebitNote());
}

@Test
void testDebitNoteGoodsClassification() {
    ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.debitNoteGoodsOriginal();

    assertTrue(code.isDebitNote());
    assertTrue(code.isGoods());
    assertTrue(code.isDebitNoteGoods());
    assertFalse(code.isDebitNoteServices());
}

@Test
void testCodeParsing() {
    ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.of("CDNG05");

    assertTrue(code.isCreditNote());
    assertTrue(code.isGoods());
    assertTrue(code.isAddition());
    assertEquals("CDNG", code.getValue().getDocumentPrefix());
    assertEquals("05", code.getValue().getFunctionSuffix());
}
```

### Integration Tests

```java
@SpringBootTest
class ThaiMessageFunctionCodeIntegrationTest {

    @Autowired
    private ThaiMessageFunctionCodeRepository repository;

    @Test
    void testFindAllTaxInvoiceCodes() {
        List<ThaiMessageFunctionCode> codes = repository.findTaxInvoiceCodes();

        assertEquals(3, codes.size());
        assertTrue(codes.stream().allMatch(c -> c.getCode().startsWith("TIVC")));
    }

    @Test
    void testFindOriginalDocuments() {
        List<ThaiMessageFunctionCode> codes = repository.findOriginalDocumentCodes();

        assertEquals(6, codes.size()); // DBNG01, DBNS01, CDNG01, CDNS01, TIVC01, RCTC01
        assertTrue(codes.stream().allMatch(c -> c.getCode().endsWith("01")));
    }
}
```

## Migration Benefits

1. **Database-Driven Configuration**
   - Code lists can be updated without redeployment
   - Easier to add new codes or modify descriptions
   - Support for bilingual content (Thai/English)

2. **Enhanced Business Logic**
   - 20 entity methods for classification
   - 33 repository query methods
   - 18 adapter helper methods
   - Rich querying capabilities

3. **Bilingual Support**
   - All codes have Thai and English descriptions
   - Proper Unicode support for Thai language
   - Easier localization

4. **Type Safety**
   - Still uses JAXB enum for XML marshalling
   - Database entity for runtime operations
   - Best of both worlds

5. **Active Management**
   - Active status flag for code lifecycle
   - Timestamps for auditing
   - Soft delete capability

## Related Code Lists

This migration follows the same pattern as:
- ISOCountryCode (252 codes, enum pattern)
- ISOCurrencyCode (182 codes, enum pattern)
- ISOLanguageCode (185 codes, string pattern)
- ThaiCategoryCode (2 codes, string pattern)

Thai Message Function Code uses the **enum pattern** like Country and Currency codes due to the JAXB-generated enum with 24 values.

## Files Changed

### Created Files (5)
1. `src/main/java/com/wpanther/etax/entity/ThaiMessageFunctionCode.java`
2. `src/main/java/com/wpanther/etax/repository/ThaiMessageFunctionCodeRepository.java`
3. `src/main/java/com/wpanther/etax/adapter/ThaiMessageFunctionCodeAdapter.java`
4. `src/main/java/com/wpanther/etax/xml/thaimessagefunction/ThaiMessageFunctionCodeType.java`
5. `src/main/java/com/wpanther/etax/xml/thaimessagefunction/package-info.java`

### Database Files (Referenced)
1. `thai_message_function_code.sql` - Table schema
2. `thai_message_function_code_data.sql` - 24 code data

## Summary Statistics

- **Total Codes:** 24
- **Document Types:** 4 (DebitNote, CreditNote, TaxInvoice, Receipt)
- **Categories:** 4 (Goods, Service, Invoice, Receipt)
- **Function Suffixes:** 6 (01, 02, 03, 04, 05, 99)
- **Entity Methods:** 20 business logic methods
- **Repository Methods:** 33 query methods
- **Adapter Helpers:** 18 static methods
- **Factory Methods:** 10 common code creators
- **Database Views:** 2 (summary and active)
- **JAXB Enum Values:** 24

## Notes

1. **Enum Naming Convention:** JAXB generates enum constants with underscores (DBNG_01) but the actual XML value and database code use no underscores (DBNG01). The adapter handles this conversion automatically.

2. **Function Code Distribution:** Not all document types support all function codes. Credit notes have the most variations (11 total codes), while tax invoices have the fewest (3 codes).

3. **CDNG05 Special Case:** The "Addition" function (05) is only available for Credit Note Goods (CDNG05), making it the most specific code in the system.

4. **Bilingual Requirement:** All codes must have both Thai and English descriptions for compliance with ETDA standards.

5. **Database Views:** Use the summary view to get code counts by document type and category, and the active view for current valid codes only.
