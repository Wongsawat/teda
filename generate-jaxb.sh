#!/bin/bash
# JAXB Code Generation Script for Thai e-Tax Invoice
# Run: ./generate-jaxb.sh

set -e  # Exit on error

# Configuration
BASE_DIR="e-tax-invoice-receipt-v2.1"
OUTPUT_DIR="generated-src/main/java"
BASE_PACKAGE="com.example.etax.generated"

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Thai e-Tax Invoice JAXB Code Generation ===${NC}\n"

# Create output directory
mkdir -p "$OUTPUT_DIR"

# Function to generate with error handling
generate() {
    local name=$1
    local package=$2
    local files=$3

    echo -e "${YELLOW}Generating $name...${NC}"
    if xjc -d "$OUTPUT_DIR" \
           -p "$package" \
           -encoding UTF-8 \
           -no-header \
           -quiet \
           $files 2>/dev/null; then
        echo -e "${GREEN}✓ $name generated successfully${NC}"
    else
        echo -e "${RED}✗ Failed to generate $name${NC}"
        return 1
    fi
    echo ""
}

# 1. ISO Country Codes
generate "ISO Country Codes" \
    "${BASE_PACKAGE}.iso.country" \
    "${BASE_DIR}/uncefact/identifierlist/standard/ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd"

# 2. ISO Currency Codes
generate "ISO Currency Codes" \
    "${BASE_PACKAGE}.iso.currency" \
    "${BASE_DIR}/uncefact/codelist/standard/ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd"

# 3. ISO Language Codes
generate "ISO Language Codes" \
    "${BASE_PACKAGE}.iso.language" \
    "${BASE_DIR}/uncefact/codelist/standard/ISO_ISO2AlphaLanguageCode_2006-10-27.xsd"

# 4. Address Type Code
generate "Address Type Code" \
    "${BASE_PACKAGE}.unece.address" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_AddressType_D14A.xsd"

# 5. Allowance Charge Identification Code
generate "Allowance Charge Identification Code" \
    "${BASE_PACKAGE}.unece.allowancecharge" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_AllowanceChargeIdentificationCode_D14A.xsd"

# 6. Allowance Charge Reason Code
generate "Allowance Charge Reason Code" \
    "${BASE_PACKAGE}.unece.reason" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_AllowanceChargeReasonCode_D15B.xsd"

# 7. Delivery Terms Code
generate "Delivery Terms Code" \
    "${BASE_PACKAGE}.unece.delivery" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_DeliveryTermsCode_2010.xsd"

# 8. Document Name Code
generate "Document Name Code" \
    "${BASE_PACKAGE}.unece.document" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_DocumentNameCode_Invoice_D14A.xsd"

# 9. Duty Tax Fee Type Code
generate "Duty Tax Fee Type Code" \
    "${BASE_PACKAGE}.unece.tax" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_DutyTaxFeeTypeCode_D14A.xsd"

# 10. Freight Cost Code
generate "Freight Cost Code" \
    "${BASE_PACKAGE}.unece.freight" \
    "${BASE_DIR}/uncefact/identifierlist/standard/UNECE_FreightCostCode_4.xsd"

# 11. Message Function Code
generate "Message Function Code" \
    "${BASE_PACKAGE}.unece.message" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_MessageFunctionCode_D14A.xsd"

# 12. Payment Terms Type Code
generate "Payment Terms Type Code" \
    "${BASE_PACKAGE}.unece.payment" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_PaymentTermsTypeCode_D14A.xsd"

# 13. Payment Terms Description Identifier
generate "Payment Terms Description Identifier" \
    "${BASE_PACKAGE}.unece.paymentdesc" \
    "${BASE_DIR}/uncefact/identifierlist/standard/UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd"

# 14. Reference Type Code (Large - 798 codes)
generate "Reference Type Code" \
    "${BASE_PACKAGE}.unece.reference" \
    "${BASE_DIR}/uncefact/codelist/standard/UNECE_ReferenceTypeCode_D14A.xsd"

# 15. Thai Category Code
generate "Thai Category Code" \
    "${BASE_PACKAGE}.thai.category" \
    "${BASE_DIR}/ETDA/codelist/standard/ThaiCategoryCode_1p0.xsd"

# 16. Thai Document Name Code
generate "Thai Document Name Code" \
    "${BASE_PACKAGE}.thai.document" \
    "${BASE_DIR}/ETDA/codelist/standard/ThaiDocumentNameCode_Invoice_1p0.xsd"

# 17. Thai Message Function Code
generate "Thai Message Function Code" \
    "${BASE_PACKAGE}.thai.message" \
    "${BASE_DIR}/ETDA/codelist/standard/ThaiMessageFunctionCode_1p0.xsd"

# 18. Thai Province (ISO Subdivision)
generate "Thai Province Code" \
    "${BASE_PACKAGE}.thai.province" \
    "${BASE_DIR}/ETDA/codelist/standard/ThaiISOCountrySubdivisionCode_1p0.xsd"

# 19. Thai Purpose Code
generate "Thai Purpose Code" \
    "${BASE_PACKAGE}.thai.purpose" \
    "${BASE_DIR}/ETDA/codelist/standard/ThaiPurposeCode_Invoice_1p0.xsd"

# 20. TISI City Name
generate "TISI City Name" \
    "${BASE_PACKAGE}.thai.city" \
    "${BASE_DIR}/ETDA/codelist/standard/TISICityName_1p0.xsd"

# 21. TISI City Subdivision Name
generate "TISI City Subdivision Name" \
    "${BASE_PACKAGE}.thai.subdivision" \
    "${BASE_DIR}/ETDA/codelist/standard/TISICitySubDivisionName_1p0.xsd"

echo -e "${GREEN}=== Generation Complete ===${NC}"
echo -e "Generated classes are in: ${YELLOW}$OUTPUT_DIR${NC}"
echo ""
echo "Next steps:"
echo "  1. Review generated classes in $OUTPUT_DIR"
echo "  2. Add them to your IDE source path"
echo "  3. Use them in your application"
echo ""
echo "Example usage:"
echo "  import ${BASE_PACKAGE}.iso.country.*;"
echo "  ISOTwoletterCountryCodeContentType thailand = ISOTwoletterCountryCodeContentType.TH;"
