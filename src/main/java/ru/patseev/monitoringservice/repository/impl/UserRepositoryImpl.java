package ru.patseev.monitoringservice.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.repository.UserRepository;

import java.sql.*;
import java.util.Optional;

/**
 * The UserRepositoryImpl class is an implementation of the UserRepository interface.
 * It provides methods for interacting with user data storage.
 */
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionManager connectionManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer saveUser(User user) {
		Connection connection = null;
		Integer userId = null;
		try {
			connection = connectionManager.takeConnection();
			connection.setAutoCommit(false);

			userId = saveUserWithTransaction(connection, user);

			connection.commit();
		} catch (SQLException e) {
			connectionManager.rollbackTransaction(connection);
		} finally {
			connectionManager.closeConnection(connection);
		}
		return userId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findUserByUsername(String username) {
		final String selectUserSql = "SELECT * FROM monitoring_service.users WHERE username = ?";
		Optional<User> optionalUser = Optional.empty();
		try (Connection connection = connectionManager.takeConnection();
			 PreparedStatement statement = connection.prepareStatement(selectUserSql)) {

			statement.setString(1, username);

			try (ResultSet resultSet = statement.executeQuery()) {
				optionalUser = extractUser(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return optionalUser;
	}

	/**
	 * Extracts a User object from the ResultSet.
	 *
	 * @param resultSet The ResultSet containing user data.
	 * @return An Optional containing the extracted User, or empty if the ResultSet is empty.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private Optional<User> extractUser(ResultSet resultSet) throws SQLException {
		if (!resultSet.next()) {
			return Optional.empty();
		}
		int userId = resultSet.getInt("user_id");
		String username = resultSet.getString("username");
		String password = resultSet.getString("password");
		int roleId = resultSet.getInt("role_id");

		return Optional.of(new User(userId, username, password, roleId));
	}

	/**
	 * Stores the user action within a transaction in the database.
	 *
	 * @param connection The database connection.
	 * @param user       The user to be saved.
	 * @return The generated user ID.
	 * @throws SQLException If a SQL exception occurs.
	 */
	private int saveUserWithTransaction(Connection connection, User user) throws SQLException {
		final String insertDataSql = "INSERT INTO monitoring_service.users (username, password, role_id) VALUES (?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(insertDataSql, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getRoleId());
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				generatedKeys.next();
				return generatedKeys.getInt(1);
			}
		}
	}
}