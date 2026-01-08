package com.wpanther.etax.generated.cancellationnote;

import com.wpanther.etax.generated.cancellationnote.rsm.CancellationNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.cancellationnote.rsm.ObjectFactory;
import com.wpanther.etax.generated.debitcreditnote.rsm.DebitCreditNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.invoice.rsm.Invoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify CancellationNote JAXB classes were generated correctly
 */
public class CancellationNoteJAXBGenerationTest {

    @Test
    public void testCancellationNoteRootClassExists() {
        assertNotNull(CancellationNote_CrossIndustryInvoiceType.class);
        assertNotNull(ObjectFactory.class);
    }

    @Test
    public void testCancellationNoteHasDifferentNamespace() {
        String cancellationnotePackage = CancellationNote_CrossIndustryInvoiceType.class.getPackage().getName();
        String taxInvoicePackage = TaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String receiptPackage = Receipt_CrossIndustryInvoiceType.class.getPackage().getName();
        String invoicePackage = Invoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String debitcreditnotePackage = DebitCreditNote_CrossIndustryInvoiceType.class.getPackage().getName();

        assertTrue(cancellationnotePackage.contains("cancellationnote"));
        assertTrue(taxInvoicePackage.contains("taxinvoice"));
        assertTrue(receiptPackage.contains("receipt"));
        assertTrue(invoicePackage.contains("invoice"));
        assertTrue(debitcreditnotePackage.contains("debitcreditnote"));
    }

    @Test
    public void testCancellationNoteSharesQDTWithTaxInvoiceReceiptDebitCreditNote() {
        // CancellationNote shares common.qdt with TaxInvoice, Receipt, and DebitCreditNote
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.qdt.ThaiInvoiceDocumentCodeType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common QDT package should exist");
        }
    }
}
