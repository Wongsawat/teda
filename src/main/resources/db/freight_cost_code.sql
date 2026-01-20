-- UN/CEFACT Freight Cost Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_FreightCostCode_4.xsd
-- Standard: UN/CEFACT Recommendation 23, Version 4

CREATE TABLE freight_cost_code (
    code VARCHAR(6) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    category VARCHAR(100),
    code_group VARCHAR(3) GENERATED ALWAYS AS (SUBSTRING(code, 1, 3)) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_freight_code_format CHECK (code ~ '^[0-9]{6}$')
);;

-- Add comment to table
COMMENT ON TABLE freight_cost_code IS 'UN/CEFACT Recommendation 23 freight and shipping cost codes for e-Tax Invoice transportation charges';;

-- Add comments to columns
COMMENT ON COLUMN freight_cost_code.code IS 'Freight cost code (6 digits: 100000-609999)';;
COMMENT ON COLUMN freight_cost_code.name IS 'Description of the freight cost/charge';;
COMMENT ON COLUMN freight_cost_code.category IS 'High-level category (Basic Freight, Container Services, Terminal Charges, etc.)';;
COMMENT ON COLUMN freight_cost_code.code_group IS 'First 3 digits of code for grouping (100-609)';;

-- Create indexes for faster lookups
CREATE INDEX idx_freight_cost_code_name ON freight_cost_code USING GIN (to_tsvector('english', name));;
CREATE INDEX idx_freight_cost_code_category ON freight_cost_code(category);;
CREATE INDEX idx_freight_cost_code_group ON freight_cost_code(code_group);;
CREATE INDEX idx_freight_cost_code_name_pattern ON freight_cost_code(name text_pattern_ops);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_freight_cost_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_freight_cost_code_timestamp
    BEFORE UPDATE ON freight_cost_code
    FOR EACH ROW
    EXECUTE FUNCTION update_freight_cost_code_timestamp();;

-- Note: The actual data insertion (1,641 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (first few entries):
/*
INSERT INTO freight_cost_code (code, name, category) VALUES
('100000', 'FREIGHT CHARGES', 'General'),
('100999', 'All freight charges', 'General'),
('101000', 'BASIC FREIGHT', 'Basic Freight'),
('101002', 'Arbitrary basis freight', 'Basic Freight'),
('101003', 'Minimum freight', 'Basic Freight'),
('101006', 'Minimum freight LCL cargo', 'Basic Freight'),
('101007', 'Minimum freight FCL cargo', 'Basic Freight'),
-- ... (1,634 more records)
('609144', 'Sweeping 45 feet container', 'Container Services');;
*/

-- Create views for common freight cost categories
CREATE VIEW freight_cost_code_basic AS
SELECT code, name
FROM freight_cost_code
WHERE code_group = '101'
ORDER BY code;

CREATE VIEW freight_cost_code_container AS
SELECT code, name
FROM freight_cost_code
WHERE name ILIKE '%container%'
ORDER BY code;

CREATE VIEW freight_cost_code_dangerous_goods AS
SELECT code, name
FROM freight_cost_code
WHERE name ILIKE '%dangerous%' OR name ILIKE '%hazardous%'
ORDER BY code;

-- Create view for code group summary
CREATE VIEW freight_cost_code_group_summary AS
SELECT
    code_group,
    COUNT(*) as code_count,
    MIN(code) as min_code,
    MAX(code) as max_code,
    STRING_AGG(DISTINCT category, ', ') as categories
FROM freight_cost_code
GROUP BY code_group
ORDER BY code_group;

COMMENT ON VIEW freight_cost_code_basic IS 'Basic freight charges (code group 101)';;
COMMENT ON VIEW freight_cost_code_container IS 'All container-related freight charges';;
COMMENT ON VIEW freight_cost_code_dangerous_goods IS 'Dangerous/hazardous goods handling charges';;
COMMENT ON VIEW freight_cost_code_group_summary IS 'Summary of freight cost codes by 3-digit group';;

-- Create full-text search function
CREATE OR REPLACE FUNCTION search_freight_cost_code(search_term TEXT)
RETURNS TABLE (
    code VARCHAR(6),
    name VARCHAR(500),
    category VARCHAR(100),
    rank REAL
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        f.code,
        f.name,
        f.category,
        ts_rank(to_tsvector('english', f.name), plainto_tsquery('english', search_term)) as rank
    FROM freight_cost_code f
    WHERE to_tsvector('english', f.name) @@ plainto_tsquery('english', search_term)
    ORDER BY rank DESC, f.code
    LIMIT 50;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION search_freight_cost_code(TEXT) IS 'Full-text search freight cost codes by name';;
