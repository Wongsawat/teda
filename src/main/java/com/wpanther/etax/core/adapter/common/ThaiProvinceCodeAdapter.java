package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.ThaiProvinceCodeRepository;
import com.wpanther.etax.core.entity.ThaiProvinceCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed ThaiProvinceCode entities
 *
 * This adapter:
 * - Marshals ThaiProvinceCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ThaiProvinceCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 */
@Component
public class ThaiProvinceCodeAdapter extends XmlAdapter<String, ThaiProvinceCode> {

    private static final Logger log = LoggerFactory.getLogger(ThaiProvinceCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ThaiProvinceCodeRepository repository;

    @Autowired
    public void setRepository(ThaiProvinceCodeRepository repository) {
        ThaiProvinceCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ThaiProvinceCode entity to XML String (province code)
     *
     * @param entity The ThaiProvinceCode entity
     * @return The province code string, or null if entity is null
     */
    @Override
    public String marshal(ThaiProvinceCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        log.debug("Marshalling ThaiProvinceCode: {} ({}) -> {}", entity.getNameTh(), entity.getNameEn(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (province code) to ThaiProvinceCode entity from database
     *
     * @param code The province code from XML (e.g., "10" for Bangkok)
     * @return ThaiProvinceCode entity from database, or placeholder if not found
     */
    @Override
    public ThaiProvinceCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Try to fetch from database
        return repository.findByCodeAndActive(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Province code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private ThaiProvinceCode createPlaceholder(String code) {
        ThaiProvinceCode placeholder = new ThaiProvinceCode(code);
        placeholder.setNameTh("Unknown Province: " + code);
        placeholder.setNameEn("Unknown Province: " + code);
        placeholder.setActive(false);
        return placeholder;
    }

    /**
     * Validate if a province code exists in the database
     *
     * @param code The province code
     * @return true if code exists and is active
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCodeAndActive(code.trim());
    }

    /**
     * Get province Thai name from code
     *
     * @param code The province code
     * @return Province Thai name, or null if not found
     */
    public static String getProvinceName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCodeAndActive(code.trim())
                .map(ThaiProvinceCode::getNameTh)
                .orElse(null);
    }
}
