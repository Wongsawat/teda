-- UN/CEFACT Delivery Terms Code Table (Incoterms 2010)
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: UNECE_DeliveryTermsCode_2010.xsd
-- Standard: UN/CEFACT Code List 4053 (DeliveryTermsCode), Incoterms 2010

CREATE TABLE delivery_terms_code (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    incoterm_group VARCHAR(20),
    seller_obligation VARCHAR(20),
    is_incoterm BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_delivery_terms_code_format CHECK (code ~ '^[0-9]+$|^[A-Z]{3}$')
);

-- Add comment to table
COMMENT ON TABLE delivery_terms_code IS 'UN/CEFACT delivery terms codes (Incoterms 2010) defining responsibilities of buyers and sellers for delivery of goods';

-- Add comments to columns
COMMENT ON COLUMN delivery_terms_code.code IS 'Delivery terms code (1-2 for non-Incoterms, 3-letter codes for Incoterms 2010)';
COMMENT ON COLUMN delivery_terms_code.name IS 'Name/title of the delivery term';
COMMENT ON COLUMN delivery_terms_code.description IS 'Detailed description of the delivery term';
COMMENT ON COLUMN delivery_terms_code.incoterm_group IS 'Incoterms group classification (E=Departure, F=Main carriage unpaid, C=Main carriage paid, D=Arrival)';
COMMENT ON COLUMN delivery_terms_code.seller_obligation IS 'Level of seller obligation (Minimum, Low, Medium, High, Maximum)';
COMMENT ON COLUMN delivery_terms_code.is_incoterm IS 'True if the code is an official Incoterm, false for custom delivery arrangements';

-- Create indexes for faster lookups
CREATE INDEX idx_delivery_terms_code_name ON delivery_terms_code(name);
CREATE INDEX idx_delivery_terms_code_group ON delivery_terms_code(incoterm_group);
CREATE INDEX idx_delivery_terms_code_is_incoterm ON delivery_terms_code(is_incoterm);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_delivery_terms_code_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_delivery_terms_code_timestamp
    BEFORE UPDATE ON delivery_terms_code
    FOR EACH ROW
    EXECUTE FUNCTION update_delivery_terms_code_timestamp();

-- Insert enumeration values from schema
INSERT INTO delivery_terms_code (code, name, description, incoterm_group, seller_obligation, is_incoterm) VALUES
('1',
 'Delivery arranged by the supplier',
 'Indicates that the supplier will arrange delivery of the goods.',
 NULL, NULL, false),

('2',
 'Delivery arranged by logistic service provider',
 'Code indicating that the logistic service provider has arranged the delivery of goods.',
 NULL, NULL, false),

('EXW',
 'Ex Works (insert named place of delivery)',
 'The seller makes the goods available at their premises or another named place. The buyer bears all costs and risks from that point onwards. This represents minimum obligation for the seller.',
 'E', 'Minimum', true),

('FCA',
 'Free Carrier (insert named place of delivery)',
 'The seller delivers the goods, cleared for export, to the carrier nominated by the buyer at the named place. Risk transfers when goods are handed over to the carrier.',
 'F', 'Low', true),

('FAS',
 'Free Alongside Ship (insert named port of shipment)',
 'The seller delivers when the goods are placed alongside the vessel nominated by the buyer at the named port of shipment. Risk transfers when goods are alongside the ship.',
 'F', 'Low', true),

('FOB',
 'Free On Board (insert named port of shipment)',
 'The seller delivers the goods on board the vessel nominated by the buyer at the named port of shipment. Risk transfers when goods pass the ship''s rail.',
 'F', 'Low', true),

('CFR',
 'Cost and Freight (insert named port of destination)',
 'The seller pays the costs and freight necessary to bring the goods to the named port of destination. Risk transfers when goods pass the ship''s rail at the port of shipment, but seller must pay freight to destination.',
 'C', 'Medium', true),

('CIF',
 'Cost, Insurance and Freight (insert named port of destination)',
 'Similar to CFR, but the seller must also procure insurance against the buyer''s risk of loss or damage to the goods during carriage. Seller pays cost, insurance, and freight to destination port.',
 'C', 'Medium', true),

('CPT',
 'Carriage Paid To (insert named place of destination)',
 'The seller pays the freight for the carriage of goods to the named destination. Risk transfers to buyer when goods are handed over to the first carrier.',
 'C', 'Medium', true),

('CIP',
 'Carriage and Insurance Paid to (insert named place of destination)',
 'Similar to CPT, but the seller must also procure insurance against the buyer''s risk. Seller pays carriage and insurance to the named place of destination.',
 'C', 'Medium', true),

('DAP',
 'Delivered At Place (insert named place of destination)',
 'The seller delivers when the goods are placed at the disposal of the buyer on the arriving means of transport ready for unloading at the named place of destination. Seller bears all risks to that point.',
 'D', 'High', true),

('DAT',
 'Delivered At Terminal (insert named terminal at port or place of destination)',
 'The seller delivers when the goods, once unloaded from the arriving means of transport, are placed at the disposal of the buyer at a named terminal at the named port or place of destination.',
 'D', 'High', true),

('DPU',
 'Delivered At Place Unloaded (insert named place of destination)',
 'The seller delivers the goods and transfers risk to the buyer when the goods, once unloaded from the arriving means of transport, are placed at the disposal of the buyer at the named place of destination. Replaces DAT in Incoterms 2020.',
 'D', 'High', true),

