package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;

import java.util.List;

/**
 * The SpecifiedMonthDataOperationHandler class handles the operation of retrieving meter data for a specified month.
 */
@RequiredArgsConstructor
public class SpecifiedMonthDataOperationHandler implements OperationHandler {

	/** The meter controller for managing meter-related operations. */
	private final MeterController meterController;

	/** The response generator for generating HTTP responses. */
	private final ResponseGenerator responseGenerator;

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