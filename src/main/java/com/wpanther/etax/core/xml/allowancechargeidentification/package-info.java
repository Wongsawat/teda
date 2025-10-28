/**
 * JAXB package configuration for UN/CEFACT Allowance/Charge Identification Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 5189 (AllowanceChargeID)
 * Schema Version: D14A (15 Nov 2014)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm65189AllowanceChargeID",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A"
        )
    }
)
package com.wpanther.etax.core.xml.allowancechargeidentification;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
