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

    // Business logic method tests - true paths

    @Test
    @DisplayName("isIncoterm() should return true when isIncoterm is true")
    void testIsIncotermTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        entity.setIncoterm(true);
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isIncoterm()).isTrue();
    }

    @Test
    @DisplayName("isGroupE() should return true for Group E")
    void testIsGroupETrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        entity.setIncotermGroup("E");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isGroupE()).isTrue();
    }

    @Test
    @DisplayName("isGroupF() should return true for Group F")
    void testIsGroupFTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("FOB");
        entity.setIncotermGroup("F");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isGroupF()).isTrue();
    }

    @Test
    @DisplayName("isGroupC() should return true for Group C")
    void testIsGroupCTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CIF");
        entity.setIncotermGroup("C");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isGroupC()).isTrue();
    }

    @Test
    @DisplayName("isGroupD() should return true for Group D")
    void testIsGroupDTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("DDP");
        entity.setIncotermGroup("D");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isGroupD()).isTrue();
    }

    @Test
    @DisplayName("hasMinimumSellerObligation() should return true for Minimum")
    void testHasMinimumSellerObligationTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        entity.setSellerObligation("Minimum");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.hasMinimumSellerObligation()).isTrue();
    }

    @Test
    @DisplayName("hasLowSellerObligation() should return true for Low")
    void testHasLowSellerObligationTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("FOB");
        entity.setSellerObligation("Low");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.hasLowSellerObligation()).isTrue();
    }

    @Test
    @DisplayName("hasMediumSellerObligation() should return true for Medium")
    void testHasMediumSellerObligationTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CIF");
        entity.setSellerObligation("Medium");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.hasMediumSellerObligation()).isTrue();
    }

    @Test
    @DisplayName("hasHighSellerObligation() should return true for High")
    void testHasHighSellerObligationTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("DAP");
        entity.setSellerObligation("High");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.hasHighSellerObligation()).isTrue();
    }

    @Test
    @DisplayName("hasMaximumSellerObligation() should return true for Maximum")
    void testHasMaximumSellerObligationTrue() {
        DeliveryTermsCode entity = new DeliveryTermsCode("DDP");
        entity.setSellerObligation("Maximum");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.hasMaximumSellerObligation()).isTrue();
    }

    @Test
    @DisplayName("includesInsurance() should return true for CIF")
    void testIncludesInsuranceCIF() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CIF");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.includesInsurance()).isTrue();
    }

    @Test
    @DisplayName("includesInsurance() should return true for CIP")
    void testIncludesInsuranceCIP() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CIP");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.includesInsurance()).isTrue();
    }

    @Test
    @DisplayName("includesFreight() should return true for CIF")
    void testIncludesFreightCIF() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CIF");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.includesFreight()).isTrue();
    }

    @Test
    @DisplayName("includesFreight() should return true for CFR")
    void testIncludesFreightCFR() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CFR");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.includesFreight()).isTrue();
    }

    @Test
    @DisplayName("isSeaTransportOnly() should return true for FOB")
    void testIsSeaTransportOnlyFOB() {
        DeliveryTermsCode entity = new DeliveryTermsCode("FOB");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isSeaTransportOnly()).isTrue();
    }

    @Test
    @DisplayName("isSeaTransportOnly() should return true for CIF")
    void testIsSeaTransportOnlyCIF() {
        DeliveryTermsCode entity = new DeliveryTermsCode("CIF");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isSeaTransportOnly()).isTrue();
    }

    @Test
    @DisplayName("isAnyTransportMode() should return true for EXW")
    void testIsAnyTransportModeEXW() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isAnyTransportMode()).isTrue();
    }

    @Test
    @DisplayName("isAnyTransportMode() should return true for DDP")
    void testIsAnyTransportModeDDP() {
        DeliveryTermsCode entity = new DeliveryTermsCode("DDP");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        assertThat(type.isAnyTransportMode()).isTrue();
    }

    // toString with incoterm group

    @Test
    @DisplayName("toString() should include group when present")
    void testToStringWithGroup() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        entity.setIncotermGroup("E");
        DeliveryTermsCodeType type = new DeliveryTermsCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("EXW");
        assertThat(str).contains("Ex Works");
        assertThat(str).contains("[Group E]");
    }
}