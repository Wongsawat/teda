# XSD Dependency Hierarchy for Thai e-Tax Invoice Documents

This document describes the complete XSD dependency tree for all 6 document types in the Thai e-Tax Invoice library.

## Overview

The library generates JAXB classes for **6 document types** from ETDA v2.1 specification:

| # | Document | Type Code | Root Class | Purpose | XSD Count |
|---|----------|-----------|------------|---------|-----------|
| 1 | **TaxInvoice** | 388 | TaxInvoice_CrossIndustryInvoiceType | Thai e-Tax invoices | 24 |
| 2 | **Receipt** | 80 | Receipt_CrossIndustryInvoiceType | Payment receipts | 24 |
| 3 | **AbbreviatedTaxInvoice** | 81 | AbbreviatedTaxInvoice_CrossIndustryInvoiceType | Retail invoices | 24 |
| 4 | **DebitCreditNote** | 381/383 | DebitCreditNote_CrossIndustryInvoiceType | Credit/debit notes | 24 |
| 5 | **CancellationNote** | 380 | CancellationNote_CrossIndustryInvoiceType | Void documents | 24 |
| 6 | **Invoice** | Generic | Invoice_CrossIndustryInvoiceType | UN/CEFACT standard | 18 |

**Key Differences**:
- Documents 1-5 use **Thai QDT** (QualifiedDataType_1p0.xsd) with Thai-specific code lists
- Document 6 uses **Generic QDT** (Invoice_QualifiedDataType_1p0.xsd) - UN/CEFACT standard only

## Shared Dependencies (All 6 Documents)

### UnqualifiedDataType_16p0.xsd (UDT)
- **Path**: `../../../uncefact/data/standard/UnqualifiedDataType_16p0.xsd`
- **Namespace**: `urn:un:unece:uncefact:data:standard:UnqualifiedDataType:16`
- **Purpose**: Basic UN/CEFACT primitive data types
- **Contains**: Amount, Quantity, Text, Code, Indicator, DateTime, etc.
- **Standard**: UN/CEFACT version 16.0
- **Used by**: All 6 document types

### xmldsig-core-schema.xsd
- **Path**: `xmldsig-core-schema.xsd`
- **Namespace**: `http://www.w3.org/2000/09/xmldsig#`
- **Purpose**: W3C XML Digital Signature
- **Standard**: W3C Recommendation
- **Used by**: All 6 document types

---

## Thai Documents Group (5 Types)

**Documents**: TaxInvoice, Receipt, AbbreviatedTaxInvoice, DebitCreditNote, CancellationNote

All 5 documents share the same Thai-specific dependencies:

### QualifiedDataType_1p0.xsd (Thai QDT)
- **Path**: `QualifiedDataType_1p0.xsd`
- **Namespace**: `urn:etda:uncefact:data:standard:QualifiedDataType:1`
- **Purpose**: Thai-specific qualified data types with business constraints
- **Used by**: TaxInvoice, Receipt, AbbreviatedTaxInvoice, DebitCreditNote, CancellationNote

**Imports 19 code list schemas:**

#### Thai/ETDA Code Lists (5 files)

| File | Purpose | Codes |
|------|---------|-------|
| ThaiDocumentNameCode_Invoice_1p0.xsd | Thai document type codes | 12 codes (80, 81, 82, 380, 388, T01-T07) |
| Thai_MessageFunctionCode_1p0.xsd | Thai message function codes | 25 codes (alphanumeric: DBNG, CDNG, TIVC, RCTC, etc.) |
| ThaiCategoryCode_1p0.xsd | Thai product/service categories | ~50 categories |
| TISICityName_1p0.xsd | Thai TISI city names | 190 cities |
| TISICitySubDivisionName_1p0.xsd | Thai subdivisions | 8,940 sub-districts |

#### ISO Code Lists (3 files)

| File | Purpose | Standard |
|------|---------|----------|
| ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd | Country codes | ISO 3166-1 (252 codes) |
| ISO_ISO2AlphaLanguageCode_2006-10-27.xsd | Language codes | ISO 639-1 (180+ codes) |
| ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd | Currency codes | ISO 4217 (180+ codes) |

