
package com.wpanther.etax.generated.invoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.invoice.ram.TradeSettlementMonetarySummationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementMonetarySummationType", propOrder = {
    "taxTotalAmount",
    "netLineTotalAmount",
    "netIncludingTaxesLineTotalAmount",
    "totalPrepaidAmount"
})
public class TradeSettlementMonetarySummationTypeImpl
    implements Serializable, TradeSettlementMonetarySummationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TaxTotalAmount", type = com.wpanther.etax.generated.invoice.qdt.impl.AmountTypeImpl.class)
    protected List<com.wpanther.etax.generated.invoice.qdt.AmountType> taxTotalAmount;
    @XmlElement(name = "NetLineTotalAmount", type = com.wpanther.etax.generated.invoice.qdt.impl.AmountTypeImpl.class)
    protected List<com.wpanther.etax.generated.invoice.qdt.AmountType> netLineTotalAmount;
    @XmlElement(name = "NetIncludingTaxesLineTotalAmount", type = com.wpanther.etax.generated.invoice.qdt.impl.AmountTypeImpl.class)
    protected List<com.wpanther.etax.generated.invoice.qdt.AmountType> netIncludingTaxesLineTotalAmount;
    @XmlElement(name = "TotalPrepaidAmount", type = com.wpanther.etax.generated.common.udt.impl.AmountTypeImpl.class)
    protected List<com.wpanther.etax.generated.common.udt.AmountType> totalPrepaidAmount;

    public List<com.wpanther.etax.generated.invoice.qdt.AmountType> getTaxTotalAmount() {
        if (taxTotalAmount == null) {
            taxTotalAmount = new ArrayList<com.wpanther.etax.generated.invoice.qdt.AmountType>();
        }
        return this.taxTotalAmount;
    }

    public List<com.wpanther.etax.generated.invoice.qdt.AmountType> getNetLineTotalAmount() {
        if (netLineTotalAmount == null) {
            netLineTotalAmount = new ArrayList<com.wpanther.etax.generated.invoice.qdt.AmountType>();
        }
        return this.netLineTotalAmount;
    }

    public List<com.wpanther.etax.generated.invoice.qdt.AmountType> getNetIncludingTaxesLineTotalAmount() {
        if (netIncludingTaxesLineTotalAmount == null) {
            netIncludingTaxesLineTotalAmount = new ArrayList<com.wpanther.etax.generated.invoice.qdt.AmountType>();
        }
        return this.netIncludingTaxesLineTotalAmount;
    }

    public List<com.wpanther.etax.generated.common.udt.AmountType> getTotalPrepaidAmount() {
        if (totalPrepaidAmount == null) {
            totalPrepaidAmount = new ArrayList<com.wpanther.etax.generated.common.udt.AmountType>();
        }
        return this.totalPrepaidAmount;
    }

}
