package ru.patseev.monitoringservice.audit_service.service;

import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.util.List;

/**
 * The AuditService interface provides methods for logging user actions.
 */
public interface AuditService {

	/**
	 * Saves a user action to the audit log.
	 *
	 * @param action  The type of action performed by the user.
	 * @param userDto The UserDto representing the user who performed the action.
	 */
	void saveUserAction(ActionEnum action, UserDto userDto);

	/**
	 * Retrieves a list of user actions based on the provided username.
	 *
	 * @param username The username for which user actions are to be retrieved.
	 * @return A list of UserActionDto representing user actions.
	 * @throws UserNotFoundException If the specified user is not found.
	 */
	List<UserActionDto> getUserAction(String username) throws UserNotFoundException;
}
