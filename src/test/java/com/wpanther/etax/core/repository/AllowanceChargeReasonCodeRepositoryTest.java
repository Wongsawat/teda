package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.AllowanceChargeReasonCode;
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
@ContextConfiguration(classes = AllowanceChargeReasonCodeRepositoryTest.TestConfig.class)
@DisplayName("AllowanceChargeReasonCodeRepository Integration Tests")
class AllowanceChargeReasonCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private AllowanceChargeReasonCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "allowance_charge_reason_code");
            DatabaseInitializer.loadTestData(dataSource, "allowance_charge_reason_code");
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
                    classes = AllowanceChargeReasonCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = AllowanceChargeReasonCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all allowance/charge reason codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find agreed settlement (1)")
    void findByCode_shouldFindAgreedSettlement() {
        Optional<AllowanceChargeReasonCode> result = repository.findByCode("1");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<AllowanceChargeReasonCode> upper = repository.findByCode("ZZZ");
        Optional<AllowanceChargeReasonCode> lower = repository.findByCode("zzz");
        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
        assertThat(upper.get().getCode()).isEqualTo(lower.get().getCode());
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("ZZZZZ")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("1")).isTrue();
        assertThat(repository.existsByCode("19")).isTrue();
        assertThat(repository.existsByCode("ZZZ")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("ZZZZZ")).isFalse();
    }

    @Test
    @DisplayName("count: should return expected count")
    void count_shouldReturnExpectedCount() {
        assertThat(repository.count()).isGreaterThan(0);
    }

    @Test
    @DisplayName("findById: should find existing entity")
    void findById_shouldFindExistingEntity() {
        assertThat(repository.findById("1")).isPresent();
    }

    // === Category Tests ===

    @Test
    @DisplayName("findByCategory: should find by category")
    void findByCategory_shouldFindByCategory() {
        List<AllowanceChargeReasonCode> result = repository.findByCategory("Quality Issue");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Quality Issue".equals(c.getCategory()));
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

    // === Category-Specific Tests ===

    @Test
    @DisplayName("findQualityIssues: should find quality issue reasons")
    void findQualityIssues_shouldFindQualityIssues() {
        List<AllowanceChargeReasonCode> result = repository.findQualityIssues();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Quality Issue".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findDeliveryIssues: should find delivery issue reasons")
    void findDeliveryIssues_shouldFindDeliveryIssues() {
        List<AllowanceChargeReasonCode> result = repository.findDeliveryIssues();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Delivery Issue".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findAdministrativeErrors: should find administrative error reasons")
    void findAdministrativeErrors_shouldFindAdministrativeErrors() {
        List<AllowanceChargeReasonCode> result = repository.findAdministrativeErrors();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Administrative Error".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findDiscountsAndAllowances: should find discount/allowance reasons")
    void findDiscountsAndAllowances_shouldFindDiscounts() {
        List<AllowanceChargeReasonCode> result = repository.findDiscountsAndAllowances();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findFinancialCharges: should find financial charge reasons")
    void findFinancialCharges_shouldFindFinancialCharges() {
        List<AllowanceChargeReasonCode> result = repository.findFinancialCharges();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Financial Charges".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findClaimsAndDisputes: should find claim/dispute reasons")
    void findClaimsAndDisputes_shouldFindClaims() {
        List<AllowanceChargeReasonCode> result = repository.findClaimsAndDisputes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Claims/Disputes".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findFreightAndLogistics: should find freight/logistics reasons")
    void findFreightAndLogistics_shouldFindFreight() {
        List<AllowanceChargeReasonCode> result = repository.findFreightAndLogistics();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Freight/Logistics".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findPaymentTerms: should find payment terms reasons")
    void findPaymentTerms_shouldFindPaymentTerms() {
        List<AllowanceChargeReasonCode> result = repository.findPaymentTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Payment Terms".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findHRRelated: should find HR related reasons")
    void findHRRelated_shouldFindHRRelated() {
        List<AllowanceChargeReasonCode> result = repository.findHRRelated();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "HR Related".equals(c.getCategory()));
    }

    // === Common Reasons Tests ===

    @Test
    @DisplayName("findCommonReasons: should return commonly used reasons")
    void findCommonReasons_shouldReturnCommonReasons() {
        List<AllowanceChargeReasonCode> result = repository.findCommonReasons();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "19".equals(c.getCode())); // Trade discount
        assertThat(result).anyMatch(c -> "74".equals(c.getCode())); // Quantity discount
        assertThat(result).anyMatch(c -> "ZZZ".equals(c.getCode())); // Mutually defined
    }

    @Test
    @DisplayName("findAgreedSettlement: should find code 1")
    void findAgreedSettlement_shouldFindCode1() {
        Optional<AllowanceChargeReasonCode> result = repository.findAgreedSettlement();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findDamagedGoods: should find code 3")
    void findDamagedGoods_shouldFindCode3() {
        Optional<AllowanceChargeReasonCode> result = repository.findDamagedGoods();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("3");
    }

    @Test
    @DisplayName("findShortDelivery: should find code 4")
    void findShortDelivery_shouldFindCode4() {
        Optional<AllowanceChargeReasonCode> result = repository.findShortDelivery();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("4");
    }

    @Test
    @DisplayName("findInvoiceError: should find code 9")
    void findInvoiceError_shouldFindCode9() {
        Optional<AllowanceChargeReasonCode> result = repository.findInvoiceError();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("9");
    }

    @Test
    @DisplayName("findBankCharges: should find code 11")
    void findBankCharges_shouldFindCode11() {
        Optional<AllowanceChargeReasonCode> result = repository.findBankCharges();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("11");
    }

    @Test
    @DisplayName("findTradeDiscount: should find code 19")
    void findTradeDiscount_shouldFindCode19() {
        Optional<AllowanceChargeReasonCode> result = repository.findTradeDiscount();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("19");
    }

    @Test
    @DisplayName("findCashDiscount: should find code 66")
    void findCashDiscount_shouldFindCode66() {
        Optional<AllowanceChargeReasonCode> result = repository.findCashDiscount();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("66");
    }

    @Test
    @DisplayName("findQuantityDiscount: should find code 74")
    void findQuantityDiscount_shouldFindCode74() {
        Optional<AllowanceChargeReasonCode> result = repository.findQuantityDiscount();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("74");
    }

    @Test
    @DisplayName("findVolumeDiscount: should find code 78")
    void findVolumeDiscount_shouldFindCode78() {
        Optional<AllowanceChargeReasonCode> result = repository.findVolumeDiscount();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("78");
    }

    @Test
    @DisplayName("findMutuallyDefined: should find code ZZZ")
    void findMutuallyDefined_shouldFindZZZ() {
        Optional<AllowanceChargeReasonCode> result = repository.findMutuallyDefined();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("ZZZ");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<AllowanceChargeReasonCode> result = repository.findByNameContaining("discount");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("DISCOUNT"));
    }

    @Test
    @DisplayName("findByDescriptionContaining: should search by description")
    void findByDescriptionContaining_shouldSearchByDescription() {
        List<AllowanceChargeReasonCode> result = repository.findByDescriptionContaining("invoice");
        assertThat(result).isNotEmpty();
    }
}
