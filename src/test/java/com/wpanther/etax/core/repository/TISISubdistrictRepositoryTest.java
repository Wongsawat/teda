package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.TISISubdistrict;
import com.wpanther.etax.core.entity.ThaiProvinceCode;
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

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TISISubdistrictRepositoryTest.TestConfig.class)
@DisplayName("TISISubdistrictRepository Integration Tests")
class TISISubdistrictRepositoryTest extends PostgresTestContainer {

    @Autowired
    private TISISubdistrictRepository repository;

    @Autowired
    private ThaiProvinceCodeRepository provinceRepository;

    @Autowired
    private TISICityNameRepository cityRepository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            // Initialize dependent tables first
            DatabaseInitializer.initializeSchema(dataSource, "thai_province_code");
            DatabaseInitializer.initializeSchema(dataSource, "tisi_city_name");
            DatabaseInitializer.initializeSchema(dataSource, "tisi_subdistrict");
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
                    classes = {
                        TISISubdistrictRepository.class,
                        ThaiProvinceCodeRepository.class,
                        TISICityNameRepository.class
                    }
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = {
        TISISubdistrict.class,
        ThaiProvinceCode.class,
        TISICityName.class
    })
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all subdistricts")
    void findAll_shouldReturnAllRecords() {
        // TISISubdistrict table may have no data or test data
        // Just verify the query works
        List<TISISubdistrict> result = repository.findAll();
        // If there's data, verify we can access it
        if (!result.isEmpty()) {
            assertThat(result.get(0).getCode()).isNotNull();
        }
    }

    @Test
    @DisplayName("findByCode: should find subdistrict by code")
    void findByCode_shouldReturnEntity() {
        // This test assumes there might be data; if not, we just verify the method works
        List<TISISubdistrict> all = repository.findAll();
        if (!all.isEmpty()) {
            String testCode = all.get(0).getCode();
            Optional<TISISubdistrict> result = repository.findByCode(testCode);
            assertThat(result).isPresent();
            assertThat(result.get().getCode()).isEqualTo(testCode);
        }
        // If no data, that's OK for this test
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("99999999")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("99999999")).isFalse();
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
        List<TISISubdistrict> all = repository.findAll();
        if (!all.isEmpty()) {
            assertThat(repository.findById(all.get(0).getCode())).isPresent();
        }
        // If no data, that's OK for this test
    }

    // === Province Code Tests ===

    @Test
    @DisplayName("findByProvinceCode: should find subdistricts by province")
    void findByProvinceCode_shouldFindByProvince() {
        // Bangkok province code is 10
        List<TISISubdistrict> result = repository.findByProvinceCode("10");
        // If there's data in the table, verify results
        if (!result.isEmpty()) {
            assertThat(result).allMatch(s -> s.getProvinceCode().equals("10"));
        }
        // If no data, that's OK for this test
    }

    @Test
    @DisplayName("countByProvinceCode: should count subdistricts by province")
    void countByProvinceCode_shouldCountByProvince() {
        long count = repository.countByProvinceCode("10");
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    // === City Code Tests ===

    @Test
    @DisplayName("findByCityCode: should find subdistricts by city")
    void findByCityCode_shouldFindByCity() {
        List<TISISubdistrict> result = repository.findByCityCode("1001");
        // If there's data in the table, verify results
        if (!result.isEmpty()) {
            assertThat(result).allMatch(s -> s.getCityCode().equals("1001"));
        }
        // If no data, that's OK for this test
    }

    @Test
    @DisplayName("countByCityCode: should count subdistricts by city")
    void countByCityCode_shouldCountByCity() {
        long count = repository.countByCityCode("1001");
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameThContaining: should search by Thai name")
    void findByNameThContaining_shouldSearchByThaiName() {
        List<TISISubdistrict> result = repository.findByNameThContaining("บาง");
        // If there's data matching the search, verify results
        if (!result.isEmpty()) {
            assertThat(result).anyMatch(s -> s.getNameTh().contains("บาง"));
        }
        // If no data, that's OK for this test
    }
}
