package ru.patseev.monitoringservice.in.handler;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.session.UserSessionManager;
import ru.patseev.monitoringservice.in.OperationHandler;
import ru.patseev.monitoringservice.user_service.controller.UserController;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.util.Scanner;

@RequiredArgsConstructor
public class SignInOperationHandler implements OperationHandler {
	private final Scanner scanner;
	private final UserController userController;
	private final UserSessionManager sessionManager;

	@Override
	public void handleOperation() {
		scanner.nextLine();

		System.out.println("Enter username:");
		String username = scanner.nextLine();

		System.out.println("Enter password:");
		String password = scanner.nextLine();

		UserDto request = new UserDto(username, password, null);
		UserDto authUserData = userController.authUser(request);

		if (authUserData != null) {
			sessionManager.openSessionForUser(authUserData);
		}
	}
}