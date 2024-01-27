package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.format.DateTimeFormatter;

/**
 * The ViewMeterDataOperation class represents an operation for viewing meter data for a specific user.
 * It retrieves and displays meter data for the authenticated user.
 */
@RequiredArgsConstructor
public class ViewMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;

	/**
	 * Executes the view meter data operation, retrieving and displaying meter data for the authenticated user.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
		dataMeterController
				.getMeterDataForUser(userDto)
				.forEach(dto -> System.out.printf(TerminalInterface.METER_DATA_OUTPUT_TEXT,
						dto.date().format(format),
						dto.heatingData(),
						dto.coldWaterData(),
						dto.hotWaterData()
				));
	}
}