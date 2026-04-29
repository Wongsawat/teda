
package com.wpanther.etax.generated.cancellationnote.rsm;

import javax.xml.namespace.QName;
import com.wpanther.etax.generated.cancellationnote.rsm.impl.CancellationNote_CrossIndustryInvoiceTypeImpl;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wpanther.etax.generated.cancellationnote.rsm package. 
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
    private final static QName _CancellationNote_CrossIndustryInvoice_QNAME = new QName("urn:etda:uncefact:data:standard:CancellationNote_CrossIndustryInvoice:2", "CancellationNote_CrossIndustryInvoice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wpanther.etax.generated.cancellationnote.rsm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancellationNote_CrossIndustryInvoiceType }
     * 
     */
    public CancellationNote_CrossIndustryInvoiceType createCancellationNote_CrossIndustryInvoiceType() {
        return new CancellationNote_CrossIndustryInvoiceTypeImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancellationNote_CrossIndustryInvoiceType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CancellationNote_CrossIndustryInvoiceType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:data:standard:CancellationNote_CrossIndustryInvoice:2", name = "CancellationNote_CrossIndustryInvoice")
    public JAXBElement<CancellationNote_CrossIndustryInvoiceType> createCancellationNote_CrossIndustryInvoice(CancellationNote_CrossIndustryInvoiceType value) {
        return new JAXBElement<CancellationNote_CrossIndustryInvoiceType>(_CancellationNote_CrossIndustryInvoice_QNAME, ((Class) CancellationNote_CrossIndustryInvoiceTypeImpl.class), null, ((CancellationNote_CrossIndustryInvoiceTypeImpl) value));
    }

}
