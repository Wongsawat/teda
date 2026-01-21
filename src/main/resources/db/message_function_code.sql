-- UN/CEFACT Message Function Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_MessageFunctionCode_D14A.xsd
-- Standard: UN/CEFACT Code List 1225 (MessageFunctionCode), Version D14A

CREATE TABLE message_function_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    is_modification BOOLEAN DEFAULT false,
    is_original BOOLEAN DEFAULT false,
    is_acceptance BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_message_function_code_format CHECK (code ~ '^[0-9]+$')
);;

-- Add comment to table
COMMENT ON TABLE message_function_code IS 'UN/CEFACT message function codes indicating the purpose or action of business documents/messages in e-Tax Invoice';;

-- Add comments to columns
COMMENT ON COLUMN message_function_code.code IS 'Message function code (numeric 1-65)';;
COMMENT ON COLUMN message_function_code.name IS 'Name/title of the message function';;
COMMENT ON COLUMN message_function_code.description IS 'Detailed description of the message function and its usage';;
COMMENT ON COLUMN message_function_code.category IS 'Function category (Original, Change, Cancellation, Confirmation, etc.)';;
COMMENT ON COLUMN message_function_code.is_modification IS 'True if this function modifies a previous message (change, replace, delete, add)';;
COMMENT ON COLUMN message_function_code.is_original IS 'True if this is an original/initial transmission';;
COMMENT ON COLUMN message_function_code.is_acceptance IS 'True if this indicates acceptance or rejection';;

-- Create indexes for faster lookups
CREATE INDEX idx_message_function_code_name ON message_function_code(name);;
CREATE INDEX idx_message_function_code_category ON message_function_code(category);;
CREATE INDEX idx_message_function_code_is_modification ON message_function_code(is_modification);;
CREATE INDEX idx_message_function_code_is_original ON message_function_code(is_original);;

-- Create full-text search index
CREATE INDEX idx_message_function_code_description_fulltext
ON message_function_code USING gin(to_tsvector('english', description));;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_message_function_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_message_function_code_timestamp
    BEFORE UPDATE ON message_function_code
    FOR EACH ROW
    EXECUTE FUNCTION update_message_function_code_timestamp();;

-- Note: The actual data insertion (65 records) should be done via a separate script
-- that extracts the enumeration values from the XSD file

-- Sample data structure (for reference - key codes):
/*
INSERT INTO message_function_code (code, name, description, category, is_modification, is_original, is_acceptance) VALUES
('9', 'Original', 'Initial transmission related to a given transaction.', 'Original', false, true, false),
('1', 'Cancellation', 'Message cancelling a previous transmission for a given transaction.', 'Cancellation', true, false, false),
('4', 'Change', 'Message containing items to be changed.', 'Change', true, false, false),
('5', 'Replace', 'Message replacing a previous message.', 'Replacement', true, false, false),
('6', 'Confirmation', 'Message confirming the details of a previous transmission where such confirmation is required or recommended under the terms of a trading partner agreement.', 'Confirmation', false, false, false),
('27', 'Not accepted', 'Code indicating the fact that the referenced message is not accepted by the recipient.', 'Acceptance', false, false, true);;
*/

-- Create views for message function categories
CREATE VIEW message_function_code_original AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Original' OR is_original = true
ORDER BY code;

CREATE VIEW message_function_code_modifications AS
SELECT code, name, description, category
FROM message_function_code
WHERE is_modification = true
ORDER BY code;

CREATE VIEW message_function_code_cancellation AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Cancellation'
ORDER BY code;

CREATE VIEW message_function_code_change AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Change'
ORDER BY code;

CREATE VIEW message_function_code_acceptance AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Acceptance' OR is_acceptance = true
ORDER BY code;

CREATE VIEW message_function_code_confirmation AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Confirmation'
ORDER BY code;

CREATE VIEW message_function_code_notification AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Notification'
ORDER BY code;

CREATE VIEW message_function_code_scheduling AS
SELECT code, name, description
FROM message_function_code
WHERE category = 'Scheduling'
ORDER BY code;

