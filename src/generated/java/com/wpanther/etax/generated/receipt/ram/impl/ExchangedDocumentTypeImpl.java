
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.Max35TextType;
import com.wpanther.etax.generated.common.qdt.Max70IDType;
import com.wpanther.etax.generated.common.qdt.ThaiInvoiceDocumentCodeType;
import com.wpanther.etax.generated.common.qdt.ThaiMessageFunctionCodeType;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max35TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max70IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ThaiInvoiceDocumentCodeTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.ThaiMessageFunctionCodeTypeImpl;
import com.wpanther.etax.generated.receipt.ram.ExchangedDocumentType;
import com.wpanther.etax.generated.receipt.ram.NoteType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    protected List<Max35TextType> name;
    @XmlElement(name = "TypeCode", required = true, type = ThaiInvoiceDocumentCodeTypeImpl.class)
    protected ThaiInvoiceDocumentCodeTypeImpl typeCode;
    @XmlElement(name = "IssueDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar issueDateTime;
    @XmlElement(name = "Purpose")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String purpose;
    @XmlElement(name = "PurposeCode", type = ThaiMessageFunctionCodeTypeImpl.class)
    protected ThaiMessageFunctionCodeTypeImpl purposeCode;
    @XmlElement(name = "GlobalID", type = Max70IDTypeImpl.class)
    protected Max70IDTypeImpl globalID;
    @XmlElement(name = "CreationDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected List<XMLGregorianCalendar> creationDateTime;
    @XmlElement(name = "IncludedNote", type = NoteTypeImpl.class)
    protected List<NoteType> includedNote;

    public Max35IDType getID() {
        return id;
    }

    public void setID(Max35IDType value) {
        this.id = ((Max35IDTypeImpl) value);
    }

    public List<Max35TextType> getName() {
        if (name == null) {
            name = new ArrayList<Max35TextType>();
        }
        return this.name;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String value) {
        this.purpose = value;
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

    public List<XMLGregorianCalendar> getCreationDateTime() {
        if (creationDateTime == null) {
            creationDateTime = new ArrayList<XMLGregorianCalendar>();
        }
        return this.creationDateTime;
    }

    public List<NoteType> getIncludedNote() {
        if (includedNote == null) {
            includedNote = new ArrayList<NoteType>();
        }
        return this.includedNote;
    }

}
