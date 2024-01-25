package ru.patseev.monitoringservice.in.session;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.impl.ExitOperation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.Scanner;

@RequiredArgsConstructor
public class UserSessionManager {
	private final Scanner scanner;
	public final DataMeterController dataMeterController;
	public final OperationManager operationManager;

	public void openSessionForUser(UserDto userDto) {
		displayOperationsMenu(userDto);
	}

	private void displayOperationsMenu(UserDto userDto) {
		do {
			System.out.print(TerminalInterface.authUserInterface);
			if (scanner.hasNextInt()) {
				int selectionOfOperation = scanner.nextInt();
				Operation operation = operationManager.getOperation(selectionOfOperation);
				operation.execute(userDto);
				if (operation instanceof ExitOperation) {
					return;
				}
			} else {
				scanner.nextLine();
			}
		} while (true);
	}
}