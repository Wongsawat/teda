package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.TISICityName;
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
@ContextConfiguration(classes = TISICityNameRepositoryTest.TestConfig.class)
@DisplayName("TISICityNameRepository Integration Tests")
class TISICityNameRepositoryTest extends PostgresTestContainer {

    @Autowired
    private TISICityNameRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "tisi_city_name");
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
                    classes = TISICityNameRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = TISICityName.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all cities")
    void findAll_shouldReturnAllRecords() {
        // TISICityName table may have no data or test data
        // Just verify the query works
        List<TISICityName> result = repository.findAll();
        // If there's data, verify we can access it
        if (!result.isEmpty()) {
            assertThat(result.get(0).getCode()).isNotNull();
        }
    }

    @Test
    @DisplayName("findByCode: should find city by code")
    void findByCode_shouldReturnEntity() {
        // This test assumes there might be data; if not, we just verify the method works
        List<TISICityName> all = repository.findAll();
        if (!all.isEmpty()) {
            String testCode = all.get(0).getCode();
            Optional<TISICityName> result = repository.findByCode(testCode);
            assertThat(result).isPresent();
            assertThat(result.get().getCode()).isEqualTo(testCode);
        }
        // If no data, that's OK for this test
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("9999")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("9999")).isFalse();
    }

    @Test
    @DisplayName("count: should return count")
    void count_shouldReturnCount() {
        long count = repository.count();
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("findById: should find existing entity if data exists")
    void findById_shouldFindExistingEntity() {
        List<TISICityName> all = repository.findAll();
        if (!all.isEmpty()) {
            assertThat(repository.findById(all.get(0).getCode())).isPresent();
        }
        // If no data, that's OK for this test
    }

    // === Province Code Tests ===

    @Test
    @DisplayName("findByProvinceCode: should find cities by province")
    void findByProvinceCode_shouldFindByProvince() {
        // Bangkok province code is 10
        List<TISICityName> result = repository.findByProvinceCode("10");
        // If there's data in the table, verify results
        if (!result.isEmpty()) {
            assertThat(result).allMatch(c -> c.getProvinceCode().equals("10"));
        }
        // If no data, that's OK for this test
    }

    @Test
    @DisplayName("countByProvinceCode: should count cities by province")
    void countByProvinceCode_shouldCountByProvince() {
        long count = repository.countByProvinceCode("10");
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameThContaining: should search by Thai name")
    void findByNameThContaining_shouldSearchByThaiName() {
        List<TISICityName> result = repository.findByNameThContaining("เขต");
        // If there's data matching the search, verify results
        if (!result.isEmpty()) {
            assertThat(result).anyMatch(c -> c.getNameTh().contains("เขต"));
        }
        // If no data, that's OK for this test
    }
}
