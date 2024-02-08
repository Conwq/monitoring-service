package ru.patseev.monitoringservice.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The connectionManager class is responsible for managing database connections.
 * It provides methods to obtain a connection, and close connections, statements, and result sets.
 */
public class ConnectionManager {

	/**
	 * The JDBC URL for connecting to the database.
	 */
	private final String url;

	/**
	 * The username used for authentication when connecting to the database.
	 */
	private final String username;

	/**
	 * The password used for authentication when connecting to the database.
	 */
	private final String password;

	/**
	 * Constructs a connectionManager object using resourceManager to obtain connection details.
	 *
	 * @param resourceManager The ResourceManager providing access to configuration properties.
	 */
	public ConnectionManager(ResourceManager resourceManager) {
		url = resourceManager.getValue("url");
		username = resourceManager.getValue("username");
		password = resourceManager.getValue("password");
	}

	/**
	 * Constructs a connectionManager object with specified connection details.
	 *
	 * @param url      The JDBC URL for connecting to the database.
	 * @param username The username used for authentication.
	 * @param password The password used for authentication.
	 */
	public ConnectionManager(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * Takes a connection to the database using the configured connection details.
	 *
	 * @return A Connection object representing the database connection.
	 * @throws SQLException If a database access error occurs.
	 */
	public Connection takeConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

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