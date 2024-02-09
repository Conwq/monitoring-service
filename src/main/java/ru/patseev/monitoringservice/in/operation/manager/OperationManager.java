package ru.patseev.monitoringservice.in.operation.manager;

import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.operation.handler.impl.UnknownOperation;

import java.util.Map;

/**
 * Interface for managing operations.
 */
public interface OperationManager {

	ResponseGenerator response = MonitoringApplicationContext.getContext().getResponseGenerator();

	/**
	 * Chooses the appropriate operation handler based on the operation name.
	 *
	 * @param operationName The name of the operation.
	 * @return The operation handler for the given operation name.
	 */
	OperationHandler chooseOperation(String operationName);

	/**
	 * Gets the operation handler based on the operation name from the provided map of operations.
	 * If the operation name is not recognized, returns a default unknown operation handler.
	 *
	 * @param operationName The name of the operation.
	 * @param operations    The map containing operation enums as keys and corresponding operation handlers as values.
	 * @return The operation handler for the given operation name, or a default unknown operation handler if not found.
	 */
	default OperationHandler getOperationHandler(String operationName, Map<OperationEnum, OperationHandler> operations) {
		try {
			OperationEnum operation = OperationEnum.valueOf(operationName.toUpperCase());
			return operations.get(operation);
		} catch (IllegalArgumentException e) {
			return new UnknownOperation(response);
		}
	}
}
