package com.wpanther.etax.xml.freightcost;

import com.wpanther.etax.adapter.FreightCostCodeAdapter;
import com.wpanther.etax.entity.FreightCostCode;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Freight Cost Code
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 1,641 UN/CEFACT freight cost codes
 *
 * UN/CEFACT: Recommendation 23, Version 4
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreightCostCodeContentType",
         namespace = "urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4")
public class FreightCostCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(FreightCostCodeAdapter.class)
    private FreightCostCode value;

    // Constructors
    public FreightCostCodeType() {
    }

    public FreightCostCodeType(FreightCostCode value) {
        this.value = value;
    }

    public FreightCostCodeType(String code) {
        this.value = new FreightCostCode(code);
    }

    // Getter and Setter
    public FreightCostCode getValue() {
        return value;
    }

    public void setValue(FreightCostCode value) {
        this.value = value;
    }

    /**
     * Get the freight cost code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the freight cost name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the freight cost category
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    /**
     * Get the code group (first 3 digits)
     */
    public String getCodeGroup() {
        return value != null ? value.getCodeGroup() : null;
    }

    /**
     * Check if this is basic freight
     */
    public boolean isBasicFreight() {
        return value != null && value.isBasicFreight();
    }

    /**
     * Check if this is freight surcharge
     */
    public boolean isFreightSurcharge() {
        return value != null && value.isFreightSurcharge();
    }

    /**
     * Check if this is container service
     */
    public boolean isContainerService() {
        return value != null && value.isContainerService();
    }

    /**
     * Check if this is terminal charge
     */
    public boolean isTerminalCharge() {
        return value != null && value.isTerminalCharge();
    }

    /**
     * Check if this is handling charge
     */
    public boolean isHandlingCharge() {
        return value != null && value.isHandlingCharge();
    }

    /**
     * Check if this is storage or demurrage
     */
    public boolean isStorageOrDemurrage() {
        return value != null && value.isStorageOrDemurrage();
    }

    /**
     * Check if this is customs or documentation
     */
    public boolean isCustomsOrDocumentation() {
        return value != null && value.isCustomsOrDocumentation();
    }

    /**
     * Check if this is dangerous goods
     */
    public boolean isDangerousGoods() {
        return value != null && value.isDangerousGoods();
    }

    /**
     * Check if this is special freight
     */
    public boolean isSpecialFreight() {
        return value != null && value.isSpecialFreight();
    }

    /**
     * Check if this is insurance
     */
    public boolean isInsurance() {
        return value != null && value.isInsurance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreightCostCodeType)) return false;
        FreightCostCodeType that = (FreightCostCodeType) o;
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
    public static FreightCostCodeType of(String code) {
        return new FreightCostCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static FreightCostCodeType of(FreightCostCode entity) {
        return new FreightCostCodeType(entity);
    }
}
