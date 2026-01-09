package com.wpanther.etax.validation;

/**
 * Enum mapping document types to their Schematron validation files.
 * Each document type has a corresponding .sch file in the ETDA e-Tax Invoice specification.
 */
public enum DocumentSchematron {
    /**
     * Tax Invoice (ใบกำกับภาษี) - TypeCode 388
     */
    TAX_INVOICE(
        "TaxInvoice",
        "e-tax-invoice-receipt-v2.1/ETDA/data/standard/TaxInvoice_Schematron_2p1.sch",
        "TIV"
    ),

    /**
     * Receipt (ใบเสร็จรับเงิน) - TypeCode T01
     */
    RECEIPT(
        "Receipt",
        "e-tax-invoice-receipt-v2.1/ETDA/data/standard/Receipt_Schematron_2p1.sch",
        "RCT"
    ),

    /**
     * Debit/Credit Note (ใบเพิ่มหนี้/ใบลดหนี้) - TypeCode 381/383
     */
    DEBIT_CREDIT_NOTE(
        "DebitCreditNote",
        "e-tax-invoice-receipt-v2.1/ETDA/data/standard/DebitCreditNote_Schematron_2p1.sch",
        "DCN"
    ),

    /**
     * Generic Invoice (standard UN/CEFACT CrossIndustryInvoice)
     */
    INVOICE(
        "Invoice",
        "e-tax-invoice-receipt-v2.1/ETDA/data/standard/Invoice_Schematron_2p1.sch",
        "INV"
    ),

    /**
     * Cancellation Note (ใบยกเลิก) - TypeCode 380
     * Note: This Schematron file is currently empty (no validation rules)
     */
    CANCELLATION_NOTE(
        "CancellationNote",
        "e-tax-invoice-receipt-v2.1/ETDA/data/standard/CancellationNote_Schematron_2p1.sch",
        "CN"
    ),

    /**
     * Abbreviated Tax Invoice (ใบกำกับภาษีอย่างย่อ) - TypeCode 81
     * Note: This Schematron file is currently empty (no validation rules)
     */
    ABBREVIATED_TAX_INVOICE(
        "AbbreviatedTaxInvoice",
        "e-tax-invoice-receipt-v2.1/ETDA/data/standard/AbbreviatedTaxInvoice_Schematron_2p1.sch",
        "ATI"
    );

    private final String documentName;
    private final String schematronPath;
    private final String rulePrefix;

    DocumentSchematron(String documentName, String schematronPath, String rulePrefix) {
        this.documentName = documentName;
        this.schematronPath = schematronPath;
        this.rulePrefix = rulePrefix;
    }

    /**
     * Get the document name (e.g., "TaxInvoice", "Receipt")
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Get the classpath path to the Schematron file
     */
    public String getSchematronPath() {
        return schematronPath;
    }

    /**
     * Get the rule ID prefix used in this Schematron (e.g., "TIV", "RCT")
     */
    public String getRulePrefix() {
        return rulePrefix;
    }

    /**
     * Check if this Schematron file is known to be empty (no validation rules)
     */
    public boolean isEmptySchematron() {
        return this == CANCELLATION_NOTE || this == ABBREVIATED_TAX_INVOICE;
    }
}
