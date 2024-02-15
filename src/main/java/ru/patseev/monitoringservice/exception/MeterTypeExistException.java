package ru.patseev.monitoringservice.exception;

/**
 * Exception thrown to indicate that a meter type already exists.
 * This exception is typically thrown when attempting to create a duplicate meter type.
 */
public class MeterTypeExistException extends RuntimeException {

	/**
	 * Constructs a new MeterTypeExistException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public MeterTypeExistException(String message) {
		super(message);
	}
}
