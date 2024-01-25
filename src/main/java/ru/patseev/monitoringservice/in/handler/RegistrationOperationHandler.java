package ru.patseev.monitoringservice.in.handler;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.OperationHandler;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.Scanner;

@RequiredArgsConstructor
public class RegistrationOperationHandler implements OperationHandler {
	private final Scanner scanner;
	private final UserController userController;

	@Override
	public void handleOperation() {
		scanner.nextLine();

		System.out.println("Enter username:");
		String username = scanner.nextLine();

		System.out.println("Enter password:");
		String password = scanner.nextLine();

		UserDto userDto = new UserDto(username, password, Role.USER);

		userController.saveUser(userDto);

		System.out.println("You have successfully registered.\n");
	}
}