-- Thai Province Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ThaiISOCountrySubdivisionCode_1p0.xsd
-- Standard: Thai ISO 3166-2 Province Codes

CREATE TABLE thai_province_code (
    code VARCHAR(10) PRIMARY KEY,
    name_th VARCHAR(200) NOT NULL,
    name_en VARCHAR(200),
    region VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_province_code_format CHECK (code ~ '^[0-9]+$')
);;

-- Add comment to table
COMMENT ON TABLE thai_province_code IS 'Thai province codes based on ISO 3166-2:TH subdivision codes (77 provinces)';;

-- Add comments to columns
COMMENT ON COLUMN thai_province_code.code IS 'Province code (numeric, 2 digits)';;
COMMENT ON COLUMN thai_province_code.name_th IS 'Province name in Thai';;
COMMENT ON COLUMN thai_province_code.name_en IS 'Province name in English';;
COMMENT ON COLUMN thai_province_code.region IS 'Geographic region (Central, North, Northeast, South, East, West)';;
COMMENT ON COLUMN thai_province_code.is_active IS 'Active status';;

-- Create indexes for faster lookups
CREATE INDEX idx_thai_province_name ON thai_province_code(name_th);;
CREATE INDEX idx_thai_province_name_en ON thai_province_code(name_en);;
CREATE INDEX idx_thai_province_active ON thai_province_code(is_active);;
CREATE INDEX idx_thai_province_region ON thai_province_code(region);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_thai_province_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_thai_province_code_timestamp
    BEFORE UPDATE ON thai_province_code
    FOR EACH ROW
    EXECUTE FUNCTION update_thai_province_code_timestamp();;

-- Helper function to get province name
CREATE OR REPLACE FUNCTION get_province_name(province_code VARCHAR(10))
RETURNS VARCHAR(200) AS $$
DECLARE
    province_name VARCHAR(200);
BEGIN
    SELECT name_th INTO province_name
    FROM thai_province_code
    WHERE code = province_code AND is_active = true;

    RETURN province_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_province_name(VARCHAR) IS 'Get province Thai name from code';;

-- Helper function to validate province code
CREATE OR REPLACE FUNCTION is_valid_province_code(province_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM thai_province_code
        WHERE code = province_code AND is_active = true
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_province_code(VARCHAR) IS 'Validate if province code exists and is active';;

-- Create view for provinces by region
CREATE VIEW thai_province_by_region AS
SELECT
    region,
    COUNT(*) as province_count,
    array_agg(code ORDER BY code) as province_codes,
    array_agg(name_th ORDER BY code) as province_names
FROM thai_province_code
WHERE is_active = true
GROUP BY region
ORDER BY region;

COMMENT ON VIEW thai_province_by_region IS 'Thai provinces grouped by geographic region';;
