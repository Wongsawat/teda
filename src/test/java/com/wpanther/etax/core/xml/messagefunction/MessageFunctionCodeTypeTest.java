package com.wpanther.etax.core.xml.messagefunction;

import com.wpanther.etax.core.entity.MessageFunctionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MessageFunctionCodeType Tests")
class MessageFunctionCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("9");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        MessageFunctionCodeType type = new MessageFunctionCodeType("7");

        assertThat(type.getCode()).isEqualTo("7");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        MessageFunctionCodeType type = MessageFunctionCodeType.of("31");

        assertThat(type.getCode()).isEqualTo("31");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        MessageFunctionCode entity = new MessageFunctionCode("7");
        entity.setName("Credit note related to tax invoice");
        MessageFunctionCodeType type = MessageFunctionCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    // Getter tests with null value

    @Test
    @DisplayName("getName() should return null when value is null")
    void testGetNameWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("getDescription() should return null when value is null")
    void testGetDescriptionWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.getDescription()).isNull();
    }

    // Getter tests with value

    @Test
    @DisplayName("getName() should return name when value exists")
    void testGetNameWithValue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.getName()).isEqualTo("Original");
    }

    @Test
    @DisplayName("getDescription() should return description when value exists")
    void testGetDescriptionWithValue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setDescription("Original message");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.getDescription()).isEqualTo("Original message");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isOriginal() should return false when value is null")
    void testIsOriginalWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isOriginal()).isFalse();
    }

    @Test
    @DisplayName("isModification() should return false when value is null")
    void testIsModificationWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isModification()).isFalse();
    }

    @Test
    @DisplayName("isReplacement() should return false when value is null")
    void testIsReplacementWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isReplacement()).isFalse();
    }

    @Test
    @DisplayName("getCategory() should return null when value is null")
    void testGetCategoryWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.getCategory()).isNull();
    }

    @Test
    @DisplayName("isAcceptance() should return false when value is null")
    void testIsAcceptanceWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isAcceptance()).isFalse();
    }

    @Test
    @DisplayName("isCancellation() should return false when value is null")
    void testIsCancellationWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isCancellation()).isFalse();
    }

    @Test
    @DisplayName("isChange() should return false when value is null")
    void testIsChangeWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isChange()).isFalse();
    }

    @Test
    @DisplayName("isConfirmation() should return false when value is null")
    void testIsConfirmationWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isConfirmation()).isFalse();
    }

    @Test
    @DisplayName("isFinancialReversal() should return false when value is null")
    void testIsFinancialReversalWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isFinancialReversal()).isFalse();
    }

    @Test
    @DisplayName("isSchedule() should return false when value is null")
    void testIsScheduleWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.isSchedule()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isOriginal() should return true for code 9")
    void testIsOriginalTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isOriginal()).isTrue();
    }

    @Test
    @DisplayName("isModification() should return true for code 4")
    void testIsModificationTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("4");
        entity.setName("Change");
        entity.setIsModification(true);
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isModification()).isTrue();
    }

    @Test
    @DisplayName("isReplacement() should return true for code 5")
    void testIsReplacementTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("5");
        entity.setName("Replace");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isReplacement()).isTrue();
    }

    @Test
    @DisplayName("getCategory() should return category when value exists")
    void testGetCategoryWithValue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setCategory("Transaction Control");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.getCategory()).isEqualTo("Transaction Control");
    }

    @Test
    @DisplayName("isAcceptance() should return true when isAcceptance is true")
    void testIsAcceptanceTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("31");
        entity.setIsAcceptance(true);
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isAcceptance()).isTrue();
    }

    @Test
    @DisplayName("isCancellation() should return true for code 1")
    void testIsCancellationTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("1");
        entity.setName("Cancellation");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isCancellation()).isTrue();
    }

    @Test
    @DisplayName("isChange() should return true for code 4")
    void testIsChangeTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("4");
        entity.setName("Change");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isChange()).isTrue();
    }

    @Test
    @DisplayName("isConfirmation() should return true for code 6")
    void testIsConfirmationTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("6");
        entity.setName("Confirmation");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isConfirmation()).isTrue();
    }

    @Test
    @DisplayName("isFinancialReversal() should return true for code 37")
    void testIsFinancialReversalTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("37");
        entity.setName("Credit reversal");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isFinancialReversal()).isTrue();
    }

    @Test
    @DisplayName("isSchedule() should return true for code 24")
    void testIsScheduleTrue() {
        MessageFunctionCode entity = new MessageFunctionCode("24");
        entity.setName("Schedule");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.isSchedule()).isTrue();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();
        MessageFunctionCode entity = new MessageFunctionCode("13");
        entity.setName("Cancellation");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("13");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type1 = new MessageFunctionCodeType(entity);
        MessageFunctionCodeType type2 = new MessageFunctionCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        MessageFunctionCodeType type = new MessageFunctionCodeType("9");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        MessageFunctionCodeType type = new MessageFunctionCodeType("9");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        MessageFunctionCodeType type = new MessageFunctionCodeType("9");

        assertThat(type).isNotEqualTo("9");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        MessageFunctionCode entity1 = new MessageFunctionCode("9");
        entity1.setName("Original");
        MessageFunctionCode entity2 = new MessageFunctionCode("4");
        entity2.setName("Change");
        MessageFunctionCodeType type1 = new MessageFunctionCodeType(entity1);
        MessageFunctionCodeType type2 = new MessageFunctionCodeType(entity2);

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type1 = new MessageFunctionCodeType(entity);
        MessageFunctionCodeType type2 = new MessageFunctionCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should return 0 for null value")
    void testHashCodeNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.hashCode()).isZero();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return formatted string when value exists")
    void testToStringWithValue() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setName("Original");
        MessageFunctionCodeType type = new MessageFunctionCodeType(entity);

        String str = type.toString();

        assertThat(str).contains("9");
        assertThat(str).contains("Original");
    }

    @Test
    @DisplayName("toString() should return 'null' when value is null")
    void testToStringWithNullValue() {
        MessageFunctionCodeType type = new MessageFunctionCodeType();

        assertThat(type.toString()).isEqualTo("null");
    }
}
