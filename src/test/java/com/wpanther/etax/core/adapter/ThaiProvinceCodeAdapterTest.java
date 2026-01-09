package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ThaiProvinceCodeAdapter;
import com.wpanther.etax.core.entity.ThaiProvinceCode;
import com.wpanther.etax.core.repository.ThaiProvinceCodeRepository;
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
@DisplayName("ThaiProvinceCode Adapter Tests")
public class ThaiProvinceCodeAdapterTest {

    @Mock
    private ThaiProvinceCodeRepository repository;

    @InjectMocks
    private ThaiProvinceCodeAdapter adapter;

    private ThaiProvinceCode bangkok;
    private ThaiProvinceCode chonburi;

    @BeforeEach
    public void setUp() {
        // Initialize static repository for adapter
        new ThaiProvinceCodeAdapter();
        adapter.setRepository(repository);

        // Create test entities
        bangkok = new ThaiProvinceCode("10", "กรุงเทพมหานคร", "Bangkok");
        bangkok.setRegion("Central");
        bangkok.setActive(true);

        chonburi = new ThaiProvinceCode("20", "ชลบุรี", "Chonburi");
        chonburi.setRegion("East");
        chonburi.setActive(true);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(bangkok);

        assertEquals("10", result);
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
        when(repository.findByCodeAndActive("10")).thenReturn(Optional.of(bangkok));

        ThaiProvinceCode result = adapter.unmarshal("10");

        assertNotNull(result);
        assertEquals("10", result.getCode());
        assertEquals("กรุงเทพมหานคร", result.getNameTh());
        assertEquals("Bangkok", result.getNameEn());
        verify(repository).findByCodeAndActive("10");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCodeAndActive("10")).thenReturn(Optional.of(bangkok));

        ThaiProvinceCode result = adapter.unmarshal("  10  ");

        assertNotNull(result);
        assertEquals("10", result.getCode());
        verify(repository).findByCodeAndActive("10");
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCodeAndActive("99")).thenReturn(Optional.empty());

        ThaiProvinceCode result = adapter.unmarshal("99");

        assertNotNull(result);
        assertEquals("99", result.getCode());
        assertFalse(result.getActive());
        assertTrue(result.getNameTh().contains("Unknown"));
        verify(repository).findByCodeAndActive("99");
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ThaiProvinceCode result = adapter.unmarshal(null);

        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        ThaiProvinceCode result = adapter.unmarshal("");

        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        ThaiProvinceCode result = adapter.unmarshal("   ");

        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        ThaiProvinceCodeAdapter nullRepoAdapter = new ThaiProvinceCodeAdapter();
        nullRepoAdapter.setRepository(null);

        ThaiProvinceCode result = nullRepoAdapter.unmarshal("99");

        assertNotNull(result);
        assertEquals("99", result.getCode());
        assertFalse(result.getActive());
        assertTrue(result.getNameTh().contains("Unknown"));
        assertTrue(result.getNameEn().contains("Unknown"));
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCodeAndActive("10")).thenReturn(true);

        boolean result = ThaiProvinceCodeAdapter.isValid("10");

        assertTrue(result);
        verify(repository).existsByCodeAndActive("10");
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCodeAndActive("99")).thenReturn(false);

        boolean result = ThaiProvinceCodeAdapter.isValid("99");

        assertFalse(result);
        verify(repository).existsByCodeAndActive("99");
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = ThaiProvinceCodeAdapter.isValid(null);

        assertFalse(result);
        verify(repository, never()).existsByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("isValid should return false for empty code")
    public void testIsValidEmptyCode() {
        boolean result = ThaiProvinceCodeAdapter.isValid("");

        assertFalse(result);
        verify(repository, never()).existsByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new ThaiProvinceCodeAdapter();
        adapter.setRepository(null);

        boolean result = ThaiProvinceCodeAdapter.isValid("10");

        assertFalse(result);
    }

    @Test
    @DisplayName("getProvinceName should return Thai name for valid code")
    public void testGetProvinceNameValidCode() {
        when(repository.findByCodeAndActive("10")).thenReturn(Optional.of(bangkok));

        String result = ThaiProvinceCodeAdapter.getProvinceName("10");

        assertEquals("กรุงเทพมหานคร", result);
        verify(repository).findByCodeAndActive("10");
    }

    @Test
    @DisplayName("getProvinceName should return null for invalid code")
    public void testGetProvinceNameInvalidCode() {
        when(repository.findByCodeAndActive("99")).thenReturn(Optional.empty());

        String result = ThaiProvinceCodeAdapter.getProvinceName("99");

        assertNull(result);
        verify(repository).findByCodeAndActive("99");
    }

    @Test
    @DisplayName("getProvinceName should return null for null code")
    public void testGetProvinceNameNullCode() {
        String result = ThaiProvinceCodeAdapter.getProvinceName(null);

        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("getProvinceName should return null for empty code")
    public void testGetProvinceNameEmptyCode() {
        String result = ThaiProvinceCodeAdapter.getProvinceName("");

        assertNull(result);
        verify(repository, never()).findByCodeAndActive(anyString());
    }

    @Test
    @DisplayName("getProvinceName should return null when repository is null")
    public void testGetProvinceNameRepositoryNull() {
        new ThaiProvinceCodeAdapter();
        adapter.setRepository(null);

        String result = ThaiProvinceCodeAdapter.getProvinceName("10");

        assertNull(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCodeAndActive("10")).thenReturn(Optional.of(bangkok));

        String marshaled = adapter.marshal(bangkok);
        ThaiProvinceCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(bangkok.getCode(), unmarshaled.getCode());
        assertEquals(bangkok.getNameTh(), unmarshaled.getNameTh());
        assertEquals(bangkok.getNameEn(), unmarshaled.getNameEn());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCodeAndActive("99")).thenReturn(Optional.empty());

        ThaiProvinceCode result = adapter.unmarshal("99");

        assertEquals("99", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic Thai name")
    public void testPlaceholderNameTh() throws Exception {
        when(repository.findByCodeAndActive("99")).thenReturn(Optional.empty());

        ThaiProvinceCode result = adapter.unmarshal("99");

        assertTrue(result.getNameTh().contains("Unknown"));
        assertTrue(result.getNameTh().contains("99"));
    }

    @Test
    @DisplayName("Placeholder should have generic English name")
    public void testPlaceholderNameEn() throws Exception {
        when(repository.findByCodeAndActive("99")).thenReturn(Optional.empty());

        ThaiProvinceCode result = adapter.unmarshal("99");

        assertTrue(result.getNameEn().contains("Unknown"));
        assertTrue(result.getNameEn().contains("99"));
    }

    @Test
    @DisplayName("Placeholder should have active false")
    public void testPlaceholderActive() throws Exception {
        when(repository.findByCodeAndActive("99")).thenReturn(Optional.empty());

        ThaiProvinceCode result = adapter.unmarshal("99");

        assertFalse(result.getActive());
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCodeAndActive(anyString())).thenReturn(Optional.empty());

        ThaiProvinceCode result = adapter.unmarshal("99");

        assertNotNull(result);
        assertFalse(result.getActive());
        assertTrue(result.getNameTh().contains("Unknown"));
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCodeAndActive("10")).thenReturn(Optional.of(bangkok));
        when(repository.findByCodeAndActive("20")).thenReturn(Optional.of(chonburi));

        ThaiProvinceCode result1 = adapter.unmarshal("10");
        ThaiProvinceCode result2 = adapter.unmarshal("20");

        assertEquals("10", result1.getCode());
        assertEquals("20", result2.getCode());
        verify(repository, times(2)).findByCodeAndActive(anyString());
    }
}
