package com.wpanther.etax.core.xml.addresstype;

import com.wpanther.etax.core.adapter.common.AddressTypeAdapter;
import com.wpanther.etax.core.entity.AddressType;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Address Type Code
 * Replaces the generated JAXBElement<String> type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 3 address type codes (1=Postal, 2=Fiscal, 3=Physical)
 *
 * UN/CEFACT Code List: 3131
 * Schema Version: D14A
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressTypeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A")
public class AddressTypeType {

    @XmlValue
    @XmlJavaTypeAdapter(AddressTypeAdapter.class)
    private AddressType value;

    // Constructors
    public AddressTypeType() {
    }

    public AddressTypeType(AddressType value) {
        this.value = value;
    }

    public AddressTypeType(String code) {
        this.value = new AddressType(code);
    }

    // Getter and Setter
    public AddressType getValue() {
        return value;
    }

    public void setValue(AddressType value) {
        this.value = value;
    }

    /**
     * Get the address type code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the address type name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the address type description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Check if this is a postal address type
     */
    public boolean isPostalAddress() {
        return value != null && value.isPostalAddress();
    }

    /**
     * Check if this is a fiscal address type
     */
    public boolean isFiscalAddress() {
        return value != null && value.isFiscalAddress();
    }

    /**
     * Check if this is a physical address type
     */
    public boolean isPhysicalAddress() {
        return value != null && value.isPhysicalAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressTypeType)) return false;
        AddressTypeType that = (AddressTypeType) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.getCode() + " (" + value.getName() + ")";
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static AddressTypeType of(String code) {
        return new AddressTypeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static AddressTypeType of(AddressType entity) {
        return new AddressTypeType(entity);
    }
}
