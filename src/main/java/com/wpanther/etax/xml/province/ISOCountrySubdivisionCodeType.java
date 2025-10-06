package com.wpanther.etax.xml.province;

import com.wpanther.etax.adapter.ThaiProvinceCodeAdapter;
import com.wpanther.etax.entity.ThaiProvinceCode;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for ISO Country Subdivision Code (Thai Provinces)
 * Replaces the generated ObjectFactory method with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated factory method
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISOCountrySubdivisionCodeContentType",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
@XmlRootElement(name = "ISOCountrySubdivisionCode",
                namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
public class ISOCountrySubdivisionCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ThaiProvinceCodeAdapter.class)
    private ThaiProvinceCode value;

    // Constructors
    public ISOCountrySubdivisionCodeType() {
    }

    public ISOCountrySubdivisionCodeType(ThaiProvinceCode value) {
        this.value = value;
    }

    public ISOCountrySubdivisionCodeType(String code) {
        this.value = new ThaiProvinceCode(code);
    }

    // Getter and Setter
    public ThaiProvinceCode getValue() {
        return value;
    }

    public void setValue(ThaiProvinceCode value) {
        this.value = value;
    }

    /**
     * Get the province code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the province Thai name
     */
    public String getNameTh() {
        return value != null ? value.getNameTh() : null;
    }

    /**
     * Get the province English name
     */
    public String getNameEn() {
        return value != null ? value.getNameEn() : null;
    }

    /**
     * Get the region
     */
    public String getRegion() {
        return value != null ? value.getRegion() : null;
    }

    /**
     * Check if the province code is active
     */
    public boolean isActive() {
        return value != null && Boolean.TRUE.equals(value.getActive());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOCountrySubdivisionCodeType)) return false;
        ISOCountrySubdivisionCodeType that = (ISOCountrySubdivisionCodeType) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null ? value.getCode() + " (" + value.getNameTh() + ")" : "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static ISOCountrySubdivisionCodeType of(String code) {
        return new ISOCountrySubdivisionCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ISOCountrySubdivisionCodeType of(ThaiProvinceCode entity) {
        return new ISOCountrySubdivisionCodeType(entity);
    }
}
