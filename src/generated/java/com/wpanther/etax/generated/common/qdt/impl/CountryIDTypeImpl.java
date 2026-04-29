
package com.wpanther.etax.generated.common.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.CountryIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryIDType", propOrder = {
    "value"
})
public class CountryIDTypeImpl
    implements Serializable, CountryIDType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected ISOTwoletterCountryCodeContentType value;
    @XmlAttribute(name = "schemeID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeID;
    @XmlAttribute(name = "schemeAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeAgencyID;
    @XmlAttribute(name = "schemeVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeVersionID;

    public ISOTwoletterCountryCodeContentType getValue() {
        return value;
    }

    public void setValue(ISOTwoletterCountryCodeContentType value) {
        this.value = value;
    }

    public String getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(String value) {
        this.schemeID = value;
    }

    public String getSchemeAgencyID() {
        return schemeAgencyID;
    }

    public void setSchemeAgencyID(String value) {
        this.schemeAgencyID = value;
    }

    public String getSchemeVersionID() {
        if (schemeVersionID == null) {
            return "second edition 2006";
        } else {
            return schemeVersionID;
        }
    }

    public void setSchemeVersionID(String value) {
        this.schemeVersionID = value;
    }

}
