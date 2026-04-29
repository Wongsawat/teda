
package org.w3._2000._09.xmldsig_;

import java.util.List;
import jakarta.xml.bind.JAXBElement;
import org.w3c.dom.Element;


/**
 * <p>Java class for KeyInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KeyInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}KeyName"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}KeyValue"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}RetrievalMethod"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}X509Data"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}PGPData"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}SPKIData"/&gt;
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}MgmtData"/&gt;
 *         &lt;any processContents='lax' namespace='##other'/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface KeyInfoType {


    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link KeyValueType }{@code >}
     * {@link JAXBElement }{@code <}{@link RetrievalMethodType }{@code >}
     * {@link JAXBElement }{@code <}{@link X509DataType }{@code >}
     * {@link JAXBElement }{@code <}{@link PGPDataType }{@code >}
     * {@link JAXBElement }{@code <}{@link SPKIDataType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link Element }
     * {@link Object }
     * {@link String }
     * 
     * 
     */
    List<Object> getContent();

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setId(String value);

}
