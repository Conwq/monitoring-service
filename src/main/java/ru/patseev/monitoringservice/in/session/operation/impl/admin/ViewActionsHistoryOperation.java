package ru.patseev.monitoringservice.in.session.operation.impl.admin;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.display.TerminalInterface;

import java.util.Scanner;

/**
 * The ViewActionsHistoryOperation class represents an operation for viewing the
 * history of user actions for a specified user.
 * This operation retrieves and displays the list of user actions through the AuditController.
 */
@RequiredArgsConstructor
public class ViewActionsHistoryOperation implements Operation {

	/**
	 * A Scanner used for user input.
	 */
	private final Scanner scanner;

	/**
	 * The controller responsible for auditing user actions.
	 */
	private final AuditController auditController;

	/**
	 * Executes the view user actions history operation.
	 * Retrieves and displays the history of user actions for a specified user
	 * through the AuditController.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		try {
			System.out.print("Введите имя пользователя для просмотра истории: ");
			scanner.nextLine();
			String username = scanner.nextLine();

			auditController
					.getListOfUserActions(username, userDto)
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