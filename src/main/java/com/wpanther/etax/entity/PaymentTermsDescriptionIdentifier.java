package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JPA Entity for UN/CEFACT Payment Terms Description Identifier (Code List 64277)
 *
 * This entity stores payment terms description identifiers from the UNECE D14A standard.
 * These codes specify the type of draft (bill of exchange) used in letter of credit transactions.
 *
 * Total codes: 7 (1-7)
 * Categories: Draft on banks (1-3), Draft on applicant/other (4-5), No draft (6-7)
 *
 * Database table: payment_terms_description_identifier
 * Primary key: code (VARCHAR 2)
 */
@Entity
@Table(name = "payment_terms_description_identifier",
       indexes = {
           @Index(name = "idx_payment_terms_name", columnList = "name"),
           @Index(name = "idx_payment_terms_is_draft_required", columnList = "is_draft_required")
       })
public class PaymentTermsDescriptionIdentifier {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_draft_required", nullable = false)
    private Boolean isDraftRequired = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public PaymentTermsDescriptionIdentifier() {
    }

    public PaymentTermsDescriptionIdentifier(String code) {
        this.code = code != null ? code.trim() : null;
    }

    public PaymentTermsDescriptionIdentifier(String code, String name, String description) {
        this.code = code != null ? code.trim() : null;
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
            this.code = this.code.trim();
        }
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code != null ? code.trim() : null;
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

    public Boolean getIsDraftRequired() {
        return isDraftRequired;
    }

    public void setIsDraftRequired(Boolean isDraftRequired) {
        this.isDraftRequired = isDraftRequired;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Business logic methods
    public boolean isDraftRequired() {
        return Boolean.TRUE.equals(this.isDraftRequired);
    }

    /**
     * Check if this is a banking draft (codes 1-3)
     */
    public boolean isBankingDraft() {
        if (code == null) return false;
        return "1".equals(code) || "2".equals(code) || "3".equals(code);
    }

    /**
     * Check if draft is on issuing bank (code 1)
     */
    public boolean isIssuingBank() {
        return "1".equals(code);
    }

    /**
     * Check if draft is on advising bank (code 2)
     */
    public boolean isAdvisingBank() {
        return "2".equals(code);
    }

    /**
     * Check if draft is on reimbursing bank (code 3)
     */
    public boolean isReimbursingBank() {
        return "3".equals(code);
    }

    /**
     * Check if no draft required (code 6)
     */
    public boolean isNoDraft() {
        return "6".equals(code);
    }

    // equals and hashCode based on code (primary key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTermsDescriptionIdentifier)) return false;
        PaymentTermsDescriptionIdentifier that = (PaymentTermsDescriptionIdentifier) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "PaymentTermsDescriptionIdentifier{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", isDraftRequired=" + isDraftRequired +
                '}';
    }
}
