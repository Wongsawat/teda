package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.TISISubdistrictRepository;
import com.wpanther.etax.core.entity.TISISubdistrict;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed TISISubdistrict entities
 *
 * This adapter:
 * - Marshals TISISubdistrict entities to their code strings for XML output
 * - Unmarshals XML code strings to TISISubdistrict entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Handles 8,940+ subdistrict codes efficiently
 */
@Component
public class TISISubdistrictAdapter extends XmlAdapter<String, TISISubdistrict> {

    private static final Logger log = LoggerFactory.getLogger(TISISubdistrictAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static TISISubdistrictRepository repository;

    @Autowired
    public void setRepository(TISISubdistrictRepository repository) {
        TISISubdistrictAdapter.repository = repository;
    }

    /**
     * Marshal: Convert TISISubdistrict entity to XML String (subdistrict code)
     *
     * @param entity The TISISubdistrict entity
     * @return The subdistrict code string, or null if entity is null
     */
    @Override
    public String marshal(TISISubdistrict entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        log.debug("Marshalling TISISubdistrict: {} -> {}", entity.getNameTh(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (subdistrict code) to TISISubdistrict entity from database
     *
     * @param code The subdistrict code from XML (e.g., "100101" for Phra Borom Maha Ratchawang)
     * @return TISISubdistrict entity from database, or placeholder if not found
     */
    @Override
    public TISISubdistrict unmarshal(String code) throws Exception {
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
                    log.warn("Subdistrict code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private TISISubdistrict createPlaceholder(String code) {
        TISISubdistrict placeholder = new TISISubdistrict(code);
        placeholder.setNameTh("Unknown Subdistrict: " + code);
        return placeholder;
    }

    /**
     * Validate if a subdistrict code exists in the database
     *
     * @param code The subdistrict code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get subdistrict Thai name from code
     *
     * @param code The subdistrict code
     * @return Subdistrict Thai name, or null if not found
     */
    public static String getSubdistrictName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(TISISubdistrict::getNameTh)
                .orElse(null);
    }
}
