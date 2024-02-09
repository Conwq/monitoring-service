package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;

import java.util.List;
import java.util.Map;

/**
 * The RetrievalForAllUserMeterDataOperationHandler class handles the operation of retrieving data for all user meter data.
 */
@RequiredArgsConstructor
public class RetrievalForAllUserMeterDataOperationHandler implements OperationHandler {

	/** The meter controller for managing meter-related operations. */
	private final MeterController meterController;

	/** The response generator for generating HTTP responses. */
	private final ResponseGenerator responseGenerator;

	/**
	 * Handles the operation of retrieving data for all user meter data.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		String jwtToken = req.getHeader("Authorization");
		Map<String, List<DataMeterDto>> dataFromAllMeterUsers = meterController.getDataFromAllMeterUsers(jwtToken);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, dataFromAllMeterUsers);
	}
}
