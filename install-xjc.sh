#!/bin/bash
# Install JAXB XJC tool standalone (for manual testing/debugging)
#
# NOTE: This project uses Maven for JAXB generation (see pom.xml and generate-jaxb.sh)
# This script is provided as a fallback for manual XJC testing only.
#
# For normal usage, use: mvn generate-sources

set -e

echo "╔════════════════════════════════════════════════════════════╗"
echo "║  Installing Standalone JAXB XJC Tool                       ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "⚠️  NOTE: This project uses Maven for JAXB generation."
echo "   This script installs standalone xjc for manual testing only."
echo ""
read -p "Continue? (y/n) " -n 1 -r
echo ""
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Cancelled."
    exit 0
fi
echo ""

# Create lib directory
echo "📁 Creating lib directory..."
mkdir -p lib

# Download Jakarta XML Binding (JAXB 4.0 - for Jakarta EE)
echo "📥 Downloading JAXB libraries (Jakarta XML Binding 4.0)..."
echo "   This may take a moment..."

# Jakarta JAXB 4.0 (compatible with Jakarta EE)
wget -q --show-progress -O lib/jaxb-xjc.jar \
    https://repo1.maven.org/maven2/org/glassfish/jaxb/jaxb-xjc/4.0.0/jaxb-xjc-4.0.0.jar

wget -q --show-progress -O lib/jaxb-core.jar \
    https://repo1.maven.org/maven2/org/glassfish/jaxb/jaxb-core/4.0.0/jaxb-core-4.0.0.jar

wget -q --show-progress -O lib/jaxb-runtime.jar \
    https://repo1.maven.org/maven2/org/glassfish/jaxb/jaxb-runtime/4.0.0/jaxb-runtime-4.0.0.jar

wget -q --show-progress -O lib/jakarta.xml.bind-api.jar \
    https://repo1.maven.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/4.0.0/jakarta.xml.bind-api-4.0.0.jar

wget -q --show-progress -O lib/jakarta.activation-api.jar \
    https://repo1.maven.org/maven2/jakarta/activation/jakarta.activation-api/2.1.0/jakarta.activation-api-2.1.0.jar

echo ""
echo "✓ Download complete"
echo ""

# Create xjc wrapper script
echo "📝 Creating xjc wrapper script..."
cat > xjc.sh << 'EOF'
#!/bin/bash
# XJC wrapper script for Jakarta XML Binding 4.0

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
CLASSPATH="$SCRIPT_DIR/lib/jaxb-xjc.jar"
CLASSPATH="$CLASSPATH:$SCRIPT_DIR/lib/jaxb-core.jar"
CLASSPATH="$CLASSPATH:$SCRIPT_DIR/lib/jaxb-runtime.jar"
CLASSPATH="$CLASSPATH:$SCRIPT_DIR/lib/jakarta.xml.bind-api.jar"
CLASSPATH="$CLASSPATH:$SCRIPT_DIR/lib/jakarta.activation-api.jar"

java -cp "$CLASSPATH" com.sun.tools.xjc.XJCFacade "$@"
EOF

chmod +x xjc.sh

echo "✓ Wrapper script created"
echo ""

# Test installation
echo "🧪 Testing installation..."
if ./xjc.sh -version 2>/dev/null; then
    echo ""
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║  ✅ XJC Installed Successfully!                            ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo ""
    echo "📚 Usage:"
    echo "   Manual XJC (for testing):"
    echo "     ./xjc.sh -d output -p com.example schema.xsd"
    echo ""
    echo "   Recommended (Maven-based):"
    echo "     ./generate-jaxb.sh"
    echo "     mvn generate-sources"
    echo ""
    echo "📝 Example:"
    echo "   ./xjc.sh -d test-output -p test.generated \\"
    echo "     src/main/resources/e-tax-invoice-receipt-v2.1/ETDA/data/standard/TaxInvoice_CrossIndustryInvoice_2p1.xsd"
    echo ""
else
    echo ""
    echo "❌ Installation may have issues. Check the output above."
    exit 1
fi
