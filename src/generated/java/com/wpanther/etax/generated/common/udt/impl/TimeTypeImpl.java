
package com.wpanther.etax.generated.common.udt.impl;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.udt.TimeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeType", propOrder = {
    "timeString",
    "time"
})
public class TimeTypeImpl
    implements Serializable, TimeType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TimeString", type = TimeTypeImpl.TimeStringImpl.class)
    protected TimeTypeImpl.TimeStringImpl timeString;
    @XmlElement(name = "Time")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar time;

    public TimeType.TimeString getTimeString() {
        return timeString;
    }

    public void setTimeString(TimeType.TimeString value) {
        this.timeString = ((TimeTypeImpl.TimeStringImpl) value);
    }

    public XMLGregorianCalendar getTime() {
        return time;
    }

    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class TimeStringImpl
        implements Serializable, TimeType.TimeString
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
