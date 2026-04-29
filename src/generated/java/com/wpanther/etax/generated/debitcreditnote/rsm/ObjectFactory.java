
package com.wpanther.etax.generated.debitcreditnote.rsm;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.debitcreditnote.rsm.impl.DebitCreditNote_CrossIndustryInvoiceTypeImpl;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.debitcreditnote.rsm package. 
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
    private final static QName _DebitCreditNote_CrossIndustryInvoice_QNAME = new QName("urn:etda:uncefact:data:standard:DebitCreditNote_CrossIndustryInvoice:2", "DebitCreditNote_CrossIndustryInvoice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.debitcreditnote.rsm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DebitCreditNote_CrossIndustryInvoiceType }
     * 
     */
    public DebitCreditNote_CrossIndustryInvoiceType createDebitCreditNote_CrossIndustryInvoiceType() {
        return new DebitCreditNote_CrossIndustryInvoiceTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCreditNote_CrossIndustryInvoiceType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DebitCreditNote_CrossIndustryInvoiceType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:DebitCreditNote_CrossIndustryInvoice:2", name = "DebitCreditNote_CrossIndustryInvoice")
    public JAXBElement<DebitCreditNote_CrossIndustryInvoiceType> createDebitCreditNote_CrossIndustryInvoice(DebitCreditNote_CrossIndustryInvoiceType value) {
        return new JAXBElement<DebitCreditNote_CrossIndustryInvoiceType>(_DebitCreditNote_CrossIndustryInvoice_QNAME, ((Class) DebitCreditNote_CrossIndustryInvoiceTypeImpl.class), null, ((DebitCreditNote_CrossIndustryInvoiceTypeImpl) value));
    }

}
