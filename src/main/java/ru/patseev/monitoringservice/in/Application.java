package ru.patseev.monitoringservice.in;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.in.util.TerminalInterface;

import java.util.Scanner;

@RequiredArgsConstructor
public class Application {
	private final Scanner scanner;
	private final OperationHandler registrationOperationHandler;
	private final OperationHandler signInOperationHandler;

	public void renderInterface() {
		boolean exit = false;

		System.out.println("\nHello. Login or register.\n");
		do {
			System.out.println(TerminalInterface.mainMenuInterface);

			if (scanner.hasNextInt()) {
				int selectionOfOperation = scanner.nextInt();
				switch (selectionOfOperation) {
					case 1 -> registrationOperationHandler.handleOperation();
					case 2 -> signInOperationHandler.handleOperation();
					case 3 -> exit = true;
					default -> System.out.println("Invalid data.");
				}
			}
			else {
				scanner.nextLine();
			}
		} while (!exit);
		scanner.close();
	}
}