package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ISOCountryCode;
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

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ISOCountryCodeRepositoryTest.TestConfig.class)
@DisplayName("ISOCountryCodeRepository Integration Tests")
class ISOCountryCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ISOCountryCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "iso_country_code");
            DatabaseInitializer.loadTestData(dataSource, "iso_country_code");
            schemaInitialized = true;
        }
    }

    @AfterAll
    static void closeDataSource(@Autowired DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
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
                    classes = ISOCountryCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ISOCountryCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all countries")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find Thailand (TH)")
    void findByCode_shouldFindThailand() {
        Optional<ISOCountryCode> result = repository.findByCode("TH");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("TH");
        assertThat(result.get().getName()).containsIgnoringCase("Thailand");
        assertThat(result.get().isThailand()).isTrue();
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<ISOCountryCode> upper = repository.findByCode("TH");
        Optional<ISOCountryCode> lower = repository.findByCode("th");

        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
        assertThat(upper.get().getCode()).isEqualTo(lower.get().getCode());
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("ZZ")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("TH")).isTrue();
        assertThat(repository.existsByCode("US")).isTrue();
        assertThat(repository.existsByCode("JP")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("ZZ")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("TH")).isPresent();
    }

    // === ASEAN Country Tests ===

    @Test
    @DisplayName("findASEANCountries: should return 10 ASEAN countries")
    void findASEANCountries_shouldReturnASEANCountries() {
        List<ISOCountryCode> result = repository.findASEANCountries();
        assertThat(result).hasSize(10);
        assertThat(result).anyMatch(c -> "TH".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "SG".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "MY".equals(c.getCode()));
        assertThat(result).allMatch(c -> c.isASEANCountry());
    }

    @Test
    @DisplayName("findThailand: should find Thailand")
    void findThailand_shouldFindThailand() {
        Optional<ISOCountryCode> result = repository.findThailand();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("TH");
        assertThat(result.get().isThailand()).isTrue();
    }

    @Test
    @DisplayName("findSingapore: should find Singapore")
    void findSingapore_shouldFindSingapore() {
        Optional<ISOCountryCode> result = repository.findSingapore();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("SG");
        assertThat(result.get().isSingapore()).isTrue();
    }

    @Test
    @DisplayName("findMalaysia: should find Malaysia")
    void findMalaysia_shouldFindMalaysia() {
        Optional<ISOCountryCode> result = repository.findMalaysia();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("MY");
    }

    // === Major Trading Partner Tests ===

    @Test
    @DisplayName("findMajorTradingPartners: should return trading partners")
    void findMajorTradingPartners_shouldReturnTradingPartners() {
        List<ISOCountryCode> result = repository.findMajorTradingPartners();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCode()).isEqualTo("CN"); // China should be first
        assertThat(result).anyMatch(c -> "JP".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "US".equals(c.getCode()));
        assertThat(result.get(4).getCode()).isEqualTo("SG"); // Singapore should be 5th
    }

    @Test
    @DisplayName("findChina: should find China")
    void findChina_shouldFindChina() {
        Optional<ISOCountryCode> result = repository.findChina();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("CN");
        assertThat(result.get().isChina()).isTrue();
    }

    @Test
    @DisplayName("findJapan: should find Japan")
    void findJapan_shouldFindJapan() {
        Optional<ISOCountryCode> result = repository.findJapan();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("JP");
        assertThat(result.get().isJapan()).isTrue();
    }

    @Test
    @DisplayName("findSouthKorea: should find South Korea")
    void findSouthKorea_shouldFindSouthKorea() {
        Optional<ISOCountryCode> result = repository.findSouthKorea();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("KR");
    }

    @Test
    @DisplayName("findUnitedStates: should find United States")
    void findUnitedStates_shouldFindUnitedStates() {
        Optional<ISOCountryCode> result = repository.findUnitedStates();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("US");
        assertThat(result.get().isUnitedStates()).isTrue();
    }

    @Test
    @DisplayName("findUnitedKingdom: should find United Kingdom")
    void findUnitedKingdom_shouldFindUnitedKingdom() {
        Optional<ISOCountryCode> result = repository.findUnitedKingdom();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("GB");
    }

    // === Active and Standard Tests ===

    @Test
    @DisplayName("findAllActive: should return only active countries")
    void findAllActive_shouldReturnActiveCountries() {
        List<ISOCountryCode> result = repository.findAllActive();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.isActive());
    }

    @Test
    @DisplayName("findStandardISO: should exclude ETDA extensions")
    void findStandardISO_shouldExcludeETDAExtensions() {
        List<ISOCountryCode> result = repository.findStandardISO();
        assertThat(result).isNotEmpty();
        assertThat(result).noneMatch(c -> c.isETDAExtension());
        assertThat(result).allMatch(ISOCountryCode::isStandardISO);
    }

    @Test
    @DisplayName("findETDAExtensions: should return ETDA custom extensions")
    void findETDAExtensions_shouldReturnETDAExtensions() {
        List<ISOCountryCode> result = repository.findETDAExtensions();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(ISOCountryCode::isETDAExtension);
    }

    @Test
    @DisplayName("countActive: should count active countries")
    void countActive_shouldCountActiveCountries() {
        long count = repository.countActive();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("countASEAN: should return 10")
    void countASEAN_shouldReturn10() {
        assertThat(repository.countASEAN()).isEqualTo(10);
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should find countries by name")
    void findByNameContaining_shouldFindByName() {
        List<ISOCountryCode> result = repository.findByNameContaining("Thai");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("THAI"));
    }
}
