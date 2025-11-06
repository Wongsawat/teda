-- Thai Message Function Code (Complete UN/CEFACT) Table
-- Based on ETDA e-Tax Invoice schema v2.1
-- Schema: ThaiMessageFunctionCode_1p0.xsd
-- Standard: UN/CEFACT Message Function Code D14A

CREATE TABLE thai_message_function_code_full (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add comment to table
COMMENT ON TABLE thai_message_function_code_full IS 'Complete UN/CEFACT Message Function Code list (65 codes) for e-Tax Invoice message purposes';

-- Add comments to columns
COMMENT ON COLUMN thai_message_function_code_full.code IS 'Message function code (1-65)';
COMMENT ON COLUMN thai_message_function_code_full.name IS 'Name of the message function';
COMMENT ON COLUMN thai_message_function_code_full.description IS 'Detailed description of the message function';
COMMENT ON COLUMN thai_message_function_code_full.category IS 'Functional category (Lifecycle, Confirmation, Status, Modification, etc.)';

-- Create indexes for faster lookups
CREATE INDEX idx_thai_message_function_full_name ON thai_message_function_code_full(name);
CREATE INDEX idx_thai_message_function_full_category ON thai_message_function_code_full(category);

-- Create trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_thai_message_function_code_full_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_thai_message_function_code_full_timestamp
    BEFORE UPDATE ON thai_message_function_code_full
    FOR EACH ROW
    EXECUTE FUNCTION update_thai_message_function_code_full_timestamp();

-- Insert all 65 enumeration values from schema
INSERT INTO thai_message_function_code_full (code, name, description, category) VALUES
-- Document Lifecycle
('1', 'Cancellation', 'Message cancelling a previous transmission for a given transaction.', 'Lifecycle'),
('2', 'Addition', 'Message containing items to be added.', 'Lifecycle'),
('3', 'Deletion', 'Message containing items to be deleted.', 'Lifecycle'),
('4', 'Change', 'Message containing items to be changed.', 'Lifecycle'),
('5', 'Replace', 'Message replacing a previous message.', 'Lifecycle'),
('6', 'Confirmation', 'Message confirming the details of a previous transmission where such confirmation is required or recommended under the terms of a trading partner agreement.', 'Confirmation'),
('7', 'Duplicate', 'The message is a duplicate of a previously generated message.', 'Status'),
('8', 'Status', 'Code indicating that the referenced message is a status.', 'Status'),
('9', 'Original', 'Initial transmission related to a given transaction.', 'Lifecycle'),
('10', 'Not found', 'Message whose reference number is not filed.', 'Status'),
('11', 'Response', 'Message responding to a previous message or document.', 'Confirmation'),
('12', 'Not processed', 'Message indicating that the referenced message was received but not yet processed.', 'Status'),
('13', 'Request', 'Code indicating that the referenced message is a request.', 'Status'),
('14', 'Advance notification', 'Code indicating that the information contained in the message is an advance notification of information to follow.', 'Status'),
('15', 'Reminder', 'Repeated message transmission for reminding purposes.', 'Status'),
('16', 'Proposal', 'Message content is a proposal.', 'Status'),
('17', 'Cancel, to be reissued', 'Referenced transaction cancelled, reissued message will follow.', 'Lifecycle'),
('18', 'Reissue', 'New issue of a previous message (maybe cancelled).', 'Lifecycle'),
('19', 'Seller initiated change', 'Change information submitted by buyer but initiated by seller.', 'Modification'),
('20', 'Replace heading section only', 'Message to replace the heading of a previous message.', 'Modification'),
('21', 'Replace item detail and summary only', 'Message to replace item detail and summary of a previous message.', 'Modification'),
('22', 'Final transmission', 'Final message in a related series of messages together making up a commercial, administrative or transport transaction.', 'Lifecycle'),
('23', 'Transaction on hold', 'Message not to be processed until further release information.', 'Status'),
('24', 'Delivery instruction', 'Delivery schedule message only used to transmit short-term delivery instructions.', 'Delivery'),
('25', 'Forecast', 'Delivery schedule message only used to transmit long-term schedule information.', 'Delivery'),
('26', 'Delivery instruction and forecast', 'Combination of codes ''24'' and ''25''.', 'Delivery'),
('27', 'Not accepted', 'Message to inform that the referenced message is not accepted by the recipient.', 'Confirmation'),
('28', 'Accepted, with amendment in heading section', 'Message accepted but amended in heading section.', 'Confirmation'),
('29', 'Accepted without amendment', 'Referenced message is entirely accepted.', 'Confirmation'),
('30', 'Accepted, with amendment in detail section', 'Referenced message is accepted but amended in detail section.', 'Confirmation'),
('31', 'Copy', 'Indicates that the message is a copy of an original message that has been sent, e.g. for action or information.', 'Message Type'),
('32', 'Approval', 'A message releasing an existing referenced message for action to the receiver.', 'Authorization'),
('33', 'Change in heading section', 'Message changing the referenced message heading section.', 'Modification'),
('34', 'Accepted with amendment', 'The referenced message is accepted but amended.', 'Confirmation'),
('35', 'Retransmission', 'Change-free transmission of a message previously sent.', 'Message Type'),
('36', 'Change in detail section', 'Message changing referenced detail section.', 'Modification'),
('37', 'Reversal of a debit', 'Reversal of a previously posted debit.', 'Reversal'),
('38', 'Reversal of a credit', 'Reversal of a previously posted credit.', 'Reversal'),
('39', 'Reversal for cancellation', 'Code indicating that the referenced message is reversing a cancellation of a previous transmission for a given transaction.', 'Reversal'),
('40', 'Request for deletion', 'The message is given to inform the recipient to delete the referenced transaction.', 'Reversal'),
('41', 'Finishing/closing order', 'Last of series of call-offs.', 'Delivery'),
('42', 'Confirmation via specific means', 'Message confirming a transaction previously agreed via other means (e.g. phone).', 'Authorization'),
('43', 'Additional transmission', 'Message already transmitted via another communication channel. This transmission is to provide electronically processable data only.', 'Message Type'),
('44', 'Accepted without reserves', 'Message accepted without reserves.', 'Acceptance'),
('45', 'Accepted with reserves', 'Message accepted with reserves.', 'Acceptance'),
('46', 'Provisional', 'Message content is provisional.', 'Acceptance'),
('47', 'Definitive', 'Message content is definitive.', 'Acceptance'),
('48', 'Accepted, contents rejected', 'Message to inform that the previous message is received, but it cannot be processed due to regulations, laws, etc.', 'Acceptance'),
('49', 'Settled dispute', 'The reported dispute is settled.', 'Other'),
('50', 'Withdraw', 'Message withdrawing a previously approved message.', 'Authorization'),
('51', 'Authorisation', 'Message authorising a message or transaction(s).', 'Authorization'),
('52', 'Proposed amendment', 'A code used to indicate an amendment suggested by the sender.', 'Other'),
('53', 'Test', 'Code indicating the message is to be considered as a test.', 'Message Type'),
('54', 'Extract', 'A subset of the original.', 'Message Type'),
('55', 'Notification only', 'The receiver may use the notification information for analysis only.', 'Other'),
('56', 'Advice of ledger booked items', 'An advice that items have been booked in the ledger.', 'Accounting'),
('57', 'Advice of items pending to be booked in the ledger', 'An advice that items are pending to be booked in the ledger.', 'Accounting'),
('58', 'Pre-advice of items requiring further information', 'A pre-advice that items require further information.', 'Accounting'),
('59', 'Pre-adviced items', 'A pre-advice of items.', 'Accounting'),
('60', 'No action since last message', 'Code indicating the fact that no action has taken place since the last message.', 'Other'),
('61', 'Complete schedule', 'The message function is a complete schedule.', 'Delivery'),
('62', 'Update schedule', 'The message function is an update to a schedule.', 'Delivery'),
('63', 'Not accepted, provisional', 'Not accepted, subject to confirmation.', 'Acceptance'),
('64', 'Verification', 'The message is transmitted to verify information.', 'Other'),
('65', 'Unsettled dispute', 'To report an unsettled dispute.', 'Other');

-- Create views for different categories
CREATE VIEW thai_message_function_code_lifecycle AS
SELECT code, name, description
FROM thai_message_function_code_full
WHERE category = 'Lifecycle'
ORDER BY CAST(code AS INTEGER);

CREATE VIEW thai_message_function_code_confirmation AS
SELECT code, name, description
FROM thai_message_function_code_full
WHERE category IN ('Confirmation', 'Acceptance')
ORDER BY CAST(code AS INTEGER);

CREATE VIEW thai_message_function_code_modification AS
SELECT code, name, description
FROM thai_message_function_code_full
WHERE category IN ('Modification', 'Reversal')
ORDER BY CAST(code AS INTEGER);

CREATE VIEW thai_message_function_code_delivery AS
SELECT code, name, description
FROM thai_message_function_code_full
WHERE category = 'Delivery'
ORDER BY CAST(code AS INTEGER);

-- Create summary view by category
CREATE VIEW thai_message_function_code_category_summary AS
SELECT
    category,
    COUNT(*) as code_count,
    STRING_AGG(code, ', ' ORDER BY CAST(code AS INTEGER)) as codes
FROM thai_message_function_code_full
GROUP BY category
ORDER BY code_count DESC;

COMMENT ON VIEW thai_message_function_code_lifecycle IS 'Message function codes related to document lifecycle (original, change, cancel, etc.)';
COMMENT ON VIEW thai_message_function_code_confirmation IS 'Message function codes related to confirmations and acceptance';
COMMENT ON VIEW thai_message_function_code_modification IS 'Message function codes related to modifications and reversals';
COMMENT ON VIEW thai_message_function_code_delivery IS 'Message function codes related to delivery and scheduling';
COMMENT ON VIEW thai_message_function_code_category_summary IS 'Summary of message function codes grouped by category';
