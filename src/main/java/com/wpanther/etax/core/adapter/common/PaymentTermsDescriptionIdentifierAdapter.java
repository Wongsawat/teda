package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.PaymentTermsDescriptionIdentifierRepository;
import com.wpanther.etax.core.entity.PaymentTermsDescriptionIdentifier;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed PaymentTermsDescriptionIdentifier entities
 *
 * This adapter:
 * - Marshals PaymentTermsDescriptionIdentifier entities to their code strings for XML output
 * - Unmarshals XML code strings to PaymentTermsDescriptionIdentifier entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 7 UN/CEFACT payment terms description identifiers (1-7)
 *
 * UN/CEFACT Code List: 64277
 * Namespace: urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A
 */
@Component
public class PaymentTermsDescriptionIdentifierAdapter extends XmlAdapter<String, PaymentTermsDescriptionIdentifier> {

    private static final Logger log = LoggerFactory.getLogger(PaymentTermsDescriptionIdentifierAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static PaymentTermsDescriptionIdentifierRepository repository;

    @Autowired
    public void setRepository(PaymentTermsDescriptionIdentifierRepository repository) {
        PaymentTermsDescriptionIdentifierAdapter.repository = repository;
    }

    /**
     * Marshal: Convert PaymentTermsDescriptionIdentifier entity to XML String (code)
     *
     * @param entity The PaymentTermsDescriptionIdentifier entity
     * @return The payment terms description identifier code string, or null if entity is null
     */
    @Override
    public String marshal(PaymentTermsDescriptionIdentifier entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        log.debug("Marshalling PaymentTermsDescriptionIdentifier: {} -> {}", entity.getName(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to PaymentTermsDescriptionIdentifier entity from database
     *
     * @param code The payment terms description identifier code from XML (e.g., 1, 2, 6)
     * @return PaymentTermsDescriptionIdentifier entity from database, or placeholder if not found
     */
    @Override
    public PaymentTermsDescriptionIdentifier unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Try to fetch from database
        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Payment terms description identifier '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private PaymentTermsDescriptionIdentifier createPlaceholder(String code) {
        PaymentTermsDescriptionIdentifier placeholder = new PaymentTermsDescriptionIdentifier(code);
        placeholder.setName("Unknown Payment Terms Description: " + code);
        placeholder.setDescription("Placeholder for unknown payment terms description identifier");
        placeholder.setIsDraftRequired(false);
        return placeholder;
    }

    /**
     * Validate if a payment terms description identifier exists in the database
     *
     * @param code The payment terms description identifier code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get payment terms description name from code
     *
     * @param code The payment terms description identifier code
     * @return Payment terms description name, or null if not found
     */
    public static String getPaymentTermsDescriptionName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(PaymentTermsDescriptionIdentifier::getName)
                .orElse(null);
    }

    /**
     * Check if a code requires draft
     *
     * @param code The payment terms description identifier code
     * @return true if draft is required
     */
    public static boolean isDraftRequired(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(PaymentTermsDescriptionIdentifier::isDraftRequired)
                .orElse(false);
    }

    /**
     * Check if a code is for banking draft (codes 1-3)
     *
     * @param code The payment terms description identifier code
     * @return true if banking draft
     */
    public static boolean isBankingDraft(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(PaymentTermsDescriptionIdentifier::isBankingDraft)
                .orElse(false);
    }

    /**
     * Check if code is for issuing bank draft (code 1)
     *
     * @param code The payment terms description identifier code
     * @return true if issuing bank draft
     */
    public static boolean isIssuingBankDraft(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(PaymentTermsDescriptionIdentifier::isIssuingBank)
                .orElse(false);
    }

    /**
     * Check if code is for no draft (code 6)
     *
     * @param code The payment terms description identifier code
     * @return true if no draft
     */
    public static boolean isNoDraft(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(PaymentTermsDescriptionIdentifier::isNoDraft)
                .orElse(false);
    }
}
