# XSD Dependency Hierarchy for TaxInvoice_CrossIndustryInvoice_2p1.xsd

This document describes the complete XSD dependency tree for generating JAXB classes from the Thai e-Tax Invoice schema.

## Level 0 (Root)

### TaxInvoice_CrossIndustryInvoice_2p1.xsd
- **Path**: `e-tax-invoice-receipt-v2.1/ETDA/data/standard/TaxInvoice_CrossIndustryInvoice_2p1.xsd`
- **Namespace**: `urn:etda:uncefact:data:standard:TaxInvoice_CrossIndustryInvoice:2`
- **Root Element**: `TaxInvoice_CrossIndustryInvoice`
- **Purpose**: Thai e-Tax Invoice schema (ETDA v2.1)
- **Version**: 2.0
- **Date**: Aug 2015

## Level 1 (Direct Imports - 4 files)

### 1.1 UnqualifiedDataType_16p0.xsd
- **Path**: `../../../uncefact/data/standard/UnqualifiedDataType_16p0.xsd`
- **Namespace**: `urn:un:unece:uncefact:data:standard:UnqualifiedDataType:16`
- **Purpose**: Basic UN/CEFACT primitive data types
- **Contains**: Amount, Quantity, Text, Code, Indicator, DateTime, etc.
- **Standard**: UN/CEFACT version 16.0

### 1.2 QualifiedDataType_1p0.xsd
- **Path**: `QualifiedDataType_1p0.xsd`
- **Namespace**: `urn:etda:uncefact:data:standard:QualifiedDataType:1`
- **Purpose**: Thai-specific qualified data types with business constraints
- **Contains**: Extended types with Thai business rules and validations

### 1.3 TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd
- **Path**: `TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd`
- **Namespace**: `urn:etda:uncefact:data:standard:TaxInvoice_ReusableAggregateBusinessInformationEntity:2`
- **Purpose**: Reusable business entities for Thai e-Tax Invoice
- **Contains**: Party, Address, Line Items, Tax, Payment Terms, etc.

### 1.4 xmldsig-core-schema.xsd
- **Path**: `xmldsig-core-schema.xsd`
- **Namespace**: `http://www.w3.org/2000/09/xmldsig#`
- **Purpose**: W3C XML Digital Signature
- **Standard**: W3C Recommendation

## Level 2 (Imported by QualifiedDataType_1p0.xsd - 19 files)

### Thai/ETDA Code Lists (5 files)

#### 2.1 ThaiDocumentNameCode_Invoice_1p0.xsd
- **Path**: `../../codelist/standard/ThaiDocumentNameCode_Invoice_1p0.xsd`
- **Namespace**: `urn:etda:uncefact:codelist:standard:ThaiDocumentNameCode_Invoice:1`
- **Purpose**: Thai document type codes for invoices
- **Codes**: 80, 81, 82, 380, 388, T01, T02, T03, T04, T05, T06, T07 (12 codes)
- **Note**: Despite the name, this uses a MessageFunctionCode namespace (possibly mislabeled)

#### 2.2 Thai_MessageFunctionCode_1p0.xsd
- **Path**: `../../codelist/standard/Thai_MessageFunctionCode_1p0.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:ETDA:ThaiMessageFunctionCode:2560`
- **Purpose**: Thai-specific message function codes (alphanumeric format)
- **Codes**: 25 codes total
  - **DBNG**: 01, 02, 99 (Debit Note - Goods)
  - **DBNS**: 01, 02, 99 (Debit Note - Services)
  - **CDNG**: 01, 02, 03, 04, 05, 99 (Credit Note - Goods)
  - **CDNS**: 01, 02, 03, 04, 99 (Credit Note - Services)
  - **TIVC**: 01, 02, 99 (Tax Invoice - Commercial)
  - **RCTC**: 01, 02, 03, 04, 99 (Receipt - Commercial)
- **Version**: 100.0
- **Date**: 30 May 2016

#### 2.3 ThaiCategoryCode_1p0.xsd
- **Path**: `../../codelist/standard/ThaiCategoryCode_1p0.xsd`
- **Namespace**: `urn:etda:uncefact:codelist:standard:ThaiCategoryCode:1`
- **Purpose**: Thai product/service category codes

