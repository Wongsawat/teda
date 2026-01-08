package com.wpanther.etax.core.adapter.taxinvoice;

import com.wpanther.etax.core.repository.ThaiCategoryCodeRepository;
import com.wpanther.etax.core.entity.ThaiCategoryCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed ThaiCategoryCode entities
 *
 * This adapter:
 * - Marshals ThaiCategoryCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ThaiCategoryCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Handles 2 Thai e-Tax Invoice category codes
 */
@Component
public class ThaiCategoryCodeAdapter extends XmlAdapter<String, ThaiCategoryCode> {

    private static final Logger log = LoggerFactory.getLogger(ThaiCategoryCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ThaiCategoryCodeRepository repository;

    @Autowired
    public void setRepository(ThaiCategoryCodeRepository repository) {
        ThaiCategoryCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ThaiCategoryCode entity to XML String (category code)
     *
     * @param entity The ThaiCategoryCode entity
     * @return The category code string, or null if entity is null
     */
    @Override
    public String marshal(ThaiCategoryCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling ThaiCategoryCode: {} -> {}", entity.getNameEn(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (category code) to ThaiCategoryCode entity from database
     *
     * @param code The category code from XML (e.g., "01", "02")
     * @return ThaiCategoryCode entity from database, or placeholder if not found
     */
    @Override
    public ThaiCategoryCode unmarshal(String code) throws Exception {
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
                    log.warn("Category code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private ThaiCategoryCode createPlaceholder(String code) {
        ThaiCategoryCode placeholder = new ThaiCategoryCode(code);
        placeholder.setNameTh("Unknown Category Code: " + code);
        placeholder.setNameEn("Unknown Category Code: " + code);
        placeholder.setDescription("Placeholder for unknown category code");
        return placeholder;
    }

    /**
     * Validate if a category code exists in the database
     *
     * @param code The category code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get category Thai name from code
     *
     * @param code The category code
     * @return Thai name, or null if not found
     */
    public static String getNameTh(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ThaiCategoryCode::getNameTh)
                .orElse(null);
    }

    /**
     * Get category English name from code
     *
     * @param code The category code
     * @return English name, or null if not found
     */
    public static String getNameEn(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ThaiCategoryCode::getNameEn)
                .orElse(null);
    }

    /**
     * Check if code is 01 - Original document reference (cancellation/debit/credit)
     *
     * @param code The category code
     * @return true if code is 01
     */
    public static boolean isOriginalDocumentReference(String code) {
        return "01".equals(code != null ? code.trim() : null);
    }

    /**
     * Check if code is 02 - Advance payment reference
     *
     * @param code The category code
     * @return true if code is 02
     */
    public static boolean isAdvancePaymentReference(String code) {
        return "02".equals(code != null ? code.trim() : null);
    }
}
