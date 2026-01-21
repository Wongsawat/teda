#!/usr/bin/env python3
"""
Extract ISO Language Code data from XSD schema file and generate SQL INSERT statements
Note: The XSD contains both lowercase and uppercase versions of each code.
This script extracts BOTH lowercase and uppercase variants as defined in the XSD.
"""

import xml.etree.ElementTree as ET
from pathlib import Path

def extract_language_codes(xsd_file):
    """Extract language codes and names from XSD file, keeping both lowercase and uppercase"""

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
    languages = {}  # Use dict to store by exact code value

    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        # Store using exact code as key to preserve both cases
        if code not in languages:
            languages[code] = name

    # Convert to sorted list (sort by code, lowercase before uppercase for each pair)
    # Sort key: (lowercase code, is_uppercase) - this puts lowercase first (False < True)
    language_list = sorted(
        [(code, name) for code, name in languages.items()],
        key=lambda x: (x[0].lower(), x[0].isupper())
    )

    return language_list

def generate_sql_insert(languages, output_file):
    """Generate SQL INSERT statements with comments for each language pair"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- ISO Language Code Data Insert Statements\n")
        f.write("-- Generated from ISO_ISO2AlphaLanguageCode_2006-10-27.xsd\n")
        f.write(f"-- Total language code entries: {len(languages)}\n")
        f.write("-- Includes both lowercase and uppercase variants as defined in the XSD schema\n\n")

        f.write("INSERT INTO iso_language_code (code, name, is_active) VALUES\n")

        i = 0
        while i < len(languages):
            code, name = languages[i]
            code_lower = code.lower()
            code_upper = code.upper()

            # Check if we have both lowercase and uppercase of this language
            # Since sorting puts lowercase first, check if next is uppercase of same language
            if i + 1 < len(languages):
                next_code, next_name = languages[i + 1]
                if next_code == code_upper and next_code != code:
                    # We have a pair (lowercase then uppercase) - add comment and both entries
                    f.write(f"-- {code_lower} / {code_upper} - {name}\n")

                    # Escape single quotes in name
                    name_escaped = name.replace("'", "''")
                    next_name_escaped = next_name.replace("'", "''")

                    # Write lowercase first, then uppercase
                    f.write(f"('{code}', '{name_escaped}', true),\n")
                    f.write(f"('{next_code}', '{next_name_escaped}', true)")

                    # Add comma if not the last pair
                    if i + 2 < len(languages):
                        f.write(",\n")
                    else:
                        f.write(";\n")

                    i += 2
                    continue

            # Single entry (no pair) - add comment
            f.write(f"-- {code} - {name}\n")

            # Escape single quotes in name
            name_escaped = name.replace("'", "''")

            # Add comma except for last record
            comma = ',' if i < len(languages) - 1 else ';'
            f.write(f"('{code}', '{name_escaped}', true){comma}\n")
            i += 1

        f.write("\n-- End of insert statements\n")

def analyze_data(languages):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total language code entries: {len(languages)}")

    # Count unique languages (by lowercase)
    unique_langs = set(code.lower() for code, _ in languages)
    print(f"Unique languages (case-insensitive): {len(unique_langs)}")

    # Count lowercase vs uppercase
    lowercase_count = sum(1 for code, _ in languages if code.islower())
    uppercase_count = sum(1 for code, _ in languages if code.isupper())
    print(f"Lowercase codes: {lowercase_count}")
    print(f"Uppercase codes: {uppercase_count}")

    # Find ASEAN languages (check both cases)
    asean_codes = {'th', 'TH', 'en', 'EN', 'ms', 'MS', 'id', 'ID', 'vi', 'VI', 'my', 'MY', 'km', 'KM', 'lo', 'LO', 'tl', 'TL', 'fil', 'FIL'}
    asean_langs = [(code, name) for code, name in languages if code in asean_codes]

    print(f"\nASEAN region language codes found: {len(asean_langs)}")
    for code, name in sorted(asean_langs):
        print(f"  {code}: {name}")

    # Find major trading partner languages
    major_codes = {'en', 'EN', 'th', 'TH', 'zh', 'ZH', 'ja', 'JA', 'ko', 'KO', 'de', 'DE', 'fr', 'FR', 'es', 'ES', 'ar', 'AR', 'ru', 'RU', 'pt', 'PT', 'it', 'IT'}
    major_langs = [(code, name) for code, name in languages if code in major_codes]

    print(f"\nMajor trading partner language codes found: {len(major_langs)}")
    for code, name in sorted(major_langs):
        print(f"  {code}: {name}")

    # Check for some key Asian languages
    asian_codes = {'th', 'TH', 'zh', 'ZH', 'ja', 'JA', 'ko', 'KO', 'vi', 'VI', 'hi', 'HI', 'bn', 'BN', 'ta', 'TA', 'te', 'TE', 'mr', 'MR'}
    asian_langs = [(code, name) for code, name in languages if code in asian_codes]

    print(f"\nAsian language codes found: {len(asian_langs)}")
    for code, name in sorted(asian_langs):
        print(f"  {code}: {name}")

def main():
    # Get the script directory and resolve paths
    script_dir = Path(__file__).parent.parent.parent
    xsd_file = script_dir / 'src/main/resources/e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/ISO_ISO2AlphaLanguageCode_2006-10-27.xsd'
    output_file = script_dir / 'src/main/resources/db/iso_language_code_data.sql'

    print(f"Extracting language codes from {xsd_file}...")
    languages = extract_language_codes(str(xsd_file))

    print(f"Found {len(languages)} language code entries (includes both lowercase and uppercase)")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(languages, str(output_file))

    print("Done!")

    print(f"\nFirst 10 language codes (alphabetically sorted, lowercase first):")
    for code, name in languages[:10]:
        print(f"  {code}: {name}")

    print(f"\nLast 10 language codes (alphabetically sorted):")
    for code, name in languages[-10:]:
        print(f"  {code}: {name}")

    analyze_data(languages)

if __name__ == '__main__':
    main()
