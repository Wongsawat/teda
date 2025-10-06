/**
 * JAXB package configuration for UN/CEFACT Allowance Charge Reason Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 64465
 * Schema Version: D15B (30 May 2016)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm64465AllowanceChargeReasonCode",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B"
        )
    }
)
package com.wpanther.etax.xml.allowancecharge;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
