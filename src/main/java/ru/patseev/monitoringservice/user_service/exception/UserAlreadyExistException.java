package ru.patseev.monitoringservice.user_service.exception;

/**
 * The UserAlreadyExistException thrown when attempting to create a user that already exists.
 */
public class UserAlreadyExistException extends RuntimeException {

	/**
	 * Constructs a new UserAlreadyExistException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public UserAlreadyExistException(String message) {
		super(message);
	}
}