package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.MeterDataFeedConflictException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * The SubmitMeterDataOperation class represents an operation for submitting meter data.
 * It prompts the user to input meter readings for heating, cold water, and hot water, then sends the data to the controller.
 */
@RequiredArgsConstructor
public class SubmitMeterDataOperation implements Operation {
	private final Scanner scanner;
	private final DataMeterController dataMeterController;

	/**
	 * Executes to submit meter data operation.
	 * Prompts the user to input meter readings for heating, cold water, and hot water, then sends the data to the controller.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
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

			DataMeterDto dataMeterDto = createDataMeterDto(heatingData, coldWaterData, hotWaterData);
			dataMeterController.sendMeterData(userDto, dataMeterDto);
		} catch (MeterDataFeedConflictException e) {
			System.out.println(e.getMessage());
		}
	}

	private DataMeterDto createDataMeterDto(Long heatingData, Long coldWaterData, Long hotWaterData) {
		LocalDate currentDate = LocalDate.now();
		return new DataMeterDto(currentDate, heatingData, coldWaterData, hotWaterData);
	}
}