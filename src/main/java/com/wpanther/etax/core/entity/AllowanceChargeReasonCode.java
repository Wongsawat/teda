package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Allowance Charge Reason Code (Code List 64465, Version D15B)
 *
 * Represents reasons for allowances (discounts/deductions) and charges (additions)
 * applied to invoice amounts in e-Tax Invoice documents.
 *
 * UN/CEFACT Code List: 64465
 * Schema Version: D15B (30 May 2016)
 * Total Codes: 105 (1-104 + ZZZ)
 *
 * Categories:
 * - Quality Issue: Damaged goods, below specification, transport damage
 * - Delivery Issue: Short delivery, wrong delivery, returns
 * - Administrative Error: Invoice errors, incorrect data, incorrect references
 * - Discount/Allowance: Trade discounts, volume discounts, promotions
 * - Financial Charges: Bank charges, agent commission, draft costs
 * - Claims/Disputes: Counter claims, credit balance, offset
 * - Freight/Logistics: Container charges, freight costs
 * - Payment Terms: Agreed settlement, proof of delivery
 * - HR Related: Employee changes, salary adjustments
 * - Other: Miscellaneous reasons
 * - Custom/Other: Mutually defined (ZZZ)
 */
@Entity
@Table(name = "allowance_charge_reason_code")
public class AllowanceChargeReasonCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "category", length = 50)
    private String category;

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
    public AllowanceChargeReasonCode() {
    }

    public AllowanceChargeReasonCode(String code) {
        this.code = normalizeCode(code);
    }

    public AllowanceChargeReasonCode(String code, String name, String description) {
        this.code = normalizeCode(code);
        this.name = name;
        this.description = description;
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // Allowance charge reason codes are uppercase (numeric or ZZZ)
        return code.trim().toUpperCase();
    }

    // Business Logic Methods

    /**
     * Check if this is a quality issue reason
     */
    public boolean isQualityIssue() {
        return "Quality Issue".equals(category);
    }

    /**
     * Check if this is a delivery issue reason
     */
    public boolean isDeliveryIssue() {
        return "Delivery Issue".equals(category);
    }

    /**
     * Check if this is an administrative error reason
     */
    public boolean isAdministrativeError() {
        return "Administrative Error".equals(category);
    }

    /**
     * Check if this is a discount or allowance reason
     */
    public boolean isDiscountOrAllowance() {
        return "Discount/Allowance".equals(category);
    }

    /**
     * Check if this is a financial charge reason
     */
    public boolean isFinancialCharge() {
        return "Financial Charges".equals(category);
    }

    /**
     * Check if this is a claim or dispute reason
     */
    public boolean isClaimOrDispute() {
        return "Claims/Disputes".equals(category);
    }

    /**
     * Check if this is a freight or logistics reason
     */
    public boolean isFreightOrLogistics() {
        return "Freight/Logistics".equals(category);
    }

    /**
     * Check if this is a payment terms reason
     */
    public boolean isPaymentTerms() {
        return "Payment Terms".equals(category);
    }

    /**
     * Check if this is HR related reason
     */
    public boolean isHRRelated() {
        return "HR Related".equals(category);
    }

    /**
     * Check if this is the mutually defined code (ZZZ)
     */
    public boolean isMutuallyDefined() {
        return "ZZZ".equals(code);
    }

    /**
     * Check if this is a custom/other reason
     */
    public boolean isCustomOrOther() {
        return "Custom/Other".equals(category) || "Other".equals(category);
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
        if (!(o instanceof AllowanceChargeReasonCode)) return false;
        AllowanceChargeReasonCode that = (AllowanceChargeReasonCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AllowanceChargeReasonCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
