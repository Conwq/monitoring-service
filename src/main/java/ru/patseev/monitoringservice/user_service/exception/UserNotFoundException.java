package ru.patseev.monitoringservice.user_service.exception;

/**
 * The UserNotFoundException thrown when a requested user is not found.
 */
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
	}
}