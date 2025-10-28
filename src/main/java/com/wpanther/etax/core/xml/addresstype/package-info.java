/**
 * JAXB package configuration for UN/CEFACT Address Type Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 3131
 * Schema Version: D14A (15 Nov 2014)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm63131",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A"
        )
    }
)
package com.wpanther.etax.core.xml.addresstype;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
