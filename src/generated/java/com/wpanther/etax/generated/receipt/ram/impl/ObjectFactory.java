
package com.wpanther.etax.generated.receipt.ram.impl;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.receipt.ram.DesignatedProductClassificationType;
import com.wpanther.etax.generated.receipt.ram.DocumentContextParameterType;
import com.wpanther.etax.generated.receipt.ram.DocumentLineDocumentType;
import com.wpanther.etax.generated.receipt.ram.EmailUniversalCommunicationType;
import com.wpanther.etax.generated.receipt.ram.ExchangedDocumentContextType;
import com.wpanther.etax.generated.receipt.ram.ExchangedDocumentType;
import com.wpanther.etax.generated.receipt.ram.HeaderTradeAgreementType;
import com.wpanther.etax.generated.receipt.ram.HeaderTradeDeliveryType;
import com.wpanther.etax.generated.receipt.ram.HeaderTradeSettlementType;
import com.wpanther.etax.generated.receipt.ram.LineTradeAgreementType;
import com.wpanther.etax.generated.receipt.ram.LineTradeDeliveryType;
import com.wpanther.etax.generated.receipt.ram.LineTradeSettlementType;
import com.wpanther.etax.generated.receipt.ram.NoteType;
import com.wpanther.etax.generated.receipt.ram.ReferencedDocumentType;
import com.wpanther.etax.generated.receipt.ram.SupplyChainEventType;
import com.wpanther.etax.generated.receipt.ram.SupplyChainTradeLineItemType;
import com.wpanther.etax.generated.receipt.ram.SupplyChainTradeTransactionType;
import com.wpanther.etax.generated.receipt.ram.TaxRegistrationType;
import com.wpanther.etax.generated.receipt.ram.TelephoneUniversalCommunicationType;
import com.wpanther.etax.generated.receipt.ram.TradeAddressType;
import com.wpanther.etax.generated.receipt.ram.TradeAllowanceChargeType;
import com.wpanther.etax.generated.receipt.ram.TradeContactType;
import com.wpanther.etax.generated.receipt.ram.TradeCountryType;
import com.wpanther.etax.generated.receipt.ram.TradeDeliveryTermsType;
import com.wpanther.etax.generated.receipt.ram.TradePartyType;
import com.wpanther.etax.generated.receipt.ram.TradePaymentTermsType;
import com.wpanther.etax.generated.receipt.ram.TradePriceType;
import com.wpanther.etax.generated.receipt.ram.TradeProductInstanceType;
import com.wpanther.etax.generated.receipt.ram.TradeProductType;
import com.wpanther.etax.generated.receipt.ram.TradeSettlementHeaderMonetarySummationType;
import com.wpanther.etax.generated.receipt.ram.TradeSettlementLineMonetarySummationType;
import com.wpanther.etax.generated.receipt.ram.TradeTaxType;
import com.wpanther.etax.generated.receipt.ram.UniversalCommunicationType;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.receipt.ram.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExchangedDocumentContext_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "ExchangedDocumentContext");
    private final static QName _DocumentContextParameter_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "DocumentContextParameter");
    private final static QName _ExchangedDocument_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "ExchangedDocument");
    private final static QName _SupplyChainTradeTransaction_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "SupplyChainTradeTransaction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.receipt.ram.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExchangedDocumentContextType }
     * 
     */
    public ExchangedDocumentContextTypeImpl createExchangedDocumentContextType() {
        return new ExchangedDocumentContextTypeImpl();
    }

    /**
     * Create an instance of {@link DocumentContextParameterType }
     * 
     */
    public DocumentContextParameterTypeImpl createDocumentContextParameterType() {
        return new DocumentContextParameterTypeImpl();
    }

    /**
     * Create an instance of {@link ExchangedDocumentType }
     * 
     */
    public ExchangedDocumentTypeImpl createExchangedDocumentType() {
        return new ExchangedDocumentTypeImpl();
    }

    /**
     * Create an instance of {@link SupplyChainTradeTransactionType }
     * 
     */
    public SupplyChainTradeTransactionTypeImpl createSupplyChainTradeTransactionType() {
        return new SupplyChainTradeTransactionTypeImpl();
    }

    /**
     * Create an instance of {@link NoteType }
     * 
     */
    public NoteTypeImpl createNoteType() {
        return new NoteTypeImpl();
    }

    /**
     * Create an instance of {@link HeaderTradeAgreementType }
     * 
     */
    public HeaderTradeAgreementTypeImpl createHeaderTradeAgreementType() {
        return new HeaderTradeAgreementTypeImpl();
    }

    /**
     * Create an instance of {@link HeaderTradeDeliveryType }
     * 
     */
    public HeaderTradeDeliveryTypeImpl createHeaderTradeDeliveryType() {
        return new HeaderTradeDeliveryTypeImpl();
    }

    /**
     * Create an instance of {@link HeaderTradeSettlementType }
     * 
     */
    public HeaderTradeSettlementTypeImpl createHeaderTradeSettlementType() {
        return new HeaderTradeSettlementTypeImpl();
    }

    /**
     * Create an instance of {@link SupplyChainTradeLineItemType }
     * 
     */
    public SupplyChainTradeLineItemTypeImpl createSupplyChainTradeLineItemType() {
        return new SupplyChainTradeLineItemTypeImpl();
    }

    /**
     * Create an instance of {@link TradePartyType }
     * 
     */
    public TradePartyTypeImpl createTradePartyType() {
        return new TradePartyTypeImpl();
    }

    /**
     * Create an instance of {@link TaxRegistrationType }
     * 
     */
    public TaxRegistrationTypeImpl createTaxRegistrationType() {
        return new TaxRegistrationTypeImpl();
    }

    /**
     * Create an instance of {@link TradeContactType }
     * 
     */
    public TradeContactTypeImpl createTradeContactType() {
        return new TradeContactTypeImpl();
    }

    /**
     * Create an instance of {@link UniversalCommunicationType }
     * 
     */
    public UniversalCommunicationTypeImpl createUniversalCommunicationType() {
        return new UniversalCommunicationTypeImpl();
    }

    /**
     * Create an instance of {@link TradeAddressType }
     * 
     */
    public TradeAddressTypeImpl createTradeAddressType() {
        return new TradeAddressTypeImpl();
    }

    /**
     * Create an instance of {@link TradeDeliveryTermsType }
     * 
     */
    public TradeDeliveryTermsTypeImpl createTradeDeliveryTermsType() {
        return new TradeDeliveryTermsTypeImpl();
    }

    /**
     * Create an instance of {@link ReferencedDocumentType }
     * 
     */
    public ReferencedDocumentTypeImpl createReferencedDocumentType() {
        return new ReferencedDocumentTypeImpl();
    }

    /**
     * Create an instance of {@link SupplyChainEventType }
     * 
     */
    public SupplyChainEventTypeImpl createSupplyChainEventType() {
        return new SupplyChainEventTypeImpl();
    }

    /**
     * Create an instance of {@link TradeTaxType }
     * 
     */
    public TradeTaxTypeImpl createTradeTaxType() {
        return new TradeTaxTypeImpl();
    }

    /**
     * Create an instance of {@link TradeAllowanceChargeType }
     * 
     */
    public TradeAllowanceChargeTypeImpl createTradeAllowanceChargeType() {
        return new TradeAllowanceChargeTypeImpl();
    }

    /**
     * Create an instance of {@link TradePaymentTermsType }
     * 
     */
    public TradePaymentTermsTypeImpl createTradePaymentTermsType() {
        return new TradePaymentTermsTypeImpl();
    }

    /**
     * Create an instance of {@link TradeSettlementHeaderMonetarySummationType }
     * 
     */
    public TradeSettlementHeaderMonetarySummationTypeImpl createTradeSettlementHeaderMonetarySummationType() {
        return new TradeSettlementHeaderMonetarySummationTypeImpl();
    }

    /**
     * Create an instance of {@link DocumentLineDocumentType }
     * 
     */
    public DocumentLineDocumentTypeImpl createDocumentLineDocumentType() {
        return new DocumentLineDocumentTypeImpl();
    }

    /**
     * Create an instance of {@link LineTradeAgreementType }
     * 
     */
    public LineTradeAgreementTypeImpl createLineTradeAgreementType() {
        return new LineTradeAgreementTypeImpl();
    }

    /**
     * Create an instance of {@link TradePriceType }
     * 
     */
    public TradePriceTypeImpl createTradePriceType() {
        return new TradePriceTypeImpl();
    }

    /**
     * Create an instance of {@link LineTradeDeliveryType }
     * 
     */
    public LineTradeDeliveryTypeImpl createLineTradeDeliveryType() {
        return new LineTradeDeliveryTypeImpl();
    }

    /**
     * Create an instance of {@link LineTradeSettlementType }
     * 
     */
    public LineTradeSettlementTypeImpl createLineTradeSettlementType() {
        return new LineTradeSettlementTypeImpl();
    }

    /**
     * Create an instance of {@link TradeSettlementLineMonetarySummationType }
     * 
     */
    public TradeSettlementLineMonetarySummationTypeImpl createTradeSettlementLineMonetarySummationType() {
        return new TradeSettlementLineMonetarySummationTypeImpl();
    }

    /**
     * Create an instance of {@link TradeProductType }
     * 
     */
    public TradeProductTypeImpl createTradeProductType() {
        return new TradeProductTypeImpl();
    }

    /**
     * Create an instance of {@link TradeProductInstanceType }
     * 
     */
    public TradeProductInstanceTypeImpl createTradeProductInstanceType() {
        return new TradeProductInstanceTypeImpl();
    }

    /**
     * Create an instance of {@link TradeCountryType }
     * 
     */
    public TradeCountryTypeImpl createTradeCountryType() {
        return new TradeCountryTypeImpl();
    }

    /**
     * Create an instance of {@link TelephoneUniversalCommunicationType }
     * 
     */
    public TelephoneUniversalCommunicationTypeImpl createTelephoneUniversalCommunicationType() {
        return new TelephoneUniversalCommunicationTypeImpl();
    }

    /**
     * Create an instance of {@link DesignatedProductClassificationType }
     * 
     */
    public DesignatedProductClassificationTypeImpl createDesignatedProductClassificationType() {
        return new DesignatedProductClassificationTypeImpl();
    }

    /**
     * Create an instance of {@link EmailUniversalCommunicationType }
     * 
     */
    public EmailUniversalCommunicationTypeImpl createEmailUniversalCommunicationType() {
        return new EmailUniversalCommunicationTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentContextTypeImpl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentContextTypeImpl }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "ExchangedDocumentContext")
    public JAXBElement<ExchangedDocumentContextTypeImpl> createExchangedDocumentContext(ExchangedDocumentContextTypeImpl value) {
        return new JAXBElement<ExchangedDocumentContextTypeImpl>(_ExchangedDocumentContext_QNAME, ExchangedDocumentContextTypeImpl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentContextParameterTypeImpl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentContextParameterTypeImpl }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "DocumentContextParameter")
    public JAXBElement<DocumentContextParameterTypeImpl> createDocumentContextParameter(DocumentContextParameterTypeImpl value) {
        return new JAXBElement<DocumentContextParameterTypeImpl>(_DocumentContextParameter_QNAME, DocumentContextParameterTypeImpl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentTypeImpl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentTypeImpl }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "ExchangedDocument")
    public JAXBElement<ExchangedDocumentTypeImpl> createExchangedDocument(ExchangedDocumentTypeImpl value) {
        return new JAXBElement<ExchangedDocumentTypeImpl>(_ExchangedDocument_QNAME, ExchangedDocumentTypeImpl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SupplyChainTradeTransactionTypeImpl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SupplyChainTradeTransactionTypeImpl }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "SupplyChainTradeTransaction")
    public JAXBElement<SupplyChainTradeTransactionTypeImpl> createSupplyChainTradeTransaction(SupplyChainTradeTransactionTypeImpl value) {
        return new JAXBElement<SupplyChainTradeTransactionTypeImpl>(_SupplyChainTradeTransaction_QNAME, SupplyChainTradeTransactionTypeImpl.class, null, value);
    }

}
