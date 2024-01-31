package ru.patseev.monitoringservice.repository;

import ru.patseev.monitoringservice.domain.Role;

/**
 * The RoleRepository interface provides methods for retrieving role-related information from the database.
 */
public interface RoleRepository {

	/**
	 * Retrieves a role by its unique identifier.
	 *
	 * @param roleId The unique identifier of the role to retrieve.
	 * @return A Role object representing the role with the specified ID, or null if not found.
	 */
	Role getRoleById(int roleId);
}