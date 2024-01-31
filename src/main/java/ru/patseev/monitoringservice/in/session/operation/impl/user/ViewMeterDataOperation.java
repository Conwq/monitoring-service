package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.dto.UserDto;

/**
 * The ViewMeterDataOperation class represents an operation for viewing meter data for a specific user.
 * It retrieves and displays meter data for the authenticated user.
 */
@RequiredArgsConstructor
public class ViewMeterDataOperation implements Operation {

	/**
	 * The controller responsible for managing data meter operations.
	 */
	private final MeterController meterController;

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
		meterController
				.getMeterDataForUser(userDto)
				.forEach(printerMeterData::printData);
	}
}