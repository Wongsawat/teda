package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.ISOCountryCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for ISO 3166-1 Two-letter Country Code
 *
 * Standard: ISO 3166-1 alpha-2
 * Total Countries: 252 (249 standard + 3 ETDA extensions)
 */
@Repository
public interface ISOCountryCodeRepository extends JpaRepository<ISOCountryCode, String> {

    // Basic queries

    /**
     * Find country by code (case-insensitive, normalized to uppercase)
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE UPPER(c.code) = UPPER(:code) AND c.active = true")
    Optional<ISOCountryCode> findByCode(@Param("code") String code);

    /**
     * Find all active countries ordered by name
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE c.active = true ORDER BY c.name")
    List<ISOCountryCode> findAllActive();

    /**
     * Find all standard ISO countries (excluding ETDA extensions)
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE c.etdaExtension = false AND c.active = true ORDER BY c.code")
    List<ISOCountryCode> findStandardISO();

    /**
     * Find all ETDA extension codes (AN, KS, UN)
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE c.etdaExtension = true ORDER BY c.code")
    List<ISOCountryCode> findETDAExtensions();

    /**
     * Check if country code exists and is active
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ISOCountryCode c WHERE UPPER(c.code) = UPPER(:code) AND c.active = true")
    boolean existsByCode(@Param("code") String code);

    // ASEAN countries (10 members)

    /**
     * Find all ASEAN member countries
     * ASEAN: TH, BN, KH, ID, LA, MY, MM, PH, SG, VN
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE c.code IN ('TH', 'BN', 'KH', 'ID', 'LA', 'MY', 'MM', 'PH', 'SG', 'VN') AND c.active = true ORDER BY c.name")
    List<ISOCountryCode> findASEANCountries();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'TH' AND c.active = true")
    Optional<ISOCountryCode> findThailand();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'SG' AND c.active = true")
    Optional<ISOCountryCode> findSingapore();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'MY' AND c.active = true")
    Optional<ISOCountryCode> findMalaysia();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'ID' AND c.active = true")
    Optional<ISOCountryCode> findIndonesia();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'VN' AND c.active = true")
    Optional<ISOCountryCode> findVietnam();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'PH' AND c.active = true")
    Optional<ISOCountryCode> findPhilippines();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'MM' AND c.active = true")
    Optional<ISOCountryCode> findMyanmar();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'KH' AND c.active = true")
    Optional<ISOCountryCode> findCambodia();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'LA' AND c.active = true")
    Optional<ISOCountryCode> findLaos();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'BN' AND c.active = true")
    Optional<ISOCountryCode> findBrunei();

    // Major trading partners

    /**
     * Find major trading partners of Thailand
     * Partners: CN, JP, KR, US, GB, DE, AU, IN, TW, HK, SG
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE c.code IN ('CN', 'JP', 'KR', 'US', 'GB', 'DE', 'AU', 'IN', 'TW', 'HK', 'SG') AND c.active = true ORDER BY " +
           "CASE c.code WHEN 'CN' THEN 1 WHEN 'JP' THEN 2 WHEN 'KR' THEN 3 WHEN 'US' THEN 4 WHEN 'SG' THEN 5 ELSE 6 END, c.name")
    List<ISOCountryCode> findMajorTradingPartners();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'CN' AND c.active = true")
    Optional<ISOCountryCode> findChina();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'JP' AND c.active = true")
    Optional<ISOCountryCode> findJapan();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'KR' AND c.active = true")
    Optional<ISOCountryCode> findSouthKorea();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'US' AND c.active = true")
    Optional<ISOCountryCode> findUnitedStates();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'GB' AND c.active = true")
    Optional<ISOCountryCode> findUnitedKingdom();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'AU' AND c.active = true")
    Optional<ISOCountryCode> findAustralia();

    @Query("SELECT c FROM ISOCountryCode c WHERE c.code = 'IN' AND c.active = true")
    Optional<ISOCountryCode> findIndia();

    // Search queries

    /**
     * Search countries by name (case-insensitive partial match)
     */
    @Query("SELECT c FROM ISOCountryCode c WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%')) AND c.active = true ORDER BY c.name")
    List<ISOCountryCode> findByNameContaining(@Param("name") String name);

    /**
     * Count total active countries
     */
    @Query("SELECT COUNT(c) FROM ISOCountryCode c WHERE c.active = true")
    long countActive();

    /**
     * Count ASEAN countries (should be 10)
     */
    @Query("SELECT COUNT(c) FROM ISOCountryCode c WHERE c.code IN ('TH', 'BN', 'KH', 'ID', 'LA', 'MY', 'MM', 'PH', 'SG', 'VN') AND c.active = true")
    long countASEAN();
}
