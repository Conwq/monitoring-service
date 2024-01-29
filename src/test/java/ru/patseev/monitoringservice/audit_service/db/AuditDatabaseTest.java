package ru.patseev.monitoringservice.audit_service.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuditDatabaseTest {

	private AuditDatabase auditDatabase;
	private UserAction userAction;

	@BeforeEach
	void setUp() {
		auditDatabase = new AuditDatabase();
		userAction = new UserAction(LocalDateTime.now(), ActionEnum.REGISTRATION);
	}

	@Test
	void saveUserAction_shouldAddUserAuditToDatabase() {
		auditDatabase.saveUserAction("test", userAction);

		List<UserAction> actualAction = auditDatabase.getUserActions("test");

		assertThat(actualAction).isNotNull();
		assertThat(actualAction.get(0)).isEqualTo(userAction);
	}

	@Test
	void getUserActions_shouldReturnNullIfActionNotFound() {
		List<UserAction> actual = auditDatabase.getUserActions("test");
		assertThat(actual).isNull();
	}
}