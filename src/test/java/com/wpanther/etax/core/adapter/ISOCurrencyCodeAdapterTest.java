package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ISOCurrencyCodeAdapter;
import com.wpanther.etax.core.entity.ISOCurrencyCode;
import com.wpanther.etax.core.repository.ISOCurrencyCodeRepository;
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
@DisplayName("ISOCurrencyCode Adapter Tests")
public class ISOCurrencyCodeAdapterTest {

    @Mock
    private ISOCurrencyCodeRepository repository;

    @InjectMocks
    private ISOCurrencyCodeAdapter adapter;

    private ISOCurrencyCode thaiBaht;
    private ISOCurrencyCode usDollar;
    private ISOCurrencyCode euro;
    private ISOCurrencyCode japaneseYen;

    @BeforeEach
    public void setUp() {
        new ISOCurrencyCodeAdapter();
        adapter.setRepository(repository);

        thaiBaht = new ISOCurrencyCode("THB", "Thai Baht", "764", 2);
        thaiBaht.setActive(true);

        usDollar = new ISOCurrencyCode("USD", "US Dollar", "840", 2);
        usDollar.setActive(true);

        euro = new ISOCurrencyCode("EUR", "Euro", "978", 2);
        euro.setActive(true);

        japaneseYen = new ISOCurrencyCode("JPY", "Japanese Yen", "392", 0);
        japaneseYen.setActive(true);
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(thaiBaht);
        assertEquals("THB", result);
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
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with whitespace code to null")
    public void testMarshalWhitespaceCode() throws Exception {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setCode("   ");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        ISOCurrencyCode result = adapter.unmarshal("THB");

        assertNotNull(result);
        assertEquals("THB", result.getCode());
        assertEquals("Thai Baht", result.getName());
        verify(repository).findByCode("THB");
    }

    @Test
    @DisplayName("Should unmarshal code case-insensitively")
    public void testUnmarshalCaseInsensitive() throws Exception {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        ISOCurrencyCode result = adapter.unmarshal("thb");

        assertNotNull(result);
        assertEquals("THB", result.getCode());
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("USD")).thenReturn(Optional.of(usDollar));

        ISOCurrencyCode result = adapter.unmarshal("  usd  ");

        assertNotNull(result);
        assertEquals("USD", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ISOCurrencyCode result = adapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertFalse(result.isActive());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ISOCurrencyCode result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        ISOCurrencyCode result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        ISOCurrencyCode result = adapter.unmarshal("   ");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        ISOCurrencyCodeAdapter nullRepoAdapter = new ISOCurrencyCodeAdapter();
        nullRepoAdapter.setRepository(null);

        ISOCurrencyCode result = nullRepoAdapter.unmarshal("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertFalse(result.isActive());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("toEntity should return entity for valid code")
    public void testToEntityValidCode() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        ISOCurrencyCode result = ISOCurrencyCodeAdapter.toEntity("THB");

        assertNotNull(result);
        assertEquals("THB", result.getCode());
    }

    @Test
    @DisplayName("toEntity should return null for null code")
    public void testToEntityNullCode() {
        ISOCurrencyCode result = ISOCurrencyCodeAdapter.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("toEntity should return placeholder for invalid code")
    public void testToEntityInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ISOCurrencyCode result = ISOCurrencyCodeAdapter.toEntity("XXX");

        assertNotNull(result);
        assertEquals("XXX", result.getCode());
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("toCode should return code for valid entity")
    public void testToCodeValidEntity() {
        String result = ISOCurrencyCodeAdapter.toCode(thaiBaht);
        assertEquals("THB", result);
    }

    @Test
    @DisplayName("toCode should return null for null entity")
    public void testToCodeNullEntity() {
        String result = ISOCurrencyCodeAdapter.toCode(null);
        assertNull(result);
    }

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        boolean result = ISOCurrencyCodeAdapter.isValid("THB");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        boolean result = ISOCurrencyCodeAdapter.isValid("XXX");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = ISOCurrencyCodeAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new ISOCurrencyCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOCurrencyCodeAdapter.isValid("THB");

        assertFalse(result);
    }

    @Test
    @DisplayName("getName should return name for valid code")
    public void testGetNameValidCode() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        String result = ISOCurrencyCodeAdapter.getName("THB");

        assertEquals("Thai Baht", result);
    }

    @Test
    @DisplayName("getName should return null for invalid code")
    public void testGetNameInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        String result = ISOCurrencyCodeAdapter.getName("XXX");

        assertNull(result);
    }

    @Test
    @DisplayName("getName should return null for null code")
    public void testGetNameNullCode() {
        String result = ISOCurrencyCodeAdapter.getName(null);
        assertNull(result);
    }

    @Test
    @DisplayName("getNumericCode should return numeric code for valid currency")
    public void testGetNumericCodeValidCode() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        String result = ISOCurrencyCodeAdapter.getNumericCode("THB");

