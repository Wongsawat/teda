/**
 * JAXB types for ISO 3166-1 Two-letter Country Code (database-backed implementation)
 *
 * This package contains custom JAXB types for ISO country codes that are backed by
 * database entities instead of JAXB-generated enumerations.
 *
 * Standard: ISO 3166-1 alpha-2
 * Code List Version: Second Edition 2006
 * Total Countries: 252 (249 standard + 3 ETDA extensions)
 * Namespace: urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006
 *
 * Key Features:
 * - Database-backed country lookup
 * - ASEAN country identification (10 countries)
 * - Major trading partner identification (11 countries)
 * - ETDA extension support (AN, KS, UN)
 * - Country name resolution
 * - Active status checking
 *
 * Code Format: 2 uppercase letters (e.g., TH, US, CN, JP, SG)
 *
 * ETDA Custom Extensions:
 * - AN: NETHERLANDS ANTILLES (inactive - historical)
 * - KS: KOSOVO (active)
 * - UN: UNITED NATIONS (active)
 *
 * Database Schema:
 * - Table: iso_country_code
 * - Primary key: code (2 uppercase letters)
 * - Views: iso_country_code_active, iso_country_code_standard,
 *          iso_country_code_etda_extensions, iso_country_code_asean
 * - Helper functions: get_country_name(code)
 *
 * ASEAN Countries (10):
 * - TH: THAILAND
 * - BN: BRUNEI DARUSSALAM
 * - KH: CAMBODIA
 * - ID: INDONESIA
 * - LA: LAO PEOPLE'S DEMOCRATIC REPUBLIC
 * - MY: MALAYSIA
 * - MM: MYANMAR
 * - PH: PHILIPPINES
 * - SG: SINGAPORE
 * - VN: VIET NAM
 *
 * Major Trading Partners (11):
 * - CN: CHINA
 * - JP: JAPAN
 * - KR: KOREA, REPUBLIC OF
 * - US: UNITED STATES
 * - GB: UNITED KINGDOM
 * - DE: GERMANY
 * - AU: AUSTRALIA
 * - IN: INDIA
 * - TW: TAIWAN, PROVINCE OF CHINA
 * - HK: HONG KONG
 * - SG: SINGAPORE
 *
 * Usage Example:
 * <pre>
 * {@code
 * // In e-Tax Invoice XML classes
 * @XmlElement(namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
 * private ISOCountryCodeType countryID;
 *
 * // Create country codes
 * ISOCountryCodeType thailand = ISOCountryCodeType.thailand();
 * ISOCountryCodeType usa = ISOCountryCodeType.of("US");
 * ISOCountryCodeType china = ISOCountryCodeType.china();
 *
 * // Check country properties
 * if (countryID.isThailand()) {
 *     System.out.println("Thai invoice: " + countryID.getName());
 * }
 *
 * if (countryID.isASEANCountry()) {
 *     System.out.println("ASEAN country detected");
 * }
 *
 * if (countryID.isMajorTradingPartner()) {
 *     System.out.println("Major trading partner: " + countryID.getName());
 * }
 * }
 * </pre>
 *
 * @see com.wpanther.etax.core.entity.ISOCountryCode
 * @see com.wpanther.etax.core.repository.ISOCountryCodeRepository
 * @see com.wpanther.etax.core.adapter.taxinvoice.ISOCountryCodeAdapter
 * @see ISOCountryCodeType
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "ids5ISO316612A", namespaceURI = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
    }
)
package com.wpanther.etax.core.xml.isocountry;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
