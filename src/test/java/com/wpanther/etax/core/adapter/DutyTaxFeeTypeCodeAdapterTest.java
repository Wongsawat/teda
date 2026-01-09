package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.DutyTaxFeeTypeCodeAdapter;
import com.wpanther.etax.core.entity.DutyTaxFeeTypeCode;
import com.wpanther.etax.core.repository.DutyTaxFeeTypeCodeRepository;
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
@DisplayName("DutyTaxFeeTypeCode Adapter Tests")
public class DutyTaxFeeTypeCodeAdapterTest {

    @Mock
    private DutyTaxFeeTypeCodeRepository repository;

    @InjectMocks
    private DutyTaxFeeTypeCodeAdapter adapter;

    private DutyTaxFeeTypeCode vat;
    private DutyTaxFeeTypeCode gst;
    private DutyTaxFeeTypeCode exempt;
    private DutyTaxFeeTypeCode customs;

    @BeforeEach
    public void setUp() {
        new DutyTaxFeeTypeCodeAdapter();
        adapter.setRepository(repository);

        vat = new DutyTaxFeeTypeCode("VAT", "Value Added Tax", null);
        vat.setCategory("Tax");
        vat.setVat(true);
        vat.setActive(true);

        gst = new DutyTaxFeeTypeCode("GST", "Goods and Services Tax", null);
        gst.setCategory("GST");
        gst.setActive(true);

        exempt = new DutyTaxFeeTypeCode("EXE", "Exempt", null);
        exempt.setCategory("Exempt");
        exempt.setExempt(true);
        exempt.setActive(true);

        customs = new DutyTaxFeeTypeCode("CST", "Customs Duty", null);
        customs.setCategory("Customs");
        customs.setActive(true);
    }

    @Test
    @DisplayName("Should marshal entity to code")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(vat);
        assertEquals("VAT", result);
    }

    @Test
    @DisplayName("Should unmarshal valid code")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));

        DutyTaxFeeTypeCode result = adapter.unmarshal("VAT");

        assertNotNull(result);
        assertEquals("VAT", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCodeAndActive("XXX")).thenReturn(Optional.empty());

        DutyTaxFeeTypeCode result = adapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("VAT")).thenReturn(true);
        assertTrue(DutyTaxFeeTypeCodeAdapter.isValid("VAT"));
    }

    @Test
    @DisplayName("getDutyTaxFeeName should return name")
    public void testGetDutyTaxFeeName() {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));
        assertEquals("Value Added Tax", DutyTaxFeeTypeCodeAdapter.getDutyTaxFeeName("VAT"));
    }

    @Test
    @DisplayName("getDutyTaxFeeCategory should return category")
    public void testGetDutyTaxFeeCategory() {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));
        assertEquals("Tax", DutyTaxFeeTypeCodeAdapter.getDutyTaxFeeCategory("VAT"));
    }

    @Test
    @DisplayName("isVAT should return true for VAT")
    public void testIsVAT() {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));
        assertTrue(DutyTaxFeeTypeCodeAdapter.isVAT("VAT"));
    }

    @Test
    @DisplayName("isGST should return true for GST")
    public void testIsGST() {
        when(repository.findByCodeAndActive("GST")).thenReturn(Optional.of(gst));
        assertTrue(DutyTaxFeeTypeCodeAdapter.isGST("GST"));
    }

    @Test
    @DisplayName("isExempt should return true for exempt")
    public void testIsExempt() {
        when(repository.findByCodeAndActive("EXE")).thenReturn(Optional.of(exempt));
        assertTrue(DutyTaxFeeTypeCodeAdapter.isExempt("EXE"));
    }

    @Test
    @DisplayName("isSummary should return false for non-summary")
    public void testIsSummary() {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));
        assertFalse(DutyTaxFeeTypeCodeAdapter.isSummary("VAT"));
    }

    @Test
    @DisplayName("isCustomsDuty should return true for customs")
    public void testIsCustomsDuty() {
        when(repository.findByCodeAndActive("CST")).thenReturn(Optional.of(customs));
        assertTrue(DutyTaxFeeTypeCodeAdapter.isCustomsDuty("CST"));
    }

    @Test
    @DisplayName("isExciseTax should return false for non-excise")
    public void testIsExciseTax() {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));
        assertFalse(DutyTaxFeeTypeCodeAdapter.isExciseTax("VAT"));
    }

    @Test
    @DisplayName("isSpecialTax should return false for non-special")
    public void testIsSpecialTax() {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));
        assertFalse(DutyTaxFeeTypeCodeAdapter.isSpecialTax("VAT"));
    }

    @Test
    @DisplayName("isVAT should return false for null")
    public void testIsVATNull() {
        assertFalse(DutyTaxFeeTypeCodeAdapter.isVAT(null));
    }

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCodeAndActive("VAT")).thenReturn(Optional.of(vat));

        String marshaled = adapter.marshal(vat);
        DutyTaxFeeTypeCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(vat.getCode(), unmarshaled.getCode());
    }
}
