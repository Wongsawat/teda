package com.wpanther.etax.core.xml.subdistrict;

import com.wpanther.etax.core.entity.TISISubdistrict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for TISI1099CitySubDivisionNameType.
 */
@DisplayName("TISI1099CitySubDivisionNameType Tests")
class TISI1099CitySubDivisionNameTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        TISISubdistrict entity = new TISISubdistrict("100101", "แขวงพระบรมมหาราชวัง");
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("100101");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType("100101");

        assertThat(type.getCode()).isEqualTo("100101");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        TISI1099CitySubDivisionNameType type = TISI1099CitySubDivisionNameType.of("100102");

        assertThat(type.getCode()).isEqualTo("100102");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        TISISubdistrict entity = new TISISubdistrict("100102", "แขวงวังบูรพาภิรมย์");
        TISI1099CitySubDivisionNameType type = TISI1099CitySubDivisionNameType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getProvinceCode() should return null when value is null")
    void testGetProvinceCodeWithNullValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.getProvinceCode()).isNull();
    }

    @Test
    @DisplayName("getCityCode() should return null when value is null")
    void testGetCityCodeWithNullValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.getCityCode()).isNull();
    }

    @Test
    @DisplayName("getSubdistrictCode() should return null when value is null")
    void testGetSubdistrictCodeWithNullValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.getSubdistrictCode()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        TISISubdistrict entity = new TISISubdistrict("100101", "แขวงพระบรมมหาราชวัง");
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType(entity);

        assertThat(type.getNameTh()).isEqualTo("แขวงพระบรมมหาราชวัง");
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        TISISubdistrict entity = new TISISubdistrict("100101", "แขวงพระบรมมหาราชวัง");
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();
        TISISubdistrict entity = new TISISubdistrict("100102", "แขวงวังบูรพาภิรมย์");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("100102");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        TISISubdistrict entity = new TISISubdistrict("100101", "แขวงพระบรมมหาราชวัง");
        TISI1099CitySubDivisionNameType type1 = new TISI1099CitySubDivisionNameType(entity);
        TISI1099CitySubDivisionNameType type2 = new TISI1099CitySubDivisionNameType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType("100101");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType("100101");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType("100101");

        assertThat(type).isNotEqualTo("100101");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        TISI1099CitySubDivisionNameType type1 = new TISI1099CitySubDivisionNameType();
        TISI1099CitySubDivisionNameType type2 = new TISI1099CitySubDivisionNameType("100101");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        TISISubdistrict entity = new TISISubdistrict("100101", "แขวงพระบรมมหาราชวัง");
        TISI1099CitySubDivisionNameType type1 = new TISI1099CitySubDivisionNameType(entity);
        TISI1099CitySubDivisionNameType type2 = new TISI1099CitySubDivisionNameType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        TISISubdistrict entity = new TISISubdistrict("100101", "แขวงพระบรมมหาราชวัง");
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType(entity);

        String str = type.toString();

        assertThat(str).contains("100101");
        assertThat(str).contains("แขวงพระบรมมหาราชวัง");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        TISI1099CitySubDivisionNameType type = new TISI1099CitySubDivisionNameType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
