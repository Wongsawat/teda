package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.AllowanceChargeReasonCodeAdapter;
import com.wpanther.etax.core.entity.AllowanceChargeReasonCode;
import com.wpanther.etax.core.repository.AllowanceChargeReasonCodeRepository;
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
@DisplayName("AllowanceChargeReasonCode Adapter Tests")
public class AllowanceChargeReasonCodeAdapterTest {

    @Mock
    private AllowanceChargeReasonCodeRepository repository;

    @InjectMocks
    private AllowanceChargeReasonCodeAdapter adapter;

    private AllowanceChargeReasonCode qualityIssue;
    private AllowanceChargeReasonCode discount;
    private AllowanceChargeReasonCode zzz;

    @BeforeEach
    public void setUp() {
        new AllowanceChargeReasonCodeAdapter();
        adapter.setRepository(repository);

        qualityIssue = new AllowanceChargeReasonCode("19", "Quality difference", "Quality difference");
        qualityIssue.setCategory("Quality");

        discount = new AllowanceChargeReasonCode("41", "Special discount", "Special discount");
        discount.setCategory("Discount");

        zzz = new AllowanceChargeReasonCode("ZZZ", "Mutually defined", "Mutually defined");
        zzz.setCategory("Other");
    }

    @Test
    @DisplayName("Should marshal entity to code")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(qualityIssue);
        assertEquals("19", result);
    }

    @Test
    @DisplayName("Should unmarshal valid code")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));

        AllowanceChargeReasonCode result = adapter.unmarshal("19");

        assertNotNull(result);
        assertEquals("19", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("ZZZ")).thenReturn(Optional.empty());

        AllowanceChargeReasonCode result = adapter.unmarshal("ZZZ");

        assertNotNull(result);
        assertEquals("ZZZ", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("19")).thenReturn(true);
        assertTrue(AllowanceChargeReasonCodeAdapter.isValid("19"));
    }

    @Test
    @DisplayName("getReasonName should return name")
    public void testGetReasonName() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertEquals("Quality difference", AllowanceChargeReasonCodeAdapter.getReasonName("19"));
    }

    @Test
    @DisplayName("getReasonCategory should return category")
    public void testGetReasonCategory() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertEquals("Quality", AllowanceChargeReasonCodeAdapter.getReasonCategory("19"));
    }

    @Test
    @DisplayName("getReasonDescription should return description")
    public void testGetReasonDescription() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertNotNull(AllowanceChargeReasonCodeAdapter.getReasonDescription("19"));
    }

    @Test
    @DisplayName("isQualityIssue should return true for quality issues")
    public void testIsQualityIssue() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertTrue(AllowanceChargeReasonCodeAdapter.isQualityIssue("19"));
    }

    @Test
    @DisplayName("isDeliveryIssue should return false for non-delivery")
    public void testIsDeliveryIssue() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isDeliveryIssue("19"));
    }

    @Test
    @DisplayName("isAdministrativeError should return false for non-admin")
    public void testIsAdministrativeError() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isAdministrativeError("19"));
    }

    @Test
    @DisplayName("isDiscountOrAllowance should return true for discount")
    public void testIsDiscountOrAllowance() {
        when(repository.findByCode("41")).thenReturn(Optional.of(discount));
        assertTrue(AllowanceChargeReasonCodeAdapter.isDiscountOrAllowance("41"));
    }

    @Test
    @DisplayName("isFinancialCharge should return false for non-financial")
    public void testIsFinancialCharge() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isFinancialCharge("19"));
    }

    @Test
    @DisplayName("isClaimOrDispute should return false for non-claim")
    public void testIsClaimOrDispute() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isClaimOrDispute("19"));
    }

    @Test
    @DisplayName("isFreightOrLogistics should return false for non-freight")
    public void testIsFreightOrLogistics() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isFreightOrLogistics("19"));
    }

    @Test
    @DisplayName("isPaymentTerms should return false for non-payment")
    public void testIsPaymentTerms() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isPaymentTerms("19"));
    }

    @Test
    @DisplayName("isHRRelated should return false for non-HR")
    public void testIsHRRelated() {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));
        assertFalse(AllowanceChargeReasonCodeAdapter.isHRRelated("19"));
    }

    @Test
    @DisplayName("isMutuallyDefined should return true for ZZZ")
    public void testIsMutuallyDefined() {
        when(repository.findByCode("ZZZ")).thenReturn(Optional.of(zzz));
        assertTrue(AllowanceChargeReasonCodeAdapter.isMutuallyDefined("ZZZ"));
    }

    @Test
    @DisplayName("isQualityIssue should return false for null")
    public void testIsQualityIssueNull() {
        assertFalse(AllowanceChargeReasonCodeAdapter.isQualityIssue(null));
    }

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("19")).thenReturn(Optional.of(qualityIssue));

        String marshaled = adapter.marshal(qualityIssue);
        AllowanceChargeReasonCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(qualityIssue.getCode(), unmarshaled.getCode());
    }
}
