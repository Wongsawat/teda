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

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.getDescription()).isEqualTo("Tax Invoice document");
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
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("80", "ใบเพิ่มหนี้", "Debit Note", "Debit Note document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isDebitNote()).isTrue();
    }

    @Test
    @DisplayName("isCreditNote() should return true for code 81")
    void testIsCreditNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("81", "ใบลดหนี้", "Credit Note", "Credit Note document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCreditNote()).isTrue();
    }

    @Test
    @DisplayName("isCommercialInvoice() should return true for code 380")
    void testIsCommercialInvoiceTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("380", "ใบแจ้งการขายสินค้า", "Commercial Invoice", "Commercial Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCommercialInvoice()).isTrue();
    }

    @Test
    @DisplayName("isTaxInvoice() should return true for code 388")
    void testIsTaxInvoiceTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isTaxInvoice()).isTrue();
    }

    @Test
    @DisplayName("isReceipt() should return true for T01")
    void testIsReceiptTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T01", "ใบเสร็จรับเงิน", "Receipt", "Receipt document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isReceipt()).isTrue();
    }

    @Test
    @DisplayName("isAbbreviatedTaxInvoice() should return true for T05")
    void testIsAbbreviatedTaxInvoiceTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T05", "ใบกำกับภาษีอย่างย่อ", "Abbreviated Tax Invoice", "Abbreviated Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isAbbreviatedTaxInvoice()).isTrue();
    }

    @Test
    @DisplayName("isCancellationNote() should return true for T07")
    void testIsCancellationNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T07", "ใบแจ้งยกเลิก", "Cancellation Note", "Cancellation Note document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCancellationNote()).isTrue();
    }

    // False path tests for business logic methods

    @Test
    @DisplayName("isStandardCode() should return false when standardCode is false")
    void testIsStandardCodeFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T01", "ใบเสร็จรับเงิน", "Receipt", "Receipt document");
        entity.setStandardCode(false);
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isStandardCode()).isFalse();
    }

    @Test
    @DisplayName("isThaiExtension() should return false when thaiExtension is false")
    void testIsThaiExtensionFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("80", "ใบเพิ่มหนี้", "Debit Note", "Debit Note document");
        entity.setThaiExtension(false);
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isThaiExtension()).isFalse();
    }

    @Test
    @DisplayName("isDebitNote() should return false for non-80 code")
    void testIsDebitNoteFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isDebitNote()).isFalse();
    }

    @Test
    @DisplayName("isCreditNote() should return false for non-81 code")
    void testIsCreditNoteFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCreditNote()).isFalse();
    }

    @Test
    @DisplayName("isCommercialInvoice() should return false for non-380 code")
    void testIsCommercialInvoiceFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCommercialInvoice()).isFalse();
    }

    @Test
    @DisplayName("isTaxInvoice() should return false for non-388 code")
    void testIsTaxInvoiceFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("80", "ใบเพิ่มหนี้", "Debit Note", "Debit Note document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isReceipt() should return false for non-T01 code")
    void testIsReceiptFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isReceipt()).isFalse();
    }

    @Test
    @DisplayName("isAbbreviatedTaxInvoice() should return false for non-T05/T06 code")
    void testIsAbbreviatedTaxInvoiceFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isAbbreviatedTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isCancellationNote() should return false for non-T07 code")
    void testIsCancellationNoteFalse() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Tax Invoice document");
        ThaiDocumentNameCodeInvoiceType type = new ThaiDocumentNameCodeInvoiceType(entity);

        assertThat(type.isCancellationNote()).isFalse();
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
