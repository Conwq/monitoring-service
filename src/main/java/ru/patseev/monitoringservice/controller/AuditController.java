package ru.patseev.monitoringservice.controller;

import ru.patseev.monitoringservice.aspect.annotation.Loggable;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;

import java.util.List;

/**
 * Controller for handling audit-related operations.
 */
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
	 * Constructs a new AuditController with the specified dependencies.
	 *
	 * @param auditService   The service for audit operations
	 * @param userController The controller for user-related operations
	 * @param jwtService     The service for JWT operations
	 */
	public AuditController(AuditService auditService, UserController userController, JwtService jwtService) {
		this.auditService = auditService;
		this.userController = userController;
		this.jwtService = jwtService;
	}

	/**
	 * Retrieves a list of user actions based on the provided username.
	 *
	 * @param username The username for which user actions are to be retrieved.
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of UserActionDto representing user actions.
	 */
	@Loggable
	public List<UserActionDto> getListOfUserActions(String username, String jwtToken) {
		UserDto searchedUser = userController.getUser(username);
		return auditService.getUserAction(searchedUser.userId());
	}
}
