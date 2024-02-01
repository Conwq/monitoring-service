package ru.patseev.monitoringservice.in.session;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.controller.AuditController;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.impl.ExitOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.InvalidOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.AddNewMeterTypeOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.ViewAllUsersMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.ViewActionsHistoryOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.MeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ViewCurrentMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ViewMeterDataForSpecifiedMonthOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ViewMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.user_service.domain.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The OperationManager class manages operations based on user roles.
 */
@RequiredArgsConstructor
public class OperationManager {

	/**
	 * List of operations available to a user with the USER role.
	 */
	private final Map<Integer, Operation> userOperations = new HashMap<>();

	/**
	 * List of operations available to a user with the ADMIN role.
	 */
	private final Map<Integer, Operation> adminOperation = new HashMap<>();

	/**
	 * Constructor to initialize operations for users and admins.
	 *
	 * @param scanner            The scanner for user input.
	 * @param dataMeterController The controller for data meter operations.
	 * @param auditController     The controller for auditing actions.
	 * @param printerMeterData    The printer for displaying meter data.
	 */
	public OperationManager(Scanner scanner,
							DataMeterController dataMeterController,
							AuditController auditController,
							PrinterMeterData printerMeterData) {
		// Инициализирует операции пользователя
		userOperations.put(1, new ViewCurrentMeterDataOperation(dataMeterController, printerMeterData));
		userOperations.put(2, new MeterDataOperation(scanner, dataMeterController));
		userOperations.put(3, new ViewMeterDataForSpecifiedMonthOperation(scanner, dataMeterController, printerMeterData));
		userOperations.put(4, new ViewMeterDataOperation(dataMeterController, printerMeterData));
		userOperations.put(5, new ExitOperation());

		// Инициализирует операции админа
		adminOperation.put(1, new ViewAllUsersMeterDataOperation(dataMeterController, printerMeterData));
		adminOperation.put(2, new ViewActionsHistoryOperation(scanner, auditController));
		adminOperation.put(3, new AddNewMeterTypeOperation(scanner, dataMeterController));
		adminOperation.put(4, new ExitOperation());
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