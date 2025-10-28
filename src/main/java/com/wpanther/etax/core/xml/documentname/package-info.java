/**
 * JAXB package configuration for Thai Document Name Code (Invoice)
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * Schema: ThaiDocumentNameCode_Invoice_1p0.xsd
 * Version: 1.0
 * Agency: ETDA (Electronic Transactions Development Agency, Thailand)
 */
@XmlSchema(
    namespace = "urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm61001Invoice",
            namespaceURI = "urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1"
        )
    }
)
package com.wpanther.etax.core.xml.documentname;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
