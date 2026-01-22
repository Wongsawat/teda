package com.wpanther.etax.core.xml.category;

import com.wpanther.etax.core.entity.ThaiCategoryCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ThaiCategoryCodeTypeWrapper Tests")
class ThaiCategoryCodeTypeWrapperTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameTh("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper("2");

        assertThat(type.getCode()).isEqualTo("2");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ThaiCategoryCodeTypeWrapper type = ThaiCategoryCodeTypeWrapper.of("3");

        assertThat(type.getCode()).isEqualTo("3");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameTh("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type = ThaiCategoryCodeTypeWrapper.of(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameEn() should return null when value is null")
    void testGetNameEnWithNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getNameEn()).isNull();
    }

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameTh("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.getNameTh()).isEqualTo("Complete construction of building");
    }

    @Test
    @DisplayName("getNameEn() should return English name when value exists")
    void testGetNameEnWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameEn("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.getNameEn()).isEqualTo("Complete construction of building");
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setDescription("Construction service");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.getDescription()).isEqualTo("Construction service");
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getEntity() should return the wrapped entity")
    void testGetEntity() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameTh("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setEntity() should update the wrapped entity")
    void testSetEntity() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();
        ThaiCategoryCode entity = new ThaiCategoryCode("2");
        entity.setNameTh("Repair and maintenance of building");

        type.setEntity(entity);

        assertThat(type.getEntity()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("2");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameEn("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type1 = new ThaiCategoryCodeTypeWrapper(entity);
        ThaiCategoryCodeTypeWrapper type2 = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper("1");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper("1");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper("1");

        assertThat(type).isNotEqualTo("1");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        ThaiCategoryCode entity1 = new ThaiCategoryCode("1");
        entity1.setNameEn("Complete construction of building");
        ThaiCategoryCode entity2 = new ThaiCategoryCode("2");
        entity2.setNameEn("Repair and maintenance of building");
        ThaiCategoryCodeTypeWrapper type1 = new ThaiCategoryCodeTypeWrapper(entity1);
        ThaiCategoryCodeTypeWrapper type2 = new ThaiCategoryCodeTypeWrapper(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameEn("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type1 = new ThaiCategoryCodeTypeWrapper(entity);
        ThaiCategoryCodeTypeWrapper type2 = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("1");
        entity.setNameEn("Complete construction of building");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        String str = type.toString();

        assertThat(str).contains("1");
        assertThat(str).contains("Complete construction of building");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.toString()).isEqualTo("null");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isOriginalDocumentReference() should return false when value is null")
    void testIsOriginalDocumentReferenceWithNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.isOriginalDocumentReference()).isFalse();
    }

    @Test
    @DisplayName("isAdvancePaymentReference() should return false when value is null")
    void testIsAdvancePaymentReferenceWithNullValue() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.isAdvancePaymentReference()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isOriginalDocumentReference() should return true for code 01")
    void testIsOriginalDocumentReferenceTrue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        entity.setNameEn("Original Document Reference");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.isOriginalDocumentReference()).isTrue();
    }

    @Test
    @DisplayName("isAdvancePaymentReference() should return true for code 02")
    void testIsAdvancePaymentReferenceTrue() {
        ThaiCategoryCode entity = new ThaiCategoryCode("02");
        entity.setNameEn("Advance Payment Reference");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.isAdvancePaymentReference()).isTrue();
    }

    @Test
    @DisplayName("isOriginalDocumentReference() should return false for code 02")
    void testIsOriginalDocumentReferenceFalse() {
        ThaiCategoryCode entity = new ThaiCategoryCode("02");
        entity.setNameEn("Advance Payment Reference");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.isOriginalDocumentReference()).isFalse();
    }

    @Test
    @DisplayName("isAdvancePaymentReference() should return false for code 01")
    void testIsAdvancePaymentReferenceFalse() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        entity.setNameEn("Original Document Reference");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.isAdvancePaymentReference()).isFalse();
    }

    // Attribute getter/setter tests

    @Test
    @DisplayName("getListID() should return null when not set")
    void testGetListIDNotSet() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getListID()).isNull();
    }

    @Test
    @DisplayName("setListID() should set the list ID")
    void testSetListID() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();
        type.setListID("ThaiCategoryCode");

        assertThat(type.getListID()).isEqualTo("ThaiCategoryCode");
    }

    @Test
    @DisplayName("getListAgencyID() should return null when not set")
    void testGetListAgencyIDNotSet() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getListAgencyID()).isNull();
    }

    @Test
    @DisplayName("setListAgencyID() should set the list agency ID")
    void testSetListAgencyID() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();
        type.setListAgencyID("ETDA");

        assertThat(type.getListAgencyID()).isEqualTo("ETDA");
    }

    @Test
    @DisplayName("getListVersionID() should return null when not set")
    void testGetListVersionIDNotSet() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getListVersionID()).isNull();
    }

    @Test
    @DisplayName("setListVersionID() should set the list version ID")
    void testSetListVersionID() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();
        type.setListVersionID("1.0");

        assertThat(type.getListVersionID()).isEqualTo("1.0");
    }

    @Test
    @DisplayName("getListURI() should return null when not set")
    void testGetListURINotSet() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();

        assertThat(type.getListURI()).isNull();
    }

    @Test
    @DisplayName("setListURI() should set the list URI")
    void testSetListURI() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();
        type.setListURI("urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1");

        assertThat(type.getListURI()).isEqualTo("urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1");
    }

    // Additional tests for improved coverage

    @Test
    @DisplayName("getValue() should return code when value exists")
    void testGetValueWithEntity() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        assertThat(type.getValue()).isEqualTo("01");
    }

    @Test
    @DisplayName("setValue() should set value from code string")
    void testSetValueWithCode() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper();
        type.setValue("01");

        assertThat(type.getValue()).isEqualTo("01");
        assertThat(type.getCode()).isEqualTo("01");
    }

    @Test
    @DisplayName("setValue(null) should set value to null")
    void testSetValueNull() {
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper("01");
        type.setValue(null);

        assertThat(type.getValue()).isNull();
        assertThat(type.getEntity()).isNull();
    }

    @Test
    @DisplayName("equals() should return false when this value is null")
    void testEqualsWithNullThisValue() {
        ThaiCategoryCodeTypeWrapper type1 = new ThaiCategoryCodeTypeWrapper();
        ThaiCategoryCode entity2 = new ThaiCategoryCode("01");
        ThaiCategoryCodeTypeWrapper type2 = new ThaiCategoryCodeTypeWrapper(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return false when other value is null")
    void testEqualsWithNullOtherValue() {
        ThaiCategoryCode entity1 = new ThaiCategoryCode("01");
        ThaiCategoryCodeTypeWrapper type1 = new ThaiCategoryCodeTypeWrapper(entity1);
        ThaiCategoryCodeTypeWrapper type2 = new ThaiCategoryCodeTypeWrapper();

        assertThat(type1).isNotEqualTo(type2);
    }

    @Test
    @DisplayName("toString() should handle null nameEn gracefully")
    void testToStringWithNullNameEn() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        entity.setNameEn(null);
        ThaiCategoryCodeTypeWrapper type = new ThaiCategoryCodeTypeWrapper(entity);

        String str = type.toString();

        assertThat(str).contains("01");
    }
}
