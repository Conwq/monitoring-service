package ru.patseev.monitoringservice.user_service.exception;

/**
 * The UserAlreadyExistException thrown when attempting to create a user that already exists.
 */
public class UserAlreadyExistException extends RuntimeException {

	public UserAlreadyExistException(String message) {
		super(message);
	}
}