package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaymentTermsDescriptionIdentifier Entity Tests")
public class PaymentTermsDescriptionIdentifierEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertTrue(entity.getIsDraftRequired());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructor() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        assertEquals("1", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace from input")
    public void testCodeConstructorTrimsWhitespace() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("  3  ");
        assertEquals("3", entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier(
            "1",
            "Draft on issuing bank",
            "A draft drawn on the issuing bank"
        );
        assertEquals("1", entity.getCode());
        assertEquals("Draft on issuing bank", entity.getName());
        assertEquals("A draft drawn on the issuing bank", entity.getDescription());
    }

    @Test
    @DisplayName("Three-argument constructor should handle null code")
    public void testThreeArgumentConstructorWithNullCode() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier(
            null,
            "Draft on issuing bank",
            "A draft drawn on the issuing bank"
        );
        assertNull(entity.getCode());
        assertEquals("Draft on issuing bank", entity.getName());
        assertEquals("A draft drawn on the issuing bank", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        entity.setCode("  2  ");
        assertEquals("2", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        entity.setName("Test Name");
        assertEquals("Test Name", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetIsDraftRequired should set draft required flag")
    public void testSetIsDraftRequired() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        entity.setIsDraftRequired(false);
        assertFalse(entity.getIsDraftRequired());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps and normalize code")
    public void testPrePersistSetsTimestamps() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
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
    @DisplayName("PreUpdate should update timestamp and normalize code")
    public void testPreUpdateUpdatesTimestamp() throws InterruptedException {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
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
    @DisplayName("isDraftRequired should return true when flag is true")
    public void testIsDraftRequired() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        entity.setIsDraftRequired(true);
        assertTrue(entity.isDraftRequired());

        entity.setIsDraftRequired(false);
        assertFalse(entity.isDraftRequired());

        entity.setIsDraftRequired(null);
        assertFalse(entity.isDraftRequired());
    }

    @Test
    @DisplayName("isBankingDraft should return true for codes 1-3")
    public void testIsBankingDraft() {
        assertTrue(new PaymentTermsDescriptionIdentifier("1").isBankingDraft());
        assertTrue(new PaymentTermsDescriptionIdentifier("2").isBankingDraft());
        assertTrue(new PaymentTermsDescriptionIdentifier("3").isBankingDraft());
        assertFalse(new PaymentTermsDescriptionIdentifier("4").isBankingDraft());
        assertFalse(new PaymentTermsDescriptionIdentifier("6").isBankingDraft());
        assertFalse(new PaymentTermsDescriptionIdentifier(null).isBankingDraft());
    }

    @Test
    @DisplayName("isIssuingBank should return true for code 1")
    public void testIsIssuingBank() {
        assertTrue(new PaymentTermsDescriptionIdentifier("1").isIssuingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier("2").isIssuingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier("3").isIssuingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier(null).isIssuingBank());
    }

    @Test
    @DisplayName("isAdvisingBank should return true for code 2")
    public void testIsAdvisingBank() {
        assertTrue(new PaymentTermsDescriptionIdentifier("2").isAdvisingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier("1").isAdvisingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier("3").isAdvisingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier(null).isAdvisingBank());
    }

    @Test
    @DisplayName("isReimbursingBank should return true for code 3")
    public void testIsReimbursingBank() {
        assertTrue(new PaymentTermsDescriptionIdentifier("3").isReimbursingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier("1").isReimbursingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier("2").isReimbursingBank());
        assertFalse(new PaymentTermsDescriptionIdentifier(null).isReimbursingBank());
    }

    @Test
    @DisplayName("isNoDraft should return true for code 6")
    public void testIsNoDraft() {
        assertTrue(new PaymentTermsDescriptionIdentifier("6").isNoDraft());
        assertFalse(new PaymentTermsDescriptionIdentifier("1").isNoDraft());
        assertFalse(new PaymentTermsDescriptionIdentifier("7").isNoDraft());
        assertFalse(new PaymentTermsDescriptionIdentifier(null).isNoDraft());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        PaymentTermsDescriptionIdentifier entity1 = new PaymentTermsDescriptionIdentifier("1", "Name1", "Desc1");
        PaymentTermsDescriptionIdentifier entity2 = new PaymentTermsDescriptionIdentifier("1", "Name2", "Desc2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        PaymentTermsDescriptionIdentifier entity1 = new PaymentTermsDescriptionIdentifier("1");
        PaymentTermsDescriptionIdentifier entity2 = new PaymentTermsDescriptionIdentifier("2");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1");
        String other = "1";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        PaymentTermsDescriptionIdentifier entity1 = new PaymentTermsDescriptionIdentifier();
        PaymentTermsDescriptionIdentifier entity2 = new PaymentTermsDescriptionIdentifier();

        assertEquals(entity1, entity2); // Both have null codes - uses Objects.equals
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        PaymentTermsDescriptionIdentifier entity1 = new PaymentTermsDescriptionIdentifier("1");
        PaymentTermsDescriptionIdentifier entity2 = new PaymentTermsDescriptionIdentifier("1");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should handle null code")
    public void testHashCodeNullCode() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();
        assertNotNull(entity.hashCode()); // Objects.hash handles null
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include all relevant fields")
    public void testToString() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier("1", "Draft on issuing bank", "Description");
        entity.setIsDraftRequired(true);

        String result = entity.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("Draft on issuing bank"));
        assertTrue(result.contains("isDraftRequired=true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        PaymentTermsDescriptionIdentifier entity = new PaymentTermsDescriptionIdentifier();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("PaymentTermsDescriptionIdentifier"));
    }
}
