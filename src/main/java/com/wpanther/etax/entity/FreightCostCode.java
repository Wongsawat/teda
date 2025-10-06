package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Freight Cost Code (Recommendation 23, Version 4)
 *
 * Represents freight and shipping cost codes used in e-Tax Invoice documents
 * for international trade and transportation charges.
 *
 * UN/CEFACT: Recommendation 23
 * Schema Version: 4
 * Total Codes: 1,641 (100000 to 609144)
 *
 * Categories:
 * - Basic Freight: Standard freight charges
 * - Freight Surcharges: Additional charges, allowances, discounts
 * - Container Services: Container-related charges
 * - Terminal Charges: Port and terminal operations
 * - Handling Charges: Cargo handling and labor
 * - Storage & Demurrage: Storage and detention charges
 * - Customs & Documentation: Customs clearance and paperwork
 * - Dangerous Goods: Hazardous materials handling
 * - Special Freight: Special cargo and services
 * - Insurance: Cargo insurance charges
 * - Other Charges: Miscellaneous fees and charges
 * - General Freight: General freight classifications
 */
@Entity
@Table(name = "freight_cost_code")
public class FreightCostCode {

    @Id
    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "code_group", length = 3, insertable = false, updatable = false)
    private String codeGroup;

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
    public FreightCostCode() {
    }

    public FreightCostCode(String code) {
        this.code = normalizeCode(code);
    }

    public FreightCostCode(String code, String name, String category) {
        this.code = normalizeCode(code);
        this.name = name;
        this.category = category;
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // UN/CEFACT freight cost codes are 6-digit numeric
        return code.trim();
    }

    // Business Logic Methods

    /**
     * Check if this is a basic freight charge (code group 101)
     */
    public boolean isBasicFreight() {
        return "Basic Freight".equals(category) || "101".equals(codeGroup);
    }

    /**
     * Check if this is a freight surcharge/allowance/discount
     */
    public boolean isFreightSurcharge() {
        return "Freight Surcharges".equals(category);
    }

    /**
     * Check if this is container-related
     */
    public boolean isContainerService() {
        return "Container Services".equals(category) ||
               (name != null && name.toLowerCase().contains("container"));
    }

    /**
     * Check if this is a terminal charge
     */
    public boolean isTerminalCharge() {
        return "Terminal Charges".equals(category);
    }

    /**
     * Check if this is a handling charge
     */
    public boolean isHandlingCharge() {
        return "Handling Charges".equals(category);
    }

    /**
     * Check if this is storage or demurrage
     */
    public boolean isStorageOrDemurrage() {
        return "Storage & Demurrage".equals(category);
    }

    /**
     * Check if this is customs or documentation related
     */
    public boolean isCustomsOrDocumentation() {
        return "Customs & Documentation".equals(category);
    }

    /**
     * Check if this is dangerous goods related
     */
    public boolean isDangerousGoods() {
        return "Dangerous Goods".equals(category) ||
               (name != null && (name.toLowerCase().contains("dangerous") ||
                                 name.toLowerCase().contains("hazardous")));
    }

    /**
     * Check if this is special freight
     */
    public boolean isSpecialFreight() {
        return "Special Freight".equals(category);
    }

    /**
     * Check if this is insurance related
     */
    public boolean isInsurance() {
        return "Insurance".equals(category) ||
               (name != null && name.toLowerCase().contains("insurance"));
    }

    /**
     * Get the code group (first 3 digits)
     */
    public String getCodeGroup() {
        if (codeGroup != null) {
            return codeGroup;
        }
        if (code != null && code.length() >= 3) {
            return code.substring(0, 3);
        }
        return null;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
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
        if (!(o instanceof FreightCostCode)) return false;
        FreightCostCode that = (FreightCostCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FreightCostCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", codeGroup='" + getCodeGroup() + '\'' +
                '}';
    }
}
