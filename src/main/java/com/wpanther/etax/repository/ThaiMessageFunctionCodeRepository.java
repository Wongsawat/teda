package com.wpanther.etax.repository;

import com.wpanther.etax.entity.ThaiMessageFunctionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Thai Message Function Code entity.
 * Provides data access methods for Thai e-Tax Invoice message function codes.
 */
@Repository
public interface ThaiMessageFunctionCodeRepository extends JpaRepository<ThaiMessageFunctionCode, String> {

    /**
     * Find a message function code by its code value.
     */
    Optional<ThaiMessageFunctionCode> findByCode(String code);

    /**
     * Find all active message function codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findAllActive();

    /**
     * Find message function codes by document type.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.documentType = :documentType AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findByDocumentType(@Param("documentType") String documentType);

    /**
     * Find message function codes by category.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.category = :category AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findByCategory(@Param("category") String category);

    /**
     * Find message function codes by document type and category.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.documentType = :documentType AND c.category = :category AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findByDocumentTypeAndCategory(
            @Param("documentType") String documentType,
            @Param("category") String category);

    // Debit Note queries

    /**
     * Find all debit note codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.documentType = 'DebitNote' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findDebitNoteCodes();

    /**
     * Find debit note - goods codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE 'DBNG%' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findDebitNoteGoodsCodes();

    /**
     * Find debit note - services codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE 'DBNS%' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findDebitNoteServicesCodes();

    /**
     * Find original debit note - goods code (DBNG01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'DBNG01'")
    Optional<ThaiMessageFunctionCode> findDebitNoteGoodsOriginal();

    /**
     * Find original debit note - services code (DBNS01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'DBNS01'")
    Optional<ThaiMessageFunctionCode> findDebitNoteServicesOriginal();

    // Credit Note queries

    /**
     * Find all credit note codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.documentType = 'CreditNote' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findCreditNoteCodes();

    /**
     * Find credit note - goods codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE 'CDNG%' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findCreditNoteGoodsCodes();

    /**
     * Find credit note - services codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE 'CDNS%' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findCreditNoteServicesCodes();

    /**
     * Find original credit note - goods code (CDNG01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'CDNG01'")
    Optional<ThaiMessageFunctionCode> findCreditNoteGoodsOriginal();

    /**
     * Find original credit note - services code (CDNS01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'CDNS01'")
    Optional<ThaiMessageFunctionCode> findCreditNoteServicesOriginal();

    // Tax Invoice queries

    /**
     * Find all tax invoice codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.documentType = 'TaxInvoice' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findTaxInvoiceCodes();

    /**
     * Find original tax invoice code (TIVC01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'TIVC01'")
    Optional<ThaiMessageFunctionCode> findTaxInvoiceOriginal();

    /**
     * Find replacement tax invoice code (TIVC02).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'TIVC02'")
    Optional<ThaiMessageFunctionCode> findTaxInvoiceReplacement();

    // Receipt queries

    /**
     * Find all receipt codes.
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.documentType = 'Receipt' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findReceiptCodes();

    /**
     * Find original receipt code (RCTC01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'RCTC01'")
    Optional<ThaiMessageFunctionCode> findReceiptOriginal();

    /**
     * Find replacement receipt code (RCTC02).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'RCTC02'")
    Optional<ThaiMessageFunctionCode> findReceiptReplacement();

    /**
     * Find copy receipt code (RCTC03).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'RCTC03'")
    Optional<ThaiMessageFunctionCode> findReceiptCopy();

    /**
     * Find cancellation receipt code (RCTC04).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code = 'RCTC04'")
    Optional<ThaiMessageFunctionCode> findReceiptCancellation();

    // Function type queries

    /**
     * Find all original document codes (ending with 01).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE '%01' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findOriginalDocumentCodes();

    /**
     * Find all replacement document codes (ending with 02).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE '%02' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findReplacementDocumentCodes();

    /**
     * Find all cancellation document codes (ending with 03).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE '%03' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findCancellationDocumentCodes();

    /**
     * Find all copy document codes (ending with 04).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE '%04' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findCopyDocumentCodes();

    /**
     * Find all addition document codes (ending with 05).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE '%05' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findAdditionDocumentCodes();

    /**
     * Find all "other" document codes (ending with 99).
     */
    @Query("SELECT c FROM ThaiMessageFunctionCode c WHERE c.code LIKE '%99' AND c.active = true ORDER BY c.code")
    List<ThaiMessageFunctionCode> findOtherDocumentCodes();
}
