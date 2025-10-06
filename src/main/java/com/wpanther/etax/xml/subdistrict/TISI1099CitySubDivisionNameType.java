package com.wpanther.etax.xml.subdistrict;

import com.wpanther.etax.adapter.TISISubdistrictAdapter;
import com.wpanther.etax.entity.TISISubdistrict;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for TISI 1099-2548 City SubDivision Name (Subdistrict/Tambon)
 * Replaces the generated TISI1099CitySubDivisionName interface with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated interface
 * - Uses XmlAdapter to fetch values from database (8,940+ subdistricts)
 * - Preserves namespace: urn:un:unece:uncefact:identifierlist:standard:CitySubDivisionNameFromTISI1099_2548
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles the largest code list in Thai e-Tax Invoice schema
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TISI1099CitySubDivisionName",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:CitySubDivisionNameFromTISI1099_2548")
public class TISI1099CitySubDivisionNameType {

    @XmlValue
    @XmlJavaTypeAdapter(TISISubdistrictAdapter.class)
    private TISISubdistrict value;

    // Constructors
    public TISI1099CitySubDivisionNameType() {
    }

    public TISI1099CitySubDivisionNameType(TISISubdistrict value) {
        this.value = value;
    }

    public TISI1099CitySubDivisionNameType(String code) {
        this.value = new TISISubdistrict(code);
    }

    // Getter and Setter
    public TISISubdistrict getValue() {
        return value;
    }

    public void setValue(TISISubdistrict value) {
        this.value = value;
    }

    /**
     * Get the subdistrict code string (6 digits: PPDDSS)
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the subdistrict Thai name
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
     * Get the city code (first 4 digits)
     */
    public String getCityCode() {
        return value != null ? value.getCityCode() : null;
    }

    /**
     * Get the subdistrict code (last 2 digits)
     */
    public String getSubdistrictCode() {
        return value != null ? value.getSubdistrictCode() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TISI1099CitySubDivisionNameType)) return false;
        TISI1099CitySubDivisionNameType that = (TISI1099CitySubDivisionNameType) o;
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
    public static TISI1099CitySubDivisionNameType of(String code) {
        return new TISI1099CitySubDivisionNameType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static TISI1099CitySubDivisionNameType of(TISISubdistrict entity) {
        return new TISI1099CitySubDivisionNameType(entity);
    }
}
