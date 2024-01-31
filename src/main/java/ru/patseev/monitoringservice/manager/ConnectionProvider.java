package ru.patseev.monitoringservice.manager;

import lombok.RequiredArgsConstructor;

import java.sql.*;

/**
 * The ConnectionProvider class is responsible for managing database connections.
 * It provides methods to obtain a connection, and close connections, statements, and result sets.
 */
@RequiredArgsConstructor
public class ConnectionProvider {

	/**
	 * The resource manager used for retrieving database connection details.
	 */
	private final ResourceManager resourceManager;

	/**
	 * Takes a connection to the database using the configured connection details.
	 *
	 * @return A Connection object representing the database connection.
	 * @throws SQLException If a database access error occurs.
	 */
	public Connection takeConnection() throws SQLException {
		String url = resourceManager.getValue("url");
		String username = resourceManager.getValue("username");
		String password = resourceManager.getValue("password");

		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Closes the provided database connection and statement.
	 *
	 * @param connection The Connection object to be closed.
	 * @param statement  The Statement object to be closed.
	 * @throws SQLException If a database access error occurs.
	 */
	public void closeConnections(Connection connection, Statement statement) throws SQLException {
		closeConnections(connection, statement, null);
	}

	/**
	 * Closes the provided database connection, statement, and result set.
	 *
	 * @param connection The Connection object to be closed.
	 * @param statement  The Statement object to be closed.
	 * @param resultSet  The ResultSet object to be closed.
	 * @throws SQLException If a database access error occurs.
	 */
	public void closeConnections(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
		if (connection != null) {
			connection.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
	}
}