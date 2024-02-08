package ru.patseev.monitoringservice.exception;

/**
 * Exception thrown to indicate that a meter was not found.
 */
public class MeterNotFoundException extends RuntimeException {

	/**
	 * Constructs a new MeterNotFoundException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public MeterNotFoundException(String message) {
		super(message);
	}
}