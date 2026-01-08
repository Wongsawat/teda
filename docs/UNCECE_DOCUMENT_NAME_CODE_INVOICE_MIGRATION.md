# Database-Backed Implementation for UN/CEFACT Document Name Code (Invoice)

This document describes the database-backed implementation for UN/CEFACT Document Name Code (Invoice) from Code List 1001, Version D14A, replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/core/entity/UNECEDocumentNameCodeInvoice.java`

JPA Entity class for storing UN/CEFACT Document Name Codes in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `category`, `isCredit`, `isDebit`, `requiresPayment`, timestamps
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 17 UN/CEFACT document codes

**17 UN/CEFACT Document Codes**:
| Code | Name | Category | Credit | Debit |
|------|------|----------|--------|-------|
| 80 | Debit note related to GSN | Debit Note | ❌ | ✅ |
| 81 | Credit note related to GSN | Credit Note | ✅ | ❌ |
| 82 | Commercial invoice | Invoice | ❌ | ❌ |
| 83 | Credit note | Credit Note | ✅ | ❌ |
| 84 | Debit note | Debit Note | ❌ | ✅ |
| 261 | Credit note for partial settlement | Credit Note | ✅ | ❌ |
| 262 | Debit note for partial settlement | Debit Note | ❌ | ✅ |
| 325 | Pro forma invoice | Special | ❌ | ❌ |
| 380 | Commercial invoice | Invoice | ❌ | ❌ |
| 381 | Credit note | Credit Note | ✅ | ❌ |
| 383 | Debit note | Debit Note | ❌ | ✅ |
| 384 | Corrected invoice | Invoice | ❌ | ❌ |
| 385 | Self-billed invoice | Invoice | ❌ | ❌ |
| 386 | Prepayment invoice | Invoice | ❌ | ❌ |
| 389 | Self-billed credit note | Credit Note | ✅ | ❌ |
| 395 | Consignment invoice | Special | ❌ | ❌ |
| 396 | Factored credit note | Credit Note | ✅ | ❌ |

**Categories**:
- **Invoice** (6 codes): 82, 380, 384, 385, 386
- **Credit Note** (6 codes): 81, 83, 261, 381, 389, 396
- **Debit Note** (3 codes): 80, 84, 262, 383
- **Special** (2 codes): 325, 395

**Business Logic Methods**:
- `isInvoice()` - Check if invoice document
- `isCreditNote()` - Check if credit note
- `isDebitNote()` - Check if debit note
- `isSpecialDocument()` - Check if special document
- `isCommercialInvoice()` - Check if code 380
- `isCreditNoteDocument()` - Check if code 381
- `isDebitNoteDocument()` - Check if code 383
- `isProformaInvoice()` - Check if code 325
- `isPrepaymentInvoice()` - Check if code 386
- `isSelfBilledInvoice()` - Check if code 385

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/core/repository/UNECEDocumentNameCodeInvoiceRepository.java`

Spring Data JPA repository for database operations.

