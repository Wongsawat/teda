#!/usr/bin/env python3
"""
Extract Payment Terms Type Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_payment_terms_type_codes(xsd_file):
    """Extract payment terms type codes, names, and descriptions from XSD file"""

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

def categorize_payment_terms(code, name, description):
    """Categorize payment terms codes based on their purpose"""

    text = (name + ' ' + description).lower()

    # Define categories based on common patterns
    if any(word in text for word in ['discount', 'rebate']):
        return 'Discount Terms'
    elif any(word in text for word in ['penalty', 'interest', 'late']):
        return 'Penalty Terms'
    elif any(word in text for word in ['instant', 'immediate', 'receipt', 'delivery', 'cod']):
        return 'Immediate Payment'
    elif any(word in text for word in ['deferred', 'extended', 'postponed']):
        return 'Deferred Payment'
    elif any(word in text for word in ['end of month', 'days after', 'proximo', 'fixed date']):
        return 'Scheduled Payment'
    elif any(word in text for word in ['letter of credit', 'documentary credit']):
        return 'Letter of Credit'
    elif any(word in text for word in ['cash', 'certified cheque', 'cheque']):
        return 'Cash/Cheque Payment'
    elif any(word in text for word in ['bill of exchange', 'promissory note', 'draft']):
        return 'Trade Instruments'
    elif any(word in text for word in ['factoring', 'factor']):
        return 'Factoring'
    elif any(word in text for word in ['installment', 'instalment', 'stage payment', 'progressive']):
        return 'Installment Payment'
    elif any(word in text for word in ['advance', 'prepay', 'deposit']):
        return 'Advance Payment'
    elif any(word in text for word in ['against statement', 'against documents']):
        return 'Documentary Payment'
    elif any(word in text for word in ['lump sum', 'fixed fee']):
        return 'Fixed Payment'
    elif any(word in text for word in ['basic', 'normal', 'standard']):
        return 'Standard Terms'
    elif any(word in text for word in ['mixed', 'elective', 'mutually defined']):
        return 'Custom Terms'
    else:
        return 'Other Terms'

def generate_sql_insert(codes, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Payment Terms Type Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_PaymentTermsTypeCode_D14A.xsd\n")
        f.write(f"-- Total records: {len(codes)}\n\n")

        f.write("INSERT INTO payment_terms_type_code (code, name, description, category) VALUES\n")

        for i, (code, name, desc) in enumerate(codes):
            name_escaped = name.replace("'", "''")
            desc_escaped = desc.replace("'", "''")
            category = categorize_payment_terms(code, name, desc)
            category_escaped = category.replace("'", "''")

            comma = ',' if i < len(codes) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', '{desc_escaped}', '{category_escaped}'){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total payment terms type codes: {len(codes)}")

    # Categorize all codes
    categories = {}
    for code, name, desc in codes:
        category = categorize_payment_terms(code, name, desc)
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

    # Important codes for e-Tax Invoice
    important = ['1', '10', '22', '26', '42']
    print(f"\nImportant codes for e-Tax Invoice:")
    for code, name, _ in codes:
        if code in important:
            print(f"  {code}: {name}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/UNECE_PaymentTermsTypeCode_D14A.xsd'
    output_file = 'payment_terms_type_code_data.sql'

    print(f"Extracting payment terms type codes from {xsd_file}...")
    codes = extract_payment_terms_type_codes(xsd_file)

    print(f"Found {len(codes)} payment terms type code records")
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
