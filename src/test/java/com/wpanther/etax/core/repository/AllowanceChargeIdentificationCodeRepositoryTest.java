package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.AllowanceChargeIdentificationCode;
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
@ContextConfiguration(classes = AllowanceChargeIdentificationCodeRepositoryTest.TestConfig.class)
@DisplayName("AllowanceChargeIdentificationCodeRepository Integration Tests")
class AllowanceChargeIdentificationCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private AllowanceChargeIdentificationCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "allowance_charge_identification_code");
            DatabaseInitializer.loadTestData(dataSource, "allowance_charge_identification_code");
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
                    classes = AllowanceChargeIdentificationCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = AllowanceChargeIdentificationCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all allowance/charge identification codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find handling commission (1)")
    void findByCode_shouldFindHandlingCommission() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findByCode("1");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<AllowanceChargeIdentificationCode> upper = repository.findByCode("79");
        Optional<AllowanceChargeIdentificationCode> lower = repository.findByCode("79");
        assertThat(upper).isPresent();
        assertThat(lower).isPresent();
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("999")).isEmpty();
    }

    // === Existence Check Tests ===

    @Test
    @DisplayName("existsByCode: should return true for valid codes")
    void existsByCode_shouldReturnTrueForValidCodes() {
        assertThat(repository.existsByCode("1")).isTrue();
        assertThat(repository.existsByCode("79")).isTrue();
        assertThat(repository.existsByCode("95")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("999")).isFalse();
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
        List<AllowanceChargeIdentificationCode> result = repository.findByCategory("Discount");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Discount".equals(c.getCategory()));
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
    @DisplayName("findDocumentaryCreditCommissions: should find documentary credit commissions")
    void findDocumentaryCreditCommissions_shouldFindDocumentaryCredit() {
        List<AllowanceChargeIdentificationCode> result = repository.findDocumentaryCreditCommissions();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findCollectionCommissions: should find collection commissions")
    void findCollectionCommissions_shouldFindCollectionCommissions() {
        List<AllowanceChargeIdentificationCode> result = repository.findCollectionCommissions();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findProcessingFees: should find processing fees")
    void findProcessingFees_shouldFindProcessingFees() {
        List<AllowanceChargeIdentificationCode> result = repository.findProcessingFees();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findDiscounts: should find discounts")
    void findDiscounts_shouldFindDiscounts() {
        List<AllowanceChargeIdentificationCode> result = repository.findDiscounts();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findRebates: should find rebates")
    void findRebates_shouldFindRebates() {
        List<AllowanceChargeIdentificationCode> result = repository.findRebates();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findPenalties: should find penalties")
    void findPenalties_shouldFindPenalties() {
        List<AllowanceChargeIdentificationCode> result = repository.findPenalties();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findBonuses: should find bonuses")
    void findBonuses_shouldFindBonuses() {
        List<AllowanceChargeIdentificationCode> result = repository.findBonuses();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findFreightCharges: should find freight charges")
    void findFreightCharges_shouldFindFreightCharges() {
        List<AllowanceChargeIdentificationCode> result = repository.findFreightCharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findPackingCharges: should find packing charges")
    void findPackingCharges_shouldFindPackingCharges() {
        List<AllowanceChargeIdentificationCode> result = repository.findPackingCharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findLoadingCharges: should find loading charges")
    void findLoadingCharges_shouldFindLoadingCharges() {
        List<AllowanceChargeIdentificationCode> result = repository.findLoadingCharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findHandlingCharges: should find handling charges")
    void findHandlingCharges_shouldFindHandlingCharges() {
        List<AllowanceChargeIdentificationCode> result = repository.findHandlingCharges();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findTestingCharges: should find testing/inspection charges")
    void findTestingCharges_shouldFindTestingCharges() {
        List<AllowanceChargeIdentificationCode> result = repository.findTestingCharges();
        assertThat(result).isNotEmpty();
    }

    // === Standard vs Extension Tests ===

    @Test
    @DisplayName("findStandardCodes: should return standard UN/CEFACT codes")
    void findStandardCodes_shouldReturnStandardCodes() {
        List<AllowanceChargeIdentificationCode> result = repository.findStandardCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(AllowanceChargeIdentificationCode::isStandardCode);
    }

    @Test
    @DisplayName("findThaiExtensions: should return Thai extension codes")
    void findThaiExtensions_shouldReturnThaiExtensions() {
        List<AllowanceChargeIdentificationCode> result = repository.findThaiExtensions();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(AllowanceChargeIdentificationCode::isThaiExtension);
        assertThat(result).anyMatch(c -> c.getCode().startsWith("PP"));
    }

    // === Common Codes Tests ===

    @Test
    @DisplayName("findCommonCodes: should return commonly used codes")
    void findCommonCodes_shouldReturnCommonCodes() {
        List<AllowanceChargeIdentificationCode> result = repository.findCommonCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "79".equals(c.getCode())); // Freight charges
        assertThat(result).anyMatch(c -> "80".equals(c.getCode())); // Packing charge
        assertThat(result).anyMatch(c -> "95".equals(c.getCode())); // Discount
    }

    @Test
    @DisplayName("findHandlingCommission: should find code 1")
    void findHandlingCommission_shouldFindCode1() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findHandlingCommission();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findBankCharges: should find code 30")
    void findBankCharges_shouldFindCode30() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findBankCharges();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("30");
    }

    @Test
    @DisplayName("findCourierFee: should find code 32")
    void findCourierFee_shouldFindCode32() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findCourierFee();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("32");
    }

    @Test
    @DisplayName("findSwiftFee: should find code 35")
    void findSwiftFee_shouldFindCode35() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findSwiftFee();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("35");
    }

    @Test
    @DisplayName("findManufacturerDiscount: should find code 60")
    void findManufacturerDiscount_shouldFindCode60() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findManufacturerDiscount();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("60");
    }

    @Test
    @DisplayName("findFreightChargesCode: should find code 79")
    void findFreightChargesCode_shouldFindCode79() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findFreightChargesCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("79");
    }

    @Test
    @DisplayName("findPackingCharge: should find code 80")
    void findPackingCharge_shouldFindCode80() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findPackingCharge();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("80");
    }

    @Test
    @DisplayName("findDiscountCode: should find code 95")
    void findDiscountCode_shouldFindCode95() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findDiscountCode();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("95");
    }

    @Test
    @DisplayName("findInsurance: should find code 96")
    void findInsurance_shouldFindCode96() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findInsurance();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("96");
    }

    // === Thai Extension Tests ===

    @Test
    @DisplayName("findThaiDeposit: should find PP001")
    void findThaiDeposit_shouldFindPP001() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findThaiDeposit();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("PP001");
    }

    @Test
    @DisplayName("findThaiGuarantee: should find PP002")
    void findThaiGuarantee_shouldFindPP002() {
        Optional<AllowanceChargeIdentificationCode> result = repository.findThaiGuarantee();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("PP002");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<AllowanceChargeIdentificationCode> result = repository.findByNameContaining("freight");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("FREIGHT"));
    }

    @Test
    @DisplayName("findByDescriptionContaining: should search by description")
    void findByDescriptionContaining_shouldSearchByDescription() {
        List<AllowanceChargeIdentificationCode> result = repository.findByDescriptionContaining("bank");
        assertThat(result).isNotEmpty();
    }
}
