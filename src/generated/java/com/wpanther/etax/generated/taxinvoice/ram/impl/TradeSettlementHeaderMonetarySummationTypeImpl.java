
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.AmountType;
import com.wpanther.etax.generated.common.qdt.impl.AmountTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.TradeSettlementHeaderMonetarySummationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementHeaderMonetarySummationType", propOrder = {
    "lineTotalAmount",
    "allowanceTotalAmount",
    "chargeTotalAmount",
    "taxBasisTotalAmount",
    "taxTotalAmount",
    "grandTotalAmount",
    "totalPrepaidAmount"
})
public class TradeSettlementHeaderMonetarySummationTypeImpl
    implements Serializable, TradeSettlementHeaderMonetarySummationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "LineTotalAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> lineTotalAmount;
    @XmlElement(name = "AllowanceTotalAmount", type = AmountTypeImpl.class)
    protected List<AmountType> allowanceTotalAmount;
    @XmlElement(name = "ChargeTotalAmount", type = AmountTypeImpl.class)
    protected List<AmountType> chargeTotalAmount;
    @XmlElement(name = "TaxBasisTotalAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> taxBasisTotalAmount;
    @XmlElement(name = "TaxTotalAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> taxTotalAmount;
    @XmlElement(name = "GrandTotalAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> grandTotalAmount;
    @XmlElement(name = "TotalPrepaidAmount", type = AmountTypeImpl.class)
    protected List<AmountType> totalPrepaidAmount;

    public List<AmountType> getLineTotalAmount() {
        if (lineTotalAmount == null) {
            lineTotalAmount = new ArrayList<AmountType>();
        }
        return this.lineTotalAmount;
    }

    public List<AmountType> getAllowanceTotalAmount() {
        if (allowanceTotalAmount == null) {
            allowanceTotalAmount = new ArrayList<AmountType>();
        }
        return this.allowanceTotalAmount;
    }

    public List<AmountType> getChargeTotalAmount() {
        if (chargeTotalAmount == null) {
            chargeTotalAmount = new ArrayList<AmountType>();
        }
        return this.chargeTotalAmount;
    }

    public List<AmountType> getTaxBasisTotalAmount() {
        if (taxBasisTotalAmount == null) {
            taxBasisTotalAmount = new ArrayList<AmountType>();
        }
        return this.taxBasisTotalAmount;
    }

    public List<AmountType> getTaxTotalAmount() {
        if (taxTotalAmount == null) {
            taxTotalAmount = new ArrayList<AmountType>();
        }
        return this.taxTotalAmount;
    }

    public List<AmountType> getGrandTotalAmount() {
        if (grandTotalAmount == null) {
            grandTotalAmount = new ArrayList<AmountType>();
        }
        return this.grandTotalAmount;
    }

    public List<AmountType> getTotalPrepaidAmount() {
        if (totalPrepaidAmount == null) {
            totalPrepaidAmount = new ArrayList<AmountType>();
        }
        return this.totalPrepaidAmount;
    }

}
