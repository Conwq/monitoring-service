package ru.patseev.monitoringservice.in.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.patseev.monitoringservice.enums.RoleEnum;

import java.io.IOException;

/**
 * Filter for handling JWT-based authentication and authorization.
 * This filter is responsible for intercepting incoming HTTP requests
 * and checking whether the user is authenticated and authorized to access
 * the requested resource.
 */
//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

	/**
	 * The JwtService instance for JWT-related operations.
	 */
	private final JwtService jwtService;

	/**
	 * The RouteValidator instance for validating routes and roles.
	 */
	private final RouteValidator routeValidator;

	/**
	 * Method for performing the actual filtering of the request.
	 *
	 * @param req         The HttpServletRequest object representing the HTTP request.
	 * @param resp        The HttpServletResponse object representing the HTTP response.
	 * @param filterChain The FilterChain object that allows the filter to pass
	 *                    on the request and response to the next entity in the chain.
	 * @throws ServletException If an exception has occurred that interferes with
	 *                          the normal operation of the filter.
	 * @throws IOException      If an I/O exception has occurred.
	 */

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest req,
									@NonNull HttpServletResponse resp,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String jwtToken = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (jwtToken == null) {
			sendResponse(resp, "Please log in to the system");
			return;
		}

		if (routeValidator.isSecured.test(req)) {
			handleAuthorization(req, resp, jwtToken);
		}
		filterChain.doFilter(req, resp);
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

	/**
	 * Handles authorization logic based on user role and requested endpoint.
	 *
	 * @param req      The HttpServletRequest object representing the HTTP request.
	 * @param resp     The HttpServletResponse object representing the HTTP response.
	 * @param jwtToken The JWT token extracted from the request header.
	 * @throws IOException if an input or output exception occurs while handling authorization.
	 */
	private void handleAuthorization(HttpServletRequest req, HttpServletResponse resp, String jwtToken) throws IOException {
		RoleEnum userRole = RoleEnum.valueOf(jwtService.extractRole(jwtToken).toUpperCase());
		String requestPath = req.getServletPath();
		RoleEnum accessRole = routeValidator.getCloseEndpoints().get(requestPath);

		if (accessRole != userRole) {
			sendResponse(resp, "Content is not available for you");
		}
	}
}
