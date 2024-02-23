package ru.patseev.monitoringservice.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.patseev.auditstarter.dto.UserActionDto;
import ru.patseev.auditstarter.manager.enums.ActionEnum;
import ru.patseev.auditstarter.service.AuditService;
import ru.patseev.monitoringservice.config.annotation.DisableLiquibaseMigration;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.controller.UserController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DisableLiquibaseMigration
class AuditControllerTest {

	@MockBean
	AuditService auditService;
	@MockBean
	UserController userController;
	MockMvc mockMvc;

	@Autowired
	public AuditControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
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