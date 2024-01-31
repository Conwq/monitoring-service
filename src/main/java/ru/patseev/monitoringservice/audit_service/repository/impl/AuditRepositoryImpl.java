package ru.patseev.monitoringservice.audit_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.manager.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The AuditRepositoryImpl class is an implementation of the AuditRepository interface.
 * It provides methods for interacting with user data storage.
 */
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionProvider connectionProvider;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(int userId, UserAction userAction) {
		final String insertAuditSQL = "INSERT INTO actions (action_at, action, user_id) VALUES (?, ?, ?)";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionProvider.takeConnection();

			connection.setAutoCommit(false);

			statement = connection.prepareStatement(insertAuditSQL);
			statement.setTimestamp(1, userAction.getActionAt());
			statement.setString(2, userAction.getAction().name());
			statement.setInt(3, userId);

			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				System.out.println("Ошибка отката");
			}
			System.out.println("Ошибка операции");
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserAction> findUserActionByUserId(int userId) {
		final String selectActionsSql = "SELECT * FROM actions WHERE user_id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();

			statement = connection.prepareStatement(selectActionsSql);
			statement.setInt(1, userId);

			List<UserAction> userActionList = new ArrayList<>();

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserAction userAction = extractData(resultSet);
				userActionList.add(userAction);
			}
			return userActionList;
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return new ArrayList<>();
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException ex) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}

	/**
	 * Extract data from a ResultSet.
	 *
	 * @param resultSet The object from which data is extract.
	 * @return UserAction entity.
	 * @throws SQLException Exception if work with ResultSet fails.
	 */
	private UserAction extractData(ResultSet resultSet) throws SQLException {
		int actionId = resultSet.getInt("action_id");
		Timestamp actionAt = resultSet.getTimestamp("action_at");
		String action = resultSet.getString("action");
		int userId = resultSet.getInt("user_id");

		return new UserAction(actionId, actionAt, ActionEnum.valueOf(action), userId);
	}
}