CREATE VIEW message_function_code_common AS
SELECT code, name, description, category
FROM message_function_code
WHERE code IN ('9', '1', '4', '5', '6', '7', '8', '11', '13')
ORDER BY
    CASE code
        WHEN '9' THEN 1
        WHEN '4' THEN 2
        WHEN '5' THEN 3
        WHEN '1' THEN 4
        WHEN '6' THEN 5
        WHEN '7' THEN 6
        WHEN '11' THEN 7
        WHEN '13' THEN 8
        WHEN '8' THEN 9
    END;

COMMENT ON VIEW message_function_code_original IS 'Original/initial transmission codes';;
COMMENT ON VIEW message_function_code_modifications IS 'All modification function codes (change, replace, delete, add, cancel)';;
COMMENT ON VIEW message_function_code_cancellation IS 'Cancellation function codes';;
COMMENT ON VIEW message_function_code_change IS 'Change/amendment function codes';;
COMMENT ON VIEW message_function_code_acceptance IS 'Acceptance/rejection codes';;
COMMENT ON VIEW message_function_code_confirmation IS 'Confirmation codes';;
COMMENT ON VIEW message_function_code_notification IS 'Notification and pre-advice codes';;
COMMENT ON VIEW message_function_code_scheduling IS 'Scheduling-related codes';;
COMMENT ON VIEW message_function_code_common IS 'Most commonly used message function codes (9=Original, 4=Change, 5=Replace, 1=Cancel, 6=Confirm)';;

-- Create helper function to get message function name
CREATE OR REPLACE FUNCTION get_message_function_name(function_code VARCHAR(10))
RETURNS VARCHAR(255) AS $$
DECLARE
    function_name VARCHAR(255);
BEGIN
    SELECT name INTO function_name
    FROM message_function_code
    WHERE code = function_code;

    RETURN function_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_message_function_name(VARCHAR) IS 'Get message function name from code';;

-- Create helper function to validate message function code
CREATE OR REPLACE FUNCTION is_valid_message_function_code(function_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM message_function_code
        WHERE code = function_code
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_valid_message_function_code(VARCHAR) IS 'Validate if message function code exists';;

-- Create helper function to check if code is a modification
CREATE OR REPLACE FUNCTION is_modification_function(function_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    is_mod BOOLEAN;
BEGIN
    SELECT is_modification INTO is_mod
    FROM message_function_code
    WHERE code = function_code;

    RETURN COALESCE(is_mod, false);
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_modification_function(VARCHAR) IS 'Check if message function code represents a modification';;

-- Create helper function to check if code is original
CREATE OR REPLACE FUNCTION is_original_function(function_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    is_orig BOOLEAN;
BEGIN
    SELECT is_original INTO is_orig
    FROM message_function_code
    WHERE code = function_code;

    RETURN COALESCE(is_orig, false);
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_original_function(VARCHAR) IS 'Check if message function code represents an original transmission';;

-- Create helper function to get message function category
CREATE OR REPLACE FUNCTION get_message_function_category(function_code VARCHAR(10))
RETURNS VARCHAR(50) AS $$
DECLARE
    func_category VARCHAR(50);
BEGIN
    SELECT category INTO func_category
    FROM message_function_code
    WHERE code = function_code;

    RETURN func_category;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_message_function_category(VARCHAR) IS 'Get message function category from code';;

-- Create helper function to search message functions by keyword
CREATE OR REPLACE FUNCTION search_message_function_codes(search_term TEXT)
RETURNS TABLE(
    code VARCHAR(10),
    name VARCHAR(255),
    description TEXT,
    category VARCHAR(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        mf.code,
        mf.name,
        mf.description,
        mf.category
    FROM message_function_code mf
    WHERE
        to_tsvector('english', mf.name || ' ' || mf.description) @@ plainto_tsquery('english', search_term)
        OR mf.name ILIKE '%' || search_term || '%'
        OR mf.description ILIKE '%' || search_term || '%'
    ORDER BY mf.code;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION search_message_function_codes(TEXT) IS 'Search message function codes by keyword in name or description';;

-- Create summary view by category
CREATE VIEW message_function_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    array_agg(code ORDER BY code::INTEGER) as codes
FROM message_function_code
GROUP BY category
ORDER BY code_count DESC, category;

COMMENT ON VIEW message_function_code_category_summary IS 'Summary of message function codes grouped by category';;
