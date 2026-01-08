package com.wpanther.etax.integration;

import com.wpanther.etax.generated.cancellationnote.rsm.CancellationNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.cancellationnote.rsm.ObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test CancellationNote JAXB structure and ObjectFactory
 */
public class CancellationNoteStructureTest {

    @Test
    public void testObjectFactoryExists() {
        ObjectFactory factory = new ObjectFactory();
        assertNotNull(factory);
    }

    @Test
    public void testObjectFactoryCanCreateCancellationNoteInstance() {
        ObjectFactory factory = new ObjectFactory();
        // Test that the factory can create the root element
        assertNotNull(factory.createCancellationNote_CrossIndustryInvoice(null));
    }

    @Test
    public void testCancellationNoteTypeIsInterface() {
        // Verify that the CancellationNote type is an interface (due to generateValueClass="false")
        assertTrue(CancellationNote_CrossIndustryInvoiceType.class.isInterface());
    }
}
