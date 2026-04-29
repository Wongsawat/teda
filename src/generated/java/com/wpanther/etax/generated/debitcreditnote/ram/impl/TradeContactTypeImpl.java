
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max140TextType;
import com.wpanther.etax.generated.common.qdt.impl.Max140TextTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.EmailUniversalCommunicationType;
import com.wpanther.etax.generated.debitcreditnote.ram.TelephoneUniversalCommunicationType;
import com.wpanther.etax.generated.debitcreditnote.ram.TradeContactType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeContactType", propOrder = {
    "personName",
    "departmentName",
    "emailURIUniversalCommunication",
    "telephoneUniversalCommunication"
})
public class TradeContactTypeImpl
    implements Serializable, TradeContactType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PersonName", type = Max140TextTypeImpl.class)
    protected Max140TextTypeImpl personName;
    @XmlElement(name = "DepartmentName", type = Max140TextTypeImpl.class)
    protected Max140TextTypeImpl departmentName;
    @XmlElement(name = "EmailURIUniversalCommunication", type = EmailUniversalCommunicationTypeImpl.class)
    protected EmailUniversalCommunicationTypeImpl emailURIUniversalCommunication;
    @XmlElement(name = "TelephoneUniversalCommunication", type = TelephoneUniversalCommunicationTypeImpl.class)
    protected TelephoneUniversalCommunicationTypeImpl telephoneUniversalCommunication;

    public Max140TextType getPersonName() {
        return personName;
    }

    public void setPersonName(Max140TextType value) {
        this.personName = ((Max140TextTypeImpl) value);
    }

    public Max140TextType getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(Max140TextType value) {
        this.departmentName = ((Max140TextTypeImpl) value);
    }

    public EmailUniversalCommunicationType getEmailURIUniversalCommunication() {
        return emailURIUniversalCommunication;
    }

    public void setEmailURIUniversalCommunication(EmailUniversalCommunicationType value) {
        this.emailURIUniversalCommunication = ((EmailUniversalCommunicationTypeImpl) value);
    }

    public TelephoneUniversalCommunicationType getTelephoneUniversalCommunication() {
        return telephoneUniversalCommunication;
    }

    public void setTelephoneUniversalCommunication(TelephoneUniversalCommunicationType value) {
        this.telephoneUniversalCommunication = ((TelephoneUniversalCommunicationTypeImpl) value);
    }

}
