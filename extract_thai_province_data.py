#!/usr/bin/env python3
"""
Extract Thai Province data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_provinces(xsd_file):
    """Extract province codes and names from XSD file"""

    # Parse XML with namespace handling
    tree = ET.parse(xsd_file)
    root = tree.getroot()

    # Define namespaces
    namespaces = {
        'xsd': 'http://www.w3.org/2001/XMLSchema',
        'ccts': 'urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2'
    }

    # Find all enumeration elements
    provinces = []
    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        provinces.append((code, name))

    return provinces

def determine_region(code):
    """Determine region based on province code"""
    code_num = int(code)

    if code_num == 10:
        return 'Central'
    elif 11 <= code_num <= 19:
        return 'Central'
    elif 20 <= code_num <= 27:
        return 'Eastern'
    elif 30 <= code_num <= 49:
        return 'Northeast'
    elif 50 <= code_num <= 58:
        return 'Northern'
    elif 60 <= code_num <= 67:
        return 'Northern'
    elif 70 <= code_num <= 77:
        return 'Western'
    elif 80 <= code_num <= 96:
        return 'Southern'
    else:
        return None

def generate_sql_with_regions(provinces, output_file):
    """Generate SQL INSERT statements with region classification"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- Thai Province Data Insert Statements\n")
        f.write("-- Generated from ThaiISOCountrySubdivisionCode_1p0.xsd\n")
        f.write(f"-- Total records: {len(provinces)}\n\n")

        f.write("INSERT INTO thai_province (code, name_th, region) VALUES\n")

        for i, (code, name) in enumerate(provinces):
            # Escape single quotes in name
            name_escaped = name.replace("'", "''")

            # Determine region
            region = determine_region(code)
            region_str = f"'{region}'" if region else 'NULL'

            # Add comma except for last record
            comma = ',' if i < len(provinces) - 1 else ';'

            if name:
                f.write(f"('{code}', '{name_escaped}', {region_str}){comma}\n")
            else:
                f.write(f"('{code}', '', NULL){comma}\n")

        f.write("\n-- End of insert statements\n")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/ETDA/codelist/standard/ThaiISOCountrySubdivisionCode_1p0.xsd'
    output_file = 'thai_province_data.sql'

    print(f"Extracting provinces from {xsd_file}...")
    provinces = extract_provinces(xsd_file)

    print(f"Found {len(provinces)} province records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_with_regions(provinces, output_file)

    print("Done!")
    print(f"\nFirst 5 records:")
    for code, name in provinces[:5]:
        region = determine_region(code)
        print(f"  {code}: {name} ({region})")

    print(f"\nLast 5 records:")
    for code, name in provinces[-5:]:
        region = determine_region(code) if name else 'Reserved'
        print(f"  {code}: {name} ({region})")

    # Summary by region
    print("\nSummary by region:")
    regions = {}
    for code, name in provinces:
        region = determine_region(code)
        if region:
            regions[region] = regions.get(region, 0) + 1

    for region, count in sorted(regions.items()):
        print(f"  {region}: {count} provinces")

if __name__ == '__main__':
    main()
