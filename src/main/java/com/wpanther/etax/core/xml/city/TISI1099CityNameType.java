package com.wpanther.etax.core.xml.city;

import com.wpanther.etax.core.adapter.taxinvoice.TISICityNameAdapter;
import com.wpanther.etax.core.entity.TISICityName;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for TISI 1099-2548 City Name
 * Replaces the generated TISI1099CityName interface with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated interface
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:identifierlist:standard:CityNameFromTISI1099_2548
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TISI1099CityName",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:CityNameFromTISI1099_2548")
public class TISI1099CityNameType {

    @XmlValue
    @XmlJavaTypeAdapter(TISICityNameAdapter.class)
    private TISICityName value;

    // Constructors
    public TISI1099CityNameType() {
    }

    public TISI1099CityNameType(TISICityName value) {
        this.value = value;
    }

    public TISI1099CityNameType(String code) {
        this.value = new TISICityName(code);
    }

    // Getter and Setter
    public TISICityName getValue() {
        return value;
    }

    public void setValue(TISICityName value) {
        this.value = value;
    }

    /**
     * Get the city code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the city Thai name
     */
    public String getNameTh() {
        return value != null ? value.getNameTh() : null;
    }

    /**
     * Get the province code (first 2 digits)
     */
    public String getProvinceCode() {
        return value != null ? value.getProvinceCode() : null;
    }

    /**
     * Get the district code (last 2 digits)
     */
    public String getDistrictCode() {
        return value != null ? value.getDistrictCode() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TISI1099CityNameType)) return false;
        TISI1099CityNameType that = (TISI1099CityNameType) o;
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
    public static TISI1099CityNameType of(String code) {
        return new TISI1099CityNameType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static TISI1099CityNameType of(TISICityName entity) {
        return new TISI1099CityNameType(entity);
    }
}
