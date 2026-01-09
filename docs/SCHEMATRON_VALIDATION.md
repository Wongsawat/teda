# Schematron Validation Guide

This guide explains how to use the Schematron validation functionality in the teda library to validate Thai e-Tax Invoice XML documents against business rules.

## Overview

Schematron validation complements XSD schema validation by checking business rules that cannot be expressed in XSD. The teda library uses **ph-schematron 8.0.6** to perform runtime Schematron validation.

## What is Schematron?

Schematron is a rule-based validation language for XML that uses XPath expressions to check:
- Business rule constraints
- Conditional field requirements
- Cross-field validations
- Data consistency rules

## Available Document Types

| Document Type | Enum Constant | Schematron File | Rules |
|---------------|---------------|-----------------|-------|
| Tax Invoice | `DocumentSchematron.TAX_INVOICE` | `TaxInvoice_Schematron_2p1.sch` | 91 rules (TIV-*) |
| Receipt | `DocumentSchematron.RECEIPT` | `Receipt_Schematron_2p1.sch` | 75 rules (RCT-*) |
| Debit/Credit Note | `DocumentSchematron.DEBIT_CREDIT_NOTE` | `DebitCreditNote_Schematron_2p1.sch` | 76 rules (DCN-*) |
| Generic Invoice | `DocumentSchematron.INVOICE` | `Invoice_Schematron_2p1.sch` | 5 rules (INV-*) |
| Cancellation Note | `DocumentSchematron.CANCELLATION_NOTE` | `CancellationNote_Schematron_2p1.sch` | 0 rules (empty) |
| Abbreviated Tax Invoice | `DocumentSchematron.ABBREVIATED_TAX_INVOICE` | `AbbreviatedTaxInvoice_Schematron_2p1.sch` | 0 rules (empty) |

## Basic Usage

### 1. Add Dependencies

```xml
<dependency>
    <groupId>com.wpanther</groupId>
    <artifactId>thai-etax-invoice</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

The teda library includes all required Schematron validation dependencies.

### 2. Validate XML String

```java
import com.wpanther.etax.validation.SchematronValidator;
import com.wpanther.etax.validation.DocumentSchematron;
import com.wpanther.etax.validation.SchematronValidationResult;

// Create validator instance
SchematronValidator validator = new SchematronValidatorImpl();

// Validate XML content
String xmlContent = "<?xml version=\"1.0\"?>...";
SchematronValidationResult result = validator.validate(xmlContent, DocumentSchematron.TAX_INVOICE);

// Check result
if (result.isValid()) {
    System.out.println("Validation passed!");
} else {
    System.out.println("Validation failed with " + result.getErrors().size() + " errors");
    for (SchematronError error : result.getErrors()) {
        System.out.println("  " + error.getRuleId() + ": " + error.getMessage());
    }
}
```

### 3. Validate from InputStream

```java
import java.io.FileInputStream;

try (InputStream is = new FileInputStream("invoice.xml")) {
    SchematronValidationResult result = validator.validate(is, DocumentSchematron.TAX_INVOICE);
    // Handle result...
}
```

### 4. Check Schematron File Validity

```java
// Verify that the Schematron file itself is valid
boolean isValid = validator.isSchematronValid(DocumentSchematron.TAX_INVOICE);
```

## Validation Result

The `SchematronValidationResult` class contains:

```java
public class SchematronValidationResult {
    // Check if validation passed (no errors)
    public boolean isValid()

    // Get all errors (failed assertions)
    public List<SchematronError> getErrors()

    // Get all warnings (successful reports)
    public List<SchematronError> getWarnings()

    // Get total issue count
    public int getTotalIssueCount()
}
```

### SchematronError

Each error/warning contains:

```java
public class SchematronError {
    // Rule ID (e.g., "TIV-DocumentContext-001")
    public String getRuleId()

    // Error message (Thai + English)
    public String getMessage()

    // XPath location in the XML
    public String getLocation()

    // Error level (ERROR or WARNING)
    public ErrorLevel getLevel()

