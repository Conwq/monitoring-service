package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.MeterDataFeedConflictException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.LocalDate;
import java.util.Scanner;

@RequiredArgsConstructor
public class EnterAndSubmitMeterDataOperation implements Operation {
	private final Scanner scanner;
	private final DataMeterController dataMeterController;

	@Override
	public void execute(UserDto userDto) {
		try {
			scanner.nextLine();

			System.out.print("Введите показания отопления: ");
			Long heatingData = scanner.nextLong();

			System.out.print("Введите показания холодной воды: ");
			Long coldWaterData = scanner.nextLong();

			System.out.print("Введите показания горячей воды: ");
			Long hotWaterData = scanner.nextLong();

			System.out.println();

			DataMeterDto dataMeterDto = new DataMeterDto(LocalDate.now(), heatingData, coldWaterData, hotWaterData);
			dataMeterController.sendMeterData(userDto, dataMeterDto);
		} catch (MeterDataFeedConflictException e) {
			System.out.println(e.getMessage());
		}
	}
}
