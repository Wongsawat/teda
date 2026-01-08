package com.wpanther.etax.core.xml.thaicategory;

import com.wpanther.etax.core.adapter.common.ThaiCategoryCodeAdapter;
import com.wpanther.etax.core.entity.ThaiCategoryCode;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Objects;

/**
 * Custom JAXB type for Thai Category Code with database-backed implementation
 *
 * This class wraps the ThaiCategoryCode entity for XML marshalling/unmarshalling while
 * maintaining compatibility with the JAXB-generated namespace structure.
 *
 * Standard: ETDA Thai Category Code v1.0
 * Namespace: urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1
 * Code List Version: 1.0
 * Total Codes: 2 (01, 02)
 *
 * Features:
 * - Database-backed category lookup
 * - Bilingual support (Thai and English)
 * - Document reference categorization
 * - Business logic for category types
 *
 * Category Codes:
 * - 01: Reference to original document for: 1.Cancellation 2.Debit Note 3.Credit Note
 * - 02: Reference to document for advance payment receipt
 *
 * Usage in JAXB classes:
 * <pre>
 * {@code
 * @XmlElement(namespace = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1")
 * private ThaiCategoryCodeType categoryCode;
 * }
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ThaiCategoryCodeType {

    @XmlValue
    @XmlJavaTypeAdapter(ThaiCategoryCodeAdapter.class)
    private ThaiCategoryCode value;

    // Constructors

    public ThaiCategoryCodeType() {
    }

    public ThaiCategoryCodeType(ThaiCategoryCode value) {
        this.value = value;
    }

    public ThaiCategoryCodeType(String code) {
        this.value = ThaiCategoryCodeAdapter.isValid(code)
            ? new ThaiCategoryCode(code, ThaiCategoryCodeAdapter.getNameTh(code), ThaiCategoryCodeAdapter.getNameEn(code))
            : new ThaiCategoryCode(code);
    }

    // Factory methods

    /**
     * Create from category code string
     *
     * @param code Category code (e.g., "01", "02")
     * @return ThaiCategoryCodeType instance
     */
    public static ThaiCategoryCodeType of(String code) {
        return new ThaiCategoryCodeType(code);
    }

    /**
     * Create from ThaiCategoryCode entity
     *
     * @param entity ThaiCategoryCode entity
     * @return ThaiCategoryCodeType instance
     */
    public static ThaiCategoryCodeType of(ThaiCategoryCode entity) {
        return new ThaiCategoryCodeType(entity);
    }

    /**
     * Create category code 01 - Original document reference (cancellation/debit/credit)
     *
     * @return ThaiCategoryCodeType for code 01
     */
    public static ThaiCategoryCodeType originalDocumentReference() {
        return of("01");
    }

    /**
     * Create category code 02 - Advance payment reference
     *
     * @return ThaiCategoryCodeType for code 02
     */
    public static ThaiCategoryCodeType advancePaymentReference() {
        return of("02");
    }

    // Business logic methods delegating to entity

    /**
     * Get category code
     *
     * @return Category code string, or null if value is null
     */
    public String getCode() {
        return value != null ? value.getCode() : null;
    }

    /**
     * Get Thai category name
     *
     * @return Thai name, or null if value is null
     */
    public String getNameTh() {
        return value != null ? value.getNameTh() : null;
    }

    /**
     * Get English category name
     *
     * @return English name, or null if value is null
     */
    public String getNameEn() {
        return value != null ? value.getNameEn() : null;
    }

    /**
     * Get category description
     *
     * @return Description, or null if value is null
     */
    public String getDescription() {
        return value != null ? value.getDescription() : null;
    }

    /**
     * Check if this is code 01 - Original document reference (cancellation/debit/credit)
     *
     * @return true if code is 01
     */
    public boolean isOriginalDocumentReference() {
        return value != null && value.isOriginalDocumentReference();
    }

    /**
     * Check if this is code 02 - Advance payment reference
     *
     * @return true if code is 02
     */
    public boolean isAdvancePaymentReference() {
        return value != null && value.isAdvancePaymentReference();
    }

    // Getters and Setters

    public ThaiCategoryCode getValue() {
        return value;
    }

    public void setValue(ThaiCategoryCode value) {
        this.value = value;
    }

    // Equals and HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThaiCategoryCodeType)) return false;
        ThaiCategoryCodeType that = (ThaiCategoryCodeType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "ThaiCategoryCodeType{null}";
    }
}
