
package com.wpanther.etax.generated.taxinvoice.rsm.impl;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.taxinvoice.rsm.impl package. 
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

    private final static QName _TaxInvoice_CrossIndustryInvoice_QNAME = new QName("urn:etda:uncefact:data:standard:TaxInvoice_CrossIndustryInvoice:2", "TaxInvoice_CrossIndustryInvoice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.taxinvoice.rsm.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TaxInvoice_CrossIndustryInvoiceType }
     * 
     */
    public TaxInvoice_CrossIndustryInvoiceTypeImpl createTaxInvoice_CrossIndustryInvoiceType() {
        return new TaxInvoice_CrossIndustryInvoiceTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaxInvoice_CrossIndustryInvoiceTypeImpl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TaxInvoice_CrossIndustryInvoiceTypeImpl }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:TaxInvoice_CrossIndustryInvoice:2", name = "TaxInvoice_CrossIndustryInvoice")
    public JAXBElement<TaxInvoice_CrossIndustryInvoiceTypeImpl> createTaxInvoice_CrossIndustryInvoice(TaxInvoice_CrossIndustryInvoiceTypeImpl value) {
        return new JAXBElement<TaxInvoice_CrossIndustryInvoiceTypeImpl>(_TaxInvoice_CrossIndustryInvoice_QNAME, TaxInvoice_CrossIndustryInvoiceTypeImpl.class, null, value);
    }

}
