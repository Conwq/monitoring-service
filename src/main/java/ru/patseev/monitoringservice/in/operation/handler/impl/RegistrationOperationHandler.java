package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.validator.Validator;

/**
 * The RegistrationOperationHandler class handles the operation of user registration.
 */
public class RegistrationOperationHandler implements OperationHandler {

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
	 * Constructs a RegistrationOperationHandler object with the provided dependencies.
	 *
	 * @param responseGenerator The ResponseGenerator instance used for generating HTTP responses.
	 * @param userController    The UserController instance used for user-related operations.
	 * @param objectExtractor   The ObjectExtractor instance used for extracting objects from HTTP requests.
	 * @param userDtoValidator  The Validator instance used for validating UserDto objects.
	 */
	public RegistrationOperationHandler(ResponseGenerator responseGenerator,
										UserController userController,
										ObjectExtractor objectExtractor,
										Validator<UserDto> userDtoValidator) {
		this.responseGenerator = responseGenerator;
		this.userController = userController;
		this.objectExtractor = objectExtractor;
		this.userDtoValidator = userDtoValidator;
	}

	/**
	 * Handles the operation of user registration.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			UserDto userDto = objectExtractor.extractObject(req, UserDto.class);

			if (userDtoValidator.validate(userDto)) {
				responseGenerator.generateResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "The data is not valid");
				return;
			}
			String jwtToken = userController.saveUser(userDto);

			resp.setHeader("Authorization", jwtToken);
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, jwtToken);
		} catch (UserAlreadyExistException e) {
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_CONFLICT, e.getMessage());
		}
	}
}
