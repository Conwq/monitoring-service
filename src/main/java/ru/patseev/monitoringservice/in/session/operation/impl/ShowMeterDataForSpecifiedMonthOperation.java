package ru.patseev.monitoringservice.in.session.operation.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@RequiredArgsConstructor
public class ShowMeterDataForSpecifiedMonthOperation implements Operation {
	private final Scanner scanner;
	private final DataMeterController dataMeterController;

	@Override
	public void execute(UserDto userDto) {
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
			System.out.println("Введите номер месяца:");
			if (scanner.hasNextInt()) {
				int month = scanner.nextInt();

				if (month < 1 || month > 12) {
					System.out.println("Такого месяца не существует\n");
					return;
				}

				DataMeterDto response = dataMeterController.getMeterDataForSpecifiedMonth(userDto, month);

				System.out.printf(TerminalInterface.readingDataOutputText,
						response.date().format(format),
						response.heatingData(),
						response.coldWaterData(),
						response.hotWaterData()
				);
			} else {
				scanner.nextLine();
			}
		} catch (DataMeterNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
