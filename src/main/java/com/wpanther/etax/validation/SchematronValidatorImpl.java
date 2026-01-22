package com.wpanther.etax.validation;

import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.io.resource.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of SchematronValidator using ph-schematron 8.0.6.
 * <p>
 * This validator loads Schematron files from the classpath and applies
 * XSLT-based validation to XML documents, returning business rule violations
 * as SchematronValidationResult.
 */
public class SchematronValidatorImpl implements SchematronValidator {

    private static final Logger log = LoggerFactory.getLogger(SchematronValidatorImpl.class);
    private static final String SVRL_NS = "http://purl.oclc.org/dsdl/svrl";

    @Override
    public SchematronValidationResult validate(String xmlContent, DocumentSchematron docType) {
        if (xmlContent == null || xmlContent.isBlank()) {
            throw new IllegalArgumentException("XML content cannot be null or empty");
        }

        try {
            // Create StreamSource from string content (ph-schematron requires StreamSource or DOMSource)
            Source source = new StreamSource(new StringReader(xmlContent));
            return validateFromSource(source, docType);

        } catch (Exception e) {
            log.error("Failed to parse XML for Schematron validation", e);
            throw new SchematronValidationException("Failed to parse XML: " + e.getMessage(), docType, e);
        }
    }

    @Override
    public SchematronValidationResult validate(InputStream xmlInputStream, DocumentSchematron docType) {
        if (xmlInputStream == null) {
            throw new IllegalArgumentException("XML input stream cannot be null");
        }

        try {
            // Create Source from input stream
            Source source = new StreamSource(xmlInputStream);
            return validateFromSource(source, docType);

        } catch (Exception e) {
            log.error("Failed to parse XML for Schematron validation", e);
            throw new SchematronValidationException("Failed to parse XML: " + e.getMessage(), docType, e);
        } finally {
            try {
                xmlInputStream.close();
            } catch (IOException e) {
                log.warn("Failed to close input stream", e);
            }
        }
    }

    @Override
    public boolean isSchematronValid(DocumentSchematron docType) {
        try {
            SchematronResourcePure schematron = loadSchematron(docType);
            // Basic validity check - if we can load it, it's likely valid
            return schematron != null;
        } catch (Exception e) {
            log.error("Failed to validate Schematron file for {}", docType, e);
            return false;
        }
    }

    /**
     * Core validation logic using ph-schematron
     */
    private SchematronValidationResult validateFromSource(Source xmlSource, DocumentSchematron docType) {
        try {
            // Load Schematron resource
            SchematronResourcePure schematron = loadSchematron(docType);

            // Apply Schematron validation and get SVRL output
            SchematronOutputType output = schematron.applySchematronValidationToSVRL(xmlSource);

            // Parse SVRL output to extract errors and warnings
            return parseSchematronOutput(output, docType);

        } catch (Exception e) {
            log.error("Schematron validation failed for {}", docType, e);
            throw new SchematronValidationException("Schematron validation failed: " + e.getMessage(), docType, e);
        }
    }

    /**
     * Load Schematron resource from classpath.
     * <p>
     * This method is protected to allow testing with subclasses that simulate load failures.
     *
     * @param docType the document type whose Schematron file to load
     * @return the loaded SchematronResourcePure
     * @throws SchematronValidationException if the Schematron file is not found
     */
    protected SchematronResourcePure loadSchematron(DocumentSchematron docType) {
        // Create a ClassPathResource for the Schematron file
        IReadableResource resource = new ClassPathResource(docType.getSchematronPath());

        if (!resource.exists()) {
            throw new SchematronValidationException(
                "Schematron file not found on classpath: " + docType.getSchematronPath(), docType);
        }

        // Load and parse Schematron file using Pure implementation
        // Constructor requires: IReadableResource, String (optional language), IPSErrorHandler (optional)
        return new SchematronResourcePure(resource);
    }

