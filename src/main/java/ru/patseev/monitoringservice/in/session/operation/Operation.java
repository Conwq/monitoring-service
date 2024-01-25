package ru.patseev.monitoringservice.in.session.operation;

import ru.patseev.monitoringservice.user_service.dto.UserDto;

public interface Operation {

	void execute(UserDto userDto);
}