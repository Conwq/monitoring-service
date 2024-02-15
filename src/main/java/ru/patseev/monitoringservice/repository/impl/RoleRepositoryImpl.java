package ru.patseev.monitoringservice.repository.impl;

import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.exception.RoleNotExistsException;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.repository.RoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The RoleRepositoryImpl class provides methods to interact with the database for role-related operations.
 */
public class RoleRepositoryImpl implements RoleRepository {

	/**
	 * Provider that provides methods for working with database connections.
	 */
	private final ConnectionManager connectionManager;

	/**
	 * Constructs an RoleRepositoryImpl object with the provided ConnectionManager.
	 *
	 * @param connectionManager The ConnectionManager instance to be used for database connections.
	 */
	public RoleRepositoryImpl(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	/**
	 * Retrieves a role by its unique identifier.
	 *
	 * @param roleId The unique identifier of the role to retrieve.
	 * @return A Role object representing the role with the specified ID, or null if not found.
	 */
	@Override
	public Role getRoleById(int roleId) {
		final String selectRoleSql = "SELECT * FROM monitoring_service.roles WHERE role_id = ?";
		Role role = null;

		try (Connection connection = connectionManager.takeConnection();
			 PreparedStatement statement = connection.prepareStatement(selectRoleSql)) {
			statement.setInt(1, roleId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					throw new RoleNotExistsException("Роль не найдена");
				}
				role = extractRole(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Operation error");
		}
		return role;
	}

	/**
	 * Extract Role from a ResultSet.
	 *
	 * @param resultSet The ResultSet object from which data is extracted.
	 * @return A Role entity representing the extracted data.
	 * @throws SQLException If an exception occurs while working with the ResultSet.
	 */
	private Role extractRole(ResultSet resultSet) throws SQLException {
		int roleId = resultSet.getInt("role_id");
		String roleName = resultSet.getString("role_name");

		return new Role(roleId, roleName);
	}
}