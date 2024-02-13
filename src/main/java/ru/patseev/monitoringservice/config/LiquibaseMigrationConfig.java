package ru.patseev.monitoringservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;

/**
 * Configuration class for Liquibase migration.
 */
@Configuration
public class LiquibaseMigrationConfig {

	private final LiquibaseMigration liquibaseMigration;

	/**
	 * Constructs a LiquibaseMigrationConfig object with the specified LiquibaseMigration instance.
	 *
	 * @param liquibaseMigration The LiquibaseMigration instance used for database migration.
	 */
	@Autowired
	public LiquibaseMigrationConfig(LiquibaseMigration liquibaseMigration) {
		this.liquibaseMigration = liquibaseMigration;
	}

	/**
	 * Runs the Liquibase database migration upon application startup.
	 */
	@PostConstruct
	public void runMigration() {
		liquibaseMigration.performMigration();
	}
}
