package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.TISISubdistrictAdapter;
import com.wpanther.etax.core.entity.TISISubdistrict;
import com.wpanther.etax.core.repository.TISISubdistrictRepository;
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
@DisplayName("TISISubdistrict Adapter Tests")
public class TISISubdistrictAdapterTest {

    @Mock
    private TISISubdistrictRepository repository;

    @InjectMocks
    private TISISubdistrictAdapter adapter;

    private TISISubdistrict phraBorom;
    private TISISubdistrict bangkok;

    @BeforeEach
    public void setUp() {
        new TISISubdistrictAdapter();
        adapter.setRepository(repository);

        phraBorom = new TISISubdistrict("100101");
        phraBorom.setNameTh("พระบรมมหาราชวัง");

        bangkok = new TISISubdistrict("100100");
        bangkok.setNameTh("พระราชวัง");
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(phraBorom);
        assertEquals("100101", result);
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
        TISISubdistrict entity = new TISISubdistrict();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        TISISubdistrict entity = new TISISubdistrict();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("100101")).thenReturn(Optional.of(phraBorom));

        TISISubdistrict result = adapter.unmarshal("100101");

        assertNotNull(result);
        assertEquals("100101", result.getCode());
        assertEquals("พระบรมมหาราชวัง", result.getNameTh());
        verify(repository).findByCode("100101");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("100101")).thenReturn(Optional.of(phraBorom));

        TISISubdistrict result = adapter.unmarshal("  100101  ");

        assertNotNull(result);
        assertEquals("100101", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        TISISubdistrict result = adapter.unmarshal("999999");

        assertNotNull(result);
        assertEquals("999999", result.getCode());
        assertTrue(result.getNameTh().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        TISISubdistrict result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        TISISubdistrict result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        TISISubdistrict result = adapter.unmarshal("   ");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        TISISubdistrictAdapter nullRepoAdapter = new TISISubdistrictAdapter();
        nullRepoAdapter.setRepository(null);

        TISISubdistrict result = nullRepoAdapter.unmarshal("999999");

        assertNotNull(result);
        assertEquals("999999", result.getCode());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("100101")).thenReturn(true);

        boolean result = TISISubdistrictAdapter.isValid("100101");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("999999")).thenReturn(false);

        boolean result = TISISubdistrictAdapter.isValid("999999");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = TISISubdistrictAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for empty code")
    public void testIsValidEmptyCode() {
        boolean result = TISISubdistrictAdapter.isValid("");
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new TISISubdistrictAdapter();
        adapter.setRepository(null);

        boolean result = TISISubdistrictAdapter.isValid("100101");

        assertFalse(result);
    }

    @Test
    @DisplayName("getSubdistrictName should return Thai name for valid code")
    public void testGetSubdistrictNameValidCode() {
        when(repository.findByCode("100101")).thenReturn(Optional.of(phraBorom));

        String result = TISISubdistrictAdapter.getSubdistrictName("100101");

        assertEquals("พระบรมมหาราชวัง", result);
    }

    @Test
    @DisplayName("getSubdistrictName should return null for invalid code")
    public void testGetSubdistrictNameInvalidCode() {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        String result = TISISubdistrictAdapter.getSubdistrictName("999999");

        assertNull(result);
    }

    @Test
    @DisplayName("getSubdistrictName should return null for null code")
    public void testGetSubdistrictNameNullCode() {
        String result = TISISubdistrictAdapter.getSubdistrictName(null);
        assertNull(result);
    }

    @Test
    @DisplayName("getSubdistrictName should return null for empty code")
    public void testGetSubdistrictNameEmptyCode() {
        String result = TISISubdistrictAdapter.getSubdistrictName("");
        assertNull(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("100101")).thenReturn(Optional.of(phraBorom));

        String marshaled = adapter.marshal(phraBorom);
        TISISubdistrict unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(phraBorom.getCode(), unmarshaled.getCode());
        assertEquals(phraBorom.getNameTh(), unmarshaled.getNameTh());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        TISISubdistrict result = adapter.unmarshal("999999");

        assertEquals("999999", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic Thai name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        TISISubdistrict result = adapter.unmarshal("999999");

        assertTrue(result.getNameTh().contains("Unknown"));
        assertTrue(result.getNameTh().contains("999999"));
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCode(anyString())).thenReturn(Optional.empty());

        TISISubdistrict result = adapter.unmarshal("999999");

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCode("100101")).thenReturn(Optional.of(phraBorom));
        when(repository.findByCode("100100")).thenReturn(Optional.of(bangkok));

        TISISubdistrict result1 = adapter.unmarshal("100101");
        TISISubdistrict result2 = adapter.unmarshal("100100");

        assertEquals("100101", result1.getCode());
        assertEquals("100100", result2.getCode());
        verify(repository, times(2)).findByCode(anyString());
    }
}
