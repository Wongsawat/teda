package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Allowance/Charge Identification Code (Code List 5189, Version D14A)
 *
 * Represents specific types of allowances (discounts/deductions) and charges (additions)
 * that can be applied to invoice line items or document totals in e-Tax Invoice documents.
 *
 * UN/CEFACT Code List: 5189 (AllowanceChargeID)
 * Schema Version: D14A (15 Nov 2014)
 * Total Codes: 106 (1-101 standard + PP001-PP006 Thai extensions)
 *
 * Categories:
 * - Documentary Credit Commission: Bank fees for letters of credit, confirmations, amendments
 * - Collection Commission: Fees for documentary collections and settlements
 * - Processing Fee: Communication charges (SWIFT, courier, phone, telex, fax)
 * - Discount: Various discount types (manufacturer, production error, sample, end-of-range)
 * - Penalty: Late delivery penalties, execution delays
 * - Bonus: Early completion bonuses
 * - Freight/Packing/Loading/Handling Charges: Logistics costs
 * - Testing/Inspection Charges: Quality control fees
 * - Other Charges: Miscellaneous charges (insurance, minimum order, surcharges)
 * - Other: General category for unclassified items
 */
@Entity
@Table(name = "allowance_charge_identification_code")
public class AllowanceChargeIdentificationCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "is_standard_code")
    private Boolean isStandardCode = true;

    @Column(name = "is_thai_extension")
    private Boolean isThaiExtension = false;

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
    public AllowanceChargeIdentificationCode() {
    }

    public AllowanceChargeIdentificationCode(String code) {
        this.code = normalizeCode(code);
    }

    public AllowanceChargeIdentificationCode(String code, String name, String description) {
        this.code = normalizeCode(code);
        this.name = name;
        this.description = description;
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // Allowance charge identification codes are uppercase (numeric or alphanumeric)
        return code.trim().toUpperCase();
    }

    // Business Logic Methods

    /**
     * Check if this is a documentary credit commission
     */
    public boolean isDocumentaryCreditCommission() {
        return "Documentary Credit Commission".equals(category);
    }

    /**
     * Check if this is a collection commission
     */
    public boolean isCollectionCommission() {
        return "Collection Commission".equals(category);
    }

    /**
     * Check if this is a processing fee
     */
    public boolean isProcessingFee() {
        return "Processing Fee".equals(category);
    }

    /**
     * Check if this is a discount
     */
    public boolean isDiscount() {
        return "Discount".equals(category);
    }

    /**
     * Check if this is a rebate
     */
    public boolean isRebate() {
        return "Rebate".equals(category);
    }

    /**
     * Check if this is a penalty
     */
    public boolean isPenalty() {
        return "Penalty".equals(category);
    }

    /**
     * Check if this is a bonus
     */
    public boolean isBonus() {
        return "Bonus".equals(category);
    }

    /**
     * Check if this is freight charges
     */
    public boolean isFreightCharges() {
        return "Freight Charges".equals(category);
    }

    /**
     * Check if this is packing charges
     */
    public boolean isPackingCharges() {
        return "Packing Charges".equals(category);
    }

    /**
     * Check if this is loading/unloading charges
     */
    public boolean isLoadingCharges() {
        return "Loading/Unloading Charges".equals(category);
    }

    /**
     * Check if this is handling charges
     */
    public boolean isHandlingCharges() {
        return "Handling Charges".equals(category);
    }

    /**
     * Check if this is testing/inspection charges
     */
    public boolean isTestingCharges() {
        return "Testing/Inspection Charges".equals(category);
    }

    /**
     * Check if this is a standard UN/CEFACT code
     */
    public boolean isStandardCode() {
        return Boolean.TRUE.equals(isStandardCode);
    }

    /**
     * Check if this is a Thai/ETDA extension code
     */
    public boolean isThaiExtension() {
        return Boolean.TRUE.equals(isThaiExtension);
    }

    /**
     * Check if this is a commission-type charge
     */
    public boolean isCommission() {
        return category != null && category.contains("Commission");
    }

    /**
     * Check if this is a charge (not discount/allowance)
     */
    public boolean isCharge() {
        return category != null &&
               (category.contains("Charge") || category.contains("Fee") ||
                category.contains("Commission") || category.contains("Penalty"));
    }

    /**
     * Check if this is an allowance (discount/rebate/bonus)
     */
    public boolean isAllowance() {
        return category != null &&
               (category.contains("Discount") || category.contains("Rebate") ||
                category.contains("Bonus") || category.contains("Allowance"));
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

    public Boolean getIsStandardCode() {
        return isStandardCode;
    }

    public void setIsStandardCode(Boolean isStandardCode) {
        this.isStandardCode = isStandardCode;
    }

    public Boolean getIsThaiExtension() {
        return isThaiExtension;
    }

    public void setIsThaiExtension(Boolean isThaiExtension) {
        this.isThaiExtension = isThaiExtension;
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
        if (!(o instanceof AllowanceChargeIdentificationCode)) return false;
        AllowanceChargeIdentificationCode that = (AllowanceChargeIdentificationCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AllowanceChargeIdentificationCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isStandardCode=" + isStandardCode +
                ", isThaiExtension=" + isThaiExtension +
                '}';
    }
}
