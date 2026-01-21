package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.DeliveryTermsCodeAdapter;
import com.wpanther.etax.core.entity.DeliveryTermsCode;
import com.wpanther.etax.core.repository.DeliveryTermsCodeRepository;
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
@DisplayName("DeliveryTermsCode Adapter Tests")
public class DeliveryTermsCodeAdapterTest {

    @Mock
    private DeliveryTermsCodeRepository repository;

    @InjectMocks
    private DeliveryTermsCodeAdapter adapter;

    private DeliveryTermsCode exw;
    private DeliveryTermsCode fob;
    private DeliveryTermsCode cif;

    @BeforeEach
    public void setUp() {
        new DeliveryTermsCodeAdapter();
        adapter.setRepository(repository);

        // EXW - Group E (Departure) - Minimum seller obligation
        exw = new DeliveryTermsCode("EXW", "Ex Works");
        exw.setIncotermGroup("E");
        exw.setSellerObligation("Minimum");

        // FOB - Group F (Main carriage unpaid) - Low seller obligation
        fob = new DeliveryTermsCode("FOB", "Free On Board");
        fob.setIncotermGroup("F");
        fob.setSellerObligation("Low");

        // CIF - Group C (Main carriage paid) - Medium seller obligation
        cif = new DeliveryTermsCode("CIF", "Cost Insurance and Freight");
        cif.setIncotermGroup("C");
        cif.setSellerObligation("Medium");
    }

    @Test
    @DisplayName("Should marshal entity to code")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(exw);
        assertEquals("EXW", result);
    }

    @Test
    @DisplayName("Should unmarshal valid code")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));

        DeliveryTermsCode result = adapter.unmarshal("EXW");

        assertNotNull(result);
        assertEquals("EXW", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        DeliveryTermsCode result = adapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("EXW")).thenReturn(true);
        assertTrue(DeliveryTermsCodeAdapter.isValid("EXW"));
    }

    @Test
    @DisplayName("getDeliveryTermsName should return name")
    public void testGetDeliveryTermsName() {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));
        assertEquals("Ex Works", DeliveryTermsCodeAdapter.getDeliveryTermsName("EXW"));
    }

    @Test
    @DisplayName("getIncotermGroup should return group")
    public void testGetIncotermGroup() {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));
        assertEquals("E", DeliveryTermsCodeAdapter.getIncotermGroup("EXW"));
    }

    @Test
    @DisplayName("getSellerObligation should return obligation")
    public void testGetSellerObligation() {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));
        assertEquals("Minimum", DeliveryTermsCodeAdapter.getSellerObligation("EXW"));
    }

    @Test
    @DisplayName("isIncoterm should return true for INCOTERMS")
    public void testIsIncoterm() {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));
        assertTrue(DeliveryTermsCodeAdapter.isIncoterm("EXW"));
    }

    @Test
    @DisplayName("isGroupE should return true for Group E")
    public void testIsGroupE() {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));
        assertTrue(DeliveryTermsCodeAdapter.isGroupE("EXW"));
    }

    @Test
    @DisplayName("isGroupF should return true for Group F")
    public void testIsGroupF() {
        when(repository.findByCode("FOB")).thenReturn(Optional.of(fob));
        assertTrue(DeliveryTermsCodeAdapter.isGroupF("FOB"));
    }

    @Test
    @DisplayName("isGroupC should return true for Group C")
    public void testIsGroupC() {
        when(repository.findByCode("CIF")).thenReturn(Optional.of(cif));
        assertTrue(DeliveryTermsCodeAdapter.isGroupC("CIF"));
    }

    @Test
    @DisplayName("includesInsurance should return true for CIF")
    public void testIncludesInsurance() {
        when(repository.findByCode("CIF")).thenReturn(Optional.of(cif));
        assertTrue(DeliveryTermsCodeAdapter.includesInsurance("CIF"));
    }

    @Test
    @DisplayName("includesFreight should return true for CIF")
    public void testIncludesFreight() {
        when(repository.findByCode("CIF")).thenReturn(Optional.of(cif));
        assertTrue(DeliveryTermsCodeAdapter.includesFreight("CIF"));
    }

    @Test
    @DisplayName("isSeaTransportOnly should return true for CIF")
    public void testIsSeaTransportOnly() {
        when(repository.findByCode("CIF")).thenReturn(Optional.of(cif));
        assertTrue(DeliveryTermsCodeAdapter.isSeaTransportOnly("CIF"));
    }

    @Test
    @DisplayName("isAnyTransportMode should return true for EXW")
    public void testIsAnyTransportMode() {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));
        assertTrue(DeliveryTermsCodeAdapter.isAnyTransportMode("EXW"));
    }

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("EXW")).thenReturn(Optional.of(exw));

        String marshaled = adapter.marshal(exw);
        DeliveryTermsCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(exw.getCode(), unmarshaled.getCode());
    }
}
