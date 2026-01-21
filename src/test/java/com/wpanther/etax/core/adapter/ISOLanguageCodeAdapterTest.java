package com.wpanther.etax.core.adapter;

import com.wpanther.etax.core.adapter.common.ISOLanguageCodeAdapter;
import com.wpanther.etax.core.entity.ISOLanguageCode;
import com.wpanther.etax.core.repository.ISOLanguageCodeRepository;
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
@DisplayName("ISOLanguageCode Adapter Tests")
public class ISOLanguageCodeAdapterTest {

    @Mock
    private ISOLanguageCodeRepository repository;

    @InjectMocks
    private ISOLanguageCodeAdapter adapter;

    private ISOLanguageCode thai;
    private ISOLanguageCode english;
    private ISOLanguageCode chinese;
    private ISOLanguageCode japanese;

    @BeforeEach
    public void setUp() {
        new ISOLanguageCodeAdapter();
        adapter.setRepository(repository);

        thai = new ISOLanguageCode("TH", "Thai");
        english = new ISOLanguageCode("EN", "English");
        chinese = new ISOLanguageCode("ZH", "Chinese");
        japanese = new ISOLanguageCode("JA", "Japanese");
    }

    // Marshal Tests

    @Test
    @DisplayName("Should marshal entity to code string")
    public void testMarshal() throws Exception {
        String result = adapter.marshal(thai);
        assertEquals("TH", result);
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
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setCode(null);
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with empty code to null")
    public void testMarshalEmptyCode() throws Exception {
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setCode("");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    @Test
    @DisplayName("Should marshal entity with whitespace code to null")
    public void testMarshalWhitespaceCode() throws Exception {
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setCode("   ");
        String result = adapter.marshal(entity);
        assertNull(result);
    }

    // Unmarshal Tests

    @Test
    @DisplayName("Should unmarshal valid code to entity")
    public void testUnmarshalValidCode() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thai));

        ISOLanguageCode result = adapter.unmarshal("th");

