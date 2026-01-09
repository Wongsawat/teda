package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.TISICityNameAdapter;
import com.wpanther.etax.core.entity.TISICityName;
import com.wpanther.etax.core.repository.TISICityNameRepository;
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
@DisplayName("TISICityName Adapter Tests")
public class TISICityNameAdapterTest {

    @Mock
    private TISICityNameRepository repository;

    @InjectMocks
    private TISICityNameAdapter adapter;

    private TISICityName phraNakhon;
    private TISICityName bangkok;

    @BeforeEach
    public void setUp() {
        new TISICityNameAdapter();
        adapter.setRepository(repository);

        phraNakhon = new TISICityName("1001");
        phraNakhon.setNameTh("พระนคร");

        bangkok = new TISICityName("1001");
        bangkok.setNameTh("กรุงเทพมหานคร");
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(phraNakhon);
        assertEquals("1001", result);
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
        TISICityName entity = new TISICityName();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        TISICityName entity = new TISICityName();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("1001")).thenReturn(Optional.of(phraNakhon));

        TISICityName result = adapter.unmarshal("1001");

        assertNotNull(result);
        assertEquals("1001", result.getCode());
        assertEquals("พระนคร", result.getNameTh());
        verify(repository).findByCode("1001");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("1001")).thenReturn(Optional.of(phraNakhon));

        TISICityName result = adapter.unmarshal("  1001  ");

        assertNotNull(result);
        assertEquals("1001", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("9999")).thenReturn(Optional.empty());

        TISICityName result = adapter.unmarshal("9999");

        assertNotNull(result);
        assertEquals("9999", result.getCode());
        assertTrue(result.getNameTh().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        TISICityName result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        TISICityName result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        TISICityName result = adapter.unmarshal("   ");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        TISICityNameAdapter nullRepoAdapter = new TISICityNameAdapter();
        nullRepoAdapter.setRepository(null);

        TISICityName result = nullRepoAdapter.unmarshal("9999");

        assertNotNull(result);
        assertEquals("9999", result.getCode());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("1001")).thenReturn(true);

        boolean result = TISICityNameAdapter.isValid("1001");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("9999")).thenReturn(false);

        boolean result = TISICityNameAdapter.isValid("9999");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = TISICityNameAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for empty code")
    public void testIsValidEmptyCode() {
        boolean result = TISICityNameAdapter.isValid("");
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new TISICityNameAdapter();
        adapter.setRepository(null);

        boolean result = TISICityNameAdapter.isValid("1001");

        assertFalse(result);
    }

    @Test
    @DisplayName("getCityName should return Thai name for valid code")
    public void testGetCityNameValidCode() {
        when(repository.findByCode("1001")).thenReturn(Optional.of(phraNakhon));

        String result = TISICityNameAdapter.getCityName("1001");

        assertEquals("พระนคร", result);
    }

    @Test
    @DisplayName("getCityName should return null for invalid code")
    public void testGetCityNameInvalidCode() {
        when(repository.findByCode("9999")).thenReturn(Optional.empty());

        String result = TISICityNameAdapter.getCityName("9999");

        assertNull(result);
    }

    @Test
    @DisplayName("getCityName should return null for null code")
    public void testGetCityNameNullCode() {
        String result = TISICityNameAdapter.getCityName(null);
        assertNull(result);
    }

    @Test
    @DisplayName("getCityName should return null for empty code")
    public void testGetCityNameEmptyCode() {
        String result = TISICityNameAdapter.getCityName("");
        assertNull(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("1001")).thenReturn(Optional.of(phraNakhon));

        String marshaled = adapter.marshal(phraNakhon);
        TISICityName unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(phraNakhon.getCode(), unmarshaled.getCode());
        assertEquals(phraNakhon.getNameTh(), unmarshaled.getNameTh());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("9999")).thenReturn(Optional.empty());

        TISICityName result = adapter.unmarshal("9999");

        assertEquals("9999", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic Thai name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("9999")).thenReturn(Optional.empty());

        TISICityName result = adapter.unmarshal("9999");

        assertTrue(result.getNameTh().contains("Unknown"));
        assertTrue(result.getNameTh().contains("9999"));
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCode(anyString())).thenReturn(Optional.empty());

        TISICityName result = adapter.unmarshal("9999");

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCode("1001")).thenReturn(Optional.of(phraNakhon));

        TISICityName result1 = adapter.unmarshal("1001");
        TISICityName result2 = adapter.unmarshal("1001");

        assertEquals("1001", result1.getCode());
        assertEquals("1001", result2.getCode());
        verify(repository, times(2)).findByCode(anyString());
    }
}
