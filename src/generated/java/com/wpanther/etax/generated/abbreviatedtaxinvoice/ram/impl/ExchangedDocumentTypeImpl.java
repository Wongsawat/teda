
package com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.qdt.Max256TextType;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.Max35TextType;
import com.wpanther.etax.generated.common.qdt.Max70IDType;
import com.wpanther.etax.generated.common.qdt.ThaiInvoiceDocumentCodeType;
import com.wpanther.etax.generated.common.qdt.ThaiMessageFunctionCodeType;
import com.wpanther.etax.generated.common.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max35TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max70IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ThaiInvoiceDocumentCodeTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ThaiMessageFunctionCodeTypeImpl;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.ExchangedDocumentType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.NoteType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangedDocumentType", propOrder = {
    "id",
    "name",
    "typeCode",
    "issueDateTime",
    "purpose",
    "purposeCode",
    "globalID",
    "creationDateTime",
    "includedNote"
})
public class ExchangedDocumentTypeImpl
    implements Serializable, ExchangedDocumentType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", required = true, type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl id;
    @XmlElement(name = "Name", required = true, type = Max35TextTypeImpl.class)
    protected Max35TextTypeImpl name;
    @XmlElement(name = "TypeCode", required = true, type = ThaiInvoiceDocumentCodeTypeImpl.class)
    protected ThaiInvoiceDocumentCodeTypeImpl typeCode;
    @XmlElement(name = "IssueDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueDateTime;
    @XmlElement(name = "Purpose", type = Max256TextTypeImpl.class)
    protected Max256TextTypeImpl purpose;
    @XmlElement(name = "PurposeCode", type = ThaiMessageFunctionCodeTypeImpl.class)
    protected ThaiMessageFunctionCodeTypeImpl purposeCode;
    @XmlElement(name = "GlobalID", type = Max70IDTypeImpl.class)
    protected Max70IDTypeImpl globalID;
    @XmlElement(name = "CreationDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationDateTime;
    @XmlElement(name = "IncludedNote", type = NoteTypeImpl.class)
    protected List<NoteType> includedNote;

    public Max35IDType getID() {
        return id;
    }

    public void setID(Max35IDType value) {
        this.id = ((Max35IDTypeImpl) value);
    }

    public Max35TextType getName() {
        return name;
    }

    public void setName(Max35TextType value) {
        this.name = ((Max35TextTypeImpl) value);
    }

    public ThaiInvoiceDocumentCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(ThaiInvoiceDocumentCodeType value) {
        this.typeCode = ((ThaiInvoiceDocumentCodeTypeImpl) value);
    }

    public XMLGregorianCalendar getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(XMLGregorianCalendar value) {
        this.issueDateTime = value;
    }

    public Max256TextType getPurpose() {
        return purpose;
    }

    public void setPurpose(Max256TextType value) {
        this.purpose = ((Max256TextTypeImpl) value);
    }

    public ThaiMessageFunctionCodeType getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(ThaiMessageFunctionCodeType value) {
        this.purposeCode = ((ThaiMessageFunctionCodeTypeImpl) value);
    }

    public Max70IDType getGlobalID() {
        return globalID;
    }

    public void setGlobalID(Max70IDType value) {
        this.globalID = ((Max70IDTypeImpl) value);
    }

    public XMLGregorianCalendar getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(XMLGregorianCalendar value) {
        this.creationDateTime = value;
    }

    public List<NoteType> getIncludedNote() {
        if (includedNote == null) {
            includedNote = new ArrayList<NoteType>();
        }
        return this.includedNote;
    }

}
