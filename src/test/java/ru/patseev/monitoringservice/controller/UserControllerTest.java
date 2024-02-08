package ru.patseev.monitoringservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

	private static AuditService auditService;
	private static UserService userService;
	private static JwtService jwtService;
	private static UserController userController;

	private UserDto userDto;

	@BeforeAll
	public static void setUp() {
		auditService = mock(AuditService.class);
		userService = mock(UserService.class);
		jwtService = mock(JwtService.class);

		userController = new UserController(userService, auditService, jwtService);
	}

	@BeforeEach
	public void createUser() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
	}

	@Test
	@DisplayName("saveUser should save user")
	void saveUser_shouldSaveUser() {
		when(userService.saveUser(userDto))
				.thenReturn(userDto);

		userController.saveUser(userDto);

		verify(userService, times(1))
				.saveUser(userDto);
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.REGISTRATION, userDto.userId());
	}

	@Test
	@DisplayName("authUser should return UserDto")
	void authUser_shouldReturnUserDto() {
		when(userService.authUser(userDto))
				.thenReturn(userDto);

		//todo
		UserDto userData = null;

		assertThat(userData)
				.isEqualTo(userDto);
		verify(auditService)
				.saveUserAction(ActionEnum.LOG_IN, userDto.userId());
	}

	@Test
	@DisplayName("getUser should return user data by username")
	void getUser_shouldReturnUser() {
		when(userService.getUser(userDto.username()))
				.thenReturn(userDto);

		UserDto actual = userController.getUser(userDto.username());

		assertThat(actual)
				.isEqualTo(userDto);
	}
}