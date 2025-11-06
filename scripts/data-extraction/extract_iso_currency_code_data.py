#!/usr/bin/env python3
"""
Extract ISO Currency Code data from XSD schema file and generate SQL INSERT statements
"""

import xml.etree.ElementTree as ET

# ISO 4217 numeric codes and minor units for common currencies
# This data is not in the XSD, so we maintain it separately
CURRENCY_METADATA = {
    'AED': ('784', 2), 'AFN': ('971', 2), 'ALL': ('008', 2), 'AMD': ('051', 2),
    'ANG': ('532', 2), 'AOA': ('973', 2), 'ARS': ('032', 2), 'AUD': ('036', 2),
    'AWG': ('533', 2), 'AZN': ('944', 2), 'BAM': ('977', 2), 'BBD': ('052', 2),
    'BDT': ('050', 2), 'BGN': ('975', 2), 'BHD': ('048', 3), 'BIF': ('108', 0),
    'BMD': ('060', 2), 'BND': ('096', 2), 'BOB': ('068', 2), 'BRL': ('986', 2),
    'BSD': ('044', 2), 'BTN': ('064', 2), 'BWP': ('072', 2), 'BYR': ('974', 0),
    'BZD': ('084', 2), 'CAD': ('124', 2), 'CDF': ('976', 2), 'CHF': ('756', 2),
    'CLP': ('152', 0), 'CNY': ('156', 2), 'COP': ('170', 2), 'CRC': ('188', 2),
    'CUC': ('931', 2), 'CUP': ('192', 2), 'CVE': ('132', 2), 'CZK': ('203', 2),
    'DJF': ('262', 0), 'DKK': ('208', 2), 'DOP': ('214', 2), 'DZD': ('012', 2),
    'EGP': ('818', 2), 'ERN': ('232', 2), 'ETB': ('230', 2), 'EUR': ('978', 2),
    'FJD': ('242', 2), 'FKP': ('238', 2), 'GBP': ('826', 2), 'GEL': ('981', 2),
    'GHS': ('936', 2), 'GIP': ('292', 2), 'GMD': ('270', 2), 'GNF': ('324', 0),
    'GTQ': ('320', 2), 'GYD': ('328', 2), 'HKD': ('344', 2), 'HNL': ('340', 2),
    'HRK': ('191', 2), 'HTG': ('332', 2), 'HUF': ('348', 2), 'IDR': ('360', 2),
    'ILS': ('376', 2), 'INR': ('356', 2), 'IQD': ('368', 3), 'IRR': ('364', 2),
    'ISK': ('352', 0), 'JMD': ('388', 2), 'JOD': ('400', 3), 'JPY': ('392', 0),
    'KES': ('404', 2), 'KGS': ('417', 2), 'KHR': ('116', 2), 'KMF': ('174', 0),
    'KPW': ('408', 2), 'KRW': ('410', 0), 'KWD': ('414', 3), 'KYD': ('136', 2),
    'KZT': ('398', 2), 'LAK': ('418', 2), 'LBP': ('422', 2), 'LKR': ('144', 2),
    'LRD': ('430', 2), 'LSL': ('426', 2), 'LYD': ('434', 3), 'MAD': ('504', 2),
    'MDL': ('498', 2), 'MGA': ('969', 2), 'MKD': ('807', 2), 'MMK': ('104', 2),
    'MNT': ('496', 2), 'MOP': ('446', 2), 'MRO': ('478', 2), 'MUR': ('480', 2),
    'MVR': ('462', 2), 'MWK': ('454', 2), 'MXN': ('484', 2), 'MYR': ('458', 2),
    'MZN': ('943', 2), 'NAD': ('516', 2), 'NGN': ('566', 2), 'NIO': ('558', 2),
    'NOK': ('578', 2), 'NPR': ('524', 2), 'NZD': ('554', 2), 'OMR': ('512', 3),
    'PAB': ('590', 2), 'PEN': ('604', 2), 'PGK': ('598', 2), 'PHP': ('608', 2),
    'PKR': ('586', 2), 'PLN': ('985', 2), 'PYG': ('600', 0), 'QAR': ('634', 2),
    'RON': ('946', 2), 'RSD': ('941', 2), 'RUB': ('643', 2), 'RWF': ('646', 0),
    'SAR': ('682', 2), 'SBD': ('090', 2), 'SCR': ('690', 2), 'SDG': ('938', 2),
    'SEK': ('752', 2), 'SGD': ('702', 2), 'SHP': ('654', 2), 'SLL': ('694', 2),
    'SOS': ('706', 2), 'SRD': ('968', 2), 'SSP': ('728', 2), 'STD': ('678', 2),
    'SYP': ('760', 2), 'SZL': ('748', 2), 'THB': ('764', 2), 'TJS': ('972', 2),
    'TMT': ('934', 2), 'TND': ('788', 3), 'TOP': ('776', 2), 'TRY': ('949', 2),
    'TTD': ('780', 2), 'TWD': ('901', 2), 'TZS': ('834', 2), 'UAH': ('980', 2),
    'UGX': ('800', 0), 'USD': ('840', 2), 'UYU': ('858', 2), 'UZS': ('860', 2),
    'VEF': ('937', 2), 'VND': ('704', 0), 'VUV': ('548', 0), 'WST': ('882', 2),
    'XAF': ('950', 0), 'XCD': ('951', 2), 'XOF': ('952', 0), 'XPF': ('953', 0),
    'YER': ('886', 2), 'ZAR': ('710', 2), 'ZMW': ('967', 2), 'ZWL': ('932', 2),
}

