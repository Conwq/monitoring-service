package ru.patseev.monitoringservice.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.dto.UserDto;

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
	 * Retrieves a list of user actions based on the provided username.
	 *
	 * @param username The username for which user actions are to be retrieved.
	 * @param userDto The data transfer object containing user authentication information.
	 * @return A list of UserActionDto representing user actions.
	 */
	public List<UserActionDto> getListOfUserActions(String username, UserDto userDto) {
		UserDto searchedUser = userController.getUser(username);
		List<UserActionDto> userAction = auditService.getUserAction(searchedUser.userId());
		auditService.saveUserAction(ActionEnum.GET_USERS_ACTION, userDto);
		return userAction;
	}
}