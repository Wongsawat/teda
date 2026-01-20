package com.wpanther.etax.core.xml.thaimessagefunction;

import com.wpanther.etax.core.entity.ThaiMessageFunctionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ThaiMessageFunctionCodeType.
 */
@DisplayName("ThaiMessageFunctionCodeType Tests")
class ThaiMessageFunctionCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("TIVC01");
        entity.setDescriptionEn("Tax Invoice - Original");
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("TIVC01");
    }

    // Factory method tests

    @Test
    @DisplayName("taxInvoiceOriginal() should create TIVC01")
    void testTaxInvoiceOriginalFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.taxInvoiceOriginal();

        assertThat(type.getCode()).isEqualTo("TIVC01");
    }

    @Test
    @DisplayName("taxInvoiceReplacement() should create TIVC02")
    void testTaxInvoiceReplacementFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.taxInvoiceReplacement();

        assertThat(type.getCode()).isEqualTo("TIVC02");
    }

    @Test
    @DisplayName("receiptOriginal() should create RCTC01")
    void testReceiptOriginalFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.receiptOriginal();

        assertThat(type.getCode()).isEqualTo("RCTC01");
    }

    @Test
    @DisplayName("receiptReplacement() should create RCTC02")
    void testReceiptReplacementFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.receiptReplacement();

        assertThat(type.getCode()).isEqualTo("RCTC02");
    }

    @Test
    @DisplayName("receiptCopy() should create RCTC03")
    void testReceiptCopyFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.receiptCopy();

        assertThat(type.getCode()).isEqualTo("RCTC03");
    }

    @Test
    @DisplayName("receiptCancellation() should create RCTC04")
    void testReceiptCancellationFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.receiptCancellation();

        assertThat(type.getCode()).isEqualTo("RCTC04");
    }

    @Test
    @DisplayName("debitNoteGoodsOriginal() should create DBNG01")
    void testDebitNoteGoodsOriginalFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.debitNoteGoodsOriginal();

        assertThat(type.getCode()).isEqualTo("DBNG01");
    }

    @Test
    @DisplayName("debitNoteServicesOriginal() should create DBNS01")
    void testDebitNoteServicesOriginalFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.debitNoteServicesOriginal();

        assertThat(type.getCode()).isEqualTo("DBNS01");
    }

    @Test
    @DisplayName("creditNoteGoodsOriginal() should create CDNG01")
    void testCreditNoteGoodsOriginalFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.creditNoteGoodsOriginal();

        assertThat(type.getCode()).isEqualTo("CDNG01");
    }

    @Test
    @DisplayName("creditNoteServicesOriginal() should create CDNS01")
    void testCreditNoteServicesOriginalFactory() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.creditNoteServicesOriginal();

        assertThat(type.getCode()).isEqualTo("CDNS01");
    }

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ThaiMessageFunctionCodeType type = ThaiMessageFunctionCodeType.of("TIVC01");

        assertThat(type.getCode()).isEqualTo("TIVC01");
    }

    // Getter tests with null value

    @Test
    @DisplayName("getDescriptionEn() should return null when value is null")
    void testGetDescriptionEnWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.getDescriptionEn()).isNull();
    }

    @Test
    @DisplayName("getDescriptionTh() should return null when value is null")
    void testGetDescriptionThWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.getDescriptionTh()).isNull();
    }

    @Test
    @DisplayName("getDocumentType() should return null when value is null")
    void testGetDocumentTypeWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.getDocumentType()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.getCategory()).isNull();
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isDebitNote() should return false when value is null")
    void testIsDebitNoteWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isDebitNote()).isFalse();
    }

    @Test
    @DisplayName("isCreditNote() should return false when value is null")
    void testIsCreditNoteWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isCreditNote()).isFalse();
    }

    @Test
    @DisplayName("isTaxInvoice() should return false when value is null")
    void testIsTaxInvoiceWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isReceipt() should return false when value is null")
    void testIsReceiptWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isReceipt()).isFalse();
    }

    @Test
    @DisplayName("isGoods() should return false when value is null")
    void testIsGoodsWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isGoods()).isFalse();
    }

    @Test
    @DisplayName("isService() should return false when value is null")
    void testIsServiceWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isService()).isFalse();
    }

    @Test
    @DisplayName("isOriginal() should return false when value is null")
    void testIsOriginalWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isOriginal()).isFalse();
    }

    @Test
    @DisplayName("isReplacement() should return false when value is null")
    void testIsReplacementWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isReplacement()).isFalse();
    }

    @Test
    @DisplayName("isCancellation() should return false when value is null")
    void testIsCancellationWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isCancellation()).isFalse();
    }

    @Test
    @DisplayName("isCopy() should return false when value is null")
    void testIsCopyWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isCopy()).isFalse();
    }

    @Test
    @DisplayName("isAddition() should return false when value is null")
    void testIsAdditionWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isAddition()).isFalse();
    }

    @Test
    @DisplayName("isOther() should return false when value is null")
    void testIsOtherWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isOther()).isFalse();
    }

    @Test
    @DisplayName("isDebitNoteGoods() should return false when value is null")
    void testIsDebitNoteGoodsWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isDebitNoteGoods()).isFalse();
    }

    @Test
    @DisplayName("isDebitNoteServices() should return false when value is null")
    void testIsDebitNoteServicesWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isDebitNoteServices()).isFalse();
    }

    @Test
    @DisplayName("isCreditNoteGoods() should return false when value is null")
    void testIsCreditNoteGoodsWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isCreditNoteGoods()).isFalse();
    }

    @Test
    @DisplayName("isCreditNoteServices() should return false when value is null")
    void testIsCreditNoteServicesWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.isCreditNoteServices()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("TIVC01");
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("RCTC01");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("RCTC01");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same code")
    void testEqualsSameCode() {
        ThaiMessageFunctionCode entity1 = new ThaiMessageFunctionCode("TIVC01");
        ThaiMessageFunctionCode entity2 = new ThaiMessageFunctionCode("TIVC01");
        ThaiMessageFunctionCodeType type1 = new ThaiMessageFunctionCodeType(entity1);
        ThaiMessageFunctionCodeType type2 = new ThaiMessageFunctionCodeType(entity2);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType(new ThaiMessageFunctionCode("TIVC01"));

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType(new ThaiMessageFunctionCode("TIVC01"));

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType(new ThaiMessageFunctionCode("TIVC01"));

        assertThat(type).isNotEqualTo("TIVC01");
    }

    @Test
    @DisplayName("equals() should return true for both null values")
    void testEqualsBothNullValues() {
        ThaiMessageFunctionCodeType type1 = new ThaiMessageFunctionCodeType();
        ThaiMessageFunctionCodeType type2 = new ThaiMessageFunctionCodeType();

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return false when one value is null")
    void testEqualsOneNullValue() {
        ThaiMessageFunctionCodeType type1 = new ThaiMessageFunctionCodeType();
        ThaiMessageFunctionCodeType type2 = new ThaiMessageFunctionCodeType(new ThaiMessageFunctionCode("TIVC01"));

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ThaiMessageFunctionCode entity1 = new ThaiMessageFunctionCode("TIVC01");
        ThaiMessageFunctionCode entity2 = new ThaiMessageFunctionCode("TIVC01");
        ThaiMessageFunctionCodeType type1 = new ThaiMessageFunctionCodeType(entity1);
        ThaiMessageFunctionCodeType type2 = new ThaiMessageFunctionCodeType(entity2);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return code when value is not null")
    void testToStringWithValue() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("TIVC01");
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType(entity);

        assertThat(type.toString()).isEqualTo("TIVC01");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ThaiMessageFunctionCodeType type = new ThaiMessageFunctionCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}