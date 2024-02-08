package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.domain.UserAction;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.manager.ResourceManager;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;
import ru.patseev.monitoringservice.repository.impl.AuditRepositoryImpl;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuditRepositoryTest extends AbstractPostgreSQLContainer {

	private static AuditRepository auditRepository;

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

		auditRepository = new AuditRepositoryImpl(connectionManager);
	}

	@Test
	@DisplayName("save must save the user action to the database and return from the database")
	void save_shouldSaveUserAction() {
		int userId = 1; //такой пользователь у меня уже будет на моменте миграции бд.
		Timestamp currentTime = Timestamp.valueOf("2000-01-01 00:00:00");

		UserAction registrationAction = UserAction.builder()
				.actionAt(currentTime)
				.action(ActionEnum.REGISTRATION)
				.userId(userId).build();

		UserAction logInAction = UserAction.builder()
				.actionAt(currentTime)
				.action(ActionEnum.LOG_IN)
				.userId(userId).build();

		auditRepository.save(userId, registrationAction);
		auditRepository.save(userId, logInAction);

		List<UserAction> actual = auditRepository.findUserActionsByUserId(userId);

		registrationAction.setActionId(1);
		logInAction.setActionId(2);

		assertThat(actual)
				.isNotNull();
		assertThat(actual)
				.isEqualTo(List.of(registrationAction, logInAction));
	}
}