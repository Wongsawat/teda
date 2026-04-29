
package com.wpanther.etax.generated.common.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.ThaiMessageFunctionCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import un.unece.uncefact.codelist.standard.etda.thaimessagefunctioncode._2560.ThaiMessageFunctionCodeContentType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThaiMessageFunctionCodeType", propOrder = {
    "value"
})
public class ThaiMessageFunctionCodeTypeImpl
    implements Serializable, ThaiMessageFunctionCodeType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected ThaiMessageFunctionCodeContentType value;

    public ThaiMessageFunctionCodeContentType getValue() {
        return value;
    }

    public void setValue(ThaiMessageFunctionCodeContentType value) {
        this.value = value;
    }

}
