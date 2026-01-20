package com.wpanther.etax.core.xml.paymentterms;

import com.wpanther.etax.core.entity.PaymentTermsTypeCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaymentTermsCodeType Tests")
class PaymentTermsCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("10");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        PaymentTermsCodeType type = new PaymentTermsCodeType("20");

        assertThat(type.getCode()).isEqualTo("20");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        PaymentTermsCodeType type = PaymentTermsCodeType.of("30");

        assertThat(type.getCode()).isEqualTo("30");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        PaymentTermsCodeType type = PaymentTermsCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.getCategory()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.getName()).isEqualTo("Net 30 days");
    }

    @Test
    @DisplayName("getCategory() should return category when value exists")
    void testGetCategoryWithValue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setCategory("Deferred payment");
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.getCategory()).isEqualTo("Deferred payment");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isImmediate() should return false when value is null")
    void testIsImmediateWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.isImmediate()).isFalse();
    }

    @Test
    @DisplayName("isDeferred() should return false when value is null")
    void testIsDeferredWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.isDeferred()).isFalse();
    }

    @Test
    @DisplayName("hasDiscount() should return false when value is null")
    void testHasDiscountWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.hasDiscount()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isImmediate() should return true for immediate payment term")
    void testIsImmediateTrue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setIsImmediate(true);
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.isImmediate()).isTrue();
    }

    @Test
    @DisplayName("isDeferred() should return true for deferred payment term")
    void testIsDeferredTrue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("20");
        entity.setIsDeferred(true);
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.isDeferred()).isTrue();
    }

    @Test
    @DisplayName("hasDiscount() should return true for payment term with discount")
    void testHasDiscountTrue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("30");
        entity.setHasDiscount(true);
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.hasDiscount()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("20");
        entity.setName("Net 60 days");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("20");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        PaymentTermsCodeType type1 = new PaymentTermsCodeType(entity);
        PaymentTermsCodeType type2 = new PaymentTermsCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        PaymentTermsCodeType type = new PaymentTermsCodeType("10");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        PaymentTermsCodeType type = new PaymentTermsCodeType("10");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        PaymentTermsCodeType type = new PaymentTermsCodeType("10");

        assertThat(type).isNotEqualTo("10");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        PaymentTermsTypeCode entity1 = new PaymentTermsTypeCode("10");
        entity1.setName("Net 30 days");
        PaymentTermsTypeCode entity2 = new PaymentTermsTypeCode("20");
        entity2.setName("Net 60 days");
        PaymentTermsCodeType type1 = new PaymentTermsCodeType(entity1);
        PaymentTermsCodeType type2 = new PaymentTermsCodeType(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        PaymentTermsCodeType type1 = new PaymentTermsCodeType(entity);
        PaymentTermsCodeType type2 = new PaymentTermsCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("10");
        entity.setName("Net 30 days");
        entity.setCategory("Deferred payment");
        PaymentTermsCodeType type = new PaymentTermsCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("10");
        assertThat(str).contains("Net 30 days");
        assertThat(str).contains("Deferred payment");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        PaymentTermsCodeType type = new PaymentTermsCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
