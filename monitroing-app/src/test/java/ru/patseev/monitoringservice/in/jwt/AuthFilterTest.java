package ru.patseev.monitoringservice.in.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import ru.patseev.monitoringservice.config.annotation.DisableLiquibaseMigration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisableLiquibaseMigration
class AuthFilterTest {

	@MockBean
	JwtService jwtService;
	RouteValidator routeValidator;
	MockMvc mockMvc;

	@Autowired
	public AuthFilterTest(RouteValidator routeValidator, MockMvc mockMvc) {
		this.routeValidator = routeValidator;
		this.mockMvc = mockMvc;
	}

	@Test
	@DisplayName("Accessing Inaccessible Content")
	void doFilterInternal_inaccessibleContent() throws Exception {
		when(jwtService.extractRole("auth_token")).thenReturn("USER");

		mockMvc.perform(get("/meters/all_data")
						.header(HttpHeaders.AUTHORIZATION, "auth_token"))
				.andExpect(status().isForbidden())
				.andExpect(content().string("Content is not available for you"));
	}

	@Test
	@DisplayName("Unauthorized Access")
	void doFilterInternal_notAuthorized() throws Exception {
		mockMvc.perform(get("/meters/all_data"))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string("Please log in to the system"));
	}
}