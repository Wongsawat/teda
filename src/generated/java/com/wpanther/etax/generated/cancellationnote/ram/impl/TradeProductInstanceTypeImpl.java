
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.cancellationnote.ram.TradeProductInstanceType;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeProductInstanceType", propOrder = {
    "batchID",
    "expiryDateTime"
})
public class TradeProductInstanceTypeImpl
    implements Serializable, TradeProductInstanceType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "BatchID", type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl batchID;
    @XmlElement(name = "ExpiryDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiryDateTime;

    public Max35IDType getBatchID() {
        return batchID;
    }

    public void setBatchID(Max35IDType value) {
        this.batchID = ((Max35IDTypeImpl) value);
    }

    public XMLGregorianCalendar getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(XMLGregorianCalendar value) {
        this.expiryDateTime = value;
    }

}
