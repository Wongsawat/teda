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

    // Business logic method tests - true paths

    @Test
    @DisplayName("isDocumentaryCreditCommission() should return true for Documentary Credit Commission")
    void testIsDocumentaryCreditCommissionTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("10");
        entity.setCategory("Documentary Credit Commission");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isDocumentaryCreditCommission()).isTrue();
    }

    @Test
    @DisplayName("isCollectionCommission() should return true for Collection Commission")
    void testIsCollectionCommissionTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("20");
        entity.setCategory("Collection Commission");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isCollectionCommission()).isTrue();
    }

    @Test
    @DisplayName("isProcessingFee() should return true for Processing Fee")
    void testIsProcessingFeeTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("30");
        entity.setCategory("Processing Fee");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isProcessingFee()).isTrue();
    }

    @Test
    @DisplayName("isDiscount() should return true for Discount")
    void testIsDiscountTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("41");
        entity.setCategory("Discount");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isDiscount()).isTrue();
    }

    @Test
    @DisplayName("isRebate() should return true for Rebate")
    void testIsRebateTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("42");
        entity.setCategory("Rebate");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isRebate()).isTrue();
    }

    @Test
    @DisplayName("isPenalty() should return true for Penalty")
    void testIsPenaltyTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("57");
        entity.setCategory("Penalty");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isPenalty()).isTrue();
    }

    @Test
    @DisplayName("isBonus() should return true for Bonus")
    void testIsBonusTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("59");
        entity.setCategory("Bonus");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isBonus()).isTrue();
    }

    @Test
    @DisplayName("isFreightCharges() should return true for Freight Charges")
    void testIsFreightChargesTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("70");
        entity.setCategory("Freight Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isFreightCharges()).isTrue();
    }

    @Test
    @DisplayName("isPackingCharges() should return true for Packing Charges")
    void testIsPackingChargesTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("71");
        entity.setCategory("Packing Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isPackingCharges()).isTrue();
    }

    @Test
    @DisplayName("isLoadingCharges() should return true for Loading/Unloading Charges")
    void testIsLoadingChargesTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("72");
        entity.setCategory("Loading/Unloading Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isLoadingCharges()).isTrue();
    }

    @Test
    @DisplayName("isHandlingCharges() should return true for Handling Charges")
    void testIsHandlingChargesTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("73");
        entity.setCategory("Handling Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isHandlingCharges()).isTrue();
    }

    @Test
    @DisplayName("isTestingCharges() should return true for Testing/Inspection Charges")
    void testIsTestingChargesTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("74");
        entity.setCategory("Testing/Inspection Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isTestingCharges()).isTrue();
    }

    @Test
    @DisplayName("isStandardCode() should return true when isStandardCode is true")
    void testIsStandardCodeTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setIsStandardCode(true);
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isStandardCode()).isTrue();
    }

    @Test
    @DisplayName("isThaiExtension() should return true when isThaiExtension is true")
    void testIsThaiExtensionTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("PP001");
        entity.setIsThaiExtension(true);
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isThaiExtension()).isTrue();
    }

    @Test
    @DisplayName("isCommission() should return true for Commission category")
    void testIsCommissionTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setCategory("Documentary Credit Commission");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isCommission()).isTrue();
    }

    @Test
    @DisplayName("isCharge() should return true for Charge category")
    void testIsChargeTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("73");
        entity.setCategory("Handling Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isCharge()).isTrue();
    }

    @Test
    @DisplayName("isAllowance() should return true for Discount category")
    void testIsAllowanceTrue() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("41");
        entity.setCategory("Discount");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        assertThat(type.isAllowance()).isTrue();
    }

    // toString with Thai Extension

    @Test
    @DisplayName("toString() should include Thai Extension marker")
    void testToStringWithThaiExtension() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("PP001");
        entity.setName("Thai specific code");
        entity.setIsThaiExtension(true);
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("PP001");
        assertThat(str).contains("Thai Extension");
    }

    // toString with category

    @Test
    @DisplayName("toString() should include category when present")
    void testToStringWithCategory() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("73");
        entity.setName("Handling charges");
        entity.setCategory("Handling Charges");
        AllowanceChargeIdentificationCodeType type = new AllowanceChargeIdentificationCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("73");
        assertThat(str).contains("Handling charges");
        assertThat(str).contains("[Handling Charges]");
    }
}