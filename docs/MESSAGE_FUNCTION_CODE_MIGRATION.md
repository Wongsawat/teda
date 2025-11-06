# Database-Backed Implementation for Message Function Code

This document describes the database-backed implementation for UN/CEFACT Message Function Code (Code List 61225), replacing the JAXB-generated String type with a fully database-integrated solution.

## Files Created

### 1. Entity Layer

**File**: `src/main/java/com/wpanther/etax/entity/MessageFunctionCode.java`

JPA Entity class for storing UN/CEFACT Message Function Codes in the database.

**Key Features**:
- Primary key: `code` (VARCHAR 10)
- Fields: `name`, `description`, `category`, `isModification`, `isOriginal`, `isAcceptance`, timestamps
- Code normalization (trim only)
- JPA lifecycle callbacks for timestamp management
- Equals/hashCode based on code
- Supports 65 codes (1-65)

**Message Function Categories**:
- **Transaction Control**: Cancellation (1), Addition (2), Deletion (3), Change (4), Replace (5), Confirmation (6), Original (9)
- **Message Status**: Duplicate (7), Status (8), Not found (10), Response (11), Not processed (12), Request (13), etc.
- **Acceptance/Rejection**: Not accepted (27), Accepted without amendment (29), Accepted with amendment (28, 30, 34), Approval (32)
- **Financial**: Reversal of debit (37), Reversal of credit (38), Reversal for cancellation (39), Ledger advice (56-59)
- **Schedule**: Delivery instruction (24), Forecast (25), Complete schedule (61), Update schedule (62)

**Business Logic Methods**:
- `isModification()` - Check if modification function
- `isOriginal()` - Check if original transmission (code 9)
- `isAcceptance()` - Check if acceptance/rejection related
- `isCancellation()` - Codes 1, 17, 18, 39
- `isChange()` - Codes 4, 19, 28, 30, 33, 34, 36, 52
- `isReplacement()` - Codes 5, 20, 21
- `isConfirmation()` - Codes 6, 42
- `isFinancialReversal()` - Codes 37, 38, 39
- `isSchedule()` - Codes 24, 25, 26, 61, 62

### 2. Repository Layer

**File**: `src/main/java/com/wpanther/etax/repository/MessageFunctionCodeRepository.java`

Spring Data JPA repository for database operations.

**Query Methods**:
- `findByCode(code)` - Find by code
- `findModifications()` - Get all modification functions
- `findOriginals()` - Get original transmission functions
- `findAcceptanceRelated()` - Get acceptance/rejection functions
- `findByCategory(category)` - Get by category
- `findAllCategories()` - Get all available categories
- `findByNameContaining(name)` - Search by name
- `findByDescriptionContaining(keyword)` - Search by description
- `existsByCode(code)` - Check if code exists
- `findTransactionControl()` - Get transaction control codes
- `findMessageStatus()` - Get message status codes
- `findFinancial()` - Get financial codes
- `findSchedule()` - Get schedule codes
- `findCancellations()` - Get cancellation codes (1, 17, 18, 39)
- `findChanges()` - Get change/amendment codes
- `findReplacements()` - Get replacement codes (5, 20, 21)
- `findConfirmations()` - Get confirmation codes (6, 42)
- `findCommonCodes()` - Get most commonly used codes
- `findOriginalFunction()` - Get code 9
- `findCancellationFunction()` - Get code 1
- `findChangeFunction()` - Get code 4
- `findReplaceFunction()` - Get code 5
- `findConfirmationFunction()` - Get code 6

### 3. Adapter Layer

**File**: `src/main/java/com/wpanther/etax/adapter/MessageFunctionCodeAdapter.java`

JAXB XmlAdapter for converting between XML strings and database entities.

**Key Features**:
- `marshal(entity)` - Convert entity → XML string
- `unmarshal(code)` - Convert XML string → entity (with database lookup)
- Graceful handling of unknown codes (creates placeholder)
- Static helper methods:
  - `isValid(code)` - Validate code exists
  - `getMessageFunctionName(code)` - Get name from code
  - `getMessageFunctionCategory(code)` - Get category from code
  - `isModification(code)` - Check if modification
  - `isOriginal(code)` - Check if original (code 9)
  - `isAcceptance(code)` - Check if acceptance/rejection
  - `isCancellation(code)` - Check if cancellation
  - `isChange(code)` - Check if change/amendment
  - `isReplacement(code)` - Check if replacement
  - `isConfirmation(code)` - Check if confirmation

