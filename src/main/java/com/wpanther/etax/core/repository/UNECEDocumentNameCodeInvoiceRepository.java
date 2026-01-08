package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.entity.UNECEDocumentNameCodeInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UNECEDocumentNameCodeInvoiceRepository extends JpaRepository<UNECEDocumentNameCodeInvoice, String> {

    // Find by code
    Optional<UNECEDocumentNameCodeInvoice> findByCode(String code);

    // Find by category
    List<UNECEDocumentNameCodeInvoice> findByCategory(String category);

    // Find invoices
    @Query("SELECT d FROM UNECEDocumentNameCodeInvoice d WHERE d.category = 'Invoice'")
    List<UNECEDocumentNameCodeInvoice> findInvoices();

    // Find credit notes
    @Query("SELECT d FROM UNECEDocumentNameCodeInvoice d WHERE d.isCredit = true OR d.category = 'Credit Note'")
    List<UNECEDocumentNameCodeInvoice> findCreditNotes();

    // Find debit notes
    @Query("SELECT d FROM UNECEDocumentNameCodeInvoice d WHERE d.isDebit = true OR d.category = 'Debit Note'")
    List<UNECEDocumentNameCodeInvoice> findDebitNotes();

    // Find special documents
    @Query("SELECT d FROM UNECEDocumentNameCodeInvoice d WHERE d.category = 'Special'")
    List<UNECEDocumentNameCodeInvoice> findSpecialDocuments();

    // Find documents requiring payment
    List<UNECEDocumentNameCodeInvoice> findByRequiresPaymentTrue();

    // Find documents not requiring payment
    List<UNECEDocumentNameCodeInvoice> findByRequiresPaymentFalse();

    // Find common codes (380, 381, 383, 325, 386)
    @Query("SELECT d FROM UNECEDocumentNameCodeInvoice d WHERE d.code IN ('380', '381', '383', '325', '386')")
    List<UNECEDocumentNameCodeInvoice> findCommonCodes();

    // Find by name containing
    List<UNECEDocumentNameCodeInvoice> findByNameContainingIgnoreCase(String name);

    // Count by category
    @Query("SELECT d.category, COUNT(d) FROM UNECEDocumentNameCodeInvoice d GROUP BY d.category")
    List<Object[]> countByCategory();

    // Specific document finders
    default Optional<UNECEDocumentNameCodeInvoice> findCommercialInvoice() {
        return findByCode("380");
    }

    default Optional<UNECEDocumentNameCodeInvoice> findCreditNote() {
        return findByCode("381");
    }

    default Optional<UNECEDocumentNameCodeInvoice> findDebitNote() {
        return findByCode("383");
    }

    default Optional<UNECEDocumentNameCodeInvoice> findProformaInvoice() {
        return findByCode("325");
    }

    default Optional<UNECEDocumentNameCodeInvoice> findPrepaymentInvoice() {
        return findByCode("386");
    }
}
