-- UN/CEFACT Address Type Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_AddressType_D14A.xsd
-- Standard: UN/CEFACT Code List 3131, Version D14A

CREATE TABLE address_type (
    code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_address_type_code_format CHECK (code ~ '^[1-3]$')
);

-- Add comment to table
COMMENT ON TABLE address_type IS 'UN/CEFACT address type codes for classifying addresses in e-Tax Invoice (postal, fiscal, physical)';

-- Add comments to columns
COMMENT ON COLUMN address_type.code IS 'Address type code (1-3)';
COMMENT ON COLUMN address_type.name IS 'Name of the address type';
COMMENT ON COLUMN address_type.description IS 'Detailed description of the address type usage';

-- Create indexes for faster lookups
CREATE INDEX idx_address_type_name ON address_type(name);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_address_type_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_address_type_timestamp
    BEFORE UPDATE ON address_type
    FOR EACH ROW
    EXECUTE FUNCTION update_address_type_timestamp();

-- Insert enumeration values from schema
INSERT INTO address_type (code, name, description) VALUES
('1',
 'Postal address',
 'The address is representing a postal address.'),

('2',
 'Fiscal address',
 'Identification of an address as required by fiscal administrations.'),

('3',
 'Physical address',
 'The address represents an actual physical location.');

-- Create helper function to get address type name
CREATE OR REPLACE FUNCTION get_address_type_name(type_code VARCHAR(2))
RETURNS VARCHAR(255) AS $$
DECLARE
    type_name VARCHAR(255);
BEGIN
    SELECT name INTO type_name
    FROM address_type
    WHERE code = type_code;

    RETURN type_name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_address_type_name(VARCHAR) IS 'Get address type name from code';

-- Create helper function to validate address type code
CREATE OR REPLACE FUNCTION is_valid_address_type(type_code VARCHAR(2))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM address_type
        WHERE code = type_code
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION is_valid_address_type(VARCHAR) IS 'Validate if address type code exists';
