package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.ISOCurrencyCode;
import com.wpanther.etax.repository.ISOCurrencyCodeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import un.unece.uncefact.codelist.standard.iso.iso3alphacurrencycode._2012_08_31.ISO3AlphaCurrencyCodeContentType;

/**
 * JAXB XmlAdapter to convert between JAXB enum values and database-backed ISOCurrencyCode entities
 *
 * This adapter:
 * - Marshals ISOCurrencyCode entities to JAXB enum values for XML output
 * - Unmarshals JAXB enum values to ISOCurrencyCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 172 ISO 4217 currency codes
 *
 * Standard: ISO 4217 alpha-3
 * Namespace: urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31
 */
@Component
public class ISOCurrencyCodeAdapter extends XmlAdapter<ISO3AlphaCurrencyCodeContentType, ISOCurrencyCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOCurrencyCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ISOCurrencyCodeRepository repository;

    @Autowired
    public void setRepository(ISOCurrencyCodeRepository repository) {
        ISOCurrencyCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOCurrencyCode entity to JAXB enum value
     *
     * @param entity The ISOCurrencyCode entity
     * @return The JAXB enum value, or null if entity is null
     */
    @Override
    public ISO3AlphaCurrencyCodeContentType marshal(ISOCurrencyCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling ISOCurrencyCode: {} ({}) -> {}",
                  entity.getName(), entity.getNumericCode(), code);

        try {
            return ISO3AlphaCurrencyCodeContentType.valueOf(code);
        } catch (IllegalArgumentException e) {
            log.warn("Currency code '{}' not found in JAXB enum, cannot marshal", code);
            return null;
        }
    }

    /**
     * Unmarshal: Convert JAXB enum value to ISOCurrencyCode entity from database
     *
     * @param enumValue The JAXB enum value (e.g., THB, USD, EUR)
     * @return ISOCurrencyCode entity from database, or placeholder if not found
     */
    @Override
    public ISOCurrencyCode unmarshal(ISO3AlphaCurrencyCodeContentType enumValue) throws Exception {
        if (enumValue == null) {
            return null;
        }

        String code = enumValue.name();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", code);
            return createPlaceholder(code);
        }

        // Try to fetch from database
        return repository.findByCode(code)
                .orElseGet(() -> {
                    log.warn("Currency code '{}' not found in database, creating placeholder", code);
                    return createPlaceholder(code);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private ISOCurrencyCode createPlaceholder(String code) {
        ISOCurrencyCode placeholder = new ISOCurrencyCode(code);
        placeholder.setName("Unknown Currency: " + code);
        placeholder.setIsActive(false);
        placeholder.setMinorUnits(2); // Default to 2 decimal places
        return placeholder;
    }

    /**
     * Validate if a currency code exists in the database
     *
     * @param code The currency code
     * @return true if code exists and is active
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim().toUpperCase());
    }

    /**
     * Get currency name from code
     *
     * @param code The currency code
     * @return Currency name, or null if not found
     */
    public static String getName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCurrencyCode::getName)
                .orElse(null);
    }

    /**
     * Get numeric code from currency code
     *
     * @param code The currency code
     * @return Numeric code, or null if not found
     */
    public static String getNumericCode(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCurrencyCode::getNumericCode)
                .orElse(null);
    }

    /**
     * Get minor units (decimal places) from currency code
     *
     * @param code The currency code
     * @return Minor units (0, 2, or 3), defaults to 2 if not found
     */
    public static Integer getMinorUnits(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return 2;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCurrencyCode::getDecimalPlaces)
                .orElse(2);
    }

    /**
     * Check if currency is Thai Baht
     *
     * @param code The currency code
     * @return true if THB
     */
    public static boolean isThaiBasht(String code) {
        return "THB".equalsIgnoreCase(code);
    }

    /**
     * Check if currency is US Dollar
     *
     * @param code The currency code
     * @return true if USD
     */
    public static boolean isUSDollar(String code) {
        return "USD".equalsIgnoreCase(code);
    }

    /**
     * Check if currency is a major reserve currency
     *
     * @param code The currency code
     * @return true if major currency
     */
    public static boolean isMajorCurrency(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCurrencyCode::isMajorCurrency)
                .orElse(false);
    }

    /**
     * Check if currency is an ASEAN currency
     *
     * @param code The currency code
     * @return true if ASEAN currency
     */
    public static boolean isASEANCurrency(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCurrencyCode::isASEANCurrency)
                .orElse(false);
    }

    /**
     * Check if currency has zero decimal places
     *
     * @param code The currency code
     * @return true if 0 decimal places (like JPY, KRW)
     */
    public static boolean hasNoDecimalPlaces(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(ISOCurrencyCode::hasNoDecimalPlaces)
                .orElse(false);
    }

    /**
     * Format amount with currency code
     *
     * @param amount The amount
     * @param code The currency code
     * @return Formatted string with amount and currency code
     */
    public static String formatAmount(double amount, String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return String.format("%,.2f %s", amount, code);
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(c -> c.formatAmount(amount))
                .orElse(String.format("%,.2f %s", amount, code));
    }
}
