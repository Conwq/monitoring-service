package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.validator.Validator;

/**
 * The AuthorizationOperationHandler class handles the operation of user authorization.
 */
@RequiredArgsConstructor
public class AuthorizationOperationHandler implements OperationHandler {

	/**
	 * The response generator for generating HTTP responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * The user controller for managing user-related operations.
	 */
	private final UserController userController;

	/**
	 * The object extractor for extracting objects from HTTP requests.
	 */
	private final ObjectExtractor objectExtractor;

	/**
	 * This class represents is used to validate instances of UserDto objects.
	 */
	private final Validator<UserDto> userDtoValidator;

	/**
	 * Handles the operation of user authorization.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			UserDto userDto = objectExtractor.extractObject(req, UserDto.class);

			if (!userDtoValidator.validate(userDto)) {
				responseGenerator.generateResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "The data is not valid");
				return;
			}
			String jwtToken = userController.authUser(userDto);

			if (jwtToken == null || jwtToken.isEmpty()) {
				responseGenerator.generateResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}

			resp.setHeader("Authorization", jwtToken);
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, jwtToken);
		} catch (UserNotFoundException e) {
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		}
	}
}
