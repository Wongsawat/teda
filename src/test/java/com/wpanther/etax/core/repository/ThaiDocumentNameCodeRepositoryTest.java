package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ThaiDocumentNameCode;
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
@ContextConfiguration(classes = ThaiDocumentNameCodeRepositoryTest.TestConfig.class)
@DisplayName("ThaiDocumentNameCodeRepository Integration Tests")
class ThaiDocumentNameCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ThaiDocumentNameCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "thai_document_name_code");
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
                    classes = ThaiDocumentNameCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ThaiDocumentNameCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all document codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find existing document")
    void findByCode_shouldReturnEntity() {
        Optional<ThaiDocumentNameCode> result = repository.findByCode("380");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("380");
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
        assertThat(repository.existsByCode("380")).isTrue();
        assertThat(repository.existsByCode("381")).isTrue();
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
        assertThat(repository.findById("380")).isPresent();
    }

    // === Standard vs Extension Tests ===

    @Test
    @DisplayName("findStandardCodes: should return UN/CEFACT standard codes")
    void findStandardCodes_shouldReturnStandardCodes() {
        List<ThaiDocumentNameCode> result = repository.findStandardCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> Boolean.TRUE.equals(c.getStandardCode()));
    }

    @Test
    @DisplayName("findThaiExtensions: should return Thai extension codes")
    void findThaiExtensions_shouldReturnThaiExtensions() {
        List<ThaiDocumentNameCode> result = repository.findThaiExtensions();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> Boolean.TRUE.equals(c.getThaiExtension()));
        assertThat(result).anyMatch(c -> c.getCode().startsWith("T"));
    }

    // === Document Type Tests ===

    @Test
    @DisplayName("findTaxInvoiceTypes: should find tax invoice types")
    void findTaxInvoiceTypes_shouldFindTaxInvoices() {
        List<ThaiDocumentNameCode> result = repository.findTaxInvoiceTypes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getNameEn().toLowerCase().contains("tax invoice"));
    }

    @Test
    @DisplayName("findReceiptTypes: should find receipt types")
    void findReceiptTypes_shouldFindReceipts() {
        List<ThaiDocumentNameCode> result = repository.findReceiptTypes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getNameEn().toLowerCase().contains("receipt"));
    }

    // === Debit/Credit Note Tests ===

    @Test
    @DisplayName("findDebitNoteCodes: should find debit notes")
    void findDebitNoteCodes_shouldFindDebitNotes() {
        List<ThaiDocumentNameCode> result = repository.findDebitNoteCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "383".equals(c.getCode()) || c.getNameEn().toLowerCase().contains("debit"));
    }

    @Test
    @DisplayName("findCreditNoteCodes: should find credit notes")
    void findCreditNoteCodes_shouldFindCreditNotes() {
        List<ThaiDocumentNameCode> result = repository.findCreditNoteCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "381".equals(c.getCode()) || c.getNameEn().toLowerCase().contains("credit"));
    }

    // === Abbreviated and Cancellation Tests ===

    @Test
    @DisplayName("findAbbreviatedTaxInvoiceTypes: should find abbreviated types")
    void findAbbreviatedTaxInvoiceTypes_shouldFindAbbreviated() {
        List<ThaiDocumentNameCode> result = repository.findAbbreviatedTaxInvoiceTypes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "T05".equals(c.getCode()) || "T06".equals(c.getCode()));
    }

    @Test
    @DisplayName("findCancellationNote: should find cancellation note")
    void findCancellationNote_shouldFindCancellation() {
        Optional<ThaiDocumentNameCode> result = repository.findCancellationNote();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("T07");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameThContaining: should search by Thai name")
    void findByNameThContaining_shouldSearchByThaiName() {
        List<ThaiDocumentNameCode> result = repository.findByNameThContaining("ใบ");
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findByNameEnContaining: should search by English name")
    void findByNameEnContaining_shouldSearchByEnglishName() {
        List<ThaiDocumentNameCode> result = repository.findByNameEnContaining("invoice");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getNameEn().toLowerCase().contains("invoice"));
    }
}
