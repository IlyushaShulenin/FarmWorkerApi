package ru.shulenin.farmworkerapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * General class for test containers.
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(initializers = {FarmWorkerApiApplicationTest.Initializer.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class FarmWorkerApiApplicationTest {
    private static final String DATABASE_NAME = "spring-app";

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.9")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
                    "CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
                    "CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
