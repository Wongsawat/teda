package com.wpanther.etax.core.xml.documentname;

import com.wpanther.etax.core.adapter.ThaiDocumentNameCodeAdapter;
import com.wpanther.etax.core.entity.ThaiDocumentNameCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for Thai Document Name Code (Invoice)
 * Replaces the generated ThaiDocumentNameCodeInvoiceContentType enum with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated enum
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles document type codes: 80, 81, 82, 380, 388, T01-T07
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThaiDocumentNameCodeInvoiceContentType",
         namespace = "urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1")
public class ThaiDocumentNameCodeInvoiceType {

    @XmlValue
    @XmlJavaTypeAdapter(ThaiDocumentNameCodeAdapter.class)
    private ThaiDocumentNameCode value;

    // Constructors
    public ThaiDocumentNameCodeInvoiceType() {
    }

    public ThaiDocumentNameCodeInvoiceType(ThaiDocumentNameCode value) {
        this.value = value;
    }

    public ThaiDocumentNameCodeInvoiceType(String code) {
        this.value = new ThaiDocumentNameCode(code);
    }

    // Getter and Setter
    public ThaiDocumentNameCode getValue() {
        return value;
    }

    public void setValue(ThaiDocumentNameCode value) {
        this.value = value;
    }

    /**
     * Get the document code string (80, 81, 82, 380, 388, T01-T07)
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the document name in Thai
     */
    public String getNameTh() {
        return value != null ? value.getNameTh() : null;
    }

    /**
     * Get the document name in English
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
        if (!(o instanceof ThaiDocumentNameCodeInvoiceType)) return false;
        ThaiDocumentNameCodeInvoiceType that = (ThaiDocumentNameCodeInvoiceType) o;
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
    public static ThaiDocumentNameCodeInvoiceType of(String code) {
        return new ThaiDocumentNameCodeInvoiceType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static ThaiDocumentNameCodeInvoiceType of(ThaiDocumentNameCode entity) {
        return new ThaiDocumentNameCodeInvoiceType(entity);
    }
}
