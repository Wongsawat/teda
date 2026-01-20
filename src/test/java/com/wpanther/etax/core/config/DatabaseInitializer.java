package com.wpanther.etax.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for initializing the PostgreSQL test database with schema and data.
 *
 * <p>This class loads SQL files from the classpath and executes them against the test database.
 * It handles PostgreSQL-specific features like triggers, functions, views, and check constraints
 * that are not supported by H2.
 *
 * <p>Schema files are loaded in a specific order to handle dependencies between tables.
 */
public class DatabaseInitializer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    /**
     * List of schema SQL files in the order they should be executed.
     * This order respects foreign key dependencies between tables.
     */
    private static final List<String> SCHEMA_FILES = Arrays.asList(
            // Core ISO code tables (no dependencies)
            "db/iso_country_code.sql",
            "db/iso_currency_code.sql",
            "db/iso_language_code.sql",

            // UNECE code tables
            "db/unece_reference_type_code.sql",
            "db/document_name_code_invoice.sql",
            "db/freight_cost_code.sql",

            // Thai-specific code tables
            "db/thai_province_code.sql",
            "db/thai_message_function_code.sql",
            "db/thai_document_name_code.sql",
            "db/thai_category_code.sql",

            // TISI geographic code tables
            "db/tisi_subdistrict.sql",
            "db/tisi_city_name.sql",

            // Additional code tables
            "db/address_type.sql",
            "db/allowance_charge_identification_code.sql",
            "db/allowance_charge_reason_code.sql",
            "db/delivery_terms_code.sql",
            "db/message_function_code.sql",
            "db/payment_terms_description_identifier.sql",
            "db/payment_terms_type_code.sql",
            "db/duty_tax_fee_type_code.sql"
    );

    /**
     * List of data SQL files for loading initial/test data.
     */
    private static final List<String> DATA_FILES = Arrays.asList(
            "db/iso_country_code_data.sql",
            "db/iso_currency_code_data.sql",
            "db/iso_language_code_data.sql",
            "db/unece_reference_type_code_data.sql",
            "db/thai_province_code_data.sql",
            "db/thai_message_function_code_data.sql",
            "db/duty_tax_fee_type_code_data.sql"
    );

    private DatabaseInitializer() {
        // Utility class - prevent instantiation
    }

    /**
     * Initializes all database schemas by executing the schema SQL files.
     * This method drops existing objects and recreates them from scratch.
     *
     * @param dataSource the DataSource to execute SQL against
     */
    public static void initializeAllSchemas(DataSource dataSource) {
        log.info("Initializing all database schemas...");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.setSeparator(";;");

        for (String schemaFile : SCHEMA_FILES) {
            ClassPathResource resource = new ClassPathResource(schemaFile);
            if (resource.exists()) {
                populator.addScript(resource);
                log.debug("Added schema file: {}", schemaFile);
            } else {
                log.warn("Schema file not found, skipping: {}", schemaFile);
            }
        }

        populator.execute(dataSource);
        log.info("Database schemas initialized successfully");
    }

    /**
     * Initializes a specific table schema by executing its SQL file.
     *
     * @param dataSource the DataSource to execute SQL against
     * @param tableName  the table name (used to construct the SQL file path)
     */
    public static void initializeSchema(DataSource dataSource, String tableName) {
        String schemaFile = "db/" + tableName + ".sql";
        log.info("Initializing schema from: {}", schemaFile);

        ClassPathResource resource = new ClassPathResource(schemaFile);
        if (!resource.exists()) {
            log.warn("Schema file not found: {}", schemaFile);
            return;
        }

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.setSeparator(";;");
        populator.addScript(resource);
        populator.execute(dataSource);

        log.info("Schema {} initialized successfully", tableName);
    }

    /**
     * Loads test data for all tables that have data files.
     *
     * @param dataSource the DataSource to execute SQL against
     */
    public static void loadAllTestData(DataSource dataSource) {
        log.info("Loading all test data...");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true); // Continue even if some data files don't exist
        populator.setSeparator(";;");

        for (String dataFile : DATA_FILES) {
            ClassPathResource resource = new ClassPathResource(dataFile);
            if (resource.exists()) {
                populator.addScript(resource);
                log.debug("Added data file: {}", dataFile);
            } else {
                log.debug("Data file not found, skipping: {}", dataFile);
            }
        }

        populator.execute(dataSource);
        log.info("Test data loaded successfully");
    }

    /**
     * Loads test data for a specific table.
     *
     * @param dataSource the DataSource to execute SQL against
     * @param tableName  the table name (used to construct the data file path)
     */
    public static void loadTestData(DataSource dataSource, String tableName) {
        String dataFile = "db/" + tableName + "_data.sql";
        log.info("Loading test data from: {}", dataFile);

        ClassPathResource resource = new ClassPathResource(dataFile);
        if (!resource.exists()) {
            log.debug("Data file not found: {}", dataFile);
            return;
        }

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.setSeparator(";;");
        populator.addScript(resource);
        populator.execute(dataSource);

        log.info("Test data for {} loaded successfully", tableName);
    }

    /**
     * Executes a raw SQL script from a classpath resource.
     *
     * @param dataSource   the DataSource to execute SQL against
     * @param resourcePath the classpath resource path
     */
    public static void executeSqlResource(DataSource dataSource, String resourcePath) {
        log.info("Executing SQL from: {}", resourcePath);

        ClassPathResource resource = new ClassPathResource(resourcePath);
        if (!resource.exists()) {
            throw new IllegalArgumentException("SQL resource not found: " + resourcePath);
        }

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.setSeparator(";;");
        populator.addScript(resource);
        populator.execute(dataSource);

        log.info("SQL script executed successfully: {}", resourcePath);
    }

    /**
     * Drops all tables and related objects from the database.
     * Useful for cleaning up between test classes.
     *
     * @param jdbcTemplate the JdbcTemplate to use for execution
     */
    public static void dropAllTables(JdbcTemplate jdbcTemplate) {
        log.info("Dropping all tables...");

        // Drop tables in reverse order of creation (to handle foreign keys)
        String[] tables = {
                "duty_tax_fee_type_code",
                "payment_terms_type_code",
                "payment_terms_description_identifier",
                "message_function_code",
                "delivery_terms_code",
                "allowance_charge_reason_code",
                "allowance_charge_identification_code",
                "address_type",
                "tisi_city_name",
                "tisi_subdistrict",
                "thai_category_code",
                "thai_document_name_code",
                "thai_message_function_code",
                "thai_province_code",
                "freight_cost_code",
                "document_name_code_invoice",
                "unece_reference_type_code",
                "iso_language_code",
                "iso_currency_code",
                "iso_country_code"
        };

        for (String table : tables) {
            try {
                jdbcTemplate.execute("DROP TABLE IF EXISTS " + table + " CASCADE");
                log.debug("Dropped table: {}", table);
            } catch (Exception e) {
                log.warn("Could not drop table {}: {}", table, e.getMessage());
            }
        }

        log.info("All tables dropped");
    }

    /**
     * Truncates all tables while preserving the schema.
     * Faster than dropping and recreating tables.
     *
     * @param jdbcTemplate the JdbcTemplate to use for execution
     */
    public static void truncateAllTables(JdbcTemplate jdbcTemplate) {
        log.info("Truncating all tables...");

        String[] tables = {
                "duty_tax_fee_type_code",
                "payment_terms_type_code",
                "payment_terms_description_identifier",
                "message_function_code",
                "delivery_terms_code",
                "allowance_charge_reason_code",
                "allowance_charge_identification_code",
                "address_type",
                "tisi_city_name",
                "tisi_subdistrict",
                "thai_category_code",
                "thai_document_name_code",
                "thai_message_function_code",
                "thai_province_code",
                "freight_cost_code",
                "document_name_code_invoice",
                "unece_reference_type_code",
                "iso_language_code",
                "iso_currency_code",
                "iso_country_code"
        };

        for (String table : tables) {
            try {
                jdbcTemplate.execute("TRUNCATE TABLE " + table + " CASCADE");
                log.debug("Truncated table: {}", table);
            } catch (Exception e) {
                log.warn("Could not truncate table {}: {}", table, e.getMessage());
            }
        }

        log.info("All tables truncated");
    }
}
