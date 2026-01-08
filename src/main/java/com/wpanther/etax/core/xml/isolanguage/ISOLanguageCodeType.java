package com.wpanther.etax.core.xml.isolanguage;

import com.wpanther.etax.core.adapter.common.ISOLanguageCodeAdapter;
import com.wpanther.etax.core.entity.ISOLanguageCode;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Objects;

/**
 * Custom JAXB type for ISO 639-1 Two-letter Language Code with database-backed implementation
 *
 * This class wraps the ISOLanguageCode entity for XML marshalling/unmarshalling while
 * maintaining compatibility with the JAXB-generated namespace structure.
 *
 * Standard: ISO 639-1 alpha-2
 * Namespace: urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27
 * Code List Version: 2006-10-27
 * Total Languages: 185
 *
 * Features:
 * - Case-insensitive handling (both 'th' and 'TH' work)
 * - Database-backed language lookup
 * - ASEAN language identification (th, en, ms, id, vi, my, km, lo, tl)
 * - Major trading partner language identification (en, th, zh, ja, ko, de, fr, es, ar, ru)
 * - Language name resolution
 * - Active status checking
 *
 * Usage in JAXB classes:
 * <pre>
 * {@code
 * @XmlElement(namespace = "urn:un:unece:uncefact:codelist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27")
 * private ISOLanguageCodeType languageID;
 * }
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ISOLanguageCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOLanguageCodeAdapter.class)
    private ISOLanguageCode value;

    // Constructors
    public ISOLanguageCodeType() {
    }

    public ISOLanguageCodeType(ISOLanguageCode value) {
        this.value = value;
    }

    public ISOLanguageCodeType(String code) {
        this.value = ISOLanguageCodeAdapter.isValid(code)
            ? new ISOLanguageCode(code, ISOLanguageCodeAdapter.getName(code))
            : new ISOLanguageCode(code);
    }

    // Factory methods

    /**
     * Create from language code string (case-insensitive)
     *
     * @param code Language code (e.g., "th", "TH", "en", "EN")
     * @return ISOLanguageCodeType instance
     */
    public static ISOLanguageCodeType of(String code) {
        return new ISOLanguageCodeType(code);
    }

    /**
     * Create from ISOLanguageCode entity
     *
     * @param entity ISOLanguageCode entity
     * @return ISOLanguageCodeType instance
     */
    public static ISOLanguageCodeType of(ISOLanguageCode entity) {
        return new ISOLanguageCodeType(entity);
    }

    /**
     * Create Thai language code
     *
     * @return ISOLanguageCodeType for Thai ('th')
     */
    public static ISOLanguageCodeType thai() {
        return of("th");
    }

    /**
     * Create English language code
     *
     * @return ISOLanguageCodeType for English ('en')
     */
    public static ISOLanguageCodeType english() {
        return of("en");
    }

    /**
     * Create Chinese language code
     *
     * @return ISOLanguageCodeType for Chinese ('zh')
     */
    public static ISOLanguageCodeType chinese() {
        return of("zh");
    }

    /**
     * Create Japanese language code
     *
     * @return ISOLanguageCodeType for Japanese ('ja')
     */
    public static ISOLanguageCodeType japanese() {
        return of("ja");
    }

    // Business logic methods delegating to entity

    /**
     * Get language code (lowercase standard format)
     *
     * @return Language code string, or null if value is null
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get language name
     *
     * @return Language name, or null if value is null
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Check if this is Thai language (th)
     *
     * @return true if Thai
     */
    public boolean isThai() {
        return value != null && value.isThai();
    }

    /**
     * Check if this is English language (en)
     *
     * @return true if English
     */
    public boolean isEnglish() {
        return value != null && value.isEnglish();
    }

    /**
     * Check if this is Chinese language (zh)
     *
     * @return true if Chinese
     */
    public boolean isChinese() {
        return value != null && value.isChinese();
    }

    /**
     * Check if this is Japanese language (ja)
     *
     * @return true if Japanese
     */
    public boolean isJapanese() {
        return value != null && value.isJapanese();
    }

    /**
     * Check if this is an ASEAN language
     * (th, en, ms, id, vi, my, km, lo, tl)
     *
     * @return true if ASEAN language
     */
    public boolean isASEANLanguage() {
        return value != null && value.isASEANLanguage();
    }

    /**
     * Check if this is a major trading partner language
     * (en, th, zh, ja, ko, de, fr, es, ar, ru)
     *
     * @return true if major trading language
     */
    public boolean isMajorTradingLanguage() {
        return value != null && value.isMajorTradingLanguage();
    }

    /**
     * Check if language is active
     *
     * @return true if active
     */
    public boolean isActive() {
        return value != null && Boolean.TRUE.equals(value.getIsActive());
    }

    // Getters and Setters

    public ISOLanguageCode getValue() {
        return value;
    }

    public void setValue(ISOLanguageCode value) {
        this.value = value;
    }

    // Equals and HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOLanguageCodeType)) return false;
        ISOLanguageCodeType that = (ISOLanguageCodeType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "ISOLanguageCodeType{null}";
    }
}
