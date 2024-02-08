package ru.patseev.monitoringservice.migration;

/**
 * The Migration interface represents a contract for performing database migrations.
 * Implementing classes must provide an implementation for the performMigration() method.
 */
public interface Migration {

	/**
	 * Performs the database migration.
	 */
	void performMigration();
}