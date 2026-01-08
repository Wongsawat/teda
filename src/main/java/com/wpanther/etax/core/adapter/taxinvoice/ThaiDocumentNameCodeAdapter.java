package com.wpanther.etax.core.adapter.taxinvoice;

import com.wpanther.etax.core.repository.ThaiDocumentNameCodeRepository;
import com.wpanther.etax.core.entity.ThaiDocumentNameCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed ThaiDocumentNameCode entities
 *
 * This adapter:
 * - Marshals ThaiDocumentNameCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ThaiDocumentNameCode entities from database
 * - Maintains full JAXB namespace compatibility with ETDA e-Tax Invoice schema
 * - Handles missing codes gracefully by creating placeholder entities
 */
@Component
public class ThaiDocumentNameCodeAdapter extends XmlAdapter<String, ThaiDocumentNameCode> {

    private static final Logger log = LoggerFactory.getLogger(ThaiDocumentNameCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ThaiDocumentNameCodeRepository repository;

    @Autowired
    public void setRepository(ThaiDocumentNameCodeRepository repository) {
        ThaiDocumentNameCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ThaiDocumentNameCode entity to XML String (document code)
     *
     * @param entity The ThaiDocumentNameCode entity
     * @return The document code string (80, 81, 388, T01-T07, etc.), or null if entity is null
     */
    @Override
    public String marshal(ThaiDocumentNameCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling ThaiDocumentNameCode: {} ({}) -> {}",
                entity.getNameEn(), entity.getNameTh(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (document code) to ThaiDocumentNameCode entity from database
     *
     * @param code The document code from XML (80, 81, 388, T01-T07, etc.)
     * @return ThaiDocumentNameCode entity from database, or placeholder if not found
     */
    @Override
    public ThaiDocumentNameCode unmarshal(String code) throws Exception {
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
                    log.warn("Document code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private ThaiDocumentNameCode createPlaceholder(String code) {
        ThaiDocumentNameCode placeholder = new ThaiDocumentNameCode(code);
        placeholder.setNameEn("Unknown Document Type: " + code);
        placeholder.setNameTh("ประเภทเอกสารไม่ทราบ: " + code);
        placeholder.setDescription("Placeholder for unknown document code");
        placeholder.setStandardCode(false);
        placeholder.setThaiExtension(false);
        return placeholder;
    }

    /**
     * Validate if a document code exists in the database
     *
     * @param code The document code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get document name in English from code
     *
     * @param code The document code
     * @return Document name in English, or null if not found
     */
    public static String getEnglishName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ThaiDocumentNameCode::getNameEn)
                .orElse(null);
    }

    /**
     * Get document name in Thai from code
     *
     * @param code The document code
     * @return Document name in Thai, or null if not found
     */
    public static String getThaiName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ThaiDocumentNameCode::getNameTh)
                .orElse(null);
    }

    /**
     * Check if code is a standard UN/CEFACT code
     *
     * @param code The document code
     * @return true if it's a standard code (80, 81, 82, 380, 388)
     */
    public static boolean isStandardCode(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(ThaiDocumentNameCode::getStandardCode)
                .orElse(false);
    }

    /**
     * Check if code is a Thai extension
     *
     * @param code The document code
     * @return true if it's a Thai extension code (T01-T07)
     */
    public static boolean isThaiExtension(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(ThaiDocumentNameCode::getThaiExtension)
                .orElse(false);
    }

    /**
     * Check if code represents a tax invoice type
     *
     * @param code The document code
     * @return true if it's a tax invoice type
     */
    public static boolean isTaxInvoice(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(ThaiDocumentNameCode::isTaxInvoice)
                .orElse(false);
    }
}
