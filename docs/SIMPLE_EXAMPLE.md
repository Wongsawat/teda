# Simple Example: Using TaxInvoice_CrossIndustryInvoiceType with Database-Backed Classes

## Overview

This is a simplified, practical example showing how to work with the JAXB-generated classes and your database-backed entities.

## Key Points

1. **Generated code has 6 document types**: TaxInvoice, Receipt, DebitCreditNote, CancellationNote, AbbreviatedTaxInvoice, and Invoice
2. **Shared common types**: UDT (UnqualifiedDataType) and Thai QDT (QualifiedDataType) are shared across all document types
3. **Database-backed adapters**: Use Spring-injected repositories to fetch entities from PostgreSQL
4. **Integration strategy**: Use repositories and adapters for database lookups, then convert to XML types for marshalling

## Working Example

```java
package com.wpanther.etax.example;

import com.wpanther.etax.core.adapter.common.*;
import com.wpanther.etax.core.entity.*;
import com.wpanther.etax.core.repository.*;
import com.wpanther.etax.core.xml.isocountry.ISOTwoletterCountryCodeType;
import com.wpanther.etax.core.xml.isocurrency.ISOThreeletterCurrencyCodeType;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Service to demonstrate JAXB XML processing with database-backed code lists
 */
@Service
public class TaxInvoiceService {

    private final ISOCurrencyCodeRepository currencyRepository;
    private final ISOCountryCodeRepository countryRepository;
    private final ThaiDocumentNameCodeRepository documentNameRepository;
    private final JAXBContext jaxbContext;

    public TaxInvoiceService(
            ISOCurrencyCodeRepository currencyRepository,
            ISOCountryCodeRepository countryRepository,
            ThaiDocumentNameCodeRepository documentNameRepository) throws Exception {

        this.currencyRepository = currencyRepository;
        this.countryRepository = countryRepository;
        this.documentNameRepository = documentNameRepository;

        // Initialize JAXB context for the root element
        this.jaxbContext = JAXBContext.newInstance(
            TaxInvoice_CrossIndustryInvoiceType.class
        );
    }

    /**
     * Example 1: Parse XML and extract database-backed entities
     */
    public void parseXmlExample(InputStream xmlInput) throws Exception {

        // Step 1: Unmarshal XML to JAXB objects
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        TaxInvoice_CrossIndustryInvoiceType invoice =
            (TaxInvoice_CrossIndustryInvoiceType) unmarshaller.unmarshal(xmlInput);

        // Step 2: Extract codes from JAXB objects
        if (invoice.getExchangedDocument() != null) {

            // Get invoice number
            String invoiceNumber = invoice.getExchangedDocument()
                .getID().getValue();

            System.out.println("Invoice Number: " + invoiceNumber);

            // Get document type code (comes from JAXB as enum value)
            if (invoice.getExchangedDocument().getTypeCode() != null) {
                // The getValue() method returns the code as String
                String docTypeCode = invoice.getExchangedDocument()
                    .getTypeCode().getValue();

                // Step 3: Use repository to get database entity
                ThaiDocumentNameCode docType =
                    documentNameRepository.findByCode(docTypeCode)
                        .orElse(null);

                if (docType != null) {
                    System.out.println("Document Type: " + docType.getCode());
                    System.out.println("Description (EN): " + docType.getDescriptionEn());
                    System.out.println("Description (TH): " + docType.getDescriptionTh());
                }
            }
        }

        // Extract currency from settlement
        if (invoice.getSupplyChainTradeTransaction() != null &&
            invoice.getSupplyChainTradeTransaction()
                .getApplicableHeaderTradeSettlement() != null) {

            String currencyCode = invoice.getSupplyChainTradeTransaction()
                .getApplicableHeaderTradeSettlement()
                .getInvoiceCurrencyCode()
                .getValue(); // Returns code as String

            // Use repository to get database entity with full details
            ISOCurrencyCode currency =
                currencyRepository.findByCode(currencyCode).orElse(null);

            if (currency != null) {
                System.out.println("Currency: " + currency.getCode());
                System.out.println("Currency Name: " + currency.getName());
                System.out.println("Decimal Places: " + currency.getDecimalPlaces());

                // Use business methods from database entity
                if (currency.isThaiBaht()) {
                    System.out.println("This is Thai Baht!");
                }
            }
        }
    }

    /**
     * Example 2: Create invoice programmatically using database entities
     */
    public TaxInvoice_CrossIndustryInvoiceType createInvoiceExample() {

        // Step 1: Look up database-backed entities using repositories
        ISOCurrencyCode thbCurrency = currencyRepository.findByCode("THB").orElse(null);
        ISOCountryCode thailandCountry = countryRepository.findByCode("TH").orElse(null);
        ThaiDocumentNameCode invoiceType = documentNameRepository.findByCode("388").orElse(null);

        // Step 2: Create JAXB invoice object
        TaxInvoice_CrossIndustryInvoiceType invoice =
            new TaxInvoice_CrossIndustryInvoiceType();

        // Step 3: Build the invoice structure
        // (This would involve creating ExchangedDocument, SupplyChainTradeTransaction, etc.)
        // For this example, we'll show the conceptual approach

        System.out.println("Creating invoice with:");
        if (thbCurrency != null) {
            System.out.println("  Currency: " + thbCurrency.getCode() + " (" + thbCurrency.getName() + ")");
        }
        if (thailandCountry != null) {
            System.out.println("  Country: " + thailandCountry.getCode() + " (" + thailandCountry.getName() + ")");
        }
        if (invoiceType != null) {
            System.out.println("  Document Type: " + invoiceType.getCode());
        }

        // In real implementation, you would:
        // 1. Create all JAXB objects using the generated classes
        // 2. Set XML types using database entities
        // 3. Set all required fields according to Thai e-Tax specification

        return invoice;
    }

    /**
     * Example 3: Marshal invoice to XML
     */
    public String marshalToXml(TaxInvoice_CrossIndustryInvoiceType invoice)
            throws Exception {

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(invoice, writer);

        return writer.toString();
    }

    /**
     * Example 4: Create XML type from database entity
     */
    public String createCountryXml(String countryCode) {
        // Fetch from database
        ISOCountryCode country = countryRepository.findByCode(countryCode).orElse(null);

        if (country == null) {
            return null;
        }

        // Create XML type wrapper
        ISOTwoletterCountryCodeType countryType = ISOTwoletterCountryCodeType.of(country);

        // Now you can use countryType in JAXB objects
        return countryType.getCode(); // Returns "TH"
    }

    /**
     * Example 5: Using adapter static helper methods
     */
    public void validateCodes(String countryCode, String currencyCode) {
        // Check if codes are valid using adapter static methods
        boolean validCountry = ISOCountryCodeAdapter.isValid(countryCode);
        boolean validCurrency = ISOCurrencyCodeAdapter.isValid(currencyCode);

        System.out.println("Country " + countryCode + " is valid: " + validCountry);
        System.out.println("Currency " + currencyCode + " is valid: " + validCurrency);

        // Get document name using adapter
        String docName = ThaiDocumentNameCodeAdapter.getDocumentName("388");
        System.out.println("Document name for code 388: " + docName);
    }
}
```

