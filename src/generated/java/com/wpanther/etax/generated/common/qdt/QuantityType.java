
package com.wpanther.etax.generated.common.qdt;

import java.math.BigDecimal;


/**
 * <p>Java class for QuantityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QuantityType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;urn:etda:uncefact:data:standard:QualifiedDataType:1&gt;Quantity"&gt;
 *       &lt;attribute name="unitCode" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *       &lt;attribute name="unitCodeListID" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *       &lt;attribute name="unitCodeListAgencyID" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *       &lt;attribute name="unitCodeListAgencyName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface QuantityType {


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
     * Gets the value of the unitCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUnitCode();

    /**
     * Sets the value of the unitCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUnitCode(String value);

    /**
     * Gets the value of the unitCodeListID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUnitCodeListID();

    /**
     * Sets the value of the unitCodeListID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUnitCodeListID(String value);

    /**
     * Gets the value of the unitCodeListAgencyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUnitCodeListAgencyID();

    /**
     * Sets the value of the unitCodeListAgencyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUnitCodeListAgencyID(String value);

    /**
     * Gets the value of the unitCodeListAgencyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUnitCodeListAgencyName();

    /**
     * Sets the value of the unitCodeListAgencyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUnitCodeListAgencyName(String value);

}
