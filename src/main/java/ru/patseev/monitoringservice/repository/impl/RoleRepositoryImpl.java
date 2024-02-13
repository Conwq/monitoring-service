package ru.patseev.monitoringservice.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.exception.RoleNotExistsException;
import ru.patseev.monitoringservice.repository.RoleRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The RoleRepositoryImpl class provides methods to interact with the database for role-related operations.
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {


	/**
	 * The data source used for obtaining a database connection.
	 */
	private final DataSource dataSource;

	/**
	 * Constructs an AuditRepositoryImpl object with the provided DataSource.
	 *
	 * @param dataSource The DataSource instance used for obtaining database connections.
	 */
	@Autowired
	public RoleRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
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

		try (Connection connection = dataSource.getConnection();
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