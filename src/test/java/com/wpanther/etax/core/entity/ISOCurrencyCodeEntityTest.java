package com.wpanther.etax.core.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ISOCurrencyCode Entity Tests")
public class ISOCurrencyCodeEntityTest {

    // Constructor Tests

    @Test
    @DisplayName("Default constructor should create empty entity")
    public void testDefaultConstructor() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        assertNotNull(entity);
        assertNull(entity.getCode());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getNumericCode());
        assertNull(entity.getMinorUnits());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("Code constructor should normalize to uppercase")
    public void testCodeConstructor() {
        ISOCurrencyCode entity = new ISOCurrencyCode("thb");
        assertEquals("THB", entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should handle null")
    public void testCodeConstructorWithNull() {
        ISOCurrencyCode entity = new ISOCurrencyCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("Code constructor should trim whitespace")
    public void testCodeConstructorTrimsWhitespace() {
        ISOCurrencyCode entity = new ISOCurrencyCode("  usd  ");
        assertEquals("USD", entity.getCode());
    }

    @Test
    @DisplayName("Code and name constructor should set both fields")
    public void testCodeNameConstructor() {
        ISOCurrencyCode entity = new ISOCurrencyCode("usd", "US Dollar");
        assertEquals("USD", entity.getCode());
        assertEquals("US Dollar", entity.getName());
    }

    @Test
    @DisplayName("Full constructor should set all fields")
    public void testFullConstructor() {
        ISOCurrencyCode entity = new ISOCurrencyCode("thb", "Thai Baht", "764", 2);
        assertEquals("THB", entity.getCode());
        assertEquals("Thai Baht", entity.getName());
        assertEquals("764", entity.getNumericCode());
        assertEquals(2, entity.getMinorUnits());
    }

    // Getter/Setter Tests

    @Test
    @DisplayName("SetCode should normalize to uppercase")
    public void testSetCodeNormalizesToUppercase() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setCode("eur");
        assertEquals("EUR", entity.getCode());
    }

    @Test
    @DisplayName("SetCode should handle null")
    public void testSetCodeHandlesNull() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB");
        entity.setCode(null);
        assertNull(entity.getCode());
    }

    @Test
    @DisplayName("SetCode should trim whitespace")
    public void testSetCodeTrimsWhitespace() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setCode("  gbp  ");
        assertEquals("GBP", entity.getCode());
    }

    @Test
    @DisplayName("SetName should set name")
    public void testSetName() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setName("Japanese Yen");
        assertEquals("Japanese Yen", entity.getName());
    }

    @Test
    @DisplayName("SetDescription should set description")
    public void testSetDescription() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setDescription("Test description");
        assertEquals("Test description", entity.getDescription());
    }

    @Test
    @DisplayName("SetNumericCode should set numeric code")
    public void testSetNumericCode() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setNumericCode("840");
        assertEquals("840", entity.getNumericCode());
    }

    @Test
    @DisplayName("SetMinorUnits should set minor units")
    public void testSetMinorUnits() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setMinorUnits(2);
        assertEquals(2, entity.getMinorUnits());
    }

    @Test
    @DisplayName("SetIsActive should set active flag")
    public void testSetIsActive() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        entity.setActive(false);
        assertFalse(entity.isActive());
    }

    // Audit Field Tests

    @Test
    @DisplayName("PrePersist should set timestamps")
    public void testPrePersistSetsTimestamps() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB");
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
        ISOCurrencyCode entity = new ISOCurrencyCode("THB");
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
    @DisplayName("isThaiBasht should return true for THB")
    public void testIsThaiBasht() {
        ISOCurrencyCode thb = new ISOCurrencyCode("THB");
        assertTrue(thb.isThaiBasht());

        ISOCurrencyCode usd = new ISOCurrencyCode("USD");
        assertFalse(usd.isThaiBasht());
    }

    @Test
    @DisplayName("isUSDollar should return true for USD")
    public void testIsUSDollar() {
        ISOCurrencyCode usd = new ISOCurrencyCode("USD");
        assertTrue(usd.isUSDollar());

        ISOCurrencyCode thb = new ISOCurrencyCode("THB");
        assertFalse(thb.isUSDollar());
    }

    @Test
    @DisplayName("isEuro should return true for EUR")
    public void testIsEuro() {
        ISOCurrencyCode eur = new ISOCurrencyCode("EUR");
        assertTrue(eur.isEuro());

        ISOCurrencyCode gbp = new ISOCurrencyCode("GBP");
        assertFalse(gbp.isEuro());
    }

    @Test
    @DisplayName("isMajorCurrency should return true for major currencies")
    public void testIsMajorCurrency() {
        // Test all major currencies
        assertTrue(new ISOCurrencyCode("USD").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("EUR").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("JPY").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("GBP").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("CNY").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("CHF").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("CAD").isMajorCurrency());
        assertTrue(new ISOCurrencyCode("AUD").isMajorCurrency());

        // Test non-major currency
        assertFalse(new ISOCurrencyCode("THB").isMajorCurrency());
        assertFalse(new ISOCurrencyCode("MXN").isMajorCurrency());
    }

    @Test
    @DisplayName("isASEANCurrency should return true for ASEAN currencies")
    public void testIsASEANCurrency() {
        // Test all ASEAN currencies
        assertTrue(new ISOCurrencyCode("THB").isASEANCurrency()); // Thailand
        assertTrue(new ISOCurrencyCode("BND").isASEANCurrency()); // Brunei
        assertTrue(new ISOCurrencyCode("KHR").isASEANCurrency()); // Cambodia
        assertTrue(new ISOCurrencyCode("IDR").isASEANCurrency()); // Indonesia
        assertTrue(new ISOCurrencyCode("LAK").isASEANCurrency()); // Laos
        assertTrue(new ISOCurrencyCode("MYR").isASEANCurrency()); // Malaysia
        assertTrue(new ISOCurrencyCode("MMK").isASEANCurrency()); // Myanmar
        assertTrue(new ISOCurrencyCode("PHP").isASEANCurrency()); // Philippines
        assertTrue(new ISOCurrencyCode("SGD").isASEANCurrency()); // Singapore
        assertTrue(new ISOCurrencyCode("VND").isASEANCurrency()); // Vietnam

        // Test non-ASEAN currency
        assertFalse(new ISOCurrencyCode("USD").isASEANCurrency());
        assertFalse(new ISOCurrencyCode("EUR").isASEANCurrency());
    }

    @Test
    @DisplayName("hasNoDecimalPlaces should return true when minorUnits is 0")
    public void testHasNoDecimalPlaces() {
        ISOCurrencyCode jpy = new ISOCurrencyCode("JPY", "Japanese Yen", "392", 0);
        assertTrue(jpy.hasNoDecimalPlaces());

        ISOCurrencyCode usd = new ISOCurrencyCode("USD", "US Dollar", "840", 2);
        assertFalse(usd.hasNoDecimalPlaces());

        ISOCurrencyCode unknown = new ISOCurrencyCode("XXX");
        assertFalse(unknown.hasNoDecimalPlaces());
    }

    @Test
    @DisplayName("hasThreeDecimalPlaces should return true when minorUnits is 3")
    public void testHasThreeDecimalPlaces() {
        ISOCurrencyCode bhd = new ISOCurrencyCode("BHD", "Bahraini Dinar", "048", 3);
        assertTrue(bhd.hasThreeDecimalPlaces());

        ISOCurrencyCode usd = new ISOCurrencyCode("USD", "US Dollar", "840", 2);
        assertFalse(usd.hasThreeDecimalPlaces());

        ISOCurrencyCode unknown = new ISOCurrencyCode("XXX");
        assertFalse(unknown.hasThreeDecimalPlaces());
    }

    @Test
    @DisplayName("getDecimalPlaces should return minorUnits or default to 2")
    public void testGetDecimalPlaces() {
        ISOCurrencyCode jpy = new ISOCurrencyCode("JPY", "Japanese Yen", "392", 0);
        assertEquals(0, jpy.getDecimalPlaces());

        ISOCurrencyCode usd = new ISOCurrencyCode("USD", "US Dollar", "840", 2);
        assertEquals(2, usd.getDecimalPlaces());

        ISOCurrencyCode bhd = new ISOCurrencyCode("BHD", "Bahraini Dinar", "048", 3);
        assertEquals(3, bhd.getDecimalPlaces());

        ISOCurrencyCode unknown = new ISOCurrencyCode("XXX");
        assertEquals(2, unknown.getDecimalPlaces()); // defaults to 2
    }

    @Test
    @DisplayName("formatAmount should format with correct decimal places")
    public void testFormatAmount() {
        ISOCurrencyCode jpy = new ISOCurrencyCode("JPY", "Japanese Yen", "392", 0);
        assertEquals("1,234 JPY", jpy.formatAmount(1234.0));

        ISOCurrencyCode usd = new ISOCurrencyCode("USD", "US Dollar", "840", 2);
        assertEquals("1,234.56 USD", usd.formatAmount(1234.56));

        ISOCurrencyCode bhd = new ISOCurrencyCode("BHD", "Bahraini Dinar", "048", 3);
        assertEquals("1,234.560 BHD", bhd.formatAmount(1234.56));

        ISOCurrencyCode unknown = new ISOCurrencyCode("XXX");
        assertEquals("1,234.56 XXX", unknown.formatAmount(1234.56)); // defaults to 2 decimals
    }

    // Equals/HashCode Tests

    @Test
    @DisplayName("Equals should return true for same code")
    public void testEqualsSameCode() {
        ISOCurrencyCode entity1 = new ISOCurrencyCode("THB", "Thai Baht");
        ISOCurrencyCode entity2 = new ISOCurrencyCode("THB", "Different Name");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different code")
    public void testEqualsDifferentCode() {
        ISOCurrencyCode entity1 = new ISOCurrencyCode("THB");
        ISOCurrencyCode entity2 = new ISOCurrencyCode("USD");

        assertNotEquals(entity1, entity2);
    }

    @Test
    @DisplayName("Equals should return true for same instance")
    public void testEqualsSameInstance() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB");
        assertEquals(entity, entity);
    }

    @Test
    @DisplayName("Equals should return false for null")
    public void testEqualsNull() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB");
        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Equals should return false for different class")
    public void testEqualsDifferentClass() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB");
        String other = "THB";
        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Equals should handle null code")
    public void testEqualsNullCode() {
        ISOCurrencyCode entity1 = new ISOCurrencyCode();
        ISOCurrencyCode entity2 = new ISOCurrencyCode();

        assertNotEquals(entity1, entity2); // Both have null codes
    }

    @Test
    @DisplayName("HashCode should be same for equal objects")
    public void testHashCodeConsistency() {
        ISOCurrencyCode entity1 = new ISOCurrencyCode("THB");
        ISOCurrencyCode entity2 = new ISOCurrencyCode("THB");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    @DisplayName("HashCode should be 0 for null code")
    public void testHashCodeNullCode() {
        ISOCurrencyCode entity = new ISOCurrencyCode();
        assertEquals(0, entity.hashCode());
    }

    // ToString Tests

    @Test
    @DisplayName("ToString should include code and name")
    public void testToString() {
        ISOCurrencyCode entity = new ISOCurrencyCode("THB", "Thai Baht", "764", 2);
        entity.setActive(true);

        String result = entity.toString();

        assertTrue(result.contains("THB"));
        assertTrue(result.contains("Thai Baht"));
        assertTrue(result.contains("764"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("true"));
    }

    @Test
    @DisplayName("ToString should handle null values")
    public void testToStringWithNulls() {
        ISOCurrencyCode entity = new ISOCurrencyCode();

        String result = entity.toString();

        assertNotNull(result);
        assertTrue(result.contains("ISOCurrencyCode"));
    }
}
