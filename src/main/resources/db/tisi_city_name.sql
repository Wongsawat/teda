-- TISI 1099-2548 City Name (Thai District/City) Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: TISICityName_1p0.xsd

-- Drop existing objects for clean recreation
DROP FUNCTION IF EXISTS update_tisi_city_name_timestamp() CASCADE;;
DROP TABLE IF EXISTS tisi_city_name CASCADE;;

CREATE TABLE tisi_city_name (
    code VARCHAR(4) PRIMARY KEY,
    name_th VARCHAR(255) NOT NULL,
    province_code VARCHAR(2) GENERATED ALWAYS AS (SUBSTRING(code, 1, 2)) STORED,
    district_code VARCHAR(2) GENERATED ALWAYS AS (SUBSTRING(code, 3, 2)) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);;

-- Add comment to table
COMMENT ON TABLE tisi_city_name IS 'TISI 1099-2548 standard Thai city/district names for e-Tax Invoice addresses';;

-- Add comments to columns
COMMENT ON COLUMN tisi_city_name.code IS 'TISI 1099-2548 city/district code (4 digits)';;
COMMENT ON COLUMN tisi_city_name.name_th IS 'Thai name of city/district (เขต/อำเภอ/เทศบาล)';;
COMMENT ON COLUMN tisi_city_name.province_code IS 'Province code (first 2 digits of code)';;
COMMENT ON COLUMN tisi_city_name.district_code IS 'District code (last 2 digits of code)';;

-- Create indexes for faster lookups
CREATE INDEX idx_tisi_city_name_name_th ON tisi_city_name(name_th);;
CREATE INDEX idx_tisi_city_name_province_code ON tisi_city_name(province_code);;
CREATE INDEX idx_tisi_city_name_district_code ON tisi_city_name(district_code);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_tisi_city_name_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_tisi_city_name_timestamp
    BEFORE UPDATE ON tisi_city_name
    FOR EACH ROW
    EXECUTE FUNCTION update_tisi_city_name_timestamp();;

-- Note: The actual data insertion (958 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file
-- Sample data structure (first few entries):
/*
INSERT INTO tisi_city_name (code, name_th) VALUES
('1001', 'เขตพระนคร'),
('1002', 'เขตดุสิต'),
('1003', 'เขตหนองจอก'),
('1004', 'เขตบางรัก'),
('1005', 'เขตบางเขน'),
-- ... (953 more records)
('9613', 'เจาะไอร้อง'),
('9705', '');;
*/
