package ru.patseev.monitoringservice.migration.impl;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.migration.Migration;

import java.sql.Connection;
import java.sql.Statement;

/**
 * The LiquibaseMigration class implements the Migration interface and is specifically
 * designed for executing Liquibase-based database migrations.
 */

public class LiquibaseMigration implements Migration {

	/**
	 * The connectionManager used to obtain the database connection for the migration.
	 */
	private final ConnectionManager connectionManager;

	/**
	 * The resource manager used for retrieving database connection details.
	 */
	private final ResourceManager resourceManager;

	/**
	 * Constructs a LiquibaseMigration object with the provided ConnectionManager and ResourceManager.
	 *
	 * @param connectionManager The ConnectionManager instance to be used for database connections.
	 * @param resourceManager   The ResourceManager instance to be used for accessing migration resources.
	 */
	public LiquibaseMigration(ConnectionManager connectionManager, ResourceManager resourceManager) {
		this.connectionManager = connectionManager;
		this.resourceManager = resourceManager;
	}

	/**
	 * Performs the Liquibase-based database migration, creating the necessary schema and applying
	 * changelogs as defined in the specified XML file.
	 */
	@Override
	public void performMigration() {
		try (Connection connection = connectionManager.takeConnection();
			 Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS migration");

			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(connection));
			database.setLiquibaseSchemaName(resourceManager.getValue("liquibase.schema"));

			Liquibase liquibase = new Liquibase(resourceManager.getValue("changelog.liquibase.file"),
					new ClassLoaderResourceAccessor(), database);
			liquibase.getDatabase().setDefaultSchemaName(resourceManager.getValue("default.schema"));
			liquibase.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
