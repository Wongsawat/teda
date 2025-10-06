package com.wpanther.etax.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity for UN/CEFACT Reference Type Codes (Code List 61153)
 * Replaces the generated JAXB String type with database-backed implementation
 *
 * Reference Type Codes identify the type of reference document in e-Tax Invoice transactions.
 * This includes standard UN/CEFACT codes (AAA-ZZZ) and Thai ETDA extensions (80, 81, 380, 388, T01-T07).
 */
@Entity
@Table(name = "unece_reference_type_code", indexes = {
    @Index(name = "idx_unece_reference_type_code_name", columnList = "name"),
    @Index(name = "idx_unece_reference_type_code_is_etda_extension", columnList = "is_etda_extension"),
    @Index(name = "idx_unece_reference_type_code_is_active", columnList = "is_active")
})
public class UNECEReferenceTypeCode {

    @Id
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "name", nullable = false, length = 500)
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
    public UNECEReferenceTypeCode() {
    }

    public UNECEReferenceTypeCode(String code) {
        this.code = code != null ? code.toUpperCase() : null;
    }

    public UNECEReferenceTypeCode(String code, String name) {
        this.code = code != null ? code.toUpperCase() : null;
        this.name = name;
    }

    public UNECEReferenceTypeCode(String code, String name, String description) {
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
        if (!(o instanceof UNECEReferenceTypeCode)) return false;
        UNECEReferenceTypeCode that = (UNECEReferenceTypeCode) o;
        return code != null && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UNECEReferenceTypeCode{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", etdaExtension=" + etdaExtension +
                ", active=" + active +
                '}';
    }
}
