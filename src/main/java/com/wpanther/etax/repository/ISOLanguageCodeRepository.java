package com.wpanther.etax.repository;

import com.wpanther.etax.entity.ISOLanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for ISO 639-1 Language Codes
 *
 * Provides query methods for international language codes:
 * - ASEAN languages (th, en, ms, id, vi, my, km, lo, tl)
 * - Major trading partner languages (en, th, zh, ja, ko, de, fr, es, ar, ru)
 * - Case-insensitive lookup (handles both 'th' and 'TH')
 * - Active languages only
 */
@Repository
public interface ISOLanguageCodeRepository extends JpaRepository<ISOLanguageCode, String> {

    /**
     * Find language by code (case-insensitive)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE LOWER(l.code) = LOWER(:code) AND l.isActive = true")
    Optional<ISOLanguageCode> findByCode(@Param("code") String code);

    /**
     * Find all active languages
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.isActive = true ORDER BY l.name")
    List<ISOLanguageCode> findAllActive();

    /**
     * Find ASEAN languages (th, en, ms, id, vi, my, km, lo, tl)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code IN ('th', 'en', 'ms', 'id', 'vi', 'my', 'km', 'lo', 'tl') AND l.isActive = true ORDER BY l.name")
    List<ISOLanguageCode> findASEANLanguages();

    /**
     * Find major trading partner languages
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code IN ('en', 'th', 'zh', 'ja', 'ko', 'de', 'fr', 'es', 'ar', 'ru') AND l.isActive = true ORDER BY " +
           "CASE l.code " +
           "WHEN 'th' THEN 1 " +
           "WHEN 'en' THEN 2 " +
           "WHEN 'zh' THEN 3 " +
           "WHEN 'ja' THEN 4 " +
           "WHEN 'ko' THEN 5 " +
           "ELSE 6 END, l.name")
    List<ISOLanguageCode> findMajorTradingLanguages();

    /**
     * Search by name (case-insensitive)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE UPPER(l.name) LIKE UPPER(CONCAT('%', :name, '%')) AND l.isActive = true ORDER BY l.name")
    List<ISOLanguageCode> findByNameContaining(@Param("name") String name);

    /**
     * Check if language code exists (case-insensitive)
     */
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM ISOLanguageCode l WHERE LOWER(l.code) = LOWER(:code) AND l.isActive = true")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find Thai language (th)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'th' AND l.isActive = true")
    Optional<ISOLanguageCode> findThai();

    /**
     * Find English language (en)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'en' AND l.isActive = true")
    Optional<ISOLanguageCode> findEnglish();

    /**
     * Find Chinese language (zh)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'zh' AND l.isActive = true")
    Optional<ISOLanguageCode> findChinese();

    /**
     * Find Japanese language (ja)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'ja' AND l.isActive = true")
    Optional<ISOLanguageCode> findJapanese();

    /**
     * Find Korean language (ko)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'ko' AND l.isActive = true")
    Optional<ISOLanguageCode> findKorean();

    /**
     * Find Indonesian language (id)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'id' AND l.isActive = true")
    Optional<ISOLanguageCode> findIndonesian();

    /**
     * Find Malay language (ms)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'ms' AND l.isActive = true")
    Optional<ISOLanguageCode> findMalay();

    /**
     * Find Vietnamese language (vi)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'vi' AND l.isActive = true")
    Optional<ISOLanguageCode> findVietnamese();

    /**
     * Find Burmese language (my)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'my' AND l.isActive = true")
    Optional<ISOLanguageCode> findBurmese();

    /**
     * Find Khmer/Cambodian language (km)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'km' AND l.isActive = true")
    Optional<ISOLanguageCode> findKhmer();

    /**
     * Find Lao language (lo)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'lo' AND l.isActive = true")
    Optional<ISOLanguageCode> findLao();

    /**
     * Find Tagalog language (tl)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'tl' AND l.isActive = true")
    Optional<ISOLanguageCode> findTagalog();

    /**
     * Find German language (de)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'de' AND l.isActive = true")
    Optional<ISOLanguageCode> findGerman();

    /**
     * Find French language (fr)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'fr' AND l.isActive = true")
    Optional<ISOLanguageCode> findFrench();

    /**
     * Find Spanish language (es)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'es' AND l.isActive = true")
    Optional<ISOLanguageCode> findSpanish();

    /**
     * Find Arabic language (ar)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'ar' AND l.isActive = true")
    Optional<ISOLanguageCode> findArabic();

    /**
     * Find Russian language (ru)
     */
    @Query("SELECT l FROM ISOLanguageCode l WHERE l.code = 'ru' AND l.isActive = true")
    Optional<ISOLanguageCode> findRussian();
}
