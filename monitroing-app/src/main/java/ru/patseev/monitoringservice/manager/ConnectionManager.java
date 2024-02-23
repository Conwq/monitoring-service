package ru.patseev.monitoringservice.manager;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The connectionManager class is responsible for managing database connections.
 * It provides methods to obtain a connection, and close connections, statements, and result sets.
 */
@Component
public class ConnectionManager {

	/**
	 * Closes the provided database connection using the connection provider.
	 *
	 * @param connection The database connection to be closed.
	 */
	public void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Error during rollback: " + e.getMessage());
		}
	}

	/**
	 * Rolls back a transaction on the provided database connection.
	 *
	 * @param connection The database connection on which to perform the rollback.
	 */
	public void rollbackTransaction(Connection connection) {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException e) {
			System.err.println("Error closing database connection: " + e.getMessage());
		}
		System.err.println("Operation error");
	}
}