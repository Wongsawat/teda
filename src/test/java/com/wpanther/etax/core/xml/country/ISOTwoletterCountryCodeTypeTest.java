package com.wpanther.etax.core.xml.country;

import com.wpanther.etax.core.entity.ISOCountryCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ISOTwoletterCountryCodeType Tests")
class ISOTwoletterCountryCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("TH");
        assertThat(type.getName()).isEqualTo("Thailand");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType("US");

        assertThat(type.getCode()).isEqualTo("US");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ISOTwoletterCountryCodeType type = ISOTwoletterCountryCodeType.of("JP");

        assertThat(type.getCode()).isEqualTo("JP");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ISOCountryCode entity = new ISOCountryCode("SG", "Singapore");
        ISOTwoletterCountryCodeType type = ISOTwoletterCountryCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType(entity);

        assertThat(type.getName()).isEqualTo("Thailand");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isEtdaExtension() should return false when value is null")
    void testIsEtdaExtensionWithNullValue() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();

        assertThat(type.isEtdaExtension()).isFalse();
    }

    @Test
    @DisplayName("isActive() should return false when value is null")
    void testIsActiveWithNullValue() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();

        assertThat(type.isActive()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isEtdaExtension() should return true for ETDA extension")
    void testIsEtdaExtensionTrue() {
        ISOCountryCode entity = new ISOCountryCode("AN", "Andorra");
        entity.setEtdaExtension(true);
        entity.setActive(true);
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType(entity);

        assertThat(type.isEtdaExtension()).isTrue();
    }

    @Test
    @DisplayName("isActive() should return true for active entity")
    void testIsActiveTrue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        entity.setActive(true);
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType(entity);

        assertThat(type.isActive()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ISOCountryCode entity = new ISOCountryCode("DE", "Germany");
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();
        ISOCountryCode entity = new ISOCountryCode("FR", "France");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("FR");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOTwoletterCountryCodeType type1 = new ISOTwoletterCountryCodeType(entity);
        ISOTwoletterCountryCodeType type2 = new ISOTwoletterCountryCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType("TH");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType("TH");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType("TH");

        assertThat(type).isNotEqualTo("TH");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        ISOTwoletterCountryCodeType type1 = new ISOTwoletterCountryCodeType(new ISOCountryCode("TH", "Thailand"));
        ISOTwoletterCountryCodeType type2 = new ISOTwoletterCountryCodeType(new ISOCountryCode("US", "United States"));

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOTwoletterCountryCodeType type1 = new ISOTwoletterCountryCodeType(entity);
        ISOTwoletterCountryCodeType type2 = new ISOTwoletterCountryCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("TH");
        assertThat(str).contains("Thailand");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ISOTwoletterCountryCodeType type = new ISOTwoletterCountryCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
