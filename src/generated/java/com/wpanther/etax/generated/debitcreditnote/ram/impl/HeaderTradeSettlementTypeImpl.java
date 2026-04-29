
package com.wpanther.etax.generated.debitcreditnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.CurrencyCodeType;
import com.wpanther.etax.generated.common.qdt.impl.CurrencyCodeTypeImpl;
import com.wpanther.etax.generated.debitcreditnote.ram.HeaderTradeSettlementType;
import com.wpanther.etax.generated.debitcreditnote.ram.TradeAllowanceChargeType;
import com.wpanther.etax.generated.debitcreditnote.ram.TradePartyType;
import com.wpanther.etax.generated.debitcreditnote.ram.TradePaymentTermsType;
import com.wpanther.etax.generated.debitcreditnote.ram.TradeSettlementMonetaryHeaderSummationType;
import com.wpanther.etax.generated.debitcreditnote.ram.TradeTaxType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeSettlementType", propOrder = {
    "invoiceCurrencyCode",
    "applicableTradeTax",
    "specifiedTradeAllowanceCharge",
    "specifiedTradePaymentTerms",
    "specifiedTradeSettlementHeaderMonetarySummation",
    "invoicerTradeParty",
    "invoiceeTradeParty",
    "payerTradeParty",
    "payeeTradeParty"
})
public class HeaderTradeSettlementTypeImpl
    implements Serializable, HeaderTradeSettlementType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "InvoiceCurrencyCode", type = CurrencyCodeTypeImpl.class)
    protected CurrencyCodeTypeImpl invoiceCurrencyCode;
    @XmlElement(name = "ApplicableTradeTax", required = true, type = TradeTaxTypeImpl.class)
    protected List<TradeTaxType> applicableTradeTax;
    @XmlElement(name = "SpecifiedTradeAllowanceCharge", type = TradeAllowanceChargeTypeImpl.class)
    protected List<TradeAllowanceChargeType> specifiedTradeAllowanceCharge;
    @XmlElement(name = "SpecifiedTradePaymentTerms", type = TradePaymentTermsTypeImpl.class)
    protected List<TradePaymentTermsType> specifiedTradePaymentTerms;
    @XmlElement(name = "SpecifiedTradeSettlementHeaderMonetarySummation", required = true, type = TradeSettlementMonetaryHeaderSummationTypeImpl.class)
    protected TradeSettlementMonetaryHeaderSummationTypeImpl specifiedTradeSettlementHeaderMonetarySummation;
    @XmlElement(name = "InvoicerTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl invoicerTradeParty;
    @XmlElement(name = "InvoiceeTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl invoiceeTradeParty;
    @XmlElement(name = "PayerTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl payerTradeParty;
    @XmlElement(name = "PayeeTradeParty", type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl payeeTradeParty;

    public CurrencyCodeType getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }

    public void setInvoiceCurrencyCode(CurrencyCodeType value) {
        this.invoiceCurrencyCode = ((CurrencyCodeTypeImpl) value);
    }

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

    public List<TradePaymentTermsType> getSpecifiedTradePaymentTerms() {
        if (specifiedTradePaymentTerms == null) {
            specifiedTradePaymentTerms = new ArrayList<TradePaymentTermsType>();
        }
        return this.specifiedTradePaymentTerms;
    }

    public TradeSettlementMonetaryHeaderSummationType getSpecifiedTradeSettlementHeaderMonetarySummation() {
        return specifiedTradeSettlementHeaderMonetarySummation;
    }

    public void setSpecifiedTradeSettlementHeaderMonetarySummation(TradeSettlementMonetaryHeaderSummationType value) {
        this.specifiedTradeSettlementHeaderMonetarySummation = ((TradeSettlementMonetaryHeaderSummationTypeImpl) value);
    }

    public TradePartyType getInvoicerTradeParty() {
        return invoicerTradeParty;
    }

    public void setInvoicerTradeParty(TradePartyType value) {
        this.invoicerTradeParty = ((TradePartyTypeImpl) value);
    }

    public TradePartyType getInvoiceeTradeParty() {
        return invoiceeTradeParty;
    }

    public void setInvoiceeTradeParty(TradePartyType value) {
        this.invoiceeTradeParty = ((TradePartyTypeImpl) value);
    }

    public TradePartyType getPayerTradeParty() {
        return payerTradeParty;
    }

    public void setPayerTradeParty(TradePartyType value) {
        this.payerTradeParty = ((TradePartyTypeImpl) value);
    }

    public TradePartyType getPayeeTradeParty() {
        return payeeTradeParty;
    }

    public void setPayeeTradeParty(TradePartyType value) {
        this.payeeTradeParty = ((TradePartyTypeImpl) value);
    }

}
