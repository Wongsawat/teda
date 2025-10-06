/**
 * JAXB package configuration for ISO 4217 Three-letter Currency Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * Standard: ISO 4217 alpha-3
 * Code List Version: 2012-08-31
 * Schema Version: 9.3
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm5ISO42173A",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31"
        )
    }
)
package com.wpanther.etax.xml.isocurrency;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