#### UN/CEFACT Code Lists (11 files)

| File | Purpose | Version |
|------|---------|---------|
| UNECE_AddressType_D14A.xsd | Address types | D14A |
| UNECE_AllowanceChargeIdentificationCode_D14A.xsd | Allowance/charge IDs | D14A |
| UNECE_AllowanceChargeReasonCode_D15B.xsd | Allowance/charge reasons | D15B |
| UNECE_DeliveryTermsCode_2010.xsd | Incoterms | Incoterms 2010 |
| UNECE_DutyTaxFeeTypeCode_D14A.xsd | Tax types | D14A |
| UNECE_FreightCostCode_4.xsd | Freight costs | Recommendation 23 |
| UNECE_MessageFunctionCode_D14A.xsd | Message functions | D14A (numeric 1-65) |
| UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd | Payment descriptions | D14A |
| UNECE_PaymentTermsTypeCode_D14A.xsd | Payment terms | D14A |
| UNECE_ReferenceTypeCode_D14A.xsd | Reference types | D14A (798 codes) |

### Document-Specific RAM Schemas (5 files)

Each Thai document has its own Reusable Aggregate Business Information Entity:

| Document | RAM Schema |
|----------|------------|
| TaxInvoice | TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd |
| Receipt | Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd |
| AbbreviatedTaxInvoice | AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd |
| DebitCreditNote | DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p1.xsd |
| CancellationNote | CancellationNote_ReusableAggregateBusinessInformationEntity_2p1.xsd |

### Root XSD Files (5 files)

| Document | Root XSD | Namespace |
|----------|----------|-----------|
| TaxInvoice | TaxInvoice_CrossIndustryInvoice_2p1.xsd | urn:etda:uncefact:data:standard:TaxInvoice_CrossIndustryInvoice:2 |
| Receipt | Receipt_CrossIndustryInvoice_2p1.xsd | urn:etda:uncefact:data:standard:Receipt_CrossIndustryInvoice:2 |
| AbbreviatedTaxInvoice | AbbreviatedTaxInvoice_CrossIndustryInvoice_2p1.xsd | urn:etda:uncefact:data:standard:AbbreviatedTaxInvoice_CrossIndustryInvoice:2 |
| DebitCreditNote | DebitCreditNote_CrossIndustryInvoice_2p1.xsd | urn:etda:uncefact:data:standard:DebitCreditNote_CrossIndustryInvoice:2 |
| CancellationNote | CancellationNote_CrossIndustryInvoice_2p1.xsd | urn:etda:uncefact:data:standard:CancellationNote_CrossIndustryInvoice:2 |

**Each Thai document imports**:
1. UnqualifiedDataType_16p0.xsd (UDT) - **SHARED**
2. QualifiedDataType_1p0.xsd (Thai QDT) - **SHARED among 5**
3. Document-specific RAM schema
4. xmldsig-core-schema.xsd - **SHARED**

**Total per Thai document**: 24 XSD files (1 + 1 + 19 + 1 + 1 + 1 = 24)

---

## Generic Invoice Document (UN/CEFACT)

**Document**: Invoice (Generic UN/CEFACT standard)

### Invoice_QualifiedDataType_1p0.xsd (Generic QDT)
- **Path**: `Invoice_QualifiedDataType_1p0.xsd`
- **Namespace**: `urn:un:unece:uncefact:data:standard:Invoice_QualifiedDataType:1`
- **Purpose**: UN/CEFACT standard qualified data types (NO Thai-specific codes)
- **Used by**: Invoice document only

**Imports 13 code list schemas (NO Thai-specific):**

#### ISO Code Lists (3 files) - Same as Thai

| File | Purpose | Standard |
|------|---------|----------|
| ISO_ISOTwo-letterCountryCode_SecondEdition2006.xsd | Country codes | ISO 3166-1 |
| ISO_ISO2AlphaLanguageCode_2006-10-27.xsd | Language codes | ISO 639-1 |
| ISO_ISO3AlphaCurrencyCode_2012-08-31.xsd | Currency codes | ISO 4217 |

