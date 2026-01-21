package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.ISOCurrencyCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for ISO 4217 Currency Codes
 *
 * Provides query methods for international currency codes:
 * - Major reserve currencies (USD, EUR, JPY, GBP, CNY, CHF, CAD, AUD)
 * - ASEAN currencies (THB, BND, KHR, IDR, LAK, MYR, MMK, PHP, SGD, VND)
 * - Thai trading partner currencies
 * - Active currencies only
 */
@Repository
public interface ISOCurrencyCodeRepository extends JpaRepository<ISOCurrencyCode, String> {

    /**
     * Find currency by code (case-insensitive)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = UPPER(:code) AND c.active = true")
    Optional<ISOCurrencyCode> findByCode(@Param("code") String code);

    /**
     * Find currency by numeric code
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.numericCode = :numericCode AND c.active = true")
    Optional<ISOCurrencyCode> findByNumericCode(@Param("numericCode") String numericCode);

    /**
     * Find all active currencies
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findAllActive();

    /**
     * Find major reserve currencies
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code IN ('USD', 'EUR', 'JPY', 'GBP', 'CNY', 'CHF', 'CAD', 'AUD') AND c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findMajorCurrencies();

    /**
     * Find ASEAN currencies
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code IN ('THB', 'BND', 'KHR', 'IDR', 'LAK', 'MYR', 'MMK', 'PHP', 'SGD', 'VND') AND c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findASEANCurrencies();

    /**
     * Find common currencies used in Thai international trade
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code IN ('THB', 'USD', 'EUR', 'JPY', 'CNY', 'GBP', 'SGD', 'MYR', 'IDR', 'VND', 'KRW', 'HKD', 'TWD', 'AUD', 'INR', 'CHF') AND c.active = true ORDER BY " +
           "CASE c.code " +
           "WHEN 'THB' THEN 1 " +
           "WHEN 'USD' THEN 2 " +
           "WHEN 'EUR' THEN 3 " +
           "WHEN 'JPY' THEN 4 " +
           "WHEN 'CNY' THEN 5 " +
           "WHEN 'GBP' THEN 6 " +
           "WHEN 'SGD' THEN 7 " +
           "WHEN 'HKD' THEN 8 " +
           "WHEN 'AUD' THEN 9 " +
           "WHEN 'MYR' THEN 10 " +
           "ELSE 11 END")
    List<ISOCurrencyCode> findThaiTradingCurrencies();

    /**
     * Find currencies with specific minor units (decimal places)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.minorUnits = :minorUnits AND c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findByMinorUnits(@Param("minorUnits") Integer minorUnits);

    /**
     * Find currencies with zero decimal places (JPY, KRW, etc.)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.minorUnits = 0 AND c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findZeroDecimalCurrencies();

    /**
     * Find currencies with 3 decimal places (BHD, KWD, etc.)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.minorUnits = 3 AND c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findThreeDecimalCurrencies();

    /**
     * Search by name (case-insensitive)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%')) AND c.active = true ORDER BY c.code")
    List<ISOCurrencyCode> findByNameContaining(@Param("name") String name);

    /**
     * Check if currency code exists and is active
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ISOCurrencyCode c WHERE c.code = UPPER(:code) AND c.active = true")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find Thai Baht (THB)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'THB' AND c.active = true")
    Optional<ISOCurrencyCode> findThaiBaht();

    /**
     * Find US Dollar (USD)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'USD' AND c.active = true")
    Optional<ISOCurrencyCode> findUSDollar();

    /**
     * Find Euro (EUR)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'EUR' AND c.active = true")
    Optional<ISOCurrencyCode> findEuro();

    /**
     * Find Japanese Yen (JPY)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'JPY' AND c.active = true")
    Optional<ISOCurrencyCode> findJapaneseYen();

    /**
     * Find Chinese Yuan (CNY)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'CNY' AND c.active = true")
    Optional<ISOCurrencyCode> findChineseYuan();

    /**
     * Find British Pound (GBP)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'GBP' AND c.active = true")
    Optional<ISOCurrencyCode> findBritishPound();

    /**
     * Find Singapore Dollar (SGD)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'SGD' AND c.active = true")
    Optional<ISOCurrencyCode> findSingaporeDollar();

    /**
     * Find Malaysian Ringgit (MYR)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'MYR' AND c.active = true")
    Optional<ISOCurrencyCode> findMalaysianRinggit();

    /**
     * Find Indonesian Rupiah (IDR)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'IDR' AND c.active = true")
    Optional<ISOCurrencyCode> findIndonesianRupiah();

    /**
     * Find Hong Kong Dollar (HKD)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'HKD' AND c.active = true")
    Optional<ISOCurrencyCode> findHongKongDollar();

    /**
     * Find Korean Won (KRW)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'KRW' AND c.active = true")
    Optional<ISOCurrencyCode> findKoreanWon();

    /**
     * Find Vietnamese Dong (VND)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'VND' AND c.active = true")
    Optional<ISOCurrencyCode> findVietnameseDong();

    /**
     * Find Australian Dollar (AUD)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'AUD' AND c.active = true")
    Optional<ISOCurrencyCode> findAustralianDollar();

    /**
     * Find Swiss Franc (CHF)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'CHF' AND c.active = true")
    Optional<ISOCurrencyCode> findSwissFranc();

    /**
     * Find Canadian Dollar (CAD)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'CAD' AND c.active = true")
    Optional<ISOCurrencyCode> findCanadianDollar();

    /**
     * Find Indian Rupee (INR)
     */
    @Query("SELECT c FROM ISOCurrencyCode c WHERE c.code = 'INR' AND c.active = true")
    Optional<ISOCurrencyCode> findIndianRupee();
}