#### 2.4 TISICityName_1p0.xsd
- **Path**: `../../identifierlist/standard/TISICityName_1p0.xsd`
- **Namespace**: `urn:etda:uncefact:identifierlist:standard:TISI:CityName:1`
- **Purpose**: Thai Industrial Standards Institute (TISI) city names
- **Standard**: Thai address standards

#### 2.5 TISICitySubDivisionName_1p0.xsd
- **Path**: `../../identifierlist/standard/TISICitySubDivisionName_1p0.xsd`
- **Namespace**: `urn:etda:uncefact:identifierlist:standard:TISI:CitySubDivisionName:1`
- **Purpose**: Thai city subdivision names (districts/sub-districts)
- **Standard**: Thai address standards

### ISO Code Lists (3 files)

#### 2.6 ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd
- **Path**: `../../../uncefact/identifierlist/standard/ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd`
- **Namespace**: `urn:un:unece:uncefact:identifierlist:standard:ISO:ISOTwo-letterCountryCode:SecondEdition2006`
- **Purpose**: ISO 3166-1 alpha-2 country codes
- **Codes**: 249 standard + 3 ETDA extensions = 252 total
- **ETDA Extensions**:
  - **AN**: NETHERLANDS ANTILLES (inactive)
  - **KS**: KOSOVO
  - **UN**: UNITED NATIONS
- **Standard**: ISO 3166-1 Second Edition 2006
- **Version**: 8.6
- **Last Update**: 30 Jan 2024

#### 2.7 ISO_ISO2AlphaLanguageCode_2006-10-27.xsd
- **Path**: `../../../uncefact/identifierlist/standard/ISO_ISO2AlphaLanguageCode_2006-10-27.xsd`
- **Namespace**: `urn:un:unece:uncefact:identifierlist:standard:ISO:ISO2AlphaLanguageCode:2006-10-27`
- **Purpose**: ISO 639-1 two-letter language codes
- **Standard**: ISO 639-1:2002

#### 2.8 ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd
- **Path**: `../../../uncefact/identifierlist/standard/ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd`
- **Namespace**: `urn:un:unece:uncefact:identifierlist:standard:ISO:ISO3AlphaCurrencyCode:2012-08-31`
- **Purpose**: ISO 4217 three-letter currency codes
- **Standard**: ISO 4217:2008

### UN/CEFACT Code Lists (12 files)

#### 2.9 UNECE_AddressType_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_AddressType_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:AddressType:D14A`
- **Purpose**: Address type classification codes
- **Version**: D14A (Directory 2014A)

#### 2.10 UNECE_AllowanceChargeIdentificationCode_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_AllowanceChargeIdentificationCode_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeIdentificationCode:D14A`
- **Purpose**: Allowance and charge identification codes
- **Version**: D14A

#### 2.11 UNECE_AllowanceChargeReasonCode_D15B.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_AllowanceChargeReasonCode_D15B.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:AllowanceChargeReasonCode:D15B`
- **Purpose**: Reason codes for allowances and charges
- **Version**: D15B (Directory 2015B)

#### 2.12 UNECE_DeliveryTermsCode_2010.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_DeliveryTermsCode_2010.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:DeliveryTermsCode:2010`
- **Purpose**: Incoterms delivery terms codes
- **Standard**: Incoterms 2010

#### 2.13 UNECE_DutyTaxFeeTypeCode_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_DutyTaxFeeTypeCode_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:DutyTaxFeeTypeCode:D14A`
- **Purpose**: Tax, duty, and fee type classification codes
- **Version**: D14A

#### 2.14 UNECE_FreightCostCode_4.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_FreightCostCode_4.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:FreightCostCode:4`
- **Purpose**: Freight and transportation cost codes

#### 2.15 UNECE_MessageFunctionCode_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_MessageFunctionCode_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:MessageFunctionCode:D14A`
- **Purpose**: UN/CEFACT message function codes (numeric format)
- **Codes**: 1-65 (e.g., 1=Cancellation, 9=Original, 29=Accepted without amendment)
- **Version**: D14A
- **Note**: This is DIFFERENT from Thai_MessageFunctionCode_1p0.xsd which uses alphanumeric codes

#### 2.16 UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd
- **Path**: `../../../uncefact/identifierlist/standard/UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:identifierlist:standard:UNECE:PaymentTermsDescriptionIdentifier:D14A`
- **Purpose**: Payment terms description identifiers
- **Version**: D14A

