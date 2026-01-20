package com.wpanther.etax.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for DocumentSchematron enum.
 */
@DisplayName("DocumentSchematron Tests")
class DocumentSchematronTest {

    // Enum values tests

    @Test
    @DisplayName("TAX_INVOICE should have correct properties")
    void testTaxInvoice() {
        DocumentSchematron doc = DocumentSchematron.TAX_INVOICE;

        assertThat(doc.getDocumentName()).isEqualTo("TaxInvoice");
        assertThat(doc.getSchematronPath()).isEqualTo("e-tax-invoice-receipt-v2.1/ETDA/data/standard/TaxInvoice_Schematron_2p1.sch");
        assertThat(doc.getRulePrefix()).isEqualTo("TIV");
    }

    @Test
    @DisplayName("RECEIPT should have correct properties")
    void testReceipt() {
        DocumentSchematron doc = DocumentSchematron.RECEIPT;

        assertThat(doc.getDocumentName()).isEqualTo("Receipt");
        assertThat(doc.getSchematronPath()).isEqualTo("e-tax-invoice-receipt-v2.1/ETDA/data/standard/Receipt_Schematron_2p1.sch");
        assertThat(doc.getRulePrefix()).isEqualTo("RCT");
    }

    @Test
    @DisplayName("DEBIT_CREDIT_NOTE should have correct properties")
    void testDebitCreditNote() {
        DocumentSchematron doc = DocumentSchematron.DEBIT_CREDIT_NOTE;

        assertThat(doc.getDocumentName()).isEqualTo("DebitCreditNote");
        assertThat(doc.getSchematronPath()).isEqualTo("e-tax-invoice-receipt-v2.1/ETDA/data/standard/DebitCreditNote_Schematron_2p1.sch");
        assertThat(doc.getRulePrefix()).isEqualTo("DCN");
    }

    @Test
    @DisplayName("INVOICE should have correct properties")
    void testInvoice() {
        DocumentSchematron doc = DocumentSchematron.INVOICE;

        assertThat(doc.getDocumentName()).isEqualTo("Invoice");
        assertThat(doc.getSchematronPath()).isEqualTo("e-tax-invoice-receipt-v2.1/ETDA/data/standard/Invoice_Schematron_2p1.sch");
        assertThat(doc.getRulePrefix()).isEqualTo("INV");
    }

    @Test
    @DisplayName("CANCELLATION_NOTE should have correct properties")
    void testCancellationNote() {
        DocumentSchematron doc = DocumentSchematron.CANCELLATION_NOTE;

        assertThat(doc.getDocumentName()).isEqualTo("CancellationNote");
        assertThat(doc.getSchematronPath()).isEqualTo("e-tax-invoice-receipt-v2.1/ETDA/data/standard/CancellationNote_Schematron_2p1.sch");
        assertThat(doc.getRulePrefix()).isEqualTo("CN");
    }

    @Test
    @DisplayName("ABBREVIATED_TAX_INVOICE should have correct properties")
    void testAbbreviatedTaxInvoice() {
        DocumentSchematron doc = DocumentSchematron.ABBREVIATED_TAX_INVOICE;

        assertThat(doc.getDocumentName()).isEqualTo("AbbreviatedTaxInvoice");
        assertThat(doc.getSchematronPath()).isEqualTo("e-tax-invoice-receipt-v2.1/ETDA/data/standard/AbbreviatedTaxInvoice_Schematron_2p1.sch");
        assertThat(doc.getRulePrefix()).isEqualTo("ATI");
    }

    // isEmptySchematron tests

    @Test
    @DisplayName("isEmptySchematron() should return true for CANCELLATION_NOTE")
    void testIsEmptySchematronCancellationNote() {
        assertThat(DocumentSchematron.CANCELLATION_NOTE.isEmptySchematron()).isTrue();
    }

    @Test
    @DisplayName("isEmptySchematron() should return true for ABBREVIATED_TAX_INVOICE")
    void testIsEmptySchematronAbbreviatedTaxInvoice() {
        assertThat(DocumentSchematron.ABBREVIATED_TAX_INVOICE.isEmptySchematron()).isTrue();
    }

    @Test
    @DisplayName("isEmptySchematron() should return false for TAX_INVOICE")
    void testIsEmptySchematronTaxInvoice() {
        assertThat(DocumentSchematron.TAX_INVOICE.isEmptySchematron()).isFalse();
    }

    @Test
    @DisplayName("isEmptySchematron() should return false for RECEIPT")
    void testIsEmptySchematronReceipt() {
        assertThat(DocumentSchematron.RECEIPT.isEmptySchematron()).isFalse();
    }

    @Test
    @DisplayName("isEmptySchematron() should return false for DEBIT_CREDIT_NOTE")
    void testIsEmptySchematronDebitCreditNote() {
        assertThat(DocumentSchematron.DEBIT_CREDIT_NOTE.isEmptySchematron()).isFalse();
    }

