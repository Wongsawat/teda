
package com.wpanther.etax.generated.invoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.invoice.qdt.Max35IDType;
import com.wpanther.etax.generated.invoice.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.invoice.ram.DocumentLineDocumentType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentLineDocumentType", propOrder = {
    "lineID"
})
public class DocumentLineDocumentTypeImpl
    implements Serializable, DocumentLineDocumentType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "LineID", required = true, type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl lineID;

    public Max35IDType getLineID() {
        return lineID;
    }

    public void setLineID(Max35IDType value) {
        this.lineID = ((Max35IDTypeImpl) value);
    }

}
