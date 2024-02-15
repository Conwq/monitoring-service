package ru.patseev.monitoringservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;

/**
 * Configuration class for Liquibase migration.
 */
@Configuration
@RequiredArgsConstructor
public class LiquibaseMigrationConfig {

	/**
	 * The LiquibaseMigration instance used for database migration.
	 */
	private final LiquibaseMigration liquibaseMigration;

	/**
	 * Runs the Liquibase database migration upon application startup.
	 */
	@PostConstruct
	public void runMigration() {
		liquibaseMigration.performMigration();
	}
}
