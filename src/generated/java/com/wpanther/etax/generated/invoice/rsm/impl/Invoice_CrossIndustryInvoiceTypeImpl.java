
package com.wpanther.etax.generated.invoice.rsm.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.invoice.ram.ExchangedDocumentContextType;
import com.wpanther.etax.generated.invoice.ram.ExchangedDocumentType;
import com.wpanther.etax.generated.invoice.ram.SupplyChainTradeTransactionType;
import com.wpanther.etax.generated.invoice.ram.impl.ExchangedDocumentContextTypeImpl;
import com.wpanther.etax.generated.invoice.ram.impl.ExchangedDocumentTypeImpl;
import com.wpanther.etax.generated.invoice.ram.impl.SupplyChainTradeTransactionTypeImpl;
import com.wpanther.etax.generated.invoice.rsm.Invoice_CrossIndustryInvoiceType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.SignatureType;
import org.w3._2000._09.xmldsig_.impl.SignatureTypeImpl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Invoice_CrossIndustryInvoiceType", propOrder = {
    "exchangedDocumentContext",
    "exchangedDocument",
    "supplyChainTradeTransaction",
    "signature"
})
public class Invoice_CrossIndustryInvoiceTypeImpl
    implements Serializable, Invoice_CrossIndustryInvoiceType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ExchangedDocumentContext", required = true, type = ExchangedDocumentContextTypeImpl.class)
    protected ExchangedDocumentContextTypeImpl exchangedDocumentContext;
    @XmlElement(name = "ExchangedDocument", required = true, type = ExchangedDocumentTypeImpl.class)
    protected ExchangedDocumentTypeImpl exchangedDocument;
    @XmlElement(name = "SupplyChainTradeTransaction", required = true, type = SupplyChainTradeTransactionTypeImpl.class)
    protected SupplyChainTradeTransactionTypeImpl supplyChainTradeTransaction;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#", type = SignatureTypeImpl.class)
    protected SignatureTypeImpl signature;

    public ExchangedDocumentContextType getExchangedDocumentContext() {
        return exchangedDocumentContext;
    }

    public void setExchangedDocumentContext(ExchangedDocumentContextType value) {
        this.exchangedDocumentContext = ((ExchangedDocumentContextTypeImpl) value);
    }

    public ExchangedDocumentType getExchangedDocument() {
        return exchangedDocument;
    }

    public void setExchangedDocument(ExchangedDocumentType value) {
        this.exchangedDocument = ((ExchangedDocumentTypeImpl) value);
    }

    public SupplyChainTradeTransactionType getSupplyChainTradeTransaction() {
        return supplyChainTradeTransaction;
    }

    public void setSupplyChainTradeTransaction(SupplyChainTradeTransactionType value) {
        this.supplyChainTradeTransaction = ((SupplyChainTradeTransactionTypeImpl) value);
    }

    public SignatureType getSignature() {
        return signature;
    }

    public void setSignature(SignatureType value) {
        this.signature = ((SignatureTypeImpl) value);
    }

}
