package ru.patseev.monitoringservice.in.session.operation.impl.admin;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.session.operation.util.PrinterMeterData;
import ru.patseev.monitoringservice.dto.UserDto;

import java.util.List;
import java.util.Map;

/**
 * The ViewAllUsersMeterDataOperation class represents an operation for viewing meter data of all users.
 * It retrieves and displays meter data for all users through the DataMeterController.
 */
@RequiredArgsConstructor
public class ViewAllUsersMeterDataOperation implements Operation {

	/**
	 * The controller responsible for managing data meter operations.
	 */
	private final MeterController meterController;

	/**
	 * The printer responsible for displaying meter data.
	 */
	private final PrinterMeterData printerMeterData;

	/**
	 * Executes the view all users' meter data operation.
	 * Retrieves and displays meter data for all users through the DataMeterController.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		Map<String, List<DataMeterDto>> dataFromAllMeterUsers = meterController.getDataFromAllMeterUsers(userDto);
		displayAllUsersMeterData(dataFromAllMeterUsers);
	}

	/**
	 * Displays all meter data for each user in a formatted manner.
	 *
	 * @param dataFromAllMeterUsers A map containing usernames as keys and corresponding lists of DataMeterDto as values.
	 */
	private void displayAllUsersMeterData(Map<String, List<DataMeterDto>> dataFromAllMeterUsers) {
		for (Map.Entry<String, List<DataMeterDto>> entry : dataFromAllMeterUsers.entrySet()) {
			System.out.printf("\n****** %s ******", entry.getKey());

			List<DataMeterDto> listDataMeter = entry.getValue();
			listDataMeter.forEach(printerMeterData::printData);
			System.out.println("+------------+");
		}
	}
}