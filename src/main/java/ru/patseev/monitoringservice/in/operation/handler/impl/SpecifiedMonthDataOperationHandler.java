package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;

import java.util.List;

/**
 * The SpecifiedMonthDataOperationHandler class handles the operation of retrieving meter data for a specified month.
 */
public class SpecifiedMonthDataOperationHandler implements OperationHandler {

	/**
	 * The meter controller for managing meter-related operations.
	 */
	private final MeterController meterController;

	/**
	 * The response generator for generating HTTP responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * Constructs a SpecifiedMonthDataOperationHandler object with the provided dependencies.
	 *
	 * @param meterController   The MeterController instance used for handling meter-related operations.
	 * @param responseGenerator The ResponseGenerator instance used for generating HTTP responses.
	 */
	public SpecifiedMonthDataOperationHandler(MeterController meterController, ResponseGenerator responseGenerator) {
		this.meterController = meterController;
		this.responseGenerator = responseGenerator;
	}

	/**
	 * Handles the operation of retrieving meter data for a specified month.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		String jwtToken = req.getHeader("Authorization");
		String month = req.getParameter("month");
		List<DataMeterDto> meterDataForSpecifiedMonth = meterController.getMeterDataForSpecifiedMonth(jwtToken, month);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, meterDataForSpecifiedMonth);
	}
}