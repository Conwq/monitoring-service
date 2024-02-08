package ru.patseev.monitoringservice.exception;

/**
 * Custom exception thrown to indicate that a role is not found.
 */
public class RoleNotExistsException extends RuntimeException {

	/**
	 * Constructs a RoleNotFoundException with the specified detail message.
	 *
	 * @param message The detail message that describes the reason for the exception.
	 */
	public RoleNotExistsException(String message) {
		super(message);
	}
}