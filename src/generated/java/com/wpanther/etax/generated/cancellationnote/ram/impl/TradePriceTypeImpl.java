
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.cancellationnote.ram.TradeAllowanceChargeType;
import com.wpanther.etax.generated.cancellationnote.ram.TradePriceType;
import com.wpanther.etax.generated.common.qdt.AmountType;
import com.wpanther.etax.generated.common.qdt.impl.AmountTypeImpl;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePriceType", propOrder = {
    "chargeAmount",
    "appliedCITradeAllowanceCharge"
})
public class TradePriceTypeImpl
    implements Serializable, TradePriceType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ChargeAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> chargeAmount;
    @XmlElement(name = "AppliedCITradeAllowanceCharge", type = TradeAllowanceChargeTypeImpl.class)
    protected List<TradeAllowanceChargeType> appliedCITradeAllowanceCharge;

    public List<AmountType> getChargeAmount() {
        if (chargeAmount == null) {
            chargeAmount = new ArrayList<AmountType>();
        }
        return this.chargeAmount;
    }

    public List<TradeAllowanceChargeType> getAppliedCITradeAllowanceCharge() {
        if (appliedCITradeAllowanceCharge == null) {
            appliedCITradeAllowanceCharge = new ArrayList<TradeAllowanceChargeType>();
        }
        return this.appliedCITradeAllowanceCharge;
    }

}
