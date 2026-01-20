-- UN/CEFACT Payment Terms Description Identifier Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd
-- Standard: UN/CEFACT Code List 4277, Version D14A

CREATE TABLE payment_terms_description_identifier (
    code VARCHAR(2) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    is_draft_required BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_payment_terms_code_format CHECK (code ~ '^[1-7]$')
);;

-- Add comment to table
COMMENT ON TABLE payment_terms_description_identifier IS 'UN/CEFACT payment terms description identifiers for banking draft requirements in e-Tax Invoice';;

-- Add comments to columns
COMMENT ON COLUMN payment_terms_description_identifier.code IS 'Payment terms identifier code (1-7)';;
COMMENT ON COLUMN payment_terms_description_identifier.name IS 'Name of the payment terms';;
COMMENT ON COLUMN payment_terms_description_identifier.description IS 'Detailed description of payment draft requirements';;
COMMENT ON COLUMN payment_terms_description_identifier.is_draft_required IS 'Indicates whether a draft is required (false for code 6 and 7)';;

-- Create indexes for faster lookups
CREATE INDEX idx_payment_terms_name ON payment_terms_description_identifier(name);;
CREATE INDEX idx_payment_terms_is_draft_required ON payment_terms_description_identifier(is_draft_required);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_payment_terms_description_identifier_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_payment_terms_description_identifier_timestamp
    BEFORE UPDATE ON payment_terms_description_identifier
    FOR EACH ROW
    EXECUTE FUNCTION update_payment_terms_description_identifier_timestamp();;

-- Insert enumeration values from schema
INSERT INTO payment_terms_description_identifier (code, name, description, is_draft_required) VALUES
('1',
 'Draft(s) drawn on issuing bank',
 'Draft(s) must be drawn on the issuing bank.',
 true),

('2',
 'Draft(s) drawn on advising bank',
 'Draft(s) must be drawn on the advising bank.',
 true),

('3',
 'Draft(s) drawn on reimbursing bank',
 'Draft(s) must be drawn on the reimbursing bank.',
 true),

('4',
 'Draft(s) drawn on applicant',
 'Draft(s) must be drawn on the applicant.',
 true),

('5',
 'Draft(s) drawn on any other drawee',
 'Draft(s) must be drawn on any other drawee.',
 true),

('6',
 'No drafts',
 'No drafts required.',
 false),

('7',
 'Payment means specified in commercial account summary',
 'An indication that the payment means are specified in a commercial account summary.',
 false);;

-- Create views for different payment term categories
CREATE VIEW payment_terms_draft_required AS
SELECT code, name, description
FROM payment_terms_description_identifier
WHERE is_draft_required = true
ORDER BY code;

CREATE VIEW payment_terms_no_draft AS
SELECT code, name, description
FROM payment_terms_description_identifier
WHERE is_draft_required = false
ORDER BY code;

-- Create view for banking-related terms
CREATE VIEW payment_terms_banking AS
SELECT code, name, description
FROM payment_terms_description_identifier
WHERE name LIKE '%bank%'
ORDER BY code;

COMMENT ON VIEW payment_terms_draft_required IS 'Payment terms requiring draft(s) (codes 1-5)';;
COMMENT ON VIEW payment_terms_no_draft IS 'Payment terms not requiring drafts (codes 6-7)';;
COMMENT ON VIEW payment_terms_banking IS 'Banking-related payment terms';;

-- Create helper function to get payment terms name
CREATE OR REPLACE FUNCTION get_payment_terms_name(terms_code VARCHAR(2))
RETURNS VARCHAR(255) AS $$
DECLARE
    terms_name VARCHAR(255);
BEGIN
    SELECT name INTO terms_name
    FROM payment_terms_description_identifier
    WHERE code = terms_code;

    RETURN terms_name;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION get_payment_terms_name(VARCHAR) IS 'Get payment terms name from code';;

-- Create helper function to check if draft is required
CREATE OR REPLACE FUNCTION is_draft_required(terms_code VARCHAR(2))
RETURNS BOOLEAN AS $$
DECLARE
    draft_required BOOLEAN;
BEGIN
    SELECT is_draft_required INTO draft_required
    FROM payment_terms_description_identifier
    WHERE code = terms_code;

    RETURN COALESCE(draft_required, false);;
END;
$$ LANGUAGE plpgsql;;

COMMENT ON FUNCTION is_draft_required(VARCHAR) IS 'Check if draft is required for given payment terms code';;
