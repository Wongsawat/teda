
package com.wpanther.etax.generated.abbreviatedtaxinvoice.ram;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import com.wpanther.etax.generated.common.qdt.PaymentTermsTypeCodeType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns:ns1="urn:un:unece:uncefact:data:Standard:QualifiedDataType:19" xmlns:ns2="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:19" xmlns:ns3="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:19" xmlns:ns4="urn:etda:uncefact:data:standard:DebitCreditNote_ReusableAggregateBusinessInformationEntity:2" xmlns:qdt="urn:etda:uncefact:data:standard:QualifiedDataType:1" xmlns:ram="urn:etda:uncefact:data:standard:AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity:2" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:16" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;UN01001672&lt;/ccts:UniqueID&gt;
 * </pre>
 * 
 * 
 * <p>Java class for TradePaymentTermsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradePaymentTermsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Description" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}Max256Text" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="DueDateDateTime" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}ISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="TypeCode" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}PaymentTermsTypeCodeType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface TradePaymentTermsType {


    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    List<String> getDescription();

    /**
     * Gets the value of the dueDateDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getDueDateDateTime();

    /**
     * Sets the value of the dueDateDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setDueDateDateTime(XMLGregorianCalendar value);

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsTypeCodeType }
     *     
     */
    PaymentTermsTypeCodeType getTypeCode();

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsTypeCodeType }
     *     
     */
    void setTypeCode(PaymentTermsTypeCodeType value);

}
