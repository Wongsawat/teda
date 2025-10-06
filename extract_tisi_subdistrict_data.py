#!/usr/bin/env python3
"""
Extract TISI Subdistrict Name data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_subdistricts(xsd_file):
    """Extract subdistrict codes and names from XSD file"""

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
    subdistricts = []
    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        subdistricts.append((code, name))

    return subdistricts

def generate_sql_insert(subdistricts, output_file, batch_size=1000):
    """Generate SQL INSERT statements in batches"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- TISI Subdistrict Name Data Insert Statements\n")
        f.write("-- Generated from TISICitySubDivisionName_1p0.xsd\n")
        f.write(f"-- Total records: {len(subdistricts)}\n\n")

        # Process in batches for better performance
        total_batches = (len(subdistricts) + batch_size - 1) // batch_size

        for batch_num in range(total_batches):
            start_idx = batch_num * batch_size
            end_idx = min((batch_num + 1) * batch_size, len(subdistricts))
            batch = subdistricts[start_idx:end_idx]

            f.write(f"-- Batch {batch_num + 1}/{total_batches} (records {start_idx + 1}-{end_idx})\n")
            f.write("INSERT INTO tisi_subdistrict (code, name_th) VALUES\n")

            for i, (code, name) in enumerate(batch):
                # Escape single quotes in name
                name_escaped = name.replace("'", "''")

                # Add comma except for last record in batch
                comma = ',' if i < len(batch) - 1 else ';'

                f.write(f"('{code}', '{name_escaped}'){comma}\n")

            f.write("\n")

        f.write("-- End of insert statements\n")

def analyze_data(subdistricts):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    # Group by province
    province_counts = {}
    city_counts = {}

    for code, name in subdistricts:
        province_code = code[:2]
        city_code = code[:4]

        province_counts[province_code] = province_counts.get(province_code, 0) + 1
        city_counts[city_code] = city_counts.get(city_code, 0) + 1

    print(f"Total subdistricts: {len(subdistricts)}")
    print(f"Unique provinces: {len(province_counts)}")
    print(f"Unique cities/districts: {len(city_counts)}")

    # Top 10 provinces by subdistrict count
    print("\nTop 10 provinces by subdistrict count:")
    sorted_provinces = sorted(province_counts.items(), key=lambda x: x[1], reverse=True)
    for prov_code, count in sorted_provinces[:10]:
        print(f"  Province {prov_code}: {count} subdistricts")

    # Check for empty names
    empty_count = sum(1 for _, name in subdistricts if not name.strip())
    if empty_count > 0:
        print(f"\nWarning: {empty_count} subdistricts have empty names")

    # Check for special characters
    special_chars = set()
    for code, name in subdistricts:
        for char in name:
            if char in ['*', '#', '@', '!']:
                special_chars.add(char)
    if special_chars:
        print(f"\nSpecial characters found in names: {special_chars}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/ETDA/codelist/standard/TISICitySubDivisionName_1p0.xsd'
    output_file = 'tisi_subdistrict_data.sql'

    print(f"Extracting subdistrict names from {xsd_file}...")
    subdistricts = extract_subdistricts(xsd_file)

    print(f"Found {len(subdistricts)} subdistrict records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(subdistricts, output_file, batch_size=1000)

    print("Done!")
    print(f"\nFirst 5 records:")
    for code, name in subdistricts[:5]:
        prov = code[:2]
        city = code[:4]
        subdist = code[4:6]
        print(f"  {code} ({prov}-{city}-{subdist}): {name}")

    print(f"\nLast 5 records:")
    for code, name in subdistricts[-5:]:
        prov = code[:2]
        city = code[:4]
        subdist = code[4:6]
        print(f"  {code} ({prov}-{city}-{subdist}): {name}")

    analyze_data(subdistricts)

if __name__ == '__main__':
    main()
