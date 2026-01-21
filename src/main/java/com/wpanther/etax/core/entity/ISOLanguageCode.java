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
    private Boolean active = true;

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
        this.code = code != null ? code.trim() : null;
    }

    public ISOLanguageCode(String code, String name) {
        this.code = code != null ? code.trim() : null;
        this.name = name;
    }

    // Business Logic Methods (case-insensitive)

    /**
     * Check if this is Thai language (th/TH)
     */
    public boolean isThai() {
        return "th".equalsIgnoreCase(code);
    }

    /**
     * Check if this is English language (en/EN)
     */
    public boolean isEnglish() {
        return "en".equalsIgnoreCase(code);
    }

    /**
     * Check if this is Chinese language (zh/ZH)
     */
    public boolean isChinese() {
        return "zh".equalsIgnoreCase(code);
    }

    /**
     * Check if this is Japanese language (ja/JA)
     */
    public boolean isJapanese() {
        return "ja".equalsIgnoreCase(code);
    }

    /**
     * Check if this is an ASEAN language
     * (th, en, ms, id, vi, my, km, lo, tl)
     */
    public boolean isASEANLanguage() {
        String lowerCode = code != null ? code.toLowerCase() : null;
        return "th".equals(lowerCode) || "en".equals(lowerCode) || "ms".equals(lowerCode) ||
               "id".equals(lowerCode) || "vi".equals(lowerCode) || "my".equals(lowerCode) ||
               "km".equals(lowerCode) || "lo".equals(lowerCode) || "tl".equals(lowerCode);
    }

    /**
     * Check if this is a major trading partner language
     * (en, th, zh, ja, ko, de, fr, es, ar, ru)
     */
    public boolean isMajorTradingLanguage() {
        String lowerCode = code != null ? code.toLowerCase() : null;
        return "en".equals(lowerCode) || "th".equals(lowerCode) || "zh".equals(lowerCode) ||
               "ja".equals(lowerCode) || "ko".equals(lowerCode) || "de".equals(lowerCode) ||
               "fr".equals(lowerCode) || "es".equals(lowerCode) || "ar".equals(lowerCode) ||
               "ru".equals(lowerCode);
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

    public String getCodeUpper() {
        return codeUpper;
    }

    public String getCodeLower() {
        return codeLower;
    }

    public Boolean isActive() {
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
                ", active=" + active +
                '}';
    }
}
