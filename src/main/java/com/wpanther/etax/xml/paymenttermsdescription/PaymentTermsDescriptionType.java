package com.wpanther.etax.xml.paymenttermsdescription;

import com.wpanther.etax.adapter.PaymentTermsDescriptionIdentifierAdapter;
import com.wpanther.etax.entity.PaymentTermsDescriptionIdentifier;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Payment Terms Description Identifier
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 7 UN/CEFACT payment terms description identifiers (1-7)
 *
 * UN/CEFACT Code List: 64277
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTermsDescriptionIdentifierContentType",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A")
public class PaymentTermsDescriptionType {

    @XmlValue
    @XmlJavaTypeAdapter(PaymentTermsDescriptionIdentifierAdapter.class)
    private PaymentTermsDescriptionIdentifier value;

    // Constructors
    public PaymentTermsDescriptionType() {
    }

    public PaymentTermsDescriptionType(PaymentTermsDescriptionIdentifier value) {
        this.value = value;
    }

    public PaymentTermsDescriptionType(String code) {
        this.value = new PaymentTermsDescriptionIdentifier(code);
    }

    // Getter and Setter
    public PaymentTermsDescriptionIdentifier getValue() {
        return value;
    }

    public void setValue(PaymentTermsDescriptionIdentifier value) {
        this.value = value;
    }

    /**
     * Get the payment terms description identifier code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the payment terms description name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the payment terms description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Check if draft is required
     */
    public boolean isDraftRequired() {
        return value != null && value.isDraftRequired();
    }

    /**
     * Check if this is a banking draft (codes 1-3)
     */
    public boolean isBankingDraft() {
        return value != null && value.isBankingDraft();
    }

    /**
     * Check if draft is on issuing bank (code 1)
     */
    public boolean isIssuingBank() {
        return value != null && value.isIssuingBank();
    }

    /**
     * Check if draft is on advising bank (code 2)
     */
    public boolean isAdvisingBank() {
        return value != null && value.isAdvisingBank();
    }

    /**
     * Check if draft is on reimbursing bank (code 3)
     */
    public boolean isReimbursingBank() {
        return value != null && value.isReimbursingBank();
    }

    /**
     * Check if no draft required (code 6)
     */
    public boolean isNoDraft() {
        return value != null && value.isNoDraft();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTermsDescriptionType)) return false;
        PaymentTermsDescriptionType that = (PaymentTermsDescriptionType) o;
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
            return sb.toString();
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static PaymentTermsDescriptionType of(String code) {
        return new PaymentTermsDescriptionType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static PaymentTermsDescriptionType of(PaymentTermsDescriptionIdentifier entity) {
        return new PaymentTermsDescriptionType(entity);
    }
}
