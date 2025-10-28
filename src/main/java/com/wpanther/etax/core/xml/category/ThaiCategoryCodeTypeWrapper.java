package com.wpanther.etax.core.xml.category;

import com.wpanther.etax.core.adapter.ThaiCategoryCodeAdapter;
import com.wpanther.etax.core.entity.ThaiCategoryCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

/**
 * Custom JAXB implementation for Thai Category Code
 * Database-backed implementation for Thai document reference categories
 *
 * This class:
 * - Maintains XML structure for Thai category codes
 * - Uses XmlAdapter to fetch values from database (2 category codes)
 * - Preserves namespace: urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles document reference categories for Thai e-Tax Invoice system
 *
 * Note: This is an alternative to ThaiCategoryCodeType with additional attributes support
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThaiCategoryCodeType", propOrder = {"value"})
public class ThaiCategoryCodeTypeWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlValue
    @XmlJavaTypeAdapter(ThaiCategoryCodeAdapter.class)
    protected ThaiCategoryCode value;

    @XmlAttribute(name = "listID")
    protected String listID;

    @XmlAttribute(name = "listAgencyID")
    protected String listAgencyID;

    @XmlAttribute(name = "listVersionID")
    protected String listVersionID;

    @XmlAttribute(name = "listURI")
    protected String listURI;

    // Constructors
    public ThaiCategoryCodeTypeWrapper() {
    }

    public ThaiCategoryCodeTypeWrapper(ThaiCategoryCode value) {
        this.value = value;
    }

    public ThaiCategoryCodeTypeWrapper(String code) {
        this.value = new ThaiCategoryCode(code);
    }

    // Value getters/setters
    public String getValue() {
        return value != null ? value.getCode() : null;
    }

    public void setValue(String code) {
        if (code != null) {
            this.value = new ThaiCategoryCode(code);
        } else {
            this.value = null;
        }
    }

    // Additional getter/setter for entity
    public ThaiCategoryCode getEntity() {
        return value;
    }

    public void setEntity(ThaiCategoryCode value) {
        this.value = value;
    }

    // Attribute getters/setters
    public String getListID() {
        return listID;
    }

    public void setListID(String value) {
        this.listID = value;
    }

    public String getListAgencyID() {
        return listAgencyID;
    }

    public void setListAgencyID(String value) {
        this.listAgencyID = value;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public void setListVersionID(String value) {
        this.listVersionID = value;
    }

    public String getListURI() {
        return listURI;
    }

    public void setListURI(String value) {
        this.listURI = value;
    }

    // Helper methods
    /**
     * Get the category code (e.g., "01", "02")
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the Thai name
     */
    public String getNameTh() {
        return value != null ? value.getNameTh() : null;
    }

    /**
     * Get the English name
     */
    public String getNameEn() {
        return value != null ? value.getNameEn() : null;
    }

    /**
     * Get the description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Check if this is code 01 - Reference to original document for cancellation/debit/credit
     */
    public boolean isOriginalDocumentReference() {
        return value != null && value.isOriginalDocumentReference();
    }

    /**
     * Check if this is code 02 - Reference to document for advance payment
     */
    public boolean isAdvancePaymentReference() {
        return value != null && value.isAdvancePaymentReference();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThaiCategoryCodeTypeWrapper)) return false;
        ThaiCategoryCodeTypeWrapper that = (ThaiCategoryCodeTypeWrapper) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null ? value.getCode() + " (" + value.getNameEn() + ")" : "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static ThaiCategoryCodeTypeWrapper of(String code) {
        return new ThaiCategoryCodeTypeWrapper(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ThaiCategoryCodeTypeWrapper of(ThaiCategoryCode entity) {
        return new ThaiCategoryCodeTypeWrapper(entity);
    }
}
