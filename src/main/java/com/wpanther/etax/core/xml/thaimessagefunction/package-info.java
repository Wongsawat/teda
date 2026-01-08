/**
 * Thai Message Function Code XML types with database-backed implementation.
 *
 * <p>This package provides custom JAXB types for Thai e-Tax Invoice message function codes,
 * replacing the generated JAXB enums with database-backed entities for dynamic code list management.</p>
 *
 * <h2>Overview</h2>
 * <p>The Thai Message Function Code is used to indicate the document type and purpose in Thai e-Tax invoices.
 * It combines document type (DebitNote, CreditNote, TaxInvoice, Receipt) with category (Goods, Service)
 * and function (Original, Replacement, Cancellation, Copy, Addition, Other).</p>
 *
 * <h2>Code Structure</h2>
 * <p>Thai Message Function Codes follow the pattern: <code>[PREFIX][SUFFIX]</code></p>
 * <ul>
 *   <li><strong>PREFIX (4 chars):</strong> Document type and category
 *     <ul>
 *       <li>DBNG - Debit Note Goods</li>
 *       <li>DBNS - Debit Note Services</li>
 *       <li>CDNG - Credit Note Goods</li>
 *       <li>CDNS - Credit Note Services</li>
 *       <li>TIVC - Tax Invoice</li>
 *       <li>RCTC - Receipt</li>
 *     </ul>
 *   </li>
 *   <li><strong>SUFFIX (2 digits):</strong> Document function
 *     <ul>
 *       <li>01 - Original (ต้นฉบับ)</li>
 *       <li>02 - Replacement (ฉบับแทน)</li>
 *       <li>03 - Cancellation (ยกเลิก)</li>
 *       <li>04 - Copy (สำเนา)</li>
 *       <li>05 - Addition (เพิ่มเติม)</li>
 *       <li>99 - Other (อื่นๆ)</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Total Codes: 24</h2>
 * <ul>
 *   <li><strong>Debit Note - Goods (3 codes):</strong> DBNG01, DBNG02, DBNG99</li>
 *   <li><strong>Debit Note - Services (3 codes):</strong> DBNS01, DBNS02, DBNS99</li>
 *   <li><strong>Credit Note - Goods (6 codes):</strong> CDNG01, CDNG02, CDNG03, CDNG04, CDNG05, CDNG99</li>
 *   <li><strong>Credit Note - Services (5 codes):</strong> CDNS01, CDNS02, CDNS03, CDNS04, CDNS99</li>
 *   <li><strong>Tax Invoice (3 codes):</strong> TIVC01, TIVC02, TIVC99</li>
 *   <li><strong>Receipt (5 codes):</strong> RCTC01, RCTC02, RCTC03, RCTC04, RCTC99</li>
 * </ul>
 *
 * <h2>Common Usage Examples</h2>
 * <pre>{@code
 * // Creating message function codes
 * ThaiMessageFunctionCodeType taxInvoice = ThaiMessageFunctionCodeType.taxInvoiceOriginal();
 * ThaiMessageFunctionCodeType receipt = ThaiMessageFunctionCodeType.receiptOriginal();
 * ThaiMessageFunctionCodeType debitNote = ThaiMessageFunctionCodeType.debitNoteGoodsOriginal();
 * ThaiMessageFunctionCodeType creditNote = ThaiMessageFunctionCodeType.creditNoteServicesOriginal();
 *
 * // From code string
 * ThaiMessageFunctionCodeType code = ThaiMessageFunctionCodeType.of("TIVC01");
 *
 * // Checking document type
 * if (code.isTaxInvoice()) {
 *     System.out.println("This is a tax invoice");
 * }
 *
 * if (code.isDebitNote()) {
 *     System.out.println("This is a debit note");
 * }
 *
 * // Checking category
 * if (code.isGoods()) {
 *     System.out.println("This is for goods");
 * }
 *
 * if (code.isService()) {
 *     System.out.println("This is for services");
 * }
 *
 * // Checking function type
 * if (code.isOriginal()) {
 *     System.out.println("This is an original document");
 * }
 *
 * if (code.isReplacement()) {
 *     System.out.println("This is a replacement document");
 * }
 *
 * // Getting descriptions
 * String englishDesc = code.getDescriptionEn();
 * String thaiDesc = code.getDescriptionTh();
 * }</pre>
 *
 * <h2>Bilingual Support</h2>
 * <p>All message function codes include both Thai and English descriptions:</p>
 * <ul>
 *   <li><strong>TIVC01:</strong> Tax Invoice (Original) / ใบกำกับภาษี (ต้นฉบับ)</li>
 *   <li><strong>RCTC01:</strong> Receipt (Original) / ใบเสร็จรับเงิน (ต้นฉบับ)</li>
 *   <li><strong>DBNG01:</strong> Debit Note - Goods (Original) / ใบเพิ่มหนี้ - สินค้า (ต้นฉบับ)</li>
 *   <li><strong>CDNG01:</strong> Credit Note - Goods (Original) / ใบลดหนี้ - สินค้า (ต้นฉบับ)</li>
 * </ul>
 *
 * <h2>Database Schema</h2>
 * <p>Message function codes are stored in the <code>thai_message_function_code</code> table with:</p>
 * <ul>
 *   <li>Bilingual descriptions (English and Thai)</li>
 *   <li>Document type classification</li>
 *   <li>Category classification</li>
 *   <li>Active status flag</li>
 *   <li>Timestamps for audit</li>
 * </ul>
 *
 * <h2>XSD Standard</h2>
 * <p>Based on ETDA Thai_MessageFunctionCode_1p0.xsd standard:</p>
 * <ul>
 *   <li><strong>Namespace:</strong> urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560</li>
 *   <li><strong>Schema Agency:</strong> UNCEFACT</li>
 *   <li><strong>Schema Version:</strong> 100.0</li>
 *   <li><strong>Schema Date:</strong> 30 May 2016</li>
 * </ul>
 *
 * <h2>JAXB Integration</h2>
 * <p>The custom type integrates with JAXB through {@link com.wpanther.etax.core.adapter.ThaiMessageFunctionCodeAdapter}
 * which handles conversion between the JAXB enum {@link un.unece.uncefact.codelist.standard.etda.thaimessagefunctioncode._2560.ThaiMessageFunctionCodeContentType}
 * and database entities.</p>
 *
 * @see com.wpanther.etax.core.entity.ThaiMessageFunctionCode
 * @see com.wpanther.etax.core.repository.ThaiMessageFunctionCodeRepository
 * @see com.wpanther.etax.core.adapter.taxinvoice.ThaiMessageFunctionCodeAdapter
 * @see ThaiMessageFunctionCodeType
 */
@XmlSchema(
    namespace = "urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560",
    elementFormDefault = XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "thaiMsgFunc", namespaceURI = "urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560")
    }
)
package com.wpanther.etax.core.xml.thaimessagefunction;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
