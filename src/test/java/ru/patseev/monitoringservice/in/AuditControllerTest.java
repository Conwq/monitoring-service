package ru.patseev.monitoringservice.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.controller.AuditController;
import ru.patseev.monitoringservice.in.controller.UserController;
import ru.patseev.monitoringservice.service.AuditService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuditControllerTest {

	MockMvc mockMvc;
	AuditService auditService;
	UserController userController;

	@BeforeEach
	void setUp() {
		auditService = mock(AuditService.class);
		userController = mock(UserController.class);

		AuditController auditController = new AuditController(auditService, userController);
		mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
	}

	@Test
	@DisplayName("Test successful retrieval of user actions")
	void getListOfUserActions_successful() throws Exception {
		String username = "testUser";
		String jwtToken = "jwtToken";
		UserDto searchedUser = new UserDto(1, username, "password", RoleEnum.USER);

		List<UserActionDto> userActions = new ArrayList<>();
		userActions.add(new UserActionDto(Timestamp.from(Instant.now()), ActionEnum.REGISTRATION));
		userActions.add(new UserActionDto(Timestamp.from(Instant.now()), ActionEnum.AUTHORIZATION));

		when(userController.getUser(username)).thenReturn(searchedUser);
		when(auditService.getUserActions(searchedUser.userId())).thenReturn(userActions);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/audits")
				.param("username", username)
				.header(HttpHeaders.AUTHORIZATION, jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(userActions.size()))
				.andExpect(jsonPath("$[0].action").value(userActions.get(0).action().name()))
				.andExpect(jsonPath("$[1].action").value(userActions.get(1).action().name()));
	}

	@Test
	@DisplayName("Test when user is not found")
	void getListOfUserActions_notFoundUser() throws Exception {
		String username = "testUser";
		String jwtToken = "jwtToken";

		when(userController.getUser(username)).thenThrow(UserNotFoundException.class);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/audits")
				.param("username", username)
				.header(HttpHeaders.AUTHORIZATION, jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
				.andExpect(status().isNotFound());
	}
}
