-- UNECE Reference Type Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_ReferenceTypeCode_D14A.xsd
-- Standard: UN/CEFACT Code List 61153

CREATE TABLE unece_reference_type_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    description TEXT,
    is_etda_extension BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_reference_code_format CHECK (code ~ '^[A-Z0-9]+$')
);

-- Add comment to table
COMMENT ON TABLE unece_reference_type_code IS 'UN/CEFACT Reference Type Code (Code List 61153) for e-Tax Invoice reference documents';

-- Add comments to columns
COMMENT ON COLUMN unece_reference_type_code.code IS 'UN/CEFACT reference type code (alphanumeric, typically 3 characters)';
COMMENT ON COLUMN unece_reference_type_code.name IS 'Official name of the reference type';
COMMENT ON COLUMN unece_reference_type_code.description IS 'Detailed description of the reference type usage';
COMMENT ON COLUMN unece_reference_type_code.is_etda_extension IS 'True if this is an ETDA custom extension for Thai e-Tax Invoice (80, 81, 380, 388, T01, T02, T03, T04, T05, T06, T07)';
COMMENT ON COLUMN unece_reference_type_code.is_active IS 'True if the code is currently active/valid';

-- Create indexes for faster lookups
CREATE INDEX idx_unece_reference_type_code_name ON unece_reference_type_code(name);
CREATE INDEX idx_unece_reference_type_code_is_etda_extension ON unece_reference_type_code(is_etda_extension);
CREATE INDEX idx_unece_reference_type_code_is_active ON unece_reference_type_code(is_active);
CREATE INDEX idx_unece_reference_type_code_name_lower ON unece_reference_type_code(LOWER(name));

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_unece_reference_type_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_unece_reference_type_code_timestamp
    BEFORE UPDATE ON unece_reference_type_code
    FOR EACH ROW
    EXECUTE FUNCTION update_unece_reference_type_code_timestamp();

-- Note: The actual data insertion (798 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file
--
-- Breakdown:
--   Standard UN/CEFACT codes: 787 records
--   ETDA Thai extensions: 11 records (80, 81, 380, 388, T01, T02, T03, T04, T05, T06, T07)

-- Create views for different purposes
CREATE VIEW unece_reference_type_code_active AS
SELECT code, name, description, is_etda_extension
FROM unece_reference_type_code
WHERE is_active = true
ORDER BY code;

CREATE VIEW unece_reference_type_code_standard AS
SELECT code, name, description
FROM unece_reference_type_code
WHERE is_etda_extension = false
ORDER BY code;

CREATE VIEW unece_reference_type_code_etda_extensions AS
SELECT code, name, description
FROM unece_reference_type_code
WHERE is_etda_extension = true
ORDER BY code;

-- Create view for common invoice-related reference codes
CREATE VIEW unece_reference_type_code_invoice AS
SELECT code, name, description
FROM unece_reference_type_code
WHERE is_active = true
  AND (
    name ILIKE '%invoice%' OR
    name ILIKE '%credit note%' OR
    name ILIKE '%debit note%' OR
    name ILIKE '%order%' OR
    name ILIKE '%contract%'
  )
ORDER BY code;

-- Create view for financial/payment related codes
CREATE VIEW unece_reference_type_code_financial AS
SELECT code, name, description
FROM unece_reference_type_code
WHERE is_active = true
  AND (
    name ILIKE '%payment%' OR
    name ILIKE '%bank%' OR
    name ILIKE '%credit%' OR
    name ILIKE '%account%' OR
    name ILIKE '%financial%'
  )
ORDER BY code;

COMMENT ON VIEW unece_reference_type_code_active IS 'Active UN/CEFACT reference type codes';
COMMENT ON VIEW unece_reference_type_code_standard IS 'Standard UN/CEFACT codes (excluding ETDA extensions)';
COMMENT ON VIEW unece_reference_type_code_etda_extensions IS 'ETDA Thai e-Tax Invoice extension codes (11 codes: 80, 81, 380, 388, T01-T07)';
COMMENT ON VIEW unece_reference_type_code_invoice IS 'Invoice and order related reference type codes';
COMMENT ON VIEW unece_reference_type_code_financial IS 'Financial and payment related reference type codes';

-- Create function for reference type code lookup
CREATE OR REPLACE FUNCTION get_reference_type_name(ref_code VARCHAR(10))
RETURNS VARCHAR(500) AS $$
DECLARE
    ref_name VARCHAR(500);
BEGIN
    SELECT name INTO ref_name
    FROM unece_reference_type_code
    WHERE code = UPPER(ref_code) AND is_active = true;

    RETURN ref_name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_reference_type_name(VARCHAR) IS 'Get reference type name from UN/CEFACT reference type code';
