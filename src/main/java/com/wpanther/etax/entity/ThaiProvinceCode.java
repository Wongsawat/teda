package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for Thai ISO Country Subdivision Codes (Provinces)
 * Based on ThaiISOCountrySubdivisionCode_1p0.xsd
 *
 * Contains 77 Thai provinces with their codes and names
 */
@Entity
@Table(name = "thai_province_code", indexes = {
    @Index(name = "idx_thai_province_name", columnList = "name_th"),
    @Index(name = "idx_thai_province_name_en", columnList = "name_en"),
    @Index(name = "idx_thai_province_active", columnList = "is_active")
})
public class ThaiProvinceCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name_th", nullable = false, length = 200)
    private String nameTh;

    @Column(name = "name_en", length = 200)
    private String nameEn;

    @Column(name = "region", length = 50)
    private String region; // Central, North, Northeast, South, East, West

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
    public ThaiProvinceCode() {
    }

    public ThaiProvinceCode(String code) {
        this.code = code;
    }

    public ThaiProvinceCode(String code, String nameTh) {
        this.code = code;
        this.nameTh = nameTh;
    }

    public ThaiProvinceCode(String code, String nameTh, String nameEn) {
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
        if (!(o instanceof ThaiProvinceCode)) return false;
        ThaiProvinceCode that = (ThaiProvinceCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ThaiProvinceCode{" +
                "code='" + code + '\'' +
                ", nameTh='" + nameTh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", region='" + region + '\'' +
                ", active=" + active +
                '}';
    }
}
