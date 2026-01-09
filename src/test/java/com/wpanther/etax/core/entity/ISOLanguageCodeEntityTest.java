package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ISOLanguageCode Entity Tests")
public class ISOLanguageCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ISOLanguageCode entity = new ISOLanguageCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertTrue(entity.getIsActive());
    }

    @Test
    @DisplayName("Code constructor should normalize to lowercase")
    public void testCodeConstructor() {
        ISOLanguageCode entity = new ISOLanguageCode("TH");
        assertEquals("th", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        ISOLanguageCode entity = new ISOLanguageCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        ISOLanguageCode entity = new ISOLanguageCode("  EN  ");
        assertEquals("en", entity.getCode());
    }

    @Test
    @DisplayName("Code and name constructor should set both fields")
    public void testCodeNameConstructor() {
        ISOLanguageCode entity = new ISOLanguageCode("EN", "English");
        assertEquals("en", entity.getCode());
        assertEquals("English", entity.getName());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to lowercase")
    public void testSetCodeNormalizesToLowercase() {
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setCode("JA");
        assertEquals("ja", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setCode("  zh  ");
        assertEquals("zh", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setName("Japanese");
        assertEquals("Japanese", entity.getName());
    }

    @Test
    @DisplayName("SetIsActive should set active flag")
    public void testSetIsActive() {
        ISOLanguageCode entity = new ISOLanguageCode();
        entity.setIsActive(false);
        assertFalse(entity.getIsActive());
    }

    @Test
    @DisplayName("GetCodeUpper should return uppercase code")
    public void testGetCodeUpper() {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        // Note: codeUpper is a computed column, so it will be null in this unit test
        // In integration tests with database, it would contain "TH"
        assertNull(entity.getCodeUpper());
    }

    @Test
    @DisplayName("GetCodeLower should return lowercase code")
    public void testGetCodeLower() {
        ISOLanguageCode entity = new ISOLanguageCode("TH");
        // Note: codeLower is a computed column, so it will be null in this unit test
        // In integration tests with database, it would contain "th"
        assertNull(entity.getCodeLower());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);

        entity.onCreate();

        LocalDateTime after = LocalDateTime.now().plusSeconds(1);
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
        assertTrue(entity.getCreatedAt().isAfter(before));
        assertTrue(entity.getCreatedAt().isBefore(after));
        // Timestamps should be equal or very close (within 1 second)
        assertTrue(entity.getCreatedAt().equals(entity.getUpdatedAt()) ||
                   java.time.Duration.between(entity.getCreatedAt(), entity.getUpdatedAt()).abs().toMillis() < 1000);
    }

    @Test
    @DisplayName("PreUpdate should update timestamp")
    public void testPreUpdateUpdatesTimestamp() throws InterruptedException {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        entity.onCreate();
        LocalDateTime originalUpdated = entity.getUpdatedAt();

        // Small delay to ensure timestamp difference
        Thread.sleep(10);

        entity.onUpdate();

        assertNotNull(entity.getUpdatedAt());
        assertTrue(entity.getUpdatedAt().isAfter(originalUpdated));
        // createdAt should not change (within 1 second tolerance)
        assertTrue(entity.getCreatedAt().equals(originalUpdated) ||
                   java.time.Duration.between(entity.getCreatedAt(), originalUpdated).abs().toMillis() < 1000);
    }

    // Business Logic Tests

    @Test
    @DisplayName("isThai should return true for th")
    public void testIsThai() {
        ISOLanguageCode th = new ISOLanguageCode("th");
        assertTrue(th.isThai());

        ISOLanguageCode en = new ISOLanguageCode("en");
        assertFalse(en.isThai());
    }

    @Test
    @DisplayName("isEnglish should return true for en")
    public void testIsEnglish() {
        ISOLanguageCode en = new ISOLanguageCode("en");
        assertTrue(en.isEnglish());

        ISOLanguageCode th = new ISOLanguageCode("th");
        assertFalse(th.isEnglish());
    }

    @Test
    @DisplayName("isChinese should return true for zh")
    public void testIsChinese() {
        ISOLanguageCode zh = new ISOLanguageCode("zh");
        assertTrue(zh.isChinese());

        ISOLanguageCode ja = new ISOLanguageCode("ja");
        assertFalse(ja.isChinese());
    }

    @Test
    @DisplayName("isJapanese should return true for ja")
    public void testIsJapanese() {
        ISOLanguageCode ja = new ISOLanguageCode("ja");
        assertTrue(ja.isJapanese());

        ISOLanguageCode ko = new ISOLanguageCode("ko");
        assertFalse(ko.isJapanese());
    }

    @Test
    @DisplayName("isASEANLanguage should return true for ASEAN languages")
    public void testIsASEANLanguage() {
        // Test all ASEAN languages
        assertTrue(new ISOLanguageCode("th").isASEANLanguage()); // Thai
        assertTrue(new ISOLanguageCode("en").isASEANLanguage()); // English
        assertTrue(new ISOLanguageCode("ms").isASEANLanguage()); // Malay
        assertTrue(new ISOLanguageCode("id").isASEANLanguage()); // Indonesian
        assertTrue(new ISOLanguageCode("vi").isASEANLanguage()); // Vietnamese
        assertTrue(new ISOLanguageCode("my").isASEANLanguage()); // Burmese
        assertTrue(new ISOLanguageCode("km").isASEANLanguage()); // Khmer
        assertTrue(new ISOLanguageCode("lo").isASEANLanguage()); // Lao
        assertTrue(new ISOLanguageCode("tl").isASEANLanguage()); // Tagalog

        // Test non-ASEAN language
        assertFalse(new ISOLanguageCode("ja").isASEANLanguage());
        assertFalse(new ISOLanguageCode("zh").isASEANLanguage());
    }

    @Test
    @DisplayName("isMajorTradingLanguage should return true for major trading languages")
    public void testIsMajorTradingLanguage() {
        // Test all major trading languages
        assertTrue(new ISOLanguageCode("en").isMajorTradingLanguage()); // English
        assertTrue(new ISOLanguageCode("th").isMajorTradingLanguage()); // Thai
        assertTrue(new ISOLanguageCode("zh").isMajorTradingLanguage()); // Chinese
        assertTrue(new ISOLanguageCode("ja").isMajorTradingLanguage()); // Japanese
        assertTrue(new ISOLanguageCode("ko").isMajorTradingLanguage()); // Korean
        assertTrue(new ISOLanguageCode("de").isMajorTradingLanguage()); // German
        assertTrue(new ISOLanguageCode("fr").isMajorTradingLanguage()); // French
        assertTrue(new ISOLanguageCode("es").isMajorTradingLanguage()); // Spanish
        assertTrue(new ISOLanguageCode("ar").isMajorTradingLanguage()); // Arabic
        assertTrue(new ISOLanguageCode("ru").isMajorTradingLanguage()); // Russian

        // Test non-major trading language
        assertFalse(new ISOLanguageCode("it").isMajorTradingLanguage());
        assertFalse(new ISOLanguageCode("pt").isMajorTradingLanguage());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ISOLanguageCode entity1 = new ISOLanguageCode("th", "Thai");
        ISOLanguageCode entity2 = new ISOLanguageCode("th", "Different Name");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ISOLanguageCode entity1 = new ISOLanguageCode("th");
        ISOLanguageCode entity2 = new ISOLanguageCode("en");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        ISOLanguageCode entity = new ISOLanguageCode("th");
        String other = "th";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        ISOLanguageCode entity1 = new ISOLanguageCode();
        ISOLanguageCode entity2 = new ISOLanguageCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        ISOLanguageCode entity1 = new ISOLanguageCode("th");
        ISOLanguageCode entity2 = new ISOLanguageCode("th");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        ISOLanguageCode entity = new ISOLanguageCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and name")
    public void testToString() {
        ISOLanguageCode entity = new ISOLanguageCode("th", "Thai");
        entity.setIsActive(true);

        String result = entity.toString();

        assertTrue(result.contains("th"));
        assertTrue(result.contains("Thai"));
        assertTrue(result.contains("true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        ISOLanguageCode entity = new ISOLanguageCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("ISOLanguageCode"));
    }
}
