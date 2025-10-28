package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Delivery Terms Code (Code List 64053, INCOTERMS 2010)
 *
 * Represents delivery terms codes (INCOTERMS 2010) used in e-Tax Invoice documents
 * for international trade, defining responsibilities of buyers and sellers.
 *
 * UN/CEFACT Code List: 64053
 * Standard: INCOTERMS 2010 (with INCOTERMS 2020 updates)
 * Total Codes: 14 (2 custom + 12 INCOTERMS)
 *
 * INCOTERMS Groups:
 * - Group E (Departure): EXW - Minimum seller obligation
 * - Group F (Main carriage unpaid): FCA, FAS, FOB - Low seller obligation
 * - Group C (Main carriage paid): CFR, CIF, CPT, CIP - Medium seller obligation
 * - Group D (Arrival): DAP, DAT, DPU, DDP - High to maximum seller obligation
 */
@Entity
@Table(name = "delivery_terms_code")
public class DeliveryTermsCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "incoterm_group", length = 20)
    private String incotermGroup;

    @Column(name = "seller_obligation", length = 20)
    private String sellerObligation;

    @Column(name = "is_incoterm")
    private Boolean isIncoterm = true;

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
    public DeliveryTermsCode() {
    }

    public DeliveryTermsCode(String code) {
        this.code = normalizeCode(code);
    }

    public DeliveryTermsCode(String code, String name) {
        this.code = normalizeCode(code);
        this.name = name;
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // Delivery terms codes are case-sensitive (INCOTERMS use uppercase)
        return code.trim().toUpperCase();
    }

    // Business Logic Methods

    /**
     * Check if this is an official INCOTERM
     */
    public boolean isIncoterm() {
        return Boolean.TRUE.equals(isIncoterm);
    }

    /**
     * Check if this is Group E (Departure) - Minimum seller obligation
     */
    public boolean isGroupE() {
        return "E".equals(incotermGroup);
    }

    /**
     * Check if this is Group F (Main carriage unpaid) - Low seller obligation
     */
    public boolean isGroupF() {
        return "F".equals(incotermGroup);
    }

    /**
     * Check if this is Group C (Main carriage paid) - Medium seller obligation
     */
    public boolean isGroupC() {
        return "C".equals(incotermGroup);
    }

    /**
     * Check if this is Group D (Arrival) - High to maximum seller obligation
     */
    public boolean isGroupD() {
        return "D".equals(incotermGroup);
    }

    /**
     * Check if seller has minimum obligation (EXW)
     */
    public boolean hasMinimumSellerObligation() {
        return "Minimum".equals(sellerObligation);
    }

    /**
     * Check if seller has low obligation
     */
    public boolean hasLowSellerObligation() {
        return "Low".equals(sellerObligation);
    }

    /**
     * Check if seller has medium obligation
     */
    public boolean hasMediumSellerObligation() {
        return "Medium".equals(sellerObligation);
    }

    /**
     * Check if seller has high obligation
     */
    public boolean hasHighSellerObligation() {
        return "High".equals(sellerObligation);
    }

    /**
     * Check if seller has maximum obligation (DDP)
     */
    public boolean hasMaximumSellerObligation() {
        return "Maximum".equals(sellerObligation);
    }

    /**
     * Check if this term includes insurance (CIF, CIP)
     */
    public boolean includesInsurance() {
        return "CIF".equals(code) || "CIP".equals(code);
    }

    /**
     * Check if this term includes freight payment (CFR, CIF, CPT, CIP)
     */
    public boolean includesFreight() {
        return "CFR".equals(code) || "CIF".equals(code) ||
               "CPT".equals(code) || "CIP".equals(code);
    }

    /**
     * Check if this is for sea/inland waterway transport only
     */
    public boolean isSeaTransportOnly() {
        return "FAS".equals(code) || "FOB".equals(code) ||
               "CFR".equals(code) || "CIF".equals(code);
    }

    /**
     * Check if this is for any mode of transport
     */
    public boolean isAnyTransportMode() {
        return "EXW".equals(code) || "FCA".equals(code) ||
               "CPT".equals(code) || "CIP".equals(code) ||
               "DAP".equals(code) || "DPU".equals(code) || "DDP".equals(code);
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

    public String getIncotermGroup() {
        return incotermGroup;
    }

    public void setIncotermGroup(String incotermGroup) {
        this.incotermGroup = incotermGroup;
    }

    public String getSellerObligation() {
        return sellerObligation;
    }

    public void setSellerObligation(String sellerObligation) {
        this.sellerObligation = sellerObligation;
    }

    public Boolean getIncoterm() {
        return isIncoterm;
    }

    public void setIncoterm(Boolean incoterm) {
        isIncoterm = incoterm;
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
        if (!(o instanceof DeliveryTermsCode)) return false;
        DeliveryTermsCode that = (DeliveryTermsCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DeliveryTermsCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", incotermGroup='" + incotermGroup + '\'' +
                ", sellerObligation='" + sellerObligation + '\'' +
                ", isIncoterm=" + isIncoterm +
                '}';
    }
}
