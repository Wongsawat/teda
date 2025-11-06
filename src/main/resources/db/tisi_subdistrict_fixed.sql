-- TISI 1099-2548 Subdistrict Name (Thai Tambon/Khwaeng) Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: TISICitySubDivisionName_1p0.xsd

CREATE TABLE tisi_subdistrict (
    code VARCHAR(6) PRIMARY KEY,
    name_th VARCHAR(255) NOT NULL,
    province_code VARCHAR(2) GENERATED ALWAYS AS (SUBSTRING(code, 1, 2)) STORED,
    city_code VARCHAR(4) GENERATED ALWAYS AS (SUBSTRING(code, 1, 4)) STORED,
    subdistrict_code VARCHAR(2) GENERATED ALWAYS AS (SUBSTRING(code, 5, 2)) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tisi_subdistrict_province
        FOREIGN KEY (province_code)
        REFERENCES thai_province_code(code)
        ON DELETE RESTRICT,
    CONSTRAINT fk_tisi_subdistrict_city
        FOREIGN KEY (city_code)
        REFERENCES tisi_city_name(code)
        ON DELETE RESTRICT
);

-- Add comment to table
COMMENT ON TABLE tisi_subdistrict IS 'TISI 1099-2548 standard Thai subdistrict names (ตำบล/แขวง) for e-Tax Invoice addresses';

-- Add comments to columns
COMMENT ON COLUMN tisi_subdistrict.code IS 'TISI 1099-2548 subdistrict code (6 digits: PPDDSS)';
COMMENT ON COLUMN tisi_subdistrict.name_th IS 'Thai name of subdistrict (ตำบล/แขวง)';
COMMENT ON COLUMN tisi_subdistrict.province_code IS 'Province code (first 2 digits)';
COMMENT ON COLUMN tisi_subdistrict.city_code IS 'City/District code (first 4 digits)';
COMMENT ON COLUMN tisi_subdistrict.subdistrict_code IS 'Subdistrict code (last 2 digits)';

-- Create indexes for faster lookups
CREATE INDEX idx_tisi_subdistrict_name_th ON tisi_subdistrict(name_th);
CREATE INDEX idx_tisi_subdistrict_province_code ON tisi_subdistrict(province_code);
CREATE INDEX idx_tisi_subdistrict_city_code ON tisi_subdistrict(city_code);
CREATE INDEX idx_tisi_subdistrict_code_pattern ON tisi_subdistrict(subdistrict_code);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_tisi_subdistrict_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_tisi_subdistrict_timestamp
    BEFORE UPDATE ON tisi_subdistrict
    FOR EACH ROW
    EXECUTE FUNCTION update_tisi_subdistrict_timestamp();

-- Create view for complete address hierarchy
CREATE VIEW v_thai_address_hierarchy AS
SELECT
    s.code as subdistrict_code,
    s.name_th as subdistrict_name_th,
    c.code as city_code,
    c.name_th as city_name_th,
    p.code as province_code,
    p.name_th as province_name_th,
    p.name_en as province_name_en,
    p.region
FROM tisi_subdistrict s
JOIN tisi_city_name c ON s.city_code = c.code
JOIN thai_province p ON s.province_code = p.code;

COMMENT ON VIEW v_thai_address_hierarchy IS 'Complete Thai address hierarchy: subdistrict > city/district > province';

-- Note: The actual data insertion (8,940 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file
-- Sample data structure (first few entries):
/*
INSERT INTO tisi_subdistrict (code, name_th) VALUES
('100101', 'พระบรมมหาราชวัง'),
('100102', 'วังบูรพาภิรมย์'),
('100103', 'วัดราชบพิธ'),
('100104', 'สำราญราษฎร์'),
-- ... (8,936 more records)
('961303', 'มะรือโบออก'),
('970596', 'โคกสอาด*');
*/
