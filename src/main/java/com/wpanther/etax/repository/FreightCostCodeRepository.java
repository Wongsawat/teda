package com.wpanther.etax.repository;

import com.wpanther.etax.entity.FreightCostCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Freight Cost Codes
 *
 * Provides query methods for freight and shipping cost classification:
 * - Basic freight charges
 * - Container services
 * - Terminal and handling charges
 * - Storage and demurrage
 * - Customs and documentation
 * - Dangerous goods
 * - Insurance and other charges
 */
@Repository
public interface FreightCostCodeRepository extends JpaRepository<FreightCostCode, String> {

    /**
     * Find freight cost code by code
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.code = :code")
    Optional<FreightCostCode> findByCode(@Param("code") String code);

    /**
     * Find freight cost codes by category
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = :category ORDER BY f.code")
    List<FreightCostCode> findByCategory(@Param("category") String category);

    /**
     * Find all available categories
     */
    @Query("SELECT DISTINCT f.category FROM FreightCostCode f WHERE f.category IS NOT NULL ORDER BY f.category")
    List<String> findAllCategories();

    /**
     * Find freight cost codes by code group (first 3 digits)
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.codeGroup = :codeGroup ORDER BY f.code")
    List<FreightCostCode> findByCodeGroup(@Param("codeGroup") String codeGroup);

    /**
     * Search freight cost codes by name (case-insensitive)
     */
    @Query("SELECT f FROM FreightCostCode f WHERE UPPER(f.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY f.code")
    List<FreightCostCode> findByNameContaining(@Param("name") String name);

    /**
     * Check if freight cost code exists
     */
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FreightCostCode f WHERE f.code = :code")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find basic freight charges (code group 101)
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Basic Freight' OR f.codeGroup = '101' ORDER BY f.code")
    List<FreightCostCode> findBasicFreight();

    /**
     * Find freight surcharges, allowances, and discounts
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Freight Surcharges' ORDER BY f.code")
    List<FreightCostCode> findFreightSurcharges();

    /**
     * Find container-related charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Container Services' ORDER BY f.code")
    List<FreightCostCode> findContainerServices();

    /**
     * Find terminal charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Terminal Charges' ORDER BY f.code")
    List<FreightCostCode> findTerminalCharges();

    /**
     * Find handling charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Handling Charges' ORDER BY f.code")
    List<FreightCostCode> findHandlingCharges();

    /**
     * Find storage and demurrage charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Storage & Demurrage' ORDER BY f.code")
    List<FreightCostCode> findStorageAndDemurrage();

    /**
     * Find customs and documentation charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Customs & Documentation' ORDER BY f.code")
    List<FreightCostCode> findCustomsAndDocumentation();

    /**
     * Find dangerous goods charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Dangerous Goods' ORDER BY f.code")
    List<FreightCostCode> findDangerousGoods();

    /**
     * Find special freight charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Special Freight' ORDER BY f.code")
    List<FreightCostCode> findSpecialFreight();

    /**
     * Find insurance charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Insurance' ORDER BY f.code")
    List<FreightCostCode> findInsurance();

    /**
     * Find general freight charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'General Freight' ORDER BY f.code")
    List<FreightCostCode> findGeneralFreight();

    /**
     * Find other charges
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.category = 'Other Charges' ORDER BY f.code")
    List<FreightCostCode> findOtherCharges();

    /**
     * Find most commonly used freight cost codes for e-Tax Invoice
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.code IN ('100000', '101000', '101021', '102000', '103000', '104000', '105000', '106000', '107000', '108000') ORDER BY f.code")
    List<FreightCostCode> findCommonCodes();

    /**
     * Find codes by code range
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.code BETWEEN :startCode AND :endCode ORDER BY f.code")
    List<FreightCostCode> findByCodeRange(@Param("startCode") String startCode, @Param("endCode") String endCode);

    /**
     * Get count by category
     */
    @Query("SELECT f.category, COUNT(f) FROM FreightCostCode f WHERE f.category IS NOT NULL GROUP BY f.category ORDER BY f.category")
    List<Object[]> countByCategory();

    /**
     * Get count by code group
     */
    @Query("SELECT f.codeGroup, COUNT(f) FROM FreightCostCode f WHERE f.codeGroup IS NOT NULL GROUP BY f.codeGroup ORDER BY f.codeGroup")
    List<Object[]> countByCodeGroup();

    /**
     * Find freight charges code (100000)
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.code = '100000'")
    Optional<FreightCostCode> findFreightChargesCode();

    /**
     * Find basic freight code (101000)
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.code = '101000'")
    Optional<FreightCostCode> findBasicFreightCode();

    /**
     * Find all freight charges (100999)
     */
    @Query("SELECT f FROM FreightCostCode f WHERE f.code = '100999'")
    Optional<FreightCostCode> findAllFreightChargesCode();

    /**
     * Search by keyword using full-text search
     */
    @Query(value = "SELECT * FROM search_freight_cost_code(:searchTerm)", nativeQuery = true)
    List<FreightCostCode> searchByKeyword(@Param("searchTerm") String searchTerm);
}
