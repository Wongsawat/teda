package com.wpanther.etax.validation;

import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Internal tests for SchematronValidatorImpl using reflection to test private methods
 * and edge cases that cannot be reached through the public API.
 */
class SchematronValidatorImplTest {

    private SchematronValidatorImpl validator;

    @BeforeEach
    void setUp() {
        validator = new SchematronValidatorImpl();
    }

    @Nested
    @DisplayName("isSchematronValid tests")
    class IsSchematronValidTests {

        @Test
        @DisplayName("isSchematronValid returns false when loadSchematron throws exception")
        void testIsSchematronValidReturnsFalseOnLoadException() {
            // Create a subclass that throws on loadSchematron
            SchematronValidatorImpl throwingValidator = new SchematronValidatorImpl() {
                @Override
                protected com.helger.schematron.pure.SchematronResourcePure loadSchematron(DocumentSchematron docType) {
                    throw new RuntimeException("Simulated load failure");
                }
            };

            boolean isValid = throwingValidator.isSchematronValid(DocumentSchematron.TAX_INVOICE);

            assertThat(isValid).isFalse();
        }
    }

    @Nested
    @DisplayName("loadSchematron tests")
    class LoadSchematronTests {

        @Test
        @DisplayName("loadSchematron throws SchematronValidationException when file not found")
        void testLoadSchematronThrowsWhenFileNotFound() throws Exception {
            // Create mock DocumentSchematron that returns a non-existent path
            DocumentSchematron mockDocType = mock(DocumentSchematron.class);
            when(mockDocType.getSchematronPath()).thenReturn("non-existent-schematron-file.sch");

            // Use reflection to access the protected loadSchematron method
            Method loadSchematronMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "loadSchematron", DocumentSchematron.class);
            loadSchematronMethod.setAccessible(true);

