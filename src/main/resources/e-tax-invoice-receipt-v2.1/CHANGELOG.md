# Change Details @ 2024-02-13
---------------------------
# เพิ่ม Codelist CategoryCode รหัสที่ระบุหมวดหมู่ของเอกสารที่อ้างอิง
1.เพิ่มไฟล์ e-tax-invoice-receipt\e-tax-invoice-receipt\ETDA\codelist\standard\ThaiCategoryCode_1p0.xsd
2.แก้ไขไฟล์ QualifiedDataType_1p0.xsd ตัวแปร ThaiCategoryCodeType
3.แก้ไขไฟล์ Invoice_QualifiedDataType_1p0.xsd ตัวแปร ThaiCategoryCodeType

# ใบกากับภาษีอิเล็กทรอนิกส์:TaxInvoice_CrossIndustryInvoice
4.แก้ไขไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูล 3.1.5.4  CategoryCode รหัสที่ระบุหมวดหมู่ของเอกสารที่อ้างอิง 
5.ไฟล์ TaxInvoice_Schematron_2p1.sch เพิ่มการเช็ค 3.1.5.4  CategoryCode  รหัสระบุหมวดหมู่ของเอกสารที่อ้างอิงต้องเป็น 02 
6.แก้ไขไฟล์ตัวอย่างเพิ่ม 3.1.5.4  CategoryCode

# ใบรับอิเล็กทรอนิกส์:Receipt_CrossIndustryInvoice
7.แก้ไขไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูล 3.1.5.4  CategoryCode รหัสที่ระบุหมวดหมู่ของเอกสารที่อ้างอิง 
8.ไฟล์ Receipt_Schematron_2p1.sch เพิ่มการเช็ค 3.1.5.4  CategoryCode  รหัสระบุหมวดหมู่ของเอกสารที่อ้างอิงต้องเป็น 02 

# ใบแจ้งหนี้อิเล็กทรอนิกส์:Invoice_CrossIndustryInvoice  (อยู่ระหว่างศึกษากรณีเงินรับล่วงหน้าในใบแจ้งหนี้ จึงยังไม่ได้ปรับ schematron ให้มี business rule เรื่องเงินรับล่วงหน้า)
9.แก้ไขไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูล 3.1.5.4  CategoryCode รหัสที่ระบุหมวดหมู่ของเอกสารที่อ้างอิง 

# ใบเพิ่มหนี้/ใบลดหนี้อิเล็กทรอนิกส์:DebitCreditNote_CrossIndustryInvoice
10.แก้ไขไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูล 3.1.5.4  CategoryCode รหัสที่ระบุหมวดหมู่ของเอกสารที่อ้างอิง 
11.ไฟล์ DebitCreditNote_Schematron_2p1.sch เพิ่มการเช็ค 3.1.5.4  CategoryCode  รหัสระบุหมวดหมู่ของเอกสารที่อ้างอิงต้องเป็น 02 
12.แก้ไขไฟล์ตัวอย่างเพิ่ม 3.1.5.4  CategoryCode

# ใบกากับภาษีอย่างย่ออิเล็กทรอนิกส์:AbbreviatedTaxInvoice_CrossIndustryInvoice (อยู่ระหว่างศึกษากรณีเงินรับล่วงหน้าในใบแจ้งหนี้ จึงยังไม่ได้ปรับ schematron ให้มี business rule เรื่องเงินรับล่วงหน้า)
13.แก้ไขไฟล์ AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูล 3.1.2.4  CategoryCode รหัสที่ระบุหมวดหมู่ของเอกสารที่อ้างอิง 


# Change Details @ 2024-12-20
---------------------------
# ใบกากับภาษีอิเล็กทรอนิกส์:TaxInvoice_CrossIndustryInvoice
1.แก้ไข version ของไฟล์ TaxInvoice_CrossIndustryInvoice_2p0.xsd เป็น TaxInvoice_CrossIndustryInvoice_2p1.xsd
2.แก้ไข version ของไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p0.xsd เป็น TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd
3.แก้ไข version ของไฟล์ TaxInvoice_Schematron_2p0.sch เป็น TaxInvoice_Schematron_2p1.sch
4.ไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของ TradeAllowanceChargeType เป็น 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2  PrepaidIndicator ตัวบอกเงินรับล่วงหน้า
5.ไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไขการเรียง sequence จาก ChargeIndicator, ActualAmount, ReasonCode, Reason, TypeCode เป็น ChargeIndicator, PrepaidIndicator, ActualAmount, ReasonCode, Reason, TypeCode
6.ไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไข PrepaidIndicator สำหรับ 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2 ให้ fixed="true"
7.ไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของผลรวมเงินรับล่วงหน้า SpecifiedTradeSettlementHeaderMonetarySummation เป็น 3.3.5.9
8.ไฟล์ TaxInvoice_Schematron_2p1.sch เพิ่มการเช็ค PrepaidIndicator ของ 3.3.3.2 , 3.4.3.1.2.2 และ 3.4.5.2.2
9.เพิ่มไฟล์ตัวอย่างกรณีเงินรับล่วงหน้า PrepaidIndicator ของ 3.3.3.2 , 3.4.3.1.2.2 และ 3.4.5.2.2

