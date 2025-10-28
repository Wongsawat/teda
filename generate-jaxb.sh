echo ""

# Generate JAXB sources
echo -e "${YELLOW}⚙️  Generating JAXB classes from XSD...${NC}"
if mvn generate-sources; then
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
if [ -d "target/generated-sources/jaxb/com/wpanther/etax/generated/invoice" ]; then
    echo -e "${BLUE}📦 Generated packages:${NC}"
    find target/generated-sources/jaxb/com/wpanther/etax/generated/invoice -maxdepth 1 -type d | \
        grep -v "^target/generated-sources/jaxb/com/wpanther/etax/generated/invoice$" | \
        while read -r dir; do
            pkg=$(basename "$dir")
            count=$(find "$dir" -name "*.java" | wc -l)
            echo "  • ${pkg}: ${count} classes"
        done
    echo ""
fi

echo -e "${BLUE}📝 Next steps:${NC}"
echo "  1. Refresh your IDE to see generated classes"
echo "  2. Generated code is in target/ (not committed to git)"
echo "  3. Use 'mvn compile' to compile everything"
echo ""

echo -e "${BLUE}💡 Usage example:${NC}"
cat << 'EOF'
  import com.wpanther.etax.generated.invoice.rsm.impl.*;
  import com.wpanther.etax.adapter.*;

  // Create invoice using database-backed entities
  ISOCurrencyCode thb = ISOCurrencyCodeAdapter.toEntity("THB");
  TaxInvoice_CrossIndustryInvoiceTypeImpl invoice =
      new TaxInvoice_CrossIndustryInvoiceTypeImpl();

  // See SIMPLE_EXAMPLE.md for complete examples
EOF
echo ""

echo -e "${GREEN}✨ Done!${NC}"
