
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.receipt.ram.TelephoneUniversalCommunicationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelephoneUniversalCommunicationType", propOrder = {
    "completeNumber"
})
public class TelephoneUniversalCommunicationTypeImpl
    implements Serializable, TelephoneUniversalCommunicationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "CompleteNumber")
    protected String completeNumber;

    public String getCompleteNumber() {
        return completeNumber;
    }

    public void setCompleteNumber(String value) {
        this.completeNumber = value;
    }

}
