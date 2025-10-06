package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.AllowanceChargeReasonCode;
import com.wpanther.etax.repository.AllowanceChargeReasonCodeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed AllowanceChargeReasonCode entities
 *
 * This adapter:
 * - Marshals AllowanceChargeReasonCode entities to their code strings for XML output
 * - Unmarshals XML code strings to AllowanceChargeReasonCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 105 UN/CEFACT allowance charge reason codes
 *
 * UN/CEFACT Code List: 64465
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B
 */
@Component
public class AllowanceChargeReasonCodeAdapter extends XmlAdapter<String, AllowanceChargeReasonCode> {

    private static final Logger log = LoggerFactory.getLogger(AllowanceChargeReasonCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static AllowanceChargeReasonCodeRepository repository;

    @Autowired
    public void setRepository(AllowanceChargeReasonCodeRepository repository) {
        AllowanceChargeReasonCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert AllowanceChargeReasonCode entity to XML String (code)
     *
     * @param entity The AllowanceChargeReasonCode entity
     * @return The allowance charge reason code string, or null if entity is null
     */
    @Override
    public String marshal(AllowanceChargeReasonCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling AllowanceChargeReasonCode: {} ({}) -> {}",
                  entity.getName(), entity.getCategory(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to AllowanceChargeReasonCode entity from database
     *
     * @param code The allowance charge reason code from XML (e.g., 1, 3, 19, ZZZ, etc.)
     * @return AllowanceChargeReasonCode entity from database, or placeholder if not found
     */
    @Override
    public AllowanceChargeReasonCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim().toUpperCase();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Try to fetch from database
        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Allowance charge reason code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private AllowanceChargeReasonCode createPlaceholder(String code) {
        AllowanceChargeReasonCode placeholder = new AllowanceChargeReasonCode(code);
        placeholder.setName("Unknown Allowance/Charge Reason: " + code);
        placeholder.setDescription("Placeholder for unknown allowance charge reason code");
        placeholder.setCategory("Other");
        return placeholder;
    }

    /**
     * Validate if an allowance charge reason code exists in the database
     *
     * @param code The allowance charge reason code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim().toUpperCase());
    }

    /**
     * Get allowance charge reason name from code
     *
     * @param code The allowance charge reason code
     * @return Reason name, or null if not found
     */
    public static String getReasonName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::getName)
                .orElse(null);
    }

    /**
     * Get allowance charge reason category from code
     *
     * @param code The allowance charge reason code
     * @return Reason category, or null if not found
     */
    public static String getReasonCategory(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::getCategory)
                .orElse(null);
    }

    /**
     * Get allowance charge reason description from code
     *
     * @param code The allowance charge reason code
     * @return Reason description, or null if not found
     */
    public static String getReasonDescription(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::getDescription)
                .orElse(null);
    }

    /**
     * Check if code is quality issue
     *
     * @param code The allowance charge reason code
     * @return true if quality issue
     */
    public static boolean isQualityIssue(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isQualityIssue)
                .orElse(false);
    }

    /**
     * Check if code is delivery issue
     *
     * @param code The allowance charge reason code
     * @return true if delivery issue
     */
    public static boolean isDeliveryIssue(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isDeliveryIssue)
                .orElse(false);
    }

    /**
     * Check if code is administrative error
     *
     * @param code The allowance charge reason code
     * @return true if administrative error
     */
    public static boolean isAdministrativeError(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isAdministrativeError)
                .orElse(false);
    }

    /**
     * Check if code is discount or allowance
     *
     * @param code The allowance charge reason code
     * @return true if discount or allowance
     */
    public static boolean isDiscountOrAllowance(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isDiscountOrAllowance)
                .orElse(false);
    }

    /**
     * Check if code is financial charge
     *
     * @param code The allowance charge reason code
     * @return true if financial charge
     */
    public static boolean isFinancialCharge(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isFinancialCharge)
                .orElse(false);
    }

    /**
     * Check if code is claim or dispute
     *
     * @param code The allowance charge reason code
     * @return true if claim or dispute
     */
    public static boolean isClaimOrDispute(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isClaimOrDispute)
                .orElse(false);
    }

    /**
     * Check if code is freight or logistics
     *
     * @param code The allowance charge reason code
     * @return true if freight or logistics
     */
    public static boolean isFreightOrLogistics(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isFreightOrLogistics)
                .orElse(false);
    }

    /**
     * Check if code is payment terms
     *
     * @param code The allowance charge reason code
     * @return true if payment terms
     */
    public static boolean isPaymentTerms(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isPaymentTerms)
                .orElse(false);
    }

    /**
     * Check if code is HR related
     *
     * @param code The allowance charge reason code
     * @return true if HR related
     */
    public static boolean isHRRelated(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isHRRelated)
                .orElse(false);
    }

    /**
     * Check if code is mutually defined (ZZZ)
     *
     * @param code The allowance charge reason code
     * @return true if mutually defined
     */
    public static boolean isMutuallyDefined(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeReasonCode::isMutuallyDefined)
                .orElse(false);
    }
}
