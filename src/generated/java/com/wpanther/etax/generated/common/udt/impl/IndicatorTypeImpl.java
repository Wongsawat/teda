
package com.wpanther.etax.generated.common.udt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.udt.IndicatorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndicatorType", propOrder = {
    "indicatorString",
    "indicator"
})
public class IndicatorTypeImpl
    implements Serializable, IndicatorType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "IndicatorString", type = IndicatorTypeImpl.IndicatorStringImpl.class)
    protected IndicatorTypeImpl.IndicatorStringImpl indicatorString;
    @XmlElement(name = "Indicator")
    protected Boolean indicator;

    public IndicatorType.IndicatorString getIndicatorString() {
        return indicatorString;
    }

    public void setIndicatorString(IndicatorType.IndicatorString value) {
        this.indicatorString = ((IndicatorTypeImpl.IndicatorStringImpl) value);
    }

    public Boolean isIndicator() {
        return indicator;
    }

    public void setIndicator(Boolean value) {
        this.indicator = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class IndicatorStringImpl
        implements Serializable, IndicatorType.IndicatorString
    {

        private final static long serialVersionUID = 1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "format")
        protected String format;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String value) {
            this.format = value;
        }

    }

}
