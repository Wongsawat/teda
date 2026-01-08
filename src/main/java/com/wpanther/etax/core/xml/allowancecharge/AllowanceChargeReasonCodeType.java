package com.wpanther.etax.core.xml.allowancecharge;

import com.wpanther.etax.core.adapter.common.AllowanceChargeReasonCodeAdapter;
import com.wpanther.etax.core.entity.AllowanceChargeReasonCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Allowance Charge Reason Code
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 105 UN/CEFACT allowance charge reason codes
 *
 * UN/CEFACT Code List: 64465
 * Schema Version: D15B
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowanceChargeReasonCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B")
public class AllowanceChargeReasonCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(AllowanceChargeReasonCodeAdapter.class)
    private AllowanceChargeReasonCode value;

    // Constructors
    public AllowanceChargeReasonCodeType() {
    }

    public AllowanceChargeReasonCodeType(AllowanceChargeReasonCode value) {
        this.value = value;
    }

    public AllowanceChargeReasonCodeType(String code) {
        this.value = new AllowanceChargeReasonCode(code);
    }

    // Getter and Setter
    public AllowanceChargeReasonCode getValue() {
        return value;
    }

    public void setValue(AllowanceChargeReasonCode value) {
        this.value = value;
    }

    /**
     * Get the allowance charge reason code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the allowance charge reason name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the allowance charge reason description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the allowance charge reason category
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    /**
     * Check if this is a quality issue reason
     */
    public boolean isQualityIssue() {
        return value != null && value.isQualityIssue();
    }

    /**
     * Check if this is a delivery issue reason
     */
    public boolean isDeliveryIssue() {
        return value != null && value.isDeliveryIssue();
    }

    /**
     * Check if this is an administrative error reason
     */
    public boolean isAdministrativeError() {
        return value != null && value.isAdministrativeError();
    }

    /**
     * Check if this is a discount or allowance reason
     */
    public boolean isDiscountOrAllowance() {
        return value != null && value.isDiscountOrAllowance();
    }

    /**
     * Check if this is a financial charge reason
     */
    public boolean isFinancialCharge() {
        return value != null && value.isFinancialCharge();
    }

    /**
     * Check if this is a claim or dispute reason
     */
    public boolean isClaimOrDispute() {
        return value != null && value.isClaimOrDispute();
    }

    /**
     * Check if this is a freight or logistics reason
     */
    public boolean isFreightOrLogistics() {
        return value != null && value.isFreightOrLogistics();
    }

    /**
     * Check if this is a payment terms reason
     */
    public boolean isPaymentTerms() {
        return value != null && value.isPaymentTerms();
    }

    /**
     * Check if this is HR related reason
     */
    public boolean isHRRelated() {
        return value != null && value.isHRRelated();
    }

    /**
     * Check if this is mutually defined (ZZZ)
     */
    public boolean isMutuallyDefined() {
        return value != null && value.isMutuallyDefined();
    }

    /**
     * Check if this is custom or other reason
     */
    public boolean isCustomOrOther() {
        return value != null && value.isCustomOrOther();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllowanceChargeReasonCodeType)) return false;
        AllowanceChargeReasonCodeType that = (AllowanceChargeReasonCodeType) o;
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
    public static AllowanceChargeReasonCodeType of(String code) {
        return new AllowanceChargeReasonCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static AllowanceChargeReasonCodeType of(AllowanceChargeReasonCode entity) {
        return new AllowanceChargeReasonCodeType(entity);
    }
}
