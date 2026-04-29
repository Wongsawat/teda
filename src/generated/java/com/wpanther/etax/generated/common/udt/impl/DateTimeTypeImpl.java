
package com.wpanther.etax.generated.common.udt.impl;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.udt.DateTimeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeType", propOrder = {
    "dateTimeString",
    "dateTime"
})
public class DateTimeTypeImpl
    implements Serializable, DateTimeType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DateTimeString", type = DateTimeTypeImpl.DateTimeStringImpl.class)
    protected DateTimeTypeImpl.DateTimeStringImpl dateTimeString;
    @XmlElement(name = "DateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;

    public DateTimeType.DateTimeString getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(DateTimeType.DateTimeString value) {
        this.dateTimeString = ((DateTimeTypeImpl.DateTimeStringImpl) value);
    }

    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class DateTimeStringImpl
        implements Serializable, DateTimeType.DateTimeString
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
