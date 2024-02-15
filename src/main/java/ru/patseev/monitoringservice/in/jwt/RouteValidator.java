package ru.patseev.monitoringservice.in.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import ru.patseev.monitoringservice.enums.RoleEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The RouteValidator class validates routes based on their associated operations and roles.
 */
@Component
public class RouteValidator {

	/**
	 * The role required for accessing each operation.
	 */
	private static final RoleEnum USER_ROLE = RoleEnum.USER;
	private static final RoleEnum ADMIN_ROLE = RoleEnum.ADMIN;

	/**
	 * The map containing close endpoints (operations requiring specific roles).
	 */
	private final Map<String, RoleEnum> closeEndpoints = new HashMap<>() {{
		put("/audits", ADMIN_ROLE);
		put("/meters/last_data", USER_ROLE);
		put("/meters/save_data", USER_ROLE);
		put("/meters/specified_month", USER_ROLE);
		put("/meters/data", USER_ROLE);
		put("/meters/all_data", ADMIN_ROLE);
		put("/meters/meter_types", USER_ROLE);
		put("/meters/save_meter", ADMIN_ROLE);
	}};

	/**
	 * The set containing open endpoints (operations available to all users).
	 */
	private final Set<String> openEndpoints = new HashSet<>() {{
		add("/users/*");
	}};

	/**
	 * Predicate indicating whether an endpoint is secured.
	 */
	public final Predicate<HttpServletRequest> isSecured =
			request -> openEndpoints.stream()
					.noneMatch(path -> request.getServletPath().contains(path));

	/**
	 * Retrieves the map of close endpoints and their associated roles.
	 *
	 * @return The map of close endpoints and their associated roles.
	 */
	public Map<String, RoleEnum> getCloseEndpoints() {
		return closeEndpoints;
	}
}
