#!/usr/bin/env python3
"""
Extract ISO Country Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

def extract_country_codes(xsd_file):
    """Extract country codes and names from XSD file"""

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
    countries = []
    etda_extensions = ['AN', 'KS', 'UN']

    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        # Find description (for ETDA extensions)
        desc_elem = enum.find('.//ccts:Description', namespaces)
        description = desc_elem.text if desc_elem is not None and desc_elem.text else None

        # Check if it's an ETDA extension
        is_etda_ext = code in etda_extensions

        # Mark Netherlands Antilles as inactive (dissolved in 2010)
        is_active = code != 'AN'

        countries.append((code, name, description, is_etda_ext, is_active))

    return countries

def generate_sql_insert(countries, output_file):
    """Generate SQL INSERT statements"""

    # Separate ETDA extensions from standard codes
    standard_codes = [c for c in countries if not c[3]]
    etda_codes = [c for c in countries if c[3]]

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- ISO Country Code Data Insert Statements\n")
        f.write("-- Generated from ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd\n")
        f.write(f"-- Total records: {len(countries)}\n")
        f.write(f"-- Standard ISO codes: {len(standard_codes)}\n")
        f.write(f"-- ETDA extensions: {len(etda_codes)}\n\n")

        # Insert ETDA extensions first (already in main SQL file, but shown here for completeness)
        f.write("-- ETDA Custom Extensions (already inserted in main schema file)\n")
        f.write("-- INSERT INTO iso_country_code (code, name, description, is_etda_extension, is_active) VALUES\n")
        for code, name, desc, is_etda, is_active in etda_codes:
            name_escaped = name.replace("'", "''")
            desc_str = f"'{desc}'" if desc else 'NULL'
            active_str = 'true' if is_active else 'false'
            f.write(f"-- ('{code}', '{name_escaped}', {desc_str}, true, {active_str})\n")
        f.write("\n")

        # Insert standard ISO codes
        f.write("-- Standard ISO 3166-1 Country Codes\n")
        f.write("INSERT INTO iso_country_code (code, name, description, is_etda_extension, is_active) VALUES\n")

        for i, (code, name, desc, is_etda, is_active) in enumerate(standard_codes):
            # Escape single quotes in name
            name_escaped = name.replace("'", "''")

            # Add comma except for last record
            comma = ',' if i < len(standard_codes) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', NULL, false, true){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(countries):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    standard_codes = [c for c in countries if not c[3]]
    etda_codes = [c for c in countries if c[3]]

    print(f"Total countries: {len(countries)}")
    print(f"Standard ISO codes: {len(standard_codes)}")
    print(f"ETDA extensions: {len(etda_codes)}")

    print("\nETDA Extensions:")
    for code, name, desc, _, is_active in etda_codes:
        status = "Active" if is_active else "Inactive"
        print(f"  {code}: {name} ({status})")
        if desc:
            print(f"      Note: {desc}")

    # Check for asterisks in names
    with_asterisk = [c for c in countries if '*' in c[1]]
    if with_asterisk:
        print(f"\nCodes with asterisk (*): {len(with_asterisk)}")
        for code, name, _, _, _ in with_asterisk[:5]:
            print(f"  {code}: {name}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/identifierlist/standard/ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd'
    output_file = 'iso_country_code_data.sql'

    print(f"Extracting country codes from {xsd_file}...")
    countries = extract_country_codes(xsd_file)

    print(f"Found {len(countries)} country records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(countries, output_file)

    print("Done!")

    print(f"\nFirst 10 records:")
    for code, name, _, is_etda, _ in countries[:10]:
        ext_marker = " (ETDA)" if is_etda else ""
        print(f"  {code}: {name}{ext_marker}")

    print(f"\nLast 10 records:")
    for code, name, _, is_etda, _ in countries[-10:]:
        ext_marker = " (ETDA)" if is_etda else ""
        print(f"  {code}: {name}{ext_marker}")

    analyze_data(countries)

    # Find some key countries
    print("\nKey countries:")
    key_countries = ['TH', 'US', 'CN', 'JP', 'GB', 'DE', 'FR', 'SG', 'MY']
    for code in key_countries:
        for c_code, c_name, _, _, _ in countries:
            if c_code == code:
                print(f"  {code}: {c_name}")
                break

if __name__ == '__main__':
    main()
