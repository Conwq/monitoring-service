package ru.patseev.monitoringservice.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.controller.UserController;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

	MockMvc mockMvc;
	UserService userService;
	JwtService jwtService;
	ResponseGenerator responseGenerator;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		jwtService = mock(JwtService.class);
		responseGenerator = spy(ResponseGenerator.class);

		UserController userController = new UserController(userService, jwtService, responseGenerator);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	@DisplayName("Test saving user successfully")
	void saveUser_success() throws Exception {
		UserDto userDto = new UserDto(null, "username", "password", null);
		UserDto savedUser = new UserDto(1, "username", "password", RoleEnum.USER);
		when(userService.saveUser(userDto))
				.thenReturn(savedUser);

		mockMvc.perform(poputJson("/users/register", userDto))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test saving user with conflict")
	void saveUser_conflict() throws Exception {
		UserDto userDto = new UserDto(null, "username", "password", null);
		when(userService.saveUser(userDto))
				.thenThrow(new UserAlreadyExistException("User with this username already exists"));

		mockMvc.perform(poputJson("/users/register", userDto))
				.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("Test user authentication successfully")
	void authUser_success() throws Exception {
		UserDto userDto = new UserDto(null, "username", "password", null);
		UserDto authData = new UserDto(1, "username", "password", RoleEnum.USER);

		when(userService.authUser(userDto))
				.thenReturn(authData);
		when(jwtService.generateToken(anyMap(), any(UserDto.class)))
				.thenReturn("jwtToken");

		mockMvc.perform(poputJson("/users/auth", userDto))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test user not found during authentication")
	void authUser_notFound() throws Exception {
		when(userService.authUser(any(UserDto.class)))
				.thenThrow(UserNotFoundException.class);

		mockMvc.perform(poputJson("/users/auth", new UserDto(null, "1", "1", null)))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Test unauthorized user")
	void authUser_unauthorized() throws Exception {
		UserDto userDto = new UserDto(null, "username", "password", null);
		UserDto authData = new UserDto(1, "username", "password", RoleEnum.USER);

		when(userService.authUser(userDto))
				.thenReturn(authData);
		when(jwtService.generateToken(anyMap(), any(UserDto.class)))
				.thenReturn(null);

		mockMvc.perform(poputJson("/users/auth", userDto))
				.andExpect(status().isUnauthorized());
	}

	public static MockHttpServletRequestBuilder poputJson(String uri, Object body) {
		try {
			String json = new ObjectMapper().writeValueAsString(body);
			return post(uri)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(json);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