# ใบรับอิเล็กทรอนิกส์:Receipt_CrossIndustryInvoice
10.แก้ไข version ของไฟล์ Receipt_CrossIndustryInvoice_2p0.xsd เป็น Receipt_CrossIndustryInvoice_2p1.xsd
11.แก้ไข version ของไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p0.xsd เป็น Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd
12.แก้ไข version ของไฟล์ Receipt_Schematron_2p0.sch เป็น Receipt_Schematron_2p1.sch
13.ไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของ TradeAllowanceChargeType เป็น 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2  PrepaidIndicator ตัวบอกเงินรับล่วงหน้า
14.ไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไขการเรียง sequence จาก ChargeIndicator, ActualAmount, ReasonCode, Reason, TypeCode เป็น ChargeIndicator, PrepaidIndicator, ActualAmount, ReasonCode, Reason, TypeCode
15.ไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไข PrepaidIndicator สำหรับ 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2 ให้ fixed="true"
16.ไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของผลรวมเงินรับล่วงหน้า SpecifiedTradeSettlementHeaderMonetarySummation เป็น 3.3.5.9
17.ไฟล์ Receipt_Schematron_2p1.sch เพิ่มการเช็ค PrepaidIndicator ของ 3.3.3.2 , 3.4.3.1.2.2 และ 3.4.5.2.2
18.เพิ่มไฟล์ตัวอย่างกรณีเงินรับล่วงหน้า PrepaidIndicator ของ 3.3.3.2 , 3.4.3.1.2.2 และ 3.4.5.2.2

# ใบแจ้งหนี้อิเล็กทรอนิกส์:Invoice_CrossIndustryInvoice (อยู่ระหว่างศึกษากรณีเงินรับล่วงหน้าในใบแจ้งหนี้ จึงยังไม่ได้ปรับ schematron ให้มี business rule เรื่องเงินรับล่วงหน้า)
19.แก้ไข version ของไฟล์ Invoice_CrossIndustryInvoice_2p0.xsd เป็น Invoice_CrossIndustryInvoice_2p1.xsd
20.แก้ไข version ของไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p0.xsd เป็น Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd
21.แก้ไข version ของไฟล์ Invoice_Schematron_2p0.sch เป็น Invoice_Schematron_2p1.sch
22.ไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p0.xsd เพิ่มโครงสร้างข้อมูลของ TradeAllowanceChargeType เป็น 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2  PrepaidIndicator ตัวบอกเงินรับล่วงหน้า
23.ไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p0.xsd แก้ไขการเรียง sequence จาก ChargeIndicator, ActualAmount, ReasonCode, Reason, TypeCode เป็น ChargeIndicator, PrepaidIndicator, ActualAmount, ReasonCode, Reason, TypeCode
24.ไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไข PrepaidIndicator สำหรับ 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2 ให้ fixed="true"
25.ไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของผลรวมเงินรับล่วงหน้า SpecifiedTradeSettlementHeaderMonetarySummation เป็น 3.3.5.9


