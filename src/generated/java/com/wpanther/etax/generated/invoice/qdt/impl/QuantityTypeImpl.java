
package com.wpanther.etax.generated.invoice.qdt.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import com.wpanther.etax.generated.invoice.qdt.QuantityType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuantityType", propOrder = {
    "value"
})
public class QuantityTypeImpl
    implements Serializable, QuantityType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "unitCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCode;
    @XmlAttribute(name = "unitCodeListID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCodeListID;
    @XmlAttribute(name = "unitCodeListAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCodeListAgencyID;
    @XmlAttribute(name = "unitCodeListAgencyName")
    protected String unitCodeListAgencyName;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String value) {
        this.unitCode = value;
    }

    public String getUnitCodeListID() {
        return unitCodeListID;
    }

    public void setUnitCodeListID(String value) {
        this.unitCodeListID = value;
    }

    public String getUnitCodeListAgencyID() {
        return unitCodeListAgencyID;
    }

    public void setUnitCodeListAgencyID(String value) {
        this.unitCodeListAgencyID = value;
    }

    public String getUnitCodeListAgencyName() {
        return unitCodeListAgencyName;
    }

    public void setUnitCodeListAgencyName(String value) {
        this.unitCodeListAgencyName = value;
    }

}
