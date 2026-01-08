# JAXB Integration Guide: Using Database-Backed Adapters with Generated Code

## Overview

This guide explains how to use JAXB-generated classes from XSD while integrating your existing database-backed entities through the adapter pattern.

## Current Setup

### 1. Generated JAXB Classes

Location: `target/generated-sources/jaxb/`

The library generates **6 document types** for ETDA v2.1:

| Document Type | Package | Root Class | Purpose |
|---------------|---------|------------|---------|
| **TaxInvoice** | `com.wpanther.etax.generated.taxinvoice.*` | `TaxInvoice_CrossIndustryInvoiceType` | Thai e-Tax invoices (Type 388) |
| **Receipt** | `com.wpanther.etax.generated.receipt.*` | `Receipt_CrossIndustryInvoiceType` | Payment receipts (Type 80) |
| **AbbreviatedTaxInvoice** | `com.wpanther.etax.generated.abbreviatedtaxinvoice.*` | `AbbreviatedTaxInvoice_CrossIndustryInvoiceType` | Retail invoices (Type 81) |
| **DebitCreditNote** | `com.wpanther.etax.generated.debitcreditnote.*` | `DebitCreditNote_CrossIndustryInvoiceType` | Credit/debit notes (Types 381/383) |
| **CancellationNote** | `com.wpanther.etax.generated.cancellationnote.*` | `CancellationNote_CrossIndustryInvoiceType` | Void documents (Type 380) |
| **Invoice** | `com.wpanther.etax.generated.invoice.*` | `Invoice_CrossIndustryInvoiceType` | Generic UN/CEFACT invoices |

**Shared Types** (used by all 6 document types):
- `com.wpanther.etax.generated.common.udt.*` - Unqualified data types (44 classes)
- `com.wpanther.etax.generated.common.qdt.*` - Thai qualified data types (72 classes)

Total: **748+ generated classes**

**Note**: The generated code includes enum-based types which are replaced by custom database-backed types.

### 2. Your Database-Backed Classes

Location: `src/main/java/com/wpanther/etax/core/`

- **Entities**: `entity/ISOCurrencyCode.java`, `entity/ISOCountryCode.java`, etc. (20 entities)
- **Adapters**: `adapter/common/ISOCurrencyCodeAdapter.java`, etc. (20 adapters)
- **Repositories**: `repository/ISOCurrencyCodeRepository.java`, etc. (20 repositories)
- **XML Types**: `xml/isocurrency/ISOThreeletterCurrencyCodeType.java`, etc. (21 types)

## Integration Strategy: Custom XML Types with Database Backing

Since the generated JAXB code uses enums, we create custom XML types that:
1. **Maintain XML namespace compatibility** with the generated schema
2. **Use XmlAdapter** to bridge between JAXB and database entities
3. **Provide static helper methods** for code validation

## Architecture

```
┌────────────────────────────────────────────────────────────────┐
│                     XML Document                               │
│  <ISO3AlphaCurrencyCode>THB</ISO3AlphaCurrencyCode>           │
└────────────────────────────────────────────────────────────────┘
                              ↓
┌────────────────────────────────────────────────────────────────┐
│       ISOThreeletterCurrencyCodeType (Custom XML Type)         │
│  - @XmlJavaTypeAdapter(ISOCurrencyCodeAdapter.class)           │
│  - Maintains namespace: urn:un:unece:uncefact:...              │
└────────────────────────────────────────────────────────────────┘
                              ↓
┌────────────────────────────────────────────────────────────────┐
│         ISOCurrencyCodeAdapter (XmlAdapter)                    │
│  - marshal(): entity → "THB"                                   │
│  - unmarshal(): "THB" → entity (via repository)               │
│  - Static helpers: isValid(), getCurrencyName()                │
└────────────────────────────────────────────────────────────────┘
                              ↓
┌────────────────────────────────────────────────────────────────┐
│       ISOCurrencyCodeRepository (Spring Data JPA)              │
│  - findByCode("THB") → Optional<ISOCurrencyCode>              │
│  - findByActiveTrue() → List<ISOCurrencyCode>                 │
└────────────────────────────────────────────────────────────────┘
                              ↓
┌────────────────────────────────────────────────────────────────┐
│            ISOCurrencyCode (JPA Entity)                        │
│  - code: "THB"                                                │
│  - name: "Thai Baht"                                          │
│  - numericCode: "764"                                         │
│  - decimalPlaces: 2                                           │
│  - isThaiBaht(): true                                          │
└────────────────────────────────────────────────────────────────┘
                              ↓
┌────────────────────────────────────────────────────────────────┐
│                  PostgreSQL Database                           │
│  Table: iso_currency_code (180+ records)                      │
└────────────────────────────────────────────────────────────────┘
```

## Example Implementation

### Step 1: Create Domain Model (Your Business Layer)