        assertNotNull(result);
        assertEquals("TH", result.getCode());
        assertEquals("Thai", result.getName());
        verify(repository).findByCode("TH");
    }

    @Test
    @DisplayName("Should unmarshal code case-insensitively")
    public void testUnmarshalCaseInsensitive() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thai));

        ISOLanguageCode result = adapter.unmarshal("TH");

        assertNotNull(result);
        assertEquals("TH", result.getCode());
        assertEquals("Thai", result.getName());
    }

    @Test
    @DisplayName("Should unmarshal code with whitespace")
    public void testUnmarshalWithWhitespace() throws Exception {
        when(repository.findByCode("EN")).thenReturn(Optional.of(english));

        ISOLanguageCode result = adapter.unmarshal("  en  ");

        assertNotNull(result);
        assertEquals("EN", result.getCode());
    }

    @Test
    @DisplayName("Should create placeholder for invalid code")
    public void testUnmarshalInvalidCode() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOLanguageCode result = adapter.unmarshal("xx");

        assertNotNull(result);
        assertEquals("XX", result.getCode());
        assertFalse(result.isActive());
        assertTrue(result.getName().contains("Unknown"));
    }

    @Test
    @DisplayName("Should unmarshal null code to null")
    public void testUnmarshalNullCode() throws Exception {
        ISOLanguageCode result = adapter.unmarshal(null);
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal empty code to null")
    public void testUnmarshalEmptyCode() throws Exception {
        ISOLanguageCode result = adapter.unmarshal("");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should unmarshal whitespace code to null")
    public void testUnmarshalWhitespaceCode() throws Exception {
        ISOLanguageCode result = adapter.unmarshal("   ");
        assertNull(result);
        verify(repository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Should create placeholder when repository is null")
    public void testUnmarshalRepositoryNull() throws Exception {
        ISOLanguageCodeAdapter nullRepoAdapter = new ISOLanguageCodeAdapter();
        nullRepoAdapter.setRepository(null);

        ISOLanguageCode result = nullRepoAdapter.unmarshal("xx");

        assertNotNull(result);
        assertEquals("XX", result.getCode());
        assertFalse(result.isActive());
    }

    // Static Helper Method Tests

    @Test
    @DisplayName("isValid should return true for valid code")
    public void testIsValidValidCode() {
        when(repository.existsByCode("TH")).thenReturn(true);

        boolean result = ISOLanguageCodeAdapter.isValid("th");

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid should return false for invalid code")
    public void testIsValidInvalidCode() {
        when(repository.existsByCode("XX")).thenReturn(false);

        boolean result = ISOLanguageCodeAdapter.isValid("xx");

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for null code")
    public void testIsValidNullCode() {
        boolean result = ISOLanguageCodeAdapter.isValid(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false for empty code")
    public void testIsValidEmptyCode() {
        boolean result = ISOLanguageCodeAdapter.isValid("");
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid should return false when repository is null")
    public void testIsValidRepositoryNull() {
        new ISOLanguageCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOLanguageCodeAdapter.isValid("th");

        assertFalse(result);
    }

    @Test
    @DisplayName("getName should return name for valid code")
    public void testGetNameValidCode() {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thai));

        String result = ISOLanguageCodeAdapter.getName("th");

        assertEquals("Thai", result);
    }

    @Test
    @DisplayName("getName should return null for invalid code")
    public void testGetNameInvalidCode() {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        String result = ISOLanguageCodeAdapter.getName("xx");

        assertNull(result);
    }

    @Test
    @DisplayName("getName should return null for null code")
    public void testGetNameNullCode() {
        String result = ISOLanguageCodeAdapter.getName(null);
        assertNull(result);
    }

    @Test
    @DisplayName("getName should return null for empty code")
    public void testGetNameEmptyCode() {
        String result = ISOLanguageCodeAdapter.getName("");
        assertNull(result);
    }

    @Test
    @DisplayName("normalize should return lowercase code")
    public void testNormalize() {
        String result = ISOLanguageCodeAdapter.normalize("TH");
        assertEquals("TH", result);
    }

    @Test
    @DisplayName("normalize should trim whitespace")
    public void testNormalizeTrim() {
        String result = ISOLanguageCodeAdapter.normalize("  th  ");
        assertEquals("TH", result);
    }

    @Test
    @DisplayName("normalize should return null for null input")
    public void testNormalizeNull() {
        String result = ISOLanguageCodeAdapter.normalize(null);
        assertNull(result);
    }

    @Test
    @DisplayName("normalize should return null for empty input")
    public void testNormalizeEmpty() {
        String result = ISOLanguageCodeAdapter.normalize("");
        assertNull(result);
    }

    @Test
    @DisplayName("normalize should return null for whitespace input")
    public void testNormalizeWhitespace() {
        String result = ISOLanguageCodeAdapter.normalize("   ");
        assertNull(result);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isThai should return true for th")
    public void testIsThaiValidCode() {
        boolean result = ISOLanguageCodeAdapter.isThai("th");
        assertTrue(result);
    }

    @Test
    @DisplayName("isThai should return true for uppercase TH")
    public void testIsThaiUpperCase() {
        boolean result = ISOLanguageCodeAdapter.isThai("TH");
        assertTrue(result);
    }

    @Test
    @DisplayName("isThai should return true for mixed case")
    public void testIsThaiMixedCase() {
        boolean result = ISOLanguageCodeAdapter.isThai("Th");
        assertTrue(result);
    }

    @Test
    @DisplayName("isThai should return false for non-th")
    public void testIsThaiNonThai() {
        boolean result = ISOLanguageCodeAdapter.isThai("en");
        assertFalse(result);
    }

    @Test
    @DisplayName("isThai should return false for null")
    public void testIsThaiNull() {
        boolean result = ISOLanguageCodeAdapter.isThai(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isThai should trim whitespace")
    public void testIsThaiWhitespace() {
        boolean result = ISOLanguageCodeAdapter.isThai("  th  ");
        assertTrue(result);
    }

    @Test
    @DisplayName("isEnglish should return true for en")
    public void testIsEnglishValidCode() {
        boolean result = ISOLanguageCodeAdapter.isEnglish("en");
        assertTrue(result);
    }

    @Test
    @DisplayName("isEnglish should return true for uppercase EN")
    public void testIsEnglishUpperCase() {
        boolean result = ISOLanguageCodeAdapter.isEnglish("EN");
        assertTrue(result);
    }

    @Test
    @DisplayName("isEnglish should return false for non-en")
    public void testIsEnglishNonEnglish() {
        boolean result = ISOLanguageCodeAdapter.isEnglish("th");
        assertFalse(result);
    }

    @Test
    @DisplayName("isEnglish should return false for null")
    public void testIsEnglishNull() {
        boolean result = ISOLanguageCodeAdapter.isEnglish(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isChinese should return true for zh")
    public void testIsChineseValidCode() {
        boolean result = ISOLanguageCodeAdapter.isChinese("zh");
        assertTrue(result);
    }

    @Test
    @DisplayName("isChinese should return false for non-zh")
    public void testIsChineseNonChinese() {
        boolean result = ISOLanguageCodeAdapter.isChinese("en");
        assertFalse(result);
    }

    @Test
    @DisplayName("isChinese should return false for null")
    public void testIsChineseNull() {
        boolean result = ISOLanguageCodeAdapter.isChinese(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isJapanese should return true for ja")
    public void testIsJapaneseValidCode() {
        boolean result = ISOLanguageCodeAdapter.isJapanese("ja");
        assertTrue(result);
    }

    @Test
    @DisplayName("isJapanese should return false for non-ja")
    public void testIsJapaneseNonJapanese() {
        boolean result = ISOLanguageCodeAdapter.isJapanese("en");
        assertFalse(result);
    }

    @Test
    @DisplayName("isJapanese should return false for null")
    public void testIsJapaneseNull() {
        boolean result = ISOLanguageCodeAdapter.isJapanese(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isASEANLanguage should return true for ASEAN languages")
    public void testIsASEANLanguageValid() {
        ISOLanguageCode thaiLang = new ISOLanguageCode("th", "Thai");
        ISOLanguageCode vietnamese = new ISOLanguageCode("vi", "Vietnamese");

        when(repository.findByCode("TH")).thenReturn(Optional.of(thaiLang));
        when(repository.findByCode("VI")).thenReturn(Optional.of(vietnamese));

        assertTrue(ISOLanguageCodeAdapter.isASEANLanguage("th"));
        assertTrue(ISOLanguageCodeAdapter.isASEANLanguage("vi"));
    }

    @Test
    @DisplayName("isASEANLanguage should return false for non-ASEAN language")
    public void testIsASEANLanguageNonASEAN() {
        when(repository.findByCode("JA")).thenReturn(Optional.of(japanese));

        boolean result = ISOLanguageCodeAdapter.isASEANLanguage("ja");

        assertFalse(result);
    }

    @Test
    @DisplayName("isASEANLanguage should return false for null")
    public void testIsASEANLanguageNull() {
        boolean result = ISOLanguageCodeAdapter.isASEANLanguage(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isASEANLanguage should return false when repository is null")
    public void testIsASEANLanguageRepositoryNull() {
        new ISOLanguageCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOLanguageCodeAdapter.isASEANLanguage("th");

        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorTradingLanguage should return true for major languages")
    public void testIsMajorTradingLanguageValid() {
        when(repository.findByCode("EN")).thenReturn(Optional.of(english));
        when(repository.findByCode("ZH")).thenReturn(Optional.of(chinese));
        when(repository.findByCode("JA")).thenReturn(Optional.of(japanese));

        assertTrue(ISOLanguageCodeAdapter.isMajorTradingLanguage("en"));
        assertTrue(ISOLanguageCodeAdapter.isMajorTradingLanguage("zh"));
        assertTrue(ISOLanguageCodeAdapter.isMajorTradingLanguage("ja"));
    }

    @Test
    @DisplayName("isMajorTradingLanguage should return false for non-major language")
    public void testIsMajorTradingLanguageNonMajor() {
        // Italian is not a major trading language
        ISOLanguageCode italian = new ISOLanguageCode("IT", "Italian");
        when(repository.findByCode("IT")).thenReturn(Optional.of(italian));

        boolean result = ISOLanguageCodeAdapter.isMajorTradingLanguage("it");

        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorTradingLanguage should return false for null")
    public void testIsMajorTradingLanguageNull() {
        boolean result = ISOLanguageCodeAdapter.isMajorTradingLanguage(null);
        assertFalse(result);
    }

    @Test
    @DisplayName("isMajorTradingLanguage should return false when repository is null")
    public void testIsMajorTradingLanguageRepositoryNull() {
        new ISOLanguageCodeAdapter();
        adapter.setRepository(null);

        boolean result = ISOLanguageCodeAdapter.isMajorTradingLanguage("en");

        assertFalse(result);
    }

    // Round-trip Tests

    @Test
    @DisplayName("Should round-trip through marshal and unmarshal")
    public void testRoundTrip() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thai));

        String marshaled = adapter.marshal(thai);
        ISOLanguageCode unmarshaled = adapter.unmarshal(marshaled);

        assertEquals(thai.getCode(), unmarshaled.getCode());
        assertEquals(thai.getName(), unmarshaled.getName());
    }

    @Test
    @DisplayName("Should round-trip with case changes")
    public void testRoundTripCaseChange() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thai));

        String marshaled = adapter.marshal(thai);
        ISOLanguageCode unmarshaled = adapter.unmarshal(marshaled.toUpperCase());

        assertEquals(thai.getCode(), unmarshaled.getCode());
    }

    // Placeholder Tests

    @Test
    @DisplayName("Placeholder should have correct code")
    public void testPlaceholderCode() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOLanguageCode result = adapter.unmarshal("xx");

        assertEquals("XX", result.getCode());
    }

    @Test
    @DisplayName("Placeholder should have generic name")
    public void testPlaceholderName() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOLanguageCode result = adapter.unmarshal("xx");

        assertTrue(result.getName().contains("Unknown"));
        assertTrue(result.getName().contains("XX"));
    }

    @Test
    @DisplayName("Placeholder should have active false")
    public void testPlaceholderActive() throws Exception {
        when(repository.findByCode("XX")).thenReturn(Optional.empty());

        ISOLanguageCode result = adapter.unmarshal("xx");

        assertFalse(result.isActive());
    }

    // Integration Tests

    @Test
    @DisplayName("Should work with repository returning empty")
    public void testRepositoryReturnsEmpty() throws Exception {
        when(repository.findByCode(anyString())).thenReturn(Optional.empty());

        ISOLanguageCode result = adapter.unmarshal("xx");

        assertNotNull(result);
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Should handle multiple sequential calls")
    public void testMultipleSequentialCalls() throws Exception {
        when(repository.findByCode("TH")).thenReturn(Optional.of(thai));
        when(repository.findByCode("EN")).thenReturn(Optional.of(english));

        ISOLanguageCode result1 = adapter.unmarshal("th");
        ISOLanguageCode result2 = adapter.unmarshal("en");

        assertEquals("TH", result1.getCode());
        assertEquals("EN", result2.getCode());
        verify(repository, times(2)).findByCode(anyString());
    }
}
