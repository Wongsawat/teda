/**
 * JAXB package configuration for UN/CEFACT Duty Tax Fee Type Code
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 *
 * UN/CEFACT Code List: 65153
 * Schema Version: D14A (Nov 15, 2014)
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "clm65153",
            namespaceURI = "urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A"
        )
    }
)
package com.wpanther.etax.core.xml.dutytaxfee;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
