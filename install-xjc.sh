#!/bin/bash
# Install JAXB XJC tool standalone

set -e

echo "Installing JAXB XJC tool..."

# Create lib directory
mkdir -p lib

# Download JAXB jars
echo "Downloading JAXB libraries..."
wget -q -O lib/jaxb-xjc.jar https://repo1.maven.org/maven2/com/sun/xml/bind/jaxb-xjc/2.3.3/jaxb-xjc-2.3.3.jar
wget -q -O lib/jaxb-core.jar https://repo1.maven.org/maven2/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-core-2.3.0.1.jar
wget -q -O lib/jaxb-impl.jar https://repo1.maven.org/maven2/com/sun/xml/bind/jaxb-impl/2.3.3/jaxb-impl-2.3.3.jar
wget -q -O lib/jaxb-api.jar https://repo1.maven.org/maven2/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar
wget -q -O lib/activation.jar https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar

echo "Creating xjc wrapper script..."
cat > xjc.sh << 'EOF'
#!/bin/bash
# XJC wrapper script

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
CLASSPATH="$SCRIPT_DIR/lib/jaxb-xjc.jar:$SCRIPT_DIR/lib/jaxb-core.jar:$SCRIPT_DIR/lib/jaxb-impl.jar:$SCRIPT_DIR/lib/jaxb-api.jar:$SCRIPT_DIR/lib/activation.jar"

java -cp "$CLASSPATH" com.sun.tools.xjc.XJCFacade "$@"
EOF

chmod +x xjc.sh

echo ""
echo "✓ XJC installed successfully!"
echo ""
echo "Usage:"
echo "  ./xjc.sh -d src/main/java -p com.example.etax schema.xsd"
echo ""
echo "Test it:"
echo "  ./xjc.sh -version"
