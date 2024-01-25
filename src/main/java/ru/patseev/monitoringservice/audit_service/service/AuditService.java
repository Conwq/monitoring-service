package ru.patseev.monitoringservice.audit_service.service;

import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

/**
 * The AuditService interface provides methods for logging user actions.
 */
public interface AuditService {

	/**
	 * Saves a user action to the audit log.
	 *
	 * @param action   The type of action performed by the user.
	 * @param userDto  The UserDto representing the user who performed the action.
	 */
	void saveUserAction(ActionEnum action, UserDto userDto);
}