# Simple Example: Using TaxInvoice_CrossIndustryInvoiceType with Database-Backed Classes

## Overview

This is a simplified, practical example showing how to work with the JAXB-generated classes and your database-backed entities.

## Key Points

1. **Generated code uses enums**: The JAXB-generated code references enum types like `ISO3AlphaCurrencyCodeContentType`
2. **Your adapters use Strings**: Your database-backed adapters work with String codes, not enums
3. **Integration strategy**: Convert between enum and String when needed

## Working Example

```java
package com.wpanther.etax.example;

import com.wpanther.etax.adapter.*;
import com.wpanther.etax.entity.*;
import com.wpanther.etax.generated.invoice.rsm.impl.TaxInvoice_CrossIndustryInvoiceTypeImpl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.io.*;

/**
 * Service to demonstrate JAXB XML processing with database-backed code lists
 */
@Service
public class TaxInvoiceService {

    private final ISOCurrencyCodeAdapter currencyAdapter;
    private final ISOCountryCodeAdapter countryAdapter;
    private final ThaiDocumentNameCodeAdapter documentNameAdapter;
    private final JAXBContext jaxbContext;

    public TaxInvoiceService(
            ISOCurrencyCodeAdapter currencyAdapter,
            ISOCountryCodeAdapter countryAdapter,
            ThaiDocumentNameCodeAdapter documentNameAdapter) throws Exception {

        this.currencyAdapter = currencyAdapter;
        this.countryAdapter = countryAdapter;
        this.documentNameAdapter = documentNameAdapter;

        // Initialize JAXB context for the root element
        this.jaxbContext = JAXBContext.newInstance(
            TaxInvoice_CrossIndustryInvoiceTypeImpl.class
        );
    }

    /**
     * Example 1: Parse XML and extract database-backed entities
     */
    public void parseXmlExample(InputStream xmlInput) throws Exception {

        // Step 1: Unmarshal XML to JAXB objects
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        @SuppressWarnings("unchecked")
        JAXBElement<TaxInvoice_CrossIndustryInvoiceTypeImpl> element =
            (JAXBElement<TaxInvoice_CrossIndustryInvoiceTypeImpl>)
            unmarshaller.unmarshal(xmlInput);

        TaxInvoice_CrossIndustryInvoiceTypeImpl invoice = element.getValue();

        // Step 2: Extract codes from JAXB objects
        if (invoice.getExchangedDocument() != null) {

            // Get invoice number
            String invoiceNumber = invoice.getExchangedDocument()
                .getID().getValue();

            System.out.println("Invoice Number: " + invoiceNumber);

            // Get document type code (comes from JAXB as enum)
            if (invoice.getExchangedDocument().getTypeCode() != null) {
                // The value() method returns the enum
                String docTypeCode = invoice.getExchangedDocument()
                    .getTypeCode().getValue();

                // Step 3: Use adapter to get database entity
                ThaiDocumentNameCode docType =
                    ThaiDocumentNameCodeAdapter.toEntity(docTypeCode);

                System.out.println("Document Type: " + docType.getCode());
                System.out.println("Description (EN): " + docType.getDescriptionEn());
                System.out.println("Description (TH): " + docType.getDescriptionTh());
            }
        }

        // Extract currency from settlement
        if (invoice.getSupplyChainTradeTransaction() != null &&
            invoice.getSupplyChainTradeTransaction()
                .getApplicableHeaderTradeSettlement() != null) {

            String currencyCode = invoice.getSupplyChainTradeTransaction()
                .getApplicableHeaderTradeSettlement()
                .getInvoiceCurrencyCode()
                .getValue(); // Returns enum value as String

            // Use adapter to get database entity with full details
            ISOCurrencyCode currency =
                ISOCurrencyCodeAdapter.toEntity(currencyCode);

            System.out.println("Currency: " + currency.getCode());
            System.out.println("Currency Name: " + currency.getName());
            System.out.println("Decimal Places: " + currency.getDecimalPlaces());

            // Use business methods from database entity
            if (currency.isThaiBasht()) {
                System.out.println("This is Thai Baht!");
            }
        }
    }

    /**
     * Example 2: Create invoice programmatically using database entities
     */
    public TaxInvoice_CrossIndustryInvoiceTypeImpl createInvoiceExample() {

        // Step 1: Look up database-backed entities
        ISOCurrencyCode thbCurrency = ISOCurrencyCodeAdapter.toEntity("THB");
        ISOCountryCode thailandCountry = ISOCountryCodeAdapter.toEntity("TH");
        ThaiDocumentNameCode invoiceType = ThaiDocumentNameCodeAdapter.toEntity("T01");

        // Step 2: Create JAXB invoice object
        TaxInvoice_CrossIndustryInvoiceTypeImpl invoice =
            new TaxInvoice_CrossIndustryInvoiceTypeImpl();

        // Step 3: Build the invoice structure
        // (This would involve creating ExchangedDocument, SupplyChainTradeTransaction, etc.)
        // For this example, we'll show the conceptual approach

        System.out.println("Creating invoice with:");
        System.out.println("  Currency: " + thbCurrency.getCode() + " (" + thbCurrency.getName() + ")");
        System.out.println("  Country: " + thailandCountry.getCode() + " (" + thailandCountry.getName() + ")");
        System.out.println("  Document Type: " + invoiceType.getCode());

        // In real implementation, you would:
        // 1. Create all JAXB implementation objects (*Impl classes)
        // 2. Convert your database entity codes to JAXB enum values
        // 3. Set all required fields according to Thai e-Tax specification

        return invoice;
    }

    /**
     * Example 3: Marshal invoice to XML
     */
    public String marshalToXml(TaxInvoice_CrossIndustryInvoiceTypeImpl invoice)
            throws Exception {

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        // Create JAXBElement with proper namespace
        QName qName = new QName(
            "urn:etda:uncefact:data:standard:TaxInvoice_CrossIndustryInvoice:2",
            "TaxInvoice_CrossIndustryInvoice"
        );

        JAXBElement<TaxInvoice_CrossIndustryInvoiceTypeImpl> element =
            new JAXBElement<>(qName, TaxInvoice_CrossIndustryInvoiceTypeImpl.class, invoice);

        StringWriter writer = new StringWriter();
        marshaller.marshal(element, writer);

        return writer.toString();
    }

    /**
     * Helper: Convert String code to database entity
     */
    public ISOCurrencyCode getCurrencyFromCode(String code) {
        return ISOCurrencyCodeAdapter.toEntity(code);
    }

    /**
     * Helper: Convert database entity to String code
     */
    public String getCodeFromCurrency(ISOCurrencyCode currency) {
        return ISOCurrencyCodeAdapter.toCode(currency);
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
JAXB Objects (with enum values)
   ↓
Extract String codes
   ↓
YourAdapter.toEntity(code) ← Database lookup
   ↓
Your Entity Objects (with full data)
   ↓
Business Logic
```

