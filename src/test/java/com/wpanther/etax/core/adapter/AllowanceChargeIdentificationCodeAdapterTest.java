package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.AllowanceChargeIdentificationCodeAdapter;
import com.wpanther.etax.core.entity.AllowanceChargeIdentificationCode;
import com.wpanther.etax.core.repository.AllowanceChargeIdentificationCodeRepository;
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
@DisplayName("AllowanceChargeIdentificationCode Adapter Tests")
public class AllowanceChargeIdentificationCodeAdapterTest {

    @Mock
    private AllowanceChargeIdentificationCodeRepository repository;

    @InjectMocks
    private AllowanceChargeIdentificationCodeAdapter adapter;

    private AllowanceChargeIdentificationCode discountCode;
    private AllowanceChargeIdentificationCode thaiExtensionCode;

    @BeforeEach
    public void setUp() {
        // Initialize static repository for adapter
        new AllowanceChargeIdentificationCodeAdapter();
        adapter.setRepository(repository);

        // Create test entities
        discountCode = new AllowanceChargeIdentificationCode("30", "Manufacturer discount", "Discount from manufacturer");
        discountCode.setCategory("Discount");
        discountCode.setIsStandardCode(true);
        discountCode.setIsThaiExtension(false);

        thaiExtensionCode = new AllowanceChargeIdentificationCode("PP001", "Thai Deposit", "Thai specific deposit code");
        thaiExtensionCode.setCategory("Other");
        thaiExtensionCode.setIsStandardCode(false);
        thaiExtensionCode.setIsThaiExtension(true);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(discountCode);

        assertEquals("30", result);
    }

    @Test
    @DisplayName("Should marshal Thai extension code")
    public void testMarshalThaiExtension() throws Exception {
        String result = adapter.marshal(thaiExtensionCode);

        assertEquals("PP001", result);
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
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        AllowanceChargeIdentificationCode result = adapter.unmarshal("30");

        assertNotNull(result);
        assertEquals("30", result.getCode());
        assertEquals("Manufacturer discount", result.getName());
        assertEquals("Discount", result.getCategory());
        verify(repository).findByCode("30");
    }

    @Test
    @DisplayName("Should unmarshal code case-insensitively")
    public void testUnmarshalCaseInsensitive() throws Exception {
        when(repository.findByCode("PP001")).thenReturn(Optional.of(thaiExtensionCode));

        AllowanceChargeIdentificationCode result = adapter.unmarshal("pp001");

        assertNotNull(result);
        assertEquals("PP001", result.getCode());
        assertEquals("Thai Deposit", result.getName());
        verify(repository).findByCode("PP001");
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        AllowanceChargeIdentificationCode result = adapter.unmarshal("  30  ");

        assertNotNull(result);
        assertEquals("30", result.getCode());
        verify(repository).findByCode("30");
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("999")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("999");

        assertNotNull(result);
        assertEquals("999", result.getCode());
        assertEquals("Other", result.getCategory());
        assertFalse(result.getIsStandardCode());
        verify(repository).findByCode("999");
    }

