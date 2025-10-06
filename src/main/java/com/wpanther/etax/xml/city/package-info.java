/**
 * JAXB package configuration for TISI 1099-2548 City Name
 *
 * This package-info configures the XML namespace and prefix to match
 * the original ETDA e-Tax Invoice schema requirements exactly.
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:CityNameFromTISI1099_2548",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(
            prefix = "thcity",
            namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:CityNameFromTISI1099_2548"
        )
    }
)
package com.wpanther.etax.xml.city;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