### 4. XML Wrapper Layer

**File**: `src/main/java/com/wpanther/etax/xml/messagefunction/MessageFunctionCodeType.java`

Custom JAXB type wrapper for XML binding.

**Key Features**:
- Maintains XML structure compatibility
- Uses `@XmlJavaTypeAdapter` for database integration
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A`
- Helper methods: `getCode()`, `getName()`, `getDescription()`, `getCategory()`, `isModification()`, `isOriginal()`, `isAcceptance()`, `isCancellation()`, `isChange()`, `isReplacement()`, `isConfirmation()`, `isFinancialReversal()`, `isSchedule()`
- Factory methods: `of(String)`, `of(MessageFunctionCode)`

**File**: `src/main/java/com/wpanther/etax/xml/messagefunction/package-info.java`

Package-level JAXB configuration for namespace and prefix.

**Configuration**:
- Namespace: `urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A`
- Prefix: `clm61225MessageFunctionTypeCode`
- Element form: QUALIFIED

### 5. Database Schema (Already Exists)

**File**: `message_function_code.sql` (263 lines)

PostgreSQL table schema with:
- Table definition with 8 columns
- 5 indexes for performance (name, category, is_modification, is_original, full-text search)
- Auto-update timestamp trigger
- 9 views (original, modifications, cancellation, change, acceptance, confirmation, notification, scheduling, common)
- 6 helper functions (get name, validate, check modification, check original, get category, search by keyword)

**File**: `message_function_code_data.sql` (73 lines)

Data insert statements for all 65 codes.

## Architecture Pattern

This implementation follows the **exact same pattern** as other code lists:

```
┌─────────────────────────────────────────────────────────────┐
│                      XML Document                           │
│  <MessageFunctionCode>9</MessageFunctionCode>              │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      MessageFunctionCodeType (XML Wrapper)                  │
│  - @XmlValue with @XmlJavaTypeAdapter                      │
│  - Namespace preservation                                   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│      MessageFunctionCodeAdapter (XmlAdapter)                │
│  - marshal(): entity → String                              │
│  - unmarshal(): String → entity (DB lookup)                │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│    MessageFunctionCodeRepository (Spring Data JPA)          │
│  - findByCode()                                             │
│  - findModifications()                                      │
│  - findCommonCodes()                                        │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│       MessageFunctionCode (JPA Entity)                      │
│  - @Entity with table mapping                              │
│  - Business logic and validation                            │
│  - Transaction control classification                       │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│            PostgreSQL Database                              │
│  Table: message_function_code (65 records)                 │
└─────────────────────────────────────────────────────────────┘
```

## Benefits

### ✅ Type Safety
- Strongly-typed entities instead of plain strings
- Compile-time validation of entity structure

### ✅ Validation
- Automatic validation during XML unmarshalling
- Rejects invalid codes or creates placeholder
- Database constraints enforce data integrity

### ✅ Rich Metadata
- Access to message function names and descriptions
- Category-based classification
- Boolean flags for modification/original/acceptance

### ✅ Business Logic Integration
- Query by transaction type (cancellation, change, replacement)
- Filter by message status
- Identify original vs modified transmissions
- Classify acceptance/rejection responses

### ✅ Query Capabilities
- Search by category (5 main categories)
- Filter by function type
- Find common codes (9 most used)
- Case-insensitive search

### ✅ Maintainability
- Centralized management in database
- Easy to add new codes
- Consistent pattern across all code lists

## Usage Examples

### XML Unmarshalling

```java
// XML: <MessageFunctionCode>9</MessageFunctionCode>
// Automatically looks up in database and returns entity

JAXBContext context = JAXBContext.newInstance(Invoice.class);
Unmarshaller unmarshaller = context.createUnmarshaller();
Invoice invoice = (Invoice) unmarshaller.unmarshal(xmlFile);

