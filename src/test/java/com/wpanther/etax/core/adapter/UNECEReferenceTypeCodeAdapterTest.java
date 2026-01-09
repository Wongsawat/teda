package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.UNECEReferenceTypeCodeAdapter;
import com.wpanther.etax.core.entity.UNECEReferenceTypeCode;
import com.wpanther.etax.core.repository.UNECEReferenceTypeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UNECEReferenceTypeCode Adapter Tests")
public class UNECEReferenceTypeCodeAdapterTest {

    @Mock
    private UNECEReferenceTypeCodeRepository repository;

    @InjectMocks
    private UNECEReferenceTypeCodeAdapter adapter;

    private UNECEReferenceTypeCode invoiceReference;
    private UNECEReferenceTypeCode purchaseOrder;
    private UNECEReferenceTypeCode deliveryReference;
    private UNECEReferenceTypeCode etdaExtension;

    @BeforeEach
    public void setUp() {
        new UNECEReferenceTypeCodeAdapter();
        adapter.setRepository(repository);

        invoiceReference = new UNECEReferenceTypeCode("AAA", "Invoice reference");
        invoiceReference.setActive(true);
        invoiceReference.setEtdaExtension(false);

        purchaseOrder = new UNECEReferenceTypeCode("ABO", "Purchase order reference");
        purchaseOrder.setActive(true);
        purchaseOrder.setEtdaExtension(false);

        deliveryReference = new UNECEReferenceTypeCode("DQ", "Delivery reference");
        deliveryReference.setActive(true);
        deliveryReference.setEtdaExtension(false);

        etdaExtension = new UNECEReferenceTypeCode("T01", "ETDA Extension 1");
        etdaExtension.setActive(true);
        etdaExtension.setEtdaExtension(true);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(invoiceReference);
        assertEquals("AAA", result);
    }

    @Test
    @DisplayName("Should marshal null entity to null")
    public void testMarshalNull() throws Exception {
        String result = adapter.marshal(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with null code to null")
    public void testMarshalNullCode() throws Exception {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCodeAndActive("AAA")).thenReturn(Optional.of(invoiceReference));

        UNECEReferenceTypeCode result = adapter.unmarshal("AAA");

        assertNotNull(result);
        assertEquals("AAA", result.getCode());
        assertEquals("Invoice reference", result.getName());
        verify(repository).findByCodeAndActive("AAA");
    }

    @Test
    @DisplayName("Should unmarshal code case-insensitively")
    public void testUnmarshalCaseInsensitive() throws Exception {
        when(repository.findByCodeAndActive("AAA")).thenReturn(Optional.of(invoiceReference));

        UNECEReferenceTypeCode result = adapter.unmarshal("aaa");

        assertNotNull(result);
        assertEquals("AAA", result.getCode());
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCodeAndActive("ABO")).thenReturn(Optional.of(purchaseOrder));

        UNECEReferenceTypeCode result = adapter.unmarshal("  abo  ");

        assertNotNull(result);
        assertEquals("ABO", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        UNECEReferenceTypeCode result = adapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertFalse(result.getActive());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        UNECEReferenceTypeCode result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        UNECEReferenceTypeCode result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        UNECEReferenceTypeCode result = adapter.unmarshal("   ");
        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        UNECEReferenceTypeCodeAdapter nullRepoAdapter = new UNECEReferenceTypeCodeAdapter();
        nullRepoAdapter.setRepository(null);

        UNECEReferenceTypeCode result = nullRepoAdapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertFalse(result.getActive());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCodeAndActive("AAA")).thenReturn(true);

        boolean result = UNECEReferenceTypeCodeAdapter.isValid("AAA");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCodeAndActive("XXX")).thenReturn(false);

        boolean result = UNECEReferenceTypeCodeAdapter.isValid("XXX");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = UNECEReferenceTypeCodeAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new UNECEReferenceTypeCodeAdapter();
        adapter.setRepository(null);

        boolean result = UNECEReferenceTypeCodeAdapter.isValid("AAA");

