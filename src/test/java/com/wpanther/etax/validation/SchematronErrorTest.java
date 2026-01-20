package com.wpanther.etax.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for SchematronError.
 */
@DisplayName("SchematronError Tests")
class SchematronErrorTest {

    // Constructor tests

    @Test
    @DisplayName("Constructor should set all fields")
    void testConstructor() {
        SchematronError error = new SchematronError(
            "TIV-001",
            "Error message",
            "/path/to/element",
            SchematronError.ErrorLevel.ERROR,
            "test() expression"
        );

        assertThat(error.getRuleId()).isEqualTo("TIV-001");
        assertThat(error.getMessage()).isEqualTo("Error message");
        assertThat(error.getLocation()).isEqualTo("/path/to/element");
        assertThat(error.getLevel()).isEqualTo(SchematronError.ErrorLevel.ERROR);
        assertThat(error.getTestExpression()).isEqualTo("test() expression");
    }

    @Test
    @DisplayName("Constructor should accept null values")
    void testConstructorWithNulls() {
        SchematronError error = new SchematronError(null, null, null, null, null);

        assertThat(error.getRuleId()).isNull();
        assertThat(error.getMessage()).isNull();
        assertThat(error.getLocation()).isNull();
        assertThat(error.getLevel()).isNull();
        assertThat(error.getTestExpression()).isNull();
    }

    @Test
    @DisplayName("Constructor should create warning level")
    void testConstructorWarningLevel() {
        SchematronError warning = new SchematronError(
            "TIV-W01",
            "Warning message",
            "/path",
            SchematronError.ErrorLevel.WARNING,
            "test()"
        );

        assertThat(warning.getLevel()).isEqualTo(SchematronError.ErrorLevel.WARNING);
    }

    // Getter tests

    @Test
    @DisplayName("getRuleId() should return rule ID")
    void testGetRuleId() {
        SchematronError error = new SchematronError("RCT-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.getRuleId()).isEqualTo("RCT-001");
    }

    @Test
    @DisplayName("getMessage() should return message with Thai and English text")
    void testGetMessage() {
        String message = "ชื่อเอกสารต้องระบุ / Document name is required";
        SchematronError error = new SchematronError("TIV-001", message, "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.getMessage()).isEqualTo(message);
        assertThat(error.getMessage()).contains("ชื่อเอกสาร");
        assertThat(error.getMessage()).contains("Document name");
    }

    @Test
    @DisplayName("getLocation() should return XPath location")
    void testGetLocation() {
        String xpath = "/CrossIndustryInvoice/ExchangedDocument/ID";
        SchematronError error = new SchematronError("TIV-001", "msg", xpath, SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.getLocation()).isEqualTo(xpath);
    }

    @Test
    @DisplayName("getLevel() should return ERROR level")
    void testGetLevelError() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.getLevel()).isEqualTo(SchematronError.ErrorLevel.ERROR);
    }

    @Test
    @DisplayName("getLevel() should return WARNING level")
    void testGetLevelWarning() {
        SchematronError warning = new SchematronError("TIV-W01", "msg", "/path", SchematronError.ErrorLevel.WARNING, "test()");

        assertThat(warning.getLevel()).isEqualTo(SchematronError.ErrorLevel.WARNING);
    }

    @Test
    @DisplayName("getTestExpression() should return XPath test")
    void testGetTestExpression() {
        String test = "string-length(ram:ID) > 0";
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, test);

        assertThat(error.getTestExpression()).isEqualTo(test);
    }

    // ErrorLevel enum tests

    @Test
    @DisplayName("ErrorLevel enum should have ERROR value")
    void testErrorLevelEnumError() {
        assertThat(SchematronError.ErrorLevel.ERROR).isNotNull();
        assertThat(SchematronError.ErrorLevel.ERROR.name()).isEqualTo("ERROR");
    }

    @Test
    @DisplayName("ErrorLevel enum should have WARNING value")
    void testErrorLevelEnumWarning() {
        assertThat(SchematronError.ErrorLevel.WARNING).isNotNull();
        assertThat(SchematronError.ErrorLevel.WARNING.name()).isEqualTo("WARNING");
    }

    @Test
    @DisplayName("ErrorLevel enum should have exactly 2 values")
    void testErrorLevelEnumCount() {
        assertThat(SchematronError.ErrorLevel.values()).hasSize(2);
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same content")
    void testEqualsSameContent() {
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error1).isEqualTo(error2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error).isEqualTo(error);
    }

    @Test
    @DisplayName("equals() should return false for different ruleId")
    void testEqualsDifferentRuleId() {
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-002", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error1).isNotEqualTo(error2);
    }

    @Test
    @DisplayName("equals() should return false for different message")
    void testEqualsDifferentMessage() {
        SchematronError error1 = new SchematronError("TIV-001", "msg1", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-001", "msg2", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error1).isNotEqualTo(error2);
    }

    @Test
    @DisplayName("equals() should return false for different location")
    void testEqualsDifferentLocation() {
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path1", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-001", "msg", "/path2", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error1).isNotEqualTo(error2);
    }

    @Test
    @DisplayName("equals() should return false for different level")
    void testEqualsDifferentLevel() {
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.WARNING, "test()");

        assertThat(error1).isNotEqualTo(error2);
    }

    @Test
    @DisplayName("equals() should ignore testExpression in comparison")
    void testEqualsIgnoresTestExpression() {
        // Based on the equals implementation which doesn't include testExpression
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test1()");
        SchematronError error2 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test2()");

        // These should be equal because testExpression is not part of equals
        assertThat(error1).isEqualTo(error2);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error).isNotEqualTo("string");
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error1.hashCode()).isEqualTo(error2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should be different for different objects")
    void testHashCodeDifferent() {
        SchematronError error1 = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        SchematronError error2 = new SchematronError("TIV-002", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error1.hashCode()).isNotEqualTo(error2.hashCode());
    }

    // toString tests

    @Test
    @DisplayName("toString() should contain ruleId")
    void testToStringContainsRuleId() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.toString()).contains("TIV-001");
    }

    @Test
    @DisplayName("toString() should contain message")
    void testToStringContainsMessage() {
        SchematronError error = new SchematronError("TIV-001", "Error message", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.toString()).contains("Error message");
    }

    @Test
    @DisplayName("toString() should contain location")
    void testToStringContainsLocation() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path/to/element", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.toString()).contains("/path/to/element");
    }

    @Test
    @DisplayName("toString() should contain level")
    void testToStringContainsLevel() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");

        assertThat(error.toString()).contains("ERROR");
    }

    @Test
    @DisplayName("toString() should have correct format")
    void testToStringFormat() {
        SchematronError error = new SchematronError("TIV-001", "msg", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        String str = error.toString();

        assertThat(str).startsWith("SchematronError{");
        assertThat(str).endsWith("}");
        assertThat(str).contains("ruleId='TIV-001'");
    }
}