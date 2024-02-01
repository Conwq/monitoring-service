package ru.patseev.monitoringservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.repository.RoleRepository;
import ru.patseev.monitoringservice.repository.UserRepository;
import ru.patseev.monitoringservice.service.UserService;
import ru.patseev.monitoringservice.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

	private static UserRepository userRepository;
	private static RoleRepository roleRepository;
	private static UserService userService;
	private UserDto userDto;
	private User user;
	private Role role;

	@BeforeAll
	public static void setUp() {
		userRepository = mock(UserRepository.class);
		roleRepository = mock(RoleRepository.class);
		userService = new UserServiceImpl(userRepository, roleRepository);
	}

	@BeforeEach
	public void createUser() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
		user = new User(1, "test", "test", RoleEnum.USER.getRoleId());
		role = new Role(RoleEnum.USER.getRoleId(), RoleEnum.USER.name());
	}

	@Test
	void saveUser_shouldSaveUser() {
		UserDto test = new UserDto(1, "test", null, RoleEnum.USER);
		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.empty());
		when(userRepository.saveUser(any(User.class)))
				.thenReturn(1);

		UserDto actual = userService.saveUser(userDto);

		assertThat(test)
				.isEqualTo(actual);
	}

	@Test
	void saveUser_shouldThrowUserAlreadyExistException() {
		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));

		assertThrows(UserAlreadyExistException.class,
				() -> userService.saveUser(userDto)
		);
	}

	@Test
	void authUser_shouldReturnUserDto() {
		UserDto test = new UserDto(1, "test", null, RoleEnum.USER);

		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));
		when(roleRepository.getRoleById(user.getRoleId()))
				.thenReturn(role);

		UserDto userData = userService.authUser(userDto);

		assertThat(userData)
				.isEqualTo(test);
	}

	@Test
	void authUser_shouldThrowExceptionWhenInvalidPassword() {
		user.setPassword("incorrect_password");

		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));

		assertThrows(UserNotFoundException.class,
				() -> userService.authUser(userDto)
		);
	}

	@Test
	void authUser_shouldThrowExceptionWhenInvalidUsername() {
		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class,
				() -> userService.authUser(userDto)
		);
	}

	//todo getUser
}