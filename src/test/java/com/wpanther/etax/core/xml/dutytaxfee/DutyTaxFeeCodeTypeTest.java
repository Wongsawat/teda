package com.wpanther.etax.core.xml.dutytaxfee;

import com.wpanther.etax.core.entity.DutyTaxFeeTypeCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for DutyTaxFeeCodeType.
 */
@DisplayName("DutyTaxFeeCodeType Tests")
class DutyTaxFeeCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setName("Value Added Tax");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("VAT");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType("VAT");

        assertThat(type.getCode()).isEqualTo("VAT");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        DutyTaxFeeCodeType type = DutyTaxFeeCodeType.of("GST");

        assertThat(type.getCode()).isEqualTo("GST");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("GST");
        entity.setName("Goods and Services Tax");
        DutyTaxFeeCodeType type = DutyTaxFeeCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.getDescription()).isNull();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.getCategory()).isNull();
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isVat() should return false when value is null")
    void testIsVatWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isVat()).isFalse();
    }

    @Test
    @DisplayName("isExempt() should return false when value is null")
    void testIsExemptWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isExempt()).isFalse();
    }

    @Test
    @DisplayName("isSummary() should return false when value is null")
    void testIsSummaryWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isSummary()).isFalse();
    }

    @Test
    @DisplayName("isCustomsDuty() should return false when value is null")
    void testIsCustomsDutyWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isCustomsDuty()).isFalse();
    }

    @Test
    @DisplayName("isExciseTax() should return false when value is null")
    void testIsExciseTaxWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isExciseTax()).isFalse();
    }

    @Test
    @DisplayName("isGST() should return false when value is null")
    void testIsGSTWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isGST()).isFalse();
    }

    @Test
    @DisplayName("isSpecialTax() should return false when value is null")
    void testIsSpecialTaxWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        assertThat(type.isSpecialTax()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isVat() should return true for VAT code")
    void testIsVatTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setName("Value Added Tax");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isVat()).isTrue();
    }

    @Test
    @DisplayName("isExempt() should return true when isExempt is true")
    void testIsExemptTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("FRE");
        entity.setName("Exempt");
        entity.setExempt(true);
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isExempt()).isTrue();
    }

    @Test
    @DisplayName("isSummary() should return true when isSummary is true")
    void testIsSummaryTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("TOT");
        entity.setName("Summary");
        entity.setSummary(true);
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isSummary()).isTrue();
    }

    @Test
    @DisplayName("isCustomsDuty() should return true for Customs category")
    void testIsCustomsDutyTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("AAA");
        entity.setName("Customs duty");
        entity.setCategory("Customs");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isCustomsDuty()).isTrue();
    }

    @Test
    @DisplayName("isExciseTax() should return true for Excise category")
    void testIsExciseTaxTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("EXC");
        entity.setName("Excise tax");
        entity.setCategory("Excise");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isExciseTax()).isTrue();
    }

    @Test
    @DisplayName("isGST() should return true for GST category")
    void testIsGSTTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("GST");
        entity.setName("Goods and Services Tax");
        entity.setCategory("GST");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isGST()).isTrue();
    }

    @Test
    @DisplayName("isSpecialTax() should return true for Special Tax category")
    void testIsSpecialTaxTrue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("STX");
        entity.setName("Special tax");
        entity.setCategory("Special Tax");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.isSpecialTax()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setName("Value Added Tax");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("GST");
        entity.setName("Goods and Services Tax");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("GST");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setName("Value Added Tax");
        DutyTaxFeeCodeType type1 = new DutyTaxFeeCodeType(entity);
        DutyTaxFeeCodeType type2 = new DutyTaxFeeCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType("VAT");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType("VAT");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType("VAT");

        assertThat(type).isNotEqualTo("VAT");
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setName("Value Added Tax");
        DutyTaxFeeCodeType type1 = new DutyTaxFeeCodeType(entity);
        DutyTaxFeeCodeType type2 = new DutyTaxFeeCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setName("Value Added Tax");
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("VAT");
        assertThat(str).contains("Value Added Tax");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        DutyTaxFeeCodeType type = new DutyTaxFeeCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}