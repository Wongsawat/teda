package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.ThaiMessageFunctionCodeRepository;
import com.wpanther.etax.core.entity.ThaiMessageFunctionCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB adapter for converting between XML String values and database-backed ThaiMessageFunctionCode entities.
 *
 * This adapter:
 * - Marshals ThaiMessageFunctionCode entities to their code strings for XML output
 * - Unmarshals XML code strings to ThaiMessageFunctionCode entities from database
 * - Maintains full JAXB namespace compatibility with ETDA e-Tax Invoice schema
 * - Handles missing codes gracefully by creating placeholder entities
 */
@Component
public class ThaiMessageFunctionCodeAdapter extends XmlAdapter<String, ThaiMessageFunctionCode> {

    private static final Logger log = LoggerFactory.getLogger(ThaiMessageFunctionCodeAdapter.class);

    private static ThaiMessageFunctionCodeRepository repository;

    @Autowired
    public void setRepository(ThaiMessageFunctionCodeRepository repo) {
        ThaiMessageFunctionCodeAdapter.repository = repo;
    }

    /**
     * Marshal: Convert ThaiMessageFunctionCode entity to XML String (message function code)
     *
     * @param entity The ThaiMessageFunctionCode entity
     * @return The message function code string (DBNG01, TIVC01, RCTC01, etc.), or null if entity is null
     */
    @Override
    public String marshal(ThaiMessageFunctionCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling ThaiMessageFunctionCode: {} ({}) -> {}",
                entity.getDescriptionEn(), entity.getDescriptionTh(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (message function code) to ThaiMessageFunctionCode entity from database
     *
     * @param code The message function code from XML (DBNG01, TIVC01, RCTC01, etc.)
     * @return ThaiMessageFunctionCode entity from database, or placeholder if not found
     */
    @Override
    public ThaiMessageFunctionCode unmarshal(String code) throws Exception {
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
                    log.warn("Thai message function code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database.
     * This allows processing to continue even if database is incomplete.
     *
     * @param code The message function code
     * @return A placeholder entity
     */
    private static ThaiMessageFunctionCode createPlaceholder(String code) {
        log.warn("Thai message function code '{}' not found in database, creating placeholder", code);

        ThaiMessageFunctionCode placeholder = new ThaiMessageFunctionCode();
        placeholder.setCode(code);
        placeholder.setDescriptionEn("Unknown code: " + code);
        placeholder.setDescriptionTh("รหัสที่ไม่รู้จัก: " + code);
        placeholder.setDocumentType(determineDocumentType(code));
        placeholder.setCategory(determineCategory(code));
        placeholder.setActive(false);

        return placeholder;
    }

    /**
     * Determine document type from code prefix.
     */
    private static String determineDocumentType(String code) {
        if (code == null || code.length() < 4) {
            return "Unknown";
        }

        String prefix = code.substring(0, 4);
        return switch (prefix) {
            case "DBNG", "DBNS" -> "DebitNote";
            case "CDNG", "CDNS" -> "CreditNote";
            case "TIVC" -> "TaxInvoice";
            case "RCTC" -> "Receipt";
            default -> "Unknown";
        };
    }

    /**
     * Determine category from code prefix.
     */
    private static String determineCategory(String code) {
        if (code == null || code.length() < 4) {
            return "Unknown";
        }

        String prefix = code.substring(0, 4);
        return switch (prefix) {
            case "DBNG", "CDNG" -> "Goods";
            case "DBNS", "CDNS" -> "Service";
            case "TIVC" -> "Invoice";
            case "RCTC" -> "Receipt";
            default -> "Unknown";
        };
    }

    // Static helper methods for common conversions

    /**
     * Convert code string to entity.
     */
    public static ThaiMessageFunctionCode toEntity(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        if (repository == null) {
            return createPlaceholder(code.trim());
        }
        return repository.findByCode(code.trim())
                .orElseGet(() -> createPlaceholder(code.trim()));
    }

    /**
     * Convert entity to code string.
     */
    public static String toCode(ThaiMessageFunctionCode entity) {
        return entity != null ? entity.getCode() : null;
    }

    /**
     * Validate if a message function code exists in the database.
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim()).isPresent();
    }

    /**
     * Get English description from code.
     */
    public static String getEnglishDescription(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ThaiMessageFunctionCode::getDescriptionEn)
                .orElse(null);
    }

    /**
     * Get Thai description from code.
     */
    public static String getThaiDescription(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(ThaiMessageFunctionCode::getDescriptionTh)
                .orElse(null);
    }

    // Document type check helpers

    public static boolean isDebitNote(String code) {
        return code != null && (code.startsWith("DBNG") || code.startsWith("DBNS"));
    }

    public static boolean isCreditNote(String code) {
        return code != null && (code.startsWith("CDNG") || code.startsWith("CDNS"));
    }

    public static boolean isTaxInvoice(String code) {
        return code != null && code.startsWith("TIVC");
    }

    public static boolean isReceipt(String code) {
        return code != null && code.startsWith("RCTC");
    }

    public static boolean isGoods(String code) {
        return code != null && (code.startsWith("DBNG") || code.startsWith("CDNG"));
    }

    public static boolean isServices(String code) {
        return code != null && (code.startsWith("DBNS") || code.startsWith("CDNS"));
    }

    // Function suffix check helpers

    public static boolean isOriginal(String code) {
        return code != null && code.endsWith("01");
    }

    public static boolean isReplacement(String code) {
        return code != null && code.endsWith("02");
    }

    public static boolean isCancellation(String code) {
        return code != null && code.endsWith("03");
    }

    public static boolean isCopy(String code) {
        return code != null && code.endsWith("04");
    }

    public static boolean isAddition(String code) {
        return code != null && code.endsWith("05");
    }

    public static boolean isOther(String code) {
        return code != null && code.endsWith("99");
    }
}
