-- Thai Province (ISO Country Subdivision Code) Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ThaiISOCountrySubdivisionCode_1p0.xsd
-- Standard: ISO 3166-2:TH

CREATE TABLE thai_province (
    code VARCHAR(2) PRIMARY KEY,
    name_th VARCHAR(255) NOT NULL,
    name_en VARCHAR(255),
    region VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add comment to table
COMMENT ON TABLE thai_province IS 'Thai provinces based on ISO 3166-2:TH standard for e-Tax Invoice addresses';

-- Add comments to columns
COMMENT ON COLUMN thai_province.code IS 'ISO 3166-2:TH province code (2 digits)';
COMMENT ON COLUMN thai_province.name_th IS 'Thai name of province (จังหวัด)';
COMMENT ON COLUMN thai_province.name_en IS 'English name of province (optional)';
COMMENT ON COLUMN thai_province.region IS 'Geographic region (Central, Northeast, Northern, Western, Southern)';

-- Create indexes for faster lookups
CREATE INDEX idx_thai_province_name_th ON thai_province(name_th);
CREATE INDEX idx_thai_province_name_en ON thai_province(name_en);
CREATE INDEX idx_thai_province_region ON thai_province(region);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_thai_province_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_thai_province_timestamp
    BEFORE UPDATE ON thai_province
    FOR EACH ROW
    EXECUTE FUNCTION update_thai_province_timestamp();

-- Insert enumeration values from schema (77 provinces)
INSERT INTO thai_province (code, name_th, name_en, region) VALUES
-- Central Region (10-27)
('10', 'กรุงเทพมหานคร', 'Bangkok', 'Central'),
('11', 'สมุทรปราการ', 'Samut Prakan', 'Central'),
('12', 'นนทบุรี', 'Nonthaburi', 'Central'),
('13', 'ปทุมธานี', 'Pathum Thani', 'Central'),
('14', 'พระนครศรีอยุธยา', 'Phra Nakhon Si Ayutthaya', 'Central'),
('15', 'อ่างทอง', 'Ang Thong', 'Central'),
('16', 'ลพบุรี', 'Lopburi', 'Central'),
('17', 'สิงห์บุรี', 'Sing Buri', 'Central'),
('18', 'ชัยนาท', 'Chai Nat', 'Central'),
('19', 'สระบุรี', 'Saraburi', 'Central'),
('20', 'ชลบุรี', 'Chon Buri', 'Eastern'),
('21', 'ระยอง', 'Rayong', 'Eastern'),
('22', 'จันทบุรี', 'Chanthaburi', 'Eastern'),
('23', 'ตราด', 'Trat', 'Eastern'),
('24', 'ฉะเชิงเทรา', 'Chachoengsao', 'Eastern'),
('25', 'ปราจีนบุรี', 'Prachin Buri', 'Eastern'),
('26', 'นครนายก', 'Nakhon Nayok', 'Central'),
('27', 'สระแก้ว', 'Sa Kaeo', 'Eastern'),

-- Northeast Region (30-49)
('30', 'นครราชสีมา', 'Nakhon Ratchasima', 'Northeast'),
('31', 'บุรีรัมย์', 'Buriram', 'Northeast'),
('32', 'สุรินทร์', 'Surin', 'Northeast'),
('33', 'ศรีสะเกษ', 'Si Sa Ket', 'Northeast'),
('34', 'อุบลราชธานี', 'Ubon Ratchathani', 'Northeast'),
('35', 'ยโสธร', 'Yasothon', 'Northeast'),
('36', 'ชัยภูมิ', 'Chaiyaphum', 'Northeast'),
('37', 'อำนาจเจริญ', 'Amnat Charoen', 'Northeast'),
('38', 'บึงกาฬ', 'Bueng Kan', 'Northeast'),
('39', 'หนองบัวลำภู', 'Nong Bua Lam Phu', 'Northeast'),
('40', 'ขอนแก่น', 'Khon Kaen', 'Northeast'),
('41', 'อุดรธานี', 'Udon Thani', 'Northeast'),
('42', 'เลย', 'Loei', 'Northeast'),
('43', 'หนองคาย', 'Nong Khai', 'Northeast'),
('44', 'มหาสารคาม', 'Maha Sarakham', 'Northeast'),
('45', 'ร้อยเอ็ด', 'Roi Et', 'Northeast'),
('46', 'กาฬสินธุ์', 'Kalasin', 'Northeast'),
('47', 'สกลนคร', 'Sakon Nakhon', 'Northeast'),
('48', 'นครพนม', 'Nakhon Phanom', 'Northeast'),
('49', 'มุกดาหาร', 'Mukdahan', 'Northeast'),

-- Northern Region (50-58)
('50', 'เชียงใหม่', 'Chiang Mai', 'Northern'),
('51', 'ลำพูน', 'Lamphun', 'Northern'),
('52', 'ลำปาง', 'Lampang', 'Northern'),
('53', 'อุตรดิตถ์', 'Uttaradit', 'Northern'),
('54', 'แพร่', 'Phrae', 'Northern'),
('55', 'น่าน', 'Nan', 'Northern'),
('56', 'พะเยา', 'Phayao', 'Northern'),
('57', 'เชียงราย', 'Chiang Rai', 'Northern'),
('58', 'แม่ฮ่องสอน', 'Mae Hong Son', 'Northern'),

-- Western Region (60-67)
('60', 'นครสวรรค์', 'Nakhon Sawan', 'Northern'),
('61', 'อุทัยธานี', 'Uthai Thani', 'Western'),
('62', 'กำแพงเพชร', 'Kamphaeng Phet', 'Northern'),
('63', 'ตาก', 'Tak', 'Western'),
('64', 'สุโขทัย', 'Sukhothai', 'Northern'),
('65', 'พิษณุโลก', 'Phitsanulok', 'Northern'),
('66', 'พิจิตร', 'Phichit', 'Northern'),
('67', 'เพชรบูรณ์', 'Phetchabun', 'Northern'),

-- Central-West Region (70-77)
('70', 'ราชบุรี', 'Ratchaburi', 'Western'),
('71', 'กาญจนบุรี', 'Kanchanaburi', 'Western'),
('72', 'สุพรรณบุรี', 'Suphan Buri', 'Central'),
('73', 'นครปฐม', 'Nakhon Pathom', 'Central'),
('74', 'สมุทรสาคร', 'Samut Sakhon', 'Central'),
('75', 'สมุทรสงคราม', 'Samut Songkhram', 'Central'),
('76', 'เพชรบุรี', 'Phetchaburi', 'Western'),
('77', 'ประจวบคีรีขันธ์', 'Prachuap Khiri Khan', 'Western'),

-- Southern Region (80-96)
('80', 'นครศรีธรรมราช', 'Nakhon Si Thammarat', 'Southern'),
('81', 'กระบี่', 'Krabi', 'Southern'),
('82', 'พังงา', 'Phang Nga', 'Southern'),
('83', 'ภูเก็ต', 'Phuket', 'Southern'),
('84', 'สุราษฎร์ธานี', 'Surat Thani', 'Southern'),
('85', 'ระนอง', 'Ranong', 'Southern'),
('86', 'ชุมพร', 'Chumphon', 'Southern'),
('90', 'สงขลา', 'Songkhla', 'Southern'),
('91', 'สตูล', 'Satun', 'Southern'),
('92', 'ตรัง', 'Trang', 'Southern'),
('93', 'พัทลุง', 'Phatthalung', 'Southern'),
('94', 'ปัตตานี', 'Pattani', 'Southern'),
('95', 'ยะลา', 'Yala', 'Southern'),
('96', 'นราธิวาส', 'Narathiwat', 'Southern'),

-- Reserved
('97', '', NULL, NULL);

-- Create view for active provinces (excluding reserved codes)
CREATE VIEW thai_province_active AS
SELECT * FROM thai_province WHERE code != '97';

COMMENT ON VIEW thai_province_active IS 'Active Thai provinces excluding reserved codes';