## Usage Pattern

### Reading XML (Unmarshal)

```
XML File
   ↓
JAXB Unmarshal
   ↓
JAXB Objects (with String/enum values)
   ↓
Extract String codes
   ↓
repository.findByCode(code) ← Database lookup
   ↓
Your Entity Objects (with full data)
   ↓
Business Logic
```

### Writing XML (Marshal)

```
Business Logic
   ↓
Your Entity Objects (from repository)
   ↓
Custom XML Type: of(entity)
   ↓
XML Type with database-backed entity
   ↓
Set in JAXB objects
   ↓
JAXB Marshal
   ↓
XML File
```

## Key Concepts

### 1. Database Entity Lookup

```java
// Use Spring-injected repositories
@Autowired
private ISOCountryCodeRepository countryRepository;

// Fetch entity from database
ISOCountryCode thb = countryRepository.findByCode("TH").orElse(null);

// Now you have full database-backed entity
if (thb != null) {
    System.out.println(thb.getName());  // "THAILAND"
    System.out.println(thb.getNumericCode());  // "764"
    System.out.println(thb.isActive());  // true
}
```

### 2. Creating XML Types from Entities

```java
// Fetch entity from repository
ISOCountryCode thailand = countryRepository.findByCode("TH").orElse(null);

// Create custom XML type wrapper
ISOTwoletterCountryCodeType countryType = ISOTwoletterCountryCodeType.of(thailand);

// Use in JAXB objects
invoice.setCountry(countryType);
```

