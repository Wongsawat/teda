
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.taxinvoice.ram.SupplyChainEventType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainEventType", propOrder = {
    "occurrenceDateTime"
})
public class SupplyChainEventTypeImpl
    implements Serializable, SupplyChainEventType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "OccurrenceDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar occurrenceDateTime;

    public XMLGregorianCalendar getOccurrenceDateTime() {
        return occurrenceDateTime;
    }

    public void setOccurrenceDateTime(XMLGregorianCalendar value) {
        this.occurrenceDateTime = value;
    }

}
