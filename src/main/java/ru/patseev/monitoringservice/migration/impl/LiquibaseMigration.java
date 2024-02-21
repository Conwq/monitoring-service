package ru.patseev.monitoringservice.migration.impl;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ru.patseev.monitoringservice.migration.Migration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * The LiquibaseMigration class implements the Migration interface and is specifically
 * designed for executing Liquibase-based database migrations.
 */
@Configuration
@RequiredArgsConstructor
public class LiquibaseMigration implements Migration {

	/**
	 * The name of the schema used by Liquibase for database migrations.
	 */
	@Value("${spring.liquibase.liquibase-schema}")
	private String liquibaseMigrationSchema;

	/**
	 * The path to the Liquibase changelog XML file containing migration instructions.
	 */
	@Value("${spring.liquibase.change-log}")
	private String pathChangelog;

	/**
	 * The default schema name to be used during the migration process.
	 */
	@Value("${spring.liquibase.default-schema}")
	private String defaultSchema;

	/**
	 * The data source used for obtaining a database connection.
	 */
	private final DataSource dataSource;

	/**
	 * Performs the Liquibase-based database migration, creating the necessary schema and applying
	 * changelogs as defined in the specified XML file.
	 */
	@Override
	@EventListener(ApplicationReadyEvent.class)
	public void performMigration() {
		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS migration");

			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(connection));
			database.setLiquibaseSchemaName(liquibaseMigrationSchema);

			Liquibase liquibase = new Liquibase(pathChangelog, new ClassLoaderResourceAccessor(), database);
			liquibase.getDatabase().setDefaultSchemaName(defaultSchema);
			liquibase.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
