package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.DutyTaxFeeTypeCode;
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
@ContextConfiguration(classes = DutyTaxFeeTypeCodeRepositoryTest.TestConfig.class)
@DisplayName("DutyTaxFeeTypeCodeRepository Integration Tests")
class DutyTaxFeeTypeCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private DutyTaxFeeTypeCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "duty_tax_fee_type_code");
            DatabaseInitializer.loadTestData(dataSource, "duty_tax_fee_type_code");
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
                    classes = DutyTaxFeeTypeCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = DutyTaxFeeTypeCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all duty tax fee type codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find VAT code")
    void findByCode_shouldFindVATCode() {
        Optional<DutyTaxFeeTypeCode> result = repository.findByCode("VAT");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("VAT");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive via normalization")
    void findByCode_shouldNormalizeCode() {
        Optional<DutyTaxFeeTypeCode> upper = repository.findByCode("VAT");
        Optional<DutyTaxFeeTypeCode> lower = repository.findByCode("vat");
        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
        assertThat(upper.get().getCode()).isEqualTo(lower.get().getCode());
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("INVALID")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("VAT")).isTrue();
        assertThat(repository.existsByCode("GST")).isTrue();
        assertThat(repository.existsByCode("FRE")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("INVALID")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("VAT")).isPresent();
    }

    // === VAT Tests ===

    @Test
    @DisplayName("findVATCodes: should return all VAT-related codes")
    void findVATCodes_shouldReturnVATCodes() {
        List<DutyTaxFeeTypeCode> result = repository.findVATCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(DutyTaxFeeTypeCode::isVat);
    }

    @Test
    @DisplayName("findVATCode: should find VAT code")
    void findVATCode_shouldFindVAT() {
        Optional<DutyTaxFeeTypeCode> result = repository.findVATCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("VAT");
        assertThat(result.get().isVat()).isTrue();
    }

    // === Exempt Tests ===

    @Test
    @DisplayName("findExemptCodes: should return all exempt codes")
    void findExemptCodes_shouldReturnExemptCodes() {
        List<DutyTaxFeeTypeCode> result = repository.findExemptCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(DutyTaxFeeTypeCode::isExempt);
    }

    // === Summary Tests ===

    @Test
    @DisplayName("findSummaryCodes: should return all summary codes")
    void findSummaryCodes_shouldReturnSummaryCodes() {
        List<DutyTaxFeeTypeCode> result = repository.findSummaryCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(DutyTaxFeeTypeCode::isSummary);
    }

    // === GST Tests ===

    @Test
    @DisplayName("findGSTCode: should find GST code")
    void findGSTCode_shouldFindGST() {
        Optional<DutyTaxFeeTypeCode> result = repository.findGSTCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("GST");
    }

    @Test
    @DisplayName("findGSTCodes: should return GST category codes")
    void findGSTCodes_shouldReturnGSTCodes() {
        List<DutyTaxFeeTypeCode> result = repository.findGSTCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "GST".equals(c.getCategory()));
    }

    // === Common Codes Tests ===

    @Test
    @DisplayName("findCommonCodes: should return commonly used codes")
    void findCommonCodes_shouldReturnCommonCodes() {
        List<DutyTaxFeeTypeCode> result = repository.findCommonCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "VAT".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "GST".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "FRE".equals(c.getCode()));
    }

    @Test
    @DisplayName("findFreeCode: should find FRE code")
    void findFreeCode_shouldFindFRE() {
        Optional<DutyTaxFeeTypeCode> result = repository.findFreeCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("FRE");
    }

    @Test
    @DisplayName("findOtherCode: should find OTH code")
    void findOtherCode_shouldFindOTH() {
        Optional<DutyTaxFeeTypeCode> result = repository.findOtherCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("OTH");
    }

    @Test
    @DisplayName("findTotalCode: should find TOT code")
    void findTotalCode_shouldFindTOT() {
        Optional<DutyTaxFeeTypeCode> result = repository.findTotalCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("TOT");
    }

    // === Category Tests ===

    @Test
    @DisplayName("findByCategory: should find codes by category")
    void findByCategory_shouldFindByCategory() {
        List<DutyTaxFeeTypeCode> result = repository.findByCategory("VAT");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "VAT".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findAllCategories: should return all categories")
    void findAllCategories_shouldReturnAllCategories() {
        List<String> result = repository.findAllCategories();
        assertThat(result).isNotEmpty();
        assertThat(result).contains("VAT", "GST");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<DutyTaxFeeTypeCode> result = repository.findByNameContaining("tax");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("TAX"));
    }

    @Test
    @DisplayName("findByDescriptionContaining: should search by description")
    void findByDescriptionContaining_shouldSearchByDescription() {
        List<DutyTaxFeeTypeCode> result = repository.findByDescriptionContaining("value added");
        assertThat(result).isNotEmpty();
    }
}
