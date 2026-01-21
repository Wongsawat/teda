package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.PaymentTermsTypeCodeAdapter;
import com.wpanther.etax.core.entity.PaymentTermsTypeCode;
import com.wpanther.etax.core.repository.PaymentTermsTypeCodeRepository;
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
@DisplayName("PaymentTermsTypeCode Adapter Tests")
public class PaymentTermsTypeCodeAdapterTest {

    @Mock
    private PaymentTermsTypeCodeRepository repository;

    @InjectMocks
    private PaymentTermsTypeCodeAdapter adapter;

    private PaymentTermsTypeCode immediate;
    private PaymentTermsTypeCode net30;
    private PaymentTermsTypeCode net60;

    @BeforeEach
    public void setUp() {
        new PaymentTermsTypeCodeAdapter();
        adapter.setRepository(repository);

        immediate = new PaymentTermsTypeCode("10", "Instant", "Payment is due on receipt of invoice.");
        immediate.setCategory("Immediate Payment");
        immediate.setIsImmediate(true);

        net30 = new PaymentTermsTypeCode("4", "Deferred", "Payments are deferred beyond the normal due date.");
        net30.setCategory("Deferred Payment");
        net30.setIsDeferred(true);

        net60 = new PaymentTermsTypeCode("22", "Discount", "Payment terms on which discounts are applicable.");
        net60.setCategory("Discount Terms");
        net60.setHasDiscount(true);
    }

    @Test
    @DisplayName("Should marshal entity to code")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(immediate);
        assertEquals("10", result);
    }

    @Test
    @DisplayName("Should unmarshal valid code")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("10")).thenReturn(Optional.of(immediate));

        PaymentTermsTypeCode result = adapter.unmarshal("10");

        assertNotNull(result);
        assertEquals("10", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("99")).thenReturn(Optional.empty());

        PaymentTermsTypeCode result = adapter.unmarshal("99");

        assertNotNull(result);
        assertEquals("99", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("10")).thenReturn(true);
        assertTrue(PaymentTermsTypeCodeAdapter.isValid("10"));
    }

    @Test
    @DisplayName("getPaymentTermsName should return name")
    public void testGetPaymentTermsName() {
        when(repository.findByCode("10")).thenReturn(Optional.of(immediate));
        assertEquals("Instant", PaymentTermsTypeCodeAdapter.getPaymentTermsName("10"));
    }

    @Test
    @DisplayName("getPaymentTermsCategory should return category")
    public void testGetPaymentTermsCategory() {
        when(repository.findByCode("10")).thenReturn(Optional.of(immediate));
        assertEquals("Immediate Payment", PaymentTermsTypeCodeAdapter.getPaymentTermsCategory("10"));
    }

    @Test
    @DisplayName("isImmediatePayment should return true for immediate")
    public void testIsImmediatePayment() {
        when(repository.findByCode("10")).thenReturn(Optional.of(immediate));
        assertTrue(PaymentTermsTypeCodeAdapter.isImmediatePayment("10"));
    }

    @Test
    @DisplayName("isDeferredPayment should return true for deferred")
    public void testIsDeferredPayment() {
        when(repository.findByCode("4")).thenReturn(Optional.of(net30));
        assertTrue(PaymentTermsTypeCodeAdapter.isDeferredPayment("4"));
    }

    @Test
    @DisplayName("hasDiscount should return true for discount terms")
    public void testHasDiscount() {
        when(repository.findByCode("22")).thenReturn(Optional.of(net60));
        assertTrue(PaymentTermsTypeCodeAdapter.hasDiscount("22"));
    }

    @Test
    @DisplayName("isImmediatePayment should return false for null")
    public void testIsImmediatePaymentNull() {
        assertFalse(PaymentTermsTypeCodeAdapter.isImmediatePayment(null));
    }

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("10")).thenReturn(Optional.of(immediate));

        String marshaled = adapter.marshal(immediate);
        PaymentTermsTypeCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(immediate.getCode(), unmarshaled.getCode());
    }
}
