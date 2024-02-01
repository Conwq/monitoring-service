package ru.patseev.monitoringservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

	private static AuditService auditService;
	private static UserService userService;
	private static UserController userController;

	private UserDto userDto;

	@BeforeAll
	public static void setUp() {
		auditService = mock(AuditService.class);
		userService = mock(UserService.class);

		userController = new UserController(userService, auditService);
	}

	@BeforeEach
	public void createUser() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
	}

	@Test
	@DisplayName("Should save user")
	void saveUser_shouldSaveUser() {
		when(userService.saveUser(userDto))
				.thenReturn(userDto);

		userController.saveUser(userDto);

		verify(userService, times(1))
				.saveUser(userDto);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.REGISTRATION, userDto);
	}

	@Test
	@DisplayName("Should return UserDto")
	void authUser_shouldReturnUserDto() {
		when(userService.authUser(userDto))
				.thenReturn(userDto);

		UserDto userData = userController.authUser(userDto);

		assertThat(userData)
				.isEqualTo(userDto);
		verify(auditService)
				.saveUserAction(ActionEnum.LOG_IN, userDto);
	}

	//todo getUser(String username)
}