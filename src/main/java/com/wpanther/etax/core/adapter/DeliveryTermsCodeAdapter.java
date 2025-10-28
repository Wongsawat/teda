package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.repository.DeliveryTermsCodeRepository;
import com.wpanther.etax.core.entity.DeliveryTermsCode;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JAXB XmlAdapter to convert between XML String values and database-backed DeliveryTermsCode entities
 *
 * This adapter:
 * - Marshals DeliveryTermsCode entities to their code strings for XML output
 * - Unmarshals XML code strings to DeliveryTermsCode entities from database
 * - Maintains full JAXB namespace compatibility
 * - Handles missing codes gracefully by creating placeholder entities
 * - Supports all 14 delivery terms codes (2 custom + 12 INCOTERMS 2010)
 *
 * UN/CEFACT Code List: 64053
 * Namespace: urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010
 */
@Component
public class DeliveryTermsCodeAdapter extends XmlAdapter<String, DeliveryTermsCode> {

    private static final Logger log = LoggerFactory.getLogger(DeliveryTermsCodeAdapter.class);

    // Static reference to allow JAXB to use Spring-managed repository
    private static DeliveryTermsCodeRepository repository;

    @Autowired
    public void setRepository(DeliveryTermsCodeRepository repository) {
        DeliveryTermsCodeAdapter.repository = repository;
    }

    /**
     * Marshal: Convert DeliveryTermsCode entity to XML String (code)
     *
     * @param entity The DeliveryTermsCode entity
     * @return The delivery terms code string, or null if entity is null
     */
    @Override
    public String marshal(DeliveryTermsCode entity) throws Exception {
        if (entity == null) {
            return null;
        }
        String code = entity.getCode();
        log.debug("Marshalling DeliveryTermsCode: {} (Group: {}, Obligation: {}) -> {}",
                  entity.getName(), entity.getIncotermGroup(), entity.getSellerObligation(), code);
        return code;
    }

    /**
     * Unmarshal: Convert XML String (code) to DeliveryTermsCode entity from database
     *
     * @param code The delivery terms code from XML (e.g., EXW, FOB, CIF, etc.)
     * @return DeliveryTermsCode entity from database, or placeholder if not found
     */
    @Override
    public DeliveryTermsCode unmarshal(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        String trimmedCode = code.trim().toUpperCase();

        if (repository == null) {
            log.warn("Repository not initialized, creating placeholder for code: {}", trimmedCode);
            return createPlaceholder(trimmedCode);
        }

        // Try to fetch from database
        return repository.findByCode(trimmedCode)
                .orElseGet(() -> {
                    log.warn("Delivery terms code '{}' not found in database, creating placeholder", trimmedCode);
                    return createPlaceholder(trimmedCode);
                });
    }

    /**
     * Create a placeholder entity for codes not found in database
     * This allows XML unmarshalling to continue even with unknown codes
     */
    private DeliveryTermsCode createPlaceholder(String code) {
        DeliveryTermsCode placeholder = new DeliveryTermsCode(code);
        placeholder.setName("Unknown Delivery Terms: " + code);
        placeholder.setDescription("Placeholder for unknown delivery terms code");
        placeholder.setIncoterm(false);
        return placeholder;
    }

    /**
     * Validate if a delivery terms code exists in the database
     *
     * @param code The delivery terms code
     * @return true if code exists
     */
    public static boolean isValid(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.existsByCode(code.trim().toUpperCase());
    }

    /**
     * Get delivery terms name from code
     *
     * @param code The delivery terms code
     * @return Delivery terms name, or null if not found
     */
    public static String getDeliveryTermsName(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::getName)
                .orElse(null);
    }

    /**
     * Get INCOTERMS group from code
     *
     * @param code The delivery terms code
     * @return INCOTERMS group (E/F/C/D), or null if not found
     */
    public static String getIncotermGroup(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::getIncotermGroup)
                .orElse(null);
    }

    /**
     * Get seller obligation level from code
     *
     * @param code The delivery terms code
     * @return Seller obligation level, or null if not found
     */
    public static String getSellerObligation(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return null;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::getSellerObligation)
                .orElse(null);
    }

    /**
     * Check if code is an official INCOTERM
     *
     * @param code The delivery terms code
     * @return true if official INCOTERM
     */
    public static boolean isIncoterm(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isIncoterm)
                .orElse(false);
    }

    /**
     * Check if code is Group E (Departure)
     *
     * @param code The delivery terms code
     * @return true if Group E
     */
    public static boolean isGroupE(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isGroupE)
                .orElse(false);
    }

    /**
     * Check if code is Group F (Main carriage unpaid)
     *
     * @param code The delivery terms code
     * @return true if Group F
     */
    public static boolean isGroupF(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isGroupF)
                .orElse(false);
    }

    /**
     * Check if code is Group C (Main carriage paid)
     *
     * @param code The delivery terms code
     * @return true if Group C
     */
    public static boolean isGroupC(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isGroupC)
                .orElse(false);
    }

    /**
     * Check if code is Group D (Arrival)
     *
     * @param code The delivery terms code
     * @return true if Group D
     */
    public static boolean isGroupD(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isGroupD)
                .orElse(false);
    }

    /**
     * Check if code includes insurance (CIF, CIP)
     *
     * @param code The delivery terms code
     * @return true if includes insurance
     */
    public static boolean includesInsurance(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::includesInsurance)
                .orElse(false);
    }

    /**
     * Check if code includes freight payment (CFR, CIF, CPT, CIP)
     *
     * @param code The delivery terms code
     * @return true if includes freight
     */
    public static boolean includesFreight(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::includesFreight)
                .orElse(false);
    }

    /**
     * Check if code is for sea transport only
     *
     * @param code The delivery terms code
     * @return true if sea transport only
     */
    public static boolean isSeaTransportOnly(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isSeaTransportOnly)
                .orElse(false);
    }

    /**
     * Check if code is for any mode of transport
     *
     * @param code The delivery terms code
     * @return true if any transport mode
     */
    public static boolean isAnyTransportMode(String code) {
        if (repository == null || code == null || code.trim().isEmpty()) {
            return false;
        }
        return repository.findByCode(code.trim().toUpperCase())
                .map(DeliveryTermsCode::isAnyTransportMode)
                .orElse(false);
    }
}
