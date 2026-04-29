
package com.wpanther.etax.generated.common.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.FormattedDateTimeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormattedDateTimeType", propOrder = {
    "dateTimeString"
})
public class FormattedDateTimeTypeImpl
    implements Serializable, FormattedDateTimeType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DateTimeString", required = true, type = FormattedDateTimeTypeImpl.DateTimeStringImpl.class)
    protected FormattedDateTimeTypeImpl.DateTimeStringImpl dateTimeString;

    public FormattedDateTimeType.DateTimeString getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(FormattedDateTimeType.DateTimeString value) {
        this.dateTimeString = ((FormattedDateTimeTypeImpl.DateTimeStringImpl) value);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class DateTimeStringImpl
        implements Serializable, FormattedDateTimeType.DateTimeString
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
