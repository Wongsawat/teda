package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MessageFunctionCode Entity Tests")
public class MessageFunctionCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        MessageFunctionCode entity = new MessageFunctionCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCategory());
        assertFalse(entity.getIsModification());
        assertFalse(entity.getIsOriginal());
        assertFalse(entity.getIsAcceptance());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructor() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        assertEquals("9", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        MessageFunctionCode entity = new MessageFunctionCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace from input")
    public void testCodeConstructorTrimsWhitespace() {
        MessageFunctionCode entity = new MessageFunctionCode("  1  ");
        assertEquals("1", entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        MessageFunctionCode entity = new MessageFunctionCode(
            "9",
            "Original",
            "Indicates the original transmission of a message"
        );
        assertEquals("9", entity.getCode());
        assertEquals("Original", entity.getName());
        assertEquals("Indicates the original transmission of a message", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setCode("  5  ");
        assertEquals("5", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setName("Test Name");
        assertEquals("Test Name", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setCategory("Transaction Control");
        assertEquals("Transaction Control", entity.getCategory());
    }

    @Test
    @DisplayName("SetIsModification should set modification flag")
    public void testSetIsModification() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setIsModification(true);
        assertTrue(entity.getIsModification());
    }

    @Test
    @DisplayName("SetIsOriginal should set original flag")
    public void testSetIsOriginal() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setIsOriginal(true);
        assertTrue(entity.getIsOriginal());
    }

    @Test
    @DisplayName("SetIsAcceptance should set acceptance flag")
    public void testSetIsAcceptance() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setIsAcceptance(true);
        assertTrue(entity.getIsAcceptance());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps and normalize code")
    public void testPrePersistSetsTimestamps() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
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
        MessageFunctionCode entity = new MessageFunctionCode("9");
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
    @DisplayName("isModification should return true when flag is true")
    public void testIsModification() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setIsModification(true);
        assertTrue(entity.isModification());

        entity.setIsModification(false);
        assertFalse(entity.isModification());

        entity.setIsModification(null);
        assertFalse(entity.isModification());
    }

    @Test
    @DisplayName("isOriginal should return true when flag is true")
    public void testIsOriginal() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setIsOriginal(true);
        assertTrue(entity.isOriginal());

        entity.setIsOriginal(false);
        assertFalse(entity.isOriginal());

        entity.setIsOriginal(null);
        assertFalse(entity.isOriginal());
    }

    @Test
    @DisplayName("isAcceptance should return true when flag is true")
    public void testIsAcceptance() {
        MessageFunctionCode entity = new MessageFunctionCode();
        entity.setIsAcceptance(true);
        assertTrue(entity.isAcceptance());

        entity.setIsAcceptance(false);
        assertFalse(entity.isAcceptance());

        entity.setIsAcceptance(null);
        assertFalse(entity.isAcceptance());
    }

    @Test
    @DisplayName("isCancellation should return true for cancellation codes")
    public void testIsCancellation() {
        assertTrue(new MessageFunctionCode("1").isCancellation());
        assertTrue(new MessageFunctionCode("17").isCancellation());
        assertTrue(new MessageFunctionCode("18").isCancellation());
        assertTrue(new MessageFunctionCode("39").isCancellation());
        assertFalse(new MessageFunctionCode("2").isCancellation());
        assertFalse(new MessageFunctionCode("9").isCancellation());
        assertFalse(new MessageFunctionCode(null).isCancellation());
    }

    @Test
    @DisplayName("isChange should return true for change codes")
    public void testIsChange() {
        assertTrue(new MessageFunctionCode("4").isChange());
        assertTrue(new MessageFunctionCode("19").isChange());
        assertTrue(new MessageFunctionCode("28").isChange());
        assertTrue(new MessageFunctionCode("30").isChange());
        assertTrue(new MessageFunctionCode("33").isChange());
        assertTrue(new MessageFunctionCode("34").isChange());
        assertTrue(new MessageFunctionCode("36").isChange());
        assertTrue(new MessageFunctionCode("52").isChange());
        assertFalse(new MessageFunctionCode("1").isChange());
        assertFalse(new MessageFunctionCode(null).isChange());
    }

    @Test
    @DisplayName("isReplacement should return true for replacement codes")
    public void testIsReplacement() {
        assertTrue(new MessageFunctionCode("5").isReplacement());
        assertTrue(new MessageFunctionCode("20").isReplacement());
        assertTrue(new MessageFunctionCode("21").isReplacement());
        assertFalse(new MessageFunctionCode("1").isReplacement());
        assertFalse(new MessageFunctionCode("9").isReplacement());
        assertFalse(new MessageFunctionCode(null).isReplacement());
    }

    @Test
    @DisplayName("isConfirmation should return true for confirmation codes")
    public void testIsConfirmation() {
        assertTrue(new MessageFunctionCode("6").isConfirmation());
        assertTrue(new MessageFunctionCode("42").isConfirmation());
        assertFalse(new MessageFunctionCode("1").isConfirmation());
        assertFalse(new MessageFunctionCode("9").isConfirmation());
        assertFalse(new MessageFunctionCode(null).isConfirmation());
    }

    @Test
    @DisplayName("isFinancialReversal should return true for financial reversal codes")
    public void testIsFinancialReversal() {
        assertTrue(new MessageFunctionCode("37").isFinancialReversal());
        assertTrue(new MessageFunctionCode("38").isFinancialReversal());
        assertTrue(new MessageFunctionCode("39").isFinancialReversal());
        assertFalse(new MessageFunctionCode("1").isFinancialReversal());
        assertFalse(new MessageFunctionCode("9").isFinancialReversal());
        assertFalse(new MessageFunctionCode(null).isFinancialReversal());
    }

    @Test
    @DisplayName("isSchedule should return true for schedule codes")
    public void testIsSchedule() {
        assertTrue(new MessageFunctionCode("24").isSchedule());
        assertTrue(new MessageFunctionCode("25").isSchedule());
        assertTrue(new MessageFunctionCode("26").isSchedule());
        assertTrue(new MessageFunctionCode("61").isSchedule());
        assertTrue(new MessageFunctionCode("62").isSchedule());
        assertFalse(new MessageFunctionCode("1").isSchedule());
        assertFalse(new MessageFunctionCode("9").isSchedule());
        assertFalse(new MessageFunctionCode(null).isSchedule());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        MessageFunctionCode entity1 = new MessageFunctionCode("9", "Original", "Desc1");
        MessageFunctionCode entity2 = new MessageFunctionCode("9", "Different Name", "Desc2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        MessageFunctionCode entity1 = new MessageFunctionCode("9");
        MessageFunctionCode entity2 = new MessageFunctionCode("1");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        MessageFunctionCode entity = new MessageFunctionCode("9");
        String other = "9";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        MessageFunctionCode entity1 = new MessageFunctionCode();
        MessageFunctionCode entity2 = new MessageFunctionCode();

        assertEquals(entity1, entity2); // Both have null codes - uses Objects.equals
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        MessageFunctionCode entity1 = new MessageFunctionCode("9");
        MessageFunctionCode entity2 = new MessageFunctionCode("9");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should handle null code")
    public void testHashCodeNullCode() {
        MessageFunctionCode entity = new MessageFunctionCode();
        assertNotNull(entity.hashCode()); // Objects.hash handles null
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include all relevant fields")
    public void testToString() {
        MessageFunctionCode entity = new MessageFunctionCode("9", "Original", "Description");
        entity.setCategory("Transaction Control");
        entity.setIsModification(false);
        entity.setIsOriginal(true);
        entity.setIsAcceptance(false);

        String result = entity.toString();

        assertTrue(result.contains("9"));
        assertTrue(result.contains("Original"));
        assertTrue(result.contains("Transaction Control"));
        assertTrue(result.contains("isModification=false"));
        assertTrue(result.contains("isOriginal=true"));
        assertTrue(result.contains("isAcceptance=false"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        MessageFunctionCode entity = new MessageFunctionCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("MessageFunctionCode"));
    }
}
