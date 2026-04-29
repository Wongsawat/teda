
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max2048TextType;
import com.wpanther.etax.generated.common.qdt.Max256IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max2048TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max256IDTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.UniversalCommunicationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UniversalCommunicationType", propOrder = {
    "uriid",
    "completeNumber"
})
public class UniversalCommunicationTypeImpl
    implements Serializable, UniversalCommunicationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "URIID", type = Max256IDTypeImpl.class)
    protected Max256IDTypeImpl uriid;
    @XmlElement(name = "CompleteNumber", type = Max2048TextTypeImpl.class)
    protected Max2048TextTypeImpl completeNumber;

    public Max256IDType getURIID() {
        return uriid;
    }

    public void setURIID(Max256IDType value) {
        this.uriid = ((Max256IDTypeImpl) value);
    }

    public Max2048TextType getCompleteNumber() {
        return completeNumber;
    }

    public void setCompleteNumber(Max2048TextType value) {
        this.completeNumber = ((Max2048TextTypeImpl) value);
    }

}
