package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ThaiMessageFunctionCodeAdapter;
import com.wpanther.etax.core.entity.ThaiMessageFunctionCode;
import com.wpanther.etax.core.repository.ThaiMessageFunctionCodeRepository;
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
@DisplayName("ThaiMessageFunctionCode Adapter Tests")
public class ThaiMessageFunctionCodeAdapterTest {

    @Mock
    private ThaiMessageFunctionCodeRepository repository;

    @InjectMocks
    private ThaiMessageFunctionCodeAdapter adapter;

    private ThaiMessageFunctionCode originalTaxInvoice;
    private ThaiMessageFunctionCode originalReceipt;
    private ThaiMessageFunctionCode originalDebitNoteGoods;
    private ThaiMessageFunctionCode originalCreditNoteService;

    @BeforeEach
    public void setUp() {
        new ThaiMessageFunctionCodeAdapter();
        adapter.setRepository(repository);

        originalTaxInvoice = new ThaiMessageFunctionCode();
        originalTaxInvoice.setCode("TIVC01");
        originalTaxInvoice.setDescriptionEn("Original Tax Invoice");
        originalTaxInvoice.setDescriptionTh("ใบกำกับภาษีฉบับแรก");
        originalTaxInvoice.setDocumentType("TaxInvoice");
        originalTaxInvoice.setCategory("Invoice");
        originalTaxInvoice.setActive(true);

        originalReceipt = new ThaiMessageFunctionCode();
        originalReceipt.setCode("RCTC01");
        originalReceipt.setDescriptionEn("Original Receipt");
        originalReceipt.setDescriptionTh("ใบเสร็จรับเงินฉบับแรก");
        originalReceipt.setDocumentType("Receipt");
        originalReceipt.setCategory("Receipt");
        originalReceipt.setActive(true);

        originalDebitNoteGoods = new ThaiMessageFunctionCode();
        originalDebitNoteGoods.setCode("DBNG01");
        originalDebitNoteGoods.setDescriptionEn("Original Debit Note Goods");
        originalDebitNoteGoods.setDescriptionTh("ใบเพิ่มหนี้สินค้าฉบับแรก");
        originalDebitNoteGoods.setDocumentType("DebitNote");
        originalDebitNoteGoods.setCategory("Goods");
        originalDebitNoteGoods.setActive(true);

        originalCreditNoteService = new ThaiMessageFunctionCode();
        originalCreditNoteService.setCode("CDNS01");
        originalCreditNoteService.setDescriptionEn("Original Credit Note Service");
        originalCreditNoteService.setDescriptionTh("ใบลดหนี้บริการฉบับแรก");
        originalCreditNoteService.setDocumentType("CreditNote");
        originalCreditNoteService.setCategory("Service");
        originalCreditNoteService.setActive(true);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(originalTaxInvoice);
        assertEquals("TIVC01", result);
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
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        ThaiMessageFunctionCode result = adapter.unmarshal("TIVC01");

        assertNotNull(result);
        assertEquals("TIVC01", result.getCode());
        assertEquals("Original Tax Invoice", result.getDescriptionEn());
        verify(repository).findByCode("TIVC01");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        ThaiMessageFunctionCode result = adapter.unmarshal("  TIVC01  ");

        assertNotNull(result);
        assertEquals("TIVC01", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        ThaiMessageFunctionCode result = adapter.unmarshal("XXXXX");

        assertNotNull(result);
        assertEquals("XXXXX", result.getCode());
        assertFalse(result.isActive());
        assertTrue(result.getDescriptionEn().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ThaiMessageFunctionCode result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        ThaiMessageFunctionCode result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        ThaiMessageFunctionCodeAdapter nullRepoAdapter = new ThaiMessageFunctionCodeAdapter();
        nullRepoAdapter.setRepository(null);

        ThaiMessageFunctionCode result = nullRepoAdapter.unmarshal("XXXXX");

        assertNotNull(result);
        assertEquals("XXXXX", result.getCode());
        assertFalse(result.isActive());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("toEntity should return entity for valid code")
    public void testToEntityValidCode() {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        ThaiMessageFunctionCode result = ThaiMessageFunctionCodeAdapter.toEntity("TIVC01");

        assertNotNull(result);
        assertEquals("TIVC01", result.getCode());
    }

    @Test
    @DisplayName("toEntity should return null for null code")
    public void testToEntityNullCode() {
        ThaiMessageFunctionCode result = ThaiMessageFunctionCodeAdapter.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("toEntity should return placeholder for invalid code")
    public void testToEntityInvalidCode() {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        ThaiMessageFunctionCode result = ThaiMessageFunctionCodeAdapter.toEntity("XXXXX");

        assertNotNull(result);
        assertEquals("XXXXX", result.getCode());
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("toCode should return code for valid entity")
    public void testToCodeValidEntity() {
        String result = ThaiMessageFunctionCodeAdapter.toCode(originalTaxInvoice);
        assertEquals("TIVC01", result);
    }

    @Test
    @DisplayName("toCode should return null for null entity")
    public void testToCodeNullEntity() {
        String result = ThaiMessageFunctionCodeAdapter.toCode(null);
        assertNull(result);
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        boolean result = ThaiMessageFunctionCodeAdapter.isValid("TIVC01");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        boolean result = ThaiMessageFunctionCodeAdapter.isValid("XXXXX");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = ThaiMessageFunctionCodeAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("getEnglishDescription should return English description for valid code")
    public void testGetEnglishDescriptionValidCode() {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        String result = ThaiMessageFunctionCodeAdapter.getEnglishDescription("TIVC01");

        assertEquals("Original Tax Invoice", result);
    }

    @Test
    @DisplayName("getEnglishDescription should return null for invalid code")
    public void testGetEnglishDescriptionInvalidCode() {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        String result = ThaiMessageFunctionCodeAdapter.getEnglishDescription("XXXXX");

        assertNull(result);
    }

    @Test
    @DisplayName("getThaiDescription should return Thai description for valid code")
    public void testGetThaiDescriptionValidCode() {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        String result = ThaiMessageFunctionCodeAdapter.getThaiDescription("TIVC01");

        assertEquals("ใบกำกับภาษีฉบับแรก", result);
    }

    @Test
    @DisplayName("getThaiDescription should return null for invalid code")
    public void testGetThaiDescriptionInvalidCode() {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        String result = ThaiMessageFunctionCodeAdapter.getThaiDescription("XXXXX");

        assertNull(result);
    }

    // Document Type Tests

    @Test
    @DisplayName("isDebitNote should return true for debit note codes")
    public void testIsDebitNoteValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isDebitNote("DBNG01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isDebitNote("DBNS01"));
    }

    @Test
    @DisplayName("isDebitNote should return false for non-debit note codes")
    public void testIsDebitNoteNonDebitNote() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isDebitNote("TIVC01"));
    }

    @Test
    @DisplayName("isDebitNote should return false for null")
    public void testIsDebitNoteNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isDebitNote(null));
    }

    @Test
    @DisplayName("isCreditNote should return true for credit note codes")
    public void testIsCreditNoteValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isCreditNote("CDNG01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isCreditNote("CDNS01"));
    }

    @Test
    @DisplayName("isCreditNote should return false for non-credit note codes")
    public void testIsCreditNoteNonCreditNote() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isCreditNote("TIVC01"));
    }

