#!/usr/bin/env python3
"""
Extract ISO Language Code data from XSD schema file and generate SQL INSERT statements
Note: The XSD contains both lowercase and uppercase versions of each code.
This script deduplicates them and stores only unique languages.
"""

import xml.etree.ElementTree as ET

def extract_language_codes(xsd_file):
    """Extract language codes and names from XSD file"""

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
    languages = {}  # Use dict to deduplicate by lowercase code

    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        # Find the name in documentation
        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        # Store using lowercase code as key to deduplicate
        code_lower = code.lower()
        if code_lower not in languages:
            languages[code_lower] = name

    # Convert to sorted list
    language_list = sorted([(code, name) for code, name in languages.items()])

    return language_list

def generate_sql_insert(languages, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- ISO Language Code Data Insert Statements\n")
        f.write("-- Generated from ISO_ISO2AlphaLanguageCode_2006-10-27.xsd\n")
        f.write(f"-- Total unique languages: {len(languages)}\n")
        f.write("-- Note: Deduplicated from XSD which contains both lowercase and uppercase variants\n\n")

        f.write("INSERT INTO iso_language_code (code, name, is_active) VALUES\n")

        for i, (code, name) in enumerate(languages):
            # Escape single quotes in name
            name_escaped = name.replace("'", "''")

            # Add comma except for last record
            comma = ',' if i < len(languages) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', true){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(languages):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total unique languages: {len(languages)}")

    # Find ASEAN languages
    asean_codes = {'th', 'en', 'ms', 'id', 'vi', 'my', 'km', 'lo', 'tl', 'fil'}
    asean_langs = [(code, name) for code, name in languages if code in asean_codes]

    print(f"\nASEAN region languages found: {len(asean_langs)}")
    for code, name in sorted(asean_langs):
        print(f"  {code}: {name}")

    # Find major trading partner languages
    major_codes = {'en', 'th', 'zh', 'ja', 'ko', 'de', 'fr', 'es', 'ar', 'ru', 'pt', 'it'}
    major_langs = [(code, name) for code, name in languages if code in major_codes]

    print(f"\nMajor trading partner languages found: {len(major_langs)}")
    for code, name in sorted(major_langs):
        print(f"  {code}: {name}")

    # Check for some key Asian languages
    asian_codes = {'th', 'zh', 'ja', 'ko', 'vi', 'hi', 'bn', 'ta', 'te', 'mr'}
    asian_langs = [(code, name) for code, name in languages if code in asian_codes]

    print(f"\nAsian languages found: {len(asian_langs)}")
    for code, name in sorted(asian_langs):
        print(f"  {code}: {name}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/ISO_ISO2AlphaLanguageCode_2006-10-27.xsd'
    output_file = 'iso_language_code_data.sql'

    print(f"Extracting language codes from {xsd_file}...")
    languages = extract_language_codes(xsd_file)

    print(f"Found {len(languages)} unique language codes (after deduplication)")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(languages, output_file)

    print("Done!")

    print(f"\nFirst 10 languages (alphabetically):")
    for code, name in languages[:10]:
        print(f"  {code}: {name}")

    print(f"\nLast 10 languages (alphabetically):")
    for code, name in languages[-10:]:
        print(f"  {code}: {name}")

    analyze_data(languages)

if __name__ == '__main__':
    main()
