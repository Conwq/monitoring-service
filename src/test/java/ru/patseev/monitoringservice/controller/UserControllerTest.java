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

	private static UserService userService;
	private static JwtService jwtService;
	private static UserController userController;

	private UserDto userDto;

	@BeforeAll
	public static void setUp() {
		userService = mock(UserService.class);
		jwtService = mock(JwtService.class);

		userController = new UserController(userService, jwtService);
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

		verify(userService)
				.saveUser(userDto);
	}

	@Test
	@DisplayName("authUser should return UserDto")
	void authUser_shouldReturnUserDto() {
		String jwtToken = "jwtToken";
		when(userService.authUser(userDto))
				.thenReturn(userDto);
		when(jwtService.generateToken(anyMap(), any(UserDto.class)))
				.thenReturn(jwtToken);

		String actual = userController.authUser(userDto);

		assertThat(actual)
				.isEqualTo(jwtToken);
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