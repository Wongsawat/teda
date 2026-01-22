package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.UNECEDocumentNameCodeInvoiceAdapter;
import com.wpanther.etax.core.entity.UNECEDocumentNameCodeInvoice;
import com.wpanther.etax.core.repository.UNECEDocumentNameCodeInvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UNECEDocumentNameCodeInvoiceAdapterTest {

    @Mock
    private UNECEDocumentNameCodeInvoiceRepository repository;

    @InjectMocks
    private UNECEDocumentNameCodeInvoiceAdapter adapter;

    private UNECEDocumentNameCodeInvoice commercialInvoice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set static repository for adapter
        new UNECEDocumentNameCodeInvoiceAdapter();
        adapter.setRepository(repository);

        // Create test entity
        commercialInvoice = new UNECEDocumentNameCodeInvoice("380");
        commercialInvoice.setName("Commercial invoice");
        commercialInvoice.setCategory("Invoice");
        commercialInvoice.setIsCredit(false);
        commercialInvoice.setIsDebit(false);
        commercialInvoice.setRequiresPayment(true);
    }

    @Test
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("380")).thenReturn(Optional.of(commercialInvoice));

        UNECEDocumentNameCodeInvoice result = adapter.unmarshal("380");

        assertNotNull(result);
        assertEquals("380", result.getCode());
        assertEquals("Commercial invoice", result.getName());
        assertEquals("Invoice", result.getCategory());
    }

    @Test
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("999")).thenReturn(Optional.empty());

        UNECEDocumentNameCodeInvoice result = adapter.unmarshal("999");

        assertNotNull(result);
        assertEquals("999", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    public void testMarshal() throws Exception {
        String result = adapter.marshal(commercialInvoice);

        assertEquals("380", result);
    }

    @Test
    public void testMarshalNull() throws Exception {
        String result = adapter.marshal(null);

        assertNull(result);
    }

    @Test
    public void testIsValid() {
        when(repository.existsById("380")).thenReturn(true);
        when(repository.existsById("999")).thenReturn(false);

        assertTrue(UNECEDocumentNameCodeInvoiceAdapter.isValid("380"));
        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.isValid("999"));
    }

    @Test
    public void testGetDocumentName() {
        when(repository.findByCode("380")).thenReturn(Optional.of(commercialInvoice));

        String name = UNECEDocumentNameCodeInvoiceAdapter.getDocumentName("380");

        assertEquals("Commercial invoice", name);
    }

    @Test
    public void testIsCreditNote() {
        UNECEDocumentNameCodeInvoice creditNote = new UNECEDocumentNameCodeInvoice("381");
        creditNote.setName("Credit note");
        creditNote.setIsCredit(true);

        when(repository.findByCode("381")).thenReturn(Optional.of(creditNote));

        assertTrue(UNECEDocumentNameCodeInvoiceAdapter.isCreditNote("381"));
        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.isDebitNote("381"));
    }

    // Unmarshal null/empty tests

    @Test
    public void testUnmarshalNull() throws Exception {
        UNECEDocumentNameCodeInvoice result = adapter.unmarshal(null);
        assertNull(result);
    }

    @Test
    public void testUnmarshalEmpty() throws Exception {
        UNECEDocumentNameCodeInvoice result = adapter.unmarshal("");
        assertNull(result);
    }

    @Test
    public void testUnmarshalWhitespace() throws Exception {
        UNECEDocumentNameCodeInvoice result = adapter.unmarshal("   ");
        assertNull(result);
    }

    // Marshal with empty/null code

    @Test
    public void testMarshalEmptyCode() throws Exception {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    public void testMarshalNullCode() throws Exception {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // getDocumentDescription tests

    @Test
    public void testGetDocumentDescription() {
        commercialInvoice.setDescription("A document claiming payment");
        when(repository.findByCode("380")).thenReturn(Optional.of(commercialInvoice));

        String description = UNECEDocumentNameCodeInvoiceAdapter.getDocumentDescription("380");
        assertEquals("A document claiming payment", description);
    }

    @Test
    public void testGetDocumentDescriptionWithNullCode() {
        String description = UNECEDocumentNameCodeInvoiceAdapter.getDocumentDescription(null);
        assertNull(description);
    }

    @Test
    public void testGetDocumentDescriptionNotFound() {
        when(repository.findByCode("999")).thenReturn(Optional.empty());
        String description = UNECEDocumentNameCodeInvoiceAdapter.getDocumentDescription("999");
        assertNull(description);
    }

    // isDebitNote tests

    @Test
    public void testIsDebitNoteTrue() {
        UNECEDocumentNameCodeInvoice debitNote = new UNECEDocumentNameCodeInvoice("383");
        debitNote.setName("Debit note");
        debitNote.setIsDebit(true);
        when(repository.findByCode("383")).thenReturn(Optional.of(debitNote));

        assertTrue(UNECEDocumentNameCodeInvoiceAdapter.isDebitNote("383"));
    }

    @Test
    public void testIsDebitNoteFalse() {
        when(repository.findByCode("380")).thenReturn(Optional.of(commercialInvoice));
        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.isDebitNote("380"));
    }

    @Test
    public void testIsDebitNoteWithNullCode() {
        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.isDebitNote(null));
    }

    // requiresPayment tests

    @Test
    public void testRequiresPaymentTrue() {
        when(repository.findByCode("380")).thenReturn(Optional.of(commercialInvoice));
        assertTrue(UNECEDocumentNameCodeInvoiceAdapter.requiresPayment("380"));
    }

    @Test
    public void testRequiresPaymentFalse() {
        UNECEDocumentNameCodeInvoice creditNote = new UNECEDocumentNameCodeInvoice("381");
        creditNote.setRequiresPayment(false);
        when(repository.findByCode("381")).thenReturn(Optional.of(creditNote));

        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.requiresPayment("381"));
    }

    @Test
    public void testRequiresPaymentWithNullCode() {
        assertTrue(UNECEDocumentNameCodeInvoiceAdapter.requiresPayment(null));
    }

    // getDocumentName with null code

    @Test
    public void testGetDocumentNameWithNullCode() {
        String name = UNECEDocumentNameCodeInvoiceAdapter.getDocumentName(null);
        assertNull(name);
    }

    @Test
    public void testGetDocumentNameNotFound() {
        when(repository.findByCode("999")).thenReturn(Optional.empty());
        String name = UNECEDocumentNameCodeInvoiceAdapter.getDocumentName("999");
        assertNull(name);
    }

    // isValid with null code

    @Test
    public void testIsValidWithNullCode() {
        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.isValid(null));
    }

    @Test
    public void testIsValidWithEmptyCode() {
        assertFalse(UNECEDocumentNameCodeInvoiceAdapter.isValid(""));
    }
}
