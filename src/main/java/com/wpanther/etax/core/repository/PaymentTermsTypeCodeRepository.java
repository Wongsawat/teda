package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.PaymentTermsTypeCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Payment Terms Type Codes
 *
 * Provides query methods for payment terms classification:
 * - Immediate payment terms (payment due on receipt)
 * - Deferred payment terms (scheduled/installment payments)
 * - Discount terms (early payment discounts)
 * - Letter of credit terms
 * - Category-based queries
 */
@Repository
public interface PaymentTermsTypeCodeRepository extends JpaRepository<PaymentTermsTypeCode, String> {

    /**
     * Find payment terms type code by code (case-insensitive)
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE UPPER(p.code) = UPPER(:code)")
    Optional<PaymentTermsTypeCode> findByCode(@Param("code") String code);

    /**
     * Find all immediate payment terms (payment due on receipt)
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE p.isImmediate = true ORDER BY p.code")
    List<PaymentTermsTypeCode> findImmediatePaymentTerms();

    /**
     * Find all deferred payment terms (scheduled/installment)
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE p.isDeferred = true ORDER BY p.code")
    List<PaymentTermsTypeCode> findDeferredPaymentTerms();

    /**
     * Find all discount payment terms
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE p.hasDiscount = true ORDER BY p.code")
    List<PaymentTermsTypeCode> findDiscountPaymentTerms();

    /**
     * Find payment terms by category
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE p.category = :category ORDER BY p.code")
    List<PaymentTermsTypeCode> findByCategory(@Param("category") String category);

    /**
     * Find all available categories
     */
    @Query("SELECT DISTINCT p.category FROM PaymentTermsTypeCode p WHERE p.category IS NOT NULL ORDER BY p.category")
    List<String> findAllCategories();

    /**
     * Search payment terms by name (case-insensitive)
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY p.code")
    List<PaymentTermsTypeCode> findByNameContaining(@Param("name") String name);

    /**
     * Search payment terms by description (case-insensitive)
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE UPPER(p.description) LIKE UPPER(CONCAT('%', :keyword, '%')) ORDER BY p.code")
    List<PaymentTermsTypeCode> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Check if payment terms code exists
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PaymentTermsTypeCode p WHERE UPPER(p.code) = UPPER(:code)")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find letter of credit payment terms
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE UPPER(p.description) LIKE '%LETTER OF CREDIT%' OR UPPER(p.name) LIKE '%CREDIT%' ORDER BY p.code")
    List<PaymentTermsTypeCode> findLetterOfCreditTerms();

    /**
     * Find advance payment terms
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE UPPER(p.description) LIKE '%ADVANCE%' OR UPPER(p.name) LIKE '%ADVANCE%' ORDER BY p.code")
    List<PaymentTermsTypeCode> findAdvancePaymentTerms();

    /**
     * Find installment payment terms
     */
    @Query("SELECT p FROM PaymentTermsTypeCode p WHERE UPPER(p.description) LIKE '%INSTALLMENT%' OR UPPER(p.name) LIKE '%INSTALLMENT%' ORDER BY p.code")
    List<PaymentTermsTypeCode> findInstallmentPaymentTerms();
}
