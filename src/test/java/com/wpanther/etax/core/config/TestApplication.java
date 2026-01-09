package com.wpanther.etax.core.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.wpanther.etax.core.entity")
@EnableJpaRepositories(basePackages = "com.wpanther.etax.core.repository")
public class TestApplication {
}
