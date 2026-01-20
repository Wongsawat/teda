-- UN/CEFACT Document Name Code (Invoice) Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_DocumentNameCode_Invoice_D14A.xsd
-- Standard: UN/CEFACT Code List 1001 (DocumentNameCode), Version D14A

CREATE TABLE document_name_code_invoice (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    is_credit BOOLEAN DEFAULT false,
    is_debit BOOLEAN DEFAULT false,
    requires_payment BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_document_name_code_invoice_format CHECK (code ~ '^[0-9]+$')
);;

-- Add comment to table
COMMENT ON TABLE document_name_code_invoice IS 'UN/CEFACT document name codes for invoices and related financial documents in e-Tax Invoice system';;

-- Add comments to columns
COMMENT ON COLUMN document_name_code_invoice.code IS 'Document name code (numeric)';;
COMMENT ON COLUMN document_name_code_invoice.name IS 'Name/title of the document type';;
COMMENT ON COLUMN document_name_code_invoice.description IS 'Detailed description of the document type and its usage';;
COMMENT ON COLUMN document_name_code_invoice.category IS 'Document category (Invoice, Credit Note, Debit Note, Special)';;
COMMENT ON COLUMN document_name_code_invoice.is_credit IS 'True if this is a credit note (reduces amount owed)';;
COMMENT ON COLUMN document_name_code_invoice.is_debit IS 'True if this is a debit note (increases amount owed)';;
COMMENT ON COLUMN document_name_code_invoice.requires_payment IS 'True if this document claims payment';;

-- Create indexes for faster lookups
CREATE INDEX idx_document_name_code_invoice_name ON document_name_code_invoice(name);;
CREATE INDEX idx_document_name_code_invoice_category ON document_name_code_invoice(category);;
CREATE INDEX idx_document_name_code_invoice_is_credit ON document_name_code_invoice(is_credit);;
CREATE INDEX idx_document_name_code_invoice_is_debit ON document_name_code_invoice(is_debit);;

-- Create full-text search index
CREATE INDEX idx_document_name_code_invoice_description_fulltext
ON document_name_code_invoice USING gin(to_tsvector('english', description));;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_document_name_code_invoice_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_document_name_code_invoice_timestamp
    BEFORE UPDATE ON document_name_code_invoice
    FOR EACH ROW
    EXECUTE FUNCTION update_document_name_code_invoice_timestamp();;

-- Insert enumeration values from schema
INSERT INTO document_name_code_invoice (code, name, description, category, is_credit, is_debit, requires_payment) VALUES
('80',
 'Debit note related to goods or services',
 'Debit information related to a transaction for goods or services to the relevant party.',
 'Debit Note', false, true, true),

('81',
 'Credit note related to goods or services',
 'Document message used to provide credit information related to a transaction for goods or services to the relevant party.',
 'Credit Note', true, false, false),

('82',
 'Metered services invoice',
 'Document/message claiming payment for the supply of metered services (e.g., gas, electricity, etc.) supplied to a fixed meter whose consumption is measured over a period of time.',
 'Invoice', false, false, true),

('83',
 'Credit note related to financial adjustments',
 'Document message for providing credit information related to financial adjustments to the relevant party, e.g., bonuses.',
 'Credit Note', true, false, false),

('84',
 'Debit note related to financial adjustments',
 'Document/message for providing debit information related to financial adjustments to the relevant party.',
 'Debit Note', false, true, true),

('261',
 'Self billed credit note',
 'A document which indicates that the customer is claiming credit in a self billing environment.',
 'Credit Note', true, false, false),

('262',
 'Consolidated credit note - goods and services',
 'Credit note for goods and services that covers multiple transactions involving more than one invoice.',
 'Credit Note', true, false, false),

('325',
 'Proforma invoice',
 'Document/message serving as a preliminary invoice, containing - on the whole - the same information as the final invoice, but not actually claiming payment.',
 'Special', false, false, false),

('380',
 'Commercial invoice',
 '(1334) Document/message claiming payment for goods or services supplied under conditions agreed between seller and buyer.',
 'Invoice', false, false, true),

('381',
 'Credit note',
 '(1113) Document/message for providing credit information to the relevant party.',
 'Credit Note', true, false, false),

('383',
 'Debit note',
 'Document/message for providing debit information to the relevant party.',
 'Debit Note', false, true, true),

('384',
 'Corrected invoice',
 'Commercial invoice that includes revised information differing from an earlier submission of the same invoice.',
 'Invoice', false, false, true),

('385',
 'Consolidated invoice',
 'Commercial invoice that covers multiple transactions involving more than one vendor.',
 'Invoice', false, false, true),

('386',
 'Prepayment invoice',
 'An invoice to pay amounts for goods and services in advance; these amounts will be subtracted from the final invoice.',
 'Invoice', false, false, true),

('389',
 'Self-billed invoice',
 'An invoice the invoicee is producing instead of the seller.',
 'Invoice', false, false, true),

('395',
 'Consignment invoice',
 'Commercial invoice that covers a transaction other than one involving a sale.',
 'Special', false, false, false),

('396',
 'Factored credit note',
 'Credit note related to assigned invoice(s).',
 'Credit Note', true, false, false);;

-- Create views for document categories
CREATE VIEW document_name_code_invoices AS
SELECT code, name, description, requires_payment
FROM document_name_code_invoice
WHERE category = 'Invoice'
ORDER BY code;

