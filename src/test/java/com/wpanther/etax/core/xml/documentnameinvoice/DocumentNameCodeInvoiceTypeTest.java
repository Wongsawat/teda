package com.wpanther.etax.core.xml.documentnameinvoice;

import com.wpanther.etax.core.entity.UNECEDocumentNameCodeInvoice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for DocumentNameCodeInvoiceType.
 * Tests for UN/CEFACT Document Name Code (Invoice) wrapper type.
 */
@DisplayName("DocumentNameCodeInvoiceType Tests")
class DocumentNameCodeInvoiceTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setName("Commercial invoice");
        entity.setCategory("Invoice");
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("380");
        assertThat(type.getName()).isEqualTo("Commercial invoice");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType("380");
        assertThat(type.getCode()).isEqualTo("380");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        DocumentNameCodeInvoiceType type = DocumentNameCodeInvoiceType.of("380");
        assertThat(type.getCode()).isEqualTo("380");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("381");
        entity.setName("Credit note");
        DocumentNameCodeInvoiceType type = DocumentNameCodeInvoiceType.of(entity);
        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getCode() should return null when value is null")
    void testGetCodeWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.getCategory()).isNull();
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isCredit() should return false when value is null")
    void testIsCreditWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.isCredit()).isFalse();
    }

    @Test
    @DisplayName("isDebit() should return false when value is null")
    void testIsDebitWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.isDebit()).isFalse();
    }

    @Test
    @DisplayName("requiresPayment() should return false when value is null")
    void testRequiresPaymentWithNullValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        assertThat(type.requiresPayment()).isFalse();
    }

    // Business logic method tests with valid entity - true cases

    @Test
    @DisplayName("isCredit() should return true for credit note")
    void testIsCreditTrue() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("381");
        entity.setIsCredit(true);
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.isCredit()).isTrue();
    }

    @Test
    @DisplayName("isDebit() should return true for debit note")
    void testIsDebitTrue() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("383");
        entity.setIsDebit(true);
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.isDebit()).isTrue();
    }

    @Test
    @DisplayName("requiresPayment() should return true when set")
    void testRequiresPaymentTrue() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setRequiresPayment(true);
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.requiresPayment()).isTrue();
    }

    // Business logic method tests - false cases

    @Test
    @DisplayName("isCredit() should return false when isCredit is false")
    void testIsCreditFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setIsCredit(false);
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.isCredit()).isFalse();
    }

    @Test
    @DisplayName("isDebit() should return false when isDebit is false")
    void testIsDebitFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setIsDebit(false);
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.isDebit()).isFalse();
    }

    @Test
    @DisplayName("requiresPayment() should return false when set to false")
    void testRequiresPaymentFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("381");
        entity.setRequiresPayment(false);
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.requiresPayment()).isFalse();
    }

    // Getter tests with valid entity

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setDescription("Document used to request payment");
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.getDescription()).isEqualTo("Document used to request payment");
    }

    @Test
    @DisplayName("getCategory() should return category when value exists")
    void testGetCategoryWithValue() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setCategory("Invoice");
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.getCategory()).isEqualTo("Invoice");
    }

    // setValue test

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType();
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("383");
        entity.setName("Debit note");
        type.setValue(entity);
        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("383");
    }

    // getValue test

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setName("Commercial invoice");
        DocumentNameCodeInvoiceType type = new DocumentNameCodeInvoiceType(entity);
        assertThat(type.getValue()).isEqualTo(entity);
    }
}
