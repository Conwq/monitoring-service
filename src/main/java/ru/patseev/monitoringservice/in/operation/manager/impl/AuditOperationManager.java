package ru.patseev.monitoringservice.in.operation.manager.impl;

import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.operation.handler.impl.GettingAuditOperationHandler;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;

import java.util.EnumMap;
import java.util.Map;

/**
 * Implementation of the OperationManager interface for managing audit operations.
 */
public class AuditOperationManager implements OperationManager {

	/**
	 * Map to store operation enums and their corresponding handlers.
	 */
	private Map<OperationEnum, OperationHandler> operations;

	/**
	 * Constructs a new AuditOperationManager with the given audit controller and response generator.
	 *
	 * @param auditController   The audit controller to handle audit-related operations.
	 * @param responseGenerator The response generator to generate HTTP responses.
	 */
	public AuditOperationManager(AuditController auditController,
								 ResponseGenerator responseGenerator) {
		operations = initializationOperations(auditController, responseGenerator);
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
	 * @param auditController   The audit controller for managing audit-related operations.
	 * @param responseGenerator The response generator for generating HTTP responses.
	 * @return The map containing operation enums as keys and their corresponding handlers as values.
	 */
	private Map<OperationEnum, OperationHandler> initializationOperations(AuditController auditController,
																		  ResponseGenerator responseGenerator) {
		Map<OperationEnum, OperationHandler> operations = new EnumMap<>(OperationEnum.class);

		operations.put(OperationEnum.GET_AUDIT,
				new GettingAuditOperationHandler(responseGenerator, auditController));

		return operations;
	}
}
