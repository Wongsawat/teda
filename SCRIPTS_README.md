# Build Scripts Documentation

This document explains the build and code generation scripts in this project.

## Primary Script (Maven-based) ⭐

### `generate-jaxb.sh`

**Purpose**: Generate JAXB classes from XSD using Maven

**Usage**:
```bash
./generate-jaxb.sh
```

**What it does**:
1. Runs `mvn clean` to remove previous generated code
2. Runs `mvn generate-sources` to generate JAXB classes from XSD
3. Shows summary of generated files (293 Java classes)
4. Lists packages breakdown (rsm, ram, qdt, udt)

**Output**:
- Location: `target/generated-sources/jaxb/`
- Not committed to git (in `.gitignore`)
- Automatically included in Maven compile classpath

**When to use**:
- After modifying XSD files
- After changing JAXB bindings (`src/main/resources/jaxb-bindings.xjb`)
- When setting up project for first time
- When IDE doesn't see generated classes

**Configuration**:
- Maven plugin: See `pom.xml` lines 67-100
- JAXB bindings: `src/main/resources/jaxb-bindings.xjb`
- XSD files: `src/main/resources/e-tax-invoice-receipt-v2.1/`

---

## Fallback Script (Standalone XJC) 🔧

### `install-xjc.sh`

**Purpose**: Install standalone XJC tool for manual testing/debugging

**Usage**:
```bash
./install-xjc.sh
```

**What it does**:
1. Downloads Jakarta XML Binding 4.0 JARs to `lib/` directory
2. Creates `xjc.sh` wrapper script
3. Tests the installation

**When to use**:
- When you need to test XJC command-line options
- For debugging JAXB generation issues
- When Maven is not available
- For quick one-off XSD conversions

**Note**: ⚠️ This is NOT the primary method. Use `generate-jaxb.sh` (Maven) for normal development.

**Manual XJC Usage** (after running install-xjc.sh):
```bash
# Basic usage
./xjc.sh -d output -p com.example.generated schema.xsd

# Example with project XSD
./xjc.sh -d test-output -p test.generated \
  src/main/resources/e-tax-invoice-receipt-v2.1/ETDA/data/standard/TaxInvoice_CrossIndustryInvoice_2p1.xsd
```

**Files created** (not committed to git):
- `lib/` - JAXB JAR files
- `xjc.sh` - Wrapper script
- These are in `.gitignore`

---

## Maven Commands (Direct)

You can also use Maven directly without scripts:

```bash
# Generate JAXB sources
mvn generate-sources

# Clean and generate
mvn clean generate-sources

# Full build
mvn clean compile

# Package
mvn clean package
```

---

## Project Structure

```
teda/
├── pom.xml                          # Maven configuration with JAXB plugin
├── generate-jaxb.sh                 # ⭐ Primary generation script
├── install-xjc.sh                   # 🔧 Fallback XJC installer
├── .gitignore                       # Excludes generated code
│
├── src/main/resources/
│   ├── jaxb-bindings.xjb           # JAXB customization bindings
│   └── e-tax-invoice-receipt-v2.1/ # XSD schema files (source of truth)
│       ├── ETDA/                   # Thai-specific schemas
│       └── uncefact/               # UN/CEFACT schemas
│
├── src/main/java/com/wpanther/etax/
│   ├── adapter/                    # String-based adapters
│   ├── entity/                     # Database entities
│   └── repository/                 # JPA repositories
│
└── target/                         # Generated & compiled code (not in git)
    └── generated-sources/jaxb/     # JAXB-generated classes
        └── com/wpanther/etax/generated/invoice/
            ├── rsm/                # Root schema (6 classes)
            ├── ram/                # Reusable aggregates (74 classes)
            ├── qdt/                # Qualified data types (72 classes)
            └── udt/                # Unqualified data types (44 classes)
```

---

## Workflow

### Initial Setup

```bash
# 1. Clone repository
git clone <repo-url>
cd teda

# 2. Generate JAXB classes
./generate-jaxb.sh

# 3. Build project
mvn compile

# 4. Refresh IDE
# IntelliJ: File → Reload All from Disk
# Eclipse: Project → Clean
# VSCode: Reload Window
```

### Making Changes

#### When XSD Changes:
```bash
# 1. Update XSD files in src/main/resources/e-tax-invoice-receipt-v2.1/
# 2. Regenerate JAXB classes
./generate-jaxb.sh

# 3. Rebuild
mvn compile
```

#### When Bindings Change:
```bash
# 1. Edit src/main/resources/jaxb-bindings.xjb
# 2. Regenerate
./generate-jaxb.sh

# 3. Rebuild
mvn compile
```

#### When Your Code Changes:
```bash
# Just compile (no need to regenerate JAXB)
mvn compile
```

---

## Git Workflow

### What to Commit ✅

```bash
git add pom.xml
git add generate-jaxb.sh
git add install-xjc.sh
git add .gitignore
git add src/main/resources/jaxb-bindings.xjb
git add src/main/resources/e-tax-invoice-receipt-v2.1/
git add src/main/java/
```

### What NOT to Commit ❌

These are automatically ignored by `.gitignore`:
- `target/` - All build output
- `target/generated-sources/jaxb/` - Generated JAXB code
- `lib/` - Downloaded XJC JARs (if using install-xjc.sh)
- `xjc.sh` - Generated wrapper script
- `generated-src/` - Old output directory
- `test-output/` - Test output

---

## Troubleshooting

### IDE doesn't see generated classes

**Solution**:
```bash
# 1. Regenerate
./generate-jaxb.sh

# 2. Refresh IDE
# IntelliJ: File → Invalidate Caches → Restart
# Eclipse: Project → Clean
# VSCode: Developer → Reload Window
```

### Build errors after XSD changes

**Solution**:
```bash
# Clean everything and rebuild
mvn clean
./generate-jaxb.sh
mvn compile
```

### "Class not found" errors

**Cause**: Generated classes not in classpath

**Solution**:
1. Check `target/generated-sources/jaxb/` exists
2. Ensure Maven added it to classpath (automatic)
3. Refresh IDE project

### Maven says "No XSD files found"

**Cause**: Incorrect paths in `pom.xml`

**Solution**:
1. Verify XSD files exist: `ls src/main/resources/e-tax-invoice-receipt-v2.1/`
2. Check `pom.xml` line 80 has correct path
3. Path must be relative to project root

---

## Performance

**Generation time**: ~5 seconds
**Generated files**: 293 Java classes
**Generated code size**: ~2.5 MB
**Compilation time**: ~3.5 seconds

Total clean build: **~8.5 seconds**

---

## References

- **Maven JAXB Plugin**: https://github.com/mojohaus/jaxb2-maven-plugin
- **Jakarta XML Binding**: https://eclipse-ee4j.github.io/jaxb-ri/
- **Integration Guide**: See `JAXB_INTEGRATION_GUIDE.md`
- **Usage Examples**: See `SIMPLE_EXAMPLE.md`
