package ru.patseev.monitoringservice.in.session.operation.impl;

import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

/**
 * The ExitOperation class represents an operation that signals the end of a session.
 * When executed, it prints a farewell message.
 */
public class ExitOperation implements Operation {

	/**
	 * Executes the exit operation, printing a farewell message.
	 *
	 * @param userDto The UserDto representing the user data (not used in this operation).
	 */
	@Override
	public void execute(UserDto userDto) {
		System.out.println("\nДо свидания!");
	}
}