package ru.patseev.monitoringservice.audit_service.db;

import ru.patseev.monitoringservice.audit_service.domain.UserAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The AuditDatabase class represents an in-memory database for storing user actions in an audit log.
 */
public class AuditDatabase {

	/**
	 * A map to store user actions grouped by username.
	 */
	private final Map<String, List<UserAction>> actions = new HashMap<>();

	/**
	 * Saves a user action to the audit log in the database.
	 *
	 * @param username   The username associated with the user who performed the action.
	 * @param userAction The UserAction object representing the action performed by the user.
	 */
	public void saveUserAction(String username, UserAction userAction) {
		if (!actions.containsKey(username)) {
			actions.put(username, new ArrayList<>());
		}

		List<UserAction> userActions = actions.get(username);
		userActions.add(userAction);
	}

	/**
	 * Retrieves a list of user actions for a specific username from the audit log in the database.
	 *
	 * @param username The username for which to retrieve user actions.
	 * @return A list of UserAction objects representing the actions performed by the user.
	 */
	public List<UserAction> getUserActions(String username) {
		List<UserAction> userActions = actions.get(username);
		if (userActions == null) {
			userActions = new ArrayList<>();
		}
		return userActions;
	}
}