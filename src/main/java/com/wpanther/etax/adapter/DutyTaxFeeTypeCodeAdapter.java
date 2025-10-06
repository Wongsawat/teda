package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.DutyTaxFeeTypeCode;
import com.wpanther.etax.repository.DutyTaxFeeTypeCodeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed DutyTaxFeeTypeCode entities
 *
 * This adapter:
 * - Marshals DutyTaxFeeTypeCode entities to their code strings for XML output
 * - Unmarshals XML code strings to DutyTaxFeeTypeCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 53 UN/CEFACT duty tax fee type codes
 *
 * UN/CEFACT Code List: 65153
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A
 */
@Component
public class DutyTaxFeeTypeCodeAdapter extends XmlAdapter<String, DutyTaxFeeTypeCode> {

    private static final Logger log = LoggerFactory.getLogger(DutyTaxFeeTypeCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static DutyTaxFeeTypeCodeRepository repository;

    @Autowired
    public void setRepository(DutyTaxFeeTypeCodeRepository repository) {
        DutyTaxFeeTypeCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert DutyTaxFeeTypeCode entity to XML String (code)
     *
     * @param entity The DutyTaxFeeTypeCode entity
     * @return The duty tax fee type code string, or null if entity is null
     */
    @Override
    public String marshal(DutyTaxFeeTypeCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling DutyTaxFeeTypeCode: {} ({}) -> {}",
                  entity.getName(), entity.getCategory(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to DutyTaxFeeTypeCode entity from database
     *
     * @param code The duty tax fee type code from XML (e.g., VAT, GST, EXE)
     * @return DutyTaxFeeTypeCode entity from database, or placeholder if not found
     */
    @Override
    public DutyTaxFeeTypeCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim().toUpperCase();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Try to fetch from database
        return repository.findByCodeAndActive(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Duty tax fee type code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private DutyTaxFeeTypeCode createPlaceholder(String code) {
        DutyTaxFeeTypeCode placeholder = new DutyTaxFeeTypeCode(code);
        placeholder.setName("Unknown Duty Tax Fee Type: " + code);
        placeholder.setDescription("Placeholder for unknown duty tax fee type code");
        placeholder.setCategory("Unknown");
        return placeholder;
    }

    /**
     * Validate if a duty tax fee type code exists in the database
     *
     * @param code The duty tax fee type code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim().toUpperCase());
    }

    /**
     * Get duty tax fee type name from code
     *
     * @param code The duty tax fee type code
     * @return Duty tax fee type name, or null if not found
     */
    public static String getDutyTaxFeeName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::getName)
                .orElse(null);
    }

    /**
     * Get duty tax fee type category from code
     *
     * @param code The duty tax fee type code
     * @return Duty tax fee type category, or null if not found
     */
    public static String getDutyTaxFeeCategory(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::getCategory)
                .orElse(null);
    }

    /**
     * Check if a code is VAT-related
     *
     * @param code The duty tax fee type code
     * @return true if VAT
     */
    public static boolean isVAT(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isVat)
                .orElse(false);
    }

    /**
     * Check if a code is exempt
     *
     * @param code The duty tax fee type code
     * @return true if exempt
     */
    public static boolean isExempt(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isExempt)
                .orElse(false);
    }

    /**
     * Check if a code is a summary code
     *
     * @param code The duty tax fee type code
     * @return true if summary
     */
    public static boolean isSummary(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isSummary)
                .orElse(false);
    }

    /**
     * Check if a code is a customs duty
     *
     * @param code The duty tax fee type code
     * @return true if customs duty
     */
    public static boolean isCustomsDuty(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isCustomsDuty)
                .orElse(false);
    }

    /**
     * Check if a code is an excise tax
     *
     * @param code The duty tax fee type code
     * @return true if excise tax
     */
    public static boolean isExciseTax(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isExciseTax)
                .orElse(false);
    }

    /**
     * Check if a code is GST
     *
     * @param code The duty tax fee type code
     * @return true if GST
     */
    public static boolean isGST(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isGST)
                .orElse(false);
    }

    /**
     * Check if a code is a special tax
     *
     * @param code The duty tax fee type code
     * @return true if special tax
     */
    public static boolean isSpecialTax(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(DutyTaxFeeTypeCode::isSpecialTax)
                .orElse(false);
    }
}
