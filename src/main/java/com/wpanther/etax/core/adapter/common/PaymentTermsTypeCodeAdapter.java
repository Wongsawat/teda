package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.PaymentTermsTypeCodeRepository;
import com.wpanther.etax.core.entity.PaymentTermsTypeCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed PaymentTermsTypeCode entities
 *
 * This adapter:
 * - Marshals PaymentTermsTypeCode entities to their code strings for XML output
 * - Unmarshals XML code strings to PaymentTermsTypeCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 79 UN/CEFACT Payment Terms Type Codes (1-78 + ZZZ)
 *
 * UN/CEFACT Code List: 64279
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A
 */
@Component
public class PaymentTermsTypeCodeAdapter extends XmlAdapter<String, PaymentTermsTypeCode> {

    private static final Logger log = LoggerFactory.getLogger(PaymentTermsTypeCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static PaymentTermsTypeCodeRepository repository;

    @Autowired
    public void setRepository(PaymentTermsTypeCodeRepository repository) {
        PaymentTermsTypeCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert PaymentTermsTypeCode entity to XML String (payment terms type code)
     *
     * @param entity The PaymentTermsTypeCode entity
     * @return The payment terms type code string, or null if entity is null
     */
    @Override
    public String marshal(PaymentTermsTypeCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling PaymentTermsTypeCode: {} ({}) -> {}",
                  entity.getName(), entity.getCategory(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (payment terms type code) to PaymentTermsTypeCode entity from database
     *
     * @param code The payment terms type code from XML (e.g., 1, 10, 22, ZZZ)
     * @return PaymentTermsTypeCode entity from database, or placeholder if not found
     */
    @Override
    public PaymentTermsTypeCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String upperCode = code.trim().toUpperCase();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", upperCode);
            return createPlaceholder(upperCode);
        }

        // Try to fetch from database
        return repository.findByCode(upperCode)
                .orElseGet(() -> {
                    log.warn("Payment terms type code '{}' not found in database, creating placeholder", upperCode);
                    return createPlaceholder(upperCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private PaymentTermsTypeCode createPlaceholder(String code) {
        PaymentTermsTypeCode placeholder = new PaymentTermsTypeCode(code);
        placeholder.setName("Unknown Payment Terms: " + code);
        placeholder.setDescription("Placeholder for unknown payment terms type code");
        placeholder.setCategory("Unknown");
        return placeholder;
    }

    /**
     * Validate if a payment terms type code exists in the database
     *
     * @param code The payment terms type code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim().toUpperCase());
    }

    /**
     * Get payment terms type name from code
     *
     * @param code The payment terms type code
     * @return Payment terms type name, or null if not found
     */
    public static String getPaymentTermsName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(PaymentTermsTypeCode::getName)
                .orElse(null);
    }

    /**
     * Get payment terms type category from code
     *
     * @param code The payment terms type code
     * @return Payment terms category, or null if not found
     */
    public static String getPaymentTermsCategory(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(PaymentTermsTypeCode::getCategory)
                .orElse(null);
    }

    /**
     * Check if a code represents immediate payment terms
     *
     * @param code The payment terms type code
     * @return true if payment is immediate
     */
    public static boolean isImmediatePayment(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(PaymentTermsTypeCode::isImmediate)
                .orElse(false);
    }

    /**
     * Check if a code represents deferred payment terms
     *
     * @param code The payment terms type code
     * @return true if payment is deferred
     */
    public static boolean isDeferredPayment(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(PaymentTermsTypeCode::isDeferred)
                .orElse(false);
    }

    /**
     * Check if a code offers discount terms
     *
     * @param code The payment terms type code
     * @return true if discount is available
     */
    public static boolean hasDiscount(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(PaymentTermsTypeCode::hasDiscount)
                .orElse(false);
    }
}
