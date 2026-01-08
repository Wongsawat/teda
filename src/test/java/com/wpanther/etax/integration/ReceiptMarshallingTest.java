package com.wpanther.etax.integration;

import com.wpanther.etax.generated.receipt.rsm.Receipt_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.receipt.rsm.ObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Receipt JAXB structure and ObjectFactory
 */
public class ReceiptMarshallingTest {

    @Test
    public void testObjectFactoryExists() {
        ObjectFactory factory = new ObjectFactory();
        assertNotNull(factory);
    }

    @Test
    public void testObjectFactoryCanCreateReceiptInstance() {
        ObjectFactory factory = new ObjectFactory();
        // Test that the factory can create the root element
        assertNotNull(factory.createReceipt_CrossIndustryInvoice(null));
    }

    @Test
    public void testReceiptTypeIsInterface() {
        // Verify that the Receipt type is an interface (due to generateValueClass="false")
        assertTrue(Receipt_CrossIndustryInvoiceType.class.isInterface());
    }
}
