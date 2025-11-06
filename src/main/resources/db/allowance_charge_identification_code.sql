-- UN/CEFACT Allowance/Charge Identification Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_AllowanceChargeIdentificationCode_D14A.xsd
-- Standard: UN/CEFACT Code List 5189 (AllowanceChargeID), Version D14A

CREATE TABLE allowance_charge_identification_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    is_standard_code BOOLEAN DEFAULT true,
    is_thai_extension BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_allowance_charge_code_format CHECK (code ~ '^[0-9A-Z]+$')
);

-- Add comment to table
COMMENT ON TABLE allowance_charge_identification_code IS 'UN/CEFACT allowance and charge identification codes for e-Tax Invoice line-level and document-level adjustments';

-- Add comments to columns
COMMENT ON COLUMN allowance_charge_identification_code.code IS 'Allowance/charge identification code (numeric or alphanumeric)';
COMMENT ON COLUMN allowance_charge_identification_code.name IS 'Name/title of the allowance or charge';
COMMENT ON COLUMN allowance_charge_identification_code.description IS 'Detailed description of the allowance or charge usage and purpose';
COMMENT ON COLUMN allowance_charge_identification_code.category IS 'Category classification (Commission, Fee, Charge, Allowance, Discount, etc.)';
COMMENT ON COLUMN allowance_charge_identification_code.is_standard_code IS 'True if the code is from UN/CEFACT standard';
COMMENT ON COLUMN allowance_charge_identification_code.is_thai_extension IS 'True if the code is a Thai/ETDA extension (PP*** series)';

-- Create indexes for faster lookups
CREATE INDEX idx_allowance_charge_code_name ON allowance_charge_identification_code(name);
CREATE INDEX idx_allowance_charge_code_category ON allowance_charge_identification_code(category);
CREATE INDEX idx_allowance_charge_code_is_standard ON allowance_charge_identification_code(is_standard_code);
CREATE INDEX idx_allowance_charge_code_is_thai ON allowance_charge_identification_code(is_thai_extension);

-- Create full-text search index
CREATE INDEX idx_allowance_charge_code_description_fulltext
ON allowance_charge_identification_code USING gin(to_tsvector('english', description));

CREATE INDEX idx_allowance_charge_code_name_fulltext
ON allowance_charge_identification_code USING gin(to_tsvector('english', name));

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_allowance_charge_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_allowance_charge_code_timestamp
    BEFORE UPDATE ON allowance_charge_identification_code
    FOR EACH ROW
    EXECUTE FUNCTION update_allowance_charge_code_timestamp();

