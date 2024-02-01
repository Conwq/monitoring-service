package ru.patseev.monitoringservice.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.repository.UserRepository;
import ru.patseev.monitoringservice.repository.impl.UserRepositoryImpl;

import java.util.Optional;

class UserRepositoryTest {
//	private static UserRepository userRepository;
//	private User user;
//
//	@BeforeEach
//	public void createUser() {
//		user = User.builder()
//				.username("test")
//				.password("test")
//				.role(Role.USER)
//				.build();
//	}
//
//	@BeforeAll
//	public static void setUp() {
//		userRepository = new UserRepositoryImpl();
//	}
//
//	@Test
//	void mustSuccessfullyRegisterUser() {
//		userRepository.saveUser(user);
//		Mockito.verify(, Mockito.times(1)).saveUser(user);
//	}
//
//	@Test
//	void mustFindUserByUsername() {
//		Mockito.when(userDatabase.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
//
//		Optional<User> optionalUser = userRepository.findUserByUsername(user.getUsername());
//
//		Mockito.verify(userDatabase, Mockito.times(1)).findUserByUsername(user.getUsername());
//		AssertionsForClassTypes.assertThat(optionalUser).isEqualTo(Optional.of(user));
//	}
}