    @Test
    @DisplayName("isEmptySchematron() should return false for INVOICE")
    void testIsEmptySchematronInvoice() {
        assertThat(DocumentSchematron.INVOICE.isEmptySchematron()).isFalse();
    }

    // All enum values tests

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("All enum values should have valid paths ending with .sch")
    void testAllEnumValuesHaveValidPaths(DocumentSchematron docType) {
        assertThat(docType.getSchematronPath())
            .isNotNull()
            .isNotEmpty()
            .endsWith(".sch");
    }

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("All enum values should have non-null document names")
    void testAllEnumValuesHaveDocumentNames(DocumentSchematron docType) {
        assertThat(docType.getDocumentName())
            .isNotNull()
            .isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("All enum values should have non-null rule prefixes")
    void testAllEnumValuesHaveRulePrefixes(DocumentSchematron docType) {
        assertThat(docType.getRulePrefix())
            .isNotNull()
            .isNotEmpty();
    }

    @Test
    @DisplayName("Enum should have exactly 6 values")
    void testEnumCount() {
        assertThat(DocumentSchematron.values()).hasSize(6);
    }

    // getDocumentName tests

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("getDocumentName() should return document name without spaces")
    void testGetDocumentNameNoSpaces(DocumentSchematron docType) {
        assertThat(docType.getDocumentName()).doesNotContain(" ");
    }

    // getSchematronPath tests

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("getSchematronPath() should contain document name")
    void testGetSchematronPathContainsDocumentName(DocumentSchematron docType) {
        String path = docType.getSchematronPath();
        String docName = docType.getDocumentName();

        assertThat(path).contains(docName);
    }

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("getSchematronPath() should start with correct directory")
    void testGetSchematronPathStartsCorrectly(DocumentSchematron docType) {
        assertThat(docType.getSchematronPath())
            .startsWith("e-tax-invoice-receipt-v2.1/ETDA/data/standard/");
    }

    @ParameterizedTest
    @EnumSource(DocumentSchematron.class)
    @DisplayName("getSchematronPath() should contain version 2p1")
    void testGetSchematronPathContainsVersion(DocumentSchematron docType) {
        assertThat(docType.getSchematronPath()).contains("2p1");
    }

    // getRulePrefix tests

    @Test
    @DisplayName("getRulePrefix() should return uppercase prefixes")
    void testGetRulePrefixUppercase() {
        for (DocumentSchematron docType : DocumentSchematron.values()) {
            String prefix = docType.getRulePrefix();
            assertThat(prefix).isUpperCase();
        }
    }

    @Test
    @DisplayName("All rule prefixes should be unique")
    void testRulePrefixesUnique() {
        DocumentSchematron[] values = DocumentSchematron.values();
        long uniqueCount = java.util.Arrays.stream(values)
            .map(DocumentSchematron::getRulePrefix)
            .distinct()
            .count();

        assertThat(uniqueCount).isEqualTo(values.length);
    }

    // valueOf tests

    @Test
    @DisplayName("valueOf() should return correct enum for TAX_INVOICE")
    void testValueOfTaxInvoice() {
        assertThat(DocumentSchematron.valueOf("TAX_INVOICE")).isEqualTo(DocumentSchematron.TAX_INVOICE);
    }

    @Test
    @DisplayName("valueOf() should return correct enum for RECEIPT")
    void testValueOfReceipt() {
        assertThat(DocumentSchematron.valueOf("RECEIPT")).isEqualTo(DocumentSchematron.RECEIPT);
    }

    @Test
    @DisplayName("valueOf() should return correct enum for DEBIT_CREDIT_NOTE")
    void testValueOfDebitCreditNote() {
        assertThat(DocumentSchematron.valueOf("DEBIT_CREDIT_NOTE")).isEqualTo(DocumentSchematron.DEBIT_CREDIT_NOTE);
    }

    @Test
    @DisplayName("valueOf() should return correct enum for INVOICE")
    void testValueOfInvoice() {
        assertThat(DocumentSchematron.valueOf("INVOICE")).isEqualTo(DocumentSchematron.INVOICE);
    }

    @Test
    @DisplayName("valueOf() should return correct enum for CANCELLATION_NOTE")
    void testValueOfCancellationNote() {
        assertThat(DocumentSchematron.valueOf("CANCELLATION_NOTE")).isEqualTo(DocumentSchematron.CANCELLATION_NOTE);
    }

    @Test
    @DisplayName("valueOf() should return correct enum for ABBREVIATED_TAX_INVOICE")
    void testValueOfAbbreviatedTaxInvoice() {
        assertThat(DocumentSchematron.valueOf("ABBREVIATED_TAX_INVOICE")).isEqualTo(DocumentSchematron.ABBREVIATED_TAX_INVOICE);
    }
}
