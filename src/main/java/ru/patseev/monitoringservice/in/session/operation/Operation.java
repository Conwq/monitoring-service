package ru.patseev.monitoringservice.in.session.operation;

import ru.patseev.monitoringservice.user_service.dto.UserDto;

/**
 * The Operation interface represents an operation that can be executed by a user.
 */
public interface Operation {

	/**
	 * Executes the operation based on the provided user data.
	 *
	 * @param userDto The UserDto representing the user data.
	 */
	void execute(UserDto userDto);
}