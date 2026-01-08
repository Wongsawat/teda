package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JPA Entity for UN/CEFACT Document Name Code (Invoice)
 *
 * UN/CEFACT: Code List 1001 (DocumentNameCode), Version D14A
 * Total Codes: 17 (80, 81, 82, 83, 84, 261, 262, 325, 380, 381, 383, 384, 385, 386, 389, 395, 396)
 *
 * Categories:
 * - Invoice: 82, 380, 384, 385, 386, 389
 * - Credit Note: 81, 83, 261, 262, 381, 396
 * - Debit Note: 80, 84, 383
 * - Special: 325, 395
 */
@Entity
@Table(name = "document_name_code_invoice")
public class UNECEDocumentNameCodeInvoice {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "is_credit", nullable = false)
    private Boolean isCredit = false;

    @Column(name = "is_debit", nullable = false)
    private Boolean isDebit = false;

    @Column(name = "requires_payment", nullable = false)
    private Boolean requiresPayment = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public UNECEDocumentNameCodeInvoice() {}

    public UNECEDocumentNameCodeInvoice(String code) {
        this.code = code;
    }

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

    // Business Logic Methods
    public boolean isInvoice() {
        return "Invoice".equals(category);
    }

    public boolean isCreditNote() {
        return "Credit Note".equals(category) || Boolean.TRUE.equals(isCredit);
    }

    public boolean isDebitNote() {
        return "Debit Note".equals(category) || Boolean.TRUE.equals(isDebit);
    }

    public boolean isSpecialDocument() {
        return "Special".equals(category);
    }

    // Common document checks
    public boolean isCommercialInvoice() {
        return "380".equals(code);
    }

    public boolean isCreditNoteDocument() {
        return "381".equals(code);
    }

    public boolean isDebitNoteDocument() {
        return "383".equals(code);
    }

    public boolean isProformaInvoice() {
        return "325".equals(code);
    }

    public boolean isPrepaymentInvoice() {
        return "386".equals(code);
    }

    public boolean isSelfBilledInvoice() {
        return "389".equals(code);
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Boolean isCredit) {
        this.isCredit = isCredit;
    }

    public Boolean getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(Boolean isDebit) {
        this.isDebit = isDebit;
    }

    public Boolean getRequiresPayment() {
        return requiresPayment;
    }

    public void setRequiresPayment(Boolean requiresPayment) {
        this.requiresPayment = requiresPayment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UNECEDocumentNameCodeInvoice that = (UNECEDocumentNameCodeInvoice) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "UNECEDocumentNameCodeInvoice{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
