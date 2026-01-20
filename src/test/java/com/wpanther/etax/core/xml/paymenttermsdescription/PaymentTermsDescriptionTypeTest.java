package com.wpanther.etax.core.xml.paymenttermsdescription;

import com.wpanther.etax.core.entity.PaymentTermsDescriptionIdentifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PaymentTermsDescriptionType Tests")
class PaymentTermsDescriptionTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType("2");

        assertThat(type.getCode()).isEqualTo("2");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        PaymentTermsDescriptionType type = PaymentTermsDescriptionType.of("3");

        assertThat(type.getCode()).isEqualTo("3");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = PaymentTermsDescriptionType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.getName()).isEqualTo("Draft on issuing bank");
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setDescription("Draft to be drawn on the issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.getDescription()).isEqualTo("Draft to be drawn on the issuing bank");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isDraftRequired() should return false when value is null")
    void testIsDraftRequiredWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.isDraftRequired()).isFalse();
    }

    @Test
    @DisplayName("isBankingDraft() should return false when value is null")
    void testIsBankingDraftWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.isBankingDraft()).isFalse();
    }

    @Test
    @DisplayName("isIssuingBank() should return false when value is null")
    void testIsIssuingBankWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.isIssuingBank()).isFalse();
    }

    @Test
    @DisplayName("isAdvisingBank() should return false when value is null")
    void testIsAdvisingBankWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.isAdvisingBank()).isFalse();
    }

    @Test
    @DisplayName("isReimbursingBank() should return false when value is null")
    void testIsReimbursingBankWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.isReimbursingBank()).isFalse();
    }

    @Test
    @DisplayName("isNoDraft() should return false when value is null")
    void testIsNoDraftWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.isNoDraft()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isDraftRequired() should return true when draft is required")
    void testIsDraftRequiredTrue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        entity.setIsDraftRequired(true);
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isDraftRequired()).isTrue();
    }

    @Test
    @DisplayName("isDraftRequired() should return false when draft is not required")
    void testIsDraftRequiredFalse() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("6");
        entity.setName("No draft");
        entity.setIsDraftRequired(false);
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isDraftRequired()).isFalse();
    }

    @Test
    @DisplayName("isBankingDraft() should return true for banking draft codes (1-3)")
    void testIsBankingDraftTrue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isBankingDraft()).isTrue();
    }

    @Test
    @DisplayName("isIssuingBank() should return true for code 1")
    void testIsIssuingBankTrue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isIssuingBank()).isTrue();
    }

    @Test
    @DisplayName("isAdvisingBank() should return true for code 2")
    void testIsAdvisingBankTrue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("2");
        entity.setName("Draft on advising bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isAdvisingBank()).isTrue();
    }

    @Test
    @DisplayName("isReimbursingBank() should return true for code 3")
    void testIsReimbursingBankTrue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("3");
        entity.setName("Draft on reimbursing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isReimbursingBank()).isTrue();
    }

    @Test
    @DisplayName("isNoDraft() should return true for code 6")
    void testIsNoDraftTrue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("6");
        entity.setName("No draft required");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.isNoDraft()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("2");
        entity.setName("Draft on advising bank");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("2");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type1 = new PaymentTermsDescriptionType(entity);
        PaymentTermsDescriptionType type2 = new PaymentTermsDescriptionType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType("1");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType("1");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType("1");

        assertThat(type).isNotEqualTo("1");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        PaymentTermsDescriptionIdentifier entity1 = new PaymentTermsDescriptionIdentifier("1");
        entity1.setName("Draft on issuing bank");
        PaymentTermsDescriptionIdentifier entity2 = new PaymentTermsDescriptionIdentifier("2");
        entity2.setName("Draft on advising bank");
        PaymentTermsDescriptionType type1 = new PaymentTermsDescriptionType(entity1);
        PaymentTermsDescriptionType type2 = new PaymentTermsDescriptionType(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type1 = new PaymentTermsDescriptionType(entity);
        PaymentTermsDescriptionType type2 = new PaymentTermsDescriptionType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setName("Draft on issuing bank");
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType(entity);

        String str = type.toString();

        assertThat(str).contains("1");
        assertThat(str).contains("Draft on issuing bank");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        PaymentTermsDescriptionType type = new PaymentTermsDescriptionType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
