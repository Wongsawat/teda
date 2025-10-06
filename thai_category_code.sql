-- Thai Category Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ThaiCategoryCode_1p0.xsd

CREATE TABLE thai_category_code (
    code VARCHAR(2) PRIMARY KEY,
    name_th VARCHAR(500) NOT NULL,
    name_en VARCHAR(500),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add comment to table
COMMENT ON TABLE thai_category_code IS 'Thai e-Tax Invoice category codes for document references (cancellation, debit/credit notes, advance payments)';

-- Add comments to columns
COMMENT ON COLUMN thai_category_code.code IS 'Category code (2 digits)';
COMMENT ON COLUMN thai_category_code.name_th IS 'Thai name/description of the category';
COMMENT ON COLUMN thai_category_code.name_en IS 'English translation of the category';
COMMENT ON COLUMN thai_category_code.description IS 'Detailed explanation of when to use this category';

-- Create index for faster lookups
CREATE INDEX idx_thai_category_code_name_th ON thai_category_code(name_th);
CREATE INDEX idx_thai_category_code_name_en ON thai_category_code(name_en);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_thai_category_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_thai_category_code_timestamp
    BEFORE UPDATE ON thai_category_code
    FOR EACH ROW
    EXECUTE FUNCTION update_thai_category_code_timestamp();

-- Insert enumeration values from schema
INSERT INTO thai_category_code (code, name_th, name_en, description) VALUES
('01',
 'อ้างอิงเอกสารฉบับเดิมกรณี 1.ยกเลิก 2.เพิ่มหนี้ 3.ลดหนี้',
 'Reference to original document for: 1. Cancellation 2. Debit note 3. Credit note',
 'Used when referencing the original invoice document for cases of cancellation, creating debit notes (increasing debt), or creating credit notes (decreasing debt). This category is essential for document amendment and correction workflows.'
),
('02',
 'อ้างอิงเอกสารเพื่อออกเงินรับล่วงหน้า',
 'Reference to document for advance payment receipt',
 'Used when referencing documents related to advance payments or prepayments. This category is used when issuing receipts for payments received before the actual goods/services delivery or final invoice.'
);

-- Create view for active categories
CREATE VIEW thai_category_code_active AS
SELECT
    code,
    name_th,
    name_en,
    description
FROM thai_category_code
ORDER BY code;

COMMENT ON VIEW thai_category_code_active IS 'Active Thai category codes for e-Tax Invoice document references';
