package com.wpanther.etax.core.xml.allowancechargeidentification;

import com.wpanther.etax.core.entity.AllowanceChargeIdentificationCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for AllowanceChargeIdentificationCodeType.
 */
@DisplayName("AllowanceChargeIdentificationCodeType Tests")
class AllowanceChargeIdentificationCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Handling commission");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType("1");

        assertThat(type.getCode()).isEqualTo("1");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        AllowanceChargeIdentificationCodeType type = AllowanceChargeIdentificationCodeType.of("2");

        assertThat(type.getCode()).isEqualTo("2");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("2");
        entity.setName("Amendment commission");
        AllowanceChargeIdentificationCodeType type = AllowanceChargeIdentificationCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();

        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();

        assertThat(type.getCategory()).isNull();
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isDocumentaryCreditCommission() should return false when value is null")
    void testIsDocumentaryCreditCommissionWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isDocumentaryCreditCommission()).isFalse();
    }

    @Test
    @DisplayName("isCollectionCommission() should return false when value is null")
    void testIsCollectionCommissionWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isCollectionCommission()).isFalse();
    }

    @Test
    @DisplayName("isProcessingFee() should return false when value is null")
    void testIsProcessingFeeWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isProcessingFee()).isFalse();
    }

    @Test
    @DisplayName("isDiscount() should return false when value is null")
    void testIsDiscountWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isDiscount()).isFalse();
    }

    @Test
    @DisplayName("isRebate() should return false when value is null")
    void testIsRebateWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isRebate()).isFalse();
    }

    @Test
    @DisplayName("isPenalty() should return false when value is null")
    void testIsPenaltyWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isPenalty()).isFalse();
    }

    @Test
    @DisplayName("isBonus() should return false when value is null")
    void testIsBonusWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isBonus()).isFalse();
    }

    @Test
    @DisplayName("isFreightCharges() should return false when value is null")
    void testIsFreightChargesWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isFreightCharges()).isFalse();
    }

    @Test
    @DisplayName("isPackingCharges() should return false when value is null")
    void testIsPackingChargesWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isPackingCharges()).isFalse();
    }

    @Test
    @DisplayName("isLoadingCharges() should return false when value is null")
    void testIsLoadingChargesWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isLoadingCharges()).isFalse();
    }

    @Test
    @DisplayName("isHandlingCharges() should return false when value is null")
    void testIsHandlingChargesWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isHandlingCharges()).isFalse();
    }

    @Test
    @DisplayName("isTestingCharges() should return false when value is null")
    void testIsTestingChargesWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isTestingCharges()).isFalse();
    }

    @Test
    @DisplayName("isStandardCode() should return false when value is null")
    void testIsStandardCodeWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isStandardCode()).isFalse();
    }

    @Test
    @DisplayName("isThaiExtension() should return false when value is null")
    void testIsThaiExtensionWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isThaiExtension()).isFalse();
    }

    @Test
    @DisplayName("isCommission() should return false when value is null")
    void testIsCommissionWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isCommission()).isFalse();
    }

    @Test
    @DisplayName("isCharge() should return false when value is null")
    void testIsChargeWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isCharge()).isFalse();
    }

    @Test
    @DisplayName("isAllowance() should return false when value is null")
    void testIsAllowanceWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        assertThat(type.isAllowance()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Handling");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("2");
        entity.setName("Amendment");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("2");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Handling");
        AllowanceChargeIdentificationCodeType type1 = new AllowanceChargeIdentificationCodeType(entity);
        AllowanceChargeIdentificationCodeType type2 = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType("1");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType("1");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType("1");

        assertThat(type).isNotEqualTo("1");
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Handling");
        AllowanceChargeIdentificationCodeType type1 = new AllowanceChargeIdentificationCodeType(entity);
        AllowanceChargeIdentificationCodeType type2 = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Handling commission");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("1");
        assertThat(str).contains("Handling commission");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}