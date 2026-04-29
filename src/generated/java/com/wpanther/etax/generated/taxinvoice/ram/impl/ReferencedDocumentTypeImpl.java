
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.ReferenceCodeType;
import com.wpanther.etax.generated.common.qdt.ThaiCategoryCodeType;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ReferenceCodeTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ThaiCategoryCodeTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.ReferencedDocumentType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferencedDocumentType", propOrder = {
    "issuerAssignedID",
    "issueDateTime",
    "referenceTypeCode",
    "categoryCode"
})
public class ReferencedDocumentTypeImpl
    implements Serializable, ReferencedDocumentType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "IssuerAssignedID", type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl issuerAssignedID;
    @XmlElement(name = "IssueDateTime")
    protected String issueDateTime;
    @XmlElement(name = "ReferenceTypeCode", type = ReferenceCodeTypeImpl.class)
    protected ReferenceCodeTypeImpl referenceTypeCode;
    @XmlElement(name = "CategoryCode", type = ThaiCategoryCodeTypeImpl.class)
    protected ThaiCategoryCodeTypeImpl categoryCode;

    public Max35IDType getIssuerAssignedID() {
        return issuerAssignedID;
    }

    public void setIssuerAssignedID(Max35IDType value) {
        this.issuerAssignedID = ((Max35IDTypeImpl) value);
    }

    public String getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(String value) {
        this.issueDateTime = value;
    }

    public ReferenceCodeType getReferenceTypeCode() {
        return referenceTypeCode;
    }

    public void setReferenceTypeCode(ReferenceCodeType value) {
        this.referenceTypeCode = ((ReferenceCodeTypeImpl) value);
    }

    public ThaiCategoryCodeType getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(ThaiCategoryCodeType value) {
        this.categoryCode = ((ThaiCategoryCodeTypeImpl) value);
    }

}
