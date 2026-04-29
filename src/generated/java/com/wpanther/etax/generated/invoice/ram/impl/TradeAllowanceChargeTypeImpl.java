
package com.wpanther.etax.generated.invoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.invoice.qdt.AllowanceChargeIdentificationCodeType;
import com.wpanther.etax.generated.invoice.qdt.AllowanceChargeReasonCode;
import com.wpanther.etax.generated.invoice.qdt.AmountType;
import com.wpanther.etax.generated.invoice.qdt.impl.AllowanceChargeIdentificationCodeTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.AllowanceChargeReasonCodeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.AmountTypeImpl;
import com.wpanther.etax.generated.invoice.ram.TradeAllowanceChargeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeAllowanceChargeType", propOrder = {
    "chargeIndicator",
    "prepaidIndicator",
    "actualAmount",
    "reasonCode",
    "reason",
    "typeCode"
})
public class TradeAllowanceChargeTypeImpl
    implements Serializable, TradeAllowanceChargeType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ChargeIndicator")
    protected Boolean chargeIndicator;
    @XmlElement(name = "PrepaidIndicator")
    protected Boolean prepaidIndicator;
    @XmlElement(name = "ActualAmount", type = AmountTypeImpl.class)
    protected List<AmountType> actualAmount;
    @XmlElement(name = "ReasonCode", type = AllowanceChargeReasonCodeImpl.class)
    protected AllowanceChargeReasonCodeImpl reasonCode;
    @XmlElement(name = "Reason")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reason;
    @XmlElement(name = "TypeCode", type = AllowanceChargeIdentificationCodeTypeImpl.class)
    protected AllowanceChargeIdentificationCodeTypeImpl typeCode;

    public Boolean isChargeIndicator() {
        return chargeIndicator;
    }

    public void setChargeIndicator(Boolean value) {
        this.chargeIndicator = value;
    }

    public Boolean isPrepaidIndicator() {
        return prepaidIndicator;
    }

    public void setPrepaidIndicator(Boolean value) {
        this.prepaidIndicator = value;
    }

    public List<AmountType> getActualAmount() {
        if (actualAmount == null) {
            actualAmount = new ArrayList<AmountType>();
        }
        return this.actualAmount;
    }

    public AllowanceChargeReasonCode getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(AllowanceChargeReasonCode value) {
        this.reasonCode = ((AllowanceChargeReasonCodeImpl) value);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }

    public AllowanceChargeIdentificationCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(AllowanceChargeIdentificationCodeType value) {
        this.typeCode = ((AllowanceChargeIdentificationCodeTypeImpl) value);
    }

}
