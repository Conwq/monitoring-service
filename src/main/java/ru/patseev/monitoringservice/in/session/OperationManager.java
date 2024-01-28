package ru.patseev.monitoringservice.in.session;

import ru.patseev.monitoringservice.audit_service.controller.AuditController;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.impl.ExitOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.InvalidOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.ViewAllUsersMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.ViewActionsHistoryOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.MeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ViewCurrentMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ViewMeterDataForSpecifiedMonthOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ViewMeterDataOperation;
import ru.patseev.monitoringservice.user_service.domain.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The OperationManager class manages operations based on user roles.
 */
public class OperationManager {
	private final Map<Integer, Operation> userOperations = new HashMap<>();
	private final Map<Integer, Operation> adminOperation = new HashMap<>();

	public OperationManager(Scanner scanner, DataMeterController dataMeterController, AuditController auditController) {
		// Инициализирует операции админа
		userOperations.put(1, new ViewCurrentMeterDataOperation(dataMeterController));
		userOperations.put(2, new MeterDataOperation(scanner, dataMeterController));
		userOperations.put(3, new ViewMeterDataForSpecifiedMonthOperation(scanner, dataMeterController));
		userOperations.put(4, new ViewMeterDataOperation(dataMeterController));
		userOperations.put(5, new ExitOperation());

		// Инициализирует операции пользователя
		adminOperation.put(1, new ViewAllUsersMeterDataOperation(dataMeterController));
		adminOperation.put(2, new ViewActionsHistoryOperation(scanner, auditController));
		adminOperation.put(3, new ExitOperation());
	}

	/**
	 * Gets the operation based on the operation number and user role.
	 *
	 * @param operationNumber The number representing the desired operation.
	 * @param role            The role of the user.
	 * @return The corresponding Operation based on the operation number and user role.
	 */
	public Operation getOperation(int operationNumber, Role role) {
		Map<Integer, Operation> roleOperations =
				switch (role) {
					case ADMIN -> adminOperation;
					case USER -> userOperations;
				};

		return roleOperations.getOrDefault(operationNumber, new InvalidOperation());
	}
}