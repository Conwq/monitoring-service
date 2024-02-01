package ru.patseev.monitoringservice.user_service.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.service.UserService;

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
		userService.saveUser(userDto);
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
}