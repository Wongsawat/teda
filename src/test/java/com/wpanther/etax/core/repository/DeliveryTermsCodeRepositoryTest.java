package com.wpanther.etax.core.repository;

import com.wpanther.etax.core.config.DatabaseInitializer;
import com.wpanther.etax.core.config.PostgresTestContainer;
import com.wpanther.etax.core.entity.DeliveryTermsCode;
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
@ContextConfiguration(classes = DeliveryTermsCodeRepositoryTest.TestConfig.class)
@DisplayName("DeliveryTermsCodeRepository Integration Tests")
class DeliveryTermsCodeRepositoryTest extends PostgresTestContainer {

    @Autowired
    private DeliveryTermsCodeRepository repository;

    @Autowired
    private DataSource dataSource;

    private static boolean schemaInitialized = false;

    @BeforeAll
    static void setUpSchema(@Autowired DataSource dataSource) {
        if (!schemaInitialized) {
            DatabaseInitializer.initializeSchema(dataSource, "delivery_terms_code");
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
                    classes = DeliveryTermsCodeRepository.class
            ),
            basePackages = "com.wpanther.etax.core.repository"
    )
    @EntityScan(basePackageClasses = DeliveryTermsCode.class)
    static class TestConfig {
    }

    // === Query Method Tests ===

    @Test
    @DisplayName("findAll: should return all delivery terms codes")
    void findAll_shouldReturnAllRecords() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("findByCode: should find EXW")
    void findByCode_shouldFindEXW() {
        Optional<DeliveryTermsCode> result = repository.findByCode("EXW");
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("EXW");
    }

    @Test
    @DisplayName("findByCode: should be case insensitive")
    void findByCode_shouldBeCaseInsensitive() {
        Optional<DeliveryTermsCode> upper = repository.findByCode("EXW");
        Optional<DeliveryTermsCode> lower = repository.findByCode("exw");

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
        assertThat(repository.existsByCode("EXW")).isTrue();
        assertThat(repository.existsByCode("FOB")).isTrue();
        assertThat(repository.existsByCode("CIF")).isTrue();
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
        assertThat(repository.findById("EXW")).isPresent();
    }

    // === INCOTERMS Tests ===

    @Test
    @DisplayName("findAllIncoterms: should return all INCOTERMS codes")
    void findAllIncoterms_shouldReturnIncoterms() {
        List<DeliveryTermsCode> result = repository.findAllIncoterms();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(DeliveryTermsCode::isIncoterm);
    }

    @Test
    @DisplayName("findNonIncoterms: should return non-INCOTERMS codes")
    void findNonIncoterms_shouldReturnNonIncoterms() {
        List<DeliveryTermsCode> result = repository.findNonIncoterms();
        assertThat(result).allMatch(c -> !c.isIncoterm());
    }

    // === INCOTERMS Group Tests ===

