package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for TISI 1099-2548 City Names (Thai Districts/Cities)
 * Based on TISICityName_1p0.xsd
 *
 * Contains 1,916 Thai city/district names for e-Tax Invoice addresses
 */
@Entity
@Table(name = "tisi_city_name", indexes = {
    @Index(name = "idx_tisi_city_name_name_th", columnList = "name_th"),
    @Index(name = "idx_tisi_city_name_province_code", columnList = "province_code"),
    @Index(name = "idx_tisi_city_name_district_code", columnList = "district_code")
})
public class TISICityName {

    @Id
    @Column(name = "code", length = 4, nullable = false)
    private String code;

    @Column(name = "name_th", nullable = false, length = 255)
    private String nameTh;

    @Column(name = "province_code", length = 2, insertable = false, updatable = false)
    private String provinceCode;

    @Column(name = "district_code", length = 2, insertable = false, updatable = false)
    private String districtCode;

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
    public TISICityName() {
    }

    public TISICityName(String code) {
        this.code = code;
    }

    public TISICityName(String code, String nameTh) {
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

    public String getDistrictCode() {
        return districtCode;
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
        if (!(o instanceof TISICityName)) return false;
        TISICityName that = (TISICityName) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TISICityName{" +
                "code='" + code + '\'' +
                ", nameTh='" + nameTh + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                '}';
    }
}
