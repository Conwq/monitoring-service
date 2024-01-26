package ru.patseev.monitoringservice.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.user_service.domain.User;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;
import ru.patseev.monitoringservice.user_service.service.UserService;

import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public void saveUser(UserDto userDto) {
		Optional<User> optionalUser = userRepository.findUserByUsername(userDto.username());

		if (optionalUser.isPresent()) {
			throw new UserAlreadyExistException("Такой пользователь уже существует.");
		}

		User user = User.builder()
				.username(userDto.username())
				.password(userDto.password())
				.role(userDto.role())
				.build();

		userRepository.saveUser(user);
	}

	@Override
	public UserDto authUser(UserDto userDto) {
		User user = userRepository.findUserByUsername(userDto.username())
				.orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));

		if (!user.getPassword().equals(userDto.password())) {
			throw new UserNotFoundException("Пользователь не найден.");
		}

		return new UserDto(
				user.getUsername(),
				user.getPassword(),
				user.getRole()
		);
	}
}