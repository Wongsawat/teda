-- UN/CEFACT Duty/Tax/Fee Type Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_DutyTaxFeeTypeCode_D14A.xsd
-- Standard: UN/CEFACT Code List 5153 (DutyTaxFeeTypeCode), Version D14A

CREATE TABLE duty_tax_fee_type_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    is_vat BOOLEAN DEFAULT false,
    is_exempt BOOLEAN DEFAULT false,
    is_summary BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_duty_tax_fee_type_code_format CHECK (code ~ '^[A-Z]{3}$')
);;

-- Add comment to table
COMMENT ON TABLE duty_tax_fee_type_code IS 'UN/CEFACT duty, tax, and fee type codes for classifying fiscal charges on goods and services in e-Tax Invoice';;

-- Add comments to columns
COMMENT ON COLUMN duty_tax_fee_type_code.code IS 'Three-letter tax type code';;
COMMENT ON COLUMN duty_tax_fee_type_code.name IS 'Name/title of the tax type';;
COMMENT ON COLUMN duty_tax_fee_type_code.description IS 'Detailed description of the tax type and its application';;
COMMENT ON COLUMN duty_tax_fee_type_code.category IS 'Tax category (VAT, GST, Customs Duty, Excise Tax, etc.)';;
COMMENT ON COLUMN duty_tax_fee_type_code.is_vat IS 'True if this is a VAT type';;
COMMENT ON COLUMN duty_tax_fee_type_code.is_exempt IS 'True if this represents tax exemption';;
COMMENT ON COLUMN duty_tax_fee_type_code.is_summary IS 'True if this represents a summary/total';;

-- Create indexes for faster lookups
CREATE INDEX idx_duty_tax_fee_type_code_name ON duty_tax_fee_type_code(name);;
CREATE INDEX idx_duty_tax_fee_type_code_category ON duty_tax_fee_type_code(category);;
CREATE INDEX idx_duty_tax_fee_type_code_is_vat ON duty_tax_fee_type_code(is_vat);;
CREATE INDEX idx_duty_tax_fee_type_code_is_exempt ON duty_tax_fee_type_code(is_exempt);;

-- Create full-text search index
CREATE INDEX idx_duty_tax_fee_type_code_description_fulltext
ON duty_tax_fee_type_code USING gin(to_tsvector('english', description));;

CREATE INDEX idx_duty_tax_fee_type_code_name_fulltext
ON duty_tax_fee_type_code USING gin(to_tsvector('english', name));;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_duty_tax_fee_type_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_duty_tax_fee_type_code_timestamp
    BEFORE UPDATE ON duty_tax_fee_type_code
    FOR EACH ROW
    EXECUTE FUNCTION update_duty_tax_fee_type_code_timestamp();;

