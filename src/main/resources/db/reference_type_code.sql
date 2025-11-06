-- UN/CEFACT Reference Type Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_ReferenceTypeCode_D14A.xsd
-- Standard: UN/CEFACT Code List 1153 (ReferenceTypeCode), Version D14A

CREATE TABLE reference_type_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    is_thai_extension BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_reference_type_code_format CHECK (code ~ '^[A-Z0-9]+$')
);

-- Add comment to table
COMMENT ON TABLE reference_type_code IS 'UN/CEFACT reference type codes identifying various business document types and reference numbers in commercial transactions (798 codes)';

-- Add comments to columns
COMMENT ON COLUMN reference_type_code.code IS 'Reference type code (3-letter AAA-ZZZ, numeric, or Thai T## codes)';
COMMENT ON COLUMN reference_type_code.name IS 'Name/title of the reference type';
COMMENT ON COLUMN reference_type_code.description IS 'Detailed description of the reference type and its usage';
COMMENT ON COLUMN reference_type_code.category IS 'Reference category (Invoice, Order, Delivery, Contract, Payment, Customs, etc.)';
COMMENT ON COLUMN reference_type_code.is_thai_extension IS 'True if this is a Thai/ETDA extension code';

-- Create indexes for faster lookups
CREATE INDEX idx_reference_type_code_name ON reference_type_code(name);
CREATE INDEX idx_reference_type_code_category ON reference_type_code(category);
CREATE INDEX idx_reference_type_code_is_thai ON reference_type_code(is_thai_extension);

-- Create full-text search index
CREATE INDEX idx_reference_type_code_description_fulltext
ON reference_type_code USING gin(to_tsvector('english', description));

CREATE INDEX idx_reference_type_code_name_fulltext
ON reference_type_code USING gin(to_tsvector('english', name));

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_reference_type_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_reference_type_code_timestamp
    BEFORE UPDATE ON reference_type_code
    FOR EACH ROW
    EXECUTE FUNCTION update_reference_type_code_timestamp();

