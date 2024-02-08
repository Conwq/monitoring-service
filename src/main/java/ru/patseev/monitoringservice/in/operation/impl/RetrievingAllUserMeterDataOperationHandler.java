package ru.patseev.monitoringservice.in.operation.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.operation.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;

import java.util.List;

/**
 * The RetrievingAllUserMeterDataOperationHandler class handles the operation of retrieving all user meter data.
 */
@RequiredArgsConstructor
public class RetrievingAllUserMeterDataOperationHandler implements OperationHandler {

	/** The meter controller for managing meter-related operations. */
	private final MeterController meterController;

	/** The response generator for generating HTTP responses. */
	private final ResponseGenerator responseGenerator;

	/**
	 * Handles the operation of retrieving all user meter data.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		String jwtToken = req.getHeader("Authorization");
		List<DataMeterDto> meterDataForUser = meterController.getMeterDataForUser(jwtToken);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, meterDataForUser);
	}
}
