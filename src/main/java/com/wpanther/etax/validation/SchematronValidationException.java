package com.wpanther.etax.validation;

/**
 * Exception thrown when Schematron validation fails due to processing errors.
 * This is distinct from validation errors found in the XML document itself
 * (which are returned in SchematronValidationResult).
 * <p>
 * Use this exception for problems like:
 * <ul>
 *   <li>Unable to load Schematron file</li>
 *   <li>Invalid Schematron syntax</li>
 *   <li>XML parsing errors</li>
 *   <li>XSLT transformation errors</li>
 * </ul>
 */
public class SchematronValidationException extends RuntimeException {

    private final DocumentSchematron documentType;

    public SchematronValidationException(String message) {
        super(message);
        this.documentType = null;
    }

    public SchematronValidationException(String message, Throwable cause) {
        super(message, cause);
        this.documentType = null;
    }

    public SchematronValidationException(String message, DocumentSchematron documentType) {
        super(message);
        this.documentType = documentType;
    }

    public SchematronValidationException(String message, DocumentSchematron documentType, Throwable cause) {
        super(message, cause);
        this.documentType = documentType;
    }

    public DocumentSchematron getDocumentType() {
        return documentType;
    }
}
