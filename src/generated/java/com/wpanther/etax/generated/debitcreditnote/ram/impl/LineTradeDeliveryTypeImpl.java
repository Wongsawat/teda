
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.QuantityType;
import com.wpanther.etax.generated.common.qdt.impl.QuantityTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.LineTradeDeliveryType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeDeliveryType", propOrder = {
    "billedQuantity",
    "perPackageUnitQuantity"
})
public class LineTradeDeliveryTypeImpl
    implements Serializable, LineTradeDeliveryType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "BilledQuantity", type = QuantityTypeImpl.class)
    protected QuantityTypeImpl billedQuantity;
    @XmlElement(name = "PerPackageUnitQuantity", type = QuantityTypeImpl.class)
    protected QuantityTypeImpl perPackageUnitQuantity;

    public QuantityType getBilledQuantity() {
        return billedQuantity;
    }

    public void setBilledQuantity(QuantityType value) {
        this.billedQuantity = ((QuantityTypeImpl) value);
    }

    public QuantityType getPerPackageUnitQuantity() {
        return perPackageUnitQuantity;
    }

    public void setPerPackageUnitQuantity(QuantityType value) {
        this.perPackageUnitQuantity = ((QuantityTypeImpl) value);
    }

}
