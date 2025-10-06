#!/usr/bin/env python3
"""
Extract Duty/Tax/Fee Type Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_duty_tax_fee_type_codes(xsd_file):
    """Extract duty/tax/fee type codes, names, and descriptions from XSD file"""

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

def categorize_tax_type(code, name, description):
    """Categorize tax types based on their purpose"""

    text = (name + ' ' + description).lower()

    # Define categories based on common patterns
    if 'vat' in code.lower() or 'value added' in text:
        return 'VAT'
    elif 'gst' in code.lower() or 'goods and services tax' in text:
        return 'GST'
    elif any(word in text for word in ['customs duty', 'customs tariff']):
        return 'Customs Duty'
    elif any(word in text for word in ['excise', 'federal excise']):
        return 'Excise Tax'
    elif any(word in text for word in ['anti-dumping', 'countervailing']):
        return 'Trade Remedy Duty'
    elif any(word in text for word in ['environmental', 'energy fee']):
        return 'Environmental Tax'
    elif any(word in text for word in ['petroleum', 'mineral oil', 'tobacco', 'coffee', 'alcohol']):
        return 'Commodity Tax'
    elif 'sales tax' in text or 'harmonised sales' in text or 'provincial sales' in text:
        return 'Sales Tax'
    elif any(word in text for word in ['stamp duty', 'stamp tax']):
        return 'Stamp Duty'
    elif 'agricultural' in text:
        return 'Agricultural Levy'
    elif 'car tax' in text or 'vehicle' in text:
        return 'Vehicle Tax'
    elif 'construction' in text:
        return 'Construction Tax'
    elif 'wage' in text or 'payroll' in text:
        return 'Wage Tax'
    elif 'turnover' in text:
        return 'Turnover Tax'
    elif 'tonnage' in text:
        return 'Tonnage Tax'
    elif 'free' in text.lower() or 'no tax' in text:
        return 'Tax Exempt'
    elif code == 'TOT' or 'total' in text or 'summary' in text:
        return 'Summary'
    elif 'deposit' in text:
        return 'Deposit'
    elif 'rebate' in text:
        return 'Rebate'
    else:
        return 'Other Tax'

def generate_sql_insert(codes, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Duty/Tax/Fee Type Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_DutyTaxFeeTypeCode_D14A.xsd\n")
        f.write(f"-- Total records: {len(codes)}\n\n")

        f.write("INSERT INTO duty_tax_fee_type_code (code, name, description, category) VALUES\n")

        for i, (code, name, desc) in enumerate(codes):
            name_escaped = name.replace("'", "''")
            desc_escaped = desc.replace("'", "''")
            category = categorize_tax_type(code, name, desc)
            category_escaped = category.replace("'", "''")

            comma = ',' if i < len(codes) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', '{desc_escaped}', '{category_escaped}'){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total duty/tax/fee type codes: {len(codes)}")

    # Categorize all codes
    categories = {}
    for code, name, desc in codes:
        category = categorize_tax_type(code, name, desc)
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

    # Important codes
    important = ['VAT', 'GST', 'EXC', 'CUD', 'FRE', 'TOT']
    print(f"\nImportant codes for e-Tax Invoice:")
    for code, name, _ in codes:
        if code in important:
            print(f"  {code}: {name}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/UNECE_DutyTaxFeeTypeCode_D14A.xsd'
    output_file = 'duty_tax_fee_type_code_data.sql'

    print(f"Extracting duty/tax/fee type codes from {xsd_file}...")
    codes = extract_duty_tax_fee_type_codes(xsd_file)

    print(f"Found {len(codes)} duty/tax/fee type code records")
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
