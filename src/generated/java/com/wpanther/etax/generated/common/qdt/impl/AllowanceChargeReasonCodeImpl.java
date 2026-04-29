
package com.wpanther.etax.generated.common.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.AllowanceChargeReasonCode;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllowanceChargeReasonCode", propOrder = {
    "value"
})
public class AllowanceChargeReasonCodeImpl
    implements Serializable, AllowanceChargeReasonCode
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
