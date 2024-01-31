package ru.patseev.monitoringservice.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.dto.RoleEnum;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.repository.RoleRepository;
import ru.patseev.monitoringservice.repository.UserRepository;
import ru.patseev.monitoringservice.service.UserService;

import java.util.Optional;

/**
 * The UserServiceImpl class is an implementation of the UserService interface.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	/**
	 * The user repository used for interacting with user data storage.
	 */
	private final UserRepository userRepository;

	/**
	 * The role repository used to interact with the role store.
	 */
	private final RoleRepository roleRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto saveUser(UserDto userDto) {
		Optional<User> optionalUser = userRepository.findUserByUsername(userDto.username());

		if (optionalUser.isPresent()) {
			throw new UserAlreadyExistException("Такой пользователь уже существует.");
		}

		User user = User.builder()
				.username(userDto.username())
				.password(userDto.password())
				.roleId(RoleEnum.USER.getRoleId())
				.build();

		int generatedKey = userRepository.saveUser(user);

		return new UserDto(generatedKey, userDto.username(), null, RoleEnum.USER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto authUser(UserDto userDto) {
		User user = userRepository.findUserByUsername(userDto.username())
				.orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));

		if (!user.getPassword().equals(userDto.password())) {
			throw new UserNotFoundException("Пользователь не найден.");
		}

		Role role = roleRepository.getRoleById(user.getRoleId());
		RoleEnum roleEnum = RoleEnum.valueOf(role.getRoleName());

		return toDto(user, roleEnum);
	}

	/**
	 * Retrieves a user DTO by the specified username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return A UserDto object representing the user with the specified username.
	 * @throws UserNotFoundException If the user with the specified username is not found.
	 */
	@Override
	public UserDto getUser(String username) {
		return userRepository.findUserByUsername(username)
				.map(user -> {
					Role role = roleRepository.getRoleById(user.getRoleId());
					RoleEnum roleEnum = RoleEnum.valueOf(role.getRoleName());
					return toDto(user, roleEnum);
				})
				.orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
	}

	/**
	 * Converts a User object to a code UserDto object.
	 *
	 * @param user     The User object to be converted.
	 * @param roleEnum The code RoleEnum associated with the user.
	 * @return A UserDto object representing the converted user.
	 */
	private UserDto toDto(User user, RoleEnum roleEnum) {
		return new UserDto(
				user.getUserId(),
				user.getUsername(),
				null,
				roleEnum
		);
	}
}