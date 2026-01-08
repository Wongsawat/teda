# Thai e-Tax Invoice Structure Guide

This guide explains the structure of ETDA Thai e-Tax Invoice (TaxInvoice_CrossIndustryInvoice) based on the UN/CEFACT CrossIndustryInvoice standard.

## Document Types

| Code | Thai Name | English Name | Usage |
|------|-----------|--------------|-------|
| 388 | ใบกำกับภาษี | Tax Invoice | Standard tax invoice for VAT-registered businesses |
| 80 | ใบเสร็จรับเงิน | Receipt | Payment receipt |
| 81 | ใบกำกับภาษีอย่างย่อ | Abbreviated Tax Invoice | Simplified tax invoice (retail) |
| 380 | ใบแจ้งยกเลิก | Cancellation Note | Void/cancel documents |
| 381 | ใบลดหนี้ | Credit Note | Refund or price reduction |
| 383 | ใบเพิ่มหนี้ | Debit Note | Additional charges |

## Invoice Structure

```
TaxInvoice_CrossIndustryInvoice (Root Element)
│
├── ExchangedDocumentContext
│   └── GuidelineSpecifiedDocumentContextParameter
│       └── ID (ETDA version: "ER3-2560")
│
├── ExchangedDocument (Invoice Header)
│   ├── ID (Invoice Number)
│   ├── Name (Document Name in Thai)
│   ├── TypeCode (Document Type: 388, 80, 81, 381, 383)
│   ├── IssueDateTime (Issue Date/Time)
│   ├── Purpose (Optional description)
│   ├── GlobalID (Optional OID)
│   └── IncludedNote (Optional notes)
│
└── SupplyChainTradeTransaction (Main Content)
    │
    ├── ApplicableHeaderTradeAgreement
    │   ├── SellerTradeParty
    │   │   ├── ID
    │   │   ├── Name
    │   │   ├── SpecifiedTaxRegistration (13-digit Tax ID)
    │   │   ├── DefinedTradeContact (Contact info)
    │   │   └── PostalTradeAddress
    │   │       ├── PostcodeCode
    │   │       ├── BuildingName
    │   │       ├── BuildingNumber
    │   │       ├── StreetName
    │   │       ├── CityName (District code)
    │   │       ├── CitySubDivisionName (Sub-district code)
    │   │       ├── CountryID (TH)
    │   │       └── CountrySubDivisionID (Province code)
    │   │
    │   ├── BuyerTradeParty (Same structure as Seller)
    │   ├── ApplicableTradeDeliveryTerms (INCOTERMS)
    │   ├── BuyerOrderReferencedDocument (PO reference)
    │   └── AdditionalReferencedDocument (Other references)
    │
    ├── ApplicableHeaderTradeDelivery
    │   ├── ShipToTradeParty (Delivery address)
    │   ├── ShipFromTradeParty (Ship from address)
    │   └── ActualDeliverySupplyChainEvent (Delivery date)
    │
    ├── ApplicableHeaderTradeSettlement
    │   ├── InvoiceCurrencyCode (THB)
    │   ├── ApplicableTradeTax (VAT info)
    │   │   ├── TypeCode (VAT)
    │   │   ├── CalculatedRate (7.00)
    │   │   ├── BasisAmount (Taxable amount)
    │   │   └── CalculatedAmount (Tax amount)
    │   ├── SpecifiedTradeAllowanceCharge (Discounts/charges)
    │   ├── SpecifiedTradePaymentTerms (Payment terms)
    │   └── SpecifiedTradeSettlementHeaderMonetarySummation
    │       ├── LineTotalAmount (Subtotal)
    │       ├── AllowanceTotalAmount (Total discounts)
    │       ├── ChargeTotalAmount (Total charges)
    │       ├── TaxBasisTotalAmount (Taxable amount)
    │       ├── TaxTotalAmount (Total tax)
    │       └── GrandTotalAmount (Grand total)
    │
    └── IncludedSupplyChainTradeLineItem (Repeated for each item)
        ├── AssociatedDocumentLineDocument
        │   └── LineID (Line number)
        ├── SpecifiedTradeProduct
        │   ├── ID (Product code)
        │   ├── Name (Product name)
        │   ├── Description
        │   └── DesignatedProductClassification (Product category)
        ├── SpecifiedLineTradeAgreement
        │   └── NetPriceProductTradePrice
        │       └── ChargeAmount (Unit price)
        ├── SpecifiedLineTradeDelivery
        │   └── BilledQuantity (Quantity)
        └── SpecifiedLineTradeSettlement
            ├── ApplicableTradeTax (Line item tax)
            └── SpecifiedTradeSettlementLineMonetarySummation
                └── LineTotalAmount (Line total)
```

