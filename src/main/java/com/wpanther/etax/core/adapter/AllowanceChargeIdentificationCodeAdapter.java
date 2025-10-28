package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.repository.AllowanceChargeIdentificationCodeRepository;
import com.wpanther.etax.core.entity.AllowanceChargeIdentificationCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed AllowanceChargeIdentificationCode entities
 *
 * This adapter:
 * - Marshals AllowanceChargeIdentificationCode entities to their code strings for XML output
 * - Unmarshals XML code strings to AllowanceChargeIdentificationCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 106 codes (100 standard UN/CEFACT + 6 Thai extensions)
 *
 * UN/CEFACT Code List: 5189 (AllowanceChargeID)
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A
 */
@Component
public class AllowanceChargeIdentificationCodeAdapter extends XmlAdapter<String, AllowanceChargeIdentificationCode> {

    private static final Logger log = LoggerFactory.getLogger(AllowanceChargeIdentificationCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static AllowanceChargeIdentificationCodeRepository repository;

    @Autowired
    public void setRepository(AllowanceChargeIdentificationCodeRepository repository) {
        AllowanceChargeIdentificationCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert AllowanceChargeIdentificationCode entity to XML String (code)
     *
     * @param entity The AllowanceChargeIdentificationCode entity
     * @return The allowance/charge identification code string, or null if entity is null
     */
    @Override
    public String marshal(AllowanceChargeIdentificationCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling AllowanceChargeIdentificationCode: {} ({}) -> {}",
                  entity.getName(), entity.getCategory(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to AllowanceChargeIdentificationCode entity from database
     *
     * @param code The allowance/charge identification code from XML (e.g., 1, 30, 79, PP001, etc.)
     * @return AllowanceChargeIdentificationCode entity from database, or placeholder if not found
     */
    @Override
    public AllowanceChargeIdentificationCode unmarshal(String code) throws Exception {
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
                    log.warn("Allowance/charge identification code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private AllowanceChargeIdentificationCode createPlaceholder(String code) {
        AllowanceChargeIdentificationCode placeholder = new AllowanceChargeIdentificationCode(code);
        placeholder.setName("Unknown Allowance/Charge ID: " + code);
        placeholder.setDescription("Placeholder for unknown allowance/charge identification code");
        placeholder.setCategory("Other");
        placeholder.setIsStandardCode(false);
        placeholder.setIsThaiExtension(code.startsWith("PP"));
        return placeholder;
    }

    /**
     * Validate if an allowance/charge identification code exists in the database
     *
     * @param code The allowance/charge identification code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim().toUpperCase());
    }

    /**
     * Get allowance/charge name from code
     *
     * @param code The allowance/charge identification code
     * @return Name, or null if not found
     */
    public static String getName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::getName)
                .orElse(null);
    }

    /**
     * Get allowance/charge category from code
     *
     * @param code The allowance/charge identification code
     * @return Category, or null if not found
     */
    public static String getCategory(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::getCategory)
                .orElse(null);
    }

    /**
     * Get allowance/charge description from code
     *
     * @param code The allowance/charge identification code
     * @return Description, or null if not found
     */
    public static String getDescription(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::getDescription)
                .orElse(null);
    }

    /**
     * Check if code is a documentary credit commission
     *
     * @param code The allowance/charge identification code
     * @return true if documentary credit commission
     */
    public static boolean isDocumentaryCreditCommission(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isDocumentaryCreditCommission)
                .orElse(false);
    }

    /**
     * Check if code is a collection commission
     *
     * @param code The allowance/charge identification code
     * @return true if collection commission
     */
    public static boolean isCollectionCommission(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isCollectionCommission)
                .orElse(false);
    }

    /**
     * Check if code is a processing fee
     *
     * @param code The allowance/charge identification code
     * @return true if processing fee
     */
    public static boolean isProcessingFee(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isProcessingFee)
                .orElse(false);
    }

    /**
     * Check if code is a discount
     *
     * @param code The allowance/charge identification code
     * @return true if discount
     */
    public static boolean isDiscount(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isDiscount)
                .orElse(false);
    }

    /**
     * Check if code is a penalty
     *
     * @param code The allowance/charge identification code
     * @return true if penalty
     */
    public static boolean isPenalty(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isPenalty)
                .orElse(false);
    }

    /**
     * Check if code is a bonus
     *
     * @param code The allowance/charge identification code
     * @return true if bonus
     */
    public static boolean isBonus(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isBonus)
                .orElse(false);
    }

    /**
     * Check if code is freight charges
     *
     * @param code The allowance/charge identification code
     * @return true if freight charges
     */
    public static boolean isFreightCharges(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isFreightCharges)
                .orElse(false);
    }

    /**
     * Check if code is a standard UN/CEFACT code
     *
     * @param code The allowance/charge identification code
     * @return true if standard code
     */
    public static boolean isStandardCode(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isStandardCode)
                .orElse(false);
    }

    /**
     * Check if code is a Thai extension
     *
     * @param code The allowance/charge identification code
     * @return true if Thai extension
     */
    public static boolean isThaiExtension(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isThaiExtension)
                .orElse(false);
    }

    /**
     * Check if code is a commission
     *
     * @param code The allowance/charge identification code
     * @return true if commission
     */
    public static boolean isCommission(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isCommission)
                .orElse(false);
    }

    /**
     * Check if code is a charge
     *
     * @param code The allowance/charge identification code
     * @return true if charge
     */
    public static boolean isCharge(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isCharge)
                .orElse(false);
    }

    /**
     * Check if code is an allowance
     *
     * @param code The allowance/charge identification code
     * @return true if allowance
     */
    public static boolean isAllowance(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(AllowanceChargeIdentificationCode::isAllowance)
                .orElse(false);
    }
}
