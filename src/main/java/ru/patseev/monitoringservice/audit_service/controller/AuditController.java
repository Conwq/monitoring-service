package ru.patseev.monitoringservice.audit_service.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.service.AuditService;

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
	 * @return A list of UserActionDto representing user actions.
	 */
	public List<UserActionDto> getListOfUserActions(String username) {
		return auditService.getUserAction(username);
	}
}