('DDP',
 'Delivered Duty Paid (insert named place of destination)',
 'The seller delivers the goods when they are placed at the disposal of the buyer, cleared for import, on the arriving means of transport ready for unloading at the named place of destination. This represents maximum obligation for the seller.',
 'D', 'Maximum', true);

-- Create views for Incoterms groups
CREATE VIEW delivery_terms_incoterm_group_e AS
SELECT code, name, description, seller_obligation
FROM delivery_terms_code
WHERE incoterm_group = 'E'
ORDER BY code;

CREATE VIEW delivery_terms_incoterm_group_f AS
SELECT code, name, description, seller_obligation
FROM delivery_terms_code
WHERE incoterm_group = 'F'
ORDER BY code;

CREATE VIEW delivery_terms_incoterm_group_c AS
SELECT code, name, description, seller_obligation
FROM delivery_terms_code
WHERE incoterm_group = 'C'
ORDER BY code;

CREATE VIEW delivery_terms_incoterm_group_d AS
SELECT code, name, description, seller_obligation
FROM delivery_terms_code
WHERE incoterm_group = 'D'
ORDER BY code;

CREATE VIEW delivery_terms_non_incoterm AS
SELECT code, name, description
FROM delivery_terms_code
WHERE is_incoterm = false
ORDER BY code;

CREATE VIEW delivery_terms_incoterm_only AS
SELECT code, name, description, incoterm_group, seller_obligation
FROM delivery_terms_code
WHERE is_incoterm = true
ORDER BY
    CASE incoterm_group
        WHEN 'E' THEN 1
        WHEN 'F' THEN 2
        WHEN 'C' THEN 3
        WHEN 'D' THEN 4
    END,
    code;

COMMENT ON VIEW delivery_terms_incoterm_group_e IS 'Group E - Departure: EXW (minimum seller obligation)';
COMMENT ON VIEW delivery_terms_incoterm_group_f IS 'Group F - Main carriage unpaid: FCA, FAS, FOB';
COMMENT ON VIEW delivery_terms_incoterm_group_c IS 'Group C - Main carriage paid: CFR, CIF, CPT, CIP';
COMMENT ON VIEW delivery_terms_incoterm_group_d IS 'Group D - Arrival: DAP, DAT, DPU, DDP (maximum seller obligation)';
COMMENT ON VIEW delivery_terms_non_incoterm IS 'Non-Incoterm delivery arrangements';
COMMENT ON VIEW delivery_terms_incoterm_only IS 'All official Incoterms 2010 codes ordered by group';

-- Create helper function to get delivery terms name
CREATE OR REPLACE FUNCTION get_delivery_terms_name(terms_code VARCHAR(10))
RETURNS VARCHAR(255) AS $$
DECLARE
    terms_name VARCHAR(255);
BEGIN
    SELECT name INTO terms_name
    FROM delivery_terms_code
    WHERE code = UPPER(terms_code);

    RETURN terms_name;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_delivery_terms_name(VARCHAR) IS 'Get delivery terms name from code';

-- Create helper function to validate delivery terms code
CREATE OR REPLACE FUNCTION is_valid_delivery_terms_code(terms_code VARCHAR(10))
RETURNS BOOLEAN AS $$
DECLARE
    code_exists BOOLEAN;
BEGIN
    SELECT EXISTS(
        SELECT 1 FROM delivery_terms_code
        WHERE code = UPPER(terms_code)
    ) INTO code_exists;

    RETURN code_exists;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION is_valid_delivery_terms_code(VARCHAR) IS 'Validate if delivery terms code exists';

-- Create helper function to get Incoterms group
CREATE OR REPLACE FUNCTION get_incoterm_group(terms_code VARCHAR(10))
RETURNS VARCHAR(20) AS $$
DECLARE
    terms_group VARCHAR(20);
BEGIN
    SELECT incoterm_group INTO terms_group
    FROM delivery_terms_code
    WHERE code = UPPER(terms_code);

    RETURN terms_group;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_incoterm_group(VARCHAR) IS 'Get Incoterms group (E, F, C, D) from code';

-- Create helper function to get seller obligation level
CREATE OR REPLACE FUNCTION get_seller_obligation_level(terms_code VARCHAR(10))
RETURNS VARCHAR(20) AS $$
DECLARE
    obligation_level VARCHAR(20);
BEGIN
    SELECT seller_obligation INTO obligation_level
    FROM delivery_terms_code
    WHERE code = UPPER(terms_code);

    RETURN obligation_level;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION get_seller_obligation_level(VARCHAR) IS 'Get seller obligation level (Minimum, Low, Medium, High, Maximum) from code';

-- Create summary view
CREATE VIEW delivery_terms_code_summary AS
SELECT
    incoterm_group,
    seller_obligation,
    COUNT(*) as code_count,
    array_agg(code ORDER BY code) as codes,
    array_agg(name ORDER BY code) as names
FROM delivery_terms_code
WHERE is_incoterm = true
GROUP BY incoterm_group, seller_obligation
ORDER BY
    CASE incoterm_group
        WHEN 'E' THEN 1
        WHEN 'F' THEN 2
        WHEN 'C' THEN 3
        WHEN 'D' THEN 4
    END;

COMMENT ON VIEW delivery_terms_code_summary IS 'Summary of Incoterms 2010 grouped by category and seller obligation level';
