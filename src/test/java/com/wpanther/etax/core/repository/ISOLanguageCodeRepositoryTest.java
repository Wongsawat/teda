package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ISOLanguageCode;
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
@ContextConfiguration(classes = ISOLanguageCodeRepositoryTest.TestConfig.class)
@DisplayName("ISOLanguageCodeRepository Integration Tests")
class ISOLanguageCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ISOLanguageCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "iso_language_code");
            DatabaseInitializer.loadTestData(dataSource, "iso_language_code");
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
                    classes = ISOLanguageCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ISOLanguageCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all active languages")
    void findAll_shouldReturnAllRecords() {
        List<ISOLanguageCode> result = repository.findAll();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find Thai language (th)")
    void findByCode_shouldFindThaiLanguage() {
        Optional<ISOLanguageCode> result = repository.findByCode("th");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("th");
        assertThat(result.get().getName()).contains("Thai");
    }

    @Test
    @DisplayName("findByCode: should find English (en)")
    void findByCode_shouldFindEnglish() {
        Optional<ISOLanguageCode> result = repository.findByCode("en");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("en");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<ISOLanguageCode> upper = repository.findByCode("TH");
        Optional<ISOLanguageCode> lower = repository.findByCode("th");
        Optional<ISOLanguageCode> mixed = repository.findByCode("Th");

        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
        assertThat(mixed).isPresent();
        assertThat(upper.get().getCode()).isEqualTo(lower.get().getCode());
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("zzz")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("th")).isTrue();
        assertThat(repository.existsByCode("en")).isTrue();
        assertThat(repository.existsByCode("zh")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("zzz")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("th")).isPresent();
    }

    // === ASEAN Language Tests ===

    @Test
    @DisplayName("findASEANLanguages: should return 9 ASEAN languages")
    void findASEANLanguages_shouldReturnASEANLanguages() {
        List<ISOLanguageCode> result = repository.findASEANLanguages();
        assertThat(result).hasSize(9);
        assertThat(result).anyMatch(l -> "th".equals(l.getCode()));
        assertThat(result).anyMatch(l -> "en".equals(l.getCode()));
    }

    @Test
    @DisplayName("findThai: should find Thai language")
    void findThai_shouldFindThai() {
        Optional<ISOLanguageCode> result = repository.findThai();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("th");
    }

    @Test
    @DisplayName("findEnglish: should find English")
    void findEnglish_shouldFindEnglish() {
        Optional<ISOLanguageCode> result = repository.findEnglish();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("en");
    }

    // === Major Trading Partner Tests ===

    @Test
    @DisplayName("findMajorTradingLanguages: should return trading partner languages")
    void findMajorTradingLanguages_shouldReturnTradingPartnerLanguages() {
        List<ISOLanguageCode> result = repository.findMajorTradingLanguages();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCode()).isEqualTo("th"); // Thai should be first
        assertThat(result).anyMatch(l -> "en".equals(l.getCode()));
        assertThat(result).anyMatch(l -> "zh".equals(l.getCode()));
        assertThat(result).anyMatch(l -> "ja".equals(l.getCode()));
    }

    @Test
    @DisplayName("findChinese: should find Chinese")
    void findChinese_shouldFindChinese() {
        Optional<ISOLanguageCode> result = repository.findChinese();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("zh");
    }

    @Test
    @DisplayName("findJapanese: should find Japanese")
    void findJapanese_shouldFindJapanese() {
        Optional<ISOLanguageCode> result = repository.findJapanese();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("ja");
    }

    @Test
    @DisplayName("findKorean: should find Korean")
    void findKorean_shouldFindKorean() {
        Optional<ISOLanguageCode> result = repository.findKorean();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("ko");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should find languages by name")
    void findByNameContaining_shouldFindByName() {
        List<ISOLanguageCode> result = repository.findByNameContaining("Thai");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(l -> l.getName().contains("Thai"));
    }

    @Test
    @DisplayName("findAllActive: should return only active languages")
    void findAllActive_shouldReturnActiveLanguages() {
        List<ISOLanguageCode> result = repository.findAllActive();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(l -> l.isActive());
    }
}
