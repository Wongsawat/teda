package com.wpanther.etax.core.xml.freightcost;

import com.wpanther.etax.core.entity.FreightCostCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FreightCostCodeType Tests")
class FreightCostCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("FC");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        FreightCostCodeType type = new FreightCostCodeType("PP");

        assertThat(type.getCode()).isEqualTo("PP");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        FreightCostCodeType type = FreightCostCodeType.of("PR");

        assertThat(type.getCode()).isEqualTo("PR");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type = FreightCostCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.getCategory()).isNull();
    }

    @Test
    @DisplayName("getCodeGroup() should return null when value is null")
    void testGetCodeGroupWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.getCodeGroup()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.getName()).isEqualTo("Freight collect");
    }

    @Test
    @DisplayName("getCategory() should return category when value exists")
    void testGetCategoryWithValue() {
        FreightCostCode entity = new FreightCostCode("100000");
        entity.setName("Basic Freight");
        entity.setCategory("Basic Freight");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.getCategory()).isEqualTo("Basic Freight");
    }

    @Test
    @DisplayName("getCodeGroup() should return code group when value exists")
    void testGetCodeGroupWithValue() {
        FreightCostCode entity = new FreightCostCode("100000");
        entity.setName("Basic Freight");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.getCodeGroup()).isEqualTo("100");
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        FreightCostCodeType type = new FreightCostCodeType();
        FreightCostCode entity = new FreightCostCode("PP");
        entity.setName("Prepaid");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("PP");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type1 = new FreightCostCodeType(entity);
        FreightCostCodeType type2 = new FreightCostCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        FreightCostCodeType type = new FreightCostCodeType("FC");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        FreightCostCodeType type = new FreightCostCodeType("FC");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        FreightCostCodeType type = new FreightCostCodeType("FC");

        assertThat(type).isNotEqualTo("FC");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        FreightCostCode entity1 = new FreightCostCode("FC");
        entity1.setName("Freight collect");
        FreightCostCode entity2 = new FreightCostCode("PP");
        entity2.setName("Prepaid");
        FreightCostCodeType type1 = new FreightCostCodeType(entity1);
        FreightCostCodeType type2 = new FreightCostCodeType(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type1 = new FreightCostCodeType(entity);
        FreightCostCodeType type2 = new FreightCostCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        FreightCostCode entity = new FreightCostCode("FC");
        entity.setName("Freight collect");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("FC");
        assertThat(str).contains("Freight collect");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isBasicFreight() should return false when value is null")
    void testIsBasicFreightWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isBasicFreight()).isFalse();
    }

    @Test
    @DisplayName("isFreightSurcharge() should return false when value is null")
    void testIsFreightSurchargeWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isFreightSurcharge()).isFalse();
    }

    @Test
    @DisplayName("isContainerService() should return false when value is null")
    void testIsContainerServiceWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isContainerService()).isFalse();
    }

    @Test
    @DisplayName("isTerminalCharge() should return false when value is null")
    void testIsTerminalChargeWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isTerminalCharge()).isFalse();
    }

    @Test
    @DisplayName("isHandlingCharge() should return false when value is null")
    void testIsHandlingChargeWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isHandlingCharge()).isFalse();
    }

    @Test
    @DisplayName("isStorageOrDemurrage() should return false when value is null")
    void testIsStorageOrDemurrageWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isStorageOrDemurrage()).isFalse();
    }

    @Test
    @DisplayName("isCustomsOrDocumentation() should return false when value is null")
    void testIsCustomsOrDocumentationWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isCustomsOrDocumentation()).isFalse();
    }

    @Test
    @DisplayName("isDangerousGoods() should return false when value is null")
    void testIsDangerousGoodsWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isDangerousGoods()).isFalse();
    }

    @Test
    @DisplayName("isSpecialFreight() should return false when value is null")
    void testIsSpecialFreightWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isSpecialFreight()).isFalse();
    }

    @Test
    @DisplayName("isInsurance() should return false when value is null")
    void testIsInsuranceWithNullValue() {
        FreightCostCodeType type = new FreightCostCodeType();

        assertThat(type.isInsurance()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isBasicFreight() should return true for Basic Freight category")
    void testIsBasicFreightTrue() {
        FreightCostCode entity = new FreightCostCode("100000");
        entity.setName("Basic Freight");
        entity.setCategory("Basic Freight");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isBasicFreight()).isTrue();
    }

    @Test
    @DisplayName("isFreightSurcharge() should return true for Freight Surcharges category")
    void testIsFreightSurchargeTrue() {
        FreightCostCode entity = new FreightCostCode("210000");
        entity.setName("Bunker Adjustment Factor");
        entity.setCategory("Freight Surcharges");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isFreightSurcharge()).isTrue();
    }

    @Test
    @DisplayName("isContainerService() should return true for Container Services category")
    void testIsContainerServiceTrue() {
        FreightCostCode entity = new FreightCostCode("300000");
        entity.setName("Container Handling Charge");
        entity.setCategory("Container Services");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isContainerService()).isTrue();
    }

    @Test
    @DisplayName("isTerminalCharge() should return true for Terminal Charges category")
    void testIsTerminalChargeTrue() {
        FreightCostCode entity = new FreightCostCode("400000");
        entity.setName("Terminal Handling Charge");
        entity.setCategory("Terminal Charges");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isTerminalCharge()).isTrue();
    }

    @Test
    @DisplayName("isHandlingCharge() should return true for Handling Charges category")
    void testIsHandlingChargeTrue() {
        FreightCostCode entity = new FreightCostCode("500000");
        entity.setName("Cargo Handling Charge");
        entity.setCategory("Handling Charges");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isHandlingCharge()).isTrue();
    }

    @Test
    @DisplayName("isStorageOrDemurrage() should return true for Storage & Demurrage category")
    void testIsStorageOrDemurrageTrue() {
        FreightCostCode entity = new FreightCostCode("540000");
        entity.setName("Storage Charge");
        entity.setCategory("Storage & Demurrage");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isStorageOrDemurrage()).isTrue();
    }

    @Test
    @DisplayName("isCustomsOrDocumentation() should return true for Customs & Documentation category")
    void testIsCustomsOrDocumentationTrue() {
        FreightCostCode entity = new FreightCostCode("580000");
        entity.setName("Customs Clearance Fee");
        entity.setCategory("Customs & Documentation");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isCustomsOrDocumentation()).isTrue();
    }

    @Test
    @DisplayName("isDangerousGoods() should return true for Dangerous Goods category")
    void testIsDangerousGoodsTrue() {
        FreightCostCode entity = new FreightCostCode("590000");
        entity.setName("Dangerous Goods Fee");
        entity.setCategory("Dangerous Goods");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isDangerousGoods()).isTrue();
    }

    @Test
    @DisplayName("isSpecialFreight() should return true for Special Freight category")
    void testIsSpecialFreightTrue() {
        FreightCostCode entity = new FreightCostCode("600000");
        entity.setName("Special Cargo Handling");
        entity.setCategory("Special Freight");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isSpecialFreight()).isTrue();
    }

    @Test
    @DisplayName("isInsurance() should return true for Insurance category")
    void testIsInsuranceTrue() {
        FreightCostCode entity = new FreightCostCode("570000");
        entity.setName("Cargo Insurance Premium");
        entity.setCategory("Insurance");
        FreightCostCodeType type = new FreightCostCodeType(entity);

        assertThat(type.isInsurance()).isTrue();
    }
}
