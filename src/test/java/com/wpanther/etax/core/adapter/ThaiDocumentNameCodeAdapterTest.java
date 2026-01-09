package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ThaiDocumentNameCodeAdapter;
import com.wpanther.etax.core.entity.ThaiDocumentNameCode;
import com.wpanther.etax.core.repository.ThaiDocumentNameCodeRepository;
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
@DisplayName("ThaiDocumentNameCode Adapter Tests")
public class ThaiDocumentNameCodeAdapterTest {

    @Mock
    private ThaiDocumentNameCodeRepository repository;

    @InjectMocks
    private ThaiDocumentNameCodeAdapter adapter;

    private ThaiDocumentNameCode taxInvoice;
    private ThaiDocumentNameCode receipt;
    private ThaiDocumentNameCode creditNote;
    private ThaiDocumentNameCode thaiExtension;

    @BeforeEach
    public void setUp() {
        new ThaiDocumentNameCodeAdapter();
        adapter.setRepository(repository);

        taxInvoice = new ThaiDocumentNameCode("388");
        taxInvoice.setNameEn("Tax Invoice");
        taxInvoice.setNameTh("ใบกำกับภาษี");
        taxInvoice.setStandardCode(true);
        taxInvoice.setThaiExtension(false);

        receipt = new ThaiDocumentNameCode("80");
        receipt.setNameEn("Receipt");
        receipt.setNameTh("ใบเสร็จรับเงิน");
        receipt.setStandardCode(true);
        receipt.setThaiExtension(false);

        creditNote = new ThaiDocumentNameCode("381");
        creditNote.setNameEn("Credit Note");
        creditNote.setNameTh("ใบลดหนี้");
        creditNote.setStandardCode(true);
        creditNote.setThaiExtension(false);

        thaiExtension = new ThaiDocumentNameCode("T01");
        thaiExtension.setNameEn("Thai Extension 1");
        thaiExtension.setNameTh("ส่วนขยายไทย 1");
        thaiExtension.setStandardCode(false);
        thaiExtension.setThaiExtension(true);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(taxInvoice);
        assertEquals("388", result);
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
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        ThaiDocumentNameCode result = adapter.unmarshal("388");

        assertNotNull(result);
        assertEquals("388", result.getCode());
        assertEquals("Tax Invoice", result.getNameEn());
        assertEquals("ใบกำกับภาษี", result.getNameTh());
        verify(repository).findByCode("388");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        ThaiDocumentNameCode result = adapter.unmarshal("  388  ");

        assertNotNull(result);
        assertEquals("388", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ThaiDocumentNameCode result = adapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertTrue(result.getNameEn().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ThaiDocumentNameCode result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        ThaiDocumentNameCode result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        ThaiDocumentNameCodeAdapter nullRepoAdapter = new ThaiDocumentNameCodeAdapter();
        nullRepoAdapter.setRepository(null);

        ThaiDocumentNameCode result = nullRepoAdapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertFalse(result.getStandardCode());
        assertFalse(result.getThaiExtension());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("388")).thenReturn(true);

        boolean result = ThaiDocumentNameCodeAdapter.isValid("388");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("XXX")).thenReturn(false);

        boolean result = ThaiDocumentNameCodeAdapter.isValid("XXX");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = ThaiDocumentNameCodeAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("getEnglishName should return English name for valid code")
    public void testGetEnglishNameValidCode() {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        String result = ThaiDocumentNameCodeAdapter.getEnglishName("388");

        assertEquals("Tax Invoice", result);
    }

    @Test
    @DisplayName("getEnglishName should return null for invalid code")
    public void testGetEnglishNameInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        String result = ThaiDocumentNameCodeAdapter.getEnglishName("XXX");

        assertNull(result);
    }

    @Test
    @DisplayName("getEnglishName should return null for null code")
    public void testGetEnglishNameNullCode() {
        String result = ThaiDocumentNameCodeAdapter.getEnglishName(null);
        assertNull(result);
    }

    @Test
    @DisplayName("getThaiName should return Thai name for valid code")
    public void testGetThaiNameValidCode() {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        String result = ThaiDocumentNameCodeAdapter.getThaiName("388");

        assertEquals("ใบกำกับภาษี", result);
    }

