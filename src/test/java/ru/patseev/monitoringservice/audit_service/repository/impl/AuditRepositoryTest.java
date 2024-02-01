package ru.patseev.monitoringservice.audit_service.repository.impl;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.audit_service.db.AuditDatabase;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class AuditRepositoryTest {
	private static AuditDatabase auditDatabase;
	private static AuditRepository auditRepository;

	@BeforeAll
	static void setUp() {
		auditDatabase = Mockito.mock(AuditDatabase.class);
		auditRepository = new AuditRepositoryImpl(auditDatabase);
	}

	@Test
	void mustSaveUser() {
		auditRepository.save("test", new UserAction());

		Mockito.verify(auditDatabase, Mockito.atLeastOnce())
				.saveUserAction(Mockito.anyString(), Mockito.any(UserAction.class));
	}

	@Test
	void mustFindUserActionByUsername() {
		LocalDateTime registrationAt = LocalDateTime.now();
		LocalDateTime logInAt = LocalDateTime.now();

		List<UserAction> actual = new ArrayList<>() {{
			add(new UserAction(registrationAt, ActionEnum.REGISTRATION));
			add(new UserAction(logInAt, ActionEnum.LOG_IN));
		}};

		Mockito.when(auditDatabase.getUserActions("test"))
				.thenReturn(actual);

		List<UserAction> expected = auditDatabase.getUserActions("test");

		AssertionsForClassTypes.assertThat(actual).isEqualTo(expected);
	}
}