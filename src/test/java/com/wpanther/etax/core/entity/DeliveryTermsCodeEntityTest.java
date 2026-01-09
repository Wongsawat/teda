package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DeliveryTermsCode Entity Tests")
public class DeliveryTermsCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getIncotermGroup());
        assertNull(entity.getSellerObligation());
        assertTrue(entity.getIncoterm());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        DeliveryTermsCode entity = new DeliveryTermsCode("exw");
        assertEquals("EXW", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        DeliveryTermsCode entity = new DeliveryTermsCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        DeliveryTermsCode entity = new DeliveryTermsCode("  fob  ");
        assertEquals("FOB", entity.getCode());
    }

    @Test
    @DisplayName("Code and name constructor should set both fields")
    public void testCodeNameConstructor() {
        DeliveryTermsCode entity = new DeliveryTermsCode("cif", "Cost Insurance and Freight");
        assertEquals("CIF", entity.getCode());
        assertEquals("Cost Insurance and Freight", entity.getName());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setCode("dap");
        assertEquals("DAP", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setCode("  ddp  ");
        assertEquals("DDP", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setName("Free Carrier");
        assertEquals("Free Carrier", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetIncotermGroup should set group")
    public void testSetIncotermGroup() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncotermGroup("E");
        assertEquals("E", entity.getIncotermGroup());
    }

    @Test
    @DisplayName("SetSellerObligation should set obligation")
    public void testSetSellerObligation() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setSellerObligation("Minimum");
        assertEquals("Minimum", entity.getSellerObligation());
    }

    @Test
    @DisplayName("SetIncoterm should set incoterm flag")
    public void testSetIncoterm() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncoterm(false);
        assertFalse(entity.getIncoterm());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
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
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
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
    @DisplayName("isIncoterm should return true when flag is true")
    public void testIsIncoterm() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncoterm(true);
        assertTrue(entity.isIncoterm());

        entity.setIncoterm(false);
        assertFalse(entity.isIncoterm());

        entity.setIncoterm(null);
        assertFalse(entity.isIncoterm());
    }

    @Test
    @DisplayName("isGroupE should return true for Group E")
    public void testIsGroupE() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncotermGroup("E");
        assertTrue(entity.isGroupE());

        entity.setIncotermGroup("F");
        assertFalse(entity.isGroupE());
    }

    @Test
    @DisplayName("isGroupF should return true for Group F")
    public void testIsGroupF() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncotermGroup("F");
        assertTrue(entity.isGroupF());

        entity.setIncotermGroup("E");
        assertFalse(entity.isGroupF());
    }

    @Test
    @DisplayName("isGroupC should return true for Group C")
    public void testIsGroupC() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncotermGroup("C");
        assertTrue(entity.isGroupC());

        entity.setIncotermGroup("D");
        assertFalse(entity.isGroupC());
    }

    @Test
    @DisplayName("isGroupD should return true for Group D")
    public void testIsGroupD() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setIncotermGroup("D");
        assertTrue(entity.isGroupD());

        entity.setIncotermGroup("C");
        assertFalse(entity.isGroupD());
    }

    @Test
    @DisplayName("hasMinimumSellerObligation should return true for Minimum")
    public void testHasMinimumSellerObligation() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setSellerObligation("Minimum");
        assertTrue(entity.hasMinimumSellerObligation());

        entity.setSellerObligation("Low");
        assertFalse(entity.hasMinimumSellerObligation());
    }

    @Test
    @DisplayName("hasLowSellerObligation should return true for Low")
    public void testHasLowSellerObligation() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setSellerObligation("Low");
        assertTrue(entity.hasLowSellerObligation());

        entity.setSellerObligation("Medium");
        assertFalse(entity.hasLowSellerObligation());
    }

    @Test
    @DisplayName("hasMediumSellerObligation should return true for Medium")
    public void testHasMediumSellerObligation() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setSellerObligation("Medium");
        assertTrue(entity.hasMediumSellerObligation());

        entity.setSellerObligation("High");
        assertFalse(entity.hasMediumSellerObligation());
    }

    @Test
    @DisplayName("hasHighSellerObligation should return true for High")
    public void testHasHighSellerObligation() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setSellerObligation("High");
        assertTrue(entity.hasHighSellerObligation());

        entity.setSellerObligation("Maximum");
        assertFalse(entity.hasHighSellerObligation());
    }

    @Test
    @DisplayName("hasMaximumSellerObligation should return true for Maximum")
    public void testHasMaximumSellerObligation() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        entity.setSellerObligation("Maximum");
        assertTrue(entity.hasMaximumSellerObligation());

        entity.setSellerObligation("High");
        assertFalse(entity.hasMaximumSellerObligation());
    }

    @Test
    @DisplayName("includesInsurance should return true for CIF and CIP")
    public void testIncludesInsurance() {
        assertTrue(new DeliveryTermsCode("CIF").includesInsurance());
        assertTrue(new DeliveryTermsCode("CIP").includesInsurance());
        assertFalse(new DeliveryTermsCode("CFR").includesInsurance());
        assertFalse(new DeliveryTermsCode("FOB").includesInsurance());
    }

    @Test
    @DisplayName("includesFreight should return true for CFR, CIF, CPT, CIP")
    public void testIncludesFreight() {
        assertTrue(new DeliveryTermsCode("CFR").includesFreight());
        assertTrue(new DeliveryTermsCode("CIF").includesFreight());
        assertTrue(new DeliveryTermsCode("CPT").includesFreight());
        assertTrue(new DeliveryTermsCode("CIP").includesFreight());
        assertFalse(new DeliveryTermsCode("FOB").includesFreight());
        assertFalse(new DeliveryTermsCode("EXW").includesFreight());
    }

    @Test
    @DisplayName("isSeaTransportOnly should return true for FAS, FOB, CFR, CIF")
    public void testIsSeaTransportOnly() {
        assertTrue(new DeliveryTermsCode("FAS").isSeaTransportOnly());
        assertTrue(new DeliveryTermsCode("FOB").isSeaTransportOnly());
        assertTrue(new DeliveryTermsCode("CFR").isSeaTransportOnly());
        assertTrue(new DeliveryTermsCode("CIF").isSeaTransportOnly());
        assertFalse(new DeliveryTermsCode("EXW").isSeaTransportOnly());
        assertFalse(new DeliveryTermsCode("FCA").isSeaTransportOnly());
    }

    @Test
    @DisplayName("isAnyTransportMode should return true for EXW, FCA, CPT, CIP, DAP, DPU, DDP")
    public void testIsAnyTransportMode() {
        assertTrue(new DeliveryTermsCode("EXW").isAnyTransportMode());
        assertTrue(new DeliveryTermsCode("FCA").isAnyTransportMode());
        assertTrue(new DeliveryTermsCode("CPT").isAnyTransportMode());
        assertTrue(new DeliveryTermsCode("CIP").isAnyTransportMode());
        assertTrue(new DeliveryTermsCode("DAP").isAnyTransportMode());
        assertTrue(new DeliveryTermsCode("DPU").isAnyTransportMode());
        assertTrue(new DeliveryTermsCode("DDP").isAnyTransportMode());
        assertFalse(new DeliveryTermsCode("FOB").isAnyTransportMode());
        assertFalse(new DeliveryTermsCode("CIF").isAnyTransportMode());
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        DeliveryTermsCode entity1 = new DeliveryTermsCode("EXW", "Ex Works");
        DeliveryTermsCode entity2 = new DeliveryTermsCode("EXW", "Different Name");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        DeliveryTermsCode entity1 = new DeliveryTermsCode("EXW");
        DeliveryTermsCode entity2 = new DeliveryTermsCode("FOB");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW");
        String other = "EXW";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        DeliveryTermsCode entity1 = new DeliveryTermsCode();
        DeliveryTermsCode entity2 = new DeliveryTermsCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        DeliveryTermsCode entity1 = new DeliveryTermsCode("EXW");
        DeliveryTermsCode entity2 = new DeliveryTermsCode("EXW");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        DeliveryTermsCode entity = new DeliveryTermsCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and name")
    public void testToString() {
        DeliveryTermsCode entity = new DeliveryTermsCode("EXW", "Ex Works");
        entity.setIncotermGroup("E");
        entity.setSellerObligation("Minimum");
        entity.setIncoterm(true);

        String result = entity.toString();

        assertTrue(result.contains("EXW"));
        assertTrue(result.contains("Ex Works"));
        assertTrue(result.contains("E"));
        assertTrue(result.contains("Minimum"));
        assertTrue(result.contains("true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        DeliveryTermsCode entity = new DeliveryTermsCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("DeliveryTermsCode"));
    }
}
