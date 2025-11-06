#!/usr/bin/env python3
"""
Extract TISI City Name data from XSD schema file and generate SQL INSERT statements
"""

import re
import xml.etree.ElementTree as ET

def extract_city_names(xsd_file):
    """Extract city codes and names from XSD file"""

    # Parse XML with namespace handling
    tree = ET.parse(xsd_file)
    root = tree.getroot()

    # Define namespaces
    namespaces = {
        'xsd': 'http://www.w3.org/2001/XMLSchema',
        'ccts': 'urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2'
    }

    # Find all enumeration elements
    cities = []
    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        cities.append((code, name))

    return cities

def generate_sql_insert(cities, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- TISI City Name Data Insert Statements\n")
        f.write("-- Generated from TISICityName_1p0.xsd\n")
        f.write(f"-- Total records: {len(cities)}\n\n")

        f.write("INSERT INTO tisi_city_name (code, name_th) VALUES\n")

        for i, (code, name) in enumerate(cities):
            # Escape single quotes in name
            name_escaped = name.replace("'", "''")

            # Add comma except for last record
            comma = ',' if i < len(cities) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}'){comma}\n")

        f.write("\n-- End of insert statements\n")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/ETDA/codelist/standard/TISICityName_1p0.xsd'
    output_file = 'tisi_city_name_data.sql'

    print(f"Extracting city names from {xsd_file}...")
    cities = extract_city_names(xsd_file)

    print(f"Found {len(cities)} city records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(cities, output_file)

    print("Done!")
    print(f"\nFirst 5 records:")
    for code, name in cities[:5]:
        print(f"  {code}: {name}")

    print(f"\nLast 5 records:")
    for code, name in cities[-5:]:
        print(f"  {code}: {name}")

if __name__ == '__main__':
    main()