```java
// src/main/java/com/wpanther/etax/model/TaxInvoice.java
package com.wpanther.etax.model;

import com.wpanther.etax.core.entity.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Business domain model for Tax Invoice
 * Uses database-backed entities instead of JAXB-generated enums
 */
public class TaxInvoice {

    private String id;
    private LocalDate issueDate;
    private ISOCurrencyCode documentCurrency;
    private ISOCountryCode sellerCountry;
    private ThaiDocumentNameCode documentType;
    private ThaiMessageFunctionCode messageFunction;

    // Business fields
    private String sellerName;
    private String buyerName;
    private List<InvoiceLine> lines;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ISOCurrencyCode getDocumentCurrency() { return documentCurrency; }
    public void setDocumentCurrency(ISOCurrencyCode currency) {
        this.documentCurrency = currency;
    }

    // ... other getters/setters
}
```

### Step 2: Create Converter/Mapper

```java
// src/main/java/com/wpanther/etax/mapper/TaxInvoiceMapper.java
package com.wpanther.etax.mapper;

import com.wpanther.etax.model.TaxInvoice;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import com.wpanther.etax.core.repository.*;
import com.wpanther.etax.core.xml.isocurrency.ISOThreeletterCurrencyCodeType;
import com.wpanther.etax.core.xml.isocountry.ISOTwoletterCountryCodeType;
import org.springframework.stereotype.Component;

/**
 * Converts between JAXB-generated XML classes and business domain models
 */
@Component
public class TaxInvoiceMapper {

    private final ISOCurrencyCodeRepository currencyRepository;
    private final ISOCountryCodeRepository countryRepository;
    private final ThaiDocumentNameCodeRepository documentNameRepository;

    public TaxInvoiceMapper(ISOCurrencyCodeRepository currencyRepository,
                           ISOCountryCodeRepository countryRepository,
                           ThaiDocumentNameCodeRepository documentNameRepository) {
        this.currencyRepository = currencyRepository;
        this.countryRepository = countryRepository;
        this.documentNameRepository = documentNameRepository;
    }

    /**
     * Convert from JAXB XML model to business domain model
     */
    public TaxInvoice fromJaxb(TaxInvoice_CrossIndustryInvoiceType jaxbInvoice) {
        if (jaxbInvoice == null) return null;

        TaxInvoice invoice = new TaxInvoice();

        // Extract and convert currency code
        if (jaxbInvoice.getExchangedDocument() != null &&
            jaxbInvoice.getExchangedDocument().getID() != null) {

            invoice.setId(jaxbInvoice.getExchangedDocument().getID().getValue());
        }

        // Extract currency from settlement
        if (jaxbInvoice.getSupplyChainTradeTransaction() != null &&
            jaxbInvoice.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
            jaxbInvoice.getSupplyChainTradeTransaction()
                .getApplicableHeaderTradeSettlement().getInvoiceCurrencyCode() != null) {

            String currencyCode = jaxbInvoice.getSupplyChainTradeTransaction()
                .getApplicableHeaderTradeSettlement()
                .getInvoiceCurrencyCode().getValue();

            invoice.setDocumentCurrency(
                currencyRepository.findByCode(currencyCode).orElse(null)
            );
        }

        // Extract other fields...
        return invoice;
    }

    /**
     * Convert from business domain model to JAXB XML model
     */
    public TaxInvoice_CrossIndustryInvoiceType toJaxb(TaxInvoice invoice) {
        if (invoice == null) return null;

        TaxInvoice_CrossIndustryInvoiceType jaxbInvoice =
            new TaxInvoice_CrossIndustryInvoiceType();

        // Create ExchangedDocument
        // ...

        // Convert currency entity to JAXB XML type
        if (invoice.getDocumentCurrency() != null) {
            ISOThreeletterCurrencyCodeType currencyType =
                ISOThreeletterCurrencyCodeType.of(invoice.getDocumentCurrency());

            // Set on JAXB object
            // jaxbInvoice.getSupplyChainTradeTransaction()
            //     .getApplicableHeaderTradeSettlement()
            //     .setInvoiceCurrencyCode(currencyType);
        }

        // Set other fields...
        return jaxbInvoice;
    }
}
```

### Step 3: Create Service Layer

```java
// src/main/java/com/wpanther/etax/service/TaxInvoiceService.java
package com.wpanther.etax.service;

import com.wpanther.etax.model.TaxInvoice;
import com.wpanther.etax.mapper.TaxInvoiceMapper;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import jakarta.xml.bind.*;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class TaxInvoiceService {

    private final TaxInvoiceMapper mapper;
    private final JAXBContext jaxbContext;

    public TaxInvoiceService(TaxInvoiceMapper mapper) throws JAXBException {
        this.mapper = mapper;
        this.jaxbContext = JAXBContext.newInstance(TaxInvoice_CrossIndustryInvoiceType.class);
    }

    /**
     * Parse XML to business domain model
     */
    public TaxInvoice parseXml(InputStream xmlInput) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        TaxInvoice_CrossIndustryInvoiceType jaxbInvoice =
            (TaxInvoice_CrossIndustryInvoiceType) unmarshaller.unmarshal(xmlInput);

        return mapper.fromJaxb(jaxbInvoice);
    }

    /**
     * Generate XML from business domain model
     */
    public void writeXml(TaxInvoice invoice, OutputStream xmlOutput) throws JAXBException {
        TaxInvoice_CrossIndustryInvoiceType jaxbInvoice = mapper.toJaxb(invoice);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(jaxbInvoice, xmlOutput);
    }

    /**
     * Process invoice business logic
     */
    public void processInvoice(TaxInvoice invoice) {
        // Your business logic using database-backed entities
        if (invoice.getDocumentCurrency() != null) {
            String currencyName = invoice.getDocumentCurrency().getName();
            // Use full database entity with all fields populated
        }
    }
}
```

