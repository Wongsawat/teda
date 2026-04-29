
package com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.HeaderTradeAgreementType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.HeaderTradeDeliveryType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.HeaderTradeSettlementType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.SupplyChainTradeLineItemType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.SupplyChainTradeTransactionType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainTradeTransactionType", propOrder = {
    "applicableHeaderTradeAgreement",
    "applicableHeaderTradeDelivery",
    "applicableHeaderTradeSettlement",
    "includedSupplyChainTradeLineItem"
})
public class SupplyChainTradeTransactionTypeImpl
    implements Serializable, SupplyChainTradeTransactionType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ApplicableHeaderTradeAgreement", required = true, type = HeaderTradeAgreementTypeImpl.class)
    protected HeaderTradeAgreementTypeImpl applicableHeaderTradeAgreement;
    @XmlElement(name = "ApplicableHeaderTradeDelivery", required = true, type = HeaderTradeDeliveryTypeImpl.class)
    protected HeaderTradeDeliveryTypeImpl applicableHeaderTradeDelivery;
    @XmlElement(name = "ApplicableHeaderTradeSettlement", required = true, type = HeaderTradeSettlementTypeImpl.class)
    protected HeaderTradeSettlementTypeImpl applicableHeaderTradeSettlement;
    @XmlElement(name = "IncludedSupplyChainTradeLineItem", required = true, type = SupplyChainTradeLineItemTypeImpl.class)
    protected List<SupplyChainTradeLineItemType> includedSupplyChainTradeLineItem;

    public HeaderTradeAgreementType getApplicableHeaderTradeAgreement() {
        return applicableHeaderTradeAgreement;
    }

    public void setApplicableHeaderTradeAgreement(HeaderTradeAgreementType value) {
        this.applicableHeaderTradeAgreement = ((HeaderTradeAgreementTypeImpl) value);
    }

    public HeaderTradeDeliveryType getApplicableHeaderTradeDelivery() {
        return applicableHeaderTradeDelivery;
    }

    public void setApplicableHeaderTradeDelivery(HeaderTradeDeliveryType value) {
        this.applicableHeaderTradeDelivery = ((HeaderTradeDeliveryTypeImpl) value);
    }

    public HeaderTradeSettlementType getApplicableHeaderTradeSettlement() {
        return applicableHeaderTradeSettlement;
    }

    public void setApplicableHeaderTradeSettlement(HeaderTradeSettlementType value) {
        this.applicableHeaderTradeSettlement = ((HeaderTradeSettlementTypeImpl) value);
    }

    public List<SupplyChainTradeLineItemType> getIncludedSupplyChainTradeLineItem() {
        if (includedSupplyChainTradeLineItem == null) {
            includedSupplyChainTradeLineItem = new ArrayList<SupplyChainTradeLineItemType>();
        }
        return this.includedSupplyChainTradeLineItem;
    }

}
