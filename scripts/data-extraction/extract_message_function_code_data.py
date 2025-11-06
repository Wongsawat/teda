#!/usr/bin/env python3
"""
Extract Message Function Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_message_function_codes(xsd_file):
    """Extract message function codes, names, and descriptions from XSD file"""

    print(f"Parsing {xsd_file}...")
    tree = ET.parse(xsd_file)
    root = tree.getroot()

    namespaces = {
        'xsd': 'http://www.w3.org/2001/XMLSchema',
        'ccts': 'urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2'
    }

    codes = []
    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        desc_elem = enum.find('.//ccts:Description', namespaces)
        description = desc_elem.text if desc_elem is not None and desc_elem.text else ''

        codes.append((code, name, description))

    return codes

def categorize_message_function(code, name, description):
    """Categorize message function codes based on their purpose"""

    text = (name + ' ' + description).lower()

    # Define categories based on common patterns
    if any(word in text for word in ['cancellation', 'cancel']):
        return 'Cancellation'
    elif any(word in text for word in ['addition', 'add']):
        return 'Addition'
    elif any(word in text for word in ['deletion', 'delete', 'withdraw']):
        return 'Deletion'
    elif any(word in text for word in ['change', 'amendment', 'modify', 'correction']):
        return 'Change'
    elif any(word in text for word in ['replace', 'replacing']):
        return 'Replacement'
    elif any(word in text for word in ['original', 'initial transmission']):
        return 'Original'
    elif any(word in text for word in ['duplicate', 'copy']):
        return 'Duplicate'
    elif any(word in text for word in ['confirmation', 'confirm', 'acknowledge']):
        return 'Confirmation'
    elif any(word in text for word in ['accepted', 'acceptance', 'accept']):
        return 'Acceptance'
    elif any(word in text for word in ['rejected', 'rejection', 'refuse', 'decline']):
        return 'Rejection'
    elif any(word in text for word in ['response', 'reply', 'answer']):
        return 'Response'
    elif any(word in text for word in ['request', 'query', 'enquiry']):
        return 'Request'
    elif any(word in text for word in ['status', 'state']):
        return 'Status'
    elif any(word in text for word in ['notification', 'notice', 'pre-advice', 'advance']):
        return 'Notification'
    elif any(word in text for word in ['schedule', 'scheduling']):
        return 'Scheduling'
    elif any(word in text for word in ['verification', 'verify']):
        return 'Verification'
    elif any(word in text for word in ['dispute', 'contested']):
        return 'Dispute'
    elif any(word in text for word in ['test', 'testing']):
        return 'Test'
    elif any(word in text for word in ['not processed', 'not found', 'no action']):
        return 'Processing Status'
    elif any(word in text for word in ['proposal', 'offer', 'quotation']):
        return 'Proposal'
    elif any(word in text for word in ['interim', 'partial']):
        return 'Interim'
    else:
        return 'Other'

def generate_sql_insert(codes, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Message Function Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_MessageFunctionCode_D14A.xsd\n")
        f.write(f"-- Total records: {len(codes)}\n\n")

        f.write("INSERT INTO message_function_code (code, name, description, category) VALUES\n")

        for i, (code, name, desc) in enumerate(codes):
            name_escaped = name.replace("'", "''")
            desc_escaped = desc.replace("'", "''")
            category = categorize_message_function(code, name, desc)
            category_escaped = category.replace("'", "''")

            comma = ',' if i < len(codes) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', '{desc_escaped}', '{category_escaped}'){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total message function codes: {len(codes)}")

    # Categorize all codes
    categories = {}
    for code, name, desc in codes:
        category = categorize_message_function(code, name, desc)
        if category not in categories:
            categories[category] = []
        categories[category].append((code, name))

    print(f"\nCategories found: {len(categories)}")
    for category in sorted(categories.keys()):
        codes_in_cat = categories[category]
        print(f"\n{category}: {len(codes_in_cat)} codes")
        for code, name in codes_in_cat[:5]:  # Show first 5
            print(f"  {code}: {name}")
        if len(codes_in_cat) > 5:
            print(f"  ... and {len(codes_in_cat) - 5} more")

    # Important codes for e-Tax Invoice
    important = ['1', '4', '5', '6', '9']
    print(f"\nImportant codes for e-Tax Invoice:")
    for code, name, _ in codes:
        if code in important:
            print(f"  {code}: {name}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/UNECE_MessageFunctionCode_D14A.xsd'
    output_file = 'message_function_code_data.sql'

    print(f"Extracting message function codes from {xsd_file}...")
    codes = extract_message_function_codes(xsd_file)

    print(f"Found {len(codes)} message function code records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(codes, output_file)

    print("Done!")

    print(f"\nFirst 10 records:")
    for code, name, _ in codes[:10]:
        print(f"  {code}: {name}")

    print(f"\nLast 10 records:")
    for code, name, _ in codes[-10:]:
        print(f"  {code}: {name}")

    analyze_data(codes)

if __name__ == '__main__':
    main()
