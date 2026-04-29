
package com.wpanther.etax.generated.common.udt.impl;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.udt.DateType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateType", propOrder = {
    "dateString",
    "date"
})
public class DateTypeImpl
    implements Serializable, DateType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DateString", type = DateTypeImpl.DateStringImpl.class)
    protected DateTypeImpl.DateStringImpl dateString;
    @XmlElement(name = "Date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;

    public DateType.DateString getDateString() {
        return dateString;
    }

    public void setDateString(DateType.DateString value) {
        this.dateString = ((DateTypeImpl.DateStringImpl) value);
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class DateStringImpl
        implements Serializable, DateType.DateString
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
