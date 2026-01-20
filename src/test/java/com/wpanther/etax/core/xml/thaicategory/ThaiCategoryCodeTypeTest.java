package com.wpanther.etax.core.xml.thaicategory;

import com.wpanther.etax.core.entity.ThaiCategoryCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ThaiCategoryCodeType.
 */
@DisplayName("ThaiCategoryCodeType Tests")
class ThaiCategoryCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("01");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType("01");

        assertThat(type.getCode()).isEqualTo("01");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ThaiCategoryCodeType type = ThaiCategoryCodeType.of("02");

        assertThat(type.getCode()).isEqualTo("02");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ThaiCategoryCode entity = new ThaiCategoryCode("02", "อ้างอิงใบรับชำระเงินล่วงหน้า", "Advance payment reference");
        ThaiCategoryCodeType type = ThaiCategoryCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("originalDocumentReference() should create code 01")
    void testOriginalDocumentReferenceFactory() {
        ThaiCategoryCodeType type = ThaiCategoryCodeType.originalDocumentReference();

        assertThat(type.getCode()).isEqualTo("01");
    }

    @Test
    @DisplayName("advancePaymentReference() should create code 02")
    void testAdvancePaymentReferenceFactory() {
        ThaiCategoryCodeType type = ThaiCategoryCodeType.advancePaymentReference();

        assertThat(type.getCode()).isEqualTo("02");
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getNameEn() should return null when value is null")
    void testGetNameEnWithNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.getNameEn()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.getNameTh()).isEqualTo("อ้างอิงเอกสารต้นฉบับ");
    }

    @Test
    @DisplayName("getNameEn() should return English name when value exists")
    void testGetNameEnWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.getNameEn()).isEqualTo("Original document reference");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isOriginalDocumentReference() should return false when value is null")
    void testIsOriginalDocumentReferenceWithNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.isOriginalDocumentReference()).isFalse();
    }

    @Test
    @DisplayName("isAdvancePaymentReference() should return false when value is null")
    void testIsAdvancePaymentReferenceWithNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.isAdvancePaymentReference()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isOriginalDocumentReference() should return true for code 01")
    void testIsOriginalDocumentReferenceTrue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.isOriginalDocumentReference()).isTrue();
    }

    @Test
    @DisplayName("isAdvancePaymentReference() should return true for code 02")
    void testIsAdvancePaymentReferenceTrue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("02", "อ้างอิงใบรับชำระเงินล่วงหน้า", "Advance payment reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.isAdvancePaymentReference()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();
        ThaiCategoryCode entity = new ThaiCategoryCode("02", "อ้างอิงใบรับชำระเงินล่วงหน้า", "Advance payment reference");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("02");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type1 = new ThaiCategoryCodeType(entity);
        ThaiCategoryCodeType type2 = new ThaiCategoryCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType("01");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType("01");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType("01");

        assertThat(type).isNotEqualTo("01");
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type1 = new ThaiCategoryCodeType(entity);
        ThaiCategoryCodeType type2 = new ThaiCategoryCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should handle null value")
    void testHashCodeNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        int hashCode = type.hashCode();
        assertThat(hashCode).isNotNull();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return entity toString when value is not null")
    void testToStringWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "อ้างอิงเอกสารต้นฉบับ", "Original document reference");
        ThaiCategoryCodeType type = new ThaiCategoryCodeType(entity);

        assertThat(type.toString()).isEqualTo(entity.toString());
    }

    @Test
    @DisplayName("toString() should return null indicator when value is null")
    void testToStringWithNullValue() {
        ThaiCategoryCodeType type = new ThaiCategoryCodeType();

        assertThat(type.toString()).isEqualTo("ThaiCategoryCodeType{null}");
    }
}