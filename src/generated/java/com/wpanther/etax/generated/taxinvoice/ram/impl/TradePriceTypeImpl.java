
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.AmountType;
import com.wpanther.etax.generated.common.qdt.impl.AmountTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.TradeAllowanceChargeType;
import com.wpanther.etax.generated.taxinvoice.ram.TradePriceType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePriceType", propOrder = {
    "chargeAmount",
    "appliedTradeAllowanceCharge"
})
public class TradePriceTypeImpl
    implements Serializable, TradePriceType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ChargeAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> chargeAmount;
    @XmlElement(name = "AppliedTradeAllowanceCharge", type = TradeAllowanceChargeTypeImpl.class)
    protected List<TradeAllowanceChargeType> appliedTradeAllowanceCharge;

    public List<AmountType> getChargeAmount() {
        if (chargeAmount == null) {
            chargeAmount = new ArrayList<AmountType>();
        }
        return this.chargeAmount;
    }

    public List<TradeAllowanceChargeType> getAppliedTradeAllowanceCharge() {
        if (appliedTradeAllowanceCharge == null) {
            appliedTradeAllowanceCharge = new ArrayList<TradeAllowanceChargeType>();
        }
        return this.appliedTradeAllowanceCharge;
    }

}
