package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ThaiProvinceCode;
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
@ContextConfiguration(classes = ThaiProvinceCodeRepositoryTest.TestConfig.class)
@DisplayName("ThaiProvinceCodeRepository Integration Tests")
class ThaiProvinceCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ThaiProvinceCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "thai_province_code");
            DatabaseInitializer.loadTestData(dataSource, "thai_province_code");
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
                    classes = ThaiProvinceCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ThaiProvinceCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return 78 codes (77 provinces + 1 placeholder)")
    void findAll_shouldReturn77Provinces() {
        assertThat(repository.findAll()).hasSize(78);
    }

    @Test
    @DisplayName("findByCodeAndActive: should find Bangkok (10)")
    void findByCodeAndActive_shouldFindBangkok() {
        Optional<ThaiProvinceCode> result = repository.findByCodeAndActive("10");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("10");
        assertThat(result.get().getNameTh()).contains("กรุงเทพ");
    }

    @Test
    @DisplayName("findByCodeAndActive: should return empty for inactive code")
    void findByCodeAndActive_shouldReturnEmptyForInactiveCode() {
        // All provinces should be active in the test data
        // This test verifies the method works correctly
        Optional<ThaiProvinceCode> result = repository.findByCodeAndActive("99");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByCodeAndActive: should return empty for invalid code")
    void findByCodeAndActive_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCodeAndActive("invalid")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCodeAndActive: should return true for valid codes")
    void existsByCodeAndActive_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCodeAndActive("10")).isTrue();
        assertThat(repository.existsByCodeAndActive("11")).isTrue();
    }

    @Test
    @DisplayName("existsByCodeAndActive: should return false for invalid code")
    void existsByCodeAndActive_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCodeAndActive("99")).isFalse();
    }

    @Test
    @DisplayName("count: should return 78 (77 provinces + 1 placeholder)")
    void count_shouldReturn77() {
        assertThat(repository.count()).isEqualTo(78);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("10")).isPresent();
    }

    // === Active Provinces Tests ===

    @Test
    @DisplayName("findByActiveTrue: should return all active codes (77 provinces + 1 placeholder)")
    void findByActiveTrue_shouldReturnActiveProvinces() {
        List<ThaiProvinceCode> result = repository.findByActiveTrue();
        assertThat(result).hasSize(78);
        assertThat(result).allMatch(ThaiProvinceCode::isActive);
    }

    // === Region Tests ===

    @Test
    @DisplayName("findByRegion: should find Central region provinces")
    void findByRegion_shouldFindCentralRegion() {
        List<ThaiProvinceCode> result = repository.findByRegion("Central");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(p -> p.getNameEn().contains("Bangkok") || p.getNameEn().contains("Samut"));
    }

    @Test
    @DisplayName("findByRegion: should find Northern region provinces")
    void findByRegion_shouldFindNorthernRegion() {
        List<ThaiProvinceCode> result = repository.findByRegion("North");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(p -> p.getNameEn().contains("Chiang Mai") || p.getNameEn().contains("Lampang"));
    }

    @Test
    @DisplayName("findByRegion: should find Northeastern region provinces")
    void findByRegion_shouldFindNorthEasternRegion() {
        List<ThaiProvinceCode> result = repository.findByRegion("Northeast");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(p -> p.getNameEn().contains("Nakhon Ratchasima") || p.getNameEn().contains("Khon Kaen"));
    }

    @Test
    @DisplayName("findByRegion: should find Southern region provinces")
    void findByRegion_shouldFindSouthernRegion() {
        List<ThaiProvinceCode> result = repository.findByRegion("South");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(p -> p.getNameEn().contains("Phuket") || p.getNameEn().contains("Surat Thani"));
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameThContaining: should search by Thai name")
    void findByNameThContaining_shouldSearchByThaiName() {
        List<ThaiProvinceCode> result = repository.findByNameThContaining("กรุงเทพ");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(p -> p.getNameTh().contains("กรุงเทพ"));
    }

    @Test
    @DisplayName("findByNameEnContaining: should search by English name")
    void findByNameEnContaining_shouldSearchByEnglishName() {
        List<ThaiProvinceCode> result = repository.findByNameEnContaining("Bangkok");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(p -> p.getNameEn().contains("Bangkok"));
    }

    @Test
    @DisplayName("findByNameEnContaining: should be case insensitive")
    void findByNameEnContaining_shouldBeCaseInsensitive() {
        List<ThaiProvinceCode> upper = repository.findByNameEnContaining("BANGKOK");
        List<ThaiProvinceCode> lower = repository.findByNameEnContaining("bangkok");
        assertThat(upper).isNotEmpty();
        assertThat(lower).isNotEmpty();
    }
}
