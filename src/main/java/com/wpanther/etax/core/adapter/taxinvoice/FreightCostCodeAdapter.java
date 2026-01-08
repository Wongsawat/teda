package com.wpanther.etax.core.adapter.taxinvoice;

import com.wpanther.etax.core.repository.FreightCostCodeRepository;
import com.wpanther.etax.core.entity.FreightCostCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed FreightCostCode entities
 *
 * This adapter:
 * - Marshals FreightCostCode entities to their code strings for XML output
 * - Unmarshals XML code strings to FreightCostCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 1,641 UN/CEFACT freight cost codes
 *
 * UN/CEFACT: Recommendation 23, Version 4
 * Namespace: urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4
 */
@Component
public class FreightCostCodeAdapter extends XmlAdapter<String, FreightCostCode> {

    private static final Logger log = LoggerFactory.getLogger(FreightCostCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static FreightCostCodeRepository repository;

    @Autowired
    public void setRepository(FreightCostCodeRepository repository) {
        FreightCostCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert FreightCostCode entity to XML String (code)
     *
     * @param entity The FreightCostCode entity
     * @return The freight cost code string, or null if entity is null
     */
    @Override
    public String marshal(FreightCostCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling FreightCostCode: {} ({}) -> {}",
                  entity.getName(), entity.getCategory(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to FreightCostCode entity from database
     *
     * @param code The freight cost code from XML (e.g., 100000, 101000, etc.)
     * @return FreightCostCode entity from database, or placeholder if not found
     */
    @Override
    public FreightCostCode unmarshal(String code) throws Exception {
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
                    log.warn("Freight cost code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private FreightCostCode createPlaceholder(String code) {
        FreightCostCode placeholder = new FreightCostCode(code);
        placeholder.setName("Unknown Freight Cost Code: " + code);
        placeholder.setCategory("Unknown");
        return placeholder;
    }

    /**
     * Validate if a freight cost code exists in the database
     *
     * @param code The freight cost code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get freight cost name from code
     *
     * @param code The freight cost code
     * @return Freight cost name, or null if not found
     */
    public static String getFreightCostName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::getName)
                .orElse(null);
    }

    /**
     * Get freight cost category from code
     *
     * @param code The freight cost code
     * @return Freight cost category, or null if not found
     */
    public static String getFreightCostCategory(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::getCategory)
                .orElse(null);
    }

    /**
     * Get code group (first 3 digits) from code
     *
     * @param code The freight cost code
     * @return Code group, or null if not found
     */
    public static String getCodeGroup(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::getCodeGroup)
                .orElse(null);
    }

    /**
     * Check if a code is basic freight
     *
     * @param code The freight cost code
     * @return true if basic freight
     */
    public static boolean isBasicFreight(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isBasicFreight)
                .orElse(false);
    }

    /**
     * Check if a code is freight surcharge
     *
     * @param code The freight cost code
     * @return true if freight surcharge
     */
    public static boolean isFreightSurcharge(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isFreightSurcharge)
                .orElse(false);
    }

    /**
     * Check if a code is container service
     *
     * @param code The freight cost code
     * @return true if container service
     */
    public static boolean isContainerService(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isContainerService)
                .orElse(false);
    }

    /**
     * Check if a code is terminal charge
     *
     * @param code The freight cost code
     * @return true if terminal charge
     */
    public static boolean isTerminalCharge(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isTerminalCharge)
                .orElse(false);
    }

    /**
     * Check if a code is handling charge
     *
     * @param code The freight cost code
     * @return true if handling charge
     */
    public static boolean isHandlingCharge(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isHandlingCharge)
                .orElse(false);
    }

    /**
     * Check if a code is storage or demurrage
     *
     * @param code The freight cost code
     * @return true if storage or demurrage
     */
    public static boolean isStorageOrDemurrage(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isStorageOrDemurrage)
                .orElse(false);
    }

    /**
     * Check if a code is customs or documentation
     *
     * @param code The freight cost code
     * @return true if customs or documentation
     */
    public static boolean isCustomsOrDocumentation(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isCustomsOrDocumentation)
                .orElse(false);
    }

    /**
     * Check if a code is dangerous goods
     *
     * @param code The freight cost code
     * @return true if dangerous goods
     */
    public static boolean isDangerousGoods(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isDangerousGoods)
                .orElse(false);
    }

    /**
     * Check if a code is special freight
     *
     * @param code The freight cost code
     * @return true if special freight
     */
    public static boolean isSpecialFreight(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isSpecialFreight)
                .orElse(false);
    }

    /**
     * Check if a code is insurance
     *
     * @param code The freight cost code
     * @return true if insurance
     */
    public static boolean isInsurance(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(FreightCostCode::isInsurance)
                .orElse(false);
    }
}
