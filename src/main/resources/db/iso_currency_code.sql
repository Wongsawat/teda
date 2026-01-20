-- ISO 4217 Three-letter Currency Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd
-- Standard: ISO 4217 alpha-3

CREATE TABLE iso_currency_code (
    code VARCHAR(3) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    numeric_code VARCHAR(3),
    minor_units INTEGER,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_currency_code_format CHECK (code ~ '^[A-Z]{3}$')
);;

-- Add comment to table
COMMENT ON TABLE iso_currency_code IS 'ISO 4217 alpha-3 three-letter currency codes for e-Tax Invoice monetary amounts';;

-- Add comments to columns
COMMENT ON COLUMN iso_currency_code.code IS 'ISO 4217 alpha-3 currency code (3 uppercase letters)';;
COMMENT ON COLUMN iso_currency_code.name IS 'Currency name in English';;
COMMENT ON COLUMN iso_currency_code.description IS 'Additional information about the currency (e.g., effective dates, special notes)';;
COMMENT ON COLUMN iso_currency_code.numeric_code IS 'ISO 4217 numeric code (3 digits)';;
COMMENT ON COLUMN iso_currency_code.minor_units IS 'Number of decimal places (e.g., 2 for cents, 0 for yen)';;
COMMENT ON COLUMN iso_currency_code.is_active IS 'True if the currency is currently active/valid';;

-- Create indexes for faster lookups
CREATE INDEX idx_iso_currency_code_name ON iso_currency_code(name);;
CREATE INDEX idx_iso_currency_code_is_active ON iso_currency_code(is_active);;
CREATE INDEX idx_iso_currency_code_numeric ON iso_currency_code(numeric_code);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_iso_currency_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_iso_currency_code_timestamp
    BEFORE UPDATE ON iso_currency_code
    FOR EACH ROW
    EXECUTE FUNCTION update_iso_currency_code_timestamp();;

-- Note: The actual data insertion (172 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (for reference - key currencies):
/*
INSERT INTO iso_currency_code (code, name, description, numeric_code, minor_units, is_active) VALUES
('THB', 'Baht', NULL, '764', 2, true),
('USD', 'US Dollar', NULL, '840', 2, true),
('EUR', 'Euro', NULL, '978', 2, true),
('JPY', 'Yen', NULL, '392', 0, true),
('CNY', 'Yuan Renminbi', NULL, '156', 2, true),
('GBP', 'Pound Sterling', NULL, '826', 2, true),
('SGD', 'Singapore Dollar', NULL, '702', 2, true),
('MYR', 'Malaysian Ringgit', NULL, '458', 2, true),
('IDR', 'Rupiah', NULL, '360', 2, true),
('ZWL', 'Zimbabwe Dollar', 'effective 1 February 2009', '932', 2, true);;
*/

-- Create views for currency categories
CREATE VIEW iso_currency_code_major AS
SELECT code, name, description, numeric_code, minor_units
FROM iso_currency_code
WHERE code IN ('USD', 'EUR', 'JPY', 'GBP', 'CNY', 'CHF', 'CAD', 'AUD')
  AND is_active = true
ORDER BY code;

CREATE VIEW iso_currency_code_asean AS
SELECT code, name, description, numeric_code, minor_units
FROM iso_currency_code
WHERE code IN ('THB', 'BND', 'KHR', 'IDR', 'LAK', 'MYR', 'MMK', 'PHP', 'SGD', 'VND')
  AND is_active = true
ORDER BY code;

CREATE VIEW iso_currency_code_thai_trading AS
SELECT code, name, description, numeric_code, minor_units
FROM iso_currency_code
WHERE code IN ('THB', 'USD', 'EUR', 'JPY', 'CNY', 'GBP', 'SGD', 'MYR', 'IDR', 'VND',
               'KRW', 'HKD', 'TWD', 'AUD', 'INR', 'CHF')
  AND is_active = true
ORDER BY code;

CREATE VIEW iso_currency_code_active AS
SELECT code, name, description, numeric_code, minor_units
FROM iso_currency_code
WHERE is_active = true
ORDER BY code;

COMMENT ON VIEW iso_currency_code_major IS 'Major world reserve currencies';;
COMMENT ON VIEW iso_currency_code_asean IS 'ASEAN member country currencies';;
COMMENT ON VIEW iso_currency_code_thai_trading IS 'Common currencies used in Thai international trade';;
COMMENT ON VIEW iso_currency_code_active IS 'All active ISO 4217 currency codes';;

-- Create helper function to get currency name
CREATE OR REPLACE FUNCTION get_currency_name(currency_code VARCHAR(3))
RETURNS VARCHAR(255) AS $$
DECLARE
    currency_name VARCHAR(255);
BEGIN
    SELECT name INTO currency_name
    FROM iso_currency_code
    WHERE code = UPPER(currency_code) AND is_active = true;

    RETURN currency_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_currency_name(VARCHAR) IS 'Get currency name from ISO 4217 code';;

-- Create helper function to validate currency code
CREATE OR REPLACE FUNCTION is_valid_currency_code(currency_code VARCHAR(3))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM iso_currency_code
        WHERE code = UPPER(currency_code) AND is_active = true
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_currency_code(VARCHAR) IS 'Validate if currency code exists in ISO 4217';;

-- Create helper function to get minor units (decimal places)
CREATE OR REPLACE FUNCTION get_currency_minor_units(currency_code VARCHAR(3))
RETURNS INTEGER AS $$
DECLARE
    units INTEGER;
BEGIN
    SELECT minor_units INTO units
    FROM iso_currency_code
    WHERE code = UPPER(currency_code) AND is_active = true;

    RETURN COALESCE(units, 2); -- Default to 2 decimal places if not found
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_currency_minor_units(VARCHAR) IS 'Get number of decimal places for a currency (e.g., 2 for USD, 0 for JPY)';;

-- Create helper function to format amount with currency
CREATE OR REPLACE FUNCTION format_amount_with_currency(
    amount NUMERIC,
    currency_code VARCHAR(3)
)
RETURNS TEXT AS $$
DECLARE
    currency_name VARCHAR(255);
    minor_units INTEGER;
    formatted_amount TEXT;
BEGIN
    SELECT name, COALESCE(minor_units, 2)
    INTO currency_name, minor_units
    FROM iso_currency_code
    WHERE code = UPPER(currency_code) AND is_active = true;

    IF currency_name IS NULL THEN
        RETURN amount::TEXT || ' ' || currency_code;
    END IF;

    formatted_amount := TO_CHAR(amount, 'FM999,999,999,990.' || REPEAT('0', minor_units));;

    RETURN formatted_amount || ' ' || currency_code || ' (' || currency_name || ')';
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION format_amount_with_currency(NUMERIC, VARCHAR) IS 'Format monetary amount with currency code and name';;
