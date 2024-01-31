package ru.patseev.monitoringservice.in.handler.operation;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.handler.AbstractOperationHandler;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.RoleEnum;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;

import java.util.Scanner;

/**
 * The RegistrationOperationHandler class handles the user registration operation,
 * allowing users to create a new account by providing a username and password.
 * This handler prompts the user for registration details, invokes the UserController
 * to save the user, and displays the outcome of the registration process.
 */
@RequiredArgsConstructor
public class RegistrationOperationHandler extends AbstractOperationHandler {

	/**
	 * A Scanner used for user input.
	 */
	private final Scanner scanner;

	/**
	 * The controller responsible for user-related operations.
	 */
	private final UserController userController;

	/**
	 * Handles the user registration operation.
	 * Prompts the user for a username and password, attempts to save the user using
	 * the UserController, and displays the outcome of the registration process.
	 */
	@Override
	public void handleOperation() {
		try {
			scanner.nextLine();

			System.out.print("Введите логин: ");
			String username = scanner.nextLine();

			System.out.print("Введите пароль: ");
			String password = scanner.nextLine();

			UserDto userDto = new UserDto(null, username, password, RoleEnum.USER);

			userController.saveUser(userDto);

			System.out.println("\nУспешная регистрация.");
		} catch (UserAlreadyExistException e) {
			System.out.println(e.getMessage());
		}
	}
}