package ru.patseev.monitoringservice.in;

/**
 * The AbstractOperationHandler class is an abstract base class representing a generic operation handler.
 * Subclasses should implement the handleOperation method to define specific operations.
 */
public abstract class AbstractOperationHandler {

	/**
	 * Handles a specific operation. Subclasses must implement this method to provide the operation logic.
	 */
	abstract public void handleOperation();
}