package ru.patseev.monitoringservice.in.session.operation.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class ShowCurrentMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;

	@Override
	public void execute(UserDto userDto) {
		try {
			DataMeterDto currentDataMeter = dataMeterController.getCurrentMeterData(userDto);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");

			System.out.printf(TerminalInterface.readingDataOutputText,
					currentDataMeter.date().format(format),
					currentDataMeter.heatingData(),
					currentDataMeter.coldWaterData(),
					currentDataMeter.hotWaterData()
			);
		} catch (DataMeterNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
