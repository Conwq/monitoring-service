package ru.patseev.monitoringservice.user_service.exception;

/**
 * The UserNotFoundException thrown when a requested user is not found.
 */
public class UserNotFoundException extends RuntimeException {

	/**
	 * Constructs a new UserNotFoundException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public UserNotFoundException(String message) {
		super(message);
	}
}