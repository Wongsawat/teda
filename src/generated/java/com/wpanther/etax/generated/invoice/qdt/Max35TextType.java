
package com.wpanther.etax.generated.invoice.qdt;



/**
 * <p>Java class for Max35TextType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Max35TextType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;urn:etda:uncefact:data:standard:Invoice_QualifiedDataType:1&gt;Max35Text"&gt;
 *       &lt;attribute name="languageID" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *       &lt;attribute name="languageLocaleID" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface Max35TextType {


    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getValue();

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setValue(String value);

    /**
     * Gets the value of the languageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getLanguageID();

    /**
     * Sets the value of the languageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setLanguageID(String value);

    /**
     * Gets the value of the languageLocaleID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getLanguageLocaleID();

    /**
     * Sets the value of the languageLocaleID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setLanguageLocaleID(String value);

}
