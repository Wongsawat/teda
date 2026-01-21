package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.ThaiMessageFunctionCode;
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
@ContextConfiguration(classes = ThaiMessageFunctionCodeRepositoryTest.TestConfig.class)
@DisplayName("ThaiMessageFunctionCodeRepository Integration Tests")
class ThaiMessageFunctionCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ThaiMessageFunctionCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "thai_message_function_code");
            DatabaseInitializer.loadTestData(dataSource, "thai_message_function_code");
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
                    classes = ThaiMessageFunctionCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = ThaiMessageFunctionCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all message function codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find existing code")
    void findByCode_shouldReturnEntity() {
        Optional<ThaiMessageFunctionCode> result = repository.findByCode("TIVC01");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("TIVC01");
    }

    @Test
    @DisplayName("findByCode: should return empty for invalid code")
    void findByCode_shouldReturnEmptyForInvalidCode() {
        assertThat(repository.findByCode("INVALID")).isEmpty();
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
        assertThat(repository.findById("TIVC01")).isPresent();
    }

    // === Active Codes Tests ===

    @Test
    @DisplayName("findAllActive: should return only active codes")
    void findAllActive_shouldReturnActiveCodes() {
        List<ThaiMessageFunctionCode> result = repository.findAllActive();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.isActive());
    }

    // === Debit Note Tests ===

    @Test
    @DisplayName("findDebitNoteCodes: should find all debit note codes")
    void findDebitNoteCodes_shouldFindDebitNoteCodes() {
        List<ThaiMessageFunctionCode> result = repository.findDebitNoteCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getDocumentType().equals("DebitNote") || c.getCode().startsWith("DBN"));
    }

    @Test
    @DisplayName("findDebitNoteGoodsCodes: should find debit note goods codes")
    void findDebitNoteGoodsCodes_shouldFindGoodsCodes() {
        List<ThaiMessageFunctionCode> result = repository.findDebitNoteGoodsCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().startsWith("DBNG"));
    }

    @Test
    @DisplayName("findDebitNoteServicesCodes: should find debit note services codes")
    void findDebitNoteServicesCodes_shouldFindServicesCodes() {
        List<ThaiMessageFunctionCode> result = repository.findDebitNoteServicesCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().startsWith("DBNS"));
    }

    @Test
    @DisplayName("findDebitNoteGoodsOriginal: should find DBNG01")
    void findDebitNoteGoodsOriginal_shouldFindDBNG01() {
        Optional<ThaiMessageFunctionCode> result = repository.findDebitNoteGoodsOriginal();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("DBNG01");
    }

    @Test
    @DisplayName("findDebitNoteServicesOriginal: should find DBNS01")
    void findDebitNoteServicesOriginal_shouldFindDBNS01() {
        Optional<ThaiMessageFunctionCode> result = repository.findDebitNoteServicesOriginal();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("DBNS01");
    }

    // === Credit Note Tests ===

    @Test
    @DisplayName("findCreditNoteCodes: should find all credit note codes")
    void findCreditNoteCodes_shouldFindCreditNoteCodes() {
        List<ThaiMessageFunctionCode> result = repository.findCreditNoteCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getDocumentType().equals("CreditNote") || c.getCode().startsWith("CDN"));
    }

    @Test
    @DisplayName("findCreditNoteGoodsCodes: should find credit note goods codes")
    void findCreditNoteGoodsCodes_shouldFindGoodsCodes() {
        List<ThaiMessageFunctionCode> result = repository.findCreditNoteGoodsCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().startsWith("CDNG"));
    }

    @Test
    @DisplayName("findCreditNoteGoodsOriginal: should find CDNG01")
    void findCreditNoteGoodsOriginal_shouldFindCDNG01() {
        Optional<ThaiMessageFunctionCode> result = repository.findCreditNoteGoodsOriginal();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("CDNG01");
    }

    @Test
    @DisplayName("findCreditNoteServicesOriginal: should find CDNS01")
    void findCreditNoteServicesOriginal_shouldFindCDNS01() {
        Optional<ThaiMessageFunctionCode> result = repository.findCreditNoteServicesOriginal();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("CDNS01");
    }

    // === Tax Invoice Tests ===

    @Test
    @DisplayName("findTaxInvoiceCodes: should find all tax invoice codes")
    void findTaxInvoiceCodes_shouldFindTaxInvoiceCodes() {
        List<ThaiMessageFunctionCode> result = repository.findTaxInvoiceCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getDocumentType().equals("TaxInvoice") || c.getCode().startsWith("TIV"));
    }

    @Test
    @DisplayName("findTaxInvoiceOriginal: should find TIVC01")
    void findTaxInvoiceOriginal_shouldFindTIVC01() {
        Optional<ThaiMessageFunctionCode> result = repository.findTaxInvoiceOriginal();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("TIVC01");
    }

    @Test
    @DisplayName("findTaxInvoiceReplacement: should find TIVC02")
    void findTaxInvoiceReplacement_shouldFindTIVC02() {
        Optional<ThaiMessageFunctionCode> result = repository.findTaxInvoiceReplacement();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("TIVC02");
    }

    // === Receipt Tests ===

    @Test
    @DisplayName("findReceiptCodes: should find all receipt codes")
    void findReceiptCodes_shouldFindReceiptCodes() {
        List<ThaiMessageFunctionCode> result = repository.findReceiptCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getDocumentType().equals("Receipt") || c.getCode().startsWith("RCT"));
    }

    @Test
    @DisplayName("findReceiptOriginal: should find RCTC01")
    void findReceiptOriginal_shouldFindRCTC01() {
        Optional<ThaiMessageFunctionCode> result = repository.findReceiptOriginal();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("RCTC01");
    }

    @Test
    @DisplayName("findReceiptReplacement: should find RCTC02")
    void findReceiptReplacement_shouldFindRCTC02() {
        Optional<ThaiMessageFunctionCode> result = repository.findReceiptReplacement();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("RCTC02");
    }

    @Test
    @DisplayName("findReceiptCopy: should find RCTC03")
    void findReceiptCopy_shouldFindRCTC03() {
        Optional<ThaiMessageFunctionCode> result = repository.findReceiptCopy();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("RCTC03");
    }

    @Test
    @DisplayName("findReceiptCancellation: should find RCTC04")
    void findReceiptCancellation_shouldFindRCTC04() {
        Optional<ThaiMessageFunctionCode> result = repository.findReceiptCancellation();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("RCTC04");
    }

    // === Function Type Tests ===

    @Test
    @DisplayName("findOriginalDocumentCodes: should find codes ending with 01")
    void findOriginalDocumentCodes_shouldFindOriginalCodes() {
        List<ThaiMessageFunctionCode> result = repository.findOriginalDocumentCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().endsWith("01"));
    }

    @Test
    @DisplayName("findReplacementDocumentCodes: should find codes ending with 02")
    void findReplacementDocumentCodes_shouldFindReplacementCodes() {
        List<ThaiMessageFunctionCode> result = repository.findReplacementDocumentCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().endsWith("02"));
    }

    @Test
    @DisplayName("findCancellationDocumentCodes: should find codes ending with 03")
    void findCancellationDocumentCodes_shouldFindCancellationCodes() {
        List<ThaiMessageFunctionCode> result = repository.findCancellationDocumentCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().endsWith("03"));
    }

    @Test
    @DisplayName("findCopyDocumentCodes: should find codes ending with 04")
    void findCopyDocumentCodes_shouldFindCopyCodes() {
        List<ThaiMessageFunctionCode> result = repository.findCopyDocumentCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().endsWith("04"));
    }

    @Test
    @DisplayName("findAdditionDocumentCodes: should find codes ending with 05")
    void findAdditionDocumentCodes_shouldFindAdditionCodes() {
        List<ThaiMessageFunctionCode> result = repository.findAdditionDocumentCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().endsWith("05"));
    }

    @Test
    @DisplayName("findOtherDocumentCodes: should find codes ending with 99")
    void findOtherDocumentCodes_shouldFindOtherCodes() {
        List<ThaiMessageFunctionCode> result = repository.findOtherDocumentCodes();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> c.getCode().endsWith("99"));
    }
}
