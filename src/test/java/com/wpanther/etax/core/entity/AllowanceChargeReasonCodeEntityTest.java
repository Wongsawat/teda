package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AllowanceChargeReasonCode Entity Tests")
public class AllowanceChargeReasonCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCategory());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("zzz");
        assertEquals("ZZZ", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("  1  ");
        assertEquals("1", entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode(
            "1",
            "Damaged goods",
            "Goods are damaged"
        );
        assertEquals("1", entity.getCode());
        assertEquals("Damaged goods", entity.getName());
        assertEquals("Goods are damaged", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCode("zzz");
        assertEquals("ZZZ", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCode("  10  ");
        assertEquals("10", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setName("Test Name");
        assertEquals("Test Name", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Quality Issue");
        assertEquals("Quality Issue", entity.getCategory());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
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
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
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
    @DisplayName("isQualityIssue should return true for Quality Issue category")
    public void testIsQualityIssue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Quality Issue");
        assertTrue(entity.isQualityIssue());

        entity.setCategory("Delivery Issue");
        assertFalse(entity.isQualityIssue());
    }

    @Test
    @DisplayName("isDeliveryIssue should return true for Delivery Issue category")
    public void testIsDeliveryIssue() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Delivery Issue");
        assertTrue(entity.isDeliveryIssue());

        entity.setCategory("Quality Issue");
        assertFalse(entity.isDeliveryIssue());
    }

    @Test
    @DisplayName("isAdministrativeError should return true for Administrative Error category")
    public void testIsAdministrativeError() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Administrative Error");
        assertTrue(entity.isAdministrativeError());

        entity.setCategory("Quality Issue");
        assertFalse(entity.isAdministrativeError());
    }

    @Test
    @DisplayName("isDiscountOrAllowance should return true for Discount/Allowance category")
    public void testIsDiscountOrAllowance() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Discount/Allowance");
        assertTrue(entity.isDiscountOrAllowance());

        entity.setCategory("Financial Charges");
        assertFalse(entity.isDiscountOrAllowance());
    }

    @Test
    @DisplayName("isFinancialCharge should return true for Financial Charges category")
    public void testIsFinancialCharge() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Financial Charges");
        assertTrue(entity.isFinancialCharge());

        entity.setCategory("Discount/Allowance");
        assertFalse(entity.isFinancialCharge());
    }

    @Test
    @DisplayName("isClaimOrDispute should return true for Claims/Disputes category")
    public void testIsClaimOrDispute() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Claims/Disputes");
        assertTrue(entity.isClaimOrDispute());

        entity.setCategory("Freight/Logistics");
        assertFalse(entity.isClaimOrDispute());
    }

    @Test
    @DisplayName("isFreightOrLogistics should return true for Freight/Logistics category")
    public void testIsFreightOrLogistics() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Freight/Logistics");
        assertTrue(entity.isFreightOrLogistics());

        entity.setCategory("Payment Terms");
        assertFalse(entity.isFreightOrLogistics());
    }

    @Test
    @DisplayName("isPaymentTerms should return true for Payment Terms category")
    public void testIsPaymentTerms() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("Payment Terms");
        assertTrue(entity.isPaymentTerms());

        entity.setCategory("HR Related");
        assertFalse(entity.isPaymentTerms());
    }

    @Test
    @DisplayName("isHRRelated should return true for HR Related category")
    public void testIsHRRelated() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        entity.setCategory("HR Related");
        assertTrue(entity.isHRRelated());

        entity.setCategory("Payment Terms");
        assertFalse(entity.isHRRelated());
    }

    @Test
    @DisplayName("isMutuallyDefined should return true for code ZZZ")
    public void testIsMutuallyDefined() {
        assertTrue(new AllowanceChargeReasonCode("ZZZ").isMutuallyDefined());
        assertTrue(new AllowanceChargeReasonCode("zzz").isMutuallyDefined()); // normalized to ZZZ
        assertFalse(new AllowanceChargeReasonCode("1").isMutuallyDefined());
        assertFalse(new AllowanceChargeReasonCode(null).isMutuallyDefined());
    }

    @Test
    @DisplayName("isCustomOrOther should return true for Custom/Other or Other category")
    public void testIsCustomOrOther() {
        AllowanceChargeReasonCode entity1 = new AllowanceChargeReasonCode();
        entity1.setCategory("Custom/Other");
        assertTrue(entity1.isCustomOrOther());

        AllowanceChargeReasonCode entity2 = new AllowanceChargeReasonCode();
        entity2.setCategory("Other");
        assertTrue(entity2.isCustomOrOther());

        AllowanceChargeReasonCode entity3 = new AllowanceChargeReasonCode();
        entity3.setCategory("Quality Issue");
        assertFalse(entity3.isCustomOrOther());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        AllowanceChargeReasonCode entity1 = new AllowanceChargeReasonCode("1", "Name1", "Desc1");
        AllowanceChargeReasonCode entity2 = new AllowanceChargeReasonCode("1", "Name2", "Desc2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        AllowanceChargeReasonCode entity1 = new AllowanceChargeReasonCode("1");
        AllowanceChargeReasonCode entity2 = new AllowanceChargeReasonCode("2");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1");
        String other = "1";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        AllowanceChargeReasonCode entity1 = new AllowanceChargeReasonCode();
        AllowanceChargeReasonCode entity2 = new AllowanceChargeReasonCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        AllowanceChargeReasonCode entity1 = new AllowanceChargeReasonCode("1");
        AllowanceChargeReasonCode entity2 = new AllowanceChargeReasonCode("1");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code, name and category")
    public void testToString() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode("1", "Damaged goods", "Description");
        entity.setCategory("Quality Issue");

        String result = entity.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("Damaged goods"));
        assertTrue(result.contains("Quality Issue"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        AllowanceChargeReasonCode entity = new AllowanceChargeReasonCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("AllowanceChargeReasonCode"));
    }
}
