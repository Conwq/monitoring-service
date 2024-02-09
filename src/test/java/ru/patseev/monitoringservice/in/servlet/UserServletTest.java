package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.controller.AuditController;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.controller.UserController;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.in.extract.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.manager.OperationManager;

import static org.mockito.Mockito.*;

class UserServletTest {

	static final String JWT_TOKEN = "jwtToken";
	static final String AUTH_HEADER = "Authorization";
	static final String OPERATION_PARAM = "operation";

	HttpServletRequest req;
	HttpServletResponse resp;
	ResponseGenerator responseGenerator;
	UserController userController;
	ObjectExtractor objectExtractor;
	UserServlet userServlet;
	UserDto userDto;
	UserDto invalidUserData;

	@BeforeEach
	void setUp() {
		req = mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		AuditController auditController = mock(AuditController.class);

		objectExtractor = mock(ObjectExtractor.class);
		userController = mock(UserController.class);
		responseGenerator = mock(ResponseGenerator.class);
		MeterController meterController = mock(MeterController.class);
		OperationManager operationManager
				= new OperationManager(meterController, responseGenerator, userController, objectExtractor, auditController);

		userServlet = new UserServlet(operationManager);

		userDto = new UserDto(1, "username", "password", RoleEnum.USER);
		invalidUserData = new UserDto(null, "", "", null);
	}

	@Test
	@DisplayName("doPost should authorize the user, assign a header with a token and return a successful status")
	void doPost_shouldAuthorizationUser() {
		String operationName = "auth";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(objectExtractor.extractObject(req, UserDto.class))
				.thenReturn(userDto);
		when(userController.authUser(userDto))
				.thenReturn(JWT_TOKEN);

		userServlet.doPost(req, resp);

		verify(resp)
				.setHeader(AUTH_HEADER, JWT_TOKEN);
		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, JWT_TOKEN);
	}

	@Test
	@DisplayName("doPost should not authorize the user due to incorrect data entered and return a bad_request status")
	void doPost_shouldNotAuthorizationUser() {
		String operationName = "auth";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(objectExtractor.extractObject(req, UserDto.class))
				.thenReturn(invalidUserData);

		userServlet.doPost(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "The data is not valid");
	}

	@Test
	@DisplayName("should not authorize the user due to null which was returned from the controller and return the Unauthorier status")
	void doPost() {
		String operationName = "auth";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(objectExtractor.extractObject(req, UserDto.class))
				.thenReturn(userDto);
		when(userController.authUser(userDto))
				.thenReturn(null);

		userServlet.doPost(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

	@Test
	@DisplayName("should return a status code with confirmation of registration")
	void doPost_shouldRegistrationUser() {
		String operationName = "registration";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(objectExtractor.extractObject(req, UserDto.class))
				.thenReturn(userDto);
		when(userController.saveUser(userDto))
				.thenReturn(JWT_TOKEN);

		userServlet.doPost(req, resp);

		verify(resp)
				.setHeader(AUTH_HEADER, JWT_TOKEN);
		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, JWT_TOKEN);
	}

	@Test
	@DisplayName("should return a status code with invalid data if the data is not valid")
	void doPost_shouldNotRegistrationUser() {
		String operationName = "registration";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(objectExtractor.extractObject(req, UserDto.class))
				.thenReturn(invalidUserData);

		userServlet.doPost(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "The data is not valid");
	}
}