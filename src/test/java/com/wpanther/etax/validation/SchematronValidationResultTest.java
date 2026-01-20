package com.wpanther.etax.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for SchematronValidationResult.
 */
@DisplayName("SchematronValidationResult Tests")
class SchematronValidationResultTest {

    // Factory method tests

    @Test
    @DisplayName("success() should return valid result with empty lists")
    void testSuccessFactoryMethod() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result.isValid()).isTrue();
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getWarnings()).isEmpty();
        assertThat(result.hasErrors()).isFalse();
        assertThat(result.hasWarnings()).isFalse();
        assertThat(result.getTotalIssueCount()).isZero();
    }

    @Test
    @DisplayName("invalid(errors) should return invalid result with errors only")
    void testInvalidWithErrorsOnly() {
        List<SchematronError> errors = List.of(
            new SchematronError("TIV-001", "Error message", "/path", SchematronError.ErrorLevel.ERROR, "test()"),
            new SchematronError("TIV-002", "Another error", "/path2", SchematronError.ErrorLevel.ERROR, "test2()")
        );

        SchematronValidationResult result = SchematronValidationResult.invalid(errors);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors()).hasSize(2);
        assertThat(result.getWarnings()).isEmpty();
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.hasWarnings()).isFalse();
    }

    @Test
    @DisplayName("invalid(errors, warnings) should contain both errors and warnings")
    void testInvalidWithErrorsAndWarnings() {
        List<SchematronError> errors = List.of(
            new SchematronError("TIV-001", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()")
        );
        List<SchematronError> warnings = List.of(
            new SchematronError("TIV-W01", "Warning", "/path", SchematronError.ErrorLevel.WARNING, "test()")
        );

        SchematronValidationResult result = SchematronValidationResult.invalid(errors, warnings);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getWarnings()).hasSize(1);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.hasWarnings()).isTrue();
    }

    @Test
    @DisplayName("validWithWarnings() should return valid result with warnings")
    void testValidWithWarningsOnly() {
        List<SchematronError> warnings = List.of(
            new SchematronError("TIV-W01", "Warning", "/path", SchematronError.ErrorLevel.WARNING, "test()")
        );

        SchematronValidationResult result = SchematronValidationResult.validWithWarnings(warnings);

        assertThat(result.isValid()).isTrue();
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getWarnings()).hasSize(1);
        assertThat(result.hasErrors()).isFalse();
        assertThat(result.hasWarnings()).isTrue();
    }

    // getTotalIssueCount tests

    @Test
    @DisplayName("getTotalIssueCount() should return sum of errors and warnings")
    void testGetTotalIssueCount() {
        List<SchematronError> errors = List.of(
            new SchematronError("E1", "Error 1", "/path", SchematronError.ErrorLevel.ERROR, "test()"),
            new SchematronError("E2", "Error 2", "/path", SchematronError.ErrorLevel.ERROR, "test()")
        );
        List<SchematronError> warnings = List.of(
            new SchematronError("W1", "Warning 1", "/path", SchematronError.ErrorLevel.WARNING, "test()")
        );

        SchematronValidationResult result = SchematronValidationResult.invalid(errors, warnings);

        assertThat(result.getTotalIssueCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("getTotalIssueCount() should return 0 for success")
    void testGetTotalIssueCountForSuccess() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result.getTotalIssueCount()).isZero();
    }

    // hasErrors/hasWarnings tests

    @Test
    @DisplayName("hasErrors() should return true when errors exist")
    void testHasErrorsTrue() {
        List<SchematronError> errors = List.of(
            new SchematronError("E1", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()")
        );

        SchematronValidationResult result = SchematronValidationResult.invalid(errors);

        assertThat(result.hasErrors()).isTrue();
    }

    @Test
    @DisplayName("hasErrors() should return false when no errors")
    void testHasErrorsFalse() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("hasWarnings() should return true when warnings exist")
    void testHasWarningsTrue() {
        List<SchematronError> warnings = List.of(
            new SchematronError("W1", "Warning", "/path", SchematronError.ErrorLevel.WARNING, "test()")
        );

        SchematronValidationResult result = SchematronValidationResult.validWithWarnings(warnings);

        assertThat(result.hasWarnings()).isTrue();
    }

    @Test
    @DisplayName("hasWarnings() should return false when no warnings")
    void testHasWarningsFalse() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result.hasWarnings()).isFalse();
    }

    // Equals and HashCode tests

    @Test
    @DisplayName("equals() should return true for same result")
    void testEqualsSameResult() {
        SchematronValidationResult result1 = SchematronValidationResult.success();
        SchematronValidationResult result2 = SchematronValidationResult.success();

        assertThat(result1).isEqualTo(result2);
    }

    @Test
    @DisplayName("equals() should return true for same errors and warnings")
    void testEqualsWithSameContent() {
        SchematronError error = new SchematronError("E1", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()");
        List<SchematronError> errors = List.of(error);

        SchematronValidationResult result1 = SchematronValidationResult.invalid(errors);
        SchematronValidationResult result2 = SchematronValidationResult.invalid(errors);

        assertThat(result1).isEqualTo(result2);
    }

    @Test
    @DisplayName("equals() should return false for different content")
    void testEqualsDifferentContent() {
        SchematronValidationResult result1 = SchematronValidationResult.success();
        SchematronValidationResult result2 = SchematronValidationResult.invalid(
            List.of(new SchematronError("E1", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()"))
        );

        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result).isNotEqualTo("string");
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        SchematronValidationResult result = SchematronValidationResult.success();

        assertThat(result).isEqualTo(result);
    }

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        SchematronValidationResult result1 = SchematronValidationResult.success();
        SchematronValidationResult result2 = SchematronValidationResult.success();

        assertThat(result1.hashCode()).isEqualTo(result2.hashCode());
    }

    // toString tests

    @Test
    @DisplayName("toString() should contain valid status")
    void testToStringContainsValid() {
        SchematronValidationResult result = SchematronValidationResult.success();
        String str = result.toString();

        assertThat(str).contains("valid=true");
    }

    @Test
    @DisplayName("toString() should contain invalid status")
    void testToStringContainsInvalid() {
        SchematronValidationResult result = SchematronValidationResult.invalid(
            List.of(new SchematronError("E1", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()"))
        );
        String str = result.toString();

        assertThat(str).contains("valid=false");
    }

    @Test
    @DisplayName("toString() should contain error count")
    void testToStringContainsErrorCount() {
        SchematronValidationResult result = SchematronValidationResult.invalid(
            List.of(
                new SchematronError("E1", "Error 1", "/path", SchematronError.ErrorLevel.ERROR, "test()"),
                new SchematronError("E2", "Error 2", "/path", SchematronError.ErrorLevel.ERROR, "test()")
            )
        );
        String str = result.toString();

        assertThat(str).contains("errors=2");
    }

    @Test
    @DisplayName("toString() should contain warning count")
    void testToStringContainsWarningCount() {
        SchematronValidationResult result = SchematronValidationResult.validWithWarnings(
            List.of(new SchematronError("W1", "Warning", "/path", SchematronError.ErrorLevel.WARNING, "test()"))
        );
        String str = result.toString();

        assertThat(str).contains("warnings=1");
    }

    // Immutability tests

    @Test
    @DisplayName("getErrors() should return unmodifiable list")
    void testGetErrorsImmutability() {
        SchematronValidationResult result = SchematronValidationResult.invalid(
            List.of(new SchematronError("E1", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()"))
        );

        assertThatThrownBy(() -> result.getErrors().add(
            new SchematronError("E2", "Error 2", "/path", SchematronError.ErrorLevel.ERROR, "test()")
        )).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("getWarnings() should return unmodifiable list")
    void testGetWarningsImmutability() {
        SchematronValidationResult result = SchematronValidationResult.validWithWarnings(
            List.of(new SchematronError("W1", "Warning", "/path", SchematronError.ErrorLevel.WARNING, "test()"))
        );

        assertThatThrownBy(() -> result.getWarnings().add(
            new SchematronError("W2", "Warning 2", "/path", SchematronError.ErrorLevel.WARNING, "test()")
        )).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Modifying original list should not affect result")
    void testDefensiveCopy() {
        List<SchematronError> errors = new ArrayList<>();
        errors.add(new SchematronError("E1", "Error", "/path", SchematronError.ErrorLevel.ERROR, "test()"));

        SchematronValidationResult result = SchematronValidationResult.invalid(errors);

        // Modify original list
        errors.add(new SchematronError("E2", "Error 2", "/path", SchematronError.ErrorLevel.ERROR, "test()"));

        // Result should still have only 1 error
        assertThat(result.getErrors()).hasSize(1);
    }
}
