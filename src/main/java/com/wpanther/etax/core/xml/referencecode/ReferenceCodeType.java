package com.wpanther.etax.core.xml.referencecode;

import com.wpanther.etax.core.adapter.taxinvoice.UNECEReferenceTypeCodeAdapter;
import com.wpanther.etax.core.entity.UNECEReferenceTypeCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Reference Type Code
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles standard UN/CEFACT codes (AAA-ZZZ) and Thai ETDA extensions (80, 81, 380, 388, T01-T07)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceTypeCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A")
public class ReferenceCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(UNECEReferenceTypeCodeAdapter.class)
    private UNECEReferenceTypeCode value;

    // Constructors
    public ReferenceCodeType() {
    }

    public ReferenceCodeType(UNECEReferenceTypeCode value) {
        this.value = value;
    }

    public ReferenceCodeType(String code) {
        this.value = new UNECEReferenceTypeCode(code);
    }

    // Getter and Setter
    public UNECEReferenceTypeCode getValue() {
        return value;
    }

    public void setValue(UNECEReferenceTypeCode value) {
        this.value = value;
    }

    /**
     * Get the reference type code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the reference type name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the reference type description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Check if this is a Thai ETDA extension code
     */
    public boolean isEtdaExtension() {
        return value != null && Boolean.TRUE.equals(value.getEtdaExtension());
    }

    /**
     * Check if the reference type code is active
     */
    public boolean isActive() {
        return value != null && Boolean.TRUE.equals(value.getActive());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceCodeType)) return false;
        ReferenceCodeType that = (ReferenceCodeType) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (value != null) {
            String desc = value.getName() != null ? value.getName() : "Unknown";
            return value.getCode() + " (" + desc + ")";
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static ReferenceCodeType of(String code) {
        return new ReferenceCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ReferenceCodeType of(UNECEReferenceTypeCode entity) {
        return new ReferenceCodeType(entity);
    }
}
