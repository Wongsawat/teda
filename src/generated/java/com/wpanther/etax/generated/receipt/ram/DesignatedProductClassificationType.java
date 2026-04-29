
package com.wpanther.etax.generated.receipt.ram;

import java.util.List;
import com.wpanther.etax.generated.common.qdt.Max16CodeType;
import com.wpanther.etax.generated.common.qdt.Max256TextType;


/**
 * <p>Java class for DesignatedProductClassificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DesignatedProductClassificationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClassCode" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}Max16CodeType" minOccurs="0"/&gt;
 *         &lt;element name="ClassName" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}Max256TextType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface DesignatedProductClassificationType {


    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link Max16CodeType }
     *     
     */
    Max16CodeType getClassCode();

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max16CodeType }
     *     
     */
    void setClassCode(Max16CodeType value);

    /**
     * Gets the value of the className property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the className property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Max256TextType }
     * 
     * 
     */
    List<Max256TextType> getClassName();

}
