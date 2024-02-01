package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

/**
 * The ViewMeterDataOperation class represents an operation for viewing meter data for a specific user.
 * It retrieves and displays meter data for the authenticated user.
 */
@RequiredArgsConstructor
public class ViewMeterDataOperation implements Operation {

	/**
	 * The controller responsible for managing data meter operations.
	 */
	private final DataMeterController dataMeterController;

	/**
	 * The printer responsible for displaying meter data.
	 */
	private final PrinterMeterData printerMeterData;

	/**
	 * Executes the view meter data operation, retrieving and displaying meter data for the authenticated user.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		dataMeterController
				.getMeterDataForUser(userDto)
				.forEach(printerMeterData::printData);
	}
}