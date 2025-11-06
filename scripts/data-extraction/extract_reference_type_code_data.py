#!/usr/bin/env python3
"""
Extract Reference Type Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET
import re

def extract_reference_type_codes(xsd_file):
    """Extract reference type codes, names, and descriptions from XSD file"""

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

def categorize_reference_type(code, name, description):
    """Categorize reference type codes based on their purpose"""

    text = (name + ' ' + description).lower()

    # Check for Thai extensions first
    if code.startswith('T') or code in ['80', '81', '380', '388']:
        return 'Thai Extension'

    # Define categories based on common patterns
    if any(word in text for word in ['invoice', 'bill', 'credit note', 'debit note']):
        return 'Invoice/Billing'
    elif any(word in text for word in ['order', 'purchase order', 'quotation', 'offer']):
        return 'Order'
    elif any(word in text for word in ['delivery', 'despatch', 'shipment', 'waybill', 'consignment']):
        return 'Delivery/Shipment'
    elif any(word in text for word in ['contract', 'agreement', 'tender']):
        return 'Contract'
    elif any(word in text for word in ['payment', 'remittance', 'credit', 'debit card', 'bank']):
        return 'Payment'
    elif any(word in text for word in ['customs', 'import', 'export', 'tariff', 'declaration']):
        return 'Customs'
    elif any(word in text for word in ['licence', 'license', 'permit', 'certificate', 'authorization']):
        return 'License/Permit'
    elif any(word in text for word in ['transport', 'carrier', 'freight', 'cargo']):
        return 'Transport'
    elif any(word in text for word in ['insurance', 'policy', 'claim']):
        return 'Insurance'
    elif any(word in text for word in ['warehouse', 'storage', 'inventory']):
        return 'Warehouse'
    elif any(word in text for word in ['packing', 'packaging', 'container']):
        return 'Packaging'
    elif any(word in text for word in ['party', 'customer', 'supplier', 'buyer', 'seller']):
        return 'Party Identification'
    elif any(word in text for word in ['product', 'item', 'goods', 'article', 'commodity']):
        return 'Product'
    elif any(word in text for word in ['specification', 'drawing', 'design', 'plan']):
        return 'Technical Document'
    elif any(word in text for word in ['test', 'inspection', 'quality', 'analysis']):
        return 'Quality/Testing'
    elif any(word in text for word in ['tax', 'duty', 'vat']):
        return 'Tax'
    elif any(word in text for word in ['account', 'financial', 'statement']):
        return 'Financial'
    else:
        return 'Other Reference'

def generate_sql_insert(codes, output_file):
    """Generate SQL INSERT statements in batches"""

    batch_size = 100
    batches = [codes[i:i + batch_size] for i in range(0, len(codes), batch_size)]

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Reference Type Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_ReferenceTypeCode_D14A.xsd\n")
        f.write(f"-- Total records: {len(codes)}\n")
        f.write(f"-- Batches: {len(batches)}\n\n")

        for batch_num, batch in enumerate(batches, 1):
            f.write(f"-- Batch {batch_num}/{len(batches)}\n")
            f.write("INSERT INTO reference_type_code (code, name, description, category) VALUES\n")

            for i, (code, name, desc) in enumerate(batch):
                name_escaped = name.replace("'", "''")
                desc_escaped = desc.replace("'", "''")
                category = categorize_reference_type(code, name, desc)
                category_escaped = category.replace("'", "''")

                comma = ',' if i < len(batch) - 1 else ';'

                f.write(f"('{code}', '{name_escaped}', '{desc_escaped}', '{category_escaped}'){comma}\n")

            f.write("\n")

        f.write("-- End of insert statements\n")

def analyze_data(codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total reference type codes: {len(codes)}")

    # Categorize all codes
    categories = {}
    for code, name, desc in codes:
        category = categorize_reference_type(code, name, desc)
        if category not in categories:
            categories[category] = []
        categories[category].append((code, name))

    print(f"\nCategories found: {len(categories)}")
    for category in sorted(categories.keys()):
        codes_in_cat = categories[category]
        print(f"\n{category}: {len(codes_in_cat)} codes")
        for code, name in codes_in_cat[:3]:  # Show first 3
            print(f"  {code}: {name}")
        if len(codes_in_cat) > 3:
            print(f"  ... and {len(codes_in_cat) - 3} more")

    # Thai extension codes
    thai_codes = [(c, n) for c, n, d in codes if c.startswith('T') or c in ['80', '81', '380', '388']]
    print(f"\nThai extension codes: {len(thai_codes)}")
    for code, name in thai_codes:
        print(f"  {code}: {name}")

    # Code format distribution
    three_letter = [c for c, n, d in codes if re.match(r'^[A-Z]{3}$', c)]
    numeric = [c for c, n, d in codes if c.isdigit()]
    thai_ext = [c for c, n, d in codes if c.startswith('T')]

    print(f"\nCode format distribution:")
    print(f"  Three-letter codes (AAA-ZZZ): {len(three_letter)}")
    print(f"  Numeric codes: {len(numeric)}")
    print(f"  Thai extension codes (T##): {len(thai_ext)}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/UNECE_ReferenceTypeCode_D14A.xsd'
    output_file = 'reference_type_code_data.sql'

    print(f"Extracting reference type codes from {xsd_file}...")
    codes = extract_reference_type_codes(xsd_file)

    print(f"Found {len(codes)} reference type code records")
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
