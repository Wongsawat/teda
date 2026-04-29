
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.cancellationnote.ram.SpecifiedTaxRegistrationType;
import com.wpanther.etax.generated.cancellationnote.ram.TradeAddressType;
import com.wpanther.etax.generated.cancellationnote.ram.TradeContactType;
import com.wpanther.etax.generated.cancellationnote.ram.TradePartyType;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.Max70IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max70IDTypeImpl;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    @XmlElement(name = "Name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @XmlElement(name = "SpecifiedTaxRegistration", required = true, type = SpecifiedTaxRegistrationTypeImpl.class)
    protected SpecifiedTaxRegistrationTypeImpl specifiedTaxRegistration;
    @XmlElement(name = "DefinedTradeContact", type = TradeContactTypeImpl.class)
    protected List<TradeContactType> definedTradeContact;
    @XmlElement(name = "PostalTradeAddress", required = true, type = TradeAddressTypeImpl.class)
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

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public SpecifiedTaxRegistrationType getSpecifiedTaxRegistration() {
        return specifiedTaxRegistration;
    }

    public void setSpecifiedTaxRegistration(SpecifiedTaxRegistrationType value) {
        this.specifiedTaxRegistration = ((SpecifiedTaxRegistrationTypeImpl) value);
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
