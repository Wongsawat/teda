package com.wpanther.etax.integration;

import com.wpanther.etax.generated.taxinvoice.rsm.TaxInvoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.taxinvoice.rsm.ObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test TaxInvoice JAXB structure and ObjectFactory
 */
public class TaxInvoiceStructureTest {

    @Test
    public void testObjectFactoryExists() {
        ObjectFactory factory = new ObjectFactory();
        assertNotNull(factory);
    }

    @Test
    public void testObjectFactoryCanCreateTaxInvoiceInstance() {
        ObjectFactory factory = new ObjectFactory();
        // Test that the factory can create the root element
        assertNotNull(factory.createTaxInvoice_CrossIndustryInvoice(null));
    }

    @Test
    public void testTaxInvoiceTypeIsInterface() {
        // Verify that the TaxInvoice type is an interface (due to generateValueClass="false")
        assertTrue(TaxInvoice_CrossIndustryInvoiceType.class.isInterface());
    }
}
