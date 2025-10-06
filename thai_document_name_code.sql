-- Thai Document Name Code (Invoice Document Types) Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ThaiDocumentNameCode_Invoice_1p0.xsd

CREATE TABLE thai_document_name_code (
    code VARCHAR(10) PRIMARY KEY,
    name_th VARCHAR(255),
    name_en VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    is_standard_code BOOLEAN DEFAULT false,
    is_thai_extension BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add comment to table
COMMENT ON TABLE thai_document_name_code IS 'Thai e-Tax Invoice document type codes (invoice, receipt, tax invoice, credit/debit notes, etc.)';

-- Add comments to columns
COMMENT ON COLUMN thai_document_name_code.code IS 'Document type code (80, 81, 82, 380, 388, T01-T07)';
COMMENT ON COLUMN thai_document_name_code.name_th IS 'Thai name of the document type';
COMMENT ON COLUMN thai_document_name_code.name_en IS 'English name of the document type';
COMMENT ON COLUMN thai_document_name_code.description IS 'Detailed description of the document type';
COMMENT ON COLUMN thai_document_name_code.is_standard_code IS 'True if this is a UN/CEFACT standard code';
COMMENT ON COLUMN thai_document_name_code.is_thai_extension IS 'True if this is a Thai-specific extension (T01-T07)';

-- Create indexes for faster lookups
CREATE INDEX idx_thai_document_name_code_name_th ON thai_document_name_code(name_th);
CREATE INDEX idx_thai_document_name_code_name_en ON thai_document_name_code(name_en);
CREATE INDEX idx_thai_document_name_code_is_standard ON thai_document_name_code(is_standard_code);
CREATE INDEX idx_thai_document_name_code_is_thai ON thai_document_name_code(is_thai_extension);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_thai_document_name_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_thai_document_name_code_timestamp
    BEFORE UPDATE ON thai_document_name_code
    FOR EACH ROW
    EXECUTE FUNCTION update_thai_document_name_code_timestamp();

-- Insert enumeration values from schema
INSERT INTO thai_document_name_code (code, name_th, name_en, description, is_standard_code, is_thai_extension) VALUES
-- UN/CEFACT Standard Codes
('80',
 'ใบเพิ่มหนี้',
 'Debit note related to goods or services',
 'Debit information related to a transaction for goods or services to the relevant party.',
 true, false),

('81',
 'ใบลดหนี้',
 'Credit note related to goods or services',
 'Document message used to provide credit information related to a transaction for goods or services to the relevant party.',
 true, false),

('82',
 'ใบแจ้งหนี้ค่าบริการตามมิเตอร์',
 'Metered services invoice',
 'Document/message claiming payment for the supply of metered services (e.g., gas, electricity, etc.) supplied to a fixed meter whose consumption is measured over a period of time.',
 true, false),

('380',
 'ใบแจ้งหนี้การค้า',
 'Commercial invoice',
 '(1334) Document/message claiming payment for goods or services supplied under conditions agreed between seller and buyer.',
 true, false),

('388',
 'ใบกำกับภาษี',
 'Tax Invoice',
 'Tax Invoice',
 true, false),

-- Thai-Specific Extension Codes
('T01',
 'ใบรับ',
 'Receipt',
 'Receipt',
 false, true),

('T02',
 'ใบแจ้งหนี้/ใบกำกับภาษี',
 'Invoice/Tax Invoice',
 'Invoice/ Tax Invoice',
 false, true),

('T03',
 'ใบเสร็จรับเงิน/ใบกำกับภาษี',
 'Receipt/Tax Invoice',
 'Receipt/ Tax Invoice',
 false, true),

('T04',
 'ใบส่งของ/ใบกำกับภาษี',
 'Delivery order/Tax Invoice',
 'Delivery order/ Tax Invoice',
 false, true),

('T05',
 'ใบกำกับภาษีอย่างย่อ',
 'Abbreviated Tax Invoice',
 'Abbreviated Tax Invoice',
 false, true),

('T06',
 'ใบเสร็จรับเงิน/ใบกำกับภาษีอย่างย่อ',
 'Receipt/Abbreviated Tax Invoice',
 'Receipt/Abbreviated Tax Invoice',
 false, true),

('T07',
 'ใบแจ้งยกเลิก',
 'Cancellation note',
 'Cancellation note',
 false, true);

-- Create views for different document categories
CREATE VIEW thai_document_name_code_standard AS
SELECT code, name_th, name_en, description
FROM thai_document_name_code
WHERE is_standard_code = true
ORDER BY code;

CREATE VIEW thai_document_name_code_thai_extension AS
SELECT code, name_th, name_en, description
FROM thai_document_name_code
WHERE is_thai_extension = true
ORDER BY code;

-- Create view for tax invoice types only
CREATE VIEW thai_document_name_code_tax_invoice AS
SELECT code, name_th, name_en, description
FROM thai_document_name_code
WHERE name_en LIKE '%Tax Invoice%' OR name_en LIKE '%tax invoice%'
ORDER BY code;

COMMENT ON VIEW thai_document_name_code_standard IS 'UN/CEFACT standard document type codes (80, 81, 82, 380, 388)';
COMMENT ON VIEW thai_document_name_code_thai_extension IS 'Thai-specific extension document type codes (T01-T07)';
COMMENT ON VIEW thai_document_name_code_tax_invoice IS 'All document types that include tax invoice functionality';
