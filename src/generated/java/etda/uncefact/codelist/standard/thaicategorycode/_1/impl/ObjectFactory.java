
package etda.uncefact.codelist.standard.thaicategorycode._1.impl;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the etda.uncefact.codelist.standard.thaicategorycode._1.impl package. 
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

    private final static QName _ThaiCategoryCode_QNAME = new QName("urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1", "ThaiCategoryCode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: etda.uncefact.codelist.standard.thaicategorycode._1.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1", name = "ThaiCategoryCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createThaiCategoryCode(String value) {
        return new JAXBElement<String>(_ThaiCategoryCode_QNAME, String.class, null, value);
    }

}
