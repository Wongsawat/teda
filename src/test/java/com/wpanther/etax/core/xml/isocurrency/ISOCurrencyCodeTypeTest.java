package com.wpanther.etax.core.xml.isocurrency;

import com.wpanther.etax.core.entity.ISOCurrencyCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ISOCurrencyCodeType.
 */
@DisplayName("ISOCurrencyCodeType Tests")
class ISOCurrencyCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("THB");
        assertThat(type.getName()).isEqualTo("Baht");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType("USD");

        assertThat(type.getCode()).isEqualTo("USD");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ISOCurrencyCodeType type = ISOCurrencyCodeType.of("EUR");

        assertThat(type.getCode()).isEqualTo("EUR");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ISOCurrencyCode entity = new ISOCurrencyCode("JPY", "Yen");
        ISOCurrencyCodeType type = ISOCurrencyCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getNumericCode() should return null when value is null")
    void testGetNumericCodeWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.getNumericCode()).isNull();
    }

    @Test
    @DisplayName("getMinorUnits() should return 2 when value is null")
    void testGetMinorUnitsWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.getMinorUnits()).isEqualTo(2);
    }

    @Test
    @DisplayName("getDecimalPlaces() should return 2 when value is null")
    void testGetDecimalPlacesWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.getDecimalPlaces()).isEqualTo(2);
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        entity.setDescription("Thai Baht currency");
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        assertThat(type.getDescription()).isEqualTo("Thai Baht currency");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isThaiBaht() should return false when value is null")
    void testIsThaiBahtWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.isThaiBaht()).isFalse();
    }

    @Test
    @DisplayName("isUSDollar() should return false when value is null")
    void testIsUSDollarWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.isUSDollar()).isFalse();
    }

    @Test
    @DisplayName("isEuro() should return false when value is null")
    void testIsEuroWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.isEuro()).isFalse();
    }

    @Test
    @DisplayName("isMajorCurrency() should return false when value is null")
    void testIsMajorCurrencyWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.isMajorCurrency()).isFalse();
    }

    @Test
    @DisplayName("isASEANCurrency() should return false when value is null")
    void testIsASEANCurrencyWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.isASEANCurrency()).isFalse();
    }

    @Test
    @DisplayName("hasNoDecimalPlaces() should return false when value is null")
    void testHasNoDecimalPlacesWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.hasNoDecimalPlaces()).isFalse();
    }

    @Test
    @DisplayName("hasThreeDecimalPlaces() should return false when value is null")
    void testHasThreeDecimalPlacesWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.hasThreeDecimalPlaces()).isFalse();
    }

    // formatAmount tests

    @Test
    @DisplayName("formatAmount() should use default format when value is null")
    void testFormatAmountWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        String formatted = type.formatAmount(1234.567);

        assertThat(formatted).contains("1,234.57");
    }

    @Test
    @DisplayName("formatAmount() should delegate to entity when value exists")
    void testFormatAmountWithValue() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        entity.setMinorUnits(2);
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        String formatted = type.formatAmount(1000.00);

        assertThat(formatted).isNotNull();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ISOCurrencyCode entity = new ISOCurrencyCode("GBP", "Pound Sterling");
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();
        ISOCurrencyCode entity = new ISOCurrencyCode("CHF", "Swiss Franc");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("CHF");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        ISOCurrencyCodeType type1 = new ISOCurrencyCodeType(entity);
        ISOCurrencyCodeType type2 = new ISOCurrencyCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType("THB");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType("THB");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType("THB");

        assertThat(type).isNotEqualTo("THB");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        ISOCurrencyCodeType type1 = new ISOCurrencyCodeType();
        ISOCurrencyCodeType type2 = new ISOCurrencyCodeType("USD");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        ISOCurrencyCodeType type1 = new ISOCurrencyCodeType(entity);
        ISOCurrencyCodeType type2 = new ISOCurrencyCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("THB");
        assertThat(str).contains("Baht");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        ISOCurrencyCodeType type = new ISOCurrencyCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }

    @Test
    @DisplayName("toString() should include numeric code when present")
    void testToStringWithNumericCode() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Baht");
        entity.setNumericCode("764");
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("764");
    }

    @Test
    @DisplayName("toString() should include decimal info for non-standard currencies")
    void testToStringWithNonStandardDecimals() {
        ISOCurrencyCode entity = new ISOCurrencyCode("JPY", "Yen");
        entity.setMinorUnits(0);
        ISOCurrencyCodeType type = new ISOCurrencyCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("0 decimals");
    }
}