package ru.patseev.monitoringservice.repository;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractPostgreSQLContainer {

	static PostgreSQLContainer<?> POSTGRES;

	static {
		POSTGRES = new PostgreSQLContainer<>("postgres:latest");
		POSTGRES.start();
	}
}