package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ThaiCategoryCode;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ThaiCategoryCodeRepositoryTest.TestConfig.class)
@DisplayName("ThaiCategoryCodeRepository Integration Tests")
class ThaiCategoryCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ThaiCategoryCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "thai_category_code");
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
                    classes = ThaiCategoryCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ThaiCategoryCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return 2 category codes")
    void findAll_shouldReturnTwoRecords() {
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("findByCode: should find code 01")
    void findByCode_shouldFindCode01() {
        Optional<ThaiCategoryCode> result = repository.findByCode("01");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("01");
    }

    @Test
    @DisplayName("findByCode: should find code 02")
    void findByCode_shouldFindCode02() {
        Optional<ThaiCategoryCode> result = repository.findByCode("02");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("02");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<ThaiCategoryCode> result = repository.findByCode("01");
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("99")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("01")).isTrue();
        assertThat(repository.existsByCode("02")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("99")).isFalse();
    }

    @Test
    @DisplayName("count: should return 2")
    void count_shouldReturnTwo() {
        assertThat(repository.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("01")).isPresent();
    }

    // === Specific Code Tests ===

    @Test
    @DisplayName("findOriginalDocumentReference: should find code 01")
    void findOriginalDocumentReference_shouldFindCode01() {
        Optional<ThaiCategoryCode> result = repository.findOriginalDocumentReference();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("01");
    }

    @Test
    @DisplayName("findAdvancePaymentReference: should find code 02")
    void findAdvancePaymentReference_shouldFindCode02() {
        Optional<ThaiCategoryCode> result = repository.findAdvancePaymentReference();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("02");
    }
}
