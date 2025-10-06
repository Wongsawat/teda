package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for TISI 1099-2548 Subdistrict Names (Thai Tambon/Khwaeng)
 * Based on TISICitySubDivisionName_1p0.xsd
 *
 * Contains 8,940 Thai subdistrict names for e-Tax Invoice addresses
 * This is the most granular level of Thai address hierarchy
 */
@Entity
@Table(name = "tisi_subdistrict", indexes = {
    @Index(name = "idx_tisi_subdistrict_name_th", columnList = "name_th"),
    @Index(name = "idx_tisi_subdistrict_province_code", columnList = "province_code"),
    @Index(name = "idx_tisi_subdistrict_city_code", columnList = "city_code"),
    @Index(name = "idx_tisi_subdistrict_code_pattern", columnList = "subdistrict_code")
})
public class TISISubdistrict {

    @Id
    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "name_th", nullable = false, length = 255)
    private String nameTh;

    @Column(name = "province_code", length = 2, insertable = false, updatable = false)
    private String provinceCode;

    @Column(name = "city_code", length = 4, insertable = false, updatable = false)
    private String cityCode;

    @Column(name = "subdistrict_code", length = 2, insertable = false, updatable = false)
    private String subdistrictCode;

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
    public TISISubdistrict() {
    }

    public TISISubdistrict(String code) {
        this.code = code;
    }

    public TISISubdistrict(String code, String nameTh) {
        this.code = code;
        this.nameTh = nameTh;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getSubdistrictCode() {
        return subdistrictCode;
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
        if (!(o instanceof TISISubdistrict)) return false;
        TISISubdistrict that = (TISISubdistrict) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TISISubdistrict{" +
                "code='" + code + '\'' +
                ", nameTh='" + nameTh + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", subdistrictCode='" + subdistrictCode + '\'' +
                '}';
    }
}
