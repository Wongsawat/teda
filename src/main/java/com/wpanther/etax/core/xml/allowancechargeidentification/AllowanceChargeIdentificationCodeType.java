package com.wpanther.etax.core.xml.allowancechargeidentification;

import com.wpanther.etax.core.adapter.common.AllowanceChargeIdentificationCodeAdapter;
import com.wpanther.etax.core.entity.AllowanceChargeIdentificationCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Allowance/Charge Identification Code
 * Replaces the generated JAXBElement<String> type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 106 codes (100 standard + 6 Thai extensions)
 *
 * UN/CEFACT Code List: 5189 (AllowanceChargeID)
 * Schema Version: D14A
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowanceChargeIdentificationCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A")
public class AllowanceChargeIdentificationCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(AllowanceChargeIdentificationCodeAdapter.class)
    private AllowanceChargeIdentificationCode value;

    // Constructors
    public AllowanceChargeIdentificationCodeType() {
    }

    public AllowanceChargeIdentificationCodeType(AllowanceChargeIdentificationCode value) {
        this.value = value;
    }

    public AllowanceChargeIdentificationCodeType(String code) {
        this.value = new AllowanceChargeIdentificationCode(code);
    }

    // Getter and Setter
    public AllowanceChargeIdentificationCode getValue() {
        return value;
    }

    public void setValue(AllowanceChargeIdentificationCode value) {
        this.value = value;
    }

    /**
     * Get the allowance/charge identification code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the allowance/charge name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the allowance/charge description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the allowance/charge category
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    /**
     * Check if this is a documentary credit commission
     */
    public boolean isDocumentaryCreditCommission() {
        return value != null && value.isDocumentaryCreditCommission();
    }

    /**
     * Check if this is a collection commission
     */
    public boolean isCollectionCommission() {
        return value != null && value.isCollectionCommission();
    }

    /**
     * Check if this is a processing fee
     */
    public boolean isProcessingFee() {
        return value != null && value.isProcessingFee();
    }

    /**
     * Check if this is a discount
     */
    public boolean isDiscount() {
        return value != null && value.isDiscount();
    }

    /**
     * Check if this is a rebate
     */
    public boolean isRebate() {
        return value != null && value.isRebate();
    }

    /**
     * Check if this is a penalty
     */
    public boolean isPenalty() {
        return value != null && value.isPenalty();
    }

    /**
     * Check if this is a bonus
     */
    public boolean isBonus() {
        return value != null && value.isBonus();
    }

    /**
     * Check if this is freight charges
     */
    public boolean isFreightCharges() {
        return value != null && value.isFreightCharges();
    }

    /**
     * Check if this is packing charges
     */
    public boolean isPackingCharges() {
        return value != null && value.isPackingCharges();
    }

    /**
     * Check if this is loading/unloading charges
     */
    public boolean isLoadingCharges() {
        return value != null && value.isLoadingCharges();
    }

    /**
     * Check if this is handling charges
     */
    public boolean isHandlingCharges() {
        return value != null && value.isHandlingCharges();
    }

    /**
     * Check if this is testing/inspection charges
     */
    public boolean isTestingCharges() {
        return value != null && value.isTestingCharges();
    }

    /**
     * Check if this is a standard UN/CEFACT code
     */
    public boolean isStandardCode() {
        return value != null && value.isStandardCode();
    }

    /**
     * Check if this is a Thai extension code
     */
    public boolean isThaiExtension() {
        return value != null && value.isThaiExtension();
    }

    /**
     * Check if this is a commission-type charge
     */
    public boolean isCommission() {
        return value != null && value.isCommission();
    }

    /**
     * Check if this is a charge (not allowance)
     */
    public boolean isCharge() {
        return value != null && value.isCharge();
    }

    /**
     * Check if this is an allowance (not charge)
     */
    public boolean isAllowance() {
        return value != null && value.isAllowance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllowanceChargeIdentificationCodeType)) return false;
        AllowanceChargeIdentificationCodeType that = (AllowanceChargeIdentificationCodeType) o;
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
            if (value.isThaiExtension()) {
                sb.append(" [Thai Extension]");
            }
            return sb.toString();
        }
        return "null";
    }

    /**
     * Static factory method to create from code string
     */
    public static AllowanceChargeIdentificationCodeType of(String code) {
        return new AllowanceChargeIdentificationCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static AllowanceChargeIdentificationCodeType of(AllowanceChargeIdentificationCode entity) {
        return new AllowanceChargeIdentificationCodeType(entity);
    }
}
