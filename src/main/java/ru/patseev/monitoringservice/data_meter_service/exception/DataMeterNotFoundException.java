package ru.patseev.monitoringservice.data_meter_service.exception;

/**
 * The DataMeterNotFoundException is an exception thrown when the requested meter data is not found.
 */
public class DataMeterNotFoundException extends Exception {

	public DataMeterNotFoundException(String message) {
		super(message);
	}
}