### 3. Using Generated JAXB Types

The generated code is organized into 6 document types:

- **taxinvoice**: `com.wpanther.etax.generated.taxinvoice.*`
  - `rsm`: Root schema module (TaxInvoice_CrossIndustryInvoiceType)
  - `ram`: Reusable aggregate business entities

- **receipt**: `com.wpanther.etax.generated.receipt.*`
  - `rsm`: Receipt_CrossIndustryInvoiceType

- **debitcreditnote**: `com.wpanther.etax.generated.debitcreditnote.*`
  - `rsm`: DebitCreditNote_CrossIndustryInvoiceType

- **cancellationnote**: `com.wpanther.etax.generated.cancellationnote.*`
  - `rsm`: CancellationNote_CrossIndustryInvoiceType

- **abbreviatedtaxinvoice**: `com.wpanther.etax.generated.abbreviatedtaxinvoice.*`
  - `rsm`: AbbreviatedTaxInvoice_CrossIndustryInvoiceType

- **invoice**: `com.wpanther.etax.generated.invoice.*`
  - `rsm`: Invoice_CrossIndustryInvoiceType (generic UN/CEFACT)

All types share:
- **common.udt**: Unqualified data types (44 classes)
- **common.qdt**: Thai qualified data types (72 classes)

## Practical Workflow

1. **For XML Reading**:
   - Unmarshal XML → JAXB objects
   - Extract code strings from JAXB objects
   - Use `repository.findByCode()` to get database entities
   - Work with database entities in your business logic

2. **For XML Writing**:
   - Work with database entities in business logic
   - Use `CustomXmlType.of(entity)` to create XML wrapper
   - Set XML types on JAXB objects
   - Marshal JAXB objects → XML

## Benefits

✅ **XML Compliance**: JAXB handles all XML serialization correctly
✅ **Database Power**: Full database-backed entities with all fields and business methods
✅ **Clean Code**: Business logic uses entities, not JAXB classes
✅ **Maintainable**: When XSD changes, just regenerate JAXB code

## Files

- Generated JAXB: `target/generated-sources/jaxb/com/wpanther/etax/generated/`
  - `taxinvoice/`, `receipt/`, `debitcreditnote/`, `cancellationnote/`, `abbreviatedtaxinvoice/`, `invoice/`
  - `common/udt/`, `common/qdt/`
- Your entities: `src/main/java/com/wpanther/etax/core/entity/`
- Your repositories: `src/main/java/com/wpanther/etax/core/repository/`
- Your adapters: `src/main/java/com/wpanther/etax/core/adapter/common/`
- Your XML types: `src/main/java/com/wpanther/etax/core/xml/`
- Integration guide: `JAXB_INTEGRATION_GUIDE.md`

## Next Steps

The example above shows the pattern. For a complete implementation:

1. Study the generated JAXB classes structure (all 6 document types)
2. Create builder/factory classes to construct JAXB objects
3. Create mapper classes to convert between JAXB and your business models
4. Keep JAXB objects isolated to XML processing layer
5. Use your database entities everywhere else

This gives you maximum flexibility and clean separation of concerns!
