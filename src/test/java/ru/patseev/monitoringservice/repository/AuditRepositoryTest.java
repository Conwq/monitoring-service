package ru.patseev.monitoringservice.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import ru.patseev.monitoringservice.domain.UserAction;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.manager.ConnectionManager;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;
import ru.patseev.monitoringservice.repository.impl.AuditRepositoryImpl;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuditRepositoryTest extends AbstractPostgreSQLContainer {

	private static AuditRepository auditRepository;

	@BeforeAll
	static void beforeAll() {
		auditRepository = new AuditRepositoryImpl(DATA_SOURCE, new ConnectionManager());
	}

	@Test
	@DisplayName("save must save the user action to the database and return from the database")
	void save_shouldSaveUserAction() {
		int userId = 1; //такой пользователь у меня уже будет на моменте миграции бд.
		Timestamp currentTime = Timestamp.valueOf("2000-01-01 00:00:00");

		UserAction registrationAction = new UserAction(null, currentTime, ActionEnum.REGISTRATION, userId);

		UserAction logInAction = new UserAction(null, currentTime, ActionEnum.AUTHORIZATION, userId);

		auditRepository.save(registrationAction);
		auditRepository.save(logInAction);

		List<UserAction> actual = auditRepository.findUserActionsByUserId(userId);

		registrationAction.setActionId(1);
		logInAction.setActionId(2);

		assertThat(actual)
				.isNotNull();
		assertThat(actual)
				.isEqualTo(List.of(registrationAction, logInAction));
	}
}