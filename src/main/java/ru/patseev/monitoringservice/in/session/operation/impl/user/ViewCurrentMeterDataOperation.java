package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

/**
 * The ViewCurrentMeterDataOperation class represents an operation for viewing the current meter data.
 * It retrieves and displays the current meter data for the authenticated user.
 */
@RequiredArgsConstructor
public class ViewCurrentMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;
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
			DataMeterDto currentDataMeter = dataMeterController.getCurrentMeterData(userDto);
			printerMeterData.printData(currentDataMeter);
		} catch (DataMeterNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}