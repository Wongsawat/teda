package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for Thai Document Name Codes (Invoice Document Types)
 * Based on ETDA ThaiDocumentNameCode_Invoice_1p0.xsd schema
 *
 * Document types include:
 * - Standard codes (80, 81, 82, 380, 388): UN/CEFACT standard document types
 * - Thai extension codes (T01-T07): Thai-specific document types
 */
@Entity
@Table(name = "thai_document_name_code", indexes = {
    @Index(name = "idx_thai_document_name_code_name_th", columnList = "name_th"),
    @Index(name = "idx_thai_document_name_code_name_en", columnList = "name_en"),
    @Index(name = "idx_thai_document_name_code_is_standard", columnList = "is_standard_code"),
    @Index(name = "idx_thai_document_name_code_is_thai", columnList = "is_thai_extension")
})
public class ThaiDocumentNameCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name_th", length = 255)
    private String nameTh;

    @Column(name = "name_en", length = 255, nullable = false)
    private String nameEn;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "is_standard_code", nullable = false)
    private Boolean standardCode = false;

    @Column(name = "is_thai_extension", nullable = false)
    private Boolean thaiExtension = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
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
    public ThaiDocumentNameCode() {
    }

    public ThaiDocumentNameCode(String code) {
        this.code = code;
    }

    public ThaiDocumentNameCode(String code, String nameEn, String description) {
        this.code = code;
        this.nameEn = nameEn;
        this.description = description;
    }

    public ThaiDocumentNameCode(String code, String nameTh, String nameEn, String description) {
        this.code = code;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.description = description;
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

    public Boolean getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(Boolean standardCode) {
        this.standardCode = standardCode;
    }

    public Boolean getThaiExtension() {
        return thaiExtension;
    }

    public void setThaiExtension(Boolean thaiExtension) {
        this.thaiExtension = thaiExtension;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Helper methods
    /**
     * Check if this is a debit note document type (code 80)
     */
    public boolean isDebitNote() {
        return "80".equals(code);
    }

    /**
     * Check if this is a credit note document type (code 81)
     */
    public boolean isCreditNote() {
        return "81".equals(code);
    }

    /**
     * Check if this is a commercial invoice (code 380)
     */
    public boolean isCommercialInvoice() {
        return "380".equals(code);
    }

    /**
     * Check if this is a tax invoice type (code 388)
     */
    public boolean isTaxInvoice() {
        return "388".equals(code);
    }

    /**
     * Check if this is a receipt type (code T01)
     */
    public boolean isReceipt() {
        return "T01".equals(code);
    }

    /**
     * Check if this is an abbreviated tax invoice (code T05)
     */
    public boolean isAbbreviatedTaxInvoice() {
        return "T05".equals(code);
    }

    /**
     * Check if this is a cancellation note (code T07)
     */
    public boolean isCancellationNote() {
        return "T07".equals(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThaiDocumentNameCode)) return false;
        ThaiDocumentNameCode that = (ThaiDocumentNameCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ThaiDocumentNameCode{" +
                "code='" + code + '\'' +
                ", nameTh='" + nameTh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", standardCode=" + standardCode +
                ", thaiExtension=" + thaiExtension +
                '}';
    }
}
