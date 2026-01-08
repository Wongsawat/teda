package com.wpanther.etax.generated.abbreviatedtaxinvoice;

import com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.AbbreviatedTaxInvoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.ObjectFactory;
import com.wpanther.etax.generated.cancellationnote.rsm.CancellationNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.debitcreditnote.rsm.DebitCreditNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.invoice.rsm.Invoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify AbbreviatedTaxInvoice JAXB classes were generated correctly
 */
public class AbbreviatedTaxInvoiceJAXBGenerationTest {

    @Test
    public void testAbbreviatedTaxInvoiceRootClassExists() {
        assertNotNull(AbbreviatedTaxInvoice_CrossIndustryInvoiceType.class);
        assertNotNull(ObjectFactory.class);
    }

    @Test
    public void testAbbreviatedTaxInvoiceHasDifferentNamespace() {
        String abbreviatedtaxinvoicePackage = AbbreviatedTaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String taxInvoicePackage = TaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String receiptPackage = Receipt_CrossIndustryInvoiceType.class.getPackage().getName();
        String invoicePackage = Invoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String debitcreditnotePackage = DebitCreditNote_CrossIndustryInvoiceType.class.getPackage().getName();
        String cancellationnotePackage = CancellationNote_CrossIndustryInvoiceType.class.getPackage().getName();

        assertTrue(abbreviatedtaxinvoicePackage.contains("abbreviatedtaxinvoice"));
        assertTrue(taxInvoicePackage.contains("taxinvoice"));
        assertTrue(receiptPackage.contains("receipt"));
        assertTrue(invoicePackage.contains("invoice"));
        assertTrue(debitcreditnotePackage.contains("debitcreditnote"));
        assertTrue(cancellationnotePackage.contains("cancellationnote"));
    }

    @Test
    public void testAbbreviatedTaxInvoiceSharesQDTWithTaxInvoiceReceiptDebitCreditNoteCancellationNote() {
        // AbbreviatedTaxInvoice shares common.qdt with TaxInvoice, Receipt, DebitCreditNote, and CancellationNote
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.qdt.ThaiInvoiceDocumentCodeType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common QDT package should exist");
        }
    }
}
