package com.wpanther.etax.generated.invoice;

import com.wpanther.etax.generated.invoice.rsm.Invoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.invoice.rsm.ObjectFactory;
import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify Invoice JAXB classes were generated correctly
 */
public class InvoiceJAXBGenerationTest {

    @Test
    public void testInvoiceRootClassExists() {
        assertNotNull(Invoice_CrossIndustryInvoiceType.class);
        assertNotNull(ObjectFactory.class);
    }

    @Test
    public void testInvoiceHasDifferentNamespace() {
        String invoicePackage = Invoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String taxInvoicePackage = TaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String receiptPackage = Receipt_CrossIndustryInvoiceType.class.getPackage().getName();

        assertTrue(invoicePackage.contains("invoice"));
        assertTrue(taxInvoicePackage.contains("taxinvoice"));
        assertTrue(receiptPackage.contains("receipt"));
    }

    @Test
    public void testInvoiceHasOwnQDTPackage() {
        // Invoice has its own QDT package (not shared with TaxInvoice/Receipt)
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.invoice.qdt.InvoiceDocumentCodeType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Invoice QDT package should exist with InvoiceDocumentCodeType");
        }
    }

    @Test
    public void testSharedUDTPackageExists() {
        // All three document types share the same UDT package
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.udt.IDType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common UDT package should exist");
        }
    }

    @Test
    public void testCommonQDTPackageStillExistsForTaxInvoiceReceipt() {
        // common.qdt should still exist for TaxInvoice/Receipt
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.qdt.ThaiInvoiceDocumentCodeType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common QDT package should still exist for TaxInvoice/Receipt");
        }
    }
}
