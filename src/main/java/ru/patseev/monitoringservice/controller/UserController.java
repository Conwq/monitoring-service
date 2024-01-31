package ru.patseev.monitoringservice.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.service.UserService;

/**
 * Controller class for handling user-related operations.
 */
@RequiredArgsConstructor
public class UserController {

	/**
	 * The service responsible for user-related business logic.
	 */
	private final UserService userService;

	/**
	 * The service responsible for auditing user actions.
	 */
	private final AuditService auditService;

	/**
	 * Saves a user using the UserService.
	 *
	 * @param userDto The data transfer object containing user information.
	 */
	public void saveUser(UserDto userDto) {
		userDto = userService.saveUser(userDto);
		auditService.saveUserAction(ActionEnum.REGISTRATION, userDto);
	}

	/**
	 * Authenticates a user using the UserService.
	 *
	 * @param userDto The data transfer object containing user authentication information.
	 * @return A dto object that stores authenticated user data.
	 */
	public UserDto authUser(UserDto userDto) {
		UserDto userData = userService.authUser(userDto);
		auditService.saveUserAction(ActionEnum.LOG_IN, userData);
		return userData;
	}

	/**
	 * Retrieves a user DTO by the specified username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return A UserDto object representing the user with the specified username,or null if the user is not found.
	 */
	public UserDto getUser(String username) {
		return userService.getUser(username);
	}

}