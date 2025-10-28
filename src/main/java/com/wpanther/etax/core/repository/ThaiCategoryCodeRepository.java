package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.ThaiCategoryCode;

import java.util.Optional;

/**
 * Spring Data JPA Repository for Thai Category Code
 *
 * Provides database access for 2 Thai e-Tax Invoice category codes
 * used for document references (cancellation, debit/credit notes, advance payments)
 */
@Repository
public interface ThaiCategoryCodeRepository extends JpaRepository<ThaiCategoryCode, String> {

    /**
     * Find category code by code (case-insensitive)
     *
     * @param code The category code (e.g., "01", "02")
     * @return Optional containing the code if found
     */
    @Query("SELECT c FROM ThaiCategoryCode c WHERE UPPER(c.code) = UPPER(:code)")
    Optional<ThaiCategoryCode> findByCode(@Param("code") String code);

    /**
     * Check if a category code exists (case-insensitive)
     *
     * @param code The category code
     * @return true if exists
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ThaiCategoryCode c WHERE UPPER(c.code) = UPPER(:code)")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find category code 01 - Original document reference (cancellation/debit/credit)
     *
     * @return Optional containing code 01
     */
    @Query("SELECT c FROM ThaiCategoryCode c WHERE c.code = '01'")
    Optional<ThaiCategoryCode> findOriginalDocumentReference();

    /**
     * Find category code 02 - Advance payment reference
     *
     * @return Optional containing code 02
     */
    @Query("SELECT c FROM ThaiCategoryCode c WHERE c.code = '02'")
    Optional<ThaiCategoryCode> findAdvancePaymentReference();
}
