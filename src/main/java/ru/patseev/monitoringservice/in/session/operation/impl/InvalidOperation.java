package ru.patseev.monitoringservice.in.session.operation.impl;

import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

public class InvalidOperation implements Operation {

	@Override
	public void execute(UserDto userDto) {
		System.out.println("Неверная операция.\n");
	}
}