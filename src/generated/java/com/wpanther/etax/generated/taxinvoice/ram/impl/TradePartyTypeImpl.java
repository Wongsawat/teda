
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.Max256TextType;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.Max70IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max70IDTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.TaxRegistrationType;
import com.wpanther.etax.generated.taxinvoice.ram.TradeAddressType;
import com.wpanther.etax.generated.taxinvoice.ram.TradeContactType;
import com.wpanther.etax.generated.taxinvoice.ram.TradePartyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePartyType", propOrder = {
    "id",
    "globalID",
    "name",
    "specifiedTaxRegistration",
    "definedTradeContact",
    "postalTradeAddress"
})
public class TradePartyTypeImpl
    implements Serializable, TradePartyType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", type = Max35IDTypeImpl.class)
    protected List<Max35IDType> id;
    @XmlElement(name = "GlobalID", type = Max70IDTypeImpl.class)
    protected List<Max70IDType> globalID;
    @XmlElement(name = "Name", type = Max256TextTypeImpl.class)
    protected Max256TextTypeImpl name;
    @XmlElement(name = "SpecifiedTaxRegistration", type = TaxRegistrationTypeImpl.class)
    protected TaxRegistrationTypeImpl specifiedTaxRegistration;
    @XmlElement(name = "DefinedTradeContact", type = TradeContactTypeImpl.class)
    protected List<TradeContactType> definedTradeContact;
    @XmlElement(name = "PostalTradeAddress", type = TradeAddressTypeImpl.class)
    protected TradeAddressTypeImpl postalTradeAddress;

    public List<Max35IDType> getID() {
        if (id == null) {
            id = new ArrayList<Max35IDType>();
        }
        return this.id;
    }

    public List<Max70IDType> getGlobalID() {
        if (globalID == null) {
            globalID = new ArrayList<Max70IDType>();
        }
        return this.globalID;
    }

    public Max256TextType getName() {
        return name;
    }

    public void setName(Max256TextType value) {
        this.name = ((Max256TextTypeImpl) value);
    }

    public TaxRegistrationType getSpecifiedTaxRegistration() {
        return specifiedTaxRegistration;
    }

    public void setSpecifiedTaxRegistration(TaxRegistrationType value) {
        this.specifiedTaxRegistration = ((TaxRegistrationTypeImpl) value);
    }

    public List<TradeContactType> getDefinedTradeContact() {
        if (definedTradeContact == null) {
            definedTradeContact = new ArrayList<TradeContactType>();
        }
        return this.definedTradeContact;
    }

    public TradeAddressType getPostalTradeAddress() {
        return postalTradeAddress;
    }

    public void setPostalTradeAddress(TradeAddressType value) {
        this.postalTradeAddress = ((TradeAddressTypeImpl) value);
    }

}
