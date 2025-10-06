package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for ISO 3166-1 alpha-2 Country Codes
 * Replaces the generated JAXB enum with database-backed implementation
 */
@Entity
@Table(name = "iso_country_code", indexes = {
    @Index(name = "idx_iso_country_code_name", columnList = "name"),
    @Index(name = "idx_iso_country_code_active", columnList = "is_active")
})
public class ISOCountryCode {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_etda_extension", nullable = false)
    private Boolean etdaExtension = false;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

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
    public ISOCountryCode() {
    }

    public ISOCountryCode(String code) {
        this.code = code != null ? code.toUpperCase() : null;
    }

    public ISOCountryCode(String code, String name) {
        this.code = code != null ? code.toUpperCase() : null;
        this.name = name;
    }

    public ISOCountryCode(String code, String name, String description) {
        this.code = code != null ? code.toUpperCase() : null;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code != null ? code.toUpperCase() : null;
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

    public Boolean getEtdaExtension() {
        return etdaExtension;
    }

    public void setEtdaExtension(Boolean etdaExtension) {
        this.etdaExtension = etdaExtension;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOCountryCode)) return false;
        ISOCountryCode that = (ISOCountryCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    // Business Logic Methods

    /**
     * Check if this is Thailand (TH)
     */
    public boolean isThailand() {
        return "TH".equals(code);
    }

    /**
     * Check if this is an ASEAN country
     * ASEAN members: TH, BN, KH, ID, LA, MY, MM, PH, SG, VN
     */
    public boolean isASEANCountry() {
        return "TH".equals(code) || "BN".equals(code) || "KH".equals(code) ||
               "ID".equals(code) || "LA".equals(code) || "MY".equals(code) ||
               "MM".equals(code) || "PH".equals(code) || "SG".equals(code) ||
               "VN".equals(code);
    }

    /**
     * Check if this is a major trading partner of Thailand
     * Major partners: CN, JP, KR, US, GB, DE, AU, IN, TW, HK, SG
     */
    public boolean isMajorTradingPartner() {
        return "CN".equals(code) || "JP".equals(code) || "KR".equals(code) ||
               "US".equals(code) || "GB".equals(code) || "DE".equals(code) ||
               "AU".equals(code) || "IN".equals(code) || "TW".equals(code) ||
               "HK".equals(code) || "SG".equals(code);
    }

    /**
     * Check if this is an ETDA custom extension (AN, KS, UN)
     */
    public boolean isETDAExtension() {
        return Boolean.TRUE.equals(etdaExtension);
    }

    /**
     * Check if this is a standard ISO 3166-1 code
     */
    public boolean isStandardISO() {
        return !Boolean.TRUE.equals(etdaExtension);
    }

    /**
     * Check if this is China (CN)
     */
    public boolean isChina() {
        return "CN".equals(code);
    }

    /**
     * Check if this is Japan (JP)
     */
    public boolean isJapan() {
        return "JP".equals(code);
    }

    /**
     * Check if this is South Korea (KR)
     */
    public boolean isSouthKorea() {
        return "KR".equals(code);
    }

    /**
     * Check if this is United States (US)
     */
    public boolean isUnitedStates() {
        return "US".equals(code);
    }

    /**
     * Check if this is Singapore (SG)
     */
    public boolean isSingapore() {
        return "SG".equals(code);
    }

    /**
     * Check if this is Malaysia (MY)
     */
    public boolean isMalaysia() {
        return "MY".equals(code);
    }

    /**
     * Check if this is Indonesia (ID)
     */
    public boolean isIndonesia() {
        return "ID".equals(code);
    }

    /**
     * Check if this is Vietnam (VN)
     */
    public boolean isVietnam() {
        return "VN".equals(code);
    }

    /**
     * Check if this is Philippines (PH)
     */
    public boolean isPhilippines() {
        return "PH".equals(code);
    }

    @Override
    public String toString() {
        return "ISOCountryCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", etdaExtension=" + etdaExtension +
                ", active=" + active +
                '}';
    }
}
