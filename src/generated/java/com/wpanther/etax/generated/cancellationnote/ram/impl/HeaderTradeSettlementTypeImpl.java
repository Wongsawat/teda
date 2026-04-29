
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.cancellationnote.ram.HeaderTradeSettlementType;
import com.wpanther.etax.generated.cancellationnote.ram.TradePartyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeSettlementType", propOrder = {
    "invoicerTradeParty"
})
public class HeaderTradeSettlementTypeImpl
    implements Serializable, HeaderTradeSettlementType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "InvoicerTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl invoicerTradeParty;

    public TradePartyType getInvoicerTradeParty() {
        return invoicerTradeParty;
    }

    public void setInvoicerTradeParty(TradePartyType value) {
        this.invoicerTradeParty = ((TradePartyTypeImpl) value);
    }

}
