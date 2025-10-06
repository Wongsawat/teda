-- Thai Message Function Code INSERT statements
-- Generated from: Thai_MessageFunctionCode_1p0.xsd
-- Schema: urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560
-- Total codes: 24

-- Note: This XSD only contains enumeration values without descriptions
-- Descriptions are based on the thai_message_function_code_data.sql reference

INSERT INTO thai_message_function_code (code, description_en, description_th, document_type, category) VALUES
-- Debit Note - Goods (3 codes)
('DBNG01', 'Debit Note - Goods (Original)', 'ใบเพิ่มหนี้ - สินค้า (ต้นฉบับ)', 'DebitNote', 'Goods'),
('DBNG02', 'Debit Note - Goods (Replacement)', 'ใบเพิ่มหนี้ - สินค้า (ฉบับแทน)', 'DebitNote', 'Goods'),
('DBNG99', 'Debit Note - Goods (Other)', 'ใบเพิ่มหนี้ - สินค้า (อื่นๆ)', 'DebitNote', 'Goods'),

-- Debit Note - Services (3 codes)
('DBNS01', 'Debit Note - Services (Original)', 'ใบเพิ่มหนี้ - บริการ (ต้นฉบับ)', 'DebitNote', 'Service'),
('DBNS02', 'Debit Note - Services (Replacement)', 'ใบเพิ่มหนี้ - บริการ (ฉบับแทน)', 'DebitNote', 'Service'),
('DBNS99', 'Debit Note - Services (Other)', 'ใบเพิ่มหนี้ - บริการ (อื่นๆ)', 'DebitNote', 'Service'),

-- Credit Note - Goods (6 codes)
('CDNG01', 'Credit Note - Goods (Original)', 'ใบลดหนี้ - สินค้า (ต้นฉบับ)', 'CreditNote', 'Goods'),
('CDNG02', 'Credit Note - Goods (Replacement)', 'ใบลดหนี้ - สินค้า (ฉบับแทน)', 'CreditNote', 'Goods'),
('CDNG03', 'Credit Note - Goods (Cancellation)', 'ใบลดหนี้ - สินค้า (ยกเลิก)', 'CreditNote', 'Goods'),
('CDNG04', 'Credit Note - Goods (Copy)', 'ใบลดหนี้ - สินค้า (สำเนา)', 'CreditNote', 'Goods'),
('CDNG05', 'Credit Note - Goods (Addition)', 'ใบลดหนี้ - สินค้า (เพิ่มเติม)', 'CreditNote', 'Goods'),
('CDNG99', 'Credit Note - Goods (Other)', 'ใบลดหนี้ - สินค้า (อื่นๆ)', 'CreditNote', 'Goods'),

-- Credit Note - Services (5 codes)
('CDNS01', 'Credit Note - Services (Original)', 'ใบลดหนี้ - บริการ (ต้นฉบับ)', 'CreditNote', 'Service'),
('CDNS02', 'Credit Note - Services (Replacement)', 'ใบลดหนี้ - บริการ (ฉบับแทน)', 'CreditNote', 'Service'),
('CDNS03', 'Credit Note - Services (Cancellation)', 'ใบลดหนี้ - บริการ (ยกเลิก)', 'CreditNote', 'Service'),
('CDNS04', 'Credit Note - Services (Copy)', 'ใบลดหนี้ - บริการ (สำเนา)', 'CreditNote', 'Service'),
('CDNS99', 'Credit Note - Services (Other)', 'ใบลดหนี้ - บริการ (อื่นๆ)', 'CreditNote', 'Service'),

-- Tax Invoice (3 codes)
('TIVC01', 'Tax Invoice (Original)', 'ใบกำกับภาษี (ต้นฉบับ)', 'TaxInvoice', 'Invoice'),
('TIVC02', 'Tax Invoice (Replacement)', 'ใบกำกับภาษี (ฉบับแทน)', 'TaxInvoice', 'Invoice'),
('TIVC99', 'Tax Invoice (Other)', 'ใบกำกับภาษี (อื่นๆ)', 'TaxInvoice', 'Invoice'),

-- Receipt (5 codes)
('RCTC01', 'Receipt (Original)', 'ใบเสร็จรับเงิน (ต้นฉบับ)', 'Receipt', 'Receipt'),
('RCTC02', 'Receipt (Replacement)', 'ใบเสร็จรับเงิน (ฉบับแทน)', 'Receipt', 'Receipt'),
('RCTC03', 'Receipt (Copy)', 'ใบเสร็จรับเงิน (สำเนา)', 'Receipt', 'Receipt'),
('RCTC04', 'Receipt (Cancellation)', 'ใบเสร็จรับเงิน (ยกเลิก)', 'Receipt', 'Receipt'),
('RCTC99', 'Receipt (Other)', 'ใบเสร็จรับเงิน (อื่นๆ)', 'Receipt', 'Receipt');

-- Code Structure Analysis:
-- PREFIX (4 chars) - Document Type + Category:
--   DBNG - Debit Note Goods
--   DBNS - Debit Note Services
--   CDNG - Credit Note Goods
--   CDNS - Credit Note Services
--   TIVC - Tax Invoice
--   RCTC - Receipt
--
-- SUFFIX (2 digits) - Function:
--   01 - Original (ต้นฉบับ)
--   02 - Replacement (ฉบับแทน)
--   03 - Cancellation (ยกเลิก) - Credit Note & Receipt only
--   04 - Copy (สำเนา) - Credit Note & Receipt only
--   05 - Addition (เพิ่มเติม) - Credit Note Goods only
--   99 - Other (อื่นๆ)

-- Total breakdown:
-- Debit Note Goods:     3 codes (DBNG01, DBNG02, DBNG99)
-- Debit Note Services:  3 codes (DBNS01, DBNS02, DBNS99)
-- Credit Note Goods:    6 codes (CDNG01-CDNG05, CDNG99)
-- Credit Note Services: 5 codes (CDNS01-CDNS04, CDNS99)
-- Tax Invoice:          3 codes (TIVC01, TIVC02, TIVC99)
-- Receipt:              5 codes (RCTC01-RCTC04, RCTC99)
-- ----------------
-- TOTAL:               24 codes