## Required Fields

### Minimum Required for Valid Invoice

1. **ExchangedDocumentContext**
   - Guideline ID: "ER3-2560" with scheme "ETDA" and version "v2.1"

2. **ExchangedDocument**
   - ID (Invoice Number)
   - TypeCode (Document Type)
   - IssueDateTime (Issue Date)

3. **SellerTradeParty**
   - Name
   - SpecifiedTaxRegistration > ID (13-digit Thai Tax ID)
   - PostalTradeAddress (with at least country code)

4. **BuyerTradeParty**
   - Name
   - SpecifiedTaxRegistration > ID (13-digit Thai Tax ID)
   - PostalTradeAddress (with at least country code)

5. **ApplicableHeaderTradeSettlement**
   - InvoiceCurrencyCode (THB)
   - ApplicableTradeTax (VAT information)
   - SpecifiedTradeSettlementHeaderMonetarySummation (Totals)

6. **IncludedSupplyChainTradeLineItem** (At least 1 item)
   - LineID
   - Product Name
   - Quantity
   - Unit Price
   - Line Total

## Thai-Specific Requirements

### Tax ID Format
- **Format**: 13 digits
- **Example**: 1234567890123
- **Scheme ID**: "TaxID"
- **Scheme Agency**: "RD" (Revenue Department)

### Address Format
Thai addresses use specific codes:

```xml
<ram:PostalTradeAddress>
  <ram:PostcodeCode>10110</ram:PostcodeCode>
  <ram:BuildingName>อาคารสยามทาวเวอร์</ram:BuildingName>
  <ram:BuildingNumber>99</ram:BuildingNumber>
  <ram:StreetName>ถนนพระราม 4</ram:StreetName>
  <ram:CityName>1006</ram:CityName>              <!-- District code -->
  <ram:CitySubDivisionName>100601</ram:CitySubDivisionName>  <!-- Sub-district code -->
  <ram:CountryID>TH</ram:CountryID>
  <ram:CountrySubDivisionID>10</ram:CountrySubDivisionID>    <!-- Province code -->
</ram:PostalTradeAddress>
```

**Province Codes** (Database-backed):
- 10 = Bangkok (กรุงเทพมหานคร)
- 50 = Chiang Mai (เชียงใหม่)
- 80 = Phuket (ภูเก็ต)
- See `thai_province_code` table for complete list (77 provinces)

### VAT Rate
- **Standard Rate**: 7%
- **Type Code**: "VAT"
- **Calculated Rate**: 7.00

### Currency
- **Code**: THB (Thai Baht)
- **Numeric Code**: 764
- Database-backed from `iso_currency_code` table

## Code Lists (Database-Backed)

Your library provides database-backed code lists for:

| Code List | Table | Records | Usage |
|-----------|-------|---------|-------|
| Country Codes | `iso_country_code` | 252 | Country identification |
| Currency Codes | `iso_currency_code` | 180+ | Invoice currency |
| Province Codes | `thai_province_code` | 77 | Thai provinces |
| District/Sub-district | `tisi_subdistrict` | 8,940 | Thai subdivisions |
| Reference Type Codes | `unece_reference_type_code` | 798 | Document references |
| Document Name Codes (Invoice) | `document_name_code_invoice` | 17 | UN/CEFACT document types |
| Tax Type Codes | `duty_tax_fee_type_code` | - | Tax categories |
| Payment Terms | `payment_terms_type_code` | - | Payment terms |
| Delivery Terms | `delivery_terms_code` | - | INCOTERMS |
| Document Name Codes | `thai_document_name_code` | 12 | Thai document types |

