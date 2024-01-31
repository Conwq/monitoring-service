package ru.patseev.monitoringservice.user_service.repository.impl;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.repository.impl.UserRepositoryImpl;
import ru.patseev.monitoringservice.user_service.db.UserDatabase;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.repository.UserRepository;

import java.util.Optional;

class UserRepositoryTest {
	private static UserDatabase userDatabase;
	private static UserRepository userRepository;
	private User user;

	@BeforeEach
	public void createUser() {
		user = User.builder()
				.username("test")
				.password("test")
				.role(Role.USER)
				.build();
	}

	@BeforeAll
	public static void setUp() {
		userDatabase = Mockito.mock(UserDatabase.class);
		userRepository = new UserRepositoryImpl(userDatabase);
	}

	@Test
	void mustSuccessfullyRegisterUser() {
		userRepository.saveUser(user);
		Mockito.verify(userDatabase, Mockito.times(1)).saveUser(user);
	}

	@Test
	void mustFindUserByUsername() {
		Mockito.when(userDatabase.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

		Optional<User> optionalUser = userRepository.findUserByUsername(user.getUsername());

		Mockito.verify(userDatabase, Mockito.times(1)).findUserByUsername(user.getUsername());
		AssertionsForClassTypes.assertThat(optionalUser).isEqualTo(Optional.of(user));
	}
}