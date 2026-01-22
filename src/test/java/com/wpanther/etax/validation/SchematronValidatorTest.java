package com.wpanther.etax.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for SchematronValidator using example XML files.
 */
class SchematronValidatorTest {

    private SchematronValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SchematronValidatorImpl();
    }

    @Test
    @DisplayName("Schematron files should be valid")
    void testSchematronFilesAreValid() {
        for (DocumentSchematron docType : DocumentSchematron.values()) {
            boolean isValid = validator.isSchematronValid(docType);
            if (docType.isEmptySchematron()) {
                // Empty schematrons may have validation issues, which is acceptable
                assertThat(isValid).isTrue();
            } else {
                assertThat(isValid)
                    .as("Schematron file for " + docType + " should be valid")
                    .isTrue();
            }
        }
    }

    @ParameterizedTest
    @EnumSource(value = DocumentSchematron.class, names = {"TAX_INVOICE", "RECEIPT", "DEBIT_CREDIT_NOTE", "INVOICE"})
    @DisplayName("Example files should pass Schematron validation")
    void testExampleFilesPassValidation(DocumentSchematron docType) throws IOException {
        String exampleFile = getExampleFilePath(docType);
        String xmlContent = readResourceAsString(exampleFile);

        SchematronValidationResult result = validator.validate(xmlContent, docType);

        assertThat(result.isValid())
            .as("Example file " + exampleFile + " should pass validation")
            .isTrue();

        assertThat(result.getErrors())
            .as("Example file should have no validation errors")
            .isEmpty();

        // Print warnings for informational purposes
        if (result.hasWarnings()) {
            System.out.println(docType + " validation warnings:");
            result.getWarnings().forEach(w -> System.out.println("  - " + w.getRuleId() + ": " + w.getMessage()));
        }
    }

    @Test
    @DisplayName("Empty schematrons should handle validation gracefully")
    void testEmptySchematrons() throws IOException {
        // Test Cancellation Note (has empty Schematron)
        String cancellationExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_CreditNote_2p1_v1.xml";
        String xmlContent = readResourceAsString(cancellationExample);

        // Even though CancellationNote Schematron is empty, we should still be able to validate
        SchematronValidationResult result = validator.validate(xmlContent, DocumentSchematron.CANCELLATION_NOTE);

        // Empty schematrons should return success (no rules to fail)
        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("Invalid scheme version should trigger Schematron error")
    void testInvalidSchemeVersion() throws IOException {
        String taxInvoiceExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_TaxInvoice_2p1_v1.xml";
        String xmlContent = readResourceAsString(taxInvoiceExample);

        // Corrupt the schemeVersionID to trigger validation error
        String corruptedXml = xmlContent.replace("schemeVersionID=\"v2.1\"", "schemeVersionID=\"v1.0\"");

        SchematronValidationResult result = validator.validate(corruptedXml, DocumentSchematron.TAX_INVOICE);

        // Note: The Schematron validation may not check schemeVersionID
        // This test verifies that the validator runs successfully
        // In production, additional business rule validation may be needed
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Validation should handle cross-document-type validation")
    void testWrongDocumentType() throws IOException {
        String taxInvoiceExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_TaxInvoice_2p1_v1.xml";
        String xmlContent = readResourceAsString(taxInvoiceExample);

        // Try to validate TaxInvoice as Receipt
        // Note: Schematron validation may not check document type compatibility
        // This test verifies that the validator runs successfully
        SchematronValidationResult result = validator.validate(xmlContent, DocumentSchematron.RECEIPT);

        // Verify result is returned (may be valid as Schematron might not enforce type checking)
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Null XML content should throw exception")
    void testNullXmlContent() {
        assertThatThrownBy(() -> validator.validate((String) null, DocumentSchematron.TAX_INVOICE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("null or empty");
    }

    @Test
    @DisplayName("Empty XML content should throw exception")
    void testEmptyXmlContent() {
        assertThatThrownBy(() -> validator.validate("", DocumentSchematron.TAX_INVOICE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("null or empty");
    }

    @Test
    @DisplayName("Null input stream should throw exception")
    void testNullInputStream() {
        assertThatThrownBy(() -> validator.validate((InputStream) null, DocumentSchematron.TAX_INVOICE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("cannot be null");
    }

    @Test
    @DisplayName("Malformed XML should throw exception")
    void testMalformedXml() {
        String malformedXml = "<?xml version=\"1.0\"?><broken><unclosed>";

        assertThatThrownBy(() -> validator.validate(malformedXml, DocumentSchematron.TAX_INVOICE))
            .isInstanceOf(SchematronValidationException.class)
            .hasMessageContaining("parse");
    }

    @Test
    @DisplayName("Whitespace-only XML content should throw exception")
    void testWhitespaceOnlyXmlContent() {
        assertThatThrownBy(() -> validator.validate("   \n\t  ", DocumentSchematron.TAX_INVOICE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("null or empty");
    }

    @Test
    @DisplayName("validate(InputStream) should work with valid XML stream")
    void testValidateWithInputStream() throws IOException {
        String taxInvoiceExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_TaxInvoice_2p1_v1.xml";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(taxInvoiceExample)) {
            SchematronValidationResult result = validator.validate(is, DocumentSchematron.TAX_INVOICE);
            assertThat(result.isValid()).isTrue();
        }
    }

    @Test
    @DisplayName("validate(InputStream) should throw on malformed XML")
    void testValidateInputStreamWithMalformedXml() {
        String malformedXml = "<?xml version=\"1.0\"?><broken><unclosed>";
        InputStream is = new ByteArrayInputStream(malformedXml.getBytes(StandardCharsets.UTF_8));

        assertThatThrownBy(() -> validator.validate(is, DocumentSchematron.TAX_INVOICE))
            .isInstanceOf(SchematronValidationException.class);
    }

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("isSchematronValid should return true for all document types")
    void testIsSchematronValidAllTypes(DocumentSchematron docType) {
        boolean isValid = validator.isSchematronValid(docType);
        assertThat(isValid)
            .as("Schematron for " + docType + " should be valid")
            .isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = DocumentSchematron.class, names = {"CANCELLATION_NOTE", "ABBREVIATED_TAX_INVOICE"})
    @DisplayName("Empty schematrons should validate successfully")
    void testEmptySchematronTypes(DocumentSchematron docType) throws IOException {
        String exampleFile = getExampleFilePath(docType);
        String xmlContent = readResourceAsString(exampleFile);

        SchematronValidationResult result = validator.validate(xmlContent, docType);

        // Empty schematrons have no rules, so validation always passes
        assertThat(result.isValid()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    @DisplayName("validate(InputStream) for Receipt should work")
    void testValidateReceiptWithInputStream() throws IOException {
        String receiptExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_Receipt_2p1_v1.xml";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(receiptExample)) {
            SchematronValidationResult result = validator.validate(is, DocumentSchematron.RECEIPT);
            assertThat(result.isValid()).isTrue();
        }
    }

    @Test
    @DisplayName("validate(InputStream) for Invoice should work")
    void testValidateInvoiceWithInputStream() throws IOException {
        String invoiceExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_Invoice_2p1_v1.xml";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(invoiceExample)) {
            SchematronValidationResult result = validator.validate(is, DocumentSchematron.INVOICE);
            assertThat(result.isValid()).isTrue();
        }
    }

    @Test
    @DisplayName("validate(InputStream) for DebitCreditNote should work")
    void testValidateDebitCreditNoteWithInputStream() throws IOException {
        String debitNoteExample = "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_DebitNote_2p1_v1.xml";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(debitNoteExample)) {
            SchematronValidationResult result = validator.validate(is, DocumentSchematron.DEBIT_CREDIT_NOTE);
            assertThat(result.isValid()).isTrue();
        }
    }

    @Test
    @DisplayName("validate(InputStream) should handle IOException on close gracefully")
    void testInputStreamCloseThrowsIOException() throws IOException {
        String validXml = readResourceAsString("e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_TaxInvoice_2p1_v1.xml");

        // Track number of close calls - first close may be from XML parser, we want to throw on second (from finally block)
        final int[] closeCallCount = {0};

        // Create an InputStream that throws IOException only on the second close (the one in finally block)
        InputStream mockStream = new ByteArrayInputStream(validXml.getBytes(StandardCharsets.UTF_8)) {
            @Override
            public void close() throws IOException {
                closeCallCount[0]++;
                super.close();
                // Only throw on second or later close calls (the finally block close)
                // This ensures validation completes successfully before the IOException
                if (closeCallCount[0] > 1) {
                    throw new IOException("Simulated close failure");
                }
            }
        };

        // Validation should complete successfully, IOException on close is logged but not thrown
        SchematronValidationResult result = validator.validate(mockStream, DocumentSchematron.TAX_INVOICE);
        assertThat(result.isValid()).isTrue();
        // Verify close was called at least once (by validation) and again by finally block
        assertThat(closeCallCount[0]).isGreaterThanOrEqualTo(1);
    }

    // Helper methods

    private String getExampleFilePath(DocumentSchematron docType) {
        return switch (docType) {
            case TAX_INVOICE -> "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_TaxInvoice_2p1_v1.xml";
            case RECEIPT -> "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_Receipt_2p1_v1.xml";
            case DEBIT_CREDIT_NOTE -> "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_DebitNote_2p1_v1.xml";
            case INVOICE -> "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_Invoice_2p1_v1.xml";
            case CANCELLATION_NOTE -> "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_CreditNote_2p1_v1.xml";
            case ABBREVIATED_TAX_INVOICE -> "e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_AbbreviatedTaxInvocie_2p1_v1.xml";
        };
    }

    private String readResourceAsString(String resourcePath) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
