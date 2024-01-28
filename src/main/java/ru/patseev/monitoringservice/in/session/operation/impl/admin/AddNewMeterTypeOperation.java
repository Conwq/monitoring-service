package ru.patseev.monitoringservice.in.session.operation.impl.admin;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.MeterTypeDto;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.Scanner;

/**
 * The AddNewMeterTypeOperation class represents an operation for adding a new meter type.
 * It prompts the user to input the new meter type and sends the data to the controller.
 */
@RequiredArgsConstructor
public class AddNewMeterTypeOperation implements Operation {
	private final Scanner scanner;
	private final DataMeterController dataMeterController;

	/**
	 * Executes the operation to add a new meter type.
	 *
	 * @param userDto The UserDto representing the authenticated user.
	 */
	@Override
	public void execute(UserDto userDto) {
		scanner.nextLine();

		System.out.print("Введите новый тип счетчика: ");
		String inputTypeName = scanner.nextLine();

		MeterTypeDto newMeterType = new MeterTypeDto(null, inputTypeName);

		dataMeterController.addNewMeterType(userDto, newMeterType);

		System.out.println("Счетчик успешно добавлен!");
	}
}