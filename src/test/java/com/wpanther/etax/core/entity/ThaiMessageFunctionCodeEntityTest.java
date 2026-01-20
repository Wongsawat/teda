package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ThaiMessageFunctionCode Entity Tests")
public class ThaiMessageFunctionCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getDescriptionEn());
        assertNull(entity.getDescriptionTh());
        assertNull(entity.getDocumentType());
        assertNull(entity.getCategory());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
        assertEquals("DBNG01", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Full constructor should set all fields")
    public void testFullConstructor() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode(
            "DBNG01",
            "Original Debit Note - Goods",
            "ใบเพิ่มหนี้ - สินค้าฉบับจริง",
            "DebitNote",
            "Goods"
        );
        assertEquals("DBNG01", entity.getCode());
        assertEquals("Original Debit Note - Goods", entity.getDescriptionEn());
        assertEquals("ใบเพิ่มหนี้ - สินค้าฉบับจริง", entity.getDescriptionTh());
        assertEquals("DebitNote", entity.getDocumentType());
        assertEquals("Goods", entity.getCategory());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setCode("CDNG01");
        assertEquals("CDNG01", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetDescriptionEn should set English description")
    public void testSetDescriptionEn() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setDescriptionEn("Test description");
        assertEquals("Test description", entity.getDescriptionEn());
    }

    @Test
    @DisplayName("SetDescriptionTh should set Thai description")
    public void testSetDescriptionTh() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setDescriptionTh("คำอธิบายภาษาไทย");
        assertEquals("คำอธิบายภาษาไทย", entity.getDescriptionTh());
    }

    @Test
    @DisplayName("SetDocumentType should set document type")
    public void testSetDocumentType() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setDocumentType("TaxInvoice");
        assertEquals("TaxInvoice", entity.getDocumentType());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setCategory("Service");
        assertEquals("Service", entity.getCategory());
    }

    @Test
    @DisplayName("SetActive should set active flag")
    public void testSetActive() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        entity.setActive(false);
        assertFalse(entity.isActive());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
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
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
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
    @DisplayName("isDebitNote should return true for DebitNote document type")
    public void testIsDebitNote() {
        ThaiMessageFunctionCode debitNote = new ThaiMessageFunctionCode();
        debitNote.setDocumentType("DebitNote");
        assertTrue(debitNote.isDebitNote());

        ThaiMessageFunctionCode other = new ThaiMessageFunctionCode();
        other.setDocumentType("CreditNote");
        assertFalse(other.isDebitNote());
    }

    @Test
    @DisplayName("isCreditNote should return true for CreditNote document type")
    public void testIsCreditNote() {
        ThaiMessageFunctionCode creditNote = new ThaiMessageFunctionCode();
        creditNote.setDocumentType("CreditNote");
        assertTrue(creditNote.isCreditNote());

        ThaiMessageFunctionCode other = new ThaiMessageFunctionCode();
        other.setDocumentType("DebitNote");
        assertFalse(other.isCreditNote());
    }

    @Test
    @DisplayName("isTaxInvoice should return true for TaxInvoice document type")
    public void testIsTaxInvoice() {
        ThaiMessageFunctionCode taxInvoice = new ThaiMessageFunctionCode();
        taxInvoice.setDocumentType("TaxInvoice");
        assertTrue(taxInvoice.isTaxInvoice());

        ThaiMessageFunctionCode other = new ThaiMessageFunctionCode();
        other.setDocumentType("Receipt");
        assertFalse(other.isTaxInvoice());
    }

    @Test
    @DisplayName("isReceipt should return true for Receipt document type")
    public void testIsReceipt() {
        ThaiMessageFunctionCode receipt = new ThaiMessageFunctionCode();
        receipt.setDocumentType("Receipt");
        assertTrue(receipt.isReceipt());

        ThaiMessageFunctionCode other = new ThaiMessageFunctionCode();
        other.setDocumentType("TaxInvoice");
        assertFalse(other.isReceipt());
    }

    @Test
    @DisplayName("isGoods should return true for Goods category")
    public void testIsGoods() {
        ThaiMessageFunctionCode goods = new ThaiMessageFunctionCode();
        goods.setCategory("Goods");
        assertTrue(goods.isGoods());

        ThaiMessageFunctionCode other = new ThaiMessageFunctionCode();
        other.setCategory("Service");
        assertFalse(other.isGoods());
    }

    @Test
    @DisplayName("isService should return true for Service category")
    public void testIsService() {
        ThaiMessageFunctionCode service = new ThaiMessageFunctionCode();
        service.setCategory("Service");
        assertTrue(service.isService());

        ThaiMessageFunctionCode other = new ThaiMessageFunctionCode();
        other.setCategory("Goods");
        assertFalse(other.isService());
    }

    @Test
    @DisplayName("isOriginal should return true for codes ending with 01")
    public void testIsOriginal() {
        assertTrue(new ThaiMessageFunctionCode("DBNG01").isOriginal());
        assertTrue(new ThaiMessageFunctionCode("CDNS01").isOriginal());
        assertFalse(new ThaiMessageFunctionCode("DBNG02").isOriginal());
        assertFalse(new ThaiMessageFunctionCode("CDNG03").isOriginal());
    }

    @Test
    @DisplayName("isReplacement should return true for codes ending with 02")
    public void testIsReplacement() {
        assertTrue(new ThaiMessageFunctionCode("DBNG02").isReplacement());
        assertTrue(new ThaiMessageFunctionCode("CDNS02").isReplacement());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isReplacement());
        assertFalse(new ThaiMessageFunctionCode("CDNG03").isReplacement());
    }

    @Test
    @DisplayName("isCancellation should return true for codes ending with 03")
    public void testIsCancellation() {
        assertTrue(new ThaiMessageFunctionCode("DBNG03").isCancellation());
        assertTrue(new ThaiMessageFunctionCode("CDNS03").isCancellation());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isCancellation());
        assertFalse(new ThaiMessageFunctionCode("CDNG02").isCancellation());
    }

    @Test
    @DisplayName("isCopy should return true for codes ending with 04")
    public void testIsCopy() {
        assertTrue(new ThaiMessageFunctionCode("DBNG04").isCopy());
        assertTrue(new ThaiMessageFunctionCode("CDNS04").isCopy());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isCopy());
        assertFalse(new ThaiMessageFunctionCode("CDNG03").isCopy());
    }

    @Test
    @DisplayName("isAddition should return true for codes ending with 05")
    public void testIsAddition() {
        assertTrue(new ThaiMessageFunctionCode("DBNG05").isAddition());
        assertTrue(new ThaiMessageFunctionCode("CDNS05").isAddition());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isAddition());
        assertFalse(new ThaiMessageFunctionCode("CDNG03").isAddition());
    }

    @Test
    @DisplayName("isOther should return true for codes ending with 99")
    public void testIsOther() {
        assertTrue(new ThaiMessageFunctionCode("DBNG99").isOther());
        assertTrue(new ThaiMessageFunctionCode("CDNS99").isOther());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isOther());
        assertFalse(new ThaiMessageFunctionCode("CDNG03").isOther());
    }

    @Test
    @DisplayName("getDocumentPrefix should return first 4 characters")
    public void testGetDocumentPrefix() {
        assertEquals("DBNG", new ThaiMessageFunctionCode("DBNG01").getDocumentPrefix());
        assertEquals("CDNS", new ThaiMessageFunctionCode("CDNS02").getDocumentPrefix());
        assertEquals("TIVC", new ThaiMessageFunctionCode("TIVC01").getDocumentPrefix());
        assertNull(new ThaiMessageFunctionCode("ABC").getDocumentPrefix());
        assertNull(new ThaiMessageFunctionCode(null).getDocumentPrefix());
    }

    @Test
    @DisplayName("getFunctionSuffix should return last 2 characters")
    public void testGetFunctionSuffix() {
        assertEquals("01", new ThaiMessageFunctionCode("DBNG01").getFunctionSuffix());
        assertEquals("02", new ThaiMessageFunctionCode("CDNS02").getFunctionSuffix());
        assertEquals("99", new ThaiMessageFunctionCode("TIVC99").getFunctionSuffix());
        assertNull(new ThaiMessageFunctionCode("ABCDE").getFunctionSuffix());
        assertNull(new ThaiMessageFunctionCode(null).getFunctionSuffix());
    }

    @Test
    @DisplayName("isDebitNoteGoods should return true for DBNG prefix")
    public void testIsDebitNoteGoods() {
        assertTrue(new ThaiMessageFunctionCode("DBNG01").isDebitNoteGoods());
        assertTrue(new ThaiMessageFunctionCode("DBNG99").isDebitNoteGoods());
        assertFalse(new ThaiMessageFunctionCode("DBNS01").isDebitNoteGoods());
        assertFalse(new ThaiMessageFunctionCode("CDNG01").isDebitNoteGoods());
    }

    @Test
    @DisplayName("isDebitNoteServices should return true for DBNS prefix")
    public void testIsDebitNoteServices() {
        assertTrue(new ThaiMessageFunctionCode("DBNS01").isDebitNoteServices());
        assertTrue(new ThaiMessageFunctionCode("DBNS99").isDebitNoteServices());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isDebitNoteServices());
        assertFalse(new ThaiMessageFunctionCode("CDNS01").isDebitNoteServices());
    }

    @Test
    @DisplayName("isCreditNoteGoods should return true for CDNG prefix")
    public void testIsCreditNoteGoods() {
        assertTrue(new ThaiMessageFunctionCode("CDNG01").isCreditNoteGoods());
        assertTrue(new ThaiMessageFunctionCode("CDNG99").isCreditNoteGoods());
        assertFalse(new ThaiMessageFunctionCode("CDNS01").isCreditNoteGoods());
        assertFalse(new ThaiMessageFunctionCode("DBNG01").isCreditNoteGoods());
    }

    @Test
    @DisplayName("isCreditNoteServices should return true for CDNS prefix")
    public void testIsCreditNoteServices() {
        assertTrue(new ThaiMessageFunctionCode("CDNS01").isCreditNoteServices());
        assertTrue(new ThaiMessageFunctionCode("CDNS99").isCreditNoteServices());
        assertFalse(new ThaiMessageFunctionCode("CDNG01").isCreditNoteServices());
        assertFalse(new ThaiMessageFunctionCode("DBNS01").isCreditNoteServices());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ThaiMessageFunctionCode entity1 = new ThaiMessageFunctionCode("DBNG01", "Desc1", "คำอธิบาย1", "DebitNote", "Goods");
        ThaiMessageFunctionCode entity2 = new ThaiMessageFunctionCode("DBNG01", "Desc2", "คำอธิบาย2", "CreditNote", "Service");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ThaiMessageFunctionCode entity1 = new ThaiMessageFunctionCode("DBNG01");
        ThaiMessageFunctionCode entity2 = new ThaiMessageFunctionCode("CDNG01");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01");
        String other = "DBNG01";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        ThaiMessageFunctionCode entity1 = new ThaiMessageFunctionCode();
        ThaiMessageFunctionCode entity2 = new ThaiMessageFunctionCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        ThaiMessageFunctionCode entity1 = new ThaiMessageFunctionCode("DBNG01");
        ThaiMessageFunctionCode entity2 = new ThaiMessageFunctionCode("DBNG01");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include all fields")
    public void testToString() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode("DBNG01", "Original Debit Note", "ใบเพิ่มหนี้", "DebitNote", "Goods");
        entity.setActive(true);

        String result = entity.toString();

        assertTrue(result.contains("DBNG01"));
        assertTrue(result.contains("Original Debit Note"));
        assertTrue(result.contains("DebitNote"));
        assertTrue(result.contains("Goods"));
        assertTrue(result.contains("true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        ThaiMessageFunctionCode entity = new ThaiMessageFunctionCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("ThaiMessageFunctionCode"));
    }
}
