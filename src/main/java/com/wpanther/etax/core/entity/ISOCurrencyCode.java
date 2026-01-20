package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for ISO 4217 Three-letter Currency Code
 *
 * Represents international currency codes used in e-Tax Invoice monetary amounts.
 *
 * Standard: ISO 4217 alpha-3
 * Code List Version: 2012-08-31
 * Schema Version: 9.3
 * Total Codes: 172
 *
 * Common Currencies:
 * - THB (764): Baht - Thai currency, 2 decimal places
 * - USD (840): US Dollar - 2 decimal places
 * - EUR (978): Euro - 2 decimal places
 * - JPY (392): Yen - 0 decimal places (no cents)
 * - CNY (156): Yuan Renminbi - 2 decimal places
 * - GBP (826): Pound Sterling - 2 decimal places
 */
@Entity
@Table(name = "iso_currency_code")
public class ISOCurrencyCode {

    @Id
    @Column(name = "code", length = 3, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "numeric_code", length = 3)
    private String numericCode;

    @Column(name = "minor_units")
    private Integer minorUnits;

    @Column(name = "is_active")
    private Boolean active = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // JPA Lifecycle Callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public ISOCurrencyCode() {
    }

    public ISOCurrencyCode(String code) {
        this.code = normalizeCode(code);
    }

    public ISOCurrencyCode(String code, String name) {
        this.code = normalizeCode(code);
        this.name = name;
    }

    public ISOCurrencyCode(String code, String name, String numericCode, Integer minorUnits) {
        this.code = normalizeCode(code);
        this.name = name;
        this.numericCode = numericCode;
        this.minorUnits = minorUnits;
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // Currency codes are 3 uppercase letters
        return code.trim().toUpperCase();
    }

    // Business Logic Methods

    /**
     * Check if this is Thai Baht (THB)
     */
    public boolean isThaiBasht() {
        return "THB".equals(code);
    }

    /**
     * Check if this is US Dollar (USD)
     */
    public boolean isUSDollar() {
        return "USD".equals(code);
    }

    /**
     * Check if this is Euro (EUR)
     */
    public boolean isEuro() {
        return "EUR".equals(code);
    }

    /**
     * Check if this is a major reserve currency
     * (USD, EUR, JPY, GBP, CNY, CHF, CAD, AUD)
     */
    public boolean isMajorCurrency() {
        return "USD".equals(code) || "EUR".equals(code) || "JPY".equals(code) ||
               "GBP".equals(code) || "CNY".equals(code) || "CHF".equals(code) ||
               "CAD".equals(code) || "AUD".equals(code);
    }

    /**
     * Check if this is an ASEAN currency
     */
    public boolean isASEANCurrency() {
        return "THB".equals(code) || "BND".equals(code) || "KHR".equals(code) ||
               "IDR".equals(code) || "LAK".equals(code) || "MYR".equals(code) ||
               "MMK".equals(code) || "PHP".equals(code) || "SGD".equals(code) ||
               "VND".equals(code);
    }

    /**
     * Check if this currency has zero decimal places (like JPY, KRW)
     */
    public boolean hasNoDecimalPlaces() {
        return minorUnits != null && minorUnits == 0;
    }

    /**
     * Check if this currency has 3 decimal places (like BHD, KWD)
     */
    public boolean hasThreeDecimalPlaces() {
        return minorUnits != null && minorUnits == 3;
    }

    /**
     * Get the number of decimal places (defaults to 2 if not set)
     */
    public int getDecimalPlaces() {
        return minorUnits != null ? minorUnits : 2;
    }

    /**
     * Format an amount with the correct decimal places for this currency
     */
    public String formatAmount(double amount) {
        int decimals = getDecimalPlaces();
        if (decimals == 0) {
            return String.format("%,.0f %s", amount, code);
        } else if (decimals == 3) {
            return String.format("%,.3f %s", amount, code);
        } else {
            return String.format("%,.2f %s", amount, code);
        }
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = normalizeCode(code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public Integer getMinorUnits() {
        return minorUnits;
    }

    public void setMinorUnits(Integer minorUnits) {
        this.minorUnits = minorUnits;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Equals and HashCode based on code
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOCurrencyCode)) return false;
        ISOCurrencyCode that = (ISOCurrencyCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ISOCurrencyCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", numericCode='" + numericCode + '\'' +
                ", minorUnits=" + minorUnits +
                ", active=" + active +
                '}';
    }
}
