package ru.patseev.monitoringservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.repository.RoleRepository;
import ru.patseev.monitoringservice.repository.UserRepository;
import ru.patseev.monitoringservice.service.impl.UserServiceImpl;
import ru.patseev.monitoringservice.service.mapper.UserMapper;

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

		UserMapper userMapper = UserMapper.instance;

		userService = new UserServiceImpl(userRepository, roleRepository, userMapper);
	}

	@BeforeEach
	public void createUser() {
		userDto = new UserDto(1, "test", null, RoleEnum.USER);
		user = new User(1, "test", "test", RoleEnum.USER.getRoleId());
		role = new Role(RoleEnum.USER.getRoleId(), RoleEnum.USER.name());
	}

	@Test
	@DisplayName("saveUser should save user")
	void saveUser_shouldSaveUser() {
		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.empty());
		when(userRepository.saveUser(any(User.class)))
				.thenReturn(1);

		UserDto actual = userService.saveUser(userDto);

		assertThat(actual)
				.isEqualTo(userDto);
	}

	@Test
	@DisplayName("saveUser should throw an exception because a user with the same username exists")
	void saveUser_shouldThrowUserAlreadyExistException() {
		when(userRepository.existUserByUsername(userDto.username()))
				.thenReturn(true);

		assertThrows(UserAlreadyExistException.class,
				() -> userService.saveUser(userDto)
		);
	}

	@Test
	@DisplayName("authUser should return user data")
	void authUser_shouldReturnUserDto() {
		UserDto request = new UserDto(1, "test", "test", RoleEnum.USER);

		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));
		when(roleRepository.getRoleById(user.getRoleId()))
				.thenReturn(role);

		UserDto actual = userService.authUser(request);

		assertThat(actual)
				.isEqualTo(userDto);
	}

	@Test
	@DisplayName("authUser should throw exception because input password invalid")
	void authUser_shouldThrowExceptionWhenInvalidPassword() {
		user.setPassword("incorrect_password");

		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));

		assertThrows(UserNotFoundException.class,
				() -> userService.authUser(userDto)
		);
	}

	@Test
	@DisplayName("authUser should throw exception because the user with the entered username not exist")
	void authUser_shouldThrowExceptionWhenInvalidUsername() {
		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class,
				() -> userService.authUser(userDto)
		);
	}

	@Test
	@DisplayName("getUser should return user data by username")
	void getUser_shouldReturnUser() {
		when(userRepository.findUserByUsername(userDto.username()))
				.thenReturn(Optional.of(user));
		when(roleRepository.getRoleById(userDto.role().getRoleId()))
				.thenReturn(role);

		UserDto actual = userService.getUser(userDto.username());

		assertThat(actual)
				.isEqualTo(userDto);
	}

	@Test
	@DisplayName("getUser should throw an exception when the name of the user you are looking for does not exist")
	void getUser_shouldThrowUSerNotFoundException() {
		String testUsername = "not_found_user";

		when(userRepository.findUserByUsername(testUsername))
				.thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class,
				() -> userService.getUser(testUsername));
	}
}