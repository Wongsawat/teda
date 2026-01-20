-- Thai Message Function Code Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: Thai_MessageFunctionCode_1p0.xsd

CREATE TABLE thai_message_function_code (
    code VARCHAR(6) PRIMARY KEY,
    description_en VARCHAR(255) NOT NULL,
    description_th VARCHAR(255),
    document_type VARCHAR(20) NOT NULL,
    category VARCHAR(10) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);;

-- Add comment to table
COMMENT ON TABLE thai_message_function_code IS 'Thai e-Tax Invoice message function codes indicating document type and purpose';;

-- Add comments to columns
COMMENT ON COLUMN thai_message_function_code.code IS 'Message function code (e.g., TIVC01, RCTC99, DBNG01)';;
COMMENT ON COLUMN thai_message_function_code.description_en IS 'English description of the message function';;
COMMENT ON COLUMN thai_message_function_code.description_th IS 'Thai description of the message function';;
COMMENT ON COLUMN thai_message_function_code.document_type IS 'Document type: DebitNote, CreditNote, TaxInvoice, Receipt';;
COMMENT ON COLUMN thai_message_function_code.category IS 'Category: Goods, Service, or Other';;
COMMENT ON COLUMN thai_message_function_code.active IS 'Whether this code is currently active';;

-- Create indexes for faster lookups
CREATE INDEX idx_thai_message_function_code_document_type ON thai_message_function_code(document_type);;
CREATE INDEX idx_thai_message_function_code_category ON thai_message_function_code(category);;
CREATE INDEX idx_thai_message_function_code_active ON thai_message_function_code(active);;

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_thai_message_function_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

CREATE TRIGGER trigger_update_thai_message_function_code_timestamp
    BEFORE UPDATE ON thai_message_function_code
    FOR EACH ROW
    EXECUTE FUNCTION update_thai_message_function_code_timestamp();;
