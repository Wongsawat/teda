package com.wpanther.etax.core.xml.dutytaxfee;

import com.wpanther.etax.core.adapter.taxinvoice.DutyTaxFeeTypeCodeAdapter;
import com.wpanther.etax.core.entity.DutyTaxFeeTypeCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Duty Tax Fee Type Code
 * Replaces the generated enum type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 53 UN/CEFACT duty tax fee type codes
 *
 * UN/CEFACT Code List: 65153
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DutyTaxFeeTypeCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A")
public class DutyTaxFeeCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(DutyTaxFeeTypeCodeAdapter.class)
    private DutyTaxFeeTypeCode value;

    // Constructors
    public DutyTaxFeeCodeType() {
    }

    public DutyTaxFeeCodeType(DutyTaxFeeTypeCode value) {
        this.value = value;
    }

    public DutyTaxFeeCodeType(String code) {
        this.value = new DutyTaxFeeTypeCode(code);
    }

    // Getter and Setter
    public DutyTaxFeeTypeCode getValue() {
        return value;
    }

    public void setValue(DutyTaxFeeTypeCode value) {
        this.value = value;
    }

    /**
     * Get the duty tax fee type code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the duty tax fee type name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the duty tax fee type description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the duty tax fee type category
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    /**
     * Check if this is a VAT code
     */
    public boolean isVat() {
        return value != null && value.isVat();
    }

    /**
     * Check if this is an exempt code
     */
    public boolean isExempt() {
        return value != null && value.isExempt();
    }

    /**
     * Check if this is a summary code
     */
    public boolean isSummary() {
        return value != null && value.isSummary();
    }

    /**
     * Check if this is a customs duty
     */
    public boolean isCustomsDuty() {
        return value != null && value.isCustomsDuty();
    }

    /**
     * Check if this is an excise tax
     */
    public boolean isExciseTax() {
        return value != null && value.isExciseTax();
    }

    /**
     * Check if this is GST
     */
    public boolean isGST() {
        return value != null && value.isGST();
    }

    /**
     * Check if this is a special tax
     */
    public boolean isSpecialTax() {
        return value != null && value.isSpecialTax();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DutyTaxFeeCodeType)) return false;
        DutyTaxFeeCodeType that = (DutyTaxFeeCodeType) o;
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
    public static DutyTaxFeeCodeType of(String code) {
        return new DutyTaxFeeCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static DutyTaxFeeCodeType of(DutyTaxFeeTypeCode entity) {
        return new DutyTaxFeeCodeType(entity);
    }
}
