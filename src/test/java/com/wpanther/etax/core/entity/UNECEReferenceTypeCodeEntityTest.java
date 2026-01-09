package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UNECEReferenceTypeCode Entity Tests")
public class UNECEReferenceTypeCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertFalse(entity.getEtdaExtension());
        assertTrue(entity.getActive());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("aaa");
        assertEquals("AAA", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code and name constructor should set both fields")
    public void testCodeNameConstructor() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("aaa", "Test Reference");
        assertEquals("AAA", entity.getCode());
        assertEquals("Test Reference", entity.getName());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("aaa", "Test Reference", "Description");
        assertEquals("AAA", entity.getCode());
        assertEquals("Test Reference", entity.getName());
        assertEquals("Description", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setCode("aab");
        assertEquals("AAB", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setName("Purchase Order");
        assertEquals("Purchase Order", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetEtdaExtension should set ETDA extension flag")
    public void testSetEtdaExtension() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setEtdaExtension(true);
        assertTrue(entity.getEtdaExtension());
    }

    @Test
    @DisplayName("SetActive should set active flag")
    public void testSetActive() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        entity.setActive(false);
        assertFalse(entity.getActive());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
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
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
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
        UNECEReferenceTypeCode entity1 = new UNECEReferenceTypeCode("AAA", "Reference 1");
        UNECEReferenceTypeCode entity2 = new UNECEReferenceTypeCode("AAA", "Reference 2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        UNECEReferenceTypeCode entity1 = new UNECEReferenceTypeCode("AAA");
        UNECEReferenceTypeCode entity2 = new UNECEReferenceTypeCode("BBB");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA");
        String other = "AAA";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        UNECEReferenceTypeCode entity1 = new UNECEReferenceTypeCode();
        UNECEReferenceTypeCode entity2 = new UNECEReferenceTypeCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        UNECEReferenceTypeCode entity1 = new UNECEReferenceTypeCode("AAA");
        UNECEReferenceTypeCode entity2 = new UNECEReferenceTypeCode("AAA");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and name")
    public void testToString() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode("AAA", "Test Reference", "Description");
        entity.setEtdaExtension(false);
        entity.setActive(true);

        String result = entity.toString();

        assertTrue(result.contains("AAA"));
        assertTrue(result.contains("Test Reference"));
        assertTrue(result.contains("false"));
        assertTrue(result.contains("true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        UNECEReferenceTypeCode entity = new UNECEReferenceTypeCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("UNECEReferenceTypeCode"));
    }
}