    // XPath test expression that failed
    public String getTestExpression()
}
```

## Example: Complete Validation Flow

```java
import com.wpanther.etax.validation.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InvoiceValidator {
    private final SchematronValidator validator;

    public InvoiceValidator() {
        this.validator = new SchematronValidatorImpl();
    }

    public ValidationResult validateInvoice(String filePath) throws IOException {
        // Read XML file
        String xmlContent = Files.readString(Paths.get(filePath));

        // Determine document type from file name or content
        DocumentSchematron docType = determineDocumentType(filePath);

        // Validate against Schematron
        SchematronValidationResult result = validator.validate(xmlContent, docType);

        // Build validation result
        if (result.isValid()) {
            return ValidationResult.success("Schematron validation passed");
        } else {
            StringBuilder errors = new StringBuilder();
            errors.append("Schematron validation failed:\n");
            for (SchematronError error : result.getErrors()) {
                errors.append("  [")
                      .append(error.getRuleId())
                      .append("] ")
                      .append(error.getMessage())
                      .append("\n");
            }
            return ValidationResult.failure(errors.toString());
        }
    }

    private DocumentSchematron determineDocumentType(String fileName) {
        if (fileName.contains("TaxInvoice")) {
            return DocumentSchematron.TAX_INVOICE;
        } else if (fileName.contains("Receipt")) {
            return DocumentSchematron.RECEIPT;
        } else if (fileName.contains("CreditNote") || fileName.contains("DebitNote")) {
            return DocumentSchematron.DEBIT_CREDIT_NOTE;
        } else if (fileName.contains("Cancellation")) {
            return DocumentSchematron.CANCELLATION_NOTE;
        } else if (fileName.contains("Abbreviated")) {
            return DocumentSchematron.ABBREVIATED_TAX_INVOICE;
        } else {
            return DocumentSchematron.INVOICE;
        }
    }
}
```

## Integration with Spring Boot

```java
import com.wpanther.etax.validation.SchematronValidator;
import com.wpanther.etax.validation.DocumentSchematron;
import org.springframework.stereotype.Service;

@Service
public class InvoiceValidationService {

    private final SchematronValidator schematronValidator;

    public InvoiceValidationService(SchematronValidator schematronValidator) {
        this.schematronValidator = schematronValidator;
    }

    public boolean validateTaxInvoice(String xmlContent) {
        SchematronValidationResult result = schematronValidator.validate(
            xmlContent,
            DocumentSchematron.TAX_INVOICE
        );
        return result.isValid();
    }
}
```

## Common Validation Rules

### TaxInvoice Schematron (TIV-*)

- **TIV-DocumentContext-001**: Check schemeVersionID equals "v2.1"
- **TIV-Document-001**: Check document type code is present
- **TIV-SellerTaxId-001**: Validate seller tax ID format
- **TIV-InvoicerTaxId-001**: Validate invoicer tax ID matches seller when applicable
- **TIV-PurposeCode-001**: Validate purpose code based on document scenario

### Receipt Schematron (RCT-*)

- **RCT-Document-001**: Check receipt type code
- **RCT-PaymentMean-001**: Validate payment means code
- **RCT-SellerTaxId-001**: Validate seller tax ID in receipt context

### DebitCreditNote Schematron (DCN-*)

- **DCN-Document-001**: Check debit/credit note type (381/383)
- **DCN-Reference-001**: Validate reference to original invoice
- **DCN-Amount-001**: Validate amount calculations

## Error Message Format

Schematron errors include both Thai and English messages:

```
[TIV-DocumentContext-001] ตรวจสอบ schemeVersionID ต้องมีค่าเป็น v2.1
Check that schemeVersionID has value v2.1
```

## Limitations

1. **Empty Schematrons**: `CancellationNote_Schematron_2p1.sch` and `AbbreviatedTaxInvoice_Schematron_2p1.sch` are currently empty (no validation rules). Validation will always pass for these document types.

2. **Rule Coverage**: Schematron rules may not cover all possible business rule violations. Additional application-level validation may be required.

3. **XSD Validation Required**: Schematron validation does not replace XSD schema validation. Always validate against XSD first using JAXB marshalling.

## Troubleshooting

### Issue: "Schematron file not found on classpath"

**Cause**: The `.sch` file is not in the classpath.

**Solution**: Ensure the `.sch` files are in `src/main/resources/e-tax-invoice-receipt-v2.1/ETDA/data/standard/` and rebuild the project.

### Issue: Validation always passes even for invalid XML

**Cause**: The Schematron file may be empty or the specific rule doesn't exist.

**Solution**: Check the `.sch` file to see if the validation rule exists. Use `isSchematronValid()` to verify the Schematron file itself is valid.

### Issue: Error messages are empty

**Cause**: The Schematron rule may not have a text message, or the reflection-based extraction failed.

**Solution**: Check the `.sch` file for the `text` element in the `assert` or `report` tags. Enable debug logging to see extraction failures.

## Performance Considerations

- Each validation creates a new `SchematronResourcePure` instance
- For high-volume scenarios, consider caching the Schematron resources
- Typical validation time: 50-200ms per document

## References

- [ISO Schematron Specification](https://www.schematron.com/)
- [ph-schematron GitHub](https://github.com/phax/ph-schematron)
- [ETDA e-Tax Invoice v2.1 Specification](https://www.etda.or.th/)
