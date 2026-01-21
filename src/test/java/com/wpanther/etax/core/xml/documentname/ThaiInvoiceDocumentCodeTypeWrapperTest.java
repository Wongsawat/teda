package com.wpanther.etax.core.xml.documentname;

import com.wpanther.etax.core.entity.ThaiDocumentNameCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ThaiInvoiceDocumentCodeTypeWrapper Tests")
class ThaiInvoiceDocumentCodeTypeWrapperTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.getEntity()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("388");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper("80");

        assertThat(type.getCode()).isEqualTo("80");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ThaiInvoiceDocumentCodeTypeWrapper type = ThaiInvoiceDocumentCodeTypeWrapper.of("81");

        assertThat(type.getCode()).isEqualTo("81");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = ThaiInvoiceDocumentCodeTypeWrapper.of(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getNameEn() should return null when value is null")
    void testGetNameEnWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.getNameEn()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameTh("ใบกำกับภาษี");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.getNameTh()).isEqualTo("ใบกำกับภาษี");
    }

    @Test
    @DisplayName("getNameEn() should return English name when value exists")
    void testGetNameEnWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.getNameEn()).isEqualTo("TaxInvoice");
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setDescription("Thai e-Tax Invoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.getDescription()).isEqualTo("Thai e-Tax Invoice");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isTaxInvoice() should return false when value is null")
    void testIsTaxInvoiceWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.isTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isReceipt() should return false when value is null")
    void testIsReceiptWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.isReceipt()).isFalse();
    }

    @Test
    @DisplayName("isAbbreviatedTaxInvoice() should return false when value is null")
    void testIsAbbreviatedTaxInvoiceWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.isAbbreviatedTaxInvoice()).isFalse();
    }

    @Test
    @DisplayName("isDebitNote() should return false when value is null")
    void testIsDebitNoteWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.isDebitNote()).isFalse();
    }

    @Test
    @DisplayName("isCreditNote() should return false when value is null")
    void testIsCreditNoteWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.isCreditNote()).isFalse();
    }

    @Test
    @DisplayName("isCancellationNote() should return false when value is null")
    void testIsCancellationNoteWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.isCancellationNote()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isTaxInvoice() should return true for code 388")
    void testIsTaxInvoiceTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.isTaxInvoice()).isTrue();
    }

    @Test
    @DisplayName("isReceipt() should return true for code T01")
    void testIsReceiptTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T01");
        entity.setNameEn("Receipt");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.isReceipt()).isTrue();
    }

    @Test
    @DisplayName("isAbbreviatedTaxInvoice() should return true for code T05")
    void testIsAbbreviatedTaxInvoiceTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T05");
        entity.setNameEn("AbbreviatedTaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.isAbbreviatedTaxInvoice()).isTrue();
    }

    @Test
    @DisplayName("isDebitNote() should return true for code 80")
    void testIsDebitNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("80");
        entity.setNameEn("DebitNote");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.isDebitNote()).isTrue();
    }

    @Test
    @DisplayName("isCreditNote() should return true for code 81")
    void testIsCreditNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("81");
        entity.setNameEn("CreditNote");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.isCreditNote()).isTrue();
    }

    @Test
    @DisplayName("isCancellationNote() should return true for code T07")
    void testIsCancellationNoteTrue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("T07");
        entity.setNameEn("CancellationNote");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.isCancellationNote()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getEntity() should return the wrapped entity")
    void testGetEntity() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setEntity() should update the wrapped entity")
    void testSetEntity() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("80");
        entity.setNameEn("Receipt");

        type.setEntity(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("80");
    }

    @Test
    @DisplayName("getValue() should return the code string")
    void testGetValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper("388");

        assertThat(type.getValue()).isEqualTo("388");
    }

    @Test
    @DisplayName("setValue(String) should update the code")
    void testSetValueString() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();
        type.setValue("80");

        assertThat(type.getValue()).isEqualTo("80");
        assertThat(type.getCode()).isEqualTo("80");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type1 = new ThaiInvoiceDocumentCodeTypeWrapper(entity);
        ThaiInvoiceDocumentCodeTypeWrapper type2 = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper("388");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper("388");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper("388");

        assertThat(type).isNotEqualTo("388");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        ThaiDocumentNameCode entity1 = new ThaiDocumentNameCode("388");
        entity1.setNameEn("TaxInvoice");
        ThaiDocumentNameCode entity2 = new ThaiDocumentNameCode("80");
        entity2.setNameEn("Receipt");
        ThaiInvoiceDocumentCodeTypeWrapper type1 = new ThaiInvoiceDocumentCodeTypeWrapper(entity1);
        ThaiInvoiceDocumentCodeTypeWrapper type2 = new ThaiInvoiceDocumentCodeTypeWrapper(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type1 = new ThaiInvoiceDocumentCodeTypeWrapper(entity);
        ThaiInvoiceDocumentCodeTypeWrapper type2 = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setNameEn("TaxInvoice");
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper(entity);

        String str = type.toString();

        assertThat(str).contains("388");
        assertThat(str).contains("TaxInvoice");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ThaiInvoiceDocumentCodeTypeWrapper type = new ThaiInvoiceDocumentCodeTypeWrapper();

        assertThat(type.toString()).isEqualTo("null");
    }
}
