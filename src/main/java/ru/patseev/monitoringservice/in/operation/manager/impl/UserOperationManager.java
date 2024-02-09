package ru.patseev.monitoringservice.in.operation.manager.impl;

import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.operation.handler.impl.AuthorizationOperationHandler;
import ru.patseev.monitoringservice.in.operation.handler.impl.RegistrationOperationHandler;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;
import ru.patseev.monitoringservice.in.validator.impl.UserDtoValidator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Implementation of OperationManager for managing operations related to users.
 */
public class UserOperationManager implements OperationManager {

	/**
	 * Map to store operation enums and their corresponding handlers.
	 */
	private final Map<OperationEnum, OperationHandler> operations;

	/**
	 * Constructs a UserOperationManager.
	 *
	 * @param userController    The user controller for managing user-related operations.
	 * @param responseGenerator The response generator for generating HTTP responses.
	 * @param objectExtractor   The object extractor for extracting objects from HTTP requests.
	 */
	public UserOperationManager(UserController userController,
								ResponseGenerator responseGenerator,
								ObjectExtractor objectExtractor) {
		operations = initializationOperations(userController, responseGenerator, objectExtractor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationHandler chooseOperation(String operationName) {
		return getOperationHandler(operationName, operations);
	}

	/**
	 * Initializes the operations map with operation enums and their corresponding handlers.
	 *
	 * @param userController    The user controller for managing user-related operations.
	 * @param responseGenerator The response generator for generating HTTP responses.
	 * @param objectExtractor   The object extractor for extracting objects from HTTP requests.
	 * @return The map containing operation enums as keys and their corresponding handlers as values.
	 */
	private Map<OperationEnum, OperationHandler> initializationOperations(UserController userController,
																		  ResponseGenerator responseGenerator,
																		  ObjectExtractor objectExtractor) {
		Map<OperationEnum, OperationHandler> operations = new EnumMap<>(OperationEnum.class);

		operations.put(OperationEnum.AUTH,
				new AuthorizationOperationHandler(responseGenerator, userController, objectExtractor, new UserDtoValidator()));
		operations.put(OperationEnum.REGISTRATION,
				new RegistrationOperationHandler(responseGenerator, userController, objectExtractor, new UserDtoValidator()));

		return operations;
	}
}
