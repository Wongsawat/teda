package com.wpanther.etax.core.xml.deliveryterms;

import com.wpanther.etax.core.entity.DeliveryTermsCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for DeliveryTermsCodeType.
 */
@DisplayName("DeliveryTermsCodeType Tests")
class DeliveryTermsCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("EXW");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType("FOB");

        assertThat(type.getCode()).isEqualTo("FOB");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        DeliveryTermsCodeType type = DeliveryTermsCodeType.of("CIF");

        assertThat(type.getCode()).isEqualTo("CIF");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        DeliveryTermsCode entity = new DeliveryTermsCode("DDP", "Delivered Duty Paid");
        DeliveryTermsCodeType type = DeliveryTermsCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getIncotermGroup() should return null when value is null")
    void testGetIncotermGroupWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.getIncotermGroup()).isNull();
    }

    @Test
    @DisplayName("getSellerObligation() should return null when value is null")
    void testGetSellerObligationWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.getSellerObligation()).isNull();
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isIncoterm() should return false when value is null")
    void testIsIncotermWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isIncoterm()).isFalse();
    }

    @Test
    @DisplayName("isGroupE() should return false when value is null")
    void testIsGroupEWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isGroupE()).isFalse();
    }

    @Test
    @DisplayName("isGroupF() should return false when value is null")
    void testIsGroupFWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isGroupF()).isFalse();
    }

    @Test
    @DisplayName("isGroupC() should return false when value is null")
    void testIsGroupCWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isGroupC()).isFalse();
    }

    @Test
    @DisplayName("isGroupD() should return false when value is null")
    void testIsGroupDWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isGroupD()).isFalse();
    }

    @Test
    @DisplayName("hasMinimumSellerObligation() should return false when value is null")
    void testHasMinimumSellerObligationWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.hasMinimumSellerObligation()).isFalse();
    }

    @Test
    @DisplayName("hasLowSellerObligation() should return false when value is null")
    void testHasLowSellerObligationWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.hasLowSellerObligation()).isFalse();
    }

    @Test
    @DisplayName("hasMediumSellerObligation() should return false when value is null")
    void testHasMediumSellerObligationWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.hasMediumSellerObligation()).isFalse();
    }

    @Test
    @DisplayName("hasHighSellerObligation() should return false when value is null")
    void testHasHighSellerObligationWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.hasHighSellerObligation()).isFalse();
    }

    @Test
    @DisplayName("hasMaximumSellerObligation() should return false when value is null")
    void testHasMaximumSellerObligationWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.hasMaximumSellerObligation()).isFalse();
    }

    @Test
    @DisplayName("includesInsurance() should return false when value is null")
    void testIncludesInsuranceWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.includesInsurance()).isFalse();
    }

    @Test
    @DisplayName("includesFreight() should return false when value is null")
    void testIncludesFreightWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.includesFreight()).isFalse();
    }

    @Test
    @DisplayName("isSeaTransportOnly() should return false when value is null")
    void testIsSeaTransportOnlyWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isSeaTransportOnly()).isFalse();
    }

    @Test
    @DisplayName("isAnyTransportMode() should return false when value is null")
    void testIsAnyTransportModeWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        assertThat(type.isAnyTransportMode()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();
        DeliveryTermsCode entity = new DeliveryTermsCode("FOB", "Free On Board");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("FOB");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        DeliveryTermsCodeType type1 = new DeliveryTermsCodeType(entity);
        DeliveryTermsCodeType type2 = new DeliveryTermsCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType("EXW");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType("EXW");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType("EXW");

        assertThat(type).isNotEqualTo("EXW");
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        DeliveryTermsCodeType type1 = new DeliveryTermsCodeType(entity);
        DeliveryTermsCodeType type2 = new DeliveryTermsCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("EXW");
        assertThat(str).contains("Ex Works");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        DeliveryTermsCodeType type = new DeliveryTermsCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}