// Access message function details
MessageFunctionCodeType msgFunc = invoice.getMessageFunction();
String code = msgFunc.getCode();                    // "9"
String name = msgFunc.getName();                    // "Original"
String description = msgFunc.getDescription();      // "Initial transmission related to a given transaction."
String category = msgFunc.getCategory();            // "Transaction Control"
boolean isOriginal = msgFunc.isOriginal();          // true
boolean isModification = msgFunc.isModification();  // false
```

### XML Marshalling

```java
// Create from entity
MessageFunctionCode entity = repository.findByCode("9").get();
MessageFunctionCodeType msgFunc = MessageFunctionCodeType.of(entity);

// Or create from code string
MessageFunctionCodeType msgFunc = MessageFunctionCodeType.of("1");

// Set in invoice
invoice.setMessageFunction(msgFunc);

// Marshal to XML
Marshaller marshaller = context.createMarshaller();
marshaller.marshal(invoice, xmlFile);
// Output: <MessageFunctionCode>1</MessageFunctionCode>
```

### Validation

```java
// Check if code is valid
if (MessageFunctionCodeAdapter.isValid("9")) {
    // Code exists
}

// Get message function name
String name = MessageFunctionCodeAdapter.getMessageFunctionName("1");
// Returns: "Cancellation"

// Get category
String category = MessageFunctionCodeAdapter.getMessageFunctionCategory("9");
// Returns: "Transaction Control"

// Check function type
boolean isCancel = MessageFunctionCodeAdapter.isCancellation("1");
// Returns: true

boolean isOriginal = MessageFunctionCodeAdapter.isOriginal("9");
// Returns: true

boolean isChange = MessageFunctionCodeAdapter.isChange("4");
// Returns: true
```

### Repository Queries

```java
@Autowired
private MessageFunctionCodeRepository repository;

// Get original function (code 9)
Optional<MessageFunctionCode> original = repository.findOriginalFunction();

// Get all modification functions
List<MessageFunctionCode> modifications = repository.findModifications();

// Get cancellation functions
List<MessageFunctionCode> cancellations = repository.findCancellations();
// Returns: codes 1, 17, 18, 39

// Get common codes
List<MessageFunctionCode> common = repository.findCommonCodes();
// Returns: 9=Original, 4=Change, 5=Replace, 1=Cancel, 6=Confirm, etc.

// Get by category
List<MessageFunctionCode> transControl = repository.findByCategory("Transaction Control");

// Search by name
List<MessageFunctionCode> acceptCodes = repository.findByNameContaining("accept");
```

## Database Setup

```sql
-- 1. Create schema (already done)
\i message_function_code.sql

-- 2. Load data (already done)
\i message_function_code_data.sql

-- 3. Verify
SELECT COUNT(*) FROM message_function_code;
-- Expected: 65

SELECT COUNT(*) FROM message_function_code WHERE is_modification = true;
-- Expected: ~20

SELECT COUNT(*) FROM message_function_code WHERE is_original = true;
-- Expected: 1 (code 9)

-- 4. Query examples
SELECT * FROM message_function_code WHERE code = '9';
SELECT * FROM message_function_code_original;
SELECT * FROM message_function_code_modifications;
SELECT * FROM message_function_code_common;

-- 5. Use functions
SELECT get_message_function_name('9');
SELECT is_modification_function('4');
```

## Migration from JAXB Generated Code

### Before (JAXB Generated)

```java
// JAXB generates: JAXBElement<String>
// No type safety, no validation, no metadata

String code = "9";  // Just a string, could be invalid
JAXBElement<String> msgFunc = objectFactory.createMessageFunctionCode(code);
// No way to know if "9" is valid
// No way to get the name or category
// No business logic integration
```

### After (Database-Backed)

```java
// Database-backed: MessageFunctionCodeType with full entity
MessageFunctionCodeType msgFunc = MessageFunctionCodeType.of("9");
// Validates against database
// Full access to metadata

