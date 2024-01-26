package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class ShowUsersMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;

	@Override
	public void execute(UserDto userDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
		dataMeterController
				.getMeterDataForUserByUsername(userDto)
				.forEach(dto -> System.out.printf(TerminalInterface.METER_DATA_OUTPUT_TEXT,
						dto.date().format(format),
						dto.heatingData(),
						dto.coldWaterData(),
						dto.hotWaterData()
				));
	}
}
