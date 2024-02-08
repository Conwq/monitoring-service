package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.User;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;
import ru.patseev.monitoringservice.repository.impl.UserRepositoryImpl;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserRepositoryTest extends AbstractPostgreSQLContainer {

	private static UserRepository userRepository;

	private User user;

	@BeforeAll
	static void beforeAll() {
		ConnectionManager connectionManager = new ConnectionManager(
				POSTGRES.getJdbcUrl(),
				POSTGRES.getUsername(),
				POSTGRES.getPassword()
		);

		ResourceManager resourceManager = new ResourceManager("application");

		new LiquibaseMigration(connectionManager, resourceManager)
				.performMigration();

		userRepository = new UserRepositoryImpl(connectionManager);
	}

	@BeforeEach
	void setUp() {
		user = User.builder()
				.username("test")
				.password("test")
				.roleId(RoleEnum.USER.getRoleId()).build();
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