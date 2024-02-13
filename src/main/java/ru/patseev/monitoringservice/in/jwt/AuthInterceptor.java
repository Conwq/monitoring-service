package ru.patseev.monitoringservice.in.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.patseev.monitoringservice.enums.RoleEnum;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;

	private final RouteValidator routeValidator;

	@Autowired
	public AuthInterceptor(JwtService jwtService, RouteValidator routeValidator) {
		this.jwtService = jwtService;
		this.routeValidator = routeValidator;
	}

	@Override
	public boolean preHandle(@NonNull HttpServletRequest req,
							 @NonNull HttpServletResponse resp,
							 @NonNull Object handler) {
		final String jwtToken = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (jwtToken != null) {

			if (routeValidator.isSecured.test(req)) {
				RoleEnum userRole = RoleEnum.valueOf(jwtService.extractRole(jwtToken).toUpperCase());

				String requestPath = req.getServletPath();
				RoleEnum accessRole = routeValidator.getCloseEndpoints().get(requestPath);

				if (accessRole != userRole) {
					sendResponse(resp, "Content is not available for you");
					return false;
				}
			}
			return true;
		}
		sendResponse(resp, "Please log in to the system");
		return false;
	}

	private void sendResponse(HttpServletResponse resp, String message) {
		try {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.getOutputStream().write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
