package ru.patseev.monitoringservice.in.handler;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.OperationHandler;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserAlreadyExistException;

import java.util.Scanner;

@RequiredArgsConstructor
public class RegistrationOperationHandler implements OperationHandler {
	private final Scanner scanner;
	private final UserController userController;

	@Override
	public void handleOperation() {
		try {
			scanner.nextLine();

			System.out.print("Введите логин: ");
			String username = scanner.nextLine();

			System.out.print("Введите пароль: ");
			String password = scanner.nextLine();

			UserDto userDto = new UserDto(username, password, Role.USER);

			userController.saveUser(userDto);

			System.out.println("Успешная регистрация.\n");
		} catch (UserAlreadyExistException e) {
			System.out.println(e.getMessage());
		}
	}
}