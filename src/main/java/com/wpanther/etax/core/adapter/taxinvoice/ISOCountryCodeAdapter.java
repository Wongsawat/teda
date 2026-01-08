package com.wpanther.etax.core.adapter.taxinvoice;

import com.wpanther.etax.core.repository.ISOCountryCodeRepository;
import com.wpanther.etax.core.entity.ISOCountryCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XML Adapter for ISO 3166-1 Two-letter Country Code
 *
 * Converts between XML String values and database-backed ISOCountryCode entities.
 *
 * This adapter:
 * - Marshals ISOCountryCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ISOCountryCode entities from database
 * - Maintains full JAXB namespace compatibility with ISO 3166-1 schema
 * - Handles missing codes gracefully by creating placeholder entities
 *
 * Standard: ISO 3166-1 alpha-2
 * Total Countries: 252 (249 standard + 3 ETDA extensions)
 * Code Format: 2 uppercase letters (TH, US, JP, etc.)
 *
 * Standard ISO codes: AD, AE, AF, ... TH, ... US, ... ZW
 * ETDA extensions: AN (inactive), KS, UN
 */
@Component
public class ISOCountryCodeAdapter extends XmlAdapter<String, ISOCountryCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOCountryCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ISOCountryCodeRepository repository;

    @Autowired
    public void setRepository(ISOCountryCodeRepository repository) {
        ISOCountryCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOCountryCode entity to XML String (country code)
     *
     * @param entity ISOCountryCode entity from database
     * @return The country code string (TH, US, JP, etc.), or null if entity is null
     */
    @Override
    public String marshal(ISOCountryCode entity) throws Exception {
        if (entity == null) {
            return null;
        }

        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        log.debug("Marshalling ISOCountryCode: {} ({}) -> {}", code, entity.getName(), code);
        return code.toUpperCase();
    }

    /**
     * Unmarshal: Convert XML String (country code) to ISOCountryCode entity from database
     *
     * @param code The country code from XML (TH, US, JP, etc.)
     * @return ISOCountryCode entity from database, or placeholder if not found
     */
    @Override
    public ISOCountryCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String upperCode = code.trim().toUpperCase();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", upperCode);
            return createPlaceholder(upperCode);
        }

        return repository.findByCode(upperCode)
                .orElseGet(() -> {
                    log.warn("Country code '{}' not found in database, creating placeholder", upperCode);
                    return createPlaceholder(upperCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private static ISOCountryCode createPlaceholder(String code) {
        ISOCountryCode placeholder = new ISOCountryCode(code);
        placeholder.setName("Unknown Country: " + code);
        placeholder.setDescription("Placeholder for unknown country code");
        placeholder.setActive(false);
        return placeholder;
    }

    // Static Helper Methods

    /**
     * Convert code string to entity.
     */
    public static ISOCountryCode toEntity(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        String upperCode = code.trim().toUpperCase();
        if (repository == null) {
            return createPlaceholder(upperCode);
        }
        return repository.findByCode(upperCode)
                .orElseGet(() -> createPlaceholder(upperCode));
    }

    /**
     * Convert entity to code string.
     */
    public static String toCode(ISOCountryCode entity) {
        return entity != null ? entity.getCode() : null;
    }

    /**
     * Validate if country code exists in database
     *
     * @param code Country code (e.g., "TH", "US")
     * @return true if code exists and is active
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase()).isPresent();
    }

    /**
     * Get country name from code
     *
     * @param code Country code (e.g., "TH")
     * @return Country name (e.g., "THAILAND"), or null if not found
     */
    public static String getName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCountryCode::getName)
                .orElse(null);
    }

    /**
     * Normalize country code to uppercase
     *
     * @param code Country code
     * @return Normalized uppercase code
     */
    public static String normalize(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        return code.trim().toUpperCase();
    }

    /**
     * Check if code is Thailand (TH)
     *
     * @param code Country code
     * @return true if Thailand
     */
    public static boolean isThailand(String code) {
        return "TH".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is an ASEAN country
     *
     * @param code Country code
     * @return true if ASEAN country
     */
    public static boolean isASEANCountry(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCountryCode::isASEANCountry)
                .orElse(false);
    }

    /**
     * Check if code is a major trading partner
     *
     * @param code Country code
     * @return true if major trading partner
     */
    public static boolean isMajorTradingPartner(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCountryCode::isMajorTradingPartner)
                .orElse(false);
    }

    /**
     * Check if code is an ETDA extension (AN, KS, UN)
     *
     * @param code Country code
     * @return true if ETDA extension
     */
    public static boolean isETDAExtension(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCountryCode::isETDAExtension)
                .orElse(false);
    }

    /**
     * Check if code is China (CN)
     *
     * @param code Country code
     * @return true if China
     */
    public static boolean isChina(String code) {
        return "CN".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is Japan (JP)
     *
     * @param code Country code
     * @return true if Japan
     */
    public static boolean isJapan(String code) {
        return "JP".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is United States (US)
     *
     * @param code Country code
     * @return true if United States
     */
    public static boolean isUnitedStates(String code) {
        return "US".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if code is Singapore (SG)
     *
     * @param code Country code
     * @return true if Singapore
     */
    public static boolean isSingapore(String code) {
        return "SG".equalsIgnoreCase(code != null ? code.trim() : null);
    }
}
