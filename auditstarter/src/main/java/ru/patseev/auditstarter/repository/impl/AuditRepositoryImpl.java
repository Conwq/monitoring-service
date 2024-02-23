package ru.patseev.auditstarter.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.auditstarter.domain.UserAction;
import ru.patseev.auditstarter.manager.enums.ActionEnum;
import ru.patseev.auditstarter.repository.AuditRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The AuditRepositoryImpl class is an implementation of the AuditRepository interface.
 */
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

	/**
	 * The data source used for obtaining a database connection.
	 */
	private final DataSource dataSource;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(UserAction userAction) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			saveUserActionWithTransaction(userAction, connection);

			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				System.err.println("Error closing database connection: " + ex.getMessage());
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Error during rollback: " + e.getMessage());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserAction> findUserActionsByUserId(int userId) {
		final String selectActionsSql = "SELECT * FROM monitoring_service.actions WHERE user_id = ?";

		List<UserAction> userActionList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(selectActionsSql)) {
			statement.setInt(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					UserAction userAction = extractUserAction(resultSet);
					userActionList.add(userAction);
				}
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return userActionList;
	}

	/**
	 * Stores the user action within a transaction in the database.
	 *
	 * @param userAction The UserAction object to be saved.
	 * @param connection The database connection to be used.
	 * @throws SQLException If an exception occurs during database operations.
	 */
	private void saveUserActionWithTransaction(UserAction userAction, Connection connection) throws SQLException {
		final String insertAuditSQL = "INSERT INTO monitoring_service.actions (action_at, action, user_id) VALUES (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(insertAuditSQL)) {
			statement.setTimestamp(1, userAction.getActionAt());
			statement.setString(2, userAction.getAction().name());
			statement.setInt(3, userAction.getUserId());
			statement.executeUpdate();
		}
	}

	/**
	 * Extract UserAction from a ResultSet.
	 *
	 * @param resultSet The ResultSet object from which data is extracted.
	 * @return A UserAction entity representing the extracted data.
	 * @throws SQLException If an exception occurs while working with the ResultSet.
	 */
	private UserAction extractUserAction(ResultSet resultSet) throws SQLException {
		int actionId = resultSet.getInt("action_id");
		Timestamp actionAt = resultSet.getTimestamp("action_at");
		String action = resultSet.getString("action");
		int userId = resultSet.getInt("user_id");

		return new UserAction(actionId, actionAt, ActionEnum.valueOf(action), userId);
	}
}