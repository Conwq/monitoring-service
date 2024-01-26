package ru.patseev.monitoringservice.in;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.util.TerminalInterface;

import java.util.Scanner;

/**
 * The Application class represents the main entry point of the monitoring service application.
 * It provides a console-based user interface for user registration, sign-in, and exiting the application.
 */
@RequiredArgsConstructor
public class Application {
	private final Scanner scanner;
	private final AbstractOperationHandler registrationOperationHandler;
	private final AbstractOperationHandler signInOperationHandler;

	/**
	 * Renders the main interface for user interaction, allowing them to register, sign in, or exit the application.
	 */
	public void renderInterface() {
		boolean exit = false;

		System.out.println("\nВойдите или зарегистрируйтесь.");
		do {
			System.out.println(TerminalInterface.MAIN_MENU_INTERFACE);
			if (scanner.hasNextInt()) {
				int selectionOfOperation = scanner.nextInt();
				switch (selectionOfOperation) {
					case 1 -> registrationOperationHandler.handleOperation();
					case 2 -> signInOperationHandler.handleOperation();
					case 3 -> exit = true;
					default -> System.out.println("Неверные данные.");
				}
			} else {
				scanner.nextLine();
			}
		} while (!exit);
		scanner.close();
	}
}