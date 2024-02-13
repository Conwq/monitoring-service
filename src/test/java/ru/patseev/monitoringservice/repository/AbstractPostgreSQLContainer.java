package ru.patseev.monitoringservice.repository;

import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;

public class AbstractPostgreSQLContainer {

	static PostgreSQLContainer<?> POSTGRES;

	static PGSimpleDataSource DATA_SOURCE;

	static {
		POSTGRES = new PostgreSQLContainer<>("postgres:latest");
		POSTGRES.start();

		DATA_SOURCE.setURL(POSTGRES.getJdbcUrl());
		DATA_SOURCE.setUser(POSTGRES.getUsername());
		DATA_SOURCE.setPassword(POSTGRES.getPassword());

		new LiquibaseMigration(DATA_SOURCE)
				.performMigration();
	}
}