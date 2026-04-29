
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.cancellationnote.ram.ReferencedDocumentType;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.ReferenceCodeType;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ReferenceCodeTypeImpl;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferencedDocumentType", propOrder = {
    "issuerAssignedID",
    "issueDateTime",
    "referenceTypeCode"
})
public class ReferencedDocumentTypeImpl
    implements Serializable, ReferencedDocumentType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "IssuerAssignedID", required = true, type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl issuerAssignedID;
    @XmlElement(name = "IssueDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueDateTime;
    @XmlElement(name = "ReferenceTypeCode", required = true, type = ReferenceCodeTypeImpl.class)
    protected ReferenceCodeTypeImpl referenceTypeCode;

    public Max35IDType getIssuerAssignedID() {
        return issuerAssignedID;
    }

    public void setIssuerAssignedID(Max35IDType value) {
        this.issuerAssignedID = ((Max35IDTypeImpl) value);
    }

    public XMLGregorianCalendar getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(XMLGregorianCalendar value) {
        this.issueDateTime = value;
    }

    public ReferenceCodeType getReferenceTypeCode() {
        return referenceTypeCode;
    }

    public void setReferenceTypeCode(ReferenceCodeType value) {
        this.referenceTypeCode = ((ReferenceCodeTypeImpl) value);
    }

}
