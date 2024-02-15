package ru.patseev.monitoringservice.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.patseev.monitoringservice.context.MonitoringApplicationContext;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;

import java.io.IOException;

/**
 * JWT authentication filter for securing access to resources.
 */
@WebFilter(urlPatterns = {"/meters/*", "/audits/*"})
public class JwtAuthFilter extends HttpFilter {

	/**
	 * RouteValidator for validating routes and roles.
	 */
	private final RouteValidator routeValidator = new RouteValidator();

	/**
	 * JwtService for JWT-related operations.
	 */
	private final JwtService jwtService = MonitoringApplicationContext.getContext().getJwtService();

	/**
	 * ResponseGenerator for generating HTTP responses.
	 */
	private final ResponseGenerator responseGenerator = MonitoringApplicationContext.getContext().getResponseGenerator();

	/**
	 * Filters incoming HTTP requests to authenticate and authorize access to resources.
	 *
	 * @param req    The HttpServletRequest object representing the HTTP request.
	 * @param res    The HttpServletResponse object representing the HTTP response.
	 * @param chain  The FilterChain object for invoking the next filter in the chain.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 * @throws ServletException If the servlet encounters difficulty while handling the request.
	 */
	@Override
	protected void doFilter(HttpServletRequest req,
							HttpServletResponse res,
							FilterChain chain) throws IOException, ServletException {
		String errorMessage = "Access to resource is denied";
		String authHeaderParam = "Authorization";

		String jwtToken = req.getHeader(authHeaderParam);

		if (jwtToken == null) {
			responseGenerator.generateResponse(res, HttpServletResponse.SC_FORBIDDEN, errorMessage);
			return;
		}

		if (routeValidator.isSecured.test(req)) {
			OperationEnum operation = OperationEnum.valueOf(req.getParameter("operation").toUpperCase());
			RoleEnum roleForOperation = routeValidator.getRoleForOperation(operation);
			RoleEnum userRole = RoleEnum.valueOf(jwtService.extractRole(jwtToken));

			if (roleForOperation != userRole) {
				responseGenerator.generateResponse(res, HttpServletResponse.SC_FORBIDDEN, errorMessage);
				return;
			}
		}

		res.setHeader(authHeaderParam, jwtToken);
		chain.doFilter(req, res);
	}
}
