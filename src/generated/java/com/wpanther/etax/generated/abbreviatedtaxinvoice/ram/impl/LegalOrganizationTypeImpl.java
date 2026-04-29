
package com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.LegalOrganizationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegalOrganizationType", propOrder = {
    "id"
})
public class LegalOrganizationTypeImpl
    implements Serializable, LegalOrganizationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", required = true, type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl id;

    public Max35IDType getID() {
        return id;
    }

    public void setID(Max35IDType value) {
        this.id = ((Max35IDTypeImpl) value);
    }

}
