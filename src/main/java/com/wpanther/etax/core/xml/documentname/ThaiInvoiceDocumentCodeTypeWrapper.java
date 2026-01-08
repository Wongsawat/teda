package com.wpanther.etax.core.xml.documentname;

import com.wpanther.etax.core.adapter.common.ThaiDocumentNameCodeAdapter;
import com.wpanther.etax.core.entity.ThaiDocumentNameCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

/**
 * Custom JAXB implementation for Thai Invoice Document Code
 * Database-backed implementation for Thai document type codes
 *
 * This class:
 * - Maintains XML structure for Thai invoice document codes
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles document type codes: 80, 81, 82, 380, 388, T01-T07
 *
 * Note: This is an alternative to ThaiDocumentNameCodeInvoiceType with additional attributes support
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThaiInvoiceDocumentCodeType", propOrder = {"value"})
public class ThaiInvoiceDocumentCodeTypeWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlValue
    @XmlJavaTypeAdapter(ThaiDocumentNameCodeAdapter.class)
    protected ThaiDocumentNameCode value;

    @XmlAttribute(name = "listID")
    protected String listID;

    @XmlAttribute(name = "listAgencyID")
    protected String listAgencyID;

    @XmlAttribute(name = "listVersionID")
    protected String listVersionID;

    @XmlAttribute(name = "listURI")
    protected String listURI;

    // Constructors
    public ThaiInvoiceDocumentCodeTypeWrapper() {
    }

    public ThaiInvoiceDocumentCodeTypeWrapper(ThaiDocumentNameCode value) {
        this.value = value;
    }

    public ThaiInvoiceDocumentCodeTypeWrapper(String code) {
        this.value = new ThaiDocumentNameCode(code);
    }

    // Value getters/setters
    public String getValue() {
        return value != null ? value.getCode() : null;
    }

    public void setValue(String code) {
        if (code != null) {
            this.value = new ThaiDocumentNameCode(code);
        } else {
            this.value = null;
        }
    }

    // Additional getter/setter for entity
    public ThaiDocumentNameCode getEntity() {
        return value;
    }

    public void setEntity(ThaiDocumentNameCode value) {
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
     * Get the document code (e.g., "80", "388", "T01")
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
     * Check if this is a standard UN/CEFACT code
     */
    public boolean isStandardCode() {
        return value != null && Boolean.TRUE.equals(value.getStandardCode());
    }

    /**
     * Check if this is a Thai extension code (T01-T07)
     */
    public boolean isThaiExtension() {
        return value != null && Boolean.TRUE.equals(value.getThaiExtension());
    }

    /**
     * Check if this is a debit note (code 80)
     */
    public boolean isDebitNote() {
        return value != null && value.isDebitNote();
    }

    /**
     * Check if this is a credit note (code 81)
     */
    public boolean isCreditNote() {
        return value != null && value.isCreditNote();
    }

    /**
     * Check if this is a commercial invoice (code 380)
     */
    public boolean isCommercialInvoice() {
        return value != null && value.isCommercialInvoice();
    }

    /**
     * Check if this is a tax invoice type
     */
    public boolean isTaxInvoice() {
        return value != null && value.isTaxInvoice();
    }

    /**
     * Check if this is a receipt type
     */
    public boolean isReceipt() {
        return value != null && value.isReceipt();
    }

    /**
     * Check if this is an abbreviated tax invoice (T05, T06)
     */
    public boolean isAbbreviatedTaxInvoice() {
        return value != null && value.isAbbreviatedTaxInvoice();
    }

    /**
     * Check if this is a cancellation note (T07)
     */
    public boolean isCancellationNote() {
        return value != null && value.isCancellationNote();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThaiInvoiceDocumentCodeTypeWrapper)) return false;
        ThaiInvoiceDocumentCodeTypeWrapper that = (ThaiInvoiceDocumentCodeTypeWrapper) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (value == null) return "null";
        return value.getCode() + " (" + value.getNameEn() +
               (value.getNameTh() != null ? " / " + value.getNameTh() : "") + ")";
    }

    /**
     * Static factory method to create from code string
     */
    public static ThaiInvoiceDocumentCodeTypeWrapper of(String code) {
        return new ThaiInvoiceDocumentCodeTypeWrapper(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ThaiInvoiceDocumentCodeTypeWrapper of(ThaiDocumentNameCode entity) {
        return new ThaiInvoiceDocumentCodeTypeWrapper(entity);
    }
}
