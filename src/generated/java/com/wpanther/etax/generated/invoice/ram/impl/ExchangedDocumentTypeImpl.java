
package com.wpanther.etax.generated.invoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.invoice.qdt.InvoiceDocumentCodeType;
import com.wpanther.etax.generated.invoice.qdt.Max256TextType;
import com.wpanther.etax.generated.invoice.qdt.Max35IDType;
import com.wpanther.etax.generated.invoice.qdt.Max35TextType;
import com.wpanther.etax.generated.invoice.qdt.MessageFunctionCodeType;
import com.wpanther.etax.generated.invoice.qdt.impl.InvoiceDocumentCodeTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max35TextTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.MessageFunctionCodeTypeImpl;
import com.wpanther.etax.generated.invoice.ram.ExchangedDocumentType;
import com.wpanther.etax.generated.invoice.ram.NoteType;
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
    "includedNote"
})
public class ExchangedDocumentTypeImpl
    implements Serializable, ExchangedDocumentType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", required = true, type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl id;
    @XmlElement(name = "Name", type = Max35TextTypeImpl.class)
    protected Max35TextTypeImpl name;
    @XmlElement(name = "TypeCode", type = InvoiceDocumentCodeTypeImpl.class)
    protected InvoiceDocumentCodeTypeImpl typeCode;
    @XmlElement(name = "IssueDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueDateTime;
    @XmlElement(name = "Purpose", type = Max256TextTypeImpl.class)
    protected Max256TextTypeImpl purpose;
    @XmlElement(name = "PurposeCode", type = MessageFunctionCodeTypeImpl.class)
    protected MessageFunctionCodeTypeImpl purposeCode;
    @XmlElement(name = "GlobalID", type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl globalID;
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

    public InvoiceDocumentCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(InvoiceDocumentCodeType value) {
        this.typeCode = ((InvoiceDocumentCodeTypeImpl) value);
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

    public MessageFunctionCodeType getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(MessageFunctionCodeType value) {
        this.purposeCode = ((MessageFunctionCodeTypeImpl) value);
    }

    public Max35IDType getGlobalID() {
        return globalID;
    }

    public void setGlobalID(Max35IDType value) {
        this.globalID = ((Max35IDTypeImpl) value);
    }

    public List<NoteType> getIncludedNote() {
        if (includedNote == null) {
            includedNote = new ArrayList<NoteType>();
        }
        return this.includedNote;
    }

}
