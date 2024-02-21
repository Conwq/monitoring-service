package ru.patseev.monitoringservice.in.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.patseev.monitoringservice.enums.RoleEnum;

import java.io.IOException;

/**
 * Interceptor for handling JWT-based authentication and authorization.
 * This interceptor is responsible for intercepting incoming HTTP requests
 * and checking whether the user is authenticated and authorized to access
 * the requested resource.
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * The JwtService instance for JWT-related operations.
	 */
	private final JwtService jwtService;

	/**
	 * The RouteValidator instance for validating routes and roles.
	 */
	private final RouteValidator routeValidator;

	/**
	 * Intercepts the incoming HTTP request to authenticate and authorize access to resources.
	 *
	 * @param req     The HttpServletRequest object representing the HTTP request.
	 * @param resp    The HttpServletResponse object representing the HTTP response.
	 * @param handler The handler object for the intercepted request.
	 * @return true if the request is allowed to proceed, false otherwise.
	 */
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

	/**
	 * Sends an unauthorized response with the specified message.
	 *
	 * @param resp    The HttpServletResponse object representing the HTTP response.
	 * @param message The message to be included in the response body.
	 */
	private void sendResponse(HttpServletResponse resp, String message) {
		try {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.getOutputStream().write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