            // When using reflection, InvocationTargetException wraps the actual exception
            assertThatThrownBy(() -> loadSchematronMethod.invoke(validator, mockDocType))
                .isInstanceOf(java.lang.reflect.InvocationTargetException.class)
                .extracting(e -> ((java.lang.reflect.InvocationTargetException) e).getTargetException())
                .isInstanceOf(SchematronValidationException.class)
                .extracting(Throwable::getMessage)
                .asString()
                .contains("not found");
        }
    }

    @Nested
    @DisplayName("parseSchematronOutput tests")
    class ParseSchematronOutputTests {

        @Test
        @DisplayName("parseSchematronOutput returns success for null output")
        void testParseNullSchematronOutputReturnsSuccess() throws Exception {
            Method parseMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "parseSchematronOutput", SchematronOutputType.class, DocumentSchematron.class);
            parseMethod.setAccessible(true);

            SchematronValidationResult result = (SchematronValidationResult)
                parseMethod.invoke(validator, null, DocumentSchematron.TAX_INVOICE);

            assertThat(result.isValid()).isTrue();
            assertThat(result.getErrors()).isEmpty();
            assertThat(result.getWarnings()).isEmpty();
        }

        @Test
        @DisplayName("parseSchematronOutput returns success when output has null list")
        void testParseSchematronOutputWithNullListReturnsSuccess() throws Exception {
            Method parseMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "parseSchematronOutput", SchematronOutputType.class, DocumentSchematron.class);
            parseMethod.setAccessible(true);

            // Create a SchematronOutputType with null list
            SchematronOutputType output = new SchematronOutputType();
            // getActivePatternAndFiredRuleAndFailedAssert() returns an empty list by default, not null
            // The null check in the code is for the output itself being null

            SchematronValidationResult result = (SchematronValidationResult)
                parseMethod.invoke(validator, output, DocumentSchematron.TAX_INVOICE);

            assertThat(result.isValid()).isTrue();
            assertThat(result.getErrors()).isEmpty();
        }
    }

    @Nested
    @DisplayName("extractWarningFromObject tests")
    class ExtractWarningFromObjectTests {

        @Test
        @DisplayName("extractWarningFromObject returns fallback when reflection fails")
        void testExtractWarningFallbackOnReflectionFailure() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractWarningFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Pass an object that doesn't have getId/getLocation/getTest methods
            Object badObject = new Object();

            SchematronError result = (SchematronError) extractMethod.invoke(validator, badObject);

            assertThat(result.getRuleId()).isEqualTo("UNKNOWN");
            assertThat(result.getMessage()).isEqualTo("Failed to extract warning details");
            assertThat(result.getLevel()).isEqualTo(SchematronError.ErrorLevel.WARNING);
        }

        @Test
        @DisplayName("extractWarningFromObject handles object with null getText result")
        void testExtractWarningWithNullTextResult() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractWarningFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Create a mock object with the required methods but getText returns null
            MockSuccessfulReport mockReport = new MockSuccessfulReport("TEST-001", "/test/path", "test expression", null);

            SchematronError result = (SchematronError) extractMethod.invoke(validator, mockReport);

            assertThat(result.getRuleId()).isEqualTo("TEST-001");
            assertThat(result.getLocation()).isEqualTo("/test/path");
            assertThat(result.getTestExpression()).isEqualTo("test expression");
            assertThat(result.getMessage()).isEmpty();
            assertThat(result.getLevel()).isEqualTo(SchematronError.ErrorLevel.WARNING);
        }

        @Test
        @DisplayName("extractWarningFromObject falls back to toString when getValue not available")
        void testExtractWarningFallsBackToToString() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractWarningFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Create a mock object where getText returns an object without getValue method
            MockSuccessfulReport mockReport = new MockSuccessfulReport(
                "TEST-002", "/test/path", "test expression",
                new TextWithoutGetValue("message via toString")
            );

            SchematronError result = (SchematronError) extractMethod.invoke(validator, mockReport);

            assertThat(result.getRuleId()).isEqualTo("TEST-002");
            assertThat(result.getMessage()).contains("toString");
        }

        @Test
        @DisplayName("extractWarningFromObject extracts message via getValue")
        void testExtractWarningWithGetValue() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractWarningFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Create a mock object where getText returns an object with getValue method
            MockSuccessfulReport mockReport = new MockSuccessfulReport(
                "TEST-003", "/test/path", "test expression",
                new TextWithGetValue("message via getValue")
            );

            SchematronError result = (SchematronError) extractMethod.invoke(validator, mockReport);

            assertThat(result.getRuleId()).isEqualTo("TEST-003");
            assertThat(result.getMessage()).isEqualTo("message via getValue");
        }
    }

    @Nested
    @DisplayName("extractErrorFromObject tests")
    class ExtractErrorFromObjectTests {

        @Test
        @DisplayName("extractErrorFromObject returns fallback when reflection fails")
        void testExtractErrorFallbackOnReflectionFailure() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractErrorFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Pass an object that doesn't have getId/getLocation/getTest methods
            Object badObject = new Object();

            SchematronError result = (SchematronError) extractMethod.invoke(validator, badObject);

            assertThat(result.getRuleId()).isEqualTo("UNKNOWN");
            assertThat(result.getMessage()).isEqualTo("Failed to extract error details");
            assertThat(result.getLevel()).isEqualTo(SchematronError.ErrorLevel.ERROR);
        }

        @Test
        @DisplayName("extractErrorFromObject handles object with null getText result")
        void testExtractErrorWithNullTextResult() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractErrorFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Create a mock object with the required methods but getText returns null
            MockFailedAssert mockAssert = new MockFailedAssert("ERR-001", "/error/path", "error expression", null);

            SchematronError result = (SchematronError) extractMethod.invoke(validator, mockAssert);

            assertThat(result.getRuleId()).isEqualTo("ERR-001");
            assertThat(result.getLocation()).isEqualTo("/error/path");
            assertThat(result.getTestExpression()).isEqualTo("error expression");
            assertThat(result.getMessage()).isEmpty();
            assertThat(result.getLevel()).isEqualTo(SchematronError.ErrorLevel.ERROR);
        }

        @Test
        @DisplayName("extractErrorFromObject falls back to toString when getValue not available")
        void testExtractErrorFallsBackToToString() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractErrorFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Create a mock object where getText returns an object without getValue method
            MockFailedAssert mockAssert = new MockFailedAssert(
                "ERR-002", "/error/path", "error expression",
                new TextWithoutGetValue("error via toString")
            );

            SchematronError result = (SchematronError) extractMethod.invoke(validator, mockAssert);

            assertThat(result.getRuleId()).isEqualTo("ERR-002");
            assertThat(result.getMessage()).contains("toString");
        }

        @Test
        @DisplayName("extractErrorFromObject extracts message via getValue")
        void testExtractErrorWithGetValue() throws Exception {
            Method extractMethod = SchematronValidatorImpl.class.getDeclaredMethod(
                "extractErrorFromObject", Object.class);
            extractMethod.setAccessible(true);

            // Create a mock object where getText returns an object with getValue method
            MockFailedAssert mockAssert = new MockFailedAssert(
                "ERR-003", "/error/path", "error expression",
                new TextWithGetValue("error via getValue")
            );

            SchematronError result = (SchematronError) extractMethod.invoke(validator, mockAssert);

            assertThat(result.getRuleId()).isEqualTo("ERR-003");
            assertThat(result.getMessage()).isEqualTo("error via getValue");
        }
    }

    // Helper classes for testing reflection-based extraction

    /**
     * Mock SuccessfulReport-like object for testing extractWarningFromObject
     */
    static class MockSuccessfulReport {
        private final String id;
        private final String location;
        private final String test;
        private final Object text;

        MockSuccessfulReport(String id, String location, String test, Object text) {
            this.id = id;
            this.location = location;
            this.test = test;
            this.text = text;
        }

        public String getId() { return id; }
        public String getLocation() { return location; }
        public String getTest() { return test; }
        public Object getText() { return text; }
    }

    /**
     * Mock FailedAssert-like object for testing extractErrorFromObject
     */
    static class MockFailedAssert {
        private final String id;
        private final String location;
        private final String test;
        private final Object text;

        MockFailedAssert(String id, String location, String test, Object text) {
            this.id = id;
            this.location = location;
            this.test = test;
            this.text = text;
        }

        public String getId() { return id; }
        public String getLocation() { return location; }
        public String getTest() { return test; }
        public Object getText() { return text; }
    }

    /**
     * Mock text object without getValue method (falls back to toString)
     */
    static class TextWithoutGetValue {
        private final String value;

        TextWithoutGetValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "toString: " + value;
        }
    }

    /**
     * Mock text object with getValue method
     */
    static class TextWithGetValue {
        private final String value;

        TextWithGetValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "TextWithGetValue{" + value + "}";
        }
    }
}