-- Note: The actual data insertion (53 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (for reference - key codes):
/*
INSERT INTO duty_tax_fee_type_code (code, name, description, category, is_vat, is_exempt, is_summary) VALUES
('VAT', 'Value added tax', 'A tax on domestic or imported goods applied to the value added at each stage in the production/distribution cycle.', 'VAT', true, false, false),
('GST', 'Goods and services tax', 'Tax levied on the final consumption of goods and services throughout the production and distribution chain.', 'GST', false, false, false),
('EXC', 'Excise duty', 'Customs or fiscal authorities code to identify a specific or ad valorem levy on a specific commodity, applied either domestically or at time of importation.', 'Excise Tax', false, false, false),
('CUD', 'Customs duty', 'Duties laid down in the Customs tariff, to which goods are liable on entering or leaving the Customs territory (CCC).', 'Customs Duty', false, false, false),
('FRE', 'Free', 'No tax levied.', 'Tax Exempt', false, true, false),
('TOT', 'Total', 'The summary amount of all taxes.', 'Summary', false, false, true);;
*/

-- Create views for major tax categories
CREATE VIEW duty_tax_fee_type_vat AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE category = 'VAT' OR is_vat = true
ORDER BY code;

CREATE VIEW duty_tax_fee_type_gst AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE category = 'GST'
ORDER BY code;

CREATE VIEW duty_tax_fee_type_sales_tax AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE category = 'Sales Tax'
ORDER BY code;

CREATE VIEW duty_tax_fee_type_customs AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE category = 'Customs Duty' OR category = 'Trade Remedy Duty'
ORDER BY code;

CREATE VIEW duty_tax_fee_type_excise AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE category = 'Excise Tax' OR category = 'Commodity Tax'
ORDER BY code;

CREATE VIEW duty_tax_fee_type_environmental AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE category = 'Environmental Tax'
ORDER BY code;

CREATE VIEW duty_tax_fee_type_exempt AS
SELECT code, name, description
FROM duty_tax_fee_type_code
WHERE is_exempt = true
ORDER BY code;

CREATE VIEW duty_tax_fee_type_common AS
SELECT code, name, description, category
FROM duty_tax_fee_type_code
WHERE code IN ('VAT', 'GST', 'EXC', 'CUD', 'FRE', 'TOT', 'ADD', 'CVD', 'ENV')
ORDER BY
    CASE code
        WHEN 'VAT' THEN 1
        WHEN 'GST' THEN 2
        WHEN 'EXC' THEN 3
        WHEN 'CUD' THEN 4
        WHEN 'ADD' THEN 5
        WHEN 'CVD' THEN 6
        WHEN 'ENV' THEN 7
        WHEN 'FRE' THEN 8
        WHEN 'TOT' THEN 9
    END;

COMMENT ON VIEW duty_tax_fee_type_vat IS 'Value Added Tax (VAT) codes';;
COMMENT ON VIEW duty_tax_fee_type_gst IS 'Goods and Services Tax (GST) codes';;
COMMENT ON VIEW duty_tax_fee_type_sales_tax IS 'Sales tax codes (harmonized, provincial, state, local)';;
COMMENT ON VIEW duty_tax_fee_type_customs IS 'Customs duties and trade remedy duties';;
COMMENT ON VIEW duty_tax_fee_type_excise IS 'Excise taxes and commodity-specific taxes';;
COMMENT ON VIEW duty_tax_fee_type_environmental IS 'Environmental and energy taxes';;
COMMENT ON VIEW duty_tax_fee_type_exempt IS 'Tax exempt codes';;
COMMENT ON VIEW duty_tax_fee_type_common IS 'Most commonly used tax type codes';;

-- Create helper function to get tax type name
CREATE OR REPLACE FUNCTION get_duty_tax_fee_type_name(type_code VARCHAR(10))
RETURNS VARCHAR(255) AS $$
DECLARE
    type_name VARCHAR(255);
BEGIN
    SELECT name INTO type_name
    FROM duty_tax_fee_type_code
    WHERE code = UPPER(type_code);;

    RETURN type_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_duty_tax_fee_type_name(VARCHAR) IS 'Get tax type name from code';;

-- Create helper function to validate tax type code
CREATE OR REPLACE FUNCTION is_valid_duty_tax_fee_type_code(type_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM duty_tax_fee_type_code
        WHERE code = UPPER(type_code)
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_duty_tax_fee_type_code(VARCHAR) IS 'Validate if tax type code exists';;

-- Create helper function to check if code is VAT
CREATE OR REPLACE FUNCTION is_vat_type(type_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    vat_flag BOOLEAN;
BEGIN
    SELECT is_vat INTO vat_flag
    FROM duty_tax_fee_type_code
    WHERE code = UPPER(type_code);;

    RETURN COALESCE(vat_flag, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_vat_type(VARCHAR) IS 'Check if tax type code is VAT';;

-- Create helper function to check if code is tax exempt
CREATE OR REPLACE FUNCTION is_tax_exempt(type_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    exempt_flag BOOLEAN;
BEGIN
    SELECT is_exempt INTO exempt_flag
    FROM duty_tax_fee_type_code
    WHERE code = UPPER(type_code);;

    RETURN COALESCE(exempt_flag, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_tax_exempt(VARCHAR) IS 'Check if tax type code represents exemption';;

-- Create helper function to get tax category
CREATE OR REPLACE FUNCTION get_duty_tax_fee_category(type_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    tax_category VARCHAR(50);
BEGIN
    SELECT category INTO tax_category
    FROM duty_tax_fee_type_code
    WHERE code = UPPER(type_code);;

    RETURN tax_category;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_duty_tax_fee_category(VARCHAR) IS 'Get tax category from code';;

-- Create helper function to search tax types by keyword
CREATE OR REPLACE FUNCTION search_duty_tax_fee_types(search_term TEXT)
RETURNS TABLE(
    code VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    category VARCHAR(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        dt.code,
        dt.name,
        dt.description,
        dt.category
    FROM duty_tax_fee_type_code dt
    WHERE
        to_tsvector('english', dt.name || ' ' || dt.description) @@ plainto_tsquery('english', search_term)
        OR dt.name ILIKE '%' || search_term || '%'
        OR dt.description ILIKE '%' || search_term || '%'
    ORDER BY dt.code;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION search_duty_tax_fee_types(TEXT) IS 'Search tax type codes by keyword in name or description';;

-- Create summary view by category
CREATE VIEW duty_tax_fee_type_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    array_agg(code ORDER BY code) as codes
FROM duty_tax_fee_type_code
GROUP BY category
ORDER BY code_count DESC, category;

COMMENT ON VIEW duty_tax_fee_type_code_category_summary IS 'Summary of tax type codes grouped by category';;
