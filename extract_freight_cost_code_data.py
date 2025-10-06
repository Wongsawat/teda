#!/usr/bin/env python3
"""
Extract Freight Cost Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET
import re

def extract_freight_codes(xsd_file):
    """Extract freight cost codes and names from XSD file"""

    print(f"Parsing {xsd_file}...")
    # Parse XML with namespace handling
    tree = ET.parse(xsd_file)
    root = tree.getroot()

    # Define namespaces
    namespaces = {
        'xsd': 'http://www.w3.org/2001/XMLSchema',
        'ccts': 'urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2'
    }

    # Find all enumeration elements
    freight_codes = []
    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        freight_codes.append((code, name))

    return freight_codes

def categorize_code(code, name):
    """Categorize freight code based on code range and name"""

    code_prefix = code[:3]
    name_lower = name.lower()

    # Basic categorization logic
    if code_prefix == '100':
        return 'General Freight'
    elif code_prefix == '101':
        return 'Basic Freight'
    elif code_prefix in ['102', '103', '104']:
        return 'Freight Surcharges'
    elif code_prefix in ['105', '106', '107']:
        return 'Special Freight'
    elif 'container' in name_lower:
        return 'Container Services'
    elif 'terminal' in name_lower:
        return 'Terminal Charges'
    elif 'handling' in name_lower:
        return 'Handling Charges'
    elif 'storage' in name_lower or 'demurrage' in name_lower:
        return 'Storage & Demurrage'
    elif 'dangerous' in name_lower or 'hazardous' in name_lower:
        return 'Dangerous Goods'
    elif 'customs' in name_lower:
        return 'Customs & Documentation'
    elif 'insurance' in name_lower:
        return 'Insurance'
    else:
        return 'Other Charges'

def generate_sql_insert(freight_codes, output_file, batch_size=500):
    """Generate SQL INSERT statements in batches"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Freight Cost Code Data Insert Statements\n")
        f.write("-- Generated from UNECE_FreightCostCode_4.xsd\n")
        f.write(f"-- Total records: {len(freight_codes)}\n\n")

        # Process in batches for better performance
        total_batches = (len(freight_codes) + batch_size - 1) // batch_size

        for batch_num in range(total_batches):
            start_idx = batch_num * batch_size
            end_idx = min((batch_num + 1) * batch_size, len(freight_codes))
            batch = freight_codes[start_idx:end_idx]

            f.write(f"-- Batch {batch_num + 1}/{total_batches} (records {start_idx + 1}-{end_idx})\n")
            f.write("INSERT INTO freight_cost_code (code, name, category) VALUES\n")

            for i, (code, name) in enumerate(batch):
                # Escape single quotes in name
                name_escaped = name.replace("'", "''")

                # Categorize the code
                category = categorize_code(code, name)
                category_escaped = category.replace("'", "''")

                # Add comma except for last record in batch
                comma = ',' if i < len(batch) - 1 else ';'

                f.write(f"('{code}', '{name_escaped}', '{category_escaped}'){comma}\n")

            f.write("\n")

        f.write("-- End of insert statements\n")

def analyze_data(freight_codes):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    # Group by first 3 digits
    code_groups = {}
    categories = {}

    for code, name in freight_codes:
        prefix = code[:3]
        code_groups[prefix] = code_groups.get(prefix, 0) + 1

        category = categorize_code(code, name)
        categories[category] = categories.get(category, 0) + 1

    print(f"Total freight codes: {len(freight_codes)}")
    print(f"Unique code groups (3-digit): {len(code_groups)}")

    print(f"\nTop 10 code groups by count:")
    sorted_groups = sorted(code_groups.items(), key=lambda x: x[1], reverse=True)
    for prefix, count in sorted_groups[:10]:
        print(f"  {prefix}xxx: {count} codes")

    print(f"\nCategories:")
    sorted_categories = sorted(categories.items(), key=lambda x: x[1], reverse=True)
    for category, count in sorted_categories:
        print(f"  {category}: {count} codes")

    # Check code range
    codes_numeric = [int(code) for code, _ in freight_codes]
    print(f"\nCode range: {min(codes_numeric)} - {max(codes_numeric)}")

    # Find container-related codes
    container_codes = [(code, name) for code, name in freight_codes if 'container' in name.lower()]
    print(f"\nContainer-related codes: {len(container_codes)}")

    # Find dangerous goods codes
    dangerous_codes = [(code, name) for code, name in freight_codes
                      if 'dangerous' in name.lower() or 'hazardous' in name.lower()]
    print(f"Dangerous goods codes: {len(dangerous_codes)}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/identifierlist/standard/UNECE_FreightCostCode_4.xsd'
    output_file = 'freight_cost_code_data.sql'

    print(f"Extracting freight cost codes from {xsd_file}...")
    freight_codes = extract_freight_codes(xsd_file)

    print(f"Found {len(freight_codes)} freight cost code records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(freight_codes, output_file, batch_size=500)

    print("Done!")

    print(f"\nFirst 10 records:")
    for code, name in freight_codes[:10]:
        category = categorize_code(code, name)
        print(f"  {code}: {name} ({category})")

    print(f"\nLast 10 records:")
    for code, name in freight_codes[-10:]:
        category = categorize_code(code, name)
        print(f"  {code}: {name} ({category})")

    analyze_data(freight_codes)

if __name__ == '__main__':
    main()
