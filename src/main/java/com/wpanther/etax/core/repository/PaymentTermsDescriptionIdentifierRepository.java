package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.PaymentTermsDescriptionIdentifier;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Payment Terms Description Identifiers
 *
 * Provides query methods for payment draft classification:
 * - Draft on various banks (issuing, advising, reimbursing)
 * - Draft on applicant or other drawee
 * - No draft required
 * - Payment terms specified elsewhere
 */
@Repository
public interface PaymentTermsDescriptionIdentifierRepository extends JpaRepository<PaymentTermsDescriptionIdentifier, String> {

    /**
     * Find payment terms description identifier by code
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.code = :code")
    Optional<PaymentTermsDescriptionIdentifier> findByCode(@Param("code") String code);

    /**
     * Find all identifiers requiring draft
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.isDraftRequired = true ORDER BY p.code")
    List<PaymentTermsDescriptionIdentifier> findDraftRequired();

    /**
     * Find all identifiers not requiring draft
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.isDraftRequired = false ORDER BY p.code")
    List<PaymentTermsDescriptionIdentifier> findNoDraft();

    /**
     * Find banking-related payment terms (drafts on banks)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE LOWER(p.name) LIKE '%bank%' ORDER BY p.code")
    List<PaymentTermsDescriptionIdentifier> findBankingRelated();

    /**
     * Search payment terms by name (case-insensitive)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY p.code")
    List<PaymentTermsDescriptionIdentifier> findByNameContaining(@Param("name") String name);

    /**
     * Search payment terms by description (case-insensitive)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE UPPER(p.description) LIKE UPPER(CONCAT('%', :keyword, '%')) ORDER BY p.code")
    List<PaymentTermsDescriptionIdentifier> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Check if payment terms description identifier exists
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PaymentTermsDescriptionIdentifier p WHERE p.code = :code")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find draft on issuing bank (code 1)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.code = '1'")
    Optional<PaymentTermsDescriptionIdentifier> findIssuingBankDraft();

    /**
     * Find draft on advising bank (code 2)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.code = '2'")
    Optional<PaymentTermsDescriptionIdentifier> findAdvisingBankDraft();

    /**
     * Find draft on reimbursing bank (code 3)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.code = '3'")
    Optional<PaymentTermsDescriptionIdentifier> findReimbursingBankDraft();

    /**
     * Find no draft option (code 6)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.code = '6'")
    Optional<PaymentTermsDescriptionIdentifier> findNoDraftOption();

    /**
     * Find commercial account summary option (code 7)
     */
    @Query("SELECT p FROM PaymentTermsDescriptionIdentifier p WHERE p.code = '7'")
    Optional<PaymentTermsDescriptionIdentifier> findCommercialAccountSummary();
}
