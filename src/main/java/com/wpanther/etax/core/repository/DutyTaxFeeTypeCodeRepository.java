package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.DutyTaxFeeTypeCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Duty Tax Fee Type Codes
 *
 * Provides query methods for tax and duty classification:
 * - VAT and GST codes
 * - Customs duties
 * - Excise taxes
 * - Tax exemptions
 * - Special taxes and fees
 */
@Repository
public interface DutyTaxFeeTypeCodeRepository extends JpaRepository<DutyTaxFeeTypeCode, String> {

    /**
     * Find duty tax fee type code by code
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE UPPER(d.code) = UPPER(:code)")
    Optional<DutyTaxFeeTypeCode> findByCode(@Param("code") String code);

    /**
     * Find active duty tax fee type code by code
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = :code AND d.active = true")
    Optional<DutyTaxFeeTypeCode> findByCodeAndActive(@Param("code") String code);

    /**
     * Find all VAT-related codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.isVat = true AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findVATCodes();

    /**
     * Find all exempt codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.isExempt = true AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findExemptCodes();

    /**
     * Find all summary codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.isSummary = true AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findSummaryCodes();

    /**
     * Find duty tax fee codes by category
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.category = :category AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findByCategory(@Param("category") String category);

    /**
     * Find all available categories
     */
    @Query("SELECT DISTINCT d.category FROM DutyTaxFeeTypeCode d WHERE d.category IS NOT NULL AND d.active = true ORDER BY d.category")
    List<String> findAllCategories();

    /**
     * Search duty tax fee codes by name (case-insensitive)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE UPPER(d.name) LIKE UPPER(CONCAT('%', :name, '%')) AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findByNameContaining(@Param("name") String name);

    /**
     * Search duty tax fee codes by description (case-insensitive)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE UPPER(d.description) LIKE UPPER(CONCAT('%', :keyword, '%')) AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Check if duty tax fee code exists
     */
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DutyTaxFeeTypeCode d WHERE d.code = :code")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find customs duty codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.category = 'Customs' AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findCustomsDuties();

    /**
     * Find excise tax codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.category = 'Excise' AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findExciseTaxes();

    /**
     * Find GST codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.category = 'GST' AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findGSTCodes();

    /**
     * Find special tax codes
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.category = 'Special Tax' AND d.active = true ORDER BY d.code")
    List<DutyTaxFeeTypeCode> findSpecialTaxes();

    /**
     * Find most commonly used codes for e-Tax Invoice
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code IN ('VAT', 'GST', 'EXE', 'TAX', 'FRE', 'OTH', 'TOT', 'ZZZ') AND d.active = true ORDER BY " +
           "CASE d.code " +
           "WHEN 'VAT' THEN 1 " +
           "WHEN 'GST' THEN 2 " +
           "WHEN 'EXE' THEN 3 " +
           "WHEN 'TAX' THEN 4 " +
           "WHEN 'FRE' THEN 5 " +
           "WHEN 'OTH' THEN 6 " +
           "WHEN 'TOT' THEN 7 " +
           "WHEN 'ZZZ' THEN 8 " +
           "END")
    List<DutyTaxFeeTypeCode> findCommonCodes();

    /**
     * Find VAT code
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'VAT'")
    Optional<DutyTaxFeeTypeCode> findVATCode();

    /**
     * Find GST code
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'GST'")
    Optional<DutyTaxFeeTypeCode> findGSTCode();

    /**
     * Find exempt code (EXE)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'EXE'")
    Optional<DutyTaxFeeTypeCode> findExemptCode();

    /**
     * Find tax code (TAX)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'TAX'")
    Optional<DutyTaxFeeTypeCode> findTaxCode();

    /**
     * Find free code (FRE)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'FRE'")
    Optional<DutyTaxFeeTypeCode> findFreeCode();

    /**
     * Find other code (OTH)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'OTH'")
    Optional<DutyTaxFeeTypeCode> findOtherCode();

    /**
     * Find total code (TOT)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'TOT'")
    Optional<DutyTaxFeeTypeCode> findTotalCode();

    /**
     * Find mutually defined code (ZZZ)
     */
    @Query("SELECT d FROM DutyTaxFeeTypeCode d WHERE d.code = 'ZZZ'")
    Optional<DutyTaxFeeTypeCode> findMutuallyDefinedCode();
}
