package ru.patseev.monitoringservice.exception;

/**
 * Custom exception thrown to indicate that a meter type is not found.
 */
public class MeterTypeNotFoundException extends RuntimeException {

	/**
	 * Constructs a MeterTypeNotFoundException with the specified detail message.
	 *
	 * @param message The detail message that describes the reason for the exception.
	 */
	public MeterTypeNotFoundException(String message) {
		super(message);
	}
}