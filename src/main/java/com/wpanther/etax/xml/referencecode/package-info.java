/**
 * JAXB package configuration for UN/CEFACT Reference Type Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm61153",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A"
        )
    }
)
package com.wpanther.etax.xml.referencecode;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
