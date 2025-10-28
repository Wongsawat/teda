/**
 * JAXB types for ISO 639-1 Two-letter Language Code (database-backed implementation)
 *
 * This package contains custom JAXB types for ISO language codes that are backed by
 * database entities instead of JAXB-generated enumerations.
 *
 * Standard: ISO 639-1 alpha-2
 * Code List Version: 2006-10-27
 * Total Languages: 185
 * Namespace: urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27
 *
 * Key Features:
 * - Case-insensitive handling (both 'th' and 'TH' work)
 * - Database-backed language lookup
 * - ASEAN language identification (th, en, ms, id, vi, my, km, lo, tl)
 * - Major trading partner language identification (en, th, zh, ja, ko, de, fr, es, ar, ru)
 * - Language name resolution
 * - Active status checking
 *
 * Database Schema:
 * - Table: iso_language_code
 * - Primary key: code (2 characters, lowercase)
 * - Generated columns: code_upper, code_lower (for case-insensitive queries)
 * - Views: v_iso_language_asean, v_iso_language_major_trading, v_iso_language_active
 * - Helper functions: get_language_name(), is_valid_language_code(), normalize_language_code()
 *
 * Common Languages:
 * - th: Thai - Primary language for Thai e-Tax Invoice
 * - en: English - International business language
 * - zh: Chinese - Major trading partner
 * - ja: Japanese - Major trading partner
 * - id: Indonesian - ASEAN neighbor
 * - ms: Malay - ASEAN neighbor
 * - vi: Vietnamese - ASEAN neighbor
 *
 * Usage Example:
 * <pre>
 * {@code
 * // In e-Tax Invoice XML classes
 * @XmlElement(namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27")
 * private ISOLanguageCodeType languageID;
 *
 * // Create language codes (case-insensitive)
 * ISOLanguageCodeType thai = ISOLanguageCodeType.thai();
 * ISOLanguageCodeType english = ISOLanguageCodeType.of("en");
 * ISOLanguageCodeType thaiUpper = ISOLanguageCodeType.of("TH");  // Also works
 *
 * // Check language properties
 * if (languageID.isThai()) {
 *     System.out.println("Thai invoice: " + languageID.getName());
 * }
 *
 * if (languageID.isASEANLanguage()) {
 *     System.out.println("ASEAN language detected");
 * }
 * }
 * </pre>
 *
 * @see com.wpanther.etax.core.entity.ISOLanguageCode
 * @see com.wpanther.etax.core.repository.ISOLanguageCodeRepository
 * @see com.wpanther.etax.core.adapter.ISOLanguageCodeAdapter
 * @see ISOLanguageCodeType
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "clm5ISO63912A", namespaceURI = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27")
    }
)
package com.wpanther.etax.core.xml.isolanguage;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
