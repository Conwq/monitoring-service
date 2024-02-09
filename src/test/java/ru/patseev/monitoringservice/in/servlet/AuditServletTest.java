package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.UserActionDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.in.extract.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.manager.OperationManager;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

class AuditServletTest {

	static final String JWT_TOKEN = "jwtToken";
	static final String AUTH_HEADER = "Authorization";
	static final String OPERATION_PARAM = "operation";

	HttpServletRequest req;
	HttpServletResponse resp;
	ResponseGenerator responseGenerator;
	AuditController auditController;
	AuditServlet auditServlet;

	@BeforeEach
	void setUp() {
		req = mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		auditController = mock(AuditController.class);

		ObjectExtractor objectExtractor = mock(ObjectExtractor.class);
		UserController userController = mock(UserController.class);
		responseGenerator = mock(ResponseGenerator.class);
		MeterController meterController = mock(MeterController.class);
		OperationManager operationManager
				= new OperationManager(meterController, responseGenerator, userController, objectExtractor, auditController);

		auditServlet = new AuditServlet(operationManager);
	}

	@Test
	@DisplayName("should return a list of all user actions")
	void doPost_shouldAuthorizationUser() {
		String usernameParam = "username";
		String username = "user123";
		String operationName = "get_audit";
		List<UserActionDto> listOfUserActions = List.of(
				new UserActionDto(Timestamp.from(Instant.now()), ActionEnum.REGISTRATION),
				new UserActionDto(Timestamp.from(Instant.now()), ActionEnum.AUTHORIZATION)
		);

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(req.getParameter(usernameParam))
				.thenReturn(username);
		when(auditController.getListOfUserActions(username, JWT_TOKEN))
				.thenReturn(listOfUserActions);

		auditServlet.doGet(req, resp);

		verify(responseGenerator).generateResponse(resp, HttpServletResponse.SC_OK, listOfUserActions);
	}
}