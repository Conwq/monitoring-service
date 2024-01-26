package ru.patseev.monitoringservice.user_service.controller;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.service.UserService;

class UserControllerTest {
	private static AuditService auditService;
	private static UserService userService;
	private static UserController userController;
	private UserDto userDto;

	@BeforeAll
	public static void setUp() {
		auditService = Mockito.mock(AuditService.class);
		userService = Mockito.mock(UserService.class);

		userController = new UserController(userService, auditService);
	}

	@BeforeEach
	public void createUser() {
		userDto = new UserDto("test", "test", Role.USER);
	}

	@Test
	void mustSaveUser() {
		userController.saveUser(userDto);

		Mockito
				.verify(userService, Mockito.times(1))
				.saveUser(userDto);
		Mockito
				.verify(auditService, Mockito.times(1))
				.saveUserAction(ActionEnum.REGISTRATION, userDto);
	}

	@Test
	void mustAuthUser() {
		Mockito
				.when(userService.authUser(userDto))
				.thenReturn(userDto);

		UserDto userData = userController.authUser(userDto);

		AssertionsForClassTypes.assertThat(userData).isEqualTo(userDto);
		Mockito.verify(auditService).saveUserAction(ActionEnum.LOG_IN, userDto);
	}
}