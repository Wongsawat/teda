
package com.wpanther.etax.generated.invoice.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.invoice.qdt.FreightChargeTypeIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreightChargeTypeIDType", propOrder = {
    "value"
})
public class FreightChargeTypeIDTypeImpl
    implements Serializable, FreightChargeTypeIDType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;
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

    public String getSchemeAgencyID() {
        return schemeAgencyID;
    }

    public void setSchemeAgencyID(String value) {
        this.schemeAgencyID = value;
    }

    public String getSchemeVersionID() {
        return schemeVersionID;
    }

    public void setSchemeVersionID(String value) {
        this.schemeVersionID = value;
    }

}
