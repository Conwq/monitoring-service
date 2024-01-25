package ru.patseev.monitoringservice.in.session.operation.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class ShowAllUsersMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;

	@Override
	public void execute(UserDto userDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
		dataMeterController
				.getAllMeterData(userDto)
				.forEach(dto -> System.out.printf(TerminalInterface.readingDataOutputText,
						dto.date().format(format),
						dto.heatingData(),
						dto.coldWaterData(),
						dto.hotWaterData()
				));
	}
}