#### 2.17 UNECE_PaymentTermsTypeCode_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_PaymentTermsTypeCode_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:PaymentTermsTypeCode:D14A`
- **Purpose**: Payment terms type codes
- **Version**: D14A

#### 2.18 UNECE_ReferenceTypeCode_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_ReferenceTypeCode_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:ReferenceTypeCode:D14A`
- **Purpose**: Reference document type codes
- **Version**: D14A

### Additional Schema (Not Imported but Referenced)

#### UNECE_DocumentNameCode_Invoice_D14A.xsd
- **Path**: `../../../uncefact/codelist/standard/UNECE_DocumentNameCode_Invoice_D14A.xsd`
- **Namespace**: `urn:un:unece:uncefact:codelist:standard:UNECE:DocumentNameCode_Invoice:D14A`
- **Purpose**: UN/CEFACT document name codes for invoices
- **Codes**: 17 codes (80-84, 261, 262, 325, 380, 381, 383-386, 389, 395, 396)
- **Status**: Referenced in Invoice_QualifiedDataType_1p0.xsd but types are inlined
- **Version**: 1.2

---

## Summary Statistics

- **Total XSD files**: 24 (23 imported + 1 referenced)
- **Level 0 (Root)**: 1 file
- **Level 1 (Direct imports)**: 4 files
- **Level 2 (Imported by QualifiedDataType_1p0.xsd)**: 19 files
  - Thai/ETDA: 5 files
  - ISO: 3 files
  - UN/CEFACT: 12 files

## Dependency Graph

```
TaxInvoice_CrossIndustryInvoice_2p1.xsd (ROOT)
├── UnqualifiedDataType_16p0.xsd
├── QualifiedDataType_1p0.xsd
│   ├── ThaiDocumentNameCode_Invoice_1p0.xsd
│   ├── Thai_MessageFunctionCode_1p0.xsd
│   ├── ThaiCategoryCode_1p0.xsd
│   ├── TISICityName_1p0.xsd
│   ├── TISICitySubDivisionName_1p0.xsd
│   ├── ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd
│   ├── ISO_ISO2AlphaLanguageCode_2006-10-27.xsd
│   ├── ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd
│   ├── UNECE_AddressType_D14A.xsd
│   ├── UNECE_AllowanceChargeIdentificationCode_D14A.xsd
│   ├── UNECE_AllowanceChargeReasonCode_D15B.xsd
│   ├── UNECE_DeliveryTermsCode_2010.xsd
│   ├── UNECE_DutyTaxFeeTypeCode_D14A.xsd
│   ├── UNECE_FreightCostCode_4.xsd
│   ├── UNECE_MessageFunctionCode_D14A.xsd
│   ├── UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd
│   ├── UNECE_PaymentTermsTypeCode_D14A.xsd
│   └── UNECE_ReferenceTypeCode_D14A.xsd
├── TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd
│   ├── UnqualifiedDataType_16p0.xsd (already listed)
│   └── QualifiedDataType_1p0.xsd (already listed)
└── xmldsig-core-schema.xsd
```

## JAXB Generation Notes

When generating JAXB classes from `TaxInvoice_CrossIndustryInvoice_2p1.xsd`, all 25 XSD files will be processed and Java classes will be generated for:

1. **Root Schema Package**: `urn.etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2`
2. **Data Type Packages**: Qualified and unqualified data types
3. **Business Entity Package**: Reusable aggregate entities
4. **Code List Packages**: Separate packages for each code list namespace

### Key Code Lists for Database Integration

The following code lists are candidates for database-backed implementation (instead of enums):

1. **ISO_ISOTwo-letterCountryCode** (252 codes) - ✅ Already implemented
2. **Thai_MessageFunctionCode** (25 codes) - Consider implementing
3. **ThaiDocumentNameCode_Invoice** (12 codes) - Consider implementing
4. **ISO_ISO3AlphaCurrencyCode** (180+ codes) - Consider implementing
5. **UNECE_DutyTaxFeeTypeCode** - Consider implementing for tax codes

### Namespace Conflicts to Watch

- **ThaiMessageFunctionCode** vs **UNECE_MessageFunctionCode**: Different namespaces, different code formats
- **ThaiDocumentNameCode_Invoice** namespace mismatch: Filename says "PurposeCode" but namespace is "MessageFunctionCode"

---

## Generated: 2025-10-03
## Project: Thai e-Tax Invoice JAXB Integration
