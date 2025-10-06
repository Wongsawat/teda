package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.ThaiMessageFunctionCode;
import com.wpanther.etax.repository.ThaiMessageFunctionCodeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import un.unece.uncefact.codelist.standard.etda.thaimessagefunctioncode._2560.ThaiMessageFunctionCodeContentType;

/**
 * JAXB adapter for converting between ThaiMessageFunctionCodeContentType enum
 * and database-backed ThaiMessageFunctionCode entity.
 *
 * This adapter enables seamless XML marshalling/unmarshalling while using
 * database-backed code list instead of static enums.
 */
@Component
public class ThaiMessageFunctionCodeAdapter extends XmlAdapter<ThaiMessageFunctionCodeContentType, ThaiMessageFunctionCode> {

    private static final Logger log = LoggerFactory.getLogger(ThaiMessageFunctionCodeAdapter.class);

    private static ThaiMessageFunctionCodeRepository repository;

    @Autowired
    public void setRepository(ThaiMessageFunctionCodeRepository repo) {
        ThaiMessageFunctionCodeAdapter.repository = repo;
    }

    /**
     * Convert database entity to JAXB enum for XML marshalling.
     *
     * @param entity The database entity
     * @return The JAXB enum value
     * @throws Exception if conversion fails
     */
    @Override
    public ThaiMessageFunctionCodeContentType marshal(ThaiMessageFunctionCode entity) throws Exception {
        if (entity == null) {
            return null;
        }

        String code = entity.getCode();
        if (code == null) {
            log.warn("ThaiMessageFunctionCode entity has null code");
            return null;
        }

        try {
            // Convert code to enum constant name (DBNG01 -> DBNG_01)
            String enumName = code.substring(0, 4) + "_" + code.substring(4);
            ThaiMessageFunctionCodeContentType enumValue = ThaiMessageFunctionCodeContentType.valueOf(enumName);
            return enumValue;
        } catch (IllegalArgumentException e) {
            log.warn("Thai message function code '{}' not found in JAXB enum, returning null", code);
            return null;
        }
    }

    /**
     * Convert JAXB enum to database entity for XML unmarshalling.
     *
     * @param enumValue The JAXB enum value
     * @return The database entity
     * @throws Exception if conversion fails
     */
    @Override
    public ThaiMessageFunctionCode unmarshal(ThaiMessageFunctionCodeContentType enumValue) throws Exception {
        if (enumValue == null) {
            return null;
        }

        // Get the actual code value from enum (DBNG_01 -> DBNG01)
        String code = enumValue.value();

        return repository.findByCode(code)
                .orElseGet(() -> createPlaceholder(code));
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
        if (code == null) {
            return null;
        }
        return repository.findByCode(code)
                .orElseGet(() -> createPlaceholder(code));
    }

    /**
     * Convert entity to code string.
     */
    public static String toCode(ThaiMessageFunctionCode entity) {
        return entity != null ? entity.getCode() : null;
    }

    /**
     * Convert enum to entity.
     */
    public static ThaiMessageFunctionCode fromEnum(ThaiMessageFunctionCodeContentType enumValue) {
        if (enumValue == null) {
            return null;
        }
        return toEntity(enumValue.value());
    }

    /**
     * Convert entity to enum.
     */
    public static ThaiMessageFunctionCodeContentType toEnum(ThaiMessageFunctionCode entity) {
        if (entity == null || entity.getCode() == null) {
            return null;
        }

        try {
            String code = entity.getCode();
            String enumName = code.substring(0, 4) + "_" + code.substring(4);
            return ThaiMessageFunctionCodeContentType.valueOf(enumName);
        } catch (IllegalArgumentException e) {
            log.warn("Thai message function code '{}' not found in JAXB enum", entity.getCode());
            return null;
        }
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
