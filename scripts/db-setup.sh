#!/bin/bash
# Create the etax database and load all schema + data from src/main/resources/db/.
#
# Usage:
#   ./scripts/db-setup.sh                        # defaults: localhost:5432, user postgres
#   ./scripts/db-setup.sh -h 127.0.0.1 -p 5433
#   ./scripts/db-setup.sh -U myuser -W mypassword
#   ./scripts/db-setup.sh --drop                 # drop and recreate the database first
#
# Options:
#   -h HOST       PostgreSQL host   (default: localhost)
#   -p PORT       PostgreSQL port   (default: 5432)
#   -U USER       PostgreSQL user   (default: postgres)
#   -W PASSWORD   PostgreSQL password (default: postgres)
#   --drop        Drop and recreate the etax database before setup

set -e

# Defaults
HOST="localhost"
PORT="5432"
USER="postgres"
PASSWORD="postgres"
DROP=false

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h) HOST="$2";     shift 2 ;;
        -p) PORT="$2";     shift 2 ;;
        -U) USER="$2";     shift 2 ;;
        -W) PASSWORD="$2"; shift 2 ;;
        --drop) DROP=true; shift   ;;
        *) echo "Unknown option: $1"; exit 1 ;;
    esac
done

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DB_DIR="$SCRIPT_DIR/../src/main/resources/db"
export PGPASSWORD="$PASSWORD"
PSQL="psql -h $HOST -p $PORT -U $USER"

echo "PostgreSQL: $USER@$HOST:$PORT"

# Drop database if requested
if [ "$DROP" = true ]; then
    echo "Dropping database etax..."
    $PSQL -d postgres -c "DROP DATABASE IF EXISTS etax;" 2>/dev/null
fi

# Create database (no-op if already exists)
if ! $PSQL -d postgres -tc "SELECT 1 FROM pg_database WHERE datname='etax'" | grep -q 1; then
    echo "Creating database etax..."
    $PSQL -d postgres -c "CREATE DATABASE etax;"
else
    echo "Database etax already exists."
fi

# Schema files — run in dependency order (independent tables first)
SCHEMA_FILES=(
    address_type.sql
    iso_country_code.sql
    iso_currency_code.sql
    iso_language_code.sql
    reference_type_code.sql
    unece_reference_type_code.sql
    unece_document_name_code_invoice.sql
    thai_province_code.sql
    thai_document_name_code.sql
    thai_message_function_code.sql
    thai_category_code.sql
    tisi_subdistrict.sql
    tisi_city_name.sql
    freight_cost_code.sql
    delivery_terms_code.sql
    allowance_charge_identification_code.sql
    allowance_charge_reason_code.sql
    payment_terms_type_code.sql
    payment_terms_description_identifier.sql
    message_function_code.sql
    duty_tax_fee_type_code.sql
)

echo ""
echo "Loading schemas..."
for f in "${SCHEMA_FILES[@]}"; do
    echo "  $f"
    $PSQL -d etax -f "$DB_DIR/$f" > /dev/null
done

# Data files — load after all schemas exist
DATA_FILES=(
    iso_country_code_data.sql
    iso_currency_code_data.sql
    iso_language_code_data.sql
    reference_type_code_data.sql
    unece_reference_type_code_data.sql
    thai_province_code_data.sql
    thai_message_function_code_data.sql
    tisi_subdistrict_data.sql
    tisi_city_name_data.sql
    freight_cost_code_data.sql
    allowance_charge_identification_code_data.sql
    allowance_charge_reason_code_data.sql
    payment_terms_type_code_data.sql
    message_function_code_data.sql
    duty_tax_fee_type_code_data.sql
)

echo ""
echo "Loading data..."
for f in "${DATA_FILES[@]}"; do
    echo "  $f"
    $PSQL -d etax -f "$DB_DIR/$f" > /dev/null
done

echo ""
echo "Done. Tables created and populated:"
$PSQL -d etax -c "\dt" 2>/dev/null | grep -v "^$" | tail -n +3
