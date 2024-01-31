package ru.patseev.monitoringservice.user_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.manager.ConnectionProvider;
import ru.patseev.monitoringservice.user_service.domain.User;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;

import java.sql.*;
import java.util.Objects;
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
	private final ConnectionProvider connectionProvider;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer saveUser(User user) {
		String insertDataSql = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;

		try {
			connection = connectionProvider.takeConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(insertDataSql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getRoleId());
			statement.executeUpdate();

			generatedKeys = statement.getGeneratedKeys();
			generatedKeys.next();
			connection.commit();

			return generatedKeys.getInt(1);
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				System.out.println("Ошибка отката");
			}
			System.out.println("Ошибка операции");
			return null;
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, generatedKeys);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findUserByUsername(String username) {
		final String selectUserSql = "SELECT * FROM users WHERE username = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();

			statement = connection.prepareStatement(selectUserSql);
			statement.setString(1, username);

			resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				return Optional.empty();
			}

			int userId = resultSet.getInt("user_id");
			username = resultSet.getString("username");
			String password = resultSet.getString("password");
			int roleId = resultSet.getInt("role_id");

			return Optional.of(new User(userId, username, password, roleId));
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return Optional.empty();
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}
}