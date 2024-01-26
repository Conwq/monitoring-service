package ru.patseev.monitoringservice.user_service.service.impl;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.domain.User;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;
import ru.patseev.monitoringservice.user_service.service.UserService;

import java.util.Optional;

class UserServiceTest {
	private static UserRepository userRepository;
	private static UserService userService;
	private UserDto userDto;
	private User user;

	@BeforeAll
	public static void setUp() {
		userRepository = Mockito.mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository);
	}

	@BeforeEach
	public void createUser() {
		userDto = new UserDto("test", "test", Role.USER);
		user = new User("test", "test", Role.USER);
	}

	@Test
	void mustSaveUser() {
		Mockito.when(userRepository.findUserByUsername(userDto.username())).thenReturn(Optional.empty());

		userService.saveUser(userDto);

		Mockito.verify(userRepository, Mockito.times(1)).saveUser(user);
	}

	@Test
	void mustThrowExceptionWhenSaving() {
		Mockito.when(userRepository.findUserByUsername(userDto.username())).thenReturn(Optional.of(user));

		Assertions.assertThrows(UserAlreadyExistException.class,
				() -> userService.saveUser(userDto));
	}

	@Test
	void mustLogInSuccessfully() {
		Mockito.when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));

		UserDto userData = userService.authUser(userDto);

		AssertionsForClassTypes.assertThat(userData).isEqualTo(userDto);
	}

	@Test
	void mustThrowExceptionIfPasswordIsIncorrect() {
		user.setPassword("incorrect_password");

		Mockito.when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));

		Assertions.assertThrows(UserNotFoundException.class,
				() -> userService.authUser(userDto));
	}

	@Test
	void mustThrowExceptionWhenLoginIsInvalid() {
		Mockito.when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.empty());

		Assertions.assertThrows(UserNotFoundException.class,
				() -> userService.authUser(userDto));
	}
}