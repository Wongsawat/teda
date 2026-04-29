
package com.wpanther.etax.generated.receipt.ram;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.receipt.ram.impl.DesignatedProductClassificationTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.DocumentContextParameterTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.DocumentLineDocumentTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.EmailUniversalCommunicationTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.ExchangedDocumentContextTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.ExchangedDocumentTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.HeaderTradeAgreementTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.HeaderTradeDeliveryTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.HeaderTradeSettlementTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.LineTradeAgreementTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.LineTradeDeliveryTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.LineTradeSettlementTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.NoteTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.ReferencedDocumentTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.SupplyChainEventTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.SupplyChainTradeLineItemTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.SupplyChainTradeTransactionTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TaxRegistrationTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TelephoneUniversalCommunicationTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeAddressTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeAllowanceChargeTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeContactTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeCountryTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeDeliveryTermsTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradePartyTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradePaymentTermsTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradePriceTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeProductInstanceTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeProductTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeSettlementHeaderMonetarySummationTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeSettlementLineMonetarySummationTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.TradeTaxTypeImpl;
import com.wpanther.etax.generated.receipt.ram.impl.UniversalCommunicationTypeImpl;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.receipt.ram package. 
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

    private final static Void _useJAXBProperties = null;
    private final static QName _ExchangedDocumentContext_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "ExchangedDocumentContext");
    private final static QName _DocumentContextParameter_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "DocumentContextParameter");
    private final static QName _ExchangedDocument_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "ExchangedDocument");
    private final static QName _SupplyChainTradeTransaction_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", "SupplyChainTradeTransaction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.receipt.ram
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExchangedDocumentContextType }
     * 
     */
    public ExchangedDocumentContextType createExchangedDocumentContextType() {
        return new ExchangedDocumentContextTypeImpl();
    }

    /**
     * Create an instance of {@link DocumentContextParameterType }
     * 
     */
    public DocumentContextParameterType createDocumentContextParameterType() {
        return new DocumentContextParameterTypeImpl();
    }

    /**
     * Create an instance of {@link ExchangedDocumentType }
     * 
     */
    public ExchangedDocumentType createExchangedDocumentType() {
        return new ExchangedDocumentTypeImpl();
    }

    /**
     * Create an instance of {@link SupplyChainTradeTransactionType }
     * 
     */
    public SupplyChainTradeTransactionType createSupplyChainTradeTransactionType() {
        return new SupplyChainTradeTransactionTypeImpl();
    }

    /**
     * Create an instance of {@link NoteType }
     * 
     */
    public NoteType createNoteType() {
        return new NoteTypeImpl();
    }

    /**
     * Create an instance of {@link HeaderTradeAgreementType }
     * 
     */
    public HeaderTradeAgreementType createHeaderTradeAgreementType() {
        return new HeaderTradeAgreementTypeImpl();
    }

    /**
     * Create an instance of {@link HeaderTradeDeliveryType }
     * 
     */
    public HeaderTradeDeliveryType createHeaderTradeDeliveryType() {
        return new HeaderTradeDeliveryTypeImpl();
    }

    /**
     * Create an instance of {@link HeaderTradeSettlementType }
     * 
     */
    public HeaderTradeSettlementType createHeaderTradeSettlementType() {
        return new HeaderTradeSettlementTypeImpl();
    }

    /**
     * Create an instance of {@link SupplyChainTradeLineItemType }
     * 
     */
    public SupplyChainTradeLineItemType createSupplyChainTradeLineItemType() {
        return new SupplyChainTradeLineItemTypeImpl();
    }

    /**
     * Create an instance of {@link TradePartyType }
     * 
     */
    public TradePartyType createTradePartyType() {
        return new TradePartyTypeImpl();
    }

    /**
     * Create an instance of {@link TaxRegistrationType }
     * 
     */
    public TaxRegistrationType createTaxRegistrationType() {
        return new TaxRegistrationTypeImpl();
    }

    /**
     * Create an instance of {@link TradeContactType }
     * 
     */
    public TradeContactType createTradeContactType() {
        return new TradeContactTypeImpl();
    }

    /**
     * Create an instance of {@link UniversalCommunicationType }
     * 
     */
    public UniversalCommunicationType createUniversalCommunicationType() {
        return new UniversalCommunicationTypeImpl();
    }

    /**
     * Create an instance of {@link TradeAddressType }
     * 
     */
    public TradeAddressType createTradeAddressType() {
        return new TradeAddressTypeImpl();
    }

    /**
     * Create an instance of {@link TradeDeliveryTermsType }
     * 
     */
    public TradeDeliveryTermsType createTradeDeliveryTermsType() {
        return new TradeDeliveryTermsTypeImpl();
    }

    /**
     * Create an instance of {@link ReferencedDocumentType }
     * 
     */
    public ReferencedDocumentType createReferencedDocumentType() {
        return new ReferencedDocumentTypeImpl();
    }

    /**
     * Create an instance of {@link SupplyChainEventType }
     * 
     */
    public SupplyChainEventType createSupplyChainEventType() {
        return new SupplyChainEventTypeImpl();
    }

    /**
     * Create an instance of {@link TradeTaxType }
     * 
     */
    public TradeTaxType createTradeTaxType() {
        return new TradeTaxTypeImpl();
    }

    /**
     * Create an instance of {@link TradeAllowanceChargeType }
     * 
     */
    public TradeAllowanceChargeType createTradeAllowanceChargeType() {
        return new TradeAllowanceChargeTypeImpl();
    }

    /**
     * Create an instance of {@link TradePaymentTermsType }
     * 
     */
    public TradePaymentTermsType createTradePaymentTermsType() {
        return new TradePaymentTermsTypeImpl();
    }

    /**
     * Create an instance of {@link TradeSettlementHeaderMonetarySummationType }
     * 
     */
    public TradeSettlementHeaderMonetarySummationType createTradeSettlementHeaderMonetarySummationType() {
        return new TradeSettlementHeaderMonetarySummationTypeImpl();
    }

    /**
     * Create an instance of {@link DocumentLineDocumentType }
     * 
     */
    public DocumentLineDocumentType createDocumentLineDocumentType() {
        return new DocumentLineDocumentTypeImpl();
    }

    /**
     * Create an instance of {@link LineTradeAgreementType }
     * 
     */
    public LineTradeAgreementType createLineTradeAgreementType() {
        return new LineTradeAgreementTypeImpl();
    }

    /**
     * Create an instance of {@link TradePriceType }
     * 
     */
    public TradePriceType createTradePriceType() {
        return new TradePriceTypeImpl();
    }

    /**
     * Create an instance of {@link LineTradeDeliveryType }
     * 
     */
    public LineTradeDeliveryType createLineTradeDeliveryType() {
        return new LineTradeDeliveryTypeImpl();
    }

    /**
     * Create an instance of {@link LineTradeSettlementType }
     * 
     */
    public LineTradeSettlementType createLineTradeSettlementType() {
        return new LineTradeSettlementTypeImpl();
    }

    /**
     * Create an instance of {@link TradeSettlementLineMonetarySummationType }
     * 
     */
    public TradeSettlementLineMonetarySummationType createTradeSettlementLineMonetarySummationType() {
        return new TradeSettlementLineMonetarySummationTypeImpl();
    }

    /**
     * Create an instance of {@link TradeProductType }
     * 
     */
    public TradeProductType createTradeProductType() {
        return new TradeProductTypeImpl();
    }

    /**
     * Create an instance of {@link TradeProductInstanceType }
     * 
     */
    public TradeProductInstanceType createTradeProductInstanceType() {
        return new TradeProductInstanceTypeImpl();
    }

    /**
     * Create an instance of {@link TradeCountryType }
     * 
     */
    public TradeCountryType createTradeCountryType() {
        return new TradeCountryTypeImpl();
    }

    /**
     * Create an instance of {@link TelephoneUniversalCommunicationType }
     * 
     */
    public TelephoneUniversalCommunicationType createTelephoneUniversalCommunicationType() {
        return new TelephoneUniversalCommunicationTypeImpl();
    }

    /**
     * Create an instance of {@link DesignatedProductClassificationType }
     * 
     */
    public DesignatedProductClassificationType createDesignatedProductClassificationType() {
        return new DesignatedProductClassificationTypeImpl();
    }

    /**
     * Create an instance of {@link EmailUniversalCommunicationType }
     * 
     */
    public EmailUniversalCommunicationType createEmailUniversalCommunicationType() {
        return new EmailUniversalCommunicationTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentContextType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentContextType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "ExchangedDocumentContext")
    public JAXBElement<ExchangedDocumentContextType> createExchangedDocumentContext(ExchangedDocumentContextType value) {
        return new JAXBElement<ExchangedDocumentContextType>(_ExchangedDocumentContext_QNAME, ((Class) ExchangedDocumentContextTypeImpl.class), null, ((ExchangedDocumentContextTypeImpl) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentContextParameterType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentContextParameterType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "DocumentContextParameter")
    public JAXBElement<DocumentContextParameterType> createDocumentContextParameter(DocumentContextParameterType value) {
        return new JAXBElement<DocumentContextParameterType>(_DocumentContextParameter_QNAME, ((Class) DocumentContextParameterTypeImpl.class), null, ((DocumentContextParameterTypeImpl) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExchangedDocumentType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "ExchangedDocument")
    public JAXBElement<ExchangedDocumentType> createExchangedDocument(ExchangedDocumentType value) {
        return new JAXBElement<ExchangedDocumentType>(_ExchangedDocument_QNAME, ((Class) ExchangedDocumentTypeImpl.class), null, ((ExchangedDocumentTypeImpl) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SupplyChainTradeTransactionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SupplyChainTradeTransactionType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_ReusableAggregateBusinessInformationEntity:2", name = "SupplyChainTradeTransaction")
    public JAXBElement<SupplyChainTradeTransactionType> createSupplyChainTradeTransaction(SupplyChainTradeTransactionType value) {
        return new JAXBElement<SupplyChainTradeTransactionType>(_SupplyChainTradeTransaction_QNAME, ((Class) SupplyChainTradeTransactionTypeImpl.class), null, ((SupplyChainTradeTransactionTypeImpl) value));
    }

}
