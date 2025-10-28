/**
 * JAXB package configuration for UN/CEFACT Delivery Terms Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 64053
 * Standard: INCOTERMS 2010
 * Schema Version: 2010
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm64053",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010"
        )
    }
)
package com.wpanther.etax.core.xml.deliveryterms;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