        assertEquals("764", result);
    }

    @Test
    @DisplayName("getNumericCode should return null for invalid code")
    public void testGetNumericCodeInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        String result = ISOCurrencyCodeAdapter.getNumericCode("XXX");

        assertNull(result);
    }

    @Test
    @DisplayName("getNumericCode should return null for null code")
    public void testGetNumericCodeNullCode() {
        String result = ISOCurrencyCodeAdapter.getNumericCode(null);
        assertNull(result);
    }

    @Test
    @DisplayName("getMinorUnits should return minor units for valid currency")
    public void testGetMinorUnitsValidCode() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        Integer result = ISOCurrencyCodeAdapter.getMinorUnits("THB");

        assertEquals(2, result);
    }

    @Test
    @DisplayName("getMinorUnits should return 2 for invalid code")
    public void testGetMinorUnitsInvalidCode() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        Integer result = ISOCurrencyCodeAdapter.getMinorUnits("XXX");

        assertEquals(2, result);
    }

    @Test
    @DisplayName("getMinorUnits should return 2 for null code")
    public void testGetMinorUnitsNullCode() {
        Integer result = ISOCurrencyCodeAdapter.getMinorUnits(null);
        assertEquals(2, result);
    }

    @Test
    @DisplayName("getMinorUnits should return 0 for JPY")
    public void testGetMinorUnitsJPY() {
        when(repository.findByCode("JPY")).thenReturn(Optional.of(japaneseYen));

        Integer result = ISOCurrencyCodeAdapter.getMinorUnits("JPY");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("normalize should return uppercase code")
    public void testNormalize() {
        String result = ISOCurrencyCodeAdapter.normalize("thb");
        assertEquals("THB", result);
    }

    @Test
    @DisplayName("normalize should trim whitespace")
    public void testNormalizeTrim() {
        String result = ISOCurrencyCodeAdapter.normalize("  thb  ");
        assertEquals("THB", result);
    }

    @Test
    @DisplayName("normalize should return null for null input")
    public void testNormalizeNull() {
        String result = ISOCurrencyCodeAdapter.normalize(null);
        assertNull(result);
    }

    @Test
    @DisplayName("normalize should return null for empty input")
    public void testNormalizeEmpty() {
        String result = ISOCurrencyCodeAdapter.normalize("");
        assertNull(result);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isThaiBasht should return true for THB")
    public void testIsThaiBashtValidCode() {
        boolean result = ISOCurrencyCodeAdapter.isThaiBasht("THB");
        assertTrue(result);
    }

    @Test
    @DisplayName("isThaiBasht should return true for lowercase")
    public void testIsThaiBashtLowerCase() {
        boolean result = ISOCurrencyCodeAdapter.isThaiBasht("thb");
        assertTrue(result);
    }

    @Test
    @DisplayName("isThaiBasht should return false for non-THB")
    public void testIsThaiBashtNonTHB() {
        boolean result = ISOCurrencyCodeAdapter.isThaiBasht("USD");
        assertFalse(result);
    }

    @Test
    @DisplayName("isThaiBasht should return false for null")
    public void testIsThaiBashtNull() {
        boolean result = ISOCurrencyCodeAdapter.isThaiBasht(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isUSDollar should return true for USD")
    public void testIsUSDollarValidCode() {
        boolean result = ISOCurrencyCodeAdapter.isUSDollar("USD");
        assertTrue(result);
    }

    @Test
    @DisplayName("isUSDollar should return false for non-USD")
    public void testIsUSDollarNonUSD() {
        boolean result = ISOCurrencyCodeAdapter.isUSDollar("THB");
        assertFalse(result);
    }

    @Test
    @DisplayName("isUSDollar should return false for null")
    public void testIsUSDollarNull() {
        boolean result = ISOCurrencyCodeAdapter.isUSDollar(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isEuro should return true for EUR")
    public void testIsEuroValidCode() {
        boolean result = ISOCurrencyCodeAdapter.isEuro("EUR");
        assertTrue(result);
    }

    @Test
    @DisplayName("isEuro should return false for non-EUR")
    public void testIsEuroNonEUR() {
        boolean result = ISOCurrencyCodeAdapter.isEuro("USD");
        assertFalse(result);
    }

    @Test
    @DisplayName("isJapaneseYen should return true for JPY")
    public void testIsJapaneseYenValidCode() {
        boolean result = ISOCurrencyCodeAdapter.isJapaneseYen("JPY");
        assertTrue(result);
    }

    @Test
    @DisplayName("isJapaneseYen should return false for non-JPY")
    public void testIsJapaneseYenNonJPY() {
        boolean result = ISOCurrencyCodeAdapter.isJapaneseYen("USD");
        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorCurrency should return true for major currencies")
    public void testIsMajorCurrencyValid() {
        when(repository.findByCode("USD")).thenReturn(Optional.of(usDollar));
        when(repository.findByCode("EUR")).thenReturn(Optional.of(euro));
        when(repository.findByCode("JPY")).thenReturn(Optional.of(japaneseYen));

        assertTrue(ISOCurrencyCodeAdapter.isMajorCurrency("USD"));
        assertTrue(ISOCurrencyCodeAdapter.isMajorCurrency("EUR"));
        assertTrue(ISOCurrencyCodeAdapter.isMajorCurrency("JPY"));
    }

    @Test
    @DisplayName("isMajorCurrency should return false for non-major currency")
    public void testIsMajorCurrencyNonMajor() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        boolean result = ISOCurrencyCodeAdapter.isMajorCurrency("THB");

        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorCurrency should return false for null")
    public void testIsMajorCurrencyNull() {
        boolean result = ISOCurrencyCodeAdapter.isMajorCurrency(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorCurrency should return false when repository is null")
    public void testIsMajorCurrencyRepositoryNull() {
        new ISOCurrencyCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOCurrencyCodeAdapter.isMajorCurrency("USD");

        assertFalse(result);
    }

    @Test
    @DisplayName("isASEANCurrency should return true for ASEAN currencies")
    public void testIsASEANCurrencyValid() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));
        when(repository.findByCode("SGD")).thenReturn(Optional.of(new ISOCurrencyCode("SGD", "Singapore Dollar")));

        assertTrue(ISOCurrencyCodeAdapter.isASEANCurrency("THB"));
        assertTrue(ISOCurrencyCodeAdapter.isASEANCurrency("SGD"));
    }

    @Test
    @DisplayName("isASEANCurrency should return false for non-ASEAN currency")
    public void testIsASEANCurrencyNonASEAN() {
        when(repository.findByCode("USD")).thenReturn(Optional.of(usDollar));

        boolean result = ISOCurrencyCodeAdapter.isASEANCurrency("USD");

        assertFalse(result);
    }

    @Test
    @DisplayName("hasNoDecimalPlaces should return true for JPY")
    public void testHasNoDecimalPlacesValid() {
        when(repository.findByCode("JPY")).thenReturn(Optional.of(japaneseYen));

        boolean result = ISOCurrencyCodeAdapter.hasNoDecimalPlaces("JPY");

        assertTrue(result);
    }

    @Test
    @DisplayName("hasNoDecimalPlaces should return false for THB")
    public void testHasNoDecimalPlacesFalse() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        boolean result = ISOCurrencyCodeAdapter.hasNoDecimalPlaces("THB");

        assertFalse(result);
    }

    @Test
    @DisplayName("hasNoDecimalPlaces should return false for null")
    public void testHasNoDecimalPlacesNull() {
        boolean result = ISOCurrencyCodeAdapter.hasNoDecimalPlaces(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("formatAmount should format with currency code")
    public void testFormatAmountValid() {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        String result = ISOCurrencyCodeAdapter.formatAmount(1234.56, "THB");

        assertEquals("1,234.56 THB", result);
    }

    @Test
    @DisplayName("formatAmount should format JPY without decimals")
    public void testFormatAmountJPY() {
        when(repository.findByCode("JPY")).thenReturn(Optional.of(japaneseYen));

        String result = ISOCurrencyCodeAdapter.formatAmount(1234.0, "JPY");

        assertEquals("1,234 JPY", result);
    }

    @Test
    @DisplayName("formatAmount should default to 2 decimals for unknown currency")
    public void testFormatAmountUnknown() {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        String result = ISOCurrencyCodeAdapter.formatAmount(1234.56, "XXX");

        assertEquals("1,234.56 XXX", result);
    }

    @Test
    @DisplayName("formatAmount should default to 2 decimals for null repository")
    public void testFormatAmountRepositoryNull() {
        new ISOCurrencyCodeAdapter();
        adapter.setRepository(null);

        String result = ISOCurrencyCodeAdapter.formatAmount(1234.56, "XXX");

        assertEquals("1,234.56 XXX", result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("THB")).thenReturn(Optional.of(thaiBaht));

        String marshaled = adapter.marshal(thaiBaht);
        ISOCurrencyCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(thaiBaht.getCode(), unmarshaled.getCode());
        assertEquals(thaiBaht.getName(), unmarshaled.getName());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ISOCurrencyCode result = adapter.unmarshal("XXX");

        assertEquals("XXX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ISOCurrencyCode result = adapter.unmarshal("XXX");

        assertTrue(result.getName().contains("Unknown"));
        assertTrue(result.getName().contains("XXX"));
    }

    @Test
    @DisplayName("Placeholder should have active false")
    public void testPlaceholderActive() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ISOCurrencyCode result = adapter.unmarshal("XXX");

        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Placeholder should default to 2 minor units")
    public void testPlaceholderMinorUnits() throws Exception {
        when(repository.findByCode("XXX")).thenReturn(Optional.empty());

        ISOCurrencyCode result = adapter.unmarshal("XXX");

        assertEquals(2, result.getMinorUnits());
    }
}
