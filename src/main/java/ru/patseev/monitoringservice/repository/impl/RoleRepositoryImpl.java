package ru.patseev.monitoringservice.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.manager.ConnectionProvider;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.repository.RoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The RoleRepositoryImpl class provides methods to interact with the database for role-related operations.
 */
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionProvider connectionProvider;

	/**
	 * Retrieves a role by its unique identifier.
	 *
	 * @param roleId The unique identifier of the role to retrieve.
	 * @return A Role object representing the role with the specified ID, or null if not found.
	 */
	@Override
	public Role getRoleById(int roleId) {
		final String selectRoleSql = "SELECT * FROM roles WHERE role_id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionProvider.takeConnection();

			statement = connection.prepareStatement(selectRoleSql);
			statement.setInt(1, roleId);

			resultSet = statement.executeQuery();
			resultSet.next();

			roleId = resultSet.getInt("role_id");
			String roleName = resultSet.getString("role_name");

			return new Role(roleId, roleName);
		} catch (SQLException e) {
			System.out.println("Ошибка операции");
			return null;
		} finally {
			try {
				connectionProvider.closeConnections(connection, statement, resultSet);
			} catch (SQLException e) {
				System.out.println("Ошибка с освобождением ресурсов");
			}
		}
	}
}