**Query Methods**:
- `findByCode(code)` - Find by code
- `findByCategory(category)` - Get by category
- `findInvoices()` - Get all invoice documents
- `findCreditNotes()` - Get all credit notes
- `findDebitNotes()` - Get all debit notes
- `findSpecialDocuments()` - Get special documents
- `findByRequiresPaymentTrue()` - Get documents requiring payment
- `findByRequiresPaymentFalse()` - Get documents not requiring payment
- `findCommonCodes()` - Get common codes (380, 381, 383, 325, 386)
- `findByNameContaining(name)` - Search by name
- `countByCategory()` - Count by category
- `findCommercialInvoice()` - Get code 380
- `findCreditNote()` - Get code 381
- `findDebitNote()` - Get code 383
- `findProformaInvoice()` - Get code 325
- `findPrepaymentInvoice()` - Get code 386

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/core/adapter/common/UNECEDocumentNameCodeInvoiceAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods:
  - `isValid(code)` - Validate code exists
  - `getDocumentName(code)` - Get name from code
  - `getDocumentDescription(code)` - Get description from code
  - `isCreditNote(code)` - Check if credit note
  - `isDebitNote(code)` - Check if debit note
  - `requiresPayment(code)` - Check if payment required

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/core/xml/documentnameinvoice/DocumentNameCodeInvoiceType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A`
- Helper methods: `getCode()`, `getName()`, `getDescription()`, `getCategory()`, `isCredit()`, `isDebit()`, `requiresPayment()`
- Factory methods: `of(String)`, `of(UNECEDocumentNameCodeInvoice)`

**File**: `src/main/java/com/wpanther/etax/core/xml/documentnameinvoice/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A`
- Element form: QUALIFIED
- Attribute form: UNQUALIFIED

### 5. Database Schema (Already Exists)

**File**: `src/main/resources/db/document_name_code_invoice.sql`

PostgreSQL table schema with:
- Table definition with 8 columns
- Primary key on `code`
- Indexes on `name`, `category`, `is_credit`, `is_debit`, `requires_payment`
- Auto-update timestamp trigger
- Views for invoices, credit notes, debit notes, special documents
- Helper functions for document type checking

**File**: `src/main/resources/db/document_name_code_invoice_data.sql`

Data insert statements for all 17 codes.

## Architecture Pattern

This implementation follows the **exact same pattern** as other code lists:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <DocumentNameCodeInvoice>380</DocumentNameCodeInvoice>    │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│   DocumentNameCodeInvoiceType (XML Wrapper)                 │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ UNECEDocumentNameCodeInvoiceAdapter (XmlAdapter)           │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ UNECEDocumentNameCodeInvoiceRepository (Spring Data JPA)  │
│  - findByCode()                                             │
│  - findByCategory()                                         │
│  - findInvoices()                                           │
│  - findCreditNotes()                                        │
│  - findDebitNotes()                                         │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│   UNECEDocumentNameCodeInvoice (JPA Entity)                 │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - Document type classification                             │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: document_name_code_invoice (17 records)           │
└─────────────────────────────────────────────────────────────┘
```

## Benefits

### ✅ Type Safety
- Strongly-typed entities instead of plain strings
- Compile-time validation of entity structure
- Runtime flexibility for new codes

### ✅ Validation
- Automatic validation during XML unmarshalling
- Rejects invalid codes or creates placeholder
- Database constraints enforce data integrity

### ✅ Rich Metadata
- Access to document names and descriptions
- Category-based classification (Invoice, Credit Note, Debit Note, Special)
- Payment requirement flags
- Credit/debit identification

### ✅ Business Logic Integration
- Query by document type (invoice, credit note, debit note)
- Filter by payment requirement
- Identify specific document types (commercial, proforma, self-billed, etc.)
- Search by name

### ✅ Query Capabilities
- Search by category (4 main categories)
- Find documents requiring/not requiring payment
- Filter by credit/debit documents
- Find common document codes
- Case-insensitive search

### ✅ Maintainability
- Centralized management in database
- Easy to add new document codes
- Consistent pattern across all code lists
- Shared by all 6 document types (TaxInvoice, Receipt, AbbreviatedTaxInvoice, DebitCreditNote, CancellationNote, Invoice)

## Usage Examples

### XML Unmarshalling

```java
// XML: <DocumentNameCodeInvoice>380</DocumentNameCodeInvoice>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access document code details
DocumentNameCodeInvoiceType docCode = invoice.getDocumentNameCode();
String code = docCode.getCode();              // "380"
String name = docCode.getName();              // "Commercial invoice"
String category = docCode.getCategory();      // "Invoice"
boolean isCredit = docCode.isCredit();        // false
boolean isDebit = docCode.isDebit();          // false
boolean needsPayment = docCode.requiresPayment(); // true
```

### XML Marshalling

```java
// Create from entity
UNECEDocumentNameCodeInvoice entity = repository.findByCode("380").get();
DocumentNameCodeInvoiceType docCode = DocumentNameCodeInvoiceType.of(entity);

// Or create from code string
DocumentNameCodeInvoiceType docCode = DocumentNameCodeInvoiceType.of("380");

// Set in invoice
invoice.setDocumentNameCode(docCode);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <DocumentNameCodeInvoice>380</DocumentNameCodeInvoice>
```