## Calculation Examples

### Example 1: Simple Invoice (No Discounts)

```
Line Items:
  Item 1: 2 x 15,000.00 = 30,000.00
  Item 2: 5 x    500.00 =  2,500.00
                          ----------
Subtotal:                 32,500.00
VAT (7%):                  2,275.00
                          ----------
Grand Total:              34,775.00
```

### Example 2: Invoice with Discount

```
Line Items:
  Item 1: 2 x 15,000.00 = 30,000.00
  Item 2: 5 x    500.00 =  2,500.00
                          ----------
Subtotal:                 32,500.00
Discount (500.00):          -500.00
                          ----------
Taxable Amount:           32,000.00
VAT (7%):                  2,240.00
                          ----------
Grand Total:              34,240.00
```

### Example 3: Invoice with Discount and Charges

```
Line Items:
  Item 1: 2 x 15,000.00 = 30,000.00
  Item 2: 5 x    500.00 =  2,500.00
                          ----------
Subtotal:                 32,500.00
Discount (500.00):          -500.00
Shipping Charge (+200):     +200.00
                          ----------
Taxable Amount:           32,200.00
VAT (7%):                  2,254.00
                          ----------
Grand Total:              34,454.00
```

## XML Namespaces

```xml
<rsm:TaxInvoice_CrossIndustryInvoice
    xmlns:ram="urn:etda:uncefact:data:standard:TaxInvoice_ReusableAggregateBusinessInformationEntity:2"
    xmlns:rsm="urn:etda:uncefact:data:standard:TaxInvoice_CrossIndustryInvoice:2"
    xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:16"
    xmlns:qdt="urn:etda:uncefact:data:standard:QualifiedDataType:1"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
```

**Namespace Prefixes:**
- `rsm`: Root Schema Module (main invoice structure)
- `ram`: Reusable Aggregate Module (business entities)
- `udt`: Unqualified Data Types (basic types)
- `qdt`: Qualified Data Types (Thai-specific types)

## Common Validation Rules

1. **Tax ID**: Must be exactly 13 digits
2. **Document Type**: Must be one of: 388, 80, 81, 381, 383
3. **Currency**: Must be valid ISO 4217 code (usually THB)
4. **VAT Rate**: Standard rate is 7% in Thailand
5. **Line Total**: Must equal Quantity × Unit Price
6. **Tax Basis**: Must equal Subtotal - Allowances + Charges
7. **Grand Total**: Must equal Tax Basis + VAT
8. **Province Code**: Must exist in `thai_province_code` table
9. **Country Code**: Must exist in `iso_country_code` table

## Example XML Output

See the example files:
- [Example_Invoice_2p1_v1.xml](../../../src/main/resources/e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_Invoice_2p1_v1.xml)
- [Example_Receipt_2p1_v1.xml](../../../src/main/resources/e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_Receipt_2p1_v1.xml)
- [Example_CreditNote_2p1_v1.xml](../../../src/main/resources/e-tax-invoice-receipt-v2.1/ETDA/ExampleFile/Example_CreditNote_2p1_v1.xml)

## Code Examples

- [SimpleTaxInvoiceExample.java](SimpleTaxInvoiceExample.java) - Basic invoice creation
- [CompleteTaxInvoiceExample.java](CompleteTaxInvoiceExample.java) - Full-featured invoice with all options
- [InvoiceServiceExample.java](InvoiceServiceExample.java) - Production-ready service layer

## References

- ETDA e-Tax Invoice Specification v2.1
- UN/CEFACT CrossIndustryInvoice Standard
- [JAXB_INTEGRATION_GUIDE.md](../JAXB_INTEGRATION_GUIDE.md)
- [DATABASE_BACKED_JAXB.md](../DATABASE_BACKED_JAXB.md)
