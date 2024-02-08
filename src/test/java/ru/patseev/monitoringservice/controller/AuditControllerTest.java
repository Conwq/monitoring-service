package ru.patseev.monitoringservice.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class AuditControllerTest {

	private static AuditService auditService;
	private static AuditController auditController;
	private static JwtService jwtService;
	private static UserController userController;

	private UserDto userDto;
	private List<UserActionDto> actionDtoList;

	@BeforeAll
	static void setUp() {
		auditService = mock(AuditService.class);
		userController = mock(UserController.class);
		jwtService = mock(JwtService.class);
		auditController = new AuditController(auditService, userController, jwtService);
	}

	@BeforeEach
	void createData() {
		userDto = new UserDto(1, "test", "test", RoleEnum.USER);
		actionDtoList = new ArrayList<>() {{
			add(new UserActionDto(Timestamp.from(Instant.now()), ActionEnum.REGISTRATION));
			add(new UserActionDto(Timestamp.from(Instant.now()), ActionEnum.LOG_IN));
		}};
	}

	@Test
	@DisplayName("getListUserActions should return list of UserActions")
	void getListOfUserActions_shouldReturnListUserActions() {
		when(userController.getUser(userDto.username()))
				.thenReturn(userDto);
		when(auditService.getUserAction(userDto.userId()))
				.thenReturn(actionDtoList);

		List<UserActionDto> expected = auditController.getListOfUserActions("test", "jwt_token");

		assertThat(actionDtoList)
				.isEqualTo(expected);
		verify(userController, times(1))
				.getUser(userDto.username());
		verify(auditService, times(1))
				.saveUserAction(ActionEnum.GET_USERS_ACTION, userDto.userId());
	}
}