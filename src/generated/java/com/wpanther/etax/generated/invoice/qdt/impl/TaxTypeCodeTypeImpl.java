
package com.wpanther.etax.generated.invoice.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.invoice.qdt.TaxTypeCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import un.unece.uncefact.codelist.standard.unece.dutytaxfeetypecode.d14a.DutyTaxFeeTypeCodeContentType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxTypeCodeType", propOrder = {
    "value"
})
public class TaxTypeCodeTypeImpl
    implements Serializable, TaxTypeCodeType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected DutyTaxFeeTypeCodeContentType value;
    @XmlAttribute(name = "listID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listID;
    @XmlAttribute(name = "listAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listAgencyID;
    @XmlAttribute(name = "listVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listVersionID;
    @XmlAttribute(name = "listURI")
    @XmlSchemaType(name = "anyURI")
    protected String listURI;

    public DutyTaxFeeTypeCodeContentType getValue() {
        return value;
    }

    public void setValue(DutyTaxFeeTypeCodeContentType value) {
        this.value = value;
    }

    public String getListID() {
        if (listID == null) {
            return "5153";
        } else {
            return listID;
        }
    }

    public void setListID(String value) {
        this.listID = value;
    }

    public String getListAgencyID() {
        if (listAgencyID == null) {
            return "6";
        } else {
            return listAgencyID;
        }
    }

    public void setListAgencyID(String value) {
        this.listAgencyID = value;
    }

    public String getListVersionID() {
        if (listVersionID == null) {
            return "D14A";
        } else {
            return listVersionID;
        }
    }

    public void setListVersionID(String value) {
        this.listVersionID = value;
    }

    public String getListURI() {
        return listURI;
    }

    public void setListURI(String value) {
        this.listURI = value;
    }

}
