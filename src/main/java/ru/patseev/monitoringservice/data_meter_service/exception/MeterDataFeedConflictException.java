package ru.patseev.monitoringservice.data_meter_service.exception;

/**
 * The MeterDataFeedConflictException is an exception thrown when there is a conflict in meter data feed.
 */
public class MeterDataFeedConflictException extends Exception {

	public MeterDataFeedConflictException(String message) {
		super(message);
	}
}