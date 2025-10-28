package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.ThaiDocumentNameCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Thai Document Name Codes
 */
@Repository
public interface ThaiDocumentNameCodeRepository extends JpaRepository<ThaiDocumentNameCode, String> {

    /**
     * Find document by code
     */
    Optional<ThaiDocumentNameCode> findByCode(String code);

    /**
     * Find all standard UN/CEFACT codes
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE d.standardCode = true ORDER BY d.code")
    List<ThaiDocumentNameCode> findStandardCodes();

    /**
     * Find all Thai extension codes (T01-T07)
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE d.thaiExtension = true ORDER BY d.code")
    List<ThaiDocumentNameCode> findThaiExtensions();

    /**
     * Find all tax invoice types
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE LOWER(d.nameEn) LIKE LOWER('%tax invoice%') ORDER BY d.code")
    List<ThaiDocumentNameCode> findTaxInvoiceTypes();

    /**
     * Find all receipt types
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE LOWER(d.nameEn) LIKE LOWER('%receipt%') ORDER BY d.code")
    List<ThaiDocumentNameCode> findReceiptTypes();

    /**
     * Search by Thai name
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE LOWER(d.nameTh) LIKE LOWER(CONCAT('%', :nameTh, '%'))")
    List<ThaiDocumentNameCode> findByNameThContaining(@Param("nameTh") String nameTh);

    /**
     * Search by English name
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE LOWER(d.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%'))")
    List<ThaiDocumentNameCode> findByNameEnContaining(@Param("nameEn") String nameEn);

    /**
     * Check if code exists
     */
    boolean existsByCode(String code);

    /**
     * Find debit note codes
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE d.code IN ('80') ORDER BY d.code")
    List<ThaiDocumentNameCode> findDebitNoteCodes();

    /**
     * Find credit note codes
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE d.code IN ('81') ORDER BY d.code")
    List<ThaiDocumentNameCode> findCreditNoteCodes();

    /**
     * Find abbreviated tax invoice types
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE d.code IN ('T05', 'T06') ORDER BY d.code")
    List<ThaiDocumentNameCode> findAbbreviatedTaxInvoiceTypes();

    /**
     * Find cancellation note
     */
    @Query("SELECT d FROM ThaiDocumentNameCode d WHERE d.code = 'T07'")
    Optional<ThaiDocumentNameCode> findCancellationNote();
}
