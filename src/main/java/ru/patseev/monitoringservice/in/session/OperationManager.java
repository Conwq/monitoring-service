package ru.patseev.monitoringservice.in.session;

import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OperationManager {
	private final Map<Integer, Operation> operations = new HashMap<>();

	public OperationManager(Scanner scanner, DataMeterController dataMeterController) {
		operations.put(1, new ShowCurrentMeterDataOperation(dataMeterController));
		operations.put(2, new EnterAndSubmitMeterDataOperation(scanner, dataMeterController));
		operations.put(3, new ShowMeterDataForSpecifiedMonthOperation(scanner, dataMeterController));
		operations.put(4, new ShowAllUsersMeterDataOperation(dataMeterController));

		operations.put(6, new ExitOperation());
	}

	public Operation getOperation(int operationNumber) {
		Operation operation = operations.get(operationNumber);

		if (operation == null) {
			return new InvalidOperation();
		}

		return operation;
	}
}