# ใบเพิ่มหนี้/ใบลดหนี้อิเล็กทรอนิกส์:DebitCreditNote_CrossIndustryInvoice 
26.แก้ไข version ของไฟล์ DebitCreditNote_CrossIndustryInvoice_2p0.xsd เป็น DebitCreditNote_CrossIndustryInvoice_2p1.xsd
27.แก้ไข version ของไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p0.xsd เป็น DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p1.xsd
28.แก้ไข version ของไฟล์ DebitCreditNote_Schematron_2p0.sch เป็น DebitCreditNote_Schematron_2p1.sch
29.ไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p0.xsd  เพิ่มโครงสร้างข้อมูลของ TradeAllowanceChargeType เป็น 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2  PrepaidIndicator ตัวบอกเงินรับล่วงหน้า
30.ไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p0.xsd แก้ไขการเรียง sequence จาก ChargeIndicator, ActualAmount, ReasonCode, Reason, TypeCode เป็น ChargeIndicator, PrepaidIndicator, ActualAmount, ReasonCode, Reason, TypeCode
31.ไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไข PrepaidIndicator สำหรับ 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2 ให้ fixed="true"
32.ไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของผลรวมเงินรับล่วงหน้า SpecifiedTradeSettlementHeaderMonetarySummation เป็น 3.3.5.9
33.ไฟล์ DebitCreditNote_Schematron_2p0.sch เพิ่มการเช็ค PrepaidIndicator ของ 3.3.3.2 , 3.4.3.1.2.2 และ 3.4.5.2.2
34.เพิ่มไฟล์ตัวอย่างกรณีเงินรับล่วงหน้า PrepaidIndicator ของ 3.3.3.2 , 3.4.3.1.2.2 และ 3.4.5.2.2

# ใบกากับภาษีอย่างย่ออิเล็กทรอนิกส์:AbbreviatedTaxInvoice_CrossIndustryInvoice (อยู่ระหว่างศึกษากรณีเงินรับล่วงหน้าในใบแจ้งหนี้ จึงยังไม่ได้ปรับ schematron ให้มี business rule เรื่องเงินรับล่วงหน้า)
35.แก้ไข version ของไฟล์ AbbreviatedTaxInvoice_CrossIndustryInvoice_2p0.xsd เป็น AbbreviatedTaxInvoice_CrossIndustryInvoice_2p1.xsd
36.แก้ไข version ของไฟล์ AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p0.xsd เป็น AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd
37.แก้ไข version ของไฟล์ AbbreviatedTaxInvoice_Schematron_2p0.sch เป็น AbbreviatedTaxInvoice_Schematron_2p1.sch
38.ไฟล์ AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p0.xsd เพิ่มโครงสร้างข้อมูลของ TradeAllowanceChargeType เป็น 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2  PrepaidIndicator ตัวบอกเงินรับล่วงหน้า
39.ไฟล์ AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p0.xsd แก้ไขการเรียง sequence จาก ChargeIndicator, ActualAmount, ReasonCode, Reason, TypeCode เป็น ChargeIndicator, PrepaidIndicator, ActualAmount, ReasonCode, Reason, TypeCode
40.ไฟล์ AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd แก้ไข PrepaidIndicator สำหรับ 3.3.3.2, 3.4.3.1.2.2 และ 3.4.5.2.2 ให้ fixed="true"
41.ไฟล์ AbbreviatedTaxInvoice_ReusableAggregateBusinessInformationEntity_2p1.xsd เพิ่มโครงสร้างข้อมูลของผลรวมเงินรับล่วงหน้า SpecifiedTradeSettlementHeaderMonetarySummation เป็น 3.3.5.9

# เพิ่ม Codelist
42.UNECE_AllowanceChargeIdentificationCode_D14A.xsd เพิ่ม Codelist เงินรับล่วงหน้าของประเทศไทย (Pre-Paid) 6 รายการดังนี้ PP001 = เงินมัดจำ, PP002= เงินประกัน, PP003= เงินจอง, PP004= เงินจ่ายล่วงหน้า, PP005= เงินประกันผลงาน, PP006=เงินรับล่วงหน้าอื่น ๆ 


# Change Details @ 2024-08-02
---------------------------
1.ไฟล์ Invoice_ReusableAggregateBusinessInformationEntity_2p0.xsd แก้ไขโครงสร้างข้อมูลของ 3.3.4.3 TypeCode จากประเภท Max16Text เป็น PaymentTermsTypeCodeType
2.ไฟล์ TaxInvoice_ReusableAggregateBusinessInformationEntity_2p0.xsd แก้ไขโครงสร้างข้อมูลของ 3.3.4.3 TypeCode จากประเภท Max16Text เป็น PaymentTermsTypeCodeType
3.ไฟล์ AbbreviatedTaxInvoice_CrossIndustryInvoice_2p0.xsd แก้ไขโครงสร้างข้อมูลของ 3.3.4.3 TypeCode จากประเภท Max16Text เป็น PaymentTermsTypeCodeType

