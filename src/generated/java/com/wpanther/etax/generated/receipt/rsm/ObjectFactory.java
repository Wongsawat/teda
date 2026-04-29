
package com.wpanther.etax.generated.receipt.rsm;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.receipt.rsm.impl.Receipt_CrossIndustryInvoiceTypeImpl;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.receipt.rsm package. 
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
    private final static QName _Receipt_CrossIndustryInvoice_QNAME = new QName("urn:etda:uncefact:data:standard:Receipt_CrossIndustryInvoice:2", "Receipt_CrossIndustryInvoice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.receipt.rsm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Receipt_CrossIndustryInvoiceType }
     * 
     */
    public Receipt_CrossIndustryInvoiceType createReceipt_CrossIndustryInvoiceType() {
        return new Receipt_CrossIndustryInvoiceTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receipt_CrossIndustryInvoiceType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Receipt_CrossIndustryInvoiceType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:Receipt_CrossIndustryInvoice:2", name = "Receipt_CrossIndustryInvoice")
    public JAXBElement<Receipt_CrossIndustryInvoiceType> createReceipt_CrossIndustryInvoice(Receipt_CrossIndustryInvoiceType value) {
        return new JAXBElement<Receipt_CrossIndustryInvoiceType>(_Receipt_CrossIndustryInvoice_QNAME, ((Class) Receipt_CrossIndustryInvoiceTypeImpl.class), null, ((Receipt_CrossIndustryInvoiceTypeImpl) value));
    }

}
