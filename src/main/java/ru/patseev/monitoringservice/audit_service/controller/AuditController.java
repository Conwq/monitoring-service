package ru.patseev.monitoringservice.audit_service.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.List;

/**
 * Controller for handling audit-related operations.
 */
@RequiredArgsConstructor
public class AuditController {
	private final AuditService auditService;

	/**
	 * Retrieves a list of user actions based on the provided username.
	 *
	 * @param username The username for which user actions are to be retrieved.
	 * @param userDto The data transfer object containing user authentication information.
	 * @return A list of UserActionDto representing user actions.
	 */
	public List<UserActionDto> getListOfUserActions(String username, UserDto userDto) {
		List<UserActionDto> userAction = auditService.getUserAction(username);
		auditService.saveUserAction(ActionEnum.GET_USERS_ACTION, userDto);
		return userAction;
	}
}
