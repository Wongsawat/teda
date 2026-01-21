package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.entity.UNECEDocumentNameCodeInvoice;
import com.wpanther.etax.core.repository.UNECEDocumentNameCodeInvoiceRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed UNECEDocumentNameCodeInvoice entities
 *
 * This adapter:
 * - Marshals UNECEDocumentNameCodeInvoice entities to their code strings for XML output
 * - Unmarshals XML code strings to UNECEDocumentNameCodeInvoice entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 17 UN/CEFACT document name codes
 *
 * UN/CEFACT: Code List 1001 (DocumentNameCode), Version D14A
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A
 */
@Component
public class UNECEDocumentNameCodeInvoiceAdapter extends XmlAdapter<String, UNECEDocumentNameCodeInvoice> {

    private static final Logger log = LoggerFactory.getLogger(UNECEDocumentNameCodeInvoiceAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static UNECEDocumentNameCodeInvoiceRepository repository;

    @Autowired
    public void setRepository(UNECEDocumentNameCodeInvoiceRepository repository) {
        UNECEDocumentNameCodeInvoiceAdapter.repository = repository;
    }

    @Override
    public String marshal(UNECEDocumentNameCodeInvoice entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        log.debug("Marshalling DocumentNameCodeInvoice: {} -> {}", entity.getName(), code);
        return code;
    }

    @Override
    public UNECEDocumentNameCodeInvoice unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Try to fetch from database
        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Document name code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    private static UNECEDocumentNameCodeInvoice createPlaceholder(String code) {
        UNECEDocumentNameCodeInvoice placeholder = new UNECEDocumentNameCodeInvoice(code);
        placeholder.setName("Unknown Document: " + code);
        placeholder.setDescription("Unknown document name code");
        placeholder.setRequiresPayment(true);
        return placeholder;
    }

    // Static helper methods
    public static boolean isValid(String code) {
        if (repository == null || code == null) {
            return false;
        }
        return repository.existsById(code);
    }

    public static String getDocumentName(String code) {
        if (repository == null || code == null) {
            return null;
        }
        return repository.findByCode(code)
                .map(UNECEDocumentNameCodeInvoice::getName)
                .orElse(null);
    }

    public static String getDocumentDescription(String code) {
        if (repository == null || code == null) {
            return null;
        }
        return repository.findByCode(code)
                .map(UNECEDocumentNameCodeInvoice::getDescription)
                .orElse(null);
    }

    public static boolean isCreditNote(String code) {
        if (repository == null || code == null) {
            return false;
        }
        return repository.findByCode(code)
                .map(UNECEDocumentNameCodeInvoice::isCreditNote)
                .orElse(false);
    }

    public static boolean isDebitNote(String code) {
        if (repository == null || code == null) {
            return false;
        }
        return repository.findByCode(code)
                .map(UNECEDocumentNameCodeInvoice::isDebitNote)
                .orElse(false);
    }

    public static boolean requiresPayment(String code) {
        if (repository == null || code == null) {
            return true; // Default to true for safety
        }
        return repository.findByCode(code)
                .map(doc -> Boolean.TRUE.equals(doc.getRequiresPayment()))
                .orElse(true);
    }
}
