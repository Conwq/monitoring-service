package ru.patseev.monitoringservice.exception;

/**
 * An exception thrown when meter data has already been submitted for the current month.
 */
public class MeterDataConflictException extends RuntimeException {

	/**
	 * Constructs a new MeterDataWasSubmittedException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
	 */
	public MeterDataConflictException(String message) {
		super(message);
	}
}