String code = msgFunc.getCode();                // "9"
String name = msgFunc.getName();                // "Original"
String category = msgFunc.getCategory();        // "Transaction Control"
boolean isOriginal = msgFunc.isOriginal();      // true
boolean isModification = msgFunc.isModification(); // false
boolean isCancellation = msgFunc.isCancellation(); // false
```

## Comparison with Other Code Lists

| Feature | PaymentTermsTypeCode | PaymentTermsDescriptionIdentifier | MessageFunctionCode |
|---------|---------------------|-----------------------------------|---------------------|
| **Total Codes** | 79 | 7 | 65 |
| **Code Format** | Numeric + ZZZ | Numeric 1-7 | Numeric 1-65 |
| **JAXB Original** | String | String | String |
| **Categories** | ✅ 9 categories | ✅ 3 categories | ✅ 5 categories |
| **Boolean Flags** | ✅ immediate/deferred/discount | ✅ isDraftRequired | ✅ modification/original/acceptance |
| **Entity** | ✅ | ✅ | ✅ |
| **Repository** | ✅ | ✅ | ✅ |
| **Adapter** | ✅ | ✅ | ✅ |
| **XML Wrapper** | ✅ | ✅ | ✅ |
| **Package-info** | ✅ | ✅ | ✅ |
| **SQL Schema** | ✅ | ✅ | ✅ (already exists) |

## Code List 61225 - Common Codes

| Code | Name | Category | Modification | Original | Acceptance |
|------|------|----------|--------------|----------|------------|
| 9 | Original | Transaction Control | ❌ | ✅ | ❌ |
| 1 | Cancellation | Transaction Control | ✅ | ❌ | ❌ |
| 4 | Change | Transaction Control | ✅ | ❌ | ❌ |
| 5 | Replace | Transaction Control | ✅ | ❌ | ❌ |
| 6 | Confirmation | Transaction Control | ❌ | ❌ | ❌ |
| 7 | Duplicate | Message Status | ❌ | ❌ | ❌ |
| 8 | Status | Message Status | ❌ | ❌ | ❌ |
| 11 | Response | Message Status | ❌ | ❌ | ❌ |
| 13 | Request | Message Status | ❌ | ❌ | ❌ |
| 27 | Not accepted | Acceptance/Rejection | ❌ | ❌ | ✅ |
| 29 | Accepted without amendment | Acceptance/Rejection | ❌ | ❌ | ✅ |

## e-Tax Invoice Use Cases

Message Function Codes are used in e-Tax Invoice to indicate the purpose of the document:

1. **Original Invoice (Code 9)**: First submission of an invoice
2. **Cancellation (Code 1)**: Cancel a previously submitted invoice
3. **Change (Code 4)**: Modify details in a previously submitted invoice
4. **Replace (Code 5)**: Completely replace a previous invoice
5. **Confirmation (Code 6)**: Confirm receipt/acceptance of an invoice
6. **Response (Code 11)**: Respond to a previous invoice (e.g., payment confirmation)

## Next Steps

1. **Database already setup** ✅ - SQL files exist and are loaded
2. **Configure Spring Boot** to enable JPA repository
3. **Test XML unmarshalling** with sample e-Tax Invoice documents
4. **Use transaction control queries** for invoice lifecycle management
5. **Integrate with invoice processing workflow** for automated handling

## Related Code Lists

Code lists migrated to database-backed pattern:

- ✅ **ISOCountryCode** - Complete (252 codes)
- ✅ **UNECEReferenceTypeCode** - Complete (798 codes)
- ✅ **PaymentTermsTypeCode** - Complete (79 codes)
- ✅ **PaymentTermsDescriptionIdentifier** - Complete (7 codes)
- ✅ **MessageFunctionCode** - Just completed (65 codes)
- ⏳ **Thai_MessageFunctionCode** - Pending (25 codes)
- ⏳ **ThaiDocumentNameCode** - Pending (12 codes)
- ⏳ **DutyTaxFeeTypeCode** - Pending
- ⏳ **ISO3AlphaCurrencyCode** - Pending (180+ codes)

---

**Created**: 2025-10-03
**Pattern**: Database-backed JAXB integration
**Status**: ✅ Complete and ready for use
**UN/CEFACT Code List**: 61225 (Message Function Code)
**Schema Version**: D14A
**Use Case**: e-Tax Invoice transaction control and workflow management
