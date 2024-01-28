package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.data_meter_service.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.util.TerminalInterface;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The ViewMeterDataForSpecifiedMonthOperation class represents an operation for viewing meter data for a specified month.
 * It prompts the user to input a month, retrieves and displays meter data for the specified month for the authenticated user.
 */
@RequiredArgsConstructor
public class ViewMeterDataForSpecifiedMonthOperation implements Operation {
	private final Scanner scanner;
	private final DataMeterController dataMeterController;

	/**
	 * Executes the view meter data for a specified month operation.
	 * Prompts the user to input a month, retrieves and displays meter data for the specified month for the authenticated user.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		try {
			System.out.print("Введите номер месяца: ");
			if (scanner.hasNextInt()) {
				int month = scanner.nextInt();
				if (month < 1 || month > 12) {
					System.out.println("Такого месяца не существует\n");
					return;
				}
				DataMeterDto response = dataMeterController.getMeterDataForSpecifiedMonth(userDto, month);
				printData(response);
			} else {
				scanner.nextLine();
			}
		} catch (DataMeterNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private void printData(DataMeterDto dataMeterDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
		System.out.printf(TerminalInterface.METER_DATA_OUTPUT_TEXT,
				dataMeterDto.date().format(format),
				dataMeterDto.meterTypeName(),
				dataMeterDto.value()
		);
	}
}