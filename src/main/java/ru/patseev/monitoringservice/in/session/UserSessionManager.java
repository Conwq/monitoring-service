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
		operationMenuSelection(userDto);
	}

	private void operationMenuSelection(UserDto userDto) {
		String terminalInterface =
				userDto.role() == Role.ADMIN ? TerminalInterface.ADMIN_INTERFACE : TerminalInterface.AUTH_USER_INTERFACE;
		boolean exit = false;

		do {
			System.out.println(terminalInterface);
			if (scanner.hasNextInt()) {
				int selectionOfOperation = scanner.nextInt();
				Operation operation = operationManager.getOperation(selectionOfOperation, userDto.role());
				operation.execute(userDto);
				if (operation instanceof ExitOperation) {
					exit = true;
				}
			} else {
				System.out.println("Выберите номер операции.");
				scanner.nextLine();
			}
		} while (!exit);
	}
}