-- Note: The actual data insertion (106 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (for reference - key codes):
/*
INSERT INTO allowance_charge_identification_code (code, name, description, category) VALUES
('1', 'Handling commission', 'Fee for the processing of documentary credit, collection and payment which are charged to the customer.', 'Documentary Credit Commission'),
('2', 'Amendment commission', 'Fee for amendments in documentary credit and collection business (not extensions and increases of documentary credits).', 'Documentary Credit Commission'),
('3', 'Acceptance commission', 'Fee for the acceptance of draft in documentary credit and collection business which are drawn on us (also to be seen as a kind of ''guarantee commission'').', 'Documentary Credit Commission'),
('60', 'Manufacturer''s consumer discount', 'A discount given by the manufacturer of goods to consumers.', 'Discount'),
('79', 'Freight charges', 'Charges paid for shipping goods.', 'Freight Charges'),
('PP001', 'เงินมัดจำ', 'Deposit (Thai extension)', 'Other'),
('PP002', 'เงินประกัน', 'Guarantee/Security deposit (Thai extension)', 'Other');
*/

-- Create views for code categories
CREATE VIEW allowance_charge_code_commissions AS
SELECT code, name, description, category
FROM allowance_charge_identification_code
WHERE category LIKE '%Commission%'
ORDER BY code;

CREATE VIEW allowance_charge_code_fees AS
SELECT code, name, description, category
FROM allowance_charge_identification_code
WHERE category LIKE '%Fee%'
ORDER BY code;

CREATE VIEW allowance_charge_code_charges AS
SELECT code, name, description, category
FROM allowance_charge_identification_code
WHERE category LIKE '%Charges%' OR category LIKE '%Charge'
ORDER BY code;

CREATE VIEW allowance_charge_code_discounts AS
SELECT code, name, description, category
FROM allowance_charge_identification_code
WHERE category LIKE '%Discount%' OR category LIKE '%Rebate%' OR category LIKE '%Allowance%'
ORDER BY code;

CREATE VIEW allowance_charge_code_standard AS
SELECT code, name, description, category
FROM allowance_charge_identification_code
WHERE is_standard_code = true
ORDER BY code;

CREATE VIEW allowance_charge_code_thai AS
SELECT code, name, description, category
FROM allowance_charge_identification_code
WHERE is_thai_extension = true
ORDER BY code;

COMMENT ON VIEW allowance_charge_code_commissions IS 'All commission-type codes (documentary credit, collection, etc.)';
COMMENT ON VIEW allowance_charge_code_fees IS 'All fee-type codes (processing, discrepancy, etc.)';
COMMENT ON VIEW allowance_charge_code_charges IS 'All charge-type codes (freight, packing, handling, etc.)';
COMMENT ON VIEW allowance_charge_code_discounts IS 'All discount/rebate/allowance codes';
COMMENT ON VIEW allowance_charge_code_standard IS 'Standard UN/CEFACT codes only';
COMMENT ON VIEW allowance_charge_code_thai IS 'Thai/ETDA extension codes only';

-- Create helper function to get allowance/charge name
CREATE OR REPLACE FUNCTION get_allowance_charge_name(charge_code VARCHAR(10))
RETURNS VARCHAR(500) AS $$
DECLARE
    charge_name VARCHAR(500);
BEGIN
    SELECT name INTO charge_name
    FROM allowance_charge_identification_code
    WHERE code = charge_code;

    RETURN charge_name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_allowance_charge_name(VARCHAR) IS 'Get allowance/charge name from code';

-- Create helper function to validate allowance/charge code
CREATE OR REPLACE FUNCTION is_valid_allowance_charge_code(charge_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM allowance_charge_identification_code
        WHERE code = charge_code
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION is_valid_allowance_charge_code(VARCHAR) IS 'Validate if allowance/charge code exists';

-- Create helper function to get allowance/charge category
CREATE OR REPLACE FUNCTION get_allowance_charge_category(charge_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    charge_category VARCHAR(50);
BEGIN
    SELECT category INTO charge_category
    FROM allowance_charge_identification_code
    WHERE code = charge_code;

    RETURN charge_category;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_allowance_charge_category(VARCHAR) IS 'Get allowance/charge category from code';

-- Create helper function to search allowance/charge codes by keyword
CREATE OR REPLACE FUNCTION search_allowance_charge_codes(search_term TEXT)
RETURNS TABLE(
    code VARCHAR(10),
    name VARCHAR(500),
    description TEXT,
    category VARCHAR(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        ac.code,
        ac.name,
        ac.description,
        ac.category
    FROM allowance_charge_identification_code ac
    WHERE
        to_tsvector('english', ac.name || ' ' || ac.description) @@ plainto_tsquery('english', search_term)
        OR ac.name ILIKE '%' || search_term || '%'
        OR ac.description ILIKE '%' || search_term || '%'
    ORDER BY ac.code;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION search_allowance_charge_codes(TEXT) IS 'Search allowance/charge codes by keyword in name or description';

-- Create summary view by category
CREATE VIEW allowance_charge_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    array_agg(code ORDER BY code) as codes
FROM allowance_charge_identification_code
GROUP BY category
ORDER BY code_count DESC, category;

COMMENT ON VIEW allowance_charge_code_category_summary IS 'Summary of allowance/charge codes grouped by category';
