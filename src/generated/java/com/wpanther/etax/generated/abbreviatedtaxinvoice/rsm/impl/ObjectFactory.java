
package com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.impl;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.AbbreviatedTaxInvoice_CrossIndustryInvoiceType;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.impl package. 
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

    private final static QName _AbbreviatedTaxInvoice_CrossIndustryInvoice_QNAME = new QName("urn:etda:uncefact:data:standard:AbbreviatedTaxInvoice_CrossIndustryInvoice:2", "AbbreviatedTaxInvoice_CrossIndustryInvoice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AbbreviatedTaxInvoice_CrossIndustryInvoiceType }
     * 
     */
    public AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl createAbbreviatedTaxInvoice_CrossIndustryInvoiceType() {
        return new AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:AbbreviatedTaxInvoice_CrossIndustryInvoice:2", name = "AbbreviatedTaxInvoice_CrossIndustryInvoice")
    public JAXBElement<AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl> createAbbreviatedTaxInvoice_CrossIndustryInvoice(AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl value) {
        return new JAXBElement<AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl>(_AbbreviatedTaxInvoice_CrossIndustryInvoice_QNAME, AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl.class, null, value);
    }

}
