package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;

/**
 * The UnknownOperation class handles unknown operations.
 */
@RequiredArgsConstructor
public class UnknownOperation implements OperationHandler {

	private final ResponseGenerator responseGenerator;

	/**
	 * Handles the request for an unknown operation.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Unknown operation");
	}
}