    @Test
    @DisplayName("isCreditNote should return false for null")
    public void testIsCreditNoteNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isCreditNote(null));
    }

    @Test
    @DisplayName("isTaxInvoice should return true for tax invoice codes")
    public void testIsTaxInvoiceValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isTaxInvoice("TIVC01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isTaxInvoice("TIVC02"));
    }

    @Test
    @DisplayName("isTaxInvoice should return false for non-tax invoice codes")
    public void testIsTaxInvoiceNonTaxInvoice() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isTaxInvoice("RCTC01"));
    }

    @Test
    @DisplayName("isTaxInvoice should return false for null")
    public void testIsTaxInvoiceNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isTaxInvoice(null));
    }

    @Test
    @DisplayName("isReceipt should return true for receipt codes")
    public void testIsReceiptValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isReceipt("RCTC01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isReceipt("RCTC02"));
    }

    @Test
    @DisplayName("isReceipt should return false for non-receipt codes")
    public void testIsReceiptNonReceipt() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isReceipt("TIVC01"));
    }

    @Test
    @DisplayName("isReceipt should return false for null")
    public void testIsReceiptNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isReceipt(null));
    }

    @Test
    @DisplayName("isGoods should return true for goods codes")
    public void testIsGoodsValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isGoods("DBNG01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isGoods("CDNG01"));
    }

    @Test
    @DisplayName("isGoods should return false for non-goods codes")
    public void testIsGoodsNonGoods() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isGoods("DBNS01"));
    }

    @Test
    @DisplayName("isGoods should return false for null")
    public void testIsGoodsNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isGoods(null));
    }

    @Test
    @DisplayName("isServices should return true for service codes")
    public void testIsServicesValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isServices("DBNS01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isServices("CDNS01"));
    }

    @Test
    @DisplayName("isServices should return false for non-service codes")
    public void testIsServicesNonServices() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isServices("DBNG01"));
    }

    @Test
    @DisplayName("isServices should return false for null")
    public void testIsServicesNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isServices(null));
    }

    // Function Suffix Tests

    @Test
    @DisplayName("isOriginal should return true for codes ending with 01")
    public void testIsOriginalValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isOriginal("TIVC01"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isOriginal("RCTC01"));
    }

    @Test
    @DisplayName("isOriginal should return false for non-original codes")
    public void testIsOriginalNonOriginal() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isOriginal("TIVC02"));
    }

    @Test
    @DisplayName("isOriginal should return false for null")
    public void testIsOriginalNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isOriginal(null));
    }

    @Test
    @DisplayName("isReplacement should return true for codes ending with 02")
    public void testIsReplacementValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isReplacement("TIVC02"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isReplacement("RCTC02"));
    }

    @Test
    @DisplayName("isReplacement should return false for non-replacement codes")
    public void testIsReplacementNonReplacement() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isReplacement("TIVC01"));
    }

    @Test
    @DisplayName("isReplacement should return false for null")
    public void testIsReplacementNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isReplacement(null));
    }

    @Test
    @DisplayName("isCancellation should return true for codes ending with 03")
    public void testIsCancellationValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isCancellation("TIVC03"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isCancellation("RCTC03"));
    }

    @Test
    @DisplayName("isCancellation should return false for non-cancellation codes")
    public void testIsCancellationNonCancellation() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isCancellation("TIVC01"));
    }

    @Test
    @DisplayName("isCancellation should return false for null")
    public void testIsCancellationNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isCancellation(null));
    }

    @Test
    @DisplayName("isCopy should return true for codes ending with 04")
    public void testIsCopyValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isCopy("TIVC04"));
        assertTrue(ThaiMessageFunctionCodeAdapter.isCopy("RCTC04"));
    }

    @Test
    @DisplayName("isCopy should return false for non-copy codes")
    public void testIsCopyNonCopy() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isCopy("TIVC01"));
    }

    @Test
    @DisplayName("isCopy should return false for null")
    public void testIsCopyNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isCopy(null));
    }

    @Test
    @DisplayName("isAddition should return true for codes ending with 05")
    public void testIsAdditionValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isAddition("TIVC05"));
    }

    @Test
    @DisplayName("isAddition should return false for non-addition codes")
    public void testIsAdditionNonAddition() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isAddition("TIVC01"));
    }

    @Test
    @DisplayName("isAddition should return false for null")
    public void testIsAdditionNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isAddition(null));
    }

    @Test
    @DisplayName("isOther should return true for codes ending with 99")
    public void testIsOtherValid() {
        assertTrue(ThaiMessageFunctionCodeAdapter.isOther("TIVC99"));
    }

    @Test
    @DisplayName("isOther should return false for non-other codes")
    public void testIsOtherNonOther() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isOther("TIVC01"));
    }

    @Test
    @DisplayName("isOther should return false for null")
    public void testIsOtherNull() {
        assertFalse(ThaiMessageFunctionCodeAdapter.isOther(null));
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("TIVC01")).thenReturn(Optional.of(originalTaxInvoice));

        String marshaled = adapter.marshal(originalTaxInvoice);
        ThaiMessageFunctionCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(originalTaxInvoice.getCode(), unmarshaled.getCode());
        assertEquals(originalTaxInvoice.getDescriptionEn(), unmarshaled.getDescriptionEn());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        ThaiMessageFunctionCode result = adapter.unmarshal("XXXXX");

        assertEquals("XXXXX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic English description")
    public void testPlaceholderEnglishDescription() throws Exception {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        ThaiMessageFunctionCode result = adapter.unmarshal("XXXXX");

        assertTrue(result.getDescriptionEn().contains("Unknown"));
        assertTrue(result.getDescriptionEn().contains("XXXXX"));
    }

    @Test
    @DisplayName("Placeholder should have generic Thai description")
    public void testPlaceholderThaiDescription() throws Exception {
        when(repository.findByCode("XXXXX")).thenReturn(Optional.empty());

        ThaiMessageFunctionCode result = adapter.unmarshal("XXXXX");

        assertTrue(result.getDescriptionTh().contains("รหัสที่ไม่รู้จัก"));
    }
}