-- Note: The actual data insertion (798 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file
-- Data is inserted in batches of 100 records each

-- Sample data structure (for reference - key codes):
/*
INSERT INTO reference_type_code (code, name, description, category, is_thai_extension) VALUES
('AAA', 'Order acknowledgement document identifier', '[1018] Reference number identifying the acknowledgement of an order.', 'Order', false),
('AAB', 'Proforma invoice document identifier', '[1088] Reference number to identify a proforma invoice.', 'Invoice/Billing', false),
('AAJ', 'Delivery order number', 'Reference number assigned by issuer to a delivery order.', 'Delivery/Shipment', false),
('380', 'ใบแจ้งหนี้', 'ใบแจ้งหนี้ (Invoice)', 'Thai Extension', true),
('388', 'ใบกำกับภาษี', 'ใบกำกับภาษี (Tax Invoice)', 'Thai Extension', true),
('T01', 'ใบรับ', 'ใบรับ (Receipt)', 'Thai Extension', true);
*/

-- Create views for reference categories
CREATE VIEW reference_type_code_invoice AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Invoice/Billing'
ORDER BY code;

CREATE VIEW reference_type_code_order AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Order'
ORDER BY code;

CREATE VIEW reference_type_code_delivery AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Delivery/Shipment'
ORDER BY code;

CREATE VIEW reference_type_code_contract AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Contract'
ORDER BY code;

CREATE VIEW reference_type_code_payment AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Payment'
ORDER BY code;

CREATE VIEW reference_type_code_customs AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Customs'
ORDER BY code;

CREATE VIEW reference_type_code_transport AS
SELECT code, name, description
FROM reference_type_code
WHERE category = 'Transport'
ORDER BY code;

CREATE VIEW reference_type_code_thai AS
SELECT code, name, description
FROM reference_type_code
WHERE is_thai_extension = true OR category = 'Thai Extension'
ORDER BY code;

CREATE VIEW reference_type_code_common AS
SELECT code, name, description, category
FROM reference_type_code
WHERE code IN ('AAA', 'AAB', 'AAJ', 'AAK', 'AAN', 'ON', 'IV', 'DQ', 'PK',
               '380', '388', 'T01', 'T02', 'T03', 'T04', 'T05')
ORDER BY
    CASE
        WHEN code = 'ON' THEN 1   -- Purchase order number
        WHEN code = 'AAA' THEN 2  -- Order acknowledgement
        WHEN code = 'DQ' THEN 3   -- Delivery note number
        WHEN code = 'IV' THEN 4   -- Invoice number
        WHEN code = 'AAB' THEN 5  -- Proforma invoice
        WHEN code = '380' THEN 6  -- Invoice (Thai)
        WHEN code = '388' THEN 7  -- Tax Invoice (Thai)
        WHEN code ~ '^T0' THEN 10 + SUBSTRING(code FROM 2)::INTEGER
        ELSE 99
    END;

COMMENT ON VIEW reference_type_code_invoice IS 'Invoice and billing reference codes';
COMMENT ON VIEW reference_type_code_order IS 'Order reference codes';
COMMENT ON VIEW reference_type_code_delivery IS 'Delivery and shipment reference codes';
COMMENT ON VIEW reference_type_code_contract IS 'Contract reference codes';
COMMENT ON VIEW reference_type_code_payment IS 'Payment reference codes';
COMMENT ON VIEW reference_type_code_customs IS 'Customs reference codes';
COMMENT ON VIEW reference_type_code_transport IS 'Transport reference codes';
COMMENT ON VIEW reference_type_code_thai IS 'Thai extension reference codes';
COMMENT ON VIEW reference_type_code_common IS 'Most commonly used reference type codes';

-- Create helper function to get reference type name
CREATE OR REPLACE FUNCTION get_reference_type_name(ref_code VARCHAR(10))
RETURNS VARCHAR(500) AS $$
DECLARE
    ref_name VARCHAR(500);
BEGIN
    SELECT name INTO ref_name
    FROM reference_type_code
    WHERE code = UPPER(ref_code);

    RETURN ref_name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_reference_type_name(VARCHAR) IS 'Get reference type name from code';

-- Create helper function to validate reference type code
CREATE OR REPLACE FUNCTION is_valid_reference_type_code(ref_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM reference_type_code
        WHERE code = UPPER(ref_code)
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION is_valid_reference_type_code(VARCHAR) IS 'Validate if reference type code exists';

-- Create helper function to check if code is Thai extension
CREATE OR REPLACE FUNCTION is_thai_extension_code(ref_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    thai_flag BOOLEAN;
BEGIN
    SELECT is_thai_extension INTO thai_flag
    FROM reference_type_code
    WHERE code = UPPER(ref_code);

    RETURN COALESCE(thai_flag, false);
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION is_thai_extension_code(VARCHAR) IS 'Check if reference type code is a Thai extension';

-- Create helper function to get reference category
CREATE OR REPLACE FUNCTION get_reference_category(ref_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    ref_category VARCHAR(50);
BEGIN
    SELECT category INTO ref_category
    FROM reference_type_code
    WHERE code = UPPER(ref_code);

    RETURN ref_category;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_reference_category(VARCHAR) IS 'Get reference category from code';

-- Create helper function to search reference types by keyword
CREATE OR REPLACE FUNCTION search_reference_type_codes(search_term TEXT)
RETURNS TABLE(
    code VARCHAR(10),
    name VARCHAR(500),
    description TEXT,
    category VARCHAR(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        rt.code,
        rt.name,
        rt.description,
        rt.category
    FROM reference_type_code rt
    WHERE
        to_tsvector('english', rt.name || ' ' || rt.description) @@ plainto_tsquery('english', search_term)
        OR rt.name ILIKE '%' || search_term || '%'
        OR rt.description ILIKE '%' || search_term || '%'
    ORDER BY rt.code;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION search_reference_type_codes(TEXT) IS 'Search reference type codes by keyword in name or description';

-- Create summary view by category
CREATE VIEW reference_type_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    COUNT(CASE WHEN is_thai_extension THEN 1 END) as thai_extension_count,
    array_agg(code ORDER BY code) FILTER (WHERE is_thai_extension) as thai_codes,
    (array_agg(code ORDER BY code))[1:5] as sample_codes
FROM reference_type_code
GROUP BY category
ORDER BY code_count DESC, category;

COMMENT ON VIEW reference_type_code_category_summary IS 'Summary of reference type codes grouped by category';
