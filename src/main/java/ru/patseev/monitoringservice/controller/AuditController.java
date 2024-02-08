package ru.patseev.monitoringservice.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;

import java.util.List;

/**
 * Controller for handling audit-related operations.
 */
@RequiredArgsConstructor
public class AuditController {

	/**
	 * The AuditService used by the controller for handling audit-related operations.
	 */
	private final AuditService auditService;

	/**
	 * UserController used by the controller to work with the users service.
	 */
	private final UserController userController;

	/**
	 * JwtService used by the controller for JWT-related operations.
	 */
	private final JwtService jwtService;

	/**
	 * Retrieves a list of user actions based on the provided username.
	 *
	 * @param username  The username for which user actions are to be retrieved.
	 * @param jwtToken  The JWT token for user authentication.
	 * @return A list of UserActionDto representing user actions.
	 */
	public List<UserActionDto> getListOfUserActions(String username, String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);

		UserDto searchedUser = userController.getUser(username);
		List<UserActionDto> userActions = auditService.getUserAction(searchedUser.userId());
		auditService.saveUserAction(ActionEnum.GET_USERS_ACTION, userId);
		return userActions;
	}
}
