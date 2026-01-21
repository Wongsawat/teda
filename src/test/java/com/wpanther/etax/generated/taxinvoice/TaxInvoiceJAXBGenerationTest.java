package com.wpanther.etax.generated.taxinvoice;

import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.taxinvoice.rsm.ObjectFactory;
import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify TaxInvoice JAXB classes were generated correctly
 */
public class TaxInvoiceJAXBGenerationTest {

    @Test
    public void testTaxInvoiceRootClassExists() {
        assertNotNull(TaxInvoice_CrossIndustryInvoiceType.class);
        assertNotNull(ObjectFactory.class);
    }

    @Test
    public void testTaxInvoiceAndReceiptAreDifferent() {
        // Verify that TaxInvoice and Receipt classes are in different packages
        String taxInvoicePackage = TaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();
        String receiptPackage = Receipt_CrossIndustryInvoiceType.class.getPackage().getName();

        assertTrue(taxInvoicePackage.contains("taxinvoice"));
        assertTrue(receiptPackage.contains("receipt"));
        assertNotEquals(taxInvoicePackage, receiptPackage);
    }

    @Test
    public void testCommonPackagesExist() {
        // Verify that common UDT/QDT packages exist
        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.udt.IDType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common UDT package should exist");
        }

        try {
            Class<?> clazz = Class.forName("com.wpanther.etax.generated.common.qdt.ThaiMessageFunctionCodeType");
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Common QDT package should exist");
        }
    }
}
