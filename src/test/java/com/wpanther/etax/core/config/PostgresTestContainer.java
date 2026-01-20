package com.wpanther.etax.core.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Shared PostgreSQL container for integration tests.
 * Uses a singleton pattern so all tests share the same container instance,
 * reducing test execution time significantly compared to per-test containers.
 *
 * <p>Tests that need PostgreSQL integration should extend this class.
 * The container is started once when the class is loaded and remains running
 * for all tests in the JVM, then is automatically cleaned up.
 *
 * <p>Usage:
 * <pre>
 * class MyRepositoryTest extends PostgresTestContainer {
 *     // ... test methods
 * }
 * </pre>
 */
@Testcontainers
public abstract class PostgresTestContainer {

    /**
     * Singleton PostgreSQL container instance.
     * Uses PostgreSQL 16 Alpine for smaller image size and faster startup.
     * Protected to allow subclasses in other packages to access the container.
     */
    protected static final PostgreSQLContainer<?> postgres;

    static {
        DockerImageName imageName = DockerImageName.parse("docker.io/library/postgres:16-alpine")
                .asCompatibleSubstituteFor("postgres");
        postgres = new PostgreSQLContainer<>(imageName)
                .withDatabaseName("etax_test")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true); // Enable container reuse across test runs
        postgres.start();
    }

    /**
     * Dynamically configures Spring datasource properties to use the Testcontainers PostgreSQL.
     * This method is called by Spring TestContext framework before each test class initialization.
     *
     * @param registry the dynamic property registry to add properties to
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    /**
     * Returns the JDBC URL of the running PostgreSQL container.
     * Useful for tests that need direct JDBC access.
     *
     * @return the JDBC URL
     */
    protected static String getJdbcUrl() {
        return postgres.getJdbcUrl();
    }

    /**
     * Returns the database username.
     *
     * @return the username
     */
    protected static String getUsername() {
        return postgres.getUsername();
    }

    /**
     * Returns the database password.
     *
     * @return the password
     */
    protected static String getPassword() {
        return postgres.getPassword();
    }
}
