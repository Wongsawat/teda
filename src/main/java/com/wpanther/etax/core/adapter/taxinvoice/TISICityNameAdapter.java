package com.wpanther.etax.core.adapter.taxinvoice;

import com.wpanther.etax.core.repository.TISICityNameRepository;
import com.wpanther.etax.core.entity.TISICityName;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed TISICityName entities
 *
 * This adapter:
 * - Marshals TISICityName entities to their code strings for XML output
 * - Unmarshals XML code strings to TISICityName entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 */
@Component
public class TISICityNameAdapter extends XmlAdapter<String, TISICityName> {

    private static final Logger log = LoggerFactory.getLogger(TISICityNameAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static TISICityNameRepository repository;

    @Autowired
    public void setRepository(TISICityNameRepository repository) {
        TISICityNameAdapter.repository = repository;
    }

    /**
     * Marshal: Convert TISICityName entity to XML String (city code)
     *
     * @param entity The TISICityName entity
     * @return The city code string, or null if entity is null
     */
    @Override
    public String marshal(TISICityName entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling TISICityName: {} -> {}", entity.getNameTh(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (city code) to TISICityName entity from database
     *
     * @param code The city code from XML (e.g., "1001" for Phra Nakhon)
     * @return TISICityName entity from database, or placeholder if not found
     */
    @Override
    public TISICityName unmarshal(String code) throws Exception {
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
                    log.warn("City code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private TISICityName createPlaceholder(String code) {
        TISICityName placeholder = new TISICityName(code);
        placeholder.setNameTh("Unknown City: " + code);
        return placeholder;
    }

    /**
     * Validate if a city code exists in the database
     *
     * @param code The city code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get city Thai name from code
     *
     * @param code The city code
     * @return City Thai name, or null if not found
     */
    public static String getCityName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(TISICityName::getNameTh)
                .orElse(null);
    }
}
