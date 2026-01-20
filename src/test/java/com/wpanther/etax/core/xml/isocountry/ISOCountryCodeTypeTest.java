package com.wpanther.etax.core.xml.isocountry;

import com.wpanther.etax.core.entity.ISOCountryCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ISOCountryCodeType.
 */
@DisplayName("ISOCountryCodeType Tests")
class ISOCountryCodeTypeTest {

    // Constructor tests

    @Test
    @DisplayName("Default constructor should create null value")
    void testDefaultConstructor() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.getValue()).isNull();
        assertThat(type.getCode()).isNull();
        assertThat(type.getName()).isNull();
    }

    @Test
    @DisplayName("Entity constructor should wrap entity")
    void testEntityConstructor() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOCountryCodeType type = new ISOCountryCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("TH");
        assertThat(type.getName()).isEqualTo("Thailand");
    }

    @Test
    @DisplayName("String constructor should create from code")
    void testStringConstructor() {
        ISOCountryCodeType type = new ISOCountryCodeType("TH");

        assertThat(type.getCode()).isEqualTo("TH");
    }

    @Test
    @DisplayName("String constructor should handle lowercase code")
    void testStringConstructorLowercase() {
        ISOCountryCodeType type = new ISOCountryCodeType("th");

        assertThat(type.getCode()).isEqualTo("TH");
    }

    // Factory method tests

    @Test
    @DisplayName("of(String) should create instance from code")
    void testOfString() {
        ISOCountryCodeType type = ISOCountryCodeType.of("US");

        assertThat(type.getCode()).isEqualTo("US");
    }

    @Test
    @DisplayName("of(Entity) should create instance from entity")
    void testOfEntity() {
        ISOCountryCode entity = new ISOCountryCode("JP", "Japan");
        ISOCountryCodeType type = ISOCountryCodeType.of(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("thailand() should create Thailand instance")
    void testThailandFactory() {
        ISOCountryCodeType type = ISOCountryCodeType.thailand();

        assertThat(type.getCode()).isEqualTo("TH");
    }

    @Test
    @DisplayName("unitedStates() should create US instance")
    void testUnitedStatesFactory() {
        ISOCountryCodeType type = ISOCountryCodeType.unitedStates();

        assertThat(type.getCode()).isEqualTo("US");
    }

    @Test
    @DisplayName("china() should create China instance")
    void testChinaFactory() {
        ISOCountryCodeType type = ISOCountryCodeType.china();

        assertThat(type.getCode()).isEqualTo("CN");
    }

    @Test
    @DisplayName("japan() should create Japan instance")
    void testJapanFactory() {
        ISOCountryCodeType type = ISOCountryCodeType.japan();

        assertThat(type.getCode()).isEqualTo("JP");
    }

    @Test
    @DisplayName("singapore() should create Singapore instance")
    void testSingaporeFactory() {
        ISOCountryCodeType type = ISOCountryCodeType.singapore();

        assertThat(type.getCode()).isEqualTo("SG");
    }

    // Business logic method tests with null value

    @Test
    @DisplayName("isThailand() should return false when value is null")
    void testIsThailandWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isThailand()).isFalse();
    }

    @Test
    @DisplayName("isASEANCountry() should return false when value is null")
    void testIsASEANCountryWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isASEANCountry()).isFalse();
    }

    @Test
    @DisplayName("isMajorTradingPartner() should return false when value is null")
    void testIsMajorTradingPartnerWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isMajorTradingPartner()).isFalse();
    }

    @Test
    @DisplayName("isETDAExtension() should return false when value is null")
    void testIsETDAExtensionWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isETDAExtension()).isFalse();
    }

    @Test
    @DisplayName("isStandardISO() should return false when value is null")
    void testIsStandardISOWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isStandardISO()).isFalse();
    }

    @Test
    @DisplayName("isChina() should return false when value is null")
    void testIsChinaWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isChina()).isFalse();
    }

    @Test
    @DisplayName("isJapan() should return false when value is null")
    void testIsJapanWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isJapan()).isFalse();
    }

    @Test
    @DisplayName("isSouthKorea() should return false when value is null")
    void testIsSouthKoreaWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isSouthKorea()).isFalse();
    }

    @Test
    @DisplayName("isUnitedStates() should return false when value is null")
    void testIsUnitedStatesWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isUnitedStates()).isFalse();
    }

    @Test
    @DisplayName("isSingapore() should return false when value is null")
    void testIsSingaporeWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isSingapore()).isFalse();
    }

    @Test
    @DisplayName("isMalaysia() should return false when value is null")
    void testIsMalaysiaWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isMalaysia()).isFalse();
    }

    @Test
    @DisplayName("isIndonesia() should return false when value is null")
    void testIsIndonesiaWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isIndonesia()).isFalse();
    }

    @Test
    @DisplayName("isVietnam() should return false when value is null")
    void testIsVietnamWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isVietnam()).isFalse();
    }

    @Test
    @DisplayName("isPhilippines() should return false when value is null")
    void testIsPhilippinesWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.isPhilippines()).isFalse();
    }

    // Business logic method tests with valid entity

    @Test
    @DisplayName("isThailand() should return true for TH")
    void testIsThailandTrue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOCountryCodeType type = new ISOCountryCodeType(entity);

        assertThat(type.isThailand()).isTrue();
    }

    @Test
    @DisplayName("isThailand() should return false for non-TH")
    void testIsThailandFalse() {
        ISOCountryCode entity = new ISOCountryCode("US", "United States");
        ISOCountryCodeType type = new ISOCountryCodeType(entity);

        assertThat(type.isThailand()).isFalse();
    }

    // Getter/Setter tests

    @Test
    @DisplayName("getValue() should return the wrapped entity")
    void testGetValue() {
        ISOCountryCode entity = new ISOCountryCode("DE", "Germany");
        ISOCountryCodeType type = new ISOCountryCodeType(entity);

        assertThat(type.getValue()).isEqualTo(entity);
    }

    @Test
    @DisplayName("setValue() should update the wrapped entity")
    void testSetValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();
        ISOCountryCode entity = new ISOCountryCode("FR", "France");

        type.setValue(entity);

        assertThat(type.getValue()).isEqualTo(entity);
        assertThat(type.getCode()).isEqualTo("FR");
    }

    // Equals tests

    @Test
    @DisplayName("equals() should return true for same value")
    void testEqualsSameValue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOCountryCodeType type1 = new ISOCountryCodeType(entity);
        ISOCountryCodeType type2 = new ISOCountryCodeType(entity);

        assertThat(type1).isEqualTo(type2);
    }

    @Test
    @DisplayName("equals() should return true for same instance")
    void testEqualsSameInstance() {
        ISOCountryCodeType type = new ISOCountryCodeType("TH");

        assertThat(type).isEqualTo(type);
    }

    @Test
    @DisplayName("equals() should return false for null")
    void testEqualsNull() {
        ISOCountryCodeType type = new ISOCountryCodeType("TH");

        assertThat(type).isNotEqualTo(null);
    }

    @Test
    @DisplayName("equals() should return false for different type")
    void testEqualsDifferentType() {
        ISOCountryCodeType type = new ISOCountryCodeType("TH");

        assertThat(type).isNotEqualTo("TH");
    }

    @Test
    @DisplayName("equals() should return false for different value")
    void testEqualsDifferentValue() {
        ISOCountryCodeType type1 = new ISOCountryCodeType(new ISOCountryCode("TH", "Thailand"));
        ISOCountryCodeType type2 = new ISOCountryCodeType(new ISOCountryCode("US", "United States"));

        assertThat(type1).isNotEqualTo(type2);
    }

    // HashCode tests

    @Test
    @DisplayName("hashCode() should be consistent for equal objects")
    void testHashCodeConsistent() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOCountryCodeType type1 = new ISOCountryCodeType(entity);
        ISOCountryCodeType type2 = new ISOCountryCodeType(entity);

        assertThat(type1.hashCode()).isEqualTo(type2.hashCode());
    }

    @Test
    @DisplayName("hashCode() should handle null value")
    void testHashCodeNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        // Should not throw exception
        int hashCode = type.hashCode();
        assertThat(hashCode).isNotNull();
    }

    // toString tests

    @Test
    @DisplayName("toString() should return entity toString when value is not null")
    void testToStringWithValue() {
        ISOCountryCode entity = new ISOCountryCode("TH", "Thailand");
        ISOCountryCodeType type = new ISOCountryCodeType(entity);

        assertThat(type.toString()).isEqualTo(entity.toString());
    }

    @Test
    @DisplayName("toString() should return null indicator when value is null")
    void testToStringWithNullValue() {
        ISOCountryCodeType type = new ISOCountryCodeType();

        assertThat(type.toString()).isEqualTo("ISOCountryCodeType{null}");
    }
}
