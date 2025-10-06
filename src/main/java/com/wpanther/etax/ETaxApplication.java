package com.wpanther.etax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot Application for Thai e-Tax Invoice
 *
 * This application demonstrates database-backed JAXB implementation
 * for large code lists (ISO codes, UN/CEFACT codes, Thai codes)
 *
 * Features:
 * - Database-backed XML marshalling/unmarshalling
 * - Full JAXB namespace preservation
 * - Spring Data JPA for data access
 * - Support for large code lists (798 reference types, 8,940 subdivisions)
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.wpanther.etax.repository")
public class ETaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETaxApplication.class, args);
    }
}
