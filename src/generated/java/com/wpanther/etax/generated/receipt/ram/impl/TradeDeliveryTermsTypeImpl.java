
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.DeliveryTermsCodeType;
import com.wpanther.etax.generated.common.qdt.impl.DeliveryTermsCodeTypeImpl;
import com.wpanther.etax.generated.receipt.ram.TradeDeliveryTermsType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeDeliveryTermsType", propOrder = {
    "deliveryTypeCode"
})
public class TradeDeliveryTermsTypeImpl
    implements Serializable, TradeDeliveryTermsType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DeliveryTypeCode", type = DeliveryTermsCodeTypeImpl.class)
    protected DeliveryTermsCodeTypeImpl deliveryTypeCode;

    public DeliveryTermsCodeType getDeliveryTypeCode() {
        return deliveryTypeCode;
    }

    public void setDeliveryTypeCode(DeliveryTermsCodeType value) {
        this.deliveryTypeCode = ((DeliveryTermsCodeTypeImpl) value);
    }

}
