package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.patseev.monitoringservice.domain.Role;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.RoleNotExistsException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest
class RoleRepositoryTest {

	@Container
	@ServiceConnection
	@SuppressWarnings("unused")
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
	RoleRepository roleRepository;

	@Autowired
	public RoleRepositoryTest(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
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