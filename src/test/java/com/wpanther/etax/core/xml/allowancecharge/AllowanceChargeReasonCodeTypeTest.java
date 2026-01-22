package com.wpanther.etax.core.xml.allowancecharge;

import com.wpanther.etax.core.entity.AllowanceChargeReasonCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for AllowanceChargeReasonCodeType.
 */
@DisplayName("AllowanceChargeReasonCodeType Tests")
class AllowanceChargeReasonCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus for works ahead of schedule");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType("1");

        assertThat(type.getCode()).isEqualTo("1");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        AllowanceChargeReasonCodeType type = AllowanceChargeReasonCodeType.of("2");

        assertThat(type.getCode()).isEqualTo("2");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("2");
        entity.setName("Other bonus");
        AllowanceChargeReasonCodeType type = AllowanceChargeReasonCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.getCategory()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus for works ahead of schedule");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.getName()).isEqualTo("Bonus for works ahead of schedule");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isQualityIssue() should return false when value is null")
    void testIsQualityIssueWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isQualityIssue()).isFalse();
    }

    @Test
    @DisplayName("isDeliveryIssue() should return false when value is null")
    void testIsDeliveryIssueWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isDeliveryIssue()).isFalse();
    }

    @Test
    @DisplayName("isAdministrativeError() should return false when value is null")
    void testIsAdministrativeErrorWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isAdministrativeError()).isFalse();
    }

    @Test
    @DisplayName("isDiscountOrAllowance() should return false when value is null")
    void testIsDiscountOrAllowanceWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isDiscountOrAllowance()).isFalse();
    }

    @Test
    @DisplayName("isFinancialCharge() should return false when value is null")
    void testIsFinancialChargeWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isFinancialCharge()).isFalse();
    }

    @Test
    @DisplayName("isClaimOrDispute() should return false when value is null")
    void testIsClaimOrDisputeWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isClaimOrDispute()).isFalse();
    }

    @Test
    @DisplayName("isFreightOrLogistics() should return false when value is null")
    void testIsFreightOrLogisticsWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isFreightOrLogistics()).isFalse();
    }

    @Test
    @DisplayName("isPaymentTerms() should return false when value is null")
    void testIsPaymentTermsWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isPaymentTerms()).isFalse();
    }

    @Test
    @DisplayName("isHRRelated() should return false when value is null")
    void testIsHRRelatedWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isHRRelated()).isFalse();
    }

    @Test
    @DisplayName("isMutuallyDefined() should return false when value is null")
    void testIsMutuallyDefinedWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isMutuallyDefined()).isFalse();
    }

    @Test
    @DisplayName("isCustomOrOther() should return false when value is null")
    void testIsCustomOrOtherWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.isCustomOrOther()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("2");
        entity.setName("Other bonus");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("2");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus");
        AllowanceChargeReasonCodeType type1 = new AllowanceChargeReasonCodeType(entity);
        AllowanceChargeReasonCodeType type2 = new AllowanceChargeReasonCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType("1");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType("1");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType("1");

        assertThat(type).isNotEqualTo("1");
    }

    @Test
    @DisplayName("equals() should handle null value")
    void testEqualsWithNullValue() {
        AllowanceChargeReasonCodeType type1 = new AllowanceChargeReasonCodeType();
        AllowanceChargeReasonCodeType type2 = new AllowanceChargeReasonCodeType("1");

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus");
        AllowanceChargeReasonCodeType type1 = new AllowanceChargeReasonCodeType(entity);
        AllowanceChargeReasonCodeType type2 = new AllowanceChargeReasonCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("1");
        assertThat(str).contains("Bonus");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }

    @Test
    @DisplayName("toString() should include category when present")
    void testToStringWithCategory() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus");
        entity.setCategory("Discount");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("Discount");
    }

    // Business logic method tests - true paths

    @Test
    @DisplayName("isQualityIssue() should return true for Quality Issue category")
    void testIsQualityIssueTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("41");
        entity.setName("Goods damaged");
        entity.setCategory("Quality Issue");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isQualityIssue()).isTrue();
    }

    @Test
    @DisplayName("isDeliveryIssue() should return true for Delivery Issue category")
    void testIsDeliveryIssueTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("65");
        entity.setName("Short delivery");
        entity.setCategory("Delivery Issue");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isDeliveryIssue()).isTrue();
    }

    @Test
    @DisplayName("isAdministrativeError() should return true for Administrative Error category")
    void testIsAdministrativeErrorTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("70");
        entity.setName("Invoice error");
        entity.setCategory("Administrative Error");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isAdministrativeError()).isTrue();
    }

    @Test
    @DisplayName("isDiscountOrAllowance() should return true for Discount/Allowance category")
    void testIsDiscountOrAllowanceTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setName("Bonus for works ahead of schedule");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isDiscountOrAllowance()).isTrue();
    }

    @Test
    @DisplayName("isFinancialCharge() should return true for Financial Charges category")
    void testIsFinancialChargeTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("28");
        entity.setName("Bank charges");
        entity.setCategory("Financial Charges");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isFinancialCharge()).isTrue();
    }

    @Test
    @DisplayName("isClaimOrDispute() should return true for Claims/Disputes category")
    void testIsClaimOrDisputeTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("53");
        entity.setName("Counter claim");
        entity.setCategory("Claims/Disputes");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isClaimOrDispute()).isTrue();
    }

    @Test
    @DisplayName("isFreightOrLogistics() should return true for Freight/Logistics category")
    void testIsFreightOrLogisticsTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("19");
        entity.setName("Container charges");
        entity.setCategory("Freight/Logistics");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isFreightOrLogistics()).isTrue();
    }

    @Test
    @DisplayName("isPaymentTerms() should return true for Payment Terms category")
    void testIsPaymentTermsTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("61");
        entity.setName("Agreed settlement");
        entity.setCategory("Payment Terms");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isPaymentTerms()).isTrue();
    }

    @Test
    @DisplayName("isHRRelated() should return true for HR Related category")
    void testIsHRRelatedTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("95");
        entity.setName("Employee changes");
        entity.setCategory("HR Related");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isHRRelated()).isTrue();
    }

    @Test
    @DisplayName("isMutuallyDefined() should return true for code ZZZ")
    void testIsMutuallyDefinedTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("ZZZ");
        entity.setName("Mutually defined");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isMutuallyDefined()).isTrue();
    }

    @Test
    @DisplayName("isCustomOrOther() should return true for Custom/Other category")
    void testIsCustomOrOtherTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("98");
        entity.setName("Other reasons");
        entity.setCategory("Custom/Other");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isCustomOrOther()).isTrue();
    }

    @Test
    @DisplayName("isCustomOrOther() should return true for Other category")
    void testIsOtherTrue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("99");
        entity.setName("Miscellaneous");
        entity.setCategory("Other");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isCustomOrOther()).isTrue();
    }

    // Business logic method tests - false paths

    @Test
    @DisplayName("isQualityIssue() should return false for different category")
    void testIsQualityIssueFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isQualityIssue()).isFalse();
    }

    @Test
    @DisplayName("isDeliveryIssue() should return false for different category")
    void testIsDeliveryIssueFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isDeliveryIssue()).isFalse();
    }

    @Test
    @DisplayName("isAdministrativeError() should return false for different category")
    void testIsAdministrativeErrorFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isAdministrativeError()).isFalse();
    }

    @Test
    @DisplayName("isDiscountOrAllowance() should return false for different category")
    void testIsDiscountOrAllowanceFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("41");
        entity.setCategory("Quality Issue");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isDiscountOrAllowance()).isFalse();
    }

    @Test
    @DisplayName("isFinancialCharge() should return false for different category")
    void testIsFinancialChargeFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isFinancialCharge()).isFalse();
    }

    @Test
    @DisplayName("isClaimOrDispute() should return false for different category")
    void testIsClaimOrDisputeFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isClaimOrDispute()).isFalse();
    }

    @Test
    @DisplayName("isFreightOrLogistics() should return false for different category")
    void testIsFreightOrLogisticsFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isFreightOrLogistics()).isFalse();
    }

    @Test
    @DisplayName("isPaymentTerms() should return false for different category")
    void testIsPaymentTermsFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isPaymentTerms()).isFalse();
    }

    @Test
    @DisplayName("isHRRelated() should return false for different category")
    void testIsHRRelatedFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isHRRelated()).isFalse();
    }

    @Test
    @DisplayName("isMutuallyDefined() should return false for non-ZZZ code")
    void testIsMutuallyDefinedFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isMutuallyDefined()).isFalse();
    }

    @Test
    @DisplayName("isCustomOrOther() should return false for different category")
    void testIsCustomOrOtherFalse() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCategory("Discount/Allowance");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.isCustomOrOther()).isFalse();
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setDescription("Bonus for early completion");
        AllowanceChargeReasonCodeType type = new AllowanceChargeReasonCodeType(entity);

        assertThat(type.getDescription()).isEqualTo("Bonus for early completion");
    }
}