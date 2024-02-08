package ru.patseev.monitoringservice.in.operation.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.in.operation.OperationHandler;

/**
 * The UnknownOperation class handles unknown operations.
 */
public class UnknownOperation implements OperationHandler {

	/**
	 * Handles the request for an unknown operation.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		//todo
	}
}
