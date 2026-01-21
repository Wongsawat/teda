-- ISO 639-1 Two-letter Language Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ISO_ISO2AlphaLanguageCode_2006-10-27.xsd
-- Standard: ISO 639-1 alpha-2

CREATE TABLE iso_language_code (
    code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code_upper VARCHAR(2) GENERATED ALWAYS AS (UPPER(code)) STORED,
    code_lower VARCHAR(2) GENERATED ALWAYS AS (LOWER(code)) STORED,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_language_code_format CHECK (code ~ '^[a-zA-Z]{2}$')
);;

-- Add comment to table
COMMENT ON TABLE iso_language_code IS 'ISO 639-1 alpha-2 two-letter language codes for e-Tax Invoice multilingual support';;

-- Add comments to columns
COMMENT ON COLUMN iso_language_code.code IS 'ISO 639-1 alpha-2 language code (2 letters, stored in lowercase)';;
COMMENT ON COLUMN iso_language_code.name IS 'Language name in English';;
COMMENT ON COLUMN iso_language_code.code_upper IS 'Uppercase version of code (generated)';;
COMMENT ON COLUMN iso_language_code.code_lower IS 'Lowercase version of code (generated)';;
COMMENT ON COLUMN iso_language_code.is_active IS 'True if the language code is currently active/valid';;

-- Create indexes for faster lookups
CREATE INDEX idx_iso_language_code_name ON iso_language_code(name);;
CREATE INDEX idx_iso_language_code_upper ON iso_language_code(code_upper);;
CREATE INDEX idx_iso_language_code_lower ON iso_language_code(code_lower);;
CREATE INDEX idx_iso_language_code_name_pattern ON iso_language_code(name text_pattern_ops);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_iso_language_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_iso_language_code_timestamp
    BEFORE UPDATE ON iso_language_code
    FOR EACH ROW
    EXECUTE FUNCTION update_iso_language_code_timestamp();;

-- Note: The actual data insertion (185 unique languages) should be done via a separate script
-- that extracts the enumeration values from the XSD file and deduplicates them

-- Sample data structure (for reference - first few entries):
/*
INSERT INTO iso_language_code (code, name, is_active) VALUES
('aa', 'Afar', true),
('ab', 'Abkhazian', true),
('ae', 'Avestan', true),
('af', 'Afrikaans', true),
('ak', 'Akan', true),
('en', 'English', true),
('th', 'Thai', true),
('zh', 'Chinese', true),
-- ... (177 more languages)
('zu', 'Zulu', true);;
*/

-- Create views for common language groups
CREATE VIEW iso_language_code_asean AS
SELECT code, name, code_upper, code_lower
FROM iso_language_code
WHERE code IN ('th', 'en', 'ms', 'id', 'vi', 'my', 'km', 'lo', 'tl')
  AND is_active = true
ORDER BY name;

CREATE VIEW iso_language_code_major_trading AS
SELECT code, name, code_upper, code_lower
FROM iso_language_code
WHERE code IN ('en', 'th', 'zh', 'ja', 'ko', 'de', 'fr', 'es', 'ar', 'ru')
  AND is_active = true
ORDER BY name;

CREATE VIEW iso_language_code_active AS
SELECT code, name, code_upper, code_lower
FROM iso_language_code
WHERE is_active = true
ORDER BY name;

COMMENT ON VIEW iso_language_code_asean IS 'ASEAN region languages (Thai, English, Malay, Indonesian, etc.)';;
COMMENT ON VIEW iso_language_code_major_trading IS 'Major trading partner languages';;
COMMENT ON VIEW iso_language_code_active IS 'All active ISO 639-1 language codes';;

-- Create function for case-insensitive language code lookup
CREATE OR REPLACE FUNCTION get_language_name(lang_code VARCHAR(2))
RETURNS VARCHAR(255) AS $$
DECLARE
    language_name VARCHAR(255);
BEGIN
    SELECT name INTO language_name
    FROM iso_language_code
    WHERE LOWER(code) = LOWER(lang_code) AND is_active = true;

    RETURN language_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_language_name(VARCHAR) IS 'Get language name from ISO 639-1 code (case-insensitive)';;

-- Create function to validate language code
CREATE OR REPLACE FUNCTION is_valid_language_code(lang_code VARCHAR(2))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM iso_language_code
        WHERE LOWER(code) = LOWER(lang_code) AND is_active = true
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_language_code(VARCHAR) IS 'Validate if language code exists in ISO 639-1 (case-insensitive)';;

-- Create function to normalize language code to lowercase
CREATE OR REPLACE FUNCTION normalize_language_code(lang_code VARCHAR(2))
RETURNS VARCHAR(2) AS $$
BEGIN
    RETURN LOWER(lang_code);
END;
$$ LANGUAGE plpgsql IMMUTABLE;

COMMENT ON FUNCTION normalize_language_code(VARCHAR) IS 'Normalize language code to lowercase standard format';;
