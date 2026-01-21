package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.UNECEDocumentNameCodeInvoice;
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
@ContextConfiguration(classes = UNECEDocumentNameCodeInvoiceRepositoryTest.TestConfig.class)
@DisplayName("UNCEDocumentNameCodeInvoiceRepository Integration Tests")
class UNECEDocumentNameCodeInvoiceRepositoryTest extends PostgresTestContainer {

    @Autowired
    private UNECEDocumentNameCodeInvoiceRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "unece_document_name_code_invoice");
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
                    classes = UNECEDocumentNameCodeInvoiceRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = UNECEDocumentNameCodeInvoice.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all document codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find commercial invoice (380)")
    void findByCode_shouldFindCommercialInvoice() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findByCode("380");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("380");
    }

    @Test
    @DisplayName("findByCode: should find credit note (381)")
    void findByCode_shouldFindCreditNote() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findByCode("381");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("381");
    }

    @Test
    @DisplayName("findByCode: should find debit note (383)")
    void findByCode_shouldFindDebitNote() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findByCode("383");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("383");
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("999")).isEmpty();
    }

    // === Existence Check Tests ===

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

    // === Category Tests ===

    @Test
    @DisplayName("findByCategory: should find invoice category")
    void findByCategory_shouldFindInvoiceCategory() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findByCategory("Invoice");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Invoice".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findInvoices: should find all invoices")
    void findInvoices_shouldFindInvoices() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findInvoices();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "380".equals(c.getCode()));
    }

    @Test
    @DisplayName("findCreditNotes: should find all credit notes")
    void findCreditNotes_shouldFindCreditNotes() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findCreditNotes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getIsCredit() || "381".equals(c.getCode()));
    }

    @Test
    @DisplayName("findDebitNotes: should find all debit notes")
    void findDebitNotes_shouldFindDebitNotes() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findDebitNotes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getIsDebit() || "383".equals(c.getCode()));
    }

    @Test
    @DisplayName("findSpecialDocuments: should find special documents")
    void findSpecialDocuments_shouldFindSpecialDocuments() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findSpecialDocuments();
        assertThat(result).allMatch(c -> "Special".equals(c.getCategory()));
    }

    // === Payment Tests ===

    @Test
    @DisplayName("findByRequiresPaymentTrue: should find documents requiring payment")
    void findByRequiresPaymentTrue_shouldFindPaymentRequired() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findByRequiresPaymentTrue();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(UNECEDocumentNameCodeInvoice::getRequiresPayment);
    }

    @Test
    @DisplayName("findByRequiresPaymentFalse: should find documents not requiring payment")
    void findByRequiresPaymentFalse_shouldFindPaymentNotRequired() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findByRequiresPaymentFalse();
        assertThat(result).allMatch(c -> !c.getRequiresPayment());
    }

    // === Common Codes Tests ===

    @Test
    @DisplayName("findCommonCodes: should find commonly used codes")
    void findCommonCodes_shouldFindCommonCodes() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findCommonCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "380".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "381".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "383".equals(c.getCode()));
    }

    // === Default Method Tests ===

    @Test
    @DisplayName("findCommercialInvoice: should return code 380")
    void findCommercialInvoice_shouldReturn380() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findCommercialInvoice();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("380");
    }

    @Test
    @DisplayName("findCreditNote: should return code 381")
    void findCreditNote_shouldReturn381() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findCreditNote();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("381");
    }

    @Test
    @DisplayName("findDebitNote: should return code 383")
    void findDebitNote_shouldReturn383() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findDebitNote();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("383");
    }

    @Test
    @DisplayName("findProformaInvoice: should return code 325")
    void findProformaInvoice_shouldReturn325() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findProformaInvoice();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("325");
    }

    @Test
    @DisplayName("findPrepaymentInvoice: should return code 386")
    void findPrepaymentInvoice_shouldReturn386() {
        Optional<UNECEDocumentNameCodeInvoice> result = repository.findPrepaymentInvoice();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("386");
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContainingIgnoreCase: should search by name")
    void findByNameContainingIgnoreCase_shouldSearchByName() {
        List<UNECEDocumentNameCodeInvoice> result = repository.findByNameContainingIgnoreCase("invoice");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toLowerCase().contains("invoice"));
    }

    @Test
    @DisplayName("countByCategory: should group by category")
    void countByCategory_shouldGroupByCategory() {
        List<Object[]> result = repository.countByCategory();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(row -> row[0].equals("Invoice"));
    }
}
