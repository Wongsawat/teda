package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TISICityName Entity Tests")
public class TISICityNameEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        TISICityName entity = new TISICityName();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getNameTh());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        TISICityName entity = new TISICityName("1001");
        assertEquals("1001", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        TISICityName entity = new TISICityName(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code and name constructor should set both fields")
    public void testCodeNameConstructor() {
        TISICityName entity = new TISICityName("1001", "เมืองเชียงใหม่");
        assertEquals("1001", entity.getCode());
        assertEquals("เมืองเชียงใหม่", entity.getNameTh());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        TISICityName entity = new TISICityName();
        entity.setCode("1002");
        assertEquals("1002", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        TISICityName entity = new TISICityName("1001");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetNameTh should set Thai name")
    public void testSetNameTh() {
        TISICityName entity = new TISICityName();
        entity.setNameTh("เมืองเชียงใหม่");
        assertEquals("เมืองเชียงใหม่", entity.getNameTh());
    }

    @Test
    @DisplayName("GetProvinceCode should return province code")
    public void testGetProvinceCode() {
        TISICityName entity = new TISICityName("1001");
        // Note: provinceCode is a computed column, so it will be null in unit tests
        // In integration tests with database, it would contain the actual province code
        assertNull(entity.getProvinceCode());
    }

    @Test
    @DisplayName("GetDistrictCode should return district code")
    public void testGetDistrictCode() {
        TISICityName entity = new TISICityName("1001");
        // Note: districtCode is a computed column, so it will be null in unit tests
        // In integration tests with database, it would contain the actual district code
        assertNull(entity.getDistrictCode());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        TISICityName entity = new TISICityName("1001");
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
        TISICityName entity = new TISICityName("1001");
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

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        TISICityName entity1 = new TISICityName("1001", "เมืองเชียงใหม่");
        TISICityName entity2 = new TISICityName("1001", "Different Name");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        TISICityName entity1 = new TISICityName("1001");
        TISICityName entity2 = new TISICityName("1002");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        TISICityName entity = new TISICityName("1001");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        TISICityName entity = new TISICityName("1001");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        TISICityName entity = new TISICityName("1001");
        String other = "1001";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        TISICityName entity1 = new TISICityName();
        TISICityName entity2 = new TISICityName();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        TISICityName entity1 = new TISICityName("1001");
        TISICityName entity2 = new TISICityName("1001");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        TISICityName entity = new TISICityName();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and name")
    public void testToString() {
        TISICityName entity = new TISICityName("1001", "เมืองเชียงใหม่");

        String result = entity.toString();

        assertTrue(result.contains("1001"));
        assertTrue(result.contains("เมืองเชียงใหม่"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        TISICityName entity = new TISICityName();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("TISICityName"));
    }
}
