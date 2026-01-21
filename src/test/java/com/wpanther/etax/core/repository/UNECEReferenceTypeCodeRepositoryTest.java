package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.UNECEReferenceTypeCode;
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
@ContextConfiguration(classes = UNECEReferenceTypeCodeRepositoryTest.TestConfig.class)
@DisplayName("UNECEReferenceTypeCodeRepository Integration Tests")
class UNECEReferenceTypeCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private UNECEReferenceTypeCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "unece_reference_type_code");
            DatabaseInitializer.loadTestData(dataSource, "unece_reference_type_code");
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
                    classes = UNECEReferenceTypeCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = UNECEReferenceTypeCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all reference type codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCodeAndActive: should find existing code")
    void findByCodeAndActive_shouldReturnEntity() {
        Optional<UNECEReferenceTypeCode> result = repository.findByCodeAndActive("AAR");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("AAR");
    }

    @Test
    @DisplayName("findByCodeAndActive: should be case insensitive")
    void findByCodeAndActive_shouldBeCaseInsensitive() {
        Optional<UNECEReferenceTypeCode> upper = repository.findByCodeAndActive("AAR");
        Optional<UNECEReferenceTypeCode> lower = repository.findByCodeAndActive("aar");

        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
        assertThat(upper.get().getCode()).isEqualTo(lower.get().getCode());
    }

    @Test
    @DisplayName("findByCodeAndActive: should return empty for invalid code")
    void findByCodeAndActive_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCodeAndActive("ZZZZZ")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCodeAndActive: should return true for valid codes")
    void existsByCodeAndActive_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCodeAndActive("AAR")).isTrue();
        assertThat(repository.existsByCodeAndActive("ACE")).isTrue();
    }

    @Test
    @DisplayName("existsByCodeAndActive: should return false for invalid code")
    void existsByCodeAndActive_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCodeAndActive("ZZZZZ")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("AAR")).isPresent();
    }

    // === Active Codes Tests ===

    @Test
    @DisplayName("findByActiveTrue: should return only active codes")
    void findByActiveTrue_shouldReturnActiveCodes() {
        List<UNECEReferenceTypeCode> result = repository.findByActiveTrue();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.isActive());
    }

    // === ETDA Extension Tests ===

    @Test
    @DisplayName("findEtdaExtensions: should return ETDA custom extensions")
    void findEtdaExtensions_shouldReturnETDAExtensions() {
        List<UNECEReferenceTypeCode> result = repository.findEtdaExtensions();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> Boolean.TRUE.equals(c.getEtdaExtension()));
    }

    // === Invoice Related Tests ===

    @Test
    @DisplayName("findInvoiceRelatedCodes: should find invoice-related references")
    void findInvoiceRelatedCodes_shouldFindInvoiceRelated() {
        List<UNECEReferenceTypeCode> result = repository.findInvoiceRelatedCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("INVOICE"));
    }

    // === Financial Related Tests ===

    @Test
    @DisplayName("findFinancialRelatedCodes: should find financial-related references")
    void findFinancialRelatedCodes_shouldFindFinancialRelated() {
        List<UNECEReferenceTypeCode> result = repository.findFinancialRelatedCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("PAYMENT") ||
                                           c.getName().toUpperCase().contains("BANK"));
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<UNECEReferenceTypeCode> result = repository.findByNameContaining("invoice");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("INVOICE"));
    }

    @Test
    @DisplayName("findByNameContaining: should be case insensitive")
    void findByNameContaining_shouldBeCaseInsensitive() {
        List<UNECEReferenceTypeCode> upper = repository.findByNameContaining("INVOICE");
        List<UNECEReferenceTypeCode> lower = repository.findByNameContaining("invoice");
        assertThat(upper).isNotEmpty();
        assertThat(lower).isNotEmpty();
    }
}
