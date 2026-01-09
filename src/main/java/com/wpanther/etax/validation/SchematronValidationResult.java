package com.wpanther.etax.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Result of Schematron validation containing errors and warnings.
 * Instances are immutable and can be created via factory methods.
 */
public class SchematronValidationResult {
    private final List<SchematronError> errors;
    private final List<SchematronError> warnings;
    private final boolean valid;

    private SchematronValidationResult(List<SchematronError> errors, List<SchematronError> warnings) {
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
        this.warnings = Collections.unmodifiableList(new ArrayList<>(warnings));
        this.valid = this.errors.isEmpty();
    }

    /**
     * Create a successful validation result (no errors or warnings)
     */
    public static SchematronValidationResult success() {
        return new SchematronValidationResult(List.of(), List.of());
    }

    /**
     * Create a validation result with errors
     */
    public static SchematronValidationResult invalid(List<SchematronError> errors) {
        return new SchematronValidationResult(errors, List.of());
    }

    /**
     * Create a validation result with errors and warnings
     */
    public static SchematronValidationResult invalid(List<SchematronError> errors, List<SchematronError> warnings) {
        return new SchematronValidationResult(errors, warnings);
    }

    /**
     * Create a validation result with warnings only (no errors)
     */
    public static SchematronValidationResult validWithWarnings(List<SchematronError> warnings) {
        return new SchematronValidationResult(List.of(), warnings);
    }

    /**
     * Check if validation passed (no errors)
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Get all errors (failed assertions)
     */
    public List<SchematronError> getErrors() {
        return errors;
    }

    /**
     * Get all warnings (successful reports)
     */
    public List<SchematronError> getWarnings() {
        return warnings;
    }

    /**
     * Get total number of issues (errors + warnings)
     */
    public int getTotalIssueCount() {
        return errors.size() + warnings.size();
    }

    /**
     * Check if there are any errors
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Check if there are any warnings
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchematronValidationResult that = (SchematronValidationResult) o;
        return valid == that.valid &&
               Objects.equals(errors, that.errors) &&
               Objects.equals(warnings, that.warnings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, warnings, valid);
    }

    @Override
    public String toString() {
        return "SchematronValidationResult{" +
               "valid=" + valid +
               ", errors=" + errors.size() +
               ", warnings=" + warnings.size() +
               '}';
    }
}
