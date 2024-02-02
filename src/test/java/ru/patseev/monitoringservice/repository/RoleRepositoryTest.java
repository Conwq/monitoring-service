package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.RoleNotExistsException;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;
import ru.patseev.monitoringservice.repository.impl.RoleRepositoryImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleRepositoryTest extends AbstractPostgreSQLContainer {

	private static RoleRepository roleRepository;

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

		roleRepository = new RoleRepositoryImpl(connectionManager);
	}

	@Test
	@DisplayName("getRoleById should return role")
	void getRoleById_shouldReturnRole() {
		Role expected = new Role(RoleEnum.USER.getRoleId(), RoleEnum.USER.name());

		Role actual = roleRepository.getRoleById(RoleEnum.USER.getRoleId());

		assertThat(actual)
				.isEqualTo(expected);
	}

	@Test
	@DisplayName("getRoleById should throw an exception because the role with id was not found")
	void getRoleById_shouldThrowException() {
		assertThrows(RoleNotExistsException.class,
				() -> roleRepository.getRoleById(3));
	}
}