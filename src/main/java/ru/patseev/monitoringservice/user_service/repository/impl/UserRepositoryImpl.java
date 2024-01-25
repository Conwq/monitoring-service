package ru.patseev.monitoringservice.user_service.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.user_service.db.UserDatabase;
import ru.patseev.monitoringservice.user_service.domain.User;
import ru.patseev.monitoringservice.user_service.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
	private final UserDatabase userDatabase;

	@Override
	public void saveUser(User user) {
		userDatabase.saveUser(user);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userDatabase.findUserByUsername(username);
	}
}