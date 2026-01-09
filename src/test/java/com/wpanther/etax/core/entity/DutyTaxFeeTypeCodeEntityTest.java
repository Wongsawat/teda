package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DutyTaxFeeTypeCode Entity Tests")
public class DutyTaxFeeTypeCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCategory());
        assertFalse(entity.isVat());
        assertFalse(entity.isExempt());
        assertFalse(entity.isSummary());
        assertTrue(entity.getActive());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("vat");
        assertEquals("VAT", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("  gst  ");
        assertEquals("GST", entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode(
            "VAT",
            "Value Added Tax",
            "Standard VAT"
        );
        assertEquals("VAT", entity.getCode());
        assertEquals("Value Added Tax", entity.getName());
        assertEquals("Standard VAT", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCode("exe");
        assertEquals("EXE", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCode("  aaa  ");
        assertEquals("AAA", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setName("Test Name");
        assertEquals("Test Name", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCategory("VAT");
        assertEquals("VAT", entity.getCategory());
    }

    @Test
    @DisplayName("SetVat should set VAT flag")
    public void testSetVat() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setVat(true);
        assertTrue(entity.isVat());
    }

    @Test
    @DisplayName("SetExempt should set exempt flag")
    public void testSetExempt() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setExempt(true);
        assertTrue(entity.isExempt());
    }

    @Test
    @DisplayName("SetSummary should set summary flag")
    public void testSetSummary() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setSummary(true);
        assertTrue(entity.isSummary());
    }

    @Test
    @DisplayName("SetActive should set active flag")
    public void testSetActive() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setActive(false);
        assertFalse(entity.getActive());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
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
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
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
    @DisplayName("isVat should return true when flag is true")
    public void testIsVat() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setVat(true);
        assertTrue(entity.isVat());

        entity.setVat(false);
        assertFalse(entity.isVat());

        entity.setVat(null);
        assertFalse(entity.isVat());
    }

    @Test
    @DisplayName("isExempt should return true when flag is true")
    public void testIsExempt() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setExempt(true);
        assertTrue(entity.isExempt());

        entity.setExempt(false);
        assertFalse(entity.isExempt());

        entity.setExempt(null);
        assertFalse(entity.isExempt());
    }

    @Test
    @DisplayName("isSummary should return true when flag is true")
    public void testIsSummary() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setSummary(true);
        assertTrue(entity.isSummary());

        entity.setSummary(false);
        assertFalse(entity.isSummary());

        entity.setSummary(null);
        assertFalse(entity.isSummary());
    }

    @Test
    @DisplayName("isCustomsDuty should return true for Customs category")
    public void testIsCustomsDuty() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCategory("Customs");
        assertTrue(entity.isCustomsDuty());

        entity.setCategory("VAT");
        assertFalse(entity.isCustomsDuty());
    }

    @Test
    @DisplayName("isExciseTax should return true for Excise category")
    public void testIsExciseTax() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCategory("Excise");
        assertTrue(entity.isExciseTax());

        entity.setCategory("Customs");
        assertFalse(entity.isExciseTax());
    }

    @Test
    @DisplayName("isGST should return true for GST category")
    public void testIsGST() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCategory("GST");
        assertTrue(entity.isGST());

        entity.setCategory("VAT");
        assertFalse(entity.isGST());
    }

    @Test
    @DisplayName("isSpecialTax should return true for Special Tax category")
    public void testIsSpecialTax() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        entity.setCategory("Special Tax");
        assertTrue(entity.isSpecialTax());

        entity.setCategory("VAT");
        assertFalse(entity.isSpecialTax());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        DutyTaxFeeTypeCode entity1 = new DutyTaxFeeTypeCode("VAT", "Value Added Tax", "Desc1");
        DutyTaxFeeTypeCode entity2 = new DutyTaxFeeTypeCode("VAT", "Different Name", "Desc2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        DutyTaxFeeTypeCode entity1 = new DutyTaxFeeTypeCode("VAT");
        DutyTaxFeeTypeCode entity2 = new DutyTaxFeeTypeCode("GST");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT");
        String other = "VAT";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        DutyTaxFeeTypeCode entity1 = new DutyTaxFeeTypeCode();
        DutyTaxFeeTypeCode entity2 = new DutyTaxFeeTypeCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        DutyTaxFeeTypeCode entity1 = new DutyTaxFeeTypeCode("VAT");
        DutyTaxFeeTypeCode entity2 = new DutyTaxFeeTypeCode("VAT");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include all relevant fields")
    public void testToString() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode("VAT", "Value Added Tax", "Description");
        entity.setCategory("VAT");
        entity.setVat(true);
        entity.setExempt(false);

        String result = entity.toString();

        assertTrue(result.contains("VAT"));
        assertTrue(result.contains("Value Added Tax"));
        assertTrue(result.contains("isVat=true"));
        assertTrue(result.contains("isExempt=false"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        DutyTaxFeeTypeCode entity = new DutyTaxFeeTypeCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("DutyTaxFeeTypeCode"));
    }
}
