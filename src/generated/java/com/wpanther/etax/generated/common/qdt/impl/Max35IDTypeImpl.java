
package com.wpanther.etax.generated.common.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Max35IDType", propOrder = {
    "value"
})
public class Max35IDTypeImpl
    implements Serializable, Max35IDType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;
    @XmlAttribute(name = "schemeID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeID;
    @XmlAttribute(name = "schemeName")
    protected String schemeName;
    @XmlAttribute(name = "schemeAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeAgencyID;
    @XmlAttribute(name = "schemeAgencyName")
    protected String schemeAgencyName;
    @XmlAttribute(name = "schemeVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeVersionID;
    @XmlAttribute(name = "schemeDataURI")
    @XmlSchemaType(name = "anyURI")
    protected String schemeDataURI;
    @XmlAttribute(name = "schemeURI")
    @XmlSchemaType(name = "anyURI")
    protected String schemeURI;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(String value) {
        this.schemeID = value;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String value) {
        this.schemeName = value;
    }

    public String getSchemeAgencyID() {
        return schemeAgencyID;
    }

    public void setSchemeAgencyID(String value) {
        this.schemeAgencyID = value;
    }

    public String getSchemeAgencyName() {
        return schemeAgencyName;
    }

    public void setSchemeAgencyName(String value) {
        this.schemeAgencyName = value;
    }

    public String getSchemeVersionID() {
        return schemeVersionID;
    }

    public void setSchemeVersionID(String value) {
        this.schemeVersionID = value;
    }

    public String getSchemeDataURI() {
        return schemeDataURI;
    }

    public void setSchemeDataURI(String value) {
        this.schemeDataURI = value;
    }

    public String getSchemeURI() {
        return schemeURI;
    }

    public void setSchemeURI(String value) {
        this.schemeURI = value;
    }

}
