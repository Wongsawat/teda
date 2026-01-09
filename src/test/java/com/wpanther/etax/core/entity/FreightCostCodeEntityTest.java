package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FreightCostCode Entity Tests")
public class FreightCostCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        FreightCostCode entity = new FreightCostCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getCategory());
    }

    @Test
    @DisplayName("Code constructor should normalize code")
    public void testCodeConstructor() {
        FreightCostCode entity = new FreightCostCode("100000");
        assertEquals("100000", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        FreightCostCode entity = new FreightCostCode("  100000  ");
        assertEquals("100000", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        FreightCostCode entity = new FreightCostCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Three-argument constructor should set all fields")
    public void testThreeArgumentConstructor() {
        FreightCostCode entity = new FreightCostCode("100000", "Basic Freight Charge", "Basic Freight");
        assertEquals("100000", entity.getCode());
        assertEquals("Basic Freight Charge", entity.getName());
        assertEquals("Basic Freight", entity.getCategory());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        FreightCostCode entity = new FreightCostCode();
        entity.setCode("  101000  ");
        assertEquals("101000", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        FreightCostCode entity = new FreightCostCode("100000");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        FreightCostCode entity = new FreightCostCode();
        entity.setName("Container Service");
        assertEquals("Container Service", entity.getName());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        FreightCostCode entity = new FreightCostCode();
        entity.setCategory("Container Services");
        assertEquals("Container Services", entity.getCategory());
    }

    @Test
    @DisplayName("SetCodeGroup should set code group")
    public void testSetCodeGroup() {
        FreightCostCode entity = new FreightCostCode();
        entity.setCodeGroup("101");
        assertEquals("101", entity.getCodeGroup());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        FreightCostCode entity = new FreightCostCode("100000");
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
        FreightCostCode entity = new FreightCostCode("100000");
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
    @DisplayName("isBasicFreight should return true for Basic Freight category")
    public void testIsBasicFreight() {
        FreightCostCode entity = new FreightCostCode("100000", "Basic Freight", "Basic Freight");
        assertTrue(entity.isBasicFreight());

        FreightCostCode other = new FreightCostCode("200000", "Surcharge", "Freight Surcharges");
        assertFalse(other.isBasicFreight());
    }

    @Test
    @DisplayName("isBasicFreight should return true for code group 101")
    public void testIsBasicFreightCodeGroup() {
        FreightCostCode entity = new FreightCostCode("101000", "Test", "Other");
        entity.setCodeGroup("101");
        assertTrue(entity.isBasicFreight());
    }

    @Test
    @DisplayName("isFreightSurcharge should return true for Freight Surcharges category")
    public void testIsFreightSurcharge() {
        FreightCostCode entity = new FreightCostCode("200000", "Surcharge", "Freight Surcharges");
        assertTrue(entity.isFreightSurcharge());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isFreightSurcharge());
    }

    @Test
    @DisplayName("isContainerService should return true for Container Services category")
    public void testIsContainerService() {
        FreightCostCode entity = new FreightCostCode("300000", "Container Charge", "Container Services");
        assertTrue(entity.isContainerService());
    }

    @Test
    @DisplayName("isContainerService should return true when name contains 'container'")
    public void testIsContainerServiceByName() {
        FreightCostCode entity = new FreightCostCode("300000", "Container handling fee", "Other");
        assertTrue(entity.isContainerService());

        FreightCostCode entity2 = new FreightCostCode("300001", "CONTAINER SERVICE", "Other");
        assertTrue(entity2.isContainerService());
    }

    @Test
    @DisplayName("isTerminalCharge should return true for Terminal Charges category")
    public void testIsTerminalCharge() {
        FreightCostCode entity = new FreightCostCode("400000", "Terminal Fee", "Terminal Charges");
        assertTrue(entity.isTerminalCharge());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isTerminalCharge());
    }

    @Test
    @DisplayName("isHandlingCharge should return true for Handling Charges category")
    public void testIsHandlingCharge() {
        FreightCostCode entity = new FreightCostCode("500000", "Handling Fee", "Handling Charges");
        assertTrue(entity.isHandlingCharge());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isHandlingCharge());
    }

    @Test
    @DisplayName("isStorageOrDemurrage should return true for Storage & Demurrage category")
    public void testIsStorageOrDemurrage() {
        FreightCostCode entity = new FreightCostCode("600000", "Storage Fee", "Storage & Demurrage");
        assertTrue(entity.isStorageOrDemurrage());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isStorageOrDemurrage());
    }

    @Test
    @DisplayName("isCustomsOrDocumentation should return true for Customs & Documentation category")
    public void testIsCustomsOrDocumentation() {
        FreightCostCode entity = new FreightCostCode("700000", "Customs Fee", "Customs & Documentation");
        assertTrue(entity.isCustomsOrDocumentation());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isCustomsOrDocumentation());
    }

    @Test
    @DisplayName("isDangerousGoods should return true for Dangerous Goods category")
    public void testIsDangerousGoods() {
        FreightCostCode entity = new FreightCostCode("800000", "DG Fee", "Dangerous Goods");
        assertTrue(entity.isDangerousGoods());
    }

    @Test
    @DisplayName("isDangerousGoods should return true when name contains 'dangerous' or 'hazardous'")
    public void testIsDangerousGoodsByName() {
        FreightCostCode entity1 = new FreightCostCode("800000", "Dangerous cargo handling", "Other");
        assertTrue(entity1.isDangerousGoods());

        FreightCostCode entity2 = new FreightCostCode("800001", "HAZARDOUS MATERIAL FEE", "Other");
        assertTrue(entity2.isDangerousGoods());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isDangerousGoods());
    }

    @Test
    @DisplayName("isSpecialFreight should return true for Special Freight category")
    public void testIsSpecialFreight() {
        FreightCostCode entity = new FreightCostCode("900000", "Special Cargo", "Special Freight");
        assertTrue(entity.isSpecialFreight());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isSpecialFreight());
    }

    @Test
    @DisplayName("isInsurance should return true for Insurance category")
    public void testIsInsurance() {
        FreightCostCode entity = new FreightCostCode("110000", "Cargo Insurance", "Insurance");
        assertTrue(entity.isInsurance());
    }

    @Test
    @DisplayName("isInsurance should return true when name contains 'insurance'")
    public void testIsInsuranceByName() {
        FreightCostCode entity = new FreightCostCode("110000", "Cargo insurance premium", "Other");
        assertTrue(entity.isInsurance());

        FreightCostCode other = new FreightCostCode("100000", "Basic", "Basic Freight");
        assertFalse(other.isInsurance());
    }

    @Test
    @DisplayName("getCodeGroup should return first 3 digits of code")
    public void testGetCodeGroup() {
        FreightCostCode entity = new FreightCostCode("101234");
        assertEquals("101", entity.getCodeGroup());

        FreightCostCode entity2 = new FreightCostCode("999999");
        assertEquals("999", entity2.getCodeGroup());
    }

    @Test
    @DisplayName("getCodeGroup should return computed column if set")
    public void testGetCodeGroupComputed() {
        FreightCostCode entity = new FreightCostCode("101234");
        entity.setCodeGroup("101");
        assertEquals("101", entity.getCodeGroup());
    }

    @Test
    @DisplayName("getCodeGroup should handle null code")
    public void testGetCodeGroupNull() {
        FreightCostCode entity = new FreightCostCode();
        assertNull(entity.getCodeGroup());
    }

    @Test
    @DisplayName("getCodeGroup should handle short code")
    public void testGetCodeGroupShort() {
        FreightCostCode entity = new FreightCostCode("12");
        assertNull(entity.getCodeGroup());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        FreightCostCode entity1 = new FreightCostCode("100000", "Name 1", "Category 1");
        FreightCostCode entity2 = new FreightCostCode("100000", "Name 2", "Category 2");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        FreightCostCode entity1 = new FreightCostCode("100000");
        FreightCostCode entity2 = new FreightCostCode("200000");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        FreightCostCode entity = new FreightCostCode("100000");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        FreightCostCode entity = new FreightCostCode("100000");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        FreightCostCode entity = new FreightCostCode("100000");
        String other = "100000";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        FreightCostCode entity1 = new FreightCostCode();
        FreightCostCode entity2 = new FreightCostCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        FreightCostCode entity1 = new FreightCostCode("100000");
        FreightCostCode entity2 = new FreightCostCode("100000");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        FreightCostCode entity = new FreightCostCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code, name, and category")
    public void testToString() {
        FreightCostCode entity = new FreightCostCode("101234", "Basic Freight", "Basic Freight");

        String result = entity.toString();

        assertTrue(result.contains("101234"));
        assertTrue(result.contains("Basic Freight"));
        assertTrue(result.contains("101"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        FreightCostCode entity = new FreightCostCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("FreightCostCode"));
    }
}
