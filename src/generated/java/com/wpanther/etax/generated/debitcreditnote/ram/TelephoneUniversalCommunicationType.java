
package com.wpanther.etax.generated.debitcreditnote.ram;



/**
 * <p>Java class for TelephoneUniversalCommunicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelephoneUniversalCommunicationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompleteNumber" type="{urn:etda:uncefact:data:standard:QualifiedDataType:1}PhoneNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface TelephoneUniversalCommunicationType {


    /**
     * Gets the value of the completeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getCompleteNumber();

    /**
     * Sets the value of the completeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setCompleteNumber(String value);

}
