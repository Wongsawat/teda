package com.wpanther.etax.core.xml.messagefunction;

import com.wpanther.etax.core.adapter.common.MessageFunctionCodeAdapter;
import com.wpanther.etax.core.entity.MessageFunctionCode;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Custom JAXB type for UN/CEFACT Message Function Code
 * Replaces the generated String type with database-backed implementation
 *
 * This class:
 * - Maintains the exact same XML structure as the generated code
 * - Uses XmlAdapter to fetch values from database
 * - Preserves namespace: urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A
 * - Supports both marshalling (Java -> XML) and unmarshalling (XML -> Java)
 * - Handles all 65 UN/CEFACT message function codes (1-65)
 *
 * UN/CEFACT Code List: 61225
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageFunctionCodeContentType",
         namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A")
public class MessageFunctionCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(MessageFunctionCodeAdapter.class)
    private MessageFunctionCode value;

    // Constructors
    public MessageFunctionCodeType() {
    }

    public MessageFunctionCodeType(MessageFunctionCode value) {
        this.value = value;
    }

    public MessageFunctionCodeType(String code) {
        this.value = new MessageFunctionCode(code);
    }

    // Getter and Setter
    public MessageFunctionCode getValue() {
        return value;
    }

    public void setValue(MessageFunctionCode value) {
        this.value = value;
    }

    /**
     * Get the message function code string
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get the message function name
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Get the message function description
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Get the message function category
     */
    public String getCategory() {
        return value != null ? value.getCategory() : null;
    }

    /**
     * Check if this is a modification function
     */
    public boolean isModification() {
        return value != null && value.isModification();
    }

    /**
     * Check if this is an original transmission
     */
    public boolean isOriginal() {
        return value != null && value.isOriginal();
    }

    /**
     * Check if this is acceptance/rejection related
     */
    public boolean isAcceptance() {
        return value != null && value.isAcceptance();
    }

    /**
     * Check if this is a cancellation function
     */
    public boolean isCancellation() {
        return value != null && value.isCancellation();
    }

    /**
     * Check if this is a change/amendment function
     */
    public boolean isChange() {
        return value != null && value.isChange();
    }

    /**
     * Check if this is a replacement function
     */
    public boolean isReplacement() {
        return value != null && value.isReplacement();
    }

    /**
     * Check if this is a confirmation function
     */
    public boolean isConfirmation() {
        return value != null && value.isConfirmation();
    }

    /**
     * Check if this is a financial reversal
     */
    public boolean isFinancialReversal() {
        return value != null && value.isFinancialReversal();
    }

    /**
     * Check if this is a schedule-related function
     */
    public boolean isSchedule() {
        return value != null && value.isSchedule();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageFunctionCodeType)) return false;
        MessageFunctionCodeType that = (MessageFunctionCodeType) o;
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
    public static MessageFunctionCodeType of(String code) {
        return new MessageFunctionCodeType(code);
    }

    /**
     * Static factory method to create from entity
     */
    public static MessageFunctionCodeType of(MessageFunctionCode entity) {
        return new MessageFunctionCodeType(entity);
    }
}