# Change Details @ 2024-07-25
---------------------------
1. ไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p0.xsd ปรับโครงสร้างข้อมูลของ 3.3.4 SpecifiedTradePaymentTerms แก้ไขการเรียง sequence จาก TypeCode, Description, DueDateDateTime  เป็น Description, DueDateDateTime, TypeCode 
2. ไฟล์ Receipt_ReusableAggregateBusinessInformationEntity_2p0.xsd แก้ไข index 3.4.2.6.2 ClassName ชื่อหมวดหมู่สินค้า ปรับ Multi เป็น 0..n
3. ไฟล์ Receipt_CrossIndustryInvoice_2p0.sch แก้ไข index 3.1.5.3 เพิ่มเงื่อนไขให้ ReferenceTypeCode สามารถรับค่า T05, T06 ได้ 
4. ไฟล์ DebitCreditNote_ReusableAggregateBusinessInformationEntity_2p0.xsd ปรับโครงสร้างข้อมูลของ 3.3.4.1 SpecifiedTradePaymentTerms แก้ไขการเรียง sequence จาก TypeCode, Description, DueDateDateTime  เป็น Description, DueDateDateTime, TypeCode
5. ไฟล์ DebitCreditNote_CrossIndustryInvoice_2p0.sch แก้ไข index 3.1.5.3 เพิ่มเงื่อนไขให้ ReferenceTypeCode สามารถรับค่า T05, T06 ได้ 

# Change Details @ 2024-01-30
---------------------------
เพิ่ม Country Code 3 รายการได้แก่ AN, KS, UN

# Change Details @ 2022-08-30
---------------------------
1.  DebitCredit_Schematron_2p0.sch แก้ไชไฟล์ Schematron แก้ filed IssueDateTime และ IssuerAssignID เป็น Mandatory
2.  TaxInvoice_Schematron_2p0.sch แก้ไชไฟล์ Schematron แก้ filed IssueDateTime และ IssuerAssignID เป็น Mandatory

# Change Details @ 2021-10-26
---------------------------
1.  Invoice_Schematron_2p0.sch แก้ไชไฟล์ Schematron ยกเลิกการเช็ค Name ของเอกสาร
	
# Change Details @ 2021-03-22
---------------------------
1.  Invoice_CrossIndustryInvoice_2p0.xsd 
    1.1.  เพิ่ม Element Signature ใน schema  
    


# Change Details @ 2019-12-11
---------------------------
1. แก้ไข TaxInvoice_Schematron_2p0.sch ดังนี้
    
    1.1. แก้ไข TIV-InvoicerTradeParty-008 กรณีระบุประเภทเลขประจำตัวผู้เสียภาษีอากรของผู้ซื้อ 
         เปลี่ยนเป็น TIV-InvoicerTradeParty-008 กรณีระบุประเภทเลขประจำตัวผู้เสียภาษีอากรของผู้ออกเอกสารแทน
   
    1.2. แก้ไข (BuyerTradeParty/SpecifiedTaxRegistration/ID) เปลี่ยนเป็น (InvoicerTradeParty/SpecifiedTaxRegistration/ID)

2. แก้ไข Receipt_Schematron_2p0.sch ดังนี้ 
    
    2.1. แก้ไข RCT-InvoicerTradeParty-008 กรณีระบุประเภทเลขประจำตัวผู้เสียภาษีอากรของผู้ซื้อ 
         เปลี่ยนเป็น  RCT-InvoicerTradeParty-008 กรณีระบุประเภทเลขประจำตัวผู้เสียภาษีอากรของผู้ออกเอกสารแทน
    
    2.2.  แก้ไข (BuyerTradeParty/SpecifiedTaxRegistration/ID) เปลี่ยนเป็น (InvoicerTradeParty/SpecifiedTaxRegistration/ID)

3. แก้ไขไฟล์ DebitCreditNote_Schematron_2p0.sch ดังนี้
  
    3.1.  แก้ไข DCN-InvoicerTradeParty-008 กรณีระบุประเภทเลขประจำตัวผู้เสียภาษีอากรของผู้ซื้อ 
         เปลี่ยนเป็น DCN-InvoicerTradeParty-008 กรณีระบุประเภทเลขประจำตัวผู้เสียภาษีอากรของผู้ออกเอกสารแทน
    
    3.2.  แก้ไข (BuyerTradeParty/SpecifiedTaxRegistration/ID) เปลี่ยนเป็น (InvoicerTradeParty/SpecifiedTaxRegistration/ID)