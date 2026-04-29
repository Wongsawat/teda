
package com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.LineTradeSettlementType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.TradeAllowanceChargeType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.TradeSettlementMonetarySummationType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.TradeTaxType;
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
    @XmlElement(name = "SpecifiedTradeSettlementLineMonetarySummation", required = true, type = TradeSettlementMonetarySummationTypeImpl.class)
    protected TradeSettlementMonetarySummationTypeImpl specifiedTradeSettlementLineMonetarySummation;

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

    public TradeSettlementMonetarySummationType getSpecifiedTradeSettlementLineMonetarySummation() {
        return specifiedTradeSettlementLineMonetarySummation;
    }

    public void setSpecifiedTradeSettlementLineMonetarySummation(TradeSettlementMonetarySummationType value) {
        this.specifiedTradeSettlementLineMonetarySummation = ((TradeSettlementMonetarySummationTypeImpl) value);
    }

}
