-- UN/CEFACT Payment Terms Type Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_PaymentTermsTypeCode_D14A.xsd
-- Standard: UN/CEFACT Code List 4279 (PaymentTermsTypeCode), Version D14A

CREATE TABLE payment_terms_type_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    is_immediate BOOLEAN DEFAULT false,
    is_deferred BOOLEAN DEFAULT false,
    has_discount BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_payment_terms_type_code_format CHECK (code ~ '^[0-9]+$|^[A-Z]+$')
);;

-- Add comment to table
COMMENT ON TABLE payment_terms_type_code IS 'UN/CEFACT payment terms type codes specifying conditions and timing of payment in commercial transactions';;

-- Add comments to columns
COMMENT ON COLUMN payment_terms_type_code.code IS 'Payment terms type code (numeric 1-78 or ZZZ for mutually defined)';;
COMMENT ON COLUMN payment_terms_type_code.name IS 'Name/title of the payment terms type';;
COMMENT ON COLUMN payment_terms_type_code.description IS 'Detailed description of the payment terms and conditions';;
COMMENT ON COLUMN payment_terms_type_code.category IS 'Payment terms category (Standard, Immediate, Deferred, Discount, etc.)';;
COMMENT ON COLUMN payment_terms_type_code.is_immediate IS 'True if payment is required immediately (instant, COD, on receipt)';;
COMMENT ON COLUMN payment_terms_type_code.is_deferred IS 'True if payment is deferred/extended beyond normal due date';;
COMMENT ON COLUMN payment_terms_type_code.has_discount IS 'True if the terms include discount provisions';;

-- Create indexes for faster lookups
CREATE INDEX idx_payment_terms_type_code_name ON payment_terms_type_code(name);;
CREATE INDEX idx_payment_terms_type_code_category ON payment_terms_type_code(category);;
CREATE INDEX idx_payment_terms_type_code_is_immediate ON payment_terms_type_code(is_immediate);;
CREATE INDEX idx_payment_terms_type_code_is_deferred ON payment_terms_type_code(is_deferred);;

-- Create full-text search index
CREATE INDEX idx_payment_terms_type_code_description_fulltext
ON payment_terms_type_code USING gin(to_tsvector('english', description));;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_payment_terms_type_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_payment_terms_type_code_timestamp
    BEFORE UPDATE ON payment_terms_type_code
    FOR EACH ROW
    EXECUTE FUNCTION update_payment_terms_type_code_timestamp();;

