package ru.patseev.monitoringservice.jwt;

import jakarta.servlet.http.HttpServletRequest;
import ru.patseev.monitoringservice.enums.OperationEnum;
import ru.patseev.monitoringservice.enums.RoleEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The RouteValidator class validates routes based on their associated operations and roles.
 */
public class RouteValidator {

	/**
	 * The role required for accessing each operation.
	 */
	private static final RoleEnum USER_ROLE = RoleEnum.USER;
	private static final RoleEnum ADMIN_ROLE = RoleEnum.ADMIN;

	/**
	 * The map containing close endpoints (operations requiring specific roles).
	 */
	private final Map<OperationEnum, RoleEnum> closeEndpoints = new HashMap<>() {{
		put(OperationEnum.LAST_DATA, USER_ROLE);
		put(OperationEnum.SPECIFIED_MONTH_DATA, USER_ROLE);
		put(OperationEnum.USER_DATA, USER_ROLE);
		put(OperationEnum.ALL_METER_TYPES, USER_ROLE);
		put(OperationEnum.SAVE_DATA, USER_ROLE);

		put(OperationEnum.ALL_DATA, ADMIN_ROLE);
		put(OperationEnum.ADD_METER_TYPE, ADMIN_ROLE);

		put(OperationEnum.GET_AUDIT, ADMIN_ROLE);
	}};

	/**
	 * The set containing open endpoints (operations accessible without specific roles).
	 */
	private final Set<OperationEnum> openEndpoints = new HashSet<>() {{
		add(OperationEnum.AUTH);
		add(OperationEnum.REGISTRATION);
	}};

	/**
	 * Gets the required role for a specific operation.
	 *
	 * @param operationEnum The operation for which to retrieve the required role.
	 * @return The required role for the operation.
	 */
	public RoleEnum getRoleForOperation(OperationEnum operationEnum) {
		return closeEndpoints.get(operationEnum);
	}

	/**
	 * Predicate indicating whether an endpoint is secured.
	 */
	public final Predicate<HttpServletRequest> isSecured =
			request -> openEndpoints.stream()
					.noneMatch(operation -> request.getParameter("operation").toUpperCase().contains(operation.name()));
}