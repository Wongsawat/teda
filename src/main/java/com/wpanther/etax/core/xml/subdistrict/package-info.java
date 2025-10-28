/**
 * JAXB package configuration for TISI 1099-2548 City SubDivision Name
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:CitySubDivisionNameFromTISI1099_2548",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "thsubdiv",
            namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:CitySubDivisionNameFromTISI1099_2548"
        )
    }
)
package com.wpanther.etax.core.xml.subdistrict;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
