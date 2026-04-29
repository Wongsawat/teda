
package com.wpanther.etax.generated.common.udt.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import com.wpanther.etax.generated.common.udt.PercentType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PercentType", propOrder = {
    "value"
})
public class PercentTypeImpl
    implements Serializable, PercentType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "format")
    protected String format;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String value) {
        this.format = value;
    }

}