## Benefits of This Approach

1. **Clean Separation**: Business logic uses database entities, XML handling uses JAXB
2. **No Code Generation Modification**: Generated JAXB code stays untouched
3. **Type Safety**: Custom XML types provide compile-time safety
4. **Database Integration**: Full access to database-backed code lists (20 code lists)
5. **Testability**: Can test business logic independent of XML serialization
6. **Validation**: Static adapter methods for code validation

## Maven Commands

```bash
# Generate JAXB classes from XSD (all 6 document types)
mvn clean generate-sources

# Compile everything (82 source files + 748 generated files)
mvn clean compile

# Package
mvn package
```

## File Structure

```
src/main/java/com/wpanther/etax/
├── core/
│   ├── entity/                    # Database entities (20)
│   │   ├── ISOCurrencyCode.java
│   │   ├── ISOCountryCode.java
│   │   └── ...
│   ├── adapter/common/            # JAXB XmlAdapters (20)
│   │   ├── ISOCurrencyCodeAdapter.java
│   │   ├── ISOCountryCodeAdapter.java
│   │   └── ...
│   ├── repository/                # Spring Data JPA repositories (20)
│   │   └── ...
│   └── xml/                       # Custom JAXB types (21)
│       ├── isocurrency/
│       ├── isocountry/
│       └── ...
├── model/                         # Business domain models
│   ├── TaxInvoice.java
│   └── InvoiceLine.java
├── mapper/                        # JAXB ↔ Domain converters
│   └── TaxInvoiceMapper.java
└── service/                       # Business services
    └── TaxInvoiceService.java

target/generated-sources/jaxb/com/wpanther/etax/generated/
├── common/                        # Shared types (116 classes)
│   ├── udt/                       # Unqualified data types (44)
│   └── qdt/                       # Thai qualified data types (72)
├── taxinvoice/                    # TaxInvoice-specific (~80 classes)
│   ├── rsm/                       # Root schema
│   └── ram/                       # Reusable aggregates
├── receipt/                       # Receipt-specific (~76 classes)
├── debitcreditnote/               # DebitCreditNote-specific (~76 classes)
├── cancellationnote/              # CancellationNote-specific (~74 classes)
├── abbreviatedtaxinvoice/         # AbbreviatedTaxInvoice-specific (~80 classes)
└── invoice/                       # Generic Invoice-specific (~146 classes)
    ├── qdt/                       # Generic qualified data types (66)
    ├── rsm/
    └── ram/
```

## Static Adapter Helper Methods

All adapters provide static helper methods (accessible even without Spring context):

```java
// Check if code exists in database
boolean isValid = ISOCurrencyCodeAdapter.isValid("THB");

// Get name from code
String name = ISOCountryCodeAdapter.getCountryName("TH");
// Returns: "THAILAND"

// Check business logic
boolean isThai = ISOCountryCodeAdapter.isThaiCountry("TH");
boolean isASEAN = ISOCountryCodeAdapter.isASEANCountry("TH");
```

## Working with Custom XML Types

```java
// Create from database entity
ISOCountryCode thailand = countryRepository.findByCode("TH").get();
ISOTwoletterCountryCodeType countryType = ISOTwoletterCountryCodeType.of(thailand);

// Create from code string (looks up in database)
ISOTwoletterCountryCodeType countryType = ISOTwoletterCountryCodeType.of("TH");

// Get the underlying entity
ISOCountryCode entity = countryType.getValue();

// Access properties
String code = countryType.getCode();      // "TH"
String name = countryType.getName();      // "THAILAND"
boolean isActive = countryType.isActive(); // true
```

## Next Steps

1. Create `model/` package for your business domain classes
2. Create `mapper/` package for JAXB ↔ Domain conversion
3. Create `service/` package for business logic
4. Use generated JAXB classes only for XML marshalling/unmarshalling
5. Use your database entities everywhere else in your application

This approach gives you:
- ✅ Full XSD compliance (via generated JAXB)
- ✅ Database-backed code lists (20 code lists via adapters)
- ✅ Clean business logic (no XML concerns)
- ✅ Easy maintenance (regenerate JAXB when XSD changes)

## Related Documentation

- [JAXB_GENERATION_SUMMARY.md](JAXB_GENERATION_SUMMARY.md) - Generated code overview
- [DATABASE_BACKED_JAXB.md](DATABASE_BACKED_JAXB.md) - Architecture explanation
- [SIMPLE_EXAMPLE.md](SIMPLE_EXAMPLE.md) - Working code examples
- Migration guides (20 files) - Individual code list details
