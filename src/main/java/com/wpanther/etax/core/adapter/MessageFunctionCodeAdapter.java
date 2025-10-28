package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.repository.MessageFunctionCodeRepository;
import com.wpanther.etax.core.entity.MessageFunctionCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed MessageFunctionCode entities
 *
 * This adapter:
 * - Marshals MessageFunctionCode entities to their code strings for XML output
 * - Unmarshals XML code strings to MessageFunctionCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 65 UN/CEFACT message function codes (1-65)
 *
 * UN/CEFACT Code List: 61225
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A
 */
@Component
public class MessageFunctionCodeAdapter extends XmlAdapter<String, MessageFunctionCode> {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctionCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static MessageFunctionCodeRepository repository;

    @Autowired
    public void setRepository(MessageFunctionCodeRepository repository) {
        MessageFunctionCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert MessageFunctionCode entity to XML String (code)
     *
     * @param entity The MessageFunctionCode entity
     * @return The message function code string, or null if entity is null
     */
    @Override
    public String marshal(MessageFunctionCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling MessageFunctionCode: {} ({}) -> {}",
                  entity.getName(), entity.getCategory(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to MessageFunctionCode entity from database
     *
     * @param code The message function code from XML (e.g., 1, 9, 4, 5)
     * @return MessageFunctionCode entity from database, or placeholder if not found
     */
    @Override
    public MessageFunctionCode unmarshal(String code) throws Exception {
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
                    log.warn("Message function code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private MessageFunctionCode createPlaceholder(String code) {
        MessageFunctionCode placeholder = new MessageFunctionCode(code);
        placeholder.setName("Unknown Message Function: " + code);
        placeholder.setDescription("Placeholder for unknown message function code");
        placeholder.setCategory("Unknown");
        return placeholder;
    }

    /**
     * Validate if a message function code exists in the database
     *
     * @param code The message function code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim());
    }

    /**
     * Get message function name from code
     *
     * @param code The message function code
     * @return Message function name, or null if not found
     */
    public static String getMessageFunctionName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::getName)
                .orElse(null);
    }

    /**
     * Get message function category from code
     *
     * @param code The message function code
     * @return Message function category, or null if not found
     */
    public static String getMessageFunctionCategory(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::getCategory)
                .orElse(null);
    }

    /**
     * Check if a code is a modification function
     *
     * @param code The message function code
     * @return true if modification
     */
    public static boolean isModification(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isModification)
                .orElse(false);
    }

    /**
     * Check if a code is an original transmission
     *
     * @param code The message function code
     * @return true if original (code 9)
     */
    public static boolean isOriginal(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isOriginal)
                .orElse(false);
    }

    /**
     * Check if a code is acceptance/rejection related
     *
     * @param code The message function code
     * @return true if acceptance/rejection
     */
    public static boolean isAcceptance(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isAcceptance)
                .orElse(false);
    }

    /**
     * Check if a code is a cancellation
     *
     * @param code The message function code
     * @return true if cancellation (codes 1, 17, 18, 39)
     */
    public static boolean isCancellation(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isCancellation)
                .orElse(false);
    }

    /**
     * Check if a code is a change/amendment
     *
     * @param code The message function code
     * @return true if change
     */
    public static boolean isChange(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isChange)
                .orElse(false);
    }

    /**
     * Check if a code is a replacement
     *
     * @param code The message function code
     * @return true if replacement (codes 5, 20, 21)
     */
    public static boolean isReplacement(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isReplacement)
                .orElse(false);
    }

    /**
     * Check if a code is a confirmation
     *
     * @param code The message function code
     * @return true if confirmation (codes 6, 42)
     */
    public static boolean isConfirmation(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim())
                .map(MessageFunctionCode::isConfirmation)
                .orElse(false);
    }
}
