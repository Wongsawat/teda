package com.wpanther.etax.repository;

import com.wpanther.etax.entity.DeliveryTermsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Delivery Terms Codes
 *
 * Provides query methods for INCOTERMS 2010:
 * - Group E: Departure (minimum seller obligation)
 * - Group F: Main carriage unpaid (low seller obligation)
 * - Group C: Main carriage paid (medium seller obligation)
 * - Group D: Arrival (high to maximum seller obligation)
 */
@Repository
public interface DeliveryTermsCodeRepository extends JpaRepository<DeliveryTermsCode, String> {

    /**
     * Find delivery terms code by code
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = UPPER(:code)")
    Optional<DeliveryTermsCode> findByCode(@Param("code") String code);

    /**
     * Find all INCOTERMS codes
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.isIncoterm = true ORDER BY " +
           "CASE d.incotermGroup WHEN 'E' THEN 1 WHEN 'F' THEN 2 WHEN 'C' THEN 3 WHEN 'D' THEN 4 END, d.code")
    List<DeliveryTermsCode> findAllIncoterms();

    /**
     * Find non-INCOTERMS codes (custom delivery arrangements)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.isIncoterm = false ORDER BY d.code")
    List<DeliveryTermsCode> findNonIncoterms();

    /**
     * Find by INCOTERMS group
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.incotermGroup = :group ORDER BY d.code")
    List<DeliveryTermsCode> findByIncotermGroup(@Param("group") String group);

    /**
     * Find Group E (Departure) - EXW
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.incotermGroup = 'E' ORDER BY d.code")
    List<DeliveryTermsCode> findGroupE();

    /**
     * Find Group F (Main carriage unpaid) - FCA, FAS, FOB
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.incotermGroup = 'F' ORDER BY d.code")
    List<DeliveryTermsCode> findGroupF();

    /**
     * Find Group C (Main carriage paid) - CFR, CIF, CPT, CIP
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.incotermGroup = 'C' ORDER BY d.code")
    List<DeliveryTermsCode> findGroupC();

    /**
     * Find Group D (Arrival) - DAP, DAT, DPU, DDP
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.incotermGroup = 'D' ORDER BY d.code")
    List<DeliveryTermsCode> findGroupD();

    /**
     * Find by seller obligation level
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.sellerObligation = :obligation ORDER BY d.code")
    List<DeliveryTermsCode> findBySellerObligation(@Param("obligation") String obligation);

    /**
     * Find terms with minimum seller obligation (EXW)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.sellerObligation = 'Minimum'")
    Optional<DeliveryTermsCode> findMinimumSellerObligation();

    /**
     * Find terms with maximum seller obligation (DDP)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.sellerObligation = 'Maximum'")
    Optional<DeliveryTermsCode> findMaximumSellerObligation();

    /**
     * Find terms that include insurance (CIF, CIP)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code IN ('CIF', 'CIP') ORDER BY d.code")
    List<DeliveryTermsCode> findWithInsurance();

    /**
     * Find terms that include freight payment (CFR, CIF, CPT, CIP)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code IN ('CFR', 'CIF', 'CPT', 'CIP') ORDER BY d.code")
    List<DeliveryTermsCode> findWithFreight();

    /**
     * Find sea/inland waterway transport only (FAS, FOB, CFR, CIF)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code IN ('FAS', 'FOB', 'CFR', 'CIF') ORDER BY d.code")
    List<DeliveryTermsCode> findSeaTransportOnly();

    /**
     * Find any mode of transport (EXW, FCA, CPT, CIP, DAP, DPU, DDP)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code IN ('EXW', 'FCA', 'CPT', 'CIP', 'DAP', 'DPU', 'DDP') ORDER BY d.code")
    List<DeliveryTermsCode> findAnyTransportMode();

    /**
     * Search by name (case-insensitive)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE UPPER(d.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY d.code")
    List<DeliveryTermsCode> findByNameContaining(@Param("name") String name);

    /**
     * Check if delivery terms code exists
     */
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DeliveryTermsCode d WHERE d.code = UPPER(:code)")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find most commonly used INCOTERMS for e-Tax Invoice
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code IN ('EXW', 'FOB', 'CIF', 'DDP', 'FCA') ORDER BY " +
           "CASE d.code WHEN 'EXW' THEN 1 WHEN 'FOB' THEN 2 WHEN 'CIF' THEN 3 WHEN 'DDP' THEN 4 WHEN 'FCA' THEN 5 END")
    List<DeliveryTermsCode> findCommonIncoterms();

    /**
     * Find EXW (Ex Works)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'EXW'")
    Optional<DeliveryTermsCode> findEXW();

    /**
     * Find FOB (Free On Board)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'FOB'")
    Optional<DeliveryTermsCode> findFOB();

    /**
     * Find CIF (Cost, Insurance and Freight)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'CIF'")
    Optional<DeliveryTermsCode> findCIF();

    /**
     * Find DDP (Delivered Duty Paid)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'DDP'")
    Optional<DeliveryTermsCode> findDDP();

    /**
     * Find FCA (Free Carrier)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'FCA'")
    Optional<DeliveryTermsCode> findFCA();

    /**
     * Find CFR (Cost and Freight)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'CFR'")
    Optional<DeliveryTermsCode> findCFR();

    /**
     * Find DAP (Delivered At Place)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'DAP'")
    Optional<DeliveryTermsCode> findDAP();

    /**
     * Find DPU (Delivered At Place Unloaded)
     */
    @Query("SELECT d FROM DeliveryTermsCode d WHERE d.code = 'DPU'")
    Optional<DeliveryTermsCode> findDPU();

    /**
     * Get all INCOTERMS groups
     */
    @Query("SELECT DISTINCT d.incotermGroup FROM DeliveryTermsCode d WHERE d.incotermGroup IS NOT NULL ORDER BY d.incotermGroup")
    List<String> findAllIncotermGroups();

    /**
     * Get all seller obligation levels
     */
    @Query("SELECT DISTINCT d.sellerObligation FROM DeliveryTermsCode d WHERE d.sellerObligation IS NOT NULL ORDER BY " +
           "CASE d.sellerObligation WHEN 'Minimum' THEN 1 WHEN 'Low' THEN 2 WHEN 'Medium' THEN 3 WHEN 'High' THEN 4 WHEN 'Maximum' THEN 5 END")
    List<String> findAllSellerObligationLevels();
}
