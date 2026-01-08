package com.wpanther.etax.core.xml.documentnameinvoice;

import com.wpanther.etax.core.adapter.common.UNECEDocumentNameCodeInvoiceAdapter;
import com.wpanther.etax.core.entity.UNECEDocumentNameCodeInvoice;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Document Name Code (Invoice)
 * Replaces the generated enum type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated type
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 *
 * UN/CEFACT: Code List 1001 (DocumentNameCode), Version D14A
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentNameCodeInvoiceContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A")
@XmlRootElement(name = "DocumentNameCodeInvoice",
                namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A")
public class DocumentNameCodeInvoiceType {

    @XmlValue
    @XmlJavaTypeAdapter(UNECEDocumentNameCodeInvoiceAdapter.class)
    private UNECEDocumentNameCodeInvoice value;

    // Constructors
    public DocumentNameCodeInvoiceType() {
    }

    public DocumentNameCodeInvoiceType(UNECEDocumentNameCodeInvoice value) {
        this.value = value;
    }

    public DocumentNameCodeInvoiceType(String code) {
        this.value = new UNECEDocumentNameCodeInvoice(code);
    }

    // Getter and Setter
    public UNECEDocumentNameCodeInvoice getValue() {
        return value;
    }

    public void setValue(UNECEDocumentNameCodeInvoice value) {
        this.value = value;
    }

    // Convenience methods
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    public String getName() {
        return value != null ? value.getName() : null;
    }

    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    public boolean isCredit() {
        return value != null && Boolean.TRUE.equals(value.getIsCredit());
    }

    public boolean isDebit() {
        return value != null && Boolean.TRUE.equals(value.getIsDebit());
    }

    public boolean requiresPayment() {
        return value != null && Boolean.TRUE.equals(value.getRequiresPayment());
    }

    // Factory methods
    public static DocumentNameCodeInvoiceType of(String code) {
        return new DocumentNameCodeInvoiceType(code);
    }

    public static DocumentNameCodeInvoiceType of(UNECEDocumentNameCodeInvoice entity) {
        return new DocumentNameCodeInvoiceType(entity);
    }
}
