package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ISOCurrencyCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ISOCurrencyCodeRepositoryTest.TestConfig.class)
@DisplayName("ISOCurrencyCodeRepository Integration Tests")
class ISOCurrencyCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ISOCurrencyCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "iso_currency_code");
            DatabaseInitializer.loadTestData(dataSource, "iso_currency_code");
            schemaInitialized = true;
        }
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.show-sql", () -> "true");
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = ISOCurrencyCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ISOCurrencyCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all currencies")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find Thai Baht (THB)")
    void findByCode_shouldFindThaiBaht() {
        Optional<ISOCurrencyCode> result = repository.findByCode("THB");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("THB");
        assertThat(result.get().getNumericCode()).isEqualTo("764");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<ISOCurrencyCode> upper = repository.findByCode("THB");
        Optional<ISOCurrencyCode> lower = repository.findByCode("thb");

        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
        assertThat(upper.get().getCode()).isEqualTo(lower.get().getCode());
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("ZZZ")).isEmpty();
    }

    @Test
    @DisplayName("findByNumericCode: should find by numeric code")
    void findByNumericCode_shouldFindByNumericCode() {
        Optional<ISOCurrencyCode> result = repository.findByNumericCode("764");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("THB");
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("THB")).isTrue();
        assertThat(repository.existsByCode("USD")).isTrue();
        assertThat(repository.existsByCode("EUR")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("ZZZ")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("THB")).isPresent();
    }

    // === Major Currency Tests ===

    @Test
    @DisplayName("findMajorCurrencies: should return 8 major reserve currencies")
    void findMajorCurrencies_shouldReturnMajorCurrencies() {
        List<ISOCurrencyCode> result = repository.findMajorCurrencies();
        assertThat(result).hasSize(8);
        assertThat(result).anyMatch(c -> "USD".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "EUR".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "THB".equals(c.getCode()) || "JPY".equals(c.getCode()));
    }

    @Test
    @DisplayName("findThaiBasht: should find Thai Baht")
    void findThaiBasht_shouldFindThaiBaht() {
        Optional<ISOCurrencyCode> result = repository.findThaiBasht();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("THB");
        assertThat(result.get().getMinorUnits()).isEqualTo(2);
    }

    @Test
    @DisplayName("findUSDollar: should find US Dollar")
    void findUSDollar_shouldFindUSDollar() {
        Optional<ISOCurrencyCode> result = repository.findUSDollar();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("USD");
        assertThat(result.get().getNumericCode()).isEqualTo("840");
    }

    @Test
    @DisplayName("findEuro: should find Euro")
    void findEuro_shouldFindEuro() {
        Optional<ISOCurrencyCode> result = repository.findEuro();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("EUR");
        assertThat(result.get().getNumericCode()).isEqualTo("978");
    }

    @Test
    @DisplayName("findJapaneseYen: should find Japanese Yen")
    void findJapaneseYen_shouldFindJapaneseYen() {
        Optional<ISOCurrencyCode> result = repository.findJapaneseYen();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("JPY");
        assertThat(result.get().getMinorUnits()).isEqualTo(0); // Yen has no decimal places
    }

    // === ASEAN Currency Tests ===

    @Test
    @DisplayName("findASEANCurrencies: should return 10 ASEAN currencies")
    void findASEANCurrencies_shouldReturnASEANCurrencies() {
        List<ISOCurrencyCode> result = repository.findASEANCurrencies();
        assertThat(result).hasSize(10);
        assertThat(result).anyMatch(c -> "THB".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "SGD".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "MYR".equals(c.getCode()));
    }

    // === Decimal Place Tests ===

    @Test
    @DisplayName("findZeroDecimalCurrencies: should find currencies with no decimals")
    void findZeroDecimalCurrencies_shouldFindZeroDecimalCurrencies() {
        List<ISOCurrencyCode> result = repository.findZeroDecimalCurrencies();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "JPY".equals(c.getCode()));
        assertThat(result).allMatch(c -> c.getMinorUnits() == 0);
    }

    @Test
    @DisplayName("findThreeDecimalCurrencies: should find currencies with 3 decimals")
    void findThreeDecimalCurrencies_shouldFindThreeDecimalCurrencies() {
        List<ISOCurrencyCode> result = repository.findThreeDecimalCurrencies();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getMinorUnits() == 3);
    }

    @Test
    @DisplayName("findByMinorUnits: should find by decimal places")
    void findByMinorUnits_shouldFindByMinorUnits() {
        List<ISOCurrencyCode> twoDecimals = repository.findByMinorUnits(2);
        assertThat(twoDecimals).isNotEmpty();
        assertThat(twoDecimals).anyMatch(c -> "THB".equals(c.getCode()) || "USD".equals(c.getCode()));
    }

    // === Thai Trading Partner Tests ===

    @Test
    @DisplayName("findThaiTradingCurrencies: should return trading partner currencies")
    void findThaiTradingCurrencies_shouldReturnTradingPartnerCurrencies() {
        List<ISOCurrencyCode> result = repository.findThaiTradingCurrencies();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCode()).isEqualTo("THB"); // THB should be first
        assertThat(result).anyMatch(c -> "USD".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "EUR".equals(c.getCode()));
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should find currencies by name")
    void findByNameContaining_shouldFindByName() {
        List<ISOCurrencyCode> result = repository.findByNameContaining("Baht");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().contains("Baht"));
    }

    @Test
    @DisplayName("findAllActive: should return only active currencies")
    void findAllActive_shouldReturnActiveCurrencies() {
        List<ISOCurrencyCode> result = repository.findAllActive();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.isActive());
    }
}
