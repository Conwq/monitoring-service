package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;

/**
 * The ObtainingLatestRelevantDataOperationHandler class handles the operation of obtaining the latest relevant data.
 */
public class ObtainingLatestDataMeterOperationHandler implements OperationHandler {

	/**
	 * The meter controller for managing meter-related operations.
	 */
	private final MeterController meterController;

	/**
	 * The response generator for generating HTTP responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * Constructs an ObtainingLatestDataMeterOperationHandler object with the provided dependencies.
	 *
	 * @param meterController   The MeterController instance used for handling meter-related operations.
	 * @param responseGenerator The ResponseGenerator instance used for generating HTTP responses.
	 */
	public ObtainingLatestDataMeterOperationHandler(MeterController meterController, ResponseGenerator responseGenerator) {
		this.meterController = meterController;
		this.responseGenerator = responseGenerator;
	}

	/**
	 * Handles the operation of obtaining the latest relevant data.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String jwtToken = req.getHeader("Authorization");
			DataMeterDto currentMeterData = meterController.getLatestMeterData(jwtToken);
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, currentMeterData);
		} catch (DataMeterNotFoundException e) {
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		}
	}
}
