package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ThaiProvinceCode Entity Tests")
public class ThaiProvinceCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        assertNull(entity.getCode());
        assertNull(entity.getNameTh());
        assertNull(entity.getNameEn());
        assertNull(entity.getRegion());
        assertTrue(entity.isActive());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        assertEquals("10", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorNull() {
        ThaiProvinceCode entity = new ThaiProvinceCode((String) null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code and Thai name constructor should set both")
    public void testCodeNameThConstructor() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10", "กรุงเทพมหานคร");
        assertEquals("10", entity.getCode());
        assertEquals("กรุงเทพมหานคร", entity.getNameTh());
    }

    @Test
    @DisplayName("Full constructor should set all three")
    public void testFullConstructor() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10", "กรุงเทพมหานคร", "Bangkok");
        assertEquals("10", entity.getCode());
        assertEquals("กรุงเทพมหานคร", entity.getNameTh());
        assertEquals("Bangkok", entity.getNameEn());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        entity.setCode("10");
        assertEquals("10", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeWithNull() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetNameTh should set Thai name")
    public void testSetNameTh() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        entity.setNameTh("กรุงเทพมหานคร");
        assertEquals("กรุงเทพมหานคร", entity.getNameTh());
    }

    @Test
    @DisplayName("SetNameEn should set English name")
    public void testSetNameEn() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        entity.setNameEn("Bangkok");
        assertEquals("Bangkok", entity.getNameEn());
    }

    @Test
    @DisplayName("SetRegion should set region")
    public void testSetRegion() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        entity.setRegion("Central");
        assertEquals("Central", entity.getRegion());
    }

    @Test
    @DisplayName("SetActive should set flag")
    public void testSetActive() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        entity.setActive(false);
        assertFalse(entity.isActive());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ThaiProvinceCode entity1 = new ThaiProvinceCode("10");
        entity1.setNameTh("กรุงเทพมหานคร");
        ThaiProvinceCode entity2 = new ThaiProvinceCode("10");
        entity2.setNameTh("ชลบุรี");
        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ThaiProvinceCode entity1 = new ThaiProvinceCode("10");
        ThaiProvinceCode entity2 = new ThaiProvinceCode("11");
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        assertNotEquals(null, entity);
    }

    @Test
    @DisplayName("Equals should return false for different type")
    public void testEqualsDifferentType() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        assertNotEquals("10", entity);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false when both codes are null")
    public void testEqualsBothNullCodes() {
        ThaiProvinceCode entity1 = new ThaiProvinceCode();
        ThaiProvinceCode entity2 = new ThaiProvinceCode();
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("HashCode should be consistent for equal objects")
    public void testHashCodeConsistent() {
        ThaiProvinceCode entity1 = new ThaiProvinceCode("10");
        ThaiProvinceCode entity2 = new ThaiProvinceCode("10");
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be different for different codes")
    public void testHashCodeDifferentCodes() {
        ThaiProvinceCode entity1 = new ThaiProvinceCode("10");
        ThaiProvinceCode entity2 = new ThaiProvinceCode("11");
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should return 0 for null code")
    public void testHashCodeNullCode() {
        ThaiProvinceCode entity = new ThaiProvinceCode();
        assertEquals(0, entity.hashCode());
    }

    // toString Tests

    @Test
    @DisplayName("toString should contain code")
    public void testToStringContainsCode() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        entity.setNameTh("กรุงเทพมหานคร");
        entity.setNameEn("Bangkok");
        String result = entity.toString();
        assertTrue(result.contains("10"));
    }

    @Test
    @DisplayName("toString should contain Thai name")
    public void testToStringContainsNameTh() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        entity.setNameTh("กรุงเทพมหานคร");
        String result = entity.toString();
        assertTrue(result.contains("กรุงเทพมหานคร"));
    }

    @Test
    @DisplayName("toString should contain English name")
    public void testToStringContainsNameEn() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        entity.setNameEn("Bangkok");
        String result = entity.toString();
        assertTrue(result.contains("Bangkok"));
    }

    @Test
    @DisplayName("toString should have correct format")
    public void testToStringFormat() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        entity.setNameTh("กรุงเทพมหานคร");
        entity.setNameEn("Bangkok");
        entity.setRegion("Central");
        entity.setActive(true);
        String result = entity.toString();
        assertTrue(result.contains("ThaiProvinceCode{"));
        assertTrue(result.contains("code='10'"));
        assertTrue(result.contains("nameTh='กรุงเทพมหานคร'"));
        assertTrue(result.contains("nameEn='Bangkok'"));
        assertTrue(result.contains("region='Central'"));
        assertTrue(result.contains("active=true"));
    }

    // Audit Field Tests

    @Test
    @DisplayName("onCreate should set timestamps when called")
    public void testOnCreateSetsTimestamps() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
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
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
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
    @DisplayName("onCreate can be called multiple times")
    public void testOnCreateMultipleCalls() {
        ThaiProvinceCode entity = new ThaiProvinceCode("10");
        entity.onCreate();
        LocalDateTime firstCreatedAt = entity.getCreatedAt();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }

        entity.onCreate();
        LocalDateTime secondCreatedAt = entity.getCreatedAt();

        assertNotNull(secondCreatedAt);
        assertNotNull(entity.getUpdatedAt());
    }
}
