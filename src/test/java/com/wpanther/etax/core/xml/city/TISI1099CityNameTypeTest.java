package com.wpanther.etax.core.xml.city;

import com.wpanther.etax.core.entity.TISICityName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for TISI1099CityNameType.
 */
@DisplayName("TISI1099CityNameType Tests")
class TISI1099CityNameTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        TISI1099CityNameType type = new TISI1099CityNameType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        TISICityName entity = new TISICityName("1001", "เขตพระนคร");
        TISI1099CityNameType type = new TISI1099CityNameType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1001");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        TISI1099CityNameType type = new TISI1099CityNameType("1001");

        assertThat(type.getCode()).isEqualTo("1001");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        TISI1099CityNameType type = TISI1099CityNameType.of("1002");

        assertThat(type.getCode()).isEqualTo("1002");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        TISICityName entity = new TISICityName("1002", "เขตดุสิต");
        TISI1099CityNameType type = TISI1099CityNameType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        TISI1099CityNameType type = new TISI1099CityNameType();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getProvinceCode() should return null when value is null")
    void testGetProvinceCodeWithNullValue() {
        TISI1099CityNameType type = new TISI1099CityNameType();

        assertThat(type.getProvinceCode()).isNull();
    }

    @Test
    @DisplayName("getDistrictCode() should return null when value is null")
    void testGetDistrictCodeWithNullValue() {
        TISI1099CityNameType type = new TISI1099CityNameType();

        assertThat(type.getDistrictCode()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        TISICityName entity = new TISICityName("1001", "เขตพระนคร");
        TISI1099CityNameType type = new TISI1099CityNameType(entity);

        assertThat(type.getNameTh()).isEqualTo("เขตพระนคร");
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        TISICityName entity = new TISICityName("1001", "เขตพระนคร");
        TISI1099CityNameType type = new TISI1099CityNameType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        TISI1099CityNameType type = new TISI1099CityNameType();
        TISICityName entity = new TISICityName("1002", "เขตดุสิต");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1002");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        TISICityName entity = new TISICityName("1001", "เขตพระนคร");
        TISI1099CityNameType type1 = new TISI1099CityNameType(entity);
        TISI1099CityNameType type2 = new TISI1099CityNameType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        TISI1099CityNameType type = new TISI1099CityNameType("1001");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        TISI1099CityNameType type = new TISI1099CityNameType("1001");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        TISI1099CityNameType type = new TISI1099CityNameType("1001");

        assertThat(type).isNotEqualTo("1001");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        TISI1099CityNameType type1 = new TISI1099CityNameType();
        TISI1099CityNameType type2 = new TISI1099CityNameType("1001");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        TISICityName entity = new TISICityName("1001", "เขตพระนคร");
        TISI1099CityNameType type1 = new TISI1099CityNameType(entity);
        TISI1099CityNameType type2 = new TISI1099CityNameType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        TISI1099CityNameType type = new TISI1099CityNameType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        TISICityName entity = new TISICityName("1001", "เขตพระนคร");
        TISI1099CityNameType type = new TISI1099CityNameType(entity);

        String str = type.toString();

        assertThat(str).contains("1001");
        assertThat(str).contains("เขตพระนคร");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        TISI1099CityNameType type = new TISI1099CityNameType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
