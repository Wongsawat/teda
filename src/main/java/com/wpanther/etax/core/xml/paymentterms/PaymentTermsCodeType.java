package com.wpanther.etax.core.xml.paymentterms;

import com.wpanther.etax.core.adapter.PaymentTermsTypeCodeAdapter;
import com.wpanther.etax.core.entity.PaymentTermsTypeCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Payment Terms Type Code
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 79 UN/CEFACT payment terms type codes (1-78 + ZZZ)
 *
 * UN/CEFACT Code List: 64279
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTermsTypeCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A")
public class PaymentTermsCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(PaymentTermsTypeCodeAdapter.class)
    private PaymentTermsTypeCode value;

    // Constructors
    public PaymentTermsCodeType() {
    }

    public PaymentTermsCodeType(PaymentTermsTypeCode value) {
        this.value = value;
    }

    public PaymentTermsCodeType(String code) {
        this.value = new PaymentTermsTypeCode(code);
    }

    // Getter and Setter
    public PaymentTermsTypeCode getValue() {
        return value;
    }

    public void setValue(PaymentTermsTypeCode value) {
        this.value = value;
    }

    /**
     * Get the payment terms type code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the payment terms type name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the payment terms type description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the payment terms category
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    /**
     * Check if this is an immediate payment term
     */
    public boolean isImmediate() {
        return value != null && value.isImmediate();
    }

    /**
     * Check if this is a deferred payment term
     */
    public boolean isDeferred() {
        return value != null && value.isDeferred();
    }

    /**
     * Check if this payment term has discount
     */
    public boolean hasDiscount() {
        return value != null && value.hasDiscount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTermsCodeType)) return false;
        PaymentTermsCodeType that = (PaymentTermsCodeType) o;
        return value != null && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (value != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCode());
            if (value.getName() != null) {
                sb.append(" (").append(value.getName()).append(")");
            }
            if (value.getCategory() != null) {
                sb.append(" [").append(value.getCategory()).append("]");
            }
            return sb.toString();
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static PaymentTermsCodeType of(String code) {
        return new PaymentTermsCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static PaymentTermsCodeType of(PaymentTermsTypeCode entity) {
        return new PaymentTermsCodeType(entity);
    }
}
