package com.wpanther.etax.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for SchematronValidationException.
 */
@DisplayName("SchematronValidationException Tests")
class SchematronValidationExceptionTest {

    // Constructor tests

    @Test
    @DisplayName("Constructor with message only should set message")
    void testConstructorWithMessageOnly() {
        String message = "Validation failed";
        SchematronValidationException exception = new SchematronValidationException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isNull();
        assertThat(exception.getDocumentType()).isNull();
    }

    @Test
    @DisplayName("Constructor with message and cause should set both")
    void testConstructorWithMessageAndCause() {
        String message = "Validation failed";
        Throwable cause = new RuntimeException("Root cause");
        SchematronValidationException exception = new SchematronValidationException(message, cause);

        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getDocumentType()).isNull();
    }

    @Test
    @DisplayName("Constructor with message and document type should set both")
    void testConstructorWithMessageAndDocumentType() {
        String message = "Validation failed";
        DocumentSchematron docType = DocumentSchematron.TAX_INVOICE;
        SchematronValidationException exception = new SchematronValidationException(message, docType);

        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isNull();
        assertThat(exception.getDocumentType()).isEqualTo(docType);
    }

    @Test
    @DisplayName("Constructor with message, document type and cause should set all")
    void testConstructorWithAllParameters() {
        String message = "Validation failed";
        DocumentSchematron docType = DocumentSchematron.RECEIPT;
        Throwable cause = new RuntimeException("Root cause");
        SchematronValidationException exception = new SchematronValidationException(message, docType, cause);

        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getDocumentType()).isEqualTo(docType);
    }

    // getDocumentType tests

    @Test
    @DisplayName("getDocumentType() should return TAX_INVOICE")
    void testGetDocumentTypeTaxInvoice() {
        SchematronValidationException exception = new SchematronValidationException(
            "Error", DocumentSchematron.TAX_INVOICE);

        assertThat(exception.getDocumentType()).isEqualTo(DocumentSchematron.TAX_INVOICE);
    }

    @Test
    @DisplayName("getDocumentType() should return RECEIPT")
    void testGetDocumentTypeReceipt() {
        SchematronValidationException exception = new SchematronValidationException(
            "Error", DocumentSchematron.RECEIPT);

        assertThat(exception.getDocumentType()).isEqualTo(DocumentSchematron.RECEIPT);
    }

    @Test
    @DisplayName("getDocumentType() should return DEBIT_CREDIT_NOTE")
    void testGetDocumentTypeDebitCreditNote() {
        SchematronValidationException exception = new SchematronValidationException(
            "Error", DocumentSchematron.DEBIT_CREDIT_NOTE);

        assertThat(exception.getDocumentType()).isEqualTo(DocumentSchematron.DEBIT_CREDIT_NOTE);
    }

    @Test
    @DisplayName("getDocumentType() should return INVOICE")
    void testGetDocumentTypeInvoice() {
        SchematronValidationException exception = new SchematronValidationException(
            "Error", DocumentSchematron.INVOICE);

        assertThat(exception.getDocumentType()).isEqualTo(DocumentSchematron.INVOICE);
    }

    @Test
    @DisplayName("getDocumentType() should return CANCELLATION_NOTE")
    void testGetDocumentTypeCancellationNote() {
        SchematronValidationException exception = new SchematronValidationException(
            "Error", DocumentSchematron.CANCELLATION_NOTE);

        assertThat(exception.getDocumentType()).isEqualTo(DocumentSchematron.CANCELLATION_NOTE);
    }

    @Test
    @DisplayName("getDocumentType() should return ABBREVIATED_TAX_INVOICE")
    void testGetDocumentTypeAbbreviatedTaxInvoice() {
        SchematronValidationException exception = new SchematronValidationException(
            "Error", DocumentSchematron.ABBREVIATED_TAX_INVOICE);

        assertThat(exception.getDocumentType()).isEqualTo(DocumentSchematron.ABBREVIATED_TAX_INVOICE);
    }

    @Test
    @DisplayName("getDocumentType() should return null when not set")
    void testGetDocumentTypeNull() {
        SchematronValidationException exception = new SchematronValidationException("Error");

        assertThat(exception.getDocumentType()).isNull();
    }

    // Exception hierarchy tests

    @Test
    @DisplayName("SchematronValidationException should extend RuntimeException")
    void testExtendsRuntimeException() {
        SchematronValidationException exception = new SchematronValidationException("Error");

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("SchematronValidationException should extend Exception")
    void testExtendsException() {
        SchematronValidationException exception = new SchematronValidationException("Error");

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("SchematronValidationException should extend Throwable")
    void testExtendsThrowable() {
        SchematronValidationException exception = new SchematronValidationException("Error");

        assertThat(exception).isInstanceOf(Throwable.class);
    }

    // Null handling tests

    @Test
    @DisplayName("Constructor should accept null message")
    void testNullMessage() {
        SchematronValidationException exception = new SchematronValidationException(null);

        assertThat(exception.getMessage()).isNull();
    }

    @Test
    @DisplayName("Constructor should accept null cause")
    void testNullCause() {
        SchematronValidationException exception = new SchematronValidationException("Error", (Throwable) null);

        assertThat(exception.getCause()).isNull();
    }

    @Test
    @DisplayName("Constructor should accept null document type")
    void testNullDocumentType() {
        SchematronValidationException exception = new SchematronValidationException("Error", (DocumentSchematron) null);

        assertThat(exception.getDocumentType()).isNull();
    }

    // Real-world scenario tests

    @Test
    @DisplayName("Exception should be usable in try-catch")
    void testUsableInTryCatch() {
        try {
            throw new SchematronValidationException("Parse error", DocumentSchematron.TAX_INVOICE);
        } catch (SchematronValidationException e) {
            assertThat(e.getMessage()).isEqualTo("Parse error");
            assertThat(e.getDocumentType()).isEqualTo(DocumentSchematron.TAX_INVOICE);
        }
    }

    @Test
    @DisplayName("Exception should preserve cause chain")
    void testCauseChain() {
        Exception rootCause = new IllegalArgumentException("Invalid XML");
        Exception intermediateCause = new RuntimeException("Processing failed", rootCause);
        SchematronValidationException exception = new SchematronValidationException(
            "Validation failed", DocumentSchematron.RECEIPT, intermediateCause);

        assertThat(exception.getCause()).isEqualTo(intermediateCause);
        assertThat(exception.getCause().getCause()).isEqualTo(rootCause);
    }

    @Test
    @DisplayName("Exception should work with nested exceptions")
    void testNestedExceptions() {
        try {
            try {
                throw new IllegalArgumentException("Inner error");
            } catch (IllegalArgumentException e) {
                throw new SchematronValidationException("Outer error", DocumentSchematron.INVOICE, e);
            }
        } catch (SchematronValidationException e) {
            assertThat(e.getMessage()).isEqualTo("Outer error");
            assertThat(e.getCause()).isInstanceOf(IllegalArgumentException.class);
            assertThat(e.getCause().getMessage()).isEqualTo("Inner error");
        }
    }

    // getMessage tests

    @Test
    @DisplayName("getMessage() should return the error message")
    void testGetMessage() {
        String message = "Unable to load Schematron file";
        SchematronValidationException exception = new SchematronValidationException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("getMessage() should return message with Thai characters")
    void testGetMessageWithThaiCharacters() {
        String message = "ไม่สามารถโหลดไฟล์ Schematron / Unable to load Schematron file";
        SchematronValidationException exception = new SchematronValidationException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getMessage()).contains("ไม่สามารถ");
    }

    // getCause tests

    @Test
    @DisplayName("getCause() should return the cause")
    void testGetCause() {
        Throwable cause = new IllegalStateException("Invalid state");
        SchematronValidationException exception = new SchematronValidationException("Error", cause);

        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("getCause() should return null when no cause")
    void testGetCauseNull() {
        SchematronValidationException exception = new SchematronValidationException("Error");

        assertThat(exception.getCause()).isNull();
    }
}
