package com.wpanther.etax.validation;

/**
 * Validator interface for Schematron business rules validation.
 * <p>
 * This interface provides methods to validate XML documents against
 * ETDA Schematron rules, which complement XSD schema validation with
 * business rule checks.
 * <p>
 * Schematron validation uses XPath-based rules defined in .sch files
 * to check business rules that cannot be expressed in XSD, such as:
 * <ul>
 *   <li>Document type and version constraints</li>
 *   <li>Conditional field requirements</li>
 *   <li>Tax ID validation between seller and invoicer</li>
 *   <li>Purpose code validation based on document context</li>
 * </ul>
 */
public interface SchematronValidator {

    /**
     * Validate XML content against Schematron business rules for the specified document type.
     *
     * @param xmlContent The XML content to validate (as a string)
     * @param docType    The document type (determines which .sch file to use)
     * @return SchematronValidationResult containing any errors and warnings found
     * @throws IllegalArgumentException if xmlContent is null or empty
     * @throws SchematronValidationException if validation fails due to processing errors
     */
    SchematronValidationResult validate(String xmlContent, DocumentSchematron docType);

    /**
     * Validate XML content from an input stream against Schematron business rules.
     *
     * @param xmlInputStream The XML content as an input stream
     * @param docType        The document type (determines which .sch file to use)
     * @return SchematronValidationResult containing any errors and warnings found
     * @throws IllegalArgumentException if xmlInputStream is null
     * @throws SchematronValidationException if validation fails due to processing errors
     */
    SchematronValidationResult validate(java.io.InputStream xmlInputStream, DocumentSchematron docType);

    /**
     * Check if the Schematron file for the given document type is valid.
     * This validates the .sch file itself, not an XML document.
     *
     * @param docType The document type to check
     * @return true if the Schematron file is valid, false otherwise
     */
    boolean isSchematronValid(DocumentSchematron docType);
}
