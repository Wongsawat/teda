package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.AddressType;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AddressTypeRepositoryTest.TestConfig.class)
@DisplayName("AddressTypeRepository Integration Tests")
class AddressTypeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private AddressTypeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "address_type");
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
                    classes = AddressTypeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = AddressType.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return 3 address types")
    void findAll_shouldReturnThreeRecords() {
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("findByCode: should find postal address (code 1)")
    void findByCode_shouldReturnPostalAddress() {
        Optional<AddressType> result = repository.findByCode("1");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Postal address");
        assertThat(result.get().isPostalAddress()).isTrue();
    }

    @Test
    @DisplayName("findByCode: should find fiscal address (code 2)")
    void findByCode_shouldReturnFiscalAddress() {
        Optional<AddressType> result = repository.findByCode("2");
        assertThat(result).isPresent();
        assertThat(result.get().isFiscalAddress()).isTrue();
    }

    @Test
    @DisplayName("findByCode: should find physical address (code 3)")
    void findByCode_shouldReturnPhysicalAddress() {
        Optional<AddressType> result = repository.findByCode("3");
        assertThat(result).isPresent();
        assertThat(result.get().isPhysicalAddress()).isTrue();
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("99")).isEmpty();
    }

    // === Convenience Query Tests ===

    @Test
    @DisplayName("findPostalAddress: should return code 1")
    void findPostalAddress_shouldReturnCode1() {
        Optional<AddressType> result = repository.findPostalAddress();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findFiscalAddress: should return code 2")
    void findFiscalAddress_shouldReturnCode2() {
        Optional<AddressType> result = repository.findFiscalAddress();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("2");
    }

    @Test
    @DisplayName("findPhysicalAddress: should return code 3")
    void findPhysicalAddress_shouldReturnCode3() {
        Optional<AddressType> result = repository.findPhysicalAddress();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("3");
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("1")).isTrue();
        assertThat(repository.existsByCode("2")).isTrue();
        assertThat(repository.existsByCode("3")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("99")).isFalse();
    }

    @Test
    @DisplayName("count: should return 3")
    void count_shouldReturnThree() {
        assertThat(repository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("1")).isPresent();
    }
}