    @Test
    @DisplayName("Should create placeholder with Thai extension detection for PP codes")
    public void testUnmarshalPlaceholderThaiDetection() throws Exception {
        when(repository.findByCode("PP999")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("PP999");

        assertNotNull(result);
        assertEquals("PP999", result.getCode());
        assertEquals("Other", result.getCategory());
        assertFalse(result.getIsStandardCode());
        assertTrue(result.getIsThaiExtension());
    }

    @Test
    @DisplayName("Should create placeholder without Thai extension for non-PP codes")
    public void testUnmarshalPlaceholderNonThai() throws Exception {
        when(repository.findByCode("999")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("999");

        assertNotNull(result);
        assertEquals("999", result.getCode());
        assertFalse(result.getIsThaiExtension());
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        AllowanceChargeIdentificationCode result = adapter.unmarshal(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        AllowanceChargeIdentificationCode result = adapter.unmarshal("");

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        AllowanceChargeIdentificationCode result = adapter.unmarshal("   ");

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        AllowanceChargeIdentificationCodeAdapter nullRepoAdapter = new AllowanceChargeIdentificationCodeAdapter();
        nullRepoAdapter.setRepository(null);

        AllowanceChargeIdentificationCode result = nullRepoAdapter.unmarshal("XX");

        assertNotNull(result);
        assertEquals("XX", result.getCode());
        assertEquals("Other", result.getCategory());
        assertFalse(result.getIsStandardCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("30")).thenReturn(true);

        boolean result = AllowanceChargeIdentificationCodeAdapter.isValid("30");

        assertTrue(result);
        verify(repository).existsByCode("30");
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("999")).thenReturn(false);

        boolean result = AllowanceChargeIdentificationCodeAdapter.isValid("999");

        assertFalse(result);
        verify(repository).existsByCode("999");
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = AllowanceChargeIdentificationCodeAdapter.isValid(null);

        assertFalse(result);
        verify(repository, never()).existsByCode(anyString());
    }

    @Test
    @DisplayName("isValid should return false for empty code")
    public void testIsValidEmptyCode() {
        boolean result = AllowanceChargeIdentificationCodeAdapter.isValid("");

        assertFalse(result);
        verify(repository, never()).existsByCode(anyString());
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new AllowanceChargeIdentificationCodeAdapter();
        adapter.setRepository(null);

        boolean result = AllowanceChargeIdentificationCodeAdapter.isValid("30");

        assertFalse(result);
    }

    @Test
    @DisplayName("getName should return name for valid code")
    public void testGetNameValidCode() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        String result = AllowanceChargeIdentificationCodeAdapter.getName("30");

        assertEquals("Manufacturer discount", result);
        verify(repository).findByCode("30");
    }

    @Test
    @DisplayName("getName should return null for invalid code")
    public void testGetNameInvalidCode() {
        when(repository.findByCode("999")).thenReturn(Optional.empty());

        String result = AllowanceChargeIdentificationCodeAdapter.getName("999");

        assertNull(result);
        verify(repository).findByCode("999");
    }

    @Test
    @DisplayName("getName should return null for null code")
    public void testGetNameNullCode() {
        String result = AllowanceChargeIdentificationCodeAdapter.getName(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("getCategory should return category for valid code")
    public void testGetCategoryValidCode() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        String result = AllowanceChargeIdentificationCodeAdapter.getCategory("30");

        assertEquals("Discount", result);
        verify(repository).findByCode("30");
    }

    @Test
    @DisplayName("getCategory should return null for invalid code")
    public void testGetCategoryInvalidCode() {
        when(repository.findByCode("999")).thenReturn(Optional.empty());

        String result = AllowanceChargeIdentificationCodeAdapter.getCategory("999");

        assertNull(result);
        verify(repository).findByCode("999");
    }

    @Test
    @DisplayName("getCategory should return null for null code")
    public void testGetCategoryNullCode() {
        String result = AllowanceChargeIdentificationCodeAdapter.getCategory(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("getDescription should return description for valid code")
    public void testGetDescriptionValidCode() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        String result = AllowanceChargeIdentificationCodeAdapter.getDescription("30");

        assertEquals("Discount from manufacturer", result);
        verify(repository).findByCode("30");
    }

    @Test
    @DisplayName("getDescription should return null for invalid code")
    public void testGetDescriptionInvalidCode() {
        when(repository.findByCode("999")).thenReturn(Optional.empty());

        String result = AllowanceChargeIdentificationCodeAdapter.getDescription("999");

        assertNull(result);
        verify(repository).findByCode("999");
    }

    @Test
    @DisplayName("getDescription should return null for null code")
    public void testGetDescriptionNullCode() {
        String result = AllowanceChargeIdentificationCodeAdapter.getDescription(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    // Static Business Logic Tests - Category Checks

    @Test
    @DisplayName("isDocumentaryCreditCommission should return true for correct category")
    public void testIsDocumentaryCreditCommissionValid() {
        AllowanceChargeIdentificationCode docCredit = new AllowanceChargeIdentificationCode();
        docCredit.setCategory("Documentary Credit Commission");
        when(repository.findByCode("1")).thenReturn(Optional.of(docCredit));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isDocumentaryCreditCommission("1");

        assertTrue(result);
        verify(repository).findByCode("1");
    }

    @Test
    @DisplayName("isDocumentaryCreditCommission should return false for different category")
    public void testIsDocumentaryCreditCommissionInvalid() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isDocumentaryCreditCommission("30");

        assertFalse(result);
    }

    @Test
    @DisplayName("isDocumentaryCreditCommission should return false for null code")
    public void testIsDocumentaryCreditCommissionNull() {
        boolean result = AllowanceChargeIdentificationCodeAdapter.isDocumentaryCreditCommission(null);

        assertFalse(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("isCollectionCommission should return true for correct category")
    public void testIsCollectionCommissionValid() {
        AllowanceChargeIdentificationCode collectionComm = new AllowanceChargeIdentificationCode();
        collectionComm.setCategory("Collection Commission");
        when(repository.findByCode("10")).thenReturn(Optional.of(collectionComm));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isCollectionCommission("10");

        assertTrue(result);
    }

    @Test
    @DisplayName("isProcessingFee should return true for correct category")
    public void testIsProcessingFeeValid() {
        AllowanceChargeIdentificationCode processingFee = new AllowanceChargeIdentificationCode();
        processingFee.setCategory("Processing Fee");
        when(repository.findByCode("25")).thenReturn(Optional.of(processingFee));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isProcessingFee("25");

        assertTrue(result);
    }

    @Test
    @DisplayName("isDiscount should return true for correct category")
    public void testIsDiscountValid() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isDiscount("30");

        assertTrue(result);
    }

    @Test
    @DisplayName("isDiscount should return false for different category")
    public void testIsDiscountInvalid() {
        AllowanceChargeIdentificationCode otherCode = new AllowanceChargeIdentificationCode();
        otherCode.setCategory("Freight Charges");
        when(repository.findByCode("79")).thenReturn(Optional.of(otherCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isDiscount("79");

        assertFalse(result);
    }

    @Test
    @DisplayName("isPenalty should return true for correct category")
    public void testIsPenaltyValid() {
        AllowanceChargeIdentificationCode penalty = new AllowanceChargeIdentificationCode();
        penalty.setCategory("Penalty");
        when(repository.findByCode("98")).thenReturn(Optional.of(penalty));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isPenalty("98");

        assertTrue(result);
    }

    @Test
    @DisplayName("isBonus should return true for correct category")
    public void testIsBonusValid() {
        AllowanceChargeIdentificationCode bonus = new AllowanceChargeIdentificationCode();
        bonus.setCategory("Bonus");
        when(repository.findByCode("99")).thenReturn(Optional.of(bonus));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isBonus("99");

        assertTrue(result);
    }

    @Test
    @DisplayName("isFreightCharges should return true for correct category")
    public void testIsFreightChargesValid() {
        AllowanceChargeIdentificationCode freight = new AllowanceChargeIdentificationCode();
        freight.setCategory("Freight Charges");
        when(repository.findByCode("79")).thenReturn(Optional.of(freight));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isFreightCharges("79");

        assertTrue(result);
    }

    @Test
    @DisplayName("isStandardCode should return true for standard codes")
    public void testIsStandardCodeValid() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isStandardCode("30");

        assertTrue(result);
    }

    @Test
    @DisplayName("isStandardCode should return false for Thai extensions")
    public void testIsStandardCodeThaiExtension() {
        when(repository.findByCode("PP001")).thenReturn(Optional.of(thaiExtensionCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isStandardCode("PP001");

        assertFalse(result);
    }

    @Test
    @DisplayName("isThaiExtension should return true for Thai extensions")
    public void testIsThaiExtensionValid() {
        when(repository.findByCode("PP001")).thenReturn(Optional.of(thaiExtensionCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isThaiExtension("PP001");

        assertTrue(result);
    }

    @Test
    @DisplayName("isThaiExtension should return false for standard codes")
    public void testIsThaiExtensionStandard() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isThaiExtension("30");

        assertFalse(result);
    }

    @Test
    @DisplayName("isThaiExtension should return false for null code")
    public void testIsThaiExtensionNull() {
        boolean result = AllowanceChargeIdentificationCodeAdapter.isThaiExtension(null);

        assertFalse(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("isCommission should return true for commission categories")
    public void testIsCommissionValid() {
        AllowanceChargeIdentificationCode docCredit = new AllowanceChargeIdentificationCode();
        docCredit.setCategory("Documentary Credit Commission");
        when(repository.findByCode("1")).thenReturn(Optional.of(docCredit));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isCommission("1");

        assertTrue(result);
    }

    @Test
    @DisplayName("isCommission should return false for non-commission categories")
    public void testIsCommissionInvalid() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isCommission("30");

        assertFalse(result);
    }

    @Test
    @DisplayName("isCharge should return true for charge categories")
    public void testIsChargeValid() {
        AllowanceChargeIdentificationCode freight = new AllowanceChargeIdentificationCode();
        freight.setCategory("Freight Charges");
        when(repository.findByCode("79")).thenReturn(Optional.of(freight));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isCharge("79");

        assertTrue(result);
    }

    @Test
    @DisplayName("isCharge should return false for allowance categories")
    public void testIsChargeAllowance() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isCharge("30");

        assertFalse(result);
    }

    @Test
    @DisplayName("isCharge should return false for null code")
    public void testIsChargeNull() {
        boolean result = AllowanceChargeIdentificationCodeAdapter.isCharge(null);

        assertFalse(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("isAllowance should return true for allowance categories")
    public void testIsAllowanceValid() {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isAllowance("30");

        assertTrue(result);
    }

    @Test
    @DisplayName("isAllowance should return false for charge categories")
    public void testIsAllowanceCharge() {
        AllowanceChargeIdentificationCode freight = new AllowanceChargeIdentificationCode();
        freight.setCategory("Freight Charges");
        when(repository.findByCode("79")).thenReturn(Optional.of(freight));

        boolean result = AllowanceChargeIdentificationCodeAdapter.isAllowance("79");

        assertFalse(result);
    }

    @Test
    @DisplayName("isAllowance should return false for null code")
    public void testIsAllowanceNull() {
        boolean result = AllowanceChargeIdentificationCodeAdapter.isAllowance(null);

        assertFalse(result);
        verify(repository, never()).findByCode(anyString());
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        String marshaled = adapter.marshal(discountCode);
        AllowanceChargeIdentificationCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(discountCode.getCode(), unmarshaled.getCode());
        assertEquals(discountCode.getName(), unmarshaled.getName());
        assertEquals(discountCode.getCategory(), unmarshaled.getCategory());
    }

    @Test
    @DisplayName("Should round-trip with Thai extension code")
    public void testRoundTripThaiExtension() throws Exception {
        when(repository.findByCode("PP001")).thenReturn(Optional.of(thaiExtensionCode));

        String marshaled = adapter.marshal(thaiExtensionCode);
        AllowanceChargeIdentificationCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(thaiExtensionCode.getCode(), unmarshaled.getCode());
        assertEquals(thaiExtensionCode.getName(), unmarshaled.getName());
        assertEquals(thaiExtensionCode.getIsThaiExtension(), unmarshaled.getIsThaiExtension());
    }

    @Test
    @DisplayName("Should round-trip with case changes")
    public void testRoundTripCaseChange() throws Exception {
        when(repository.findByCode("PP001")).thenReturn(Optional.of(thaiExtensionCode));

        String marshaled = adapter.marshal(thaiExtensionCode);
        AllowanceChargeIdentificationCode unmarshaled = adapter.unmarshal(marshaled.toLowerCase());

        assertEquals(thaiExtensionCode.getCode(), unmarshaled.getCode());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("XX");

        assertEquals("XX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("XX");

        assertTrue(result.getName().contains("Unknown"));
        assertTrue(result.getName().contains("XX"));
    }

    @Test
    @DisplayName("Placeholder should have Other category")
    public void testPlaceholderCategory() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("XX");

        assertEquals("Other", result.getCategory());
    }

    @Test
    @DisplayName("Placeholder should have description")
    public void testPlaceholderDescription() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("XX");

        assertNotNull(result.getDescription());
        assertTrue(result.getDescription().contains("placeholder") ||
                   result.getDescription().contains("Placeholder"));
    }

    @Test
    @DisplayName("Placeholder should have isStandardCode false")
    public void testPlaceholderStandardCode() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("XX");

        assertFalse(result.getIsStandardCode());
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCode(anyString())).thenReturn(Optional.empty());

        AllowanceChargeIdentificationCode result = adapter.unmarshal("XX");

        assertNotNull(result);
        assertEquals("Other", result.getCategory());
        assertFalse(result.getIsStandardCode());
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));
        when(repository.findByCode("PP001")).thenReturn(Optional.of(thaiExtensionCode));

        AllowanceChargeIdentificationCode result1 = adapter.unmarshal("30");
        AllowanceChargeIdentificationCode result2 = adapter.unmarshal("PP001");

        assertEquals("30", result1.getCode());
        assertEquals("PP001", result2.getCode());
        verify(repository, times(2)).findByCode(anyString());
    }

    @Test
    @DisplayName("Should handle case insensitive repository lookups")
    public void testCaseInsensitiveRepositoryLookup() throws Exception {
        when(repository.findByCode("30")).thenReturn(Optional.of(discountCode));

        AllowanceChargeIdentificationCode result = adapter.unmarshal("30");

        assertNotNull(result);
        assertEquals("30", result.getCode());
        // Verify the lookup was done with uppercase
        verify(repository).findByCode("30");
    }
}
