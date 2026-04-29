
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.cancellationnote.ram.TradeSettlementMonetaryHeaderSummationType;
import com.wpanther.etax.generated.common.qdt.AmountType;
import com.wpanther.etax.generated.common.qdt.impl.AmountTypeImpl;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementMonetaryHeaderSummationType", propOrder = {
    "originalInformationAmount",
    "lineTotalAmount",
    "differenceInformationAmount",
    "allowanceTotalAmount",
    "chargeTotalAmount",
    "taxBasisTotalAmount",
    "taxTotalAmount",
    "grandTotalAmount"
})
public class TradeSettlementMonetaryHeaderSummationTypeImpl
    implements Serializable, TradeSettlementMonetaryHeaderSummationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "OriginalInformationAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> originalInformationAmount;
    @XmlElement(name = "LineTotalAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> lineTotalAmount;
    @XmlElement(name = "DifferenceInformationAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> differenceInformationAmount;
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

    public List<AmountType> getOriginalInformationAmount() {
        if (originalInformationAmount == null) {
            originalInformationAmount = new ArrayList<AmountType>();
        }
        return this.originalInformationAmount;
    }

    public List<AmountType> getLineTotalAmount() {
        if (lineTotalAmount == null) {
            lineTotalAmount = new ArrayList<AmountType>();
        }
        return this.lineTotalAmount;
    }

    public List<AmountType> getDifferenceInformationAmount() {
        if (differenceInformationAmount == null) {
            differenceInformationAmount = new ArrayList<AmountType>();
        }
        return this.differenceInformationAmount;
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

}
