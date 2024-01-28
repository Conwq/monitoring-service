package ru.patseev.monitoringservice.user_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.user_service.db.UserDatabase;
import ru.patseev.monitoringservice.user_service.domain.User;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;

import java.util.Optional;

/**
 * The UserRepositoryImpl class is an implementation of the UserRepository interface.
 * It provides methods for interacting with user data storage.
 */
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	/**
	 * The user database used for storing user-related information.
	 */
	private final UserDatabase userDatabase;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveUser(User user) {
		userDatabase.saveUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<User> findUserByUsername(String username) {
		return userDatabase.findUserByUsername(username);
	}
}