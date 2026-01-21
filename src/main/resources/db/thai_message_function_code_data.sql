-- Thai Message Function Code Data
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: Thai_MessageFunctionCode_1p0.xsd
-- Active enumeration values from the XSD

INSERT INTO thai_message_function_code (code, description_en, description_th, document_type, category, active) VALUES
-- Debit Note - Goods
('DBNG01', 'Debit Note - Goods (Original)', 'ใบเพิ่มหนี้ - สินค้า (ต้นฉบับ)', 'DebitNote', 'Goods', true),
('DBNG02', 'Debit Note - Goods (Replacement)', 'ใบเพิ่มหนี้ - สินค้า (ฉบับแทน)', 'DebitNote', 'Goods', true),
('DBNG99', 'Debit Note - Goods (Other)', 'ใบเพิ่มหนี้ - สินค้า (อื่นๆ)', 'DebitNote', 'Goods', true),

-- Debit Note - Services
('DBNS01', 'Debit Note - Services (Original)', 'ใบเพิ่มหนี้ - บริการ (ต้นฉบับ)', 'DebitNote', 'Service', true),
('DBNS02', 'Debit Note - Services (Replacement)', 'ใบเพิ่มหนี้ - บริการ (ฉบับแทน)', 'DebitNote', 'Service', true),
('DBNS99', 'Debit Note - Services (Other)', 'ใบเพิ่มหนี้ - บริการ (อื่นๆ)', 'DebitNote', 'Service', true),

-- Credit Note - Goods
('CDNG01', 'Credit Note - Goods (Original)', 'ใบลดหนี้ - สินค้า (ต้นฉบับ)', 'CreditNote', 'Goods', true),
('CDNG02', 'Credit Note - Goods (Replacement)', 'ใบลดหนี้ - สินค้า (ฉบับแทน)', 'CreditNote', 'Goods', true),
('CDNG03', 'Credit Note - Goods (Cancellation)', 'ใบลดหนี้ - สินค้า (ยกเลิก)', 'CreditNote', 'Goods', true),
('CDNG04', 'Credit Note - Goods (Copy)', 'ใบลดหนี้ - สินค้า (สำเนา)', 'CreditNote', 'Goods', true),
('CDNG05', 'Credit Note - Goods (Addition)', 'ใบลดหนี้ - สินค้า (เพิ่มเติม)', 'CreditNote', 'Goods', true),
('CDNG99', 'Credit Note - Goods (Other)', 'ใบลดหนี้ - สินค้า (อื่นๆ)', 'CreditNote', 'Goods', true),

-- Credit Note - Services
('CDNS01', 'Credit Note - Services (Original)', 'ใบลดหนี้ - บริการ (ต้นฉบับ)', 'CreditNote', 'Service', true),
('CDNS02', 'Credit Note - Services (Replacement)', 'ใบลดหนี้ - บริการ (ฉบับแทน)', 'CreditNote', 'Service', true),
('CDNS03', 'Credit Note - Services (Cancellation)', 'ใบลดหนี้ - บริการ (ยกเลิก)', 'CreditNote', 'Service', true),
('CDNS04', 'Credit Note - Services (Copy)', 'ใบลดหนี้ - บริการ (สำเนา)', 'CreditNote', 'Service', true),
('CDNS99', 'Credit Note - Services (Other)', 'ใบลดหนี้ - บริการ (อื่นๆ)', 'CreditNote', 'Service', true),

-- Tax Invoice
('TIVC01', 'Tax Invoice (Original)', 'ใบกำกับภาษี (ต้นฉบับ)', 'TaxInvoice', 'Invoice', true),
('TIVC02', 'Tax Invoice (Replacement)', 'ใบกำกับภาษี (ฉบับแทน)', 'TaxInvoice', 'Invoice', true),
('TIVC99', 'Tax Invoice (Other)', 'ใบกำกับภาษี (อื่นๆ)', 'TaxInvoice', 'Invoice', true),

-- Receipt
('RCTC01', 'Receipt (Original)', 'ใบเสร็จรับเงิน (ต้นฉบับ)', 'Receipt', 'Receipt', true),
('RCTC02', 'Receipt (Replacement)', 'ใบเสร็จรับเงิน (ฉบับแทน)', 'Receipt', 'Receipt', true),
('RCTC03', 'Receipt (Copy)', 'ใบเสร็จรับเงิน (สำเนา)', 'Receipt', 'Receipt', true),
('RCTC04', 'Receipt (Cancellation)', 'ใบเสร็จรับเงิน (ยกเลิก)', 'Receipt', 'Receipt', true),
('RCTC99', 'Receipt (Other)', 'ใบเสร็จรับเงิน (อื่นๆ)', 'Receipt', 'Receipt', true);;

-- Create summary view by document type and category
CREATE VIEW thai_message_function_code_summary AS
SELECT
    document_type,
    category,
    COUNT(*) as code_count,
    STRING_AGG(code, ', ' ORDER BY code) as codes
FROM thai_message_function_code
WHERE active = true
GROUP BY document_type, category
ORDER BY document_type, category;

-- Create view for active codes only
CREATE VIEW thai_message_function_code_active AS
SELECT code, description_en, description_th, document_type, category
FROM thai_message_function_code
WHERE active = true
ORDER BY document_type, code;

COMMENT ON VIEW thai_message_function_code_summary IS 'Summary of Thai message function codes grouped by document type and category';
COMMENT ON VIEW thai_message_function_code_active IS 'All active Thai message function codes';