CREATE VIEW document_name_code_credit_notes AS
SELECT code, name, description
FROM document_name_code_invoice
WHERE category = 'Credit Note' OR is_credit = true
ORDER BY code;

CREATE VIEW document_name_code_debit_notes AS
SELECT code, name, description
FROM document_name_code_invoice
WHERE category = 'Debit Note' OR is_debit = true
ORDER BY code;

CREATE VIEW document_name_code_special AS
SELECT code, name, description, requires_payment
FROM document_name_code_invoice
WHERE category = 'Special'
ORDER BY code;

CREATE VIEW document_name_code_payment_required AS
SELECT code, name, description, category
FROM document_name_code_invoice
WHERE requires_payment = true
ORDER BY code;

CREATE VIEW document_name_code_no_payment AS
SELECT code, name, description, category
FROM document_name_code_invoice
WHERE requires_payment = false
ORDER BY code;

COMMENT ON VIEW document_name_code_invoices IS 'Standard invoices that claim payment (380, 82, 384, 385, 386, 389)';;
COMMENT ON VIEW document_name_code_credit_notes IS 'Credit notes that reduce amounts owed (81, 83, 261, 262, 381, 396)';;
COMMENT ON VIEW document_name_code_debit_notes IS 'Debit notes that increase amounts owed (80, 84, 383)';;
COMMENT ON VIEW document_name_code_special IS 'Special document types (325=Proforma, 395=Consignment)';;
COMMENT ON VIEW document_name_code_payment_required IS 'Documents that require payment';;
COMMENT ON VIEW document_name_code_no_payment AS 'Documents that do not require payment (credit notes, proforma)';

-- Create helper function to get document name
CREATE OR REPLACE FUNCTION get_document_name_invoice(doc_code VARCHAR(10))
RETURNS VARCHAR(255) AS $$
DECLARE
    doc_name VARCHAR(255);
BEGIN
    SELECT name INTO doc_name
    FROM document_name_code_invoice
    WHERE code = doc_code;

    RETURN doc_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_document_name_invoice(VARCHAR) IS 'Get document name from code';;

-- Create helper function to validate document code
CREATE OR REPLACE FUNCTION is_valid_document_name_code_invoice(doc_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM document_name_code_invoice
        WHERE code = doc_code
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_document_name_code_invoice(VARCHAR) IS 'Validate if document name code exists';;

-- Create helper function to check if document is credit note
CREATE OR REPLACE FUNCTION is_credit_note(doc_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    is_credit_doc BOOLEAN;
BEGIN
    SELECT is_credit INTO is_credit_doc
    FROM document_name_code_invoice
    WHERE code = doc_code;

    RETURN COALESCE(is_credit_doc, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_credit_note(VARCHAR) IS 'Check if document code is a credit note';;

-- Create helper function to check if document is debit note
CREATE OR REPLACE FUNCTION is_debit_note(doc_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    is_debit_doc BOOLEAN;
BEGIN
    SELECT is_debit INTO is_debit_doc
    FROM document_name_code_invoice
    WHERE code = doc_code;

    RETURN COALESCE(is_debit_doc, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_debit_note(VARCHAR) IS 'Check if document code is a debit note';;

-- Create helper function to check if document requires payment
CREATE OR REPLACE FUNCTION document_requires_payment(doc_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    requires_pay BOOLEAN;
BEGIN
    SELECT requires_payment INTO requires_pay
    FROM document_name_code_invoice
    WHERE code = doc_code;

    RETURN COALESCE(requires_pay, true);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION document_requires_payment(VARCHAR) IS 'Check if document requires payment';;

-- Create helper function to get document category
CREATE OR REPLACE FUNCTION get_document_category_invoice(doc_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    doc_category VARCHAR(50);
BEGIN
    SELECT category INTO doc_category
    FROM document_name_code_invoice
    WHERE code = doc_code;

    RETURN doc_category;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_document_category_invoice(VARCHAR) IS 'Get document category (Invoice, Credit Note, Debit Note, Special)';;

-- Create summary view by category
CREATE VIEW document_name_code_invoice_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    COUNT(CASE WHEN requires_payment THEN 1 END) as payment_required_count,
    COUNT(CASE WHEN is_credit THEN 1 END) as credit_count,
    COUNT(CASE WHEN is_debit THEN 1 END) as debit_count,
    array_agg(code ORDER BY code) as codes
FROM document_name_code_invoice
GROUP BY category
ORDER BY
    CASE category
        WHEN 'Invoice' THEN 1
        WHEN 'Credit Note' THEN 2
        WHEN 'Debit Note' THEN 3
        WHEN 'Special' THEN 4
    END;

COMMENT ON VIEW document_name_code_invoice_summary IS 'Summary of document name codes grouped by category';;

-- Create view for most commonly used codes
CREATE VIEW document_name_code_invoice_common AS
SELECT code, name, description, category
FROM document_name_code_invoice
WHERE code IN ('380', '381', '383', '325', '386')
ORDER BY
    CASE code
        WHEN '380' THEN 1
        WHEN '381' THEN 2
        WHEN '383' THEN 3
        WHEN '325' THEN 4
        WHEN '386' THEN 5
    END;

COMMENT ON VIEW document_name_code_invoice_common IS 'Most commonly used document codes (380=Commercial invoice, 381=Credit note, 383=Debit note, 325=Proforma, 386=Prepayment)';;
