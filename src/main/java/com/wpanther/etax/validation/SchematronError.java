package com.wpanther.etax.validation;

import java.util.Objects;

/**
 * Represents a single Schematron validation error or warning.
 * Schematron rules typically include both Thai and English error messages.
 */
public class SchematronError {
    private final String ruleId;
    private final String message;
    private final String location;
    private final ErrorLevel level;
    private final String testExpression;

    public SchematronError(String ruleId, String message, String location, ErrorLevel level, String testExpression) {
        this.ruleId = ruleId;
        this.message = message;
        this.location = location;
        this.level = level;
        this.testExpression = testExpression;
    }

    /**
     * Get the rule ID (e.g., "TIV-DocumentContext-001", "RCT-Document-001")
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * Get the error message (may contain both Thai and English text)
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the XPath location of the failed assertion
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the error level (ERROR or WARNING)
     */
    public ErrorLevel getLevel() {
        return level;
    }

    /**
     * Get the XPath test expression that failed
     */
    public String getTestExpression() {
        return testExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchematronError that = (SchematronError) o;
        return Objects.equals(ruleId, that.ruleId) &&
               Objects.equals(message, that.message) &&
               Objects.equals(location, that.location) &&
               level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, message, location, level);
    }

    @Override
    public String toString() {
        return "SchematronError{" +
               "ruleId='" + ruleId + '\'' +
               ", message='" + message + '\'' +
               ", location='" + location + '\'' +
               ", level=" + level +
               '}';
    }

    /**
     * Error level for Schematron validation results
     */
    public enum ErrorLevel {
        /**
         * Failed assert (business rule violation)
         */
        ERROR,
        /**
         * Successful report (informational message)
         */
        WARNING
    }
}
