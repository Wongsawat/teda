
package com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.DocumentLineDocumentType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.LineTradeAgreementType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.LineTradeDeliveryType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.LineTradeSettlementType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.SupplyChainTradeLineItemType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.ram.TradeProductType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainTradeLineItemType", propOrder = {
    "associatedDocumentLineDocument",
    "specifiedTradeProduct",
    "specifiedLineTradeAgreement",
    "specifiedLineTradeDelivery",
    "specifiedLineTradeSettlement"
})
public class SupplyChainTradeLineItemTypeImpl
    implements Serializable, SupplyChainTradeLineItemType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "AssociatedDocumentLineDocument", required = true, type = DocumentLineDocumentTypeImpl.class)
    protected DocumentLineDocumentTypeImpl associatedDocumentLineDocument;
    @XmlElement(name = "SpecifiedTradeProduct", required = true, type = TradeProductTypeImpl.class)
    protected TradeProductTypeImpl specifiedTradeProduct;
    @XmlElement(name = "SpecifiedLineTradeAgreement", required = true, type = LineTradeAgreementTypeImpl.class)
    protected LineTradeAgreementTypeImpl specifiedLineTradeAgreement;
    @XmlElement(name = "SpecifiedLineTradeDelivery", required = true, type = LineTradeDeliveryTypeImpl.class)
    protected LineTradeDeliveryTypeImpl specifiedLineTradeDelivery;
    @XmlElement(name = "SpecifiedLineTradeSettlement", required = true, type = LineTradeSettlementTypeImpl.class)
    protected LineTradeSettlementTypeImpl specifiedLineTradeSettlement;

    public DocumentLineDocumentType getAssociatedDocumentLineDocument() {
        return associatedDocumentLineDocument;
    }

    public void setAssociatedDocumentLineDocument(DocumentLineDocumentType value) {
        this.associatedDocumentLineDocument = ((DocumentLineDocumentTypeImpl) value);
    }

    public TradeProductType getSpecifiedTradeProduct() {
        return specifiedTradeProduct;
    }

    public void setSpecifiedTradeProduct(TradeProductType value) {
        this.specifiedTradeProduct = ((TradeProductTypeImpl) value);
    }

    public LineTradeAgreementType getSpecifiedLineTradeAgreement() {
        return specifiedLineTradeAgreement;
    }

    public void setSpecifiedLineTradeAgreement(LineTradeAgreementType value) {
        this.specifiedLineTradeAgreement = ((LineTradeAgreementTypeImpl) value);
    }

    public LineTradeDeliveryType getSpecifiedLineTradeDelivery() {
        return specifiedLineTradeDelivery;
    }

    public void setSpecifiedLineTradeDelivery(LineTradeDeliveryType value) {
        this.specifiedLineTradeDelivery = ((LineTradeDeliveryTypeImpl) value);
    }

    public LineTradeSettlementType getSpecifiedLineTradeSettlement() {
        return specifiedLineTradeSettlement;
    }

    public void setSpecifiedLineTradeSettlement(LineTradeSettlementType value) {
        this.specifiedLineTradeSettlement = ((LineTradeSettlementTypeImpl) value);
    }

}
