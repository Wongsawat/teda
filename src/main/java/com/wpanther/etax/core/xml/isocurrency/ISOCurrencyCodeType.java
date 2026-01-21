package com.wpanther.etax.core.xml.isocurrency;

import com.wpanther.etax.core.adapter.common.ISOCurrencyCodeAdapter;
import com.wpanther.etax.core.entity.ISOCurrencyCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for ISO 4217 Three-letter Currency Code
 * Replaces the generated JAXBElement<ENUM> type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 172 ISO 4217 currency codes
 *
 * Standard: ISO 4217 alpha-3
 * Code List Version: 2012-08-31
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISO3AlphaCurrencyCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31")
public class ISOCurrencyCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOCurrencyCodeAdapter.class)
    private ISOCurrencyCode value;

    // Constructors
    public ISOCurrencyCodeType() {
    }

    public ISOCurrencyCodeType(ISOCurrencyCode value) {
        this.value = value;
    }

    public ISOCurrencyCodeType(String code) {
        this.value = new ISOCurrencyCode(code);
    }

    // Getter and Setter
    public ISOCurrencyCode getValue() {
        return value;
    }

    public void setValue(ISOCurrencyCode value) {
        this.value = value;
    }

    /**
     * Get the currency code string (e.g., "THB", "USD")
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the currency name (e.g., "Baht", "US Dollar")
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the currency description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the ISO 4217 numeric code (e.g., "764" for THB)
     */
    public String getNumericCode() {
        return value != null ? value.getNumericCode() : null;
    }

    /**
     * Get the number of minor units (decimal places)
     * Returns 2 for most currencies, 0 for JPY/KRW, 3 for BHD/KWD
     */
    public Integer getMinorUnits() {
        return value != null ? value.getMinorUnits() : 2;
    }

    /**
     * Get the decimal places (convenience method, defaults to 2)
     */
    public int getDecimalPlaces() {
        return value != null ? value.getDecimalPlaces() : 2;
    }

    /**
     * Check if this is Thai Baht (THB)
     */
    public boolean isThaiBaht() {
        return value != null && value.isThaiBaht();
    }

    /**
     * Check if this is US Dollar (USD)
     */
    public boolean isUSDollar() {
        return value != null && value.isUSDollar();
    }

    /**
     * Check if this is Euro (EUR)
     */
    public boolean isEuro() {
        return value != null && value.isEuro();
    }

    /**
     * Check if this is a major reserve currency
     */
    public boolean isMajorCurrency() {
        return value != null && value.isMajorCurrency();
    }

    /**
     * Check if this is an ASEAN currency
     */
    public boolean isASEANCurrency() {
        return value != null && value.isASEANCurrency();
    }

    /**
     * Check if this currency has zero decimal places (like JPY, KRW)
     */
    public boolean hasNoDecimalPlaces() {
        return value != null && value.hasNoDecimalPlaces();
    }

    /**
     * Check if this currency has 3 decimal places (like BHD, KWD)
     */
    public boolean hasThreeDecimalPlaces() {
        return value != null && value.hasThreeDecimalPlaces();
    }

    /**
     * Format an amount with the correct decimal places for this currency
     */
    public String formatAmount(double amount) {
        return value != null ? value.formatAmount(amount) : String.format("%,.2f", amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOCurrencyCodeType)) return false;
        ISOCurrencyCodeType that = (ISOCurrencyCodeType) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (value != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCode());
            if (value.getName() != null) {
                sb.append(" (").append(value.getName()).append(")");
            }
            if (value.getNumericCode() != null) {
                sb.append(" [").append(value.getNumericCode()).append("]");
            }
            if (value.getMinorUnits() != null && value.getMinorUnits() != 2) {
                sb.append(" {").append(value.getMinorUnits()).append(" decimals}");
            }
            return sb.toString();
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static ISOCurrencyCodeType of(String code) {
        return new ISOCurrencyCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ISOCurrencyCodeType of(ISOCurrencyCode entity) {
        return new ISOCurrencyCodeType(entity);
    }
}
