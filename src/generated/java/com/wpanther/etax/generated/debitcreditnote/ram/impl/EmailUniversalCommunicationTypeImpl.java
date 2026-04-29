
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max256IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max256IDTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.EmailUniversalCommunicationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailUniversalCommunicationType", propOrder = {
    "uriid"
})
public class EmailUniversalCommunicationTypeImpl
    implements Serializable, EmailUniversalCommunicationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "URIID", type = Max256IDTypeImpl.class)
    protected Max256IDTypeImpl uriid;

    public Max256IDType getURIID() {
        return uriid;
    }

    public void setURIID(Max256IDType value) {
        this.uriid = ((Max256IDTypeImpl) value);
    }

}
