package io.jay.coursecatalog.repository

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
open class PostgresDBInitializer {

    companion object {
        @Container
        val postgresDB = PostgreSQLContainer(DockerImageName.parse("postgres:15.2")).apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("postgres")
        }

        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresDB::getJdbcUrl)
            registry.add("spring.datasource.username", postgresDB::getUsername)
            registry.add("spring.datasource.password", postgresDB::getPassword)
        }
    }

}