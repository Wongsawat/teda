package com.wpanther.etax.core.xml.addresstype;

import com.wpanther.etax.core.entity.AddressType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for AddressTypeType.
 */
@DisplayName("AddressTypeType Tests")
class AddressTypeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal address");
        AddressTypeType type = new AddressTypeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1");
        assertThat(type.getName()).isEqualTo("Postal address");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        AddressTypeType type = new AddressTypeType("2");

        assertThat(type.getCode()).isEqualTo("2");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        AddressTypeType type = AddressTypeType.of("1");

        assertThat(type.getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        AddressType entity = new AddressType("3");
        entity.setName("Physical address");
        AddressTypeType type = AddressTypeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal address");
        entity.setDescription("Address for postal delivery");
        AddressTypeType type = new AddressTypeType(entity);

        assertThat(type.getDescription()).isEqualTo("Address for postal delivery");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isPostalAddress() should return false when value is null")
    void testIsPostalAddressWithNullValue() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.isPostalAddress()).isFalse();
    }

    @Test
    @DisplayName("isFiscalAddress() should return false when value is null")
    void testIsFiscalAddressWithNullValue() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.isFiscalAddress()).isFalse();
    }

    @Test
    @DisplayName("isPhysicalAddress() should return false when value is null")
    void testIsPhysicalAddressWithNullValue() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.isPhysicalAddress()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isPostalAddress() should return true for code 1")
    void testIsPostalAddressTrue() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal");
        AddressTypeType type = new AddressTypeType(entity);

        assertThat(type.isPostalAddress()).isTrue();
    }

    @Test
    @DisplayName("isFiscalAddress() should return true for code 2")
    void testIsFiscalAddressTrue() {
        AddressType entity = new AddressType("2");
        entity.setName("Fiscal");
        AddressTypeType type = new AddressTypeType(entity);

        assertThat(type.isFiscalAddress()).isTrue();
    }

    @Test
    @DisplayName("isPhysicalAddress() should return true for code 3")
    void testIsPhysicalAddressTrue() {
        AddressType entity = new AddressType("3");
        entity.setName("Physical");
        AddressTypeType type = new AddressTypeType(entity);

        assertThat(type.isPhysicalAddress()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal");
        AddressTypeType type = new AddressTypeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        AddressTypeType type = new AddressTypeType();
        AddressType entity = new AddressType("2");
        entity.setName("Fiscal");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("2");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal");
        AddressTypeType type1 = new AddressTypeType(entity);
        AddressTypeType type2 = new AddressTypeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        AddressTypeType type = new AddressTypeType("1");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        AddressTypeType type = new AddressTypeType("1");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        AddressTypeType type = new AddressTypeType("1");

        assertThat(type).isNotEqualTo("1");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        AddressTypeType type1 = new AddressTypeType();
        AddressTypeType type2 = new AddressTypeType("1");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal");
        AddressTypeType type1 = new AddressTypeType(entity);
        AddressTypeType type2 = new AddressTypeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        AddressType entity = new AddressType("1");
        entity.setName("Postal");
        AddressTypeType type = new AddressTypeType(entity);

        String str = type.toString();

        assertThat(str).contains("1");
        assertThat(str).contains("Postal");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        AddressTypeType type = new AddressTypeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
