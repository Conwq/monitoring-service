package ru.patseev.monitoringservice.in.handler.operation;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.handler.AbstractOperationHandler;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserNotFoundException;

import java.util.Scanner;

/**
 * The SignInOperationHandler class handles the user sign-in operation,
 * allowing users to log in by providing their credentials.
 * This handler prompts the user for login details, invokes the UserController
 * to authenticate the user, and opens a session using the UserSessionManager if successful.
 */
@RequiredArgsConstructor
public class SignInOperationHandler extends AbstractOperationHandler {

	/**
	 * A Scanner used for user input.
	 */
	private final Scanner scanner;

	/**
	 * The controller responsible for user-related operations.
	 */
	private final UserController userController;

	/**
	 * The manager for user sessions.
	 */
	private final UserSessionManager sessionManager;

	/**
	 * Handles the user sign-in operation.
	 * Prompts the user for a username and password, attempts to authenticate the user using
	 * the UserController, and opens a session using the UserSessionManager if successful.
	 */
	@Override
	public void handleOperation() {
		try {
			scanner.nextLine();

			System.out.print("Введите логин: ");
			String username = scanner.nextLine();

			System.out.print("Введите пароль: ");
			String password = scanner.nextLine();

			System.out.println();

			UserDto request = new UserDto(null, username, password, null);
			UserDto authUserData = userController.authUser(request);

			if (authUserData != null) {
				sessionManager.openSessionForUser(authUserData);
			}
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}