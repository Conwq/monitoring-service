package ru.patseev.monitoringservice.in.session.operation.impl;

import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

public class ExitOperation implements Operation {

	@Override
	public void execute(UserDto userDto) {
		System.out.println("До свидания!\n");
	}
}