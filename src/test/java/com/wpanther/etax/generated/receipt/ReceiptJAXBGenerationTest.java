package com.wpanther.etax.generated.receipt;

import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.receipt.rsm.ObjectFactory;
import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify Receipt JAXB classes were generated correctly
 */
public class ReceiptJAXBGenerationTest {

    @Test
    public void testReceiptRootClassExists() {
        assertNotNull(Receipt_CrossIndustryInvoiceType.class);
        assertNotNull(ObjectFactory.class);
    }

    @Test
    public void testReceiptAndTaxInvoiceAreDifferent() {
        // Verify that Receipt and TaxInvoice classes are in different packages
        String receiptPackage = Receipt_CrossIndustryInvoiceType.class.getPackage().getName();
        String taxInvoicePackage = TaxInvoice_CrossIndustryInvoiceType.class.getPackage().getName();

        assertTrue(receiptPackage.contains("receipt"));
        assertTrue(taxInvoicePackage.contains("taxinvoice"));
        assertNotEquals(receiptPackage, taxInvoicePackage);
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
