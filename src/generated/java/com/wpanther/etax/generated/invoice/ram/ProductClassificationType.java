
package com.wpanther.etax.generated.invoice.ram;

import java.util.List;
import com.wpanther.etax.generated.invoice.qdt.Max16CodeType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns:qdt="urn:etda:uncefact:data:standard:Invoice_QualifiedDataType:1" xmlns:ram="urn:etda:uncefact:data:standard:Invoice_ReusableAggregateBusinessInformationEntity:2" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:16" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;UN01002608&lt;/ccts:UniqueID&gt;
 * </pre>
 * 
 * 
 * <p>Java class for ProductClassificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductClassificationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClassCode" type="{urn:etda:uncefact:data:standard:Invoice_QualifiedDataType:1}Max16CodeType" minOccurs="0"/&gt;
 *         &lt;element name="ClassName" type="{urn:etda:uncefact:data:standard:Invoice_QualifiedDataType:1}Max256Text" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ProductClassificationType {


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
     * {@link String }
     * 
     * 
     */
    List<String> getClassName();

}
