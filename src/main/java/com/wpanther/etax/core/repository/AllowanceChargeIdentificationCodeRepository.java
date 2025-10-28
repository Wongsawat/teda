package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.AllowanceChargeIdentificationCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Allowance/Charge Identification Codes
 *
 * Provides query methods for specific types of invoice adjustments:
 * - Documentary credit commissions (handling, amendment, acceptance, confirmation)
 * - Collection commissions (delivery, collection, return)
 * - Processing fees (SWIFT, courier, phone, fax, telex, postage)
 * - Discounts (manufacturer, production error, sample, end-of-range, Incoterm)
 * - Penalties (late delivery, execution delays)
 * - Bonuses (early completion)
 * - Freight/packing/loading/handling charges
 * - Testing/inspection charges
 * - Thai extensions (deposits, guarantees, advance payments)
 */
@Repository
public interface AllowanceChargeIdentificationCodeRepository extends JpaRepository<AllowanceChargeIdentificationCode, String> {

    /**
     * Find allowance/charge identification code by code
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = UPPER(:code)")
    Optional<AllowanceChargeIdentificationCode> findByCode(@Param("code") String code);

    /**
     * Find by category
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = :category ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findByCategory(@Param("category") String category);

    /**
     * Find all categories
     */
    @Query("SELECT DISTINCT a.category FROM AllowanceChargeIdentificationCode a WHERE a.category IS NOT NULL ORDER BY a.category")
    List<String> findAllCategories();

    /**
     * Find documentary credit commission codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Documentary Credit Commission' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findDocumentaryCreditCommissions();

    /**
     * Find collection commission codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Collection Commission' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findCollectionCommissions();

    /**
     * Find processing fee codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Processing Fee' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findProcessingFees();

    /**
     * Find discount codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Discount' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findDiscounts();

    /**
     * Find rebate codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Rebate' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findRebates();

    /**
     * Find penalty codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Penalty' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findPenalties();

    /**
     * Find bonus codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Bonus' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findBonuses();

    /**
     * Find freight charges codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Freight Charges' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findFreightCharges();

    /**
     * Find packing charges codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Packing Charges' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findPackingCharges();

    /**
     * Find loading/unloading charges codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Loading/Unloading Charges' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findLoadingCharges();

    /**
     * Find handling charges codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Handling Charges' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findHandlingCharges();

    /**
     * Find testing/inspection charges codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category = 'Testing/Inspection Charges' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findTestingCharges();

    /**
     * Find standard UN/CEFACT codes only
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.isStandardCode = true ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findStandardCodes();

    /**
     * Find Thai extension codes only
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.isThaiExtension = true ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findThaiExtensions();

    /**
     * Search by name (case-insensitive)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE UPPER(a.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findByNameContaining(@Param("name") String name);

    /**
     * Search by description (case-insensitive)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE UPPER(a.description) LIKE UPPER(CONCAT('%', :keyword, '%')) ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Check if code exists
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AllowanceChargeIdentificationCode a WHERE a.code = UPPER(:code)")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find most commonly used codes for e-Tax Invoice
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code IN ('1', '30', '32', '35', '60', '79', '80', '95', '96', 'PP001', 'PP002') ORDER BY " +
           "CASE a.code " +
           "WHEN '79' THEN 1 " +  // Freight charges
           "WHEN '80' THEN 2 " +  // Packing charge
           "WHEN '95' THEN 3 " +  // Discount
           "WHEN '60' THEN 4 " +  // Manufacturer's consumer discount
           "WHEN '96' THEN 5 " +  // Insurance
           "WHEN '30' THEN 6 " +  // Bank charges
           "WHEN '35' THEN 7 " +  // SWIFT fee
           "WHEN 'PP001' THEN 8 " +  // Thai: Deposit
           "WHEN 'PP002' THEN 9 " +  // Thai: Guarantee
           "WHEN '1' THEN 10 " +  // Handling commission
           "WHEN '32' THEN 11 " +  // Courier fee
           "END")
    List<AllowanceChargeIdentificationCode> findCommonCodes();

    /**
     * Find handling commission (code 1)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '1'")
    Optional<AllowanceChargeIdentificationCode> findHandlingCommission();

    /**
     * Find bank charges (code 30)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '30'")
    Optional<AllowanceChargeIdentificationCode> findBankCharges();

    /**
     * Find courier fee (code 32)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '32'")
    Optional<AllowanceChargeIdentificationCode> findCourierFee();

    /**
     * Find SWIFT fee (code 35)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '35'")
    Optional<AllowanceChargeIdentificationCode> findSwiftFee();

    /**
     * Find manufacturer's consumer discount (code 60)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '60'")
    Optional<AllowanceChargeIdentificationCode> findManufacturerDiscount();

    /**
     * Find freight charges (code 79)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '79'")
    Optional<AllowanceChargeIdentificationCode> findFreightChargesCode();

    /**
     * Find packing charge (code 80)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '80'")
    Optional<AllowanceChargeIdentificationCode> findPackingCharge();

    /**
     * Find discount (code 95)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '95'")
    Optional<AllowanceChargeIdentificationCode> findDiscountCode();

    /**
     * Find insurance (code 96)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '96'")
    Optional<AllowanceChargeIdentificationCode> findInsurance();

    /**
     * Find special rebate (code 100)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '100'")
    Optional<AllowanceChargeIdentificationCode> findSpecialRebate();

    /**
     * Find carbon footprint charge (code 101)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = '101'")
    Optional<AllowanceChargeIdentificationCode> findCarbonFootprintCharge();

    /**
     * Find Thai deposit code (PP001)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = 'PP001'")
    Optional<AllowanceChargeIdentificationCode> findThaiDeposit();

    /**
     * Find Thai guarantee code (PP002)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = 'PP002'")
    Optional<AllowanceChargeIdentificationCode> findThaiGuarantee();

    /**
     * Find Thai reservation code (PP003)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = 'PP003'")
    Optional<AllowanceChargeIdentificationCode> findThaiReservation();

    /**
     * Find Thai advance payment code (PP004)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = 'PP004'")
    Optional<AllowanceChargeIdentificationCode> findThaiAdvancePayment();

    /**
     * Find Thai performance guarantee code (PP005)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = 'PP005'")
    Optional<AllowanceChargeIdentificationCode> findThaiPerformanceGuarantee();

    /**
     * Find Thai other advance payment code (PP006)
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.code = 'PP006'")
    Optional<AllowanceChargeIdentificationCode> findThaiOtherAdvancePayment();

    /**
     * Get count by category
     */
    @Query("SELECT a.category, COUNT(a) FROM AllowanceChargeIdentificationCode a WHERE a.category IS NOT NULL GROUP BY a.category ORDER BY a.category")
    List<Object[]> countByCategory();

    /**
     * Full-text search by keyword
     */
    @Query(value = "SELECT * FROM search_allowance_charge_codes(:searchTerm)", nativeQuery = true)
    List<AllowanceChargeIdentificationCode> searchByKeyword(@Param("searchTerm") String searchTerm);

    /**
     * Find all commission-type codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category LIKE '%Commission%' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findAllCommissions();

    /**
     * Find all charge-type codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category LIKE '%Charge%' OR a.category LIKE '%Fee%' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findAllCharges();

    /**
     * Find all allowance-type codes
     */
    @Query("SELECT a FROM AllowanceChargeIdentificationCode a WHERE a.category LIKE '%Discount%' OR a.category LIKE '%Rebate%' OR a.category LIKE '%Bonus%' OR a.category LIKE '%Allowance%' ORDER BY a.code")
    List<AllowanceChargeIdentificationCode> findAllAllowances();
}
