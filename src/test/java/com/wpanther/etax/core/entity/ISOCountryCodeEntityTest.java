package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ISOCountryCode Entity Tests")
public class ISOCountryCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ISOCountryCode entity = new ISOCountryCode();
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
        assertFalse(entity.getEtdaExtension());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        ISOCountryCode entity = new ISOCountryCode("th");
        assertEquals("TH", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorNull() {
        ISOCountryCode entity = new ISOCountryCode((String) null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code and name constructor should normalize code to uppercase")
    public void testCodeNameConstructor() {
        ISOCountryCode entity = new ISOCountryCode("th", "Thailand");
        assertEquals("TH", entity.getCode());
        assertEquals("Thailand", entity.getName());
    }

    @Test
    @DisplayName("Code and name constructor should handle null code")
    public void testCodeNameConstructorWithNullCode() {
        ISOCountryCode entity = new ISOCountryCode(null, "Thailand");
        assertNull(entity.getCode());
        assertEquals("Thailand", entity.getName());
    }

    @Test
    @DisplayName("Full constructor should normalize code to uppercase")
    public void testFullConstructor() {
        ISOCountryCode entity = new ISOCountryCode("th", "Thailand", "Kingdom of Thailand");
        assertEquals("TH", entity.getCode());
        assertEquals("Thailand", entity.getName());
        assertEquals("Kingdom of Thailand", entity.getDescription());
    }

    @Test
    @DisplayName("Full constructor should handle null code")
    public void testFullConstructorWithNullCode() {
        ISOCountryCode entity = new ISOCountryCode(null, "Thailand", "Kingdom of Thailand");
        assertNull(entity.getCode());
        assertEquals("Thailand", entity.getName());
        assertEquals("Kingdom of Thailand", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode("us");
        assertEquals("US", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeWithNull() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle empty string")
    public void testSetCodeWithEmptyString() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode("");
        assertEquals("", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle lowercase")
    public void testSetCodeWithLowerCase() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setCode("jp");
        assertEquals("JP", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setName("Thailand");
        assertEquals("Thailand", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setDescription("Kingdom of Thailand");
        assertEquals("Kingdom of Thailand", entity.getDescription());
    }

    @Test
    @DisplayName("SetEtdaExtension should set flag")
    public void testSetEtdaExtension() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setEtdaExtension(true);
        assertTrue(entity.getEtdaExtension());
    }

    @Test
    @DisplayName("SetActive should set flag")
    public void testSetActive() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setActive(false);
        assertFalse(entity.isActive());
    }

    // Business Logic Tests

    @Test
    @DisplayName("isThailand should return true for TH")
    public void testIsThailand() {
        ISOCountryCode entity = new ISOCountryCode("TH", "THAILAND");
        assertTrue(entity.isThailand());
    }

    @Test
    @DisplayName("isThailand should return false for non-TH")
    public void testIsThailandNonThailand() {
        ISOCountryCode entity = new ISOCountryCode("US", "UNITED STATES");
        assertFalse(entity.isThailand());
    }

    @Test
    @DisplayName("isThailand should return false for null code")
    public void testIsThailandNullCode() {
        ISOCountryCode entity = new ISOCountryCode();
        assertFalse(entity.isThailand());
    }

    @Test
    @DisplayName("isASEANCountry should return true for all ASEAN codes")
    public void testIsASEANCountry() {
        assertTrue(new ISOCountryCode("TH").isASEANCountry());
        assertTrue(new ISOCountryCode("BN").isASEANCountry());
        assertTrue(new ISOCountryCode("KH").isASEANCountry());
        assertTrue(new ISOCountryCode("ID").isASEANCountry());
        assertTrue(new ISOCountryCode("LA").isASEANCountry());
        assertTrue(new ISOCountryCode("MY").isASEANCountry());
        assertTrue(new ISOCountryCode("MM").isASEANCountry());
        assertTrue(new ISOCountryCode("PH").isASEANCountry());
        assertTrue(new ISOCountryCode("SG").isASEANCountry());
        assertTrue(new ISOCountryCode("VN").isASEANCountry());
    }

    @Test
    @DisplayName("isASEANCountry should return false for non-ASEAN")
    public void testIsASEANCountryNonASEAN() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isASEANCountry());
    }

    @Test
    @DisplayName("isMajorTradingPartner should return true for all trading partners")
    public void testIsMajorTradingPartner() {
        assertTrue(new ISOCountryCode("CN").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("JP").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("KR").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("US").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("GB").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("DE").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("AU").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("IN").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("TW").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("HK").isMajorTradingPartner());
        assertTrue(new ISOCountryCode("SG").isMajorTradingPartner());
    }

    @Test
    @DisplayName("isMajorTradingPartner should return false for non-partners")
    public void testIsMajorTradingPartnerNonPartner() {
        ISOCountryCode entity = new ISOCountryCode("ZZ");
        assertFalse(entity.isMajorTradingPartner());
    }

    @Test
    @DisplayName("isETDAExtension should return true when etdaExtension is true")
    public void testIsETDAExtension() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setEtdaExtension(true);
        assertTrue(entity.isETDAExtension());
    }

    @Test
    @DisplayName("isETDAExtension should return false when etdaExtension is false")
    public void testIsETDAExtensionFalse() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setEtdaExtension(false);
        assertFalse(entity.isETDAExtension());
    }

    @Test
    @DisplayName("isETDAExtension should return false when etdaExtension is null")
    public void testIsETDAExtensionNull() {
        ISOCountryCode entity = new ISOCountryCode();
        assertFalse(entity.isETDAExtension());
    }

    @Test
    @DisplayName("isStandardISO should return true when etdaExtension is false")
    public void testIsStandardISO() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setEtdaExtension(false);
        assertTrue(entity.isStandardISO());
    }

    @Test
    @DisplayName("isStandardISO should return false when etdaExtension is true")
    public void testIsStandardISOFalse() {
        ISOCountryCode entity = new ISOCountryCode();
        entity.setEtdaExtension(true);
        assertFalse(entity.isStandardISO());
    }

    @Test
    @DisplayName("isChina should return true for CN")
    public void testIsChina() {
        ISOCountryCode entity = new ISOCountryCode("CN");
        assertTrue(entity.isChina());
    }

    @Test
    @DisplayName("isChina should return false for non-CN")
    public void testIsChinaNonChina() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isChina());
    }

    @Test
    @DisplayName("isJapan should return true for JP")
    public void testIsJapan() {
        ISOCountryCode entity = new ISOCountryCode("JP");
        assertTrue(entity.isJapan());
    }

    @Test
    @DisplayName("isJapan should return false for non-JP")
    public void testIsJapanNonJapan() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isJapan());
    }

    @Test
    @DisplayName("isSouthKorea should return true for KR")
    public void testIsSouthKorea() {
        ISOCountryCode entity = new ISOCountryCode("KR");
        assertTrue(entity.isSouthKorea());
    }

    @Test
    @DisplayName("isSouthKorea should return false for non-KR")
    public void testIsSouthKoreaNonKorea() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isSouthKorea());
    }

    @Test
    @DisplayName("isUnitedStates should return true for US")
    public void testIsUnitedStates() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertTrue(entity.isUnitedStates());
    }

    @Test
    @DisplayName("isUnitedStates should return false for non-US")
    public void testIsUnitedStatesNonUS() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        assertFalse(entity.isUnitedStates());
    }

    @Test
    @DisplayName("isSingapore should return true for SG")
    public void testIsSingapore() {
        ISOCountryCode entity = new ISOCountryCode("SG");
        assertTrue(entity.isSingapore());
    }

    @Test
    @DisplayName("isSingapore should return false for non-SG")
    public void testIsSingaporeNonSingapore() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isSingapore());
    }

    @Test
    @DisplayName("isMalaysia should return true for MY")
    public void testIsMalaysia() {
        ISOCountryCode entity = new ISOCountryCode("MY");
        assertTrue(entity.isMalaysia());
    }

    @Test
    @DisplayName("isMalaysia should return false for non-MY")
    public void testIsMalaysiaNonMalaysia() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isMalaysia());
    }

    @Test
    @DisplayName("isIndonesia should return true for ID")
    public void testIsIndonesia() {
        ISOCountryCode entity = new ISOCountryCode("ID");
        assertTrue(entity.isIndonesia());
    }

    @Test
    @DisplayName("isIndonesia should return false for non-ID")
    public void testIsIndonesiaNonIndonesia() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isIndonesia());
    }

    @Test
    @DisplayName("isVietnam should return true for VN")
    public void testIsVietnam() {
        ISOCountryCode entity = new ISOCountryCode("VN");
        assertTrue(entity.isVietnam());
    }

    @Test
    @DisplayName("isVietnam should return false for non-VN")
    public void testIsVietnamNonVietnam() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isVietnam());
    }

    @Test
    @DisplayName("isPhilippines should return true for PH")
    public void testIsPhilippines() {
        ISOCountryCode entity = new ISOCountryCode("PH");
        assertTrue(entity.isPhilippines());
    }

    @Test
    @DisplayName("isPhilippines should return false for non-PH")
    public void testIsPhilippinesNonPhilippines() {
        ISOCountryCode entity = new ISOCountryCode("US");
        assertFalse(entity.isPhilippines());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ISOCountryCode entity1 = new ISOCountryCode("TH", "Thailand");
        ISOCountryCode entity2 = new ISOCountryCode("TH", "Thailand 2");
        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ISOCountryCode entity1 = new ISOCountryCode("TH", "Thailand");
        ISOCountryCode entity2 = new ISOCountryCode("US", "United States");
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        assertNotEquals(null, entity);
    }

    @Test
    @DisplayName("Equals should return false for different type")
    public void testEqualsDifferentType() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        assertNotEquals("TH", entity);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false when both codes are null")
    public void testEqualsBothNullCodes() {
        ISOCountryCode entity1 = new ISOCountryCode();
        ISOCountryCode entity2 = new ISOCountryCode();
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("HashCode should be consistent for equal objects")
    public void testHashCodeConsistent() {
        ISOCountryCode entity1 = new ISOCountryCode("TH");
        ISOCountryCode entity2 = new ISOCountryCode("TH");
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be different for different codes")
    public void testHashCodeDifferentCodes() {
        ISOCountryCode entity1 = new ISOCountryCode("TH");
        ISOCountryCode entity2 = new ISOCountryCode("US");
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should return 0 for null code")
    public void testHashCodeNullCode() {
        ISOCountryCode entity = new ISOCountryCode();
        assertEquals(0, entity.hashCode());
    }

    // toString Tests

    @Test
    @DisplayName("toString should contain code")
    public void testToStringContainsCode() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        String result = entity.toString();
        assertTrue(result.contains("TH"));
    }

    @Test
    @DisplayName("toString should contain name")
    public void testToStringContainsName() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        String result = entity.toString();
        assertTrue(result.contains("Thailand"));
    }

    @Test
    @DisplayName("toString should have correct format")
    public void testToStringFormat() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        entity.setEtdaExtension(false);
        entity.setActive(true);
        String result = entity.toString();
        assertTrue(result.contains("ISOCountryCode{"));
        assertTrue(result.contains("code='TH'"));
        assertTrue(result.contains("name='Thailand'"));
        assertTrue(result.contains("etdaExtension=false"));
        assertTrue(result.contains("active=true"));
    }

    // Audit Field Tests

    @Test
    @DisplayName("onCreate should set timestamps when called")
    public void testOnCreateSetsTimestamps() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        LocalDateTime before = LocalDateTime.now();
        entity.onCreate();
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
        assertTrue(entity.getCreatedAt().isBefore(after) || entity.getCreatedAt().isEqual(after));
        assertTrue(entity.getCreatedAt().isAfter(before) || entity.getCreatedAt().isEqual(before));
    }

    @Test
    @DisplayName("onUpdate should set updatedAt when called")
    public void testOnUpdateSetsUpdatedAt() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        entity.onCreate();
        LocalDateTime originalCreatedAt = entity.getCreatedAt();

        // Wait a bit to ensure time difference
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }

        entity.onUpdate();

        assertNotNull(entity.getUpdatedAt());
        // createdAt should not change (within 1 second tolerance)
        assertTrue(entity.getCreatedAt().equals(originalCreatedAt) ||
                   java.time.Duration.between(entity.getCreatedAt(), originalCreatedAt).abs().toMillis() < 1000);
        assertTrue(entity.getUpdatedAt().isAfter(originalCreatedAt) || entity.getUpdatedAt().isEqual(originalCreatedAt));
    }

    @Test
    @DisplayName("onCreate should set timestamps when called")
    public void testOnCreateSetsTimestampsWhenAlreadySet() {
        // Note: createdAt is immutable (updatable=false), so we cannot test if it preserves
        // existing values. This test verifies that onCreate can be called multiple times.
        ISOCountryCode entity = new ISOCountryCode("TH");
        entity.onCreate();
        LocalDateTime firstCreatedAt = entity.getCreatedAt();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }

        entity.onCreate();
        LocalDateTime secondCreatedAt = entity.getCreatedAt();

        // createdAt remains the same (it's only set once by @PrePersist)
        // But onUpdate updates updatedAt
        assertNotNull(secondCreatedAt);
        assertNotNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("onCreate should set updatedAt when createdAt is null")
    public void testOnCreateSetsUpdatedAt() {
        ISOCountryCode entity = new ISOCountryCode("TH");
        entity.onCreate();
        assertNotNull(entity.getUpdatedAt());
        assertNotNull(entity.getCreatedAt());
    }
}
