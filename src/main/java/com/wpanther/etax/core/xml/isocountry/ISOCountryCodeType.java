package com.wpanther.etax.core.xml.isocountry;

import com.wpanther.etax.core.adapter.common.ISOCountryCodeAdapter;
import com.wpanther.etax.core.entity.ISOCountryCode;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Objects;

/**
 * Custom JAXB type for ISO 3166-1 Two-letter Country Code with database-backed implementation
 *
 * This class wraps the ISOCountryCode entity for XML marshalling/unmarshalling while
 * maintaining compatibility with the JAXB-generated namespace structure.
 *
 * Standard: ISO 3166-1 alpha-2
 * Namespace: urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006
 * Code List Version: Second Edition 2006
 * Total Countries: 252 (249 standard + 3 ETDA extensions)
 * Code Format: 2 uppercase letters
 *
 * Features:
 * - Database-backed country lookup
 * - ASEAN country identification (10 countries)
 * - Major trading partner identification (11 countries)
 * - ETDA extension support (AN, KS, UN)
 * - Country name resolution
 * - Active status checking
 *
 * Usage in JAXB classes:
 * <pre>
 * {@code
 * @XmlElement(namespace = "urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006")
 * private ISOCountryCodeType countryID;
 * }
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ISOCountryCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ISOCountryCodeAdapter.class)
    private ISOCountryCode value;

    // Constructors

    public ISOCountryCodeType() {
    }

    public ISOCountryCodeType(ISOCountryCode value) {
        this.value = value;
    }

    public ISOCountryCodeType(String code) {
        this.value = ISOCountryCodeAdapter.isValid(code)
            ? new ISOCountryCode(code, ISOCountryCodeAdapter.getName(code))
            : new ISOCountryCode(code);
    }

    // Factory methods

    /**
     * Create from country code string
     *
     * @param code Country code (e.g., "TH", "US", "CN")
     * @return ISOCountryCodeType instance
     */
    public static ISOCountryCodeType of(String code) {
        return new ISOCountryCodeType(code);
    }

    /**
     * Create from ISOCountryCode entity
     *
     * @param entity ISOCountryCode entity
     * @return ISOCountryCodeType instance
     */
    public static ISOCountryCodeType of(ISOCountryCode entity) {
        return new ISOCountryCodeType(entity);
    }

    /**
     * Create Thailand country code (TH)
     *
     * @return ISOCountryCodeType for Thailand
     */
    public static ISOCountryCodeType thailand() {
        return of("TH");
    }

    /**
     * Create United States country code (US)
     *
     * @return ISOCountryCodeType for United States
     */
    public static ISOCountryCodeType unitedStates() {
        return of("US");
    }

    /**
     * Create China country code (CN)
     *
     * @return ISOCountryCodeType for China
     */
    public static ISOCountryCodeType china() {
        return of("CN");
    }

    /**
     * Create Japan country code (JP)
     *
     * @return ISOCountryCodeType for Japan
     */
    public static ISOCountryCodeType japan() {
        return of("JP");
    }

    /**
     * Create Singapore country code (SG)
     *
     * @return ISOCountryCodeType for Singapore
     */
    public static ISOCountryCodeType singapore() {
        return of("SG");
    }

    // Business logic methods delegating to entity

    /**
     * Get country code (uppercase)
     *
     * @return Country code string, or null if value is null
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get country name
     *
     * @return Country name, or null if value is null
     */
    public String getName() {
        return value != null ? value.getName() : null;
    }

    /**
     * Check if this is Thailand (TH)
     *
     * @return true if Thailand
     */
    public boolean isThailand() {
        return value != null && value.isThailand();
    }

    /**
     * Check if this is an ASEAN country
     * ASEAN: TH, BN, KH, ID, LA, MY, MM, PH, SG, VN
     *
     * @return true if ASEAN country
     */
    public boolean isASEANCountry() {
        return value != null && value.isASEANCountry();
    }

    /**
     * Check if this is a major trading partner
     * Partners: CN, JP, KR, US, GB, DE, AU, IN, TW, HK, SG
     *
     * @return true if major trading partner
     */
    public boolean isMajorTradingPartner() {
        return value != null && value.isMajorTradingPartner();
    }

    /**
     * Check if this is an ETDA extension (AN, KS, UN)
     *
     * @return true if ETDA extension
     */
    public boolean isETDAExtension() {
        return value != null && value.isETDAExtension();
    }

    /**
     * Check if this is a standard ISO 3166-1 code
     *
     * @return true if standard ISO code
     */
    public boolean isStandardISO() {
        return value != null && value.isStandardISO();
    }

    /**
     * Check if this is China (CN)
     *
     * @return true if China
     */
    public boolean isChina() {
        return value != null && value.isChina();
    }

    /**
     * Check if this is Japan (JP)
     *
     * @return true if Japan
     */
    public boolean isJapan() {
        return value != null && value.isJapan();
    }

    /**
     * Check if this is South Korea (KR)
     *
     * @return true if South Korea
     */
    public boolean isSouthKorea() {
        return value != null && value.isSouthKorea();
    }

    /**
     * Check if this is United States (US)
     *
     * @return true if United States
     */
    public boolean isUnitedStates() {
        return value != null && value.isUnitedStates();
    }

    /**
     * Check if this is Singapore (SG)
     *
     * @return true if Singapore
     */
    public boolean isSingapore() {
        return value != null && value.isSingapore();
    }

    /**
     * Check if this is Malaysia (MY)
     *
     * @return true if Malaysia
     */
    public boolean isMalaysia() {
        return value != null && value.isMalaysia();
    }

    /**
     * Check if this is Indonesia (ID)
     *
     * @return true if Indonesia
     */
    public boolean isIndonesia() {
        return value != null && value.isIndonesia();
    }

    /**
     * Check if this is Vietnam (VN)
     *
     * @return true if Vietnam
     */
    public boolean isVietnam() {
        return value != null && value.isVietnam();
    }

    /**
     * Check if this is Philippines (PH)
     *
     * @return true if Philippines
     */
    public boolean isPhilippines() {
        return value != null && value.isPhilippines();
    }

    // Getters and Setters

    public ISOCountryCode getValue() {
        return value;
    }

    public void setValue(ISOCountryCode value) {
        this.value = value;
    }

    // Equals and HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISOCountryCodeType)) return false;
        ISOCountryCodeType that = (ISOCountryCodeType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "ISOCountryCodeType{null}";
    }
}
