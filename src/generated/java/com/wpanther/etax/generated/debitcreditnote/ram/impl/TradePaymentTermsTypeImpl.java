
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.qdt.Max256TextType;
import com.wpanther.etax.generated.common.qdt.PaymentTermsTypeCodeType;
import com.wpanther.etax.generated.common.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.PaymentTermsTypeCodeTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.TradePaymentTermsType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

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
    @XmlElement(name = "Description", type = Max256TextTypeImpl.class)
    protected List<Max256TextType> description;
    @XmlElement(name = "DueDateDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dueDateDateTime;
    @XmlElement(name = "TypeCode", type = PaymentTermsTypeCodeTypeImpl.class)
    protected PaymentTermsTypeCodeTypeImpl typeCode;

    public List<Max256TextType> getDescription() {
        if (description == null) {
            description = new ArrayList<Max256TextType>();
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
