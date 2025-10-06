/**
 * JAXB package configuration for UN/CEFACT Payment Terms Type Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 64279
 * Schema Version: D14A (Nov 15, 2014)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm64279",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A"
        )
    }
)
package com.wpanther.etax.xml.paymentterms;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
