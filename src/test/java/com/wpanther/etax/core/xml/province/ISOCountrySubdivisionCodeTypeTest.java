package com.wpanther.etax.core.xml.province;

import com.wpanther.etax.core.entity.ThaiProvinceCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ISOCountrySubdivisionCodeType.
 */
@DisplayName("ISOCountrySubdivisionCodeType Tests")
class ISOCountrySubdivisionCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("TH-10");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType("TH-10");

        assertThat(type.getCode()).isEqualTo("TH-10");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ISOCountrySubdivisionCodeType type = ISOCountrySubdivisionCodeType.of("TH-50");

        assertThat(type.getCode()).isEqualTo("TH-50");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-50", "เชียงใหม่", "Chiang Mai");
        ISOCountrySubdivisionCodeType type = ISOCountrySubdivisionCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getNameTh() should return null when value is null")
    void testGetNameThWithNullValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.getNameTh()).isNull();
    }

    @Test
    @DisplayName("getNameEn() should return null when value is null")
    void testGetNameEnWithNullValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.getNameEn()).isNull();
    }

    @Test
    @DisplayName("getRegion() should return null when value is null")
    void testGetRegionWithNullValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.getRegion()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getNameTh() should return Thai name when value exists")
    void testGetNameThWithValue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.getNameTh()).isEqualTo("กรุงเทพมหานคร");
    }

    @Test
    @DisplayName("getNameEn() should return English name when value exists")
    void testGetNameEnWithValue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.getNameEn()).isEqualTo("Bangkok");
    }

    @Test
    @DisplayName("getRegion() should return region when value exists")
    void testGetRegionWithValue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        entity.setRegion("Central");
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.getRegion()).isEqualTo("Central");
    }

    // isActive tests

    @Test
    @DisplayName("isActive() should return false when value is null")
    void testIsActiveWithNullValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.isActive()).isFalse();
    }

    @Test
    @DisplayName("isActive() should return true when entity is active")
    void testIsActiveTrue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        entity.setActive(true);
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.isActive()).isTrue();
    }

    @Test
    @DisplayName("isActive() should return false when entity is inactive")
    void testIsActiveFalse() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        entity.setActive(false);
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.isActive()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-50", "เชียงใหม่", "Chiang Mai");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("TH-50");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type1 = new ISOCountrySubdivisionCodeType(entity);
        ISOCountrySubdivisionCodeType type2 = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType("TH-10");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType("TH-10");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType("TH-10");

        assertThat(type).isNotEqualTo("TH-10");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        ISOCountrySubdivisionCodeType type1 = new ISOCountrySubdivisionCodeType();
        ISOCountrySubdivisionCodeType type2 = new ISOCountrySubdivisionCodeType("TH-10");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type1 = new ISOCountrySubdivisionCodeType(entity);
        ISOCountrySubdivisionCodeType type2 = new ISOCountrySubdivisionCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        ThaiProvinceCode entity = new ThaiProvinceCode("TH-10", "กรุงเทพมหานคร", "Bangkok");
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("TH-10");
        assertThat(str).contains("กรุงเทพมหานคร");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ISOCountrySubdivisionCodeType type = new ISOCountrySubdivisionCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
