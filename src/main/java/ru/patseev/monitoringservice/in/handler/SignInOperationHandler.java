package ru.patseev.monitoringservice.in.handler;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.AbstractOperationHandler;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.util.Scanner;

/**
 * The SignInOperationHandler class handles the user sign-in operation,
 * allowing users to log in by providing their credentials.
 * This handler prompts the user for login details, invokes the UserController
 * to authenticate the user, and opens a session using the UserSessionManager if successful.
 */
@RequiredArgsConstructor
public class SignInOperationHandler extends AbstractOperationHandler {
	private final Scanner scanner;
	private final UserController userController;
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

			UserDto request = new UserDto(username, password, null);
			UserDto authUserData = userController.authUser(request);

			if (authUserData != null) {
				sessionManager.openSessionForUser(authUserData);
			}
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}