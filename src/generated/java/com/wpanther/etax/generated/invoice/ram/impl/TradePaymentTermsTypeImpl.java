
package com.wpanther.etax.generated.invoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.invoice.qdt.PaymentTermsTypeCodeType;
import com.wpanther.etax.generated.invoice.qdt.impl.PaymentTermsTypeCodeTypeImpl;
import com.wpanther.etax.generated.invoice.ram.TradePaymentTermsType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePaymentTermsType", propOrder = {
    "description",
    "dueDateDateTime",
    "typeCode"
})
public class TradePaymentTermsTypeImpl
    implements Serializable, TradePaymentTermsType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> description;
    @XmlElement(name = "DueDateDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dueDateDateTime;
    @XmlElement(name = "TypeCode", type = PaymentTermsTypeCodeTypeImpl.class)
    protected PaymentTermsTypeCodeTypeImpl typeCode;

    public List<String> getDescription() {
        if (description == null) {
            description = new ArrayList<String>();
        }
        return this.description;
    }

    public XMLGregorianCalendar getDueDateDateTime() {
        return dueDateDateTime;
    }

    public void setDueDateDateTime(XMLGregorianCalendar value) {
        this.dueDateDateTime = value;
    }

    public PaymentTermsTypeCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(PaymentTermsTypeCodeType value) {
        this.typeCode = ((PaymentTermsTypeCodeTypeImpl) value);
    }

}
