package com.wpanther.etax.core.adapter.common;

import com.wpanther.etax.core.repository.UNECEReferenceTypeCodeRepository;
import com.wpanther.etax.core.entity.UNECEReferenceTypeCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed UNECEReferenceTypeCode entities
 *
 * This adapter:
 * - Marshals UNECEReferenceTypeCode entities to their code strings for XML output
 * - Unmarshals XML code strings to UNECEReferenceTypeCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports both standard UN/CEFACT codes (AAA-ZZZ) and Thai ETDA extensions (80, 81, 380, 388, T01-T07)
 */
@Component
public class UNECEReferenceTypeCodeAdapter extends XmlAdapter<String, UNECEReferenceTypeCode> {

    private static final Logger log = LoggerFactory.getLogger(UNECEReferenceTypeCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static UNECEReferenceTypeCodeRepository repository;

    @Autowired
    public void setRepository(UNECEReferenceTypeCodeRepository repository) {
        UNECEReferenceTypeCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert UNECEReferenceTypeCode entity to XML String (reference type code)
     *
     * @param entity The UNECEReferenceTypeCode entity
     * @return The reference type code string, or null if entity is null
     */
    @Override
    public String marshal(UNECEReferenceTypeCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        log.debug("Marshalling UNECEReferenceTypeCode: {} -> {}", entity.getName(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (reference type code) to UNECEReferenceTypeCode entity from database
     *
     * @param code The reference type code from XML (e.g., AAA, 380, T01)
     * @return UNECEReferenceTypeCode entity from database, or placeholder if not found
     */
    @Override
    public UNECEReferenceTypeCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String upperCode = code.trim().toUpperCase();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", upperCode);
            return createPlaceholder(upperCode);
        }

        // Try to fetch from database
        return repository.findByCodeAndActive(upperCode)
                .orElseGet(() -> {
                    log.warn("Reference type code '{}' not found in database, creating placeholder", upperCode);
                    return createPlaceholder(upperCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private UNECEReferenceTypeCode createPlaceholder(String code) {
        UNECEReferenceTypeCode placeholder = new UNECEReferenceTypeCode(code);
        placeholder.setName("Unknown Reference Type: " + code);
        placeholder.setDescription("Placeholder for unknown reference type code");
        placeholder.setActive(false);
        return placeholder;
    }

    /**
     * Validate if a reference type code exists in the database
     *
     * @param code The reference type code
     * @return true if code exists and is active
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCodeAndActive(code.trim().toUpperCase());
    }

    /**
     * Get reference type name from code
     *
     * @param code The reference type code
     * @return Reference type name, or null if not found
     */
    public static String getReferenceTypeName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(UNECEReferenceTypeCode::getName)
                .orElse(null);
    }

    /**
     * Check if a code is a Thai ETDA extension
     *
     * @param code The reference type code
     * @return true if code is an ETDA extension
     */
    public static boolean isEtdaExtension(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCodeAndActive(code.trim().toUpperCase())
                .map(UNECEReferenceTypeCode::getEtdaExtension)
                .orElse(false);
    }
}
