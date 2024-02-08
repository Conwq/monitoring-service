package ru.patseev.monitoringservice.manager;

import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.in.extract.ObjectExtractor;
import ru.patseev.monitoringservice.in.operation.OperationHandler;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The OperationManager class manages operations based on their names.
 */
public class OperationManager {

	/** The map containing operation handlers indexed by operation enums. */
	private final Map<OperationEnum, OperationHandler> operations;

	/**
	 * Constructs an OperationManager instance with specified controllers and extractors.
	 *
	 * @param meterController     The MeterController for managing meter-related operations.
	 * @param responseGenerator   The ResponseGenerator for generating HTTP responses.
	 * @param userController      The UserController for managing user-related operations.
	 * @param objectExtractor     The ObjectExtractor for extracting objects from requests.
	 * @param auditController     The AuditController for managing audit-related operations.
	 */
	public OperationManager(MeterController meterController,
							ResponseGenerator responseGenerator,
							UserController userController,
							ObjectExtractor objectExtractor,
							AuditController auditController) {

		operations = new HashMap<>() {{
			put(OperationEnum.CURRENT_DATA,
					new ObtainingLatestRelevantDataOperationHandler(meterController, responseGenerator)
			);
			put(OperationEnum.SPECIFIED_MONTH_DATA,
					new SpecifiedMonthDataOperationHandler(meterController, responseGenerator)
			);
			put(OperationEnum.USER_DATA,
					new RetrievingAllUserMeterDataOperationHandler(meterController, responseGenerator)
			);
			put(OperationEnum.ALL_DATA,
					new RetrievalForAllUserMeterDataOperationHandler(meterController, responseGenerator)
			);
			put(OperationEnum.METER_TYPE,
					new GettingAllMeterTypesOperationHandler(meterController, responseGenerator)
			);
			put(OperationEnum.SAVE_DATA,
					new SavingMeterReadingDataOperationHandler(meterController, responseGenerator, objectExtractor)
			);
			put(OperationEnum.ADD_METER_TYPE,
					new AddingNewCounterTypeOperationHandler(meterController, responseGenerator, objectExtractor)
			);
			put(OperationEnum.AUTH,
					new AuthorizationOperationHandler(responseGenerator, userController, objectExtractor)
			);
			put(OperationEnum.REGISTRATION,
					new RegistrationOperationHandler(responseGenerator, userController, objectExtractor)
			);
			put(OperationEnum.GET_AUDIT,
					new GettingAuditOperationHandler(responseGenerator, auditController)
			);
		}};
	}

	/**
	 * Chooses the operation handler based on the operation name.
	 *
	 * @param operationName The name of the operation.
	 * @return The corresponding OperationHandler.
	 */
	public OperationHandler chooseOperation(String operationName) {
		try {
			OperationEnum operation = OperationEnum.valueOf(operationName.toUpperCase());
			return operations.getOrDefault(operation, new UnknownOperation());
		} catch (IllegalArgumentException e) {
			//todo
			return new NonExistentOperation();
		}
	}
}