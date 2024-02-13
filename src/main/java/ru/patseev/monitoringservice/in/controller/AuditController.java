package ru.patseev.monitoringservice.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.service.AuditService;

import java.util.List;

/**
 * Controller for handling audit-related operations.
 */
@RestController
@RequestMapping("/audits")
public class AuditController {

	/**
	 * The AuditService used by the controller for handling audit-related operations.
	 */
	private final AuditService auditService;

	/**
	 * The UserController used by the controller to work with the users service.
	 */
	private final UserController userController;

	/**
	 * The ResponseGenerator used by the controller to generate responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * Constructs a new AuditController with the specified dependencies.
	 *
	 * @param auditService     The service for audit operations.
	 * @param userController   The controller for user-related operations.
	 * @param responseGenerator The generator for HTTP responses.
	 */
	@Autowired
	public AuditController(AuditService auditService, UserController userController, ResponseGenerator responseGenerator) {
		this.auditService = auditService;
		this.userController = userController;
		this.responseGenerator = responseGenerator;
	}

	/**
	 * Retrieves a list of user actions based on the provided username.
	 *
	 * @param username The username for which user actions are to be retrieved.
	 * @param jwtToken The JWT token for user authentication.
	 * @return A ResponseEntity with a list of UserActionDto representing user actions.
	 */
	@GetMapping
	public ResponseEntity<?> getListOfUserActions(@RequestParam("username") String username,
												  @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
		try {
			UserDto searchedUser = userController.getUser(username);
			List<UserActionDto> userActions = auditService.getUserActions(searchedUser.userId());
			return responseGenerator.generateResponse(HttpStatus.OK, userActions);
		} catch (UserNotFoundException e) {
			return responseGenerator.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
