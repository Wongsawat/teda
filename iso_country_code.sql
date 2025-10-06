-- ISO 3166-1 Two-letter Country Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd
-- Standard: ISO 3166-1 alpha-2

CREATE TABLE iso_country_code (
    code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_etda_extension BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_country_code_format CHECK (code ~ '^[A-Z]{2}$')
);

-- Add comment to table
COMMENT ON TABLE iso_country_code IS 'ISO 3166-1 alpha-2 two-letter country codes for e-Tax Invoice international trade';

-- Add comments to columns
COMMENT ON COLUMN iso_country_code.code IS 'ISO 3166-1 alpha-2 country code (2 uppercase letters)';
COMMENT ON COLUMN iso_country_code.name IS 'Official country name in English (uppercase)';
COMMENT ON COLUMN iso_country_code.description IS 'Additional information or notes about the code';
COMMENT ON COLUMN iso_country_code.is_etda_extension IS 'True if this is an ETDA custom extension (AN, KS, UN)';
COMMENT ON COLUMN iso_country_code.is_active IS 'True if the code is currently active/valid';

-- Create indexes for faster lookups
CREATE INDEX idx_iso_country_code_name ON iso_country_code(name);
CREATE INDEX idx_iso_country_code_is_etda_extension ON iso_country_code(is_etda_extension);
CREATE INDEX idx_iso_country_code_is_active ON iso_country_code(is_active);
CREATE INDEX idx_iso_country_code_name_lower ON iso_country_code(LOWER(name));

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_iso_country_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_iso_country_code_timestamp
    BEFORE UPDATE ON iso_country_code
    FOR EACH ROW
    EXECUTE FUNCTION update_iso_country_code_timestamp();

-- Note: The actual data insertion (252 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Insert ETDA custom extensions first (for reference)
INSERT INTO iso_country_code (code, name, description, is_etda_extension, is_active) VALUES
('AN', 'NETHERLANDS ANTILLES', 'This code is not listed in ISO 3166. ETDA has added for domestic use.', true, false),
('KS', 'KOSOVO', 'This code is not listed in ISO 3166. ETDA has added for domestic use.', true, true),
('UN', 'UNITED NATIONS', 'This code is not listed in ISO 3166. ETDA has added for domestic use.', true, true);

-- Sample standard ISO codes (to be completed by extraction script)
/*
INSERT INTO iso_country_code (code, name, description, is_etda_extension, is_active) VALUES
('AD', 'ANDORRA', NULL, false, true),
('AE', 'UNITED ARAB EMIRATES', NULL, false, true),
('AF', 'AFGHANISTAN', NULL, false, true),
('TH', 'THAILAND', NULL, false, true),
('US', 'UNITED STATES', NULL, false, true),
... (249 more records)
*/

-- Create views for different purposes
CREATE VIEW iso_country_code_active AS
SELECT code, name, description
FROM iso_country_code
WHERE is_active = true
ORDER BY name;

CREATE VIEW iso_country_code_standard AS
SELECT code, name, description
FROM iso_country_code
WHERE is_etda_extension = false
ORDER BY code;

CREATE VIEW iso_country_code_etda_extensions AS
SELECT code, name, description
FROM iso_country_code
WHERE is_etda_extension = true
ORDER BY code;

-- Create view for Thailand and common trading partners
CREATE VIEW iso_country_code_asean AS
SELECT code, name
FROM iso_country_code
WHERE code IN ('TH', 'BN', 'KH', 'ID', 'LA', 'MY', 'MM', 'PH', 'SG', 'VN')
  AND is_active = true
ORDER BY name;

COMMENT ON VIEW iso_country_code_active IS 'Active ISO 3166-1 country codes';
COMMENT ON VIEW iso_country_code_standard IS 'Standard ISO 3166-1 codes (excluding ETDA extensions)';
COMMENT ON VIEW iso_country_code_etda_extensions IS 'ETDA custom extension codes (AN, KS, UN)';
COMMENT ON VIEW iso_country_code_asean IS 'ASEAN member countries plus Thailand';

-- Create function for country code lookup
CREATE OR REPLACE FUNCTION get_country_name(country_code VARCHAR(2))
RETURNS VARCHAR(255) AS $$
DECLARE
    country_name VARCHAR(255);
BEGIN
    SELECT name INTO country_name
    FROM iso_country_code
    WHERE code = UPPER(country_code) AND is_active = true;

    RETURN country_name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_country_name(VARCHAR) IS 'Get country name from ISO 3166-1 alpha-2 code';
