package ru.patseev.monitoringservice.service;

import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserNotFoundException;

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
	 * @param userId The user ID for which to retrieve user actions.
	 * @return A list of UserActionDto representing user actions.
	 * @throws UserNotFoundException If the specified user is not found.
	 */
	//todo
	List<UserActionDto> getUserAction(int userId) throws UserNotFoundException;
}