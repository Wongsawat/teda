
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.cancellationnote.ram.HeaderTradeAgreementType;
import com.wpanther.etax.generated.cancellationnote.ram.ReferencedDocumentType;
import com.wpanther.etax.generated.cancellationnote.ram.TradePartyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeAgreementType", propOrder = {
    "sellerTradeParty",
    "additionalReferencedDocument"
})
public class HeaderTradeAgreementTypeImpl
    implements Serializable, HeaderTradeAgreementType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SellerTradeParty", required = true, type = TradePartyTypeImpl.class)
    protected TradePartyTypeImpl sellerTradeParty;
    @XmlElement(name = "AdditionalReferencedDocument", required = true, type = ReferencedDocumentTypeImpl.class)
    protected List<ReferencedDocumentType> additionalReferencedDocument;

    public TradePartyType getSellerTradeParty() {
        return sellerTradeParty;
    }

    public void setSellerTradeParty(TradePartyType value) {
        this.sellerTradeParty = ((TradePartyTypeImpl) value);
    }

    public List<ReferencedDocumentType> getAdditionalReferencedDocument() {
        if (additionalReferencedDocument == null) {
            additionalReferencedDocument = new ArrayList<ReferencedDocumentType>();
        }
        return this.additionalReferencedDocument;
    }

}
