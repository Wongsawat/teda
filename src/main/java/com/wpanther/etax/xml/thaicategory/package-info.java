/**
 * JAXB types for ETDA Thai Category Code (database-backed implementation)
 *
 * This package contains custom JAXB types for Thai e-Tax Invoice category codes
 * that are backed by database entities instead of JAXB-generated enumerations.
 *
 * Standard: ETDA Thai Category Code v1.0
 * Code List Version: 1.0
 * Total Codes: 2 (01, 02)
 * Namespace: urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1
 *
 * Key Features:
 * - Database-backed category lookup
 * - Bilingual support (Thai and English names)
 * - Document reference categorization
 * - Business logic for category types
 *
 * Category Codes:
 * - 01: อ้างอิงเอกสารฉบับเดิมกรณี 1.ยกเลิก 2.เพิ่มหนี้ 3.ลดหนี้
 *       (Reference to original document for: 1.Cancellation 2.Debit Note 3.Credit Note)
 *       Used for: Document cancellations, debit notes (adding charges), credit notes (reducing charges)
 *
 * - 02: อ้างอิงเอกสารเพื่อออกเงินรับล่วงหน้า
 *       (Reference to document for advance payment receipt)
 *       Used for: Advance payment receipts, prepayment scenarios
 *
 * Database Schema:
 * - Table: thai_category_code
 * - Primary key: code (2 digits: '01', '02')
 * - Fields: code, name_th, name_en, description
 * - View: thai_category_code_active
 *
 * Usage Example:
 * <pre>
 * {@code
 * // In e-Tax Invoice XML classes
 * @XmlElement(namespace = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1")
 * private ThaiCategoryCodeType categoryCode;
 *
 * // Create category codes
 * ThaiCategoryCodeType cancellation = ThaiCategoryCodeType.originalDocumentReference();
 * ThaiCategoryCodeType advance = ThaiCategoryCodeType.advancePaymentReference();
 * ThaiCategoryCodeType custom = ThaiCategoryCodeType.of("01");
 *
 * // Check category properties
 * if (categoryCode.isOriginalDocumentReference()) {
 *     System.out.println("Document amendment: " + categoryCode.getNameEn());
 * }
 *
 * if (categoryCode.isAdvancePaymentReference()) {
 *     System.out.println("Advance payment: " + categoryCode.getNameTh());
 * }
 * }
 * </pre>
 *
 * Business Context:
 *
 * Code 01 - Original Document References:
 * This category is essential for document amendment workflows in Thai e-Tax Invoice system.
 * When you need to cancel an invoice, issue a debit note (increase amount), or issue a
 * credit note (decrease amount), you must reference the original document using this category.
 *
 * Code 02 - Advance Payments:
 * This category is used when issuing receipts for payments received before goods/services
 * are delivered or before the final invoice is issued. Common in Thai business practices
 * where advance payments or deposits are collected.
 *
 * @see com.wpanther.etax.entity.ThaiCategoryCode
 * @see com.wpanther.etax.repository.ThaiCategoryCodeRepository
 * @see com.wpanther.etax.adapter.ThaiCategoryCodeAdapter
 * @see ThaiCategoryCodeType
 */
@XmlSchema(
    namespace = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "thaiCategory", namespaceURI = "urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1")
    }
)
package com.wpanther.etax.xml.thaicategory;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
