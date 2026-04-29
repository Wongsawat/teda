
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.AmountType;
import com.wpanther.etax.generated.common.qdt.impl.AmountTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.LineSettlementMonetarySummationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineSettlementMonetarySummationType", propOrder = {
    "taxTotalAmount",
    "netLineTotalAmount",
    "netIncludingTaxesLineTotalAmount"
})
public class LineSettlementMonetarySummationTypeImpl
    implements Serializable, LineSettlementMonetarySummationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TaxTotalAmount", type = AmountTypeImpl.class)
    protected List<AmountType> taxTotalAmount;
    @XmlElement(name = "NetLineTotalAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> netLineTotalAmount;
    @XmlElement(name = "NetIncludingTaxesLineTotalAmount", type = AmountTypeImpl.class)
    protected List<AmountType> netIncludingTaxesLineTotalAmount;

    public List<AmountType> getTaxTotalAmount() {
        if (taxTotalAmount == null) {
            taxTotalAmount = new ArrayList<AmountType>();
        }
        return this.taxTotalAmount;
    }

    public List<AmountType> getNetLineTotalAmount() {
        if (netLineTotalAmount == null) {
            netLineTotalAmount = new ArrayList<AmountType>();
        }
        return this.netLineTotalAmount;
    }

    public List<AmountType> getNetIncludingTaxesLineTotalAmount() {
        if (netIncludingTaxesLineTotalAmount == null) {
            netIncludingTaxesLineTotalAmount = new ArrayList<AmountType>();
        }
        return this.netIncludingTaxesLineTotalAmount;
    }

}