#### UN/CEFACT Code Lists (10 files) - Same as Thai

| File | Purpose | Version |
|------|---------|---------|
| UNECE_AddressType_D14A.xsd | Address types | D14A |
| UNECE_AllowanceChargeIdentificationCode_D14A.xsd | Allowance/charge IDs | D14A |
| UNECE_AllowanceChargeReasonCode_D15B.xsd | Allowance/charge reasons | D15B |
| UNECE_DeliveryTermsCode_2010.xsd | Incoterms | Incoterms 2010 |
| UNECE_DutyTaxFeeTypeCode_D14A.xsd | Tax types | D14A |
| UNECE_FreightCostCode_4.xsd | Freight costs | Recommendation 23 |
| UNECE_MessageFunctionCode_D14A.xsd | Message functions | D14A |
| UNECE_PaymentTermsDescriptionIdentifier_D14A.xsd | Payment descriptions | D14A |
| UNECE_PaymentTermsTypeCode_D14A.xsd | Payment terms | D14A |
| UNECE_ReferenceTypeCode_D14A.xsd | Reference types | D14A |

**NOT included in Generic QDT**:
- ❌ ThaiDocumentNameCode_Invoice_1p0.xsd
- ❌ Thai_MessageFunctionCode_1p0.xsd
- ❌ ThaiCategoryCode_1p0.xsd
- ❌ TISICityName_1p0.xsd
- ❌ TISICitySubDivisionName_1p0.xsd

### Invoice RAM Schema

| Document | RAM Schema |
|----------|------------|
| Invoice | Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd |

### Root XSD File

| Document | Root XSD | Namespace |
|----------|----------|-----------|
| Invoice | Invoice_CrossIndustryInvoice_2p1.xsd | urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100 |

**Invoice document imports**:
1. UnqualifiedDataType_16p0.xsd (UDT) - **SHARED with all 6**
2. Invoice_QualifiedDataType_1p0.xsd (Generic QDT) - **Invoice only**
3. Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd (Generic RAM)
4. xmldsig-core-schema.xsd - **SHARED with all 6**

**Total for Invoice**: 18 XSD files (1 + 1 + 13 + 1 + 1 + 1 = 18)

---

## Comparison Table

| Document Type | QDT Used | Thai Code Lists | Total XSDs | Notes |
|---------------|----------|-----------------|------------|-------|
| TaxInvoice | Thai QDT | ✅ 5 files | 24 | Type 388 |
| Receipt | Thai QDT | ✅ 5 files | 24 | Type 80 |
| AbbreviatedTaxInvoice | Thai QDT | ✅ 5 files | 24 | Type 81 |
| DebitCreditNote | Thai QDT | ✅ 5 files | 24 | Types 381/383 |
| CancellationNote | Thai QDT | ✅ 5 files | 24 | Type 380 |
| **Invoice** | **Generic QDT** | **❌ None** | **18** | **UN/CEFACT** |

### Thai QDT vs Generic QDT

| Feature | Thai QDT | Generic QDT |
|---------|----------|-------------|
| **File** | QualifiedDataType_1p0.xsd | Invoice_QualifiedDataType_1p0.xsd |
| **ISO Code Lists** | 3 files | 3 files (same) |
| **UN/CEFACT Code Lists** | 11 files | 10 files |
| **Thai/ETDA Code Lists** | 5 files ✅ | 0 files ❌ |
| **Total Code Lists** | 19 files | 13 files |
| **Used by** | 5 Thai documents | 1 Generic document |

---

## Complete Dependency Graph