### Validation

```java
// Check if code is valid
if (UNECEDocumentNameCodeInvoiceAdapter.isValid("380")) {
    // Code exists
}

// Get document name
String name = UNECEDocumentNameCodeInvoiceAdapter.getDocumentName("380");
// Returns: "Commercial invoice"

// Get description
String desc = UNECEDocumentNameCodeInvoiceAdapter.getDocumentDescription("381");
// Returns: "Document issued by a seller to a buyer...

// Check if credit note
boolean isCredit = UNECEDocumentNameCodeInvoiceAdapter.isCreditNote("381");
// Returns: true

// Check if debit note
boolean isDebit = UNECEDocumentNameCodeInvoiceAdapter.isDebitNote("383");
// Returns: true

// Check if payment required
boolean needsPayment = UNECEDocumentNameCodeInvoiceAdapter.requiresPayment("380");
// Returns: true
```

### Repository Queries

```java
@Autowired
private UNECEDocumentNameCodeInvoiceRepository repository;

// Get all invoice documents
List<UNECEDocumentNameCodeInvoice> invoices = repository.findInvoices();

// Get all credit notes
List<UNECEDocumentNameCodeInvoice> creditNotes = repository.findCreditNotes();

// Get all debit notes
List<UNECEDocumentNameCodeInvoice> debitNotes = repository.findDebitNotes();

// Get documents requiring payment
List<UNECEDocumentNameCodeInvoice> payable = repository.findByRequiresPaymentTrue();

// Get commercial invoice (code 380)
Optional<UNECEDocumentNameCodeInvoice> commercial = repository.findCommercialInvoice();

// Get common codes
List<UNECEDocumentNameCodeInvoice> common = repository.findCommonCodes();
// Returns: 380, 381, 383, 325, 386

// Count by category
List<Object[]> categoryCounts = repository.countByCategory();
```

## Database Setup

```sql
-- 1. Create schema (already done)
\i document_name_code_invoice.sql

-- 2. Load data (already done)
\i document_name_code_invoice_data.sql

-- 3. Verify
SELECT COUNT(*) FROM document_name_code_invoice;
-- Expected: 17

SELECT category, COUNT(*) FROM document_name_code_invoice GROUP BY category;
-- Expected: Invoice (6), Credit Note (6), Debit Note (3), Special (2)

-- 4. Query examples
SELECT * FROM document_name_code_invoice WHERE code = '380';
SELECT * FROM document_name_code_invoice_invoices;
SELECT * FROM document_name_code_invoice_credit_notes;
SELECT * FROM document_name_code_invoice_debit_notes;
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "380";  // Just a string, could be invalid
JAXBElement<String> docCode = objectFactory.createDocumentNameCodeInvoice(code);
// No way to know if "380" is valid
// No way to get the name or description
// No business logic integration
```

### After (Database-Backed)

```java
// Database-backed: DocumentNameCodeInvoiceType with full entity
DocumentNameCodeInvoiceType docCode = DocumentNameCodeInvoiceType.of("380");
// Validates against database
// Full access to metadata

String code = docCode.getCode();              // "380"
String name = docCode.getName();              // "Commercial invoice"
String category = docCode.getCategory();      // "Invoice"
boolean isCredit = docCode.isCredit();        // false
boolean isDebit = docCode.isDebit();          // false
boolean needsPayment = docCode.requiresPayment(); // true
```

## Comparison with Thai Document Name Code

| Feature | UNECE Doc Name Code (Invoice) | Thai Document Name Code |
|---------|------------------------------|-------------------------|
| **Total Codes** | 17 (UN/CEFACT D14A) | 12 (Thai ETDA) |
| **Code Format** | 2-3 digit numeric | 2-3 digit alphanumeric |
| **Source** | UN/CEFACT Standard | Thai ETDA extension |
| **JAXB Original** | Enum | Enum |
| **Categories** | ✅ 4 categories | ✅ Thai-specific categories |
| **Credit/Debit Flags** | ✅ isCredit, isDebit | ❌ |
| **Payment Flag** | ✅ requiresPayment | ❌ |
| **Entity** | ✅ | ✅ |
| **Repository** | ✅ 15 methods | ✅ Thai-specific methods |
| **Adapter** | ✅ 6 helpers | ✅ Thai-specific helpers |
| **XML Wrapper** | ✅ | ✅ |
| **Package-info** | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ |
| **Shared by Documents** | ✅ All 6 types | ✅ All 6 types |

