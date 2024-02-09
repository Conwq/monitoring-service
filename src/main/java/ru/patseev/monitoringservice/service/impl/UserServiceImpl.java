package ru.patseev.monitoringservice.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.aspect.annotation.Loggable;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.repository.RoleRepository;
import ru.patseev.monitoringservice.repository.UserRepository;
import ru.patseev.monitoringservice.service.UserService;
import ru.patseev.monitoringservice.service.mapper.UserMapper;

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
	 * The mapper for converting User entities to UserDto objects and vice versa.
	 */
	private final UserMapper userMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDto saveUser(UserDto userDto) {
		if (userRepository.existUserByUsername(userDto.username())) {
			throw new UserAlreadyExistException("User with this username already exists.");
		}

		User user = userMapper.toEntity(userDto);

		int generatedKey = userRepository.saveUser(user);
		user.setUserId(generatedKey);

		return userMapper.toDto(user, RoleEnum.USER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Loggable
	@Override
	public UserDto authUser(UserDto userDto) {
		User user = userRepository.findUserByUsername(userDto.username())
				.orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));

		if (!user.getPassword().equals(userDto.password())) {
			throw new UserNotFoundException("Пользователь не найден.");
		}

		Role role = roleRepository.getRoleById(user.getRoleId());
		RoleEnum roleEnum = RoleEnum.valueOf(role.getRoleName());

		return userMapper.toDto(user, roleEnum);
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
					return userMapper.toDto(user, roleEnum);
				})
				.orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
	}
}