package com.wpanther.etax.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for ISO 639-1 Two-letter Language Code
 *
 * Represents international language codes for multilingual support in e-Tax Invoice documents.
 *
 * Standard: ISO 639-1 alpha-2
 * Code List Version: 2006-10-27
 * Schema Version: 1.0
 * Total Codes: 185
 *
 * Common Languages:
 * - th: Thai - Primary language for Thai e-Tax Invoice
 * - en: English - International business language
 * - zh: Chinese - Major trading partner
 * - ja: Japanese - Major trading partner
 * - id: Indonesian - ASEAN neighbor
 * - ms: Malay - ASEAN neighbor
 * - vi: Vietnamese - ASEAN neighbor
 */
@Entity
@Table(name = "iso_language_code")
public class ISOLanguageCode {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "code_upper", length = 2, insertable = false, updatable = false)
    private String codeUpper;

    @Column(name = "code_lower", length = 2, insertable = false, updatable = false)
    private String codeLower;

    @Column(name = "is_active")
    private Boolean isActive = true;

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
    public ISOLanguageCode() {
    }

    public ISOLanguageCode(String code) {
        this.code = normalizeCode(code);
    }

    public ISOLanguageCode(String code, String name) {
        this.code = normalizeCode(code);
        this.name = name;
    }

    // Code Normalization (lowercase is standard)
    private String normalizeCode(String code) {
        if (code == null) {
            return null;
        }
        // Language codes are stored in lowercase
        return code.trim().toLowerCase();
    }

    // Business Logic Methods

    /**
     * Check if this is Thai language (th)
     */
    public boolean isThai() {
        return "th".equals(code);
    }

    /**
     * Check if this is English language (en)
     */
    public boolean isEnglish() {
        return "en".equals(code);
    }

    /**
     * Check if this is Chinese language (zh)
     */
    public boolean isChinese() {
        return "zh".equals(code);
    }

    /**
     * Check if this is Japanese language (ja)
     */
    public boolean isJapanese() {
        return "ja".equals(code);
    }

    /**
     * Check if this is an ASEAN language
     * (th, en, ms, id, vi, my, km, lo, tl)
     */
    public boolean isASEANLanguage() {
        return "th".equals(code) || "en".equals(code) || "ms".equals(code) ||
               "id".equals(code) || "vi".equals(code) || "my".equals(code) ||
               "km".equals(code) || "lo".equals(code) || "tl".equals(code);
    }

    /**
     * Check if this is a major trading partner language
     * (en, th, zh, ja, ko, de, fr, es, ar, ru)
     */
    public boolean isMajorTradingLanguage() {
        return "en".equals(code) || "th".equals(code) || "zh".equals(code) ||
               "ja".equals(code) || "ko".equals(code) || "de".equals(code) ||
               "fr".equals(code) || "es".equals(code) || "ar".equals(code) ||
               "ru".equals(code);
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

    public String getCodeUpper() {
        return codeUpper;
    }

    public String getCodeLower() {
        return codeLower;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        if (!(o instanceof ISOLanguageCode)) return false;
        ISOLanguageCode that = (ISOLanguageCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ISOLanguageCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
