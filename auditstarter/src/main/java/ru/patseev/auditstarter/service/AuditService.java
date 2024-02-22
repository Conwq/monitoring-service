package ru.patseev.auditstarter.service;


import ru.patseev.auditstarter.dto.UserActionDto;
import ru.patseev.auditstarter.manager.enums.ActionEnum;

import java.util.List;

/**
 * The AuditService interface provides methods for logging user actions.
 */
public interface AuditService {

	/**
	 * Saves a user action to the audit log.
	 *
	 * @param action The type of action performed by the user.
	 * @param userId The ID of the user who performed the action.
	 */
	void saveUserAction(ActionEnum action, int userId);

	/**
	 * Retrieves a list of user actions based on the provided user ID.
	 *
	 * @param userId The ID of the user for which to retrieve user actions.
	 * @return A list of UserActionDto representing user actions.
	 */
	List<UserActionDto> getUserActions(int userId);
}