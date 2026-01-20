package com.wpanther.etax.core.xml.referencecode;

import com.wpanther.etax.core.entity.UNECEReferenceTypeCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ReferenceCodeType Tests")
class ReferenceCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA", "Supplier's code");
        ReferenceCodeType type = new ReferenceCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("AAA");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ReferenceCodeType type = new ReferenceCodeType("AAB");

        assertThat(type.getCode()).isEqualTo("AAB");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ReferenceCodeType type = ReferenceCodeType.of("AAC");

        assertThat(type.getCode()).isEqualTo("AAC");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAB", "Purchase order number");
        ReferenceCodeType type = ReferenceCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        entity.setName("Supplier's code");
        ReferenceCodeType type = new ReferenceCodeType(entity);

        assertThat(type.getName()).isEqualTo("Supplier's code");
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        entity.setDescription("The supplier's code");
        ReferenceCodeType type = new ReferenceCodeType(entity);

        assertThat(type.getDescription()).isEqualTo("The supplier's code");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isEtdaExtension() should return false when value is null")
    void testIsEtdaExtensionWithNullValue() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.isEtdaExtension()).isFalse();
    }

    @Test
    @DisplayName("isActive() should return false when value is null")
    void testIsActiveWithNullValue() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.isActive()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isEtdaExtension() should return true for ETDA extension")
    void testIsEtdaExtensionTrue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("T01");
        entity.setName("Thai document reference");
        entity.setEtdaExtension(true);
        entity.setActive(true);
        ReferenceCodeType type = new ReferenceCodeType(entity);

        assertThat(type.isEtdaExtension()).isTrue();
    }

    @Test
    @DisplayName("isActive() should return true for active entity")
    void testIsActiveTrue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        entity.setName("Supplier's code");
        entity.setActive(true);
        ReferenceCodeType type = new ReferenceCodeType(entity);

        assertThat(type.isActive()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAB");
        entity.setName("Purchase order number");
        ReferenceCodeType type = new ReferenceCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ReferenceCodeType type = new ReferenceCodeType();
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAC");
        entity.setName("Sales order number");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("AAC");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA", "Supplier's code");
        ReferenceCodeType type1 = new ReferenceCodeType(entity);
        ReferenceCodeType type2 = new ReferenceCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ReferenceCodeType type = new ReferenceCodeType("AAA");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ReferenceCodeType type = new ReferenceCodeType("AAA");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ReferenceCodeType type = new ReferenceCodeType("AAA");

        assertThat(type).isNotEqualTo("AAA");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        ReferenceCodeType type1 = new ReferenceCodeType(new UNECEReferenceTypeCode("AAA", "Supplier's code"));
        ReferenceCodeType type2 = new ReferenceCodeType(new UNECEReferenceTypeCode("AAB", "Purchase order number"));

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA", "Supplier's code");
        ReferenceCodeType type1 = new ReferenceCodeType(entity);
        ReferenceCodeType type2 = new ReferenceCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        entity.setName("Supplier's code");
        ReferenceCodeType type = new ReferenceCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("AAA");
        assertThat(str).contains("Supplier's code");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ReferenceCodeType type = new ReferenceCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
