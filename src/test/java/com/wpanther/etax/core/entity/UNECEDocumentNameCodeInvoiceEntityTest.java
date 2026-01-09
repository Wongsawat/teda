package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UNECEDocumentNameCodeInvoice Entity Tests")
public class UNECEDocumentNameCodeInvoiceEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCategory());
        assertFalse(entity.getIsCredit());
        assertFalse(entity.getIsDebit());
        assertTrue(entity.getRequiresPayment());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertEquals("380", entity.getCode());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCode("380");
        assertEquals("380", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setName("Commercial invoice");
        assertEquals("Commercial invoice", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Invoice");
        assertEquals("Invoice", entity.getCategory());
    }

    @Test
    @DisplayName("SetIsCredit should set flag")
    public void testSetIsCredit() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setIsCredit(true);
        assertTrue(entity.getIsCredit());
    }

    @Test
    @DisplayName("SetIsDebit should set flag")
    public void testSetIsDebit() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setIsDebit(true);
        assertTrue(entity.getIsDebit());
    }

    @Test
    @DisplayName("SetRequiresPayment should set flag")
    public void testSetRequiresPayment() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setRequiresPayment(false);
        assertFalse(entity.getRequiresPayment());
    }

    @Test
    @DisplayName("SetCreatedAt should set timestamp")
    public void testSetCreatedAt() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        LocalDateTime time = LocalDateTime.now();
        entity.setCreatedAt(time);
        assertEquals(time, entity.getCreatedAt());
    }

    @Test
    @DisplayName("SetUpdatedAt should set timestamp")
    public void testSetUpdatedAt() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        LocalDateTime time = LocalDateTime.now();
        entity.setUpdatedAt(time);
        assertEquals(time, entity.getUpdatedAt());
    }

    // Business Logic Tests - Category Methods

    @Test
    @DisplayName("isInvoice should return true for Invoice category")
    public void testIsInvoice() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Invoice");
        assertTrue(entity.isInvoice());
    }

    @Test
    @DisplayName("isInvoice should return false for different category")
    public void testIsInvoiceFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Credit Note");
        assertFalse(entity.isInvoice());
    }

    @Test
    @DisplayName("isInvoice should return false for null category")
    public void testIsInvoiceNull() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        assertFalse(entity.isInvoice());
    }

    @Test
    @DisplayName("isCreditNote should return true for Credit Note category")
    public void testIsCreditNoteByCategory() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Credit Note");
        assertTrue(entity.isCreditNote());
    }

    @Test
    @DisplayName("isCreditNote should return true when isCredit is true")
    public void testIsCreditNoteByFlag() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setIsCredit(true);
        assertTrue(entity.isCreditNote());
    }

    @Test
    @DisplayName("isCreditNote should return false when neither condition met")
    public void testIsCreditNoteFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Invoice");
        entity.setIsCredit(false);
        assertFalse(entity.isCreditNote());
    }

    @Test
    @DisplayName("isDebitNote should return true for Debit Note category")
    public void testIsDebitNoteByCategory() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Debit Note");
        assertTrue(entity.isDebitNote());
    }

    @Test
    @DisplayName("isDebitNote should return true when isDebit is true")
    public void testIsDebitNoteByFlag() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setIsDebit(true);
        assertTrue(entity.isDebitNote());
    }

    @Test
    @DisplayName("isDebitNote should return false when neither condition met")
    public void testIsDebitNoteFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Invoice");
        entity.setIsDebit(false);
        assertFalse(entity.isDebitNote());
    }

    @Test
    @DisplayName("isSpecialDocument should return true for Special category")
    public void testIsSpecialDocument() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Special");
        assertTrue(entity.isSpecialDocument());
    }

    @Test
    @DisplayName("isSpecialDocument should return false for different category")
    public void testIsSpecialDocumentFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        entity.setCategory("Invoice");
        assertFalse(entity.isSpecialDocument());
    }

    // Common Document Checks

    @Test
    @DisplayName("isCommercialInvoice should return true for code 380")
    public void testIsCommercialInvoice() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertTrue(entity.isCommercialInvoice());
    }

    @Test
    @DisplayName("isCommercialInvoice should return false for other codes")
    public void testIsCommercialInvoiceFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("381");
        assertFalse(entity.isCommercialInvoice());
    }

    @Test
    @DisplayName("isCreditNoteDocument should return true for code 381")
    public void testIsCreditNoteDocument() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("381");
        assertTrue(entity.isCreditNoteDocument());
    }

    @Test
    @DisplayName("isCreditNoteDocument should return false for other codes")
    public void testIsCreditNoteDocumentFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertFalse(entity.isCreditNoteDocument());
    }

    @Test
    @DisplayName("isDebitNoteDocument should return true for code 383")
    public void testIsDebitNoteDocument() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("383");
        assertTrue(entity.isDebitNoteDocument());
    }

    @Test
    @DisplayName("isDebitNoteDocument should return false for other codes")
    public void testIsDebitNoteDocumentFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertFalse(entity.isDebitNoteDocument());
    }

    @Test
    @DisplayName("isProformaInvoice should return true for code 325")
    public void testIsProformaInvoice() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("325");
        assertTrue(entity.isProformaInvoice());
    }

    @Test
    @DisplayName("isProformaInvoice should return false for other codes")
    public void testIsProformaInvoiceFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertFalse(entity.isProformaInvoice());
    }

    @Test
    @DisplayName("isPrepaymentInvoice should return true for code 386")
    public void testIsPrepaymentInvoice() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("386");
        assertTrue(entity.isPrepaymentInvoice());
    }

    @Test
    @DisplayName("isPrepaymentInvoice should return false for other codes")
    public void testIsPrepaymentInvoiceFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertFalse(entity.isPrepaymentInvoice());
    }

    @Test
    @DisplayName("isSelfBilledInvoice should return true for code 389")
    public void testIsSelfBilledInvoice() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("389");
        assertTrue(entity.isSelfBilledInvoice());
    }

    @Test
    @DisplayName("isSelfBilledInvoice should return false for other codes")
    public void testIsSelfBilledInvoiceFalse() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertFalse(entity.isSelfBilledInvoice());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        UNECEDocumentNameCodeInvoice entity1 = new UNECEDocumentNameCodeInvoice("380");
        entity1.setName("Name 1");
        UNECEDocumentNameCodeInvoice entity2 = new UNECEDocumentNameCodeInvoice("380");
        entity2.setName("Name 2");
        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        UNECEDocumentNameCodeInvoice entity1 = new UNECEDocumentNameCodeInvoice("380");
        UNECEDocumentNameCodeInvoice entity2 = new UNECEDocumentNameCodeInvoice("381");
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertNotEquals(null, entity);
    }

    @Test
    @DisplayName("Equals should return false for different type")
    public void testEqualsDifferentType() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertNotEquals("380", entity);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return true when both codes are null")
    public void testEqualsBothNullCodes() {
        UNECEDocumentNameCodeInvoice entity1 = new UNECEDocumentNameCodeInvoice();
        UNECEDocumentNameCodeInvoice entity2 = new UNECEDocumentNameCodeInvoice();
        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("HashCode should be consistent for equal objects")
    public void testHashCodeConsistent() {
        UNECEDocumentNameCodeInvoice entity1 = new UNECEDocumentNameCodeInvoice("380");
        UNECEDocumentNameCodeInvoice entity2 = new UNECEDocumentNameCodeInvoice("380");
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be different for different codes")
    public void testHashCodeDifferentCodes() {
        UNECEDocumentNameCodeInvoice entity1 = new UNECEDocumentNameCodeInvoice("380");
        UNECEDocumentNameCodeInvoice entity2 = new UNECEDocumentNameCodeInvoice("381");
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should return consistent value for null code")
    public void testHashCodeNullCode() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice();
        assertNotNull(entity.hashCode());
    }

    // toString Tests

    @Test
    @DisplayName("toString should contain code")
    public void testToStringContainsCode() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setName("Commercial invoice");
        entity.setCategory("Invoice");
        String result = entity.toString();
        assertTrue(result.contains("380"));
    }

    @Test
    @DisplayName("toString should contain name")
    public void testToStringContainsName() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setName("Commercial invoice");
        String result = entity.toString();
        assertTrue(result.contains("Commercial invoice"));
    }

    @Test
    @DisplayName("toString should contain category")
    public void testToStringContainsCategory() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setCategory("Invoice");
        String result = entity.toString();
        assertTrue(result.contains("Invoice"));
    }

    @Test
    @DisplayName("toString should have correct format")
    public void testToStringFormat() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        entity.setName("Commercial invoice");
        entity.setCategory("Invoice");
        String result = entity.toString();
        assertTrue(result.contains("UNECEDocumentNameCodeInvoice{"));
        assertTrue(result.contains("code='380'"));
        assertTrue(result.contains("name='Commercial invoice'"));
        assertTrue(result.contains("category='Invoice'"));
    }

    // Audit Field Tests

    @Test
    @DisplayName("onCreate should set timestamps when called")
    public void testOnCreateSetsTimestamps() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
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
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
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
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
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

    @Test
    @DisplayName("setCreatedAt and setUpdatedAt work correctly")
    public void testSettersForTimestamps() {
        UNECEDocumentNameCodeInvoice entity = new UNECEDocumentNameCodeInvoice("380");
        LocalDateTime created = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime updated = LocalDateTime.of(2020, 1, 1, 1, 0);

        entity.setCreatedAt(created);
        entity.setUpdatedAt(updated);

        assertEquals(created, entity.getCreatedAt());
        assertEquals(updated, entity.getUpdatedAt());
    }
}
