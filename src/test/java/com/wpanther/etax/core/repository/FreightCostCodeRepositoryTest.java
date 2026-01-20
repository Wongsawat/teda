package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.FreightCostCode;
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
@ContextConfiguration(classes = FreightCostCodeRepositoryTest.TestConfig.class)
@DisplayName("FreightCostCodeRepository Integration Tests")
class FreightCostCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private FreightCostCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "freight_cost_code");
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
                    classes = FreightCostCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = FreightCostCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all freight cost codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find existing code")
    void findByCode_shouldReturnEntity() {
        Optional<FreightCostCode> result = repository.findByCode("100000");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("100000");
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("999999")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("100000")).isTrue();
        assertThat(repository.existsByCode("101000")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("999999")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("100000")).isPresent();
    }

    // === Category Tests ===

    @Test
    @DisplayName("findByCategory: should find by category")
    void findByCategory_shouldFindByCategory() {
        List<FreightCostCode> result = repository.findByCategory("Basic Freight");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Basic Freight".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findAllCategories: should return all categories")
    void findAllCategories_shouldReturnAllCategories() {
        List<String> result = repository.findAllCategories();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("countByCategory: should group by category")
    void countByCategory_shouldGroupByCategory() {
        List<Object[]> result = repository.countByCategory();
        assertThat(result).isNotEmpty();
    }

    // === Code Group Tests ===

    @Test
    @DisplayName("findByCodeGroup: should find by code group")
    void findByCodeGroup_shouldFindByCodeGroup() {
        List<FreightCostCode> result = repository.findByCodeGroup("101");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCodeGroup().equals("101"));
    }

    @Test
    @DisplayName("countByCodeGroup: should group by code group")
    void countByCodeGroup_shouldGroupByCodeGroup() {
        List<Object[]> result = repository.countByCodeGroup();
        assertThat(result).isNotEmpty();
    }

    // === Category-Specific Tests ===

    @Test
    @DisplayName("findBasicFreight: should find basic freight charges")
    void findBasicFreight_shouldFindBasicFreight() {
        List<FreightCostCode> result = repository.findBasicFreight();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findFreightSurcharges: should find freight surcharges")
    void findFreightSurcharges_shouldFindSurcharges() {
        List<FreightCostCode> result = repository.findFreightSurcharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findContainerServices: should find container services")
    void findContainerServices_shouldFindContainerServices() {
        List<FreightCostCode> result = repository.findContainerServices();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findTerminalCharges: should find terminal charges")
    void findTerminalCharges_shouldFindTerminalCharges() {
        List<FreightCostCode> result = repository.findTerminalCharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findHandlingCharges: should find handling charges")
    void findHandlingCharges_shouldFindHandlingCharges() {
        List<FreightCostCode> result = repository.findHandlingCharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findStorageAndDemurrage: should find storage and demurrage")
    void findStorageAndDemurrage_shouldFindStorage() {
        List<FreightCostCode> result = repository.findStorageAndDemurrage();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findCustomsAndDocumentation: should find customs charges")
    void findCustomsAndDocumentation_shouldFindCustoms() {
        List<FreightCostCode> result = repository.findCustomsAndDocumentation();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findDangerousGoods: should find dangerous goods charges")
    void findDangerousGoods_shouldFindDangerousGoods() {
        List<FreightCostCode> result = repository.findDangerousGoods();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findSpecialFreight: should find special freight")
    void findSpecialFreight_shouldFindSpecialFreight() {
        List<FreightCostCode> result = repository.findSpecialFreight();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findInsurance: should find insurance charges")
    void findInsurance_shouldFindInsurance() {
        List<FreightCostCode> result = repository.findInsurance();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findGeneralFreight: should find general freight")
    void findGeneralFreight_shouldFindGeneralFreight() {
        List<FreightCostCode> result = repository.findGeneralFreight();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findOtherCharges: should find other charges")
    void findOtherCharges_shouldFindOtherCharges() {
        List<FreightCostCode> result = repository.findOtherCharges();
        assertThat(result).isNotEmpty();
    }

    // === Common Codes Tests ===

    @Test
    @DisplayName("findCommonCodes: should return commonly used codes")
    void findCommonCodes_shouldReturnCommonCodes() {
        List<FreightCostCode> result = repository.findCommonCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "100000".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "101000".equals(c.getCode()));
    }

    @Test
    @DisplayName("findFreightChargesCode: should find 100000")
    void findFreightChargesCode_shouldFind100000() {
        Optional<FreightCostCode> result = repository.findFreightChargesCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("100000");
    }

    @Test
    @DisplayName("findBasicFreightCode: should find 101000")
    void findBasicFreightCode_shouldFind101000() {
        Optional<FreightCostCode> result = repository.findBasicFreightCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("101000");
    }

    // === Range Tests ===

    @Test
    @DisplayName("findByCodeRange: should find codes in range")
    void findByCodeRange_shouldFindInRange() {
        List<FreightCostCode> result = repository.findByCodeRange("101000", "101999");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().compareTo("101000") >= 0 &&
                                        c.getCode().compareTo("101999") <= 0);
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<FreightCostCode> result = repository.findByNameContaining("freight");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("FREIGHT"));
    }
}
