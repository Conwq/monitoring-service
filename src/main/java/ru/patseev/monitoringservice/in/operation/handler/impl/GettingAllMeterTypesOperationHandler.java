package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import java.util.List;

/**
 * The GettingAllMeterTypesOperationHandler class handles the operation of retrieving all meter types.
 */
@RequiredArgsConstructor
public class GettingAllMeterTypesOperationHandler implements OperationHandler {

	/** The meter controller for managing meter-related operations. */
	private final MeterController meterController;

	/** The response generator for generating HTTP responses. */
	private final ResponseGenerator responseGenerator;

	/**
	 * Handles the operation of retrieving all meter types.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		String jwtToken = req.getHeader("Authorization");

		List<MeterTypeDto> availableMeterType = meterController.getAvailableMeterType(jwtToken);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, availableMeterType);
	}
}
