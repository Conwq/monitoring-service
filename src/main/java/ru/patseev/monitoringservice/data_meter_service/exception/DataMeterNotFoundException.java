package ru.patseev.monitoringservice.data_meter_service.exception;

/**
 * The DataMeterNotFoundException is an exception thrown when the requested meter data is not found.
 */
public class DataMeterNotFoundException extends RuntimeException {

	/**
	 * Constructs a new DataMeterNotFoundException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public DataMeterNotFoundException(String message) {
		super(message);
	}
}