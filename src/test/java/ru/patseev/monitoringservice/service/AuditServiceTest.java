package ru.patseev.monitoringservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.domain.UserAction;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.repository.AuditRepository;
import ru.patseev.monitoringservice.service.impl.AuditServiceImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AuditServiceTest {

	private static AuditRepository auditRepository;
	private static AuditService auditService;

	private UserDto userDto;

	@BeforeAll
	static void setUp() {
		auditRepository = mock(AuditRepository.class);
		auditService = new AuditServiceImpl(auditRepository);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
	}

	@Test
	@DisplayName("saveUserAction should save user")
	void saveUserAction_shouldSaveUser() {
		auditService.saveUserAction(ActionEnum.REGISTRATION, userDto);

		verify(auditRepository, times(1))
				.save(anyInt(), any(UserAction.class));
	}

	@Test
	@DisplayName("getUserAction should return all user actions")
	void getUserAction_shouldReturnUserAction() {
		Timestamp registrationAt = Timestamp.from(Instant.now());
		Timestamp logInAt = Timestamp.from(Instant.now());

		List<UserAction> userActions = new ArrayList<>() {{
			add(new UserAction(1, registrationAt, ActionEnum.REGISTRATION, userDto.userId()));
			add(new UserAction(2, logInAt, ActionEnum.LOG_IN, userDto.userId()));
		}};

		List<UserActionDto> expected = new ArrayList<>() {{
			add(new UserActionDto(registrationAt.toLocalDateTime(), ActionEnum.REGISTRATION));
			add(new UserActionDto(logInAt.toLocalDateTime(), ActionEnum.LOG_IN));
		}};

		when(auditRepository.findUserActionsByUserId(userDto.userId()))
				.thenReturn(userActions);

		List<UserActionDto> actual = auditService.getUserAction(userDto.userId());

		assertThat(actual)
				.isEqualTo(expected);
	}

	@Test
	@DisplayName("getUserAction should throw an exception due to no user")
	void getUserAction_shouldThrowUserNotFoundException() {
		Mockito.when(auditRepository.findUserActionsByUserId(userDto.userId()))
				.thenReturn(null);

		assertThrows(UserNotFoundException.class,
				() -> auditService.getUserAction(userDto.userId())
		);
	}
}