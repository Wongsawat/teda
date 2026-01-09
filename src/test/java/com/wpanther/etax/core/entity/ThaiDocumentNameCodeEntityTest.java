package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ThaiDocumentNameCode Entity Tests")
public class ThaiDocumentNameCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getNameTh());
        assertNull(entity.getNameEn());
        assertNull(entity.getDescription());
        assertFalse(entity.getStandardCode());
        assertFalse(entity.getThaiExtension());
    }

    @Test
    @DisplayName("Code constructor should set code")
    public void testCodeConstructor() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        assertEquals("388", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set code, nameEn, and description")
    public void testThreeArgumentConstructor() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode(
            "388",
            "Tax Invoice",
            "Tax invoice for VAT registered businesses"
        );
        assertEquals("388", entity.getCode());
        assertEquals("Tax Invoice", entity.getNameEn());
        assertEquals("Tax invoice for VAT registered businesses", entity.getDescription());
        assertNull(entity.getNameTh());
    }

    @Test
    @DisplayName("Four-argument constructor should set all name fields")
    public void testFourArgumentConstructor() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode(
            "388",
            "ใบกำกับภาษี",
            "Tax Invoice",
            "Tax invoice for VAT registered businesses"
        );
        assertEquals("388", entity.getCode());
        assertEquals("ใบกำกับภาษี", entity.getNameTh());
        assertEquals("Tax Invoice", entity.getNameEn());
        assertEquals("Tax invoice for VAT registered businesses", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should set code")
    public void testSetCode() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setCode("80");
        assertEquals("80", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetNameTh should set Thai name")
    public void testSetNameTh() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setNameTh("ใบกำกับภาษี");
        assertEquals("ใบกำกับภาษี", entity.getNameTh());
    }

    @Test
    @DisplayName("SetNameEn should set English name")
    public void testSetNameEn() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setNameEn("Tax Invoice");
        assertEquals("Tax Invoice", entity.getNameEn());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetStandardCode should set standard code flag")
    public void testSetStandardCode() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setStandardCode(true);
        assertTrue(entity.getStandardCode());
    }

    @Test
    @DisplayName("SetThaiExtension should set Thai extension flag")
    public void testSetThaiExtension() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        entity.setThaiExtension(true);
        assertTrue(entity.getThaiExtension());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
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
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
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
    @DisplayName("isDebitNote should return true for code 80")
    public void testIsDebitNote() {
        ThaiDocumentNameCode debitNote = new ThaiDocumentNameCode("80");
        assertTrue(debitNote.isDebitNote());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("81");
        assertFalse(other.isDebitNote());
    }

    @Test
    @DisplayName("isCreditNote should return true for code 81")
    public void testIsCreditNote() {
        ThaiDocumentNameCode creditNote = new ThaiDocumentNameCode("81");
        assertTrue(creditNote.isCreditNote());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("80");
        assertFalse(other.isCreditNote());
    }

    @Test
    @DisplayName("isCommercialInvoice should return true for code 380")
    public void testIsCommercialInvoice() {
        ThaiDocumentNameCode commercial = new ThaiDocumentNameCode("380");
        assertTrue(commercial.isCommercialInvoice());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("388");
        assertFalse(other.isCommercialInvoice());
    }

    @Test
    @DisplayName("isTaxInvoice should return true when nameEn contains 'Tax Invoice'")
    public void testIsTaxInvoice() {
        ThaiDocumentNameCode taxInvoice1 = new ThaiDocumentNameCode("388", "Tax Invoice", "Description");
        assertTrue(taxInvoice1.isTaxInvoice());

        ThaiDocumentNameCode taxInvoice2 = new ThaiDocumentNameCode("T01", "Abbreviated tax invoice", "Description");
        assertTrue(taxInvoice2.isTaxInvoice());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("80", "Receipt", "Description");
        assertFalse(other.isTaxInvoice());
    }

    @Test
    @DisplayName("isTaxInvoice should handle null nameEn")
    public void testIsTaxInvoiceWithNullName() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        assertFalse(entity.isTaxInvoice());
    }

    @Test
    @DisplayName("isReceipt should return true when nameEn contains 'Receipt'")
    public void testIsReceipt() {
        ThaiDocumentNameCode receipt1 = new ThaiDocumentNameCode("80", "Receipt", "Description");
        assertTrue(receipt1.isReceipt());

        ThaiDocumentNameCode receipt2 = new ThaiDocumentNameCode("T02", "Tax receipt", "Description");
        assertTrue(receipt2.isReceipt());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("388", "Tax Invoice", "Description");
        assertFalse(other.isReceipt());
    }

    @Test
    @DisplayName("isReceipt should handle null nameEn")
    public void testIsReceiptWithNullName() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("80");
        assertFalse(entity.isReceipt());
    }

    @Test
    @DisplayName("isAbbreviatedTaxInvoice should return true for codes T05 and T06")
    public void testIsAbbreviatedTaxInvoice() {
        ThaiDocumentNameCode t05 = new ThaiDocumentNameCode("T05");
        assertTrue(t05.isAbbreviatedTaxInvoice());

        ThaiDocumentNameCode t06 = new ThaiDocumentNameCode("T06");
        assertTrue(t06.isAbbreviatedTaxInvoice());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("T07");
        assertFalse(other.isAbbreviatedTaxInvoice());

        ThaiDocumentNameCode standard = new ThaiDocumentNameCode("388");
        assertFalse(standard.isAbbreviatedTaxInvoice());
    }

    @Test
    @DisplayName("isCancellationNote should return true for code T07")
    public void testIsCancellationNote() {
        ThaiDocumentNameCode cancellation = new ThaiDocumentNameCode("T07");
        assertTrue(cancellation.isCancellationNote());

        ThaiDocumentNameCode other = new ThaiDocumentNameCode("T06");
        assertFalse(other.isCancellationNote());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ThaiDocumentNameCode entity1 = new ThaiDocumentNameCode("388", "Tax Invoice", "Description");
        ThaiDocumentNameCode entity2 = new ThaiDocumentNameCode("388", "Different Name", "Different Description");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ThaiDocumentNameCode entity1 = new ThaiDocumentNameCode("388");
        ThaiDocumentNameCode entity2 = new ThaiDocumentNameCode("80");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388");
        String other = "388";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        ThaiDocumentNameCode entity1 = new ThaiDocumentNameCode();
        ThaiDocumentNameCode entity2 = new ThaiDocumentNameCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        ThaiDocumentNameCode entity1 = new ThaiDocumentNameCode("388");
        ThaiDocumentNameCode entity2 = new ThaiDocumentNameCode("388");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and names")
    public void testToString() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode("388", "ใบกำกับภาษี", "Tax Invoice", "Description");
        entity.setStandardCode(true);
        entity.setThaiExtension(false);

        String result = entity.toString();

        assertTrue(result.contains("388"));
        assertTrue(result.contains("ใบกำกับภาษี"));
        assertTrue(result.contains("Tax Invoice"));
        assertTrue(result.contains("true"));
        assertTrue(result.contains("false"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        ThaiDocumentNameCode entity = new ThaiDocumentNameCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("ThaiDocumentNameCode"));
    }
}
