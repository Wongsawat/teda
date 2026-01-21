package com.wpanther.etax.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wpanther.etax.core.entity.MessageFunctionCode;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for UN/CEFACT Message Function Codes
 *
 * Provides query methods for message function classification:
 * - Transaction control (cancellation, change, replacement, etc.)
 * - Message status (original, duplicate, response, request)
 * - Acceptance/rejection
 * - Financial operations
 * - Schedule management
 */
@Repository
public interface MessageFunctionCodeRepository extends JpaRepository<MessageFunctionCode, String> {

    /**
     * Find message function code by code
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code = :code")
    Optional<MessageFunctionCode> findByCode(@Param("code") String code);

    /**
     * Find all modification functions (change, replace, delete, add, cancel)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.isModification = true ORDER BY m.code")
    List<MessageFunctionCode> findModifications();

    /**
     * Find all original transmission functions
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.isOriginal = true ORDER BY m.code")
    List<MessageFunctionCode> findOriginals();

    /**
     * Find all acceptance/rejection functions
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.isAcceptance = true ORDER BY m.code")
    List<MessageFunctionCode> findAcceptanceRelated();

    /**
     * Find message functions by category
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.category = :category ORDER BY m.code")
    List<MessageFunctionCode> findByCategory(@Param("category") String category);

    /**
     * Find all available categories
     */
    @Query("SELECT DISTINCT m.category FROM MessageFunctionCode m WHERE m.category IS NOT NULL ORDER BY m.category")
    List<String> findAllCategories();

    /**
     * Search message functions by name (case-insensitive)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE UPPER(m.name) LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY m.code")
    List<MessageFunctionCode> findByNameContaining(@Param("name") String name);

    /**
     * Search message functions by description (case-insensitive)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE UPPER(m.description) LIKE UPPER(CONCAT('%', :keyword, '%')) ORDER BY m.code")
    List<MessageFunctionCode> findByDescriptionContaining(@Param("keyword") String keyword);

    /**
     * Check if message function code exists
     */
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MessageFunctionCode m WHERE m.code = :code")
    boolean existsByCode(@Param("code") String code);

    /**
     * Find cancellation functions
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.category = 'Cancellation' ORDER BY m.code")
    List<MessageFunctionCode> findTransactionControl();

    /**
     * Find status functions
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.category IN ('Status', 'Processing Status') ORDER BY m.code")
    List<MessageFunctionCode> findMessageStatus();

    /**
     * Find financial functions (reversal of debit/credit)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code IN ('37', '38') ORDER BY m.code")
    List<MessageFunctionCode> findFinancial();

    /**
     * Find schedule-related functions
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.category = 'Scheduling' ORDER BY m.code")
    List<MessageFunctionCode> findSchedule();

    /**
     * Find cancellation functions (codes 1, 17, 18, 39)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code IN ('1', '17', '18', '39') ORDER BY m.code")
    List<MessageFunctionCode> findCancellations();

    /**
     * Find change/amendment functions
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code IN ('4', '19', '28', '30', '33', '34', '36', '52') ORDER BY m.code")
    List<MessageFunctionCode> findChanges();

    /**
     * Find replacement functions (codes 5, 20, 21)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code IN ('5', '20', '21') ORDER BY m.code")
    List<MessageFunctionCode> findReplacements();

    /**
     * Find confirmation functions (codes 6, 42)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code IN ('6', '42') ORDER BY m.code")
    List<MessageFunctionCode> findConfirmations();

    /**
     * Find most commonly used codes (9=Original, 4=Change, 5=Replace, 1=Cancel, 6=Confirm, etc.)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code IN ('9', '1', '4', '5', '6', '7', '8', '11', '13') ORDER BY " +
           "CASE m.code " +
           "WHEN '9' THEN 1 " +
           "WHEN '4' THEN 2 " +
           "WHEN '5' THEN 3 " +
           "WHEN '1' THEN 4 " +
           "WHEN '6' THEN 5 " +
           "WHEN '7' THEN 6 " +
           "WHEN '11' THEN 7 " +
           "WHEN '13' THEN 8 " +
           "WHEN '8' THEN 9 " +
           "END")
    List<MessageFunctionCode> findCommonCodes();

    /**
     * Find original function (code 9)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code = '9'")
    Optional<MessageFunctionCode> findOriginalFunction();

    /**
     * Find cancellation function (code 1)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code = '1'")
    Optional<MessageFunctionCode> findCancellationFunction();

    /**
     * Find change function (code 4)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code = '4'")
    Optional<MessageFunctionCode> findChangeFunction();

    /**
     * Find replace function (code 5)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code = '5'")
    Optional<MessageFunctionCode> findReplaceFunction();

    /**
     * Find confirmation function (code 6)
     */
    @Query("SELECT m FROM MessageFunctionCode m WHERE m.code = '6'")
    Optional<MessageFunctionCode> findConfirmationFunction();
}
