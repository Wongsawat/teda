package com.wpanther.etax.repository;

import com.wpanther.etax.entity.UNECEReferenceTypeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Reference Type Codes
 */
@Repository
public interface UNECEReferenceTypeCodeRepository extends JpaRepository<UNECEReferenceTypeCode, String> {

    /**
     * Find reference type code by code (case-insensitive) and active status
     */
    @Query("SELECT r FROM UNECEReferenceTypeCode r WHERE UPPER(r.code) = UPPER(:code) AND r.active = true")
    Optional<UNECEReferenceTypeCode> findByCodeAndActive(@Param("code") String code);

    /**
     * Find all active reference type codes
     */
    List<UNECEReferenceTypeCode> findByActiveTrue();

    /**
     * Find all ETDA extension codes
     */
    @Query("SELECT r FROM UNECEReferenceTypeCode r WHERE r.etdaExtension = true AND r.active = true")
    List<UNECEReferenceTypeCode> findEtdaExtensions();

    /**
     * Find invoice-related reference type codes
     */
    @Query("SELECT r FROM UNECEReferenceTypeCode r WHERE r.active = true AND " +
           "(UPPER(r.name) LIKE '%INVOICE%' OR UPPER(r.name) LIKE '%CREDIT NOTE%' OR " +
           "UPPER(r.name) LIKE '%DEBIT NOTE%' OR UPPER(r.name) LIKE '%ORDER%' OR " +
           "UPPER(r.name) LIKE '%CONTRACT%')")
    List<UNECEReferenceTypeCode> findInvoiceRelatedCodes();

    /**
     * Find financial/payment-related reference type codes
     */
    @Query("SELECT r FROM UNECEReferenceTypeCode r WHERE r.active = true AND " +
           "(UPPER(r.name) LIKE '%PAYMENT%' OR UPPER(r.name) LIKE '%BANK%' OR " +
           "UPPER(r.name) LIKE '%CREDIT%' OR UPPER(r.name) LIKE '%ACCOUNT%' OR " +
           "UPPER(r.name) LIKE '%FINANCIAL%')")
    List<UNECEReferenceTypeCode> findFinancialRelatedCodes();

    /**
     * Search reference type codes by name (case-insensitive)
     */
    @Query("SELECT r FROM UNECEReferenceTypeCode r WHERE UPPER(r.name) LIKE UPPER(CONCAT('%', :name, '%')) AND r.active = true")
    List<UNECEReferenceTypeCode> findByNameContaining(@Param("name") String name);

    /**
     * Check if reference type code exists and is active
     */
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM UNECEReferenceTypeCode r WHERE UPPER(r.code) = UPPER(:code) AND r.active = true")
    boolean existsByCodeAndActive(@Param("code") String code);
}
