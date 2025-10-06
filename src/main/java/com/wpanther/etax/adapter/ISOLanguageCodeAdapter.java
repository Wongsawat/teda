package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.ISOLanguageCode;
import com.wpanther.etax.repository.ISOLanguageCodeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed ISOLanguageCode entities
 *
 * This adapter:
 * - Marshals ISOLanguageCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ISOLanguageCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles case-insensitively (both 'th' and 'TH' work)
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 185 ISO 639-1 language codes
 *
 * Standard: ISO 639-1 alpha-2
 * Namespace: urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27
 */
@Component
public class ISOLanguageCodeAdapter extends XmlAdapter<String, ISOLanguageCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOLanguageCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ISOLanguageCodeRepository repository;

    @Autowired
    public void setRepository(ISOLanguageCodeRepository repository) {
        ISOLanguageCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOLanguageCode entity to XML String (code)
     *
     * @param entity The ISOLanguageCode entity
     * @return The language code string (lowercase), or null if entity is null
     */
    @Override
    public String marshal(ISOLanguageCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling ISOLanguageCode: {} -> {}", entity.getName(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to ISOLanguageCode entity from database
     *
     * @param code The language code from XML (e.g., "th", "TH", "en", "EN")
     * @return ISOLanguageCode entity from database, or placeholder if not found
     */
    @Override
    public ISOLanguageCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Case-insensitive lookup (handles both 'th' and 'TH')
        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Language code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private ISOLanguageCode createPlaceholder(String code) {
        ISOLanguageCode placeholder = new ISOLanguageCode(code);
        placeholder.setName("Unknown Language: " + code);
        placeholder.setIsActive(false);
        return placeholder;
    }

    /**
     * Validate if a language code exists in the database
     *
     * @param code The language code (case-insensitive)
     * @return true if code exists and is active
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get language name from code
     *
     * @param code The language code (case-insensitive)
     * @return Language name, or null if not found
     */
    public static String getName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ISOLanguageCode::getName)
                .orElse(null);
    }

    /**
     * Normalize language code to lowercase standard format
     *
     * @param code The language code (any case)
     * @return Lowercase code, or null if input is null
     */
    public static String normalize(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        return code.trim().toLowerCase();
    }

    /**
     * Check if code is Thai language
     *
     * @param code The language code (case-insensitive)
     * @return true if Thai
     */
    public static boolean isThai(String code) {
        return "th".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is English language
     *
     * @param code The language code (case-insensitive)
     * @return true if English
     */
    public static boolean isEnglish(String code) {
        return "en".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is Chinese language
     *
     * @param code The language code (case-insensitive)
     * @return true if Chinese
     */
    public static boolean isChinese(String code) {
        return "zh".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is Japanese language
     *
     * @param code The language code (case-insensitive)
     * @return true if Japanese
     */
    public static boolean isJapanese(String code) {
        return "ja".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is an ASEAN language
     *
     * @param code The language code (case-insensitive)
     * @return true if ASEAN language
     */
    public static boolean isASEANLanguage(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(ISOLanguageCode::isASEANLanguage)
                .orElse(false);
    }

    /**
     * Check if code is a major trading partner language
     *
     * @param code The language code (case-insensitive)
     * @return true if major trading language
     */
    public static boolean isMajorTradingLanguage(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(ISOLanguageCode::isMajorTradingLanguage)
                .orElse(false);
    }
}
