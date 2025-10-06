package com.wpanther.etax.adapter;

import com.wpanther.etax.entity.AddressType;
import com.wpanther.etax.repository.AddressTypeRepository;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed AddressType entities
 *
 * This adapter:
 * - Marshals AddressType entities to their code strings for XML output
 * - Unmarshals XML code strings to AddressType entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 3 address type codes (1=Postal, 2=Fiscal, 3=Physical)
 *
 * UN/CEFACT Code List: 3131
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A
 */
@Component
public class AddressTypeAdapter extends XmlAdapter<String, AddressType> {

    private static final Logger log = LoggerFactory.getLogger(AddressTypeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static AddressTypeRepository repository;

    @Autowired
    public void setRepository(AddressTypeRepository repository) {
        AddressTypeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert AddressType entity to XML String (code)
     *
     * @param entity The AddressType entity
     * @return The address type code string, or null if entity is null
     */
    @Override
    public String marshal(AddressType entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling AddressType: {} -> {}", entity.getName(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to AddressType entity from database
     *
     * @param code The address type code from XML (1, 2, or 3)
     * @return AddressType entity from database, or placeholder if not found
     */
    @Override
    public AddressType unmarshal(String code) throws Exception {
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
                    log.warn("Address type code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private AddressType createPlaceholder(String code) {
        AddressType placeholder = new AddressType(code);
        placeholder.setName("Unknown Address Type: " + code);
        placeholder.setDescription("Placeholder for unknown address type code");
        return placeholder;
    }

    /**
     * Validate if an address type code exists in the database
     *
     * @param code The address type code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get address type name from code
     *
     * @param code The address type code
     * @return Name, or null if not found
     */
    public static String getName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(AddressType::getName)
                .orElse(null);
    }

    /**
     * Get address type description from code
     *
     * @param code The address type code
     * @return Description, or null if not found
     */
    public static String getDescription(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(AddressType::getDescription)
                .orElse(null);
    }

    /**
     * Check if code is postal address (1)
     *
     * @param code The address type code
     * @return true if postal address
     */
    public static boolean isPostalAddress(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(AddressType::isPostalAddress)
                .orElse(false);
    }

    /**
     * Check if code is fiscal address (2)
     *
     * @param code The address type code
     * @return true if fiscal address
     */
    public static boolean isFiscalAddress(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(AddressType::isFiscalAddress)
                .orElse(false);
    }

    /**
     * Check if code is physical address (3)
     *
     * @param code The address type code
     * @return true if physical address
     */
    public static boolean isPhysicalAddress(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(AddressType::isPhysicalAddress)
                .orElse(false);
    }
}
