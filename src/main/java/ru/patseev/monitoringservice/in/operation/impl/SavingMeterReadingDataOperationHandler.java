package ru.patseev.monitoringservice.in.operation.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.extract.ObjectExtractor;
import ru.patseev.monitoringservice.in.operation.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;

/**
 * The SavingMeterReadingDataOperationHandler class handles the operation of saving meter reading data.
 */
@RequiredArgsConstructor
public class SavingMeterReadingDataOperationHandler implements OperationHandler {

	/** The meter controller for managing meter-related operations. */
	private final MeterController meterController;

	/** The response generator for generating HTTP responses. */
	private final ResponseGenerator responseGenerator;

	/** The object extractor for extracting objects from HTTP requests. */
	private final ObjectExtractor objectExtractor;

	/**
	 * Handles the operation of saving meter reading data.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		String jwtToken = req.getHeader("Authorization");
		DataMeterDto dataMeterDto = objectExtractor.extractObject(req, DataMeterDto.class);
		meterController.saveMeterData(jwtToken, dataMeterDto);
		responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, "Meter reading data sent");
	}
}
