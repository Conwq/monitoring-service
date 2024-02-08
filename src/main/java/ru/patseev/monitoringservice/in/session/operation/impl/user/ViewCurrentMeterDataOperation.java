package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.dto.UserDto;

/**
 * The ViewCurrentMeterDataOperation class represents an operation for viewing the current meter data.
 * It retrieves and displays the current meter data for the authenticated user.
 */
@RequiredArgsConstructor
public class ViewCurrentMeterDataOperation implements Operation {

	/**
	 * The controller responsible for managing data meter operations.
	 */
	private final MeterController meterController;

	/**
	 * The printer responsible for displaying meter data.
	 */
	private final PrinterMeterData printerMeterData;

	/**
	 * Executes the view current meter data operation.
	 * Retrieves and displays the current meter data for the authenticated user.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		try {
			DataMeterDto currentDataMeter = meterController.getCurrentMeterData(userDto);
			printerMeterData.printData(currentDataMeter);
		} catch (DataMeterNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}