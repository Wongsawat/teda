package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for Thai Category Codes (ETDA e-Tax Invoice)
 * Replaces the XSD string enumeration with database-backed implementation
 *
 * Schema: ThaiCategoryCode_1p0.xsd
 * Namespace: urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1
 *
 * Category codes specify the purpose of document references:
 * - 01: Reference to original document for cancellation, debit note, or credit note
 * - 02: Reference to document for advance payment receipt
 */
@Entity
@Table(name = "thai_category_code", indexes = {
    @Index(name = "idx_thai_category_code_name_th", columnList = "name_th"),
    @Index(name = "idx_thai_category_code_name_en", columnList = "name_en")
})
public class ThaiCategoryCode {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "name_th", nullable = false, length = 500)
    private String nameTh;

    @Column(name = "name_en", length = 500)
    private String nameEn;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
    public ThaiCategoryCode() {
    }

    public ThaiCategoryCode(String code) {
        this.code = code;
    }

    public ThaiCategoryCode(String code, String nameTh, String nameEn) {
        this.code = code;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    // Helper methods
    /**
     * Check if this is code 01 - Reference to original document for cancellation/debit/credit
     */
    public boolean isOriginalDocumentReference() {
        return "01".equals(code);
    }

    /**
     * Check if this is code 02 - Reference to document for advance payment
     */
    public boolean isAdvancePaymentReference() {
        return "02".equals(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThaiCategoryCode)) return false;
        ThaiCategoryCode that = (ThaiCategoryCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ThaiCategoryCode{" +
                "code='" + code + '\'' +
                ", nameTh='" + nameTh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }
}
