package com.wpanther.etax.generated.debitcreditnote;

import com.wpanther.etax.generated.debitcreditnote.rsm.DebitCreditNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.debitcreditnote.rsm.ObjectFactory;
import com.wpanther.etax.generated.invoice.rsm.Invoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify DebitCreditNote JAXB classes were generated correctly
 */
public class DebitCreditNoteJAXBGenerationTest {

    @Test
    public void testDebitCreditNoteRootClassExists() {
        assertNotNull(DebitCreditNote_CrossIndustryInvoiceType.class);
        assertNotNull(ObjectFactory.class);
    }

    @Test
    public void testDebitCreditNoteHasDifferentNamespace() {
        String debitcreditnotePackage = DebitCreditNote_CrossIndustryInvoiceType.class.getPackage().getName();
        String taxInvoicePackage = TaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String receiptPackage = Receipt_CrossIndustryInvoiceType.class.getPackage().getName();
        String invoicePackage = Invoice_CrossIndustryInvoiceType.class.getPackage().getName();

        assertTrue(debitcreditnotePackage.contains("debitcreditnote"));
        assertTrue(taxInvoicePackage.contains("taxinvoice"));
        assertTrue(receiptPackage.contains("receipt"));
        assertTrue(invoicePackage.contains("invoice"));
    }

    @Test
    public void testDebitCreditNoteSharesQDTWithTaxInvoiceReceipt() {
        // DebitCreditNote shares common.qdt with TaxInvoice and Receipt
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.qdt.ThaiInvoiceDocumentCodeType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common QDT package should exist");
        }
    }
}
