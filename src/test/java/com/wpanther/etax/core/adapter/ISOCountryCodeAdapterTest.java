package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ISOCountryCodeAdapter;
import com.wpanther.etax.core.entity.ISOCountryCode;
import com.wpanther.etax.core.repository.ISOCountryCodeRepository;
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
@DisplayName("ISOCountryCode Adapter Tests")
public class ISOCountryCodeAdapterTest {

    @Mock
    private ISOCountryCodeRepository repository;

    @InjectMocks
    private ISOCountryCodeAdapter adapter;

    private ISOCountryCode thailand;
    private ISOCountryCode unitedStates;

    @BeforeEach
    public void setUp() {
        // Initialize static repository for adapter
        new ISOCountryCodeAdapter();
        adapter.setRepository(repository);

        // Create test entities
        thailand = new ISOCountryCode("TH", "THAILAND");
        thailand.setActive(true);
        thailand.setEtdaExtension(false);

        unitedStates = new ISOCountryCode("US", "UNITED STATES");
        unitedStates.setActive(true);
        unitedStates.setEtdaExtension(false);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(thailand);

        assertEquals("TH", result);
    }

    @Test
    @DisplayName("Should marshal entity with uppercase code")
    public void testMarshalUppercase() throws Exception {
        String result = adapter.marshal(unitedStates);

        assertEquals("US", result);
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
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode(null);

        String result = adapter.marshal(entity);

        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode("");

        String result = adapter.marshal(entity);

        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with whitespace code to null")
    public void testMarshalWhitespaceCode() throws Exception {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode("   ");

        String result = adapter.marshal(entity);

        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        ISOCountryCode result = adapter.unmarshal("TH");

        assertNotNull(result);
        assertEquals("TH", result.getCode());
        assertEquals("THAILAND", result.getName());
        verify(repository).findByCode("TH");
    }

    @Test
    @DisplayName("Should unmarshal code case-insensitively")
    public void testUnmarshalCaseInsensitive() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        ISOCountryCode result = adapter.unmarshal("th");

        assertNotNull(result);
        assertEquals("TH", result.getCode());
        assertEquals("THAILAND", result.getName());
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        ISOCountryCode result = adapter.unmarshal("  th  ");

        assertNotNull(result);
        assertEquals("TH", result.getCode());
        assertEquals("THAILAND", result.getName());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOCountryCode result = adapter.unmarshal("XX");

        assertNotNull(result);
        assertEquals("XX", result.getCode());
        assertFalse(result.isActive());
        assertTrue(result.getName().contains("Unknown"));
        verify(repository).findByCode("XX");
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ISOCountryCode result = adapter.unmarshal(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        ISOCountryCode result = adapter.unmarshal("");

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        ISOCountryCode result = adapter.unmarshal("   ");

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        // Set repository to null by creating a new adapter without injection
        ISOCountryCodeAdapter nullRepoAdapter = new ISOCountryCodeAdapter();
        nullRepoAdapter.setRepository(null);

        ISOCountryCode result = nullRepoAdapter.unmarshal("XX");

        assertNotNull(result);
        assertEquals("XX", result.getCode());
        assertFalse(result.isActive());
        assertTrue(result.getName().contains("Unknown"));
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("toEntity should return entity for valid code")
    public void testToEntityValidCode() {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        ISOCountryCode result = ISOCountryCodeAdapter.toEntity("TH");

        assertNotNull(result);
        assertEquals("TH", result.getCode());
        assertEquals("THAILAND", result.getName());
    }

    @Test
    @DisplayName("toEntity should return null for null code")
    public void testToEntityNullCode() {
        ISOCountryCode result = ISOCountryCodeAdapter.toEntity(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("toEntity should return null for empty code")
    public void testToEntityEmptyCode() {
        ISOCountryCode result = ISOCountryCodeAdapter.toEntity("");

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("toEntity should return placeholder for invalid code")
    public void testToEntityInvalidCode() {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOCountryCode result = ISOCountryCodeAdapter.toEntity("XX");

        assertNotNull(result);
        assertEquals("XX", result.getCode());
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("toCode should return code for valid entity")
    public void testToCodeValidEntity() {
        String result = ISOCountryCodeAdapter.toCode(thailand);

        assertEquals("TH", result);
    }

    @Test
    @DisplayName("toCode should return null for null entity")
    public void testToCodeNullEntity() {
        String result = ISOCountryCodeAdapter.toCode(null);

        assertNull(result);
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        boolean result = ISOCountryCodeAdapter.isValid("TH");

        assertTrue(result);
        verify(repository).findByCode("TH");
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        boolean result = ISOCountryCodeAdapter.isValid("XX");

        assertFalse(result);
        verify(repository).findByCode("XX");
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = ISOCountryCodeAdapter.isValid(null);

        assertFalse(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("isValid should return false for empty code")
    public void testIsValidEmptyCode() {
        boolean result = ISOCountryCodeAdapter.isValid("");

        assertFalse(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        // Temporarily set repository to null
        new ISOCountryCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOCountryCodeAdapter.isValid("TH");

        assertFalse(result);
    }

    @Test
    @DisplayName("getName should return name for valid code")
    public void testGetNameValidCode() {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        String result = ISOCountryCodeAdapter.getName("TH");

        assertEquals("THAILAND", result);
        verify(repository).findByCode("TH");
    }

    @Test
    @DisplayName("getName should return null for invalid code")
    public void testGetNameInvalidCode() {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        String result = ISOCountryCodeAdapter.getName("XX");

        assertNull(result);
        verify(repository).findByCode("XX");
    }

    @Test
    @DisplayName("getName should return null for null code")
    public void testGetNameNullCode() {
        String result = ISOCountryCodeAdapter.getName(null);

        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("normalize should return uppercase code")
    public void testNormalize() {
        String result = ISOCountryCodeAdapter.normalize("th");

        assertEquals("TH", result);
    }

    @Test
    @DisplayName("normalize should trim whitespace")
    public void testNormalizeTrim() {
        String result = ISOCountryCodeAdapter.normalize("  th  ");

        assertEquals("TH", result);
    }

    @Test
    @DisplayName("normalize should return null for null input")
    public void testNormalizeNull() {
        String result = ISOCountryCodeAdapter.normalize(null);

        assertNull(result);
    }

    @Test
    @DisplayName("normalize should return null for empty input")
    public void testNormalizeEmpty() {
        String result = ISOCountryCodeAdapter.normalize("");

        assertNull(result);
    }

    @Test
    @DisplayName("normalize should return null for whitespace input")
    public void testNormalizeWhitespace() {
        String result = ISOCountryCodeAdapter.normalize("   ");

        assertNull(result);
    }

    // Static Business Logic Tests

    @Test
    @DisplayName("isThailand should return true for TH")
    public void testIsThailandValidCode() {
        boolean result = ISOCountryCodeAdapter.isThailand("TH");

        assertTrue(result);
    }

    @Test
    @DisplayName("isThailand should return true for lowercase th")
    public void testIsThailandLowerCase() {
        boolean result = ISOCountryCodeAdapter.isThailand("th");

        assertTrue(result);
    }

    @Test
    @DisplayName("isThailand should return true for mixed case")
    public void testIsThailandMixedCase() {
        boolean result = ISOCountryCodeAdapter.isThailand("Th");

        assertTrue(result);
    }

    @Test
    @DisplayName("isThailand should return false for non-TH")
    public void testIsThailandNonThailand() {
        boolean result = ISOCountryCodeAdapter.isThailand("US");

        assertFalse(result);
    }

    @Test
    @DisplayName("isThailand should return false for null")
    public void testIsThailandNull() {
        boolean result = ISOCountryCodeAdapter.isThailand(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("isThailand should trim whitespace")
    public void testIsThailandWhitespace() {
        boolean result = ISOCountryCodeAdapter.isThailand("  TH  ");

        assertTrue(result);
    }

    @Test
    @DisplayName("isASEANCountry should return true for ASEAN countries")
    public void testIsASEANCountryValid() {
        String[] aseanCountries = {"TH", "SG", "MY", "BN", "KH", "ID", "LA", "MM", "PH", "VN"};

        for (String code : aseanCountries) {
            ISOCountryCode country = new ISOCountryCode(code, "Name");
            country.setCode(code);
            when(repository.findByCode(code)).thenReturn(Optional.of(country));

            boolean result = ISOCountryCodeAdapter.isASEANCountry(code);

            assertTrue(result, "Expected " + code + " to be ASEAN country");
        }
    }

    @Test
    @DisplayName("isASEANCountry should return false for non-ASEAN")
    public void testIsASEANCountryNonASEAN() {
        when(repository.findByCode("US")).thenReturn(Optional.of(unitedStates));

        boolean result = ISOCountryCodeAdapter.isASEANCountry("US");

        assertFalse(result);
    }

    @Test
    @DisplayName("isASEANCountry should return false for null")
    public void testIsASEANCountryNull() {
        boolean result = ISOCountryCodeAdapter.isASEANCountry(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("isASEANCountry should return false when repository is null")
    public void testIsASEANCountryRepositoryNull() {
        new ISOCountryCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOCountryCodeAdapter.isASEANCountry("TH");

        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorTradingPartner should return true for trading partners")
    public void testIsMajorTradingPartnerValid() {
        String[] partners = {"CN", "JP", "KR", "US", "GB", "DE", "AU", "IN", "TW", "HK", "SG"};

        for (String code : partners) {
            ISOCountryCode country = new ISOCountryCode(code, "Name");
            country.setCode(code);
            when(repository.findByCode(code)).thenReturn(Optional.of(country));

            boolean result = ISOCountryCodeAdapter.isMajorTradingPartner(code);

            assertTrue(result, "Expected " + code + " to be major trading partner");
        }
    }

    @Test
    @DisplayName("isMajorTradingPartner should return false for non-partner")
    public void testIsMajorTradingPartnerNonPartner() {
        when(repository.findByCode("ZZ")).thenReturn(Optional.empty());

        boolean result = ISOCountryCodeAdapter.isMajorTradingPartner("ZZ");

        assertFalse(result);
    }

    @Test
    @DisplayName("isETDAExtension should return true for ETDA extensions")
    public void testIsETDAExtensionValid() {
        ISOCountryCode etdaExtension = new ISOCountryCode("AN", "ETDA Extension");
        etdaExtension.setEtdaExtension(true);
        when(repository.findByCode("AN")).thenReturn(Optional.of(etdaExtension));

        boolean result = ISOCountryCodeAdapter.isETDAExtension("AN");

        assertTrue(result);
    }

    @Test
    @DisplayName("isETDAExtension should return false for non-ETDA")
    public void testIsETDAExtensionNonETDA() {
        when(repository.findByCode("US")).thenReturn(Optional.of(unitedStates));

        boolean result = ISOCountryCodeAdapter.isETDAExtension("US");

        assertFalse(result);
    }

    @Test
    @DisplayName("isETDAExtension should return false for null")
    public void testIsETDAExtensionNull() {
        boolean result = ISOCountryCodeAdapter.isETDAExtension(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("isChina should return true for CN")
    public void testIsChinaValidCode() {
        boolean result = ISOCountryCodeAdapter.isChina("CN");

        assertTrue(result);
    }

    @Test
    @DisplayName("isChina should return true for lowercase cn")
    public void testIsChinaLowerCase() {
        boolean result = ISOCountryCodeAdapter.isChina("cn");

        assertTrue(result);
    }

    @Test
    @DisplayName("isChina should return false for non-CN")
    public void testIsChinaNonChina() {
        boolean result = ISOCountryCodeAdapter.isChina("US");

        assertFalse(result);
    }

    @Test
    @DisplayName("isChina should return false for null")
    public void testIsChinaNull() {
        boolean result = ISOCountryCodeAdapter.isChina(null);

        assertFalse(result);
    }

    @Test
    @DisplayName("isJapan should return true for JP")
    public void testIsJapanValidCode() {
        boolean result = ISOCountryCodeAdapter.isJapan("JP");

        assertTrue(result);
    }

    @Test
    @DisplayName("isJapan should return false for non-JP")
    public void testIsJapanNonJapan() {
        boolean result = ISOCountryCodeAdapter.isJapan("US");

        assertFalse(result);
    }

    @Test
    @DisplayName("isUnitedStates should return true for US")
    public void testIsUnitedStatesValidCode() {
        boolean result = ISOCountryCodeAdapter.isUnitedStates("US");

        assertTrue(result);
    }

    @Test
    @DisplayName("isUnitedStates should return false for non-US")
    public void testIsUnitedStatesNonUS() {
        boolean result = ISOCountryCodeAdapter.isUnitedStates("TH");

        assertFalse(result);
    }

    @Test
    @DisplayName("isSingapore should return true for SG")
    public void testIsSingaporeValidCode() {
        boolean result = ISOCountryCodeAdapter.isSingapore("SG");

        assertTrue(result);
    }

    @Test
    @DisplayName("isSingapore should return false for non-SG")
    public void testIsSingaporeNonSingapore() {
        boolean result = ISOCountryCodeAdapter.isSingapore("US");

        assertFalse(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        String marshaled = adapter.marshal(thailand);
        ISOCountryCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(thailand.getCode(), unmarshaled.getCode());
        assertEquals(thailand.getName(), unmarshaled.getName());
    }

    @Test
    @DisplayName("Should round-trip with case changes")
    public void testRoundTripCaseChange() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));

        String marshaled = adapter.marshal(thailand);
        ISOCountryCode unmarshaled = adapter.unmarshal(marshaled.toLowerCase());

        assertEquals(thailand.getCode(), unmarshaled.getCode());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOCountryCode result = adapter.unmarshal("XX");

        assertEquals("XX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOCountryCode result = adapter.unmarshal("XX");

        assertTrue(result.getName().contains("Unknown"));
        assertTrue(result.getName().contains("XX"));
    }

    @Test
    @DisplayName("Placeholder should have active false")
    public void testPlaceholderActive() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOCountryCode result = adapter.unmarshal("XX");

        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Placeholder should have description")
    public void testPlaceholderDescription() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOCountryCode result = adapter.unmarshal("XX");

        assertNotNull(result.getDescription());
        assertTrue(result.getDescription().contains("placeholder") ||
                   result.getDescription().contains("Placeholder"));
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCode(anyString())).thenReturn(Optional.empty());

        ISOCountryCode result = adapter.unmarshal("XX");

        assertNotNull(result);
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thailand));
        when(repository.findByCode("US")).thenReturn(Optional.of(unitedStates));

        ISOCountryCode result1 = adapter.unmarshal("TH");
        ISOCountryCode result2 = adapter.unmarshal("US");

        assertEquals("TH", result1.getCode());
        assertEquals("US", result2.getCode());
        verify(repository, times(2)).findByCode(anyString());
    }
}
