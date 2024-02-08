package ru.patseev.monitoringservice.in.operation.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.in.extract.ObjectExtractor;
import ru.patseev.monitoringservice.in.operation.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.validator.Validator;
import ru.patseev.monitoringservice.in.validator.impl.UserDtoValidator;

/**
 * The AuthorizationOperationHandler class handles the operation of user authorization.
 */
@RequiredArgsConstructor
public class AuthorizationOperationHandler implements OperationHandler {

	/** The response generator for generating HTTP responses. */
	private final ResponseGenerator responseGenerator;

	/** The user controller for managing user-related operations. */
	private final UserController userController;

	/** The object extractor for extracting objects from HTTP requests. */
	private final ObjectExtractor objectExtractor;

	//todo
	private final Validator<UserDto> userDtoValidator = new UserDtoValidator();

	/**
	 * Handles the operation of user authorization.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		UserDto userDto = objectExtractor.extractObject(req, UserDto.class);
		boolean isValid = userDtoValidator.validate(userDto);
		if (!isValid) {
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "The data is not valid");
			return;
		}
		String jwtToken = userController.authUser(userDto);

		resp.setHeader("Authorization", jwtToken);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, jwtToken);
	}
}
