package ru.patseev.monitoringservice.in.handler;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.in.OperationHandler;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.util.Scanner;

@RequiredArgsConstructor
public class SignInOperationHandler implements OperationHandler {
	private final Scanner scanner;
	private final UserController userController;
	private final UserSessionManager sessionManager;

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