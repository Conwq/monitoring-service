package ru.patseev.monitoringservice.audit_service.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

class AuditServiceImplTest {
	private static AuditRepository auditRepository;
	private static AuditService auditService;
	private UserDto userDto;

	@BeforeAll
	static void setUp() {
		auditRepository = Mockito.mock(AuditRepository.class);
		auditService = new AuditServiceImpl(auditRepository);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto("test", "test", Role.USER);
	}

	@Test
	void mustSuccessfullySaveUserAction() {
		auditService.saveUserAction(ActionEnum.REGISTRATION, userDto);

		Mockito.verify(auditRepository, Mockito.times(1))
				.save(Mockito.anyString(), Mockito.any(UserAction.class));
	}
}