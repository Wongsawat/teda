
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.receipt.ram.HeaderTradeAgreementType;
import com.wpanther.etax.generated.receipt.ram.ReferencedDocumentType;
import com.wpanther.etax.generated.receipt.ram.TradeDeliveryTermsType;
import com.wpanther.etax.generated.receipt.ram.TradePartyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeAgreementType", propOrder = {
    "sellerTradeParty",
    "buyerTradeParty",
    "applicableTradeDeliveryTerms",
    "buyerOrderReferencedDocument",
    "additionalReferencedDocument"
})
public class HeaderTradeAgreementTypeImpl
    implements Serializable, HeaderTradeAgreementType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SellerTradeParty", required = true, type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl sellerTradeParty;
    @XmlElement(name = "BuyerTradeParty", required = true, type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl buyerTradeParty;
    @XmlElement(name = "ApplicableTradeDeliveryTerms", type = TradeDeliveryTermsTypeImpl.class)
    protected TradeDeliveryTermsTypeImpl applicableTradeDeliveryTerms;
    @XmlElement(name = "BuyerOrderReferencedDocument", type = ReferencedDocumentTypeImpl.class)
    protected ReferencedDocumentTypeImpl buyerOrderReferencedDocument;
    @XmlElement(name = "AdditionalReferencedDocument", type = ReferencedDocumentTypeImpl.class)
    protected List<ReferencedDocumentType> additionalReferencedDocument;

    public TradePartyType getSellerTradeParty() {
        return sellerTradeParty;
    }

    public void setSellerTradeParty(TradePartyType value) {
        this.sellerTradeParty = ((TradePartyTypeImpl) value);
    }

    public TradePartyType getBuyerTradeParty() {
        return buyerTradeParty;
    }

    public void setBuyerTradeParty(TradePartyType value) {
        this.buyerTradeParty = ((TradePartyTypeImpl) value);
    }

    public TradeDeliveryTermsType getApplicableTradeDeliveryTerms() {
        return applicableTradeDeliveryTerms;
    }

    public void setApplicableTradeDeliveryTerms(TradeDeliveryTermsType value) {
        this.applicableTradeDeliveryTerms = ((TradeDeliveryTermsTypeImpl) value);
    }

    public ReferencedDocumentType getBuyerOrderReferencedDocument() {
        return buyerOrderReferencedDocument;
    }

    public void setBuyerOrderReferencedDocument(ReferencedDocumentType value) {
        this.buyerOrderReferencedDocument = ((ReferencedDocumentTypeImpl) value);
    }

    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        if (additionalReferencedDocument == null) {
            additionalReferencedDocument = new ArrayList<ReferencedDocumentType>();
        }
        return this.additionalReferencedDocument;
    }

}
