package ru.patseev.monitoringservice.in.operation.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The OperationHandler interface defines a method for handling HTTP requests.
 */
public interface OperationHandler {

	/**
	 * Handles an HTTP request.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	void handleRequest(HttpServletRequest req, HttpServletResponse resp);
}