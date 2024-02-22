package ru.patseev.auditstarter.repository;


import ru.patseev.auditstarter.domain.UserAction;

import java.util.List;

/**
 * The AuditRepository interface provides methods for storing and retrieving user actions in an audit log.
 */
public interface AuditRepository {

	/**
	 * Saves a user action to the audit log.
	 *
	 * @param userAction The UserAction object representing the action performed by the user.
	 */
	void save(UserAction userAction);

	/**
	 * Retrieves a list of user actions for a specific user from the audit log.
	 *
	 * @param userId The user ID for which to retrieve user actions.
	 * @return A list of UserAction objects representing the actions performed by the user.
	 */
	List<UserAction> findUserActionsByUserId(int userId);
}