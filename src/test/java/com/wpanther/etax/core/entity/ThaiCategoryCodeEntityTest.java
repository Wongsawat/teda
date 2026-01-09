package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ThaiCategoryCode Entity Tests")
public class ThaiCategoryCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ThaiCategoryCode entity = new ThaiCategoryCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getNameTh());
        assertNull(entity.getNameEn());
        assertNull(entity.getDescription());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        assertEquals("01", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        ThaiCategoryCode entity = new ThaiCategoryCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all name fields")
    public void testThreeArgumentConstructor() {
        ThaiCategoryCode entity = new ThaiCategoryCode(
            "01",
            "อ้างอิงเอกสารต้นฉบับเพื่อยกเลิก ใบเพิ่มหนี้ หรือใบลดหนี้",
            "Reference to original document for cancellation, debit note, or credit note"
        );
        assertEquals("01", entity.getCode());
        assertEquals("อ้างอิงเอกสารต้นฉบับเพื่อยกเลิก ใบเพิ่มหนี้ หรือใบลดหนี้", entity.getNameTh());
        assertEquals("Reference to original document for cancellation, debit note, or credit note", entity.getNameEn());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        ThaiCategoryCode entity = new ThaiCategoryCode();
        entity.setCode("02");
        assertEquals("02", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetNameTh should set Thai name")
    public void testSetNameTh() {
        ThaiCategoryCode entity = new ThaiCategoryCode();
        entity.setNameTh("ชื่อภาษาไทย");
        assertEquals("ชื่อภาษาไทย", entity.getNameTh());
    }

    @Test
    @DisplayName("SetNameEn should set English name")
    public void testSetNameEn() {
        ThaiCategoryCode entity = new ThaiCategoryCode();
        entity.setNameEn("English Name");
        assertEquals("English Name", entity.getNameEn());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        ThaiCategoryCode entity = new ThaiCategoryCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
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
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
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
    @DisplayName("isOriginalDocumentReference should return true for code 01")
    public void testIsOriginalDocumentReference() {
        ThaiCategoryCode code01 = new ThaiCategoryCode("01");
        assertTrue(code01.isOriginalDocumentReference());

        ThaiCategoryCode code02 = new ThaiCategoryCode("02");
        assertFalse(code02.isOriginalDocumentReference());

        ThaiCategoryCode nullCode = new ThaiCategoryCode();
        assertFalse(nullCode.isOriginalDocumentReference());
    }

    @Test
    @DisplayName("isAdvancePaymentReference should return true for code 02")
    public void testIsAdvancePaymentReference() {
        ThaiCategoryCode code02 = new ThaiCategoryCode("02");
        assertTrue(code02.isAdvancePaymentReference());

        ThaiCategoryCode code01 = new ThaiCategoryCode("01");
        assertFalse(code01.isAdvancePaymentReference());

        ThaiCategoryCode nullCode = new ThaiCategoryCode();
        assertFalse(nullCode.isAdvancePaymentReference());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ThaiCategoryCode entity1 = new ThaiCategoryCode("01", "Name1", "Description1");
        ThaiCategoryCode entity2 = new ThaiCategoryCode("01", "Name2", "Description2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ThaiCategoryCode entity1 = new ThaiCategoryCode("01");
        ThaiCategoryCode entity2 = new ThaiCategoryCode("02");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01");
        String other = "01";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        ThaiCategoryCode entity1 = new ThaiCategoryCode();
        ThaiCategoryCode entity2 = new ThaiCategoryCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        ThaiCategoryCode entity1 = new ThaiCategoryCode("01");
        ThaiCategoryCode entity2 = new ThaiCategoryCode("01");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        ThaiCategoryCode entity = new ThaiCategoryCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and names")
    public void testToString() {
        ThaiCategoryCode entity = new ThaiCategoryCode("01", "ชื่อภาษาไทย", "English Name");

        String result = entity.toString();

        assertTrue(result.contains("01"));
        assertTrue(result.contains("ชื่อภาษาไทย"));
        assertTrue(result.contains("English Name"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        ThaiCategoryCode entity = new ThaiCategoryCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("ThaiCategoryCode"));
    }
}
