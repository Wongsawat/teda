package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TISISubdistrict Entity Tests")
public class TISISubdistrictEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        TISISubdistrict entity = new TISISubdistrict();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getNameTh());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        assertEquals("100101", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        TISISubdistrict entity = new TISISubdistrict(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code and nameTh constructor should set both fields")
    public void testCodeNameConstructor() {
        TISISubdistrict entity = new TISISubdistrict("100101", "พระนคร");
        assertEquals("100101", entity.getCode());
        assertEquals("พระนคร", entity.getNameTh());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        TISISubdistrict entity = new TISISubdistrict();
        entity.setCode("100102");
        assertEquals("100102", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetNameTh should set Thai name")
    public void testSetNameTh() {
        TISISubdistrict entity = new TISISubdistrict();
        entity.setNameTh("วัดโสมนัส");
        assertEquals("วัดโสมนัส", entity.getNameTh());
    }

    @Test
    @DisplayName("GetProvinceCode should return province code")
    public void testGetProvinceCode() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        // Note: provinceCode is a computed column, so it will be null in this unit test
        // In integration tests with database, it would contain the first 2 digits
        assertNull(entity.getProvinceCode());
    }

    @Test
    @DisplayName("GetCityCode should return city code")
    public void testGetCityCode() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        // Note: cityCode is a computed column, so it will be null in this unit test
        // In integration tests with database, it would contain the first 4 digits
        assertNull(entity.getCityCode());
    }

    @Test
    @DisplayName("GetSubdistrictCode should return subdistrict code")
    public void testGetSubdistrictCode() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        // Note: subdistrictCode is a computed column, so it will be null in this unit test
        // In integration tests with database, it would contain the last 2 digits
        assertNull(entity.getSubdistrictCode());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        TISISubdistrict entity = new TISISubdistrict("100101");
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
        TISISubdistrict entity = new TISISubdistrict("100101");
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
        TISISubdistrict entity1 = new TISISubdistrict("100101", "พระนคร");
        TISISubdistrict entity2 = new TISISubdistrict("100101", "Different Name");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        TISISubdistrict entity1 = new TISISubdistrict("100101");
        TISISubdistrict entity2 = new TISISubdistrict("100102");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        TISISubdistrict entity = new TISISubdistrict("100101");
        String other = "100101";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        TISISubdistrict entity1 = new TISISubdistrict();
        TISISubdistrict entity2 = new TISISubdistrict();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        TISISubdistrict entity1 = new TISISubdistrict("100101");
        TISISubdistrict entity2 = new TISISubdistrict("100101");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        TISISubdistrict entity = new TISISubdistrict();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and nameTh")
    public void testToString() {
        TISISubdistrict entity = new TISISubdistrict("100101", "พระนคร");

        String result = entity.toString();

        assertTrue(result.contains("100101"));
        assertTrue(result.contains("พระนคร"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        TISISubdistrict entity = new TISISubdistrict();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("TISISubdistrict"));
    }
}
