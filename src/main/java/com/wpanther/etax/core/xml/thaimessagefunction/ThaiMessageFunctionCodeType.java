package com.wpanther.etax.core.xml.thaimessagefunction;

import com.wpanther.etax.core.adapter.taxinvoice.ThaiMessageFunctionCodeAdapter;
import com.wpanther.etax.core.entity.ThaiMessageFunctionCode;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for Thai Message Function Code with database-backed implementation.
 *
 * This type replaces the generated JAXB enum with a database-backed entity,
 * enabling dynamic code list management while maintaining XML compatibility.
 *
 * Based on ETDA ThaiMessageFunctionCode_1p0.xsd standard.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThaiMessageFunctionCodeType", namespace = "urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560")
public class ThaiMessageFunctionCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ThaiMessageFunctionCodeAdapter.class)
    private ThaiMessageFunctionCode value;

    /**
     * Default constructor for JAXB.
     */
    public ThaiMessageFunctionCodeType() {
    }

    /**
     * Constructor with ThaiMessageFunctionCode entity.
     *
     * @param value The message function code entity
     */
    public ThaiMessageFunctionCodeType(ThaiMessageFunctionCode value) {
        this.value = value;
    }

    /**
     * Get the message function code entity.
     *
     * @return The message function code entity
     */
    public ThaiMessageFunctionCode getValue() {
        return value;
    }

    /**
     * Set the message function code entity.
     *
     * @param value The message function code entity
     */
    public void setValue(ThaiMessageFunctionCode value) {
        this.value = value;
    }

    /**
     * Get the code string.
     *
     * @return The code string or null
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the English description.
     *
     * @return The English description or null
     */
    public String getDescriptionEn() {
        return value != null ? value.getDescriptionEn() : null;
    }

    /**
     * Get the Thai description.
     *
     * @return The Thai description or null
     */
    public String getDescriptionTh() {
        return value != null ? value.getDescriptionTh() : null;
    }

    /**
     * Get the document type.
     *
     * @return The document type or null
     */
    public String getDocumentType() {
        return value != null ? value.getDocumentType() : null;
    }

    /**
     * Get the category.
     *
     * @return The category or null
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    // Factory methods for common codes

    /**
     * Create tax invoice original code (TIVC01).
     */
    public static ThaiMessageFunctionCodeType taxInvoiceOriginal() {
        return of("TIVC01");
    }

    /**
     * Create tax invoice replacement code (TIVC02).
     */
    public static ThaiMessageFunctionCodeType taxInvoiceReplacement() {
        return of("TIVC02");
    }

    /**
     * Create receipt original code (RCTC01).
     */
    public static ThaiMessageFunctionCodeType receiptOriginal() {
        return of("RCTC01");
    }

    /**
     * Create receipt replacement code (RCTC02).
     */
    public static ThaiMessageFunctionCodeType receiptReplacement() {
        return of("RCTC02");
    }

    /**
     * Create receipt copy code (RCTC03).
     */
    public static ThaiMessageFunctionCodeType receiptCopy() {
        return of("RCTC03");
    }

    /**
     * Create receipt cancellation code (RCTC04).
     */
    public static ThaiMessageFunctionCodeType receiptCancellation() {
        return of("RCTC04");
    }

    /**
     * Create debit note goods original code (DBNG01).
     */
    public static ThaiMessageFunctionCodeType debitNoteGoodsOriginal() {
        return of("DBNG01");
    }

    /**
     * Create debit note services original code (DBNS01).
     */
    public static ThaiMessageFunctionCodeType debitNoteServicesOriginal() {
        return of("DBNS01");
    }

    /**
     * Create credit note goods original code (CDNG01).
     */
    public static ThaiMessageFunctionCodeType creditNoteGoodsOriginal() {
        return of("CDNG01");
    }

    /**
     * Create credit note services original code (CDNS01).
     */
    public static ThaiMessageFunctionCodeType creditNoteServicesOriginal() {
        return of("CDNS01");
    }

    /**
     * Create ThaiMessageFunctionCodeType from code string.
     *
     * @param code The message function code
     * @return ThaiMessageFunctionCodeType instance
     */
    public static ThaiMessageFunctionCodeType of(String code) {
        return new ThaiMessageFunctionCodeType(ThaiMessageFunctionCodeAdapter.toEntity(code));
    }

    // Business logic delegation methods

    /**
     * Check if this is a debit note code.
     */
    public boolean isDebitNote() {
        return value != null && value.isDebitNote();
    }

    /**
     * Check if this is a credit note code.
     */
    public boolean isCreditNote() {
        return value != null && value.isCreditNote();
    }

    /**
     * Check if this is a tax invoice code.
     */
    public boolean isTaxInvoice() {
        return value != null && value.isTaxInvoice();
    }

    /**
     * Check if this is a receipt code.
     */
    public boolean isReceipt() {
        return value != null && value.isReceipt();
    }

    /**
     * Check if this is a goods-related code.
     */
    public boolean isGoods() {
        return value != null && value.isGoods();
    }

    /**
     * Check if this is a service-related code.
     */
    public boolean isService() {
        return value != null && value.isService();
    }

    /**
     * Check if this is an original document.
     */
    public boolean isOriginal() {
        return value != null && value.isOriginal();
    }

    /**
     * Check if this is a replacement document.
     */
    public boolean isReplacement() {
        return value != null && value.isReplacement();
    }

    /**
     * Check if this is a cancellation document.
     */
    public boolean isCancellation() {
        return value != null && value.isCancellation();
    }

    /**
     * Check if this is a copy document.
     */
    public boolean isCopy() {
        return value != null && value.isCopy();
    }

    /**
     * Check if this is an addition document.
     */
    public boolean isAddition() {
        return value != null && value.isAddition();
    }

    /**
     * Check if this is an "other" type document.
     */
    public boolean isOther() {
        return value != null && value.isOther();
    }

    /**
     * Check if this code is for debit note - goods.
     */
    public boolean isDebitNoteGoods() {
        return value != null && value.isDebitNoteGoods();
    }

    /**
     * Check if this code is for debit note - services.
     */
    public boolean isDebitNoteServices() {
        return value != null && value.isDebitNoteServices();
    }

    /**
     * Check if this code is for credit note - goods.
     */
    public boolean isCreditNoteGoods() {
        return value != null && value.isCreditNoteGoods();
    }

    /**
     * Check if this code is for credit note - services.
     */
    public boolean isCreditNoteServices() {
        return value != null && value.isCreditNoteServices();
    }

    @Override
    public String toString() {
        return value != null ? value.getCode() : "null";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ThaiMessageFunctionCodeType that = (ThaiMessageFunctionCodeType) obj;

        if (value == null && that.value == null) return true;
        if (value == null || that.value == null) return false;

        return value.getCode() != null && value.getCode().equals(that.value.getCode());
    }

    @Override
    public int hashCode() {
        return value != null && value.getCode() != null ? value.getCode().hashCode() : 0;
    }
}
