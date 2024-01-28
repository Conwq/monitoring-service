package ru.patseev.monitoringservice.in.session.operation.impl.admin;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.util.TerminalInterface;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * The ViewAllUsersMeterDataOperation class represents an operation for viewing meter data of all users.
 * It retrieves and displays meter data for all users through the DataMeterController.
 */
@RequiredArgsConstructor
public class ViewAllUsersMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;

	/**
	 * Executes the view all users' meter data operation.
	 * Retrieves and displays meter data for all users through the DataMeterController.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
		Map<String, List<DataMeterDto>> dataFromAllMeterUsers = dataMeterController.getDataFromAllMeterUsers(userDto);

		displayAllUsersMeterData(dataFromAllMeterUsers, format);
	}

	/**
	 * Displays all meter data for each user in a formatted manner.
	 *
	 * @param dataFromAllMeterUsers A map containing usernames as keys and corresponding lists of DataMeterDto as values.
	 * @param format                The DateTimeFormatter used for formatting the date in the output.
	 */
	private void displayAllUsersMeterData(Map<String, List<DataMeterDto>> dataFromAllMeterUsers, DateTimeFormatter format) {
		for (Map.Entry<String, List<DataMeterDto>> entry : dataFromAllMeterUsers.entrySet()) {
			System.out.printf("\n****** %s ******", entry.getKey());

			List<DataMeterDto> listDataMeter = entry.getValue();

			listDataMeter.forEach(dataMeterDto ->
					System.out.printf(TerminalInterface.METER_DATA_OUTPUT_TEXT,
							dataMeterDto.date().format(format),
							dataMeterDto.meterTypeName(),
							dataMeterDto.value())
			);
			System.out.println("+------------+");
		}
	}
}