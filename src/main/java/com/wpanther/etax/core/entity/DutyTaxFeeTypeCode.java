package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Duty Tax Fee Type Code (Code List 65153)
 *
 * Represents tax and duty classification codes used in e-Tax Invoice documents.
 * Covers VAT, GST, customs duties, excise taxes, and various other tax types.
 *
 * UN/CEFACT Code List: 65153
 * Schema Version: D14A (Nov 15, 2014)
 * Total Codes: 53 (AAA to VAT)
 *
 * Categories:
 * - VAT: Value Added Tax (VAT, ENV, EXP)
 * - GST: Goods and Services Tax
 * - Customs: Import/export duties (AAA, AAB, AAC, etc.)
 * - Excise: Petroleum, alcohol, tobacco taxes
 * - Exempt: Tax exemptions (EXE, TAX, FRE)
 * - Other: Special taxes, fees, surcharges
 */
@Entity
@Table(name = "duty_tax_fee_type_code")
public class DutyTaxFeeTypeCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "is_vat")
    private Boolean isVat = false;

    @Column(name = "is_exempt")
    private Boolean isExempt = false;

    @Column(name = "is_summary")
    private Boolean isSummary = false;

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
    public DutyTaxFeeTypeCode() {
    }

    public DutyTaxFeeTypeCode(String code) {
        this.code = normalizeCode(code);
        this.isVat = isVatCode(this.code);
    }

    public DutyTaxFeeTypeCode(String code, String name, String description) {
        this.code = normalizeCode(code);
        this.name = name;
        this.description = description;
        this.isVat = isVatCode(this.code);
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // UN/CEFACT codes are uppercase
        return code.trim().toUpperCase();
    }

    /**
     * Check if a code is a VAT-related code
     */
    private boolean isVatCode(String code) {
        if (code == null) {
            return false;
        }
        return "VAT".equals(code) || "ENV".equals(code) || "EXP".equals(code);
    }

    // Business Logic Methods

    /**
     * Check if this is a VAT-related code
     * Codes: VAT, ENV, EXP
     */
    public boolean isVat() {
        return Boolean.TRUE.equals(isVat);
    }

    /**
     * Check if this is an exemption code
     * Codes: EXE, TAX, FRE, TT, ZZZ
     */
    public boolean isExempt() {
        return Boolean.TRUE.equals(isExempt);
    }

    /**
     * Check if this is a summary tax code
     * Codes: OTH, TOT
     */
    public boolean isSummary() {
        return Boolean.TRUE.equals(isSummary);
    }

    /**
     * Check if this is a customs duty
     */
    public boolean isCustomsDuty() {
        return "Customs".equals(category);
    }

    /**
     * Check if this is an excise tax
     */
    public boolean isExciseTax() {
        return "Excise".equals(category);
    }

    /**
     * Check if this is GST
     */
    public boolean isGST() {
        return "GST".equals(category);
    }

    /**
     * Check if this is a special tax
     */
    public boolean isSpecialTax() {
        return "Special Tax".equals(category);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVat(Boolean vat) {
        isVat = vat;
    }

    public void setExempt(Boolean exempt) {
        isExempt = exempt;
    }

    public void setSummary(Boolean summary) {
        isSummary = summary;
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
        if (!(o instanceof DutyTaxFeeTypeCode)) return false;
        DutyTaxFeeTypeCode that = (DutyTaxFeeTypeCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DutyTaxFeeTypeCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isVat=" + isVat +
                ", isExempt=" + isExempt +
                '}';
    }
}
