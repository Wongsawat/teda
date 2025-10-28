package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.AllowanceChargeReasonCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Allowance Charge Reason Codes
 *
 * Provides query methods for invoice adjustment reasons:
 * - Quality issues (damaged goods, below specification)
 * - Delivery problems (short delivery, wrong delivery, returns)
 * - Administrative errors (invoice errors, incorrect data)
 * - Discounts and allowances (trade discount, volume discount)
 * - Financial charges (bank charges, commissions)
 * - Claims and disputes (counter claims, offsets)
 * - Freight and logistics
 * - Payment terms
 * - HR related
 */
@Repository
public interface AllowanceChargeReasonCodeRepository extends JpaRepository<AllowanceChargeReasonCode, String> {

    /**
     * Find allowance charge reason code by code
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = UPPER(:code)")
    Optional<AllowanceChargeReasonCode> findByCode(@Param("code") String code);

    /**
     * Find by category
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = :category ORDER BY a.code")
    List<AllowanceChargeReasonCode> findByCategory(@Param("category") String category);

    /**
     * Find all categories
     */
    @Query("SELECT DISTINCT a.category FROM AllowanceChargeReasonCode a WHERE a.category IS NOT NULL ORDER BY a.category")
    List<String> findAllCategories();

    /**
     * Find quality issue reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Quality Issue' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findQualityIssues();

    /**
     * Find delivery issue reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Delivery Issue' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findDeliveryIssues();

    /**
     * Find administrative error reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Administrative Error' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findAdministrativeErrors();

    /**
     * Find discount and allowance reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Discount/Allowance' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findDiscountsAndAllowances();

    /**
     * Find financial charge reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Financial Charges' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findFinancialCharges();

    /**
     * Find claim and dispute reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Claims/Disputes' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findClaimsAndDisputes();

    /**
     * Find freight and logistics reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Freight/Logistics' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findFreightAndLogistics();

    /**
     * Find payment terms reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'Payment Terms' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findPaymentTerms();

    /**
     * Find HR related reasons
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.category = 'HR Related' ORDER BY a.code")
    List<AllowanceChargeReasonCode> findHRRelated();

    /**
     * Search by name (case-insensitive)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE UPPER(a.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY a.code")
    List<AllowanceChargeReasonCode> findByNameContaining(@Param("name") String name);

    /**
     * Search by description (case-insensitive)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE UPPER(a.description) LIKE UPPER(CONCAT('%', :keyword, '%')) ORDER BY a.code")
    List<AllowanceChargeReasonCode> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Check if allowance charge reason code exists
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AllowanceChargeReasonCode a WHERE a.code = UPPER(:code)")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find most commonly used reason codes for e-Tax Invoice
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code IN ('1', '3', '4', '9', '11', '19', '66', '74', '78', 'ZZZ') ORDER BY " +
           "CASE a.code " +
           "WHEN '19' THEN 1 " +
           "WHEN '74' THEN 2 " +
           "WHEN '78' THEN 3 " +
           "WHEN '66' THEN 4 " +
           "WHEN '1' THEN 5 " +
           "WHEN '3' THEN 6 " +
           "WHEN '4' THEN 7 " +
           "WHEN '9' THEN 8 " +
           "WHEN '11' THEN 9 " +
           "WHEN 'ZZZ' THEN 10 " +
           "END")
    List<AllowanceChargeReasonCode> findCommonReasons();

    /**
     * Find agreed settlement (code 1)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '1'")
    Optional<AllowanceChargeReasonCode> findAgreedSettlement();

    /**
     * Find damaged goods (code 3)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '3'")
    Optional<AllowanceChargeReasonCode> findDamagedGoods();

    /**
     * Find short delivery (code 4)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '4'")
    Optional<AllowanceChargeReasonCode> findShortDelivery();

    /**
     * Find invoice error (code 9)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '9'")
    Optional<AllowanceChargeReasonCode> findInvoiceError();

    /**
     * Find bank charges (code 11)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '11'")
    Optional<AllowanceChargeReasonCode> findBankCharges();

    /**
     * Find trade discount (code 19)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '19'")
    Optional<AllowanceChargeReasonCode> findTradeDiscount();

    /**
     * Find cash discount (code 66)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '66'")
    Optional<AllowanceChargeReasonCode> findCashDiscount();

    /**
     * Find quantity discount (code 74)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '74'")
    Optional<AllowanceChargeReasonCode> findQuantityDiscount();

    /**
     * Find volume discount (code 78)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = '78'")
    Optional<AllowanceChargeReasonCode> findVolumeDiscount();

    /**
     * Find mutually defined (code ZZZ)
     */
    @Query("SELECT a FROM AllowanceChargeReasonCode a WHERE a.code = 'ZZZ'")
    Optional<AllowanceChargeReasonCode> findMutuallyDefined();

    /**
     * Get count by category
     */
    @Query("SELECT a.category, COUNT(a) FROM AllowanceChargeReasonCode a WHERE a.category IS NOT NULL GROUP BY a.category ORDER BY a.category")
    List<Object[]> countByCategory();

    /**
     * Full-text search by keyword
     */
    @Query(value = "SELECT * FROM search_allowance_charge_reason_codes(:searchTerm)", nativeQuery = true)
    List<AllowanceChargeReasonCode> searchByKeyword(@Param("searchTerm") String searchTerm);
}
