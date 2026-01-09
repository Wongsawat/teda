package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.FreightCostCodeAdapter;
import com.wpanther.etax.core.entity.FreightCostCode;
import com.wpanther.etax.core.repository.FreightCostCodeRepository;
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
@DisplayName("FreightCostCode Adapter Tests")
public class FreightCostCodeAdapterTest {

    @Mock
    private FreightCostCodeRepository repository;

    @InjectMocks
    private FreightCostCodeAdapter adapter;

    private FreightCostCode basicFreight;
    private FreightCostCode freightSurcharge;
    private FreightCostCode containerService;

    @BeforeEach
    public void setUp() {
        new FreightCostCodeAdapter();
        adapter.setRepository(repository);

        basicFreight = new FreightCostCode("100000", "Basic Freight", "Freight");
        freightSurcharge = new FreightCostCode("101000", "Fuel Surcharge", "Surcharge");
        containerService = new FreightCostCode("200000", "Container Handling", "Container");
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(basicFreight);
        assertEquals("100000", result);
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
        FreightCostCode entity = new FreightCostCode();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        FreightCostCode entity = new FreightCostCode();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        FreightCostCode result = adapter.unmarshal("100000");

        assertNotNull(result);
        assertEquals("100000", result.getCode());
        assertEquals("Basic Freight", result.getName());
        verify(repository).findByCode("100000");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        FreightCostCode result = adapter.unmarshal("  100000  ");

        assertNotNull(result);
        assertEquals("100000", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        FreightCostCode result = adapter.unmarshal("999999");

        assertNotNull(result);
        assertEquals("999999", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        FreightCostCode result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        FreightCostCode result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        FreightCostCodeAdapter nullRepoAdapter = new FreightCostCodeAdapter();
        nullRepoAdapter.setRepository(null);

        FreightCostCode result = nullRepoAdapter.unmarshal("999999");

        assertNotNull(result);
        assertEquals("999999", result.getCode());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("100000")).thenReturn(true);

        boolean result = FreightCostCodeAdapter.isValid("100000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("999999")).thenReturn(false);

        boolean result = FreightCostCodeAdapter.isValid("999999");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = FreightCostCodeAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("getFreightCostName should return name for valid code")
    public void testGetFreightCostNameValidCode() {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        String result = FreightCostCodeAdapter.getFreightCostName("100000");

        assertEquals("Basic Freight", result);
    }

    @Test
    @DisplayName("getFreightCostName should return null for invalid code")
    public void testGetFreightCostNameInvalidCode() {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        String result = FreightCostCodeAdapter.getFreightCostName("999999");

        assertNull(result);
    }

    @Test
    @DisplayName("getFreightCostCategory should return category for valid code")
    public void testGetFreightCostCategoryValidCode() {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        String result = FreightCostCodeAdapter.getFreightCostCategory("100000");

        assertEquals("Freight", result);
    }

    @Test
    @DisplayName("getFreightCostCategory should return null for invalid code")
    public void testGetFreightCostCategoryInvalidCode() {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        String result = FreightCostCodeAdapter.getFreightCostCategory("999999");

        assertNull(result);
    }

    @Test
    @DisplayName("getCodeGroup should return code group for valid code")
    public void testGetCodeGroupValidCode() {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        String result = FreightCostCodeAdapter.getCodeGroup("100000");

        assertEquals("100", result);
    }

    @Test
    @DisplayName("getCodeGroup should return null for invalid code")
    public void testGetCodeGroupInvalidCode() {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        String result = FreightCostCodeAdapter.getCodeGroup("999999");

        assertNull(result);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isBasicFreight should return true for basic freight")
    public void testIsBasicFreightValid() {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        boolean result = FreightCostCodeAdapter.isBasicFreight("100000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isBasicFreight should return false for non-basic freight")
    public void testIsBasicFreightNonBasic() {
        when(repository.findByCode("101000")).thenReturn(Optional.of(freightSurcharge));

        boolean result = FreightCostCodeAdapter.isBasicFreight("101000");

        assertFalse(result);
    }

    @Test
    @DisplayName("isBasicFreight should return false for null")
    public void testIsBasicFreightNull() {
        boolean result = FreightCostCodeAdapter.isBasicFreight(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isFreightSurcharge should return true for surcharge")
    public void testIsFreightSurchargeValid() {
        when(repository.findByCode("101000")).thenReturn(Optional.of(freightSurcharge));

        boolean result = FreightCostCodeAdapter.isFreightSurcharge("101000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isFreightSurcharge should return false for non-surcharge")
    public void testIsFreightSurchargeNonSurcharge() {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        boolean result = FreightCostCodeAdapter.isFreightSurcharge("100000");

        assertFalse(result);
    }

    @Test
    @DisplayName("isContainerService should return true for container service")
    public void testIsContainerServiceValid() {
        when(repository.findByCode("200000")).thenReturn(Optional.of(containerService));

        boolean result = FreightCostCodeAdapter.isContainerService("200000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isContainerService should return false for non-container")
    public void testIsContainerServiceNonContainer() {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        boolean result = FreightCostCodeAdapter.isContainerService("100000");

        assertFalse(result);
    }

    @Test
    @DisplayName("isTerminalCharge should return true for terminal charge")
    public void testIsTerminalChargeValid() {
        FreightCostCode terminalCharge = new FreightCostCode("300000", "Terminal Charge", "Terminal");
        when(repository.findByCode("300000")).thenReturn(Optional.of(terminalCharge));

        boolean result = FreightCostCodeAdapter.isTerminalCharge("300000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isHandlingCharge should return true for handling charge")
    public void testIsHandlingChargeValid() {
        FreightCostCode handlingCharge = new FreightCostCode("400000", "Handling Charge", "Handling");
        when(repository.findByCode("400000")).thenReturn(Optional.of(handlingCharge));

        boolean result = FreightCostCodeAdapter.isHandlingCharge("400000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isStorageOrDemurrage should return true for storage")
    public void testIsStorageOrDemurrageValid() {
        FreightCostCode storage = new FreightCostCode("500000", "Storage Charge", "Storage");
        when(repository.findByCode("500000")).thenReturn(Optional.of(storage));

        boolean result = FreightCostCodeAdapter.isStorageOrDemurrage("500000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isCustomsOrDocumentation should return true for customs")
    public void testIsCustomsOrDocumentationValid() {
        FreightCostCode customs = new FreightCostCode("600000", "Customs Fee", "Customs");
        when(repository.findByCode("600000")).thenReturn(Optional.of(customs));

        boolean result = FreightCostCodeAdapter.isCustomsOrDocumentation("600000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isDangerousGoods should return true for dangerous goods")
    public void testIsDangerousGoodsValid() {
        FreightCostCode dangerousGoods = new FreightCostCode("700000", "Dangerous Goods Fee", "Dangerous");
        when(repository.findByCode("700000")).thenReturn(Optional.of(dangerousGoods));

        boolean result = FreightCostCodeAdapter.isDangerousGoods("700000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isSpecialFreight should return true for special freight")
    public void testIsSpecialFreightValid() {
        FreightCostCode specialFreight = new FreightCostCode("800000", "Special Freight", "Special");
        when(repository.findByCode("800000")).thenReturn(Optional.of(specialFreight));

        boolean result = FreightCostCodeAdapter.isSpecialFreight("800000");

        assertTrue(result);
    }

    @Test
    @DisplayName("isInsurance should return true for insurance")
    public void testIsInsuranceValid() {
        FreightCostCode insurance = new FreightCostCode("900000", "Insurance", "Insurance");
        when(repository.findByCode("900000")).thenReturn(Optional.of(insurance));

        boolean result = FreightCostCodeAdapter.isInsurance("900000");

        assertTrue(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("100000")).thenReturn(Optional.of(basicFreight));

        String marshaled = adapter.marshal(basicFreight);
        FreightCostCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(basicFreight.getCode(), unmarshaled.getCode());
        assertEquals(basicFreight.getName(), unmarshaled.getName());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        FreightCostCode result = adapter.unmarshal("999999");

        assertEquals("999999", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        FreightCostCode result = adapter.unmarshal("999999");

        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("Placeholder should have unknown category")
    public void testPlaceholderCategory() throws Exception {
        when(repository.findByCode("999999")).thenReturn(Optional.empty());

        FreightCostCode result = adapter.unmarshal("999999");

        assertEquals("Unknown", result.getCategory());
    }
}
