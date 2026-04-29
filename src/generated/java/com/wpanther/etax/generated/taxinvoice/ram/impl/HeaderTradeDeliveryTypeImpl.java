
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.taxinvoice.ram.HeaderTradeDeliveryType;
import com.wpanther.etax.generated.taxinvoice.ram.SupplyChainEventType;
import com.wpanther.etax.generated.taxinvoice.ram.TradePartyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeDeliveryType", propOrder = {
    "shipToTradeParty",
    "shipFromTradeParty",
    "actualDeliverySupplyChainEvent"
})
public class HeaderTradeDeliveryTypeImpl
    implements Serializable, HeaderTradeDeliveryType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ShipToTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl shipToTradeParty;
    @XmlElement(name = "ShipFromTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl shipFromTradeParty;
    @XmlElement(name = "ActualDeliverySupplyChainEvent", type = SupplyChainEventTypeImpl.class)
    protected SupplyChainEventTypeImpl actualDeliverySupplyChainEvent;

    public TradePartyType getShipToTradeParty() {
        return shipToTradeParty;
    }

    public void setShipToTradeParty(TradePartyType value) {
        this.shipToTradeParty = ((TradePartyTypeImpl) value);
    }

    public TradePartyType getShipFromTradeParty() {
        return shipFromTradeParty;
    }

    public void setShipFromTradeParty(TradePartyType value) {
        this.shipFromTradeParty = ((TradePartyTypeImpl) value);
    }

    public SupplyChainEventType getActualDeliverySupplyChainEvent() {
        return actualDeliverySupplyChainEvent;
    }

    public void setActualDeliverySupplyChainEvent(SupplyChainEventType value) {
        this.actualDeliverySupplyChainEvent = ((SupplyChainEventTypeImpl) value);
    }

}
