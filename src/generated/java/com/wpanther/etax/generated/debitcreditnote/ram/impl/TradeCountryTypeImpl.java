
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.CountryIDType;
import com.wpanther.etax.generated.common.qdt.impl.CountryIDTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.TradeCountryType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeCountryType", propOrder = {
    "id"
})
public class TradeCountryTypeImpl
    implements Serializable, TradeCountryType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", type = CountryIDTypeImpl.class)
    protected CountryIDTypeImpl id;

    public CountryIDType getID() {
        return id;
    }

    public void setID(CountryIDType value) {
        this.id = ((CountryIDTypeImpl) value);
    }

}
