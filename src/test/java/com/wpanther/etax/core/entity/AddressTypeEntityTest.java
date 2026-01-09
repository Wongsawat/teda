package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AddressType Entity Tests")
public class AddressTypeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        AddressType entity = new AddressType();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructor() {
        AddressType entity = new AddressType("1");
        assertEquals("1", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        AddressType entity = new AddressType(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace from input")
    public void testCodeConstructorTrimsWhitespace() {
        AddressType entity = new AddressType("  2  ");
        assertEquals("2", entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        AddressType entity = new AddressType(
            "1",
            "Postal address",
            "The address is representing a postal address"
        );
        assertEquals("1", entity.getCode());
        assertEquals("Postal address", entity.getName());
        assertEquals("The address is representing a postal address", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        AddressType entity = new AddressType();
        entity.setCode("  3  ");
        assertEquals("3", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        AddressType entity = new AddressType("1");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        AddressType entity = new AddressType();
        entity.setName("Physical address");
        assertEquals("Physical address", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        AddressType entity = new AddressType();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        AddressType entity = new AddressType("1");
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
        AddressType entity = new AddressType("1");
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
    @DisplayName("isPostalAddress should return true for code 1")
    public void testIsPostalAddress() {
        assertTrue(new AddressType("1").isPostalAddress());
        assertFalse(new AddressType("2").isPostalAddress());
        assertFalse(new AddressType("3").isPostalAddress());
        assertFalse(new AddressType(null).isPostalAddress());
    }

    @Test
    @DisplayName("isFiscalAddress should return true for code 2")
    public void testIsFiscalAddress() {
        assertTrue(new AddressType("2").isFiscalAddress());
        assertFalse(new AddressType("1").isFiscalAddress());
        assertFalse(new AddressType("3").isFiscalAddress());
        assertFalse(new AddressType(null).isFiscalAddress());
    }

    @Test
    @DisplayName("isPhysicalAddress should return true for code 3")
    public void testIsPhysicalAddress() {
        assertTrue(new AddressType("3").isPhysicalAddress());
        assertFalse(new AddressType("1").isPhysicalAddress());
        assertFalse(new AddressType("2").isPhysicalAddress());
        assertFalse(new AddressType(null).isPhysicalAddress());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        AddressType entity1 = new AddressType("1", "Postal address", "Description1");
        AddressType entity2 = new AddressType("1", "Different Name", "Description2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        AddressType entity1 = new AddressType("1");
        AddressType entity2 = new AddressType("2");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        AddressType entity = new AddressType("1");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        AddressType entity = new AddressType("1");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        AddressType entity = new AddressType("1");
        String other = "1";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        AddressType entity1 = new AddressType();
        AddressType entity2 = new AddressType();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        AddressType entity1 = new AddressType("1");
        AddressType entity2 = new AddressType("1");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        AddressType entity = new AddressType();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and name")
    public void testToString() {
        AddressType entity = new AddressType("1", "Postal address", "Description");

        String result = entity.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("Postal address"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        AddressType entity = new AddressType();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("AddressType"));
    }
}
