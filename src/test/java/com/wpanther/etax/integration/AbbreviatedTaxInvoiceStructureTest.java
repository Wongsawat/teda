package com.wpanther.etax.integration;

import com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.AbbreviatedTaxInvoice_CrossIndustryInvoiceType;
import com.wpanther.etax.generated.abbreviatedtaxinvoice.rsm.ObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test AbbreviatedTaxInvoice JAXB structure and ObjectFactory
 */
public class AbbreviatedTaxInvoiceStructureTest {

    @Test
    public void testObjectFactoryExists() {
        ObjectFactory factory = new ObjectFactory();
        assertNotNull(factory);
    }

    @Test
    public void testObjectFactoryCanCreateAbbreviatedTaxInvoiceInstance() {
        ObjectFactory factory = new ObjectFactory();
        // Test that the factory can create the root element
        assertNotNull(factory.createAbbreviatedTaxInvoice_CrossIndustryInvoice(null));
    }

    @Test
    public void testAbbreviatedTaxInvoiceTypeIsInterface() {
        // Verify that the AbbreviatedTaxInvoice type is an interface (due to generateValueClass="false")
        assertTrue(AbbreviatedTaxInvoice_CrossIndustryInvoiceType.class.isInterface());
    }
}
