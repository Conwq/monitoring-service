package ru.patseev.monitoringservice.in.operation.handler.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.exception.MeterTypeExistException;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.validator.Validator;

/**
 * The AddingNewCounterTypeOperationHandler class handles the operation of adding a new counter type.
 */
public class AddingNewMeterTypeOperationHandler implements OperationHandler {

	/**
	 * The meter controller for managing meter-related operations.
	 */
	private final MeterController meterController;

	/**
	 * The response generator for generating HTTP responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * The object extractor for extracting objects from HTTP requests.
	 */
	private final ObjectExtractor objectExtractor;

	/**
	 * The validator for validating MeterTypeDto objects.
	 */
	private final Validator<MeterTypeDto> meterTypeDtoValidator;

	/**
	 * Constructs an AddingNewMeterTypeOperationHandler object with the provided dependencies.
	 *
	 * @param meterController       The MeterController instance used for handling meter-related operations.
	 * @param responseGenerator     The ResponseGenerator instance used for generating HTTP responses.
	 * @param objectExtractor       The ObjectExtractor instance used for extracting objects from HTTP requests.
	 * @param meterTypeDtoValidator The Validator instance used for validating MeterTypeDto objects.
	 */
	public AddingNewMeterTypeOperationHandler(MeterController meterController,
											  ResponseGenerator responseGenerator,
											  ObjectExtractor objectExtractor,
											  Validator<MeterTypeDto> meterTypeDtoValidator) {
		this.meterController = meterController;
		this.responseGenerator = responseGenerator;
		this.objectExtractor = objectExtractor;
		this.meterTypeDtoValidator = meterTypeDtoValidator;
	}

	/**
	 * Handles the operation of adding a new counter type.
	 *
	 * @param req  The HTTP servlet request.
	 * @param resp The HTTP servlet response.
	 */
	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String jwtToken = req.getHeader("Authorization");
			MeterTypeDto meterTypeDto = objectExtractor.extractObject(req, MeterTypeDto.class);

			if (meterTypeDtoValidator.validate(meterTypeDto)) {
				responseGenerator.generateResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid data");
				return;
			}
			meterController.addNewMeterType(jwtToken, meterTypeDto);
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_OK, "New meter type saved");
		} catch (MeterTypeExistException e) {
			responseGenerator.generateResponse(resp, HttpServletResponse.SC_CONFLICT, e.getMessage());
		}
	}
}
