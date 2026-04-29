
package com.wpanther.etax.generated.invoice.qdt;

import java.math.BigDecimal;
import un.unece.uncefact.codelist.standard.iso.iso3alphacurrencycode._2012_08_31.ISO3AlphaCurrencyCodeContentType;


/**
 * <p>Java class for AmountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AmountType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;urn:etda:uncefact:data:standard:Invoice_QualifiedDataType:1&gt;Amount"&gt;
 *       &lt;attribute name="currencyID" type="{urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31}ISO3AlphaCurrencyCodeContentType" /&gt;
 *       &lt;attribute name="currencyCodeListVersionID" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface AmountType {


    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    BigDecimal getValue();

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    void setValue(BigDecimal value);

    /**
     * Gets the value of the currencyID property.
     * 
     * @return
     *     possible object is
     *     {@link ISO3AlphaCurrencyCodeContentType }
     *     
     */
    ISO3AlphaCurrencyCodeContentType getCurrencyID();

    /**
     * Sets the value of the currencyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ISO3AlphaCurrencyCodeContentType }
     *     
     */
    void setCurrencyID(ISO3AlphaCurrencyCodeContentType value);

    /**
     * Gets the value of the currencyCodeListVersionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getCurrencyCodeListVersionID();

    /**
     * Sets the value of the currencyCodeListVersionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setCurrencyCodeListVersionID(String value);

}
