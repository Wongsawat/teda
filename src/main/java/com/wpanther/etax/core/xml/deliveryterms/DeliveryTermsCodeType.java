package com.wpanther.etax.core.xml.deliveryterms;

import com.wpanther.etax.core.adapter.taxinvoice.DeliveryTermsCodeAdapter;
import com.wpanther.etax.core.entity.DeliveryTermsCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Delivery Terms Code
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 14 delivery terms codes (INCOTERMS 2010)
 *
 * UN/CEFACT Code List: 64053
 * Standard: INCOTERMS 2010
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryTermsCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010")
public class DeliveryTermsCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(DeliveryTermsCodeAdapter.class)
    private DeliveryTermsCode value;

    // Constructors
    public DeliveryTermsCodeType() {
    }

    public DeliveryTermsCodeType(DeliveryTermsCode value) {
        this.value = value;
    }

    public DeliveryTermsCodeType(String code) {
        this.value = new DeliveryTermsCode(code);
    }

    // Getter and Setter
    public DeliveryTermsCode getValue() {
        return value;
    }

    public void setValue(DeliveryTermsCode value) {
        this.value = value;
    }

    /**
     * Get the delivery terms code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the delivery terms name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the delivery terms description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the INCOTERMS group
     */
    public String getIncotermGroup() {
        return value != null ? value.getIncotermGroup() : null;
    }

    /**
     * Get the seller obligation level
     */
    public String getSellerObligation() {
        return value != null ? value.getSellerObligation() : null;
    }

    /**
     * Check if this is an official INCOTERM
     */
    public boolean isIncoterm() {
        return value != null && value.isIncoterm();
    }

    /**
     * Check if this is Group E (Departure)
     */
    public boolean isGroupE() {
        return value != null && value.isGroupE();
    }

    /**
     * Check if this is Group F (Main carriage unpaid)
     */
    public boolean isGroupF() {
        return value != null && value.isGroupF();
    }

    /**
     * Check if this is Group C (Main carriage paid)
     */
    public boolean isGroupC() {
        return value != null && value.isGroupC();
    }

    /**
     * Check if this is Group D (Arrival)
     */
    public boolean isGroupD() {
        return value != null && value.isGroupD();
    }

    /**
     * Check if seller has minimum obligation
     */
    public boolean hasMinimumSellerObligation() {
        return value != null && value.hasMinimumSellerObligation();
    }

    /**
     * Check if seller has low obligation
     */
    public boolean hasLowSellerObligation() {
        return value != null && value.hasLowSellerObligation();
    }

    /**
     * Check if seller has medium obligation
     */
    public boolean hasMediumSellerObligation() {
        return value != null && value.hasMediumSellerObligation();
    }

    /**
     * Check if seller has high obligation
     */
    public boolean hasHighSellerObligation() {
        return value != null && value.hasHighSellerObligation();
    }

    /**
     * Check if seller has maximum obligation
     */
    public boolean hasMaximumSellerObligation() {
        return value != null && value.hasMaximumSellerObligation();
    }

    /**
     * Check if this term includes insurance
     */
    public boolean includesInsurance() {
        return value != null && value.includesInsurance();
    }

    /**
     * Check if this term includes freight payment
     */
    public boolean includesFreight() {
        return value != null && value.includesFreight();
    }

    /**
     * Check if this is for sea transport only
     */
    public boolean isSeaTransportOnly() {
        return value != null && value.isSeaTransportOnly();
    }

    /**
     * Check if this is for any mode of transport
     */
    public boolean isAnyTransportMode() {
        return value != null && value.isAnyTransportMode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryTermsCodeType)) return false;
        DeliveryTermsCodeType that = (DeliveryTermsCodeType) o;
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
            if (value.getIncotermGroup() != null) {
                sb.append(" [Group ").append(value.getIncotermGroup()).append("]");
            }
            return sb.toString();
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static DeliveryTermsCodeType of(String code) {
        return new DeliveryTermsCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static DeliveryTermsCodeType of(DeliveryTermsCode entity) {
        return new DeliveryTermsCodeType(entity);
    }
}
