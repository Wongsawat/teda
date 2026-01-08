package com.wpanther.etax.integration;

import com.wpanther.etax.generated.invoice.rsm.Invoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.invoice.rsm.ObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Invoice JAXB structure and ObjectFactory
 */
public class InvoiceStructureTest {

    @Test
    public void testObjectFactoryExists() {
        ObjectFactory factory = new ObjectFactory();
        assertNotNull(factory);
    }

    @Test
    public void testObjectFactoryCanCreateInvoiceInstance() {
        ObjectFactory factory = new ObjectFactory();
        // Test that the factory can create the root element
        assertNotNull(factory.createInvoice_CrossIndustryInvoice(null));
    }

    @Test
    public void testInvoiceTypeIsInterface() {
        // Verify that the Invoice type is an interface (due to generateValueClass="false")
        assertTrue(Invoice_CrossIndustryInvoiceType.class.isInterface());
    }
}
