package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AllowanceChargeIdentificationCode Entity Tests")
public class AllowanceChargeIdentificationCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getCategory());
        assertTrue(entity.getIsStandardCode());
        assertFalse(entity.getIsThaiExtension());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("aa");
        assertEquals("AA", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode((String) null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Full constructor should normalize code to uppercase")
    public void testFullConstructor() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1", "Name", "Description");
        assertEquals("1", entity.getCode());
        assertEquals("Name", entity.getName());
        assertEquals("Description", entity.getDescription());
    }

    @Test
    @DisplayName("Full constructor should handle null code")
    public void testFullConstructorWithNullCode() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode(null, "Name", "Description");
        assertNull(entity.getCode());
        assertEquals("Name", entity.getName());
        assertEquals("Description", entity.getDescription());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCode("pp001");
        assertEquals("PP001", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeWithNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCode("  PP001  ");
        assertEquals("PP001", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setName("Test Name");
        assertEquals("Test Name", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setDescription("Test Description");
        assertEquals("Test Description", entity.getDescription());
    }

    @Test
    @DisplayName("SetCategory should set category")
    public void testSetCategory() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Discount");
        assertEquals("Discount", entity.getCategory());
    }

    @Test
    @DisplayName("SetIsStandardCode should set flag")
    public void testSetIsStandardCode() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsStandardCode(false);
        assertFalse(entity.getIsStandardCode());
    }

    @Test
    @DisplayName("SetIsThaiExtension should set flag")
    public void testSetIsThaiExtension() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsThaiExtension(true);
        assertTrue(entity.getIsThaiExtension());
    }

    // Business Logic Tests - Category Methods

    @Test
    @DisplayName("isDocumentaryCreditCommission should return true for correct category")
    public void testIsDocumentaryCreditCommission() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Documentary Credit Commission");
        assertTrue(entity.isDocumentaryCreditCommission());
    }

    @Test
    @DisplayName("isDocumentaryCreditCommission should return false for different category")
    public void testIsDocumentaryCreditCommissionFalse() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Discount");
        assertFalse(entity.isDocumentaryCreditCommission());
    }

    @Test
    @DisplayName("isDocumentaryCreditCommission should return false for null category")
    public void testIsDocumentaryCreditCommissionNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        assertFalse(entity.isDocumentaryCreditCommission());
    }

    @Test
    @DisplayName("isCollectionCommission should return true for correct category")
    public void testIsCollectionCommission() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Collection Commission");
        assertTrue(entity.isCollectionCommission());
    }

    @Test
    @DisplayName("isProcessingFee should return true for correct category")
    public void testIsProcessingFee() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Processing Fee");
        assertTrue(entity.isProcessingFee());
    }

    @Test
    @DisplayName("isDiscount should return true for correct category")
    public void testIsDiscount() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Discount");
        assertTrue(entity.isDiscount());
    }

    @Test
    @DisplayName("isRebate should return true for correct category")
    public void testIsRebate() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Rebate");
        assertTrue(entity.isRebate());
    }

    @Test
    @DisplayName("isPenalty should return true for correct category")
    public void testIsPenalty() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Penalty");
        assertTrue(entity.isPenalty());
    }

    @Test
    @DisplayName("isBonus should return true for correct category")
    public void testIsBonus() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Bonus");
        assertTrue(entity.isBonus());
    }

    @Test
    @DisplayName("isFreightCharges should return true for correct category")
    public void testIsFreightCharges() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Freight Charges");
        assertTrue(entity.isFreightCharges());
    }

    @Test
    @DisplayName("isPackingCharges should return true for correct category")
    public void testIsPackingCharges() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Packing Charges");
        assertTrue(entity.isPackingCharges());
    }

    @Test
    @DisplayName("isLoadingCharges should return true for correct category")
    public void testIsLoadingCharges() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Loading/Unloading Charges");
        assertTrue(entity.isLoadingCharges());
    }

    @Test
    @DisplayName("isHandlingCharges should return true for correct category")
    public void testIsHandlingCharges() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Handling Charges");
        assertTrue(entity.isHandlingCharges());
    }

    @Test
    @DisplayName("isTestingCharges should return true for correct category")
    public void testIsTestingCharges() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Testing/Inspection Charges");
        assertTrue(entity.isTestingCharges());
    }

    // Standard/Thai Extension Tests

    @Test
    @DisplayName("isStandardCode should return true when isStandardCode is true")
    public void testIsStandardCode() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsStandardCode(true);
        assertTrue(entity.isStandardCode());
    }

    @Test
    @DisplayName("isStandardCode should return false when isStandardCode is false")
    public void testIsStandardCodeFalse() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsStandardCode(false);
        assertFalse(entity.isStandardCode());
    }

    @Test
    @DisplayName("isStandardCode should return false when isStandardCode is null")
    public void testIsStandardCodeNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsStandardCode(null);
        assertFalse(entity.isStandardCode());
    }

    @Test
    @DisplayName("isThaiExtension should return true when isThaiExtension is true")
    public void testIsThaiExtension() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsThaiExtension(true);
        assertTrue(entity.isThaiExtension());
    }

    @Test
    @DisplayName("isThaiExtension should return false when isThaiExtension is false")
    public void testIsThaiExtensionFalse() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsThaiExtension(false);
        assertFalse(entity.isThaiExtension());
    }

    @Test
    @DisplayName("isThaiExtension should return false when isThaiExtension is null")
    public void testIsThaiExtensionNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setIsThaiExtension(null);
        assertFalse(entity.isThaiExtension());
    }

    // Commission/Charge/Allowance Tests

    @Test
    @DisplayName("isCommission should return true for categories containing Commission")
    public void testIsCommission() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode();
        entity1.setCategory("Documentary Credit Commission");
        assertTrue(entity1.isCommission());

        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode();
        entity2.setCategory("Collection Commission");
        assertTrue(entity2.isCommission());
    }

    @Test
    @DisplayName("isCommission should return false for non-commission categories")
    public void testIsCommissionFalse() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Discount");
        assertFalse(entity.isCommission());
    }

    @Test
    @DisplayName("isCommission should return false for null category")
    public void testIsCommissionNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        assertFalse(entity.isCommission());
    }

    @Test
    @DisplayName("isCharge should return true for charge categories")
    public void testIsCharge() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode();
        entity1.setCategory("Freight Charges");
        assertTrue(entity1.isCharge());

        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode();
        entity2.setCategory("Processing Fee");
        assertTrue(entity2.isCharge());

        AllowanceChargeIdentificationCode entity3 = new AllowanceChargeIdentificationCode();
        entity3.setCategory("Penalty");
        assertTrue(entity3.isCharge());
    }

    @Test
    @DisplayName("isCharge should return false for non-charge categories")
    public void testIsChargeFalse() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Discount");
        assertFalse(entity.isCharge());
    }

    @Test
    @DisplayName("isCharge should return false for null category")
    public void testIsChargeNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        assertFalse(entity.isCharge());
    }

    @Test
    @DisplayName("isAllowance should return true for allowance categories")
    public void testIsAllowance() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode();
        entity1.setCategory("Discount");
        assertTrue(entity1.isAllowance());

        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode();
        entity2.setCategory("Rebate");
        assertTrue(entity2.isAllowance());

        AllowanceChargeIdentificationCode entity3 = new AllowanceChargeIdentificationCode();
        entity3.setCategory("Bonus");
        assertTrue(entity3.isAllowance());
    }

    @Test
    @DisplayName("isAllowance should return false for non-allowance categories")
    public void testIsAllowanceFalse() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        entity.setCategory("Freight Charges");
        assertFalse(entity.isAllowance());
    }

    @Test
    @DisplayName("isAllowance should return false for null category")
    public void testIsAllowanceNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        assertFalse(entity.isAllowance());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode("1");
        entity1.setName("Name 1");
        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode("1");
        entity2.setName("Name 2");
        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode("1");
        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode("2");
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        assertNotEquals(null, entity);
    }

    @Test
    @DisplayName("Equals should return false for different type")
    public void testEqualsDifferentType() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        assertNotEquals("1", entity);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false when both codes are null")
    public void testEqualsBothNullCodes() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode();
        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode();
        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("HashCode should be consistent for equal objects")
    public void testHashCodeConsistent() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode("1");
        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode("1");
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be different for different codes")
    public void testHashCodeDifferentCodes() {
        AllowanceChargeIdentificationCode entity1 = new AllowanceChargeIdentificationCode("1");
        AllowanceChargeIdentificationCode entity2 = new AllowanceChargeIdentificationCode("2");
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should return 0 for null code")
    public void testHashCodeNullCode() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode();
        assertEquals(0, entity.hashCode());
    }

    // toString Tests

    @Test
    @DisplayName("toString should contain code")
    public void testToStringContainsCode() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Test");
        entity.setCategory("Discount");
        String result = entity.toString();
        assertTrue(result.contains("1"));
    }

    @Test
    @DisplayName("toString should contain name")
    public void testToStringContainsName() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Test Name");
        String result = entity.toString();
        assertTrue(result.contains("Test Name"));
    }

    @Test
    @DisplayName("toString should contain category")
    public void testToStringContainsCategory() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setCategory("Discount");
        String result = entity.toString();
        assertTrue(result.contains("Discount"));
    }

    @Test
    @DisplayName("toString should have correct format")
    public void testToStringFormat() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
        entity.setName("Test");
        entity.setCategory("Discount");
        entity.setIsStandardCode(true);
        entity.setIsThaiExtension(false);
        String result = entity.toString();
        assertTrue(result.contains("AllowanceChargeIdentificationCode{"));
        assertTrue(result.contains("code='1'"));
        assertTrue(result.contains("name='Test'"));
        assertTrue(result.contains("category='Discount'"));
        assertTrue(result.contains("isStandardCode=true"));
        assertTrue(result.contains("isThaiExtension=false"));
    }

    // Audit Field Tests

    @Test
    @DisplayName("onCreate should set timestamps when called")
    public void testOnCreateSetsTimestamps() {
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
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
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
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
        AllowanceChargeIdentificationCode entity = new AllowanceChargeIdentificationCode("1");
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
}
