
package com.wpanther.etax.generated.taxinvoice.ram;

import com.wpanther.etax.generated.common.qdt.Max35IDType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns:ns1="urn:un:unece:uncefact:data:Standard:QualifiedDataType:19" xmlns:ns2="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:19" xmlns:ns3="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:19" xmlns:ns4="urn:etda:uncefact:data:standard:DebitCreditNote_ReusableAggregateBusinessInformationEntity:2" xmlns:qdt="urn:etda:uncefact:data:standard:QualifiedDataType:1" xmlns:ram="urn:etda:uncefact:data:standard:TaxInvoice_ReusableAggregateBusinessInformationEntity:2" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:16" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;UN01004486&lt;/ccts:UniqueID&gt;
 * </pre>
 * 
 * 
 * <p>Java class for TaxRegistrationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxRegistrationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}Max35IDType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface TaxRegistrationType {


    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Max35IDType }
     *     
     */
    Max35IDType getID();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Max35IDType }
     *     
     */
    void setID(Max35IDType value);

}
