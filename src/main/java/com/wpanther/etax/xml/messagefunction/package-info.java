/**
 * JAXB package configuration for UN/CEFACT Message Function Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 61225
 * Schema Version: D14A (Nov 15, 2014)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm61225MessageFunctionTypeCode",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A"
        )
    }
)
package com.wpanther.etax.xml.messagefunction;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