**Note**: These are TWO SEPARATE code lists for different purposes:
- **UNECEDocumentNameCodeInvoice**: Generic UN/CEFACT international standard document types
- **ThaiDocumentNameCode**: Thai-specific ETDA document types with Thai names

## UN/CEFACT Document Name Codes - Common Codes

| Code | Name | Category | Credit | Debit | Payment |
|------|------|----------|--------|-------|---------|
| 380 | Commercial invoice | Invoice | ❌ | ❌ | ✅ |
| 381 | Credit note | Credit Note | ✅ | ❌ | ✅ |
| 383 | Debit note | Debit Note | ❌ | ✅ | ✅ |
| 325 | Pro forma invoice | Special | ❌ | ❌ | ❌ |
| 386 | Prepayment invoice | Invoice | ❌ | ❌ | ✅ |

## e-Tax Invoice Use Cases

Document Name Codes are used in e-Tax Invoice to identify the type of document:

1. **Commercial Invoice (380)**: Standard trade invoice for VAT-registered businesses
2. **Credit Note (381)**: Refund or price reduction document
3. **Debit Note (383)**: Additional charges document
4. **Pro forma Invoice (325)**: Preliminary invoice before shipment
5. **Self-billed Invoice (385)**: Invoice issued by buyer on behalf of seller
6. **Prepayment Invoice (386)**: Invoice for advance payments
7. **Corrected Invoice (384)**: Invoice correcting previous errors
8. **Consignment Invoice (395)**: Invoice for consignment stock
9. **Cancellation Note (not in this list)**: Separate Thai document type

## Relationship with Thai Document Types

The Thai e-Tax system uses ETDA-specific document types that map to these UN/CEFACT codes:

| Thai Document Type | Thai Code | UN/CEFACT Code | Usage |
|-------------------|-----------|----------------|-------|
| Tax Invoice | 388 | 380 | VAT-registered business sales |
| Abbreviated Tax Invoice | 81 | 380 | Retail/small value sales |
| Receipt | 80 | N/A | Payment acknowledgment |
| Cancellation Note | 380 | N/A | Document cancellation |
| Credit Note | 381 | 381 | Refunds/price reductions |
| Debit Note | 383 | 383 | Additional charges |

## Next Steps

1. **Database already setup** ✅ - SQL files exist and are loaded
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Use document type queries** for invoice processing workflow
5. **Integrate with invoice validation** for document type checking

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **FreightCostCode** - Complete (66 active codes)
- ✅ **PaymentTermsTypeCode** - Complete (79 codes)
- ✅ **PaymentTermsDescriptionIdentifier** - Complete (7 codes)
- ✅ **MessageFunctionCode** - Complete (65 codes)
- ✅ **DutyTaxFeeTypeCode** - Complete (53 codes)
- ✅ **ThaiDocumentNameCode** - Complete (12 codes)
- ✅ **UNECEDocumentNameCodeInvoice** - Just completed (17 codes)
- ✅ **... (11 more)** - Various Thai-specific codes

## UN/CEFACT Standard Information

- **Code List**: 1001 (DocumentNameCode)
- **Version**: D14A
- **Agency**: United Nations Economic Commission for Europe (UN/CEFACT)
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A`
- **Total Codes**: 17 (from 80 to 396)
- **Usage**: Cross-industry invoice document type identification

---

**Created**: 2025-01-08
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT**: Code List 1001, Version D14A
**Use Case**: e-Tax Invoice document type classification
**Shared by**: TaxInvoice, Receipt, AbbreviatedTaxInvoice, DebitCreditNote, CancellationNote, Invoice (all 6 document types)
