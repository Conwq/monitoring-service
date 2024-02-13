package ru.patseev.monitoringservice.migration.impl;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.patseev.monitoringservice.migration.Migration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * The LiquibaseMigration class implements the Migration interface and is specifically
 * designed for executing Liquibase-based database migrations.
 */
@Component
public class LiquibaseMigration implements Migration {

	/**
	 * The name of the schema used by Liquibase for database migrations.
	 */
	@Value("${liquibase.schema}")
	private String schemaName;

	/**
	 * The path to the Liquibase changelog XML file containing migration instructions.
	 */
	@Value("${changelog.liquibase.file}")
	private String pathChangelog;

	/**
	 * The default schema name to be used during the migration process.
	 */
	@Value("${default.schema}")
	private String defaultSchema;

	private final DataSource dataSource;

	/**
	 * Constructs a LiquibaseMigration object with the provided ConnectionManager and ResourceManager.
	 *
	 * @param dataSource The data source used for obtaining a database connection.
	 */
	@Autowired
	public LiquibaseMigration(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Performs the Liquibase-based database migration, creating the necessary schema and applying
	 * changelogs as defined in the specified XML file.
	 */
	@Override
	public void performMigration() {
		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS migration");

			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(connection));
			database.setLiquibaseSchemaName(schemaName);

			Liquibase liquibase = new Liquibase(pathChangelog,
					new ClassLoaderResourceAccessor(), database);
			liquibase.getDatabase().setDefaultSchemaName(defaultSchema);
			liquibase.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
