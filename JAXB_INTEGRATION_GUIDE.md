# JAXB Integration Guide: Using Database-Backed Adapters with Generated Code

## Overview

This guide explains how to use JAXB-generated classes from XSD while integrating your existing database-backed entities through the adapter pattern.

## Current Setup

### 1. Generated JAXB Classes

Location: `target/generated-sources/jaxb/`

Generated packages:
- `com.wpanther.etax.generated.invoice.rsm` - Root schema model (TaxInvoice)
- `com.wpanther.etax.generated.invoice.ram` - Reusable aggregate business entities
- `com.wpanther.etax.generated.invoice.qdt` - Qualified data types
- `com.wpanther.etax.generated.invoice.udt` - Unqualified data types

**Important**: The generated code includes enum-based types (e.g., `ISO3AlphaCurrencyCodeContentType`) which reference JAXB-generated enums.

### 2. Your Database-Backed Classes

Location: `src/main/java/com/wpanther/etax/`

- **Entities**: `entity/ISOCurrencyCode.java`, `entity/ISOCountryCode.java`, etc.
- **Adapters**: `adapter/ISOCurrencyCodeAdapter.java`, etc. (String-based, no enum dependency)
- **Repositories**: `repository/ISOCurrencyCodeRepository.java`, etc.

## Integration Strategy: Wrapper/Facade Pattern

Since the generated JAXB code uses enums and you want database-backed entities, create wrapper classes that:

1. **Delegate to generated JAXB classes** for XML marshalling/unmarshalling
2. **Expose database entities** through your adapter pattern
3. **Convert between generated types and your entities**

## Example Implementation

### Step 1: Create Domain Model (Your Business Layer)

```java
// src/main/java/com/wpanther/etax/model/TaxInvoice.java
package com.wpanther.etax.model;

import com.wpanther.etax.entity.*;
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
import com.wpanther.etax.generated.invoice.rsm.TaxInvoiceCrossIndustryInvoiceType;
import com.wpanther.etax.adapter.*;
import org.springframework.stereotype.Component;

/**
 * Converts between JAXB-generated XML classes and business domain models
 */
@Component
public class TaxInvoiceMapper {

    private final ISOCurrencyCodeAdapter currencyAdapter;
    private final ISOCountryCodeAdapter countryAdapter;
    private final ThaiDocumentNameCodeAdapter documentNameAdapter;

    public TaxInvoiceMapper(ISOCurrencyCodeAdapter currencyAdapter,
                           ISOCountryCodeAdapter countryAdapter,
                           ThaiDocumentNameCodeAdapter documentNameAdapter) {
        this.currencyAdapter = currencyAdapter;
        this.countryAdapter = countryAdapter;
        this.documentNameAdapter = documentNameAdapter;
    }

    /**
     * Convert from JAXB XML model to business domain model
     */
    public TaxInvoice fromJaxb(TaxInvoiceCrossIndustryInvoiceType jaxbInvoice) {
        if (jaxbInvoice == null) return null;

        TaxInvoice invoice = new TaxInvoice();

        // Extract and convert currency code
        if (jaxbInvoice.getExchangedDocument() != null &&
            jaxbInvoice.getExchangedDocument().getDocumentCurrencyCode() != null) {

            String currencyCode = jaxbInvoice.getExchangedDocument()
                .getDocumentCurrencyCode().getValue().name(); // Get enum value

            invoice.setDocumentCurrency(
                ISOCurrencyCodeAdapter.toEntity(currencyCode)
            );
        }

        // Extract other fields...
        return invoice;
    }

    /**
     * Convert from business domain model to JAXB XML model
     */
    public TaxInvoiceCrossIndustryInvoiceType toJaxb(TaxInvoice invoice) {
        if (invoice == null) return null;

        TaxInvoiceCrossIndustryInvoiceType jaxbInvoice =
            new TaxInvoiceCrossIndustryInvoiceType();

        // Convert currency entity to JAXB enum
        if (invoice.getDocumentCurrency() != null) {
            String code = invoice.getDocumentCurrency().getCode();

            // Set JAXB enum value
            // You'll need to convert String to enum here
            // Example: ISO3AlphaCurrencyCodeContentType.valueOf(code)
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
import com.wpanther.etax.generated.invoice.rsm.TaxInvoiceCrossIndustryInvoiceType;
import jakarta.xml.bind.*;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class TaxInvoiceService {

    private final TaxInvoiceMapper mapper;
    private final JAXBContext jaxbContext;

    public TaxInvoiceService(TaxInvoiceMapper mapper) throws JAXBException {
        this.mapper = mapper;
        this.jaxbContext = JAXBContext.newInstance(TaxInvoiceCrossIndustryInvoiceType.class);
    }

    /**
     * Parse XML to business domain model
     */
    public TaxInvoice parseXml(InputStream xmlInput) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        TaxInvoiceCrossIndustryInvoiceType jaxbInvoice =
            (TaxInvoiceCrossIndustryInvoiceType) unmarshaller.unmarshal(xmlInput);

        return mapper.fromJaxb(jaxbInvoice);
    }

    /**
     * Generate XML from business domain model
     */
    public void writeXml(TaxInvoice invoice, OutputStream xmlOutput) throws JAXBException {
        TaxInvoiceCrossIndustryInvoiceType jaxbInvoice = mapper.toJaxb(invoice);

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
3. **Type Safety**: Your adapters provide compile-time safety
4. **Database Integration**: Full access to database-backed code lists
5. **Testability**: Can test business logic independent of XML serialization

## Maven Commands

```bash
# Generate JAXB classes from XSD
mvn clean generate-sources

# Compile everything (101 source files + 293 generated files)
mvn clean compile

# Package
mvn package
```

## File Structure

```
src/main/java/com/wpanther/etax/
├── entity/                    # Database entities
│   ├── ISOCurrencyCode.java
│   ├── ISOCountryCode.java
│   └── ...
├── adapter/                   # String-based adapters (no JAXB enum dependency)
│   ├── ISOCurrencyCodeAdapter.java
│   ├── ISOCountryCodeAdapter.java
│   └── ...
├── repository/                # Spring Data JPA repositories
│   └── ...
├── model/                     # Business domain models (NEW)
│   ├── TaxInvoice.java
│   └── InvoiceLine.java
├── mapper/                    # JAXB ↔ Domain converters (NEW)
│   └── TaxInvoiceMapper.java
└── service/                   # Business services (NEW)
    └── TaxInvoiceService.java

target/generated-sources/jaxb/com/wpanther/etax/generated/invoice/
├── rsm/                       # Root schema (generated)
├── ram/                       # Reusable aggregates (generated)
├── qdt/                       # Qualified data types (generated)
└── udt/                       # Unqualified data types (generated)
```

## Next Steps

1. Create `model/` package for your business domain classes
2. Create `mapper/` package for JAXB ↔ Domain conversion
3. Create `service/` package for business logic
4. Use generated JAXB classes only for XML marshalling/unmarshalling
5. Use your database entities everywhere else in your application

This approach gives you:
- ✅ Full XSD compliance (via generated JAXB)
- ✅ Database-backed code lists (via your adapters)
- ✅ Clean business logic (no XML concerns)
- ✅ Easy maintenance (regenerate JAXB when XSD changes)
