package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.MessageFunctionCode;
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
@ContextConfiguration(classes = MessageFunctionCodeRepositoryTest.TestConfig.class)
@DisplayName("MessageFunctionCodeRepository Integration Tests")
class MessageFunctionCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private MessageFunctionCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "message_function_code");
            DatabaseInitializer.loadTestData(dataSource, "message_function_code");
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
                    classes = MessageFunctionCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = MessageFunctionCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all message function codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find original function (9)")
    void findByCode_shouldFindOriginalFunction() {
        Optional<MessageFunctionCode> result = repository.findByCode("9");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("9");
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
        assertThat(repository.existsByCode("9")).isTrue();
        assertThat(repository.existsByCode("1")).isTrue();
        assertThat(repository.existsByCode("4")).isTrue();
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
        assertThat(repository.findById("9")).isPresent();
    }

    // === Modification Tests ===

    @Test
    @DisplayName("findModifications: should return modification functions")
    void findModifications_shouldReturnModifications() {
        List<MessageFunctionCode> result = repository.findModifications();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(MessageFunctionCode::isModification);
    }

    @Test
    @DisplayName("findOriginals: should return original transmission functions")
    void findOriginals_shouldReturnOriginals() {
        List<MessageFunctionCode> result = repository.findOriginals();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(MessageFunctionCode::isOriginal);
    }

    @Test
    @DisplayName("findAcceptanceRelated: should return acceptance functions")
    void findAcceptanceRelated_shouldReturnAcceptance() {
        List<MessageFunctionCode> result = repository.findAcceptanceRelated();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(MessageFunctionCode::isAcceptance);
    }

    // === Category Tests ===

    @Test
    @DisplayName("findByCategory: should find by category")
    void findByCategory_shouldFindByCategory() {
        List<MessageFunctionCode> result = repository.findByCategory("Cancellation");
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Cancellation".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findAllCategories: should return all categories")
    void findAllCategories_shouldReturnAllCategories() {
        List<String> result = repository.findAllCategories();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("findTransactionControl: should find cancellation functions")
    void findTransactionControl_shouldFindTransactionControl() {
        List<MessageFunctionCode> result = repository.findTransactionControl();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Cancellation".equals(c.getCategory()));
    }

    @Test
    @DisplayName("findMessageStatus: should find status functions")
    void findMessageStatus_shouldFindMessageStatus() {
        List<MessageFunctionCode> result = repository.findMessageStatus();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "Status".equals(c.getCategory()) || "Processing Status".equals(c.getCategory()));
    }

    // === Function Type Tests ===

    @Test
    @DisplayName("findCancellations: should find cancellation functions")
    void findCancellations_shouldFindCancellations() {
        List<MessageFunctionCode> result = repository.findCancellations();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "1".equals(c.getCode()));
    }

    @Test
    @DisplayName("findChanges: should find change/amendment functions")
    void findChanges_shouldFindChanges() {
        List<MessageFunctionCode> result = repository.findChanges();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "4".equals(c.getCode()));
    }

    @Test
    @DisplayName("findReplacements: should find replacement functions")
    void findReplacements_shouldFindReplacements() {
        List<MessageFunctionCode> result = repository.findReplacements();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "5".equals(c.getCode()));
    }

    @Test
    @DisplayName("findConfirmations: should find confirmation functions")
    void findConfirmations_shouldFindConfirmations() {
        List<MessageFunctionCode> result = repository.findConfirmations();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "6".equals(c.getCode()));
    }

    @Test
    @DisplayName("findOriginalFunction: should find code 9")
    void findOriginalFunction_shouldFindCode9() {
        Optional<MessageFunctionCode> result = repository.findOriginalFunction();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("9");
        assertThat(result.get().isOriginal()).isTrue();
    }

    @Test
    @DisplayName("findCancellationFunction: should find code 1")
    void findCancellationFunction_shouldFindCode1() {
        Optional<MessageFunctionCode> result = repository.findCancellationFunction();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("1");
    }

    @Test
    @DisplayName("findChangeFunction: should find code 4")
    void findChangeFunction_shouldFindCode4() {
        Optional<MessageFunctionCode> result = repository.findChangeFunction();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("4");
    }

    @Test
    @DisplayName("findReplaceFunction: should find code 5")
    void findReplaceFunction_shouldFindCode5() {
        Optional<MessageFunctionCode> result = repository.findReplaceFunction();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("5");
    }

    @Test
    @DisplayName("findConfirmationFunction: should find code 6")
    void findConfirmationFunction_shouldFindCode6() {
        Optional<MessageFunctionCode> result = repository.findConfirmationFunction();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("6");
    }

    // === Common Codes Tests ===

    @Test
    @DisplayName("findCommonCodes: should return commonly used codes")
    void findCommonCodes_shouldReturnCommonCodes() {
        List<MessageFunctionCode> result = repository.findCommonCodes();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCode()).isEqualTo("9"); // Original should be first
        assertThat(result).anyMatch(c -> "4".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "5".equals(c.getCode()));
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<MessageFunctionCode> result = repository.findByNameContaining("original");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("ORIGINAL"));
    }

    @Test
    @DisplayName("findByDescriptionContaining: should search by description")
    void findByDescriptionContaining_shouldSearchByDescription() {
        List<MessageFunctionCode> result = repository.findByDescriptionContaining("transmission");
        assertThat(result).isNotEmpty();
    }
}