    @Test
    @DisplayName("findGroupE: should find Group E codes")
    void findGroupE_shouldFindGroupE() {
        List<DeliveryTermsCode> result = repository.findGroupE();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "E".equals(c.getIncotermGroup()));
        assertThat(result).anyMatch(c -> "EXW".equals(c.getCode()));
    }

    @Test
    @DisplayName("findGroupF: should find Group F codes")
    void findGroupF_shouldFindGroupF() {
        List<DeliveryTermsCode> result = repository.findGroupF();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "F".equals(c.getIncotermGroup()));
        assertThat(result).anyMatch(c -> "FOB".equals(c.getCode()) || "FCA".equals(c.getCode()));
    }

    @Test
    @DisplayName("findGroupC: should find Group C codes")
    void findGroupC_shouldFindGroupC() {
        List<DeliveryTermsCode> result = repository.findGroupC();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "C".equals(c.getIncotermGroup()));
        assertThat(result).anyMatch(c -> "CFR".equals(c.getCode()) || "CIF".equals(c.getCode()));
    }

    @Test
    @DisplayName("findGroupD: should find Group D codes")
    void findGroupD_shouldFindGroupD() {
        List<DeliveryTermsCode> result = repository.findGroupD();
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(c -> "D".equals(c.getIncotermGroup()));
        assertThat(result).anyMatch(c -> "DAP".equals(c.getCode()) || "DDP".equals(c.getCode()));
    }

    // === Specific INCOTERMS Tests ===

    @Test
    @DisplayName("findEXW: should find Ex Works")
    void findEXW_shouldFindEXW() {
        Optional<DeliveryTermsCode> result = repository.findEXW();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("EXW");
        assertThat(result.get().getSellerObligation()).isEqualTo("Minimum");
    }

    @Test
    @DisplayName("findFOB: should find Free On Board")
    void findFOB_shouldFindFOB() {
        Optional<DeliveryTermsCode> result = repository.findFOB();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("FOB");
    }

    @Test
    @DisplayName("findCIF: should find Cost Insurance and Freight")
    void findCIF_shouldFindCIF() {
        Optional<DeliveryTermsCode> result = repository.findCIF();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("CIF");
    }

    @Test
    @DisplayName("findDDP: should find Delivered Duty Paid")
    void findDDP_shouldFindDDP() {
        Optional<DeliveryTermsCode> result = repository.findDDP();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("DDP");
        assertThat(result.get().getSellerObligation()).isEqualTo("Maximum");
    }

    @Test
    @DisplayName("findFCA: should find Free Carrier")
    void findFCA_shouldFindFCA() {
        Optional<DeliveryTermsCode> result = repository.findFCA();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("FCA");
    }

    @Test
    @DisplayName("findCFR: should find Cost and Freight")
    void findCFR_shouldFindCFR() {
        Optional<DeliveryTermsCode> result = repository.findCFR();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("CFR");
    }

    @Test
    @DisplayName("findDAP: should find Delivered At Place")
    void findDAP_shouldFindDAP() {
        Optional<DeliveryTermsCode> result = repository.findDAP();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("DAP");
    }

    @Test
    @DisplayName("findDPU: should find Delivered At Place Unloaded")
    void findDPU_shouldFindDPU() {
        Optional<DeliveryTermsCode> result = repository.findDPU();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("DPU");
    }

    // === Special Feature Tests ===

    @Test
    @DisplayName("findWithInsurance: should find terms including insurance")
    void findWithInsurance_shouldFindWithInsurance() {
        List<DeliveryTermsCode> result = repository.findWithInsurance();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "CIF".equals(c.getCode()) || "CIP".equals(c.getCode()));
    }

    @Test
    @DisplayName("findWithFreight: should find terms including freight")
    void findWithFreight_shouldFindWithFreight() {
        List<DeliveryTermsCode> result = repository.findWithFreight();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "CFR".equals(c.getCode()) || "CIF".equals(c.getCode()));
    }

    @Test
    @DisplayName("findSeaTransportOnly: should find sea transport only terms")
    void findSeaTransportOnly_shouldFindSeaTransport() {
        List<DeliveryTermsCode> result = repository.findSeaTransportOnly();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "FAS".equals(c.getCode()) || "FOB".equals(c.getCode()));
    }

    @Test
    @DisplayName("findAnyTransportMode: should find any transport mode terms")
    void findAnyTransportMode_shouldFindAnyTransport() {
        List<DeliveryTermsCode> result = repository.findAnyTransportMode();
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> "EXW".equals(c.getCode()) || "FCA".equals(c.getCode()));
    }

    // === Seller Obligation Tests ===

    @Test
    @DisplayName("findMinimumSellerObligation: should find EXW")
    void findMinimumSellerObligation_shouldFindMinimum() {
        Optional<DeliveryTermsCode> result = repository.findMinimumSellerObligation();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("EXW");
        assertThat(result.get().getSellerObligation()).isEqualTo("Minimum");
    }

    @Test
    @DisplayName("findMaximumSellerObligation: should find DDP")
    void findMaximumSellerObligation_shouldFindMaximum() {
        Optional<DeliveryTermsCode> result = repository.findMaximumSellerObligation();
        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("DDP");
        assertThat(result.get().getSellerObligation()).isEqualTo("Maximum");
    }

    // === Common INCOTERMS Tests ===

    @Test
    @DisplayName("findCommonIncoterms: should return commonly used INCOTERMS")
    void findCommonIncoterms_shouldReturnCommon() {
        List<DeliveryTermsCode> result = repository.findCommonIncoterms();
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCode()).isEqualTo("EXW"); // EXW should be first
        assertThat(result).anyMatch(c -> "FOB".equals(c.getCode()));
        assertThat(result).anyMatch(c -> "CIF".equals(c.getCode()));
    }

    // === Search Tests ===

    @Test
    @DisplayName("findByNameContaining: should search by name")
    void findByNameContaining_shouldSearchByName() {
        List<DeliveryTermsCode> result = repository.findByNameContaining("works");
        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(c -> c.getName().toUpperCase().contains("WORKS"));
    }
}
