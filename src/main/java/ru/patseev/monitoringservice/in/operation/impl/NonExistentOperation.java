package ru.patseev.monitoringservice.in.operation.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.in.operation.OperationHandler;

/**
 * The NonExistentOperation class represents an operation handler for non-existent operations.
 * It is used when an unsupported operation is requested.
 */
public class NonExistentOperation implements OperationHandler {

	/**
	 * Handles the request for a non-existent operation.
	 * Currently, this method does nothing as there is no specific action defined for non-existent operations.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		//todo
	}
}
