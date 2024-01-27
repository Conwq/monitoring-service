package ru.patseev.monitoringservice.audit_service.service.impl;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.audit_service.domain.UserAction;
import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.repository.AuditRepository;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;
import ru.patseev.monitoringservice.user_service.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class AuditServiceTest {
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

	@Test
	void mustSuccessfullyGetUserAction() {
		LocalDateTime registrationAt = LocalDateTime.now();
		LocalDateTime logInAt = LocalDateTime.now();

		List<UserAction> userActions = new ArrayList<>() {{
			add(new UserAction(registrationAt, ActionEnum.REGISTRATION));
			add(new UserAction(logInAt, ActionEnum.LOG_IN));
		}};

		List<UserActionDto> actual = new ArrayList<>() {{
			add(new UserActionDto(registrationAt, ActionEnum.REGISTRATION));
			add(new UserActionDto(logInAt, ActionEnum.LOG_IN));
		}};

		Mockito.when(auditRepository.findUserActionByUsername(userDto.username()))
				.thenReturn(userActions);

		List<UserActionDto> expected = auditService.getUserAction(userDto.username());

		AssertionsForClassTypes.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void mustThrowExceptionWhenGetUserAction() {
		Mockito.when(auditRepository.findUserActionByUsername(userDto.username()))
				.thenReturn(null);

		Assertions.assertThrows(UserNotFoundException.class,
				() -> auditService.getUserAction(userDto.username())
		);
	}
}