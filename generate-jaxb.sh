#!/bin/bash

# Color codes for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo ""
echo -e "${YELLOW}⚙️  Forcing JAXB regeneration (using -Pforce-jaxb)...${NC}"
echo ""

# Force JAXB generation with force-jaxb profile
if mvn clean generate-sources -Pforce-jaxb; then
    echo ""
    echo -e "${GREEN}✓ JAXB generation successful${NC}"
else
    echo ""
    echo -e "${RED}✗ JAXB generation failed${NC}"
    exit 1
fi
echo ""

# Count generated files
GENERATED_COUNT=$(find target/generated-sources/jaxb -name "*.java" 2>/dev/null | wc -l)

echo -e "${GREEN}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${GREEN}║  Generation Complete                                       ║${NC}"
echo -e "${GREEN}╚════════════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${BLUE}📊 Summary:${NC}"
echo "  Generated files: ${GREEN}${GENERATED_COUNT}${NC} Java classes"
echo "  Location: ${YELLOW}target/generated-sources/jaxb/${NC}"
echo ""

# List generated packages
if [ -d "target/generated-sources/jaxb/com/wpanther/etax/generated" ]; then
    echo -e "${BLUE}📦 Generated packages:${NC}"
    find target/generated-sources/jaxb/com/wpanther/etax/generated -maxdepth 1 -type d | \
        grep -v "^target/generated-sources/jaxb/com/wpanther/etax/generated$" | \
        while read -r dir; do
            pkg=$(basename "$dir")
            count=$(find "$dir" -name "*.java" | wc -l)
            echo "  • ${pkg}: ${count} classes"
        done
    echo ""
fi

echo -e "${BLUE}📝 Build Optimization:${NC}"
echo "  Since XSD files are stable and never change, you can speed up builds:"
echo ""
echo -e "${GREEN}  Fast builds (skip JAXB):${NC}"
echo "    mvn install -Pskip-jaxb"
echo "    ${YELLOW}Saves 15+ minutes per build! (~30s total)${NC}"
echo ""
echo -e "${GREEN}  Auto-detection (default):${NC}"
echo "    mvn install"
echo "    ${YELLOW}Skips generation if XSD/XJB files unchanged${NC}"
echo ""
echo -e "${GREEN}  Force regeneration:${NC}"
echo "    mvn clean generate-sources -Pforce-jaxb"
echo "    ${YELLOW}Always regenerates (like this script)${NC}"
echo ""
echo -e "${RED}  ⚠️  IMPORTANT:${NC}"
echo "    ${YELLOW}Do NOT use 'mvn clean' with -Pskip-jaxb${NC}"
echo "    ${YELLOW}It deletes generated classes before skipping regeneration!${NC}"
echo ""

echo -e "${BLUE}💡 Usage example:${NC}"
cat << 'EOF'
  import com.wpanther.etax.generated.taxinvoice.rsm.*;
  import com.wpanther.etax.adapter.common.*;

  // Create invoice using database-backed entities
  ISOCurrencyCode thb = ISOCurrencyCodeAdapter.toEntity("THB");
  TaxInvoice_CrossIndustryInvoiceType invoice =
      objectFactory.createTaxInvoice_CrossIndustryInvoiceType();

  // See CLAUDE.md for complete examples
EOF
echo ""

echo -e "${GREEN}✨ Done!${NC}"
