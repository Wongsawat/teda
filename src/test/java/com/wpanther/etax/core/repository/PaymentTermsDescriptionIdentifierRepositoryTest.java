package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.PaymentTermsDescriptionIdentifier;
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
@ContextConfiguration(classes = PaymentTermsDescriptionIdentifierRepositoryTest.TestConfig.class)
@DisplayName("PaymentTermsDescriptionIdentifierRepository Integration Tests")
class PaymentTermsDescriptionIdentifierRepositoryTest extends PostgresTestContainer {

    @Autowired
    private PaymentTermsDescriptionIdentifierRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "payment_terms_description_identifier");
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
                    classes = PaymentTermsDescriptionIdentifierRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = PaymentTermsDescriptionIdentifier.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all payment terms description identifiers")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find existing code")
    void findByCode_shouldReturnEntity() {
        Optional<PaymentTermsDescriptionIdentifier> result = repository.findByCode("1");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
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
        assertThat(repository.existsByCode("1")).isTrue();
        assertThat(repository.existsByCode("2")).isTrue();
        assertThat(repository.existsByCode("6")).isTrue();
    }

    @Test
    @DisplayName("existsByCode: should return false for invalid code")
    void existsByCode_shouldReturnFalseForInvalidCode() {
        assertThat(repository.existsByCode("99")).isFalse();
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

    // === Draft Tests ===

    @Test
    @DisplayName("findDraftRequired: should find codes requiring draft")
    void findDraftRequired_shouldFindDraftRequired() {
        List<PaymentTermsDescriptionIdentifier> result = repository.findDraftRequired();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(PaymentTermsDescriptionIdentifier::isDraftRequired);
    }

    @Test
    @DisplayName("findNoDraft: should find codes not requiring draft")
    void findNoDraft_shouldFindNoDraft() {
        List<PaymentTermsDescriptionIdentifier> result = repository.findNoDraft();
        assertThat(result).isNotEmpty();
        assertThat(result).noneMatch(PaymentTermsDescriptionIdentifier::isDraftRequired);
    }

    @Test
    @DisplayName("findBankingRelated: should find banking-related codes")
    void findBankingRelated_shouldFindBankingRelated() {
        List<PaymentTermsDescriptionIdentifier> result = repository.findBankingRelated();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toLowerCase().contains("bank"));
    }

    // === Specific Code Tests ===

    @Test
    @DisplayName("findIssuingBankDraft: should find code 1")
    void findIssuingBankDraft_shouldFindCode1() {
        Optional<PaymentTermsDescriptionIdentifier> result = repository.findIssuingBankDraft();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
        assertThat(result.get().getName().toLowerCase()).contains("issuing");
    }

    @Test
    @DisplayName("findAdvisingBankDraft: should find code 2")
    void findAdvisingBankDraft_shouldFindCode2() {
        Optional<PaymentTermsDescriptionIdentifier> result = repository.findAdvisingBankDraft();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("2");
    }

    @Test
    @DisplayName("findReimbursingBankDraft: should find code 3")
    void findReimbursingBankDraft_shouldFindCode3() {
        Optional<PaymentTermsDescriptionIdentifier> result = repository.findReimbursingBankDraft();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("3");
    }

    @Test
    @DisplayName("findNoDraftOption: should find code 6")
    void findNoDraftOption_shouldFindCode6() {
        Optional<PaymentTermsDescriptionIdentifier> result = repository.findNoDraftOption();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("6");
        assertThat(result.get().isDraftRequired()).isFalse();
    }

    @Test
    @DisplayName("findCommercialAccountSummary: should find code 7")
    void findCommercialAccountSummary_shouldFindCode7() {
        Optional<PaymentTermsDescriptionIdentifier> result = repository.findCommercialAccountSummary();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("7");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<PaymentTermsDescriptionIdentifier> result = repository.findByNameContaining("bank");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toLowerCase().contains("bank"));
    }

    @Test
    @DisplayName("findByDescriptionContaining: should search by description")
    void findByDescriptionContaining_shouldSearchByDescription() {
        List<PaymentTermsDescriptionIdentifier> result = repository.findByDescriptionContaining("draft");
        assertThat(result).isNotEmpty();
    }
}
