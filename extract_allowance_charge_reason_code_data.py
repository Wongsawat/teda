#!/usr/bin/env python3
"""
Extract Allowance/Charge Reason Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_allowance_charge_reason_codes(xsd_file):
    """Extract allowance/charge reason codes, names, and descriptions from XSD file"""

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

def categorize_reason(code, name, description):
    """Categorize allowance/charge reason codes based on their purpose"""

    text = (name + ' ' + description).lower()

    # Define categories based on common patterns
    if any(word in text for word in ['damaged', 'below specification', 'quality', 'defective']):
        return 'Quality Issue'
    elif any(word in text for word in ['short delivery', 'wrong delivery', 'goods returned', 'goods partly returned', 'returned to agent']):
        return 'Delivery Issue'
    elif any(word in text for word in ['invoice error', 'incorrect', 'price query', 'pricing error', 'mathematical error']):
        return 'Administrative Error'
    elif any(word in text for word in ['bank charges', 'agent commission', 'costs for draft']):
        return 'Financial Charges'
    elif any(word in text for word in ['discount', 'rebate', 'allowance', 'bonus']):
        return 'Discount/Allowance'
    elif any(word in text for word in ['freight', 'packing', 'container', 'transport', 'loading', 'unloading']):
        return 'Freight/Logistics'
    elif any(word in text for word in ['agreed settlement', 'payment on account', 'proof of delivery']):
        return 'Payment Terms'
    elif any(word in text for word in ['counter claim', 'outstanding invoice', 'offset']):
        return 'Claims/Disputes'
    elif any(word in text for word in ['new employee', 'retirement', 'salary', 'parental leave']):
        return 'HR Related'
    elif 'mutually defined' in text or code == 'ZZZ':
        return 'Custom/Other'
    else:
        return 'Other'

def generate_sql_insert(codes, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Allowance/Charge Reason Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_AllowanceChargeReasonCode_D15B.xsd\n")
        f.write(f"-- Total records: {len(codes)}\n\n")

        f.write("INSERT INTO allowance_charge_reason_code (code, name, description, category) VALUES\n")

        for i, (code, name, desc) in enumerate(codes):
            name_escaped = name.replace("'", "''")
            desc_escaped = desc.replace("'", "''")
            category = categorize_reason(code, name, desc)
            category_escaped = category.replace("'", "''")

            comma = ',' if i < len(codes) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', '{desc_escaped}', '{category_escaped}'){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total allowance/charge reason codes: {len(codes)}")

    # Categorize all codes
    categories = {}
    for code, name, desc in codes:
        category = categorize_reason(code, name, desc)
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

    # Special codes
    special = [c for c, n, d in codes if not c.isdigit()]
    if special:
        print(f"\nSpecial (non-numeric) codes: {len(special)}")
        for code, name, _ in codes:
            if not code.isdigit():
                print(f"  {code}: {name}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/UNECE_AllowanceChargeReasonCode_D15B.xsd'
    output_file = 'allowance_charge_reason_code_data.sql'

    print(f"Extracting allowance/charge reason codes from {xsd_file}...")
    codes = extract_allowance_charge_reason_codes(xsd_file)

    print(f"Found {len(codes)} allowance/charge reason code records")
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