def extract_currency_codes(xsd_file):
    """Extract currency codes and names from XSD file"""

    print(f"Parsing {xsd_file}...")
    tree = ET.parse(xsd_file)
    root = tree.getroot()

    namespaces = {
        'xsd': 'http://www.w3.org/2001/XMLSchema',
        'ccts': 'urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2'
    }

    currencies = []
    for enum in root.findall('.//xsd:enumeration', namespaces):
        code = enum.get('value')

        name_elem = enum.find('.//ccts:Name', namespaces)
        name = name_elem.text if name_elem is not None and name_elem.text else ''

        desc_elem = enum.find('.//ccts:Description', namespaces)
        description = desc_elem.text if desc_elem is not None and desc_elem.text else None

        # Get metadata
        numeric_code, minor_units = CURRENCY_METADATA.get(code, (None, 2))

        currencies.append((code, name, description, numeric_code, minor_units))

    return currencies

def generate_sql_insert(currencies, output_file):
    """Generate SQL INSERT statements"""

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("-- ISO Currency Code Data Insert Statements\n")
        f.write("-- Generated from ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd\n")
        f.write(f"-- Total records: {len(currencies)}\n\n")

        f.write("INSERT INTO iso_currency_code (code, name, description, numeric_code, minor_units, is_active) VALUES\n")

        for i, (code, name, desc, numeric_code, minor_units) in enumerate(currencies):
            name_escaped = name.replace("'", "''")
            desc_str = f"'{desc}'" if desc else 'NULL'
            num_str = f"'{numeric_code}'" if numeric_code else 'NULL'

            comma = ',' if i < len(currencies) - 1 else ';'

            f.write(f"('{code}', '{name_escaped}', {desc_str}, {num_str}, {minor_units}, true){comma}\n")

        f.write("\n-- End of insert statements\n")

def analyze_data(currencies):
    """Analyze the data structure"""
    print("\n=== Data Analysis ===")

    print(f"Total currencies: {len(currencies)}")

    # ASEAN currencies
    asean_codes = {'THB', 'BND', 'KHR', 'IDR', 'LAK', 'MYR', 'MMK', 'PHP', 'SGD', 'VND'}
    asean_curr = [(c, n) for c, n, _, _, _ in currencies if c in asean_codes]

    print(f"\nASEAN currencies found: {len(asean_curr)}")
    for code, name in sorted(asean_curr):
        print(f"  {code}: {name}")

    # Major currencies
    major_codes = {'USD', 'EUR', 'JPY', 'GBP', 'CNY', 'CHF', 'CAD', 'AUD'}
    major_curr = [(c, n) for c, n, _, _, _ in currencies if c in major_codes]

    print(f"\nMajor world currencies found: {len(major_curr)}")
    for code, name in sorted(major_curr):
        print(f"  {code}: {name}")

    # Currencies with descriptions
    with_desc = [(c, n, d) for c, n, d, _, _ in currencies if d]
    if with_desc:
        print(f"\nCurrencies with descriptions: {len(with_desc)}")
        for code, name, desc in with_desc:
            print(f"  {code} ({name}): {desc}")

    # Minor units distribution
    zero_decimals = [c for c, _, _, _, m in currencies if m == 0]
    three_decimals = [c for c, _, _, _, m in currencies if m == 3]

    print(f"\nCurrencies with 0 decimal places: {len(zero_decimals)}")
    print(f"  Examples: {', '.join(sorted(zero_decimals)[:10])}")
    print(f"\nCurrencies with 3 decimal places: {len(three_decimals)}")
    print(f"  Examples: {', '.join(sorted(three_decimals))}")

def main():
    xsd_file = 'e-tax-invoice-receipt-v2.1/uncefact/codelist/standard/ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd'
    output_file = 'iso_currency_code_data.sql'

    print(f"Extracting currency codes from {xsd_file}...")
    currencies = extract_currency_codes(xsd_file)

    print(f"Found {len(currencies)} currency code records")
    print(f"Generating SQL insert statements to {output_file}...")

    generate_sql_insert(currencies, output_file)

    print("Done!")

    print(f"\nFirst 10 records:")
    for code, name, _, _, _ in currencies[:10]:
        print(f"  {code}: {name}")

    print(f"\nLast 10 records:")
    for code, name, _, _, _ in currencies[-10:]:
        print(f"  {code}: {name}")

    analyze_data(currencies)

if __name__ == '__main__':
    main()
