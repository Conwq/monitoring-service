package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.enums.RoleEnum;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
class UserRepositoryTest {

	@Container
	@ServiceConnection
	@SuppressWarnings("unused")
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
	UserRepository userRepository;
	User user;

	@Autowired
	public UserRepositoryTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@BeforeEach
	void setUp() {
		user = new User(null, "test", "test", RoleEnum.USER.getRoleId());
	}

	@Test
	@DisplayName("saveUser should save the user in the database and return it")
	void saveUser_shouldSaveUser() {
		Integer userId = userRepository.saveUser(user);
		user.setUserId(userId);

		Optional<User> actual = userRepository.findUserByUsername(user.getUsername());

		assertThat(actual)
				.isPresent();
		assertThat(actual)
				.isEqualTo(Optional.of(user));
		assertThat(userId)
				.isEqualTo(2); //потому что на этапе миграции, у меня уже добавляется пользователь с id = 1
	}
}