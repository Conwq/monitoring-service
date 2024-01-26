package ru.patseev.monitoringservice.audit_service.controller;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.patseev.monitoringservice.audit_service.dto.UserActionDto;
import ru.patseev.monitoringservice.audit_service.enums.ActionEnum;
import ru.patseev.monitoringservice.audit_service.service.AuditService;
import ru.patseev.monitoringservice.user_service.domain.Role;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuditControllerTest {
	private static AuditService auditService;
	private static AuditController auditController;
	private UserDto userDto;
	private List<UserActionDto> actionDtoList;

	@BeforeAll
	static void setUp() {
		auditService = Mockito.mock(AuditService.class);
		auditController = new AuditController(auditService);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto("test", "test", Role.USER);
		actionDtoList = new ArrayList<>(){{
			add(new UserActionDto(LocalDateTime.now(), ActionEnum.REGISTRATION));
			add(new UserActionDto(LocalDateTime.now(), ActionEnum.LOG_IN));
		}};
	}

	@Test
	void successfullyObtainingListUserActions() {
		Mockito.when(auditService.getUserAction(userDto.username())).thenReturn(actionDtoList);

		List<UserActionDto> expected = auditController.getListOfUserActions("test", userDto);

		AssertionsForClassTypes.assertThat(actionDtoList).isEqualTo(expected);

		Mockito.verify(auditService, Mockito.times(1))
				.saveUserAction(ActionEnum.GET_USERS_ACTION, userDto);
	}
}