-- Note: The actual data insertion (79 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (for reference - key codes):
/*
INSERT INTO payment_terms_type_code (code, name, description, category, is_immediate, is_deferred, has_discount) VALUES
('1', 'Basic', 'Payment conditions normally applied.', 'Standard Terms', false, false, false),
('10', 'Instant', 'Payment is due on receipt of invoice.', 'Immediate Payment', true, false, false),
('22', 'Discount', 'Payment discount terms are set forth in the information value.', 'Discount Terms', false, false, true),
('4', 'Deferred', 'Payments are deferred beyond the normal due date.', 'Deferred Payment', false, true, false),
('52', 'Cash On Delivery (COD)', 'The payment terms specify that the goods must be paid for on delivery.', 'Immediate Payment', true, false, false),
('ZZZ', 'Mutually defined', 'A code assigned within a code list to be used on an interim basis and as defined among trading partners until a precise code can be assigned to the code list.', 'Custom Terms', false, false, false);;
*/

-- Create views for payment terms categories
CREATE VIEW payment_terms_type_immediate AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Immediate Payment' OR is_immediate = true
ORDER BY code;

CREATE VIEW payment_terms_type_deferred AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Deferred Payment' OR is_deferred = true
ORDER BY code;

CREATE VIEW payment_terms_type_discount AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Discount Terms' OR has_discount = true
ORDER BY code;

CREATE VIEW payment_terms_type_scheduled AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Scheduled Payment'
ORDER BY code;

CREATE VIEW payment_terms_type_credit AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Letter of Credit'
ORDER BY code;

CREATE VIEW payment_terms_type_cash AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Cash/Cheque Payment'
ORDER BY code;

CREATE VIEW payment_terms_type_installment AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Installment Payment'
ORDER BY code;

CREATE VIEW payment_terms_type_advance AS
SELECT code, name, description
FROM payment_terms_type_code
WHERE category = 'Advance Payment'
ORDER BY code;

CREATE VIEW payment_terms_type_common AS
SELECT code, name, description, category
FROM payment_terms_type_code
WHERE code IN ('1', '10', '22', '52', '4', '2', '3', '50', '21')
ORDER BY
    CASE code
        WHEN '1' THEN 1
        WHEN '10' THEN 2
        WHEN '52' THEN 3
        WHEN '22' THEN 4
        WHEN '4' THEN 5
        WHEN '2' THEN 6
        WHEN '3' THEN 7
        WHEN '21' THEN 8
        WHEN '50' THEN 9
    END;

COMMENT ON VIEW payment_terms_type_immediate IS 'Immediate payment terms (instant, COD, on receipt)';;
COMMENT ON VIEW payment_terms_type_deferred IS 'Deferred or extended payment terms';;
COMMENT ON VIEW payment_terms_type_discount IS 'Payment terms with discount provisions';;
COMMENT ON VIEW payment_terms_type_scheduled IS 'Scheduled payment terms (end of month, fixed date, etc.)';;
COMMENT ON VIEW payment_terms_type_credit IS 'Letter of credit payment terms';;
COMMENT ON VIEW payment_terms_type_cash IS 'Cash and cheque payment terms';;
COMMENT ON VIEW payment_terms_type_installment IS 'Installment payment terms';;
COMMENT ON VIEW payment_terms_type_advance IS 'Advance payment terms';;
COMMENT ON VIEW payment_terms_type_common IS 'Most commonly used payment terms codes';;

-- Create helper function to get payment terms name
CREATE OR REPLACE FUNCTION get_payment_terms_type_name(terms_code VARCHAR(10))
RETURNS VARCHAR(255) AS $$
DECLARE
    terms_name VARCHAR(255);
BEGIN
    SELECT name INTO terms_name
    FROM payment_terms_type_code
    WHERE code = UPPER(terms_code);;

    RETURN terms_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_payment_terms_type_name(VARCHAR) IS 'Get payment terms type name from code';;

-- Create helper function to validate payment terms code
CREATE OR REPLACE FUNCTION is_valid_payment_terms_type_code(terms_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM payment_terms_type_code
        WHERE code = UPPER(terms_code)
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_payment_terms_type_code(VARCHAR) IS 'Validate if payment terms type code exists';;

-- Create helper function to check if terms are immediate
CREATE OR REPLACE FUNCTION is_immediate_payment_terms(terms_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    immediate_flag BOOLEAN;
BEGIN
    SELECT is_immediate INTO immediate_flag
    FROM payment_terms_type_code
    WHERE code = UPPER(terms_code);;

    RETURN COALESCE(immediate_flag, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_immediate_payment_terms(VARCHAR) IS 'Check if payment terms require immediate payment';;

-- Create helper function to check if terms are deferred
CREATE OR REPLACE FUNCTION is_deferred_payment_terms(terms_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    deferred_flag BOOLEAN;
BEGIN
    SELECT is_deferred INTO deferred_flag
    FROM payment_terms_type_code
    WHERE code = UPPER(terms_code);;

    RETURN COALESCE(deferred_flag, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_deferred_payment_terms(VARCHAR) IS 'Check if payment terms are deferred/extended';;

-- Create helper function to check if terms have discount
CREATE OR REPLACE FUNCTION has_discount_terms(terms_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    discount_flag BOOLEAN;
BEGIN
    SELECT has_discount INTO discount_flag
    FROM payment_terms_type_code
    WHERE code = UPPER(terms_code);;

    RETURN COALESCE(discount_flag, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION has_discount_terms(VARCHAR) IS 'Check if payment terms include discount provisions';;

-- Create helper function to get payment terms category
CREATE OR REPLACE FUNCTION get_payment_terms_category(terms_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    terms_category VARCHAR(50);
BEGIN
    SELECT category INTO terms_category
    FROM payment_terms_type_code
    WHERE code = UPPER(terms_code);;

    RETURN terms_category;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_payment_terms_category(VARCHAR) IS 'Get payment terms category from code';;

-- Create helper function to search payment terms by keyword
CREATE OR REPLACE FUNCTION search_payment_terms_types(search_term TEXT)
RETURNS TABLE(
    code VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    category VARCHAR(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        pt.code,
        pt.name,
        pt.description,
        pt.category
    FROM payment_terms_type_code pt
    WHERE
        to_tsvector('english', pt.name || ' ' || pt.description) @@ plainto_tsquery('english', search_term)
        OR pt.name ILIKE '%' || search_term || '%'
        OR pt.description ILIKE '%' || search_term || '%'
    ORDER BY pt.code;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION search_payment_terms_types(TEXT) IS 'Search payment terms type codes by keyword in name or description';;

-- Create summary view by category
CREATE VIEW payment_terms_type_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    COUNT(CASE WHEN is_immediate THEN 1 END) as immediate_count,
    COUNT(CASE WHEN is_deferred THEN 1 END) as deferred_count,
    COUNT(CASE WHEN has_discount THEN 1 END) as discount_count,
    array_agg(code ORDER BY
        CASE WHEN code ~ '^[0-9]+$' THEN code::INTEGER ELSE 999 END,
        code
    ) as codes
FROM payment_terms_type_code
GROUP BY category
ORDER BY code_count DESC, category;

COMMENT ON VIEW payment_terms_type_code_category_summary IS 'Summary of payment terms type codes grouped by category';;