        assertFalse(result);
    }

    @Test
    @DisplayName("getReferenceTypeName should return name for valid code")
    public void testGetReferenceTypeNameValidCode() {
        when(repository.findByCodeAndActive("AAA")).thenReturn(Optional.of(invoiceReference));

        String result = UNECEReferenceTypeCodeAdapter.getReferenceTypeName("AAA");

        assertEquals("Invoice reference", result);
    }

    @Test
    @DisplayName("getReferenceTypeName should return null for invalid code")
    public void testGetReferenceTypeNameInvalidCode() {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        String result = UNECEReferenceTypeCodeAdapter.getReferenceTypeName("XXX");

        assertNull(result);
    }

    @Test
    @DisplayName("getReferenceTypeName should return null for null code")
    public void testGetReferenceTypeNameNullCode() {
        String result = UNECEReferenceTypeCodeAdapter.getReferenceTypeName(null);
        assertNull(result);
    }

    @Test
    @DisplayName("isEtdaExtension should return true for ETDA extensions")
    public void testIsEtdaExtensionValid() {
        when(repository.findByCodeAndActive("T01")).thenReturn(Optional.of(etdaExtension));

        boolean result = UNECEReferenceTypeCodeAdapter.isEtdaExtension("T01");

        assertTrue(result);
    }

    @Test
    @DisplayName("isEtdaExtension should return false for non-ETDA codes")
    public void testIsEtdaExtensionNonETDA() {
        when(repository.findByCodeAndActive("AAA")).thenReturn(Optional.of(invoiceReference));

        boolean result = UNECEReferenceTypeCodeAdapter.isEtdaExtension("AAA");

        assertFalse(result);
    }

    @Test
    @DisplayName("isEtdaExtension should return false for null")
    public void testIsEtdaExtensionNull() {
        boolean result = UNECEReferenceTypeCodeAdapter.isEtdaExtension(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isEtdaExtension should return false when repository is null")
    public void testIsEtdaExtensionRepositoryNull() {
        new UNECEReferenceTypeCodeAdapter();
        adapter.setRepository(null);

        boolean result = UNECEReferenceTypeCodeAdapter.isEtdaExtension("T01");

        assertFalse(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCodeAndActive("AAA")).thenReturn(Optional.of(invoiceReference));

        String marshaled = adapter.marshal(invoiceReference);
        UNECEReferenceTypeCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(invoiceReference.getCode(), unmarshaled.getCode());
        assertEquals(invoiceReference.getName(), unmarshaled.getName());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        UNECEReferenceTypeCode result = adapter.unmarshal("XXX");

        assertEquals("XXX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        UNECEReferenceTypeCode result = adapter.unmarshal("XXX");

        assertTrue(result.getName().contains("Unknown"));
        assertTrue(result.getName().contains("XXX"));
    }

    @Test
    @DisplayName("Placeholder should have active false")
    public void testPlaceholderActive() throws Exception {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        UNECEReferenceTypeCode result = adapter.unmarshal("XXX");

        assertFalse(result.getActive());
    }

    @Test
    @DisplayName("Placeholder should have description")
    public void testPlaceholderDescription() throws Exception {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        UNECEReferenceTypeCode result = adapter.unmarshal("XXX");

        assertNotNull(result.getDescription());
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCodeAndActive(anyString())).thenReturn(Optional.empty());

        UNECEReferenceTypeCode result = adapter.unmarshal("XXX");

        assertNotNull(result);
        assertFalse(result.getActive());
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCodeAndActive("AAA")).thenReturn(Optional.of(invoiceReference));
        when(repository.findByCodeAndActive("ABO")).thenReturn(Optional.of(purchaseOrder));

        UNECEReferenceTypeCode result1 = adapter.unmarshal("AAA");
        UNECEReferenceTypeCode result2 = adapter.unmarshal("ABO");

        assertEquals("AAA", result1.getCode());
        assertEquals("ABO", result2.getCode());
        verify(repository, times(2)).findByCodeAndActive(anyString());
    }
}