    /**
     * Parse SchematronOutputType to extract failed assertions and successful reports
     */
    private SchematronValidationResult parseSchematronOutput(SchematronOutputType output, DocumentSchematron docType) {
        List<SchematronError> errors = new ArrayList<>();
        List<SchematronError> warnings = new ArrayList<>();

        if (output == null || output.getActivePatternAndFiredRuleAndFailedAssert() == null) {
            log.debug("Schematron validation for {} completed: no output (possibly empty Schematron)", docType);
            return SchematronValidationResult.success();
        }

        // Iterate through all elements in the SVRL output
        for (Object obj : output.getActivePatternAndFiredRuleAndFailedAssert()) {
            // Check class name to determine type
            String className = obj.getClass().getSimpleName();
            log.trace("Processing SVRL element: {}", className);

            // NOTE: FailedAssert branch is currently unreachable because all production
            // Schematron files (TaxInvoice, Receipt, DebitCreditNote, Invoice, etc.) use
            // <sch:report> elements exclusively and contain zero <sch:assert> elements.
            // - <sch:assert> → FailedAssert in SVRL → ERROR (not used in production)
            // - <sch:report> → SuccessfulReport in SVRL → WARNING (used by all files)
            // This branch is retained for future compatibility if Schematron files are
            // updated to include assertions.
            if (className.contains("FailedAssert")) {
                // This is a failed assertion (error)
                SchematronError error = extractErrorFromObject(obj);
                log.info("Found Schematron error: ruleId={}, location={}, message={}",
                    error.getRuleId(), error.getLocation(), error.getMessage());
                errors.add(error);
            } else if (className.contains("SuccessfulReport")) {
                // This is a successful report (warning/info)
                SchematronError warning = extractWarningFromObject(obj);
                warnings.add(warning);
            }
        }

        log.debug("Schematron validation for {} completed: {} errors, {} warnings",
            docType, errors.size(), warnings.size());

        if (errors.isEmpty()) {
            return warnings.isEmpty()
                ? SchematronValidationResult.success()
                : SchematronValidationResult.validWithWarnings(warnings);
        } else {
            // NOTE: This branch is currently unreachable because all production Schematron
            // files use only <sch:report> elements (no <sch:assert>), so errors list is
            // always empty. Retained for future compatibility.
            return SchematronValidationResult.invalid(errors, warnings);
        }
    }

    /**
     * Extract error information from a failed assertion object using reflection.
     * <p>
     * <b>NOTE:</b> This method is currently unreachable in production because all Thai e-Tax
     * Schematron files use {@code <sch:report>} elements (warnings) instead of
     * {@code <sch:assert>} elements (errors). Analysis of all 6 Schematron files shows:
     * <ul>
     *   <li>TaxInvoice: 0 assert, 230 report</li>
     *   <li>Receipt: 0 assert, 233 report</li>
     *   <li>DebitCreditNote: 0 assert, 211 report</li>
     *   <li>AbbreviatedTaxInvoice: 0 assert, 53 report</li>
     *   <li>Invoice: 0 assert, 11 report</li>
     *   <li>CancellationNote: 0 assert, 9 report</li>
     * </ul>
     * <p>
     * This method is retained for future compatibility if Schematron files are updated
     * to include assertions, or for use with custom Schematron files.
     *
     * @param obj the FailedAssert object from SVRL output
     * @return a SchematronError with ERROR level
     */
    private SchematronError extractErrorFromObject(Object obj) {
        try {
            // Use reflection to get the values we need
            String ruleId = (String) obj.getClass().getMethod("getId").invoke(obj);
            String location = (String) obj.getClass().getMethod("getLocation").invoke(obj);
            String testExpression = (String) obj.getClass().getMethod("getTest").invoke(obj);

            // Get text content - the method might return a TextType directly
            String message = "";
            try {
                Object textObj = obj.getClass().getMethod("getText").invoke(obj);
                if (textObj != null) {
                    // Try different methods to get the text value
                    try {
                        message = (String) textObj.getClass().getMethod("getValue").invoke(textObj);
                    } catch (NoSuchMethodException e) {
                        // TextType might have a different method
                        message = textObj.toString();
                    }
                }
            } catch (Exception e) {
                log.trace("Failed to extract text using getText method: {}", e.getMessage());
                // Fallback to empty message
            }

            return new SchematronError(ruleId, message, location, SchematronError.ErrorLevel.ERROR, testExpression);
        } catch (Exception e) {
            log.warn("Failed to extract error from object: {}", e.getMessage(), e);
            return new SchematronError("UNKNOWN", "Failed to extract error details", "", SchematronError.ErrorLevel.ERROR, "");
        }
    }

    /**
     * Extract warning information from a successful report object using reflection
     */
    private SchematronError extractWarningFromObject(Object obj) {
        try {
            // Use reflection to get the values we need
            String ruleId = (String) obj.getClass().getMethod("getId").invoke(obj);
            String location = (String) obj.getClass().getMethod("getLocation").invoke(obj);
            String testExpression = (String) obj.getClass().getMethod("getTest").invoke(obj);

            // Get text content - the method might return a TextType directly
            String message = "";
            try {
                Object textObj = obj.getClass().getMethod("getText").invoke(obj);
                if (textObj != null) {
                    // Try different methods to get the text value
                    try {
                        message = (String) textObj.getClass().getMethod("getValue").invoke(textObj);
                    } catch (NoSuchMethodException e) {
                        // TextType might have a different method
                        message = textObj.toString();
                    }
                }
            } catch (Exception e) {
                log.trace("Failed to extract text using getText method: {}", e.getMessage());
                // Fallback to empty message
            }

            return new SchematronError(ruleId, message, location, SchematronError.ErrorLevel.WARNING, testExpression);
        } catch (Exception e) {
            log.warn("Failed to extract warning from object: {}", e.getMessage(), e);
            return new SchematronError("UNKNOWN", "Failed to extract warning details", "", SchematronError.ErrorLevel.WARNING, "");
        }
    }
}
