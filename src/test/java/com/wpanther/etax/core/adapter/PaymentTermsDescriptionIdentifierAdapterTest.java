package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.PaymentTermsDescriptionIdentifierAdapter;
import com.wpanther.etax.core.entity.PaymentTermsDescriptionIdentifier;
import com.wpanther.etax.core.repository.PaymentTermsDescriptionIdentifierRepository;
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
@DisplayName("PaymentTermsDescriptionIdentifier Adapter Tests")
public class PaymentTermsDescriptionIdentifierAdapterTest {

    @Mock
    private PaymentTermsDescriptionIdentifierRepository repository;

    @InjectMocks
    private PaymentTermsDescriptionIdentifierAdapter adapter;

    private PaymentTermsDescriptionIdentifier issuingBank;
    private PaymentTermsDescriptionIdentifier noDraft;

    @BeforeEach
    public void setUp() {
        new PaymentTermsDescriptionIdentifierAdapter();
        adapter.setRepository(repository);

        issuingBank = new PaymentTermsDescriptionIdentifier("1", "Issuing Bank Draft", "Draft on issuing bank");

        noDraft = new PaymentTermsDescriptionIdentifier("6", "No Draft", "No draft required");
    }

    @Test
    @DisplayName("Should marshal entity to code")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(issuingBank);
        assertEquals("1", result);
    }

    @Test
    @DisplayName("Should unmarshal valid code")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("1")).thenReturn(Optional.of(issuingBank));

        PaymentTermsDescriptionIdentifier result = adapter.unmarshal("1");

        assertNotNull(result);
        assertEquals("1", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("9")).thenReturn(Optional.empty());

        PaymentTermsDescriptionIdentifier result = adapter.unmarshal("9");

        assertNotNull(result);
        assertEquals("9", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("1")).thenReturn(true);
        assertTrue(PaymentTermsDescriptionIdentifierAdapter.isValid("1"));
    }

    @Test
    @DisplayName("getPaymentTermsDescriptionName should return name")
    public void testGetPaymentTermsDescriptionName() {
        when(repository.findByCode("1")).thenReturn(Optional.of(issuingBank));
        assertEquals("Issuing Bank Draft", PaymentTermsDescriptionIdentifierAdapter.getPaymentTermsDescriptionName("1"));
    }

    @Test
    @DisplayName("isDraftRequired should return true for draft required")
    public void testIsDraftRequired() {
        when(repository.findByCode("1")).thenReturn(Optional.of(issuingBank));
        assertTrue(PaymentTermsDescriptionIdentifierAdapter.isDraftRequired("1"));
    }

    @Test
    @DisplayName("isBankingDraft should return true for banking draft")
    public void testIsBankingDraft() {
        when(repository.findByCode("1")).thenReturn(Optional.of(issuingBank));
        assertTrue(PaymentTermsDescriptionIdentifierAdapter.isBankingDraft("1"));
    }

    @Test
    @DisplayName("isIssuingBankDraft should return true for issuing bank")
    public void testIsIssuingBankDraft() {
        when(repository.findByCode("1")).thenReturn(Optional.of(issuingBank));
        assertTrue(PaymentTermsDescriptionIdentifierAdapter.isIssuingBankDraft("1"));
    }

    @Test
    @DisplayName("isNoDraft should return true for no draft")
    public void testIsNoDraft() {
        when(repository.findByCode("6")).thenReturn(Optional.of(noDraft));
        assertTrue(PaymentTermsDescriptionIdentifierAdapter.isNoDraft("6"));
    }

    @Test
    @DisplayName("isDraftRequired should return false for null")
    public void testIsDraftRequiredNull() {
        assertFalse(PaymentTermsDescriptionIdentifierAdapter.isDraftRequired(null));
    }

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("1")).thenReturn(Optional.of(issuingBank));

        String marshaled = adapter.marshal(issuingBank);
        PaymentTermsDescriptionIdentifier unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(issuingBank.getCode(), unmarshaled.getCode());
    }
}