```
                                    ┌─────────────────────────────────────────────┐
                                    │   UnqualifiedDataType_16p0.xsd (UDT)        │
                                    │   SHARED by all 6 document types            │
                                    └─────────────────────────────────────────────┘
                                                              │
                ┌─────────────────────────────────────────────────────────────┴────────┐
                │                                                                      │
                │                                                                      │
        ┌───────┴────────┐                                               ┌────────┴───────┐
        │                 │                                               │                │
┌───────┴──────┐   ┌───┴────────────┐                             ┌────────┴────────┐
│               │   │                │                             │                 │
│   Thai QDT    │   │   Generic QDT   │                             │   xmldsig       │
│ (19 code      │   │ (13 code        │                             │   (SHARED)      │
│  lists)       │   │  lists)         │                             │                 │
│               │   │                 │                             │                 │
│  Thai/ETDA:   │   │  NO Thai codes  │                             │                 │
│  • 5 files    │   │                 │                             │                 │
│  ISO:         │   │  ISO: 3 files   │                             │                 │
│  • 3 files    │   │                 │                             │                 │
│  UN/CEFACT:   │   │  UN/CEFACT:     │                             │                 │
│  • 11 files   │   │  • 10 files     │                             │                 │
└───────┬───────┘   └───┬────────────┘                             └─────────────────┘
        │              │
        │              │
┌───────┴─────────────────────────────────────────┐    ┌─────────────────────────────┐
│                                             │    │                              │
│  Thai Documents (5 types)                    │    │  Invoice (Generic)            │
│  • TaxInvoice                              │    │  • UN/CEFACT standard         │
│  • Receipt                                 │    │  • No Thai code lists         │
│  • AbbreviatedTaxInvoice                   │    │  • 18 XSD files total        │
│  • DebitCreditNote                         │    └─────────────────────────────┘
│  • CancellationNote                        │
│  • Each with own RAM schema                │
│  • 24 XSD files each                       │
└─────────────────────────────────────────────┘
```

---

## Summary Statistics

### Shared Files
- **UnqualifiedDataType_16p0.xsd** - Used by all 6 document types
- **xmldsig-core-schema.xsd** - Used by all 6 document types

### Thai Documents (5 types)
- **Root XSDs**: 5 files
- **RAM Schemas**: 5 files
- **Thai QDT**: 1 file (QualifiedDataType_1p0.xsd) with 19 imported code lists
- **XSDs per document**: 24 files
- **Total for Thai group**: ~28 unique XSD files (5 root + 5 RAM + 1 Thai QDT + 17 code lists)

### Generic Invoice (1 type)
- **Root XSD**: 1 file (Invoice_CrossIndustryInvoice_2p1.xsd)
- **RAM Schema**: 1 file (Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd)
- **Generic QDT**: 1 file (Invoice_QualifiedDataType_1p0.xsd) with 13 imported code lists
- **XSDs for Invoice**: 18 files

### Total Unique XSD Files
- **Shared**: 2 files (UDT, xmldsig)
- **Thai QDT**: 1 file with 19 imported code lists
- **Generic QDT**: 1 file with 13 imported code lists
- **Code list overlap**: 13 files are shared between Thai QDT and Generic QDT (ISO + UN/CEFACT)
- **Root XSDs**: 6 files
- **RAM schemas**: 6 files

**Total**: ~30 unique XSD files across all 6 document types

---

## Generated Classes

Each document type generates its own set of JAXB classes:

- **taxinvoice/** (~80 classes) - TaxInvoice-specific
- **receipt/** (~76 classes) - Receipt-specific
- **debitcreditnote/** (~76 classes) - DebitCreditNote-specific
- **cancellationnote/** (~74 classes) - CancellationNote-specific
- **abbreviatedtaxinvoice/** (~80 classes) - AbbreviatedTaxInvoice-specific
- **invoice/** (~146 classes) - Generic Invoice-specific (includes generic QDT)

**Shared** (used by all 6):
- **common/udt/** (44 classes) - Unqualified data types
- **common/qdt/** (72 classes) - Thai qualified data types (for Thai documents)

**Total**: 748+ generated classes

---

## Related Documentation

- [JAXB_GENERATION_SUMMARY.md](JAXB_GENERATION_SUMMARY.md) - Generated code details
- [JAXB_INTEGRATION_GUIDE.md](JAXB_INTEGRATION_GUIDE.md) - Integration guide
- [DATABASE_BACKED_JAXB.md](DATABASE_BACKED_JAXB.md) - Architecture explanation

---

**Generated**: 2025-01-08
**Project**: Thai e-Tax Invoice JAXB Integration
**Total Document Types**: 6
**Total Generated Classes**: 748+
