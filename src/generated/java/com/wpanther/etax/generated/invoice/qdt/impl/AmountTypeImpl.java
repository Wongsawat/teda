
package com.wpanther.etax.generated.invoice.qdt.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import com.wpanther.etax.generated.invoice.qdt.AmountType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import un.unece.uncefact.codelist.standard.iso.iso3alphacurrencycode._2012_08_31.ISO3AlphaCurrencyCodeContentType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountType", propOrder = {
    "value"
})
public class AmountTypeImpl
    implements Serializable, AmountType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "currencyID")
    protected ISO3AlphaCurrencyCodeContentType currencyID;
    @XmlAttribute(name = "currencyCodeListVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String currencyCodeListVersionID;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public ISO3AlphaCurrencyCodeContentType getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(ISO3AlphaCurrencyCodeContentType value) {
        this.currencyID = value;
    }

    public String getCurrencyCodeListVersionID() {
        return currencyCodeListVersionID;
    }

    public void setCurrencyCodeListVersionID(String value) {
        this.currencyCodeListVersionID = value;
    }

}
