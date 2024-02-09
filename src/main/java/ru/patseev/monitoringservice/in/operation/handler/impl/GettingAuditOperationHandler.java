package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;

import java.util.List;

/**
 * Operation handler for retrieving audit-related data.
 */
@RequiredArgsConstructor
public class GettingAuditOperationHandler implements OperationHandler {

	/**
	 * ResponseGenerator used for generating HTTP responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * AuditController used for handling audit-related operations.
	 */
	private final AuditController auditController;

	/**
	 * Handles the incoming HTTP request to retrieve audit-related data.
	 *
	 * @param req  The HttpServletRequest object representing the HTTP request.
	 * @param resp The HttpServletResponse object representing the HTTP response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		String jwtToken = req.getHeader("Authorization");
		String username = req.getParameter("username");

		List<UserActionDto> listOfUserActions = auditController.getListOfUserActions(username, jwtToken);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, listOfUserActions);
	}
}
