package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing Thai e-Tax Invoice message function codes.
 * Based on ETDA ThaiMessageFunctionCode_1p0.xsd standard.
 *
 * These codes indicate the document type and purpose for Thai e-Tax invoices,
 * receipts, debit notes, and credit notes.
 */
@Entity
@Table(name = "thai_message_function_code")
public class ThaiMessageFunctionCode {

    @Id
    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "description_en", length = 255, nullable = false)
    private String descriptionEn;

    @Column(name = "description_th", length = 255)
    private String descriptionTh;

    @Column(name = "document_type", length = 20, nullable = false)
    private String documentType;

    @Column(name = "category", length = 10, nullable = false)
    private String category;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

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
    public ThaiMessageFunctionCode() {
    }

    public ThaiMessageFunctionCode(String code) {
        this.code = code;
    }

    public ThaiMessageFunctionCode(String code, String descriptionEn, String descriptionTh, String documentType, String category) {
        this.code = code;
        this.descriptionEn = descriptionEn;
        this.descriptionTh = descriptionTh;
        this.documentType = documentType;
        this.category = category;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionTh() {
        return descriptionTh;
    }

    public void setDescriptionTh(String descriptionTh) {
        this.descriptionTh = descriptionTh;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getActive() {
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

    // Business logic methods

    /**
     * Check if this is a debit note code.
     */
    public boolean isDebitNote() {
        return "DebitNote".equals(documentType);
    }

    /**
     * Check if this is a credit note code.
     */
    public boolean isCreditNote() {
        return "CreditNote".equals(documentType);
    }

    /**
     * Check if this is a tax invoice code.
     */
    public boolean isTaxInvoice() {
        return "TaxInvoice".equals(documentType);
    }

    /**
     * Check if this is a receipt code.
     */
    public boolean isReceipt() {
        return "Receipt".equals(documentType);
    }

    /**
     * Check if this is a goods-related code.
     */
    public boolean isGoods() {
        return "Goods".equals(category);
    }

    /**
     * Check if this is a service-related code.
     */
    public boolean isService() {
        return "Service".equals(category);
    }

    /**
     * Check if this is an original document (code ends with 01).
     */
    public boolean isOriginal() {
        return code != null && code.endsWith("01");
    }

    /**
     * Check if this is a replacement document (code ends with 02).
     */
    public boolean isReplacement() {
        return code != null && code.endsWith("02");
    }

    /**
     * Check if this is a cancellation document (code ends with 03).
     */
    public boolean isCancellation() {
        return code != null && code.endsWith("03");
    }

    /**
     * Check if this is a copy document (code ends with 04).
     */
    public boolean isCopy() {
        return code != null && code.endsWith("04");
    }

    /**
     * Check if this is an addition document (code ends with 05).
     */
    public boolean isAddition() {
        return code != null && code.endsWith("05");
    }

    /**
     * Check if this is an "other" type document (code ends with 99).
     */
    public boolean isOther() {
        return code != null && code.endsWith("99");
    }

    /**
     * Get the document type prefix (first 4 characters).
     * Examples: DBNG, DBNS, CDNG, CDNS, TIVC, RCTC
     */
    public String getDocumentPrefix() {
        return code != null && code.length() >= 4 ? code.substring(0, 4) : null;
    }

    /**
     * Get the function suffix (last 2 characters).
     * Examples: 01, 02, 03, 04, 05, 99
     */
    public String getFunctionSuffix() {
        return code != null && code.length() >= 6 ? code.substring(4) : null;
    }

    /**
     * Check if this code is for debit note - goods.
     */
    public boolean isDebitNoteGoods() {
        return code != null && code.startsWith("DBNG");
    }

    /**
     * Check if this code is for debit note - services.
     */
    public boolean isDebitNoteServices() {
        return code != null && code.startsWith("DBNS");
    }

    /**
     * Check if this code is for credit note - goods.
     */
    public boolean isCreditNoteGoods() {
        return code != null && code.startsWith("CDNG");
    }

    /**
     * Check if this code is for credit note - services.
     */
    public boolean isCreditNoteServices() {
        return code != null && code.startsWith("CDNS");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThaiMessageFunctionCode)) return false;
        ThaiMessageFunctionCode that = (ThaiMessageFunctionCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ThaiMessageFunctionCode{" +
                "code='" + code + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", descriptionTh='" + descriptionTh + '\'' +
                ", documentType='" + documentType + '\'' +
                ", category='" + category + '\'' +
                ", active=" + active +
                '}';
    }
}
