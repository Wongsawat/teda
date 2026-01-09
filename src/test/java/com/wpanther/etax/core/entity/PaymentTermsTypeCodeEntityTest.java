package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaymentTermsTypeCode Entity Tests")
public class PaymentTermsTypeCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCategory());
        assertFalse(entity.getIsImmediate());
        assertFalse(entity.getIsDeferred());
        assertFalse(entity.getHasDiscount());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("zzz");
        assertEquals("ZZZ", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("  1  ");
        assertEquals("1", entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode(
            "1",
            "Payment terms",
            "Basic payment terms description"
        );
        assertEquals("1", entity.getCode());
        assertEquals("Payment terms", entity.getName());
        assertEquals("Basic payment terms description", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setCode("zzz");
        assertEquals("ZZZ", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("1");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setCode("  10  ");
        assertEquals("10", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setName("Test Name");
        assertEquals("Test Name", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setCategory("Immediate");
        assertEquals("Immediate", entity.getCategory());
    }

    @Test
    @DisplayName("SetIsImmediate should set immediate flag")
    public void testSetIsImmediate() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setIsImmediate(true);
        assertTrue(entity.getIsImmediate());
    }

    @Test
    @DisplayName("SetIsDeferred should set deferred flag")
    public void testSetIsDeferred() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setIsDeferred(true);
        assertTrue(entity.getIsDeferred());
    }

    @Test
    @DisplayName("SetHasDiscount should set discount flag")
    public void testSetHasDiscount() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setHasDiscount(true);
        assertTrue(entity.getHasDiscount());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps and normalize code")
    public void testPrePersistSetsTimestamps() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("zzz");
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
        assertEquals("ZZZ", entity.getCode()); // Code normalized
    }

    @Test
    @DisplayName("PreUpdate should update timestamp and normalize code")
    public void testPreUpdateUpdatesTimestamp() throws InterruptedException {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("1");
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
    @DisplayName("isImmediate should return true when flag is true")
    public void testIsImmediate() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setIsImmediate(true);
        assertTrue(entity.isImmediate());

        entity.setIsImmediate(false);
        assertFalse(entity.isImmediate());

        entity.setIsImmediate(null);
        assertFalse(entity.isImmediate());
    }

    @Test
    @DisplayName("isDeferred should return true when flag is true")
    public void testIsDeferred() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setIsDeferred(true);
        assertTrue(entity.isDeferred());

        entity.setIsDeferred(false);
        assertFalse(entity.isDeferred());

        entity.setIsDeferred(null);
        assertFalse(entity.isDeferred());
    }

    @Test
    @DisplayName("hasDiscount should return true when flag is true")
    public void testHasDiscount() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        entity.setHasDiscount(true);
        assertTrue(entity.hasDiscount());

        entity.setHasDiscount(false);
        assertFalse(entity.hasDiscount());

        entity.setHasDiscount(null);
        assertFalse(entity.hasDiscount());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        PaymentTermsTypeCode entity1 = new PaymentTermsTypeCode("1", "Name1", "Desc1");
        PaymentTermsTypeCode entity2 = new PaymentTermsTypeCode("1", "Name2", "Desc2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        PaymentTermsTypeCode entity1 = new PaymentTermsTypeCode("1");
        PaymentTermsTypeCode entity2 = new PaymentTermsTypeCode("2");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("1");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("1");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("1");
        String other = "1";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        PaymentTermsTypeCode entity1 = new PaymentTermsTypeCode();
        PaymentTermsTypeCode entity2 = new PaymentTermsTypeCode();

        assertEquals(entity1, entity2); // Both have null codes - uses Objects.equals
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        PaymentTermsTypeCode entity1 = new PaymentTermsTypeCode("1");
        PaymentTermsTypeCode entity2 = new PaymentTermsTypeCode("1");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should handle null code")
    public void testHashCodeNullCode() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();
        assertNotNull(entity.hashCode()); // Objects.hash handles null
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include all relevant fields")
    public void testToString() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode("1", "Payment terms", "Description");
        entity.setCategory("Immediate");
        entity.setIsImmediate(true);
        entity.setIsDeferred(false);
        entity.setHasDiscount(true);

        String result = entity.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("Payment terms"));
        assertTrue(result.contains("Immediate"));
        assertTrue(result.contains("isImmediate=true"));
        assertTrue(result.contains("isDeferred=false"));
        assertTrue(result.contains("hasDiscount=true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        PaymentTermsTypeCode entity = new PaymentTermsTypeCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("PaymentTermsTypeCode"));
    }
}
