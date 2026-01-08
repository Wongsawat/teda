package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.ISOCurrencyCodeRepository;
import com.wpanther.etax.core.entity.ISOCurrencyCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XML Adapter for ISO 4217 Three-letter Currency Code
 *
 * Converts between XML String values and database-backed ISOCurrencyCode entities.
 *
 * This adapter:
 * - Marshals ISOCurrencyCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ISOCurrencyCode entities from database
 * - Maintains full JAXB namespace compatibility with ISO 4217 schema
 * - Handles missing codes gracefully by creating placeholder entities
 *
 * Standard: ISO 4217 alpha-3
 * Total Currencies: 172 official codes
 * Code Format: 3 uppercase letters (THB, USD, EUR, etc.)
 */
@Component
public class ISOCurrencyCodeAdapter extends XmlAdapter<String, ISOCurrencyCode> {

    private static final Logger log = LoggerFactory.getLogger(ISOCurrencyCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static ISOCurrencyCodeRepository repository;

    @Autowired
    public void setRepository(ISOCurrencyCodeRepository repository) {
        ISOCurrencyCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert ISOCurrencyCode entity to XML String (currency code)
     *
     * @param entity ISOCurrencyCode entity from database
     * @return The currency code string (THB, USD, EUR, etc.), or null if entity is null
     */
    @Override
    public String marshal(ISOCurrencyCode entity) throws Exception {
        if (entity == null) {
            return null;
        }

        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        log.debug("Marshalling ISOCurrencyCode: {} ({}) -> {}",
                  entity.getName(), entity.getNumericCode(), code);
        return code.toUpperCase();
    }

    /**
     * Unmarshal: Convert XML String (currency code) to ISOCurrencyCode entity from database
     *
     * @param code The currency code from XML (THB, USD, EUR, etc.)
     * @return ISOCurrencyCode entity from database, or placeholder if not found
     */
    @Override
    public ISOCurrencyCode unmarshal(String code) throws Exception {
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
                    log.warn("Currency code '{}' not found in database, creating placeholder", upperCode);
                    return createPlaceholder(upperCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private static ISOCurrencyCode createPlaceholder(String code) {
        ISOCurrencyCode placeholder = new ISOCurrencyCode(code);
        placeholder.setName("Unknown Currency: " + code);
        placeholder.setIsActive(false);
        placeholder.setMinorUnits(2); // Default to 2 decimal places
        return placeholder;
    }

    // Static Helper Methods

    /**
     * Convert code string to entity.
     */
    public static ISOCurrencyCode toEntity(String code) {
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
    public static String toCode(ISOCurrencyCode entity) {
        return entity != null ? entity.getCode() : null;
    }

    /**
     * Validate if a currency code exists in the database
     *
     * @param code The currency code (e.g., "THB", "USD")
     * @return true if code exists and is active
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase()).isPresent();
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
     * Normalize currency code to uppercase
     *
     * @param code Currency code
     * @return Normalized uppercase code
     */
    public static String normalize(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        return code.trim().toUpperCase();
    }

    /**
     * Check if currency is Thai Baht
     *
     * @param code The currency code
     * @return true if THB
     */
    public static boolean isThaiBasht(String code) {
        return "THB".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if currency is US Dollar
     *
     * @param code The currency code
     * @return true if USD
     */
    public static boolean isUSDollar(String code) {
        return "USD".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if currency is Euro
     *
     * @param code The currency code
     * @return true if EUR
     */
    public static boolean isEuro(String code) {
        return "EUR".equalsIgnoreCase(code != null ? code.trim() : null);
    }

    /**
     * Check if currency is Japanese Yen
     *
     * @param code The currency code
     * @return true if JPY
     */
    public static boolean isJapaneseYen(String code) {
        return "JPY".equalsIgnoreCase(code != null ? code.trim() : null);
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
