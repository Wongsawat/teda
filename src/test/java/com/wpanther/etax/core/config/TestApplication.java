package com.wpanther.etax.core.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot test application for integration tests.
 *
 * Since this is a library without a main application class,
 * this class provides the necessary Spring Boot configuration
 * for tests that require a full application context.
 */
@SpringBootApplication(scanBasePackages = "com.wpanther.etax.core")
@EnableJpaRepositories(basePackages = "com.wpanther.etax.core.repository")
@EntityScan(basePackages = "com.wpanther.etax.core.entity")
public class TestApplication {
}
