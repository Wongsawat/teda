
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.cancellationnote.ram.HeaderTradeAgreementType;
import com.wpanther.etax.generated.cancellationnote.ram.HeaderTradeSettlementType;
import com.wpanther.etax.generated.cancellationnote.ram.SupplyChainTradeTransactionType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainTradeTransactionType", propOrder = {
    "applicableHeaderTradeAgreement",
    "applicableHeaderTradeSettlement"
})
public class SupplyChainTradeTransactionTypeImpl
    implements Serializable, SupplyChainTradeTransactionType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ApplicableHeaderTradeAgreement", required = true, type = HeaderTradeAgreementTypeImpl.class)
    protected HeaderTradeAgreementTypeImpl applicableHeaderTradeAgreement;
    @XmlElement(name = "ApplicableHeaderTradeSettlement", required = true, type = HeaderTradeSettlementTypeImpl.class)
    protected HeaderTradeSettlementTypeImpl applicableHeaderTradeSettlement;

    public HeaderTradeAgreementType getApplicableHeaderTradeAgreement() {
        return applicableHeaderTradeAgreement;
    }

    public void setApplicableHeaderTradeAgreement(HeaderTradeAgreementType value) {
        this.applicableHeaderTradeAgreement = ((HeaderTradeAgreementTypeImpl) value);
    }

    public HeaderTradeSettlementType getApplicableHeaderTradeSettlement() {
        return applicableHeaderTradeSettlement;
    }

    public void setApplicableHeaderTradeSettlement(HeaderTradeSettlementType value) {
        this.applicableHeaderTradeSettlement = ((HeaderTradeSettlementTypeImpl) value);
    }

}
