package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.ISOCountryCode;
import com.wpanther.etax.repository.ISOCountryCodeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

/**
 * JAXB XML Adapter for ISO 3166-1 Two-letter Country Code
 *
 * Converts between JAXB enum (ISOTwoletterCountryCodeContentType) and
 * database-backed JPA entity (ISOCountryCode).
 *
 * Pattern: JAXBElement<ENUM> (same as currency code)
 *
 * Standard: ISO 3166-1 alpha-2
 * Total Countries: 252 (249 standard + 3 ETDA extensions)
 * Code Format: 2 uppercase letters
 *
 * JAXB Enum: ISOTwoletterCountryCodeContentType with 252 values
 * - Standard ISO codes: AD, AE, AF, ... TH, ... US, ... ZW
 * - ETDA extensions: AN (inactive), KS, UN
 *
 * Marshalling: ISOCountryCode entity -> ENUM value (e.g., TH)
 * Unmarshalling: ENUM value (e.g., TH) -> ISOCountryCode entity from database
 */
@Component
public class ISOCountryCodeAdapter extends XmlAdapter<ISOTwoletterCountryCodeContentType, ISOCountryCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOCountryCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ISOCountryCodeRepository repository;

    @Autowired
    public void setRepository(ISOCountryCodeRepository repository) {
        ISOCountryCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOCountryCode entity to JAXB enum value
     *
     * @param entity ISOCountryCode entity from database
     * @return JAXB enum value (e.g., ISOTwoletterCountryCodeContentType.TH)
     * @throws Exception if conversion fails
     */
    @Override
    public ISOTwoletterCountryCodeContentType marshal(ISOCountryCode entity) throws Exception {
        if (entity == null) {
            return null;
        }

        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        try {
            // Convert entity code to JAXB enum
            ISOTwoletterCountryCodeContentType enumValue = ISOTwoletterCountryCodeContentType.valueOf(code.toUpperCase());
            log.debug("Marshalled ISOCountryCode: {} ({}) -> {}", code, entity.getName(), enumValue);
            return enumValue;
        } catch (IllegalArgumentException e) {
            log.warn("Country code '{}' not found in JAXB enum, returning null", code);
            return null;
        }
    }

    /**
     * Unmarshal: Convert JAXB enum value to ISOCountryCode entity from database
     *
     * @param enumValue JAXB enum value (e.g., ISOTwoletterCountryCodeContentType.TH)
     * @return ISOCountryCode entity from database, or placeholder if not found
     * @throws Exception if conversion fails
     */
    @Override
    public ISOCountryCode unmarshal(ISOTwoletterCountryCodeContentType enumValue) throws Exception {
        if (enumValue == null) {
            return null;
        }

        // Get country code from enum (e.g., "TH")
        String code = enumValue.name();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", code);
            return createPlaceholder(code);
        }

        // Lookup in database
        return repository.findByCode(code)
                .orElseGet(() -> {
                    log.warn("Country code '{}' not found in database, creating placeholder", code);
                    return createPlaceholder(code);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private ISOCountryCode createPlaceholder(String code) {
        ISOCountryCode placeholder = new ISOCountryCode(code);
        placeholder.setName("Unknown Country: " + code);
        placeholder.setDescription("Placeholder for unknown country code");
        placeholder.setActive(false);
        return placeholder;
    }

    // Static Helper Methods

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
        return repository.existsByCode(code.trim());
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
        return repository.findByCode(code.trim())
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
        return repository.findByCode(code.trim())
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
        return repository.findByCode(code.trim())
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
        return repository.findByCode(code.trim())
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
