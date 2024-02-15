package ru.patseev.monitoringservice.in.operation.manager.impl;

import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.handler.OperationHandler;
import ru.patseev.monitoringservice.in.operation.handler.impl.*;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;
import ru.patseev.monitoringservice.in.validator.impl.MeterDataValidator;
import ru.patseev.monitoringservice.in.validator.impl.MeterTypeValidator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Implementation of OperationManager for managing operations related to meters.
 */
public class MeterOperationManager implements OperationManager {

	/**
	 * Map to store operation enums and their corresponding handlers.
	 */
	private final Map<OperationEnum, OperationHandler> operations;

	/**
	 * Constructs a MeterOperationManager.
	 *
	 * @param meterController   The meter controller for managing meter-related operations.
	 * @param responseGenerator The response generator for generating HTTP responses.
	 * @param objectExtractor   The object extractor for extracting objects from HTTP requests.
	 */
	public MeterOperationManager(MeterController meterController,
								 ResponseGenerator responseGenerator,
								 ObjectExtractor objectExtractor) {
		operations = initializationOperations(meterController, responseGenerator, objectExtractor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationHandler chooseOperation(String operationName) {
		return getOperationHandler(operationName, operations);
	}

	/**
	 * Initializes the operations map with operation enums and their corresponding handlers.
	 *
	 * @param meterController   The meter controller for managing meter-related operations.
	 * @param responseGenerator The response generator for generating HTTP responses.
	 * @param objectExtractor   The object extractor for extracting objects from HTTP requests.
	 * @return The map containing operation enums as keys and their corresponding handlers as values.
	 */
	private Map<OperationEnum, OperationHandler> initializationOperations(MeterController meterController,
																		  ResponseGenerator responseGenerator,
																		  ObjectExtractor objectExtractor) {
		Map<OperationEnum, OperationHandler> operations = new EnumMap<>(OperationEnum.class);

		operations.put(OperationEnum.LAST_DATA,
				new ObtainingLatestDataMeterOperationHandler(meterController, responseGenerator));
		operations.put(OperationEnum.SPECIFIED_MONTH_DATA,
				new SpecifiedMonthDataOperationHandler(meterController, responseGenerator));
		operations.put(OperationEnum.USER_DATA,
				new RetrievingUserMeterDataOperationHandler(meterController, responseGenerator));
		operations.put(OperationEnum.ALL_DATA,
				new RetrievalForAllUserMeterDataOperationHandler(meterController, responseGenerator));
		operations.put(OperationEnum.SAVE_DATA,
				new SavingMeterReadingDataOperationHandler(meterController, responseGenerator, objectExtractor, new MeterDataValidator()));
		operations.put(OperationEnum.ALL_METER_TYPES,
				new GettingAllMeterTypesOperationHandler(meterController, responseGenerator));
		operations.put(OperationEnum.ADD_METER_TYPE,
				new AddingNewMeterTypeOperationHandler(meterController, responseGenerator, objectExtractor, new MeterTypeValidator()));

		return operations;
	}
}
