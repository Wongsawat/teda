package com.wpanther.etax.integration;

import com.wpanther.etax.generated.debitcreditnote.rsm.DebitCreditNote_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.debitcreditnote.rsm.ObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test DebitCreditNote JAXB structure and ObjectFactory
 */
public class DebitCreditNoteStructureTest {

    @Test
    public void testObjectFactoryExists() {
        ObjectFactory factory = new ObjectFactory();
        assertNotNull(factory);
    }

    @Test
    public void testObjectFactoryCanCreateDebitCreditNoteInstance() {
        ObjectFactory factory = new ObjectFactory();
        // Test that the factory can create the root element
        assertNotNull(factory.createDebitCreditNote_CrossIndustryInvoice(null));
    }

    @Test
    public void testDebitCreditNoteTypeIsInterface() {
        // Verify that the DebitCreditNote type is an interface (due to generateValueClass="false")
        assertTrue(DebitCreditNote_CrossIndustryInvoiceType.class.isInterface());
    }
}
