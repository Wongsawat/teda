package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Address Type Code (Code List 3131, Version D14A)
 *
 * Represents the classification of addresses used in e-Tax Invoice documents.
 *
 * UN/CEFACT Code List: 3131
 * Schema Version: D14A (15 Nov 2014)
 * Total Codes: 3
 *
 * Address Types:
 * - Postal address (1): The address is representing a postal address
 * - Fiscal address (2): Identification of an address as required by fiscal administrations
 * - Physical address (3): The address represents an actual physical location
 */
@Entity
@Table(name = "address_type")
public class AddressType {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

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
    public AddressType() {
    }

    public AddressType(String code) {
        this.code = normalizeCode(code);
    }

    public AddressType(String code, String name, String description) {
        this.code = normalizeCode(code);
        this.name = name;
        this.description = description;
    }

    // Code Normalization
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // Address type codes are numeric (1-3)
        return code.trim();
    }

    // Business Logic Methods

    /**
     * Check if this is a postal address type
     */
    public boolean isPostalAddress() {
        return "1".equals(code);
    }

    /**
     * Check if this is a fiscal address type
     */
    public boolean isFiscalAddress() {
        return "2".equals(code);
    }

    /**
     * Check if this is a physical address type
     */
    public boolean isPhysicalAddress() {
        return "3".equals(code);
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
        if (!(o instanceof AddressType)) return false;
        AddressType that = (AddressType) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AddressType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