### Writing XML (Marshal)

```
Business Logic
   ↓
Your Entity Objects
   ↓
YourAdapter.toCode(entity)
   ↓
String codes
   ↓
Convert to JAXB enum (if needed)
   ↓
Create JAXB Objects
   ↓
JAXB Marshal
   ↓
XML File
```

## Key Concepts

### 1. Database Entity Lookup

```java
// Your adapters provide static helper methods
ISOCurrencyCode thb = ISOCurrencyCodeAdapter.toEntity("THB");

// Now you have full database-backed entity
System.out.println(thb.getName());  // "Thai Baht"
System.out.println(thb.getNumericCode());  // "764"
System.out.println(thb.getDecimalPlaces());  // 2

// Use business methods
if (thb.isThaiBasht()) {
    // Special handling for THB
}
```

### 2. Working with JAXB Generated Types

The generated code has this pattern:
- **Interface**: `CurrencyCodeType` (in `qdt` package)
- **Implementation**: `CurrencyCodeTypeImpl` (in `qdt.impl` package)

Always use the `Impl` classes when creating objects:

```java
// CORRECT
CurrencyCodeTypeImpl currency = new CurrencyCodeTypeImpl();
currency.setValue("THB");  // Set the enum value

// WRONG - can't instantiate interface
CurrencyCodeType currency = new CurrencyCodeType(); // Won't compile
```

### 3. Enum vs String Conversion

The JAXB types use enums, but they have `getValue()` and `setValue()` methods that work with Strings:

```java
// Get String from JAXB enum
String code = jaxbCurrencyCode.getValue();  // Returns "THB" as String

// Set JAXB enum from String
jaxbCurrencyCode.setValue("THB");  // Sets enum value
```

## Practical Workflow

1. **For XML Reading**:
   - Unmarshal XML → JAXB objects
   - Extract code strings from JAXB objects
   - Use `YourAdapter.toEntity(code)` to get database entities
   - Work with database entities in your business logic

2. **For XML Writing**:
   - Work with database entities in business logic
   - Use `YourAdapter.toCode(entity)` to get code strings
   - Set codes on JAXB objects using `setValue(code)`
   - Marshal JAXB objects → XML

## Benefits

✅ **XML Compliance**: JAXB handles all XML serialization correctly
✅ **Database Power**: You get full database-backed entities with all fields and business methods
✅ **Clean Code**: Business logic uses your entities, not JAXB classes
✅ **Maintainable**: When XSD changes, just regenerate JAXB code

## Files

- Generated JAXB: `target/generated-sources/jaxb/com/wpanther/etax/generated/invoice/`
- Your entities: `src/main/java/com/wpanther/etax/entity/`
- Your adapters: `src/main/java/com/wpanther/etax/adapter/`
- Integration guide: `JAXB_INTEGRATION_GUIDE.md`

## Next Steps

The example above shows the pattern. For a complete implementation:

1. Study the generated JAXB classes structure
2. Create builder/factory classes to construct JAXB objects
3. Create mapper classes to convert between JAXB and your business models
4. Keep JAXB objects isolated to XML processing layer
5. Use your database entities everywhere else

This gives you maximum flexibility and clean separation of concerns!
