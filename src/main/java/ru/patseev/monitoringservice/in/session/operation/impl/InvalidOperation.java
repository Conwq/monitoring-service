package ru.patseev.monitoringservice.in.session.operation.impl;

import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.dto.UserDto;

/**
 * The InvalidOperation class represents an operation that is executed when an invalid
 * operation number is selected by the user. It prints a message indicating that the
 * operation is invalid.
 */
public class InvalidOperation implements Operation {

	/**
	 * Executes the invalid operation, printing a message indicating that the operation is invalid.
	 *
	 * @param userDto The UserDto representing the user data (not used in this operation).
	 */
	@Override
	public void execute(UserDto userDto) {
		System.out.println("\nНеверная операция.");
	}
}