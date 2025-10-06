package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JPA Entity for UN/CEFACT Payment Terms Type Code (Code List 64279)
 *
 * This entity stores payment terms type codes from the UNECE D14A standard.
 * These codes define various payment conditions and terms used in commercial transactions.
 *
 * Total codes: 79 (1-78 + ZZZ)
 * Categories: Standard, Scheduled, Immediate, Deferred, Discount, Letter of Credit, etc.
 *
 * Database table: payment_terms_type_code
 * Primary key: code (VARCHAR 10)
 */
@Entity
@Table(name = "payment_terms_type_code",
       indexes = {
           @Index(name = "idx_payment_terms_type_code_category", columnList = "category"),
           @Index(name = "idx_payment_terms_type_code_is_immediate", columnList = "is_immediate"),
           @Index(name = "idx_payment_terms_type_code_is_deferred", columnList = "is_deferred"),
           @Index(name = "idx_payment_terms_type_code_has_discount", columnList = "has_discount")
       })
public class PaymentTermsTypeCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "is_immediate", nullable = false)
    private Boolean isImmediate = false;

    @Column(name = "is_deferred", nullable = false)
    private Boolean isDeferred = false;

    @Column(name = "has_discount", nullable = false)
    private Boolean hasDiscount = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public PaymentTermsTypeCode() {
    }

    public PaymentTermsTypeCode(String code) {
        this.code = code != null ? code.trim().toUpperCase() : null;
    }

    public PaymentTermsTypeCode(String code, String name, String description) {
        this.code = code != null ? code.trim().toUpperCase() : null;
        this.name = name;
        this.description = description;
    }

    // JPA Lifecycle Callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        normalizeCode();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        normalizeCode();
    }

    private void normalizeCode() {
        if (this.code != null) {
            this.code = this.code.trim().toUpperCase();
        }
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code != null ? code.trim().toUpperCase() : null;
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

    public Boolean getIsImmediate() {
        return isImmediate;
    }

    public void setIsImmediate(Boolean isImmediate) {
        this.isImmediate = isImmediate;
    }

    public Boolean getIsDeferred() {
        return isDeferred;
    }

    public void setIsDeferred(Boolean isDeferred) {
        this.isDeferred = isDeferred;
    }

    public Boolean getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(Boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Business logic methods
    public boolean isImmediate() {
        return Boolean.TRUE.equals(this.isImmediate);
    }

    public boolean isDeferred() {
        return Boolean.TRUE.equals(this.isDeferred);
    }

    public boolean hasDiscount() {
        return Boolean.TRUE.equals(this.hasDiscount);
    }

    // equals and hashCode based on code (primary key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTermsTypeCode)) return false;
        PaymentTermsTypeCode that = (PaymentTermsTypeCode) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "PaymentTermsTypeCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isImmediate=" + isImmediate +
                ", isDeferred=" + isDeferred +
                ", hasDiscount=" + hasDiscount +
                '}';
    }
}
