package ru.patseev.monitoringservice.user_service.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.domain.User;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDatabaseTest {

	private UserDatabase userDatabase;

	@BeforeEach
	void setUp() {
		userDatabase = new UserDatabase();
	}

	@Test
	void saveUser_shouldIncrementIdAndAddUserToDatabase() {
		User newUser = User.builder()
				.username("test")
				.password("test")
				.role(Role.USER)
				.build();

		userDatabase.saveUser(newUser);

		assertThat(newUser).isNotNull();
		assertThat(userDatabase.findUserByUsername("test"))
				.isEqualTo(Optional.of(newUser));
	}

	@Test
	void findUserByUsername_shouldReturnUserIfPresent() {
		String usernameToFind = "admin";

		Optional<User> foundUser = userDatabase.findUserByUsername(usernameToFind);

		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getUsername())
				.isEqualTo(usernameToFind);
	}

	@Test
	void findUserByUsername_shouldReturnEmptyOptionalIfUserNotFound() {
		String usernameToFind = "nonExistentUser";

		Optional<User> foundUser = userDatabase.findUserByUsername(usernameToFind);

		assertThat(foundUser).isEmpty();
	}
}