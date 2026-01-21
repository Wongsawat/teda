package com.wpanther.etax.core.xml.documentname;

import com.wpanther.etax.core.entity.ThaiDocumentNameCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ThaiDocumentNameCodeInvoiceType.
 */
@DisplayName("ThaiDocumentNameCodeInvoiceType Tests")
class ThaiDocumentNameCodeInvoiceTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("388");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType("388");

        assertThat(type.getCode()).isEqualTo("388");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ThaiDocumentNameCodeInvoiceType type = ThaiDocumentNameCodeInvoiceType.of("380");

        assertThat(type.getCode()).isEqualTo("380");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("383", "ใบเพิ่มหนี้", "Debit Note", "Debit Note document");
        ThaiDocumentNameCodeInvoiceType type = ThaiDocumentNameCodeInvoiceType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getNameEn() should return null when value is null")
    void testGetNameEnWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.getNameEn()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.getNameTh()).isEqualTo("ใบกำกับภาษี");
    }

    @Test
    @DisplayName("getNameEn() should return English name when value exists")
    void testGetNameEnWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.getNameEn()).isEqualTo("Tax Invoice");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isStandardCode() should return false when value is null")
    void testIsStandardCodeWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isStandardCode()).isFalse();
    }

    @Test
    @DisplayName("isThaiExtension() should return false when value is null")
    void testIsThaiExtensionWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isThaiExtension()).isFalse();
    }

    @Test
    @DisplayName("isDebitNote() should return false when value is null")
    void testIsDebitNoteWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isDebitNote()).isFalse();
    }

    @Test
    @DisplayName("isCreditNote() should return false when value is null")
    void testIsCreditNoteWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isCreditNote()).isFalse();
    }

    @Test
    @DisplayName("isCommercialInvoice() should return false when value is null")
    void testIsCommercialInvoiceWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isCommercialInvoice()).isFalse();
    }

    @Test
    @DisplayName("isTaxInvoice() should return false when value is null")
    void testIsTaxInvoiceWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isReceipt() should return false when value is null")
    void testIsReceiptWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isReceipt()).isFalse();
    }

    @Test
    @DisplayName("isAbbreviatedTaxInvoice() should return false when value is null")
    void testIsAbbreviatedTaxInvoiceWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isAbbreviatedTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isCancellationNote() should return false when value is null")
    void testIsCancellationNoteWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.isCancellationNote()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isStandardCode() should return true for standard code")
    void testIsStandardCodeTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        entity.setStandardCode(true);
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isStandardCode()).isTrue();
    }

    @Test
    @DisplayName("isThaiExtension() should return true for Thai extension")
    void testIsThaiExtensionTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T01", "ใบเสร็จรับเงิน", "Receipt", "Receipt document");
        entity.setThaiExtension(true);
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isThaiExtension()).isTrue();
    }

    @Test
    @DisplayName("isDebitNote() should return true for code 80")
    void testIsDebitNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("383", "ใบเพิ่มหนี้", "Debit Note", "Debit Note document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isDebitNote()).isTrue();
    }

    @Test
    @DisplayName("isCreditNote() should return true for code 81")
    void testIsCreditNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("381", "ใบลดหนี้", "Credit Note", "Credit Note document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCreditNote()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("383", "ใบเพิ่มหนี้", "Debit Note", "Debit Note document");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("383");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type1 = new ThaiDocumentNameCodeInvoiceType(entity);
        ThaiDocumentNameCodeInvoiceType type2 = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType("388");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType("388");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType("388");

        assertThat(type).isNotEqualTo("388");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type1 = new ThaiDocumentNameCodeInvoiceType();
        ThaiDocumentNameCodeInvoiceType type2 = new ThaiDocumentNameCodeInvoiceType("388");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type1 = new ThaiDocumentNameCodeInvoiceType(entity);
        ThaiDocumentNameCodeInvoiceType type2 = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        String str = type.toString();

        assertThat(str).contains("388");
        assertThat(str).contains("Tax Invoice");
        assertThat(str).contains("ใบกำกับภาษี");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType();

        assertThat(type.toString()).isEqualTo("null");
    }
}