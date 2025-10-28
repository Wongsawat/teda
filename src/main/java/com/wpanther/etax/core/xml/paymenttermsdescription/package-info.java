/**
 * JAXB package configuration for UN/CEFACT Payment Terms Description Identifier
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 64277
 * Schema Version: D14A (Nov 15, 2014)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "ids64277",
            namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A"
        )
    }
)
package com.wpanther.etax.core.xml.paymenttermsdescription;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
