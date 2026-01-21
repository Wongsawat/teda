package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JPA Entity for UN/CEFACT Message Function Code (Code List 61225)
 *
 * This entity stores message function codes from the UNECE D14A standard.
 * These codes indicate the purpose or action of business documents/messages in e-Tax Invoice transactions.
 *
 * Total codes: 65 (1-65)
 * Categories: Transaction Control, Message Status, Acceptance/Rejection, Financial, Schedule
 *
 * Database table: message_function_code
 * Primary key: code (VARCHAR 10)
 */
@Entity
@Table(name = "message_function_code",
       indexes = {
           @Index(name = "idx_message_function_code_name", columnList = "name"),
           @Index(name = "idx_message_function_code_category", columnList = "category"),
           @Index(name = "idx_message_function_code_is_modification", columnList = "is_modification"),
           @Index(name = "idx_message_function_code_is_original", columnList = "is_original")
       })
public class MessageFunctionCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "is_modification", nullable = false)
    private Boolean isModification = false;

    @Column(name = "is_original", nullable = false)
    private Boolean isOriginal = false;

    @Column(name = "is_acceptance", nullable = false)
    private Boolean isAcceptance = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public MessageFunctionCode() {
    }

    public MessageFunctionCode(String code) {
        this.code = code != null ? code.trim() : null;
        this.isOriginal = "9".equals(this.code);
        this.isModification = isModificationCode(this.code);
    }

    public MessageFunctionCode(String code, String name, String description) {
        this.code = code != null ? code.trim() : null;
        this.name = name;
        this.description = description;
        this.isOriginal = "9".equals(this.code);
        this.isModification = isModificationCode(this.code);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsModification() {
        return isModification;
    }

    public void setIsModification(Boolean isModification) {
        this.isModification = isModification;
    }

    public Boolean getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public Boolean getIsAcceptance() {
        return isAcceptance;
    }

    public void setIsAcceptance(Boolean isAcceptance) {
        this.isAcceptance = isAcceptance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Business logic methods
    /**
     * Check if code is a modification code
     */
    private boolean isModificationCode(String code) {
        if (code == null) return false;
        return "4".equals(code) || "19".equals(code) || "28".equals(code) ||
               "30".equals(code) || "33".equals(code) || "34".equals(code) ||
               "36".equals(code) || "52".equals(code);
    }

    public boolean isModification() {
        return Boolean.TRUE.equals(this.isModification);
    }

    public boolean isOriginal() {
        return Boolean.TRUE.equals(this.isOriginal);
    }

    public boolean isAcceptance() {
        return Boolean.TRUE.equals(this.isAcceptance);
    }

    /**
     * Check if this is a cancellation function (codes 1, 17, 18, 39)
     */
    public boolean isCancellation() {
        if (code == null) return false;
        return "1".equals(code) || "17".equals(code) || "18".equals(code) || "39".equals(code);
    }

    /**
     * Check if this is a change/amendment function (codes 4, 19, 28, 30, 33, 34, 36, 52)
     */
    public boolean isChange() {
        if (code == null) return false;
        return "4".equals(code) || "19".equals(code) || "28".equals(code) ||
               "30".equals(code) || "33".equals(code) || "34".equals(code) ||
               "36".equals(code) || "52".equals(code);
    }

    /**
     * Check if this is a replacement function (codes 5, 20, 21)
     */
    public boolean isReplacement() {
        if (code == null) return false;
        return "5".equals(code) || "20".equals(code) || "21".equals(code);
    }

    /**
     * Check if this is a confirmation function (codes 6, 42)
     */
    public boolean isConfirmation() {
        if (code == null) return false;
        return "6".equals(code) || "42".equals(code);
    }

    /**
     * Check if this is a financial reversal (codes 37, 38, 39)
     */
    public boolean isFinancialReversal() {
        if (code == null) return false;
        return "37".equals(code) || "38".equals(code) || "39".equals(code);
    }

    /**
     * Check if this is a schedule-related function (codes 24, 25, 26, 61, 62)
     */
    public boolean isSchedule() {
        if (code == null) return false;
        return "24".equals(code) || "25".equals(code) || "26".equals(code) ||
               "61".equals(code) || "62".equals(code);
    }

    // equals and hashCode based on code (primary key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageFunctionCode)) return false;
        MessageFunctionCode that = (MessageFunctionCode) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "MessageFunctionCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isModification=" + isModification +
                ", isOriginal=" + isOriginal +
                ", isAcceptance=" + isAcceptance +
                '}';
    }
}
