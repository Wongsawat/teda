
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.receipt.ram.LineTradeSettlementType;
import com.wpanther.etax.generated.receipt.ram.TradeAllowanceChargeType;
import com.wpanther.etax.generated.receipt.ram.TradeSettlementLineMonetarySummationType;
import com.wpanther.etax.generated.receipt.ram.TradeTaxType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeSettlementType", propOrder = {
    "applicableTradeTax",
    "specifiedTradeAllowanceCharge",
    "specifiedTradeSettlementLineMonetarySummation"
})
public class LineTradeSettlementTypeImpl
    implements Serializable, LineTradeSettlementType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ApplicableTradeTax", type = TradeTaxTypeImpl.class)
    protected List<TradeTaxType> applicableTradeTax;
    @XmlElement(name = "SpecifiedTradeAllowanceCharge", type = TradeAllowanceChargeTypeImpl.class)
    protected List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;
    @XmlElement(name = "SpecifiedTradeSettlementLineMonetarySummation", required = true, type = TradeSettlementLineMonetarySummationTypeImpl.class)
    protected TradeSettlementLineMonetarySummationTypeImpl specifiedTradeSettlementLineMonetarySummation;

    public List<TradeTaxType> getApplicableTradeTax() {
        if (applicableTradeTax == null) {
            applicableTradeTax = new ArrayList<TradeTaxType>();
        }
        return this.applicableTradeTax;
    }

    public List<TradeAllowanceChargeType> getSpecifiedTradeAllowanceCharge() {
        if (specifiedTradeAllowanceCharge == null) {
            specifiedTradeAllowanceCharge = new ArrayList<TradeAllowanceChargeType>();
        }
        return this.specifiedTradeAllowanceCharge;
    }

    public TradeSettlementLineMonetarySummationType getSpecifiedTradeSettlementLineMonetarySummation() {
        return specifiedTradeSettlementLineMonetarySummation;
    }

    public void setSpecifiedTradeSettlementLineMonetarySummation(TradeSettlementLineMonetarySummationType value) {
        this.specifiedTradeSettlementLineMonetarySummation = ((TradeSettlementLineMonetarySummationTypeImpl) value);
    }

}
