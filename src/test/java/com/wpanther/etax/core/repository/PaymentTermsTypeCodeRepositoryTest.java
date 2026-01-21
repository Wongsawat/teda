package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.PaymentTermsTypeCode;
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
@ContextConfiguration(classes = PaymentTermsTypeCodeRepositoryTest.TestConfig.class)
@DisplayName("PaymentTermsTypeCodeRepository Integration Tests")
class PaymentTermsTypeCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private PaymentTermsTypeCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "payment_terms_type_code");
            DatabaseInitializer.loadTestData(dataSource, "payment_terms_type_code");
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
                    classes = PaymentTermsTypeCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = PaymentTermsTypeCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all payment terms type codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find existing code")
    void findByCode_shouldReturnEntity() {
        Optional<PaymentTermsTypeCode> result = repository.findByCode("1");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<PaymentTermsTypeCode> upper = repository.findByCode("ZZZ");
        Optional<PaymentTermsTypeCode> lower = repository.findByCode("zzz");

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
        assertThat(repository.existsByCode("10")).isTrue();
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

    // === Payment Type Tests ===

    @Test
    @DisplayName("findImmediatePaymentTerms: should find immediate payment terms")
    void findImmediatePaymentTerms_shouldFindImmediate() {
        List<PaymentTermsTypeCode> result = repository.findImmediatePaymentTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(PaymentTermsTypeCode::isImmediate);
    }

    @Test
    @DisplayName("findDeferredPaymentTerms: should find deferred payment terms")
    void findDeferredPaymentTerms_shouldFindDeferred() {
        List<PaymentTermsTypeCode> result = repository.findDeferredPaymentTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(PaymentTermsTypeCode::isDeferred);
    }

    @Test
    @DisplayName("findDiscountPaymentTerms: should find discount payment terms")
    void findDiscountPaymentTerms_shouldFindDiscount() {
        List<PaymentTermsTypeCode> result = repository.findDiscountPaymentTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(PaymentTermsTypeCode::hasDiscount);
    }

    // === Category Tests ===

    @Test
    @DisplayName("findByCategory: should find by category")
    void findByCategory_shouldFindByCategory() {
        List<PaymentTermsTypeCode> result = repository.findByCategory("Standard Terms");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Standard Terms".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findAllCategories: should return all categories")
    void findAllCategories_shouldReturnAllCategories() {
        List<String> result = repository.findAllCategories();
        assertThat(result).isNotEmpty();
    }

    // === Special Payment Type Tests ===

    @Test
    @DisplayName("findLetterOfCreditTerms: should find letter of credit terms")
    void findLetterOfCreditTerms_shouldFindLetterOfCredit() {
        List<PaymentTermsTypeCode> result = repository.findLetterOfCreditTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getDescription().toUpperCase().contains("LETTER OF CREDIT") ||
                                           c.getName().toUpperCase().contains("CREDIT"));
    }

    @Test
    @DisplayName("findAdvancePaymentTerms: should find advance payment terms")
    void findAdvancePaymentTerms_shouldFindAdvancePayment() {
        List<PaymentTermsTypeCode> result = repository.findAdvancePaymentTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getDescription().toUpperCase().contains("ADVANCE") ||
                                           c.getName().toUpperCase().contains("ADVANCE"));
    }

    @Test
    @DisplayName("findInstallmentPaymentTerms: should find installment payment terms")
    void findInstallmentPaymentTerms_shouldFindInstallment() {
        List<PaymentTermsTypeCode> result = repository.findInstallmentPaymentTerms();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getDescription().toUpperCase().contains("INSTAL") ||
                                           c.getName().toUpperCase().contains("INSTAL"));
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<PaymentTermsTypeCode> result = repository.findByNameContaining("cash");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("CASH"));
    }

    @Test
    @DisplayName("findByDescriptionContaining: should search by description")
    void findByDescriptionContaining_shouldSearchByDescription() {
        List<PaymentTermsTypeCode> result = repository.findByDescriptionContaining("payment");
        assertThat(result).isNotEmpty();
    }
}
