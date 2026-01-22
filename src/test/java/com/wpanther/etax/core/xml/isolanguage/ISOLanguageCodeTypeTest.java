package com.wpanther.etax.core.xml.isolanguage;

import com.wpanther.etax.core.entity.ISOLanguageCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ISOLanguageCodeType.
 */
@DisplayName("ISOLanguageCodeType Tests")
class ISOLanguageCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("TH");
        assertThat(type.getName()).isEqualTo("Thai");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ISOLanguageCodeType type = new ISOLanguageCodeType("en");

        assertThat(type.getCode()).isEqualTo("EN");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ISOLanguageCodeType type = ISOLanguageCodeType.of("zh");

        assertThat(type.getCode()).isEqualTo("ZH");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ISOLanguageCode entity = new ISOLanguageCode("JA", "Japanese");
        ISOLanguageCodeType type = ISOLanguageCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("thai() should create Thai language instance")
    void testThaiFactory() {
        ISOLanguageCodeType type = ISOLanguageCodeType.thai();

        assertThat(type.getCode()).isEqualTo("TH");
    }

    @Test
    @DisplayName("english() should create English language instance")
    void testEnglishFactory() {
        ISOLanguageCodeType type = ISOLanguageCodeType.english();

        assertThat(type.getCode()).isEqualTo("EN");
    }

    @Test
    @DisplayName("chinese() should create Chinese language instance")
    void testChineseFactory() {
        ISOLanguageCodeType type = ISOLanguageCodeType.chinese();

        assertThat(type.getCode()).isEqualTo("ZH");
    }

    @Test
    @DisplayName("japanese() should create Japanese language instance")
    void testJapaneseFactory() {
        ISOLanguageCodeType type = ISOLanguageCodeType.japanese();

        assertThat(type.getCode()).isEqualTo("JA");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isThai() should return false when value is null")
    void testIsThaiWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isThai()).isFalse();
    }

    @Test
    @DisplayName("isEnglish() should return false when value is null")
    void testIsEnglishWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isEnglish()).isFalse();
    }

    @Test
    @DisplayName("isChinese() should return false when value is null")
    void testIsChineseWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isChinese()).isFalse();
    }

    @Test
    @DisplayName("isJapanese() should return false when value is null")
    void testIsJapaneseWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isJapanese()).isFalse();
    }

    @Test
    @DisplayName("isASEANLanguage() should return false when value is null")
    void testIsASEANLanguageWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isASEANLanguage()).isFalse();
    }

    @Test
    @DisplayName("isMajorTradingLanguage() should return false when value is null")
    void testIsMajorTradingLanguageWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isMajorTradingLanguage()).isFalse();
    }

    @Test
    @DisplayName("isActive() should return false when value is null")
    void testIsActiveWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.isActive()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isThai() should return true for TH")
    void testIsThaiTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isThai()).isTrue();
    }

    @Test
    @DisplayName("isEnglish() should return true for EN")
    void testIsEnglishTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("EN", "English");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isEnglish()).isTrue();
    }

    @Test
    @DisplayName("isChinese() should return true for ZH")
    void testIsChineseTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("ZH", "Chinese");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isChinese()).isTrue();
    }

    @Test
    @DisplayName("isJapanese() should return true for JA")
    void testIsJapaneseTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("JA", "Japanese");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isJapanese()).isTrue();
    }

    @Test
    @DisplayName("isASEANLanguage() should return true for Thai")
    void testIsASEANLanguageTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isASEANLanguage()).isTrue();
    }

    @Test
    @DisplayName("isMajorTradingLanguage() should return true for Chinese")
    void testIsMajorTradingLanguageTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("ZH", "Chinese");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isMajorTradingLanguage()).isTrue();
    }

    @Test
    @DisplayName("isThai() should return false for non-TH code")
    void testIsThaiFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("EN", "English");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isThai()).isFalse();
    }

    @Test
    @DisplayName("isEnglish() should return false for non-EN code")
    void testIsEnglishFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isEnglish()).isFalse();
    }

    @Test
    @DisplayName("isChinese() should return false for non-ZH code")
    void testIsChineseFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("EN", "English");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isChinese()).isFalse();
    }

    @Test
    @DisplayName("isJapanese() should return false for non-JA code")
    void testIsJapaneseFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("EN", "English");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isJapanese()).isFalse();
    }

    @Test
    @DisplayName("isASEANLanguage() should return false for non-ASEAN language")
    void testIsASEANLanguageFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("DE", "German");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isASEANLanguage()).isFalse();
    }

    @Test
    @DisplayName("isMajorTradingLanguage() should return false for non-major trading language")
    void testIsMajorTradingLanguageFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("HI", "Hindi");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isMajorTradingLanguage()).isFalse();
    }

    @Test
    @DisplayName("isActive() should return true when entity is active")
    void testIsActiveTrue() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        entity.setIsActive(true);
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isActive()).isTrue();
    }

    @Test
    @DisplayName("isActive() should return false when entity is inactive")
    void testIsActiveFalse() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        entity.setIsActive(false);
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.isActive()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ISOLanguageCode entity = new ISOLanguageCode("DE", "German");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();
        ISOLanguageCode entity = new ISOLanguageCode("FR", "French");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("FR");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type1 = new ISOLanguageCodeType(entity);
        ISOLanguageCodeType type2 = new ISOLanguageCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ISOLanguageCodeType type = new ISOLanguageCodeType("th");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ISOLanguageCodeType type = new ISOLanguageCodeType("th");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ISOLanguageCodeType type = new ISOLanguageCodeType("th");

        assertThat(type).isNotEqualTo("th");
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type1 = new ISOLanguageCodeType(entity);
        ISOLanguageCodeType type2 = new ISOLanguageCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should handle null value")
    void testHashCodeNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        int hashCode = type.hashCode();
        assertThat(hashCode).isNotNull();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return entity toString when value is not null")
    void testToStringWithValue() {
        ISOLanguageCode entity = new ISOLanguageCode("TH", "Thai");
        ISOLanguageCodeType type = new ISOLanguageCodeType(entity);

        assertThat(type.toString()).isEqualTo(entity.toString());
    }

    @Test
    @DisplayName("toString() should return null indicator when value is null")
    void testToStringWithNullValue() {
        ISOLanguageCodeType type = new ISOLanguageCodeType();

        assertThat(type.toString()).isEqualTo("ISOLanguageCodeType{null}");
    }
}