package ru.patseev.monitoringservice.meter_service.exception;

/**
 * The MeterDataFeedConflictException is an exception thrown when there is a conflict in meter data feed.
 */
public class MeterDataFeedConflictException extends RuntimeException {

	/**
	 * Constructs a new MeterDataFeedConflictException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public MeterDataFeedConflictException(String message) {
		super(message);
	}
}