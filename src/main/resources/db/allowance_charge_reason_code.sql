-- UN/CEFACT Allowance/Charge Reason Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_AllowanceChargeReasonCode_D15B.xsd
-- Standard: UN/CEFACT Code List 4465 (AllowanceChargeReasonCode), Version D15B

CREATE TABLE allowance_charge_reason_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_allowance_charge_reason_code_format CHECK (code ~ '^[0-9]+$|^[A-Z]+$')
);;

-- Add comment to table
COMMENT ON TABLE allowance_charge_reason_code IS 'UN/CEFACT allowance and charge reason codes explaining why adjustments were made to invoice amounts';;

-- Add comments to columns
COMMENT ON COLUMN allowance_charge_reason_code.code IS 'Reason code (numeric 1-104 or special code ZZZ)';;
COMMENT ON COLUMN allowance_charge_reason_code.name IS 'Name/title of the reason';;
COMMENT ON COLUMN allowance_charge_reason_code.description IS 'Detailed description of the reason for allowance or charge';;
COMMENT ON COLUMN allowance_charge_reason_code.category IS 'Category classification (Quality Issue, Delivery Issue, Administrative Error, etc.)';;

-- Create indexes for faster lookups
CREATE INDEX idx_allowance_charge_reason_code_name ON allowance_charge_reason_code(name);;
CREATE INDEX idx_allowance_charge_reason_code_category ON allowance_charge_reason_code(category);;

-- Create full-text search index
CREATE INDEX idx_allowance_charge_reason_code_description_fulltext
ON allowance_charge_reason_code USING gin(to_tsvector('english', description));;

CREATE INDEX idx_allowance_charge_reason_code_name_fulltext
ON allowance_charge_reason_code USING gin(to_tsvector('english', name));;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_allowance_charge_reason_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_allowance_charge_reason_code_timestamp
    BEFORE UPDATE ON allowance_charge_reason_code
    FOR EACH ROW
    EXECUTE FUNCTION update_allowance_charge_reason_code_timestamp();;

-- Note: The actual data insertion (105 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (for reference - key codes):
/*
INSERT INTO allowance_charge_reason_code (code, name, description, category) VALUES
('1', 'Agreed settlement', 'An adjustment made based on an agreement between partners.', 'Payment Terms'),
('2', 'Below specification goods', 'Goods of inferior quality.', 'Quality Issue'),
('3', 'Damaged goods', 'An adjustment due to the damage of goods.', 'Quality Issue'),
('4', 'Short delivery', 'An adjustment made because the delivered quantity was less than expected.', 'Delivery Issue'),
('9', 'Invoice error', 'Invoice not in accordance with the order.', 'Administrative Error'),
('11', 'Bank charges', 'Bank charges have been deducted from payment.', 'Financial Charges'),
('19', 'Trade discount', 'A general discount based on trading relationship.', 'Discount/Allowance'),
('ZZZ', 'Mutually defined', 'A code assigned within a code list to be used on an interim basis and as defined among trading partners until a precise code can be assigned to the code list.', 'Custom/Other');;
*/

-- Create views for reason categories
CREATE VIEW allowance_charge_reason_quality_issues AS
SELECT code, name, description
FROM allowance_charge_reason_code
WHERE category = 'Quality Issue'
ORDER BY code;

CREATE VIEW allowance_charge_reason_delivery_issues AS
SELECT code, name, description
FROM allowance_charge_reason_code
WHERE category = 'Delivery Issue'
ORDER BY code;

CREATE VIEW allowance_charge_reason_admin_errors AS
SELECT code, name, description
FROM allowance_charge_reason_code
WHERE category = 'Administrative Error'
ORDER BY code;

CREATE VIEW allowance_charge_reason_discounts AS
SELECT code, name, description
FROM allowance_charge_reason_code
WHERE category = 'Discount/Allowance'
ORDER BY code;

CREATE VIEW allowance_charge_reason_financial AS
SELECT code, name, description
FROM allowance_charge_reason_code
WHERE category = 'Financial Charges'
ORDER BY code;

CREATE VIEW allowance_charge_reason_claims AS
SELECT code, name, description
FROM allowance_charge_reason_code
WHERE category = 'Claims/Disputes'
ORDER BY code;

COMMENT ON VIEW allowance_charge_reason_quality_issues IS 'Reasons related to quality issues (damaged goods, below specification, etc.)';;
COMMENT ON VIEW allowance_charge_reason_delivery_issues IS 'Reasons related to delivery problems (short delivery, wrong delivery, returns)';;
COMMENT ON VIEW allowance_charge_reason_admin_errors IS 'Reasons related to administrative errors (invoice error, incorrect data, etc.)';;
COMMENT ON VIEW allowance_charge_reason_discounts IS 'Reasons related to discounts, rebates, and allowances';;
COMMENT ON VIEW allowance_charge_reason_financial IS 'Reasons related to financial charges (bank charges, commissions, etc.)';;
COMMENT ON VIEW allowance_charge_reason_claims IS 'Reasons related to claims and disputes (counter claims, offsets, etc.)';;

-- Create helper function to get reason name
CREATE OR REPLACE FUNCTION get_allowance_charge_reason_name(reason_code VARCHAR(10))
RETURNS VARCHAR(500) AS $$
DECLARE
    reason_name VARCHAR(500);
BEGIN
    SELECT name INTO reason_name
    FROM allowance_charge_reason_code
    WHERE code = reason_code;

    RETURN reason_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_allowance_charge_reason_name(VARCHAR) IS 'Get allowance/charge reason name from code';;

-- Create helper function to validate reason code
CREATE OR REPLACE FUNCTION is_valid_allowance_charge_reason_code(reason_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM allowance_charge_reason_code
        WHERE code = reason_code
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_allowance_charge_reason_code(VARCHAR) IS 'Validate if allowance/charge reason code exists';;

-- Create helper function to get reason category
CREATE OR REPLACE FUNCTION get_allowance_charge_reason_category(reason_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    reason_category VARCHAR(50);
BEGIN
    SELECT category INTO reason_category
    FROM allowance_charge_reason_code
    WHERE code = reason_code;

    RETURN reason_category;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_allowance_charge_reason_category(VARCHAR) IS 'Get allowance/charge reason category from code';;

-- Create helper function to search reason codes by keyword
CREATE OR REPLACE FUNCTION search_allowance_charge_reason_codes(search_term TEXT)
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
    FROM allowance_charge_reason_code ac
    WHERE
        to_tsvector('english', ac.name || ' ' || ac.description) @@ plainto_tsquery('english', search_term)
        OR ac.name ILIKE '%' || search_term || '%'
        OR ac.description ILIKE '%' || search_term || '%'
    ORDER BY ac.code;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION search_allowance_charge_reason_codes(TEXT) IS 'Search allowance/charge reason codes by keyword in name or description';;

-- Create summary view by category
CREATE VIEW allowance_charge_reason_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    array_agg(code ORDER BY code) as codes
FROM allowance_charge_reason_code
GROUP BY category
ORDER BY code_count DESC, category;

COMMENT ON VIEW allowance_charge_reason_code_category_summary IS 'Summary of allowance/charge reason codes grouped by category';;
