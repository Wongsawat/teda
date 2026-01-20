package com.wpanther.etax.core.xml.country;

import com.wpanther.etax.core.adapter.common.ISOCountryCodeAdapter;
import com.wpanther.etax.core.entity.ISOCountryCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for ISO Two-letter Country Code
 * Replaces the generated enum with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated enum
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISOTwoletterCountryCodeContentType",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
public class ISOTwoletterCountryCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOCountryCodeAdapter.class)
    private ISOCountryCode value;

    // Constructors
    public ISOTwoletterCountryCodeType() {
    }

    public ISOTwoletterCountryCodeType(ISOCountryCode value) {
        this.value = value;
    }

    public ISOTwoletterCountryCodeType(String code) {
        this.value = new ISOCountryCode(code);
    }

    // Getter and Setter
    public ISOCountryCode getValue() {
        return value;
    }

    public void setValue(ISOCountryCode value) {
        this.value = value;
    }

    /**
     * Get the 2-letter country code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the country name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Check if this is an ETDA extension code
     */
    public boolean isEtdaExtension() {
        return value != null && Boolean.TRUE.equals(value.getEtdaExtension());
    }

    /**
     * Check if the country code is active
     */
    public boolean isActive() {
        return value != null && Boolean.TRUE.equals(value.isActive());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOTwoletterCountryCodeType)) return false;
        ISOTwoletterCountryCodeType that = (ISOTwoletterCountryCodeType) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null ? value.getCode() + " (" + value.getName() + ")" : "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static ISOTwoletterCountryCodeType of(String code) {
        return new ISOTwoletterCountryCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ISOTwoletterCountryCodeType of(ISOCountryCode entity) {
        return new ISOTwoletterCountryCodeType(entity);
    }
}