    @Test
    @DisplayName("getThaiName should return null for invalid code")
    public void testGetThaiNameInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        String result = ThaiDocumentNameCodeAdapter.getThaiName("XXX");

        assertNull(result);
    }

    @Test
    @DisplayName("getThaiName should return null for null code")
    public void testGetThaiNameNullCode() {
        String result = ThaiDocumentNameCodeAdapter.getThaiName(null);
        assertNull(result);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isStandardCode should return true for standard codes")
    public void testIsStandardCodeValid() {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));
        when(repository.findByCode("80")).thenReturn(Optional.of(receipt));

        assertTrue(ThaiDocumentNameCodeAdapter.isStandardCode("388"));
        assertTrue(ThaiDocumentNameCodeAdapter.isStandardCode("80"));
    }

    @Test
    @DisplayName("isStandardCode should return false for non-standard codes")
    public void testIsStandardCodeNonStandard() {
        when(repository.findByCode("T01")).thenReturn(Optional.of(thaiExtension));

        boolean result = ThaiDocumentNameCodeAdapter.isStandardCode("T01");

        assertFalse(result);
    }

    @Test
    @DisplayName("isStandardCode should return false for null")
    public void testIsStandardCodeNull() {
        boolean result = ThaiDocumentNameCodeAdapter.isStandardCode(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isThaiExtension should return true for Thai extensions")
    public void testIsThaiExtensionValid() {
        when(repository.findByCode("T01")).thenReturn(Optional.of(thaiExtension));

        boolean result = ThaiDocumentNameCodeAdapter.isThaiExtension("T01");

        assertTrue(result);
    }

    @Test
    @DisplayName("isThaiExtension should return false for standard codes")
    public void testIsThaiExtensionNonExtension() {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        boolean result = ThaiDocumentNameCodeAdapter.isThaiExtension("388");

        assertFalse(result);
    }

    @Test
    @DisplayName("isThaiExtension should return false for null")
    public void testIsThaiExtensionNull() {
        boolean result = ThaiDocumentNameCodeAdapter.isThaiExtension(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isTaxInvoice should return true for tax invoice codes")
    public void testIsTaxInvoiceValid() {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        boolean result = ThaiDocumentNameCodeAdapter.isTaxInvoice("388");

        assertTrue(result);
    }

    @Test
    @DisplayName("isTaxInvoice should return false for non-tax invoice codes")
    public void testIsTaxInvoiceNonTaxInvoice() {
        when(repository.findByCode("80")).thenReturn(Optional.of(receipt));

        boolean result = ThaiDocumentNameCodeAdapter.isTaxInvoice("80");

        assertFalse(result);
    }

    @Test
    @DisplayName("isTaxInvoice should return false for null")
    public void testIsTaxInvoiceNull() {
        boolean result = ThaiDocumentNameCodeAdapter.isTaxInvoice(null);
        assertFalse(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("388")).thenReturn(Optional.of(taxInvoice));

        String marshaled = adapter.marshal(taxInvoice);
        ThaiDocumentNameCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(taxInvoice.getCode(), unmarshaled.getCode());
        assertEquals(taxInvoice.getNameEn(), unmarshaled.getNameEn());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ThaiDocumentNameCode result = adapter.unmarshal("XXX");

        assertEquals("XXX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic English name")
    public void testPlaceholderEnglishName() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ThaiDocumentNameCode result = adapter.unmarshal("XXX");

        assertTrue(result.getNameEn().contains("Unknown"));
        assertTrue(result.getNameEn().contains("XXX"));
    }

    @Test
    @DisplayName("Placeholder should have generic Thai name")
    public void testPlaceholderThaiName() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ThaiDocumentNameCode result = adapter.unmarshal("XXX");

        assertTrue(result.getNameTh().contains("ประเภทเอกสารไม่ทราบ"));
    }

    @Test
    @DisplayName("Placeholder should have description")
    public void testPlaceholderDescription() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ThaiDocumentNameCode result = adapter.unmarshal("XXX");

        assertNotNull(result.getDescription());
    }
}
