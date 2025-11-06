#!/usr/bin/env python3
"""
Extract Allowance/Charge Identification Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_allowance_charge_codes(xsd_file):
    """Extract allowance/charge codes, names, and descriptions from XSD file"""

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

def categorize_code(code, name, description):
    """Categorize allowance/charge codes based on their purpose"""

    text = (name + ' ' + description).lower()

    # Define categories based on common patterns
    if 'commission' in name.lower():
        if any(word in text for word in ['documentary credit', 'credit', 'acceptance', 'confirmation']):
            return 'Documentary Credit Commission'
        elif any(word in text for word in ['collection', 'payment']):
            return 'Collection Commission'
        elif 'transfer' in text:
            return 'Transfer Commission'
        elif 'negotiation' in text:
            return 'Negotiation Commission'
        elif 'opening' in text:
            return 'Opening Commission'
        elif 'domicil' in text:
            return 'Domiciliation Commission'
        elif 'trust' in text:
            return 'Trust Commission'
        elif 'return' in text:
            return 'Return Commission'
        elif 'release' in text:
            return 'Release Commission'
        else:
            return 'Other Commission'
    elif 'fee' in name.lower():
        if 'discrepancy' in text:
            return 'Discrepancy Fee'
        elif 'reserve' in text:
            return 'Reserve Payment Fee'
        else:
            return 'Processing Fee'
    elif 'charge' in name.lower() or 'charges' in name.lower():
        if 'split' in text or 'b/l' in text:
            return 'Document Charges'
        elif 'freight' in text:
            return 'Freight Charges'
        elif 'handling' in text:
            return 'Handling Charges'
        elif 'packing' in text:
            return 'Packing Charges'
        elif 'loading' in text or 'unloading' in text:
            return 'Loading/Unloading Charges'
        elif 'storage' in text or 'warehousing' in text:
            return 'Storage Charges'
        elif 'testing' in text or 'inspection' in text:
            return 'Testing/Inspection Charges'
        else:
            return 'Other Charges'
    elif 'discount' in name.lower():
        return 'Discount'
    elif 'allowance' in name.lower():
        if 'advertising' in text:
            return 'Advertising Allowance'
        elif 'packaging' in text:
            return 'Packaging Allowance'
        else:
            return 'Other Allowance'
    elif 'bonus' in name.lower():
        return 'Bonus'
    elif 'premium' in name.lower():
        return 'Premium'
    elif 'rebate' in name.lower():
        return 'Rebate'
    elif 'surcharge' in name.lower():
        return 'Surcharge'
    elif 'tax' in name.lower():
        return 'Tax'
    elif 'duty' in name.lower():
        return 'Duty'
    elif 'penalty' in name.lower():
        return 'Penalty'
    else:
        return 'Other'

def generate_sql_insert(codes, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Allowance/Charge Identification Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_AllowanceChargeIdentificationCode_D14A.xsd\n")
        f.write(f"-- Total records: {len(codes)}\n\n")

        f.write("INSERT INTO allowance_charge_identification_code (code, name, description, category) VALUES\n")

        for i, (code, name, desc) in enumerate(codes):
            name_escaped = name.replace("'", "''")
            desc_escaped = desc.replace("'", "''")
            category = categorize_code(code, name, desc)
            category_escaped = category.replace("'", "''")

            comma = ',' if i < len(codes) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', '{desc_escaped}', '{category_escaped}'){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total allowance/charge codes: {len(codes)}")

    # Categorize all codes
    categories = {}
    for code, name, desc in codes:
        category = categorize_code(code, name, desc)
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

    # Commission vs Fee vs Charge vs Other
    commissions = [c for c, n, d in codes if 'commission' in n.lower()]
    fees = [c for c, n, d in codes if 'fee' in n.lower()]
    charges = [c for c, n, d in codes if 'charge' in n.lower() or 'charges' in n.lower()]

    print(f"\nType distribution:")
    print(f"  Commissions: {len(commissions)}")
    print(f"  Fees: {len(fees)}")
    print(f"  Charges: {len(charges)}")
    print(f"  Other: {len(codes) - len(commissions) - len(fees) - len(charges)}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/UNECE_AllowanceChargeIdentificationCode_D14A.xsd'
    output_file = 'allowance_charge_identification_code_data.sql'

    print(f"Extracting allowance/charge codes from {xsd_file}...")
    codes = extract_allowance_charge_codes(xsd_file)

    print(f"Found {len(codes)} allowance/charge code records")
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
