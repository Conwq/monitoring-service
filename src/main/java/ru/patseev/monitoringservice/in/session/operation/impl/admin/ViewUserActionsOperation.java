package ru.patseev.monitoringservice.in.session.operation.impl.admin;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.audit_service.controller.AuditController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.util.Scanner;

@RequiredArgsConstructor
public class ViewUserActionsOperation implements Operation {
	private final Scanner scanner;
	private final AuditController auditController;

	@Override
	public void execute(UserDto userDto) {
		try {
			System.out.print("Введите имя пользователя для просмотра истории: ");
			scanner.nextLine();
			String username = scanner.nextLine();

			auditController
					.getListOfUserActions(username)
					.forEach(dto -> System.out.printf(
							TerminalInterface.ACTION_OUTPUT_TEXT,
							dto.actionAt(),
							dto.action())
					);

		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
	}
}
