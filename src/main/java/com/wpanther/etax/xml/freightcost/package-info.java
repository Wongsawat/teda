/**
 * JAXB package configuration for UN/CEFACT Freight Cost Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT: Recommendation 23
 * Schema Version: 4
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "ids6Recommendation23",
            namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:UNECE:FreightCostCode:4"
        )
    }
)
package com.wpanther.etax.xml.freightcost;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
