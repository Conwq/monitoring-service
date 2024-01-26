package ru.patseev.monitoringservice.in.session;

import ru.patseev.monitoringservice.audit_service.controller.AuditController;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.impl.*;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.ShowAllUsersMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.admin.ViewUserActionsOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.EnterAndSubmitMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ShowCurrentMeterDataOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ShowMeterDataForSpecifiedMonthOperation;
import ru.patseev.monitoringservice.in.session.operation.impl.user.ShowUsersMeterDataOperation;
import ru.patseev.monitoringservice.user_service.domain.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OperationManager {
	private final Map<Integer, Operation> userOperations = new HashMap<>();
	private final Map<Integer, Operation> adminOperation = new HashMap<>();

	public OperationManager(Scanner scanner, DataMeterController dataMeterController, AuditController auditController) {
		userOperations.put(1, new ShowCurrentMeterDataOperation(dataMeterController));
		userOperations.put(2, new EnterAndSubmitMeterDataOperation(scanner, dataMeterController));
		userOperations.put(3, new ShowMeterDataForSpecifiedMonthOperation(scanner, dataMeterController));
		userOperations.put(4, new ShowUsersMeterDataOperation(dataMeterController));
		userOperations.put(5, new ExitOperation());

		adminOperation.put(1, new ShowAllUsersMeterDataOperation(dataMeterController));
		adminOperation.put(2, new ViewUserActionsOperation(scanner, auditController));
		adminOperation.put(3, new ExitOperation());
	}

	public Operation getOperation(int operationNumber, Role role) {
		Map<Integer, Operation> roleOperations = switch (role){
			case ADMIN -> adminOperation;
			case USER -> userOperations;
		};

		return roleOperations.getOrDefault(operationNumber, new InvalidOperation());
	}
}