package ru.patseev.monitoringservice.in.session.operation.impl.user;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.exception.MeterDataFeedConflictException;
import ru.patseev.monitoringservice.exception.MeterNotFoundException;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * The SubmitMeterDataOperation class represents an operation for submitting meter data.
 * It prompts the user to input meter readings for heating, cold water, and hot water, then sends the data to the controller.
 */
@RequiredArgsConstructor
public class MeterDataOperation implements Operation {

	/**
	 * A Scanner used for user input.
	 */
	private final Scanner scanner;

	/**
	 * The controller responsible for managing data meter operations.
	 */
	private final MeterController meterController;

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
			List<MeterTypeDto> availableMeterType =
					meterController.getAvailableMeterType(userDto);
			do {
				printMeterTypes(availableMeterType);
				int typeNumber = promptUserInputToSelectType();
				if (typeNumber == 0) {
					return;
				}
				if (typeNumber < 0) {
					continue;
				}
				MeterTypeDto meterTypeDto = getMeterType(availableMeterType, typeNumber);
				if (checkingDataExistence(userDto, meterTypeDto.meterTypeId())) {
					return;
				}
				sendMeterData(meterTypeDto, userDto);
			} while (true);
		} catch (MeterDataFeedConflictException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Creates a DataMeterDto object based on the provided MeterTypeDto and value.
	 *
	 * @param meterTypeDto The MeterTypeDto containing information about the meter type.
	 * @param value        The value to be recorded in the data meter.
	 * @return A DataMeterDto object representing the metered data.
	 */
	private DataMeterDto createDataMeterDto(MeterTypeDto meterTypeDto, Long value) {
		LocalDate currentDate = LocalDate.now();
		return new DataMeterDto(currentDate, value, meterTypeDto.meterTypeId(), meterTypeDto.typeName());
	}

	/**
	 * Prints available meter types to the console.
	 *
	 * @param availableMeterType List of available meter types.
	 */
	private void printMeterTypes(List<MeterTypeDto> availableMeterType) {
		System.out.println("Выберите данные:");
		availableMeterType.forEach(meterTypeDto ->
				System.out.printf("""
								%s. %s
								""",
						meterTypeDto.meterTypeId(), meterTypeDto.typeName()));

		System.out.println("\n\"exit\" для выхода");
	}

	/**
	 * Prompts the user for input to select a meter type.
	 *
	 * @return The selected meter type or 0 to exit.
	 */
	private int promptUserInputToSelectType() {
		if (!scanner.hasNextInt()) {
			String enterValue = scanner.nextLine();
			if (enterValue.equalsIgnoreCase("exit")) {
				return 0;
			}
			return -1;
		}
		return scanner.nextInt();
	}

	/**
	 * Prompts the user for meter readings.
	 *
	 * @return The user-input meter value.
	 */
	private Long promptUserForMeterValue() {
		System.out.print("Введите показания счетчика: ");
		return scanner.nextLong();
	}

	/**
	 * Checks if data for the specified meter type already exists for the current month.
	 *
	 * @param userDto     The UserDto representing the authenticated user.
	 * @param meterTypeId The ID of the meter type to check.
	 * @return true if data exists, false otherwise.
	 */
	private boolean checkingDataExistence(UserDto userDto, int meterTypeId) {
		boolean dataExist = meterController
				.getMeterDataForUser(userDto)
				.stream()
				.filter(dto -> Objects.equals(dto.meterTypeId(), meterTypeId))
				.anyMatch(dto -> dto.date().getMonth() == LocalDate.now().getMonth());

		if (dataExist) {
			System.out.println("В этом месяце эти данные поданы");
		}
		return dataExist;
	}

	/**
	 * Gets the meter type based on the selected type number.
	 *
	 * @param availableMeterType List of available meter types.
	 * @param typeNumber         The selected type number.
	 * @return The MeterTypeDto for the selected type.
	 * @throws MeterNotFoundException If the meter type is not found.
	 */
	private MeterTypeDto getMeterType(List<MeterTypeDto> availableMeterType, int typeNumber) {
		return availableMeterType
				.stream()
				.filter(meterType -> meterType.meterTypeId() == typeNumber)
				.findAny()
				.orElseThrow(() -> new MeterNotFoundException("Счетчик не найден"));
	}

	/**
	 * Sends meter data for the specified meter type and user.
	 *
	 * @param meterTypeDto The selected meter type.
	 * @param userDto      The UserDto representing the authenticated user.
	 */
	private void sendMeterData(MeterTypeDto meterTypeDto, UserDto userDto) {
		Long value = promptUserForMeterValue();
		DataMeterDto dataMeterDto = createDataMeterDto(meterTypeDto, value);
		meterController.saveMeterData(userDto, dataMeterDto);
		System.out.println("Данные успешно поданы!");
	}
}