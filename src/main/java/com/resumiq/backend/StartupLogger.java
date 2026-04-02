package com.resumiq.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@Slf4j
public class StartupLogger {

    @Bean
    public CommandLineRunner checkDatabase(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                log.info("✅ Database connected successfully: {}", connection.getMetaData().getDatabaseProductName());
                log.info("🚀 ResumIQ Backend is ready at http://localhost:8080");
            } catch (Exception e) {
                log.error("❌ Database connection FAILED! Please check your DB settings.");
                log.error("Error details: {}", e.getMessage());
            }
        };
    }
}
