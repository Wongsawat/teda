
package com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.impl.AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm package. 
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
    private final static QName _AbbreviatedTaxInvoice_CrossIndustryInvoice_QNAME = new QName("urn:etda:uncefact:data:standard:AbbreviatedTaxInvoice_CrossIndustryInvoice:2", "AbbreviatedTaxInvoice_CrossIndustryInvoice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AbbreviatedTaxInvoice_CrossIndustryInvoiceType }
     * 
     */
    public AbbreviatedTaxInvoice_CrossIndustryInvoiceType createAbbreviatedTaxInvoice_CrossIndustryInvoiceType() {
        return new AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbbreviatedTaxInvoice_CrossIndustryInvoiceType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AbbreviatedTaxInvoice_CrossIndustryInvoiceType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:AbbreviatedTaxInvoice_CrossIndustryInvoice:2", name = "AbbreviatedTaxInvoice_CrossIndustryInvoice")
    public JAXBElement<AbbreviatedTaxInvoice_CrossIndustryInvoiceType> createAbbreviatedTaxInvoice_CrossIndustryInvoice(AbbreviatedTaxInvoice_CrossIndustryInvoiceType value) {
        return new JAXBElement<AbbreviatedTaxInvoice_CrossIndustryInvoiceType>(_AbbreviatedTaxInvoice_CrossIndustryInvoice_QNAME, ((Class) AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl.class), null, ((AbbreviatedTaxInvoice_CrossIndustryInvoiceTypeImpl) value));
    }

}
