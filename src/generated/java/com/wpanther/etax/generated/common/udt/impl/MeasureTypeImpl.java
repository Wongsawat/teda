
package com.wpanther.etax.generated.common.udt.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import com.wpanther.etax.generated.common.udt.MeasureType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeasureType", propOrder = {
    "value"
})
public class MeasureTypeImpl
    implements Serializable, MeasureType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "unitCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCode;
    @XmlAttribute(name = "unitCodeListVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCodeListVersionID;

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

    public String getUnitCodeListVersionID() {
        return unitCodeListVersionID;
    }

    public void setUnitCodeListVersionID(String value) {
        this.unitCodeListVersionID = value;
    }

}
