package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ThaiCategoryCodeAdapter;
import com.wpanther.etax.core.entity.ThaiCategoryCode;
import com.wpanther.etax.core.repository.ThaiCategoryCodeRepository;
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
@DisplayName("ThaiCategoryCode Adapter Tests")
public class ThaiCategoryCodeAdapterTest {

    @Mock
    private ThaiCategoryCodeRepository repository;

    @InjectMocks
    private ThaiCategoryCodeAdapter adapter;

    private ThaiCategoryCode originalReference;
    private ThaiCategoryCode advancePayment;

    @BeforeEach
    public void setUp() {
        new ThaiCategoryCodeAdapter();
        adapter.setRepository(repository);

        originalReference = new ThaiCategoryCode("01");
        originalReference.setNameTh("อ้างอิงเอกสารต้นทาง");
        originalReference.setNameEn("Original Document Reference");

        advancePayment = new ThaiCategoryCode("02");
        advancePayment.setNameTh("อ้างอิงการชำระเงินล่วงหน้า");
        advancePayment.setNameEn("Advance Payment Reference");
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(originalReference);
        assertEquals("01", result);
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
        when(repository.findByCode("01")).thenReturn(Optional.of(originalReference));

        ThaiCategoryCode result = adapter.unmarshal("01");

        assertNotNull(result);
        assertEquals("01", result.getCode());
        verify(repository).findByCode("01");
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("99")).thenReturn(Optional.empty());

        ThaiCategoryCode result = adapter.unmarshal("99");

        assertNotNull(result);
        assertEquals("99", result.getCode());
        assertTrue(result.getNameTh().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ThaiCategoryCode result = adapter.unmarshal(null);
        assertNull(result);
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("01")).thenReturn(true);

        boolean result = ThaiCategoryCodeAdapter.isValid("01");

        assertTrue(result);
    }

    @Test
    @DisplayName("getNameTh should return Thai name for valid code")
    public void testGetNameThValidCode() {
        when(repository.findByCode("01")).thenReturn(Optional.of(originalReference));

        String result = ThaiCategoryCodeAdapter.getNameTh("01");

        assertEquals("อ้างอิงเอกสารต้นทาง", result);
    }

    @Test
    @DisplayName("getNameEn should return English name for valid code")
    public void testGetNameEnValidCode() {
        when(repository.findByCode("01")).thenReturn(Optional.of(originalReference));

        String result = ThaiCategoryCodeAdapter.getNameEn("01");

        assertEquals("Original Document Reference", result);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isOriginalDocumentReference should return true for 01")
    public void testIsOriginalDocumentReferenceValid() {
        boolean result = ThaiCategoryCodeAdapter.isOriginalDocumentReference("01");
        assertTrue(result);
    }

    @Test
    @DisplayName("isOriginalDocumentReference should return false for non-01")
    public void testIsOriginalDocumentReferenceNonOriginal() {
        boolean result = ThaiCategoryCodeAdapter.isOriginalDocumentReference("02");
        assertFalse(result);
    }

    @Test
    @DisplayName("isOriginalDocumentReference should return false for null")
    public void testIsOriginalDocumentReferenceNull() {
        boolean result = ThaiCategoryCodeAdapter.isOriginalDocumentReference(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isAdvancePaymentReference should return true for 02")
    public void testIsAdvancePaymentReferenceValid() {
        boolean result = ThaiCategoryCodeAdapter.isAdvancePaymentReference("02");
        assertTrue(result);
    }

    @Test
    @DisplayName("isAdvancePaymentReference should return false for non-02")
    public void testIsAdvancePaymentReferenceNonAdvance() {
        boolean result = ThaiCategoryCodeAdapter.isAdvancePaymentReference("01");
        assertFalse(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("01")).thenReturn(Optional.of(originalReference));

        String marshaled = adapter.marshal(originalReference);
        ThaiCategoryCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(originalReference.getCode(), unmarshaled.getCode());
    }
}
