package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.AddressTypeAdapter;
import com.wpanther.etax.core.entity.AddressType;
import com.wpanther.etax.core.repository.AddressTypeRepository;
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
@DisplayName("AddressType Adapter Tests")
public class AddressTypeAdapterTest {

    @Mock
    private AddressTypeRepository repository;

    @InjectMocks
    private AddressTypeAdapter adapter;

    private AddressType postalAddress;
    private AddressType fiscalAddress;
    private AddressType physicalAddress;

    @BeforeEach
    public void setUp() {
        new AddressTypeAdapter();
        adapter.setRepository(repository);

        postalAddress = new AddressType("1", "Postal", "Postal address");
        fiscalAddress = new AddressType("2", "Fiscal", "Fiscal address");
        physicalAddress = new AddressType("3", "Physical", "Physical address");
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(postalAddress);
        assertEquals("1", result);
    }

    @Test
    @DisplayName("Should marshal null entity to null")
    public void testMarshalNull() throws Exception {
        String result = adapter.marshal(null);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        AddressType result = adapter.unmarshal("1");

        assertNotNull(result);
        assertEquals("1", result.getCode());
        verify(repository).findByCode("1");
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("9")).thenReturn(Optional.empty());

        AddressType result = adapter.unmarshal("9");

        assertNotNull(result);
        assertEquals("9", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        AddressType result = adapter.unmarshal(null);
        assertNull(result);
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("1")).thenReturn(true);

        boolean result = AddressTypeAdapter.isValid("1");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("9")).thenReturn(false);

        boolean result = AddressTypeAdapter.isValid("9");

        assertFalse(result);
    }

    @Test
    @DisplayName("getName should return name for valid code")
    public void testGetNameValidCode() {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        String result = AddressTypeAdapter.getName("1");

        assertEquals("Postal", result);
    }

    @Test
    @DisplayName("getDescription should return description for valid code")
    public void testGetDescriptionValidCode() {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        String result = AddressTypeAdapter.getDescription("1");

        assertEquals("Postal address", result);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isPostalAddress should return true for postal address")
    public void testIsPostalAddressValid() {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        boolean result = AddressTypeAdapter.isPostalAddress("1");

        assertTrue(result);
    }

    @Test
    @DisplayName("isPostalAddress should return false for non-postal")
    public void testIsPostalAddressNonPostal() {
        when(repository.findByCode("2")).thenReturn(Optional.of(fiscalAddress));

        boolean result = AddressTypeAdapter.isPostalAddress("2");

        assertFalse(result);
    }

    @Test
    @DisplayName("isFiscalAddress should return true for fiscal address")
    public void testIsFiscalAddressValid() {
        when(repository.findByCode("2")).thenReturn(Optional.of(fiscalAddress));

        boolean result = AddressTypeAdapter.isFiscalAddress("2");

        assertTrue(result);
    }

    @Test
    @DisplayName("isFiscalAddress should return false for non-fiscal")
    public void testIsFiscalAddressNonFiscal() {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        boolean result = AddressTypeAdapter.isFiscalAddress("1");

        assertFalse(result);
    }

    @Test
    @DisplayName("isPhysicalAddress should return true for physical address")
    public void testIsPhysicalAddressValid() {
        when(repository.findByCode("3")).thenReturn(Optional.of(physicalAddress));

        boolean result = AddressTypeAdapter.isPhysicalAddress("3");

        assertTrue(result);
    }

    @Test
    @DisplayName("isPhysicalAddress should return false for non-physical")
    public void testIsPhysicalAddressNonPhysical() {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        boolean result = AddressTypeAdapter.isPhysicalAddress("1");

        assertFalse(result);
    }

    @Test
    @DisplayName("isPostalAddress should return false for null")
    public void testIsPostalAddressNull() {
        boolean result = AddressTypeAdapter.isPostalAddress(null);
        assertFalse(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("1")).thenReturn(Optional.of(postalAddress));

        String marshaled = adapter.marshal(postalAddress);
        AddressType unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(postalAddress.getCode(), unmarshaled.getCode());
    }
}
